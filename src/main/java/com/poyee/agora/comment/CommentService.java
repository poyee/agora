package com.poyee.agora.comment;

import com.poyee.agora.comment.bean.CommentDto;
import com.poyee.agora.comment.bean.CommentRequest;
import com.poyee.agora.entity.Comment;
import com.poyee.agora.entity.User;
import com.poyee.agora.exception.ForbiddenException;
import com.poyee.agora.exception.NotFoundException;
import com.poyee.agora.redis.RedisService;
import com.poyee.agora.user.LocalUser;
import com.poyee.agora.utils.RedisUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final ModelMapper mapper;

    private final CommentRepository repository;

    private final RedisService redisService;


    @Autowired
    public CommentService(CommentRepository repository,
                          RedisService redisService,
                          ModelMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.redisService = redisService;
    }

    public void comment(CommentRequest request) {
        Comment entity = toEntity(request);

        repository.save(entity);

        updateRedisPollCommentCount(request.getPollId());
    }

    public List<CommentDto> getComments(Long pollId, LocalUser user) {
        List<Comment> entities = repository.findAllByPollId(pollId);

        if (Objects.nonNull(user)) {
            entities.forEach(comment -> populateEditable(comment, user.getUser()));
        }

        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public void deleteComment(Long id, LocalUser user) {
        Optional<Comment> commentOpt = repository.findById(id);

        if (!commentOpt.isPresent()) {
            throw new NotFoundException("留言不存在");
        }

        Comment comment = commentOpt.get();

        if (!comment.getUser().getId().equals(user.getUser().getId())) {
            throw new ForbiddenException("無權限刪除此留言");
        }

        repository.softDeleteById(id);
    }

    public int getPollCommentCount(Long pollId) {
        return redisService.getCount(RedisUtils.getPollCommentKey(pollId));
    }

    private void updateRedisPollCommentCount(Long pollId) {
        redisService.incr(RedisUtils.getPollCommentKey(pollId));
    }

    private Comment toEntity(CommentRequest request) {
        return mapper.map(request, Comment.class);
    }

    private void populateEditable(Comment comment, User currentUser) {
        if (currentUser == null) {
            return;
        }

        comment.setEditable(comment.getUser().getId().equals(currentUser.getId()));
    }

    private CommentDto toDto(Comment entity) {
        return mapper.map(entity, CommentDto.class);
    }
}

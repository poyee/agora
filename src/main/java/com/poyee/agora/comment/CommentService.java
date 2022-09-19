package com.poyee.agora.comment;

import com.poyee.agora.bean.CommentDto;
import com.poyee.agora.bean.CommentRequest;
import com.poyee.agora.entity.Comment;
import com.poyee.agora.redis.RedisService;
import com.poyee.agora.utils.RedisUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public int getPollCommentCount(Long pollId) {
        return redisService.getCount(RedisUtils.getPollCommentKey(pollId));
    }

    private void updateRedisPollCommentCount(Long pollId) {
        redisService.incr(RedisUtils.getPollCommentKey(pollId));
    }

    private Comment toEntity(CommentRequest request) {
        return mapper.map(request, Comment.class);
    }

    public List<CommentDto> getComments(Long pollId) {
        List<Comment> entities = repository.findAllByPollId(pollId);

        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    private CommentDto toDto(Comment entity) {
        return mapper.map(entity, CommentDto.class);
    }
}

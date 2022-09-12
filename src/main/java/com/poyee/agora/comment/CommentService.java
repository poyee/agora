package com.poyee.agora.comment;

import com.poyee.agora.bean.CommentDto;
import com.poyee.agora.bean.CommentRequest;
import com.poyee.agora.entity.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final ModelMapper mapper;

    private final CommentRepository repository;

    @Autowired
    public CommentService(CommentRepository repository,
                          ModelMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public void comment(CommentRequest request) {
        Comment entity = toEntity(request);

        repository.save(entity);
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

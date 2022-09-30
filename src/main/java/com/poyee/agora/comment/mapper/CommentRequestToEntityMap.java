package com.poyee.agora.comment.mapper;

import com.poyee.agora.comment.bean.CommentRequest;
import com.poyee.agora.entity.Comment;
import org.modelmapper.PropertyMap;

public class CommentRequestToEntityMap extends PropertyMap<CommentRequest, Comment> {
    @Override
    protected void configure() {
        skip(destination.getId());
        map(source.getPollId()).setPollId(null);
        map(source.getBody()).setBody(null);
        map(source.isAnonymous()).setAnonymous(false);
    }
}

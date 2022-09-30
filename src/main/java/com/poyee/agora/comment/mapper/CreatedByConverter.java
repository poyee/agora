package com.poyee.agora.comment.mapper;

import com.poyee.agora.entity.Comment;
import org.modelmapper.AbstractConverter;

public class CreatedByConverter extends AbstractConverter<Comment, String> {
    @Override
    protected String convert(Comment source) {
        return source.getUser().getDisplayName();
    }
}

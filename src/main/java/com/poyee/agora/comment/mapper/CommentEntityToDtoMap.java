package com.poyee.agora.comment.mapper;

import com.poyee.agora.comment.bean.CommentDto;
import com.poyee.agora.entity.Comment;
import com.poyee.agora.utils.DateTimeUtils;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.PropertyMap;

import java.util.ArrayList;

public class CommentEntityToDtoMap extends PropertyMap<Comment, CommentDto> {
    Condition<Comment, ?> anonymous = ctx -> ctx.getSource().isAnonymous();

    @Override
    protected void configure() {
        when(Conditions.not(anonymous))
                .using(new CreatedByConverter())
                .map(source).setCreatedBy(null);

        map(DateTimeUtils.toString(source.getCreatedTime())).setCreatedTime(null);
        using(new VoteEntityToNumberConverter()).map(source.getVotes()).setVotes(new ArrayList<>());

        map(source.isEditable()).setEditable(false);
    }
}

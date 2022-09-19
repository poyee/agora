package com.poyee.agora.comment.mapper;

import com.poyee.agora.bean.CommentDto;
import com.poyee.agora.entity.Comment;
import com.poyee.agora.utils.DateTimeUtils;
import org.modelmapper.PropertyMap;

import java.util.ArrayList;

public class CommentEntityToDtoMap extends PropertyMap<Comment, CommentDto> {
    @Override
    protected void configure() {
        map(source.getUser().getDisplayName()).setCreatedBy(null);
        map(DateTimeUtils.toString(source.getCreatedTime())).setCreatedTime(null);
        using(new VoteEntityToNumberConverter()).map(source.getVotes()).setVotes(new ArrayList<>());
    }
}

package com.poyee.agora.poll.mapper;

import com.poyee.agora.bean.PollRequest;
import com.poyee.agora.entity.Poll;
import org.modelmapper.PropertyMap;

import java.util.Collections;

public class RequestToEntityMap extends PropertyMap<PollRequest, Poll> {
    @Override
    protected void configure() {
        using(new OptionRequestToEntityConverter()).map(source.getOptions()).setOptions(Collections.emptyList());
    }
}

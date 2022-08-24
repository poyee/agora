package com.poyee.agora.poll.mapper;

import com.poyee.agora.entity.Poll;
import com.poyee.agora.poll.bean.PollRequest;
import org.modelmapper.PropertyMap;

import java.util.Collections;

public class RequestToEntityMap extends PropertyMap<PollRequest, Poll> {
    @Override
    protected void configure() {
        using(new OptionConverter()).map(source.getOptions()).setOptions(Collections.emptyList());
    }
}

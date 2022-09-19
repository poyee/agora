package com.poyee.agora.poll.mapper;

import com.poyee.agora.bean.PollSummaryDto;
import com.poyee.agora.entity.Poll;
import com.poyee.agora.utils.DateTimeUtils;
import org.modelmapper.PropertyMap;

public class PollEntityToSummaryMap extends PropertyMap<Poll, PollSummaryDto> {
    @Override
    protected void configure() {
        map(source.getUser().getDisplayName()).setCreatedBy(null);
        map(DateTimeUtils.toString(source.getCreatedTime())).setCreatedTime(null);
    }
}

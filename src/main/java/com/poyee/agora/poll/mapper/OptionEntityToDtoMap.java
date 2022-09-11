package com.poyee.agora.poll.mapper;

import com.poyee.agora.bean.OptionDto;
import com.poyee.agora.entity.Option;
import com.poyee.agora.utils.DateTimeUtils;
import org.modelmapper.PropertyMap;

public class OptionEntityToDtoMap extends PropertyMap<Option, OptionDto> {
    @Override
    protected void configure() {
        map(source.getUser().getDisplayName()).setCreatedBy(null);
        map(DateTimeUtils.toString(source.getCreatedTime())).setCreatedTime(null);
    }
}

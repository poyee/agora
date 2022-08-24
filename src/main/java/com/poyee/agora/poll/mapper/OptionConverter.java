package com.poyee.agora.poll.mapper;

import com.poyee.agora.entity.Option;
import com.poyee.agora.poll.bean.PollRequest;
import org.modelmapper.AbstractConverter;

import java.util.ArrayList;
import java.util.List;

public class OptionConverter extends AbstractConverter<List<PollRequest.Option>, List<Option>> {
    @Override
    protected List<Option> convert(List<PollRequest.Option> source) {
        List<Option> options = new ArrayList<>();
        for(int i = 0; i < source.size(); i++) {
            Option option = new Option();
            // start with 1
            option.setNumber(i + 1);
            option.setName(source.get(i).getOption());

            options.add(option);
        }

        return options;
    }
}

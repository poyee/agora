package com.poyee.agora.poll.mapper;

import com.poyee.agora.entity.Option;
import org.modelmapper.AbstractConverter;

import java.util.ArrayList;
import java.util.List;

public class NameToOptionConverter extends AbstractConverter<List<String>, List<Option>> {
    @Override
    protected List<Option> convert(List<String> source) {
        List<Option> options = new ArrayList<>();
        for(int i = 0; i < source.size(); i++) {
            Option option = new Option();
            // start with 1
            option.setNumber(i + 1);
            option.setName(source.get(i));

            options.add(option);
        }

        return options;
    }
}

package com.poyee.agora.config;

import com.poyee.agora.poll.mapper.OptionEntityToDtoMap;
import com.poyee.agora.poll.mapper.PollEntityToDtoMap;
import com.poyee.agora.poll.mapper.RequestToEntityMap;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean("poll")
    public ModelMapper pollModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new RequestToEntityMap());
        mapper.addMappings(new OptionEntityToDtoMap());
        mapper.addMappings(new PollEntityToDtoMap());

        return mapper;
    }
}

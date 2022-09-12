package com.poyee.agora.config;

import com.poyee.agora.comment.mapper.CommentEntityToDtoMap;
import com.poyee.agora.poll.mapper.OptionEntityToDtoMap;
import com.poyee.agora.poll.mapper.PollEntityToDtoMap;
import com.poyee.agora.poll.mapper.RequestToEntityMap;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(new RequestToEntityMap());
        mapper.addMappings(new OptionEntityToDtoMap());
        mapper.addMappings(new PollEntityToDtoMap());
        mapper.addMappings(new CommentEntityToDtoMap());

        return mapper;
    }
}

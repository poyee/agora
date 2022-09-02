package com.poyee.agora.config;

import com.poyee.agora.poll.PollController;
import com.poyee.agora.user.UserController;
import com.poyee.agora.vote.VoteController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class WebConfig {
    @Bean("ackController")
    public Set<Class<?>> ackController() {
        List<Class<?>> classes = Arrays.asList(
                PollController.class,
                VoteController.class,
                UserController.class
        );

        return new HashSet<>(classes);
    }
}

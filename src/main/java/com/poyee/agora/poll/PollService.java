package com.poyee.agora.poll;

import com.poyee.agora.bean.PollDto;
import com.poyee.agora.entity.Poll;
import com.poyee.agora.poll.bean.PollRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PollService {

    private ModelMapper mapper;

    private PollRepository repository;

    @Autowired
    public PollService(PollRepository repository, @Qualifier("poll") ModelMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public PollDto createPoll(PollRequest request) {
        Poll poll = toEntity(request);

        Poll savedPoll = repository.save(poll);

        return toDto(savedPoll);
    }

    private Poll toEntity(PollRequest request) {
        return mapper.map(request, Poll.class);
    }

    private PollDto toDto(Poll poll) {
        return mapper.map(poll, PollDto.class);
    }

}

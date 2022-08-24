package com.poyee.agora.poll;

import com.poyee.agora.bean.OptionDto;
import com.poyee.agora.bean.PollDto;
import com.poyee.agora.entity.Poll;
import com.poyee.agora.exception.NotFoundException;
import com.poyee.agora.poll.bean.PollRequest;
import com.poyee.agora.vote.VoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PollService {

    private final ModelMapper mapper;

    private final PollRepository repository;

    private final VoteService voteService;

    @Autowired
    public PollService(PollRepository repository,
                       @Qualifier("poll") ModelMapper mapper,
                       VoteService service) {
        this.mapper = mapper;
        this.repository = repository;
        this.voteService = service;
    }

    public PollDto createPoll(PollRequest request) {
        Poll poll = toEntity(request);

        Poll savedPoll = repository.save(poll);

        return toDto(savedPoll);
    }

    public PollDto getPoll(Long id) {
        Optional<Poll> optional = repository.findById(id);
        if (optional.isPresent()) {
            Poll entity = optional.get();
            PollDto dto = toDto(entity);
            populateVotes(dto);

            return dto;
        }

        throw new NotFoundException("poll " + id + " not found");
    }

    private void populateVotes(PollDto poll) {
        for (OptionDto option : poll.getOptions()) {
            int voteNumber = voteService.getVote(poll.getId(), option.getNumber());
            option.setVotes(voteNumber);
        }
    }

    private Poll toEntity(PollRequest request) {
        return mapper.map(request, Poll.class);
    }

    private PollDto toDto(Poll poll) {
        return mapper.map(poll, PollDto.class);
    }
}

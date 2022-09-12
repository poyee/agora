package com.poyee.agora.poll;

import com.poyee.agora.bean.OptionDto;
import com.poyee.agora.bean.PollDto;
import com.poyee.agora.bean.PollRequest;
import com.poyee.agora.entity.Poll;
import com.poyee.agora.entity.User;
import com.poyee.agora.entity.Vote;
import com.poyee.agora.entity.VoteId;
import com.poyee.agora.exception.NotFoundException;
import com.poyee.agora.user.LocalUser;
import com.poyee.agora.vote.VoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollService {

    private final ModelMapper mapper;

    private final PollRepository repository;

    private final VoteService voteService;

    @Autowired
    public PollService(PollRepository repository,
                       ModelMapper mapper,
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

    public PollDto getPoll(Long id, LocalUser localUser) {
        Optional<Poll> optional = repository.findById(id);
        if (optional.isPresent()) {
            Poll entity = optional.get();
            PollDto dto = toDto(entity);
            populateVotes(dto);

            if (Objects.nonNull(localUser)) {
                populateVotedOptions(dto, localUser.getUser());
            }

            return dto;
        }

        throw new NotFoundException("poll " + id + " not found");
    }

    public void createOption(Long pollId, String name, LocalUser user) {
        repository.createOption(pollId, name, user.getUser().getId());
    }

    private void populateVotes(PollDto poll) {
        for (OptionDto option : poll.getOptions()) {
            int voteNumber = voteService.getOptionVote(poll.getId(), option.getNumber());
            option.setVotes(voteNumber);
        }
    }

    private void populateVotedOptions(PollDto poll, User user) {
        List<Vote> voteOptions = voteService.getUserSelectedVote(poll.getId(), user);
        poll.setSelectedOptions(voteOptions.stream()
                .map(Vote::getId)
                .map(VoteId::getNumber)
                .collect(Collectors.toList()));
    }

    private Poll toEntity(PollRequest request) {
        return mapper.map(request, Poll.class);
    }

    private PollDto toDto(Poll poll) {
        return mapper.map(poll, PollDto.class);
    }
}

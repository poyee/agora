package com.poyee.agora.poll;

import com.poyee.agora.bean.OptionDto;
import com.poyee.agora.bean.PollDto;
import com.poyee.agora.bean.PollRequest;
import com.poyee.agora.bean.ReactType;
import com.poyee.agora.entity.Poll;
import com.poyee.agora.entity.User;
import com.poyee.agora.entity.Vote;
import com.poyee.agora.entity.VoteId;
import com.poyee.agora.exception.ForbiddenException;
import com.poyee.agora.exception.NotFoundException;
import com.poyee.agora.react.ReactService;
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

    private final ReactService reactService;

    @Autowired
    public PollService(PollRepository repository,
                       ModelMapper mapper,
                       VoteService voteService,
                       ReactService reactService) {
        this.mapper = mapper;
        this.repository = repository;
        this.voteService = voteService;
        this.reactService = reactService;
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
            populateEditable(entity, localUser.getUser());
            PollDto dto = toDto(entity);
            populateOptionVoteCounts(dto);
            populateReactCount(dto);

            if (Objects.nonNull(localUser)) {
                populateUserVote(dto, localUser.getUser());
            }

            if (Objects.nonNull(localUser)) {
                populateUserReact(dto, localUser.getUser());
            }

            return dto;
        }

        throw new NotFoundException("poll " + id + " not found");
    }

    public void createOption(Long pollId, String name, LocalUser user) {
        repository.createOption(pollId, name, user.getUser().getId());
    }

    public void delete(Long id, LocalUser user) {
        Optional<Poll> pollOpt = repository.findById(id);

        if (!pollOpt.isPresent()) {
            throw new NotFoundException("投票不存在");
        }

        Poll poll = pollOpt.get();

        if (!poll.getUser().getId().equals(user.getUser().getId())) {
            throw new ForbiddenException("無權限刪除此留言");
        }

        repository.softDeleteById(id);
    }

    private void populateOptionVoteCounts(PollDto poll) {
        for (OptionDto option : poll.getOptions()) {
            int voteNumber = voteService.getOptionVoteCount(poll.getId(), option.getNumber());
            option.setVotes(voteNumber);
        }
    }

    private void populateUserVote(PollDto poll, User user) {
        List<Vote> voteOptions = voteService.getUserVote(poll.getId(), user);
        poll.setSelectedOptions(voteOptions.stream()
                .map(Vote::getId)
                .map(VoteId::getNumber)
                .collect(Collectors.toList()));
    }

    private void populateReactCount(PollDto dto) {
        for (ReactType reactType : ReactType.values()) {
            int count = reactService.getPollReact(dto.getId(), reactType);
            dto.getReactCount().put(reactType.name(), count);
        }
    }

    private void populateUserReact(PollDto dto, User user) {
        ReactType reactType = reactService.getUserPollReact(dto.getId(), user);

        dto.setUserReact(reactType);
    }

    private void populateEditable(Poll poll, User currentUser) {
        if (currentUser == null) {
            return;
        }

        poll.setEditable(poll.getUser().getId().equals(currentUser.getId()));
    }

    private Poll toEntity(PollRequest request) {
        return mapper.map(request, Poll.class);
    }

    private PollDto toDto(Poll poll) {
        return mapper.map(poll, PollDto.class);
    }
}

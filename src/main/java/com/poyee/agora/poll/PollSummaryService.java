package com.poyee.agora.poll;

import com.poyee.agora.bean.PageRequest;
import com.poyee.agora.bean.Pagination;
import com.poyee.agora.bean.PollSummaryDto;
import com.poyee.agora.comment.CommentService;
import com.poyee.agora.entity.Poll;
import com.poyee.agora.utils.PaginationUtils;
import com.poyee.agora.vote.VoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollSummaryService {
    private final ModelMapper mapper;

    private final PollRepository repository;

    private final VoteService voteService;

    private final CommentService commentService;

    @Autowired
    public PollSummaryService(PollRepository repository,
                       ModelMapper mapper,
                       VoteService voteService,
                      CommentService commentService) {
        this.mapper = mapper;
        this.repository = repository;
        this.voteService = voteService;
        this.commentService = commentService;
    }

    public Pagination<PollSummaryDto> getSummaries(PageRequest pageRequest) {
        Pageable paging = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdTime"));

        Page<Poll> pollPage = repository.findAll(paging);

        List<PollSummaryDto> dtoList = pollPage.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        dtoList.forEach(this::populateCommentCounts);
        dtoList.forEach(this::populateTotalVoteCount);

        return PaginationUtils.toPagination(pollPage, dtoList);
    }

    private void populateCommentCounts(PollSummaryDto dto) {
        dto.setCommentCount(commentService.getPollCommentCount(dto.getId()));
    }

    private void populateTotalVoteCount(PollSummaryDto dto) {
        dto.setVoteCount(voteService.getPollTotalVoteCount(dto.getId()));
    }

    private PollSummaryDto toDto(Poll poll) {
        return mapper.map(poll, PollSummaryDto.class);
    }

}

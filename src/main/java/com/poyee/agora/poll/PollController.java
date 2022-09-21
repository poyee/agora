package com.poyee.agora.poll;

import com.poyee.agora.bean.OptionRequest;
import com.poyee.agora.bean.PageRequest;
import com.poyee.agora.bean.Pagination;
import com.poyee.agora.bean.PollDto;
import com.poyee.agora.bean.PollRequest;
import com.poyee.agora.bean.PollSummaryDto;
import com.poyee.agora.bean.ReactRequest;
import com.poyee.agora.config.CurrentUser;
import com.poyee.agora.react.ReactService;
import com.poyee.agora.response.MessageResponse;
import com.poyee.agora.user.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("polls")
public class PollController {
    @Autowired
    private PollService service;

    @Autowired
    private PollSummaryService summaryService;

    @Autowired
    private ReactService reactService;

    @PostMapping("")
    public PollDto createQuestion(@RequestBody @Valid PollRequest request) {
        return service.createPoll(request);
    }

    @GetMapping("/{id}")
    public PollDto getPoll(@PathVariable(name = "id") Long id, @CurrentUser LocalUser user) {
        return service.getPoll(id, user);
    }

    @PostMapping("/{id}/options")
    public MessageResponse createOption(@PathVariable(name = "id") Long pollId, @RequestBody @Valid OptionRequest request, @CurrentUser LocalUser user) {
        service.createOption(pollId, request.getOption(), user);

        return new MessageResponse("儲存 " + request.getOption() + " 成功");
    }

    @GetMapping("")
    public Pagination<PollSummaryDto> getPollSummaries(PageRequest pageRequest) {
        return summaryService.getSummaries(pageRequest);
    }

    @PostMapping("/{id}/reacts")
    public MessageResponse react(@PathVariable(name = "id") Long pollId, @RequestBody @Valid ReactRequest request, @CurrentUser LocalUser user) {
        reactService.pollReact(pollId, request, user);

        return new MessageResponse("更新 " + request.getReact() + " 成功");
    }
}

package com.poyee.agora.poll;

import com.poyee.agora.bean.OptionRequest;
import com.poyee.agora.bean.PollDto;
import com.poyee.agora.bean.PollRequest;
import com.poyee.agora.config.CurrentUser;
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
}

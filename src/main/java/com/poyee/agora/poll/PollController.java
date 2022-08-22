package com.poyee.agora.poll;

import com.poyee.agora.bean.PollDto;
import com.poyee.agora.poll.bean.PollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("poll")
public class PollController {
    @Autowired
    private PollService service;

    @PostMapping("")
    public PollDto createQuestion(@RequestBody PollRequest request) {
        return service.createPoll(request);
    }
}

package com.poyee.agora.vote;

import com.poyee.agora.vote.bean.VoteRequest;
import com.poyee.agora.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vote")
public class VoteController {
    @Autowired
    private VoteService service;

    @PostMapping("")
    public MessageResponse vote(@RequestBody VoteRequest request) {
        service.vote(request);

        return new MessageResponse("投票成功");
    }
}

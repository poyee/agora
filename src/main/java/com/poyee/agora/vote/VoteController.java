package com.poyee.agora.vote;

import com.poyee.agora.config.CurrentUser;
import com.poyee.agora.response.MessageResponse;
import com.poyee.agora.user.LocalUser;
import com.poyee.agora.vote.bean.VoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("votes")
public class VoteController {
    @Autowired
    private VoteService service;

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public MessageResponse vote(@CurrentUser LocalUser user, @RequestBody VoteRequest request) {
        service.vote(user, request);

        return new MessageResponse("投票成功");
    }
}

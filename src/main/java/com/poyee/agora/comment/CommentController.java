package com.poyee.agora.comment;

import com.poyee.agora.bean.CommentDto;
import com.poyee.agora.bean.CommentRequest;
import com.poyee.agora.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {
    private CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("")
    public MessageResponse comment(@RequestBody @Valid CommentRequest request) {
        service.comment(request);

        return new MessageResponse("留言成功");
    }

    @GetMapping("")
    public List<CommentDto> getComments(@RequestParam("pollId") Long pollId) {
        return service.getComments(pollId);
    }
}

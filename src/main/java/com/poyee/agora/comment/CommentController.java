package com.poyee.agora.comment;

import com.poyee.agora.comment.bean.CommentDto;
import com.poyee.agora.comment.bean.CommentRequest;
import com.poyee.agora.config.CurrentUser;
import com.poyee.agora.response.MessageResponse;
import com.poyee.agora.user.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<CommentDto> getComments(@RequestParam("pollId") Long pollId, @CurrentUser LocalUser user) {
        return service.getComments(pollId, user);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public MessageResponse delete(@PathVariable(name = "id") Long id, @CurrentUser LocalUser user) {
        this.service.deleteComment(id, user);

        return new MessageResponse("刪除留言成功");
    }
}

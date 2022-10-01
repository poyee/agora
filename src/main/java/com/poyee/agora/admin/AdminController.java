package com.poyee.agora.admin;

import com.poyee.agora.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    SyncService syncService;

    @PutMapping("/poll/sync")
    public MessageResponse syncAllPollCount() {
        syncService.syncAllPollCount();

        return new MessageResponse("同步所有開始");
    }

    @PutMapping("/poll/sync/{id}")
    public MessageResponse syncPollCount(@PathVariable(name = "id") Long id) {
        syncService.syncAllPollCount();

        return new MessageResponse("同步投票" + id + "開始");
    }
}

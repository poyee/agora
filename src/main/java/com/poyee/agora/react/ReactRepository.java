package com.poyee.agora.react;

import com.poyee.agora.bean.ReactType;
import com.poyee.agora.entity.PollReact;
import com.poyee.agora.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactRepository extends JpaRepository<PollReact, Long> {
    PollReact findByPollIdAndUser(Long pollId, User user);

    int countByPollIdAndReact(Long pollId, ReactType reactType);
}

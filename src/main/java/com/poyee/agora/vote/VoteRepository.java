package com.poyee.agora.vote;

import com.poyee.agora.entity.User;
import com.poyee.agora.entity.Vote;
import com.poyee.agora.entity.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, VoteId> {
    List<Vote> findAllById_PollIdAndId_User(Long pollId, User user);
}

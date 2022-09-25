package com.poyee.agora.poll;

import com.poyee.agora.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `option` (poll_id, name, number, user_id) " +
            "SELECT :pollId, :name, (IFNULL(MAX(number), 0)) + 1, :userId " +
            "FROM `option` WHERE poll_id = :pollId",
            nativeQuery = true)
    Integer createOption(@Param("pollId") Long pollId, @Param("name") String name, @Param("userId") Long userId);
}

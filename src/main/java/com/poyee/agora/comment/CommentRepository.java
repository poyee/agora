package com.poyee.agora.comment;

import com.poyee.agora.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPollId(Long pollId);

    long countByPollId(Long pollId);

    @Transactional
    @Modifying
    @Query("UPDATE Comment SET deleted = 1 WHERE id=?1")
    void softDeleteById(Long id);
}

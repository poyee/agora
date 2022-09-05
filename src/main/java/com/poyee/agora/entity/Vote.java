package com.poyee.agora.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Vote {
    @EmbeddedId
    private VoteId id;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @OneToOne
    @CreatedBy
    @JoinColumn(name="user_id")
    private User user;
}

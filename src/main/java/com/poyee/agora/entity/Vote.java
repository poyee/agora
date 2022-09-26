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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "`vote`")
@EntityListeners(AuditingEntityListener.class)
public class Vote {
    @EmbeddedId
    private VoteId id;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @ManyToOne
    @CreatedBy
    @JoinColumn(name="user_id", updatable = false)
    private User user;
}

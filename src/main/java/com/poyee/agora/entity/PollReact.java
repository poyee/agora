package com.poyee.agora.entity;

import com.poyee.agora.bean.ReactType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "`poll_user_react`")
@EntityListeners(AuditingEntityListener.class)
public class PollReact {
    @Id
    private Long pollId;

    @Enumerated(EnumType.STRING)
    private ReactType react;

    @ManyToOne
    @CreatedBy
    @JoinColumn(name="user_id", updatable = false)
    private User user;
}

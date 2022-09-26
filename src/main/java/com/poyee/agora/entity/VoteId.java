package com.poyee.agora.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class VoteId implements Serializable {
    @Column(name = "poll_id")
    private Long pollId;

    private Integer number;

    @ManyToOne
    @CreatedBy
    @JoinColumn(name="user_id", updatable = false)
    private User user;
}

package com.poyee.agora.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class VoteId implements Serializable {
    @Column(name = "poll_id")
    private Long pollId;

    private Integer number;
}

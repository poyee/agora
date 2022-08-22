package com.poyee.agora.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "poll")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToMany(cascade = {
            CascadeType.PERSIST
    })
    @JoinColumn(name="poll_id")
    private List<Option> options;

    @Column(name = "multi_vote")
    private Boolean multiVote;

    @Column(name = "allow_new_option")
    private Boolean allowNewOption;
}

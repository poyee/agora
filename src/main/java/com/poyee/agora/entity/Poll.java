package com.poyee.agora.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "`poll`")
@Where(clause = "deleted=false")
@EntityListeners(AuditingEntityListener.class)
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = false)
    @JoinColumn(name="poll_id")
    private List<Option> options;

    @Column(name = "multi_vote")
    private Boolean multiVote;

    @Column(name = "allow_new_option")
    private Boolean allowNewOption;

    @OneToOne
    @CreatedBy
    @JoinColumn(name="user_id", updatable = false)
    private User user;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @Transient
    private boolean editable;

    private boolean deleted;
}

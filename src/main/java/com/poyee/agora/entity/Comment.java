package com.poyee.agora.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "`comment`")
@SQLDelete(sql = "UPDATE comment SET deleted = 1 WHERE id=?")
@Where(clause = "deleted=false")
@EntityListeners(AuditingEntityListener.class)
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    @Column(name = "poll_id")
    private Long pollId;

    @OneToOne
    @CreatedBy
    @JoinColumn(name="user_id", updatable = false)
    private User user;

    private boolean anonymous;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @OneToMany
    @JoinColumns({
        @JoinColumn(name="user_id", referencedColumnName="user_id"),
        @JoinColumn(name="poll_id", referencedColumnName="poll_id")
    })
    private List<Vote> votes;

    @Transient
    private boolean editable;

    private boolean deleted;
}

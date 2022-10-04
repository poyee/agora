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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "`option`")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE option SET deleted = 1 WHERE id=?")
@EntityListeners(AuditingEntityListener.class)
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    private String name;

    @OneToOne
    @CreatedBy
    @JoinColumn(name="user_id", updatable = false)
    private User user;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;
}

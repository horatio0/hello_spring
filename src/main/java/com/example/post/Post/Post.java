package com.example.post.Post;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    private String title;
    private String content;
    private String author;
    @CreatedDate
    private LocalDateTime date;
}

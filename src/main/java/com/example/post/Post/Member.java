package com.example.post.Post;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
public class Member{
    @Id
    private String memberId;
    private String memberName;
    private String memberEmail;
    private String memberPassword;
    private String role;
}

package com.example.post.Repository;
import com.example.post.Post.Member;
import com.example.post.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MemberRepository extends JpaRepository<Member, String> {
}

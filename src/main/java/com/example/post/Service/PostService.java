package com.example.post.Service;

import com.example.post.Post.InputPost;
import com.example.post.Post.Post;
import com.example.post.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository pR;

    public void commit(InputPost inputPost, String author){
        Post p = new Post();
        p.setTitle(inputPost.getTitle());
        p.setContent(inputPost.getContent());
        p.setAuthor(author);
        pR.save(p);
    }

    public void update(InputPost inputpost, Long id){
        Post p = pR.getReferenceById(id);
        p.setTitle(inputpost.getTitle());
        p.setContent(inputpost.getContent());
    }

    public Post Read(Long id){
        return pR.getReferenceById(id);
    }

    public List<Post> getList(){
        List<Post> posts = pR.findAll();
        if(!posts.isEmpty()) Collections.reverse(posts);
        return posts;
    }

    public void delete(Long id){ pR.deleteById(id); }
}

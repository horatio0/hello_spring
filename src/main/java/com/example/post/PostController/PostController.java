package com.example.post.PostController;


import com.example.post.Post.*;
import com.example.post.Repository.MemberRepository;
import com.example.post.Repository.PostRepository;
import com.example.post.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private List<Post> getAllPosts() {
        List<Post> list = postService.getList();
        if(list.isEmpty()) {
            Post p = new Post();
            p.setNum(null);
            list.add(p);
        }
        return list;
    }

    @GetMapping("/")
    public String first(Model model, @AuthenticationPrincipal UserDetails userDetails){
        try {
            model.addAttribute("memberName", memberRepository.getReferenceById(userDetails.getUsername()).getMemberName());
        } catch(NullPointerException e){
            model.addAttribute("memberName", null);
        }

        List<Post> list = getAllPosts();
        model.addAttribute("list", list);
        return "main";
    }

    @PostMapping("/redirect")
    public String redirect(){
        return "redirect:/";
    }

    @GetMapping("/post/new")
    public String input(Model model, @AuthenticationPrincipal UserDetails userDetails){
        try {
            model.addAttribute("memberName", memberRepository.getReferenceById(userDetails.getUsername()).getMemberName());
        } catch(NullPointerException e){
            model.addAttribute("memberName", null);
        }
        return "inputPost";
    }

    @PostMapping("/post")
    public String commit(Model model,@ModelAttribute @Valid InputPost inputPost, @RequestParam String author){
        postService.commit(inputPost, author);
        return "redirect:/";
    }

    @GetMapping("/post/{postNum}")
    public String show(Model model,@PathVariable("postNum") Long postNum, @AuthenticationPrincipal UserDetails userDetails){
        Post post = postService.Read(postNum);
        model.addAttribute("list", post);
        try {
            Member member = memberRepository.getReferenceById(userDetails.getUsername());
            model.addAttribute("memberName", member.getMemberName());
            if(post.getAuthor().equals(member.getMemberName())) {
                model.addAttribute("isAuthor", true);
            } else {
                model.addAttribute("isAuthor", false);
            }
        } catch(NullPointerException e){
            model.addAttribute("memberName", null);
            model.addAttribute("isAuthor", false);
        }
        return "show";
    }

    @GetMapping("/post/{postNum}/edit")
    public String update(@AuthenticationPrincipal UserDetails userDetails, Model model, @PathVariable Long postNum){
        try {
            model.addAttribute("memberName", memberRepository.getReferenceById(userDetails.getUsername()).getMemberName());
        } catch(NullPointerException e){
            model.addAttribute("memberName", null);
        }
        Post p = postService.Read(postNum);
        model.addAttribute("post", p);
        return("updatePost");
    }

    @PutMapping("/post/{postNum}")
    public String updatePost(Model model, @PathVariable Long postNum, @ModelAttribute @Valid InputPost inputPost){
        postService.update(inputPost, postNum);
        return "redirect:/";
    }

    @DeleteMapping("/post/{postNum}")
    public String delete(Model model, @PathVariable Long postNum){
            postService.delete(postNum);
            return "redirect:/";
    }
}

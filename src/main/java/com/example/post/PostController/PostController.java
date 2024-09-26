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
    public String first(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        String id = (String) session.getAttribute("id");
        if (id==null || id.isEmpty()) model.addAttribute("isLogin", false);
        else {
            model.addAttribute("isLogin", true);
            model.addAttribute("name", memberRepository.getReferenceById(id).getMemberName());
        }
        List<Post> list = getAllPosts();
        model.addAttribute("list", list);
        return "main";
    }

    @PostMapping("/redirect")
    public String redirect(){
        return "redirect:/";
    }

    @PostMapping("/input")
    public String input(Model model, HttpSession session){
        if (session.getAttribute("id") == null) model.addAttribute("isLogin", false);
        else {
            model.addAttribute("isLogin", true);
            model.addAttribute("name", memberRepository.getReferenceById(session.getAttribute("id").toString()).getMemberName());
        }
        return "inputPost";
    }

    @PostMapping("/submitPost")
    public String commit(Model model,@ModelAttribute @Valid InputPost inputPost, @RequestParam String author){
        postService.commit(inputPost, author);

        return "redirect:/";
    }

    @GetMapping("/showPost")
    public String show(Model model, Long id, HttpSession session){
        Post list = postService.Read(id);
        model.addAttribute("list", list);
        if (session.getAttribute("id") == null) model.addAttribute("isLogin", false);
        else {
            model.addAttribute("isLogin", true);
            if(list.getAuthor().equals(memberService.getUserName(session.getAttribute("id").toString()))) model.addAttribute("isAuthor", true);
            else model.addAttribute("isAuthor", false);
            model.addAttribute("name", memberRepository.getReferenceById(session.getAttribute("id").toString()).getMemberName());
        }
        return "show";
    }

    @PostMapping("/update")
    public String update(HttpSession session, Model model, @RequestParam("action") Long id){
        if (session.getAttribute("id") == null) model.addAttribute("isLogin", false);
        else {
            model.addAttribute("isLogin", true);
            model.addAttribute("name", memberRepository.getReferenceById(session.getAttribute("id").toString()).getMemberName());
        }
        Post p = postService.Read(id);
        model.addAttribute("post", p);
        return("updatePost");
    }

    @PostMapping("/updatePost")
    public String updatePost(Model model, @RequestParam("submit") Long id, @ModelAttribute @Valid InputPost inputPost){
        postService.update(inputPost, id);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(HttpSession session,Model model, @RequestParam("delete") Long id){
            postService.delete(id);
            return "redirect:/";
    }
}

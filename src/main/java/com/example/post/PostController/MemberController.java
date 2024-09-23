package com.example.post.PostController;


import com.example.post.Post.*;
import com.example.post.Service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public String newMember(){
        return "join";
    }

    @PostMapping("/getMemberInfo")
    public String newMemberInfo(Model model, @ModelAttribute @Valid Member member){
        member.setAdmin(member.getMemberId().equals("관리자"));
        String a = memberService.join(member);
        model.addAttribute("message", a);
        model.addAttribute("searchUrl", "/");
        return "message";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, Model model, @RequestParam String memberId, @RequestParam String memberPassword){
        Member member = new Member();
        member.setMemberId(memberId);
        member.setMemberPassword(memberPassword);
        if (memberService.login(member.getMemberId(), member.getMemberPassword())){
            //model.addAttribute("message", "로그인 성공");
            //model.addAttribute("searchUrl", "/");
            session.setAttribute("id", member.getMemberId());
            session.setMaxInactiveInterval(600);
            return "redirect:/";
        }else{
            model.addAttribute("message", "로그인 실패");
            model.addAttribute("searchUrl", "/login");
            return "message";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}

package com.example.post.PostController;


import com.example.post.DTO.MemberDTO;
import com.example.post.DTO.joinDTO;
import com.example.post.Post.*;
import com.example.post.Repository.MemberRepository;
import com.example.post.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/member/join")
    public String newMember(){
        return "join";
    }

    @PostMapping("/member")
    public String memberJoin(Model model, @ModelAttribute @Valid joinDTO joinDTO){
        String a = memberService.join(joinDTO);
        model.addAttribute("message", a);
        model.addAttribute("searchUrl", "/");
        return "message";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login/fail")
    public String loginFail(Model model){
        model.addAttribute("message", "아이디 혹은 패스워드가 잘못되었습니다");
        model.addAttribute("searchUrl", "/login");
        return "message";
    }

    @DeleteMapping("/member/leave")
    public String leave(@AuthenticationPrincipal UserDetails userDetails){
        memberService.leave(memberRepository.getReferenceById(userDetails.getUsername()));
        return "redirect:/logout";
    }

    @GetMapping("/member/{memberId}/info")
    public String memberInfo(@PathVariable("memberId") String memberId, Model model){
        Member member = memberRepository.getReferenceById(memberId);
        model.addAttribute("member", member);
        return "userinfo";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @PutMapping("/member/{memberId}/update")
    public String update(@PathVariable("memberId") String memberId, @ModelAttribute @Valid MemberDTO memberdto, Model model){
        String message = memberService.update(memberId, memberdto);
        model.addAttribute("message", message);
        model.addAttribute("searchUrl", "/");
        return "message";
    }

    /*@PostMapping("/login")
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
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }*/
}

package com.example.post.Service;


import com.example.post.DTO.MemberDTO;
import com.example.post.DTO.joinDTO;
import com.example.post.Post.Member;
import com.example.post.Repository.MemberRepository;
import com.example.post.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String getUserName(String id){
        return memberRepository.getReferenceById(id).getMemberName();
    }

    /*public boolean login(String id, String password) {
        Member member = memberRepository.findById(id).orElse(null);
        if(member == null) return false;
        else return member.getMemberPassword().equals(password);
    }*/

    public String join(joinDTO joinDTO){
        if(memberRepository.existsById(joinDTO.getMemberId())){
            return "이미 존재하는 아이디 입니다.";
        } else {
            Member member = new Member();
            member.setMemberId(joinDTO.getMemberId());
            member.setMemberName(joinDTO.getMemberName());
            member.setMemberPassword(bCryptPasswordEncoder.encode(joinDTO.getMemberPassword()));
            member.setMemberEmail(joinDTO.getMemberEmail());
            member.setRole("ROLE_USER");
            memberRepository.save(member);
            return "회원가입이 완료되었습니다.";
        }
    }

    public void leave(Member member){
        memberRepository.delete(member);
    }

    public String update (String memberId, MemberDTO memberDTO){
        try{
            Member member = memberRepository.getReferenceById(memberId);
            member.setMemberName(memberDTO.getMemberName());
            member.setMemberEmail(memberDTO.getMemberEmail());
            member.setMemberId(memberDTO.getMemberId());
        } catch (NullPointerException e){
            return "오류 : 존재하지 않는 아이디 입니다.";
        }
        return "수정 성공";
    }
}
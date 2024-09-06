package com.example.post.Service;


import com.example.post.Post.Member;
import com.example.post.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean login(String id, String password) {
        Member member = memberRepository.findById(id).orElse(null);
        if(member == null) return false;
        else if (member.getMemberPassword().equals(password)) return true;
        else return false;
    }

    public String join(Member member){
        if(memberRepository.existsById(member.getMemberId())){
            return "이미 존재하는 아이디 입니다.";
        } else {
            memberRepository.save(member);
            return "회원가입이 완료되었습니다.";
        }
    }

    public void leave(Member member){
        memberRepository.delete(member);
    }

    public String update (Member member){
        if(memberRepository.existsById(member.getMemberId())){
            return "이미 존재하는 아이디 입니다.";
        } else {
            Member m = memberRepository.getReferenceById(member.getMemberId());
            m.setMemberName(member.getMemberName());
            m.setMemberEmail(member.getMemberEmail());
            m.setMemberId(member.getMemberId());
            m.setMemberPassword(member.getMemberPassword());
            return "회원 정보 수정이 완료되었습니다.";
        }
    }
}
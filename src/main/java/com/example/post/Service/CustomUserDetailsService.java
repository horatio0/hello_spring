package com.example.post.Service;


import com.example.post.DTO.UserDetailsDTO;
import com.example.post.DTO.joinDTO;
import com.example.post.Post.Member;
import com.example.post.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username).orElse(null);

        if(member == null) throw new UsernameNotFoundException("User not found");

        return new UserDetailsDTO(member);
    }
}

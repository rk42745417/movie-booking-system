package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Member;
import com.javaoop.movie_booking_app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Autowired
    public JpaUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        return User.withUsername(member.getEmail())
                .password(member.getPasswordHash())
                .build();
    }
}

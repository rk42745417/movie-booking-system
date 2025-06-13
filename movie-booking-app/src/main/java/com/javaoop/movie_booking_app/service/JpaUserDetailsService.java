package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Member;
import com.javaoop.movie_booking_app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service implementing Spring Security's UserDetailsService interface.
 * Loads user-specific data during authentication based on member email.
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    /**
     * Constructs JpaUserDetailsService with a MemberRepository.
     *
     * @param memberRepository repository to access member data
     */
    @Autowired
    public JpaUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Locates the user by the given email (used as username).
     * This method is invoked by Spring Security during authentication.
     *
     * @param email the email of the user to authenticate
     * @return UserDetails object containing user's authentication information
     * @throws UsernameNotFoundException if the user with the given email is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return User.withUsername(member.getEmail())
                .password(member.getPasswordHash())
                .build();
    }
}

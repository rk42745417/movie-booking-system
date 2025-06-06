/**
 * 
 */
package controller;

import model.Member;
import service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * 
 */
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public Member register(@RequestBody Member member) {
        return memberService.registerMember(member);
    }

    @GetMapping("/login")
    public Member login(@RequestParam String email) {
        return memberService.findByEmail(email);
    }

}

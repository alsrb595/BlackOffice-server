package org.example.blackoffice.controller;

import jakarta.validation.Valid;
import org.example.blackoffice.dto.Memberdto;
import org.example.blackoffice.model.Member;
import org.example.blackoffice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://sweet-heads-happen.loca.lt")
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Memberdto.Post memberdto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("잘못된 입력 값");
        }
        try {
            memberService.registerMember(memberdto.getPassword(), memberdto.getEmail());
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member) {
        boolean isLoggedIn = memberService.login(member.getEmail(), member.getPassword());
        System.out.println("로그인 시도 결과: " + (isLoggedIn ? "성공" : "실패"));

        if (isLoggedIn) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.badRequest().body("로그인 실패");
        }
    }*/
}

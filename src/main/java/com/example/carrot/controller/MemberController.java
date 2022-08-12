package com.example.carrot.controller;

import com.example.carrot.request.SignupRequestDto;
import com.example.carrot.service.MemberService;
import com.example.carrot.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/members/login")
    public String login() {
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/api/members/signup")
    public String signUp() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/api/members/signup")
    public String registerUser(SignupRequestDto requestDto) {
        memberService.registerUser(requestDto);
        return "redirect:/api/members/login";
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        return "index";
    }
}

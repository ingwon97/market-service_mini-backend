package com.example.carrot.controller;

import com.example.carrot.request.LoginRequestDto;
import com.example.carrot.request.MemberRequestDto;
import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;


    //Valid는 유효성 검사
    @RequestMapping(value = "/api/members/signup", method = RequestMethod.POST)
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @RequestMapping(value = "/api/members/login", method = RequestMethod.POST)
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                                HttpServletResponse response
    ) {
        return memberService.login(requestDto, response);
    }

    // 아이디 중복 체크
    @GetMapping("/api/members/check/{username}")
    public ResponseDto<?> checkId(@PathVariable String username) {
        return memberService.checkId(username);
    }

    //회원 정보 조회
    @GetMapping("/api/members/info")
    public ResponseDto<?> LoginInfo(@AuthenticationPrincipal UserDetails userInfo) {
        try {
            return  memberService.LoginInfo(userInfo);
        } catch (Exception e) {
            return  ResponseDto.fail("NOT_STATE_LOGIN", e.getMessage());
        }


    }

}


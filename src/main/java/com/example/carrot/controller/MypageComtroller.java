package com.example.carrot.controller;

import com.example.carrot.model.MemberDetailsImpl;
import com.example.carrot.request.MypageRequestDto;
import com.example.carrot.request.PostRequestDto;
import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.MypageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class MypageComtroller {


    private final MypageService mypageService;

    public MypageComtroller(MypageService mypageService) {
        this.mypageService = mypageService;
    }


    @RequestMapping(value = "/api/auth/mypage/posts", method = RequestMethod.GET)
    public ResponseDto<?> mypagePost(HttpServletRequest request) {
        return mypageService.mypageAllPost(request);
    }


    @RequestMapping(value = "/api/auth/mypage/comments", method = RequestMethod.GET)
    public ResponseDto<?> mypageComment(HttpServletRequest request) {
        return mypageService.mypageAllComment(request);
    }

    @RequestMapping(value = "/api/auth/mypage/bookmarks", method = RequestMethod.GET)
    public ResponseDto<?> mypageBookmark(HttpServletRequest request) {
        return mypageService.mypageAllBookmark(request);
    }

    @PostMapping("/api/auth/mypage/images")
    public ResponseDto<?> createImage(@RequestBody MypageRequestDto requestDto,
                                     HttpServletRequest request
    ) throws IOException {
        return mypageService.createImage(requestDto, request);
    }

    @PutMapping("/api/auth/mypage/images")
    public ResponseDto<?> updatePost(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                     @RequestBody MypageRequestDto requestDto,
                                     HttpServletRequest request
    ) throws IOException {
        Long memberId = memberDetails.getUser().getMember_id();


        //이미지가 들어왔다면
        return mypageService.updateImage(memberId, requestDto, request);
    }

//    @RequestMapping(value = "/api/mypage/comments", method = RequestMethod.GET)
//    public ResponseDto<?> mypageComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails
//    ) {
//        Long memberId = memberDetails.getUser().getMember_id();
//
//        return mypageService.mypageAllComment(memberId);
//    }

}

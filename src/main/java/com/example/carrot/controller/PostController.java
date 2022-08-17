package com.example.carrot.controller;

import com.example.carrot.model.MemberDetailsImpl;
import com.example.carrot.request.PostRequestDto;
import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/posts/{postId}")
    public ResponseDto<?> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/api/auth/posts")
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto,
                                     HttpServletRequest request
                                     ) throws IOException {
        return postService.createPost(requestDto, request);
    }

    // 게시글 조회
    @GetMapping("/api/posts")
    public ResponseDto<?> getAllPosts(@AuthenticationPrincipal UserDetails userInfo) {
        return postService.getAllPosts(userInfo);
    }

    // 게시글 수정
    @PutMapping("/api/auth/posts/{postId}")
    public ResponseDto<?> updatePost(@PathVariable Long postId,
                                     @RequestBody PostRequestDto requestDto,
                                     HttpServletRequest request
                                     ) throws IOException {
        //이미지가 들어왔다면
        return postService.updatePost(postId, requestDto, request);
    }

    @DeleteMapping("/api/auth/posts/{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        return postService.deletePost(postId, request);
    }

    @GetMapping("/api/posts/search")
    public ResponseDto<?> searchPost(@RequestParam String title) {
        return postService.searchPost(title);
    }

    @GetMapping("/api/posts/category")
    public ResponseDto<?> getPostsByCategory(@RequestParam("category") String category) {
        return postService.getPostsByCategory(category);
    }

    /*@PostMapping("/api/posts/category")
    public ResponseDto<?> addPostCategory(@RequestBody String category) {
        return postService.addPostCategory(category);
    }*/

    @PostMapping("/api/posts/bookmark/{postId}/{username}")
    public ResponseDto<?> flagBookmark(@PathVariable Long postId, @PathVariable String username) {
        return postService.flagBookmark(postId, username);
    }


}

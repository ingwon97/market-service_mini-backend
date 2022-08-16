package com.example.carrot.controller;

import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/auth/comments/{postId}")
    public ResponseDto<?> createComment(@PathVariable Long postId,
                                        @RequestParam String content,
                                        HttpServletRequest request) {
        return commentService.createComment(postId, content, request);
    }

    @GetMapping("/api/comments/{postId}")
    public ResponseDto<?> findAllComment(@PathVariable Long postId) {
        return commentService.findAllComment(postId);
    }

    @PutMapping("/api/auth/comments/{commentId}")
    public ResponseDto<?> updateComment(@PathVariable Long commentId,
                                        @RequestParam String content,
                                        HttpServletRequest request) {
        return commentService.updateComment(commentId, content, request);
    }

    @DeleteMapping("/api/auth/comments/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId,
                                        @AuthenticationPrincipal UserDetails details,
                                        HttpServletRequest request) {
        return commentService.deleteComment(commentId, request);
    }
}

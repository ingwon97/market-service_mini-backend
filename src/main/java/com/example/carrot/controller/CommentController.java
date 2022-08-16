package com.example.carrot.controller;

import com.example.carrot.response.ResponseDto;
import com.example.carrot.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comments/{postId}")
    public ResponseDto<?> createComment(@PathVariable Long postId, @AuthenticationPrincipal UserDetails details,
                                        @RequestParam String content) {
        return commentService.createComment(postId, details, content);
    }

    @GetMapping("/api/comments/{postId}")
    public ResponseDto<?> findAllComment(@PathVariable Long postId) {
        return commentService.findAllComment(postId);
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseDto<?> updateComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails details,
                                        @RequestParam String content) {
        return commentService.updateComment(commentId, details, content);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails details) {
        return commentService.deleteComment(commentId, details);
    }
}

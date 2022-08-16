package com.example.carrot.service;

import com.example.carrot.jwt.TokenProvider;
import com.example.carrot.model.*;
import com.example.carrot.repository.BookmarkRepository;
import com.example.carrot.repository.CommentRepository;
import com.example.carrot.repository.MemberRepository;
import com.example.carrot.repository.PostRepository;
import com.example.carrot.response.MypageBookmarkResponseDto;
import com.example.carrot.response.MypageCommentResponseDto;
import com.example.carrot.response.MypagePostResponseDto;
import com.example.carrot.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MypageService {


    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }


    @Transactional
    public ResponseDto<?> mypageAllPost(HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        List<Post> postsByMember = postRepository.findAllByMember(member);


        return ResponseDto.success(
                MypagePostResponseDto.builder()
                        .posts(postsByMember)
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> mypageAllComment(HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        List<Comment> commentsByMember = commentRepository.findAllByMember(member);

        return ResponseDto.success(

                MypageCommentResponseDto.builder()
                        .comments(commentsByMember)
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> mypageAllBookmark(HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }
        Member member = validateMember(request);
        List<Bookmark> bookmarksByMember = bookmarkRepository.findAllByMember(member);


        return ResponseDto.success(
                MypageBookmarkResponseDto.builder()
                        .bookmarks(bookmarksByMember)
                        .build()
        );
    }


}

package com.example.carrot.service;

import com.example.carrot.image.S3UploaderService;
import com.example.carrot.jwt.TokenProvider;
import com.example.carrot.model.*;
import com.example.carrot.repository.*;
import com.example.carrot.request.MypageRequestDto;
import com.example.carrot.response.MypageBookmarkResponseDto;
import com.example.carrot.response.MypageCommentResponseDto;
import com.example.carrot.response.MypagePostResponseDto;
import com.example.carrot.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MypageService {


    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final BookmarkRepository bookmarkRepository;
    private final MypageRepository mypageRepository;
    private final TokenProvider tokenProvider;
    private final S3UploaderService s3UploaderService;

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Mypage isPresentMypage(Long mypageId) {
        Optional<Mypage> optionalMypage = mypageRepository.findById(mypageId);
        return optionalMypage.orElse(null);
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

    @Transactional
    public ResponseDto<?> createImage(MypageRequestDto requestDto, HttpServletRequest request) throws IOException {

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

        // 멤버를 가지고, 게시글 만들기
        String imageUrl = s3UploaderService.upload(requestDto.getFile(), "static");
        System.out.println(imageUrl);
        Mypage mypage = Mypage.builder()
                .image_url(imageUrl)
                .build();

        mypageRepository.save(mypage);

        return ResponseDto.success(mypage);
    }

    @Transactional
    public ResponseDto<?> updateImage(Long memberId, MypageRequestDto requestDto, HttpServletRequest request) throws IOException {

        // 멤버를 가지고 오기
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

        Mypage mypage = isPresentMypage(memberId);
        if (null == mypage) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (mypage.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        String imageUrl = mypage.getImage_url();

        if (requestDto.getFile() != null) {
            String deleteUrl = imageUrl.substring(imageUrl.indexOf("static"));
            s3UploaderService.deleteImage(deleteUrl);

            imageUrl = s3UploaderService.upload(requestDto.getFile(), "static");
        }

        mypage.update(imageUrl, requestDto);
        return ResponseDto.success(mypage);
    }
}

package efub.assignment.community.post.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import efub.assignment.community.global.SecurityUtil;
import efub.assignment.community.heart.service.PostHeartService;
import efub.assignment.community.member.domain.User;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostModifyRequestDto;
import efub.assignment.community.post.dto.PostRequestDto;
import efub.assignment.community.post.dto.PostResponseDto;
import efub.assignment.community.post.service.PostService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostHeartService postHeartService;
    private final SecurityUtil securityUtil;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResponseDto postAdd(
        @RequestPart(value = "image") MultipartFile image,
        @RequestPart(value = "request") PostRequestDto requestDto,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        User user = securityUtil.getCurrentUser(authorization);
        return postService.addPost(image, requestDto, user);
    }

    // 전체 조회
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<PostResponseDto> postListFind(){

        List<Post> postList = postService.findPostList();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for(Post post : postList){
            responseDtoList.add(new PostResponseDto(post));
        }
        return responseDtoList;
    }

    // 개별 조회
    @GetMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postFind(@PathVariable Long postId){
        Post post = postService.findPost(postId);
        return new PostResponseDto(post);
    }

    // 삭제
    @DeleteMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String postRemove(@PathVariable Long postId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        User user = securityUtil.getCurrentUser(authorization);
        postService.removePost(user, postId);
        return "게시글이 삭제되었습니다.";
    }

    // 수정
    @PatchMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postModify(@PathVariable Long postId, @RequestBody PostRequestDto requestDto,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        User user = securityUtil.getCurrentUser(authorization);
        Post post = postService.modifyPost(user, postId, requestDto);
        return new PostResponseDto(post);
    }

    // 좋아요 생성
    @PostMapping("/{postId}/hearts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String postHeartCreate(@PathVariable final Long postId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        User user = securityUtil.getCurrentUser(authorization);
        postHeartService.createPostHeart(user, postId);
        return "좋아요를 눌렀습니다.";
    }

    // 좋아요 삭제
    @DeleteMapping("/{postId}/hearts")
    @ResponseStatus(value = HttpStatus.OK)
    public String postHeartDelete(@PathVariable final Long postId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        User user = securityUtil.getCurrentUser(authorization);
        postHeartService.deletePostHeart(user, postId);
        return "좋아요가 취소되었습니다.";
    }

    @GetMapping("/{postId}/hearts")
    @ResponseStatus(value = HttpStatus.OK)
    public Integer findHeartCount(@PathVariable final Long postId) {
        return postHeartService.findHeartCount(postId);
    }

    @GetMapping("/hearts")
    @ResponseStatus(value = HttpStatus.OK)
    public List<PostResponseDto> findHeartPost(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        User user = securityUtil.getCurrentUser(authorization);
        return postHeartService.findHeartPost(user);
    }
}

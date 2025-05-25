package efub.assignment.community.heart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import efub.assignment.community.global.SecurityUtil;
import efub.assignment.community.heart.domain.PostHeart;
import efub.assignment.community.heart.repository.PostHeartRepository;
import efub.assignment.community.member.domain.User;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostResponseDto;
import efub.assignment.community.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;
    private final PostService postService;

    public void createPostHeart(User user, Long postId) {
        Post post = postService.findPost(postId);

        if (isExistsByMemberAndPost(user, post)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 게시물입니다.");
        }

        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .user(user)
                .build();

        postHeartRepository.save(postHeart);
    }

    public void deletePostHeart(User user, Long postId) {
        Post post = postService.findPost(postId);

        PostHeart postHeart = postHeartRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요가 존재하지 않습니다."));

        postHeartRepository.delete(postHeart);
    }

    @Transactional(readOnly = true)
    public Boolean isExistsByMemberAndPost(User user, Post post) {
        return postHeartRepository.existsByUserAndPost(user, post);
    }

    @Transactional(readOnly = true)
    public Integer findHeartCount(Long postId) {
        Post post = postService.findPost(postId);
        return postHeartRepository.findAllByPost(post).size();
    }

    public List<PostResponseDto> findHeartPost(User user) {
        List<PostHeart> heartList = postHeartRepository.findAllByUser(user);
        return heartList.stream().map(PostHeart::getPost)
            .map(PostResponseDto::new).collect(Collectors.toList());
    }
}

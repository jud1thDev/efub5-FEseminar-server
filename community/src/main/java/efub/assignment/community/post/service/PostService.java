package efub.assignment.community.post.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import efub.assignment.community.global.SecurityUtil;
import efub.assignment.community.member.domain.User;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostModifyRequestDto;
import efub.assignment.community.post.dto.PostRequestDto;
import efub.assignment.community.post.dto.PostResponseDto;
import efub.assignment.community.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final S3Service s3Service;

    public PostResponseDto addPost(MultipartFile image, PostRequestDto requestDto, User user) {
        String imageUrl = s3Service.imageUpload(image, user);
        Post post = postRepository.save(
                Post.builder()
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .image(imageUrl)
                        .writer(user)
                        .build()
        );
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<Post> findPostList() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));
    }

    public void removePost(User user, Long postId) {
        Post post = findPost(postId);
        if(!post.getWriter().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시글 작성자만 삭제할 수 있습니다.");
        }
        postRepository.delete(post);
    }


    public Post modifyPost(User user, Long postId, PostRequestDto requestDto) {
        Post post = findPost(postId);
        if(!post.getWriter().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시글 작성자만 수정할 수 있습니다.");
        }
        post.updatePost(requestDto);
        return post;
    }
}

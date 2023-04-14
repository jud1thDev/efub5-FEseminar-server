package efub.assignment.community.post.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.repository.MemberRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostModifyRequestDto;
import efub.assignment.community.post.dto.PostRequestDto;
import efub.assignment.community.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Post addPost(PostRequestDto requestDto) {
        Member writer = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 계정입니다."));

        return postRepository.save(
                Post.builder()
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .writer(writer)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public List<Post> findPostList() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    public void removePost(Long postId, Long memberId) {
        Post post = postRepository.findByPostIdAndWriter_MemberId(postId, memberId)
                .orElseThrow(()->new IllegalArgumentException("잘못된 접근입니다."));
        // findByPostIdAndWriter_AccountId -> postId, accountId 중 어떤 것이 잘못된 것인지 구체화해서 메소드 새로 작성해보기
        // 메소드를 쪼개면 메세지도 구체적으로 적을 수 있다.

        postRepository.delete(post);
    }


    public Post modifyPost(Long postId, PostModifyRequestDto requestDto) {
        Post post = postRepository.findByPostIdAndWriter_MemberId(postId, requestDto.getMemberId())
                .orElseThrow(()->new IllegalArgumentException("잘못된 접근입니다."));
        post.updatePost(requestDto);
        return post;
    }
}

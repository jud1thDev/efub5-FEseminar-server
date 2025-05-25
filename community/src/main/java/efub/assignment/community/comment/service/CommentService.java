package efub.assignment.community.comment.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentModifyRequestDto;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.repository.CommentRepository;
import efub.assignment.community.global.SecurityUtil;
import efub.assignment.community.member.domain.User;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;


    public Comment addComment(User user, CommentRequestDto requestDto){
        Post post = postService.findPost(requestDto.getPostId());
        return commentRepository.save(
                Comment.builder()
                        .content(requestDto.getContent())
                        .writer(user)
                        .post(post)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    // 댓글 조회 - ID별
    public Comment findComment(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."));
    }

    @Transactional(readOnly = true)
    public List<Comment> findCommentList(Long postId){
        Post post = postService.findPost(postId);
        return commentRepository.findAllByPost(post);
    }

    public void removeComment(User user, Long commentId){
        Comment comment = findComment(commentId);
        if(!comment.getWriter().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 작성자만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }
}

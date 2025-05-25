package efub.assignment.community.comment.dto;

import java.time.LocalDateTime;

import efub.assignment.community.comment.domain.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponseDto {
    private Long postId;
    private Long commentId;
    private Long commentAuthorId;
    private String commentAuthorNickname;
    private String content;
    private LocalDateTime createdDate;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.commentAuthorId = comment.getWriter().getUserId();
        this.commentAuthorNickname = comment.getWriter().getNickname();
        this.postId = comment.getPost().getPostId();
        this.createdDate = comment.getCreatedDate();
    }
}

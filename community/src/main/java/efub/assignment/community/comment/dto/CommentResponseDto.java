package efub.assignment.community.comment.dto;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private Member writer;
    private Long postId;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.writer = comment.getWriter();
        this.postId = comment.getPost().getPostId();
        this.createDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}

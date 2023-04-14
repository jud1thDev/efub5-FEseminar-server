package efub.assignment.community.post.domain;
// data access layer에 포함

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.post.dto.PostModifyRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Boolean anonymous;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Post(Long postId, String title, String content, Boolean anonymous, Member writer, Board board) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.anonymous = anonymous;
        this.writer = writer;
        this.board = board;
    }

    public void updatePost(PostModifyRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}

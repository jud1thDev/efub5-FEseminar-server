package efub.assignment.community.post.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.heart.domain.PostHeart;
import efub.assignment.community.member.domain.User;
import efub.assignment.community.post.dto.PostModifyRequestDto;
import efub.assignment.community.post.dto.PostRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    private String title;

    @Column
    private String content;

    @Column(columnDefinition = "text")
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostHeart> postHeartList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post(Long postId, String title, String content, String image, User writer) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.image = image;
        this.writer = writer;
    }

    public void updatePost(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}

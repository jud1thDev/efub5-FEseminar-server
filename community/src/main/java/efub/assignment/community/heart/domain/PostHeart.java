package efub.assignment.community.heart.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import efub.assignment.community.member.domain.User;
import efub.assignment.community.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_heart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "게시글은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Builder
    public PostHeart(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}

package efub.assignment.community.post.repository;
//테이블(Post)에 접근하는 매개체
import efub.assignment.community.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    // pk의 타입이 Long

    Optional<Post> findByPostIdAndWriter_MemberId(Long postId, Long memberId);


}

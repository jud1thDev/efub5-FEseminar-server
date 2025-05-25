package efub.assignment.community.heart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import efub.assignment.community.heart.domain.PostHeart;
import efub.assignment.community.member.domain.User;
import efub.assignment.community.post.domain.Post;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    Boolean existsByUserAndPost(User user, Post post);

    Optional<PostHeart> findByUserAndPost(User user, Post post);

    List<PostHeart> findAllByPost(Post post);

    List<PostHeart> findAllByUser(User user);
}

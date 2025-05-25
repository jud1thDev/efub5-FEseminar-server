package efub.assignment.community.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.post.domain.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}

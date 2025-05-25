package efub.assignment.community.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import efub.assignment.community.member.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByUsername(String username);

	Boolean existsByNickname(String nickname);

	Optional<User> findByUsernameAndPassword(String username, String password);

	Optional<User> findByUsername(String username);
}

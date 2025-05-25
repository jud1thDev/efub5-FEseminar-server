package efub.assignment.community.global;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import efub.assignment.community.member.domain.User;
import efub.assignment.community.member.repository.UserRepository;
import efub.assignment.community.member.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityUtil {
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;

	public User getCurrentUser(String authorization) {
		// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// if (authentication == null || authentication.getPrincipal() == null
		// 	|| authentication.getName().equals("anonymousUser")) {
		// 	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authentication information");
		// }
		// return (User)authentication.getPrincipal();

		String username = jwtTokenProvider.parseClaims(authorization).get("username", String.class);
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "인증 실패"));
	}

}

package efub.assignment.community.member.service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import efub.assignment.community.member.domain.User;
import efub.assignment.community.member.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	@Autowired
	private UserRepository userRepository;

	private Key key;
	private Long accessTokenValidTime = Duration.ofDays(30).toMillis();

	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createAccessToken(User user) {
		Claims claims = Jwts.claims();
		claims.put("username", user.getUsername());
		claims.put("nickname", user.getNickname());

		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessTokenValidTime))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

	}

	public String resolveToken(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorization == null || !authorization.startsWith("Bearer")) {
			return null;
		}
		return authorization.substring(7);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			log.debug("Success: Token validation");
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT is empty.", e);
		}
		return false;
	}

	public Authentication getAuthentication(String token) {
		try {
			String username = parseClaims(token).get("username", String.class);
			User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "인증 실패: username = " + username));
			return new UsernamePasswordAuthenticationToken(user, "");
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (JwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT is empty.", e);
		}
		return null;
	}

	public Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}

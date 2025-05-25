package efub.assignment.community.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

	private String username;
	private String accessToken;
}

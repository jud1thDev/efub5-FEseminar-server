package efub.assignment.community.member.dto;

import efub.assignment.community.member.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {

    private Long userId;
    private String username;
    private String nickname;

    public UserResponseDto(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}

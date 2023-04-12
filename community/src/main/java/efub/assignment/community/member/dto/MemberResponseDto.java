package efub.assignment.community.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import efub.assignment.community.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {

    // 필드에서 Id 삭제
    private String email;
    private String nickname;
    private String university;
    private String studentId;

    // 생성자에서 Id 삭제
    public MemberResponseDto(String email, String nickname, String university, String studentId){
        this.email = email;
        this.nickname = nickname;
        this.university = university;
        this.studentId = studentId;
    }

    public static MemberResponseDto from(Member member){
        return new MemberResponseDto(
                member.getEmail(),
                member.getNickname(),
                member.getUniversity(),
                member.getStudentId());
    }
}

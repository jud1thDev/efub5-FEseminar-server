package efub.assignment.community.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import efub.assignment.community.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long userId;

    // @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    // @Column(nullable = false, length = 60)
    // private String email;

    @Column(nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    // @Column(nullable = false)
    // private String university;
    //
    // @Column(nullable = false)
    // private String studentId;
    //
    // @Enumerated(EnumType.STRING)
    // private MemberStatus status;

    @Builder
    public User(String username, String password, String nickname){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public void updateMember(String nickname){
        this.nickname = nickname;
    }
}

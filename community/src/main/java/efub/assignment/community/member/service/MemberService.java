package efub.assignment.community.member.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import efub.assignment.community.global.SecurityUtil;
import efub.assignment.community.member.domain.User;
import efub.assignment.community.member.dto.LoginRequestDto;
import efub.assignment.community.member.dto.LoginResponseDto;
import efub.assignment.community.member.dto.SignUpRequestDto;
import efub.assignment.community.member.dto.UserResponseDto;
import efub.assignment.community.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String signUp(SignUpRequestDto requestDto){
        if(userRepository.existsByUsername(requestDto.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다.");
        }

        if(userRepository.existsByNickname(requestDto.getNickname())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다.");
        }

        userRepository.save(requestDto.toEntity());
        return "회원가입에 성공하였습니다.";
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsernameAndPassword(
            requestDto.getUsername(), requestDto.getPassword())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "로그인에 실패하였습니다."));

        return LoginResponseDto.builder()
            .username(user.getUsername())
            .accessToken(jwtTokenProvider.createAccessToken(user))
            .build();
    }

    public UserResponseDto jwtTest(User user) {
        return new UserResponseDto(user);
    }
}

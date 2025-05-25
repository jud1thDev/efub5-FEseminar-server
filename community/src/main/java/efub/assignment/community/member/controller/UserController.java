package efub.assignment.community.member.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import efub.assignment.community.global.SecurityUtil;
import efub.assignment.community.member.domain.User;
import efub.assignment.community.member.dto.LoginRequestDto;
import efub.assignment.community.member.dto.LoginResponseDto;
import efub.assignment.community.member.dto.SignUpRequestDto;
import efub.assignment.community.member.dto.UserResponseDto;
import efub.assignment.community.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final MemberService memberService;
	private final SecurityUtil securityUtil;

	@PostMapping("/register")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String signUp(@RequestBody @Valid SignUpRequestDto requestDto){
		return memberService.signUp(requestDto);
	}

	@PostMapping("/login")
	@ResponseStatus(value = HttpStatus.CREATED)
	public LoginResponseDto login(@RequestBody @Valid LoginRequestDto requestDto){
		return memberService.login(requestDto);
	}

	@GetMapping("/test")
	@ResponseStatus(value = HttpStatus.OK)
	public UserResponseDto jwtTest(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
		User user = securityUtil.getCurrentUser(authorization);
		return memberService.jwtTest(user);
	}
}


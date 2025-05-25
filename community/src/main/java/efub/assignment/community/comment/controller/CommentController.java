package efub.assignment.community.comment.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.dto.CommentResponseDto;
import efub.assignment.community.comment.service.CommentService;
import efub.assignment.community.global.SecurityUtil;
import efub.assignment.community.member.domain.User;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final SecurityUtil securityUtil;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentResponseDto commentAdd(@RequestBody @Valid CommentRequestDto requestDto,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        User user = securityUtil.getCurrentUser(authorization);
        Comment comment = commentService.addComment(user, requestDto);
        return new CommentResponseDto(comment);
    }

    // 삭제
    @DeleteMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String commentRemove(@PathVariable final Long commentId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        User user = securityUtil.getCurrentUser(authorization);
        commentService.removeComment(user, commentId);
        return "댓글이 성공적으로 삭제되었습니다.";
    }

}

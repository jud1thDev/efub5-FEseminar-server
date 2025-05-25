package efub.assignment.community.post.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.dto.CommentResponseDto;
import efub.assignment.community.comment.service.CommentService;
import efub.assignment.community.global.SecurityUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class PostCommentController {

    private final CommentService commentService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommentResponseDto> commentList(@PathVariable Long postId){
        List<Comment> commentList = commentService.findCommentList(postId);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        for (Comment comment:commentList) {
            responseDtoList.add(new CommentResponseDto(comment));
        }
        return responseDtoList;
    }
}

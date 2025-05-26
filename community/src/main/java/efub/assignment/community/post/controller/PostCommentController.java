package efub.assignment.community.post.controller;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.dto.CommentResponseDto;
import efub.assignment.community.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "PostComment", description = "게시글 댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class PostCommentController {

    private final CommentService commentService;

    @Operation(summary = "게시글에 댓글 작성")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentResponseDto commentAdd(@PathVariable Long postId, @RequestBody @Valid CommentRequestDto requestDto){
        Comment comment = commentService.addComment(postId, requestDto);
        return new CommentResponseDto(comment);
    }

    @Operation(summary = "게시글의 댓글 목록 조회")
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

package efub.assignment.community.board.controller;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.BoardCreateRequestDto;
import efub.assignment.community.board.dto.BoardResponseDto;
import efub.assignment.community.board.dto.BoardUpdateRequestDto;
import efub.assignment.community.board.service.BoardService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostResponseDto;
import efub.assignment.community.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Board", description = "게시판 관련 API")
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final PostService postService;

    @Operation(summary = "게시판 생성")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public BoardResponseDto boardAdd(@RequestBody BoardCreateRequestDto requestDto){
        Board board = boardService.create(requestDto);
        return new BoardResponseDto(board);
    }

    @Operation(summary = "게시판 단건 조회")
    @GetMapping("/{boardId}")
    @ResponseStatus(value = HttpStatus.OK)
    public BoardResponseDto boardFind(@PathVariable Long boardId){
        Board board = boardService.read(boardId);
        return new BoardResponseDto(board);
    }

    @Operation(summary = "게시판 내 게시글 전체 조회")
    @GetMapping("/{boardId}/posts")
    @ResponseStatus(value = HttpStatus.OK)
    public List<PostResponseDto> postsListInBoard(@PathVariable Long boardId){

        List<Post> postList = postService.findPostsInBoard(boardId);
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for(Post post : postList){
            responseDtoList.add(new PostResponseDto(post));
        }
        return responseDtoList;

    }

    @Operation(summary = "게시판 수정")
    @PutMapping("/{boardId}")
    @ResponseStatus(value = HttpStatus.OK)
    public BoardResponseDto boardModifiy(@PathVariable Long boardId, @RequestBody BoardUpdateRequestDto requestDto){
        Board board = boardService.update(boardId, requestDto);
        return new BoardResponseDto(board);
    }

    @Operation(summary = "게시판 삭제")
    @DeleteMapping("/{boardId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String boardRemove(@PathVariable Long boardId){
        // 게시판에 속한 글들도 모두 삭제
        List<Post> postList = postService.findPostsInBoard(boardId);
        for (Post p: postList) {
            postService.removePost(p.getPostId());
        }

        boardService.delete(boardId);
        return "성공적으로 삭제되었습니다.";
    }
}

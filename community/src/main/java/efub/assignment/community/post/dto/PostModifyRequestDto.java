package efub.assignment.community.post.dto;


import lombok.Getter;

/*
{
    "accountId":"1",
    "title" : "꺅",
    "content": "오늘 이펍 세션이 있는 날이다. "
}
 */
@Getter
public class PostModifyRequestDto {
    private Long memberId;
    private String title;
    private String content;


}

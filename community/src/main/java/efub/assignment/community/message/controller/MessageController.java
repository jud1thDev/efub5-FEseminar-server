package efub.assignment.community.message.controller;

import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.MessageCreateResponseDto;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.service.MessageService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Message", description = "쪽지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final MessageRoomService messageRoomService;

    @Operation(summary = "쪽지 전송")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MessageCreateResponseDto messageAdd(@RequestBody @Valid MessageRequestDto requestDto){
        MessageRoom messageRoom = messageRoomService.findRoomById(requestDto.getMessageRoomId());
        Message message = messageService.addMessage(
                messageRoom, requestDto.getSenderId(), requestDto.getContent());
        return new MessageCreateResponseDto(message);
    }
}

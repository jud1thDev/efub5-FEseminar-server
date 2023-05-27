package efub.assignment.community.message.controller;

import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.MessageCreateResponseDto;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MessageCreateResponseDto messageAdd(@RequestBody @Valid MessageRequestDto requestDto){
        Message message = messageService.addMessage(requestDto);
        return new MessageCreateResponseDto(message);
    }
}

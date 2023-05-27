package efub.assignment.community.message.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.repository.MessageRepository;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageRoomService messageRoomService;
    private final MemberService memberService;

    public Message addMessage(MessageRequestDto requestDto){
        MessageRoom messageRoom = messageRoomService.findRoomById(requestDto.getMessageRoomId());

        Member sender = memberService.findMemberById(requestDto.getSenderId());

        return messageRepository.save(
                Message.builder()
                        .messageRoom(messageRoom)
                        .sender(sender)
                        .content(requestDto.getContent())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public Boolean isReceived(Message message, Long memberId){
        Member member = memberService.findMemberById(memberId);
        if(message.getSender().equals(member))
            return false;
        else
            return true;
    }
}

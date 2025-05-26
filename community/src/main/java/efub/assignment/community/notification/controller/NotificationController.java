package efub.assignment.community.notification.controller;

import efub.assignment.community.notification.domain.Notification;
import efub.assignment.community.notification.dto.NotificationResponseDto;
import efub.assignment.community.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Notification", description = "알림 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 목록 조회")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<NotificationResponseDto> notificationListFind() {
        List<Notification> notificationList = notificationService.findNotificationList();
        List<NotificationResponseDto> notificationResponseDtoList = new ArrayList<>();
        for(Notification notification:notificationList){
            notificationResponseDtoList.add(
                    new NotificationResponseDto(notification)
            );
        }
        return notificationResponseDtoList;
    }
}

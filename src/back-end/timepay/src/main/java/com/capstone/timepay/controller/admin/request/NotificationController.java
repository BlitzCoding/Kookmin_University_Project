package com.capstone.timepay.controller.admin.request;

import com.capstone.timepay.domain.notification.Notification;
import com.capstone.timepay.service.admin.dto.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController("request.NotificationController")
@RequestMapping("api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("")
    @ApiOperation(value = "공지사항 생성")
    public ResponseEntity<Boolean> createNotification(@RequestBody Notification notification) {
        boolean success = this.notificationService.create(notification);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @DeleteMapping("/{notificationId}")
    @ApiOperation(value = "공지사항 삭제")
    public ResponseEntity<Map<String, Object>> deleteNotification(@PathVariable Long notificationId) {
        Map<String, Object> result = notificationService.delete(notificationId);

        if ((Boolean) result.get("success")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

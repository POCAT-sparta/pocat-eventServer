package sparta.eventserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    TRADE_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "거래 게시글을 찾을 수 없습니다."),

    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다."),
    NOTIFICATION_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 알림에 대한 접근 권한이 없습니다."),
    NOTIFICATION_SERIALIZE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "알림 직렬화에 실패했습니다."),

    // Webhook
    WEBHOOK_IP_FORBIDDEN(HttpStatus.FORBIDDEN, "허용되지 않은 IP에서의 Webhook 요청입니다."),

    // Chat
    CHAT_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다."),
    CHAT_FORBIDDEN(HttpStatus.FORBIDDEN, "채팅방 접근 권한이 없습니다."),
    CHAT_SELF_CHAT(HttpStatus.BAD_REQUEST, "자신의 게시글에는 채팅을 시작할 수 없습니다."),
    CHAT_ALREADY_EXISTS(HttpStatus.CONFLICT, "해당 게시글에 이미 채팅방이 존재합니다.");

    private final HttpStatus status;
    private final String message;
}

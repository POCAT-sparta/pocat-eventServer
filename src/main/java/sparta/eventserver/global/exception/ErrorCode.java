package sparta.eventserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    TRADE_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "거래 게시글을 찾을 수 없습니다."),

    // Chat
    CHAT_FORBIDDEN(HttpStatus.FORBIDDEN, "채팅방 접근 권한이 없습니다."),
    CHAT_SELF_CHAT(HttpStatus.BAD_REQUEST, "자신의 게시글에는 채팅을 시작할 수 없습니다."),
    CHAT_ALREADY_EXISTS(HttpStatus.CONFLICT, "해당 게시글에 이미 채팅방이 존재합니다.");

    private final HttpStatus status;
    private final String message;
}

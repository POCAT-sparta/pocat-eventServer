package sparta.eventserver.domain.chat.dto.response;

import sparta.eventserver.domain.chat.entity.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        Long id,
        Long senderId,
        String senderNickname,
        String message,
        boolean isRead,
        LocalDateTime createdAt
) {
    public static ChatMessageResponse from(ChatMessage msg, String senderNickname) {
        return new ChatMessageResponse(
                msg.getId(),
                msg.getSenderId(),
                senderNickname,
                msg.getMessage(),
                msg.isRead(),
                msg.getCreatedAt()
        );
    }
}

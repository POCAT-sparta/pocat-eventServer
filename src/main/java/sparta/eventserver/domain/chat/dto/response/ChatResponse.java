package sparta.eventserver.domain.chat.dto.response;


import sparta.eventserver.domain.chat.entity.Chat;
import sparta.eventserver.domain.chat.entity.enums.ChatStatus;

public record ChatResponse(
        Long chatId,
        Long postId,
        Long ownerId,
        Long guestId,
        ChatStatus status
) {
    public static ChatResponse from(Chat chat) {
        return new ChatResponse(
                chat.getId(),
                chat.getPostId(),
                chat.getOwnerId(),
                chat.getGuestId(),
                chat.getStatus()
        );
    }
}

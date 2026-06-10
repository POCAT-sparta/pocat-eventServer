package sparta.eventserver.domain.chat.dto.response;


import sparta.eventserver.domain.chat.entity.enums.ChatStatus;

import java.time.LocalDateTime;

public record ChatRoomListResponse(
        Long chatId,
        String postTitle,
        String opponentNickname,
        String lastMessage,
        ChatStatus status,
        LocalDateTime updatedAt
) {
}

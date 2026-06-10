package sparta.eventserver.domain.chat.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateChatRequest(
        @NotNull Long postId
) {
}

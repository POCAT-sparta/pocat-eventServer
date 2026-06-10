package sparta.eventserver.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import sparta.eventserver.domain.chat.dto.request.SendMessageRequest;
import sparta.eventserver.domain.chat.dto.response.ChatEventType;
import sparta.eventserver.domain.chat.dto.response.ChatMessagePublishDto;
import sparta.eventserver.domain.chat.dto.response.ChatReadPublishDto;
import sparta.eventserver.domain.chat.service.ChatCommandService;
import sparta.eventserver.global.security.CustomUserDetails;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatCommandService chatCommandService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{chatId}")
    public void sendMessage(
            @DestinationVariable Long chatId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            SendMessageRequest request) {

        ChatMessagePublishDto publish = chatCommandService.sendMessage(
                chatId, userDetails.getUserId(), request.message());
        messagingTemplate.convertAndSend("/sub/chat/" + chatId, publish);
    }

    @MessageMapping("/chat/{chatId}/read")
    public void markAsRead(
            @DestinationVariable Long chatId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();
        chatCommandService.markAsRead(chatId, userId);
        messagingTemplate.convertAndSend("/sub/chat/" + chatId,
                new ChatReadPublishDto(ChatEventType.READ, chatId, userId));
    }
}

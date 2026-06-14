package sparta.eventserver.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
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
            Authentication authentication,
            SendMessageRequest request) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        ChatMessagePublishDto publish = chatCommandService.sendMessage(
                chatId, userDetails.getUserId(), request.message());
        messagingTemplate.convertAndSend("/sub/chat/" + chatId, publish);
    }

    @MessageMapping("/chat/{chatId}/read")
    public void markAsRead(
            @DestinationVariable Long chatId,
            Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        chatCommandService.markAsRead(chatId, userId);
        messagingTemplate.convertAndSend("/sub/chat/" + chatId,
                new ChatReadPublishDto(ChatEventType.READ, chatId, userId));
    }
}

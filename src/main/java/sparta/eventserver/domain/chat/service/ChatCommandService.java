package sparta.eventserver.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.eventserver.domain.chat.dto.request.CreateChatRequest;
import sparta.eventserver.domain.chat.dto.response.ChatEventType;
import sparta.eventserver.domain.chat.dto.response.ChatMessagePublishDto;
import sparta.eventserver.domain.chat.dto.response.ChatResponse;
import sparta.eventserver.domain.chat.entity.Chat;
import sparta.eventserver.domain.chat.entity.ChatMessage;
import sparta.eventserver.domain.chat.entity.enums.ChatStatus;
import sparta.eventserver.domain.chat.repository.ChatMessageRepository;
import sparta.eventserver.domain.chat.repository.ChatRepository;
import sparta.eventserver.domain.tradepost.entity.TradePost;
import sparta.eventserver.domain.tradepost.repository.TradePostRepository;
import sparta.eventserver.domain.user.entity.User;
import sparta.eventserver.domain.user.repository.UserRepository;
import sparta.eventserver.global.exception.ChatException;
import sparta.eventserver.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatCommandService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final TradePostRepository tradePostRepository;
    private final UserRepository userRepository;


    public ChatResponse createChat(Long guestId, CreateChatRequest request) {
        TradePost post = tradePostRepository.findById(request.postId())
                .orElseThrow(() -> new ChatException(ErrorCode.TRADE_POST_NOT_FOUND));
        if (post.getUserId().equals(guestId)) {
            throw new ChatException(ErrorCode.CHAT_SELF_CHAT);
        }
        if (chatRepository.existsByPostIdAndGuestId(request.postId(), guestId)) {
            throw new ChatException(ErrorCode.CHAT_ALREADY_EXISTS);
        }
        Chat chat = Chat.builder()
                .ownerId(post.getUserId())
                .guestId(guestId)
                .postId(request.postId())
                .guestLeft(false)
                .ownerLeft(false)
                .status(ChatStatus.ACTIVE)
                .build();
        try {
            return ChatResponse.from(chatRepository.save(chat));
        } catch (DataIntegrityViolationException e) {
            throw new ChatException(ErrorCode.CHAT_ALREADY_EXISTS);
        }
    }

    public ChatMessagePublishDto sendMessage(Long chatId, Long senderId, String message) {
        chatRepository.findByIdAndParticipant(chatId, senderId)
                .orElseThrow(() -> new ChatException(ErrorCode.CHAT_FORBIDDEN));

//        badWordFilterService.validate(message);

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ChatException(ErrorCode.USER_NOT_FOUND));

        ChatMessage saved = chatMessageRepository.save(ChatMessage.builder()
                .chatId(chatId)
                .senderId(senderId)
                .message(message)
                .isRead(false)
                .build());

        return new ChatMessagePublishDto(
                ChatEventType.MESSAGE,
                chatId,
                senderId,
                sender.getNickname(),
                saved.getMessage(),
                saved.getCreatedAt()
        );
    }

    public void markAsRead(Long chatId, Long userId) {
        chatRepository.findByIdAndParticipant(chatId, userId)
                .orElseThrow(() -> new ChatException(ErrorCode.CHAT_FORBIDDEN));
        chatMessageRepository.markAllAsRead(chatId, userId);
    }

    public void leaveChat(Long chatId, Long userId) {
        Chat chat = chatRepository.findByIdAndParticipant(chatId, userId)
                .orElseThrow(() -> new ChatException(ErrorCode.CHAT_FORBIDDEN));

        if (chat.getOwnerId().equals(userId)) {
            chat.markOwnerLeft();
        } else {
            chat.markGuestLeft();
        }

        if (chat.isBothLeft()) {
            chatRepository.delete(chat);
        }
    }
}

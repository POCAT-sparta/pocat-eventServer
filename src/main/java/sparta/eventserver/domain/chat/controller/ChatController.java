package sparta.eventserver.domain.chat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.eventserver.domain.chat.dto.request.CreateChatRequest;
import sparta.eventserver.domain.chat.dto.response.ChatMessageResponse;
import sparta.eventserver.domain.chat.dto.response.ChatResponse;
import sparta.eventserver.domain.chat.dto.response.ChatRoomListResponse;
import sparta.eventserver.domain.chat.service.ChatCommandService;
import sparta.eventserver.domain.chat.service.ChatQueryService;
import sparta.eventserver.global.dto.ApiResponseDto;
import sparta.eventserver.global.dto.PageResponseDto;
import sparta.eventserver.global.security.CustomUserDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatController {

    private final ChatCommandService chatCommandService;
    private final ChatQueryService chatQueryService;

    @PostMapping("/v1/chats")
    public ResponseEntity<ApiResponseDto<ChatResponse>> createChat(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreateChatRequest request) {

        ChatResponse response = chatCommandService.createChat(userDetails.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(HttpStatus.CREATED, response));
    }

    @GetMapping("/v1/chats/me")
    public ResponseEntity<ApiResponseDto<List<ChatRoomListResponse>>> getMyChats(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ChatRoomListResponse> response = chatQueryService.getMyChats(userDetails.getUserId());
        return ResponseEntity.ok(ApiResponseDto.success(HttpStatus.OK, response));
    }

    @GetMapping("/v1/chats/{chatId}/messages")
    public ResponseEntity<ApiResponseDto<PageResponseDto<ChatMessageResponse>>> getMessages(
            @PathVariable Long chatId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(size = 30) Pageable pageable) {
        Page<ChatMessageResponse> page = chatQueryService.getMessages(chatId, userDetails.getUserId(), pageable);
        return ResponseEntity.ok(ApiResponseDto.success(HttpStatus.OK, PageResponseDto.of(page, page.getContent())));
    }

    @PatchMapping("/v1/chats/{chatId}/read")
    public ResponseEntity<ApiResponseDto<Void>> markAsRead(
            @PathVariable Long chatId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        chatCommandService.markAsRead(chatId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponseDto.successWithNoContent());
    }

    @DeleteMapping("/v1/chats/{chatId}")
    public ResponseEntity<ApiResponseDto<Void>> leaveChat(
            @PathVariable Long chatId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        chatCommandService.leaveChat(chatId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponseDto.successWithNoContent());
    }
}

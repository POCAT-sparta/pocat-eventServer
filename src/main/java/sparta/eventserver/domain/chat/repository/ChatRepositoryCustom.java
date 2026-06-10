package sparta.eventserver.domain.chat.repository;


import sparta.eventserver.domain.chat.dto.response.ChatRoomListResponse;

import java.util.List;

public interface ChatRepositoryCustom {

    List<ChatRoomListResponse> findMyChatsWithDetails(Long userId);
}

package sparta.eventserver.domain.notification.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sparta.eventserver.domain.notification.entity.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // 커서 없을 때 (처음 조회) — pageSize+1 조회로 hasNext 정확하게 판단
    List<Notification> findTop21ByUserIdAndIsReadFalseOrderByIdDesc(Long userId);

    // 커서 있을 때 (다음 페이지) — 정렬 키(id)와 커서 키(id)를 동일하게 유지
    List<Notification> findTop21ByUserIdAndIsReadFalseAndIdLessThanOrderByIdDesc(
            Long userId, Long cursor);
}

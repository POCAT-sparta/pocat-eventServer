package sparta.eventserver.domain.tradepost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.eventserver.domain.tradepost.entity.TradePost;


public interface TradePostRepository extends JpaRepository<TradePost, Long> {
}

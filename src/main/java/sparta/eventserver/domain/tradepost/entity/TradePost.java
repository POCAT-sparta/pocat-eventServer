package sparta.eventserver.domain.tradepost.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sparta.eventserver.global.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trade_posts")
@SQLDelete(sql = "UPDATE trade_posts SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class TradePost extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name="price", nullable = false)
    private Long price;

    @Column(name="thumbnail", columnDefinition = "TEXT")
    private String thumbnail;

    @Column(name="view_count",nullable = false)
    private int viewCount;
}

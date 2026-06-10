package sparta.eventserver.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import sparta.eventserver.domain.chat.entity.enums.ChatStatus;
import sparta.eventserver.global.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "guest_id"})
})
@SQLDelete(sql = "UPDATE chats SET deleted_at = NOW() WHERE id = ?")
public class Chat extends BaseEntity {

    @Column(name="owner_id", nullable = false)
    private Long ownerId;

    @Column(name="guest_id", nullable = false)
    private Long guestId;

    @Column(name="post_id", nullable = false)
    private Long postId;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false, length = 20)
    private ChatStatus status;

    @Column(name="owner_left", nullable = false)
    @Builder.Default
    private boolean ownerLeft = false;

    @Column(name="guest_left", nullable = false)
    @Builder.Default
    private boolean guestLeft = false;

    public void markOwnerLeft() {
        this.ownerLeft = true;
    }

    public void markGuestLeft() {
        this.guestLeft = true;
    }

    public boolean isBothLeft() {
        return this.ownerLeft && this.guestLeft;
    }
}

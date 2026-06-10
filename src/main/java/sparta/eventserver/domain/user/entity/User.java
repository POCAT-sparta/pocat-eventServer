package sparta.eventserver.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import sparta.eventserver.domain.user.enums.UserRole;
import sparta.eventserver.global.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class User extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, length = 100)
    private String nickname;

    @Column(name = "phone", length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 20)
    private UserRole userRole;

    @Column(name = "billing_key")
    private String billingKey;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "unpaid_strike", nullable = false)
    @Builder.Default
    private int unpaidStrike = 0;

    @Column(name = "is_bid_blocked", nullable = false)
    @Builder.Default
    private boolean isBidBlocked = false;
}

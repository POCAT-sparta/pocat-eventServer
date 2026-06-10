package sparta.eventserver.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Optional;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * JWT를 1회 파싱하여 Claims 반환. 유효하지 않으면 null 반환.
     * JwtAuthenticationFilter에서 파싱 횟수를 줄이기 위해 사용.
     */
    @Nullable
    public Claims parseClaimsOrNull(String token) {
        try {
            return getClaims(token);
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public Optional<TokenPayload> extractPayload(String token) {
        try {
            Claims claims = getClaims(token);
            return Optional.of(new TokenPayload(
                    Long.parseLong(claims.getSubject()),
                    claims.get("role", String.class)
            ));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public record TokenPayload(Long userId, String role) {}

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

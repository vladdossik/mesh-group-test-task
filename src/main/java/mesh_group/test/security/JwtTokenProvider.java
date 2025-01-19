package mesh_group.test.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    public String generateToken(Long userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long validateAndGetUserId(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        JwtParserBuilder parserBuilder = Jwts.parserBuilder()
                .setSigningKey(key);
        JwtParser parser = parserBuilder.build();

        Claims claims = parser.parseClaimsJws(token).getBody();

        return claims.get("userId", Long.class);
    }
}
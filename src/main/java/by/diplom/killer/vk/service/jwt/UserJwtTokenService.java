package by.diplom.killer.vk.service.jwt;

import by.diplom.killer.vk.config.JwtProperties;
import by.diplom.killer.vk.security.SecurityAccountType;
import by.diplom.killer.vk.security.TokenBody;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserJwtTokenService {

    private final JwtProperties jwtProperties;

    public String createAccessToken(String email) {
        return getToken(email, new SecurityAccountType(SecurityAccountType.REGISTERED_ACCOUNT));
    }

    public String createRefreshToken() {
        return getToken("refreshToken", null);
    }

    private String getToken(String subject, SecurityAccountType securityAccountType) {
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes()), SignatureAlgorithm.HS512)
                .setIssuer(jwtProperties.getTokenIssuer())
                .setSubject(subject)
                .setIssuedAt(
                        Date.from(
                                LocalDateTime.now()
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()
                        )
                )
                .claim(SecurityAccountType.CLAIM_NAME, securityAccountType)
                .compact();

        return jwtProperties.getTokenPrefix() + token;
    }

    public TokenBody parseAccessToken(String accessToken) {
        if (!accessToken.startsWith(jwtProperties.getTokenPrefix())) {
            return null;
        }

        byte[] signingKey = jwtProperties.getKey().getBytes();

        Jws<Claims> parsedToken =
                Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(accessToken.replace(jwtProperties.getTokenPrefix(), ""));

        if (Objects.isNull(parsedToken)) {
            return null;
        }

        return new TokenBody(parsedToken);
    }
}

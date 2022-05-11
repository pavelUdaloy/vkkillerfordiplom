package by.diplom.killer.vk.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("unchecked")
public class TokenBody {

    private String email;

    private SecurityAccountType accountType = new SecurityAccountType();

    public TokenBody(Jws<Claims> parsedToken) {
        this.email = parsedToken.getBody().getSubject();

        Object accountTypeObject = parsedToken.getBody().get(SecurityAccountType.CLAIM_NAME);

        Map<String, Object> accountTypeMap = (Map<String, Object>) accountTypeObject;
        this.accountType.setName((String) accountTypeMap.get("name"));
    }
}

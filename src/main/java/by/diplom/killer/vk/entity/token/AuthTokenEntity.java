package by.diplom.killer.vk.entity.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "authToken", timeToLive = 86400)
@TypeAlias("authToken")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AuthTokenEntity {

    @Id
    private Long id;
    @Indexed
    private String email;
    @Indexed
    private String token;
}

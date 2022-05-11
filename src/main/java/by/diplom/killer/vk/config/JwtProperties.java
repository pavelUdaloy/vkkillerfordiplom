package by.diplom.killer.vk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String key;
    private String tokenHeader;
    private String tokenPrefix;
    private String tokenIssuer;
}

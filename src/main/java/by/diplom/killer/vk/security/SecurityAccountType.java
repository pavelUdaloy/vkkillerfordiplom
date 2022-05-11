package by.diplom.killer.vk.security;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SecurityAccountType implements GrantedAuthority {

    public static final String CLAIM_NAME = "account_type";

    public static final String REGISTERED_ACCOUNT = "registered";
    public static final String PAID_ACCOUNT = "paid";
    public static final String ADMIN_ACCOUNT = "admin";

    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}

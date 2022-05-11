package by.diplom.killer.vk.security;

import by.diplom.killer.vk.entity.User;
import by.diplom.killer.vk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthentication implements AuthenticationFacade {

    private final UserService userService;

    @Override
    public User getUser() {
        return userService.getUserByEmail(SecurityUtils.getCurrentUser());
    }

}

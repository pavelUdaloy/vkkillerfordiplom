package by.diplom.killer.vk.security;

import by.diplom.killer.vk.security.entity.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static void fillErrorResponse(HttpServletResponse response, HttpStatus status, String message) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(message);

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            new ObjectMapper().writeValue(response.getWriter(), errorDto);
        } catch (IOException ignored) {
        }
    }

    public static String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.isNull(authentication) ? null : (String) authentication.getPrincipal();
    }
}

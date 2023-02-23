package by.diplom.killer.vk.security;

import by.diplom.killer.vk.config.JwtProperties;
import by.diplom.killer.vk.exception.BaseKillerException;
import by.diplom.killer.vk.service.jwt.UserJwtTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static by.diplom.killer.vk.security.SecurityUtils.fillErrorResponse;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtProperties jwtProperties;
    private final UserJwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProperties jwtProperties,
                                   UserJwtTokenService jwtTokenService) {

        super(authenticationManager);

        this.jwtProperties = jwtProperties;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String content = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        if (filterByContentXss(content)) {
            fillErrorResponse(response, HttpStatus.FORBIDDEN, "Sorry", "You can't attack by XSS this server");
        }
        if (filterByContentSql(content)) {
            fillErrorResponse(response, HttpStatus.FORBIDDEN, "Sorry", "You can't attack with SQL injection this server");
        }
        if (filterByContentSsi(content)) {
            fillErrorResponse(response, HttpStatus.FORBIDDEN, "Sorry", "You can't attack with SSI injection this server");
        }
        try {
            Authentication authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (BaseKillerException e) {
            fillErrorResponse(response, HttpStatus.UNAUTHORIZED, "sorry", "unauthorized");
        }
    }

    private boolean filterByContentXss(String content) {
        return content.contains("<script>");
    }

    private boolean filterByContentSql(String content) {
        return content.contains(";");
    }

    private boolean filterByContentSsi(String content) {
        return content.contains("echo");
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        String accessToken = request.getHeader(jwtProperties.getTokenHeader());

        if (!StringUtils.hasText(accessToken)) {
            return null;
        }

        TokenBody tokenBody = jwtTokenService.parseAccessToken(accessToken);

        if (Objects.isNull(tokenBody)) {
            return null;
        }

        String email = tokenBody.getEmail();
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(tokenBody.getAccountType());

        return new UsernamePasswordAuthenticationToken(email, null, authorities);
    }
}

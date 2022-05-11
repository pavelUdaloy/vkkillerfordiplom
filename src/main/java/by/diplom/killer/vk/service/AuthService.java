package by.diplom.killer.vk.service;

import by.diplom.killer.vk.controller.http.entity.AuthDto;
import by.diplom.killer.vk.controller.http.entity.AuthedUserDto;
import by.diplom.killer.vk.controller.http.entity.UserTokenDto;
import by.diplom.killer.vk.entity.User;
import by.diplom.killer.vk.entity.token.RefreshTokenEntity;
import by.diplom.killer.vk.exception.BaseKillerException;
import by.diplom.killer.vk.security.TokenBody;
import by.diplom.killer.vk.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final UserService userService;
    private final UserJwtTokenService userJwtTokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthTokenService authTokenService;

    public AuthedUserDto loginUser(AuthDto authDto) {
        User user = userService.verifyUser(authDto.getEmail(), authDto.getPassword());
        String accessToken = userJwtTokenService.createAccessToken(user.getEmail());
        String refreshAccessToken = userJwtTokenService.createRefreshToken();

        Long refreshTokenId = refreshTokenService.createRefreshTokenAndGetId(user, refreshAccessToken);
        authTokenService.createOrUpdateAuthToken(user.getEmail(), accessToken, refreshTokenId);

        return AuthedUserDto.builder()
                .userDto(userMapper.mapUserToUserDto(user))
                .userTokenDto(new UserTokenDto(accessToken, refreshAccessToken))
                .build();
    }

    /**
     * Updates access and refresh tokens by token id.
     *
     * @param oldRefreshToken old refresh token. Required to get token id
     * @return new tokens(access and refresh) with user data
     */
    public AuthedUserDto reLoginUser(String oldRefreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenService.retrieveByToken(oldRefreshToken);
        User user = refreshTokenEntity.getUser();
        String accessToken = userJwtTokenService.createAccessToken(user.getEmail());
        String refreshAccessToken = userJwtTokenService.createRefreshToken();

        refreshTokenService.updateRefreshTokenById(user, refreshAccessToken, refreshTokenEntity.getId());
        authTokenService.createOrUpdateAuthToken(user.getEmail(), accessToken, refreshTokenEntity.getId());

        return AuthedUserDto.builder()
                .userDto(userMapper.mapUserToUserDto(user))
                .userTokenDto(new UserTokenDto(accessToken, refreshAccessToken))
                .build();
    }

    /**
     * Deletes access and refresh tokens by token id.
     *
     * @param accessToken old access token. Required to get token id
     */
    public void logoutUser(String accessToken) {
        Long tokenId = authTokenService.getIdByToken(accessToken);
        TokenBody tokenBody = parseAccessToken(accessToken);
        userService.getUserByEmail(tokenBody.getEmail());
        authTokenService.removeAuthTokenById(tokenId);
        refreshTokenService.removeRefreshTokenById(tokenId);
    }

    /**
     * Updates access and refresh tokens by token id.
     *
     * @param oldAccessToken old access token. Required to get token id
     * @return new tokens(access and refresh)
     */
    public UserTokenDto refreshTokensByAccess(String oldAccessToken) {
        Long tokenId = authTokenService.getIdByToken(oldAccessToken);
        TokenBody tokenBody = parseAccessToken(oldAccessToken);
        User user = userService.getUserByEmail(tokenBody.getEmail());
        String accessToken = userJwtTokenService.createAccessToken(user.getEmail());
        String refreshToken = userJwtTokenService.createRefreshToken();
        refreshTokenService.updateRefreshTokenById(user, refreshToken, tokenId);
        authTokenService.createOrUpdateAuthToken(user.getEmail(), accessToken, tokenId);

        return new UserTokenDto(accessToken, refreshToken);
    }

    public TokenBody parseAccessToken(String token) {
        TokenBody tokenBody = userJwtTokenService.parseAccessToken(token);
        if (Objects.isNull(tokenBody)) {
            throw new BaseKillerException(String.format("Access token:%s, is not valid", token));
        }
        return tokenBody;
    }
}

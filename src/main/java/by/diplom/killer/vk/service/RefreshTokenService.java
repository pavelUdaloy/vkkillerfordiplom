package by.diplom.killer.vk.service;

import by.diplom.killer.vk.config.AuthorizationProperties;
import by.diplom.killer.vk.entity.User;
import by.diplom.killer.vk.entity.token.RefreshTokenEntity;
import by.diplom.killer.vk.exception.BaseKillerException;
import by.diplom.killer.vk.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthorizationProperties authorizationProperties;

    /**
     * Saves new token.
     * There is a limit on the number of authorized clients for one user.
     * Before saving checks the count of tokens for user in repository.
     * Earliest token need to delete if the number of authorized clients has reached the limit before saving new token.
     *
     * @param user  user
     * @param token new refresh token
     * @return new refresh token id
     */
    @Transactional
    public Long createRefreshTokenAndGetId(User user, String token) {
        List<RefreshTokenEntity> refreshTokens = refreshTokenRepository.findAllByUserId(user.getId());
        boolean limitOfTokensIsExceeded = refreshTokens.size() == authorizationProperties.getAuthorizedUsersCount();
        if (limitOfTokensIsExceeded) {
            refreshTokenRepository.deleteFirstByUserId(user.getId());
        }
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(token, user);
        return refreshTokenRepository.save(refreshTokenEntity).getId();
    }

    /**
     * Updates refresh token by id.
     *
     * @param user  user
     * @param token new refresh token
     * @param id    token id
     * @throws BaseKillerException – if id does not exist in the database
     */
    @Transactional
    public void updateRefreshTokenById(User user, String token, Long id) {
        if (refreshTokenRepository.findById(id).isEmpty()) {
            throw new BaseKillerException("wrong refresh token");
        }
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(id, token, user);
        refreshTokenRepository.save(refreshTokenEntity);
    }

    /**
     * Removes all refresh tokens by user id.
     *
     * @param userId user id
     */
    @Transactional
    public void removeAllRefreshTokensByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    /**
     * Removes refresh token by id.
     *
     * @param id refresh token id
     */
    @Transactional
    public void removeRefreshTokenById(Long id) {
        refreshTokenRepository.deleteById(id);
    }

    /**
     * Receives refresh token entity by refresh token.
     *
     * @param token refresh token
     * @return refresh token entity
     * @throws BaseKillerException – if id does not exist in the database
     */
    @Transactional
    public RefreshTokenEntity retrieveByToken(String token) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(token);
        if (Objects.isNull(refreshToken)) {
            throw new BaseKillerException(String.format("Refresh token:%s, is not valid", token));
        }
        return refreshToken;
    }
}

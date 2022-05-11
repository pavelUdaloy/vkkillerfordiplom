package by.diplom.killer.vk.service;

import by.diplom.killer.vk.config.AuthorizationProperties;
import by.diplom.killer.vk.entity.token.AuthTokenEntity;
import by.diplom.killer.vk.exception.BaseKillerException;
import by.diplom.killer.vk.repository.redis.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private final AuthorizationProperties authorizationProperties;

    public void createOrUpdateAuthToken(String email, String token, Long tokenId) {
        List<AuthTokenEntity> authTokens = authTokenRepository.findAllByEmail(email);
        boolean limitOfTokensIsExceeded = authTokens.size() == authorizationProperties.getAuthorizedUsersCount();
        boolean accessTokenNotFoundById = authTokenRepository.findById(tokenId).isEmpty();
        if (limitOfTokensIsExceeded && accessTokenNotFoundById) {
            AuthTokenEntity tokenForDelete = authTokens.stream()
                    .min(Comparator.comparing(AuthTokenEntity::getId))
                    .orElseThrow(NoSuchElementException::new);
            authTokenRepository.deleteById(tokenForDelete.getId());
        }
        AuthTokenEntity authTokenEntity = new AuthTokenEntity(tokenId, email, token);
        authTokenRepository.save(authTokenEntity);
    }

    /**
     * Removes all access tokens by email.
     *
     * @param email user email
     */
    public void removeAllAuthTokensByEmail(String email) {
        List<AuthTokenEntity> authTokens = authTokenRepository.findAllByEmail(email);
        authTokens.stream()
                .map(AuthTokenEntity::getId)
                .forEach(authTokenRepository::deleteById);
    }

    /**
     * Removes access token by id.
     *
     * @param id access token id
     */
    public void removeAuthTokenById(Long id) {
        authTokenRepository.deleteById(id);
    }

    /**
     * Receives access token by token id and validate it.
     *
     * @param token old access token. Required to get token id
     * @return token id
     * @throws BaseKillerException if old access token is not valid
     */
    public Long getIdByToken(String token) {
        AuthTokenEntity authToken = authTokenRepository.findByToken(token);
        if (Objects.isNull(authToken)) {
            throw new BaseKillerException(String.format("Access token:%s, is not valid", token));
        }
        return authToken.getId();
    }

}

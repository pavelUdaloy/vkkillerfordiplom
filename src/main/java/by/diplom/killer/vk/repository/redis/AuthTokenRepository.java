package by.diplom.killer.vk.repository.redis;

import by.diplom.killer.vk.entity.token.AuthTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthTokenRepository extends CrudRepository<AuthTokenEntity, Long> {

    List<AuthTokenEntity> findAllByEmail(String email);
    AuthTokenEntity findByToken(String token);
}

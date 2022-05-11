package by.diplom.killer.vk.repository;

import by.diplom.killer.vk.entity.token.RefreshTokenEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    List<RefreshTokenEntity> findAllByUserId(Long userId);

    RefreshTokenEntity findByToken(String token);

    void deleteByUserId(Long userId);

    @Modifying
    @Query(value = "delete from refresh_jwt_tokens where id=(" +
            "select id from refresh_jwt_tokens where user_id=:userId order by id asc limit 1)",
            nativeQuery = true)
    void deleteFirstByUserId(@Param("userId") Long userId);
}

package by.diplom.killer.vk.entity.token;

import by.diplom.killer.vk.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "refresh_jwt_tokens")
@NoArgsConstructor
@DynamicInsert
@AllArgsConstructor
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_jwt_token_generator")
    @SequenceGenerator(name = "refresh_jwt_token_generator", sequenceName = "refresh_jwt_tokens_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private String token;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public RefreshTokenEntity(String token, User user) {
        this.token = token;
        this.user = user;
    }
}

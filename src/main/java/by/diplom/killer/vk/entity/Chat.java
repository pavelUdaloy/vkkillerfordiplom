package by.diplom.killer.vk.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_generator")
    @SequenceGenerator(name = "chat_generator", sequenceName = "chats_id_seq", allocationSize = 1)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "chats")
    private Set<User> users;
}

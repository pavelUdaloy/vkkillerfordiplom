package by.diplom.killer.vk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
    @SequenceGenerator(name = "tag_generator", sequenceName = "tags_id_seq", allocationSize = 1)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "blockedTags")
    private Set<User> usersThatBlockedContentByThisTag;

    @ManyToMany(mappedBy = "tags")
    private Set<Group> groupsThatExistsContentWithThisTag;
}

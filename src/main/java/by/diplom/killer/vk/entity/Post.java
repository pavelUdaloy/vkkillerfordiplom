package by.diplom.killer.vk.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "posts")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_generator")
    @SequenceGenerator(name = "post_generator", sequenceName = "posts_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String text;
    private LocalDate publicationDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}

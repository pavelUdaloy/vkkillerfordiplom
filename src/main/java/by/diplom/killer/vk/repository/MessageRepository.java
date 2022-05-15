package by.diplom.killer.vk.repository;

import by.diplom.killer.vk.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT FROM messages m WHERE m.chat_id=:chatId ORDER BY m.publication_date DESC",
            nativeQuery = true)
    Page<Message> findAllByChatIdAndOrderByPublicationDateDesc(Long chatId, Pageable pageable);
    // todo походу это неправильное решение, нет времени править
//    @EntityGraph(value = "Message.user")
//    Page<Message> findAllByChatIdAndOrderByPublicationDateDesc(Long chatId, Pageable pageable);
}

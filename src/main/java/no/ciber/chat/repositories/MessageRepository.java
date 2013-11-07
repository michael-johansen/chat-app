package no.ciber.chat.repositories;

import no.ciber.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Michael Johansen
 * Date: 07.11.13
 * Time: 15:07
 */
public interface MessageRepository extends JpaRepository<Message,Long> {
}

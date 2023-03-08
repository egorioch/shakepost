package shakepost.shake.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import shakepost.shake.domain.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {

}

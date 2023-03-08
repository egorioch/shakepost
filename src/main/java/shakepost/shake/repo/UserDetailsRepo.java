package shakepost.shake.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import shakepost.shake.domain.User;

public interface UserDetailsRepo extends JpaRepository<User, String> {

}

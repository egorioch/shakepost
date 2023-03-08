package shakepost.shake.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import shakepost.shake.domain.User;

public class UserDetailsRepo extends JpaRepository<User, String> {
}

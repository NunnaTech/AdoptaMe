package mx.com.adoptame.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmailAndIsActive(String email, Boolean isActive);
    Optional<User> findByLinkRestorePassword(String token);

}

package mx.com.adoptame.entities.user;

import mx.com.adoptame.entities.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUsernameAndEnabled(String username, Boolean enabled);
    Optional<User> findByLinkRestorePassword(String token);

    @Query(
            value = "select COUNT(u.id_user) from users u join authorities a on u.id_user = a.user_id  join roles r on a.rol_id = r.id_rol where u.enabled= 1 and r.authority = 'ROL_VOLUNTEER'",
            nativeQuery = true)
    Long countVolunteers();
    @Query(
            value = "select COUNT(u.id_user) from users u join authorities a on u.id_user = a.user_id  join roles r on a.rol_id = r.id_rol where u.enabled= 1 and r.authority = 'ROL_ADOPTER'",
            nativeQuery = true)
    Long countAdopteds();
}

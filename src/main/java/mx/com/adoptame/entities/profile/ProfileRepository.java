package mx.com.adoptame.entities.profile;

import mx.com.adoptame.entities.address.Address;
import mx.com.adoptame.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUser(User user);
    Optional<Profile> findByAddress(Address address);
}

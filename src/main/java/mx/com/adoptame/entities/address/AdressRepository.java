package mx.com.adoptame.entities.address;

import mx.com.adoptame.entities.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdressRepository extends JpaRepository<Address,Integer> {
    Optional<Address> findByProfile(Profile profile);
}

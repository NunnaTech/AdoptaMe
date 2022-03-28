package mx.com.adoptame.entities.pet.repositories;

import mx.com.adoptame.entities.pet.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet,Integer> {
    List<Pet> findAllByIsActive(Boolean isActive);
}

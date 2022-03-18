package mx.com.adoptame.entities.pet.repositories;

import mx.com.adoptame.entities.pet.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet,Integer> {
}

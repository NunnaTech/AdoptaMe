package mx.com.adoptame.entities.pet.repositories;

import mx.com.adoptame.entities.pet.entities.PetAdopted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetAdoptedRepository extends JpaRepository<PetAdopted, Integer> {
}

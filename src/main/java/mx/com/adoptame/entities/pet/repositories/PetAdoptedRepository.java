package mx.com.adoptame.entities.pet.repositories;

import mx.com.adoptame.entities.pet.entities.PetAdopted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetAdoptedRepository extends JpaRepository<PetAdopted, Integer> {
    List<PetAdopted> findAllByIsCanceled(Boolean isCanceled);
    List<PetAdopted> findByUserId(Integer username);
}

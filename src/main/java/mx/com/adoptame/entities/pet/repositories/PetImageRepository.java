package mx.com.adoptame.entities.pet.repositories;

import mx.com.adoptame.entities.pet.entities.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetImageRepository extends JpaRepository<PetImage, Integer> {
}

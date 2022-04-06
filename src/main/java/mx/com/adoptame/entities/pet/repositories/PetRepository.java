package mx.com.adoptame.entities.pet.repositories;

import mx.com.adoptame.entities.pet.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet,Integer> {
    List<Pet> findAllByIsActive(Boolean isActive);

    @Query(value = "SELECT * FROM tbl_pets p WHERE p.is_active = 1 AND p.is_adopted = 0 ORDER BY created_at DESC LIMIT 3;",
            nativeQuery = true)
    List<Pet> findLastThreePets();

    @Query(value = "SELECT * FROM tbl_pets p WHERE p.is_active = 1 AND p.is_adopted = 0;",
            nativeQuery = true)
    List<Pet> findPetsForAdopted();

    Integer countByIsActive(Boolean isActive);
    
    Integer countByIsAdopted(Boolean isAdopted);

    @Query(
            value = "SELECT * FROM tbl_pets p WHERE p.is_active = 1 ORDER BY p.created_at LIMIT 5 ",
            nativeQuery = true)
    List<Pet> findTop5ByCreatedAtDesc();

}

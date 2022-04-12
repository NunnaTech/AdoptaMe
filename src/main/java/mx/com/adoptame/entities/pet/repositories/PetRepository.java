package mx.com.adoptame.entities.pet.repositories;

import mx.com.adoptame.entities.character.Character;
import mx.com.adoptame.entities.color.Color;
import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.size.Size;
import mx.com.adoptame.entities.type.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet,Integer> {
    List<Pet> findAllByIsActiveAndIsDroppedFalse(Boolean isActive);

    @Query(value = "SELECT * FROM tbl_pets p WHERE p.is_active = 1 AND p.is_adopted = 0 AND p.is_dropped = 0 ORDER BY created_at DESC LIMIT 3;",
            nativeQuery = true)
    List<Pet> findLastThreePets();

    @Query(value = "SELECT * FROM tbl_pets p WHERE p.is_active = 1 AND p.is_adopted = 0 AND p.is_dropped = 0;",
            nativeQuery = true)
    List<Pet> findPetsForAdopted();

    Integer countByIsActiveAndIsDroppedFalse(Boolean isActive);
    
    Integer countByIsAdoptedAndIsDroppedFalse(Boolean isAdopted);

    @Query(value = "SELECT * FROM tbl_pets p WHERE p.is_active = 1 AND p.is_dropped = 0 ORDER BY p.created_at LIMIT 5 ",
            nativeQuery = true)
    List<Pet> findTop5ByCreatedAtDesc();

    List<Pet> findAllByNameContainingOrBreedContainingAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(String name, String breed);

    List<Pet> findByTypeInAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(Collection<Type> types);
    List<Pet> findBySizeInAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(Collection<Size> sizes);
    List<Pet> findByCharacterInAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(Collection<Character> characters);
    List<Pet> findByColorInAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(Collection<Color> colors);

    List<Pet> findByAgeInAndIsActiveTrueAndIsAdoptedFalseAndIsDroppedFalse(Collection<String> age);
}

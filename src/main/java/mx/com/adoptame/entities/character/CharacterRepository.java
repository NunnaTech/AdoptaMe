package mx.com.adoptame.entities.character;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends CrudRepository<Character,Integer> {
    List<Character> findAllByStatus(Boolean status);
    Optional<Character> findByName(String name);
}

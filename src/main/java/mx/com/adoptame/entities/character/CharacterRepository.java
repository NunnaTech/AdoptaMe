package mx.com.adoptame.entities.character;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends CrudRepository<Character,Integer> {
    List<Character> findAllByStatus(Boolean status);
    
}

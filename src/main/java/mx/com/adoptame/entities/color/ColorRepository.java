package mx.com.adoptame.entities.color;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends CrudRepository<Color, Integer> {
    List<Color> findAllByStatus(Boolean status);
}

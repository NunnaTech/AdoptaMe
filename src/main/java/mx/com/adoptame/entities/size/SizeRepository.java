package mx.com.adoptame.entities.size;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends CrudRepository<Size,Integer> {
    List<Size> findAllByStatus(Boolean status);
    Optional<Size> findByName(String name);
}

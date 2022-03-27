package mx.com.adoptame.entities.size;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends CrudRepository<Size,Integer> {
    List<Size> findAllByStatus(Boolean status);
}

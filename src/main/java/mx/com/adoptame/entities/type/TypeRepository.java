package mx.com.adoptame.entities.type;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends CrudRepository<Type, Integer> {
    List<Type> findAllByStatus(Boolean status);
}

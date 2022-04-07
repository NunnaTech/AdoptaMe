package mx.com.adoptame.entities.type;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends CrudRepository<Type, Integer> {
    List<Type> findAllByStatus(Boolean status);
    Optional<Type> findByName(String name);
}

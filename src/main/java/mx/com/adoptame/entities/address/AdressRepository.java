package mx.com.adoptame.entities.address;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends CrudRepository<Address,Integer> {
}

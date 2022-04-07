package mx.com.adoptame.entities.request;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request,Integer> {
    List<Request> findAllByIsAccepted(Boolean isAccepted);
    Integer countByIsAcceptedIsFalse();
}

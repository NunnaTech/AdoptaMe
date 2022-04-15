package mx.com.adoptame.entities.request;

import mx.com.adoptame.entities.log.LogService;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private LogService logService;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional(readOnly = true)
    public List<Request> findAll() {
        return  requestRepository.findAllByIsCanceledAndIsAcceptedFalse(false);
    }
    @Transactional(readOnly = true)
    public List<Request> findAllCanceled() {
        return  requestRepository.findAllByIsCanceledAndIsAcceptedFalse(true);
    }

    @Transactional(readOnly = true)
    public Optional<Request> findOne(Integer id) {
        return requestRepository.findById(id);
    }
     @Transactional(readOnly = true)
    public Optional<Request> findByUser(User user) {
        return requestRepository.findByUser(user);
    }

    @Transactional
    public Optional<Request> save(Request entity) {
        return Optional.of(requestRepository.save(entity));
    }

    @Transactional
    public Optional<Request> addRequest(String reason, User user) {
        entityManager.createNativeQuery("INSERT INTO tbl_requests (reason, user_id) VALUES (?,?);")
                .setParameter(1, reason)
                .setParameter(2, user)
                .executeUpdate();
        Optional<Request> savedRequest = findByUser(user);
        savedRequest.ifPresent(request ->
                logService.saveRequestLog("Crear",request, user));
        return savedRequest;
    }

    @Transactional
    public Optional<Request> update(Request entity) {
        Optional<Request> updatedEntity;
        updatedEntity = requestRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            requestRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Request accept(Integer id) {
        Optional<Request> entity = requestRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsAccepted(true);
            requestRepository.save(entity.get());

            return entity.get();
        }
        return null;
    }
    @Transactional
    public Boolean delete(Integer id) {
        Optional<Request> entity = requestRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsCanceled(true);
            requestRepository.save(entity.get());
            return true;
        }
        return false;
    }
    @Transactional(readOnly = true)
    public Integer findPending() {
        return requestRepository.countByIsAcceptedIsFalse();
    }

}

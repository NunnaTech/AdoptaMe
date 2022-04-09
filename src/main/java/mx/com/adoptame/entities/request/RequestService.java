package mx.com.adoptame.entities.request;


import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional(readOnly = true)
    public List<Request> findAll() {
        return  requestRepository.findAllByIsAccepted(false);
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
        return findByUser(user);
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
    public Optional<Request> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Request entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Request> updatedEntity;
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Request.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            requestRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    @Transactional
    public Boolean accept(Integer id) {
        Optional<Request> entity = requestRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsAccepted(true);
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

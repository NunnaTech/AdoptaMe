package mx.com.adoptame.entities.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    public List<Request> findAll() {
        return (List<Request>) requestRepository.findAll();
    }

    public Optional<Request> findOne(Integer id) {
        return requestRepository.findById(id);
    }

    public Optional<Request> save(Request entity) {
        return Optional.of(requestRepository.save(entity));
    }

    public Optional<Request> update(Request entity) {
        Optional<Request> updatedEntity = Optional.empty();
        updatedEntity = requestRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            requestRepository.save(entity);
        return updatedEntity;
    }

    public Optional<Request> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Request entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Request> updatedEntity = Optional.empty();
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

    public Boolean delete(Integer id) {
        boolean entity = requestRepository.existsById(id);
        if (entity) {
            requestRepository.deleteById(id);
        }
        return entity;
    }
}

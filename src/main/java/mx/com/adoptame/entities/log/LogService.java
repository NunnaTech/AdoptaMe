package mx.com.adoptame.entities.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    @Transactional(readOnly = true)
    public List<Log> findAll() {
        return (List<Log>) logRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Log> findOne(Integer id) {
        return logRepository.findById(id);
    }

    @Transactional
    public Optional<Log> save(Log entity) {
        return Optional.of(logRepository.save(entity));
    }

    @Transactional
    public Optional<Log> update(Log entity) {
        Optional<Log> updatedEntity = Optional.empty();
        updatedEntity = logRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            logRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Optional<Log> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Log entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Log> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Log.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            logRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = logRepository.existsById(id);
        if (entity) {
            logRepository.deleteById(id);
        }
        return entity;
    }    
}

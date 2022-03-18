package mx.com.adoptame.entities.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ActionService {
    @Autowired
    private ActionRepository actionRepository;

    public List<Action> findAll() {
        return (List<Action>) actionRepository.findAll();
    }

    public Optional<Action> findOne(Integer id) {
        return actionRepository.findById(id);
    }

    public Optional<Action> save(Action entity) {
        return Optional.of(actionRepository.save(entity));
    }

    public Optional<Action> update(Action entity) {
        Optional<Action> updatedEntity = Optional.empty();
        updatedEntity = actionRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            actionRepository.save(entity);
        return updatedEntity;
    }

    public Optional<Action> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Action entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Action> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Action.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            actionRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean delete(Integer id) {
        boolean entity = actionRepository.existsById(id);
        if (entity) {
            actionRepository.deleteById(id);
        }
        return entity;
    }
}

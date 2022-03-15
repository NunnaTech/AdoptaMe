package mx.com.adoptame.entities.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public List<Type> findAll() {
        return (List<Type>) typeRepository.findAll();
    }

    public Optional<Type> findOne(Integer id) {
        return typeRepository.findById(id);
    }

    public Optional<Type> save(Type entity) {
        return Optional.of(typeRepository.save(entity));
    }

    public Optional<Type> update(Type entity) {
        Optional<Type> updatedEntity = Optional.empty();
        updatedEntity = typeRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            typeRepository.save(entity);
        return updatedEntity;
    }

    public Optional<Type> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Type entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Type> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Type.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            typeRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean delete(Integer id) {
        boolean entity = typeRepository.existsById(id);
        if (entity) {
            typeRepository.deleteById(id);
        }
        return entity;
    }
}

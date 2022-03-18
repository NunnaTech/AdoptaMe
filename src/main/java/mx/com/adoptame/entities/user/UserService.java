package mx.com.adoptame.entities.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> findOne(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> save(User entity) {
        return Optional.of(userRepository.save(entity));
    }

    public Optional<User> update(User entity) {
        Optional<User> updatedEntity = Optional.empty();
        updatedEntity = userRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            userRepository.save(entity);
        return updatedEntity;
    }

    public Optional<User> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            User entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<User> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(User.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            userRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean delete(Integer id) {
        boolean entity = userRepository.existsById(id);
        if (entity) {
            userRepository.deleteById(id);
        }
        return entity;
    }
}

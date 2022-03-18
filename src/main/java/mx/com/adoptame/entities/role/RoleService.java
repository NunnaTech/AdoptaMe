package mx.com.adoptame.entities.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    public Optional<Role> findOne(Integer id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> save(Role entity) {
        return Optional.of(roleRepository.save(entity));
    }

    public Optional<Role> update(Role entity) {
        Optional<Role> updatedEntity = Optional.empty();
        updatedEntity = roleRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            roleRepository.save(entity);
        return updatedEntity;
    }

    public Optional<Role> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Role entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Role> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Role.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            roleRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean delete(Integer id) {
        boolean entity = roleRepository.existsById(id);
        if (entity) {
            roleRepository.deleteById(id);
        }
        return entity;
    }
}

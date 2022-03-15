package mx.com.adoptame.entities.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> findAll() {
        return (List<Profile>) profileRepository.findAll();
    }

    public Optional<Profile> findOne(Integer id) {
        return profileRepository.findById(id);
    }

    public Optional<Profile> save(Profile entity) {
        return Optional.of(profileRepository.save(entity));
    }

    public Optional<Profile> update(Profile entity) {
        Optional<Profile> updatedEntity = Optional.empty();
        updatedEntity = profileRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            profileRepository.save(entity);
        return updatedEntity;
    }

    public Optional<Profile> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Profile entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Profile> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Profile.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            profileRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean delete(Integer id) {
        boolean entity = profileRepository.existsById(id);
        if (entity) {
            profileRepository.deleteById(id);
        }
        return entity;
    }
}

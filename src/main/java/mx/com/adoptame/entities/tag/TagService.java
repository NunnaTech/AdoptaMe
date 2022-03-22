package mx.com.adoptame.entities.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> findAll() {
        return (List<Tag>) tagRepository.findAll();
    }

    public Optional<Tag> findOne(Integer id) {
        return tagRepository.findById(id);
    }

    public Optional<Tag> save(Tag entity) {
        return Optional.of(tagRepository.save(entity));
    }

    public Optional<Tag> update(Tag entity) {
        Optional<Tag> updatedEntity = Optional.empty();
        updatedEntity = tagRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            tagRepository.save(entity);
        return updatedEntity;
    }

    public Optional<Tag> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Tag entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Tag> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Tag.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            tagRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean delete(Integer id) {
        boolean entity = tagRepository.existsById(id);
        if (entity) {
            tagRepository.deleteById(id);
        }
        return entity;
    }
}
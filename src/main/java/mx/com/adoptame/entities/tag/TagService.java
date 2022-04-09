package mx.com.adoptame.entities.tag;

import mx.com.adoptame.entities.color.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<Tag> findAll() {
        return (List<Tag>) tagRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tag> findOne(Integer id) {
        return tagRepository.findById(id);
    }

    @Transactional
    public Optional<Tag> save(Tag entity) {
        return Optional.of(tagRepository.save(entity));
    }

    @Transactional
    public Optional<Tag> update(Tag entity) {
        Optional<Tag> updatedEntity = Optional.empty();
        updatedEntity = tagRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            tagRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
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

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = tagRepository.existsById(id);
        if (entity) {
            tagRepository.deleteById(id);
        }
        return entity;
    }

    public void fillInitialData() {
        if (tagRepository.count() > 0) return;
        List<Tag> inicialColors = new ArrayList<>();
        inicialColors.add(new Tag("Mundo animal","Relacionado con lo que pasa a nuestro alrededor"));
        inicialColors.add(new Tag("Higiene","Mantener limpio a las mascotas"));
        inicialColors.add(new Tag("Cuidados","'Saber cuidar a las mascotas"));
        inicialColors.add(new Tag("Tratar a los perros ","Que hacer para proteger a las mascota"));
        inicialColors.add(new Tag("Alimentos","Recomendación de alimentos"));
        inicialColors.add(new Tag("Maltrato animal","Como actuar en caso de maltrato"));
        inicialColors.add(new Tag("Animales abandonados","Dar a conocer como muchos animales son abandonados"));
        inicialColors.add(new Tag("Campañas de adopción","Emplear métodos para que puedan adoptar"));
        inicialColors.add(new Tag("Salud animal","Cuidar la salud de nuestras mascotas"));
        inicialColors.add(new Tag("Campañas","Relacionado para saber que hacer con las mascotas"));
        tagRepository.saveAll(inicialColors);
    }
}

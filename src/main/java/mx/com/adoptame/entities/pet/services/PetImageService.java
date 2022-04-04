package mx.com.adoptame.entities.pet.services;


import mx.com.adoptame.entities.pet.entities.PetImage;
import mx.com.adoptame.entities.pet.repositories.PetImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PetImageService {
    @Autowired
    private PetImageRepository petImageRepository;

    @Transactional(readOnly = true)
    public List<PetImage> findAll() {
        return (List<PetImage>) petImageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<PetImage> findOne(Integer id) {
        return petImageRepository.findById(id);
    }

    @Transactional
    public Optional<PetImage> save(PetImage entity) {
        return Optional.of(petImageRepository.save(entity));
    }

    @Transactional
    public Optional<PetImage> update(PetImage entity) {
        Optional<PetImage> updatedEntity = Optional.empty();
        updatedEntity = petImageRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            petImageRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Optional<PetImage> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            PetImage entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<PetImage> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(PetImage.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            petImageRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = petImageRepository.existsById(id);
        if (entity) {
            petImageRepository.deleteById(id);
        }
        return entity;
    }
}

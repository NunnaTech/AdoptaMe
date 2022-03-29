package mx.com.adoptame.entities.pet.services;


import mx.com.adoptame.entities.pet.entities.PetAdopted;
import mx.com.adoptame.entities.pet.repositories.PetAdoptedRepository;
import mx.com.adoptame.entities.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PetAdoptedService {
    @Autowired
    private PetAdoptedRepository petAdoptedRepository;

    public List<PetAdopted> findAll() {
        return (List<PetAdopted>) petAdoptedRepository.findAllByIsCanceled(false);
    }

    public Optional<PetAdopted> findOne(Integer id) {
        return petAdoptedRepository.findById(id);
    }

    public Optional<PetAdopted> save(PetAdopted entity) {
        return Optional.of(petAdoptedRepository.save(entity));
    }

    public Optional<PetAdopted> update(PetAdopted entity) {
        Optional<PetAdopted> updatedEntity = Optional.empty();
        updatedEntity = petAdoptedRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            petAdoptedRepository.save(entity);
        return updatedEntity;
    }

    public Optional<PetAdopted> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            PetAdopted entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<PetAdopted> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(PetAdopted.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            petAdoptedRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean accept(Integer id) {
        Optional<PetAdopted> entity = petAdoptedRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsCanceled(true);
            petAdoptedRepository.save(entity.get());
            return true;
        }
        return false;
    }

    public Boolean delete(Integer id) {
        boolean entity = petAdoptedRepository.existsById(id);
        if (entity) {
            petAdoptedRepository.deleteById(id);
        }
        return entity;
    }
}

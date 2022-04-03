package mx.com.adoptame.entities.pet.services;

import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.pet.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<Pet> findAll() {return petRepository.findAllByIsActive(true);}

    public List<Pet> findAllisActiveFalse() {
        return  petRepository.findAllByIsActive(false);
    }

    public List<Pet> findLastThreePets(){return petRepository.findLastThreePets();}

    public Optional<Pet> findOne(Integer id) {
        return petRepository.findById(id);
    }

    public Optional<Pet> save(Pet entity) {
        return Optional.of(petRepository.save(entity));
    }

    public Optional<Pet> update(Pet entity) {
        Optional<Pet> updatedEntity = Optional.empty();
        updatedEntity = petRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            petRepository.save(entity);
        return updatedEntity;
    }

    public Optional<Pet> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Pet entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Pet> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Pet.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            petRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean accept(Integer id) {
        Optional<Pet> entity =petRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsActive(true);
            petRepository.save(entity.get());
            return true;
        }
        return false;
    }

    public Boolean delete(Integer id) {
        Optional<Pet> entity = petRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsActive(false);
            return true;
        }
        return false;
    }
}

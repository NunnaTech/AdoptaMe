package mx.com.adoptame.entities.pet.services;

import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.pet.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Transactional(readOnly = true)
    public List<Pet> findAll() {return petRepository.findAllByIsActive(true);}

    @Transactional(readOnly = true)
    public List<Pet> findAllisActiveFalse() {
        return  petRepository.findAllByIsActive(false);
    }
    @Transactional(readOnly = true)
    public Long countTotal() {
        return  petRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Pet> findLastThreePets(){return petRepository.findLastThreePets();}

    @Transactional(readOnly = true)
    public List<Pet> findPetsForAdopted(){return petRepository.findPetsForAdopted();}

    @Transactional
    public Optional<Pet> findOne(Integer id) {
        return petRepository.findById(id);
    }

    @Transactional
    public Optional<Pet> save(Pet entity) {
        return Optional.of(petRepository.save(entity));
    }

    @Transactional
    public Optional<Pet> update(Pet entity) {
        Optional<Pet> updatedEntity = Optional.empty();
        updatedEntity = petRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            petRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
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

    @Transactional
    public Boolean accept(Integer id) {
        Optional<Pet> entity =petRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsActive(true);
            petRepository.save(entity.get());
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean delete(Integer id) {
        Optional<Pet> entity = petRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsActive(false);
            return true;
        }
        return false;
    }
    @Transactional(readOnly = true)
    public Integer coutnByIsActive(Boolean flag) {
        return  petRepository.countByIsActive(flag);
    }
    @Transactional(readOnly = true)
    public Integer coutnByIsAdopted(Boolean flag) {
        return  petRepository.countByIsAdopted(flag);
    }

    @Transactional(readOnly = true)
    public List<Pet> findTopFive() {
        return  petRepository.findTop5ByCreatedAtDesc();
    }

}

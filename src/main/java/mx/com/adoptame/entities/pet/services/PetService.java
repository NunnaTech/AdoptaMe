package mx.com.adoptame.entities.pet.services;

import mx.com.adoptame.entities.character.Character;
import mx.com.adoptame.entities.character.CharacterService;
import mx.com.adoptame.entities.color.Color;
import mx.com.adoptame.entities.color.ColorService;
import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.pet.repositories.PetRepository;
import mx.com.adoptame.entities.size.Size;
import mx.com.adoptame.entities.size.SizeService;
import mx.com.adoptame.entities.type.Type;
import mx.com.adoptame.entities.type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private TypeService typeService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private CharacterService characterService;
    @Autowired
    private ColorService colorService;

    @Transactional(readOnly = true)
    public List<Pet> findAll() {
        return petRepository.findAllByIsActive(true);
    }

    @Transactional(readOnly = true)
    public List<Pet> findAllisActiveFalse() {
        return petRepository.findAllByIsActive(false);
    }

    @Transactional(readOnly = true)
    public Long countTotal() {
        return petRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Pet> findLastThreePets() {
        return petRepository.findLastThreePets();
    }

    @Transactional(readOnly = true)
    public List<Pet> findPetsForAdopted() {
        return petRepository.findPetsForAdopted();
    }

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
        Optional<Pet> entity = petRepository.findById(id);
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
        return petRepository.countByIsActive(flag);
    }

    @Transactional(readOnly = true)
    public Integer coutnByIsAdopted(Boolean flag) {
        return petRepository.countByIsAdopted(flag);
    }

    @Transactional(readOnly = true)
    public List<Pet> findTopFive() {
        return petRepository.findTop5ByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<Pet> findByNameOrBreed(String query) {
        return petRepository.findAllByNameContainingOrBreedContainingAndIsActiveTrueAndIsAdoptedFalse(query, query);
    }

    @Transactional(readOnly = true)
    public List<Pet> findByAge(String ages) {
        String[] agesName = ages.split(",");
        List<String> agesList = List.of(agesName);
        Collection<String> collection = new ArrayList(agesList);
        return petRepository.findByAgeInAndIsActiveTrueAndIsAdoptedFalse(collection);
    }

    @Transactional(readOnly = true)
    public List<Pet> findBySize(String sizes) {
        String[] sizesNames = sizes.split(",");
        List<Size> filterSizes = new ArrayList<>();
        for (String name : sizesNames) {
            Optional<Size> size = sizeService.findByName(name);
            size.ifPresent(filterSizes::add);
        }
        Collection<Size> collection = new ArrayList<>(filterSizes);
        return petRepository.findBySizeInAndIsActiveTrueAndIsAdoptedFalse(collection);
    }

    @Transactional(readOnly = true)
    public List<Pet> findByCharacters(String characters) {
        String[] charactersNames = characters.split(",");
        List<Character> filterCharacters = new ArrayList<>();
        for (String name : charactersNames) {
            Optional<Character> character = characterService.findByName(name);
            character.ifPresent(filterCharacters::add);
        }
        Collection<Character> collection = new ArrayList<>(filterCharacters);
        return petRepository.findByCharacterInAndIsActiveTrueAndIsAdoptedFalse(collection);
    }

    @Transactional(readOnly = true)
    public List<Pet> findByColor(String colors) {
        String[] colorsNames = colors.split(",");
        List<Color> filterColors = new ArrayList<>();
        for (String name : colorsNames) {
            Optional<Color> color = colorService.findByName(name);
            color.ifPresent(filterColors::add);
        }
        Collection<Color> collection = new ArrayList<>(filterColors);
        return petRepository.findByColorInAndIsActiveTrueAndIsAdoptedFalse(collection);
    }

    @Transactional(readOnly = true)
    public List<Pet> findByType(String types) {
        String[] typesName = types.split(",");
        List<Type> filterTypes = new ArrayList<>();
        for (String s : typesName) {
            Optional<Type> type = typeService.findByName(s);
            type.ifPresent(filterTypes::add);
        }
        Collection<Type> collection = new ArrayList<>(filterTypes);
        return petRepository.findByTypeInAndIsActiveTrueAndIsAdoptedFalse(collection);
    }

    public Boolean checkIsPresentInFavorites(Pet currentPet, List<Pet> userPetsFavorites) {
        boolean flag = false;
        for (Pet p : userPetsFavorites) {
            if (p.getId() == currentPet.getId()) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}

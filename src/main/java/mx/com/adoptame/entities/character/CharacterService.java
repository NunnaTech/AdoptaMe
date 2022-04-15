package mx.com.adoptame.entities.character;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mx.com.adoptame.entities.log.LogService;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private LogService logService;

    @Transactional(readOnly = true)
    public List<Character> findAll() {
        return characterRepository.findAllByStatus(true);
    }

    @Transactional(readOnly = true)
    public Optional<Character> findOne(Integer id) {
        return characterRepository.findById(id);
    }

    @Transactional
    public Optional<Character> save(Character character, User user) {
        var action = "Actualizar";
        if (character.getId() == null) {
            action = "Crear";
        }
        logService.saveCharacterLog(action, character, user);
        return Optional.of(characterRepository.save(character));
    }

    @Transactional(readOnly = true)
    public Optional<Character> findByName(String name) {
        return characterRepository.findByName(name);
    }

    @Transactional
    public Optional<Character> update(Character entity) {
        Optional<Character> updatedEntity;
        updatedEntity = characterRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            characterRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean delete(Integer id, User user) {
        Optional<Character> entity = characterRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setStatus(false);
            logService.saveCharacterLog("Eliminar", entity.get(), user);
            characterRepository.save(entity.get());
            return true;
        }
        return false;
    }

    public void fillInitialData() {
        if (characterRepository.count() > 0) return;
        List<Character> inicial = new ArrayList<>();
        inicial.add(new Character("Activo"));
        inicial.add(new Character("Independiente"));
        inicial.add(new Character("Juguetón"));
        inicial.add(new Character("Protector"));
        inicial.add(new Character("Ruidoso"));
        inicial.add(new Character("Tímido"));
        inicial.add(new Character("Tranquilo"));
        inicial.add(new Character("Amoroso"));
        characterRepository.saveAll(inicial);
    }

}

package mx.com.adoptame.entities.type;

import mx.com.adoptame.entities.log.LogService;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private LogService logService;

    @Transactional(readOnly = true)
    public List<Type> findAll() {
        return typeRepository.findAllByStatus(true);
    }

    @Transactional(readOnly = true)
    public Optional<Type> findOne(Integer id) {
        return typeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Type> findByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    public Optional<Type> save(Type entity, User user) {
        var action = "Actualizar";
        if (entity.getId() == null) {
            action = "Crear";
        }
        logService.saveTypeLog(action, entity, user);
        return Optional.of(typeRepository.save(entity));
    }

    @Transactional
    public Optional<Type> update(Type entity) {
        Optional<Type> updatedEntity;
        updatedEntity = typeRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            typeRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean delete(Integer id, User user) {
        Optional<Type> entity = typeRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setStatus(false);
            logService.saveTypeLog("Eliminar", entity.get(), user);
            typeRepository.save(entity.get());
            return true;
        }
        return false;
    }

    public void fillInitialData() {
        if (typeRepository.count() > 0) return;
        List<Type> types = new ArrayList<>();
        types.add(new Type("Perro", "El mejor amigo del hombre", true));
        types.add(new Type("Gato", "El animal con siete vidas", true));
        types.add(new Type("Tortuga", "Los animales que viven muchos años", true));
        types.add(new Type("Ave", "Los animales del aire", true));
        typeRepository.saveAll(types);
    }
}

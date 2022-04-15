package mx.com.adoptame.entities.tag;

import mx.com.adoptame.entities.log.LogService;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private LogService logService;

    @Transactional(readOnly = true)
    public List<Tag> findAll() {
        return (List<Tag>) tagRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tag> findOne(Integer id) {
        return tagRepository.findById(id);
    }

    @Transactional
    public Optional<Tag> save(Tag entity, User user) {
        var action = "Actualizar";
        if (entity.getId() == null) {
            action = "Crear";
        }
        logService.saveTagLog(action, entity, user);
        return Optional.of(tagRepository.save(entity));
    }

    @Transactional
    public Optional<Tag> update(Tag entity) {
        Optional<Tag> updatedEntity;
        updatedEntity = tagRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            tagRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean delete(Integer id, User user) {
        Optional<Tag> entity = tagRepository.findById(id);
        if (entity.isPresent()) {
            logService.saveTagLog("Eliminar", entity.get(), user);
            tagRepository.deleteById(id);
            return true;
        }
        return false;
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

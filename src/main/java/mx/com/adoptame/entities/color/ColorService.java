package mx.com.adoptame.entities.color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mx.com.adoptame.entities.log.LogService;
import mx.com.adoptame.entities.user.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorService {

    @Autowired private ColorRepository colorRepository;

    @Autowired private LogService logService;

    @Transactional(readOnly = true)
    public List<Color> findAll() {
        return colorRepository.findAllByStatus(true);
    }

    @Transactional(readOnly = true)
    public Optional<Color> findOne(Integer id) {
        return colorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Color> findByName(String name) {
        return colorRepository.findByName(name);
    }

    @Transactional
    public Optional<Color> save(Color color, User user) {
        String action = "Actualizar";
        if (color.getId() == null) {
            action = "Crear";
        }
        logService.saveColorLog(action, color, user);
        return Optional.of(colorRepository.save(color));
    }

    @Transactional
    public Optional<Color> update(Color entity) {
        Optional<Color> updatedEntity;
        updatedEntity = colorRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            colorRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean delete(Integer id, User user) {
        Optional<Color> entity = colorRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setStatus(false);
            colorRepository.save(entity.get());
            logService.saveColorLog("Eliminar", entity.get(), user);
            return true;
        }
        return false;
    }

    public void fillInitialData() {
        if (colorRepository.count() > 0) return;
        List<Color> inicialColors = new ArrayList<>();
        inicialColors.add(new Color("Naranja", "#f47a1f"));
        inicialColors.add(new Color("Rojo pastel", "#fe6666"));
        inicialColors.add(new Color("Verde pastel", "#56cc9d"));
        inicialColors.add(new Color("Azul pastel", "#57ccf2"));
        inicialColors.add(new Color("Morado pastel", "#c980ff"));
        inicialColors.add(new Color("Negro", "#292424"));
        inicialColors.add(new Color("Cremita", "#d2a80f"));
        inicialColors.add(new Color("Gris", "#3d3d3d"));
        inicialColors.add(new Color("Blanco", "#b3b3b3"));
        colorRepository.saveAll(inicialColors);
    }

}

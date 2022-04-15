package mx.com.adoptame.entities.size;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mx.com.adoptame.entities.log.LogService;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private LogService logService;

    @Transactional(readOnly = true)
    public List<Size> findAll() {
        return sizeRepository.findAllByStatus(true);
    }

    @Transactional(readOnly = true)
    public Optional<Size> findOne(Integer id) {
        return sizeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Size> findByName(String name) {
        return sizeRepository.findByName(name);
    }

    @Transactional
    public Optional<Size> save(Size entity, User user) {
        var action = "Actualizar";
        if (entity.getId() == null) {
            action = "Crear";
        }
        logService.saveSizeLog(action, entity, user);
        return Optional.of(sizeRepository.save(entity));
    }

    @Transactional
    public Optional<Size> update(Size entity) {
        Optional<Size> updatedEntity;
        updatedEntity = sizeRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            sizeRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean delete(Integer id, User user) {
        Optional<Size> entity = sizeRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setStatus(false);
            logService.saveSizeLog("Eliminar", entity.get(), user);
            sizeRepository.save(entity.get());
            return true;
        }
        return false;
    }

    public void fillInitialData() {
        if (sizeRepository.count() > 0) return;
        List<Size> inicial = new ArrayList<>();
        inicial.add(new Size("Pequeño"));
        inicial.add(new Size("Mediano"));
        inicial.add(new Size("Grande"));
        sizeRepository.saveAll(inicial);
    }

}

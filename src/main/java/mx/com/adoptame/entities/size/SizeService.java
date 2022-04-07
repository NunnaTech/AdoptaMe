package mx.com.adoptame.entities.size;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;

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
    public Optional<Size> save(Size size) {
        return Optional.of(sizeRepository.save(size));
    }

    @Transactional
    public Optional<Size> update(Size entity) {
        Optional<Size> updatedEntity = Optional.empty();
        updatedEntity = sizeRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            sizeRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Optional<Size> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Size entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Size> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Size.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            sizeRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    @Transactional
    public Boolean delete(Integer id) {
        Optional<Size> entity = sizeRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setStatus(false);
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

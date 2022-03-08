package mx.com.adoptame.entities.color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;
import org.springframework.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public List<Color> findAll() {
        return (List<Color>) colorRepository.findAll();
    }

    public Optional<Color> findOne(Integer id) {
        return colorRepository.findById(id);
    }

    public Optional<Color> save(Color color) {
        return Optional.of(colorRepository.save(color));
    }

    public Optional<Color> update(Color entity) {
        Optional<Color> updatedEntity = Optional.empty();
        updatedEntity = colorRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            colorRepository.save(entity);
        return updatedEntity;
    }

    public Optional<Color> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Color entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Color> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Color.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            colorRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Optional<Color> delete(Integer id) {
        Optional<Color> entity = findOne(id);
        if (entity.isPresent()) {
            colorRepository.delete(entity.get());
        }
        return entity;
    }

    public void fillInicialData() {
        if (colorRepository.count() > 0)
            return;

        List<Color> inicialColors = new ArrayList<>();
        inicialColors.add(new Color("Blanco", "#f1f1f1"));
        inicialColors.add(new Color("Café", "#A54E3C"));
        inicialColors.add(new Color("Gris", "#f1f1f1"));
        inicialColors.add(new Color("Negro", "#f1f1f1"));
        inicialColors.add(new Color("Atigrado", "#f1f1f1"));
        inicialColors.add(new Color("Bicolor", "#f1f1f1"));
        inicialColors.add(new Color("Con Manchas", "#f1f1f1"));
        inicialColors.add(new Color("Varios Colores", "#f1f1f1"));
        colorRepository.saveAll(inicialColors);
    }

}

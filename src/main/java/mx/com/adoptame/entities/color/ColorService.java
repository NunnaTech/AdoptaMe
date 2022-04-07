package mx.com.adoptame.entities.color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorService {
   @Autowired
   private ColorRepository colorRepository;

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
   public Optional<Color> save(Color color) {
       return Optional.of(colorRepository.save(color));
   }

    @Transactional
   public Optional<Color> update(Color entity) {
       Optional<Color> updatedEntity = Optional.empty();
       updatedEntity = colorRepository.findById(entity.getId());
       if (!updatedEntity.isEmpty())
           colorRepository.save(entity);
       return updatedEntity;
   }

    @Transactional
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

    @Transactional
    public Boolean delete(Integer id) {
        Optional<Color> entity = colorRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setStatus(false);
            colorRepository.save(entity.get());
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
       colorRepository.saveAll(inicialColors);
   }

}

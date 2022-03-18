package mx.com.adoptame.entities.size;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

@Service
public class SizeService {
   @Autowired
   private SizeRepository sizeRepository;

   public List<Size> findAll() {
       return (List<Size>) sizeRepository.findAll();
   }

   public Optional<Size> findOne(Integer id) {
       return sizeRepository.findById(id);
   }

   public Optional<Size> save(Size size) {
       return Optional.of(sizeRepository.save(size));
   }

   public Optional<Size> update(Size entity) {
       Optional<Size> updatedEntity = Optional.empty();
       updatedEntity = sizeRepository.findById(entity.getId());
       if (!updatedEntity.isEmpty())
           sizeRepository.save(entity);
       return updatedEntity;
   }

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

    public Boolean delete(Integer id) {
        boolean entity = sizeRepository.existsById(id);
        if (entity) {
            sizeRepository.deleteById(id);
        }
        return entity;
    }

   public void fillInicialData() {
       if (sizeRepository.count() > 0)
           return;

       List<Size> inicial = new ArrayList<>();
       inicial.add(new Size("Pequeño"));
       inicial.add(new Size("Mediano"));
       inicial.add(new Size("Grande"));
       sizeRepository.saveAll(inicial);
   }

}

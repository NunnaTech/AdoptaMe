package mx.com.adoptame.entities.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Profile> findAll() {return (List<Profile>) profileRepository.findAll();}

    @Transactional(readOnly = true)
    public Optional<Profile> findOne(Integer id) {
        return profileRepository.findById(id);
    }

    @Transactional
    public Optional<Profile> save(Profile entity) {
        if(entity.getUser().getId()==null){
            entity.getUser().setPassword(passwordEncoder.encode(entity.getUser().getPassword()));
        }
        return Optional.of(profileRepository.save(entity));
    }

    @Transactional
    public Optional<Profile> update(Profile entity) {
        Optional<Profile> updatedEntity = Optional.empty();
        updatedEntity = profileRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            profileRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Optional<Profile> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Profile entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Profile> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Profile.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            profileRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    @Transactional
    public Profile findAndSetPerfil(Profile entity){
        try{
            Optional<Profile> updateEntity = findOne(entity.getId());
            if (updateEntity.isPresent()){
                updateEntity.get().setName(entity.getName());
                updateEntity.get().setLastName(entity.getLastName());
                updateEntity.get().setSecondName(entity.getSecondName());
                updateEntity.get().setPhone(entity.getPhone());
                updateEntity.get().setImage(entity.getImage());
                updateEntity.get().getAddress().setStreet(entity.getAddress().getStreet());
                updateEntity.get().getAddress().setInternalNumber(entity.getAddress().getInternalNumber());
                updateEntity.get().getAddress().setExternalNumber(entity.getAddress().getExternalNumber());
                updateEntity.get().getAddress().setZipCode(entity.getAddress().getZipCode());
                updateEntity.get().getAddress().setReferences(entity.getAddress().getReferences());
            }
            return updateEntity.get();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = profileRepository.existsById(id);
        if (entity) {
            profileRepository.deleteById(id);
        }
        return entity;
    }
}

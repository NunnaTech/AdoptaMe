package mx.com.adoptame.entities.profile;

import mx.com.adoptame.entities.request.RequestController;
import mx.com.adoptame.entities.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Profile> findAll() {
        return profileRepository.findAllByUser_Enabled(true);
    }

    @Transactional(readOnly = true)
    public Optional<Profile> findOne(Integer id) {
        return profileRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Profile> findByUser(User user) {
        return profileRepository.findByUser(user);
    }

    @Transactional
    public Optional<Profile> save(Profile entity) {
        if (entity.getUser().getId() == null) {
            entity.getUser().setPassword(passwordEncoder.encode(entity.getUser().getPassword()));
        }
        return Optional.of(profileRepository.save(entity));
    }

    @Transactional
    public Optional<Profile> addProfile(Profile profile) {
        entityManager.createNativeQuery("INSERT INTO tbl_profiles (name,last_name,second_name,phone,address_id,user_id)VALUES (?,?,?,?,null,?);")
                .setParameter(1, profile.getName())
                .setParameter(2, profile.getLastName())
                .setParameter(3, profile.getSecondName())
                .setParameter(4, profile.getPhone())
                .setParameter(5, profile.getUser().getId())
                .executeUpdate();
        return findByUser(profile.getUser());
    }

    @Transactional
    public Optional<Profile> update(Profile entity) {
        Optional<Profile> updatedEntity;
        updatedEntity = profileRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            profileRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Profile findAndSetPerfil(Profile entity) {
        try {
            Optional<Profile> updateEntity = findOne(entity.getId());
            if (updateEntity.isPresent()) {
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
                return updateEntity.get();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
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

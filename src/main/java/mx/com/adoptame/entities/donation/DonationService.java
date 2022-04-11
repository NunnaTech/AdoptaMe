package mx.com.adoptame.entities.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;

    @Transactional(readOnly = true)
    public List<Donation> findAll() {
        return (List<Donation>) donationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Donation> findOne(Integer id) {
        return donationRepository.findById(id);
    }

    @Transactional
    public Optional<Donation> save(Donation entity) {
        return Optional.of(donationRepository.save(entity));
    }

    @Transactional
    public Optional<Donation> update(Donation entity) {
        Optional<Donation> updatedEntity;
        updatedEntity = donationRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            donationRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Optional<Donation> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Donation entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Donation> updatedEntity;
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Donation.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            donationRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = donationRepository.existsById(id);
        if (entity) {
            donationRepository.deleteById(id);
        }
        return entity;
    }

    @Transactional(readOnly = true)
    public Double sumCuantity() {
        return donationRepository.sumCuantity();
    }
    @Transactional(readOnly = true)
    public List<Donation> findTop5() {
        return donationRepository.findTop5ByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Double sumCuantitybyUserId(Integer username) {
        return donationRepository.sumCuantityByUserId(username);
    }
}

package mx.com.adoptame.entities.donation;

import mx.com.adoptame.entities.log.LogService;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;

    @Autowired private LogService logService;

    @Transactional(readOnly = true)
    public List<Donation> findAll() {
        return (List<Donation>) donationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Donation> findOne(Integer id) {
        return donationRepository.findById(id);
    }

    @Transactional
    public Optional<Donation> save(Donation entity, User user) {
        var action = "Actualizar";
        if (entity.getId() == null) {
            action = "Crear";
        }
        logService.saveDonationLog(action, entity, user);
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

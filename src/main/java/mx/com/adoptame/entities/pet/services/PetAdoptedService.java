package mx.com.adoptame.entities.pet.services;

import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.pet.entities.PetAdopted;
import mx.com.adoptame.entities.pet.repositories.PetAdoptedRepository;

import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PetAdoptedService {

    @Autowired
    private PetAdoptedRepository petAdoptedRepository;

    @Transactional(readOnly = true)
    public List<PetAdopted> findAll() {
        return petAdoptedRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Integer countByUserRequestAccepted(Integer id){
        return petAdoptedRepository.countByUserIdAndIsAcceptedIsTrueAndAndIsCanceledIsFalse(id);
    }

    @Transactional(readOnly = true)
    public Integer countByUserRequestCanceled(Integer id){
        return petAdoptedRepository.countByUserIdAndIsAcceptedIsFalseAndAndIsCanceledIsTrue(id);
    }

    @Transactional(readOnly = true)
    public Integer countByUserRequestPending(Integer id){
        return petAdoptedRepository.countByUserIdAndIsAcceptedIsFalseAndAndIsCanceledIsFalse(id);
    }

    @Transactional(readOnly = true)
    public Optional<PetAdopted> findOne(Integer id) {
        return petAdoptedRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<PetAdopted> findUsername(Integer id) {
        return petAdoptedRepository.findByUserId(id);
    }

    @Transactional
    public Optional<PetAdopted> save(PetAdopted entity) {
        return Optional.of(petAdoptedRepository.save(entity));
    }

    @Transactional
    public Optional<PetAdopted> update(PetAdopted entity) {
        Optional<PetAdopted> updatedEntity;
        updatedEntity = petAdoptedRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            petAdoptedRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean accept(Integer id) {
        Optional<PetAdopted> entity = petAdoptedRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsAccepted(true);
            entity.get().setIsCanceled(false);
            entity.get().getPet().setIsAdopted(true);
            petAdoptedRepository.save(entity.get());
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean cancel(Integer id) {
        Optional<PetAdopted> entity = petAdoptedRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setIsCanceled(true);
            entity.get().setIsAccepted(false);
            petAdoptedRepository.save(entity.get());
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = petAdoptedRepository.existsById(id);
        if (entity) {
            petAdoptedRepository.deleteById(id);
        }
        return entity;
    }

    @Transactional()
    public Boolean checkIsPresentInAdoptions(Pet currentPet, User currentUser){
        boolean flag = false;
        List<PetAdopted> petUserAdopted = findUsername(currentUser.getId());
        for (PetAdopted p:petUserAdopted) {
            if(currentPet.getId().equals(p.getPet().getId())){
                flag = true;
                break;
            }
        }
        return flag;
    }
}

package mx.com.adoptame.entities.log;

import mx.com.adoptame.entities.address.Address;
import mx.com.adoptame.entities.character.Character;
import mx.com.adoptame.entities.color.Color;
import mx.com.adoptame.entities.donation.Donation;
import mx.com.adoptame.entities.news.News;
import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.pet.entities.PetAdopted;
import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.request.Request;
import mx.com.adoptame.entities.size.Size;
import mx.com.adoptame.entities.tag.Tag;
import mx.com.adoptame.entities.type.Type;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    @Transactional(readOnly = true)
    public List<Log> findAll() {
        return logRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Optional<Log> findOne(Integer id) {
        return logRepository.findById(id);
    }

    @Transactional
    public Optional<Log> save(Log entity) {
        return Optional.of(logRepository.save(entity));
    }

    @Transactional
    public void saveRequestLog(String action, Request request, User madeBy) {
        logRepository.logRequest(
                action,
                request.getId(),
                Boolean.TRUE.equals(request.getIsAccepted()) ? 1 : 0,
                request.getReason(),
                Boolean.TRUE.equals(request.getIsCanceled()) ? 1 : 0,
                madeBy.getId()
        );
    }

    @Transactional
    public void saveProfile(String action, Profile profile, User madeBy) {
        logRepository.logProfile(
                action,
                profile.getId(),
                profile.getUser().getUsername(),
                profile.getUser().getPassword(),
                Boolean.TRUE.equals(profile.getUser().getEnabled()) ? 1 : 0,
                profile.getName(),
                profile.getLastName(),
                profile.getSecondName(),
                profile.getPhone(),
                profile.getImage(),
                profile.getAddress().getExternalNumber(),
                profile.getAddress().getInternalNumber(),
                profile.getAddress().getStreet(),
                profile.getAddress().getZipCode(),
                profile.getAddress().getReferences(),
                madeBy.getId()
        );
    }

    @Transactional
    public void savePetAdoptedLog(String action, PetAdopted petAdopted, User madeBy) {
        logRepository.logPetsAdopted(
                action,
                petAdopted.getId(),
                Boolean.TRUE.equals(petAdopted.getIsAccepted()) ? 1 : 0,
                Boolean.TRUE.equals(petAdopted.getIsCanceled()) ? 1 : 0,
                madeBy.getId()
        );
    }

    @Transactional
    public void saveColorLog(String action, Color color, User madeBy) {
        logRepository.logColor(
                action,
                color.getId(),
                color.getHex_code(),
                color.getName(),
                Boolean.TRUE.equals(color.getStatus())? 1 : 0,
                madeBy.getId()
        );
    }

    @Transactional
    public void saveTypeLog(String action, Type type, User madeBy) {
        logRepository.logType(
                action,
                type.getId(),
                type.getName(),
                type.getDescription(),
                Boolean.TRUE.equals(type.getStatus()) ? 1 : 0,
                madeBy.getId()
        );
    }

    @Transactional
    public void saveDonationLog(String action, Donation donation, User madeBy) {
        logRepository.logDonation(
                action,
                donation.getId(),
                donation.getAuthorization(),
                Boolean.TRUE.equals(donation.getIsCompleted())? 1 : 0,
                donation.getQuantity(),
                donation.getUser().getId(),
                madeBy.getId()
        );
    }

    @Transactional
    public void saveCharacterLog(String action, Character character, User madeBy) {
        logRepository.logCharacter(
                action,
                character.getId(),
                character.getDescription(),
                character.getName(),
                Boolean.TRUE.equals(character.getStatus()) ? 1 : 0,
                madeBy.getId()
        );
    }

    @Transactional
    public void saveSizeLog(String action, Size size, User madeBy) {
        logRepository.logSize(
                action,
                size.getId(),
                size.getName(),
                size.getRange(),
                Boolean.TRUE.equals(size.getStatus()) ? 1 : 0,
                madeBy.getId()
        );
    }

    @Transactional
    public void saveTagLog(String action, Tag tag, User madeBy) {
        logRepository.logTag(
                action,
                tag.getId(),
                tag.getName(),
                tag.getDescription(),
                madeBy.getId()
        );
    }

    @Transactional
    public void saveAddressLog(String action, Address address, User madeBy) {
        logRepository.logAddress(
                action,
                address.getId(),
                address.getExternalNumber(),
                address.getInternalNumber(),
                address.getReferences(),
                address.getStreet(),
                address.getZipCode(),
                madeBy.getId()
        );
    }

    @Transactional
    public void saveNewsLog(String action, News news, User madeBy) {
        logRepository.logNews(
                action,
                news.getId(),
                news.getContent(),
                news.getImage(),
                Boolean.TRUE.equals(news.getIsMain()) ? 1 : 0,
                Boolean.TRUE.equals(news.getIsPublished()) ? 1 : 0,
                news.getTitle(),
                news.getUser().getId(),
                madeBy.getId()
        );
    }

    @Transactional
    public void savePetLog(String action, Pet pet, User madeBy) {
        logRepository.logPets(
                action,
                pet.getId(),
                pet.getName(),
                pet.getAge(),
                pet.getBreed(),
                pet.getDescription(),
                Boolean.TRUE.equals(pet.getGender()) ? 1 : 0,
                Boolean.TRUE.equals(pet.getIsActive()) ? 1 : 0,
                Boolean.TRUE.equals(pet.getIsAdopted()) ? 1 : 0,
                Boolean.TRUE.equals(pet.getIsDropped()) ? 1 : 0,
                pet.getCharacter().getId(),
                pet.getColor().getId(),
                pet.getSize().getId(),
                pet.getType().getId(),
                pet.getUser().getId(),
                madeBy.getId()
        );
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = logRepository.existsById(id);
        if (entity) {
            logRepository.deleteById(id);
        }
        return entity;
    }
}

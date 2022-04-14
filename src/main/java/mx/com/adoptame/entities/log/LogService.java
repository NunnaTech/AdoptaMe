package mx.com.adoptame.entities.log;

import mx.com.adoptame.entities.address.Address;
import mx.com.adoptame.entities.character.Character;
import mx.com.adoptame.entities.color.Color;
import mx.com.adoptame.entities.donation.Donation;
import mx.com.adoptame.entities.news.News;
import mx.com.adoptame.entities.pet.entities.Pet;
import mx.com.adoptame.entities.size.Size;
import mx.com.adoptame.entities.tag.Tag;
import mx.com.adoptame.entities.type.Type;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        return (List<Log>) logRepository.findAll();
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
    public void saveUserLog(String action, User user, User madeBy) {
        logRepository.logUser(
                action,
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEnabled() == true ? 1 : 0,
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
                color.getStatus() == true ? 1 : 0,
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
                type.getStatus() == true ? 1 : 0,
                madeBy.getId()
        );
    }

    @Transactional
    public void saveDonationLog(String action, Donation donation, User madeBy) {
        logRepository.logDonation(
                action,
                donation.getId(),
                donation.getAuthorization(),
                donation.getIsCompleted() == true ? 1 : 0,
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
                character.getStatus() == true ? 1 : 0,
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
                size.getStatus() == true ? 1 : 0,
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
                news.getIsMain() == true ? 1 : 0,
                news.getIsPublished() == true ? 1 : 0,
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
                pet.getGender() == true ? 1 : 0,
                pet.getIsActive() == true ? 1 : 0,
                pet.getIsAdopted() == true ? 1 : 0,
                pet.getIsDropped() == true ? 1 : 0,
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

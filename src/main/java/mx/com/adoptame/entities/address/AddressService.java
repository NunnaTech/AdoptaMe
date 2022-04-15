package mx.com.adoptame.entities.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AdressRepository addressRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Address> findAll() {
        return  addressRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Address> findOne(Integer id) {
        return addressRepository.findById(id);
    }

    @Transactional
    public Optional<Address> save(Address entity) {
        return Optional.of(addressRepository.save(entity));
    }

    @Transactional
    public Optional<Address> update(Address entity) {
        Optional<Address> updatedEntity;
        updatedEntity = addressRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            addressRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = addressRepository.existsById(id);
        if (entity) {
            addressRepository.deleteById(id);
        }
        return entity;
    }

    @Transactional
    public Optional<Address> addAddress() {
        entityManager.createNativeQuery("INSERT INTO tbl_address (external_number,street,zip_code)VALUES ('','','');")
                .executeUpdate();
        return addressRepository.findLastAddressAdded();
    }
}

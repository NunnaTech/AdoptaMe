package mx.com.adoptame.entities.address;

import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AdressRepository addressRepository;

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
}

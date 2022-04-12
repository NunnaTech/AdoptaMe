package mx.com.adoptame.entities.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Role> findOne(Integer id) {
        return roleRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Role> findByType(String type) {
        return roleRepository.findByAuthority(type);
    }

    @Transactional
    public Optional<Role> save(Role entity) {
        return Optional.of(roleRepository.save(entity));
    }

    @Transactional
    public Optional<Role> update(Role entity) {
        Optional<Role> updatedEntity;
        updatedEntity = roleRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            roleRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = roleRepository.existsById(id);
        if (entity) {
            roleRepository.deleteById(id);
        }
        return entity;
    }

    public void fillInitialData() {
        if (roleRepository.count() > 0) return;
        List<Role> inicial = new ArrayList<>();
        inicial.add(new Role("ROLE_ADMINISTRATOR"));
        inicial.add(new Role("ROLE_VOLUNTEER"));
        inicial.add(new Role("ROLE_ADOPTER"));
        roleRepository.saveAll(inicial);
    }
}

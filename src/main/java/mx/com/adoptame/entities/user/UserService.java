package mx.com.adoptame.entities.user;

import mx.com.adoptame.config.email.EmailService;
import mx.com.adoptame.entities.address.Address;
import mx.com.adoptame.entities.address.AdressRepository;
import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.profile.ProfileRepository;
import mx.com.adoptame.entities.role.Role;
import mx.com.adoptame.entities.role.RoleService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${deploy-host}")
    private String host;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findOne(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<User> save(User entity) {
        return Optional.of(userRepository.save(entity));
    }

    @Transactional
    public Optional<User> addUser(User user) {
        entityManager.createNativeQuery("INSERT INTO users (enabled, password, username) VALUES (?,?,?);")
                .setParameter(1, user.getEnabled())
                .setParameter(2, passwordEncoder.encode(user.getPassword()))
                .setParameter(3, user.getUsername())
                .executeUpdate();
        return findByEmailAnyCase(user.getUsername());
    }

    @Transactional
    public void addRole(User user, Role role) {
        entityManager.createNativeQuery("INSERT INTO authorities (user_id, rol_id) VALUES (?,?);")
                .setParameter(1, user.getId())
                .setParameter(2, role.getId())
                .executeUpdate();
    }

    @Transactional
    public Optional<User> saveWithoutPassword(User entity) {
        Optional<User> user = findOne(entity.getId());
        entity.setPassword(user.get().getPassword());
        return Optional.of(userRepository.save(entity));
    }

    @Transactional
    public Optional<User> savejustUser(User entity) {
        Optional<User> user = findOne(entity.getId());
        Optional<Profile> profile = profileRepository.findByUser(entity);
        Optional<Address> address = adressRepository.findByProfile(profile.get());
        profile.get().setAddress(address.get());

        entity.setProfile(profile.get());
        entity.setPassword(user.get().getPassword());
        entity.setPassword(user.get().getPassword());
        return Optional.of(userRepository.save(entity));
    }

    @Transactional
    public Optional<User> update(User entity) {
        Optional<User> updatedEntity = Optional.empty();
        updatedEntity = userRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            userRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Optional<User> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            User entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<User> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(User.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            userRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = userRepository.existsById(id);
        if (entity) {
            userRepository.deleteById(id);
        }
        return entity;
    }

    @Transactional
    public Boolean updatePassword(User user, String currentPassword, String newPassword, String repeatedPassword) {
        if (!passwordEncoder.matches(user.getPassword(), currentPassword)) return false;
        if (!newPassword.equals(repeatedPassword)) return false;
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    public void fillInitialData() {
        if (userRepository.count() > 0) return;
        List<Profile> profiles = new ArrayList<>();
        User superadmin = new User("super@adoptame.com", passwordEncoder.encode("admin"), Set.of(roleService.findByType("ROLE_ADMINISTRATOR").get()));
        Address address1 = new Address("Alvaró Obregon", "7", "1", "69855", "Casa del superadmin");
        Profile profile1 = new Profile("Alexis", "Álvarez", "Saldaña", "7778523699", superadmin, address1);
        User volunter = new User("volun@adoptame.com", passwordEncoder.encode("admin"), Set.of(roleService.findByType("ROLE_VOLUNTEER").get()));
        Address address2 = new Address("Avenida benito", "8", "7", "69858", "Casa del voluntario");
        Profile profile2 = new Profile("Luis", "Saldaña", "García", "7778523696", volunter, address2);
        User adopter = new User("adopt@adoptame.com", passwordEncoder.encode("admin"), Set.of(roleService.findByType("ROLE_ADOPTER").get()));
        Address address3 = new Address("Calle fresno", "9", "1", "69874", "Casa del adoptador");
        Profile profile3 = new Profile("Hector", "Ortiz", "Loya", "7778523698", adopter, address3);
        profiles.add(profile1);
        profiles.add(profile2);
        profiles.add(profile3);
        profileRepository.saveAll(profiles);
    }

    @Transactional
    public void sedEmail(String email, String path) {
        //We create a token
        String token = RandomString.make(100);
        token += LocalDateTime.now();
        //we try to send the email
        try {
            updateResetPasswordToken(token, email);
            String resetPasswordLink = host + "/user/link_restore_password?token=" + token;
            emailService.sendRecoverPasswordTemplate(email, resetPasswordLink);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * sets value for the field linkRestorePassword of
     * a user found by the given email – and persist
     * change to the database.
     */
    public void updateResetPasswordToken(String token, String email) {
        Optional<User> user = findByEmail(email);
        if (user.isPresent()) {
            user.get().setLinkRestorePassword(token);
            save(user.get());
        }
    }

    /**
     * sets new password for the user
     * (using BCrypt password encoding) and
     * nullifies the reset password token.
     */
    public Boolean updatePassword(String token, String newPassword, String repeatedPassword) {
        Optional<User> user = findByLinkRestorePassword(token);
        if (user.isEmpty()) return false;
        if (!checkTokenDate(token)) return false;
        if (!newPassword.equals(repeatedPassword)) return false;

        user.get().setPassword(passwordEncoder.encode(newPassword));
        user.get().setLinkRestorePassword(null);
        userRepository.save(user.get());
        return true;
    }

    /**
     * Check if the Token is already active
     */


    public Boolean checkTokenDate(String token) {
        try {
            LocalDateTime tokenDate = LocalDateTime.parse(token.substring(100, token.length()));
            long hours = ChronoUnit.HOURS.between(tokenDate, LocalDateTime.now());
            if (hours < 24) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Optional<User> findByLinkRestorePassword(String token) {
        return userRepository.findByLinkRestorePassword(token);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByUsernameAndEnabled(email, true);
    }
    @Transactional(readOnly = true)
    public Optional<User> findByEmailAnyCase(String email) {
        return userRepository.findByUsername(email);
    }

    @Transactional(readOnly = true)
    public Long countVolunteer() {
        return userRepository.countVolunteers();
    }

    @Transactional(readOnly = true)
    public Long countAdopter() {
        return userRepository.countAdopts();
    }

    @Transactional(readOnly = true)
    public Long countTotal() {
        return userRepository.count();
    }

    @Transactional(readOnly = true)
    public Long countTotal(Role role) {
        return userRepository.count();
    }

    public Boolean isAdmin(String username) {
        boolean flag = false;
        Optional<User> user = findByEmail(username);
        if (user.isPresent()) {
            for (Role r : user.get().getRoles()) {
                if (r.getAuthority().equals("ROLE_ADMINISTRATOR")) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public Boolean isAdopter(String username) {
        boolean flag = false;
        Optional<User> user = findByEmail(username);
        if (user.isPresent()) {
            for (Role r : user.get().getRoles()) {
                if (r.getAuthority().equals("ROLE_ADOPTER")) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}

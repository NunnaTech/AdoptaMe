package mx.com.adoptame.entities.user;

import mx.com.adoptame.config.email.EmailService;
import mx.com.adoptame.entities.address.Address;
import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.profile.ProfileRepository;
import mx.com.adoptame.entities.role.Role;
import mx.com.adoptame.entities.role.RoleService;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UserService {

    private static final String TEMPPASS = "admin";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

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
    public Optional<User> update(User entity) {
        Optional<User> updatedEntity;
        updatedEntity = userRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            userRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean delete(Integer id) {
        boolean entity = userRepository.existsById(id);
        if (entity) {
            userRepository.deleteById(id);
        }
        return entity;
    }

    @Transactional()
    public User recoveryPassword(User user) {
        if (user.getPassword().isEmpty()) {
            Optional<User> oldUser = findOne(user.getId());
            if (oldUser.isPresent()) {
                user.setPassword(oldUser.get().getPassword());
            }
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return (user);
    }

    @Transactional
    public Boolean updatePassword(User user, String currentPassword, String newPassword, String repeatedPassword) {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) return false;
        if (!newPassword.equals(repeatedPassword)) return false;
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    public void fillInitialData() {
        if (userRepository.count() > 0) return;
        List<Profile> profiles = new ArrayList<>();
        Optional<Role> admin = roleService.findByType("ROLE_ADMINISTRATOR");
        if (admin.isPresent()) {
            var superadmin = new User("super@adoptame.com", passwordEncoder.encode(TEMPPASS), Set.of(admin.get()));
            var address1 = new Address("Alvaró Obregon", "7", "1", "69855", "Casa del superadmin");
            var profile1 = new Profile("Alexis", "Álvarez", "Saldaña", "7778523699", superadmin, address1);
            profiles.add(profile1);
        }
        Optional<Role> volun = roleService.findByType("ROLE_VOLUNTEER");
        if (volun.isPresent()) {
            var volunter = new User("volun@adoptame.com", passwordEncoder.encode(TEMPPASS), Set.of(volun.get()));
            var address2 = new Address("Avenida benito", "8", "7", "69858", "Casa del voluntario");
            var profile2 = new Profile("Luis", "Saldaña", "García", "7778523696", volunter, address2);
            profiles.add(profile2);
        }
        Optional<Role> adop = roleService.findByType("ROLE_ADOPTER");
        if (adop.isPresent()) {
            var adopter = new User("adopt@adoptame.com", passwordEncoder.encode(TEMPPASS), Set.of(adop.get()));
            var address3 = new Address("Calle fresno", "9", "1", "69874", "Casa del adoptador");
            var profile3 = new Profile("Hector", "Ortiz", "Loya", "7778523698", adopter, address3);
            profiles.add(profile3);
        }
        profileRepository.saveAll(profiles);
    }

    @Transactional
    public Boolean sendForgotPasswordEmail(String email) {
        var token = RandomString.make(100);
        token += LocalDateTime.now();
        return updateResetPasswordToken(token, email);
    }

    @Transactional
    public void sendActivateEmail(User user) {
        var token = RandomString.make(100);
        token += LocalDateTime.now();
        try {
            user.setLinkActivateUsername(token);
            save(user);
            String resetPasswordLink = host + "/user/activate?token=" + token;
            emailService.sendRequestAcceptedTemplate(user, resetPasswordLink);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }


    @Transactional
    public Boolean updateResetPasswordToken(String token, String email) {
        Optional<User> user = findByEmail(email);
        if (user.isPresent()) {
            try {
                user.get().setLinkRestorePassword(token);
                save(user.get());
                String resetPasswordLink = host + "/user/link_restore_password?token=" + token;
                emailService.sendRecoverPasswordTemplate(email, resetPasswordLink);
                return true;
            } catch (MessagingException e) {
                logger.error(e.getMessage());
            }
        }
        return false;
    }

    @Transactional
    public Boolean updatePassword(String token, String newPassword, String repeatedPassword) {
        Optional<User> user = findByLinkRestorePassword(token);
        if (user.isEmpty()) return false;
        if (Boolean.FALSE.equals(checkTokenDate(token))) return false;
        if (!newPassword.equals(repeatedPassword)) return false;
        user.get().setPassword(passwordEncoder.encode(newPassword));
        user.get().setLinkRestorePassword(null);
        userRepository.save(user.get());
        return true;
    }

    public Boolean activateUser(User user) {
        user.setEnabled(true);
        user.setLinkActivateUsername(null);
        userRepository.save(user);
        return true;
    }

    public Boolean checkTokenDate(String token) {
        try {
            var tokenDate = LocalDateTime.parse(token.substring(100));
            long hours = ChronoUnit.HOURS.between(tokenDate, LocalDateTime.now());
            if (hours < 24) return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Optional<User> findByLinkRestorePassword(String token) {
        return userRepository.findByLinkRestorePassword(token);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByLinkActivateUsername(String token) {
        return userRepository.findByLinkActivateUsername(token);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByUsernameAndEnabled(email, true);
    }
    @Transactional(readOnly = true)
    public Boolean emailExist(String email) {

        return userRepository.findByUsername(email).isPresent();
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

    @Transactional
    public Boolean isAdmin(String username) {
        var flag = false;
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

    @Transactional
    public Boolean isAdopter(String username) {
        var flag = false;
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

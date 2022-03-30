package mx.com.adoptame.entities.user;

import mx.com.adoptame.config.email.EmailService;
import mx.com.adoptame.entities.address.Address;
import mx.com.adoptame.entities.address.AdressRepository;
import mx.com.adoptame.entities.profile.Profile;
import mx.com.adoptame.entities.profile.ProfileRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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
    private EmailService emailService;

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> findOne(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> save(User entity) {
        return Optional.of(userRepository.save(entity));
    }

    public Optional<User> saveWithoutPassword(User entity) {
        Optional<User> user = findOne(entity.getId());
        entity.setPassword(user.get().getPassword());
        return Optional.of(userRepository.save(entity));
    }
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

    public Optional<User> update(User entity) {
        Optional<User> updatedEntity = Optional.empty();
        updatedEntity = userRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            userRepository.save(entity);
        return updatedEntity;
    }

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
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean delete(Integer id) {
        boolean entity = userRepository.existsById(id);
        if (entity) {
            userRepository.deleteById(id);
        }
        return entity;
    }


    public Boolean updatePassword(User user,String currentPassword, String newPassword, String repeatedPassword){
        if(!passwordEncoder.matches(user.getPassword(), currentPassword)) return false;

        if(!newPassword.equals(repeatedPassword)) return false;

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    /**
     * sets value for the field linkRestorePassword of
     * a user found by the given email – and persist
     * change to the database.
     */
    public void updateResetPasswordToken(String token, String email)  {
        Optional<User> user = userRepository.findByEmailAndIsActive(email, true);
        if (user.isPresent()) {
            user.get().setLinkRestorePassword(token);
            save(user.get());
        }
    }
    /**
     *
     * sets new password for the user
     * (using BCrypt password encoding) and
     * nullifies the reset password token.
     */
    public Boolean updatePassword(User user, String newPassword, String repeatedPassword){

        if(!newPassword.equals(repeatedPassword)) return false;

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    public void sedEmail(String email, String path){
        //We create a token
        String token  = RandomString.make(100);
        String host = "http://localhost:8090";
        //we try to send the email
        try {
            updateResetPasswordToken(token, email);
            String resetPasswordLink = host +"/user/link_restore_password?token=" + token;
            emailService.sendRecoverPasswordTemplate(email,resetPasswordLink);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public Optional<User> findByLinkRestorePassword(String token) {
        return userRepository.findByLinkRestorePassword(token);
    }

}

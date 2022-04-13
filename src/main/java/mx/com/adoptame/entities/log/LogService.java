package mx.com.adoptame.entities.log;

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
    public void saveUserLog(String action,User user, User madeBy) {
                logRepository.logUser(
                        action,
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEnabled(),
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

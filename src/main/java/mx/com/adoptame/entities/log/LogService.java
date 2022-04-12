package mx.com.adoptame.entities.log;

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
    public Optional<Log> update(Log entity) {
        Optional<Log> updatedEntity;
        updatedEntity = logRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            logRepository.save(entity);
        return updatedEntity;
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

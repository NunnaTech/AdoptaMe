package mx.com.adoptame.entities.boards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public List<Board> findAll() {
        return (List<Board>) boardRepository.findAll();
    }

    public Optional<Board> findOne(Integer id) {
        return boardRepository.findById(id);
    }

    public Optional<Board> save(Board entity) {
        return Optional.of(boardRepository.save(entity));
    }

    public Optional<Board> update(Board entity) {
        Optional<Board> updatedEntity = Optional.empty();
        updatedEntity = boardRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            boardRepository.save(entity);
        return updatedEntity;
    }

    public Optional<Board> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            Board entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<Board> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(Board.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            boardRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean delete(Integer id) {
        boolean entity = boardRepository.existsById(id);
        if (entity) {
            boardRepository.deleteById(id);
        }
        return entity;
    }
}

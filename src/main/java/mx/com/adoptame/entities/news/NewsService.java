package mx.com.adoptame.entities.news;
import mx.com.adoptame.entities.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public List<News> findAll() {
        return (List<News>) newsRepository.findAll();
    }
    public List<News> findAllActives() {
        return (List<News>) newsRepository.findAllByIsPublished(true);
    }

    public List<News> findLastFive() {
        return (List<News>) newsRepository.findTop5ByOrderByCreatedAtDesc();
    }

    public List<News> findMainNews() {
        return (List<News>) newsRepository.findAllByIsMainAndIsPublishedOrderByCreatedAtDesc(true,true);
    }

    public Optional<News> findOne(Integer id) {
        return newsRepository.findById(id);
    }

    public Optional<News> save(News entity) {
        return Optional.of(newsRepository.save(entity));
    }

    public void saveTags(News news, List<Tag> tags) {
        for (Tag tag:tags) {
            saveTag(news,tag);
        }
    }

    public Optional<News> saveTag(News news,Tag tag) {
        news.addTag(tag);
        return Optional.of(newsRepository.save(news));
    }
    public Optional<News> update(News entity) {
        Optional<News> updatedEntity = Optional.empty();
        updatedEntity = newsRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            newsRepository.save(entity);
        return updatedEntity;
    }

    public Optional<News> partialUpdate(Integer id, Map<Object, Object> fields) {
        try {
            News entity = findOne(id).get();
            if (entity == null) {
                return Optional.empty();
            }
            Optional<News> updatedEntity = Optional.empty();
            fields.forEach((updatedField, value) -> {
                Field field = ReflectionUtils.findField(News.class, (String) updatedField);
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, value);
            });
            newsRepository.save(entity);
            updatedEntity = Optional.of(entity);
            return updatedEntity;
        } catch (Exception exception) {
            System.err.println(exception);
            return Optional.empty();
        }
    }

    public Boolean delete(Integer id) {
        boolean entity = newsRepository.existsById(id);
        if (entity) {
            newsRepository.deleteById(id);
        }
        return entity;
    }
}

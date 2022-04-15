package mx.com.adoptame.entities.news;

import mx.com.adoptame.entities.log.LogService;
import mx.com.adoptame.entities.tag.Tag;
import mx.com.adoptame.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private LogService logService;

    @Transactional(readOnly = true)
    public List<News> findAll() {
        return (List<News>) newsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<News> findAllActives() {
        return newsRepository.findAllByIsPublished(true);
    }

    @Transactional(readOnly = true)
    public List<News> findLastFive() {
        return newsRepository.findTop5ByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<News> findMainNews() {
        return newsRepository.findAllByIsMainAndIsPublishedOrderByCreatedAtDesc(true, true);
    }

    @Transactional
    public Optional<News> findOne(Integer id) {
        return newsRepository.findById(id);
    }

    @Transactional
    public Optional<News> save(News entity, User user) {
        var action = "Actualizar";
        if (entity.getImage().isEmpty()) {
            entity.setImage("https://s3.aws-k8s.generated.photos/ai-generated-photos/upscaler-uploads/592/89da8cb2-d1ea-4cfa-9e45-14725313b19e.png");
        }
        if (entity.getId() == null) {
            action = "Crear";
        }
        logService.saveNewsLog(action, entity, user);
        return Optional.of(newsRepository.save(entity));
    }

    @Transactional
    public Optional<News> saveTag(News news, Tag tag) {
        news.addTag(tag);
        return Optional.of(newsRepository.save(news));
    }

    @Transactional
    public Optional<News> update(News entity) {
        Optional<News> updatedEntity;
        updatedEntity = newsRepository.findById(entity.getId());
        if (!updatedEntity.isEmpty())
            newsRepository.save(entity);
        return updatedEntity;
    }

    @Transactional
    public Boolean delete(Integer id, User user) {
        Optional<News> entity = newsRepository.findById(id);
        if (entity.isPresent()) {
            logService.saveNewsLog("Eliminar", entity.get(), user);
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Integer countMainNews() {
        return newsRepository.countByIsMainIsTrue();
    }

    @Transactional(readOnly = true)
    public Long countNews() {
        return newsRepository.count();
    }

    @Transactional(readOnly = true)
    public Integer countPublishedNews() {
        return newsRepository.countByIsPublishedIsTrue();
    }
}

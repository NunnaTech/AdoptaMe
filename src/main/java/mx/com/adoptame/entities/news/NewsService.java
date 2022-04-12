package mx.com.adoptame.entities.news;

import mx.com.adoptame.entities.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

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
        return newsRepository.findAllByIsMainAndIsPublishedOrderByCreatedAtDesc(true,true);
    }

    @Transactional
    public Optional<News> findOne(Integer id) {
        return newsRepository.findById(id);
    }

    @Transactional
    public Optional<News> save(News entity) {
        return Optional.of(newsRepository.save(entity));
    }

    @Transactional
    public Optional<News> saveTag(News news,Tag tag) {
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
    public Boolean delete(Integer id) {
        boolean entity = newsRepository.existsById(id);
        if (entity) {
            newsRepository.deleteById(id);
        }
        return entity;
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

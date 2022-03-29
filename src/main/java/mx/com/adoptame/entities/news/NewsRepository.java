package mx.com.adoptame.entities.news;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News, Integer> {
    @Query(
            value = "SELECT * FROM tbl_news n WHERE n.is_published = 1 ORDER BY n.created_at LIMIT 5 ",
            nativeQuery = true)
    List<News> findTop5ByOrderByCreatedAtDesc();

    List<News> findAllByIsPublished(Boolean isPublished);

    List<News> findAllByIsMainAndIsPublishedOrderByCreatedAtDesc(Boolean isMain, Boolean isPublished);


}

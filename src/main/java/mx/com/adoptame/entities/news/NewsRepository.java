package mx.com.adoptame.entities.news;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News, Integer> {
    List<News> findTop5ByOrderByCreatedAtAsc();
}

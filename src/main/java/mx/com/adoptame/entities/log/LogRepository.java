package mx.com.adoptame.entities.log;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<Log, Integer> {
    @Query(value = "{call log_user(:action_in, :id_in, :username_in, :password_in, :enabled_in, :user_id_in)}", nativeQuery = true)
    void logUser(
            @Param("action_in")String action_in,
            @Param("id_in")Integer id_in,
            @Param("username_in")String username_in,
            @Param("password_in")String password_in,
            @Param("enabled_in")Boolean enabled_in,
            @Param("user_id_in")Integer user_id_in
    );
}

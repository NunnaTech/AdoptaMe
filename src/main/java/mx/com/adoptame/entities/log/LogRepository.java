package mx.com.adoptame.entities.log;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<Log, Integer> {

    @Procedure(procedureName = "log_user")
    void logUser(
            @Param("action_in") String action_in,
            @Param("id_in") Integer id_in,
            @Param("username_in") String username_in,
            @Param("password_in") String password_in,
            @Param("enabled_in") Integer enabled_in,
            @Param("user_id_in") Integer user_id_in
    );

    @Procedure(procedureName = "log_tbl_colors")
    void logColor(
            @Param("action_in") String action_in,
            @Param("id_in") Integer id_in,
            @Param("hex_code_in") String hex_code_in,
            @Param("name_in") String name_in,
            @Param("status_in") Integer status_in,
            @Param("user_id_in") Integer user_id_in
    );
}

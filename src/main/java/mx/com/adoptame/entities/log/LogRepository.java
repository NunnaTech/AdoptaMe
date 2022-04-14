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

    @Procedure(procedureName = "log_tlb_types")
    void logType(
            @Param("action_in") String action_in,
            @Param("id_in") Integer id_in,
            @Param("name_in") String name_in,
            @Param("description_in") String description_in,
            @Param("status_in") Integer status_in,
            @Param("user_id_in") Integer user_id_in
    );

    @Procedure(procedureName = "log_tbl_donations")
    void logDonation(
            @Param("action_in") String action_in,
            @Param("id_in") Integer id_in,
            @Param("authorization_in") String authorization_in,
            @Param("is_completed_in") Integer is_completed_in,
            @Param("quantity_in") Double quantity_in,
            @Param("user_in") Integer user_in,
            @Param("user_id_in") Integer user_id_in
    );

    @Procedure(procedureName = "log_tbl_characters")
    void logCharacter(
            @Param("action_in") String action_in,
            @Param("id_in") Integer id_in,
            @Param("description_in") String description_in,
            @Param("name_in") String name_in,
            @Param("status_in") Integer status_in,
            @Param("user_id_in") Integer user_id_in
    );

    @Procedure(procedureName = "log_tbl_tags")
    void logTag(
            @Param("action_in") String action_in,
            @Param("id_in") Integer id_in,
            @Param("name_in") String name_in,
            @Param("description_in") String description_in,
            @Param("user_id_in") Integer user_id_in
    );

    @Procedure(procedureName = "log_tbl_sizes")
    void logSize(
            @Param("action_in") String action_in,
            @Param("id_in") Integer id_in,
            @Param("name_in") String name_in,
            @Param("size_range_in") String size_range_in,
            @Param("status_in") Integer status_in,
            @Param("user_id_in") Integer user_id_in
    );

    @Procedure(procedureName = "log_tbl_address")
    void logAddress(
            @Param("action_in") String action_in,
            @Param("id_in") Integer id_in,
            @Param("external_number_in") String external_number_in,
            @Param("internal_number_in") String internal_number_in,
            @Param("references_street_in") String references_street_in,
            @Param("street_in") String street_in,
            @Param("zip_code_in") String zip_code_in,
            @Param("user_id_in") Integer user_id_in
    );

    @Procedure(procedureName = "log_tbl_news")
    void logNews(
            @Param("action_in") String action_in,
            @Param("id_in") Integer id_in,
            @Param("content_in") String content_in,
            @Param("image_in") String image_in,
            @Param("is_main_in") Integer is_main_in,
            @Param("is_published_in") Integer is_published_in,
            @Param("title_in") String title_in,
            @Param("user_in") Integer user_in,
            @Param("user_id_in") Integer user_id_in
    );

    @Procedure(procedureName = "log_tbl_pets")
    void logPets(
            @Param("action_in") String action_in,
            @Param("id_in") Integer id_in,
            @Param("name_in") String name_in,
            @Param("age_in") String age_in,
            @Param("breed_in") String breed_in,
            @Param("description_in") String description_in,
            @Param("gender_in") Integer gender_in,
            @Param("is_active_in") Integer is_active_in,
            @Param("is_adopted_in") Integer is_adopted_in,
            @Param("is_dropped_in") Integer is_dropped_in,
            @Param("character_id_in") Integer character_id_in,
            @Param("color_id_in") Integer color_id_in,
            @Param("size_id_in") Integer size_id_in,
            @Param("type_id_in") Integer type_id_in,
            @Param("user_in") Integer user_in,
            @Param("user_id_in") Integer user_id_in
    );

}

package mx.com.adoptame.entities.log;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log, Integer> {
    List<Log> findAllByOrderByCreatedAtDesc();
    @Procedure(procedureName = "log_user")
    void logUser(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("username_in") String usernameIn,
            @Param("password_in") String passwordIn,
            @Param("enabled_in") Integer enabledIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_request")
    void logRequest(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("is_accepted_in") Integer isAcceptedIn,
            @Param("reason_in") String reasonIn,
            @Param("is_canceled_in") Integer isCanceledIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_pets_adopted")
    void logPetsAdopted(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("is_accepted_in") Integer isAcceptedIn,
            @Param("is_canceled_in") Integer isCanceledIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_profile")
    void logProfile(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("username_in") String usernameIn,
            @Param("password_in") String passwordIn,
            @Param("enabled_in") Integer enabledIn,
            @Param("name_in") String nameIN,
            @Param("last_name_in") String lastNameIn,
            @Param("secondName_in") String secondNameIn,
            @Param("phone_in") String phoneIn,
            @Param("image_in") String imagenIn,
            @Param("external_number_in") String externalNumberIn,
            @Param("internal_number_in") String internalNumberIn,
            @Param("street_in") String streetIn,
            @Param("zip_code_in") String zipCodeIn,
            @Param("references_street_in") String referencesStreetIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_colors")
    void logColor(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("hex_code_in") String hexCodeIn,
            @Param("name_in") String nameIn,
            @Param("status_in") Integer statusIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tlb_types")
    void logType(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("name_in") String nameIn,
            @Param("description_in") String descriptionIn,
            @Param("status_in") Integer statusIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_donations")
    void logDonation(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("authorization_in") String authorizationIn,
            @Param("is_completed_in") Integer isCompletedIn,
            @Param("quantity_in") Double quantityIn,
            @Param("user_in") Integer userIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_characters")
    void logCharacter(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("description_in") String descriptionIn,
            @Param("name_in") String nameIn,
            @Param("status_in") Integer statusIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_tags")
    void logTag(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("name_in") String nameIn,
            @Param("description_in") String descriptionIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_sizes")
    void logSize(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("name_in") String nameIn,
            @Param("size_range_in") String sizeRangeIn,
            @Param("status_in") Integer statusIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_address")
    void logAddress(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("external_number_in") String externalNumberIn,
            @Param("internal_number_in") String internalNumberIn,
            @Param("references_street_in") String referencesStreetIn,
            @Param("street_in") String streetIn,
            @Param("zip_code_in") String zipCodeIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_news")
    void logNews(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("content_in") String contentIn,
            @Param("image_in") String imageIn,
            @Param("is_main_in") Integer isMainIn,
            @Param("is_published_in") Integer isPublishedIn,
            @Param("title_in") String titleIn,
            @Param("user_in") Integer userIn,
            @Param("user_id_in") Integer userIdIn
    );

    @Procedure(procedureName = "log_tbl_pets")
    void logPets(
            @Param("action_in") String actionIn,
            @Param("id_in") Integer idIn,
            @Param("name_in") String nameIn,
            @Param("age_in") String ageIn,
            @Param("breed_in") String breedIn,
            @Param("description_in") String descriptionIn,
            @Param("gender_in") Integer genderIn,
            @Param("is_active_in") Integer isActiveIn,
            @Param("is_adopted_in") Integer isAdoptedIn,
            @Param("is_dropped_in") Integer isDroppedIn,
            @Param("character_id_in") Integer characterIdIn,
            @Param("color_id_in") Integer colorIdIn,
            @Param("size_id_in") Integer sizeIdIn,
            @Param("type_id_in") Integer typeIdIn,
            @Param("user_in") Integer userIn,
            @Param("user_id_in") Integer userIdIn
    );

}

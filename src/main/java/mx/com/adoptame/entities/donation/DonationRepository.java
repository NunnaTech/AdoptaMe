package mx.com.adoptame.entities.donation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends CrudRepository<Donation, Integer> {
    @Query(value = "SELECT SUM(d.quantity) FROM tbl_donations d WHERE d.is_completed = 1;",
            nativeQuery = true)
    Double sumCuantity();

    @Query(
            value = "SELECT * FROM tbl_donations d WHERE d.is_completed = 1 ORDER BY d.created_at LIMIT 5 ",
            nativeQuery = true)
    List<Donation> findTop5ByCreatedAtDesc();

    @Query(value = "SELECT SUM(d.quantity) FROM tbl_donations d WHERE d.is_completed = 1 AND d.user_id=?1",
            nativeQuery = true)
    Double sumCuantityByUserId(Integer userid);
}

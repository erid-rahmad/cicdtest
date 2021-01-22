package sample.actuator.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import sample.actuator.model.RequestDetails;

@Transactional
public interface ReqDetailJpa extends JpaRepository<RequestDetails,Long> {

    @Query(value = "SELECT * FROM REQUEST_DETAILS where token=?", nativeQuery = true)
    RequestDetails findBytoken(String token);

}

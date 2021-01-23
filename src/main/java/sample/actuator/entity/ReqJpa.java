package sample.actuator.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sample.actuator.model.Request;
import sample.actuator.model.RequestDetails;
import sample.actuator.model.ResponseDetails;

import java.util.Optional;

public interface ReqJpa extends JpaRepository<Request,Long> {
    @Query(value = "SELECT * FROM REQUEST where trxconfirm=?", nativeQuery = true)
    Request findBytrxconfirm(String trxconfirm);

}

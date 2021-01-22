package sample.actuator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
@Transactional
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Request {

    @Id
    @GeneratedValue
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private String clientid;
    private String key;
    private String branchid;
    private String counterid;
    private String producttype;
    private String trxtype ;
    @JsonIgnore
    private String trxconfirm;
    @JsonIgnore
    private String status;


    @OneToOne(fetch= FetchType.LAZY)
    private RequestDetails requestDetails;

    public Request(LocalDateTime time, String clientid, String key, String branchid, String counterid, String producttype, String trxtype, String trxconfirm, String status) {
        this.time = time;
        this.clientid = clientid;
        this.key = key;
        this.branchid = branchid;
        this.counterid = counterid;
        this.producttype = producttype;
        this.trxtype = trxtype;
        this.trxconfirm = trxconfirm;
        this.status = status;
    }
}

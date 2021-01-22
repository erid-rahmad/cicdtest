package sample.actuator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Transactional

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String trxid;
    private String token;
    private String nohp;
    private String amount;

    @OneToOne(fetch= FetchType.LAZY,mappedBy = "requestDetails")
    private Request request;

    public RequestDetails(String trxid, String token, String nohp, String amount) {
        this.trxid = trxid;
        this.token = token;
        this.nohp = nohp;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RequestDetails{" +
                "id=" + id +
                ", trxid='" + trxid + '\'' +
                ", token='" + token + '\'' +
                ", nohp='" + nohp + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}


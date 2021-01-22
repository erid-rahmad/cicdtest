package sample.actuator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.actuator.model.Request;
import sample.actuator.model.RequestDetails;
import sample.actuator.model.Token;

import javax.persistence.EntityManager;
import java.util.Random;

@Service
@Slf4j
public class TokenService {

    Random rand = new Random();

    @Autowired
    EntityManager em;

    @Transactional
    public Token generatetoken(){
        Token token = new Token();
        RequestDetails requestDetails = new RequestDetails();
        Request request = new Request();

        long trxConfirm = (long)(rand.nextDouble()*10000000L);
        log.info("this random x {}",String.valueOf(trxConfirm));
        token.setToken("CSH"+String.valueOf(trxConfirm));
        requestDetails.setToken("CSH"+String.valueOf(trxConfirm));
        em.persist(requestDetails);

        request.setRequestDetails(requestDetails);
        log.info(String.valueOf(token));

        return token;
    }
}

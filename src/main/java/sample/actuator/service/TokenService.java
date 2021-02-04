package sample.actuator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.actuator.model.Request;
import sample.actuator.model.RequestDetails;
import sample.actuator.model.Token;

import javax.persistence.EntityManager;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

@Service
@Slf4j
public class TokenService {

    Random rand = new Random();

    @Autowired
    EntityManager em;

    @Transactional
    public Token generatetoken(String norek){
        Token token = new Token();
        RequestDetails requestDetails = new RequestDetails();
        Request request = new Request();

        long trxConfirm = (long)(rand.nextDouble()*10000000L);
        log.info("this random x {}",String.valueOf(trxConfirm));
        String thistoken = "CSH"+String.valueOf(trxConfirm);
        token.setToken(thistoken);
        token.setNorek(norek);
        requestDetails.setToken(thistoken);
        em.persist(requestDetails);

        request.setRequestDetails(requestDetails);
        log.info(String.valueOf(token));

        JSONObject jsonObject = new JSONObject();

        String loc = "D:/Payment gateway/Nobu-indomaret/file/"+thistoken+".json";

        try {
            jsonObject.put("token","CSH"+String.valueOf(trxConfirm));
            jsonObject.put("norek",norek);
//            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String formatDateTime = LocalDateTime.now().plusMinutes(15).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            jsonObject.put("time",formatDateTime);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            FileWriter file = new FileWriter(loc);
            file.append(String.valueOf(jsonObject));
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return token;
    }
}

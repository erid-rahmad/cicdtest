package sample.actuator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import sample.actuator.entity.ReqDetailJpa;
import sample.actuator.entity.ReqJpa;
import sample.actuator.model.*;

import javax.persistence.EntityManager;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@org.springframework.stereotype.Service
@Slf4j
public class GeneralService {

    @Autowired
    ReqJpa reqJpa;
    @Autowired
    EntityManager em;

    @Autowired
    private KafkaSender sender;

    @Autowired
    ISO iso;



    Random rand = new Random();

    @Transactional
    public Response general(Request request_income){
        Response response = new Response();
        response.setTime(request_income.getTime());
        response.setClientid(request_income.getClientid());
        response.setKey(request_income.getKey());
        response.setBranchid(request_income.getBranchid());
        response.setCounterid(request_income.getCounterid());
        response.setProducttype(request_income.getProducttype());
        response.setTrxtype(request_income.getTrxtype());

        if(request_income.getTrxtype().contains("CASHOUT")){
            if(readfile(request_income.getRequestDetails().getToken())==true){
                ResponseDetails responseDetails = new ResponseDetails();
                String trxconfirm = Request(request_income);
                responseDetails.setTrxconfirm(trxconfirm);
                response.setResponseDetails(responseDetails);
                response.setRespondetail("succes");
                response.setResponcode("00");
                sender.sendData(iso.packToIso("0200",trxconfirm));
            }else {
                ResponseDetails responseDetails = new ResponseDetails();
                responseDetails.setTrxconfirm(null);
                response.setResponseDetails(responseDetails);

                response.setRespondetail("gagal");
                response.setResponcode("05");
            }
        }else if(request_income.getTrxtype().contains("REVERSAL")){
            sender.sendData(iso.packToIso("0200","trxconfirm"));
            response.setRespondetail("succes ");
            response.setResponcode("00");
        }else if(request_income.getTrxtype().contains("NOTIFICATION")){
            response.setRespondetail("succes");
            response.setResponcode("00");
        }
        return response;
    }

    public String Request(Request request_income){
        long trxConfirm = (long)(rand.nextDouble()*1000000000000000L);
        return String.valueOf(trxConfirm);
    }

    public boolean readfile(String token) {
        boolean isBefore = false;
        String loc = "D:/Payment gateway/Nobu-indomaret/file/"+token+".json";
        JsonParser parser = new JsonParser();
            try {
                FileReader tes = new FileReader(loc);
                Object obj = parser.parse(tes);
                tes.close();

                Map<String, Object> response = new ObjectMapper().readValue(obj.toString(), HashMap.class);
                log.info("File log {}",response);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(response.get("time").toString(), formatter);
                LocalDateTime dateTime1 = LocalDateTime.now();
                isBefore = dateTime1.isBefore(dateTime);

                File file = new File(loc);
                file.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("the time {}",isBefore);
            return isBefore;
    }
}

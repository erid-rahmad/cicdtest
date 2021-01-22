package sample.actuator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sample.actuator.entity.ReqDetailJpa;
import sample.actuator.entity.ReqJpa;
import sample.actuator.model.Request;
import sample.actuator.model.RequestDetails;
import sample.actuator.model.Response;
import sample.actuator.model.ResponseDetails;

import javax.persistence.EntityManager;

import java.util.Random;

@org.springframework.stereotype.Service
@Slf4j
public class GeneralService {

    @Autowired
    ReqJpa reqJpa;

    @Autowired
    EntityManager em;

    @Autowired
    ReqDetailJpa reqDetailJpa;

    Random rand = new Random();


    public ResponseDetails findById(Long id) {
        return em.find(ResponseDetails.class,id);
    }



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
            Request request = new Request(request_income.getTime(),request_income.getClientid(),request_income.getKey(),
                    request_income.getBranchid(),request_income.getCounterid(),request_income.getProducttype(),request_income.getTrxtype(),
                    null,null);
            RequestDetails requestDetails = reqDetailJpa.findBytoken(request_income.getRequestDetails()
                    .getToken());
            if(requestDetails.getRequest()==null){
                em.persist(request);
                request.setRequestDetails(requestDetails);
                em.persist(request);
                log.info("this reqs {}",requestDetails);
                ResponseDetails responseDetails = new ResponseDetails();
                String trxconfirm = Request(request_income);
                responseDetails.setTrxconfirm(trxconfirm);
                request.setTrxconfirm(trxconfirm);
                request.setStatus("sukses");
                requestDetails.setNohp(request_income.getRequestDetails().getNohp());
                response.setResponseDetails(responseDetails);
                response.setRespondetail("succes");
                response.setResponcode("00");
            }else {
                ResponseDetails responseDetails = new ResponseDetails();
                responseDetails.setTrxconfirm(null);
                response.setResponseDetails(responseDetails);
                response.setRespondetail("gagal");
                response.setResponcode("05");
            }

        }else if(request_income.getTrxtype().contains("REVERSAL")){
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
        log.info("this random x {}",String.valueOf(trxConfirm));


        return String.valueOf(trxConfirm);

    }
}

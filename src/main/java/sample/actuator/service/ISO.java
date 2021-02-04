package sample.actuator.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.jpos.iso.ISOException;

@Slf4j
public class ISO {
    public String packToIso() {
        String result = null;
        org.jpos.iso.packager.GenericPackager genericPkg;
        org.jpos.iso.ISOMsg isoMsg;
        try {
            genericPkg = new org.jpos.iso.packager.GenericPackager("src/main/resources/isoPackager.xml");
            isoMsg = new org.jpos.iso.ISOMsg();
            isoMsg.setPackager(genericPkg);

            isoMsg.setMTI("0200"); //request financial transaction
            isoMsg.set(3, "123456");
            isoMsg.set(4, "300000");
            String formattedDate = new java.text.SimpleDateFormat("MMddhhmmss").format(new Date());
            isoMsg.set(7, formattedDate);
            isoMsg.set(11, "654321");
            isoMsg.set(22, "123");
            isoMsg.set(24, "123");
            isoMsg.set(35, "AAAAAAAAAAAAAAA");
            isoMsg.set(37, "1234567890AB");
            isoMsg.set(41, "CAT12345");
            isoMsg.set(42, "MERCHANT1234567");
            isoMsg.set(48, "000");
            isoMsg.set(63, "020010023110030001");
            byte[] isoMsgByte = isoMsg.pack();
            result = new String(isoMsgByte);
            log.info("BUILD ISO {}",result);
        } catch (org.jpos.iso.ISOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map unpackFromIso(String stringMsg){
        Map map = new HashMap();
        try{
            org.jpos.iso.ISOMsg isoMsg = new org.jpos.iso.ISOMsg();
            isoMsg.setPackager(new org.jpos.iso.packager.GenericPackager("src/main/resources/isoPackager.xml"));
            isoMsg.unpack(stringMsg.getBytes());
            for(int i=1; i<=isoMsg.getMaxField(); i++){
                if(isoMsg.hasField(i)){
                    map.put("bit-"+i, isoMsg.getString(i));
                }
            }
        }catch(ISOException e){
            e.printStackTrace();
        }
        return map;
    }

}
package com.fictiontimes.fictiontimesbackend.utils;

import com.fictiontimes.fictiontimesbackend.model.DTO.PayhereNotifyDTO;
import com.fictiontimes.fictiontimesbackend.repository.DBConnection;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.Properties;

public class PayhereUtils {

    public static String getMerchantId() throws IOException {
        Properties properties = new Properties();
        properties.load(DBConnection.class.getResourceAsStream("/payhere.properties"));
        return properties.getProperty("payhere.merchant.id");
    }

    public static String getMerchantSecret() throws IOException {
        Properties properties = new Properties();
        properties.load(DBConnection.class.getResourceAsStream("/payhere.properties"));
        return properties.getProperty("payhere.merchant.secret");
    }

    public static boolean verifyMD5Sig(PayhereNotifyDTO payhereNotifyDTO) throws IOException {
        return DigestUtils.md5Hex(
                payhereNotifyDTO.getMerchant_id() + payhereNotifyDTO.getOrder_id()
                        + payhereNotifyDTO.getPayhere_amount() + payhereNotifyDTO.getPayhere_currency()
                        + payhereNotifyDTO.getStatus_code() + DigestUtils.md5Hex(getMerchantSecret()).toUpperCase()
        ).toUpperCase().equals(payhereNotifyDTO.getMd5sig());
    }

}

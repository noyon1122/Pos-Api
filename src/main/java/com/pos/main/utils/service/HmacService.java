package com.pos.main.utils.service;

import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HmacService {

    @Value("${bank.api.secret}")
    private String secretKey;

    public boolean verifySignature(String payload, String incomingSignature) {
        if (incomingSignature == null || payload == null) {
            return false;
        }
        
        //System.out.println("Incommig Signature : "+incomingSignature);
        
        try {
            String calculatedSignature = generateSignature(payload);
            
            //System.out.println("Generated Signature : "+calculatedSignature);
            return calculatedSignature.equalsIgnoreCase(incomingSignature);
        } catch (Exception e) {
            return false;
        }
    }

    public String generateSignature(String data) throws Exception {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8), 
                "HmacSHA256"
        );
        sha256Hmac.init(secretKeySpec);

        byte[] hashBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        

        return bytesToHex(hashBytes);
    }


    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
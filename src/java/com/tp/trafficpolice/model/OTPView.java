/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.SecureRandom;

/**
 *
 * @author DELL
 */
public class OTPView {
    
    
     public String random(int size) {
        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            // Generate 20 integers 0..20
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }
     
     
     
       public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
       String result = "";
       try {
           String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";//"http://api.mVaayoo.com/mvaayooapi/MessageCompose?";
           String tempMessage = messageStr1;
           String sender_id = java.net.URLEncoder.encode("TEST SMS", "UTF-8");         // e.g. "TEST+SMS"
           System.out.println("messageStr1 is = " + messageStr1);
           messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
           String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number=" + numberStr1 + "&text=" + messageStr1 + "&route=";
           String url = host_url + queryString;
           result = callURL(url);
           System.out.println("SMS URL: " + url);
       } catch (Exception e) {
           result = e.toString();
           System.out.println("SMSModel sendSMS() Error: " + e);
       }
       return result;
   }
    
     private String callURL(String strURL) {
       String status = "";
       try {
           java.net.URL obj = new java.net.URL(strURL);
           HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
           httpReq.setDoOutput(true);
           httpReq.setInstanceFollowRedirects(true);
           httpReq.setRequestMethod("GET");
           status = httpReq.getResponseMessage();
       } catch (MalformedURLException me) {
           status = me.toString();
       } catch (IOException ioe) {
           status = ioe.toString();
       } catch (Exception e) {
           status = e.toString();
       }
       return status;
   }
    
}

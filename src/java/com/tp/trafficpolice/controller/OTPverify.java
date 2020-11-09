/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.trafficpolice.controller;



import com.tp.dbcon.DBConnection;
import com.tp.tableClasses.userEntryByImageBean;
import com.tp.trafficpolice.model.testClass;
import com.tp.webServicesModel.VehicleDriverWebModel;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;

/**
 *
 * @author Navitus2
 */
public class OTPverify extends HttpServlet{
     public void doGet(HttpServletRequest req,HttpServletResponse res)
                       throws ServletException, IOException{

       res.setContentType("text/html;charset=UTF-8");
       res.setCharacterEncoding("UTF-8");
       req.setCharacterEncoding("UTF-8");
       ServletContext ctx = getServletContext();
      userEntryByImageBean bean = new userEntryByImageBean();
      OTPverify verify = new OTPverify();
        testClass modelTypeModel = new testClass();
        modelTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        modelTypeModel.setDb_username(ctx.getInitParameter("db_userName"));
        modelTypeModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        modelTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        modelTypeModel.setConnection();
      
       VehicleDriverWebModel vehicleDriverWebServiceModel = new VehicleDriverWebModel();
        try {
            vehicleDriverWebServiceModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception ex) {
            System.out.println("ERROR : in vehicleDetail() in RideWebservices : " + ex);
        }
      

     

     
       try{
           
         OTPGeneral gen = new OTPGeneral();
   
           String tplist = req.getParameter("tplist");
       String otp = req.getParameter("otp");
         int i=1;    
             if(otp==null || otp.isEmpty() )
               {
                 System.out.println("enter the mobile number");
               }else 
               {
                   testClass twest = new testClass();
              
               int verify_OTP = twest.verifyOTP(otp); 
               System.out.println(verify_OTP);
           
               HttpSession session = req.getSession();
               String mob=(String)session.getAttribute("mobile_no");
                  req.setAttribute("mobile_no",mob);
                  
              //  String rank=req.getParameter("rank");

       try {
            String JQstring = req.getParameter("action1");
            String q = req.getParameter("q");
//            if (JQstring != null) {
//                PrintWriter pw=res.getWriter();
//                List<String> li = null;
//                if (JQstring.equals("getRank")) {
//                    li = twest.getRank(q);
//                }
//                Iterator<String> itr = li.iterator();
//                while (itr.hasNext()) {
//                    String data = itr.next();
//                    pw.println(data);
//                }
//                 return;
//           }

            } catch (Exception e) {
            System.out.println("\n Error"+e);
        }


                  
                  if(verify_OTP>0)
                  {             
                      String replaceString=tplist.replace('[',' ');
                      String replaceString1 = replaceString.replace(']',' ');
                      
                      List<userEntryByImageBean> showdata = twest.showData(replaceString1);
                  
                      
                      
                  Random randomGenerator = new Random();
	          int randomInt = randomGenerator.nextInt(1000000);
                  String Challan_no = "ORDER_"+randomInt;
                  String offender_name = bean.getOffender_name();
                  String offender_mobile_number = mob;
                  String vehicle_no = bean.getVehicle_no();
                  String vehicle_type = bean.getVehicle_type();
                  String kp_name = bean.getKey_person_name();
                  String offence_date = bean.getOffence_date();
                  String act = bean.getAct();
                  String offence_type = bean.getOffence_type();
                  String Penalty_amount = bean.getPenalty_amount();
                  String Act_origin = bean.getAct_origin();
                  String Offence_code = bean.getOffence_code();
                
                  
                    req.setAttribute("Challan_no",Challan_no);
                    req.setAttribute("offender_name",offender_name);
                    req.setAttribute("offender_mobile_number",offender_mobile_number);
                    req.setAttribute("vehicle_no",vehicle_no);
                    req.setAttribute("vehicle_type",vehicle_type);
                    req.setAttribute("kp_name",kp_name);
                    req.setAttribute("offence_date",offence_date);
                    req.setAttribute("act",act);
                    req.setAttribute("offence_type",offence_type);
                    req.setAttribute("Penalty_amount",Penalty_amount);
                    req.setAttribute("Act_origin",Act_origin);
                    req.setAttribute("Offence_code",Offence_code);
                  
                   
                     req.setAttribute("tplist",showdata);
                    
                      
                 req.getRequestDispatcher("/view/trafficPolice/TxnTest.jsp").forward(req,res);
                  }
                  else
                  {
                      String message = "INVALID OTP";
                      
                      req.setAttribute("message", message);
                   req.getRequestDispatcher("/view/VerifyOTP.jsp").forward(req,res);
                  }
         
               }
       }
       catch(Exception e)
               {
               System.out.println("\n Error"+e);
               }
        
                 
                    // pw.close();
         } 
       
           // pw.close();
     }
    


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.trafficpolice.controller;



import com.tp.dbcon.DBConnection;
import com.tp.trafficpolice.model.ModelTypeModel;
import com.tp.trafficpolice.model.OTPG;
import com.tp.trafficpolice.model.testClass;

import com.tp.webServicesModel.VehicleDriverWebModel;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
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
public class OTPGeneral extends HttpServlet{
     public void doGet(HttpServletRequest req,HttpServletResponse res)
                       throws ServletException, IOException{

       res.setContentType("text/html;charset=UTF-8");
       res.setCharacterEncoding("UTF-8");
       req.setCharacterEncoding("UTF-8");
       ServletContext ctx = getServletContext();
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
       String id=req.getParameter("mobile_no");
       String otp = req.getParameter("otp");
             
             if(id==null || id.isEmpty() )
               {
                 System.out.println("enter the mobile number");
               }else
               {
//                   
                  OTPGeneral gen = new OTPGeneral();
                   HttpSession session = req.getSession();
                    session.setAttribute("mobile_no",id);
//                otpg.VerifyMoileNo(id);                
//                otpg.verifyOTP(id);
                   testClass twest = new testClass();
               String test_mobile =     twest.VerifyMoileNo(id);
               System.out.println(test_mobile);
             
               if(test_mobile.equals("success"))
               {
                 List<String> tpid = testClass.getOffenderId(id);
                 
                 req.setAttribute("tplist",tpid);

                 req.getRequestDispatcher("/view/VerifyOTP.jsp").forward(req,res);
               }
               else
                   
               {
                   String message = " No Offender with this number";
                   
                req.setAttribute("message",message);
                    req.getRequestDispatcher("/view/OTP.jsp").forward(req,res);
               }
               
               
         }
       }
       catch(Exception e)
               {
               System.out.println("\n Error"+e);
               }
             
       
    //   req.getRequestDispatcher("/view/VerifyOTP.jsp").forward(req,res);
         
       
           // pw.close();
     }
    }


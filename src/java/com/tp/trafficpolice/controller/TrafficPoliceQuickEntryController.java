package com.tp.trafficpolice.controller;

import com.tp.tableClasses.TrafficPoliceQuickBean;
import com.tp.trafficpolice.model.TrafficPoliceQuickEntryModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrafficPoliceQuickEntryController extends HttpServlet
{
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try
        {
           ServletContext ctx = getServletContext();
            TrafficPoliceQuickEntryModel trafficPoliceModel= new TrafficPoliceQuickEntryModel();
            trafficPoliceModel.setDriverClass(ctx.getInitParameter("driverClass"));
            trafficPoliceModel.setConnectionString(ctx.getInitParameter("connectionString"));
            trafficPoliceModel.setDb_userName(ctx.getInitParameter("db_userName"));
            trafficPoliceModel.setDb_userPasswrod(ctx.getInitParameter("db_userPasswrod"));
            trafficPoliceModel.setConnection();
            TrafficPoliceQuickBean trafficPolice=new TrafficPoliceQuickBean();
            String task=request.getParameter("task");
            String transport_type=request.getParameter("transport_type");
            if(transport_type==null)
            {transport_type="Commercial";}
            
           // String offence_city="";
           // String zone="";

            String[] vehicle_no=null;
            String JQuery=request.getParameter("action1");
            String q=request.getParameter("q");
            if(JQuery!=null)
            {
                List list=null;
                String offence_city="";
                String zone="";
                String offence_location="";
                String act="";
                String offence_type="";
                String offence_code="";
                String act_origin="";
               if(JQuery.equals("getOffenceCity") || JQuery.equals("getZone") || JQuery.equals("getOffenceLocation"))
               {
               offence_city=request.getParameter("action2");
               zone=request.getParameter("action3");
               offence_location=request.getParameter("action4");
                }
               else
                {
                    act=request.getParameter("action2");
                    offence_type=request.getParameter("action3");
                    offence_code=request.getParameter("action4");
                    act_origin=request.getParameter("action5");

                }
                if(JQuery.equals("getActOrigin"))
                {
                    list=trafficPoliceModel.getActOrigin(q);
                }
                else if(JQuery.equals("getAct"))
                {
                    list=trafficPoliceModel.getAct(q,act_origin);
                }
                else if(JQuery.equals("getOffenceType"))
                {
                    list=trafficPoliceModel.getOffenceType(q,act_origin);
                }
                else if(JQuery.equals("getOffenceCode"))
                {
                    list=trafficPoliceModel.getOffenceCode(q,act_origin);
                }
                else if(JQuery.equals("getPanaltyAmount"))
                {
                   // list=trafficPoliceModel.panaltyAmount(q);
                }
                else if(JQuery.equals("getOfficerName"))
                {
                    list=trafficPoliceModel.getOfficerName(q);
                }
                else if(JQuery.equals("getOffenceCity"))
                {

                    list=trafficPoliceModel.getOffenceCity(q,zone,offence_location);
                }
                else if(JQuery.equals("getOffenceLocation"))
                {
                    
                    list=trafficPoliceModel.getOffenceLocation(q,offence_city,zone);
                }
                else if(JQuery.equals("getZone"))
                {

                    list=trafficPoliceModel.getZone(q,offence_city,offence_location);
                }
                else if(JQuery.equals("getSelect"))
                {
                    String commercial_type=request.getParameter("commercial_type");
                    list=trafficPoliceModel.select(act_origin,act,offence_type,offence_code,commercial_type);
                }
                else if(JQuery.equals("getReceipt_book_no"))
                {
                    String officerName=request.getParameter("action2");
                    list=trafficPoliceModel.getReceiptBookNo(q,officerName);
                }
                else if(JQuery.equals("getReceipt_page_no"))
                {
                    String receipt_book_no=request.getParameter("action2");
                    list=trafficPoliceModel.getReceiptPageNo(q,receipt_book_no);
                }
                else if(JQuery.equals("getOffender_city"))
                {
                    list=trafficPoliceModel.getOffenderCity(q);
                }
                else{}
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                return;
            }


 String total_no_of_rows=request.getParameter("Total_No._of_Rows");
             int total_no_of_row=Integer.parseInt(total_no_of_rows);
                       if(task.equals("Save"))
                       {
                          //String traffic_police_id[] = {"1"};
                    try {

                      String  traffic_police_id[] = request.getParameterValues("traffic_police_id");
                      trafficPolice.setTraffic_police_idM(traffic_police_id);
                      // meter_id may or may NOT be available i.e. it can be update or new record.
                    }
                    catch (Exception e)
                    {
                      // traffic_police_id[i] = "0";
                    }
                            
                      }

                    String no_of_offence = request.getParameter("no_of_offence");
                    if(no_of_offence == null || no_of_offence.isEmpty())
                        no_of_offence = "0";
                    int noOfOffence = Integer.parseInt(no_of_offence);
                    String[] offence_code = new String[noOfOffence];
                    //String[] offenceCodeValues = new String[1];
                    String[] officerName=request.getParameterValues("Officer_Name");
                    String[] jayaram_no=request.getParameterValues("jarayam_no");
                    
                    
                    String[] offence_type=request.getParameterValues("offence_type");
                   // String[] vehicle_type=request.getParameterValues("vehicle_type");
                    String[] vehicle_no_state = request.getParameterValues("vehicle_no_state");
                    String[] vehicle_no_city = request.getParameterValues("vehicle_no_city");
                    String[] vehicle_no_series = request.getParameterValues("vehicle_no_series");
                    String[] vehicle_no_digits = request.getParameterValues("vehicle_no_digits");
                    vehicle_no = new String[vehicle_no_digits.length];
                    for(int i=0;i<vehicle_no_digits.length;i++)
                   {
                       vehicle_no[i] = vehicle_no_state[i] + vehicle_no_city[i] + vehicle_no_series[i] + vehicle_no_digits[i];
                    }
                    //String[] remark=request.getParameterValues("remark");
                    String[] offender_name=request.getParameterValues("offender_name");
                    String[] offender_license_no=request.getParameterValues("offender_license_no");
                  String[]  offence_city = request.getParameterValues("offence_city");
                  String[]  Offence_Location = request.getParameterValues("Offence_Location");
                   String[] zone = request.getParameterValues("Zone");

                    String[] act_origin=request.getParameterValues("act_origin");
                    String[] deposited_amount=request.getParameterValues("total_penalty_amount");
                    String[] offence_date=request.getParameterValues("offence_date");
                    String process_type=request.getParameter("process_type");
                    if(process_type==null)process_type="";
                    String[] offender_age=request.getParameterValues("offender_age");
                    String[] father_name=request.getParameterValues("father_name");
                    String[] offender_address=request.getParameterValues("offender_address");
                    String[] offender_city=request.getParameterValues("offender_city");

                   // offenceCodeValues[0] = request.getParameter("offenceCodeValues");
                    for(int i = 0; i < noOfOffence; i++ )
                        offence_code[i] = request.getParameter("offence_code");

                    if(process_type.equals("Court"))
                    {
                         trafficPolice.setCase_noM( request.getParameterValues("case_no"));
                          trafficPolice.setCase_dateM(request.getParameterValues("case_date"));
                    }
                      else
                    {
                        String[] receipt_no=request.getParameterValues("receipt_page_no");
                        trafficPolice.setReceipt_page_noM(receipt_no);
                    String[] reciept_book_no=request.getParameterValues("receipt_book_no");
                      trafficPolice.setReceipt_noM(reciept_book_no);

                    }

                    trafficPolice.setOffence_typeM(offence_code);
                    trafficPolice.setOfficer_nameM(officerName);

                    trafficPolice.setJarayam_noM(jayaram_no);

                    trafficPolice.setOffence_typeM(offence_type);
                   // trafficPolice.setVehicle_typeM(vehicle_type);
                   // trafficPolice.setRemarkM(remark);
                    trafficPolice.setOffender_nameM(offender_name);
                    trafficPolice.setOffender_license_noM(offender_license_no);
                    trafficPolice.setCityM(offence_city);


                    trafficPolice.setAct_originM(act_origin);
                    trafficPolice.setDeposited_amountM(deposited_amount);
                    trafficPolice.setOffence_dateM(offence_date);
                    trafficPolice.setProcessing_type(process_type);
                    trafficPolice.setOffender_ageM(offender_age);
                    trafficPolice.setFather_nameM(father_name);
                    trafficPolice.setOffender_address_M(offender_address);
                    trafficPolice.setOffender_cityM(offender_city);
                    trafficPolice.setVehicle_no_stateM(vehicle_no_state);
                    trafficPolice.setVehicle_no_seriesM(vehicle_no_series);
                    trafficPolice.setVehicle_no_cityM(vehicle_no_city);
                    trafficPolice.setVehicle_no_digitsM(vehicle_no_digits);
                    trafficPolice.setVehicle_noM(vehicle_no);
                    trafficPolice.setZoneM(zone);
                    trafficPolice.setTotal_no_of_rows(total_no_of_row);
                    String[] act=request.getParameterValues("act");
                    trafficPolice.setActM(act);
                    trafficPolice.setOffence_placeM(Offence_Location);
                    if(task.equals("Save"))
                    {
                        trafficPoliceModel.insertData(trafficPolice);
                    }
                    RequestDispatcher rd=request.getRequestDispatcher("/view/trafficPolice/trafficPoliceQuickEntryView.jsp");
                    rd.forward(request, response);
                     }
        catch(Exception e)
        {
            System.out.print(e);
        }
        finally
        {
            out.close();
        }
        }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

import static com.bms.shift.model.ShiftLoginModel.unicodeToKruti;
import com.tp.tableClasses.MISBean;
import com.tp.tableClasses.userEntryByImageBean;
import com.tp.trafficpolice.model.MISModel;
import com.tp.trafficpolice.model.MailSchedularModel;
import com.tp.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author acer pc
 */
public class MISController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static List<MISBean> count_list = new ArrayList();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        
        int cnt=1;
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        MISModel mis_Model = new MISModel();
        mis_Model.setDriverClass(ctx.getInitParameter("driverClass"));
        mis_Model.setConnectionString(ctx.getInitParameter("connectionString"));
        mis_Model.setDb_username(ctx.getInitParameter("db_userName"));
        mis_Model.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        mis_Model.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        MailSchedularModel mailSchedularModel = new MailSchedularModel();
       
        String    searchFromDate = null, searchToDate = null;
       String searchOfficerName=null;     
            searchFromDate = request.getParameter("searchFromDate");
            searchToDate = request.getParameter("searchToDate");
            
            searchOfficerName = request.getParameter("searchOfficerName");
            
            try {
                
                if (searchFromDate == null) {
                    searchFromDate = "";
                }
                else
                {

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Date d = sdf.parse( "12/08/2010");

// exactly after here is useful for you
sdf.applyPattern("yyyy-MM-dd");
searchFromDate = sdf.format(d);
                }
                if (searchToDate == null) {
                    searchToDate = "";
                }
                else
                {

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Date d = sdf.parse(searchToDate);

// exactly after here is useful for you
sdf.applyPattern("yyyy-MM-dd");
searchToDate = sdf.format(d);
                }
                
    }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("error in KeyPersonController setting searchOrganisation value" + e);
            }
            
            
            String mountingType = "";
        Map<String, String> map = new HashMap<String, String>();
        try {
              String JQstring = request.getParameter("action1");  
               String q = request.getParameter("q");   
            if (JQstring == null) {
                   JQstring = "";
                             }
            if (JQstring != null) {
                  
                    //PrintWriter out = response.getWriter();
                   List<String> list = null;
             if (JQstring.equals("getOfficerSearchList")) {
                        list = MISModel.getOfficerSearchList(q);   
             }
             
            if (JQstring.equals("sendReportMail")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty() && request.getParameter("action2") != " ") {
                        String date = request.getParameter("action2");
                        
                        String s[]=date.split(" ");
                        String searchFromDate1=s[0];
                        String searchToDate1=s[1];
                        //list = tubeWellSurveyModel.getLocationUsingLatLong(lat1,long1);
                        String result=mailSchedularModel.generateREport(cnt, ctx, searchFromDate1, searchToDate1,searchOfficerName);
                        //out.println(result);
                        return;
                    }

                }
                      Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                   System.out.println(data);
                }
                MISModel.closeConnection();
               System.out.flush();
               System.out.close();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
    
        
        String task1 = request.getParameter("task1");
            if (task1 == null) {
            task1 = "";
                   }
        
      
            
            if (searchOfficerName == null) {
                searchOfficerName = "";
            }
            
          
            
           
//            if (task1.equals("generateReport")) {
//                String jrxmlFilePath;
//                List list = null;
//                try {
//                    userEntryByImageBean userEntryByImageBean1 = new userEntryByImageBean();
//                    String traffic_police_id = request.getParameter("traffic_police_id");
//                    ////////////////////////////////////////////////////////////////////////
//                    List<String> imagePathList = tubeWellSurveyModel.getImagePath(traffic_police_id);
//
//                    int listSize = imagePathList.size();
//                    for(int i=0;i<listSize;i++){
//                        if(i ==0){
//                            userEntryByImageBean1.setImgPath(imagePathList.get(i));
//                        }
//                        if(i ==1){
//                            userEntryByImageBean1.setImgPath1(imagePathList.get(i));
//                        }
//                    }
//
//                    response.setContentType("application/pdf");
//                    ServletOutputStream servletOutputStream = response.getOutputStream();
//                    
//                    //jrxmlFilePath = ctx.getRealPath("/report/shift/onlineChallanReport3.jrxml");
//                    jrxmlFilePath = ctx.getRealPath("/report/shift/onlineChallanReport4.jrxml");
//                    //list = tubeWellSurveyModel.showMISData(traffic_police_id);
//                    list = tubeWellSurveyModel.showMISData1(traffic_police_id,userEntryByImageBean1);
//                   // list.add(userEntryByImageBean1);
//                    byte[] reportInbytes = tubeWellSurveyModel.generateRecordList(jrxmlFilePath, list);
//                    response.setContentLength(reportInbytes.length);
//                    servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                    servletOutputStream.flush();
//                    servletOutputStream.close();
//                    tubeWellSurveyModel.closeConnection();
//                    return;
//                } catch (Exception e) {
//                    System.out.println("Exception is:" + e);
//                }
//            }
//            
               
            if (task1.equals("generateReport")) {
            String jrxmlFilePath;
             List list = null;
             try{
                  
                 response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/Mis_report.jrxml");
          
            list = mis_Model.showAllData1(searchOfficerName,searchFromDate, searchToDate);
            byte[] reportInbytes = mis_Model.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            //mis_Model.closeConnection();
            return;
        } catch(Exception e)
        {
            System.out.println("Exception is:" + e);
        }
            }


          else if (task1.equals("Show All Records")) {
                searchOfficerName = "";
               
                searchToDate = "";
             
            }
            String search = "";
            search = request.getParameter("search");
            try {
                if (search == null) {
                    search = "";
                }
            } catch (Exception e) {
                System.out.printf("Exception"+e);
                
            }
        
        
        
        
        
        
        try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            }
            /*if (task.equals("Search") || task.equals("clear")) {

            lowerLimit = 0;
            }*/
           
            noOfRowsInTable = mis_Model.getNoOfRows( searchFromDate, searchToDate, searchOfficerName);                  // get the number of records (rows) in the table.

            if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
            else if (buttonAction.equals("Previous")) {
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0) {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else {
                    lowerLimit = temp;
                }
            } else if (buttonAction.equals("First")) {
                lowerLimit = 0;
            } else if (buttonAction.equals("Last")) {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
                //noOfRowsToDisplay = lowerLimit + noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }

//            if (task1.equals("Save") || task1.equals("Delete") || task1.equals("Save AS New") || task1.equals("Add All Records")) {
//                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
//            }
            // Logic to show data in the table.
         List<MISBean> showDatalist = mis_Model.showData2(lowerLimit, noOfRowsToDisplay, searchFromDate, searchToDate,searchOfficerName);
            lowerLimit = lowerLimit + showDatalist.size();
            noOfRowsTraversed = showDatalist.size();
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

//////////////////////////////////////////////////////////////////////////////////////////////////////

            Date dt = new Date();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String cut_dt = df.format(dt);
            request.setAttribute("cut_dt", cut_dt);
//            request.setAttribute("officerNameList", tubeWellSurveyModel.getofficerNameList());
//            request.setAttribute("bookNoList", tubeWellSurveyModel.getBookNoList());
//            request.setAttribute("vehicleTypeList", tubeWellSurveyModel.vehicleTypeList());
            request.setAttribute("list", showDatalist);
//            request.setAttribute("IDGenerator", new UniqueIDGenerator());
//            request.setAttribute("offenceTypeList", tubeWellSurveyModel.getOffenceTypeList());
//            request.setAttribute("actTypeList", tubeWellSurveyModel.getActTypeList());
//        
           request.setAttribute("officerNameList", MISModel.getofficerSearchList());
              
            request.setAttribute("searchFromDate",searchFromDate );
            request.setAttribute("searchToDate",searchToDate);
             request.setAttribute("searchOfficerName", searchOfficerName);
        request.setAttribute("count_list",count_list);
        
        
        
        
        
        request.getRequestDispatcher("/mis_page").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

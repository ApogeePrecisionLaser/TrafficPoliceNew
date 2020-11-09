package com.tp.dataEntry.controller;

import com.tp.dataEntry.model.CityModel;
import com.tp.tableClasses.CityBean;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CityController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException
    {
         int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
         
        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        CityModel cityModel = new CityModel();

        cityModel.setDriver(ctx.getInitParameter("driverClass"));
        cityModel.setUrl(ctx.getInitParameter("connectionString"));
        cityModel.setUser(ctx.getInitParameter("db_userName"));
        cityModel.setPassword(ctx.getInitParameter("db_userPasswrod"));
        cityModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        System.out.println(cityModel.getConnection());
        String task = request.getParameter("task");
         
        String searchCity= request.getParameter("searchCity");        

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");            
            String q = request.getParameter("q");   // field own input           
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getCity")) {
                     list = cityModel.getCity(q);
                }else if(JQstring.equals("getDivision")){
                    list = cityModel.getDivision(q);
                }else if (JQstring.equals("getDistrict"))
                {
                    String diviName = request.getParameter("diviName");
                    list = cityModel.getDistrict(q, diviName);
                }else if(JQstring.equals("getCityName"))
                {
                    String districtName = request.getParameter("districtName");
                    list = cityModel.getCityName(q, districtName);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                cityModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        } 
          
         if(searchCity==null)
             searchCity="";

         if(task==null)
            task="";
         else if(task.equals("generateMapReport"))//start from here
         {
            List listAll = null;
            String jrxmlFilePath;
            String search= request.getParameter("searchCity");            
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll=cityModel.showAllData(search);            
            jrxmlFilePath = ctx.getRealPath("/report/CityReport.jrxml");
            byte[] reportInbytes = cityModel.generateMapReport(jrxmlFilePath,listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
         }else if(task.equals("generateMapXlsReport"))
         {     String jrxmlFilePath;
              List listAll=null;
               String search= request.getParameter("searchCity");
                       response.setContentType("application/vnd.ms-excel");
                       response.addHeader("Content-Disposition", "attachment; filename=city.xls");
                       ServletOutputStream servletOutputStream = response.getOutputStream();
                       jrxmlFilePath = ctx.getRealPath("/report/CityReport.jrxml");
                       listAll=cityModel.showAllData(search);
                       ByteArrayOutputStream reportInbytes =cityModel.generateCityXlsRecordList(jrxmlFilePath, listAll);
                       response.setContentLength(reportInbytes.size());
                       servletOutputStream.write(reportInbytes.toByteArray());
                       servletOutputStream.flush();
                       servletOutputStream.close();
                       return;
         }
         else if(task.equals("Search All Records"))
             searchCity="";        
          else if(task.equals("Save all records")||task.equals("Save As New")||task.equals("Save"))
          {
              if(task.equals("Save all records"))
                cityModel.insertRecord(request.getParameterValues("divisionName"),request.getParameterValues("districtName"),request.getParameterValues("city_id"),request.getParameterValues("cityName"),request.getParameterValues("cityDescription"));
              else if(task.equals("Save As New"))
              {
                String city_id[]={"1"};
                cityModel.insertRecord(request.getParameterValues("divisionName"),request.getParameterValues("districtName"),city_id,request.getParameterValues("cityName"),request.getParameterValues("cityDescription"));
              }
             else if(task.equals("Save"))
                 cityModel.updateRecord(request.getParameter("divisionName"),request.getParameter("districtName"),request.getParameter("cityId"),request.getParameter("cityName"),request.getParameter("cityDescription"));
          }else if(task.equals("Delete"))
             cityModel.deleteRecord(Integer.parseInt(request.getParameter("cityId")));
       
         noOfRowsInTable = cityModel.getTotalRowsInTable(searchCity);
        
            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }      
           
            if (task.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
            else if (task.equals("Previous")) {
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0) {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else {
                    lowerLimit = temp;
                }
            } else if (task.equals("First")) {
                lowerLimit = 0;
            } else if (task.equals("Last")) {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;               
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Add All Records")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
              }                
         ArrayList<CityBean> list = cityModel.getAllRecords(lowerLimit,noOfRowsToDisplay,searchCity);
         lowerLimit = lowerLimit + list.size();
         noOfRowsTraversed = list.size();

         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("searchCity", searchCity);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("message", cityModel.getMessage());            
            request.setAttribute("messageBGColor", cityModel.getMessageBGColor());
            request.setAttribute("cityList", list);
            cityModel.closeConnection();

                request.getRequestDispatcher("cityView").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException
    {   
        doGet(request, response);
    }
}

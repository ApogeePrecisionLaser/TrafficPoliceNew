

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.trafficpolice.controller;

import com.tp.tableClasses.VehicleTypeBean;
import com.tp.trafficpolice.model.VehicleTypeModel;
import com.tp.util.UniqueIDGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JPSS
 */
public class VehicleTypeController extends HttpServlet {
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is state Controller....");
        ServletContext ctx = getServletContext();
        VehicleTypeModel vehicleTypeModel = new VehicleTypeModel();
        vehicleTypeModel .setDriverClass(ctx.getInitParameter("driverClass"));
        vehicleTypeModel.setDb_username(ctx.getInitParameter("db_userName"));
        vehicleTypeModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        vehicleTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        vehicleTypeModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");
       String makeType= request.getParameter("makeType");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getStatusType")) {
                    list = vehicleTypeModel.geVehicleType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                vehicleTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }


        if (task == null) {
            task = "";
        }
         if (task.equals("generateMapReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/VehicleTypeReport.jrxml");
                        list=vehicleTypeModel.showAllData();
                        byte[] reportInbytes =vehicleTypeModel.generateRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        vehicleTypeModel.closeConnection();
                        return;
            }else if (task.equals("generateExcelReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment; filename=VehicleType_report.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/VehicleTypeReport.jrxml");
                        list=vehicleTypeModel.showAllData();
                        ByteArrayOutputStream reportInbytes =vehicleTypeModel.generateExcelList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.size());
                        servletOutputStream.write(reportInbytes.toByteArray());
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        vehicleTypeModel.closeConnection();
                        return;
            }
        if (task.equals("Delete")) {
         vehicleTypeModel.deleteRecord(Integer.parseInt(request.getParameter("vehicle_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int vehicle_type_id;
            try {
                vehicle_type_id = Integer.parseInt(request.getParameter("vehicle_type_id"));
            } catch (Exception e) {
                vehicle_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                vehicle_type_id = 0;
            }
           VehicleTypeBean vehicleTypeBean= new VehicleTypeBean();
            vehicleTypeBean.setVehicle_type_id(vehicle_type_id);
            vehicleTypeBean.setVehicle_type(request.getParameter("vehicle_type"));
         //   vehicleTypeBean.setCommercial_type(request.getParameter("commercial_type"));
            vehicleTypeBean.setRemark(request.getParameter("remark"));
            if (vehicle_type_id == 0) {
                System.out.println("Inserting values by model......");
                vehicleTypeModel.insertRecord(vehicleTypeBean);
            } else {
                System.out.println("Update values by model........");
                vehicleTypeModel.updateRecord(vehicleTypeBean);
            }
        }

        String   searchVehicleType = request.getParameter("searchVehicleType");
        try {
            if ( searchVehicleType == null) {
                searchVehicleType= "";
            }
        } catch (Exception e) {
        }
        // Start of Search Table
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
        if (task.equals("Show All Records")) {
             searchVehicleType= "";
        }
        System.out.println("searching.......... " +  searchVehicleType);
        noOfRowsInTable = vehicleTypeModel.getNoOfRows( searchVehicleType);                  // get the number of records (rows) in the table.

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
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } 

         List<VehicleTypeBean> vehicleTypeList =vehicleTypeModel.showData(lowerLimit,noOfRowsToDisplay,searchVehicleType);
        lowerLimit = lowerLimit + vehicleTypeList.size();
        noOfRowsTraversed = vehicleTypeList.size();
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

        request.setAttribute("vehicleTypeList",vehicleTypeList);
       request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
      request.setAttribute("searchVehicleType", searchVehicleType);
             request.setAttribute("IDGenerator", new UniqueIDGenerator());
         request.setAttribute("message", vehicleTypeModel.getMessage());
 request.getRequestDispatcher("/vehicleType").forward(request, response);
    }
          @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);}


}

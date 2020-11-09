/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.licenseVehicle.controller;


import com.tp.licenseVehicle.model.VehicleDetailModel;
import com.tp.licenseVehicle.tableClasses.VehicleDetailBean;
import com.trafficpolice.general.UniqueIDGenerator;
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
public class VehicleDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is VehicleDetailController....");
        ServletContext ctx = getServletContext();
        VehicleDetailModel vehicleDetailModel = new VehicleDetailModel();
        vehicleDetailModel.setDriverClass(ctx.getInitParameter("driverClass"));
        vehicleDetailModel.setDb_username(ctx.getInitParameter("db_userName"));
        vehicleDetailModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        vehicleDetailModel.setConnectionString(ctx.getInitParameter("connectionString"));
        vehicleDetailModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
           
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                 if (JQstring.equals("getModelList")) {
                    list = vehicleDetailModel.getModelList(q);
                } else if (JQstring.equals("getSearchVehicleNo")) {
                    list = vehicleDetailModel.getVehicleNoList(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    if (data.equals("Disable")) {
                        out.print(data);
                    } else {
                        out.println(data);
                    }
                }

                vehicleDetailModel.closeConnection();
                return;
            }
             
        } catch (Exception e) {
            System.out.println("\n Error --SiteListController get JQuery Parameters Part-" + e);
        }
       

        String task = request.getParameter("task");

        if (task == null) {
            task = "";
        }

        String searchOfficeCode = request.getParameter("searchOfficeCode");
        String searchOfficeName = request.getParameter("searchOfficeName");
        String searchVehicleNo = request.getParameter("searchVehicleNo");
        String searchFromDate = request.getParameter("searchFromDate");
        String searchToDate = request.getParameter("searchToDate");
        try {
            if (searchOfficeCode == null) {
                searchOfficeCode = "";
            }
            if (searchOfficeName == null) {
                searchOfficeName = "";
            }
            if (searchVehicleNo == null) {
                searchVehicleNo = "";
            }
            if (searchFromDate == null) {
                searchFromDate = "";
            }
            if (searchToDate == null) {
                searchToDate = "";
            }
        } catch (Exception e) {
        }

        if (task.equals("Cancel")) {
            vehicleDetailModel.cancelRecord(request.getParameter("vehicle_detail_id"), request.getParameter("vehicle_revision_no"));  // Pretty sure that organisation_type_id will be available.
        }
        if (task.equals("PRINTRecordList")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/vehicleDetailReport.jrxml");
            list = vehicleDetailModel.showAllData(searchVehicleNo);
            byte[] reportInbytes = vehicleDetailModel.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            vehicleDetailModel.closeConnection();
            return;
        } else if(task.equals("PRINTXlsRecordList")){
            String jrxmlFilePath;
            List listAll=null;
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=city.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/vehicleDetailReport.jrxml");
            listAll=vehicleDetailModel.showAllData(searchVehicleNo);
            ByteArrayOutputStream reportInbytes =vehicleDetailModel.generateVehicleXlsRecordList(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }

        else if (task.equals("Save") || task.equals("Save As New")||task.equals("Revise")|| task.equals("Add All Records")) {
            int vehicle_detail_id, vehicle_revision_no;
            try {
                vehicle_detail_id = Integer.parseInt(request.getParameter("vehicle_detail_id"));
                vehicle_revision_no = Integer.parseInt(request.getParameter("vehicle_revision_no"));
            } catch (Exception e) {
                vehicle_detail_id = 0;
                vehicle_revision_no = 0;
            }
            VehicleDetailBean vehicleDetailBean = new VehicleDetailBean();
            if (task.equals("Save As New")) {
                    vehicle_detail_id = 0;
                    String[] id = new String[1];
                    String z_id = "1";
                    id[0] = z_id;
                    //vehicleDetailBean.setLicense_detail_id(id);
            }
            if (task.equals("Add All Records") ) {
               //vehicleDetailBean.setVehicle_detail_idM(request.getParameterValues("license_detail_id"));
            }
            vehicleDetailBean.setVehicle_detail_id(vehicle_detail_id);
            vehicleDetailBean.setVehicle_revision_no(vehicle_revision_no);
            vehicleDetailBean.setVehicle_no(request.getParameter("vehicle_no"));
            vehicleDetailBean.setModel(request.getParameter("model"));
            vehicleDetailBean.setEngine_no(request.getParameter("engine_no"));
            vehicleDetailBean.setOwner_name(request.getParameter("owner_name"));
            int age = 0;
            try{
                age = Integer.parseInt(request.getParameter("age"));
            }catch(Exception ex){
                age = 0;
            }
            vehicleDetailBean.setOwner_age(age);
            vehicleDetailBean.setOwner_address(request.getParameter("address"));
            vehicleDetailBean.setOwner_contact_no(request.getParameter("contact_no"));
            vehicleDetailBean.setRemark(request.getParameter("remark"));
            //vehicleDetailBean.setLanguage_type(request.getParameter("language"));
            /*if (license_detail_id >= 0) {
                System.out.println("Inserting values by model......");
                vehicleDetailModel.insertRecord(vehicleDetailBean);

            }*/
            if (vehicle_detail_id == 0  || task.equals("Add All Records")) {
                vehicleDetailModel.insertRecord(vehicleDetailBean);
            } else if(task.equals("Revise")){
                //vehicleDetailBean.setLicense_detail_id(Integer.parseInt(request.getParameter("query_type_id").trim()));
                vehicleDetailModel.reviseRecord(vehicleDetailBean);
            }
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
            searchVehicleNo = "";
        }

        //System.out.println("searching.......... " +  searchStatusType);
        noOfRowsInTable = vehicleDetailModel.getNoOfRows( searchOfficeName,searchOfficeCode, searchVehicleNo, searchFromDate, searchToDate);                  // get the number of records (rows) in the table.

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

        if (task.equals("Save") || task.equals("Cancel") || task.equals("Save As New")|| task.equals("Revise")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }

        List<VehicleDetailBean> vehicleDetailList = vehicleDetailModel.showData(lowerLimit, noOfRowsToDisplay, searchOfficeCode, searchOfficeName, searchVehicleNo, searchFromDate, searchToDate);
        lowerLimit = lowerLimit + vehicleDetailList.size();
        noOfRowsTraversed = vehicleDetailList.size();


        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("vehicleDetailList", vehicleDetailList);
        request.setAttribute("searchOfficeCode", searchOfficeCode);
        request.setAttribute("searchOfficeName", searchOfficeName);
        request.setAttribute("searchVehicleNo", searchVehicleNo);
        request.setAttribute("vehicleList", vehicleDetailModel.getVehicleTypeList());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", vehicleDetailModel.getMessage());
        request.getRequestDispatcher("vehicleDetailView").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}

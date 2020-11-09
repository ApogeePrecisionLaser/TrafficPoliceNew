/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.licenseVehicle.controller;


import com.tp.licenseVehicle.model.LicenseDetailModel;
import com.tp.licenseVehicle.tableClasses.LicenseDetailBean;
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
public class LicenseDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is LicenseDetailController....");
        ServletContext ctx = getServletContext();
        LicenseDetailModel licenseDetailModel = new LicenseDetailModel();
        licenseDetailModel.setDriverClass(ctx.getInitParameter("driverClass"));
        licenseDetailModel.setDb_username(ctx.getInitParameter("db_userName"));
        licenseDetailModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        licenseDetailModel.setConnectionString(ctx.getInitParameter("connectionString"));
        licenseDetailModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
           
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getSearchLicenseNo")) {
                    list = licenseDetailModel.getLicenseNoList(q);
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
                licenseDetailModel.closeConnection();
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
        String searchLicenseNo = request.getParameter("searchLicenseNo");
        String searchFromDate = request.getParameter("searchFromDate");
        String searchToDate = request.getParameter("searchToDate");
        try {
            if (searchOfficeCode == null) {
                searchOfficeCode = "";
            }
            if (searchOfficeName == null) {
                searchOfficeName = "";
            }
            if (searchLicenseNo == null) {
                searchLicenseNo = "";
            }
            if (searchFromDate == null) {
                searchFromDate = "";
            }
            if (searchToDate == null) {
                searchToDate = "";
            }
        } catch (Exception e) {
        }

        if (task.equals("Delete")) {
            licenseDetailModel.deleteRecord(request.getParameter("license_detail_id"));  // Pretty sure that organisation_type_id will be available.
        }
        if (task.equals("PRINTRecordList")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/licenseDetailReport.jrxml");
            list = licenseDetailModel.showAllData(searchLicenseNo);
            byte[] reportInbytes = licenseDetailModel.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            licenseDetailModel.closeConnection();
            return;
        }else if(task.equals("PRINTXlsRecordList")){
            String jrxmlFilePath;
            List listAll=null;
            String search= request.getParameter("searchCity");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=Licence.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/licenseDetailReport.jrxml");
            listAll=licenseDetailModel.showAllData(searchLicenseNo);
            ByteArrayOutputStream reportInbytes =licenseDetailModel.generateLicenceXlsRecordList(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
         }

        else if (task.equals("Save") || task.equals("Save As New")||task.equals("Update")|| task.equals("Add All Records")) {
            int license_detail_id;
            try {
                license_detail_id = Integer.parseInt(request.getParameter("license_detail_id"));
            } catch (Exception e) {
                license_detail_id = 0;
            }
            LicenseDetailBean licenseDetailBean = new LicenseDetailBean();
            if (task.equals("Save As New")) {
                    license_detail_id = 0;
                    String[] id = new String[1];
                    String z_id = "1";
                    id[0] = z_id;
                    //licenseDetailBean.setLicense_detail_id(id);
            }
           if (task.equals("Add All Records") ) {
               licenseDetailBean.setLicense_detail_idM(request.getParameterValues("license_detail_id"));
            }
            licenseDetailBean.setLicense_detail_id(license_detail_id);
            licenseDetailBean.setLicense_no(request.getParameter("license_no"));
            licenseDetailBean.setOwner_name(request.getParameter("owner_name"));
            int age = 0;
            try{
                age = Integer.parseInt(request.getParameter("age"));
            }catch(Exception ex){
                age = 0;
            }
            licenseDetailBean.setOwner_age(age);
            licenseDetailBean.setAddress(request.getParameter("address"));
            licenseDetailBean.setIssue_date(request.getParameter("issue_date"));
            licenseDetailBean.setValidity(request.getParameter("validity"));
            licenseDetailBean.setIs_permanent(request.getParameter("is_permanent"));
            licenseDetailBean.setVehicle_type_id(Integer.parseInt(request.getParameter("vehicle_type")));
            licenseDetailBean.setRemark(request.getParameter("remark"));
            //licenseDetailBean.setLanguage_type(request.getParameter("language"));
            /*if (license_detail_id >= 0) {
                System.out.println("Inserting values by model......");
                licenseDetailModel.insertRecord(licenseDetailBean);

            }*/
            if (license_detail_id == 0  || task.equals("Add All Records")) {
                licenseDetailModel.insertRecord(licenseDetailBean);
            } else if(task.equals("Update")){
                //licenseDetailBean.setLicense_detail_id(Integer.parseInt(request.getParameter("query_type_id").trim()));
                licenseDetailModel.updateRecord(licenseDetailBean);
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
            searchLicenseNo = "";
        }

        //System.out.println("searching.......... " +  searchStatusType);
        noOfRowsInTable = licenseDetailModel.getNoOfRows( searchOfficeName,searchOfficeCode, searchLicenseNo, searchFromDate, searchToDate);                  // get the number of records (rows) in the table.

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

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save As New")|| task.equals("Update")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }

        List<LicenseDetailBean> licenseDetailList = licenseDetailModel.showData(lowerLimit, noOfRowsToDisplay, searchOfficeCode, searchOfficeName, searchLicenseNo, searchFromDate, searchToDate);
        lowerLimit = lowerLimit + licenseDetailList.size();
        noOfRowsTraversed = licenseDetailList.size();


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
        request.setAttribute("licenseDetailList", licenseDetailList);
        request.setAttribute("searchOfficeCode", searchOfficeCode);
        request.setAttribute("searchOfficeName", searchOfficeName);
        request.setAttribute("searchLicenseNo", searchLicenseNo);
        request.setAttribute("vehicleList", licenseDetailModel.getVehicleTypeList());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", licenseDetailModel.getMessage());
        request.getRequestDispatcher("licenseDetailView").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}

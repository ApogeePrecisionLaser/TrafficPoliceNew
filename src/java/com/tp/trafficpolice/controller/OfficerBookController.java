/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

import com.tp.tableClasses.OfficerBookBean;

import com.tp.trafficpolice.model.OfficerBookModel;
import com.trafficpolice.general.UniqueIDGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class OfficerBookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is state Controller....");
        ServletContext ctx = getServletContext();
        OfficerBookModel officerBookModel = new OfficerBookModel();
        officerBookModel.setDriverClass(ctx.getInitParameter("driverClass"));
        officerBookModel.setDb_username(ctx.getInitParameter("db_userName"));
        officerBookModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        officerBookModel.setConnectionString(ctx.getInitParameter("connectionString"));
        officerBookModel.setConnection();
        //request.setCharacterEncoding("UTF-8");
       // response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
           
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getBookNoList")){
                    list = officerBookModel.getBookNoList(q);
                } else if (JQstring.equals("getOrganization")) {
                    list = officerBookModel.getOrganisation_Name(q);
                } else if (JQstring.equals("getOrgOfficeCode")) {
                    list = officerBookModel.getOrgOfficeCode(q);
                } else if (JQstring.equals("getOrgOfficeName")) {
                    list = officerBookModel.getOrgOfficeName(q);
                } else if (JQstring.equals("getOfficerNameList")) {
                    list = officerBookModel.getKeyPerson(q, request.getParameter("action2"));
                } else if(JQstring.equals("getOfficerData")){
                    String officer_data = officerBookModel.getOfficerData(request.getParameter("officer_name"));
                    out.print(officer_data);
                    return;
                }
                //                } else if (JQstring.equals("getDesignation")) {
                //                    list = officerBookModel.getDesignation(q, request.getParameter("emp_code"));
                //                }
                else if (JQstring.equals("searchOfficeCode")) {
                    list = officerBookModel.searchOfficeCode(q);
                } else if (JQstring.equals("getEmployeeCode")) {
                    list = officerBookModel.getEmployeeCode(q);
                }else if (JQstring.equals("getBookType")) {
                    list = officerBookModel.getBookType(q);
                }
                else if (JQstring.equals("getSearchOfficerName")) {
                    list = officerBookModel.getKeyPersonList((q));
                } else if (JQstring.equals("getOfficerName")) {
                    String response_data = officerBookModel.getKeyPerson(request.getParameter("emp_code").trim());
                    out.println(response_data);
                } else if (JQstring.equals("getDesignation")) {
                    String response_data = officerBookModel.getDesignation(request.getParameter("emp_code").trim());
                    out.println(response_data);
                }
                if (list != null){
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext()) {
                        String data = iter.next();
                        if (data.equals("Disable")) {
                            out.print(data);
                        } else {
                            out.println(data);
                        }
                    }
                }

                officerBookModel.closeConnection();
                return;
            }
             
        } catch (Exception e) {
            System.out.println("\n Error --SiteListController get JQuery Parameters Part-" + e);
        }
       

        String task = request.getParameter("task");

        if (task == null) {
            task = "";
        }
        if (task.equals("Delete")) {
            officerBookModel.deleteRecord(request.getParameter("book_no"));  // Pretty sure that organisation_type_id will be available.
        }
        if (task.equals("PRINTRecordList")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/OfficerBook.jrxml");
            list = officerBookModel.showAllData();
            byte[] reportInbytes = officerBookModel.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            officerBookModel.closeConnection();
            return;
        }else if (task.equals("PrintExcelList")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=OfficerBook_report.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/OfficerBook.jrxml");
            list = officerBookModel.showAllData();
            ByteArrayOutputStream reportInbytes = officerBookModel.generateExcelList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            officerBookModel.closeConnection();
            return;
        } else if (task.equals("Save")) {
            int book_no;
            try {
                book_no = Integer.parseInt(request.getParameter("book_no"));
            } catch (Exception e) {
                book_no = 0;
            }

            OfficerBookBean officerBookBean = new OfficerBookBean();
            officerBookBean.setBook_no(book_no);
            officerBookBean.setKey_person_name(request.getParameter("officer_name"));
            officerBookBean.setEmp_code(request.getParameter("employeeId"));
            officerBookBean.setDesignation(request.getParameter("designation"));
            officerBookBean.setBook_no(Integer.parseInt(request.getParameter("book_no")));
            officerBookBean.setFrom_no(request.getParameter("from_no"));
            officerBookBean.setTo_no(request.getParameter("to_no"));
            officerBookBean.setRemark(request.getParameter("remark"));
            officerBookBean.setIssue_date(request.getParameter("date"));
            officerBookBean.setStatus_type(request.getParameter("status_type"));
            officerBookBean.setBook_type(request.getParameter("book_type"));
            //officerBookBean.setLanguage_type(request.getParameter("language"));
            if (book_no >= 0) {
                System.out.println("Inserting values by model......");
                officerBookModel.insertRecord(officerBookBean);
            }

        } else if (task.equals("Revise")) {
            int book_revision = 0;
            try {
                book_revision = Integer.parseInt(request.getParameter("book_revision_no"));
            } catch (Exception e) {
                book_revision = 0;
            }
            OfficerBookBean officerBookBean = new OfficerBookBean();
            officerBookBean.setBook_revision_no(book_revision);
            officerBookBean.setKey_person_name(request.getParameter("officer_name"));
            officerBookBean.setEmp_code(request.getParameter("employeeId"));
            officerBookBean.setDesignation(request.getParameter("designation"));
            officerBookBean.setBook_no(Integer.parseInt(request.getParameter("book_no").trim()));
            officerBookBean.setFrom_no(request.getParameter("from_no"));
            officerBookBean.setTo_no(request.getParameter("to_no"));
            officerBookBean.setRemark(request.getParameter("remark"));
            officerBookBean.setIssue_date(request.getParameter("date"));
            officerBookBean.setStatus_type(request.getParameter("status_type"));
            officerBookBean.setBook_type(request.getParameter("book_type"));
            if (book_revision >= 0) {
                System.out.println("Revise values by model........");
                officerBookModel.reviseRecord(officerBookBean);
            }
        }
        /* try{
            String JQstring=request.getParameter("action1");
             PrintWriter out = response.getWriter();
        if (JQstring.equals("getOfficerName")) {
                    String response_data = officerBookModel.getKeyPerson(request.getParameter("emp_code").trim());
                    out.println(response_data);
                } else if (JQstring.equals("getDesignation")) {
                    String response_data = officerBookModel.getDesignation(request.getParameter("emp_code").trim());
                    out.println(response_data);
                }
              officerBookModel.closeConnection();
                return;
        }catch(Exception e){
            System.out.println("Error occur :" +e);
        }*/
        String searchOfficeCode = request.getParameter("searchOfficeCode");
        String searchOfficeName = request.getParameter("searchOfficeName");
        String searchOfficerName = request.getParameter("searchOfficerName");
        String searchFromDate = request.getParameter("searchFromDate");
        String searchToDate = request.getParameter("searchToDate");
        String searchBookType = request.getParameter("searchBookType");
        String searchStatusType = request.getParameter("searchStatusType");
        String searchActive = request.getParameter("searchActive");
        String searchBookNo = request.getParameter("searchBookNo");
        try {
            if (searchOfficeCode == null) {
                searchOfficeCode = "";
            }
            if (searchOfficeName == null) {
                searchOfficeName = "";
            }
            if (searchOfficerName == null) {
                searchOfficerName = "";
            }
            if (searchFromDate == null) {
                searchFromDate = "";
            }
            if (searchToDate == null) {
                searchToDate = "";
            }
            if (searchBookType == null) {
                searchBookType = "";
            }
            if(searchStatusType == null)
                searchStatusType = "";
            if(searchActive == null)
                searchActive = "";
            if(searchBookNo == null)
                searchBookNo = "";
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
            searchOfficeCode = "";
            searchBookType = "";
            searchStatusType = "";
            searchOfficeCode = "";
            searchOfficeName = "";
            searchOfficerName = "";
            searchActive = "";
            searchBookNo = "";
        }

        //System.out.println("searching.......... " +  searchStatusType);
        noOfRowsInTable = officerBookModel.getNoOfRows( searchOfficeName,searchOfficeCode, searchOfficerName, searchFromDate, searchToDate, searchBookType, searchStatusType, searchActive, searchBookNo);                  // get the number of records (rows) in the table.

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

        if (task.equals("Save") || task.equals("Delete") || task.equals("Revise")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }

        List<OfficerBookBean> officerBookList = officerBookModel.showData(lowerLimit, noOfRowsToDisplay, searchOfficeCode, searchOfficeName, searchOfficerName, searchFromDate, searchToDate, searchBookType, searchStatusType, searchActive, searchBookNo);
        lowerLimit = lowerLimit + officerBookList.size();
        noOfRowsTraversed = officerBookList.size();


        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        Date dt = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String cut_dt = df.format(dt);
        request.setAttribute("cut_dt", cut_dt);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("officerBookList", officerBookList);
        request.setAttribute("searchOfficeCode", searchOfficeCode);
        request.setAttribute("searchOfficeName", searchOfficeName);
        request.setAttribute("searchOfficerName", searchOfficerName);
        request.setAttribute("searchBookType", searchBookType);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", officerBookModel.getMessage());
        request.setAttribute("statusTypeList", officerBookModel.getStatusTypeList());
        request.setAttribute("searchStatusType", searchStatusType);
        request.setAttribute("searchActive", searchActive);
        request.setAttribute("searchBookNo", searchBookNo);
        request.getRequestDispatcher("officerBook").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}

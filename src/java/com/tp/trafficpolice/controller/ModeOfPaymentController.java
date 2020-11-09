/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

import com.tp.tableClasses.ModeOfPaymentBean;
import com.tp.trafficpolice.model.ModeOfPaymentModel;
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
 * @author Shobha
 */
public class ModeOfPaymentController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is state Controller....");
        ServletContext ctx = getServletContext();
        ModeOfPaymentModel modeOfPaymentModel = new ModeOfPaymentModel();
         modeOfPaymentModel .setDriverClass(ctx.getInitParameter("driverClass"));
        modeOfPaymentModel.setDb_username(ctx.getInitParameter("db_userName"));
        modeOfPaymentModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        modeOfPaymentModel.setConnectionString(ctx.getInitParameter("connectionString"));
        modeOfPaymentModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");
        String searchPaymentType= request.getParameter("searchPaymentType");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getPaymentType")) {
                    list = modeOfPaymentModel.getPaymentType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                modeOfPaymentModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

         if (task == null) {
            task = "";
        }
//        if (task.equals("generateMapReport")) {
//                        String jrxmlFilePath;
//                        List list=null;
//                        response.setContentType("application/pdf");
//                        ServletOutputStream servletOutputStream = response.getOutputStream();
//                        jrxmlFilePath = ctx.getRealPath("/report/statusReport.jrxml");
//                        list=modeOfPaymentModel.showAllData();
//                        byte[] reportInbytes =modeOfPaymentModel.generateRecordList(jrxmlFilePath,list);
//                        response.setContentLength(reportInbytes.length);
//                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                        servletOutputStream.flush();
//                        servletOutputStream.close();
//                        modeOfPaymentModel.closeConnection();
//                        return;
//            }else if (task.equals("generateExcelReport")) {
//                        String jrxmlFilePath;
//                        List list=null;
//                        response.setContentType("application/vnd.ms-excel");
//                        response.addHeader("Content-Disposition", "attachment; filename=status_report.xls");
//                        ServletOutputStream servletOutputStream = response.getOutputStream();
//                        jrxmlFilePath = ctx.getRealPath("/report/statusReport.jrxml");
//                        list=modeOfPaymentModel.showAllData();
//                        ByteArrayOutputStream reportInbytes =modeOfPaymentModel.generateExcelList(jrxmlFilePath,list);
//                        response.setContentLength(reportInbytes.size());
//                        servletOutputStream.write(reportInbytes.toByteArray());
//                        servletOutputStream.flush();
//                        servletOutputStream.close();
//                        modeOfPaymentModel.closeConnection();
//                        return;
//            }
        if (task.equals("Delete")) {
           modeOfPaymentModel.deleteRecord(Integer.parseInt(request.getParameter("payment_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int payment_type_id;
            try {
                payment_type_id = Integer.parseInt(request.getParameter("payment_type_id"));
            } catch (Exception e) {
                payment_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                payment_type_id = 0;
            }
           ModeOfPaymentBean modeOfPaymentBean= new ModeOfPaymentBean();
            modeOfPaymentBean.setPayment_type_id(payment_type_id);
           modeOfPaymentBean.setPayment_type(request.getParameter("payment_type"));
           modeOfPaymentBean.setRemark(request.getParameter("remark"));
            if (payment_type_id == 0) {
                System.out.println("Inserting values by model......");
                modeOfPaymentModel.insertRecord(modeOfPaymentBean);
            } else {
                System.out.println("Update values by model........");
                modeOfPaymentModel.updateRecord(modeOfPaymentBean);
            }
        }

       
        try {
            if ( searchPaymentType == null) {
                searchPaymentType = "";
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
             searchPaymentType = "";
        }
        System.out.println("searching.......... " +  searchPaymentType);
        noOfRowsInTable = modeOfPaymentModel.getNoOfRows( searchPaymentType);                  // get the number of records (rows) in the table.

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
        
         List<ModeOfPaymentBean> statusTypeList = modeOfPaymentModel.showData(lowerLimit,noOfRowsToDisplay,searchPaymentType);
        lowerLimit = lowerLimit + statusTypeList.size();
        noOfRowsTraversed = statusTypeList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

        request.setAttribute("statusTypeList", statusTypeList);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("searchPaymentType", searchPaymentType);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
         request.setAttribute("message", modeOfPaymentModel.getMessage());
    request.getRequestDispatcher("/paymentType").forward(request, response);
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

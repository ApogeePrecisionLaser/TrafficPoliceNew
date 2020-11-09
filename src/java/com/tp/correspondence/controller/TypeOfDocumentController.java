/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.correspondence.controller;

import com.tp.correspondence.model.TypeOfDocumentModel;
import com.tp.correspondence.tableClasses.TypeOfDocumentBean;
import com.tp.util.UniqueIDGenerator;
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
 * @author jpss
 */
public class TypeOfDocumentController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
        TypeOfDocumentModel typeOfDocumentModel = new TypeOfDocumentModel();
        typeOfDocumentModel .setDriverClass(ctx.getInitParameter("driverClass"));
        typeOfDocumentModel.setDb_username(ctx.getInitParameter("db_userName"));
        typeOfDocumentModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        typeOfDocumentModel.setConnectionString(ctx.getInitParameter("connectionString"));
        typeOfDocumentModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");
        String searchRemark = "";
        
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getRemarkList")) {
                    list = typeOfDocumentModel.gettypeOfDocumentList(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                typeOfDocumentModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

         if (task == null) {
            task = "";
        }
        if(task.equals("Search")){
            searchRemark = request.getParameter("searchRemark");
        }
        if (task.equals("generateMapReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/statusReport.jrxml");
                        list=typeOfDocumentModel.showAllData();
                        byte[] reportInbytes =typeOfDocumentModel.generateRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        typeOfDocumentModel.closeConnection();
                        return;
            }
        if (task.equals("Delete")) {
           typeOfDocumentModel.deleteRecord(Integer.parseInt(request.getParameter("type_of_document_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int typeOfDocument_id;
            try {
                typeOfDocument_id = Integer.parseInt(request.getParameter("type_of_document_id"));
            } catch (Exception e) {
                typeOfDocument_id = 0;
            }
            if (task.equals("Save AS New")) {
                typeOfDocument_id = 0;
            }
           TypeOfDocumentBean typeOfDocumentBean= new TypeOfDocumentBean();
           typeOfDocumentBean.setType_of_document_id(typeOfDocument_id);
           typeOfDocumentBean.setType_of_document(request.getParameter("type_of_document"));
           typeOfDocumentBean.setDescription(request.getParameter("description"));
            if (typeOfDocument_id == 0) {
                System.out.println("Inserting values by model......");
                typeOfDocumentModel.insertRecord(typeOfDocumentBean);
            } else {
                System.out.println("Update values by model........");
                typeOfDocumentModel.updateRecord(typeOfDocumentBean);
            }
        }

        try {
            if ( searchRemark == null) {
                searchRemark = "";
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
             searchRemark = "";
        }
        System.out.println("searching.......... " +  searchRemark);
        noOfRowsInTable = typeOfDocumentModel.getNoOfRows( searchRemark);                  // get the number of records (rows) in the table.

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

         List<TypeOfDocumentBean> DocumentTypeList = typeOfDocumentModel.showData(lowerLimit,noOfRowsToDisplay,searchRemark);
        lowerLimit = lowerLimit + DocumentTypeList.size();
        noOfRowsTraversed = DocumentTypeList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

        request.setAttribute("DocumentTypeList", DocumentTypeList);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("searchRemark", searchRemark);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", typeOfDocumentModel.getMessage());
        request.getRequestDispatcher("/typeOfDocumentView").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

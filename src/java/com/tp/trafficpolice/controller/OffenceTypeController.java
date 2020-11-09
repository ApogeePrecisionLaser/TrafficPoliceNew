/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

import com.tp.tableClasses.OffenceTypeBean;
import com.tp.trafficpolice.model.OffenceTypeModel;
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
public class OffenceTypeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is state Controller....");
        ServletContext ctx = getServletContext();
        OffenceTypeModel offenceTypeModel = new OffenceTypeModel();
        offenceTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        offenceTypeModel.setDb_username(ctx.getInitParameter("db_userName"));
        offenceTypeModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        offenceTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        offenceTypeModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");
        String searchOffenceType = request.getParameter("searchOffenceType");
        String searchOffenceCode = request.getParameter("searchOffenceCode");

        String searchAct = request.getParameter("searchAct");
        String searchActOrigin = request.getParameter("searchActOrigin");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            String act_origin="";
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getOffenceype")) {

                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                            act_origin = request.getParameter("action2").trim();
                           // request.setAttribute("s_division_id",division_id);
                       }
                    
                    
                    list = offenceTypeModel.getOffenceType(q,act_origin);
                }
                if (JQstring.equals("getOffenceCode")) {
                    list = offenceTypeModel.getOffenceCode(q);
                }
                if (JQstring.equals("getVehicleType")) {
                    list = offenceTypeModel.getVehicleType(q);
                }
                if (JQstring.equals("getSearchAct")) {
                    list = offenceTypeModel.getSearchAct(q);
                }

                if (JQstring.equals("getSearchActOrigin")) {
                    list = offenceTypeModel.getSearchActOrigin(q);
                }
                if (JQstring.equals("getActOrigin")) {
                    list = offenceTypeModel.getActOrigin(q);
                }

                if (JQstring.equals("getTransportationType")) {
                    list = offenceTypeModel.getTransportationType(q, request.getParameter("action2"));
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                offenceTypeModel.closeConnection();
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
            List list = null;
            String searchOffence_type = request.getParameter("searchOffeneceType");
            String searchOffence_code = request.getParameter("searchOffeneceCode");
            String searchAct1 = request.getParameter("searchAct");
            String searchActOrigin1= request.getParameter("searchActOrigin");
            if (searchOffence_type == null) {
                searchOffence_type = "";
            }
            if (searchOffence_code == null) {
                searchOffence_code = "";
            }
           
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/OffenceTypeReport.jrxml");
            list = offenceTypeModel.showAllData(searchOffence_type, searchOffence_code,searchAct1,searchActOrigin1);
            byte[] reportInbytes = offenceTypeModel.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            offenceTypeModel.closeConnection();
            return;                 
        }

        if (task.equals("generateMapReportExcel")) {
            String searchOffence_type = request.getParameter("searchOffeneceType");
            String searchOffence_code = request.getParameter("searchOffeneceCode");
            String searchAct2 = request.getParameter("searchAct");
            String searchActOrigin2= request.getParameter("searchActOrigin");

            if (searchOffence_type == null) {
                searchOffence_type = "";
            }
            if (searchOffence_code == null) {
                searchOffence_code = "";
            }
            String jrxmlFilePath;
            List list = null;
            //response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/OffenceTypeReport.jrxml");
           /* list = offenceTypeModel.showAllData(searchOffence_type, searchOffence_code);
            byte[] reportInbytes = offenceTypeModel.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            offenceTypeModel.closeConnection();
            return; */


                 list = offenceTypeModel.showAllData(searchOffence_type, searchOffence_code,searchAct2,searchActOrigin2);
                ByteArrayOutputStream reportInbytes = offenceTypeModel.generateAlertReportInExcel(jrxmlFilePath,list);
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=offence_type.xls");
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                offenceTypeModel.closeConnection();
                return;
        }




        if (task.equals("Delete")) {
            offenceTypeModel.deleteRecord(Integer.parseInt(request.getParameter("offence_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int offence_type_id;
            try {
                offence_type_id = Integer.parseInt(request.getParameter("offence_type_id"));
            } catch (Exception e) {
                offence_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                offence_type_id = 0;
            }
            OffenceTypeBean offenceTypeBean = new OffenceTypeBean();
            offenceTypeBean.setOffence_type_id(offence_type_id);
            offenceTypeBean.setOffence_type(request.getParameter("offence_type"));
            offenceTypeBean.setAct(request.getParameter("act"));
            offenceTypeBean.setAct_origin(request.getParameter("act_origin"));
            offenceTypeBean.setPenalty_amount(request.getParameter("penalty"));
            offenceTypeBean.setFrom_date(request.getParameter("from_date"));
            offenceTypeBean.setTo_date(request.getParameter("to_date"));
            offenceTypeBean.setActive(request.getParameter("active"));

            offenceTypeBean.setVehicle_type(request.getParameter("vehicle_type"));
            offenceTypeBean.setTarnsport_type(request.getParameter("transportation_type"));


            offenceTypeBean.setRemark(request.getParameter("remark"));


            offenceTypeBean.setOffence_code(request.getParameter("offence_code"));
            
            offenceTypeBean.setHave_second_offence(request.getParameter("have_second_offence"));
            offenceTypeBean.setSecond_offence_penalty(request.getParameter("secondOffencePenalty"));

            if (offence_type_id == 0) {
                System.out.println("Inserting values by model......");
                offenceTypeModel.insertRecord(offenceTypeBean);
            } else {
                System.out.println("Update values by model........");
                offenceTypeModel.updateRecord(offenceTypeBean);
            }
        }



        try {
            if (searchOffenceType == null) {
                searchOffenceType = "";
            }
            if (searchOffenceCode == null) {
                searchOffenceCode = "";
            }

         if (searchAct == null) {
                searchAct = "";
            }
            if (searchActOrigin == null) {
                searchActOrigin = "";
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
            searchOffenceType = "";
            searchOffenceCode = "";
                   searchAct = "";
            searchActOrigin = "";
        }
        System.out.println("searching.......... " + searchOffenceType);
        noOfRowsInTable = offenceTypeModel.getNoOfRows(searchOffenceType, searchOffenceCode,searchAct,searchActOrigin);                  // get the number of records (rows) in the table.

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

        List<OffenceTypeBean> offenceTypeList = offenceTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchOffenceType, searchOffenceCode,searchAct,searchActOrigin);
        lowerLimit = lowerLimit + offenceTypeList.size();
        noOfRowsTraversed = offenceTypeList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        request.setAttribute("offenceTypeList", offenceTypeList);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("searchOffenceType", searchOffenceType);
        request.setAttribute("searchOffenceCode", searchOffenceCode);

        request.setAttribute("searchAct", searchAct);
        request.setAttribute("searchActOrigin", searchActOrigin);

        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", offenceTypeModel.getMessage());
        request.getRequestDispatcher("/offenceType").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

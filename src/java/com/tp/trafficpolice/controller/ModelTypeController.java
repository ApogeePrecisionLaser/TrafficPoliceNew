/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

import com.tp.tableClasses.ModelTypeBean;
import com.tp.trafficpolice.model.ModelTypeModel;
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
 * @author Administrator
 */
public class ModelTypeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is state Controller....");
        ServletContext ctx = getServletContext();
        ModelTypeModel modelTypeModel = new ModelTypeModel();
        modelTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        modelTypeModel.setDb_username(ctx.getInitParameter("db_userName"));
        modelTypeModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        modelTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        modelTypeModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");
        String modelType = request.getParameter("modelType");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getStatusType")) {
                    list = modelTypeModel.getmodelType(q);
                } else if (JQstring.equals("getMakeType")) {
                    list = modelTypeModel.getmakeType(q);
                } else if (JQstring.equals("getVehicleType")) {
                    list = modelTypeModel.getVehicleType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                modelTypeModel.closeConnection();
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
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/vehicleModel.jrxml");
            list = modelTypeModel.showAllData();
            byte[] reportInbytes = modelTypeModel.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            modelTypeModel.closeConnection();
            return;
        }else if (task.equals("generateExcelReport")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=VehicleModel_report.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/vehicleModel.jrxml");
            list = modelTypeModel.showAllData();
            ByteArrayOutputStream reportInbytes = modelTypeModel.generateExcelList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            modelTypeModel.closeConnection();
            return;
        }
        if (task.equals("Delete")) {
            modelTypeModel.deleteRecord(Integer.parseInt(request.getParameter("model_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int model_type_id;
            try {
                model_type_id = Integer.parseInt(request.getParameter("model_type_id"));
            } catch (Exception e) {
                model_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                model_type_id = 0;
            }
            ModelTypeBean modelTypeBean = new ModelTypeBean();
            modelTypeBean.setModel_id(model_type_id);
            modelTypeBean.setMake(request.getParameter("make_type"));
            modelTypeBean.setModel(request.getParameter("model_type"));
            //modelTypeBean.setVehicle_type(request.getParameter("vehicle_type"));
            modelTypeBean.setRemark(request.getParameter("remark"));
            if (model_type_id == 0) {
                System.out.println("Inserting values by model......");
                modelTypeModel.insertRecord(modelTypeBean);
            } else {
                System.out.println("Update values by model........");
                modelTypeModel.updateRecord(modelTypeBean);
            }
        }


        try {
            if (modelType == null) {
                modelType = "";
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
            modelType = "";
        }
        System.out.println("searching.......... " + modelType);
        noOfRowsInTable = modelTypeModel.getNoOfRows(modelType);                  // get the number of records (rows) in the table.

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

        List<ModelTypeBean> modelTypeList = modelTypeModel.showData(lowerLimit, noOfRowsToDisplay, modelType);
        lowerLimit = lowerLimit + modelTypeList.size();
        noOfRowsTraversed = modelTypeList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        request.setAttribute("modelTypeList", modelTypeList);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("modelType", modelType);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", modelTypeModel.getMessage());
        request.setAttribute("vehiclemodelTypeList", modelTypeModel.getModelTypeList());
        request.getRequestDispatcher("/modelType").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

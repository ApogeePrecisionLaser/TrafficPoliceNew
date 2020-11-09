/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

import com.tp.dbcon.DBConnection;
import com.tp.tableClasses.CameraInfoBean;
import com.tp.trafficpolice.model.CameraInfoModel;
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
public class CameraInfoController extends HttpServlet {

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
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is CityLocation Controller....");
        ServletContext ctx = getServletContext();
        CameraInfoModel cameraInfoModel = new CameraInfoModel();
        try {
            cameraInfoModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");


        String task = request.getParameter("task");
        String searchCameraNo = request.getParameter("searchCameraNo");
        String searchCameraType = request.getParameter("searchCameraType");
        String searchLocation = request.getParameter("searchLocation");
        
        
        if (searchCameraNo == null) {
            searchCameraNo = "";
        }
        if (searchCameraType == null) {
            searchCameraType = "";
        }
        if (searchLocation == null) {
            searchLocation = "";
        }


        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getLocation")) {
                    list = cameraInfoModel.getCityName(q);
                }
                
                if (JQstring.equals("getCameraType")) {
                    list = cameraInfoModel.getCameraType(q);
                }
                if (JQstring.equals("getCameraFacing")) {
                    list = cameraInfoModel.getCameraFacing(q);
                }
                
                if (JQstring.equals("getSearchCameraNo")) {
                    list = cameraInfoModel.getSearchCameraNo(q);
                }
                if (JQstring.equals("getSearchCameraType")) {
                    list = cameraInfoModel.getSearchCameraType(q);
                }
                if (JQstring.equals("getSearchCityLocation")) {
                    list = cameraInfoModel.getSearchCityLocation(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                cameraInfoModel.closeConnection();
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
            jrxmlFilePath = ctx.getRealPath("/report/location/location.jrxml");
            list = cameraInfoModel.showAllData(searchCameraNo, searchCameraType, searchLocation);
            byte[] reportInbytes = cameraInfoModel.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            cameraInfoModel.closeConnection();
            return;
        } else if (task.equals("generateExcelReport")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=CityLocation_report.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/location/location.jrxml");
            list = cameraInfoModel.showAllData(searchCameraNo, searchCameraType, searchLocation);
            ByteArrayOutputStream reportInbytes = cameraInfoModel.generateExcelList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            cameraInfoModel.closeConnection();
            return;
        }
        else if (task.equals("GetCordinates1"))
            {
                String longi1 = request.getParameter("longitude");
                String latti1 = request.getParameter("latitude");
                if(longi1 == null || longi1.equals("undefined"))
                    longi1 = "0";
                if(latti1 == null || latti1.equals("undefined"))
                    latti1 = "0";
                request.setAttribute("longi", longi1);
                request.setAttribute("latti", latti1);
                System.out.println(latti1 + "," + longi1);
                request.getRequestDispatcher("getCordinate1").forward(request, response);
                return;
            }
        if (task.equals("Delete")) {
            cameraInfoModel.deleteRecord(Integer.parseInt(request.getParameter("camera_Info_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int camera_Info_id;
            try {
                camera_Info_id = Integer.parseInt(request.getParameter("camera_Info_id"));
            } catch (Exception e) {
                camera_Info_id = 0;
            }
            if (task.equals("Save AS New")) {
                camera_Info_id = 0;
            }
//            String city_name = request.getParameter("city_name");
//            
//            String city_name_eng = request.getParameter("city_name_english");
            
            String channel_no = request.getParameter("channel_no");
            String camera_no = request.getParameter("camera_no");
            String camera_type = request.getParameter("camera_type");
            String camera_facing = request.getParameter("camera_facing");
            String  city_name_english= request.getParameter("city_name_english");
            
            String latitude1 = request.getParameter("latitude");
            String longitude1 = request.getParameter("longitude");
//            String location_no = request.getParameter("location_no");
            String  remark = request.getParameter("remark");
            
            
            CameraInfoBean cameraInfoBean = new CameraInfoBean();
            cameraInfoBean.setCamera_info_id(camera_Info_id);
            cameraInfoBean.setChannel_no(channel_no);
            cameraInfoBean.setCamera_no(camera_no);
            cameraInfoBean.setCamera_type(camera_type);
            cameraInfoBean.setCamera_facing(camera_facing);
            cameraInfoBean.setCity_location(city_name_english);
            cameraInfoBean.setLattitude(latitude1);
            cameraInfoBean.setLongitude(longitude1);
           
            cameraInfoBean.setRemark(remark);
            
            
            if (camera_Info_id == 0) {
                System.out.println("Inserting values by model......");
                cameraInfoModel.insertRecord(cameraInfoBean);
            } else {
                System.out.println("Update values by model........");
                cameraInfoModel.updateRecord(cameraInfoBean);
            }
        }


//        try {
//            if (searchCityName == null) {
//                searchCityName = "";
//            }
//            if (searchZoneName == null) {
//                searchZoneName = "";
//            }
//
//        } catch (Exception e) {
//        }
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
        //System.out.println("searching.......... " + searchCityName);
        noOfRowsInTable = cameraInfoModel.getNoOfRows(searchCameraNo, searchCameraType, searchLocation);

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
        } else if (task.equals("Show All Records")) {
            searchCameraNo="";
            searchCameraType="";
            searchLocation="";
        }
        List<CameraInfoBean> cityLocationList = cameraInfoModel.showData(lowerLimit, noOfRowsToDisplay, searchCameraNo, searchCameraType, searchLocation);
        lowerLimit = lowerLimit + cityLocationList.size();
        noOfRowsTraversed = cityLocationList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        request.setAttribute("cityLocationList", cityLocationList);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("searchCameraNo", searchCameraNo);
        request.setAttribute("searchCameraType", searchCameraType);
        request.setAttribute("searchLocation", searchLocation);
        
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", cameraInfoModel.getMessage());
        request.getRequestDispatcher("/cameraInfo").forward(request, response);
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

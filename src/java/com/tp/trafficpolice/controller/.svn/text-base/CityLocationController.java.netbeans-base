/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

import com.tp.tableClasses.CityLocationBean;
import com.tp.tableClasses.StatusTypeBean;
import com.tp.trafficpolice.model.CityLocationModel;
import com.tp.trafficpolice.model.StatusTypeModel;
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
public class CityLocationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is state Controller....");
        ServletContext ctx = getServletContext();
        CityLocationModel cityLocationModel = new CityLocationModel();
        cityLocationModel.setDriverClass(ctx.getInitParameter("driverClass"));
        cityLocationModel.setDb_username(ctx.getInitParameter("db_userName"));
        cityLocationModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        cityLocationModel.setConnectionString(ctx.getInitParameter("connectionString"));
        cityLocationModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");
        String searchCityName = request.getParameter("searchCity");
        String searchZoneName = request.getParameter("searchZone");
        String searchLocationName = request.getParameter("searchLocation");

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getCity")) {
                    list = cityLocationModel.searchCityName(q);
                }
                if (JQstring.equals("getZoneName")) {
                    list = cityLocationModel.getZoneName(q);
                }
                if (JQstring.equals("getLocationName")) {
                    list = cityLocationModel.getLocationName(q);
                }
                if (JQstring.equals("getCityName")) {
                    list = cityLocationModel.getCityName(q);
                }
                if (JQstring.equals("getZone")) {
                    list = cityLocationModel.getZone(q, request.getParameter("action2"));
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                cityLocationModel.closeConnection();
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
            jrxmlFilePath = ctx.getRealPath("/report/location.jrxml");
            list = cityLocationModel.showAllData(searchCityName, searchZoneName, searchLocationName);
            byte[] reportInbytes = cityLocationModel.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            cityLocationModel.closeConnection();
            return;
        } else if (task.equals("generateExcelReport")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=CityLocation_report.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/location.jrxml");
            list = cityLocationModel.showAllData(searchCityName, searchZoneName, searchLocationName);
            ByteArrayOutputStream reportInbytes = cityLocationModel.generateExcelList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            cityLocationModel.closeConnection();
            return;
        }
        if (task.equals("Delete")) {
            cityLocationModel.deleteRecord(Integer.parseInt(request.getParameter("city_location_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int city_location_id;
            try {
                city_location_id = Integer.parseInt(request.getParameter("city_location_id"));
            } catch (Exception e) {
                city_location_id = 0;
            }
            if (task.equals("Save AS New")) {
                city_location_id = 0;
            }
            CityLocationBean cityLocationBean = new CityLocationBean();
            cityLocationBean.setCity_location_id(city_location_id);
            cityLocationBean.setZone_id(cityLocationModel.getZoneId(request.getParameter("city_name"), request.getParameter("zone")));
//           cityLocationBean.setZone(request.getParameter("zone"));
            cityLocationBean.setLocation(request.getParameter("location"));
            cityLocationBean.setRemark(request.getParameter("remark"));
            if (city_location_id == 0) {
                System.out.println("Inserting values by model......");
                cityLocationModel.insertRecord(cityLocationBean);
            } else {
                System.out.println("Update values by model........");
                cityLocationModel.updateRecord(cityLocationBean);
            }
        }


        try {
            if (searchCityName == null) {
                searchCityName = "";
            }
            if (searchZoneName == null) {
                searchZoneName = "";
            }
            if (searchLocationName == null) {
                searchLocationName = "";
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
            searchCityName = "";
            searchZoneName = "";
            searchLocationName = "";
        }
        System.out.println("searching.......... " + searchCityName);
        noOfRowsInTable = cityLocationModel.getNoOfRows(searchCityName, searchZoneName, searchLocationName);                  // get the number of records (rows) in the table.

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
        List<CityLocationBean> cityLocationList = cityLocationModel.showData(lowerLimit, noOfRowsToDisplay, searchCityName, searchZoneName, searchLocationName);
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
        request.setAttribute("searchCityName", searchCityName);
        request.setAttribute("searchZoneName", searchZoneName);
        request.setAttribute("searchLocationName", searchLocationName);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", cityLocationModel.getMessage());
        request.getRequestDispatcher("/cityLocationView").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

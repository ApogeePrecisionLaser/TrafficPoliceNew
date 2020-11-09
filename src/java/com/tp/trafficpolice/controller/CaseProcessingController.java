/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.controller;

import com.tp.tableClasses.TrafficPolice;
import com.tp.trafficpolice.model.CaseProcessingModel;
import com.trafficpolice.general.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class CaseProcessingController extends HttpServlet {
    // private String jrxmlFilePath;
    //File tmpDir;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;

            ServletContext ctx = getServletContext();
            CaseProcessingModel caseProcessingModel = new CaseProcessingModel();
            caseProcessingModel.setDriverClass(ctx.getInitParameter("driverClass"));
            caseProcessingModel.setConnectionString(ctx.getInitParameter("connectionString"));
            caseProcessingModel.setDb_userName(ctx.getInitParameter("db_userName"));
            caseProcessingModel.setDb_userPasswrod(ctx.getInitParameter("db_userPasswrod"));
            caseProcessingModel.setConnection();
            request.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/plain; charset=UTF-8");

            String response_data = "";
            String JQstring = "";
            try {
                JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input
                if (JQstring != null) {
                    List<String> list = null;
                    PrintWriter out = response.getWriter();
                    if (JQstring.equals("getOfficerSearchList")) {
                        list = caseProcessingModel.getOfficerSearchList(q);                        
                    }
                    if (JQstring.equals("getBookSearchList")){
                            //String officer_name= request.getParameter("officerName");
                            // JQstring = request.getParameter("action2");
                            //JQstring = request.getParameter("action2");
                            list = caseProcessingModel.getBookSearchList(q);
                    }
                    if (JQstring.equals("getJarayamNo")) {                        
                            //String officer_name= request.getParameter("officerName");
                            // JQstring = request.getParameter("action2");
                            //JQstring = request.getParameter("action2");
                            list = caseProcessingModel.getJarayamNoList(q);                       
                    }
                    if (JQstring.equals("getActOriginList")) {
                            //String officer_name= request.getParameter("officerName");
                            // JQstring = request.getParameter("action2");
                            //JQstring = request.getParameter("action2");
                            list = caseProcessingModel.getActOriginList(q);
                    }
                    if (JQstring.equals("getOffenceCode")) {
                            //String officer_name= request.getParameter("officerName");
                            // JQstring = request.getParameter("action2");
                            //JQstring = request.getParameter("action2");
                            list = caseProcessingModel.getOffenceCodeList(q);
                    }
                    if (JQstring.equals("getOffenceSearchList")) {
                            //String BookNo= request.getParameter("BookNo");
                            list = caseProcessingModel.getOffenceSearchList(q);
                    }
                    if (JQstring.equals("getActSearchList")) {
                            // String OffenceType= request.getParameter("OffenceType");
                            list = caseProcessingModel.getActSearchList(q);
                    }
                    if (JQstring.equals("getVehicleTypeSearchList")) {
                            //String ActType= request.getParameter("ActType");
                            list = caseProcessingModel.getVehicleTypeSearchList(q);
                    }
                    if (JQstring.equals("getVehicleNoSearchList")) {
                            //   String VehicleType= request.getParameter("VehicleType");
                            list = caseProcessingModel.getVehicleNoSearchList(q);
                    }
                    if (JQstring.equals("getOffenderLicenseNo")) {
                            //   String VehicleType= request.getParameter("VehicleType");
                            list = caseProcessingModel.getOffenderLicenseNo(q);
                    }
                    if (JQstring.equals("getCityName")) {
                            //   String VehicleType= request.getParameter("VehicleType");
                            list = caseProcessingModel.getCityName(q);
                    }
                      if (JQstring.equals("getOffenderCityName")) {
                            //   String VehicleType= request.getParameter("VehicleType");
                            list = caseProcessingModel.getOffenderCityName(q);
                    }
                    if (JQstring.equals("getZoneName")) {
                            list = caseProcessingModel.getZoneName(q, request.getParameter("action2"));
                    }
                    if (JQstring.equals("getLocationName")) {
                            list = caseProcessingModel.getLocationName(q, request.getParameter("action2"));
                    } else if (JQstring.equals("getOfficerBookNameList")) {
                        String officer_name = request.getParameter("officer_name").trim();
                        response_data = caseProcessingModel.getOfficerBooklist(officer_name);
                        out.println(response_data);
                    } else if (JQstring.equals("getOffenceActTypeList")) {
                        String offence_type = request.getParameter("offence_type").trim();
                        response_data = caseProcessingModel.getOffenceActTypeList(offence_type);
                        out.println(response_data);
                    } else if (JQstring.equals("getActPenaltyAmountList")) {
                        String act = request.getParameter("act").trim();
                        response_data = caseProcessingModel.getActPenaltyAmountList(act);
                        out.println(response_data);

                    } else if (JQstring.equals("getbookRevisionNolist")) {
                        String book_no = request.getParameter("book_no").trim();
                        int response_data_amount = (caseProcessingModel.getBookRevisionNoList(book_no));
                        out.println(response_data_amount);

                    } else if (JQstring.equals("getofficerBookRevisionNolist")) {
                        String officer_name = request.getParameter("officer_name").trim();
                        int response_data_amount = (caseProcessingModel.getOfficerBookRevisionNoList(officer_name));
                        out.println(response_data_amount);
                    } else if (JQstring.equals("getBookOfficerNameList")) {
                        int book_no = Integer.parseInt(request.getParameter("book_no").trim());
                        response_data = caseProcessingModel.getBookOfficerlist(book_no);
                        out.println(response_data);
                    } else if (JQstring.equals("getVehicleTypeList")) {
                        String vehicle_type = request.getParameter("vehicle_type").trim();
                        response_data = caseProcessingModel.getVehicleTypelist(vehicle_type);
                        out.println(response_data);
                    } else if (JQstring.equals("getRecieptNo")) {
                        String book_no1 = request.getParameter("reciept_no").trim();
                        response_data = caseProcessingModel.getRecieptNo(book_no1);
                        out.println(response_data);
                        request.setAttribute("msg", caseProcessingModel.getMsg());
                            } else if (JQstring.equals("getRecieptNo")) {
                        String book_no2 = request.getParameter("reciept_no1").trim();
                        response_data = caseProcessingModel.getRecieptNo(book_no2);
                        out.println(response_data);
                          request.setAttribute("msg", caseProcessingModel.getMsg());
                    } else if (JQstring.equals("getVehicleType")) {
                        String offence_type = request.getParameter("offence_type").trim();
                        response_data = caseProcessingModel.getVehicleType(offence_type);
                        out.println(response_data);
                    } else if (JQstring.equals("getActOrigin")) {
                        String act = request.getParameter("act").trim();
                        response_data = caseProcessingModel.getActOrigin(act);
                        out.println(response_data);
                    }else if (JQstring.equals("getOffenceList")) {
                        String offence = request.getParameter("offence").trim();
                        response_data = caseProcessingModel.getOffenceList(offence);
                        out.println(response_data);
                    }                    
                    Iterator<String> iter = list.iterator();
                        while (iter.hasNext()) {
                            String data = iter.next();
                            if (data == null) {
                                out.print("");
                            } else {
                                out.println(data);
                            }
                        }
                    caseProcessingModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("error " + e);
                return;
            }
             try {
            Map<Integer, String> map = new LinkedHashMap<Integer, String>();
            if (request.getParameter("ajaxRequest") != null || !(request.getParameter("ajaxRequest").isEmpty())) {
                PrintWriter out = response.getWriter();
                String jSONFormate = "";
                if (request.getParameter("ajaxRequest").equals("getOffenceActTypeList")) {
                    String offence_type = request.getParameter("offence_type");
                        String jSONFormat = caseProcessingModel.getOffenceActTypeList(offence_type);
                        out.println(jSONFormat);
                }
                return;
            }
        } catch (Exception abc) {
        }
            String searchOfficerName = request.getParameter("searchOfficerName");
            if (searchOfficerName == null) {
                searchOfficerName = "";
            }
            String searchBookNo = null, searchOffenceType = null, searchActType = null, searchFromDate = null, searchToDate = null, 
                    searchVehicleType = null, searchVehicleNo = null, searchJarayamNo = null, searchOffenceCode = null, searchOffenderLicenseNo = null;
            searchOffenceType = request.getParameter("searchOffenceType");
            searchBookNo = request.getParameter("searchBookNo");
            searchJarayamNo = request.getParameter("searchJarayamNo");
            searchOffenceCode = request.getParameter("searchOffenceCode");
            searchActType = request.getParameter("searchActType");
            searchFromDate = request.getParameter("searchFromDate");
            searchToDate = request.getParameter("searchToDate");
            searchVehicleType = request.getParameter("searchVehicleType");
            searchVehicleNo = request.getParameter("searchVehicleNo");
            searchOffenderLicenseNo = request.getParameter("searchOffenderLicenseNo");
            String offence_city = "";
            String zone = "";
            String offence_place = "";
            try {
                if (searchBookNo == null) {
                    searchBookNo = "";
                }
                if (searchOffenceType == null) {
                    searchOffenceType = "";
                }
                if (searchActType == null) {
                    searchActType = "";
                }
                if (searchFromDate == null) {
                    searchFromDate = "";
                }
                if (searchToDate == null) {
                    searchToDate = "";
                }
                if (searchVehicleType == null) {
                    searchVehicleType = "";
                }
                if (searchVehicleNo == null) {
                    searchVehicleNo = "";
                }
                if (searchJarayamNo == null) {
                    searchJarayamNo = "";
                }
                if (searchOffenceCode == null) {
                    searchOffenceCode = "";
                }
                if(searchOffenderLicenseNo == null){
                    searchOffenderLicenseNo = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error in KeyPersonController setting searchOrganisation value" + e);
            }
            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("Delete")) {

                caseProcessingModel.deleteRecord(0, 0, Integer.parseInt(request.getParameter("traffic_police_id")));  // Pretty sure that meter_id will be available.
            }
            if(task.equals("List")){
            String offence_type=request.getParameter("values");
                 String[] offence =offence_type.split(",");
                   List<TrafficPolice> offence_list = caseProcessingModel.showData(offence);
                   request.setAttribute("offence_list",offence_list);
                   return;
            }
            if(task.equals("getOffenceCode")){
                String act_origin = request.getParameter("act_origin");
                String count = request.getParameter("count");
                List<TrafficPolice> list = caseProcessingModel.getOffenceRecordList(act_origin);
                request.setAttribute("OffenceList", list);
                request.setAttribute("count", count);
                request.getRequestDispatcher("trafficpolice_popupView").forward(request, response);
                return;
            }
            if (task.equals("PRINTRecordList")) {
                String jrxmlFilePath;
                List list = null;
                try {
                    response.setContentType("application/pdf");
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    jrxmlFilePath = ctx.getRealPath("/report/TrafficPolice.jrxml");
                    list = caseProcessingModel.showAllData(searchOfficerName, searchBookNo, searchOffenceType, searchActType, searchVehicleType, searchVehicleNo, searchFromDate, searchToDate, searchJarayamNo, searchOffenceCode, searchOffenderLicenseNo);
                    byte[] reportInbytes = caseProcessingModel.generateRecordList(jrxmlFilePath, list);
                    response.setContentLength(reportInbytes.length);
                    servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                    servletOutputStream.flush();
                    servletOutputStream.close();
                    caseProcessingModel.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("Exception is:" + e);
                }
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
            
            
            
            
            
            
            else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Add All Records") || task.equals("Cancel")) {
                try {
                    TrafficPolice trafficPolice = new TrafficPolice();
                    String traffic_police_id[] = {"1"};
                    try {
                        traffic_police_id[0] = request.getParameter("traffic_police_id");            // meter_id may or may NOT be available i.e. it can be update or new record.
                    } catch (Exception e) {
                        traffic_police_id[0] = "0";
                    }
                    if (task.equals("Save AS New")) {
                        traffic_police_id[0] = "0";
                        String traffic_police_id1[] = {"1"};
                        trafficPolice.setTraffic_police_idM(traffic_police_id1);
                    } else if (task.equals("Save")) {
                        trafficPolice.setTraffic_police_idM(traffic_police_id);
                    }

                    String no_of_offence = request.getParameter("no_of_offence");
                    if(no_of_offence == null || no_of_offence.isEmpty())
                        no_of_offence = "0";
                    int noOfOffence = Integer.parseInt(no_of_offence);
                    String[] offence_code = new String[noOfOffence];
                    String[] offenceCodeValues = new String[1];
                    offenceCodeValues[0] = request.getParameter("offenceCodeValues");
                    for(int i = 0; i < noOfOffence; i++ )
                        offence_code[i] = request.getParameter("offence_code");//offence_code0
                    
                    trafficPolice.setOffence_typeM(offence_code);
                    //trafficPolice.setOffence_type(offenceCodeValues);
                    String selectBy = request.getParameter("selectBy");
                    if(selectBy.equals("Officer Name")){
                        trafficPolice.setBook_noM(request.getParameterValues("book_no"));                        
                    }
                    else{
                        String[] book_no = {request.getParameterValues("book_no")[1]};
                        //String[] officerName = {request.getParameterValues("officer_name_selected")[1]};
                        trafficPolice.setBook_noM(book_no);
                        //trafficPolice.setOfficer_nameM(officerName);
                    }
                    trafficPolice.setOfficer_nameM(request.getParameterValues("officer_name_selected"));
                    trafficPolice.setBook_revision_noM(request.getParameterValues("book_revision_no"));
                    trafficPolice.setJarayam_noM(request.getParameterValues("jarayam_no"));
                    trafficPolice.setReceipt_noM(request.getParameterValues("reciept_no"));
                    trafficPolice.setOfficer_nameM(request.getParameterValues("officer_name_selected"));
                    if(request.getParameterValues("offence_type_selected") == null)
                        trafficPolice.setOffence_typeM(offenceCodeValues);
                    else
                        trafficPolice.setOffence_typeM(request.getParameterValues("offence_type_selected"));
                    trafficPolice.setVehicle_typeM((request.getParameterValues("vehicle_type")));
                    trafficPolice.setVehicle_noM(request.getParameterValues("vehicle_no"));
                    trafficPolice.setOffender_nameM(request.getParameterValues("offender_name"));
                    trafficPolice.setOffender_license_noM(request.getParameterValues("offender_license_no"));
                    offence_city = request.getParameter("city_name");
                    zone = request.getParameter("zone");
                    offence_place = request.getParameter("offence_place");
                    trafficPolice.setCityM(request.getParameterValues("city_name"));
                    trafficPolice.setZoneM(request.getParameterValues("zone"));
                    trafficPolice.setOffence_placeM(request.getParameterValues("offence_place"));
                    //trafficPolice.setActM(request.getParameterValues("act_type_selected"));
                    trafficPolice.setAct_originM(request.getParameterValues("act_origin"));
                    //trafficPolice.setPenalty_amountM(request.getParameterValues("penalty_amount"));
                    trafficPolice.setDeposited_amountM(request.getParameterValues("deposited_amount"));
                    trafficPolice.setOffence_dateM(request.getParameterValues("offence_date"));
                    trafficPolice.setProcessing_typeM(request.getParameterValues("process_type"));
                    //if(request.getParameter("process_type").equals("Court")){
                        trafficPolice.setRelation_typeM(request.getParameterValues("relation_type"));
                        trafficPolice.setCase_noM(request.getParameterValues("case_no"));
                        trafficPolice.setCase_dateM(request.getParameterValues("case_date"));
                        trafficPolice.setOffender_ageM(request.getParameterValues("offender_age"));
                        trafficPolice.setRelative_nameM(request.getParameterValues("relative_name"));
                        trafficPolice.setSalutationM(request.getParameterValues("salutation"));
                        trafficPolice.setOffender_cityM(request.getParameterValues("offender_city"));
                        trafficPolice.setOffender_address_M(request.getParameterValues("offender_address"));
                        trafficPolice.setCase_status(request.getParameter("case_status"));
                    //}
                    if (task.equals("Add All Records")) {
                        trafficPolice.setTraffic_police_idM(request.getParameterValues("traffic_police_id"));
                    }
                    if (traffic_police_id[0].equals("0") || task.equals("Add All Records")) {
                        if(traffic_police_id[0].equals("0")){
                            traffic_police_id[0] = "1";
                            trafficPolice.setTraffic_police_idM(traffic_police_id);
                        }
                        caseProcessingModel.insertRecord(trafficPolice, noOfOffence);
                    } else {
                        // update existing record.(payment_mode) object of bean class and payment_model (object) model .
                        caseProcessingModel.updateRecord(trafficPolice, noOfOffence);
                    }
                } catch (Exception e) {
                    System.out.println("Error - " + e);
                }
            }
            else if (task.equals("Show All Records")) {
                searchOfficerName = "";
                searchBookNo = "";
                searchOffenceType = "";
                searchActType = "";
                searchVehicleType = "";
                searchVehicleNo = "";
                searchFromDate = "";
                searchToDate = "";
                searchJarayamNo = "";
                searchOffenceCode = "";
            }
            String search = "";
            search = request.getParameter("search");
            try {
                if (search == null) {
                    search = "";
                }
            } catch (Exception e) {
            }
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
            /*if (task.equals("Search") || task.equals("clear")) {

            lowerLimit = 0;
            }*/
            noOfRowsInTable = caseProcessingModel.getNoOfRows(searchOfficerName, searchBookNo, searchOffenceType, searchActType, searchVehicleType, searchVehicleNo, searchFromDate, searchToDate, searchJarayamNo, searchOffenceCode, searchOffenderLicenseNo);                  // get the number of records (rows) in the table.
            
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
                //noOfRowsToDisplay = lowerLimit + noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Add All Records")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }
            // Logic to show data in the table.
         List<TrafficPolice> list = caseProcessingModel.showData(lowerLimit, noOfRowsToDisplay, searchOfficerName, searchBookNo, searchOffenceType, searchActType, searchVehicleType, searchVehicleNo, searchFromDate, searchToDate, searchJarayamNo, searchOffenceCode, searchOffenderLicenseNo);
            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("officerNameList", caseProcessingModel.getofficerNameList());
            request.setAttribute("bookNoList", caseProcessingModel.getBookNoList());
            request.setAttribute("vehicleTypeList", caseProcessingModel.vehicleTypeList());
            request.setAttribute("list", list);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("offenceTypeList", caseProcessingModel.getOffenceTypeList());
            request.setAttribute("actTypeList", caseProcessingModel.getActTypeList());
            request.setAttribute("message", caseProcessingModel.getMessage());
            request.setAttribute("msgBgColor", caseProcessingModel.getMsgBgColor());
            request.setAttribute("searchOfficerName", searchOfficerName);
            request.setAttribute("searchBookNo", searchBookNo);
            request.setAttribute("searchOffenceType", searchOffenceType);
            request.setAttribute("searchVehicleType", searchVehicleType);
            request.setAttribute("searchVehicleNo", searchVehicleNo);
            request.setAttribute("searchActType", searchActType);
            request.setAttribute("searchFromDate", searchFromDate);
            request.setAttribute("searchToDate", searchToDate);
            request.setAttribute("searchJarayamNo", searchJarayamNo);
            request.setAttribute("searchOffenceCode", searchOffenceCode);
            request.setAttribute("searchOffenderLicenseNo", searchOffenderLicenseNo);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("offender",request.getParameter("offender_name") );
            request.setAttribute("processingtypelist", caseProcessingModel.processingTypeList());
            request.setAttribute("caseStatusList",caseProcessingModel.caseStatusList());
            request.setAttribute("relationTypeList",caseProcessingModel.relationTypeList());
            request.setAttribute("offence_city", offence_city);
            request.setAttribute("zone", zone);
            request.setAttribute("offence_place", offence_place);
            //  request.setAttribute("searchKeyPerson", searchKeyPerson);
            caseProcessingModel.closeConnection();
            request.getRequestDispatcher("/caseProcessing").forward(request, response);

        } catch (Exception e) {
            System.out.println("KeyPersonController Exception: " + e);
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}

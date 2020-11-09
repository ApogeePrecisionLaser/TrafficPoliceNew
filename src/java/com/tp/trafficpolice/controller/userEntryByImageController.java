/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.trafficpolice.controller;

//import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import com.tp.trafficpolice.model.userEntryByImageModel;
import com.tp.tableClasses.userEntryByImageBean;
//import com.survey.util.GetDate;
import com.tp.util.UniqueIDGenerator;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.simple.JSONArray;
//import org.apache.pdfbox.util.PDFMergerUtility;

/**
 *
 * @author JPSS
 */
public class userEntryByImageController extends HttpServlet {
//ServletContext context = request.getServletContext();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

       //ServletContext context=getServletContext();
       //String path = context.getRealPath("/");
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is survey Controller....");
        ServletContext ctx = getServletContext();
        userEntryByImageModel tubeWellSurveyModel = new userEntryByImageModel();
        tubeWellSurveyModel.setDriverClass(ctx.getInitParameter("driverClass"));
        tubeWellSurveyModel.setConnectionString(ctx.getInitParameter("connectionString"));
        tubeWellSurveyModel.setDb_username(ctx.getInitParameter("db_userName"));
        tubeWellSurveyModel.setDb_password(ctx.getInitParameter("db_userPasswrod"));
        tubeWellSurveyModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String searchOfficerName = request.getParameter("searchOfficerName");
            if (searchOfficerName == null) {
                searchOfficerName = "";
            }
            String searchBookNo = null, searchOffenceType = null, searchActType = null, searchFromDate = null, searchToDate = null,
                    searchVehicleType = null, searchVehicleNo = null, searchJarayamNo = null, searchOffenceCode = null, searchOffenderLicenseNo = null,searchChallanNo=null;
            String searchage=null,  searchMobile=null, searchOffender = null,searchOffenceCity=null,searchPlaceOf=null,searchAmount=null,searchcaseDate=null;
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
            String searchReceiptBookNo = request.getParameter("searchReceiptBookNo");
            searchage = request.getParameter("searchAge");
             searchMobile = request.getParameter("searchMobileNo");
             searchOffender = request.getParameter("searchOffenderName");
             searchOffenceCity = request.getParameter("searchOffenceCity");
             searchPlaceOf = request.getParameter("searchPlaceof");
            searchAmount = request.getParameter("searchAmount");
             searchcaseDate = request.getParameter("searchCaseDate");
             
             
             
             
             searchChallanNo = request.getParameter("searchChallanNo");
            String offence_city = "";
            String zone = "";
            String offence_place = "";
            
               if(isNullOrEmpty(searchAmount))
               {
                String task1="PRINTRecordList";
               }
              
            
            
            
            
            
            
            

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
                if(searchReceiptBookNo == null)
                    searchReceiptBookNo = "";
            
                 if(searchage == null){
                    searchage = "";
                }
                
                
                 if(searchMobile == null){
                    searchMobile = "";
                }
                
                
                 if(searchOffender == null){
                    searchOffender = "";
                }
                
                
                 if(searchOffenceCity == null){
                    searchOffenceCity = "";
                }
                
                 
                 if(searchPlaceOf == null){
                    searchPlaceOf = "";
                }
                 
                   if(searchAmount == null){
                    searchAmount = "";
                }
                 
                 if(searchcaseDate == null){
                    searchcaseDate = "";
                }  
                 
                 
            if(searchChallanNo == null){
                searchChallanNo="";
            }
    }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("error in KeyPersonController setting searchOrganisation value" + e);
            }


//         String task1=request.getParameter("task1");
//         if(task1==null)task1="";

       // String searchFeeder = request.getParameter("searchFeeder");
       // String searchTypeOfConnection = request.getParameter("searchTypeOfConnection");
       // String searchToDate = request.getParameter("searchToDate");
       // String searchMeterFunctional=request.getParameter("searchMeterFunctional");

        String mountingType = "";
        Map<String, String> map = new HashMap<String, String>();
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String survey_type = request.getParameter("action2");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getLocation")) {
                    list = tubeWellSurveyModel.getLocation(q);
                }
                if (JQstring.equals("getLocationUsingLatLong")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        String latLong = request.getParameter("action2");
                        
                        String s[]=latLong.split(" ");
                        String lat1=s[0];
                        String long1=s[1];
                        list = tubeWellSurveyModel.getLocationUsingLatLong(lat1,long1);
                    }

                }
                if (JQstring.equals("getOfficerSearchList")){ 
                        list = tubeWellSurveyModel.getOfficerSearchList(q);
                }
                if (JQstring.equals("getOffenceSearchList")){
                        list = tubeWellSurveyModel.getOffenceSearchList(q); 
                }
                 if (JQstring.equals("getOffenceCode")){
                        list = tubeWellSurveyModel.getOffenceCodeList(q);
                }
                 if(JQstring.equals("getActSearchList")){
                        list = tubeWellSurveyModel.getActSearchList(q);
                }
                 if(JQstring.equals("getVehicleTypeSearchList")){
                        list = tubeWellSurveyModel.getVehicleTypeSearchList(q);
                 }
                 
                   if(JQstring.equals("getVehicleNoSearchList")){
                        list = tubeWellSurveyModel.getVehicleNoSearchList(q);
                 }
                 
                   if(JQstring.equals("getMobilenoSearchList")){
                        list = tubeWellSurveyModel.getMobileNoSearchList(q);
                 } 
                 
                 if(JQstring.equals("getOffenderNameSearchList")){
                        list = tubeWellSurveyModel.getOffenderNameSearchList(q);
                 }
                 if(JQstring.equals("getOffenderAgeSearchList")){
                        list = tubeWellSurveyModel.getOffenderAgeSearchList(q);
                 }

                 if(JQstring.equals("getplaceofSearchList")){
                        list = tubeWellSurveyModel.getPlaceOfSearchList(q);
                 }
                 if(JQstring.equals("getCaseDateSearchList")){
                        list = tubeWellSurveyModel.getCaseDateSearchList(q);
                 }
                 
                  if(JQstring.equals("getAmountSearchList")){
                        list = tubeWellSurveyModel.getAmountSearchList(q);
                 }
                 
                 
                         if(JQstring.equals("getChallanNoSearchList")){
                        list = tubeWellSurveyModel.getChallanNoSearchList(q);
                 }
                         
                if (JQstring.equals("getOfficerNameUsingMobileNo")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        String mobile_no = request.getParameter("action2");
                        
                        list = tubeWellSurveyModel.getOfficerNameUsingMobileNo(mobile_no);
                    }

                }
                  
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                tubeWellSurveyModel.closeConnection();
                out.flush();
                out.close();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        String task1 = request.getParameter("task1");
        if (task1 == null) {
            task1 = "";
        }
             String searchMeterNo="";
//        if (task1.equals("generateMapReport")) {
//            String searchPole = request.getParameter("poll_no");
//            searchIvrsNo = request.getParameter("ivrs_no");
//             searchMeterNo=tubeWellSurveyModel.getMeterNumber(searchIvrsNo);
//            List listAll = null;
//            String jrxmlFilePath;
//            response.setContentType("application/pdf");
//            ServletOutputStream servletOutputStream = response.getOutputStream();
//            listAll = tubeWellSurveyModel.showData(-1, -1, searchPole, searchIvrsNo, "",searchFileNo,searchPageNo,searchByDate,"",searchMeterFunctional,searchFeeder,searchTypeOfConnection,searchToDate);
//            jrxmlFilePath = ctx.getRealPath("/report/tubeWellSurvey_2.jrxml");
//            byte[] reportInbytes = tubeWellSurveyModel.generateMapReport(jrxmlFilePath, listAll);
//            response.setContentLength(reportInbytes.length);
//            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//            servletOutputStream.flush();
//            servletOutputStream.close();
//            return;
//        }


         if (task1.equals("generateReport")) {
                String jrxmlFilePath;
                List list = null;
                try {
                    userEntryByImageBean userEntryByImageBean1 = new userEntryByImageBean();
                    String traffic_police_id = request.getParameter("traffic_police_id");
                    ////////////////////////////////////////////////////////////////////////
                    List<String> imagePathList = tubeWellSurveyModel.getImagePath(traffic_police_id);

                    int listSize = imagePathList.size();
                    for(int i=0;i<listSize;i++){
                        if(i ==0){
                            userEntryByImageBean1.setImgPath(imagePathList.get(i));
                        }
                        if(i ==1){
                            userEntryByImageBean1.setImgPath1(imagePathList.get(i));
                        }
                    }
   
            response.setContentType("application/pdf");
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    
                    //jrxmlFilePath = ctx.getRealPath("/report/shift/onlineChallanReport3.jrxml");
                    jrxmlFilePath = ctx.getRealPath("/report/shift/TestReport.jrxml");
                    
                    
                    list = tubeWellSurveyModel.showMISData1(traffic_police_id,userEntryByImageBean1);
                    //list = tubeWellSurveyModel.showMISData2(userEntryByImageBean1,traffic_police_id);
                         // list = tubeWellSurveyModel.showAllData(searchOfficerName,searchMobile,searchPlaceOf,searchAmount ,searchcaseDate,searchBookNo, searchOffenceType, searchActType, searchVehicleType, searchVehicleNo, searchFromDate, searchToDate, searchJarayamNo, searchOffenceCode, searchOffenderLicenseNo, searchReceiptBookNo,searchChallanNo);               // list.add(userEntryByImageBean1);
                    byte[] reportInbytes = tubeWellSurveyModel.generateRecordList(jrxmlFilePath, list);
                    response.setContentLength(reportInbytes.length);
                    servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                    servletOutputStream.flush();
                    servletOutputStream.close();
                    tubeWellSurveyModel.closeConnection();
                    return;
            
               } catch (Exception e) {
                    System.out.println("Exception is:" + e);
                }
            }


//                    for(String path1 : imagePathList){
//                        userEntryByImageBean1.setImgPath(path1);
//                    }

                     //String path = context.getRealPath(imagePath);

                     //userEntryByImageBean1.setImgPath(imagePath);


                    if (task1.equals("PRINTRecordList")) {
                String jrxmlFilePath;
                List list = null;
                try {
                    
                    
                         userEntryByImageBean userEntryByImageBean1 = new userEntryByImageBean();
                    String traffic_police_id = request.getParameter("traffic_police_id");
                         list=null;
         
           response.setContentType("application/pdf");
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                     searchFromDate = request.getParameter("searchFromDate");
            searchToDate = request.getParameter("searchToDate");
            searchPlaceOf = request.getParameter("searchPlaceof");
                    //jrxmlFilePath = ctx.getRealPath("/report/shift/onlineChallanReport3.jrxml");
                   // jrxmlFilePath = ctx.getRealPath("/report/location/userentry1.jrxml");
                    jrxmlFilePath = ctx.getRealPath("/report/dyna2.jrxml");
                    
                    //list = tubeWellSurveyModel.showMISData(traffic_police_id);
                   list = tubeWellSurveyModel.showAllData3(searchOfficerName,searchMobile,searchPlaceOf,searchAmount ,searchcaseDate,searchBookNo, searchOffenceType, searchActType, searchVehicleType, searchVehicleNo, searchFromDate, searchToDate, searchJarayamNo, searchOffenceCode, searchOffenderLicenseNo, searchReceiptBookNo,searchChallanNo);       
                 //list = tubeWellSurveyModel.showAllData(searchOfficerName,searchMobile,searchPlaceOf,searchAmount ,searchcaseDate,searchBookNo, searchOffenceType, searchActType, searchVehicleType, searchVehicleNo, searchFromDate, searchToDate, searchJarayamNo, searchOffenceCode, searchOffenderLicenseNo, searchReceiptBookNo,searchChallanNo);
                   // list.add(userEntryByImageBean1);
                    byte[] reportInbytes = tubeWellSurveyModel.generateRecordList(jrxmlFilePath, list);
                    response.setContentLength(reportInbytes.length);
                    servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                    servletOutputStream.flush();
                    servletOutputStream.close();
                    tubeWellSurveyModel.closeConnection();
                    return;
                    } catch (Exception e) {
                    System.out.println("Exception is:" + e);
                }
          }
           else if (task1.equals("GetCordinates1"))
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
                         
               List image_name_list = new ArrayList();
        String image_uploaded_for = "e_challan";
        String destination_path = "";
        String imagePath="";
        String string = "";
        List<String> imageNameList = new ArrayList<String>();
        List<String> offence_code_list = new ArrayList<String>();
        boolean isCreated = true;
        List items = null;
        Iterator itr = null;
        List<File> list = new ArrayList<File>();
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
    fileItemFactory.setSizeThreshold(8 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
        fileItemFactory.setRepository(new File(""));
        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
        try {
            String auto_no="";
            items = uploadHandler.parseRequest(request);
            itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field
                    map.put(item.getFieldName(), item.getString("UTF-8"));
                    if(item.getFieldName().equals("vehicle_no")){
                        auto_no = map.get("vehicle_no");
                    }
                    if(item.getFieldName().equals("offence_code")){
                        offence_code_list.add(map.get("offence_code"));
                    }
                } else {
                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                    if (item.getName() == null || item.getName().isEmpty()) {
                        map.put(item.getFieldName(), "");
                    } else {
                        
                        String image_name = item.getName();
                        imageNameList.add(image_name);// add all image name to list
                        image_name = image_name.substring(0, image_name.length());
                        int index = image_name.indexOf('.');
                        System.out.println(index);
                        String ext = image_name.substring(index, image_name.length());
                        System.out.println(image_name);
                        map.put(item.getFieldName(), item.getName());
                        destination_path = tubeWellSurveyModel.getRepositoryPath(image_uploaded_for);
///////////////////////////////////////////////////////////////////////////////////////////////////////////

                        imagePath=destination_path;
                        String vehicle_no =  auto_no;
                        String dateTime="" ;
                //String split_date[] ;
                Date dt = new Date();
     //Date time_stamp_date = new Date(timestamp);\
     DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
     String datet = dateFormat.format(dt);
     dateTime=datet;
     if(dateTime == null || dateTime.isEmpty())
         dateTime = dateFormat.format(dt);

     String[] date = dateTime.split("\\.");
         //imagePath = date[0] + "-" + date[1] + "-" + date[2].split("/")[0] + "_" + "tp_" + traffic_police_id + fileExt;
                imagePath = imagePath + date[2].split("/")[0] + "/" + date[1] + "/" + date[0];
                imagePath = imagePath+"/"+vehicle_no;//correct it latter
////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        File file = new File(imagePath);
                        if (!image_name.isEmpty()) {
                            file = new File(imagePath);
                            if (!file.exists()) {
                                isCreated = file.mkdirs();
                            }
                            item.write(new File(imagePath + "\\" + image_name));
                            image_name_list.add(image_name);
                            list.add(new File(imagePath + "\\" + image_name));
                        }
                        
                        
                        
                    }    
                    
                }//else end
            }
            itr = null;
            itr = items.iterator();
        } catch (Exception e) {
            System.out.println("Error is :" + e);
        }
        String task = map.get("task");
        if (task == null) {
            task = "";
        }
        String task2 = request.getParameter("task2");
        if(task2 == null){
            task2="";
        }
        
      ////////////////////////////////////////////////////////////////////////////////////////////////  
        if(task2.equals("checkSecondOffence"))
            {
                 JSONObject obj1 = new JSONObject();    
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();
                 String vehicle_no_state = request.getParameter("vehicle_no_state");
                 String vehicle_no_city = request.getParameter("vehicle_no_city");
                 String vehicle_no_series = request.getParameter("vehicle_no_series");
                 String vehicle_no = request.getParameter("vehicle_no");
                 String final_vehicle_number = vehicle_no_state+""+vehicle_no_city+""+vehicle_no_series+""+vehicle_no;
               
//                arrayObj= canvasJSModel.getAllDateTime(ohlevel_id,searchDate);
                  arrayObj= tubeWellSurveyModel.checkSecondoffence(final_vehicle_number);
                obj1.put("dateTime", arrayObj);    
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }
        
        if(task2.equals("checkSecondOffencePenaltyAmount"))
            {
                 JSONObject obj1 = new JSONObject();    
                 JSONArray arrayObj = new JSONArray();
                 JSONArray arrayObj1 = new JSONArray();
                 String vehicle_no_state = request.getParameter("vehicle_no_state");
                 String vehicle_no_city = request.getParameter("vehicle_no_city");
                 String vehicle_no_series = request.getParameter("vehicle_no_series");
                 String vehicle_no = request.getParameter("vehicle_no");
                 
                 String offence_code = request.getParameter("offence_code");
                 String final_vehicle_number = vehicle_no_state+""+vehicle_no_city+""+vehicle_no_series+""+vehicle_no;
               
//                arrayObj= canvasJSModel.getAllDateTime(ohlevel_id,searchDate);
                  arrayObj= tubeWellSurveyModel.checksecondPenaltyAmount(final_vehicle_number,offence_code);
                obj1.put("dateTime", arrayObj);    
               PrintWriter out = response.getWriter();
               out.print(obj1);
                return;
            }
        
        
        ///////////////////////////////////////////////////////////////////////////////////////////////
        
        try{
        if (task.equals("Save") || task.equals("Save AS New") || task.equals("Revise") || task.equals("Delete")) {
            int Traffic_police_id;
            try {
                Traffic_police_id = Integer.parseInt(map.get("traffic_policeId"));
            } catch (Exception e) {
                Traffic_police_id = 0;
            }            
            if (task.equals("Save AS New")) {
                Traffic_police_id = 0;
            }
            userEntryByImageBean tubeWellSurveyBean = new userEntryByImageBean();
            tubeWellSurveyBean.setTraffic_police_id(Traffic_police_id);
            //tubeWellSurveyBean.setImage_name(map.get("image_name"));
            tubeWellSurveyBean.setDate(map.get("survey_date"));
            
            String offenceTime_h_m = map.get("time_h")+":"+map.get("time_m");
                    //trafficPolice.setOffence_dateM(date_time_h_m);
                    tubeWellSurveyBean.setOffenceTime_h_m(offenceTime_h_m);
            
            //tubeWellSurveyBean.setVehicle_no(map.get("vehicle_no"));
            tubeWellSurveyBean.setVehicle_no_state(map.get("vehicle_no_state"));
            tubeWellSurveyBean.setVehicle_no_city(map.get("vehicle_no_city"));
            tubeWellSurveyBean.setVehicle_no_series(map.get("vehicle_no_series"));
            tubeWellSurveyBean.setVehicle_no_digits(map.get("vehicle_no"));
            
            
            
            tubeWellSurveyBean.setLocation(map.get("location"));
            tubeWellSurveyBean.setLocation_eng(map.get("location_eng"));
            tubeWellSurveyBean.setDeposit_amount(Double.parseDouble(map.get("deposited_amount")));
            try{
            tubeWellSurveyBean.setOffender_name(map.get("owner_name").trim());
            tubeWellSurveyBean.setOffender_father_name(map.get("father_name").trim());
            tubeWellSurveyBean.setOffender_address(map.get("address").trim());
            
            tubeWellSurveyBean.setVehicle_type(map.get("vehicle_type").trim());
            
            }catch(Exception e){
                System.out.println(e);
            }
            tubeWellSurveyBean.setMobile_no(map.get("mobile_no"));
            tubeWellSurveyBean.setLattitude(map.get("lattitude"));
            tubeWellSurveyBean.setLongitude(map.get("longitude"));
            String challan_type = map.get("option");
            tubeWellSurveyBean.setChallan_option(challan_type);
            
            tubeWellSurveyBean.setOffice_mob_no(map.get("officer_mobile_no"));
            
            String no_of_offence = map.get("no_of_offence");
                    if(no_of_offence == null || no_of_offence.isEmpty())
                        no_of_offence = "0";
                    int noOfOffence = Integer.parseInt(no_of_offence);
                    String[] offence_code = new String[noOfOffence];

                     for(int i = 0; i < noOfOffence; i++ )
                        offence_code[i] = map.get("offence_code");//offence_code0

                    tubeWellSurveyBean.setOffence_typeM(offence_code);
            //tubeWellSurveyBean.setOffence_id(tubeWellSurveyModel.getOffenceId(map.get("offence_name")));
                      
            if (Traffic_police_id == 0) {
                
                int general_image_details_id=tubeWellSurveyModel.insertImageRecord(image_uploaded_for,imageNameList,imagePath);

                int status=0;
                if(general_image_details_id > 0){
                status=tubeWellSurveyModel.insertRecord(tubeWellSurveyBean,list,general_image_details_id,offence_code,offence_code_list);
                }
                if(status > 0){
                    String delete_path = "C:\\ssadvt_repository\\trafficPolice\\temp_vehicle_image\\";
                        for(int i=0;i < image_name_list.size();i++){
                        String final_delete_path=delete_path+image_name_list.get(i);
                        File d_file = new File(final_delete_path); 
          
                          if(d_file.delete()) 
                            { 
                             System.out.println("File deleted successfully"); 
                            } 
                          else
                          { 
                             System.out.println("Failed to delete the file"); 
                          } 
                        }
                                 
                }
           
            }
            
            if(Traffic_police_id >0 & !task.equals("Delete")){
                
                tubeWellSurveyModel.updateRecord(tubeWellSurveyBean,offence_code,offence_code_list);
            }
            
            if(task.equals("Delete")){
                tubeWellSurveyModel.deleteRecord(Traffic_police_id);
            }

            }
            }catch(Exception e){
                System.out.println(e);
            }

        if (task1.equals("Show All Records")) {
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
                searchReceiptBookNo = "";
                searchChallanNo="";
                 searchage=""; 
                 searchMobile=""; 
                 searchOffender = "";
                 searchOffenceCity="";
                 searchPlaceOf="";
                 searchAmount="";
                 searchcaseDate="";
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
            noOfRowsInTable = tubeWellSurveyModel.getNoOfRows(searchOfficerName,searchMobile ,searchPlaceOf,searchAmount,searchcaseDate,searchBookNo, searchOffenceType, searchActType, searchVehicleType, searchVehicleNo, searchFromDate, searchToDate, searchJarayamNo, searchOffenceCode, searchOffenderLicenseNo, searchReceiptBookNo,searchChallanNo);                  // get the number of records (rows) in the table.

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
         List<userEntryByImageBean> showDatalist = tubeWellSurveyModel.showData(lowerLimit, noOfRowsToDisplay, searchOfficerName,searchMobile ,searchPlaceOf,searchAmount,searchcaseDate,searchBookNo, searchOffenceType, searchActType, searchVehicleType, searchVehicleNo, searchFromDate, searchToDate, searchJarayamNo, searchOffenceCode, searchOffenderLicenseNo, searchReceiptBookNo,searchChallanNo);
           
      
         lowerLimit = lowerLimit + showDatalist.size();
            noOfRowsTraversed = showDatalist.size();
            
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

//////////////////////////////////////////////////////////////////////////////////////////////////////

            Date dt = new Date();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String cut_dt = df.format(dt);
            request.setAttribute("cut_dt", cut_dt);
            request.setAttribute("officerNameList", tubeWellSurveyModel.getofficerNameList());
            request.setAttribute("bookNoList", tubeWellSurveyModel.getBookNoList());
            request.setAttribute("vehicleTypeList", tubeWellSurveyModel.vehicleTypeList());
            
            request.setAttribute("getSearchAge", tubeWellSurveyModel.getOffenderAgeSearchList());
            
             request.setAttribute("getSearchOffenderName", tubeWellSurveyModel.getOffenderNameSearchList());
            
//              request.setAttribute("getSearchOffenceCity", tubeWellSurveyModel.vehicleTypeList());
//              
               request.setAttribute("searchPlaceOf", tubeWellSurveyModel.getPlaceOfSearchList());
               
                request.setAttribute("getSearchAmount", tubeWellSurveyModel.getAmountSearchList());
                
                 request.setAttribute("getSearchCaseDate", tubeWellSurveyModel.getCaseDateSearchList());
                 
                  request.setAttribute("getSearchMobile", tubeWellSurveyModel.getMobileNoSearchList());
            System.out.println("Size :"+showDatalist.size());
            request.setAttribute("list", showDatalist);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("offenceTypeList", tubeWellSurveyModel.getOffenceTypeList());
            request.setAttribute("actTypeList", tubeWellSurveyModel.getActTypeList());
            request.setAttribute("message", tubeWellSurveyModel.getMessage());
            request.setAttribute("msgBgColor", tubeWellSurveyModel.getMsgBgColor());
            request.setAttribute("searchOfficerName", searchOfficerName);
            request.setAttribute("searchBookNo", searchBookNo);
            request.setAttribute("searchOffenceType", searchOffenceType);
            request.setAttribute("searchVehicleType", searchVehicleType);
            request.setAttribute("searchMobileNo", searchMobile);
             request.setAttribute("searchage", searchage);
              request.setAttribute("searchOffenceCity", searchOffenceCity);
              request.setAttribute("searchPlaceof", searchPlaceOf);
              request.setAttribute("searchAmount", searchAmount);
              request.setAttribute("searchcaseDate", searchcaseDate);
           
              
              
              request.setAttribute("searchVehicleNo", searchVehicleNo);
            request.setAttribute("searchActType", searchActType);
            request.setAttribute("searchFromDate", searchFromDate);
            request.setAttribute("searchToDate", searchToDate);
            request.setAttribute("searchChallanNo",searchChallanNo);
            request.setAttribute("searchJarayamNo", searchJarayamNo);
            request.setAttribute("searchOffenceCode", searchOffenceCode);
            request.setAttribute("searchOffenderLicenseNo", searchOffenderLicenseNo);
            request.setAttribute("searchReceiptBookNo", searchReceiptBookNo);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("offender",request.getParameter("offender_name") );
            request.setAttribute("processingtypelist", tubeWellSurveyModel.processingTypeList());
            request.setAttribute("relationTypeList",tubeWellSurveyModel.relationTypeList());
            request.setAttribute("offence_city", offence_city);
            request.setAttribute("zone", zone);
            request.setAttribute("offence_place", offence_place);
            

/////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println("color is :" + tubeWellSurveyModel.getMsgBgColor());
        //request.setAttribute("IDGenerator", new UniqueIDGenerator());
        //request.setAttribute("searchByDate", searchByDate);
        //request.setAttribute("message", tubeWellSurveyModel.getMessage());
        //request.setAttribute("msgBgColor", tubeWellSurveyModel.getMsgBgColor());
       destroy();
        request.getRequestDispatcher("/userEntryByImage").forward(request, response);
   // }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    
    }

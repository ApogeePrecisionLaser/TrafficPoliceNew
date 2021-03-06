/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.general.controller;

import com.tp.util.UniqueIDGenerator;
import com.tp.general.model.TrafficPoliceSearchModel;
import com.tp.general.tableClasses.TrafficPoliceSearchBean;
import com.tp.util.GetDate;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import net.sf.json.JSONObject;
//import org.json.simple.JSONObject;

/**
 *
 * @author jpss
 */
public class TrafficPoliceSearchController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    private File tmpDir;
    public static String filePath;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String menuid = "";
            System.out.println("TraffivPOliceController1");
        try {
            int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;

            ServletContext ctx = getServletContext();
            TrafficPoliceSearchModel trafficPolicesearchModel = new TrafficPoliceSearchModel();
             TrafficPoliceSearchBean trafficPoliceSearchBean=new TrafficPoliceSearchBean();
            trafficPolicesearchModel.setDriverClass(ctx.getInitParameter("driverClass"));
            trafficPolicesearchModel.setConnectionString(ctx.getInitParameter("connectionString"));
            trafficPolicesearchModel.setDb_userName(ctx.getInitParameter("db_userName"));
            trafficPolicesearchModel.setDb_userPasswrod(ctx.getInitParameter("db_userPasswrod"));
            trafficPolicesearchModel.setConnection();
            request.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/plain; charset=UTF-8");

            Map<String, String> map = new HashMap<String, String>();
            String response_data = "";
            String JQstring = "";
            menuid = request.getParameter("menuid");


            try {

                JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input

                /*if (menuid.equals("1")) {
                        menuid = JQstring;
                }*/

                if (JQstring != null) {
                    List<String> list = null;
                    PrintWriter out = response.getWriter();
                    if (JQstring.equals("getOffenderLicenseNoSearchList")) {
                        list = trafficPolicesearchModel.getOffenderLicenseNoSearchList(q);
                        /*Iterator<String> iter = list.iterator();
                        while (iter.hasNext()) {
                            String data = iter.next();
                            if (data == null) {
                                out.print("");
                            } else {
                                out.println(data);
                            }
                        }*/
                    }
                    if (JQstring.equals("getVehicleNoSearchList")) {
                        try {
                            //   String VehicleType= request.getParameter("VehicleType");

                            list = trafficPolicesearchModel.getVehicleNoSearchList(q);

                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }                        
                    }
                    if (JQstring.equals("getVehicleNoJsonList")) {
                        try {
                            //   String VehicleType= request.getParameter("VehicleType");
                            //list = trafficPolicesearchModel.getVehicleNoJsonList();
                            JSONObject json;
                            json = trafficPolicesearchModel.getVehicleNoJsonList();
                            out.print(json);
                            return;
                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }
                    }
                    if (JQstring.equals("getOfficerNameList")) {
                        try {
                            //String book= request.getParameter("action2");
                            list = trafficPolicesearchModel.getOfficerNameList(q);
                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }
                    }
                    if (JQstring.equals("getOfficerName")) {
                        try {
                            String book_no= request.getParameter("action2");
                            String officer_name = trafficPolicesearchModel.getOfficerName(book_no);
                            out.print(officer_name);
                            return;
                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }
                    }
                    if (JQstring.equals("getPenaltyAmount")) {
                        try {
                            String offence_code = request.getParameter("action2");
                            double penalty_amount = trafficPolicesearchModel.getPenaltyAmount(offence_code);
                            out.print(penalty_amount);
                            return;
                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }
                    }
                    if (JQstring.equals("getOfficerBooklist")) {
                        try {
                            String officer_name = request.getParameter("action2");
                            list = trafficPolicesearchModel.getOfficerBooklist(q, officer_name);
                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }
                    }
                    if (JQstring.equals("getCitylist")) {
                        try {
                            //String officer_name = request.getParameter("action2");
                            list = trafficPolicesearchModel.getCityName(q);
                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }
                    }
                    if (JQstring.equals("getZonelist")) {
                        try {
                            String city = request.getParameter("action2");
                            list = trafficPolicesearchModel.getZoneName(q, city);
                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }
                    }
                    if (JQstring.equals("getLocationlist")) {
                        try {
                            String city = request.getParameter("action2");
                            String zone = request.getParameter("action3");
                            list = trafficPolicesearchModel.getLocationName(q, city, zone);
                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }
                    }
                    if (JQstring.equals("getOffenceCodeList")) {
                        try {
                            //   String VehicleType= request.getParameter("VehicleType");
                            list = trafficPolicesearchModel.getOffenceCodeList(q);
                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }                        
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

                    trafficPolicesearchModel.closeConnection();
                    return;
                }

            } catch (Exception e) {
                System.out.println("error " + e);
            }          

            String searchOffenderLicenseNo = request.getParameter("searchOffenderLicenseNo");
            if (searchOffenderLicenseNo == null) {
                searchOffenderLicenseNo = "";
            }
            String searchBookNo = null, searchOffenceType = null, searchActType = null, searchFromDate = null, searchToDate = null, searchVehicleType = null, searchVehicleNo = null;
            searchVehicleNo = request.getParameter("searchVehicleNo");

            try {
                if (searchVehicleNo == null) {
                    searchVehicleNo = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error in TrafficPoliceSearchController setting searchOrganisation value" + e);
            }

            
            String name = "";
            filePath = "C:/ssadvt_repository/trafficPolice/";
            List items = null;
            Iterator itr = null;
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
            fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
            fileItemFactory.setRepository(tmpDir);
            String fileExt = "", fileName = "";
            File file = null;
            String latitude = "";
            String longitude = "";
            String timestamp = "";
            String date_time = "";
            String image_name  = "";
            FileItem image_file = null;
            LinkedHashMap<String, File> fileList = new LinkedHashMap<String, File>();
            //String user_name=(String) session.getAttribute("user_name");
            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
            int count = 0;

            try {
                if(uploadHandler.isMultipartContent(request)){
                    System.out.println("Multipart ");
                }
                items = uploadHandler.parseRequest(request);
                itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString("UTF-8") + "\n");//(getString())its for form field
                        map.put(item.getFieldName(), item.getString("UTF-8"));
                    } else {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                        if (item.getName() == null || item.getName().isEmpty()) {
                            map.put(item.getFieldName(), "");
                        } else {
                            map.put(item.getFieldName(), item.getName());
                            fileName = item.getName();
                            if (fileName != null && !fileName.isEmpty()) {
                                try {
                                    File destinationDir = new File(filePath);
                                    if (!destinationDir.isDirectory()) {
                                        destinationDir.mkdirs();
                                    }
                                    fileExt = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                                    latitude = map.get("latitude");
                                    longitude = map.get("longitude");
                                    timestamp = map.get("timestamp");
                                    date_time = map.get("date_time");
                                    Date dt = new Date();
                                    if(date_time != null && !date_time.isEmpty())
                                        dt = new Date(date_time);
                                    //Date dt = new Date(date_time);
                                    //Date time_stamp_date = new Date(timestamp);
                                    DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
                                    date_time = dateFormat.format(dt);
                                    name = "temp_" + fileName;
                                    //date_time = "" + dt.getDate()+ "-" + GetDate.getMonthInString(dt.getMonth()) + "-" + dt.getYear();
                                    image_name = latitude + "_" + longitude + "_" + timestamp + fileExt; //+ count;
                                    file = new File(filePath + "/" + name);
                                    File file_re = new File(filePath + "/" + image_name);
                                    image_file = item;
                                    item.write(file);
                                    item.write(file_re);
                                    count++;
                                } catch (Exception e) {
                                    file = null;
                                    //break;
                                }
                            }
                        }
                    }
                }
                itr = null;
                itr = items.iterator();
            } catch (Exception ex) {
                System.out.println("Error encountered while uploading file " + ex);
            }


            //name="latitude"
            //name="longitude"
            //name="timestamp"
            //file.renameTo(file);

            //String task = map.get("task");


            String task = request.getParameter("task");
            if (task == null) {
                task = map.get("task");
                if (task == null)
                    task = "";
            }
             List list1 = new ArrayList(); 

            if(task.equals("new")){

                //request.setAttribute("offender_license_no", searchOffenderLicenseNo);
                //request.setAttribute("vehicle_no", searchVehicleNo);
                request.setAttribute("menuid", menuid);
                request.getRequestDispatcher("trafficePoliceInsertView").forward(request, response);
                return;
            }
            if (task.equals("showMapWindow")) {
            String longi = request.getParameter("logitude");
            String latti = request.getParameter("lattitude");
            request.setAttribute("longi", longi);
            request.setAttribute("latti", latti);
            System.out.println(latti + "," + longi);
            request.getRequestDispatcher("openMapWindowView").forward(request, response);
            return;
            }

            if(task.equals("Upload")){
                return;
            }
            
          if (task.equals("GetCordinates1"))
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
            
            
            
            
            
            
            
            
             String image_destination = "";
        image_destination = request.getParameter("img_destination");
        if (image_destination == null) {
            image_destination = "";
        }
        String task1 = request.getParameter("task1");
        if(task1 == null){
            task1="";
        }
        System.out.println("TraffivPOliceController5"+task1);
        System.out.println("TraffivPOliceController5.1"+task);

//        if (task1.equals("getImage") || task1.equals("getImageThumb")) {
//            String path = "C:/ssadvt_repository/meter_survey/survey_image/switching_point";
//            System.out.println("getImageThumb============================");
//            System.out.println("List"+list1);
//            if (image_destination == null || image_destination.isEmpty()) {
//                image_destination = path + "general/no_image.png";
//                String ext = image_destination.split("\\.")[1];
//                response.setContentType("image/" + ext);
//            } else {
//                File f = new File(image_destination);
//                if (f.exists()) {
//                    String ext = image_destination.split("\\.")[1];
//                    //response.setContentType("image/" + ext);
//                    //response.setContentType("image/" + "jpeg");
//                    response.setContentType("image/jpeg");
//                } else {
//                    image_destination = path + "general/no_image.png";
//                }
//            }
//            File f = new File(image_destination);
//            ServletOutputStream os = response.getOutputStream();
//            FileInputStream is = new FileInputStream(f);
//            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
//            response.setContentLength(is.available());
//            BufferedOutputStream out = new BufferedOutputStream(os);
//            
//            //byte[] buf = new byte[1024 * 1024];
//            int a=0;
//            int nRead = 0;
//            while ((nRead = bis.read()) != -1) {
//                //os.write(buf, 0, nRead);
//                out.write(nRead);
//                a++;
//            }
////            while ((ch = bis.read()) != -1) {
////                        out.write(ch);
//                    //}
//            
//            bis.close();
//            is.close();
//            out.close();
//            os.close();
//            response.flushBuffer();
//            System.out.println("****************************"+a);
//            return;
//        }
            
            

            if(task.equals("View_Image")){
                try {
                    List<TrafficPoliceSearchBean> imageList = new ArrayList<TrafficPoliceSearchBean>();
                    String destinationPath = "";
                    int traffic_police_id = 0;
                    try{
                        traffic_police_id = Integer.parseInt(request.getParameter("traffic_police_id"));
                    }catch(Exception ex){
                        traffic_police_id = 0;
                    }
                    if(traffic_police_id != 0){
                        
                        int general_image_detail_id = trafficPolicesearchModel.getGeneralImageDetailId(traffic_police_id);                          
                        List<String> list = trafficPolicesearchModel.getCompleteImagePath(general_image_detail_id);
                        
                        
                        for(String path : list){
                            
                            
//                            File f = new File(destination + "\\tube_well\\" +"survey_id_"+survey_id+"\\"+ imageName[i]);
                              File f = new File(path);
//                        if(f.exists())
//                        {
////                            list1.add(destination + "\\tube_well\\" +"survey_id_"+survey_id+"\\"+ imageName[i]);
//                              List<TrafficPoliceSearchBean> imageList = new ArrayList<TrafficPoliceSearchBean>();
//                              
//                              imageList.addImage_name(path);   
//                        }
                        
                        File folder = new File(path);
                        File[] listOfFiles = folder.listFiles();
                        List exactImageName = trafficPolicesearchModel.getExactImageName(general_image_detail_id);
                       for (int i = 0; i < listOfFiles.length; i++) {
                        if (listOfFiles[i].isFile()) {
//                          TrafficPoliceSearchBean fd=new TrafficPoliceSearchBean();
                           String name1=  listOfFiles[i].getName();
                           /////////////////////////////////////filterImageName//////////////////////////////////////////////
                           for(int j=0;j<exactImageName.size();j++){
                               if(exactImageName.get(j).equals(name1)){
                                   TrafficPoliceSearchBean fd=new TrafficPoliceSearchBean();
                                   fd.setImage_name(name1);
                                   fd.setTraffic_police_id(traffic_police_id);
                                   imageList.add(fd);
                               }
                               
                           }
                           
                           
                       }
                  }                                                  
                        }
                    }
                request.setAttribute("imageList", imageList);
                request.getRequestDispatcher("MultipleImage").forward(request, response);
                return;
                        
                } catch (Exception e) {
                    System.out.println("TrafficPoliceSearchController View_Image Error :" + e);
                    return;
                }
                
            }
            
            else if(task1.equals("getImage") || task1.equals("getImageThumb")) {
                     try{
                    int traffic_police_id = Integer.parseInt(request.getParameter("traffic_police_id").trim());
                    String image_name1 = request.getParameter("image_name").trim();
                    
                    int general_image_detail_id = trafficPolicesearchModel.getGeneralImageDetailId(traffic_police_id);                          
                        List<String> list = trafficPolicesearchModel.getCompleteImagePath(general_image_detail_id);
                        String destinationPath1 = list.get(0);
                    
                    //String destinationPath1=vtm.getGeneral_image_detail_id(idVal,keyperson_id);
                    String destinationPath = destinationPath1+"\\"+image_name1;
                    File f = new File(destinationPath);
                    FileInputStream fis = null;
                    if (!f.exists()) {
                        destinationPath = "C:\\ssadvt_repository\\prepaid\\general\\no_image.png";
                        f = new File(destinationPath);
                    }
                    fis = new FileInputStream(f);
                    if (destinationPath.contains("pdf")) {
                        response.setContentType("pdf");
                    } else {
                        response.setContentType("image/jpeg");
                    }

                    //  response.addHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                    // BufferedImage bi=ImageIO.read(f);
                    response.setContentLength(fis.available());
                    ServletOutputStream os = response.getOutputStream();
                    BufferedOutputStream out = new BufferedOutputStream(os);
                    int ch = 0;                    
                    while ((ch = bis.read()) != -1) {
                        out.write(ch);
                    }

                    bis.close();
                    fis.close();
                    out.close();
                    os.close();
                    response.flushBuffer();

                   trafficPolicesearchModel.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("SelectSupplierController Demand Note Error :" + e);
                    return;
                }
          }

            if(task.equals("ADD NEW OFFENCE")){

                request.setAttribute("offender_license_no", searchOffenderLicenseNo);
                request.setAttribute("vehicle_no", searchVehicleNo);
                request.setAttribute("menuid", menuid);
                request.getRequestDispatcher("trafficePoliceInsertView").forward(request, response);
                return;
            }

            if(task.equals("Save")){
                menuid = map.get("menuid");
                
                //"is_owner_license"
                //"is_not_owner_license"
                if(latitude.isEmpty())
                    latitude = map.get("latitude");
                if(longitude.isEmpty())
                    longitude = map.get("longitude");
                if(date_time.isEmpty()){
                    date_time = map.get("date_time");
                    Date dt = new Date();
                    if(date_time != null && !date_time.isEmpty())
                        dt = new Date(date_time);
                    //Date time_stamp_date = new Date(timestamp);
                    DateFormat dateFormat = new SimpleDateFormat("dd.MMMMM.yyyy/ hh:mm:ss aaa");
                    date_time = dateFormat.format(dt);
                }
           
            //if(searchOffenderLicenseNo.equals("")||searchOffenderLicenseNo!=null)
            //trafficPoliceSearchBean.setOffender_license_no(searchOffenderLicenseNo);
            //trafficPoliceSearchBean.setJarayam_no(request.getParameter("jarayam_no"));
            trafficPoliceSearchBean.setOffender_license_no(map.get("offender_license_no"));
            trafficPoliceSearchBean.setVehicle_no(map.get("vehicle_no"));
            trafficPoliceSearchBean.setOffender_name(map.get("offender_name"));
            trafficPoliceSearchBean.setBook_no(Integer.parseInt(map.get("book_no")));
            trafficPoliceSearchBean.setBook_revision_no(Integer.parseInt(map.get("book_revision_no")));
            int receipt_no = 0;
            trafficPoliceSearchBean.setReciept_no(map.get("receipt_no"));
            try{
                receipt_no = Integer.parseInt(map.get("receipt_no"));
            }catch(Exception ex){
                receipt_no = 0;
            }
            trafficPoliceSearchBean.setOfficer_name(map.get("officer_name"));
            String offence_city = map.get("offence_city");
            if(offence_city == null)
                offence_city = "";
            String offence_zone = map.get("offence_zone");
            if(offence_zone == null)
                offence_zone = "";
            String offence_location = map.get("offence_location");
            if(offence_location == null)
                offence_location = "";
            trafficPoliceSearchBean.setCity_location_id(trafficPolicesearchModel.getCity_location_id( offence_city, offence_zone, offence_location));

            int deposit_amount = 0;

            try{
                deposit_amount = Integer.parseInt(map.get("deposit_amount"));
            }catch(Exception ex){
                deposit_amount = 0;
            }
            
            trafficPoliceSearchBean.setDeposit_amount(deposit_amount);
            String processing_type = map.get("processing_type");
            if(processing_type != null && !processing_type.isEmpty())
                trafficPoliceSearchBean.setProcessing_type(processing_type);
            //String license_vehicle = request.getParameter("license_vehicle");
            /*String[] check_license_vehicle = new String[2];
            if(license_vehicle != null || !license_vehicle.isEmpty())
                check_license_vehicle = license_vehicle.split("_");
            if(check_license_vehicle[1].equals("L") )
                trafficPoliceSearchBean.setOffender_license_no(check_license_vehicle[0]);
            else
                trafficPoliceSearchBean.setVehicle_no(check_license_vehicle[0]);*/
            int noOfOffence = 0;
            try{
                noOfOffence = Integer.parseInt(map.get("no_of_offence"));
            }catch(Exception e){
                noOfOffence = 0;
            }
            String[] offence_code = new String[noOfOffence];
            for(int i = 0; i < noOfOffence ; i++){
                //String of_codeStr =
                offence_code[i] = map.get("Offence_code"+i);
                }
            
            trafficPoliceSearchBean.setOffence_codeM(offence_code);//request.getParameterValues("Offence_code")
            trafficPoliceSearchBean.setLattitude(latitude);
            trafficPoliceSearchBean.setLongitude(longitude);
            int rowAffacted = trafficPolicesearchModel.insertRecord(trafficPoliceSearchBean, noOfOffence, date_time, image_file, image_name, fileExt);
            if(rowAffacted > 0)
                request.setAttribute("receipt_no", receipt_no+1);
            else
                request.setAttribute("receipt_no", receipt_no);            
            request.setAttribute("offence_city", offence_city);
            request.setAttribute("offence_zone", offence_zone);
            request.setAttribute("offence_location", offence_location);
            request.setAttribute("menuid", menuid);
            request.setAttribute("message", trafficPolicesearchModel.getMessage());
            request.setAttribute("msgBgColor", trafficPolicesearchModel.getMsgBgColor());
            request.getRequestDispatcher("trafficePoliceInsertView").forward(request, response);
            return;
            }

            String search = "";
            //search = request.getParameter("search");
            search = map.get("search");
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
            if(!searchOffenderLicenseNo.equals("") || !searchVehicleNo.equals("")){

            noOfRowsInTable = trafficPolicesearchModel.getNoOfRows(searchOffenderLicenseNo, searchVehicleNo);                  // get the number of records (rows) in the table.
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

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Add All Records")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }
            // Logic to show data in the table.
          List<TrafficPoliceSearchBean> list = trafficPolicesearchModel.showData(lowerLimit, noOfRowsToDisplay, searchOffenderLicenseNo, searchVehicleNo);

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
            request.setAttribute("list", list);
            }

            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", trafficPolicesearchModel.getMessage());
            request.setAttribute("msgBgColor", trafficPolicesearchModel.getMsgBgColor());
            request.setAttribute("searchOffenderLicenseNo", searchOffenderLicenseNo);
            request.setAttribute("searchBookNo", searchBookNo);
            request.setAttribute("searchVehicleType", searchVehicleType);
            request.setAttribute("searchVehicleNo", searchVehicleNo);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("offender",request.getParameter("offender_name") );
            request.setAttribute("menuid", menuid);

            //  request.setAttribute("searchKeyPerson", searchKeyPerson);
            trafficPolicesearchModel.closeConnection();
            request.getRequestDispatcher("trafficPoliceSearchVeiw").forward(request, response);

        } catch (Exception e) {
            System.out.println("TrafficPoliceSearchController Exception: " + e);
            request.setAttribute("menuid", menuid);
            request.getRequestDispatcher("trafficPoliceSearchVeiw").forward(request, response);
        }


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

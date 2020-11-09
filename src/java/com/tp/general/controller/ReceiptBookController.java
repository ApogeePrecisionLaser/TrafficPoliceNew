/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.general.controller;

import com.tp.general.model.ReceiptBookModel;
import com.tp.general.tableClasses.ReceiptBookBean;
import com.tp.util.UniqueIDGenerator;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import net.sf.json.JSONObject;

/**
 *
 * @author jpss
 */
public class ReceiptBookController extends HttpServlet {

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
        try {
            int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 1, noOfRowsInTable;

            ServletContext ctx = getServletContext();
            ReceiptBookModel receiptBookModel = new ReceiptBookModel();
            receiptBookModel.setDriverClass(ctx.getInitParameter("driverClass"));
            receiptBookModel.setConnectionString(ctx.getInitParameter("connectionString"));
            receiptBookModel.setDb_userName(ctx.getInitParameter("db_userName"));
            receiptBookModel.setDb_userPasswrod(ctx.getInitParameter("db_userPasswrod"));
            receiptBookModel.setConnection();
            request.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/plain; charset=UTF-8");
            
            String response_data = "";
            String JQstring = "";
            String menuid = request.getParameter("menuid");
            try {
                JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input                
                if (JQstring != null) {
                    List<String> list = null;
                    PrintWriter out = response.getWriter();                    
                    if (JQstring.equals("getReceiptBookNo")) {
                        String officer_name = request.getParameter("action2");
                        if(officer_name == null)
                            officer_name = "";
                        list = receiptBookModel.getReceiptBookNo(q, officer_name);
                    }else if (JQstring.equals("getReceiptNo")) {
                        String receipt_book_no = request.getParameter("receipt_book_no");
                        int page_no = receiptBookModel.getReceiptNo(q, receipt_book_no);
                        out.print(page_no);
                        return;
                    }else if (JQstring.equals("getReceiptNoSearchList")) {
                        String receipt_book_no = request.getParameter("receipt_book_no");
                        list = receiptBookModel.getReceiptNoSearchList(q, receipt_book_no);
                    }else if (JQstring.equals("getOfficerBooklist")) {
                        String officer_name = request.getParameter("action2");
                        list = receiptBookModel.getOfficerBooklist(q, officer_name);
                    }else if (JQstring.equals("getChallanNo")) {
                        String book_no = request.getParameter("action2");
                        list = receiptBookModel.getChallanNo(q, book_no);
                    }else if (JQstring.equals("getChallanAmount")) {
                        String book_no = request.getParameter("book_no");
                        String challan_no = request.getParameter("challan_no");
                        String amount = receiptBookModel.getTrafficPoliceIDAmount(book_no, challan_no);
                        out.print(amount);
                        return;
                    }else if (JQstring.equals("getBookRevision")) {
                        String book_no = request.getParameter("action2");
                        int revision = receiptBookModel.getReceiptBookRevision(book_no);
                        out.print(revision);
                        return;
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
                    receiptBookModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("error " + e);
            }          
            String searchReceiptBookNo = request.getParameter("searchReceiptBookNo");
            if (searchReceiptBookNo == null) {
                searchReceiptBookNo = "";
            }
            String searchBookNo = null, searchOffenceType = null, searchActType = null, searchFromDate = null, searchToDate = null, searchVehicleType = null, searchReceiptNo = null;
            searchReceiptNo = request.getParameter("searchReceiptNo");
            try {
                if (searchReceiptNo == null) {
                    searchReceiptNo = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error in TrafficPoliceSearchController setting searchOrganisation value" + e);
            }
            String name = "";
            filePath = "C:/ssadvt_repository/trafficPolice/";   
            String latitude = "";
            String longitude = "";
            String timestamp = "";
            String date_time = "";
            String image_name  = "";
            
            //name="latitude"
            //name="longitude"
            //name="timestamp"
            //file.renameTo(file);

            //String task = map.get("task");


            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }

            if(task.equals("newReceipt")){
                request.setAttribute("menuid", menuid);
                request.getRequestDispatcher("trafficePoliceReceiptView").forward(request, response);
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
            if(task.equals("View_Image")){
                try {                    
                    String destinationPath = "";
                    int traffic_police_id = 0;
                    try{
                        traffic_police_id = Integer.parseInt(request.getParameter("traffic_police_id"));
                    }catch(Exception ex){
                        traffic_police_id = 0;
                    }
                    if(traffic_police_id != 0){
                        destinationPath = receiptBookModel.getImagePath(traffic_police_id);
                        if(destinationPath.isEmpty())
                            destinationPath = "C:\\ssadvt_repository\\trafficPolice\\general\\no_image.png";
                    } else{
                        System.out.println("Image Not Found");
                        destinationPath = "C:\\ssadvt_repository\\trafficPolice\\general\\no_image.png";
                    }

                    //String destinationPath = keyModel.exist_image(emp_code);
                    //destinationPath = "C:\\ssadvt_repository\\trafficPolice\\temp_Chrysanthemum.jpg";
                    //destinationPath = receiptBookModel.getDestinationPath("traffic_police");
                    //String image_path = receiptBookModel.getImagePath(traffic_police_id);
                    File f = new File(destinationPath);
                    FileInputStream fis = new FileInputStream(f);
                    response.setContentType("image/jpeg");
                    //  response.addHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                    // BufferedImage bi=ImageIO.read(f);
                    response.setContentLength(fis.available());
                    ServletOutputStream os = response.getOutputStream();
                    BufferedOutputStream out = new BufferedOutputStream(os);
                    int ch = 0;
                    //byte[] buf = new byte[32 * 1024];
                    /*
                     ServletOutputStream os = response.getOutputStream();
            FileInputStream is = new FileInputStream(new File(img_destination));
            byte[] buf = new byte[32 * 1024];
            int nRead = 0;
              while ((nRead = is.read(buf)) != -1) {
                os.write(buf, 0, nRead);
               }
            os.flush();
            os.close();
            is.close();
                     */
                    while ((ch = fis.read()) != -1) {
                        out.write(ch);
                        //os.write(buf, 0, ch);
                    }

                    os.flush();
                    bis.close();
                    fis.close();
                    out.close();
                    os.close();
                    //response.flushBuffer();

                    //keyModel.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("TrafficPoliceSearchController View_Image Error :" + e);
                    return;
                }
                /*int traffic_police_id = 0;
                try{
                    traffic_police_id = Integer.parseInt(request.getParameter("traffic_police_id"));
                }catch(Exception ex){
                    traffic_police_id = 0;
                }
                
                return;*/
            }
            if(task.equals("ADD NEW OFFENCE")){

                request.setAttribute("offender_license_no", searchReceiptBookNo);
                request.setAttribute("vehicle_no", searchReceiptNo);
                request.setAttribute("menuid", menuid);
                request.getRequestDispatcher("trafficePoliceInsertView").forward(request, response);
                return;
            }
            if(task.equals("Save")){
                ReceiptBookBean receiptBookBean=new ReceiptBookBean();
                String[] challan_book = request.getParameterValues("challan_book");
                int length = challan_book.length;
                double amount = 0;
                for(int i = 0; i < length; i++ ){
                    String[] ch_split = challan_book[i].split("_");
                    String amt = ch_split[ch_split.length-1];
                    amount = amount + Double.parseDouble(amt);
                }
                receiptBookBean.setChallan_book(challan_book);
                String receipt_book_no = request.getParameter("receipt_book_no");
                String receipt_book_revision = request.getParameter("receipt_book_revision");
                String receipt_no = request.getParameter("receipt_no");
                if(receipt_book_no != null && !receipt_book_no.isEmpty())
                    receiptBookBean.setReceipt_book_no(Integer.parseInt(receipt_book_no));
                if(receipt_book_revision != null && !receipt_book_revision.isEmpty())
                    receiptBookBean.setBook_revision_no(Integer.parseInt(receipt_book_revision));
                if(receipt_no != null && !receipt_no.isEmpty())
                    receiptBookBean.setPage_no(Integer.parseInt(receipt_no));
                receiptBookBean.setAmount(amount);

//                int receipt_no = 0;
//                try{
//                    receipt_no = Integer.parseInt(request.getParameter("receipt_no"));
//                }catch(Exception ex){
//                    receipt_no = 0;
//                }
               
                int rowAffacted = 0;
                rowAffacted = receiptBookModel.insertRecord(receiptBookBean);
                if(rowAffacted > 0)
                    request.setAttribute("receipt_no", receipt_no+1);
                else
                    request.setAttribute("receipt_no", receipt_no);
                menuid = request.getParameter("menuid");
                request.setAttribute("menuid", menuid);
                request.setAttribute("message", receiptBookModel.getMessage());
                request.setAttribute("msgBgColor", receiptBookModel.getMsgBgColor());
                request.getRequestDispatcher("trafficePoliceReceiptView").forward(request, response);
                return;
            }

            String search = "";
            //search = request.getParameter("search");
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
            if(task.equals("Search"))
                lowerLimit = 0;
            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            }
            /*if (task.equals("Search") || task.equals("clear")) {

            lowerLimit = 0;
            }*/
            //if(!searchReceiptBookNo.equals("") || !searchReceiptNo.equals("")){

            noOfRowsInTable = receiptBookModel.getNoOfRows(searchReceiptBookNo, searchReceiptNo);                  // get the number of records (rows) in the table.
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
          List<ReceiptBookBean> list = receiptBookModel.showData(lowerLimit, noOfRowsToDisplay, searchReceiptBookNo, searchReceiptNo);

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
            request.setAttribute("receiptList", list);
            //}

            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", receiptBookModel.getMessage());
            request.setAttribute("msgBgColor", receiptBookModel.getMsgBgColor());
            request.setAttribute("searchReceiptBookNo", searchReceiptBookNo);
            request.setAttribute("searchBookNo", searchBookNo);
            request.setAttribute("searchVehicleType", searchVehicleType);
            request.setAttribute("searchReceiptNo", searchReceiptNo);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("offender",request.getParameter("offender_name") );
            request.setAttribute("menuid", menuid);

            //  request.setAttribute("searchKeyPerson", searchKeyPerson);
            receiptBookModel.closeConnection();
            request.getRequestDispatcher("receiptSearchView").forward(request, response);

        } catch (Exception e) {
            System.out.println("TrafficPoliceSearchController Exception: " + e);
            request.getRequestDispatcher("receiptSearchView").forward(request, response);
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

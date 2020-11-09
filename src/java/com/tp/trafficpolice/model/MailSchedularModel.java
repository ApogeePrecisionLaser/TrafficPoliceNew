/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.trafficpolice.model;

import com.tp.tableClasses.MISBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.pdfbox.util.PDFMergerUtility;

/**
 *
 * @author DELL
 */
public class MailSchedularModel {
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private static Connection connection;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public void setConnection() {
        try {
            Class.forName(driverClass);
             connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            //connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("PoleDetailTypeModel setConnection() Error: " + e);
        }
    }
    UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
    KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    List list1 = new ArrayList();
    private ServletContext ctx;
    
    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df.format(dt);
    String path = "";
//ServletContext ctx = getServletContext();
    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
    }
    HttpServletRequest request;
    HttpServletResponse response;
//    List<String> emailId = MailSchedularModel.getSendto();
 String emailId = "shubhamapogee243@gmail.com";
  //  List<String> emailId = MailSchedularModel.getSendto();
    
    public String mailContent(String FileNamePath, String emailId) {
        /* Assumptions:
         * 1) "toEmailList" contains at least one entry (email address).
         * "toEmailList" is the list of all primary recipients e.g. "jpss1277@gmail.com".
         * 2) "bccEmailList" is the list of all Blind Carbon Copy(bcc) recipients (here it is sold_by person).
         */
//       String user_name = "navitus.robin@gmail.com";
//        String user_password = "robin@9905";
         
        String user_name = MailSchedularModel.getEmail();
        String user_password = MailSchedularModel.getPassword();
        
        try {
            // Code to retrieve the email id, and password from which email will be send.
//            String query = "SELECT m.sender_user_name, m.sender_password FROM mail AS m ";
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rset = pstmt.executeQuery();
//            if (rset.next()) {
//                //user_name = rset.getString("sender_user_name");
//                //user_password = rset.getString("sender_password");
//            } else {
//                return "No mails were sent. Error: No records were found in Mail Info table.";
//            }

            List sent_tolist = new ArrayList();
            sent_tolist.add(emailId);
             List<String> bcc= new ArrayList<String>(MailSchedularModel.getSendto());
             
//        String[] bcc={ "shubham.srivastava243@gmail.com" };
            String host = "smtp.gmail.com";
//            String port = "25";
            String port = "465";
            String subject = "Daily Online Challan Report";
            String bodyText = "Dear Sir/Madam,\n"
                    + "Please find attached PDF Report of Online Challan \n";

            SendMailModel sendMailModel = new SendMailModel(connection);
            sendMailModel.setHost(host);
            sendMailModel.setBcc(bcc);
            sendMailModel.setPort(port);
            sendMailModel.setUser_name(user_name);
            sendMailModel.setUser_password(user_password);
            sendMailModel.setTo(sent_tolist);
            sendMailModel.setSubject(subject);
            sendMailModel.setBodyText(bodyText);
            sendMailModel.setFileName(FileNamePath);
            return sendMailModel.sendMail();
            // System.out.println("mailed Content successfully");
        } catch (Exception e) {
            System.out.println("PreRegistrationModel - mailContent Error: " + e);
            return e.toString();
        }

    }
    
    public String generateREport(int count,ServletContext ctx,String searchFromDate,String searchToDate,String searchOfficerName) throws IOException {

        setDriverClass(ctx.getInitParameter("driverClass"));
        setConnectionString(ctx.getInitParameter("connectionString"));
        setDb_username(ctx.getInitParameter("db_userName"));
        setDb_password(ctx.getInitParameter("db_userPasswrod"));
        setConnection();
        
        
        
        String jrxmlFilePath;
        List list = null;
        MISModel mis = new MISModel();
        //list = getAllRecords(overheadTank_id);
        list=showAllData1(searchFromDate,searchToDate,searchOfficerName);
        if(list.size()>0){
            try{
         String folder_path = "C:\\ssadvt_repository\\SmartMeterSurvey\\chart3\\";
        File file = new File(folder_path);
        if (!file.exists()) {
            file.mkdirs();
        }

        //response.setContentType("application/pdf");
       // response.setCharacterEncoding("UTF-8");
        //ServletOutputStream servletOutputStream = response.getOutputStream();
        jrxmlFilePath = ctx.getRealPath("/report/Mis_report.jrxml");
        //list = getAllRecords(overheadTank_id);


        //byte[] reportInbytes = GeneralModel.generateRecordList(jrxmlFilePath, list);
        //response.setContentLength(reportInbytes.length);
        //servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
        //servletOutputStream.flush();
        //servletOutputStream.close();

       // path = folder_path + cut_dt + "__" + overheadTank_id + ".pdf";
        path = folder_path;

       result= SavePdf(jrxmlFilePath,list,cut_dt + "__" + count + ".pdf",path);
}
            catch(Exception e){
                System.out.println(e);
            }
        }
        
        return result;
    }
public String SavePdf(String jrxmlFilePath, List list, String reportName,String path) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        String mail_status="";
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            //JRXlsExporter exporter = new JRXlsExporter();
            //String path = createAppropriateDirectories1("C:/ssadvt_repository/prepaid/temp_pdf");
            path = path +reportName;

            list1.add(path);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
            exporter.exportReport();

            //merg two pdf
            mail_status=doMerge(list1);

        } catch (Exception e) {
            System.out.println("GeneralModel generateExcelList() JRException: " + e);
        }
        return mail_status;
    }

public String doMerge(List list)  {
        String image_name = "";
        String mail_status="";
         try{

//                 String[] time=date.split(" ");
//                 String s_date=time[0];
//                 String[] curr_array  = s_date.split("-");
//                 String  d = curr_array[2] + "-" + curr_array[1] + "-" +  curr_array[0];
//                 GeneralModel generalModel = new GeneralModel();
                 String img = "C:/ssadvt_repository/prepaid/RideReport/";
//           
                 File file1 = new File("C:/ssadvt_repository/prepaid/RideReport/");
        if (!file1.exists()) {
            file1.mkdirs();
        }
                 // path = path + "/" +reportName;
//           // String img="C:/ssadvt_repository/prepaid/RideReport/"+d;
            //image_name = d+"_"+id+".pdf";
                 image_name = "Daily_OnlineChallanReport"+".pdf";
           String destination = img + "/"+ image_name;
           Iterator itr = list.iterator();
           /* File destinationDir = new File(destination_path + "temp_single_bill");  //C:/ssadvt_repository/MPEB/temp_service_connection
            if (!destinationDir.isDirectory()) {
                destinationDir.mkdirs();
            }*/

           File file=null;
            PDFMergerUtility ut = new PDFMergerUtility();
            while (itr.hasNext()) {
                String bill_image = itr.next().toString();
            String ext = bill_image.substring(bill_image.lastIndexOf(".") + 1);
                File f = null;
                if (ext.equals("pdf")) {
                    f = new File(bill_image);
                } else {
                    //bill_image = imageToPdfConvert(bill_image);
                   // f = new File(bill_image);
                }

                if (f.exists()) {
                    file = new File(bill_image);
                }
                if (!f.exists()) {
                    //file = new File(destination_path + "general\\bill_not_found.pdf");
                }
                ut.addSource(file);

            }
            ut.setDestinationFileName(destination);
            try {
                ut.mergeDocuments();
                //insertImageRecord(image_name,s_date,id);

                    //this code is for delete pdf

//                      File file2 = new File("C:/ssadvt_repository/prepaid1/temp_pdf");
//                      File[]  myFiles = file2.listFiles();
//                      for (int i=0; i<myFiles.length; i++) {
//                              myFiles[i].delete();
//                 }

           mail_status=     mailContent(destination,emailId);

            } catch (Exception e) {
                System.out.println("Merge Exception :" + e);
            }
        }catch(Exception e){

        }
        return mail_status;
    }



public static String getEmail() {
        String user_name="";
        String query = " SELECT id, user_name FROM email GROUP BY user_name ORDER BY user_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
          
            while (rset.next()) {    // move cursor from BOR to valid record.
                 user_name = rset.getString("user_name");
             
                
                    count++;
                
            }
            if (count == 0) {
                System.out.println("getStatustype ERROR inside StausTypeModel - ");
            }
        } catch (Exception e) {
            System.out.println("getStatustype ERROR inside StausTypeModel - " + e);
        }
        return user_name;
    }

public static String getPassword() {
        String user_password="";
        String query = " SELECT id, user_password FROM email GROUP BY user_password ORDER BY user_password";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
          
            while (rset.next()) {    // move cursor from BOR to valid record.
                 user_password = rset.getString("user_password");
             
                
                    count++;
                
            }
            if (count == 0) {
                System.out.println("getStatustype ERROR inside StausTypeModel - ");
            }
        } catch (Exception e) {
            System.out.println("getStatustype ERROR inside StausTypeModel - " + e);
        }
        return user_password;
    }


public static List<String> getSendto() {
        List<String> list = new ArrayList<String>();
        String query = " SELECT  sendto FROM email GROUP BY sendto ORDER BY sendto";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
          
            while (rset.next()) {    // move cursor from BOR to valid record.
                String sendto = rset.getString("sendto");
             
                    list.add(sendto);
                    count++;
                
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getStatustype ERROR inside StausTypeModel - " + e);
        }
        return list;
    }




public List<MISBean> showAllData1(String searchFromDate,String searchToDate,String searchOfficerName) {
        
        int bean129_177_count=0,bean128_177_count=0,bean39_192_count=0,bean56_192_count=0,bean66_192_count=0,bean146_196_count=0,bean46_177_count=0,bean77_177_count=0,bean115_177_count=0,bean3_181_count=0;
        int  bean116_177_count=0,bean117_177_count=0,bean122_177_count=0,bean100_177_count=0,bean125_177_count=0,bean21_25_177_count=0,bean119_177_count=0,bean81_177_count=0;
        int bean185_count=0,bean93_8_177_count=0,others_count=0;
        int j = 0;
         int total_other_count = 0,Bean56_192_count=0,Bean66_192_count=0;
         
     List<MISBean> list = new ArrayList<MISBean>();
        List kpId_list = new ArrayList(); 
        int date_status=0;
        String time=" 23:59:59";
        searchToDate=searchToDate+time;
        
        try {
            
                                       

                 String query = "select tp.key_person_id\n" +
                                 "from traffic_police tp\n" +
                                 "Where IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
                                +"group by tp.key_person_id\n" +
                                 "Having tp.key_person_id > 0";
                                 
                  String query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
                               "from  traffic_police tp\n" +
                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
                               "where kp.key_person_id=?\n" +
                               "AND IF ('" + searchFromDate + "'='',tp.offence_date LIKE '%%',( (tp.offence_date) Between '" + searchFromDate + "' AND '" + searchToDate + "'))\n"//%m/%d/%Y
                               +" group by act";
                  
                  if (searchFromDate.equals(searchToDate) && searchFromDate != null) {
                      date_status++;
                  query = "select tp.key_person_id\n" +
                                 " from traffic_police tp\n" +  
                                " Where tp.offence_date like '"+searchFromDate+"%'"
                                +" group by tp.key_person_id\n" +
                                 " Having tp.key_person_id > 0";
                                 
                   query1="select key_person_name,act,count(act) as act_count,offence_code\n" +
                               "from  traffic_police tp\n" +
                               "Inner Join key_person kp ON tp.key_person_id = kp.key_person_id\n" +
                               "Inner Join  traffic_offence_map tom ON tp.traffic_police_id = tom.traffic_police_id\n" +
                               "Inner Join  offence_type oft ON tom.offence_type_id = oft.offence_type_id\n" +
                               "where kp.key_person_id=?\n" +
                               " AND tp.offence_date like '"+searchFromDate+"%'\n"
                              +" group by act";
                System.out.println(searchFromDate);
                System.out.println(searchToDate);
            }
                  
                  
                  PreparedStatement pstmt = connection.prepareStatement(query);
            
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                kpId_list.add(rset.getString("key_person_id"));
            }
            
            PreparedStatement pstmt1 = connection.prepareStatement(query1);
       
            for(int i=0;i<kpId_list.size();i++){
                int offence_count=0;
                pstmt1.setInt(1,Integer.parseInt(kpId_list.get(i).toString()));
            ResultSet rset1 = pstmt1.executeQuery();
            MISBean bean = new MISBean();
            bean.setSearchFromDate(searchFromDate);
            bean.setSearchToDate(searchToDate.split(" ")[0]);
            while (rset1.next()) {
                
                bean.setKey_person_name(unicodeToKruti.Convert_to_Kritidev_010(rset1.getString("key_person_name")));
//                  bean.setKey_person_name(rset1.getString("key_person_name"));
                String act=rset1.getString("act");
                String offence_code = rset1.getString("offence_code");
                //rset.getString("act_count");
                  if(offence_code.equals("34") || offence_code.equals("33")){
                      bean.setBean129_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean129_177_count=bean129_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                      
                  }
                  
                  if(offence_code.equals("32") || offence_code.equals("31")){
                      bean.setBean128_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean128_177_count=bean128_177_count+Integer.parseInt(rset1.getString("act_count"));
                          j = 1;
                  }
                   
                  if(offence_code.equals("81") || offence_code.equals("71") || offence_code.equals("72") || offence_code.equals("77") || offence_code.equals("78") || offence_code.equals("79")){
                      bean.setBean39_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean39_192_count=bean39_192_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;

                  }
                  
                  if(offence_code.equals("83") || offence_code.equals("84") || offence_code.equals("88")){
                     //bean.setBean56_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean56_192_count=bean56_192_count+Integer.parseInt(rset1.getString("act_count"));
                     bean.setBean56_192(String.valueOf(bean56_192_count));
                     j = 1;

                  }
                   
                  if(offence_code.equals("7") || offence_code.equals("8") || offence_code.equals("9") || offence_code.equals("10")){
                      //bean.setBean66_192(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean66_192_count=bean66_192_count+Integer.parseInt(rset1.getString("act_count"));
                      bean.setBean66_192(String.valueOf(bean66_192_count));
                      j = 1;
                  }
                  
                  if(offence_code.equals("91") || offence_code.equals("92") || offence_code.equals("94") || offence_code.equals("95") || offence_code.equals("96") || offence_code.equals("97") || offence_code.equals("98")){
                      bean.setBean146_196(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean146_196_count=bean146_196_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  
                  if(offence_code.equals("113") || offence_code.equals("114") || offence_code.equals("178") || offence_code.equals("179") || offence_code.equals("180")){
                      bean.setBean77_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean77_177_count=bean77_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  
                  if(offence_code.equals("1") || offence_code.equals("2")){
                      bean.setBean3_181(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean3_181_count=bean3_181_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                   
                  if(offence_code.equals("15") || offence_code.equals("17")){
                      bean.setBean117_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean117_177_count=bean117_177_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;
                  }
                  if(offence_code.equals("21") || offence_code.equals("22")){
                      bean.setBean122_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean122_177_count=bean122_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("115") || offence_code.equals("116")){
                      bean.setBean100_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean100_177_count=bean100_177_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;
                  }
                  if(offence_code.equals("27") || offence_code.equals("28") || offence_code.equals("131") || offence_code.equals("132")){
                      bean.setBean125_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean125_177_count=bean125_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("107") || offence_code.equals("108")){
                      bean.setBean21_25_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean21_25_177_count=bean21_25_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("16") || offence_code.equals("18")){
                      bean.setBean119_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean119_177_count=bean119_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("155") || offence_code.equals("156")){
                      bean.setBean81_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean81_177_count=bean81_177_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("57")){
                      bean.setBean185(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean185_count=bean185_count+Integer.parseInt(rset1.getString("act_count"));
                      j = 1;
                  }
                  if(offence_code.equals("133") || offence_code.equals("134")){
                      bean.setBean93_8_177(rset1.getString("act_count"));
                      offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                      bean93_8_177_count=bean93_8_177_count+Integer.parseInt(rset1.getString("act_count"));
                     j = 1;
                  }
                  else{
                      if(j==0){
                 
                  offence_count=offence_count+Integer.parseInt(rset1.getString("act_count"));
                  others_count=others_count+Integer.parseInt(rset1.getString("act_count"));   
                // bean.setOthers_count(String.valueOf(others_count));
                 bean.setOthers(String.valueOf(others_count));
                  }
                      j = 0;
                  }
            }
             Bean56_192_count = Bean56_192_count+bean56_192_count;
           bean56_192_count=0;
           Bean66_192_count = Bean66_192_count+bean66_192_count;
           bean66_192_count=0;
            total_other_count = total_other_count+others_count;
             others_count = 0;
            bean.setPerson_challan_count(offence_count+"");
            if(i == kpId_list.size()-1){
                //MISBean bean1 = new MISBean();
                int total=0;
                List<MISBean> list1 = new ArrayList<MISBean>();
            bean.setBean129_177_count(bean129_177_count+"");
            bean.setBean128_177_count(bean128_177_count+"");
            bean.setBean39_192_count(bean39_192_count+"");
            bean.setBean56_192_count(Bean56_192_count+"");
            bean.setBean66_192_count(Bean66_192_count+"");
            bean.setBean146_196_count(bean146_196_count+"");
            bean.setBean77_177_count(bean77_177_count+"");
            bean.setBean3_181_count(bean3_181_count+"");
            bean.setBean117_177_count(bean117_177_count+"");
            bean.setBean122_177_count(bean122_177_count+"");
            bean.setBean100_177_count(bean100_177_count+"");
            bean.setBean125_177_count(bean125_177_count+"");
            bean.setBean21_25_177_count(bean21_25_177_count+"");
            bean.setBean119_177_count(bean119_177_count+"");
            bean.setBean81_177_count(bean81_177_count+"");
            bean.setBean185_count(bean185_count+"");
            bean.setBean93_8_177_count(bean93_8_177_count+"");
            bean.setOthers_count(total_other_count+"");
            
            //bean.setM(list1);
            total=bean129_177_count+bean128_177_count+bean39_192_count+Bean56_192_count+Bean66_192_count+bean146_196_count+
                    bean77_177_count+bean3_181_count+bean117_177_count+bean122_177_count+bean100_177_count+bean125_177_count+bean21_25_177_count
                    +bean119_177_count+bean81_177_count+bean185_count+bean93_8_177_count+total_other_count;
            
            bean.setTotal(total);
            list1.add(bean);
            }
            list.add(bean);
            }
            
            
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--showData " + e);
        }
        return list;
             
    }
public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }
    
//public static Connection getConnection() {
//        return connection;
//    }
//
//    public static void setConnection(Connection connection) {
//        //SendPDFSchedularModel.connection = connection;
//    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }

    
    
}

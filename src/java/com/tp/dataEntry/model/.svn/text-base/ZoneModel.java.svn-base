package com.tp.dataEntry.model;

import com.tp.tableClasses.ZoneBean;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class ZoneModel {
    private Connection connection;
    private String driver,url,user,password;
    private String message,messageBGColor;

     public byte[] generateMapReport(String jrxmlFilePath,List<ZoneBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
         HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in ZoneModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

     public ByteArrayOutputStream generateExcelReport(String jrxmlFilePath,List<ZoneBean> listAll) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
         Connection c;
         HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, beanColDataSource);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("Error: in ZoneModel generateExcelReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<ZoneBean> showAllData(String zoneName)
    {
        ArrayList<ZoneBean> list = new ArrayList<ZoneBean>();
        String query=   "select zone_name,zone_description,country_name from zone, country where zone.country_id=country.country_id and if('"+zoneName+"'='',zone_name LIKE '%%',zone_name='"+zoneName+"')";
                        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ZoneBean zoneBean= new ZoneBean();                
                zoneBean.setZoneName(rset.getString("zone_name"));
                zoneBean.setZoneDescription(rset.getString("zone_description"));
                zoneBean.setCountryName(rset.getString("country_name"));
                
                list.add(zoneBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData --- ZoneModel : " + e);
        }

        return list;
    }
    public List<String> getZone(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT zone_id, zone_name FROM zone GROUP BY zone_name ORDER BY zone_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_type = rset.getString("zone_name");
                if (zone_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Zone exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZone ERROR inside ZoneModel - " + e);
        }
        return list;
    }
    public List<String> getZoneName(String q,String countryName) {
        List<String> list = new ArrayList<String>();     
        String query = " SELECT zone_name FROM zone where zone.country_id=(select country.country_id from country where country.country_name='"+countryName+"') GROUP BY zone_name ORDER BY  zone_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_type = rset.getString("zone_name");                
                if (zone_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Zone exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside ZoneModel - " + e);
        }
        return list;
    }
    public void updateRecord(String countryName,String zoneId,String zoneName,String zoneDescription)
    {       
        PreparedStatement presta=null;
        try
        {
            String query = "update zone set zone_name='"+zoneName.trim()+"',zone_description='"+zoneDescription.trim()+"',"
                    +"country_id=(select country_id from country where '"+countryName.trim()+"'=country.country_name)"
                    +"where zone_id="+Integer.parseInt(zoneId);
            presta = connection.prepareStatement(query);           
            int i = presta.executeUpdate();
            if(i>0)
            {
                message=i+" Record updated successfully......";
                messageBGColor="yellow";
            }
            else
            {
                message="Record not updated successfully......";
                messageBGColor="red";
            }
        }catch(Exception e)
        {
            System.out.println("Error in updateRecord ---- ZoneModel : "+e);
        }
    }    
    public void deleteRecord(int zoneId)
    {
        PreparedStatement presta=null;
        try
        {
            presta = connection.prepareStatement("delete from zone where zone_id=?");
            presta.setInt(1, zoneId);
            int i = presta.executeUpdate();
            if(i>0)
            {
                message="Record deleted successfully......";
                messageBGColor="yellow";
            }
            else
            {
                message="Record not deleted successfully......";
                messageBGColor="red";
            }
        }catch(Exception e)
        {
            message="Record not deleted due to dependency";
            messageBGColor="red";
            System.out.println("Error in deleteRecord ---- ZoneModel - "+e);
        }
    }
    public ArrayList<ZoneBean> getAllRecords(int lowerLimit,int noOfRowsToDisplay,String searchZone)
    {
        ArrayList<ZoneBean> list = new ArrayList<ZoneBean>();
        
        String query ="SELECT zone_id,zone_name,zone_description,country_name "
                +"FROM zone,country "
                +"WHERE IF('"+searchZone +"'='',zone_name LIKE '%%',zone_name=?) and zone.country_id=country.country_id "
                +"order by zone_name limit "+ lowerLimit +","+ noOfRowsToDisplay ;
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
             pstmt.setString(1, searchZone);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ZoneBean zoneBean = new ZoneBean();
                zoneBean.setZoneId(rset.getInt(1));
                zoneBean.setZoneName(rset.getString(2));
                zoneBean.setZoneDescription(rset.getString(3));                
                zoneBean.setCountryName(rset.getString(4));
               
                list.add(zoneBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod in ZoneModel " + e);
        }
        return list;
    }
    
   
    public int getTotalRowsInTable(String searchZone)
    {
         String query = " SELECT Count(*) "
                + " FROM zone "
                + " WHERE IF('" + searchZone + "' = '', zone_name LIKE '%%',zone_name =?) "
                + " ORDER BY zone_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchZone);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows ZoneModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;        
    }

    
    public void insertRecord(String[] countryName,String[] zone_id,String[] zoneName,String[] zoneDescription)
    {        
       int rowAffected=0;
       int rowNotAffected=0;
       message="";       
       for(int i=0;i<zone_id.length;i++)
       {
            if(zone_id[i].equals("1"))
            {
                PreparedStatement presta;
                try
                {
                    String query1 = "select zone_name from zone where zone_name='"+zoneName[i].trim()+"'";
                    String query2 = "insert into zone(zone_name,zone_description,country_id) "
                            +"values('"+zoneName[i].trim()+"','"+zoneDescription[i].trim()+"',(select country_id from country where country_name='"+countryName[i].trim()+"'))";
                    presta = connection.prepareStatement(query1);
                    ResultSet result = presta.executeQuery();
                    if(result.next())
                        rowNotAffected++;
                    else
                    {
                        presta = connection.prepareStatement(query2);
                        presta.executeUpdate();
                        rowAffected++;
                    }                    
                }catch(Exception e)
                {
                    System.out.println("Error in addAllRecords inside ZoneModel---- "+e);
                }
               
            }
       }
         if(rowAffected>0)
         {
           message=rowAffected+" Record inserted";
           messageBGColor="yellow";
         }         
        if(rowNotAffected>0)
        {
            message = message+"("+rowNotAffected+" : Record not inserted because of existing date)";
            messageBGColor="yellow";
        }
    }

    
   
    public void closeConnection()
    {
        try
        {
            connection.close();           
        }catch(Exception e)
        {
            System.out.println("ZoneModel closeConnection: "+e);
        }
    }
    public void setConnection()
    {
     try
     {
        Class.forName(driver);
        connection = DriverManager.getConnection(url + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8",user,password);
     }catch(Exception e)
     {
        System.out.println("ZoneModel setConnection error: "+e);
     }
    }
    public Connection getConnection()
    {
        return connection;
    }
    public void setDriver(String driver)
    {
        this.driver = driver;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
    public void setUser(String user)
    {
        this.user = user;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getDriver()
    {
        return driver;
    }

    public String getUrl()
    {
        return url;
    }
    public String getUser()
    {
        return user;
    }
    public String getPassword()
    {
        return password;
    }
    public String getMessage()
    {
        return message;
    }
    public String getMessageBGColor()
    {
        return messageBGColor;
    }

}
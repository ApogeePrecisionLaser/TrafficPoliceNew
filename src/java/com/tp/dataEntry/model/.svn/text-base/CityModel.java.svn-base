package com.tp.dataEntry.model;

import com.tp.tableClasses.CityBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JRExporterParameter;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class CityModel {
    private Connection connection;
    private String driver,url,user,password;
    private String message,messageBGColor;
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

     public byte[] generateMapReport(String jrxmlFilePath,List<CityBean> listAll) {
        byte[] reportInbytes = null;        
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in CityModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
     public ByteArrayOutputStream generateCityXlsRecordList(String jrxmlFilePath,List list) {
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
              //  HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("CityStatusModel generatReport() JRException: " + e);
                }
                return bytArray;
            }
    public List<CityBean> showAllData(String cityName)
    {
        cityName = krutiToUnicode.convert_to_unicode(cityName);
        ArrayList<CityBean> list = new ArrayList<CityBean>();
        String query=   "select city_name,city_description,district_name,division_name from city,district,division where city.district_id=district.district_id"
                + " and city.division_id=division.division_id and if('"+cityName+"'='',city_name LIKE '%%',city_name='"+cityName+"')";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            
            while (rset.next()) {                
                CityBean cityBean= new CityBean();
                cityBean.setCityName(rset.getString("city_name"));
                cityBean.setCityDescription(rset.getString("city_description"));
                cityBean.setDistrictName(rset.getString("district_name"));
                cityBean.setDivisionName(rset.getString("division_name"));                
              
                list.add(cityBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData --- CityModel : " + e);
        }

        return list;
    }

     public List<String> getDivision(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT division_name FROM division GROUP BY division_name ORDER BY division_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String division_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("division_name"));
                if (division_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such city exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCity ERROR inside CityModel - " + e);
        }
        return list;
    }
    public List<String> getCity(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT city_id, city_name FROM city GROUP BY city_name ORDER BY city_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name"));
                if (city_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such city exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCity ERROR inside CityModel - " + e);
        }
        return list;
    }
    public List<String> getCityName(String q,String distName) {
        List<String> list = new ArrayList<String>();       

        String query = "select city_name FROM city where city.district_id=(select district.district_id from district where district_name='"+distName+"') GROUP BY city_name ORDER BY city_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_type = rset.getString("city_name");
                if (city_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such city exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCityName ERROR inside CityModel - " + e);
        }
        return list;
    }
    public List<String> getDistrict(String q,String diviName) {
        List<String> list = new ArrayList<String>();
        diviName = krutiToUnicode.convert_to_unicode(diviName);
        String query = " SELECT district_name FROM district where district.division_id=(select division.division_id from division where division.division_name='"+diviName+"') GROUP BY district_name ORDER BY district_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String district_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name"));
                if (district_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(district_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such District exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDistrict ERROR inside CityModel - " + e);
        }
        return list;
    }

public void updateRecord(String divisionName,String districtName,String cityId,String cityName,String cityDescription)
{      
      
      PreparedStatement prestaDivision=null;
      PreparedStatement prestaDistrict=null;
      PreparedStatement prestaCity=null;
      PreparedStatement presta = null;
      int rowAffected=0;     
      try
      {
          divisionName = krutiToUnicode.convert_to_unicode(divisionName);
          districtName = krutiToUnicode.convert_to_unicode(districtName);
          cityName = krutiToUnicode.convert_to_unicode(cityName);
          cityDescription = krutiToUnicode.convert_to_unicode(cityDescription);
        String divisionQuery = "select count(*) from division where division_name='"+divisionName+"'";
        prestaDivision = connection.prepareStatement(divisionQuery);
        ResultSet resultDivision = prestaDivision.executeQuery();
        resultDivision.next();
        int divisionNo = resultDivision.getInt(1);
        if(divisionNo==1)
        {
            String districtQuery = "select count(*) from district where district_name='"+districtName+"'";
            prestaDistrict = connection.prepareStatement(districtQuery);
            ResultSet resultDistrict = prestaDistrict.executeQuery();
            resultDistrict.next();
            int districtNo = resultDistrict.getInt(1);
            if(districtNo==1)
            {
                String cityQuery = "select count(*) from city where city_name='"+cityName+"'";
                prestaCity = connection.prepareStatement(cityQuery);
                ResultSet resultCity = prestaCity.executeQuery();
                resultCity.next();
                int cityNo = resultCity.getInt(1);              
                   String query = "update city set city_name='"+cityName.trim()+"',city_description='"+cityDescription.trim()+"',"
                    +"district_id=(select district_id from district where '"+districtName.trim()+"'=district.district_name),"
                    +"division_id=(select division_id from division where '"+divisionName.trim()+"'=division.division_name)"
                    +"where city_id="+Integer.parseInt(cityId.trim());
                   presta = connection.prepareStatement(query);
                   rowAffected = presta.executeUpdate();
                   if(rowAffected>0)
                   {
                        message="Record updated successfully";
                        messageBGColor="yellow";
                   }
                   else
                   {
                       message="Record not udate successfully";
                       messageBGColor="red";
                   }
                                 
            }
            else
            {
                message="District name does not exist";
                messageBGColor="red";
            }         
        }
        else
        {
            message="Division name does not exist";
            messageBGColor="red";
        }
        }catch(Exception e)
        {
            System.out.println("Error in updateRecord -- CityModel : "+e);
        }
}   
    public void deleteRecord(int cityId)
    {
        PreparedStatement presta=null;
        try
        {
            presta = connection.prepareStatement("delete from city where city_id=?");
            presta.setInt(1, cityId);
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
            System.out.println("Error in deleting recordl ---- CityModel : "+e);
        }
    }
    public ArrayList<CityBean> getAllRecords(int lowerLimit,int noOfRowsToDisplay,String searchCity)
    {
        searchCity = krutiToUnicode.convert_to_unicode(searchCity);
        ArrayList<CityBean> list = new ArrayList<CityBean>();
        /*
        String query = " SELECT city_id, city_name, city_description,district_id,division_id "
                + " FROM city "
                + " WHERE IF('" + searchCity + "' = '', city_name LIKE '%%',city_name =?) "
                + " ORDER BY city_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
         
         */
        String query ="SELECT city_id,city_name,city_description,district_name,division_name "
                +"FROM city,district,division "
                +"WHERE IF('"+searchCity +"'='',city_name LIKE '%%',city_name=?) AND city.district_id=district.district_id and city.division_id=division.division_id "
                +"order by city_name limit "+ lowerLimit +","+ noOfRowsToDisplay ;
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
             pstmt.setString(1, searchCity);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CityBean CityBean = new CityBean();
                CityBean.setCityId(rset.getInt(1));
                CityBean.setCityName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(2)));
                CityBean.setCityDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(3)));
                CityBean.setDistrictName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(4)));
                CityBean.setDivisionName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(5)));
                list.add(CityBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- CityModel : " + e);
        }
        return list;
    }
    
   
    public int getTotalRowsInTable(String searchCity)
    {
        searchCity = krutiToUnicode.convert_to_unicode(searchCity);
         String query = " SELECT Count(*) "
                + " FROM city "
                + " WHERE IF('" + searchCity + "' = '', city_name LIKE '%%',city_name =?) "
                + " ORDER BY city_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchCity);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CityModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;        
    }
    
    public void insertRecord(String[] divisionName,String[] districtName, String[] city_id,String[] cityName,String[] cityDescription)
    {
      PreparedStatement prestaDivision=null;
      PreparedStatement prestaDistrict=null;
      PreparedStatement prestaCity=null;
      PreparedStatement presta = null;
      int rowAffected=0;
      int rowNotAffected=0;
      for(int i=0;i<city_id.length;i++)
      {
        if(city_id[i].equals("1"))
        {
            try
            {
                divisionName[i] = krutiToUnicode.convert_to_unicode(divisionName[i]);
                districtName[i] = krutiToUnicode.convert_to_unicode(districtName[i]);
                cityName[i] = krutiToUnicode.convert_to_unicode(cityName[i]);
                cityDescription[i] = krutiToUnicode.convert_to_unicode(cityDescription[i]);

                String divisionQuery = "select count(*) from division where division_name='"+divisionName[i]+"'";
                prestaDivision = connection.prepareStatement(divisionQuery);
                ResultSet resultDivision = prestaDivision.executeQuery();
                resultDivision.next();
                int divisionNo = resultDivision.getInt(1);
                if(divisionNo==1)
                {
                    String districtQuery = "select count(*) from district where district_name='"+districtName[i]+"'";
                    prestaDistrict = connection.prepareStatement(districtQuery);
                    ResultSet resultDistrict = prestaDistrict.executeQuery();
                    resultDistrict.next();
                    int districtNo = resultDistrict.getInt(1);
                    if(districtNo==1)
                    {
                        String cityQuery = "select count(*) from city where city_name='"+cityName[i]+"'";
                        prestaCity = connection.prepareStatement(cityQuery);
                        ResultSet resultCity = prestaCity.executeQuery();
                        resultCity.next();
                        int stateNo = resultCity.getInt(1);
                        if(stateNo==1)
                            rowNotAffected++;
                        else
                        {
                            String query = "insert into city(city_name,city_description,district_id,division_id)"
                                            + "values('"+cityName[i].trim()+"','"+cityDescription[i].trim()+"',(select district.district_id from district where '"+districtName[i].trim()+"'=district.district_name),(select division.division_id from division where '"+divisionName[i].trim()+"'=division.division_name))";
                            presta = connection.prepareStatement(query);
                            presta.executeUpdate();
                            rowAffected++;
                        }
                    }
                    else
                        rowNotAffected++;
                }
                else
                    rowNotAffected++;
          }catch(Exception e)
        {
              System.out.println("Error in insertRecord in CityModel : "+e);
        }
      }
     }
       if(rowAffected>0)
      {
          message=rowAffected+" Record inserted successfully";
          messageBGColor="yellow";
      }
      if(rowNotAffected>0)
      {
          if(message==null)
             message ="";
          message = message+"("+rowNotAffected+" Record not inserted (Only City name should unique))";
          messageBGColor="red";
      }
}   
   
    public void closeConnection()
    {
        try
        {
            connection.close();           
        }catch(Exception e)
        {
            System.out.println("CityModel closeConnection: "+e);
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
        System.out.println("CityModel setConnection error: "+e);
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
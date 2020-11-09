package com.tp.trafficpolice.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.tp.tableClasses.TrafficPoliceQuickBean;
import com.tp.util.KrutiDevToUnicodeConverter;
import com.tp.util.UnicodeToKrutiDevConverter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
    public class TrafficPoliceQuickEntryModel
{
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_password;
    public static KrutiDevToUnicodeConverter ku=new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter uk=new UnicodeToKrutiDevConverter();
    private int rowsAffected=0;

     public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_userName, db_password);
            System.out.println("connected - " + connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public List<String> getActOrigin(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = "select act_origin_id, act_origin from act_origin "
                        +" GROUP BY act_origin ORDER BY act_origin";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {
               String actOrigin=uk.Convert_to_Kritidev_010(rs.getString("act_origin"));
                if (actOrigin.toUpperCase().startsWith(q.toUpperCase()))
                {
                     list.add(actOrigin);
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }
    public List<String> getAct(String q,String act_origin)
    {
       List<String> list = new ArrayList<String>();
        String query = "SELECT ot.offence_type_id,ot.act from offence_type as ot , act_origin as ao where ot.act_origin_id=ao.act_origin_id " 
                +"AND IF('" + act_origin + "' = '', ao.act_origin LIKE '%%',ao.act_origin ='"+act_origin+"') "
                + " GROUP BY act ORDER BY act";
        try
        {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String act=rs.getString("act");
                
                     list.add(act);
                     count++;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }
    public List<String> getOffenceType(String q,String act_origin)
    {
         List<String> list = new ArrayList<String>();
        String query = "SELECT ot.offence_type_id,ot.offence_type from offence_type as ot , act_origin as ao where ot.act_origin_id=ao.act_origin_id  "
               +"AND IF('" + act_origin + "' = '', ao.act_origin LIKE '%%',ao.act_origin ='"+act_origin+"') "
                + " GROUP BY offence_type ORDER BY offence_type";
        try
        {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String offenceType=uk.Convert_to_Kritidev_010(rs.getString("offence_type"));
                if (offenceType.toUpperCase().startsWith(q.toUpperCase())) {
                     list.add(offenceType);
                    count++;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }
    public List<String> getOffenceCode(String q,String act_origin)
    {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ot.offence_type_id,ot.offence_code from offence_type as ot , act_origin as ao where ot.act_origin_id=ao.act_origin_id "
                +"AND IF('" + act_origin + "' = '', ao.act_origin LIKE '%%',ao.act_origin ='"+act_origin+"') "
                + " GROUP BY offence_code ORDER BY offence_code ";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String offenceCode=rs.getString("offence_code");
                     list.add(offenceCode);
                    count++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }
    public List<String> getDepositeAmount(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = "SELECT traffic_police_id,deposited_amount from offence_type "
                        + " GROUP BY deposited_amount ORDER BY deposited_amount";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String deposited_amount=uk.Convert_to_Kritidev_010(rs.getString("deposited_amount"));
                if (deposited_amount.toUpperCase().startsWith(q.toUpperCase())) {
                     list.add(deposited_amount);
                    count++;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }
    public List<String> getOfficerName(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = "SELECT key_person_id, key_person_name FROM key_person "
                        + " GROUP BY key_person_name ORDER BY key_person_name";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String key_person_name=uk.Convert_to_Kritidev_010(rs.getString("key_person_name"));
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                     list.add(key_person_name);
                    count++;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }
    public List<String> getOffenceCity(String q,String zone,String offence_location)
    {
        List<String> list = new ArrayList<String>();
        zone=ku.convert_to_unicode(zone);
        offence_location=ku.convert_to_unicode(offence_location);
       String query = "select c.city_name from city_location as cl,city as c,zone_new as z where c.city_id=z.city_id "
               + "and z.zone_new_id=cl.zone_new_id "+
               "AND IF('" + zone + "' = '', z.zone LIKE '%%',z.zone ='"+zone+"') "
               +"AND IF('" + offence_location + "' = '', cl.location LIKE '%%',cl.location ='"+offence_location+"') "
               +"GROUP BY c.city_name";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String city_name=uk.Convert_to_Kritidev_010(rs.getString("city_name"));
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                     list.add(city_name);
                    count++;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }
    public List<String> getOffenceLocation(String q,String offence_city,String zone)
    {
        List<String> list = new ArrayList<String>();
        offence_city=ku.convert_to_unicode(offence_city);
        zone=ku.convert_to_unicode(zone);
        String query = "select cl.location from city_location as cl, city as c, zone_new as z where c.city_id = z.city_id AND z.zone_new_id=cl.zone_new_id "
                +"AND IF('" + offence_city + "' = '', c.city_name LIKE '%%',c.city_name ='"+offence_city+"') "
                +"AND IF('" + zone + "' = '', z.zone LIKE '%%',z.zone ='"+zone+"') "
                +"GROUP BY cl.location";

        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String city_location=uk.Convert_to_Kritidev_010(rs.getString("location"));
                if (city_location.toUpperCase().startsWith(q.toUpperCase())) {
                     list.add(city_location);
                    count++;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }
    public List<String> getZone(String q,String offence_city,String offence_location)
    {
        List<String> list = new ArrayList<String>();
        offence_city=ku.convert_to_unicode(offence_city);
        offence_location=ku.convert_to_unicode(offence_location);
        String query = "select z.zone from city_location as cl,city as c,zone_new as z where c.city_id=z.city_id and z.zone_new_id=cl.zone_new_id "
               +"AND IF('" + offence_city + "' = '', c.city_name LIKE '%%',c.city_name ='"+offence_city+"') "
               + "AND IF('" + offence_location + "' = '', cl.location LIKE '%%',cl.location ='"+offence_location+"') "
               + "GROUP BY z.zone";

        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String zone=uk.Convert_to_Kritidev_010(rs.getString("zone"));
                if (zone.toUpperCase().startsWith(q.toUpperCase())) {
                     list.add(zone);
                    count++;
                }
            }
        } catch (Exception e)
        {
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }
    public int insertData(TrafficPoliceQuickBean trafficPolice)
    {
       try {
            String[] traffic_police_id =  trafficPolice.getTraffic_police_idM();// </editor-fold>

            for (int i = 0; i < trafficPolice.getTotal_no_of_rows(); i++)
            {
                int tpi = 0;
                tpi = (Integer.parseInt(traffic_police_id[i]));
                if (traffic_police_id[i].equals("1"))
                {
                    
 String query = "INSERT INTO traffic_police(offender_name,vehicle_no,offender_license_no,"
                + "city_location_id,deposited_amount,offence_date,reciept_no,book_no,book_revision_no,"
                + "processing_type_id,case_no,case_date,jarayam_no,offender_age,"
                + "city_id,offender_address,"
                + " vehicle_no_state, vehicle_no_city_code, vehicle_no_series, vehicle_no_digits, father_name) "
                + "VALUES(?,?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?, ?, ?, ?, ?,?)";
 String insert_query = "INSERT INTO traffic_offence_map(traffic_police_id,offence_type_id)"
                + "VALUES(?, ?)";
 String insert_case = "INSERT INTO case_processing (traffic_police_id, case_status_id, case_date) VALUES(?,(SELECT case_status_id FROM case_status WHERE case_status = 'Initial'),?)";
   PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
 pstmt.setString(1, ku.convert_to_unicode(trafficPolice.getOffender_nameM()[i]));
 String vehicle_no = trafficPolice.getVehicle_noM()[i];
 pstmt.setString(2, vehicle_no);
 pstmt.setString(3, trafficPolice.getOffender_license_noM()[i]);
 int locationid=getCity_location_id(ku.convert_to_unicode(trafficPolice.getCityM()[0]), ku.convert_to_unicode(trafficPolice.getZoneM()[0]), ku.convert_to_unicode(trafficPolice.getOffence_placeM()[0]));
 pstmt.setInt(4,locationid);
 pstmt.setDouble(5, Double.parseDouble(trafficPolice.getDeposited_amountM()[0]));
 pstmt.setDate(6, convertToSqlDate(trafficPolice.getOffence_dateM()[0]));
 pstmt.setInt(7, Integer.parseInt(trafficPolice.getReceipt_page_noM()[i]));
 int OfficerBookNo1=0;
 String OfficerBookNo=getOfficerBookNo(trafficPolice.getOfficer_nameM()[0]);
 if(OfficerBookNo=="")
 {
     OfficerBookNo1=0;
 }
 else
 {
     OfficerBookNo1=Integer.parseInt(OfficerBookNo);
     pstmt.setInt(8, OfficerBookNo1);
 //String book_revision_no = trafficPolice.getBook_revision_noM()[i];
                      // if(book_revision_no.isEmpty())
                      //  book_revision_no = trafficPolice.getBook_revision_noM()[i+1];
                     pstmt.setInt(9, getOfficerBookRevisionNoList(trafficPolice.getOfficer_nameM()[0]));
                     String processing_type1=trafficPolice.getProcessing_type();
                     if(processing_type1!=""){int processing_type = Integer.parseInt(trafficPolice.getProcessing_type());pstmt.setInt(10, processing_type);}
                    // if( processing_type== 0)
                    else pstmt.setNull(10, java.sql.Types.NULL);
                     // else
                      if(trafficPolice.getProcessing_type().equals("Court"))
                    {
                      String case_no = (trafficPolice.getCase_noM()[i]);
                      pstmt.setString(11, case_no);
                      pstmt.setDate(12, convertToSqlDate(trafficPolice.getCase_dateM()[i].toString()));
                    }
                  else
                 {
                    pstmt.setNull(11, java.sql.Types.NULL);
                     pstmt.setNull(12, java.sql.Types.NULL);
                 }
                     
                      pstmt.setString(13, trafficPolice.getJarayam_noM()[i]);
                      pstmt.setString(14, trafficPolice.getOffender_ageM()[i]);
                       int city_id = getCityId(ku.convert_to_unicode(trafficPolice.getOffender_cityM()[i]));
                        pstmt.setInt(15, city_id);
                      pstmt.setString(16, trafficPolice.getOffender_address_M()[i]);
                        pstmt.setString(17, trafficPolice.getVehicle_no_stateM()[i]);
                        pstmt.setString(18, trafficPolice.getVehicle_no_cityM()[i]);
                        pstmt.setString(19, trafficPolice.getVehicle_no_seriesM()[i]);
                        pstmt.setString(20, trafficPolice.getVehicle_no_digitsM()[i]);
                        pstmt.setString(21, ku.convert_to_unicode(trafficPolice.getFather_nameM()[i]));
                    rowsAffected = pstmt.executeUpdate();
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if(rs.next())
                        tpi = rs.getInt(1);
                    if (rowsAffected > 0) {
                    int trafficPolice_id = getTraffic_polise_id();
                    if(trafficPolice.getProcessing_type().equals("Court")){
                        PreparedStatement pst = (PreparedStatement) connection.prepareStatement(insert_case);
                        pst.setInt(1, trafficPolice_id);
                        pst.setDate(2, convertToSqlDate(trafficPolice.getCase_dateM()[i].toString()));
                        rowsAffected = pst.executeUpdate();
                    }//
                            String[] act = trafficPolice.getActM();
                            int l=act.length;
                            for(int j=0;j<l;j++)
                            {
                            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement(insert_query);
                            pstmt1.setInt(1, trafficPolice_id);
                            int offenceId=getOffenceId(act[j]);
                            pstmt1.setInt(2, offenceId);
                        rowsAffected = pstmt1.executeUpdate();
                        }
                        
   }
  //}
            }
    }
           }
       } catch (Exception e)
        {
         System.out.println(e);
        }
        return 1;
    }
    public int getOffenceId(String act)
    {
        int offence_type_id = 0;
        try {
            String query = "select max(offence_type_id) as id from offence_type where act='"+act+"'";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next())
            {
                offence_type_id = rset.getInt("id");
            }
        } catch (Exception e) {
        }
        return offence_type_id;
    }
    public int getCity_location_id(String offence_city, String zone_name, String location) {
        int city_location_id = 0;
        try {

            String query = "select city_location_id from city_location as cl,city as c,zone_new as z "
                    + "where c.city_id=z.city_id and z.zone_new_id=cl.zone_new_id "
                    + "and  c.city_name='"+offence_city+"' and z.zone='"+zone_name+"' and cl.location='"+location+"' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
           // pstmt.setString(1, ku.convert_to_unicode(offence_city));
            //pstmt.setString(2, ku.convert_to_unicode(zone_name));
            //pstmt.setString(3, ku.convert_to_unicode(location));
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                city_location_id = rset.getInt("city_location_id");

            }
        } catch (Exception e) {
        }
        return city_location_id;
    }
    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }
 public String getOfficerBookNo(String officer_name)
 {
        String officer_book_no = "";
        officer_name=ku.convert_to_unicode(officer_name);
        try {
            String query = "SELECT ob.book_no from officer_book AS ob,key_person as kp "
                    + "Where kp.key_person_name= '"+officer_name+"'"
                    +  "AND ob.key_person_id=kp.key_person_id AND ob.book_type='C'";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
          //  pstmt.setString(1, officer_name);
          //  pstmt.setString(2, reciept_book_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                officer_book_no = rset.getString("book_no");
            }
        } catch (Exception e) {
        }
        return officer_book_no;
    }
 public int getOfficerBookRevisionNoList(String officer_name)
 {
        officer_name=ku.convert_to_unicode(officer_name);
        int revision_no_list = 0;
        try {
            String query = "SELECT max(book_revision_no) as book_revision_no  from officer_book AS ob, key_person AS kp "
                    + "Where book_type='C' AND ob.key_person_id = kp.key_person_id AND key_person_name=?";
            //+"where active='y'"
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, officer_name);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                revision_no_list = rset.getInt("book_revision_no");
            }
        } catch (Exception e) {
            System.out.println("Exception is" + e);
        }
        return revision_no_list;
    }
 public int getCityId(String offence_city)
 {
        int city_id = 0;
        try {
            offence_city=ku.convert_to_unicode(offence_city);
            String query = "SELECT city_id from city where city_name='" + offence_city + "' ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                city_id = rset.getInt("city_id");
            }
        } catch (Exception e) {
        }
        return city_id;
    }
 public int getTraffic_polise_id() {

        int traffic_police_id = 0;
        try {
            String query = "SELECT MAX(traffic_police_id) as tip from traffic_police ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                traffic_police_id = rset.getInt("tip");
            }
        } catch (Exception e) {
        }
        return traffic_police_id;
    }
    public String getConnectionString() {
        return connectionString;
    }
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    public String getDb_userName() {
        return db_userName;
    }
    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }
    public String getDb_userPasswrod() {
        return db_password;
    }
    public void setDb_userPasswrod(String db_password) {
        this.db_password = db_password;
    }
    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
    public List<String> select(String act_origin, String act, String offence_type, String offence_code,String transport_type)
    {
        offence_type=ku.convert_to_unicode(offence_type);
        List list = new ArrayList();
        String query="";
        if(act!=null){
            offence_type=null;
            act_origin=null;
query="select  ot.act, ot.offence_type,ot.offence_code,ot.penalty_amount,ao.act_origin from offence_type as ot, act_origin as ao, commercial_type as ct where ot.act='"+act+"' AND ao.act_origin_id=ot.act_origin_id AND ot.commercial_type_id=ct.commercial_type_id AND ct.commercial_type='"+transport_type+"'";
        }
 if (offence_type != null) {
 query="select  ot.act, ot.offence_type,ot.offence_code,ot.penalty_amount,ao.act_origin from offence_type as ot, act_origin as ao, commercial_type as ct  where ot.offence_type='"+offence_type+"' AND ao.act_origin_id=ot.act_origin_id AND ot.commercial_type_id=ct.commercial_type_id AND ct.commercial_type='"+transport_type+"'";
        }
 if (offence_code != null) {
   query="select  ot.act, ot.offence_type,ot.offence_code,ot.penalty_amount,ao.act_origin from offence_type as ot, act_origin as ao, commercial_type as ct  where ot.offence_code='"+offence_code+"' AND ao.act_origin_id=ot.act_origin_id AND ot.commercial_type_id=ct.commercial_type_id AND ct.commercial_type='"+transport_type+"'";
        }
 if(act_origin!=null)
 {
     query="select ot.act, ot.offence_type,ot.offence_code,ot.penalty_amount,ao.act_origin from offence_type as ot, act_origin as ao, commercial_type as ct  where ao.act_origin='"+act_origin+"' AND ao.act_origin_id=ot.act_origin_id AND ot.commercial_type_id=ct.commercial_type_id AND ct.commercial_type='"+transport_type+"'";
 }
 else
        { }
//+" IF('"+ offence_type +"' = '', ot.offence_type LIKE '%%',ot.offence_type ='"+ offence_type +"')"
//+"AND IF('"+offence_code+"' = '', ot.offence_code LIKE '%%',ot.offence_code ='"+offence_code+"')"
//+"AND IF('"+act+"' = '', ot.act LIKE '%%',ot.act ='"+act+"')";
//+"AND IF('"+act_origin+"' = '', ao.act_origin LIKE '%%',ao.act_origin ='"+act_origin+"')";
try
{
PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
 ResultSet rs = pstmt.executeQuery();
  int count = 0;

            while(rs.next()) {
                String add= rs.getString("act")+"_"+uk.Convert_to_Kritidev_010(rs.getString("offence_type"))+"_"+rs.getString("offence_code")+"_"+rs.getString("penalty_amount")+"_"+rs.getString("act_origin")+"_";
           list.add(add);
                }
 }
catch(Exception e)
{
    System.out.print(e);
}
    return list;
    }
    public List getReceiptBookNo(String q,String officerName)
    {
        List<String> list = new ArrayList<String>();
        officerName=ku.convert_to_unicode(officerName);
        String query = "select ob.book_no from officer_book as ob , key_person as kp where kp.key_person_id=ob.key_person_id AND kp.key_person_name='"+officerName+"' AND ob.book_type='R'";
        try
        {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String book_no=uk.Convert_to_Kritidev_010(rs.getString("book_no"));
               
                     list.add(book_no);
                    count++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }

    public List getReceiptPageNo(String q,String receipt_book_no)
    {
        List<String> list = new ArrayList<String>();
        
        String query = "select max(rb.page_no) as page from receipt_book as rb ,officer_book as ob where "
                   +"ob.book_revision_no=rb.book_revision AND ob.book_no=rb.receipt_book_no "
                   +"AND rb.book_revision = (select max(book_revision_no) as book_revision_no from officer_book where book_no='"+receipt_book_no+"')"
                   +"AND ob.book_no='"+receipt_book_no+"'";
        try
        {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String page_no=rs.getString("page");

                     list.add(page_no);
                    count++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }

    public List getOffenderCity(String q)
    {
        List<String> list = new ArrayList<String>();

        String query = "select city_name from city";
        try
        {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
             int count = 0;
            q = q.trim();
            while (rs.next())
            {
               String city_name=uk.Convert_to_Kritidev_010(rs.getString("city_name"));

                     list.add(city_name);
                    count++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("error in TrafficPoliceModel processingTypeList(" + e);
        }
        return list;
    }
}
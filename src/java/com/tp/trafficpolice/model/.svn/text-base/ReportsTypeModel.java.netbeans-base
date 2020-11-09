/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.trafficpolice.model;

import com.tp.tableClasses.ReportsType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Administrator
 */
public class ReportsTypeModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public Connection getConnection() {
        return connection;
    }

    public void setConnection() {
          try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= (Connection) DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8",db_userName,db_userPasswrod);
            System.out.println("connected - "+connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
     public int insertRecord(ReportsType reportsType) {
        int rowsAffected = 0;
        String query = "INSERT INTO reports_type (reports_for_id,no_of_columns,report_column_name) VALUES (?,?,?) ";

        String query1="INSERT INTO reports_type(reports_for_id,no_of_columns,report_column_name) "
                + "SELECT reports_for_id,no_of_columns ,? "
                + " FROM reports_type WHERE report_revision= ? ";
        try {
               String[] reports_column_name =reportsType.getReport_column_nameM();
               String[] header_name=reportsType.getHeaderM();
             


                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1,getReports_for_id(reportsType.getReports_for()));
                pstmt.setInt(2,reportsType.getNo_of_columns());
                pstmt.setString(3,reportsType.getHeader());
               rowsAffected = pstmt.executeUpdate();
               if(rowsAffected>0){
                for(int i = 0; i <reports_column_name.length; i++){
               pstmt=connection.prepareStatement(query1);
               pstmt.setString(1,reportsType.getReport_column_nameM()[i]);
               pstmt.setInt(2,getReport_revision());
               rowsAffected=pstmt.executeUpdate();
                   }
               }
               
        } catch (Exception e) {
            System.out.println("Error: payment inserting: " + e);
        }
        if (rowsAffected > 0) {
             message = "Record saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
             message = "Record Not saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("not inserted");
        }
        return rowsAffected;
    }

      public int getReports_for_id(String reports_for){
                int reports_for_id=0;
                try {

               String query = "SELECT reports_for_id from reports_for "
                   + "Where reports_for='"+reports_for+"' ";

                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rset = pstmt.executeQuery();

                while (rset.next()) {
                   reports_for_id =rset.getInt("reports_for_id");

                }
                    } catch (Exception e) {
                     }
                return reports_for_id;
            }
       public int getReport_revision(){
                int report_revision=0;
                try {

               String query = "select max(report_revision) from reports_type ";

                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rset = pstmt.executeQuery();

                while (rset.next()) {
                   report_revision =rset.getInt("repor_revision");

                }
                    } catch (Exception e) {
                     }
                return report_revision;
            }
       public List<String> getReportsFor(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT reports_for_id, reports_for FROM reports_for GROUP BY reports_for ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String reports_for = rset.getString("reports_for");
                if (reports_for.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(reports_for);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Report Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("getStatustype ERROR inside StausTypeModel - " + e);
        }
        return list;
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
        return db_userPasswrod;
    }

    public void setDb_userPasswrod(String db_userPasswrod) {
        this.db_userPasswrod = db_userPasswrod;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }
     public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("statusTypemodel closeConnection() Error: " + e);
        }
    }

}

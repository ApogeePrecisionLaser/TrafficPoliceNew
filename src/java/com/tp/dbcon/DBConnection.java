/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.dbcon;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
//import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author jpss
 */
public class DBConnection {

    private static BasicDataSource dataSource = null;

//    public static BasicDataSource init(ServletContext ctx,HttpSession session) {
//        dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(ctx.getInitParameter("driverClass"));
//        dataSource.setUsername((String) session.getAttribute("db_userName"));
//        dataSource.setPassword((String) session.getAttribute("db_userPasswrod"));
//        dataSource.setUrl(ctx.getInitParameter("connectionString"));
//        return dataSource;
//    }
//    public static synchronized Connection getConnection(ServletContext ctx, HttpSession session) throws SQLException {
    public static synchronized Connection getConnection(ServletContext ctx) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            //  conn = DriverManager.getConnection(ctx.getInitParameter("connectionString"), (String) session.getAttribute("user_name"), (String) session.getAttribute("user_password"));
            conn = DriverManager.getConnection(ctx.getInitParameter("connectionString") + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", ctx.getInitParameter("db_userName"), ctx.getInitParameter("db_userPasswrod"));
        } catch (Exception e) {
            System.out.println("DBConncetion getConnection() Error: " + e);
        }
        return conn;
    }

    public static synchronized Connection getConnectionForUtf(ServletContext ctx) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = (Connection) DriverManager.getConnection((String) ctx.getInitParameter("connectionString") + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", (String) ctx.getInitParameter("db_userName"), (String) ctx.getInitParameter("db_userPasswrod"));
        } catch (Exception e) {
            System.out.println(" getConnectionForUtf() Error: " + e);
        }
        return conn;
    }

    public static synchronized Connection getConnectionForUtf(ServletContext ctx, HttpSession session) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = (Connection) DriverManager.getConnection(ctx.getInitParameter("connectionString") + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", (String) session.getAttribute("user_name"), (String) session.getAttribute("user_password"));
        } catch (Exception e) {
            System.out.println(" setConnection() Error: " + e);
        }
        return conn;
    }

    public static void closeConncetion(Connection con) {

        try {
            con.close();
        } catch (Exception e) {
            System.out.println("DBConncetion closeConnection() Error: " + e);
        }

    }

    /*controller part
    try {
    model.setConnection(DBConnection.getConnection(ctx, session));
    } catch (Exception e) {
    System.out.println("error in StockDocumentTypeController setConnection() calling try block"+e);
    }
     */
    /*model part
    public void setConnection(Connection con) {
    try {

    connection = con;
    } catch (Exception e) {
    System.out.println("StockDocumentTypeModel setConnection() Error: " + e);
    }
    }
     */
}

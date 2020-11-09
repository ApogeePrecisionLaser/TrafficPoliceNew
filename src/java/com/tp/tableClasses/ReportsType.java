/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tp.tableClasses;

/**
 *
 * @author Administrator
 */
public class ReportsType {
    private String header;
    private int no_of_columns;
    private String reports_for;
    private String column_name;
      private String[] headerM,reports_idM,no_of_columnsM,reports_forM,report_column_nameM;

    public String[] getHeaderM() {
        return headerM;
    }

    public void setHeaderM(String[] headerM) {
        this.headerM = headerM;
    }

    public String[] getReport_column_nameM() {
        return report_column_nameM;
    }

    public void setReport_column_nameM(String[] report_column_nameM) {
        this.report_column_nameM = report_column_nameM;
    }


    public String[] getReports_idM() {
        return reports_idM;
    }

    public void setReports_idM(String[] reports_idM) {
        this.reports_idM = reports_idM;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    

    public String[] getNo_of_columnsM() {
        return no_of_columnsM;
    }

    public void setNo_of_columnsM(String[] no_of_columnsM) {
        this.no_of_columnsM = no_of_columnsM;
    }

    public String[] getReports_forM() {
        return reports_forM;
    }

    public void setReports_forM(String[] reports_forM) {
        this.reports_forM = reports_forM;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getNo_of_columns() {
        return no_of_columns;
    }

    public void setNo_of_columns(int no_of_columns) {
        this.no_of_columns = no_of_columns;
    }

    public String getReports_for() {
        return reports_for;
    }

    public void setReports_for(String reports_for) {
        this.reports_for = reports_for;
    }

}

<%--
    Document   : officerBook
    Created on : May 30, 2014, 12:13:45 PM
    Author     : JPSS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<!--<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>-->
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script language="javascript" type="text/javascript" src="JS/calendar.js"></script>
<link rel="stylesheet" type="text/css" href="css/calendar.css" >
<!--<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.css"></script>-->
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<!--<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>-->
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="JS/css-pop.js"></script>
<script type="text/javascript" src="JS/css-pop.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<style type="text/css">
                .new_input
                {
                    font-size: 16px;
                    font-family:"kruti Dev 010", Sans-Serif;
                    font-weight: 500;
                    background-color: white;
                }
            </style>
<script type="text/javascript" language="javascript">
    
    $(document).ready(function(){
        $("#officer_name").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'kruti Dev 010'; font-size: 15px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
        
        
    });
    
    
    
    jQuery(function(){
        
                 
        
        
        
        
        
     
        $("#searchOfficeCode").autocomplete("officerBookCont", {
            extraParams: {
                action1: function() { return "getOrgOfficeCode"}

            }
        });
        $("#date").datepicker({

            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $("#searchFromDate").datepicker({

            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $("#searchToDate").datepicker({

            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $("#searchBookNo").autocomplete("officerBookCont", {
            extraParams: {
                action1: function() { return "getBookNoList"}

            }
        });
        $("#employeeId").autocomplete("officerBookCont", {
            extraParams: {
                action1: function() { return "getEmployeeCode"}
              
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////
//        $("#book_type").autocomplete("officerBookCont", {
//            extraParams: {
//                action1: function() { return "getBookType"}
//
//            }
//        });
        /////////////////////////////////////////////////////////////////////////////////


        $("#employeeId").result(function(event, data, formatted){
            fillOfficerName();
        

        });

        $("#officer_name").autocomplete("officerBookCont", {
            extraParams: {
                action1: function() { return "getOfficerNameList"},
                action2:function(){return document.getElementById("employeeId").value; }
            }
        });
        $("#officer_name").result(function(event, data, formatted){
            $.ajax({
                url:"officerBookCont",
                data:"action1=getOfficerData&officer_name="+data,
                methods:"get",
                success:function(result){
                    var res = result.trim().split("_");
                    $("#employeeId").val(res[0]);
                    $("#designation").val(res[1]);
                }
            });
        });
        //        $("#designation").autocomplete("officerBookCont", {
        //            extraParams: {
        //                action1: function() { return "getDesignation"},
        //                action2:function(){return document.getElementById("officer_name").value; }
        //            }
        //        });
       
        $("#searchOfficeName").autocomplete("officerBookCont", {
            extraParams: {
                action1: function() { return "getOrgOfficeName"}
                //action2:function(){return document.getElementById("searchOfficeCode").value; }
            }
        });
        $("#searchOfficerName").autocomplete("officerBookCont", {
            extraParams: {
                action1: function() { return "getSearchOfficerName"}
                //  action2:function(){return document.getElementById("searchOfficeName").value; }
            }
        });
        $("#employeeId").click(function(){
            $("#officer_name").val('');
            $("#officer_name").flushCache();
            $("#designation").val('');
            $("#designation").flushCache();
        });
        
          $("#showAllRecords").click(function(){
            $("#searchOfficeCode").val('');
            $("#searchOfficeCode").flushCache();
            $("#searchOfficeName").val('');
            $("#searchOfficeName").flushCache();
            $("#searchOfficerName").val('');
            $("#searchOfficerName").flushCache();
        });

    });

    function makeEditable(id) {
        document.getElementById("employeeId").disabled = false;
        document.getElementById("officer_name").disabled = false;
        document.getElementById("designation").readOnly = true;
        //document.getElementById("officer_name").disabled = false;
        document.getElementById("book_no").disabled = false;
        document.getElementById("from_no").disabled = false;
        document.getElementById("to_no").disabled = false;
        document.getElementById("date").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("status_type").disabled = false;
        document.getElementById("book_type").disabled = false;
        document.getElementById("edit").disabled = false;
        
        if(id == 'new') {
            document.getElementById("message").innerHTML = "";      // Remove message
            document.getElementById("employeeId").disabled = false;
            //document.getElementById("officer_name").readOnly = true;
            document.getElementById("designation").readOnly = true;
            document.getElementById("save").disabled = false;
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("revise").disabled = true;
            document.getElementById("from_no").readOnly = false;
            document.getElementById("to_no").readOnly=false;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 18);
            document.getElementById("employeeId").focus();
        }
        if(id == 'edit'){           
            document.getElementById("delete").disabled =false;
            document.getElementById("revise").disabled = false;
            document.getElementById("new").disabled = true;
            document.getElementById("save").disabled = true;
            //document.getElementById("from_no").readOnly = true;
            //document.getElementById("to_no").readOnly=true;
            //  document.getElementById("save").disabled = true;
            // document.getElementById("book_no").disabled = true;
        }
    }

    function setStatus(id) {

        if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        }
        else if(id == 'revise'){

            document.getElementById("clickedButton").value = "Revise";
                 
        }
        else document.getElementById("clickedButton").value = "Delete";
    }

    function verify() {
        var result;
        var clickedButton = document.getElementById("clickedButton").value;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickButton").value=='Revise') {

            var officer_name = document.getElementById("officer_name").value;
            var designation=document.getElementById("designation").value;
            var book_no = document.getElementById("book_no").value;
            var emp_code=document.getElementById("employeeId").value;
            var from_no = document.getElementById("from_no").value;
            var to_no = document.getElementById("to_no").value;
            var date = document.getElementById("date").value;
            var status_type = document.getElementById("status_type").value;
            var book_type = document.getElementById("book_type").value;
            if(myLeftTrim(officer_name).length == 0) {
                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>officer Name is required...</b></td>");
                document.getElementById("officer_name").focus();
                return false; // code to stop from submitting the form2.
            }
//            if(myLeftTrim(emp_code).length == 0) {
//                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
//                $("#message").html("<td colspan='5' bgcolor='coral'><b>Employee Id is required...</b></td>");
//                document.getElementById("employeeId").focus();
//                return false; // code to stop from submitting the form2.
//            }
            if(myLeftTrim(book_no).length == 0) {
                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Book no  is required...</b></td>");
                document.getElementById("book_no").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(status_type).length == " ") {
                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Status Type  is required...</b></td>");
                document.getElementById("status_type").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(from_no).length == 0) {
                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>From No is required...</b></td>");
                document.getElementById("from_no").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(to_no).length == 0) {
                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>To No is required...</b></td>");
                document.getElementById("to_no").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(date).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Date is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Date is required...</b></td>");
                document.getElementById("date").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(book_type).length == " ") {
                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Date is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Book Type is required...</b></td>");
                document.getElementById("book_type").focus();
                return false; // code to stop from submitting the form2.
            }
//            if(myLeftTrim(designation).length == 0) {
//                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Date is required...</b></td>";
//                $("#message").html("<td colspan='5' bgcolor='coral'><b>Designation is required...</b></td>");
//                document.getElementById("designation").focus();
//                return false; // code to stop from submitting the form2.
//            }
            if(result == false)    // if result has value false do nothing, so result will remain contain value false.
            {
            }
            else{ result = true;
            }
            
            if(document.getElementById("clickedButton").value == 'Save AS New'){
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
            if(document.getElementById("clickedButton").value == 'Revise'){
                result = confirm("Are you sure you want to revise this record?")
                return result;
            }
        } else result = confirm("Are you sure you want to delete this record?")
        return result;

        if(document.getElementById("book_no").value >= 0){
            addRow($("#officer_name").val(),$("#status_type").val(),$("#book_no").val() ,$("#from_no").val(),
            $("#to_no").val() , $("#date").val(),$("#remark").val(),$("#book_revision_no").val());
            return false;
        }
        
    }

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function fillStatusType(id){

        //alert("Hi");
        var status_type_id= id;
        alert("inside"+status_type_id);
        $.ajax({url: "officerBookCont" , async: false ,data: "task=getStatusType&status_type_id="+status_type_id, success: function(response_data) {
                //   $("#item_no").html("<option selected>select..</option>");
                alert(response_data);
                var arr = response_data.split("&#;");
                //$("#item_no").html("");
                var i;
                // if(arr.length==1){}
                // else {
                for(i = 0; i < arr.length ; i++) {
                    var opt  = document.createElement("option");
                    opt.text = $.trim(arr[i]);
                    opt.value = $.trim(arr[i]);
                    document.getElementById("status_type").options.add(opt);
                    document.getElementById("status_type").selectedIndex = 0;
                }
                            


            }
        });


    }

    function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 18;

        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit,rowNo=0;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
        //alert(t1id);
        //document.getElementById("status_type_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("employeeId").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
        document.getElementById("officer_name").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
        document.getElementById("designation").value = document.getElementById(t1id +(lowerLimit+7)).innerHTML;
        var book_type = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        book_type = book_type == "Challan"?"C":"R";
        document.getElementById("book_type").value = book_type;
        document.getElementById("book_no").value = document.getElementById(t1id +(lowerLimit+9)).innerHTML;
        document.getElementById("date").value = document.getElementById(t1id +(lowerLimit+10)).innerHTML
        document.getElementById("from_no").value = document.getElementById(t1id +(lowerLimit+11)).innerHTML;
        document.getElementById("to_no").value = document.getElementById(t1id +(lowerLimit+12)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+13)).innerHTML;
        // document.getElementById("status_type").value = status_type_id;
        //document.getElementById("active").value = document.getElementById(t1id +(lowerLimit+9)).innerHTML;
        document.getElementById("status_type_id").value = status_type_id;
        document.getElementById("status_type").value=  document.getElementById(t1id +(lowerLimit+14)).innerHTML;
        document.getElementById("book_revision_no").value = document.getElementById(t1id +(lowerLimit+15)).innerHTML;
        // alert(document.getElementById("status_type").value);
        //alerrt(status_type_id);
        //document.getElementById("book_revision_no").value=document.getElementById(t1id +(lowerLimit+7)).innerHTML;
        //document.getElementById("active").value=document.getElementById(t1id +(lowerLimit+9)).innerHTML;
        var active =document.getElementById(t1id +(lowerLimit+16)).innerHTML;//debugger;

        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
        {
            //document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        }
        //  document.getElementById("message").innerHTML = "";      // Remove message
        $("#message").html("");
        if(active == "N")
            document.getElementById("edit").disabled = true;
    }

    function addRow(officer_name,status_type,book_no,from_no,to_no,date,remark) {

        var table = document.getElementById('insertTable');
        var rowCount = table.rows.length;            // alert(rowCount);
        var row = table.insertRow(rowCount);
        var cell1 = row.insertCell(0);
        cell1.innerHTML = rowCount;
        var element1 = document.createElement("input");
        element1.type = "hidden";
        element1.name = "book_no";
        element1.id = "book_no"+rowCount;
        element1.size = 1;
        element1.value = 1;
        element1.readOnly = false;
        cell1.appendChild(element1);
        var element1 = document.createElement("input");
        element1.type = "checkbox";
        element1.name = "check_payment";
        element1.id = "check_payment"+rowCount;                //element1.size = 1;
        element1.value = "YesModify";
        element1.checked = true;
        element1.setAttribute('onclick', 'singleCheckUncheck('+rowCount+')');
        cell1.appendChild(element1);

        var cell2 = row.insertCell(1);
        var element2 = document.createElement("input");
        element2.type = "text";
        element2.name = "officer_name";
        element2.id = "officer_name"+rowCount;
        element2.size = 30;
        element2.value = officer_name;
        cell2.appendChild(element2);

        var cell3 = row.insertCell(2);
        var element2 = document.createElement("input");
        element2.type = "text";
        element2.name = "status_type";
        element2.id = "status_type"+rowCount;
        element2.size = 30;
        element2.value = status_type;
        cell3.appendChild(element2);

        var cell4 = row.insertCell(3);
        var element4 = document.createElement("input");
        element4.type = "text";
        element4.name = "book_no";
        element4.id = "book_no"+rowCount;
        element4.size = 30;
        element4.value = book_no;
        cell4.appendChild(element4);

        var cell5 = row.insertCell(4);
        var element5 = document.createElement("input");
        element5.type = "text";
        element5.name = "from_no";
        element5.id = "from_no"+rowCount;
        element5.size = 30;
        element5.value = from_no;
        cell5.appendChild(element5);

        var cell6 = row.insertCell(5);
        var element6 = document.createElement("input");
        element6.type = "text";
        element6.name = "to_no";
        element6.id = "to_no"+rowCount;
        element6.size = 30;
        element6.value = to_no;
        cell6.appendChild(element6);

        var cell7 = row.insertCell(6);
        var element7 = document.createElement("input");
        element7.type = "text";
        element7.name = "date";
        element7.id = "date"+rowCount;
        element7.size = 30;
        element7.value = date;
        cell7.appendChild(element7);

        var cell8 = row.insertCell(7);
        var element8= document.createElement("input");
        element8.type = "text";
        element8.name = "remark";
        element8.id = "remark"+rowCount;
        element8.size = 30;
        element8.value = remark;
        cell8.appendChild(element8);
        var height = (rowCount * 40)+ 60;

        document.getElementById("autoCreateTableDiv").style.visibility = "visible";
        document.getElementById("autoCreateTableDiv").style.height = height+'px';
        if(rowCount == 1){
            $('#checkUncheckAll').attr('hidden', true);
        }else{
            $('#checkUncheckAll').attr('hidden', false);
        }
    }
    function deleteRowWithoutResetForm() {
        try {
            var table = document.getElementById('insertTable');
            var rowCount = table.rows.length;

            for(var i=0; i<rowCount-1; i++) {
                table.deleteRow(1);
            }
            document.getElementById("autoCreateTableDiv").style.visibility = "hidden";
            document.getElementById("autoCreateTableDiv").style.height = '0px';
        }catch(e) {

            alert(e);
        }
    }

    function myLeftTrim(str) {
        var beginIndex = 0;
        for(var i = 0; i < str.length; i++) {
            if(str.charAt(i) == ' ')
                beginIndex++;
            else break;
        }
        return str.substring(beginIndex, str.length);
    }
    var popupwin = null;
    function getRecordlist(id){
        var queryString;
        if(id == "viewPdf")
            queryString = "task=PRINTRecordList";
        else
            queryString = "task=PrintExcelList";
        var url = "officerBookCont?" + queryString;
        popupwin = openPopUp(url, "Division List", 600, 900);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }           
         
    function organisationIsEmpty(){

        var organisation_name = document.getElementById("organisation_name").value;

        if(myLeftTrim(organisation_name).length == 0) {
            // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
            $("#message").html( "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>");
            document.getElementById("organisation_name").focus();

        }else{
            $("#message").html("");
        }
    }
    function  codeIsEmpty()
    {
        var office_code = document.getElementById("office_code").value;

        if(myLeftTrim(office_code).length == 0) {
            // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
            $("#message").html( "<td colspan='8' bgcolor='coral'><b>Office Code is required...</b></td>");
            document.getElementById("office_code").focus();

        }else{
            $("#message").html("");
        }
    }
    function  officeNameIsEmpty()
    {
        var office_name = document.getElementById("office_name").value;

        if(myLeftTrim(office_name).length == 0) {
            // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
            $("#message").html( "<td colspan='8' bgcolor='coral'><b>Office Name is required...</b></td>");
            document.getElementById("office_name").focus();

        }else{
            $("#message").html("");
        }
    }

    function officerNameIsEmpty()
    {
        var officer_name = document.getElementById("officer_name").value;

        if(myLeftTrim(officer_name).length == 0) {
            // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
            $("#message").html( "<td colspan='8' bgcolor='coral'><b>Officer name is required...</b></td>");
            document.getElementById("officer_name").focus();

        }else{
            $("#message").html("");
        }
    }
    function fillOfficerName() {
       
        var emp_code = document.getElementById("employeeId").value;
        $.ajax({url: "officerBookCont", async: false ,data: "action1=getOfficerName&emp_code="
                +emp_code , success: function(response_data_amount) {
                //alert(response_data_amount);
                 $("#officer_name").html("");
                var officer_name= response_data_amount;
                document.getElementById("officer_name").value =officer_name;
                    fillDesignation();
            }

        });
    }
    function fillDesignation() {
        var emp_code = document.getElementById("employeeId").value;
        $.ajax({url: "officerBookCont", async: false ,data: "action1=getDesignation&emp_code="
                +emp_code , success: function(response_data_amount) {
                $("#designation").html("");
                var designation= response_data_amount;
                document.getElementById("designation").value =designation;
            }
        });
    }
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Officer Book </title>
        
        <style>
        .required_field_class{
          color:red;
          padding-left: 2px;
          font-size: 15px;
           } 
           
           .ac_results li {
               font-family: 'kruti Dev 010';
               font-size: 12px;
               }
        </style>
        
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <%-- <tr>
                    <td width="50" height="600" valign="top"><%@include file="/view/layout/Leftmenu.jsp" %></td></tr> --%>
            <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="90%"><b>OFFICER AND BOOK</b></td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                        <%-- <td>
                                             <%@include file="/view/layout/org_menu.jsp" %>
                                         </td> --%>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="officerBookCont" >
                                        <table align="center" class="heading1" width="90%">
                                            <tr>
                                                <th>Book Type</th>
                                                <td>
                                                    <select class="input" id="searchBookType" name="searchBookType">
                                                        <option value="All" ${searchBookType == "All"?'selected':''}>All</option>
                                                        <option value="C" ${searchBookType == "C"?'selected':''}>Challan</option>
                                                        <option value="R" ${searchBookType == "R"?'selected':''}>Receipt</option>
                                                    </select>
                                                </td>
                                                <th class="heading1" >Status Type </th>
                                                <td>
                                                    <select class="dropdown" id="searchStatusType" name="searchStatusType">

                                                        <option value="" selected>All</option>
                                                        <c:forEach var="statusTypeList" items="${statusTypeList}">
                                                            <option value="${statusTypeList.value}" ${searchStatusType == statusTypeList.value?'selected':''}> ${statusTypeList.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <input class="input" type="hidden" id="status_type_id" name="status_type_id" value="" disabled>
                                                </td>
                                                <th>Active</th>
                                                <td>
                                                    <select class="input" id="searchActive" name="searchActive">
                                                        <option value="" ${searchActive == ""?'selected':''}>All</option>
                                                        <option value="Y" ${searchActive == "Y"?'selected':''}>Yes</option>
                                                        <option value="N" ${searchActive == "N"?'selected':''}>No</option>
                                                    </select>
                                                </td>                                            
                                                <th>Book No</th>
                                                <td><input class="input" type="text" id="searchBookNo" name="searchBookNo" value="${searchBookNo}" size="10" ></td>
                                                <th>Office Code</th>
                                                <td><input class="input" type="text" id="searchOfficeCode" name="searchOfficeCode" value="${searchOfficeCode}" size="10" ></td>
                                                <th>Office Name</th>
                                                <td><input class="input new_input" type="text" id="searchOfficeName" name="searchOfficeName" value="${searchOfficeName}" size="20" ></td>

                                            </tr>
                                            <%-- <tr>

                                                <th>From Date</th>
                                                <td > <input class="input" type="text" id="searchFromDate" name="searchFromDate" value="${searchFromDate}" size="30" ></td>
                                                <th>To Date</th>
                                                <td><input class="input" type="text" id="searchToDate" name="searchToDate" value="${searchToDate}" size="30" ></td>

</tr>--%>
                                            <tr>
                                                

                                                <td colspan="10" align="center">
                                                    <b>Officer Name</b>
                                                    <input class="input new_input" type="text" id="searchOfficerName" name="searchOfficerName" value="${searchOfficerName}" size="20" >
                                                    <input class="button" type="submit" name="task" id="searchIn" value="Search" >
                                                    <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                    <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick=" getRecordlist(id)">
                                                    <input class="button" type="button" id="viewExcel" name="viewExcel" value="Excel" onclick=" getRecordlist(id)">

                                                </td>

                                                <%-- <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>--%>

                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="officerBookCont">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class= "heading">S.No.</th>
                                                <th class="heading">Organization Name</th>
                                                <th class="heading">Office Type</th>
                                                <th class="heading">Office Code</th>
                                                <th class="heading">Office Name</th>
                                                <th class="heading">Employee Id</th>
                                                <th class="heading">Officer Name</th>
                                                <th class="heading">Designation</th>
                                                <th class="heading">Book Type</th>
                                                <th class="heading">Book No</th>
                                                <th class="heading">Date</th>
                                                <th class="heading">From No</th>
                                                <th class="heading">To No</th>
                                                <th class="heading">Remark</th>
                                                <th class="heading">Status Type</th>
                                                <th class="heading">Book Revision No</th>
                                                <th class="heading">Active</th>
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from taxTypeList of TaxController     --->
                                            <c:forEach var="retrieve" items="${requestScope['officerBookList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >

                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.organisation_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.office_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.org_office_code}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.org_office_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.emp_code}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" class="new_input">${retrieve.key_person_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"class="new_input" >${retrieve.designation}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.book_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.book_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${retrieve.issue_date}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.from_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.to_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" class="new_input"  onclick="fillColumns(id)">${retrieve.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)"  >${retrieve.status_type}</td>                                                  
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" >${retrieve.book_revision_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)"  >${retrieve.active}</td>
                                                    <td style="display: none"id="t1c${IDGenerator.uniqueID}"onclick="fillColumns(id)" >${retrieve.status_type_id}</td>
                                                    <%--  <input type="hidden" id="status_type_id${loopCounter.count}" name="status_type_id${loopCounter.count}" value="${retrieve.status_type_id}"  --%>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="16">
                                                    <c:choose>
                                                        <c:when test="${showFirst eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='First'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showPrevious == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Previous' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Previous'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showNext eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Next' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Next'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showLast == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Last' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Last'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" id="searchOffenceType" name="searchOffenceType" value="${searchOffenceType}">
                                            <input  type="hidden" name="searchBookType" value="${searchBookType}">
                                            <input  type="hidden" name="searchStatusType" value="${searchStatusType}">
                                            <input  type="hidden" name="searchOfficeCode" value="${searchOfficeCode}">
                                            <input  type="hidden" name="searchOfficeName" value="${searchOfficeName}">
                                            <input  type="hidden" name="searchOfficerName" value="${searchOfficerName}">
                                            <input  type="hidden" name="searchActive" value="${searchActive}">
                                            <input  type="hidden" name="searchBookNo" value="${searchBookNo}">

                                        </table></DIV>
                                </form>
                            </td>
                        </tr>
                        <tr id="message">
                            <c:if test="${not empty message}">
                                <td  bgcolor="${msgBgColor}"><b></b></td>
                            </c:if>
                        </tr>
                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="officerBookCont" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>

                                            <tr>
                                                <th class="heading1">Employee Id<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="text" id="employeeId" name="employeeId" value="" size="40"   disabled>
                                                </td>
                                                <th class="heading1">Officer Name<span class="required_field_class">*</span></th>

                                                <td>
                                                    <input class="input" type="hidden" id="key_person_id" name="key_person_id" value="" size="40" >
                                                    <input class="input new_input" type="text" id="officer_name" name="officer_name" value="" size="40" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Designation<span class="required_field_class">*</span></th>
                                                <td><input class="input new_input" type="text" id="designation" name="designation" value="" size="40" readonly></td>

                                                <th class="heading1">Book No<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="text" id="book_no" name="book_no" value="" size="40" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1" >Status Type <span class="required_field_class">*</span></th>
                                                <td>
                                                    <select class="dropdown" id="status_type" name="status_type" value="" disabled>

                                                        <option value=" " style="color: red" selected>Select Status Type.</option>
                                                        <c:forEach var="statusTypeList" items="${statusTypeList}">
                                                            <option value="${statusTypeList.value}"> ${statusTypeList.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <input class="input" type="hidden" id="status_type_id" name="status_type_id" value="" disabled>
                                                </td>
                                                <th class="heading1">Date<span class="required_field_class">*</span></th><td><input class="input" type="text" id="date" name="date" value="${cut_dt}" size="40" disabled></td>

                                            </tr>

                                            <tr>
                                                <th class="heading1">From Page No<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="text" id="from_no" name="from_no" value="" size="40" disabled>
                                                </td>
                                                <th class="heading1">To Page No<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="text" id="to_no" name="to_no" value="" size="40" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Remark</th>
                                                <td>
                                                    <input class="input new_input" type="text" id="remark" name="remark" value="" size="40" disabled>
                                                    <input class="input" type="hidden" id="book_revision_no" name="book_revision_no" value="" size="40" >
                                                </td>
                                                <th class="heading1">Book Type<span class="required_field_class">*</span></th>
                                                <td>
<!--                                                    <input class="input" type="text" id="book_type" name="book_type" value="" size="40" disabled>-->

                                                    <select class="input" type="hidden" id="book_type" name="book_type" disabled>
                                                        <option value=" " selected>All</option>
                                                        <option value="C">Challan</option>
                                                        <option value="R">Receipt</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1"></th><td><input class="input" type="hidden" id="active" name="active" value="" size="40"></td>
                                            </tr>
                                            <tr>
                                                <td align='center' colspan="4">
                                                    <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchOfficeCode" value="${searchOfficeCode}" >
                                            <input type="hidden"  name="searchOfficeName" value="${searchOfficeName}" >
                                            <input type="hidden"  name="searchOfficerName" value="${searchOfficerName}" >
                                        </table>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>


<%--
    document   : receiptView
    Created on : May 30, 2014, 12:13:45 PM
    Author     : JPSS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script language="javascript" type="text/javascript" src="JS/calendar.js"></script>
<link rel="stylesheet" type="text/css" href="css/calendar.css" >
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="JS/css-pop.js"></script>
<script type="text/javascript" src="JS/css-pop.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" language="javascript">
    var doc = document;

    jQuery(function(){    
        
        $("#issue_date").datepicker({

            //minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $("#validity").datepicker({

            //minDate: -100,
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

        $("#searchLicenseNo").autocomplete("licenseDetailCont.do", {
            extraParams: {
                action1: function() { return "getSearchLicenseNo"}
                //  action2:function(){return doc.getElementById("searchOfficeName").value; }
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
        doc.getElementById("license_no").disabled = false;
        doc.getElementById("owner_name").disabled = false;
        doc.getElementById("age").disabled = false;
        //doc.getElementById("officer_name").disabled = false;
        doc.getElementById("address").disabled = false;
        doc.getElementById("issue_date").disabled = false;
        doc.getElementById("validity").disabled = false;
        doc.getElementById("is_permanent").disabled = false;
        doc.getElementById("remark").disabled = false;
        doc.getElementById("vehicle_type").disabled = false;
        doc.getElementById("save").disabled = true;
        doc.getElementById("delete").disabled =false;
        doc.getElementById("save_As").disabled =false;
        doc.getElementById("update").disabled =false;

        if(id == 'new') {
            doc.getElementById("message").innerHTML = "";      // Remove message
            doc.getElementById("license_detail_id").value = 0;
            doc.getElementById("save").disabled = false;
            doc.getElementById("delete").disabled =true;
            doc.getElementById("save_As").disabled =true;
            doc.getElementById("update").disabled =true;
            setDefaultColor(doc.getElementById("noOfRowsTraversed").value, 10);
            doc.getElementById("license_no").focus();

        }
        if(id == 'edit'){
           
            doc.getElementById("delete").disabled =false;
            doc.getElementById("revise").disabled = false;
            doc.getElementById("new").disabled = true;
            doc.getElementById("save").disabled = true;
            //doc.getElementById("from_no").readOnly = true;
            //doc.getElementById("to_no").readOnly=true;

            //  doc.getElementById("save").disabled = true;
            // doc.getElementById("book_no").disabled = true;
        }


    }

    function setStatus(id) {

        if(id == 'save'){
            doc.getElementById("clickedButton").value = "Save";
        }
        else if(id == 'update'){
            doc.getElementById("clickedButton").value = "Update";                 
        }else if(id == 'save_As'){
            doc.getElementById("clickedButton").value = "Save As New";  
        }
        else doc.getElementById("clickedButton").value = "Delete";
    }

    function verify() {
        var result;
        if(doc.getElementById("clickedButton").value == 'Save' || doc.getElementById("clickedButton").value == 'Save As New' || doc.getElementById("clickButton").value=='Update') {

            var license_no = doc.getElementById("license_no").value;
            var owner_name=doc.getElementById("owner_name").value;
            var age = doc.getElementById("age").value;
            var address = doc.getElementById("address").value;
            var issue_date=doc.getElementById("issue_date").value;
            var validity = doc.getElementById("validity").value;
            var vehicle_type = doc.getElementById("vehicle_type").value;
            var is_permanent = doc.getElementById("is_permanent").value;
            var remark = doc.getElementById("remark").value;
            //var date = doc.getElementById("date").value;

debugger;

            if(myLeftTrim(license_no).length == 0) {
                // doc.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>License NO. is required...</b></td>");
                doc.getElementById("license_no").focus();
                return false; // code to stop from submitting the form2.
            } if(myLeftTrim(owner_name).length == 0) {
                // doc.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Name is required...</b></td>");
                doc.getElementById("owner_name").focus();
                return false; // code to stop from submitting the form2.
            }

            if(myLeftTrim(age).length == 0) {
                // doc.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Age is required...</b></td>");
                doc.getElementById("age").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(issue_date).length == 0) {
                // doc.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Issue Date is required...</b></td>");
                doc.getElementById("issue_date").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(validity).length == 0) {
                // doc.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Validity is required...</b></td>");
                doc.getElementById("validity").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(vehicle_type).length == 0) {
                doc.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Vehicle Type required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Date is required...</b></td>");
                doc.getElementById("vehicle_type").focus();
                return false; // code to stop from submitting the form2.
            }
            
            if(issue_date >= validity) {
                doc.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Issue Date must be less than Validity...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Issue Date must be less than Validity...</b></td>");
                doc.getElementById("issue_date").focus();
                return false; // code to stop from submitting the form2.
            }
            
            if(result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

            }
            else{ result = true;
            }

            if(doc.getElementById("clickedButton").value == 'Save As New'){
                result = confirm("Are you sure you want to save it as New record?");
                return result;
            }
            if(doc.getElementById("clickedButton").value == 'Update'){
                result = confirm("Are you sure you want to Update this record?");
                return result;
            }
        } else 
            result = confirm("Are you sure you want to delete this record?");
            return result;
        

        if(doc.getElementById("license_detail_id").value >= 0){
            addRow(owner_name,age,address,issue_date,
            validity , is_permanent, remark, vehicle_type);
            return false;
        }
    }

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                doc.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }   

    function fillColumns(id) {debugger;
        var noOfRowsTraversed = doc.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 10;

        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit,rowNo=0;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
            rowNo++;
            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
        //alert(t1id);
        doc.getElementById("license_detail_id").value= doc.getElementById("license_detail_id" + rowNo).value;
        doc.getElementById("license_no").value = doc.getElementById(t1id +(lowerLimit+1)).innerHTML;
        doc.getElementById("owner_name").value = doc.getElementById(t1id +(lowerLimit+2)).innerHTML;
        doc.getElementById("age").value = doc.getElementById(t1id +(lowerLimit+3)).innerHTML;
        doc.getElementById("address").value = doc.getElementById(t1id +(lowerLimit+4)).innerHTML;
        var issue_date = doc.getElementById(t1id +(lowerLimit+5)).innerHTML;
        var validity = doc.getElementById(t1id +(lowerLimit+6)).innerHTML;

        var pattern = /(\d{4})\-(\d{2})\-(\d{2})/;
//        var pattern1 = /(\d{1})\/(\d{1})\/(\d{2})/;
//        var pattern2 = /(\d{1})\/(\d{2})\/(\d{2})/;
//        var date = new Date(offence_date.replace(pattern1,'20$3-0$1-0$2'));
//        var bool_val = offence_date.match(pattern);
//        var bool_val1 = offence_date.match(pattern1);
        if(issue_date.match(pattern))
           issue_date = issue_date.replace(pattern,'$3-$2-$1');
       if(validity.match(pattern))
           validity = validity.replace(pattern,'$3-$2-$1');

        doc.getElementById("issue_date").value = issue_date;
        doc.getElementById("validity").value = validity;
        doc.getElementById("is_permanent").value = doc.getElementById(t1id +(lowerLimit+7)).innerHTML;
        doc.getElementById("vehicle_type").value = doc.getElementById(t1id +(lowerLimit+8)).innerHTML;
        doc.getElementById("remark").value = doc.getElementById(t1id +(lowerLimit+9)).innerHTML;
        
        for(var i = 0; i < noOfColumns; i++) {
            doc.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        //doc.getElementById("edit").disabled = false;
        /*if(!doc.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
        {
            //doc.getElementById("save_As").disabled = true;
            doc.getElementById("delete").disabled = false;
        }*/
        //  doc.getElementById("message").innerHTML = "";      // Remove message
        makeEditable('');
        $("#message").html("");
        //if(active == "N")
            //doc.getElementById("edit").disabled = true;
    }

    function addRow(officer_name,status_type,book_no,from_no,to_no,date,remark) {

        var table = doc.getElementById('insertTable');
        var rowCount = table.rows.length;            // alert(rowCount);
        var row = table.insertRow(rowCount);
        var cell1 = row.insertCell(0);
        cell1.innerHTML = rowCount;
        var element1 = doc.createElement("input");
        element1.type = "hidden";
        element1.name = "book_no";
        element1.id = "book_no"+rowCount;
        element1.size = 1;
        element1.value = 1;
        element1.readOnly = false;
        cell1.appendChild(element1);
        var element1 = doc.createElement("input");
        element1.type = "checkbox";
        element1.name = "check_payment";
        element1.id = "check_payment"+rowCount;                //element1.size = 1;
        element1.value = "YesModify";
        element1.checked = true;
        element1.setAttribute('onclick', 'singleCheckUncheck('+rowCount+')');
        cell1.appendChild(element1);

        var cell2 = row.insertCell(1);
        var element2 = doc.createElement("input");
        element2.type = "text";
        element2.name = "officer_name";
        element2.id = "officer_name"+rowCount;
        element2.size = 30;
        element2.value = officer_name;
        cell2.appendChild(element2);

        var cell3 = row.insertCell(2);
        var element2 = doc.createElement("input");
        element2.type = "text";
        element2.name = "status_type";
        element2.id = "status_type"+rowCount;
        element2.size = 30;
        element2.value = status_type;
        cell3.appendChild(element2);

        var cell4 = row.insertCell(3);
        var element4 = doc.createElement("input");
        element4.type = "text";
        element4.name = "book_no";
        element4.id = "book_no"+rowCount;
        element4.size = 30;
        element4.value = book_no;
        cell4.appendChild(element4);

        var cell5 = row.insertCell(4);
        var element5 = doc.createElement("input");
        element5.type = "text";
        element5.name = "from_no";
        element5.id = "from_no"+rowCount;
        element5.size = 30;
        element5.value = from_no;
        cell5.appendChild(element5);

        var cell6 = row.insertCell(5);
        var element6 = doc.createElement("input");
        element6.type = "text";
        element6.name = "to_no";
        element6.id = "to_no"+rowCount;
        element6.size = 30;
        element6.value = to_no;
        cell6.appendChild(element6);

        var cell7 = row.insertCell(6);
        var element7 = doc.createElement("input");
        element7.type = "text";
        element7.name = "date";
        element7.id = "date"+rowCount;
        element7.size = 30;
        element7.value = date;
        cell7.appendChild(element7);

        var cell8 = row.insertCell(7);
        var element8= doc.createElement("input");
        element8.type = "text";
        element8.name = "remark";
        element8.id = "remark"+rowCount;
        element8.size = 30;
        element8.value = remark;
        cell8.appendChild(element8);
        var height = (rowCount * 40)+ 60;

        doc.getElementById("autoCreateTableDiv").style.visibility = "visible";
        doc.getElementById("autoCreateTableDiv").style.height = height+'px';
        if(rowCount == 1){
            $('#checkUncheckAll').attr('hidden', true);
        }else{
            $('#checkUncheckAll').attr('hidden', false);
        }
    }
    function deleteRowWithoutResetForm() {
        try {
            var table = doc.getElementById('insertTable');
            var rowCount = table.rows.length;

            for(var i=0; i<rowCount-1; i++) {
                table.deleteRow(1);
            }
            doc.getElementById("autoCreateTableDiv").style.visibility = "hidden";
            doc.getElementById("autoCreateTableDiv").style.height = '0px';
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
        var searchLicence = doc.getElementById("searchLicenseNo").value;
        var queryString;
        if(id=='viewPdf')
            queryString = "task=PRINTRecordList&searchLicenseNo="+searchLicence;
        else
            queryString = "task=PRINTXlsRecordList&searchLicenseNo="+searchLicence;
        var url = "licenseDetailCont.do?" + queryString;
        popupwin = openPopUp(url, "License List", 600, 900);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }   
   
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>License Detail </title>
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
                                        <td align="center" class="header_table" width="90%"><b>License Detail</b></td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                        <%-- <td>
                                             <%@include file="/view/layout/org_menu.jsp" %>
                                         </td> --%>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="licenseDetailCont.do" >
                                        <table align="center" class="heading1" width="700">
                                            <%--<tr>
                                                <th>Office Code</th>
                                                <td><input class="input" type="text" id="searchOfficeCode" name="searchOfficeCode" value="${searchOfficeCode}" size="30" ></td>
                                                <th>Office Name</th>
                                                <td><input class="input" type="text" id="searchOfficeName" name="searchOfficeName" value="${searchOfficeName}" size="30" ></td>
                                            </tr>
                                            <%-- <tr>

                                                <th>From Date</th>
                                                <td > <input class="input" type="text" id="searchFromDate" name="searchFromDate" value="${searchFromDate}" size="30" ></td>
                                                <th>To Date</th>
                                                <td><input class="input" type="text" id="searchToDate" name="searchToDate" value="${searchToDate}" size="30" ></td>

</tr>--%>
                                            <tr>
                                                <th>License No.</th>
                                                <td><input class="input" type="text" id="searchLicenseNo" name="searchLicenseNo" value="${searchLicenseNo}" size="30" ></td>

                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search" ></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                    <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick=" getRecordlist(id)">
                                                    <input class="button" type="button" id="viewXls" name="viewXls" value="Excel" onclick=" getRecordlist(id)">
                                                </td>

                                                <%-- <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>--%>

                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="licenseDetailCont.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class= "heading">S.No.</th>
                                                <th class="heading">License No.</th>
                                                <th class="heading">Name</th>
                                                <th class="heading">Age</th>
                                                <th class="heading">Address</th>
                                                <th class="heading">Issue Date</th>
                                                <th class="heading">Validity</th>
                                                <th class="heading">Is Permanent</th>
                                                <th class="heading">Vehicle Type</th>
                                                <th class="heading">Remark</th>
                                                <!--<th  class="heading">Active</th>-->
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from taxTypeList of TaxController     --->
                                            <c:forEach var="retrieve" items="${requestScope['licenseDetailList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                        <input type="hidden" id="license_detail_id${loopCounter.count}" name="license_detail_id${loopCounter.count}" value="${retrieve.license_detail_id}">
                                                    </td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.license_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.owner_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.owner_age}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.address}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.issue_date}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.validity}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.is_permanent}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${retrieve.vehicle_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${retrieve.remark}</td>
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
                                            <input  type="hidden" id="searchOffenceType" name="searchOffenceType" value="${searchOffenceType}" >
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
                                    <form name="form2" method="POST" action="licenseDetailCont.do" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>

                                            <tr>
                                                <th class="heading1">License NO.</th>
                                                <td>
                                                    <input class="input" type="text" id="license_no" name="license_no" value="" size="40"   disabled>
                                                </td>
                                                <th class="heading1">Name</th>
                                                <td>
                                                    <input class="input" type="hidden" id="license_detail_id" name="license_detail_id" value="" size="40" >
                                                    <input class="input" type="text" id="owner_name" name="owner_name" value="" size="40" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Age</th>
                                                <td><input class="input" type="text" id="age" name="age" value="" size="40" disabled></td>
                                                <th class="heading1">Address</th>
                                                <td>
                                                    <input class="input" type="text" id="address" name="address" value="" size="40" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Issue Date</th><td><input class="input" type="text" id="issue_date" name="issue_date" value="" size="40" disabled></td>
                                                <th class="heading1">Validity</th><td><input class="input" type="text" id="validity" name="validity" value="" size="40" disabled></td>
                                            </tr>

                                            <tr>
                                                <th class="heading1">Is Permanent</th>
                                                <td>
                                                    <select name="is_permanent" class="dropdown" id="is_permanent" disabled>
                                                        <option value="Y">Yes</option>
                                                        <option value="N">No</option>
                                                    </select>                                                    
                                                </td>
                                                <th class="heading1">Vehicle Type</th>
                                                <td>
                                                    <!--<input class="input" type="text" id="vehicle_type" name="vehicle_type" value="" size="40" disabled>-->
                                                    <select class="dropdown" id="vehicle_type" name="vehicle_type" disabled>
                                                        <c:forEach var="vehicle" items="${requestScope['vehicleList']}"  varStatus="loopCounter">
                                                            <option value="${vehicle.key}">${vehicle.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="" size="40" disabled></td>
                                                <th class="heading1"></th><td><input class="input" type="hidden" id="book_revision_no" name="book_revision_no" value="" size="40" ></td>
                                            </tr>

                                            <tr>
                                                <th class="heading1"></th><td><input class="input" type="hidden" id="active" name="active" value="" size="40"></td>
                                            </tr>

                                            <tr>
                                                <td align='center' colspan="4">
                                                    <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)"  disabled>
                                                    <!--<input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>-->
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save As New" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    <!--<input class="submit" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled>-->
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


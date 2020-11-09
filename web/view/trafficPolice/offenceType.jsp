<%--
    Document   : stateView
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
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="JS/css-pop.js"></script>
<script type="text/javascript" src="JS/css-pop.js"></script>
<script type="text/javascript" language="javascript">
    
    $(document).ready(function(){
        $("#act_origin").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 15px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                 
                 $("#searchActOrigin").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 15px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                 $("#searchAct").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 15px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
        
        
    });
    
    
    jQuery(function(){
        $("#searchOffenceType").autocomplete("offenceTypeCont", {
            extraParams: {
             
                action1: function() { return "getOffenceype"},
                action2: function() { return document.getElementById("searchActOrigin").value;}
       }
        });
        $("#searchOffenceCode").autocomplete("offenceTypeCont", {
            extraParams: {
                action1: function() { return "getOffenceCode"}
            }
        });
        $("#vehicle_type").autocomplete("offenceTypeCont", {
            extraParams: {
                action1: function() { return "getVehicleType"}
            }
        });
        $("#act_origin").autocomplete("offenceTypeCont", {
            extraParams: {
                action1: function() { return "getActOrigin"}
            }
        });


        $("#searchAct").autocomplete("offenceTypeCont", {
            extraParams: {
                action1: function() { return "getSearchAct"}
            }
        });

        $("#searchActOrigin").autocomplete("offenceTypeCont", {
            extraParams: {
                action1: function() { return "getSearchActOrigin"}
            }
        });

        $("#transportation_type").autocomplete("offenceTypeCont", {
            extraParams: {
                action1: function() { return "getTransportationType"},
                action2: function() { return document.getElementById("vehicle_type").value}
            }
        });
        $("#from_date").datepicker({

            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $("#to_date").datepicker({

            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });

        var cssFunction = function(){
                             $(".ac_results li").css({
                                 'margin': '0px',
                                 'padding': '2px 5px',
                                 'cursor' : 'default',
                                 'display' : 'block',
                                 'color' : '#972800',
                                 'font-family' :'Sans-Serif',//, 'kruti Dev 010',
                                 'font-size': '12px',
                                 'line-height': '16px',
                                 'overflow': 'hidden'
                             });
                         }
         $("#searchActOrigin").click(cssFunction);
         $("#searchOffenceType").click(cssFunction);
         $("#searchAct").click(cssFunction);
         $("#act_origin").click(cssFunction);
         $("#vehicle_type").click(cssFunction);
         $("#transportation_type").click(cssFunction);
    });


    function makeEditable(id) {
        document.getElementById("offence_type").disabled = false;
        document.getElementById("act").disabled = false;
        document.getElementById("penalty").disabled = false;
        document.getElementById("from_date").disabled = false;
        document.getElementById("to_date").disabled = false;
        document.getElementById("vehicle_type").disabled = false;
        document.getElementById("act_origin").disabled = false;
        document.getElementById("active").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("transportation_type").disabled = false;
        document.getElementById("offence_code").disabled=false;
        document.getElementById("save").disabled = false;
        document.getElementById("have_second_offence").disabled = false;
        document.getElementById("secondOffencePenalty").disabled = false;

        if(id == 'new') {

            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            $("#offence_type_id").val("0");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 13);
            document.getElementById("offence_type").focus();

        }
        if(id == 'edit'){
            document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
        }


    }

    function setStatus(id) {
    
        if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        }
        else if(id == 'save_As'){
            document.getElementById("clickedButton").value = "Save AS New";
        }
        else document.getElementById("clickedButton").value = "Delete";
    }

    function verify() {
        var result;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {

            var offence_type = document.getElementById("offence_type").value;
            var act = document.getElementById("act").value;
            var penalty = document.getElementById("penalty").value;
            var from_date= document.getElementById("from_date").value;
            var to_date = document.getElementById("to_date").value;
            var active = document.getElementById("active").value;
            var act_origin=document.getElementById("act_origin").value;
            var vehicle_type=document.getElementById("vehicle_type").value;
            var offence_code=document.getElementById("offence_code").value;
            var transportation_type=document.getElementById("transportation_type").value;
            if(myLeftTrim(offence_type).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b> offence type is required...</b></td>");
                document.getElementById("offence_type").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(act).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Act  is required...</b></td>");
                document.getElementById("act").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(penalty).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>penalty Field  is required...</b></td>");
                document.getElementById("penalty").focus();
                return false; // code to stop from submitting the form2.
            }if(myLeftTrim(from_date).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>from date Field  is required...</b></td>");
                document.getElementById("from_date").focus();
                return false; // code to stop from submitting the form2.
            }if(myLeftTrim(to_date).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>To date Field  is required...</b></td>");
                document.getElementById("to_date").focus();
                return false; // code to stop from submitting the form2.
            }if(myLeftTrim(act_origin).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Act Origin Field  is required...</b></td>");
                document.getElementById("act_origin").focus();
                return false; // code to stop from submitting the form2.
            }if(myLeftTrim(vehicle_type).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Vehicle Type Field  is required...</b></td>");
                document.getElementById("vehicle_type").focus();
                return false; // code to stop from submitting the form2.
            }if(myLeftTrim(offence_code).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Offenece COde Field  is required...</b></td>");
                document.getElementById("offence_code").focus();
                return false; // code to stop from submitting the form2.
            }if(myLeftTrim(transportation_type).length == 0) {
                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Transportation Type Field  is required...</b></td>");
                document.getElementById("transportation_type").focus();
                return false; // code to stop from submitting the form2.
            }


            if(result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

            }
            else{ result = true;
            }

            if(document.getElementById("clickedButton").value == 'Save AS New'){
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else result = confirm("Are you sure you want to delete this record?")
        return result;
    }

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }

    function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns =15;

        var columnId = id;
          
    <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
            columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
            var lowerLimit, higherLimit;

            for(var i = 0; i < noOfRowsTraversed; i++) {
                lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
         
                if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
            }

            setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
            var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
       
            document.getElementById("offence_type_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
       
            document.getElementById("offence_type").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
            document.getElementById("offence_code").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
            document.getElementById("act").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
            document.getElementById("penalty").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
            document.getElementById("from_date").value = document.getElementById(t1id +(lowerLimit+7)).innerHTML;
            document.getElementById("to_date").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
            document.getElementById("vehicle_type").value = document.getElementById(t1id +(lowerLimit+9)).innerHTML;
            document.getElementById("act_origin").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
            document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+10)).innerHTML;
            document.getElementById("transportation_type").value = document.getElementById(t1id +(lowerLimit+11)).innerHTML;
            document.getElementById("active").value = document.getElementById(t1id +(lowerLimit+12)).innerHTML;
            
            document.getElementById("have_second_offence").value = document.getElementById(t1id +(lowerLimit+13)).innerHTML;
            
            document.getElementById("secondOffencePenalty").value = document.getElementById(t1id +(lowerLimit+14)).innerHTML;

            for(var i = 0; i < noOfColumns; i++) {
                document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
            }
            document.getElementById("edit").disabled = false;
            if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
            {
                document.getElementById("save_As").disabled = true;
                document.getElementById("delete").disabled = false;
            }
            
            //  document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
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
        function displayMapList(){
            var searchOffeneceType=document.getElementById("searchOffenceType").value;
            var searchOffenceCode=document.getElementById("searchOffenceCode").value;


            var searchAct=document.getElementById("searchAct").value;
            var searchActOrigin=document.getElementById("searchActOrigin").value;
       
            var queryString = "task=generateMapReport&searchOffeneceType="+searchOffeneceType+"&searchOffeneceCode="+searchOffenceCode+"&searchAct="+searchAct+"&searchActOrigin="+searchActOrigin;
            var url = "offenceTypeCont?"+queryString;


            popupwin = openPopUp(url, "Offence Type  Details in pdf  file", 500, 1000);



        }

        function displayMapListExcel(){
            var searchOffeneceType=document.getElementById("searchOffenceType").value;
            var searchOffenceCode=document.getElementById("searchOffenceCode").value;          
         
            var searchAct=document.getElementById("searchAct").value;
            var searchActOrigin=document.getElementById("searchActOrigin").value;
          
            var queryString = "task=generateMapReportExcel&searchOffeneceType="+searchOffeneceType+"&searchOffeneceCode="+searchOffenceCode+"&searchAct="+searchAct+"&searchActOrigin="+searchActOrigin;
            var url = "offenceTypeCont?"+queryString;
            popupwin = openPopUp(url, "Offence Type  Details In Excel", 500, 1000);

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
        <title>offence type</title>
        
        <style>
        .required_field_class{
          color:red;
          padding-left: 2px;
          font-size: 15px;
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
                                        <td align="center" class="header_table" width="90%"><b>OFFENCE TYPE</b></td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                        <%-- <td>
                                             <%@include file="/view/layout/org_menu.jsp" %>
                                         </td> --%>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td><div align="center">
                                    <form name="form0" method="post" action="offenceTypeCont">
                                        <table align="left" class="heading1" width="900">
                                            <tr align="left">

                                                <th>Act  origin</th>
                                                <td><input class="input" type="text" id="searchActOrigin" name="searchActOrigin" value="${searchActOrigin}" size="30" ></td>
                                                <th>Offence Type</th>
                                                <td><input class="input" type="text" id="searchOffenceType" name="searchOffenceType" value="${searchOffenceType}" size="35" ></td>

                                                <th>Offence Code</th>
                                                <td><input class="input" type="text" id="searchOffenceCode" name="searchOffenceCode" value="${searchOffenceCode}" size="20" ></td>



                                            </tr><tr align="left">



                                                <th>Act  </th>
                                                <td><input class="input" type="text" id="searchAct" name="searchAct" value="${searchAct}" size="20" ></td>

                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search" ></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>
                                                <td><input type="button" class="button"  id="viewPdf" name="viewExcel" value="Excel" onclick="displayMapListExcel()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="offenceTypeCont">
                                    <DIV style="max-height: 600px;vertical-align: top" class="content_div">
                                        <%--  <table id="table1" width="600"  border="1"  align="center" class="content">   ---%>
                                        <table border="1" id="table1" width="150%" align="center"  class="content" style="vertical-align: top">
                                            <tr>
                                                <th class="heading">S.No.</th>

                                                <th  class="heading">Act origin</th>
                                                <th class="heading" style="white-space: normal; width: 15%" >Offence Type</th>
                                                <th class="heading">Offence Code</th>
                                                <th class="heading">Act</th>
                                                <th class="heading">Penalty</th>
                                                <th class="heading">From Date</th>
                                                <th class="heading">To Date</th>
                                                <th  class="heading">Vehicle Type</th>
                                                <th  class="heading"style="white-space: normal; width: 10%">Remark</th>
                                                <th class="heading">Transport Type</th>
                                                <th class="heading">Active</th>
                                                
                                                <th class="heading">Have Second Offence</th>
                                                <th class="heading">Second Offence Penalty</th>

                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from taxTypeList of TaxController     --->
                                            <c:forEach var="offenceTypeBean" items="${requestScope['offenceTypeList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                          <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                          <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                      </td> --%>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${offenceTypeBean.offence_type_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>

                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.act_origin}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"style="white-space:normal">${offenceTypeBean.offence_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.offence_code}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.act}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.penalty_amount}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.from_date}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.to_date}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.vehicle_type}</td>

                                                    <%----    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.model}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.make}</td>   ---%>


                                                    <td  id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="white-space:normal" >${offenceTypeBean.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.tarnsport_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.active}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.have_second_offence}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${offenceTypeBean.second_offence_penalty}</td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="12">
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
                                            <input  type="hidden" id="searchOffenceCode" name="searchOffenceCode" value="${searchOffenceCode}" >
                                            <input  type="hidden" name="searchAct" value="${searchAct}" size="20" >
                                            <input  type="hidden"  name="searchActOrigin" value="${searchActOrigin}" size="30" >
                                            <input  type="hidden" name="searchOffenceCode" value="${searchOffenceCode}" size="20" >
                                            <input  type="hidden"  name="searchOffenceType" value="${searchOffenceType}" size="30" >
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="offenceTypeCont" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="700">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>

                                                <th class="heading1">Offence Type<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="hidden" id="offence_type_id" name="offence_type_id" value="" >
                                                    <input class="input new_input" type="text" id="offence_type" name="offence_type" value="" size="50" disabled>

                                                </td>
                                                <th class="heading1">Offence Code<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="text" id="offence_code" name="offence_code" value="" size="50" disabled>
                                                </td>


                                            </tr>
                                            <tr>
                                                <th class="heading1">Act<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="text" id="act" name="act" value="" size="40" disabled>
                                                </td>
                                                <th class="heading1">Act Origin<span class="required_field_class">*</span></th>
                                                <td><input class="input" type="text" id="act_origin" name="act_origin" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Penalty<span class="required_field_class">*</span></th><td><input class="input" type="text" id="penalty" name="penalty" value="" size="40" disabled></td>
                                                <th class="heading1">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">From Date<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="text" id="from_date" name="from_date" value="" size="40" disabled>
                                                </td>

                                                <th class="heading1">To Date<span class="required_field_class">*</span></th><td><input class="input" type="text" id="to_date" name="to_date" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Vehicle Type<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="text" id="vehicle_type" name="vehicle_type" value="" size="40" disabled>
                                                </td>
                                                <th class="heading1">Transportation Type<span class="required_field_class">*</span></th><td><input class="input" type="text" id="transportation_type" name="transportation_type" value="" size="40" disabled></td>

                                            </tr>
                                            <tr>
                                                <th class="heading1">Have Second Offence<span class="required_field_class">*</span></th>
                                                <td>
                                                    <select id="have_second_offence" name="have_second_offence" disabled>
                                                        <option value="NO">No</option>
                                                        <option value="YES">Yes</option>
                                                    </select>
                                                </td>
                                                
                                                <th class="heading1">Second Offence Penalty</th>
                                                <td><input class="input" type="text" id="secondOffencePenalty" name="secondOffencePenalty" value="" size="40" disabled></td>   
                                            </tr>
                                            

                                            <tr><td><input class="input" type="hidden" id="active" name="active" value="" size="40" disabled></td></tr>
                                            <tr>
                                                <td align='center' colspan="8">
                                                    <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchOffenceType" value="${searchOffenceType}" >
                                            <input  type="hidden" name="searchAct" value="${searchAct}" size="20" >
                                            <input  type="hidden"  name="searchActOrigin" value="${searchActOrigin}" size="30" >
                                            <input  type="hidden" name="searchOffenceCode" value="${searchOffenceCode}" size="20" >


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


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
<script type="text/javascript" language="javascript">
    jQuery(function(){
        $("#searchCity").autocomplete("cityLocationCont", {
            extraParams: {
                action1: function() { return "getCity"}
            }
        });
        $("#searchZone").autocomplete("cityLocationCont", {
            extraParams: {
                action1: function() { return "getZoneName"}
            }
        });
        $("#searchLocation").autocomplete("cityLocationCont", {
            extraParams: {
                action1: function() { return "getLocationName"}
            }
        });
        $("#city_name").autocomplete("cityLocationCont", {
            extraParams: {
                action1: function() { return "getCityName"}
            }
        });
        $("#zone").autocomplete("cityLocationCont", {
            extraParams: {
                action1: function() { return "getZone"},
                action2: function() { return document.getElementById("city_name").value}
            }
        });

        var cssFunction = function(){
                        $(".ac_results li").css({
                            'margin': '0px',
                            'padding': '2px 5px',
                            'cursor' : 'default',
                            'display' : 'block',
                            'color' : '#972800',
                            'font-family' :'Sans-Serif',//, 'kruti Dev 010',
                            /*font-family:Arial, Helvetica, sans-serif;
                            /*
	                    if width will be 100% horizontal scrollbar will apear
	                    when scroll mode will be used
	                    */
                            /*width: 100%;*/
                            'font-size': '12px',
                            /*
	                    it is very important, if line-height not setted or setted
	                    in relative units scroll will be broken in firefox
	                    */
                           'line-height': '16px',
                           'overflow': 'hidden'
                       });
                    }
                    $("#searchCity").click(cssFunction);
                    $("#searchZone").click(cssFunction);
                    $("#city_name").click(cssFunction);
                    $("#zone").click(cssFunction);
                    $("#searchLocation").click(cssFunction);

                    var locationFontCount = 1;

                          $("#changeLocationFont").click(function(){
                        if(locationFontCount == 1 ){
                              $(".location_name").removeClass("new_input");
                            locationFontCount = 2;
                        }else{
                              $(".location_name").addClass("new_input");
                            locationFontCount = 1;
                        }
                    });

    });


    function makeEditable(id) {
        document.getElementById("city_name").disabled = false;
        document.getElementById("zone").disabled = false;
        document.getElementById("location").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;

        if(id == 'new') {

            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            document.getElementById("city_location_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 7);
            document.getElementById("city_name").focus();

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

            var city_name = document.getElementById("city_name").value;
            var zone=document.getElementById("zone").value;
            var location=document.getElementById("location").value;

            if(myLeftTrim(city_name).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>City Name is required...</b></td>");
                document.getElementById("city_name").focus();
                return false; // code to stop from submitting the form2.
            }
             if(myLeftTrim(zone).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Zone is required...</b></td>");
                document.getElementById("city_name").focus();
                return false; // code to stop from submitting the form2.
            }
             if(myLeftTrim(location).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Location is required...</b></td>");
                document.getElementById("location").focus();
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
        var noOfColumns =7;

        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

        document.getElementById("city_location_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("city_id").value = document.getElementById(t1id +(lowerLimit+1)).innerHTML;
        document.getElementById("city_name").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        document.getElementById("zone").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
        document.getElementById("location").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
        {
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        }
        //document.getElementById("message").innerHTML = "";      // Remove message
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
    function displayMapList(id){
        var queryString;
        var report_for="status_type";
        var searchCity = $("#searchCity").val();
        var searchZone = $("#searchZone").val();
        var searchLocation = $("#searchLocation").val();
        if(id == "viewPdf")
            queryString = "task=generateMapReport" ;
        else
            queryString = "task=generateExcelReport" ;
        queryString = queryString + "&searchCity=" + searchCity + "&searchZone=" + searchZone + "&searchLocation=" + searchLocation;
        var url = "cityLocationCont?"+queryString;
        popupwin = openPopUp(url, "State Type Map Details", 500, 1000);
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
        <title>City Location</title>
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
                                        <td align="center" class="header_table" width="90%"><b>City Location</b></td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                        <%-- <td>
                                             <%@include file="/view/layout/org_menu.jsp" %>
                                         </td> --%>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="get" action="cityLocationCont">
                                        <table align="center" class="heading1" width="700">
                                            <tr>
                                                <th>City</th>
                                                <td><input class="input new_input" type="text" id="searchCity" name="searchCity" value="${searchCityName}" size="30" ></td>
                                                <th>Zone</th>
                                                <td><input class="input" type="text" id="searchZone" name="searchZone" value="${searchZoneName}" size="30" ></td>
                                            </tr><tr>
                                                <th>Location</th>
                                                <td><input class="input new_input" type="text" id="searchLocation" name="searchLocation" value="${searchLocationName}" size="30" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList(id)"></td>
                                                <td><input type="button" class="button" id="viewExcel" name="viewExcel" value="Excel" onclick="displayMapList(id)"></td>

                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="get" action="cityLocationCont">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class="heading">S.No.</th>
                                                <th class="heading">City</th>
                                                <th class="heading">Zone</th>
                                                <th class="heading" id="changeLocationFont">Location</th>
                                                <th  class="heading">Remark</th>
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from taxTypeList of TaxController     --->
                                            <c:forEach var="cityLocationBean" items="${requestScope['cityLocationList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                          <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                          <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                      </td> --%>

                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${cityLocationBean.city_location_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${cityLocationBean.zone_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)" >${cityLocationBean.city}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${cityLocationBean.zone}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" class="new_input location_name" onclick="fillColumns(id)" >${cityLocationBean.location}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${cityLocationBean.remark}</td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="6">
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
                                            <!--- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" id="searchStatusType" name="searchStatusType" value="${searchStatusType}" >
                                            <input  type="hidden" name="searchCity" value="${searchCityName}">
                                            <input  type="hidden" name="searchLocation" value="${searchLocationName}">
                                            <input  type="hidden" name="searchZone" value="${searchZoneName}">
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
                                    <form name="form2" method="get" action="cityLocationCont" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th class="heading1">City</th>
                                                <td>
                                                    <input class="input" type="hidden" id="city_location_id" name="city_location_id" value="" >
                                                    <input class="input" type="hidden" id="city_id" name="city_id" value="" >
                                                    <input class="input new_input" type="text" id="city_name" name="city_name" value="" size="40" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Zone</th><td><input class="input" type="text" id="zone" name="zone" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Location</th><td><input class="input new_input" type="text" id="location" name="location" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Remark</th><td><input class="input new_input" type="text" id="remark" name="remark" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <td align='center' colspan="2">
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
                                            <input type="hidden"  name="searchCityName" value="${searchCityName}">
                                            <input type="hidden"  name="searchZoneName" value="${searchZoneName}">
                                            <input type="hidden"  name="searchLocationName" value="${searchLocationName}">
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


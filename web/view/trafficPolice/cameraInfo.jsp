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
        
        $("#city_name_english").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 $("#searchCameraNo").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 $("#searchCameraType").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 $("#searchLocation").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                 $("#camera_type").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 $("#camera_facing").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
        
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
             $("#camera_type").autocomplete("CameraInfoCont.do", {
            extraParams: {
                action1: function() { return "getCameraType";}     
            }
            });
            $("#camera_facing").autocomplete("CameraInfoCont.do", {
            extraParams: {
                action1: function() { return "getCameraFacing";}     
            }
            });
             
             //////////////////////////////////////////////
             $("#searchCameraNo").autocomplete("CameraInfoCont.do", {
            extraParams: {
                action1: function() { return "getSearchCameraNo";}     
            }
            });


            $("#searchCameraType").autocomplete("CameraInfoCont.do", {
            extraParams: {
                action1: function() { return "getSearchCameraType"}
                 }
            });
           $("#searchLocation").autocomplete("CameraInfoCont.do", {
            extraParams: {
                action1: function() { return "getSearchCityLocation"}
              }
              });

        $("#city_name_english").autocomplete("CameraInfoCont.do", {
            extraParams: {
                action1: function() { return "getLocation"}
            }
        });
        
    });


    function makeEditable(id) {
        document.getElementById("channel_no").disabled = false;
        document.getElementById("camera_no").disabled = false;
        document.getElementById("camera_type").disabled = false;
        document.getElementById("camera_facing").disabled = false;
        document.getElementById("city_name_english").disabled = false;
        document.getElementById("latitude").disabled = false;
        document.getElementById("longitude").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;
        
        document.getElementById("get_cordinate").disabled = false;

        if(id == 'new') {

            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            document.getElementById("camera_info_id").value = "";
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 9);
            document.getElementById("channel_no").focus();

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
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') 
        {
            var channel_no =   document.getElementById("channel_no").value;
            var camera_no =   document.getElementById("camera_no").value;
            var camera_type =   document.getElementById("camera_type").value;
            var camera_facing =   document.getElementById("camera_facing").value;
            var city_name_english =   document.getElementById("city_name_english").value;
            
            
            if(myLeftTrim(channel_no).length == 0) {
                //alert(zone_name);
                $("#message").html("<td colspan='2' bgcolor='coral'><b>channel_no   is required...</b></td>");
                document.getElementById("channel_no").focus();
                return false; // code to stop from submitting the form2.
            }
            
            
            
            //var area_no = document.getElementById("area_no").value;
            if(myLeftTrim(camera_no).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>camera_no  is required...</b></td>");
                document.getElementById("camera_no").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(camera_type).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>camera_type  is required...</b></td>");
                document.getElementById("camera_type").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(camera_facing).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>camera_facing Name. is required...</b></td>");
                document.getElementById("camera_facing").focus();
                return false; // code to stop from submitting the form2.
            }
            
            if(myLeftTrim(city_name_english).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>City Name. is required...</b></td>");
                document.getElementById("city_name_english").focus();
                return false; // code to stop from submitting the form2.
            }
            
            
            if(result == false) {
                // if result has value false do nothing, so result will remain contain value false.
            } else {
                result = true;
            }

        } else {
            result = confirm("Are you sure you want to delete this record?");
        }
        return result;
    }
   



    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }

    function fillColumns(id)
    {
      
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        
        var noOfColumns =10;

        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        //setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

        document.getElementById("camera_Info_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML; 
        
        document.getElementById("channel_no").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
            
        document.getElementById("camera_no").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        
    document.getElementById("camera_type").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
        
    document.getElementById("camera_facing").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
        
        document.getElementById("city_name_english").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
        
        document.getElementById("latitude").value = document.getElementById(t1id +(lowerLimit+7)).innerHTML;
        document.getElementById("longitude").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+9)).innerHTML;
        
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
        var searchCityName = $("#searchCityName").val();
        var searchWardType = $("#searchWardType").val();
        var searchZone = $("#searchZone").val();
        var searchArea = $("#searchArea").val();
 
        if(id == "viewPdf")
            queryString = "task=generateMapReport" ;
        else
            queryString = "task=generateExcelReport" ;
        queryString = queryString + "&searchWardType="+searchWardType+"&searchZone="+searchZone+"&searchArea="+searchArea+"&searchCityName="+searchCityName ;
        var url = "cityLocationCont.do?"+queryString;
        popupwin = openPopUp(url, "State Type Map Details", 500, 1000);
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }
    
    function openMapForCord() {
        var url="CameraInfoCont.do?task=GetCordinates1";//"getCordinate";
        popupwin = openPopUp(url, "",  600, 630);
    }

</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>City Location</title>
        
        <style>
        .required_field_class{
          color:red;
          padding-left: 2px;
          font-size: 15px;
           }   
           .new_input
                {
                    font-size: 16px;
                    font-family:"kruti Dev 010", Sans-Serif;
                    font-weight: 500;
                    /*background-color: white;*/
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
                                        <td align="center" class="header_table" width="90%"><b>Camera Info</b></td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                        <%-- <td>
                                             <%@include file="/view/layout/org_menu.jsp" %>
                                         </td> --%>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="get" action="CameraInfoCont.do">
                                        <table align="center" class="heading1" width="700">
                                             <tr>
                                               <th>Camera No.</th>
                                             <td><input type="text" class="input" name="searchCameraNo" size="20" id="searchCameraNo"  value="${searchCameraNo}"/></td>
                                             <th>Camera Type</th>
                                             <td><input type="text" class="input" name="searchCameraType" size="20" id="searchCameraType"  value="${searchCameraType}"/></td>
                                             <th>Location</th>
                                               <td><input class="input" type="text" id="searchLocation" name="searchLocation" value="${searchLocation}" size="30" ></td>
                                               </tr>
                                            <tr>
                                               
                                                <td colspan="8" align="center"><input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
<!--                                                <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList(id)">
                                                <input type="button" class="button" id="viewExcel" name="viewExcel" value="Excel" onclick="displayMapList(id)">-->
                                            </td>
                                            </tr>
                                       
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="get" action="CameraInfoCont.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="700"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class="heading">S.No.</th>                                              
                                                <th class="heading">Channel No.</th>
                                                <th class="heading">Camera No.</th>
                                                <th class="heading">Camera Type</th>
                                                <th class="heading">Camera Facing</th>
                                                
                                                <th class="heading">City Location(E)</th>
                                                 <th class="heading">Latitude</th>
                                                <th class="heading">Longitude</th>
                                               
                                                <th  class="heading">Remark</th>
                                               
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from taxTypeList of TaxController     --->
                                            <c:forEach var="cityLocationBean" items="${requestScope['cityLocationList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                          <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                          <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                      </td> --%>

                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${cityLocationBean.camera_info_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${cityLocationBean.channel_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${cityLocationBean.camera_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${cityLocationBean.camera_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" class="input" onclick="fillColumns(id)">${cityLocationBean.camera_facing}</td>
                                                    
                                                    <td id="t1c${IDGenerator.uniqueID}" class="input" onclick="fillColumns(id)">${cityLocationBean.city_location}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${cityLocationBean.lattitude}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${cityLocationBean.longitude}</td>
                                                    
                                                    <td id="t1c${IDGenerator.uniqueID}"   onclick="fillColumns(id)">${cityLocationBean.remark}</td>                                                   
                                                    
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="9">
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
                                            <input  type="hidden" name="searchCameraNo" value="${searchCameraNo}">
                                            <input  type="hidden" name="searchCameraType" value="${searchCameraType}">
                                            <input  type="hidden" name="searchLocation" value="${searchLocation}">
                                            
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
                                    <form name="form2" method="post" action="CameraInfoCont.do" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <input class="input" type="hidden" id="camera_Info_id" name="camera_Info_id" value="" >
                                                <th class="heading1">Channel No.<span class="required_field_class">*</span></th>
                                                <td><input class="input" type="text" id="channel_no" name="channel_no" value="" size="20" disabled></td>
                                             <th class="heading1">Camera No.<span class="required_field_class">*</span></th>
                                             <td><input class="input" type="text" id="camera_no" name="camera_no" value="" size="20" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Camera Type<span class="required_field_class">*</span></th>
                                                <td><input class="input" type="text" id="camera_type" name="camera_type" value="" size="20" disabled></td>
                                                <th class="heading1">Camera Facing<span class="required_field_class">*</span></th>
                                                <td><input class="input" type="text" id="camera_facing" name="camera_facing" value="" size="20" disabled></td>
                                            </tr>
                                            <tr>

                                                <th class="heading1">City Location(English)<span class="required_field_class">*</span></th>
                                                <td>
                                                    
                                                    <input class="input" type="text" id="city_name_english" name="city_name_english" value="" size="25" disabled>
                                                </td>
                                                 
                                            </tr>
                                           <tr>
                                               <th class="heading1">Latitude</th><td><input class="input" type="text" id="latitude" name="latitude" value="" size="20" disabled></td>
                                                <th class="heading1">Longitude</th><td><input class="input" type="text" id="longitude" name="longitude" value="" size="20" disabled><input class="button" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()" disabled></td>
                                           </tr>
                                            <tr>
<!--                                                <th class="heading1">Location No<span class="required_field_class">*</span></th>
                                                <td><input class="input" type="text" id="location_no" name="location_no" value="" size="20" disabled></td>-->
                                                <th class="heading1">Remark</th>
                                                <td><input class="input" type="text" id="remark" name="remark" value="" size="20" disabled></td>
                                              </tr>
                                            <tr>
                                                <td align='center' colspan="4">
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
                                            <input  type="hidden" name="searchCameraNo" value="${searchCameraNo}">
                                            <input  type="hidden" name="searchCameraType" value="${searchCameraType}">
                                            <input  type="hidden" name="searchLocation" value="${searchLocation}">
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


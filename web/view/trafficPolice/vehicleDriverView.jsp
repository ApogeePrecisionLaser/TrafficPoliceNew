
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
            jQuery(function(){              

                $("#searchVehicleNo").autocomplete("VehicleDriverCont.do", {
                    extraParams: {
                        action1: function() { return "getVehicleNoList"}
                    }
                });
                $("#searchOwnerName").autocomplete("VehicleDriverCont.do", {
                    extraParams: {
                        action1: function() { return "getKeyPersonList"}
                    }
                });
                $("#search_mobile_no").autocomplete("VehicleDriverCont.do", {
                    extraParams: {
                        action1: function() { return "getMobileNoList"}
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
            });
            function fillColumns(id)
            {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 11;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit;
                for(var i = 0; i < noOfRowsTraversed; i++)
                {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

                    if((columnId>= lowerLimit) && (columnId <= higherLimit)) break;
                }

                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

                document.getElementById("vehicle_driver_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("auto_no").value=document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("mobile_no").value=document.getElementById(t1id +(lowerLimit+3)).innerHTML;
                document.getElementById("latitude").value=document.getElementById(t1id +(lowerLimit+4)).innerHTML;
                document.getElementById("longitude").value=document.getElementById(t1id +(lowerLimit+5)).innerHTML;
                document.getElementById("imei_no").value=document.getElementById(t1id +(lowerLimit+6)).innerHTML;
                document.getElementById("remark").value=document.getElementById(t1id +(lowerLimit+7)).innerHTML;
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
                }


                document.getElementById("edit").disabled = false;

                if(!document.getElementById("save").disabled)
                {
                    document.getElementById("save_as_new").disabled = true;
                    document.getElementById("delete").disabled = false;
                    // document.getElementById("revised").disabled = false;
                    dodument.getElementById("save").disabled=true;

                }

                $("#message").html("");
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {

                for(var i = 0; i < noOfRowsTraversed; i++) {

                    for(var j = 1; j <= noOfColumns; j++) {

                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";
                    }
                }}
            function myLeftTrim(str) {
                var beginIndex = 0;
                for(var i = 0; i < str.length; i++) {
                    if(str.charAt(i) == ' ')
                        beginIndex++;
                    else break;
                }
                return str.substring(beginIndex, str.length);
            }


            function makeEditable(id) {
                document.getElementById("auto_no").disabled = false;
                document.getElementById("mobile_no").disabled = false;
                document.getElementById("latitude").disabled = false;
                document.getElementById("longitude").disabled = false;
                document.getElementById("remark").disabled = false;
                document.getElementById("imei_no").disabled = false;

                document.getElementById("save").disabled = true;

                if(id == 'new') {
                    $("#message").html("");
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_as").disabled =true;
                    document.getElementById("save").disabled =false;
                    document.getElementById("vehicle_driver_id").value=0;




                }
                if(id == 'edit'){
                    $("#message").html("");
                    document.getElementById("save_as").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save").disabled = false;
                }
            }



            function setStatus(id) {

                if(id == 'save'){
                    document.getElementById("clickedButton").value = "save";
                }
                else if(id == 'save_as'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else if(id == 'delete'){
                    document.getElementById("clickedButton").value = "Delete";
                }
            }


            function openCurrentMap(lattitude, longitude) {
                var url="generalCont.do?task=showMapWindow&logitude="+ longitude +"&lattitude="+ lattitude;
                popupwin = openPopUp(url, "",  580, 620);
            }

            function viewDriverImage(id,img,auto_no){
                var queryString = "task=viewImage&kp_id="+id+"&type="+img+"&auto_no="+auto_no;
                var url = "VehicleDriverCont.do?" + queryString;
                popupwin = openPopUp(url, "Show Image", 600, 900);
            }
            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                return window.open(url, window_name, window_features);
            }

            function pdf(){
                var queryString;
                var searchVehicleNo=document.getElementById("searchVehicleNo").value;
                var searchOwnerName=document.getElementById("searchOwnerName").value;
                var search_mobile_no=document.getElementById("search_mobile_no").value;
                var e_chalan=document.getElementById("e_chalan").value;
                var date=document.getElementById("date").value;
                queryString = "task=viewPdf&searchVehicleNo="+searchVehicleNo+"&searchOwnerName="+searchOwnerName+"&search_mobile_no="+search_mobile_no+"&date="+date+"&e_chalan="+e_chalan;
                var url = "VehicleDriverCont.do?" + queryString;
                popupwin = openPopUp(url, "Division List", 600, 900);
            }
        </script>
    </head>
    <body>

        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="90%"><b>Vehicle Driver</b></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="get" action="VehicleDriverCont.do">
                                        <table align="center" class="heading1" width="800">
                                            <tr>
                                                <th>Vehicle No</th>
                                                <td><input  type="text" id="searchVehicleNo" name="searchVehicleNo" value="${searchVehicleNo}" size="20" ></td>
                                                <th>Uploaded By</th>
                                                <td><input class="new_input" type="text" id="searchOwnerName" name="searchOwnerName" value="${searchOwnerName}" size="20" ></td>
                                                <th class="heading1">Mobile No</th>
                                                <td><input type="text"  id="search_mobile_no" name="search_mobile_no" value="${search_mobile_no}" ></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Date</th>
                                                <td><input type="text"  id="date" name="date" value="${date}"></td>
                                                <td>
                                                    <b>E-Challan</b>
                                                    <select class="input" type="text" id="e_chalan" name="e_chalan">
                                                        <option value="" ${e_chalan eq ""? 'selected':''}>All</option>
                                                        <option value="Y" ${e_chalan eq "Y"? 'selected':''}>Yes</option>
                                                        <option value="N" ${e_chalan eq "N"? 'selected':''}>No</option>
                                                    </select>
                                                </td>
                                                <td colspan="6" align="center">

                                                    <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                    <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                    <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="pdf()">
                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        <form name="form1" action="VehicleDriverCont.do">
                            <TABLE BORDER="1" align="center" cellpadding="5%" width="52%" class="content">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <TH class="heading">S.no&nbsp;</TH>
                                <TH class="heading"> Vehicle No &nbsp;</TH>
                                <TH class="heading">Mobile No&nbsp;</TH>
                                <TH class="heading">IMEI No&nbsp;</TH>
                                <TH class="heading">Uploaded By&nbsp;</TH>
                                <TH class="heading">Date & Time&nbsp;</TH>
                                <TH class="heading">E-Challan&nbsp;</TH>
                                <TH class="heading">Map&nbsp;</TH>
                                <TH class="heading">Image&nbsp;</TH>

                                <c:forEach var="ShiftModel" items="${requestScope['list']}" varStatus="loopCounter">
                                    <TR class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" class="bcolor">
                                    <tr>
                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)"> ${ShiftModel.vehicle_driver_id}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center"> ${lowerLimit + loopCounter.count- noOfRowsTraversed }</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftModel.auto_no}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftModel.mobile_no}</td>
                                        <td style="display: none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftModel.latitude}</td>
                                        <td style="display: none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftModel.longitude}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftModel.imei_no}</td>
                                        <td style="display: none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftModel.remark}</td>
                                        <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)">${ShiftModel.key_person_name}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${ShiftModel.date_time}</td>
                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center">${ShiftModel.e_chalan}</td>
                                        <td>  <input id="map" class="" type="button" onclick="openCurrentMap('${ShiftModel.latitude}' , '${ShiftModel.longitude}');" value="Map"></td>
                                        <td>
                                            <input type="button" class="button"  name="Driver" value="Driver" onclick="viewDriverImage(${ShiftModel.vehicle_driver_id}, 'driver','${ShiftModel.auto_no}')">
                                            <input type="button" class="button"  name="Auto" value="Auto" onclick="viewDriverImage(${ShiftModel.vehicle_driver_id}, 'auto','${ShiftModel.auto_no}')">
                                        </td>
                                    </tr>
                                    </TR>
                                </c:forEach>

                                <tr> <td align='center' colspan="9">
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
                                                <input class="button" type='submit' name='buttonAction' value='Last' >
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </TABLE>
                            <input type="hidden"  name="lowerLimit" value="${lowerLimit}">
                            <input type="hidden"  name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                            <input type="hidden"  name="cut_dt" value="${cut_dt}">
                            <input type="hidden" name="searchOwnerName" value='${searchOwnerName}'>
                            <input type="hidden" name="searchVehicleNo" value='${searchVehicleNo}'>
                            <input type="hidden" name="search_mobile_no" value='${search_mobile_no}'>
                            <input type="hidden" name="date" value='${date}'>
                            <input type="hidden" name="e_chalan" value='${e_chalan}'>
                        </form>

                        <br>
                        <br>

                        <form  action="VehicleDriverCont.do" method="post">
                            <table  align="center"  class="content" cellpadding="2%" border="1">
                                <tr id="message">
                                    <c:if test="${not empty message}">
                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                    </c:if>
                                </tr>
                                <tr><input class="input" type="hidden" id="vehicle_driver_id" name="vehicle_driver_id" value="" ></tr>
                                <tr>
                                    <th class="heading1"> Vehicle No </th>
                                    <td>
                                        <input class="input" type="text"  id="auto_no"  name="auto_no" value="" size="20" disabled>
                                    </td>
                                    <th class="heading1">Mobile No</th>
                                    <td>
                                        <input  class="input" type="text"  id="mobile_no" size="20" name="mobile_no" value=""  disabled>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="heading1">Latitude</th><td><input class="input" type="text" id="latitude" name="latitude" value="" size="20" disabled/></td>
                                    <th class="heading1">Longitude</th><td><input class="input" type="text" id="longitude" name="longitude" value="" size="20" disabled/></td>
                                </tr>
                                <tr>
                                    <th class="heading1">IMEI No</th><td><input class="input" type="text" id="imei_no" name="imei_no" value="" size="20" disabled/></td>
                                    <th class="heading1">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="" size="20" disabled/></td>
                                </tr>
                                <tr>
                                    <td colspan="4" align="center" >
                                        <input  class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled >
                                        <input class="button" type="submit" name="task" id="save" value="save" onclick="setStatus(id)" disabled >
                                        <input  class="button" type="submit" name="task" id="save_as" value="Save AS New" onclick="setStatus(id)" disabled>
                                        <input  class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                        <input  class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                    </td>
                                </tr>
                            </table>
                            <input type="hidden" id="clickedButton" value="">
                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                            <input type="hidden"  name="cut_dt" value="${cut_dt}">
                        </form>
                    </table>
                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>

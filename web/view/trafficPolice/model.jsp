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
    
    $(document).ready(function(){
        $("#modelType").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
        
    });
    
    jQuery(function(){
        $("#modelType").autocomplete("modelTypeCont", {
            extraParams: {
                action1: function() { return "getStatusType"}
            }
        });
//        $("#make_type").autocomplete("modelTypeCont", {
//            extraParams: {
//                action1: function() { return "getMakeType"}
//            }
//        });
        $("#vehicle_type").autocomplete("modelTypeCont", {
            extraParams: {
                action1: function() { return "getVehicleType"}
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
                                 'font-size': '12px',
                                 'line-height': '16px',
                                 'overflow': 'hidden'
                             });
                         }
         $("#modelType").click(cssFunction);
         $("#make_type").click(cssFunction);
    });


    function makeEditable(id) {
     document.getElementById("make_type").disabled = false;
      document.getElementById("vehicle_type").disabled = false;
        document.getElementById("model_type").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;

        if(id == 'new') {

            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            $("#model_type_id").val("0");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
            document.getElementById("make_type").focus();

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

            var model_type = document.getElementById("model_type").value;
            var make_type = document.getElementById("make_type").value;
             
             if(myLeftTrim(make_type).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Vehicle Made By  is required...</b></td>");
                document.getElementById("make_type").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(model_type).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Vehicle Model Name is required...</b></td>");
                document.getElementById("model_type").focus();
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
        var noOfColumns = 6;

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

        document.getElementById("model_type_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("make_type").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
          document.getElementById("vehicle_type").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
         document.getElementById("model_type").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
        
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
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
        var report_for="model_type";
        if(id == "viewPdf")
            queryString = "task=generateMapReport" ;
        else
            queryString = "task=generateExcelReport" ;
        var url = "modelTypeCont?"+queryString;
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
        <title>Model</title>
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
                                        <td align="center" class="header_table" width="90%"><b>Vehicle Model</b></td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                        <%-- <td>
                                             <%@include file="/view/layout/org_menu.jsp" %>
                                         </td> --%>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="post" action="modelTypeCont">
                                        <table align="center" class="heading1" width="600">
                                            <tr>
                                                <th>Vehicle Model</th>
                                                <td><input class="input" type="text" id="modelType" name="modelType" value="${modelType}" size="30" ></td>
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
                                <form name="form1" method="get" action="modelTypeCont">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class="heading">S.No.</th>
                                                <th class="heading">Vehicle Made By</th>
                                                <th class="heading" style="display: none">Vehicle Type</th>
                                                <th class="heading">Vehicle Model</th>
                                                <th  class="heading">Remark</th>
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from taxTypeList of TaxController     --->
                                            <c:forEach var="modelTypeBean" items="${requestScope['modelTypeList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                          <input type="hidden" id="model_type_id${loopCounter.count}" value="${modelTypeBean.model_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                          <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                      </td> --%>

                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${modelTypeBean.model_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${modelTypeBean.make}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none"  onclick="fillColumns(id)" >${modelTypeBean.vehicle_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${modelTypeBean.model}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${modelTypeBean.remark}</td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="4">
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
                                            <input  type="hidden" id="modelType" name="modelType" value="${modelType}" >
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
                                    <form name="form2" method="post" action="modelTypeCont" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                             <tr>
                                                <th class="heading1">Vehicle Made By<span class="required_field_class">*</span></th>
                                                <td>
<!--                                                    <input class="input" type="text" id="make_type" name="make_type" value="" size="40" disabled>-->
                                                
                                                <select class="dropdown" id="make_type" name="make_type" disabled>
                                                        <option value="" selected>All</option>
                                                        <c:forEach var="vehiclemodelTypeList" items="${vehiclemodelTypeList}">
                                                            <option value="${vehiclemodelTypeList.value}" ${vehiclemodelTypeList == vehiclemodelTypeList.value?'selected':''}> ${vehiclemodelTypeList.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <input class="input" type="hidden" id="status_type_id" name="status_type_id" value="" disabled>

                                                
                                                
                                                
                                                </td>
                                            </tr>
                                            <tr style="display: none">
                                                <th class="heading1">Vehicle Type<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="text" id="vehicle_type" name="vehicle_type" value="" size="40" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Vehicle Model<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="hidden" id="model_type_id" name="model_type_id" value="" >
                                                    <input class="input" type="text" id="model_type" name="model_type" value="" size="40" disabled>
                                                </td>
                                            </tr>
                                            
                                            <tr>
                                                <th class="heading1">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="" size="40" disabled></td>
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
                                            <input type="hidden"  name="modelType" value="${modelType}" >
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


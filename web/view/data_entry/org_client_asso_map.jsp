<%-- 
    Document   : org_client_asso_map
    Created on : Mar 17, 2012, 5:09:57 PM
    Author     : Tarun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry: Client Associate Organisation Map Table</title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="calendar.css" rel="stylesheet" type="text/css">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
            jQuery(function(){

                $("#client_asso_org").autocomplete("orgClientAssoMapCont.do", {
                    extraParams: {
                        action1: function() { return "getClientAssoOrganization"}
                    }
                });
                $("#client_org").autocomplete("orgClientAssoMapCont.do", {
                    extraParams: {
                        action1: function() { return "getClientOrgName"}
                    }
                });                
            });
            function makeEditable(id) {
                document.getElementById("client_asso_org").disabled = false;
                document.getElementById("client_org").disabled = false;
                document.getElementById("description").disabled = false;
                document.getElementById("client_asso_org").focus();
                if(id == 'new') {
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("clickedButton2").value = "new";
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
                }
                if(id == 'edit') {
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("clickedButton2").value = "edit";
                }
                document.getElementById("save").disabled = false;
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
                var noOfColumns = 4;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNo = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                var lower= lowerLimit;
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).

                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                document.getElementById("org_client_asso_map_id").value= document.getElementById("org_client_asso_map_id" + rowNo).value;
                document.getElementById("client_asso_org").value = document.getElementById(t1id +(lower+1)).innerHTML;
                document.getElementById("client_org").value = document.getElementById(t1id +(lower+2)).innerHTML;5
                document.getElementById("description").value = document.getElementById(t1id +(lower+3)).innerHTML;

                for(var i = 0; i <= 3; i++) {
                    document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
                {
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("delete").disabled = false;
                }
                // document.getElementById("message").innerHTML = "";      // Remove message
                $("#message").html("");
            }
            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                } else if(id == 'edit') {
                    document.getElementById("clickedButton").value = "Edit";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else document.getElementById("clickedButton").value = "Delete";;
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

            //############################################################################

            function verify() {
                var result;
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var client_asso_org = document.getElementById("client_asso_org").value;
                    var client_org = document.getElementById("client_org").value;

                    if(myLeftTrim(client_asso_org).length == 0) {
                        //  document.getElementById("message").innerHTML = "<td colspan='2' bgcolor='coral'><b>Associate Org Name is required...</b></td>";
                        $("#message").html("<td colspan='2' bgcolor='coral'><b>Associate Org Name is required...</b></td>");
                        document.getElementById("client_asso_org").focus();
                        return false;
                    }
                    if(myLeftTrim(client_org).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='2' bgcolor='coral'><b>Client Org Name is required...</b></td>";
                        $("#message").html("<td colspan='2' bgcolor='coral'><b>Client Org Name is required...</b></td>");
                        document.getElementById("client_org").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(result == false)
                    {// if result has value false do nothing, so result will remain contain value false.
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

        </script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0"  class="main">            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr>
                <%--   <td width="50" height="600" valign="top"><%@include file="/view/layout/Leftmenu.jsp" %></td></tr> --%>

                <td>
                    <DIV id="body" class="maindiv">
                        <table width="100%">
                            <tr><td>
                                    <table>
                                        <tr>
                                            <td align="center" class="header_table"  width="800">Client Associate Organization Mapping Table</td>
                                            <td>
                                                <%@include file="/layout/org_menu.jsp" %>
                                            </td>
                                        </tr>
                                    </table>
                                </td></tr>
                            <tr>
                                <td align="center">
                                    <form name="form1" method="post" action="orgClientAssoMapCont.do" >
                                        <DIV class="content_div">
                                            <table  border="1" id="table1" align="center"  class="content" width="550">
                                                <tr>
                                                    <th class="heading" >S.No.</th>
                                                    <th class="heading" >Associate_Organization_name</th>
                                                    <th class="heading" >Client_Organization_name</th>
                                                    <th class="heading" >Description</th>
                                                </tr>
                                                <c:forEach var="org_ca_map_" items="${orgCAMapList}"  varStatus="loopCounter">
                                                    <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            <input type="hidden" id="org_client_asso_map_id${loopCounter.count}" value="${org_ca_map_.org_client_asso_map_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${org_ca_map_.org_asso_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${org_ca_map_.org_client_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${org_ca_map_.description}</td>

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
                                                                <input class="button" type='submit' name='buttonAction' value='Previous' disabled >
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
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <div >
                                        <form name="form2"  action="orgClientAssoMapCont.do" method="post" onsubmit="return verify();">
                                            <table  border="0" id="table2" align="center" class="content" width="550">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <th class="heading1"  >Associate Organization Name</th>
                                                    <td>
                                                        <input type="hidden" id="org_client_asso_map_id" name="org_client_asso_map_id" value="" >
                                                        <input class="input" type="text" id="client_asso_org" name="client_asso_org" size="45" value="" disabled >
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Client Organization Name</th><td><input class="input" id="client_org" name="client_org" value="" size="45" disabled></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Description</th><td><input class="input" type="text" id="description" name="description" value="" size="45" disabled></td>
                                                </tr>


                                                <tr>
                                                    <td align='center' colspan="2">
                                                        <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id);setStatus(id);" disabled> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input class="button" type="submit" name="task" id="save" value="Save"  onclick="setStatus(id);" disabled> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled> &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                                <input type="hidden" id="clickedButton2" value="">
                                            </table>
                                        </form></div>
                                </td>
                            </tr>
                        </table>
                    </DIV>
                </td></tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>


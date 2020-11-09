<%-- 
    Document   : deviation_detail
    Created on : Aug 22, 2013, 2:44:31 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@taglib prefix="myfn" uri="http://myCustomTagFunctions" %>  ---%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Commercial type Detail</title>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
         
         $(document).ready(function(){
        $("#searchCommercial").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
        
    });
         
              jQuery(function(){
                
                $("#searchCommercial").autocomplete("commercialTypeCont.do", {
                    extraParams:{
                        action1:function() { return "getCommercialType"}
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
         $("#searchCommercial").click(cssFunction);
            });
            function makeEditable(id) {

                // alert(id);
                document.getElementById("query_type1").disabled = false;
                
                document.getElementById("description").disabled = false;
                document.getElementById("update").disabled = false;
                document.getElementById("delete").disabled = false;
                document.getElementById("save_As").disabled = false;
                document.getElementById("save").disabled = true;
                if(id == 'new') {
                    $("#message").html("");
                    document.getElementById("update").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("save").disabled = false;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value,3);
                    document.getElementById("query_type1").focus();
                    // alert(id);

                    $("#query_type_id").val(0);
                }
                /*if(id == 'edit') {
                            document.getElementById("save_As").disabled = false;
                            document.getElementById("delete").disabled = false;
                            document.getElementById("deviation_percentage").focus();
                        } */
                
            }
            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'delete'){
                    document.getElementById("clickedButton").value = "Delete";
                }
                else if(id == 'update'){
                    document.getElementById("clickedButton").value = "Update";
                }
                else {
                    document.getElementById("clickedButton").value = "Save As New";;
                }
                //alert("setStatus");
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
                // alert(noOfRowsTraversed);
                var noOfColumns = 3;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNum = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNum++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                var lower= lowerLimit;
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                // alert(lowerLimit);
                var t1id = "t1c";
                document.getElementById("query_type_id").value = document.getElementById("query_type_id" + rowNum).value;

       

                document.getElementById("query_type1").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;

             
                document.getElementById("description").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                // Now enable/disable various buttons.

                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lower + i)).bgColor = "#dad0fd";        // set the background color of clicked row to yellow.
                }
                makeEditable('');
                /*
                        document.getElementById("edit").disabled = false;
                        if(!document.getElementById("save").disabled) {
                            // if save button is already enabled, then make edit, and delete button enabled too.
                            document.getElementById("delete").disabled = false;
                        }*/
                $("#message").html("");
            }
            function myLeftTrim(str) {
                var beginIndex = 0;
                for(var i = 0; i < str.length; i++) {
                    if(str.charAt(i) == ' ') {
                        beginIndex++;
                    } else {
                        break;
                    }
                }
                return str.substring(beginIndex, str.length);
            }
            function verify() {
                try{
                    var result;
                    if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save As New' || document.getElementById("clickedButton").value == 'Update') {
                        var query_type = document.getElementById("query_type1").value;
                        var description = document.getElementById("description").value;
                        if(myLeftTrim(query_type).length == 0) {
                            // document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>General uploaded for Name is required...</b></td>";
                            $("#message").html("<td colspan='2' bgcolor='coral'><b>Commercial Type  is required...</b></td>");
                            document.getElementById("query_type1").focus();
                            return false; // code to sdd from submitting the form2.
                        }
                        
                       

                        if(document.getElementById("clickedButton").value == 'Save As New'){
                            result = confirm("Are you sure you want to save it as New record?")
                            return result;
                        }
                        else if(document.getElementById("clickedButton").value == 'Update'){
                            result = confirm("Are you sure you want to update this record?")
                            return result;
                        }
                    } else {
                        result = confirm("Are you sure you want to delete this record?");
                    }

                    if(document.getElementById("query_type_id").value == 0){
                        addRow(query_type,description);
               
                        $("#message").html("");
                 
                        document.getElementById('form2').reset();
                      
                        document.getElementById("query_type1").focus();
                        //alert("fdfdf");
                        return false;

                    }
                }catch(e){
                    // alert(e);
                }
                //alert(result);
                return result;
            }
            function addRow(data1,data2) {
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;                // alert(rowCount);
                var row = table.insertRow(rowCount);
                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.type = "hidden";
                element1.name = "query_type_id";
                element1.id = "query_type_id"+rowCount;
                element1.size = 1;
                element1.value = 1;
                element1.readOnly = false;
                cell1.appendChild(element1);
                var element1 = document.createElement("input");
                element1.type = "checkbox";
                element1.name = "check_print";
                element1.id = "check_print"+rowCount;                //element1.size = 1;
                element1.value = "Yes";
                element1.checked = true;
                element1.setAttribute('onclick', 'singleCheckUncheck('+rowCount+')');
                cell1.appendChild(element1);

                var cell2 = row.insertCell(1);
                cell2.innerHTML = $.trim(data1);
                var element2 = document.createElement("input");
                element2.type = "hidden";
                element2.name = "query_type";
                element2.id = "query_type1"+rowCount;
                element2.size = 20;
                element2.value = data1;
                cell2.appendChild(element2);
               
                var cell3 = row.insertCell(2);
                cell3.innerHTML = $.trim(data2);
                var element3 = document.createElement("input");
                element3.type = "hidden";
                element3.name = "description";
                element3.id = "description"+rowCount;
                element3.size = 20;
                element3.value = data2;
                cell2.appendChild(element3);
              
                var height = (rowCount * 25)+ 60;
                document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                document.getElementById("autoCreateTableDiv").style.height = height+'px';
                //   alert("gffg")
                if(rowCount == 1){
                    $('#checkUncheckAll').attr('hidden',true);
                }else{
                    $('#checkUncheckAll').attr('hidden', false);
                }
                //alert("gfgfgf");
            }

            function deleteRow() {
                try {
                    var table = document.getElementById('insertTable');
                    var rowCount = table.rows.length;
                    for(var i=1; i<rowCount; i++) {
                        table.deleteRow(1);
                    }
                    //document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                    //document.getElementById("autoCreateTableDiv").style.height = '0px';
                    $("#autoCreateTableDiv").hide();
                }catch(e) {
                    alert(e);
                }
            }

            function singleCheckUncheck(id){
                // alert(document.getElementById('insertTable').rows.length);
                if ($('#check_print'+id).is(':checked')) {
                    $("#query_type_id"+id).val("1");
                }else{
                    $("#query_type_id"+id).val("0");
                }
                if($('form input[type=checkbox]:checked').size() == 0){
                    $("#addAllRecords").attr("disabled", "disabled");
                }else{
                    $("#addAllRecords").removeAttr("disabled");
                }
            }

            function checkUncheck(id){
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;
                if(id == 'Check All'){
                    $('input[name=check_print]').attr("checked", true);
                    for(var i=1;i<rowCount;i++){
                        $("#feeder_id"+i).val("1");
                    }
                    $('#checkUncheckAll').val('Uncheck All');
                    $("#addAllRecords").removeAttr("disabled");
                }else{
                    $('input[name=check_print]').attr("checked", false);
                    $('#checkUncheckAll').val('Check All');
                    $("#addAllRecords").attr("disabled", "disabled");
                    for(var i=1;i<rowCount;i++){
                        $("#query_type_id"+i).val("2");
                    }
                }
            }
             function displayMapList(id)
            {
                var searchComercial= document.getElementById('searchCommercial').value;
                var action;
                if(id=='viewPdf')
                 action="task=generateMapReport&searchCommercial="+searchComercial;
               else
                action="task=generateMapXlsReport&searchCommercial="+searchComercial;
                var url="commercialTypeCont.do?"+action;
                popup = popupWindow(url,"Commercial_View_Report");
            }
            function popupWindow(url,windowName)
            {
                var windowfeatures= "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
                return window.open(url,windowName,windowfeatures)
            }
        </script>
                <style type="css/text">
                    .r_set{border:inset}
                    
        .required_field_class{
          color:red;
          padding-left: 2px;
          font-size: 15px;
           }    
        
                </style>
                

    </head>
    <body>
        <table align="center"  class="main" cellpadding="0" cellspacing="0" >
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>

                <td><%@include file="/layout/menu.jsp" %> </td>
      
            </tr>
            <tr>
                <td>
                    <div class="maindiv" id="body" >
                        <table width="100%">
                            <tr>
                             
                                  <%---  <div id="wrapper" align="center">
                                        <div class="block1" style="width: 930px">
                                            <div width="90%" class="header_table" ><td>Commercial Type Detail</td></div>
                                        </div>
                                    </div> ---%>
                                 <td align="center" class="header_table" width="90%"><b>Commercial Type Detail</b></td>

                               
                            </tr>
                            <%--      <c:if test="${isSelectPriv eq 'Y'}">  ---%>
                            <tr>
                                <td colspan="2" align="center"><br style="line-height: 6px;">
                       <form action="commercialTypeCont.do" method="post">
                        <span class="heading1"> Commercial Type </span>
                        <input type="text" class="r_set" name="searchCommercial" size="30" id="searchCommercial" value="${searchCommercialType}"/>
                        <input class="button" type="submit" name="searchCommercialModel" value="Search"/>
                        <input class="button" type="submit" name="task" value="Search All Records"/>
                        <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList(id);"/>
                        <input class="button" type="button" id="viewXls" name="viewXls" value="Excel" onclick="displayMapList(id);"/>
                       </form></td>   </tr>

                            <tr>
                                <td align="center">                                  
                                    <form name="form1" method="POST" action="commercialTypeCont.do">
                                        <DIV class="content_div">
                                            <table id="table1" align="center" class="content" width="500">
                                                <tr>
                                                    <th class="heading" style="width: 20px">S.No.</th>
                                                    <th class="heading">Commercial Typee</th>
                                                    <th class="heading">Description</th>
                                                </tr>
                                                <c:forEach var="dd" items="${requestScope['queryTypeList']}" varStatus="loopCounter">
                                                    <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="query_type_id${loopCounter.count}" value="${dd.query_type_id}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${dd.query_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${dd.description}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align="center" colspan="3">
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
                                                    </td>  </tr>
                                                    <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            </table></DIV>
                                    </form>

                                </td>
                            </tr>

                            <tr>                    <td align="center">
                                    <DIV id="autoCreateTableDiv" STYLE="visibility: hidden;height: 0px ;margin-bottom: 10px">
                                        <form name="form3"  action="commercialTypeCont.do" method="post" >
                                            <table id="parentTable" class="content"  align="center" width="500">
                                                <tr>
                                                    <td>
                                                        <table id="insertTable" class="content" align="center" width="100%">
                                                            <tr>
                                                                <th class="heading" style="width: 50px">S.No.</th>
                                                                <th  class="heading">&nbsp;Commercial Type&nbsp;</th>
                                                                <th  class="heading">&nbsp;Description&nbsp;</th>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center">
                                                        <input type="button"  class="button"  name="checkUncheckAll" id="checkUncheckAll" value="Uncheck All" onclick="checkUncheck(value)"/>
                                                        <input type="submit" class="button" id="addAllRecords" name="task" value="Add All Records">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input type="button"  class="button"  name="Cancel" value="Cancel" onclick="deleteRow();">
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </DIV>
                                </td>
                            </tr>

                            <%--   </c:if>  --%>
                            <tr>
                                <td align="center">
                                    <%--   <div style="${!empty(privilegeList) ? '' : 'display:none'}">  --%>
                                    <div>
                                        <form name="form2" id="form2" method="POST" action="commercialTypeCont.do" onsubmit="return verify()">
                                            <table border="0" id="table2" align="center" class="reference" width="500">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="2" bgcolor="${deviation_percentageBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Commercial Type<span class="required_field_class">*</span></th>
                                                    <td>
                                                        <input type="hidden" id="query_type_id" name="query_type_id" value="0">

                                                        <input class="input" type="text" id="query_type1" name="query_type" value="" size="40" >
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Description</th>
                                                    <td>
                                                        <input class="input" type="text" id="description" name="description" value=""  size="40"  >
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="2">
                                                        <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)"  disabled>
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)">
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save As New" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                        <%----   <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)" disabled>
                                                          <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)"  disabled>
                                                          <input class="button" type="submit" name="task" id="save_As" value="Save As New" onclick="setStatus(id)" disabled>
                                                          <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                          <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled> ---%>

                                                    </td>
                                                </tr>

                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div></td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>

    </body>
</html>


<%-- 
    Document   : challanPaymentView
    Created on : Mar 25, 2019, 2:46:21 PM
    Author     : Shobha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>-->
<link href="style/tp_style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>


<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="JS/css-pop.js"></script>
<script type="text/javascript" src="JS/css-pop.js"></script>

<style type="text/css" >
    .ac_results li {
    margin: 0px;
    padding: 2px 5px;
    cursor: default;
    display: block;
    color: #972800;
    font-family: Sans-Serif;
/*    font-family:Arial, Helvetica, sans-serif;*/
    font-size: 12px;
    line-height: 16px;
    overflow: hidden;

}

.required_field_class{
          color:red;
          padding-left: 2px;
          font-size: 15px;
           }

</style>
<script type="text/javascript" language="javascript">
    (function(){
       $(".ac_results li").css({
        'font-family': 'Sans-Serif'
    });    
    });
    
    $(document).ready(function(){
                  
//                  $("input[type='radio']").on("change", function(){ 
//                      alert("1");
//                  //chkPanelChanged(this);
//                   });
//                     $("input[name='radio']").on("change", function () {
//                    alert(this.value);
//                    });
                
                 
                 $("#mode_of_payment").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'Sans-Serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                 $("#searchModeOfPayment").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'Sans-Serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
    });
    
    
    
    
    jQuery(function(){
        
        
        $("#payment_date").datepicker({

                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });
        
        
        $("#searchModeOfPayment").autocomplete("ChallanPaymentCont.do", {
            extraParams: {
                action1: function() { return "getSearchModeOfPayment"}
            }
        });
        $("#mode_of_payment").autocomplete("ChallanPaymentCont.do", {
            extraParams: {
                action1: function() { return "getModeOfPayment"}
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

        $("#searchStatusType").click(cssFunction);
    });


    function makeEditable(id) {
        document.getElementById("receipt_no").disabled = false;
        document.getElementById("mode_of_payment").disabled = false;
        document.getElementById("amount").disabled = false;
        document.getElementById("payment_date").disabled = false;
        document.getElementById("time_h").disabled = false;
        document.getElementById("time_m").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;

        if(id == 'new') {

            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            document.getElementById("challan_payment_id").value = 0;
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
            document.getElementById("receipt_no").focus();

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

            var receipt_no = document.getElementById("receipt_no").value;
            var mode_of_payment = document.getElementById("mode_of_payment").value;
            var amount = document.getElementById("amount").value;
            var payment_date = document.getElementById("payment_date").value;
            

            if(myLeftTrim(receipt_no).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='2' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Challan No.  is required...</b></td>");
                document.getElementById("receipt_no").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(mode_of_payment).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='2' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='2' bgcolor='coral'><b>mode_of_payment  is required...</b></td>");
                document.getElementById("mode_of_payment").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(amount).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='2' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='2' bgcolor='coral'><b>amount  is required...</b></td>");
                document.getElementById("amount").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(payment_date).length == 0) {

                document.getElementById("message").innerHTML = "<td colspan='2' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='2' bgcolor='coral'><b>payment_date  is required...</b></td>");
                document.getElementById("payment_date").focus();
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
        var noOfColumns = 7;

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

        document.getElementById("challan_payment_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("receipt_no").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
        document.getElementById("mode_of_payment").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        document.getElementById("amount").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
        //document.getElementById("payment_date").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
        var offence_date = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
//                        alert(offence_date);
                        
                        var offence_h="";
                        var offence_m="";
                        var offence_date_time =offence_date.split(" "); 
                        offence_date=offence_date_time[0];
                        var offence_time_h_m = offence_date_time[1];
                        var offence_time=offence_time_h_m.split(":");
                        offence_h = offence_time[0];
                        offence_m = offence_time[1];
                        
                        document.getElementById("payment_date").value = offence_date;
                        document.getElementById("time_h").value = offence_h;
                        document.getElementById("time_m").value = offence_m;
                        
        
        
//document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
//        for(var i = 0; i < noOfColumns; i++) {
//            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
//        }
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
        if(id == "viewPdf")
            var queryString = "task=generateMapReport" ;
        else
            queryString = "task=generateExcelReport" ;
        var url = "statusTypeCont?"+queryString;
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
        <title>Status type</title>
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
                                        <td align="center" class="header_table" width="90%"><b>Challan Payment</b></td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                        <%-- <td>
                                             <%@include file="/view/layout/org_menu.jsp" %>
                                         </td> --%>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="post" action="ChallanPaymentCont.do">
                                        <table align="center" class="heading1" width="600">
                                            <tr>
                                                <th>Mode Of Payment</th>
                                                <td><input class="input" type="text" id="searchModeOfPayment" name="searchModeOfPayment" value="${searchPaymentType}" size="30" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
<!--                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList(id)"></td>
                                                <td><input type="button" class="" id="viewExcel" name="viewExcel" value="Excel" onclick="displayMapList(id)"></td>-->
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="post" action="ChallanPaymentCont.do">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class="heading">S.No.</th>
                                                <th class="heading">Challan No.</th>
                                                <th class="heading">Mode Of Payment</th>
                                                <th  class="heading">Amount</th>
                                                <th  class="heading">Date</th>
                                                <th  class="heading">Remark</th>
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from taxTypeList of TaxController     --->
                                            <c:forEach var="statusTypeBean" items="${requestScope['statusTypeList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${statusTypeBean.challan_payment_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${statusTypeBean.receipt_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${statusTypeBean.mode_of_payment}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${statusTypeBean.amount}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${statusTypeBean.payment_date}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${statusTypeBean.remark}</td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="3">
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
                                            <input  type="hidden" id="searchStatusType" name="searchPaymentType" value="${searchPaymentType}" >
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
                                    <form name="form2" method="post" action="ChallanPaymentCont.do" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Challan No.<span class="required_field_class">*</span></th>
                                                <td>
                                                    <input class="input" type="hidden" id="challan_payment_id" name="challan_payment_id" value="" >
                                                    <input class="input" type="text" id="receipt_no" name="receipt_no" value="" size="40" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Mode Of Payment<span class="required_field_class">*</span></th><td><input class="input" type="text" id="mode_of_payment" name="mode_of_payment" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Amount<span class="required_field_class">*</span></th><td><input class="input" type="text" id="amount" name="amount" value="" size="40" disabled></td>
                                            </tr>
                                            <tr>
                                                    <th class="heading1" style=" width:18%">Date<span class="required_field_class">*</span></th> 
                                                    <td><input class="input" type="text" id="payment_date" name="payment_date" value="" size="18" disabled required>

                                                    <b>Time</b>
                                                        <input class="input " type="numeric" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" id="time_h" name="time_h" value="" maxlength="2" size="2" onkeyup="" disabled required>
                                                        <input class="input " type="numeric" pattern="[0-5]{1}[0-9]{1}" id="time_m" name="time_m" value="" maxlength="2" size="2" onkeyup="" disabled required>
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
                                            <input type="hidden"  name="searchPaymentType" value="${searchPaymentType}" >
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


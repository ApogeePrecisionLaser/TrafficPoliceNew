<%-- 
    Document   : mis_Page
    Created on : 23 May, 2019, 1:19:11 PM
    Author     : acer pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>MIS Page</title>
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
            
            <style>
               
                
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
                
                 $("#searchOfficerName").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'kruti Dev 010'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                       });
             
                   
                    
                    
        jQuery(function(){            
                    $("#searchToDate").datepicker({

                        //minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'yy-mm-dd',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    $("#searchFromDate").datepicker({

                        //minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'yy-mm-dd',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });
                    
                    
                     $("#searchOfficerName").autocomplete("trafficPoliceCont", {
                        extraParams: {

                            action1: function() { return "getOfficerSearchList";}
                        }
                    });
                    
                    
                });
                function getRecordlist(searchFromDate,searchToDate,searchOfficerName){
                            //alert(searchFromDate+"    "+searchToDate);
                            //var emp_code= doc.getElementById("emp_code1"+id).value;
                            //var queryString = "task1=viewImage&emp_code="+emp_code;
                            var queryString = "task1=generateReport&searchFromDate="+searchFromDate+"&searchToDate="+searchToDate+"&searchOfficerName="+searchOfficerName;
                             
                            // alert(queryString);
                            var url = "MISCont.do?" + queryString;
                            popupwin = openPopUp(url, "Show Image", 600, 900);
                        }
                        function sendMail(){
                           var searchFromDate= document.getElementById("searchFromDate").value;
                           var searchToDate= document.getElementById("searchToDate").value;
                            var searchOfficerName=document.getElementById("searchOfficerName").value;
                           
                            //alert(searchFromDate+"  "+searchToDate);
                            //var emp_code= doc.getElementById("emp_code1"+id).value;
                            //var queryString = "task1=viewImage&emp_code="+emp_code;
                            //var queryString = "task1=generateReport&searchFromDate="+searchFromDate+"&searchToDate="+searchToDate;
                            // alert(queryString);
                            //var url = "MISCont.do?" + queryString;
                            //popupwin = openPopUp(url, "Show Image", 600, 900);
                            
                            
                            var myData=searchFromDate+" "+searchToDate+" "+searchOfficerName;
                                $.ajax({url: "MISCont.do?action1=sendReportMail", data: "action2="+ myData +"&q=", success: function(response_data) {
                                  var result=response_data.trim();
                                  
                                  //alert(result);
                            }               
                             });
                            
                            
                            
                            
                        }
                        function openPopUp(url, window_name, popup_height, popup_width) {
                            var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                            var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                            var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                            return window.open(url, window_name, window_features);
                            
                            }
            </script>
            
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            
            <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%">MIS Page</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td></tr>

            <tr>
                                    <td  align="center">
                                        <form name="form0" method="post" action="MISCont.do">
                                            <table  align="center"  class="heading1">
                                                <tr>               
                                                </tr>
                                                <tr>
                                                    <td>
                                                       Officer Name
                                                        <input class="input new_input" type="text" id="searchOfficerName" name="searchOfficerName" value="${searchOfficerName}" size="20" >
                                                       
                                                        From Date
                                                        <input class="input" type="text" id="searchFromDate" name="searchFromDate" value="${searchFromDate}" maxlength="10" size="15" >
                                                        To Date
                                                        <input class="input" type="text" id="searchToDate" name="searchToDate" value="${searchToDate}" maxlength="10" size="15" >
                                                    </td></tr>
                                                <tr>
                                                    <td align="center">
                                                        <%--Key Person
                                                        <input class="input" type="text" id="searchKeyPerson" name="searchKeyPerson" value="${searchKeyPerson}" size="25" >--%>

                                                        <%--<input class="input" type="text" id="searchTransactionDate" name="searchTransactionDate" value="${searchTransactionDate}" size="20" >--%>
                                                        <input class="button" type="submit" name="task1" id="search" value=" Search">
                                                        <input class="button" type="submit" name="task1" id="showAllRecords" value="Show All Records">

                                                        <input type="button" id="view_pdf" class="pdf_button" name="view_pdf" value="" onclick="getRecordlist('${searchFromDate}','${searchToDate}','${searchOfficerName}')">
                                                        <input type="button" id="send_mail" class="" name="send_mail" value="sendEmail" onclick="sendMail()">
                                                        <!--                                                        <input type="button" id="view_excel" class="button" name="view_excel" value="Excel" onclick="getRecordlist(id)">-->
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <div class="content_div" style="max-height: 375px">
                                            <form name="form1" action="userEntryByImageCont.do" method="post">
                                                <table id="table1" border="1" align="center" class="content" width="980">
                                                    <tr align="center">
                                                        <th class="heading">S.No</th>

                                                        <th class="heading" style="white-space: normal">Officer Name</th>
                                                        <th class="heading" style="white-space: normal">129/177</th>
                                                        <th class="heading" style="white-space: normal">128/177</th>
                                                        <th class="heading" style="white-space: normal">39/192</th>
                                                        <th class="heading" style="white-space: normal">56/192</th>
                                                        <th class="heading" style="white-space: normal">66/192</th>
                                                        <th class="heading" style="white-space: normal">146/196</th>
                                                        <th class="heading" style="white-space: normal">77/177</th>
                                                        <th class="heading" style="white-space: normal">3/181</th>
                                                        <th class="heading" style="white-space: normal">117/177</th>
                                                        <th class="heading" style="white-space: normal">122/177</th>
                                                        <th class="heading" style="white-space: normal">100/177</th>
                                                        <th class="heading" style="white-space: normal">125/177</th>
                                                        <th class="heading" style="white-space: normal">21(25)/177</th>
                                                        <th class="heading" style="white-space: normal">119/177</th>
                                                        <th class="heading" style="white-space: normal">81/177</th>
                                                        <th class="heading" style="white-space: normal">185</th>
                                                        <th class="heading" style="white-space: normal">93(8)/177</th>
                                                         <th class="heading" style="white-space: normal">Others</th>
                                                        <th class="heading" style="white-space: normal">Total</th>

                                                    </tr>
                                                    <c:forEach var="tp" items="${requestScope['list']}" varStatus="loopCounter">
                                                        <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td> 
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="" width="90%">${tp.key_person_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean129_177}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean128_177}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean39_192}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean56_192}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean66_192}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean146_196}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean77_177}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean3_181}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean117_177}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean122_177}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean100_177}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean125_177}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean21_25_177}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean119_177}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean81_177}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean185}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean93_8_177}</td>
                                                             <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.others_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.person_challan_count}</td>
                                                        </tr>        
                                                    </c:forEach>
                                                        <c:forEach var="tp" items="${requestScope['count_list']}" varStatus="loopCounter">
                                                        <tr>
                                                            <td colspan="2" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">Total</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean129_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean128_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean39_192_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean56_192_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean66_192_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean146_196_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean77_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean3_181_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean117_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean122_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean100_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean125_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean21_25_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean119_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean81_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean185_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.bean93_8_177_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.others_count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.total}</td>
                                                        </tr>
                                                        </c:forEach>

<!--                                                    <tr>
                                                        <td align='center' colspan="13">
                                                            <c:choose>
                                                                <c:when test="${showFirst eq 'false'}">
                                                                    <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input class="button"type='submit' name='buttonAction' value='First'>
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
                                                    </tr>-->
                                                </table>
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden"  name="searchOfficerName" value="${searchOfficerName}" >
                                                <input type="hidden" id="searchFromDate" name="searchFromDate" value="${searchFromDate}">
                                                <input type="hidden" id="searchToDate" name="searchToDate" value="${searchToDate}">
                                            </form>
                                        </div>
                                    </td>
                                </tr>

                        <input type="hidden" id="lowerLimitBottom" name="lowerLimit" value="${lowerLimit}">
                                <input type="hidden" id="noOfRowsTraversedTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                <input type="hidden" id="clickedButton" value="">
                                <input type="hidden"  name="searchOfficerName" value="${searchOfficerName}" >
                                
<!--                    </table>-->

<!--                </DIV>-->
<!--            </td>-->
            
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
        
    </body>
</html>

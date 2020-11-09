<%-- 
    Document   : trafficpolice_popupView
    Created on : Jul 21, 2015, 1:53:16 PM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Get Offence Code</title>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <link rel="stylesheet" type="text/css" href="css/calendar.css" >
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/calendar.js"></script>
        <script type="text/javascript" language="javascript">     
            function submitForm(offence_code, offence_type, penalty_amount){//debugger;
                var count = $("#count").val();
//                opener.document.getElementById("offence_code"+count).value = offence_code;
                /////////////////////////////////////////////////////////////////////////////////////////////////
               
               var vehicle_no_state = opener.document.getElementById("vehicle_no_state").value;
               var vehicle_no_city = opener.document.getElementById("vehicle_no_city").value;
               var vehicle_no_series = opener.document.getElementById("vehicle_no_series").value;
               var vehicle_no = opener.document.getElementById("vehicle_no").value;
                $.ajax({
                    dataType: "json",
                    async : false,
                    url: "userEntryByImageCont.do?task2=checkSecondOffencePenaltyAmount&vehicle_no_state="+vehicle_no_state+"&vehicle_no_city="+vehicle_no_city+"&vehicle_no_series="+vehicle_no_series+"&vehicle_no="+vehicle_no+"&offence_code="+offence_code,
                    success: function(response_data) {
                        var arr = response_data.dateTime;
                       var final_string=arr[0]["myString"];
//                        var s = final_string.split(",");
                       //alert(final_string);
                       var secondOffenceStatus = "";
                        
                       if(final_string.length > 0 ){
                           opener.document.getElementById("penalty_amount"+count).value = final_string;
                           secondOffenceStatus="_YES";
                           offence_code = offence_code+secondOffenceStatus;
                       }else{
                           opener.document.getElementById("penalty_amount"+count).value = penalty_amount;
                           secondOffenceStatus="_NO";
                           offence_code = offence_code+secondOffenceStatus;
                       }

                       // alert("This vehicle is already caught for traffic rule violence on "+city_location+" at "+dateTime);
                       opener.document.getElementById("offence_code"+count).value = offence_code;
                    }
                });
                
                
                
                /////////////////////////////////////////////////////////////////////////////////////////////////////
//                opener.document.getElementById("penalty_amount"+count).value = penalty_amount;
                opener.document.getElementById("offence_type"+count).value = offence_type;
                var total_amount = 0;
                for(var i = 0; i <= count; i++){
                    var val = opener.document.getElementById("penalty_amount"+i).value;
                    total_amount = total_amount + parseFloat(val);
                }
                if(total_amount >= 0);
                else
                    total_amount = 0;
                opener.document.getElementById("deposited_amount").value = total_amount;
                window.close();
            }
        </script>

    </head>
    <body>
        <DIV id="body" class="maindiv">
            <table width="100%">
                <tr>
                    <td align="center" >
                        <table width="700" class="header_table">
                            <tr>
                                <td  align="center"    >
                                    Get Offence Code <%--Items Page For ${param.estimate_for}--%>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <%--<td align="center">
                        <form name="form1"  id="form1"  action="getEstimateItemsPopupCont"method="post">
                            <table id="searchTable0" class="content"  border="1" align="center" width="700"style="height:auto">
                                <tr align="center">
                                    <td>
                                        <b>Rate Type</b>

                                        <select name="search_rate_type" id="search_rate_type"  onchange="showHideItemAlready()">
                                            <option value="rate contract" ${search_rate_type eq 'rate contract' ? 'selected' : ''}>Rate Contract</option>
                                            <option value="item rate" ${search_rate_type eq 'item rate' ? 'selected' : ''}>Item Rate</option>
                                            <option value="work rate" ${search_rate_type eq 'work rate' ? 'selected' : ''}>Work Rate</option>
                                        </select>

                                        <b>Search Item</b>
                                        <%--   <td>
                                            <select name="search_item_type" id="search_item_type"  onchange="ChangeRateType()">

                                                            <option value="SOR"<c:if test="${search_item_type eq 'SOR'}">selected</c:if>>SOR</option>
                                                            <option value="Non SOR"<c:if test="${search_item_type eq 'Non SOR'}">selected</c:if>>Non SOR</option>
                                                        </select>
                                                    </td> --%>

                                        <%--<select name="search_item_type" id="search_item_type"  onchange="ChangeRateType()">
                                            <option value="Non SOR"<c:if test="${search_item_type eq 'Non SOR'}">selected</c:if>>Non SOR</option>
                                            <option value="SOR"<c:if test="${search_item_type eq 'SOR'}">selected</c:if>>SOR</option>
                                        </select>

                                        <b class="title_item_already">Item Already</b>
                                        <select name="item_already" id="item_already" class="title_item_already"  onchange="ChangeRateType();">
                                            <option value="N" <c:if test="${isItemApproved eq 'N'}">selected</c:if>>Not Approved</option>
                                            <option value="Y" <c:if test="${isItemApproved eq 'Y'}">selected</c:if>>Approved</option>
                                        </select>

                                        <b class="item_new title_item_already">Re-Assign Supplier</b>
                                        <input type="checkbox" class="item_new title_item_already"  name="disapproveSuppliers"id="disapproveSuppliers"value="${disapproveSuppliers}" onclick="singleCheckUncheckForNew(id)" onchange="emptySelectedItems()"/>

                                        <input type="radio" id="search_by_serial"name="search_by" value="search_by_serial_no" onclick="selectSearchMode(id)" style="display: none" checked>
                                        <input type="radio" id="search_by_hierarchy"name="search_by"value="search_by_hierarchy"onclick="selectSearchMode(id);" style="display:none">
                                        <input type="hidden" id="searchBy"value="${searchBy}">

                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div id="searchBy"style="width: auto; height: 30px;">
                                            <div id="searchBySerial" style="position:absolute;visibility: visible;height:30px">
                                                <table id="searchBySerialTable"class="content"align="center"width="700" style="height:auto">
                                                    <tr id="searchBySerialRow0" >
                                                        <th style="border: none" class="heading"width="100">Enter Serial No:</th>
                                                        <td style="border: none" width="100"><input class="input"type="text" id="searchBySerialInput"name="searchBySerialInput" value="${serialNo}" style="width:100%"></td>
                                                        <th style="border: none" class="heading"width="100">Item  Name: </th>
                                                        <td style="border: none" width="100">
                                                            <input type="text" id="search_item_name" name="search_item_name" value="${search_item_name} " style="width:100%">
                                                        </td>
                                                        <td style="border: none" width="200">
                                                            <input type="hidden" id="item_name_value" name="item_name_value"  value="${item_name_value}">
<!--                                                            <input type="hidden" id="work_name"name="work_name" value="${workName}">-->
                                                            <input type="hidden" id="esNo" name="esNo" value="${esNo}">
                                                            <input type="hidden" id="erNo" name="erNo" value="${erNo}">
                                                            <input type="hidden" id="estimate_for" name="estimate_for" value=" ${param.estimate_for}">
                                                            <input class="button"type="submit" id="task"name="task"value="Search" onclick="assignCheckBoxValue();" >
                                                            <input class="button"type="button" id="PDF"name="PDF"value="PDF" onclick="GetItemPDF()" >
                                                            <input type="hidden" name="totalSelectedItemValue" id="totalSelectedItemValue" value="${totalSelectedItemValue}">
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div id="searchByHierarchy" style="position: absolute;visibility: hidden">
                                                <table id="searchByHierarchyTable"class="content"align="center"width="700"style="height:auto" >
                                                    <tr id="searchByhierarchyRow0" style="border: none">
                                                        <td>
                                                            <table id="insertSearchTable" align="center" width="700" class="heading1" style="height:auto">
                                                                <tr id="searchItemRow0"  style="border: none">

                                                                    <th style="border: none"class="heading"width="100">Item: </th>
                                                                    <td style="border: none"width="400"><select class="dropdown" id="searchItem0" name="searchItem0" onchange="getItemByHierarchy(id)"style="width:400px"  >
                                                                            <option value="0" style="color: red" selected>Select Item</option>
                                                                            <c:forEach var="sc" items="${searchItem}">
                                                                                <option value="${sc.key}">${sc.value}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </td>
                                                                    <td width="200"  style="border: none"><input class="button" type="submit" name="task" id="searchItemByHierarchy" value="Search" >
                                                                        <input class="button" type="hidden" name="searchItemByHierarchyLevel" id="searchItemByHierarchyLevel" value="" >
                                                                    </td>

                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    </td>


                                </tr>

                                <tr id="message">
                                    <c:if test="${not empty message}">
                                        <td colspan="2" bgcolor="${msgBgColor}"><b>${message}</b></td>
                                    </c:if>
                                </tr>
                                <tr>
                                    <td colspan="2" align="center">
                                        <!-- <div id="search_item_table"  style="visibility: hidden;height: 0px ;margin-bottom: 10px">-->
                                        <div id="search_item_table"  style="visibility: hidden;height:auto">
                                            <table id="search_item_data" width="600" >
                                                <tr><th class="heading">S.No</th>
                                                    <th class="heading" style="width: 10%;white-space: normal">Item Name</th>
                                                </tr>

                                                <%--  <tr>
                                               <td id="innner" colspan="2" style="background:#777777">${item_name_value}</td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </td>--%>
                </tr>
                <tr>
                    <td  align="center">
                        <div  id="form0div" style="visibility:visible">
                            <form name="form0" id="form0" method="post" action="">
                                <table align="center"  width="700">
                                    <tr>
                                        <td align="center">
                                            <table  class="content" width="700">
                                                <tr><th class="heading" width="10%">S.No</th>
                                                    <th class="heading" width="10%" style="white-space: normal">Offence Code</th>
                                                    <th class="heading" width="90%">Act</th>
                                                    <th class="heading" width="90%" style="width: 10%">Offence Type</th>
                                                    <th class="heading" width="10%" style="white-space: normal">Vehicle Type</th>
                                                    <th class="heading" width="10%" style="white-space: normal">Is Commercial</th>
                                                    <th class="heading" width="10%" style="white-space: normal">Penalty Amount</th>
                                                </tr>
                                                <c:forEach var="ofncList" items="${requestScope['OffenceList']}" varStatus="loopCounter">
                                                    <tr id="searach_data_row${loopCounter.count}">
                                                        <td align="center">${loopCounter.count}<input name="offence_code" type="radio" onclick="submitForm('${ofncList.offence_code}', '${ofncList.offence_type}', '${ofncList.penalty_amount}')" value="${ofncList.offence_code}"></td>
                                                        <td align="center" style="white-space: normal; width:10%">${ofncList.offence_code}</td>
                                                        <td align="center">${ofncList.act}</td>
                                                        <td align="center" style="white-space: normal; width:100%">${ofncList.offence_type}</td>
                                                        <td align="center" style="white-space: normal; width: 10%">${ofncList.vehicle_type}</td>
                                                        <td align="center" style=" width: 5%">${ofncList.is_commercial}</td>
                                                        <td align="center" style="white-space: normal; width: 10%">${ofncList.penalty_amount}</td>
                                                    </tr>
                                                </c:forEach>
                                                <%--  <tr>
                                               <td id="innner" colspan="2" style="background:#777777">${item_name_value}</td>
                                                </tr>--%>
                                                <input type="hidden" id="estimate_for0" name="estimate_for" value=" ${param.estimate_for}">
                                                <input type="hidden" id="count" name="count" value="${count}">
                                            </table>
                                        </td>
                                    </tr>
                                    <%--<c:if test="${not empty itemDetailList}">
                                        <tr><td>&nbsp;</td></tr>
                                        <tr>
                                            <td align="center" width="100%">
                                                <DIV  style="max-height: 300px;vertical-align: top;width: 730px" class="content_div">
                                                    <table  id="selectTable" align="center" width="700" class="content" style="border:none">
                                                        <tr >
                                                            <th class="heading">S.No</th>
                                                            <th class="heading">Item Name</th>
                                                            <th class="heading">Rate</th>
                                                            <th class="heading">Item Unit</th>
                                                        </tr>
                                                        <c:forEach var="list" items="${requestScope['itemDetailList']}" varStatus="loopCounter">
                                                            <tr bgcolor="${list.heading}">
                                                                <%-- <tr bgcolor="${list.color_code eq 1?'#aaaaaa':${list.color_code eq 2?'#94AA74':${list.color_code eq 2?'#b9c9fe':''}}}" >--!>
                                                                <td id="" align="center">${list.serial_no}
                                                                    <input type="${list.checked ?'hidden':'checkbox'}" id="check_heading${loopCounter.count}" onclick="headerCheckUncheck(${loopCounter.count})">
                                                                    <input type="hidden" id="heading_key${loopCounter.count}" value="${list.headingKey}">
                                                                    <input type="hidden" id="isValidRow${loopCounter.count}" name="isValidRow" value="${list.checked eq 'true' && list.isExist eq 'N' ? 'Yes' : 'No'}">
                                                                    <c:if test="${list.checked eq 'true' && list.isExist eq 'N'}">
                                                                        <input type="hidden"name="item_id" id="item_id${loopCounter.count}" value="${list.item_id}">
                                                                        <input type="hidden"name="item_rate"value="${list.item_rate}">
                                                                        <input type="hidden" name="check_id" id="check_id${loopCounter.count}"value="0">
                                                                        <input type="checkbox" name="check_print"id="check_print${loopCounter.count}"value="Yes"onclick="singleCheckUncheck(${loopCounter.count})" ${list.checked?'':'disabled'}></td>
                                                                    </c:if>
                                                                <td id="item_name${loopCounter.count}" align="center" style="white-space: normal"width="50%">${list.item_name}</td>
                                                                <%--                                                    <td id="item_remark${loopCounter.count}" align="center">${list.item_description}</td>--!>
                                                                <td id="rate${loopCounter.count}" align="center">${list.item_rate}</td>
                                                                <td id="item_unit${loopCounter.count}" align="center">${list.item_unit}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </table>
                                                </DIV>
                                            </td>
                                        </tr>
                                        <tr> <td align="center" id="showMsg"></td></tr>
                                        <tr>
                                            <th align="center" class="heading">
                                                <input type="button"  class="button"  name="checkUncheckAll" id="checkUncheckAll" value="Check All" onclick="checkUncheck(value)"/>
                                                <%--  <input type="button"class="button" id="task" name="task"value="Save" onclick="addItemToTemp()"> --!>
                                                <input type="button"class="button" id="task" name="task"value="Add" onclick="addItemToEstimate()">
                                                <input type="button"  class="button"  name="Cancel" value="Cancel" onclick="deleteRow();">
                                            </th>
                                        </tr>
                                    </c:if>
                                    <tr><td>&nbsp;</td></tr>
                                    <c:if test="${not empty previousItemDetailList}">
                                        <tr><td class="header_table" align="center"> Previous Selected Items </td></tr>
                                        <tr>
                                            <td align="center" width="90%">
                                                <DIV  style="max-height: 300px;vertical-align: top;width: 680px" class="content_div">
                                                    <table  id="previousSelectTable" align="center" width="650" class="content" style="border:none; background-color: lightgreen">
                                                        <tr >
                                                            <th class="heading">S.No</th>
                                                            <th class="heading">Item Name</th>
                                                            <th class="heading">Rate</th>
                                                            <th class="heading">Item Unit</th>
                                                        </tr>
                                                        <c:forEach var="list" items="${requestScope['previousItemDetailList']}" varStatus="loopCounter">
                                                            <tr>
                                                                <%-- <tr bgcolor="${list.color_code eq 1?'#aaaaaa':${list.color_code eq 2?'#94AA74':${list.color_code eq 2?'#b9c9fe':''}}}" >--!>
                                                                <td id="" align="center">${list.serial_no}
                                                                    <input type="hidden"name="item_id" id="previous_item_id${loopCounter.count}" value="${list.item_id}">
                                                                    <input type="hidden"name="item_rate"value="${list.item_rate}">
                                                                    <input type="hidden" name="check_id"id="previous_check_id${loopCounter.count}"value="1">
                                                                    <input type="checkbox"name="previous_check_print"id="previous_check_print${loopCounter.count}"value="Yes"onclick="previousSingleCheckUncheck(${loopCounter.count})" checked  ></td>
                                                                <td id="item_name${loopCounter.count}" align="center" style="white-space: normal"width="50%">${list.item_name}</td>
                                                                <%--                                                    <td id="item_remark${loopCounter.count}" align="center">${list.item_description}</td>--!>
                                                                <td id="rate${loopCounter.count}" align="center">${list.item_rate}</td>
                                                                <td id="item_unit${loopCounter.count}" align="center">${list.item_unit}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </table>
                                                </DIV>
                                            </td>
                                        </tr>
                                    </c:if>--%>

                                </table>
                            </form>
                        </div>
                    </td>
                </tr>
            </table>
        </DIV>
    </body>
</html>

<%-- 
    Document   : trafficePoliceReceiptView
    Created on : Jul 29, 2015, 12:11:57 PM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="content/css/bootstrap.min.css">
        <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>-->
        <script src="content/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="style/style1.css">
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
            var doc = document;
            jQuery(function(){
                $("#receipt_book_no").autocomplete("receiptBookCont.do", {
                    extraParams: {
                        action1: function() { return "getReceiptBookNo";}
                    }
                });
//                $("#Offence_code0").autocomplete("receiptBookCont.do", {
//                    extraParams: {
//                        action1: function() { return "getOffenderLicenseNoSearchList";}
//                    }
//                });
                $("#receipt_no").autocomplete("receiptBookCont.do", {
                    extraParams: {
                        action1: function() { return "getReceiptNo"},
                        receipt_book_no: function(){return doc.getElementById("receipt_book_no").value ;}
                    }
                });

                $("#officer_name").autocomplete("receiptBookCont.do", {
                    extraParams: {
                        action1: function() { return "getOfficerNameList";}
                    }
                });

                $("#challan_book_no").autocomplete("receiptBookCont.do", {
                    extraParams: {
                        action1: function() { return "getOfficerBooklist";},
                        action2: function() { return "";}
                    }
                });

                $("#challan_no").autocomplete("receiptBookCont.do", {
                    extraParams: {
                        action1: function() { return "getChallanNo";},
                        action2: function() { return $("#challan_book_no").val();}
                    }
                });

                $("#offence_zone").autocomplete("receiptBookCont.do", {
                    extraParams: {
                        action1: function() { return "getZonelist";},
                        action2: function() { return $("#offence_city").val();}
                    }
                });

                $("#offence_location").autocomplete("receiptBookCont.do", {
                    extraParams: {
                        action1: function() { return "getLocationlist";},
                        action2: function() { return $("#offence_zone").val();}
                    }
                });


                $("#receipt_book_no").result(function(event, data, formatted){
                    $.ajax({url: "receiptBookCont.do?action1=getBookRevision", data: "action2="+ data +"&q=", success: function(response_data) {
                        ///var id = response_data.split("_")[0];
                        //var name = response_data.split("_")[1];
                        //var i;                       
                        $("#receipt_book_revision").val(response_data.trim());
                    }
                });
                });


            });
            $(doc).ready(function(){//debugger;
                if($("#menuid1").val() != "1"){
                    $("#menuid").hide();
                    $("#offence_td").attr("align", "left");
                }else{
                    $("#offence_td").attr("align", "center");
                }
                //if($("#noOfRows").val() > 0){
                //    $("#resultTable").show();
                //}

               var offender_license_no = $("#offender_license_no").val();
               if(offender_license_no != ""){
                   $("#is_owner").hide();
                   $("#is_owner_license").attr("disabled", true);
                   $("#is_not_owner_license").attr("disabled", true);
               }

            });
            
//if (typeof(GpsGate) == 'undefined' || typeof(GpsGate.Client) == 'undefined')
//	{
//		alert('GpsGate not installed or not started!');
//	}else
//            alert('GpsGate started');
            

            if ( navigator.geolocation )
            {//debugger;
                navigator.geolocation.getCurrentPosition(handlePosition);
                //alert("if");
            }else
                alert("else");

            function handlePosition(pos)
            {//debugger;
             //alert(pos);
             //alert("Your location is: " + pos.coords.latitude + ', ' + pos.coords.longitude + ', ' + pos.timestamp);// + ', ' + pos.coords.altitude + ', ' + pos.coords.altitudeAccuracy + ', ' + pos.coords.accuracy
             //var d = new Date(pos.trackPoint.utc);
             //alert(d);
             var timestamp = pos.timestamp;
             $("#latitude").val(pos.coords.latitude);
             $("#longitude").val(pos.coords.longitude);
             $("#timestamp").val(timestamp);
             var d = new Date(timestamp);
             $("#date_time").val(d);
             //alert(d);
            }

            /*$("#searchOffenderLicenseNo").click(function(){
                alert(1);

            });*/

            function verify(){debugger;
                var result = true;
                var receipt_book_no = $("#receipt_book_no").val().trim();
                if(receipt_book_no == "" && receipt_book_no == ""){
                     $("#message").html("<td colspan='6' bgcolor='coral'><b> Receipt Book No. is required...</b></td>");
                     doc.getElementById("receipt_book_no").focus();
                     return false;
                }    
                if($("#receipt_no").val().trim() == "" ){
                     $("#message").html("<td colspan='6' bgcolor='coral'><b> Receipt No is required...</b></td>");
                     doc.getElementById("receipt_no").focus();
                     return false;
                }
                if($("#receipt_table_body").html().trim() == "" ){
                     $("#message").html("<td colspan='6' bgcolor='coral'><b>Challan Book No. and Challan No. is required...</b></td>");
                     doc.getElementById("challan_book_no").focus();
                     return false;
                }
//                result = IsNumeric("book_no");
//                result = IsNumeric("deposit_amount");
                if ( navigator.geolocation ){//debugger;
                    navigator.geolocation.getCurrentPosition(handlePosition);
                    //alert("if");
                }//else
                     //alert("else");

                return result;
            }

            function makeEditable(id) {
            if($("#searchOffenderLicenseNo").val() != "" || $("#searchVehicleNo").val() != ""){
                //doc.getElementById("Offence_code").disabled = false;
                doc.getElementById("officer_name").disabled = false;
                doc.getElementById("deposit_amount").disabled = false;
                doc.getElementById("no_of_offence").disabled = false;
                doc.getElementById("is_owner_license").disabled = false;
                doc.getElementById("is_not_owner_license").disabled = false;
                //doc.getElementById("jarayam_no").disabled = false;
                doc.getElementById("offender_name").disabled = false;
                doc.getElementById("offence_city").disabled = false;
                doc.getElementById("offence_zone").disabled = false;
                doc.getElementById("offence_location").disabled = false;
                doc.getElementById("book_no").disabled = false;
                doc.getElementById("receipt_no").disabled = false;
                doc.getElementById("offender_license_no").disabled = false;
                doc.getElementById("vehicle_no").disabled = false;
                doc.getElementById("select_image").disabled = false;

                if(id == 'new') {
                    $("#message").html("");
                    doc.getElementById("no_of_offence").value = 1;
                    showOffenceCodeDiv();
                    //doc.getElementById("edit").disabled = true;
                    //doc.getElementById("delete").disabled = true;
                    //doc.getElementById("save_As").disabled = true;
                    //setDefaultColordOrgn(doc.getElementById("noOfRowsTraversed").value, 19);
                    //doc.getElementById("office_type").focus();
                }
                if(id == 'edit'){
                    doc.getElementById("delete").disabled = false;
                    doc.getElementById("save_As").disabled = false;
                    // doc.getElementById("office_type").focus();
                }
                doc.getElementById("save").disabled = false;

            }else{
                alert("FIrstly Search Offender License No. Or Vehicle No.");
            }
            }

    function showOffenceCodeDiv(){//debugger;
        var no_of_offence = $("#no_of_offence").val();
        $("#offence_code_div").html("<label for='Offence_code'>Offence Code </label><br>");
        var br = "";
        for(var i = 0; i < no_of_offence; i++){
            if(br == ""){
                br = i + 3;
            }else if(i >= br){
                $("#offence_code_div").append("<br>");
                br = i + 3;
            }
            $("#offence_code_div").append("<label for='Offence_code"+i+"'>"+(i+1)+" </label>\n\
            <input type='text' class='ac_input' id='Offence_code"+i+"' name='Offence_code"+i+"' onblur='' size='4' value=''>\n\
            <input type='hidden' id='hdn_Offence_code"+i+"' name='hdn_amount"+i+"' size='4' value='0'>");
            jQuery(function(){
                    $("#Offence_code"+i).autocomplete("receiptBookCont.do", {
                        extraParams: {
                            action1: function() { return "getOffenceCodeList";}
                        }
                    });
                });

                 $("#Offence_code"+i).result(function(event, data, formatted){//debugger;
                     var id =this.id;
                    $.ajax({url: "receiptBookCont.do?action1=getPenaltyAmount", data: "action2="+ data +"&q=", success: function(response_data) {
                        var p_amount = response_data.trim();
                        if(p_amount == 100000.0){
                            p_amount = 0;
                            $("#spn_deposit_amount").html(" + Court");
                        }else
                            $("#spn_deposit_amount").html("");
                        doc.getElementById("hdn_" + id).value = p_amount;                        
                        getTotalPenaltyAmount();                        
                    }
                });
                });
        }
//        <label for="Offence_code">Offence Code </label>
//        <input class="ac_input" type="hidden" id="status_type_id" name="status_type_id" value="" >
//        <input type="text" class="ac_input" id="Offence_code" name="Offence_code" size="4" value="" disabled>

    }

    function getTotalPenaltyAmount(){//debugger;
        var no_of_offence = $("#no_of_offence").val();
        $("#deposit_amount").val(0);
        for(var i = 0; i < no_of_offence; i++){
            var hdn_value = $("#hdn_Offence_code"+i).val();
            var tot_amount = $("#deposit_amount").val();
            var total;
            if(hdn_value != undefined)
                total = parseFloat(hdn_value) + parseFloat(tot_amount);
            else
                total = tot_amount;
            $("#deposit_amount").val(total);
        }
    }

    function selectSearchField(id){
        var radio_val = $("#"+id).val();
        if(radio_val == "License"){
            $("#license_no_div").show();
            $("#vehicle_no_div").hide();
            $("#searchVehicleNo").val("");
        }else{
            $("#license_no_div").hide();
            $("#searchOffenderLicenseNo").val("");
            $("#vehicle_no_div").show();
        }
    }

    function getVehicleLicenseValue(){
        if($("#searchVehicleNo").val() == "")
            $("#license_vehicle").val( $("#searchOffenderLicenseNo").val() + "_L");
        else
            $("#license_vehicle").val($("#searchVehicleNo").val() + "_V");
    }

    function checkImage(id){
        alert(id);
        alert($("#"+id).val());
    }


    function IsNumeric(id) {
                        var strString = doc.getElementById(id).value;
                        var strValidChars = "0123456789";
                        var strChar;
                        var blnResult = true;
                        if (strString.length == 0) return false;
                        for (i = 0; i < strString.length && blnResult == true; i++)
                        {
                            strChar = strString.charAt(i);
                            if (strValidChars.indexOf(strChar) == -1)
                            {
                                doc.getElementById(id).value="";
                                $("#message").html("<td colspan='6' bgcolor='coral'><b>Value should be Numeric...</b></td>");
                                doc.getElementById(id).value = "";
                                doc.getElementById(id).focus();
                                blnResult = false;
                            }else
                                $("#message").html("");
                            ////else{
//                                var  time = doc.getElementById(id).value;
//                                if(id=="transaction_time_hour"){
//                                    if(time>12){
//                                        doc.getElementById(id).value="";
//                                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Hour should Be less than 12</b></td> ");
//                                        return;
//                                    }
//                                }else{
//                                    if(time>59){
//                                        doc.getElementById(id).value="";
//                                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Min should Be less than 60</b></td>");
//                                        return;
//                                    }
//
//                                    $("#message").html("");
//                                }
//                            }
                        }
                        return blnResult;
                    }
                    var map = new Map();

                    function getAmount(book_no, challan_no){//debugger;
                        var result;
                        result = $.ajax({
                            url: "receiptBookCont.do",
                            data: "action1=getChallanAmount&book_no=" + book_no + "&challan_no="+ challan_no,
                            success: function(response){
                                result = response.trim();
                                $("#challan_book_hd").val(result);
                                var res = result.split("_");
                                var tp_id = res[0];
                                var amount = res[1];                                
                            }                        
                        });
                         return result;
//                        $.ajax({url: "trafficPoliceSearchCont.do?action1=getPenaltyAmount", data: "action2="+ data +"&q=", success: function(response_data) {
//                        var p_amount = response_data.trim();
//                        if(p_amount == 100000.0){
//                            p_amount = 0;
//                            $("#spn_deposit_amount").html(" + Court");
//                        }else
//                            $("#spn_deposit_amount").html("");
//                        doc.getElementById("hdn_" + id).value = p_amount;
//                        getTotalPenaltyAmount();
//                    }
//                });
                    }

                    function addReceiptRow(){
                        var i = 0;
                        var total_amount = 0;
                        $("#receipt_table_body").html("");
                        for (var [key, value] of map.entries()) {
                            //console.log(key + " = " + value + "  " + map.size);
                            var k = key.split("_");
                            var v = (value+"").split("_");
                            i++ ;
                            var id = key + "_" + value;
                            total_amount += parseFloat(v[1]);
                            var add_row = "<tr><td><input type='checkbox' id = 'ch_"+i+"' name='challan_book' value='"+ id +"' checked> "+ i +" </td><td>" + k[0] + "</td><td>" + k[1] + "</td><td>" + v[1] + "</td></tr>";
                            $("#receipt_table_body").html($("#receipt_table_body").html()+add_row);
                        }
                        if(total_amount == "Nan")
                            total_amount = 0;
                        var gt = "<tr><td colspan = '3'>Total Amount</td><td>"+ total_amount +"</td></tr>"
                        $("#receipt_table_body").html($("#receipt_table_body").html()+gt);
                    }

                    $(doc).ready(function(){
                        $("#add_challan").click(function(){
                            var challan_book_no = $("#challan_book_no").val();
                            var challan_no = $("#challan_no").val();debugger;
                            if(challan_book_no != "" && challan_no != ""){
                                var amount;
                                $.ajax({
                                    url: "receiptBookCont.do",
                                    data: "action1=getChallanAmount&book_no=" + challan_book_no + "&challan_no="+ challan_no,
                                    success: function(response){debugger;
                                        result = response.trim();
                                        amount = result;
                                        $("#challan_book_hd").val(result);
                                        var res = result.split("_");
                                        var tp_id = res[0];
                                        amount = res[1];
                                        map.set(challan_book_no + "_" + challan_no, result);
                                        addReceiptRow();
                                    }
                                });
                           // amount = getAmount(challan_book_no, challan_no) ;
//                            if (amount == undefined)
//                                amount = $("#challan_book_hd").val();
                           // map.set(challan_book_no + "_" + challan_no, amount);
                            //var mapSize = map.size;
                            //addReceiptRow();
                            }
                        });
                    });

        </script>
        <title>Traffic Offence New Record</title>
    </head>
    <body>
        <div class="container" align="center">
            <table id="menushow" style="width: 100%" class="main" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td><%@include file="/layout/header.jsp" %></td>
                </tr>

                <tr>
                    <td><%@include file="/layout/menu.jsp" %></td>
                </tr>
                <tr></tr>
            </table>
                <a href="trafficPoliceSearchCont.do?menuid=${menuid}" >Search</a>&nbsp;&nbsp;&nbsp;
                <a href="trafficPoliceSearchCont.do?task=new&menuid=${menuid}" >New</a>&nbsp;&nbsp;&nbsp;
                <a href="?task=newReceipt&menuid=${menuid}" >Receipt</a>&nbsp;&nbsp;&nbsp;
                <a href="receiptBookCont.do?menuid=${menuid}" >Receipt View</a>
            <h1>Receipt Book</h1>
            <%-- <table align="center" border="1">--%>
                    <div class="form-group jumbotron table-responsive" >
                        <form id="" role="form" action="receiptBookCont.do" method="post" onsubmit="return verify()"><!-- enctype="multipart/form-data"-->
                            <table  id="table2" class="table table-condensed " border="0"  align="center" width="600">
                               <tr id="message">
                                      <c:if test="${not empty message}">
                                          <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                      </c:if>
                               </tr>
                                <tr align="">                                 

                                    <td id="offence_td" align="">
                                        <div class="form-group" id="" align="center">
                                            <label for="receipt_book_no">Receipt Book No.</label>
                                            <input type="text" class="ac_input" id="receipt_book_no" value="" onkeypress="IsNumeric(id)" onkeyup="IsNumeric(id)" onclick="IsNumeric(id)" onblur="IsNumeric(id)" name="receipt_book_no">
                                            <input type="hidden" id="receipt_book_revision" value="" name="receipt_book_revision">
                                            <label for="receipt_no">Receipt No.</label>
                                            <input type="text" class="ac_input" id="receipt_no" value="" onblur="IsNumeric(id)" name="receipt_no">
                                        </div>
                                        <div class="form-group" id="">
                                            
                                        </div>
                                        <div class="form-group" id=""  align="center">
                                            <label for="offender_license_no">Challan Book No.</label>
                                            <input type="text" class="ac_input" id="challan_book_no" name="challan_book_no" value="" onkeypress="IsNumeric(id)" onkeyup="IsNumeric(id)" onclick="IsNumeric(id)" onblur="IsNumeric(id)">
                                            <input type="hidden" id="challan_book_hd" value="">
                                            <label for="vehicle_no">Challan No.</label>
                                            <input type="text" class="ac_input" id="challan_no" name="challan_no" value="" onkeypress="IsNumeric(id)" onkeyup="IsNumeric(id)" onclick="IsNumeric(id)" onblur="IsNumeric(id)">
                                            <input type="button" class="btn" id="add_challan" value="Add">

                                            <input type="hidden" id="latitude" name="latitude" value="">
                                            <input type="hidden" id="longitude" name="longitude" value="">
                                            <input type="hidden" id="timestamp" name="timestamp" value="">
                                            <input type="hidden" id="date_time" name="date_time" value="">
                                        </div>                                        
                                    </td>
                                    <td>
                                        &nbsp;
                                    </td>                                    
                                </tr>                                
                                <tr>
                                    <td align='center' colspan="">
                                        <%-- <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>--%>
                                        <%-- <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                         <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>--%>
                                    </td>
                                </tr>
                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                <input type="hidden" id="clickedButton" value="">
                                <input type="hidden" name="searchStatusType" value="${searchStatusType}" >
                                <input type="hidden" id="license_vehicle"  name="license_vehicle" value="${searchStatusType}" >
                                <input type="hidden" id="menuid1" name="menuid" value="${menuid}">
                                <input type="hidden" id="penalty_amount" name="penalty_amount" value='0'>
                            </table>
                                <div id="resultTable" class="form-group jumbotron table-responsive" style="">
                                            <table class="table table-condensed table-hover table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>S.No.</th>
                                                        <th>Challan Book No.</th>
                                                        <th>Challan No.</th>
                                                        <th>Amount</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="receipt_table_body">
                                                    
                                                </tbody>
                                            </table>
                                </div>
                                <input class="btn" type="submit" name="task" id="save" value="Save" onclick="getVehicleLicenseValue();setStatus(id)">
                                        <input class="btn" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                        
                                <div id="penalty_amoutn_div" style="display: none">
                                    <input type='hidden' id='hdn_id' name='hdn_id' size='4' value='0'>
                                </div>
                        </form>                            
                            <form id="" role="form" action="receiptBookCont.do" method="post" onsubmit="" enctype="multipart/form-data" style="display: none">
                                <label for="select_image">Select Image </label>
                                <input type="file" class="ac_input" id="select_image" multiple name="select_image" value="" onclick="checkImage(id);" disabled>
                                <input class="btn" type="submit" name="task" id="upload" value="Upload" >
                            </form>
                    </div>
        </div>
    </body>
</html>

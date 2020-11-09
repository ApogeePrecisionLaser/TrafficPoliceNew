<%-- 
    Document   : trafficePoliceInsertView
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="content/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="style/style1.css">
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
            var doc = document;
            jQuery(function(){
                $("#searchOffenderLicenseNo").autocomplete("trafficPoliceSearchCont.do", {
                    extraParams: {
                        action1: function() { return "getOffenderLicenseNoSearchList";}
                    }
                });
//                $("#Offence_code0").autocomplete("trafficPoliceSearchCont.do", {
//                    extraParams: {
//                        action1: function() { return "getOffenderLicenseNoSearchList";}
//                    }
//                });
                $("#searchVehicleNo").autocomplete("trafficPoliceSearchCont.do", {
                    extraParams: {
                        action1: function() { return "getVehicleNoSearchList"}
                        // VehicleType: function(){return doc.getElementById("searchVehicleType").value ;}

                    }
                });

                $("#officer_name").autocomplete("trafficPoliceSearchCont.do", {
                    extraParams: {
                        action1: function() { return "getOfficerNameList";}
                    }
                });

                $("#book_no").autocomplete("trafficPoliceSearchCont.do", {
                    extraParams: {
                        action1: function() { return "getOfficerBooklist";},
                        action2: function() { return $("#officer_name").val();}
                    }
                });

                $("#offence_city").autocomplete("trafficPoliceSearchCont.do", {
                    extraParams: {
                        action1: function() { return "getCitylist";}                        
                    }
                });

                $("#offence_zone").autocomplete("trafficPoliceSearchCont.do", {
                    extraParams: {
                        action1: function() { return "getZonelist";},
                        action2: function() { return $("#offence_city").val();}
                    }
                });

                $("#offence_location").autocomplete("trafficPoliceSearchCont.do", {
                    extraParams: {
                        action1: function() { return "getLocationlist";},
                        action2: function() { return $("#offence_city").val();},
                        action3: function() { return $("#offence_zone").val();}
                    }
                });


                $("#book_no").result(function(event, data, formatted){
                    $.ajax({url: "trafficPoliceSearchCont.do?action1=getOfficerName", data: "action2="+ data +"&q=", success: function(response_data) {
                            response_data = response_data.trim();debugger;
                            var name = response_data.split("_")[0];
                            var book_revision_no = response_data.split("_")[1];
                            //var i;
                            $("#officer_name").val(name);
                            $("#book_revision_no").val(book_revision_no);
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
            {debugger;
                navigator.geolocation.getCurrentPosition(handlePosition);
                //alert("if");
            }else
                alert("else");

            function handlePosition(pos)
            {debugger;
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
                var no_of_offence = $("#no_of_offence").val();
                if($("#offender_license_no").val().trim() == "" && $("#vehicle_no").val().trim() == ""){
                     $("#message").html("<td colspan='6' bgcolor='coral'><b> License No OR Vehicle No is required...</b></td>");
                     doc.getElementById("offender_license_no").focus();
                     return false;
                }
                for(var i = 0; i < no_of_offence; i++){
                    var Offence_code = $("#Offence_code"+i).val().trim();
                    if(Offence_code == ""){
                        $("#message").html("<td colspan='6' bgcolor='coral'><b> Offence Code is required...</b></td>");
                        doc.getElementById("Offence_code"+i).focus();
                        return false;
                    }else
                        result = IsNumeric("Offence_code"+i);
                    if(result == false)
                        return result;
                }

                /*if($("#jarayam_no").val() == "" ){
                     $("#message").html("<td colspan='6' bgcolor='coral'><b> Jarayam No is required...</b></td>");
                     doc.getElementById("jarayam_no").focus();
                     return false;
                }*/

                if($("#officer_name").val().trim() == "" ){
                     $("#message").html("<td colspan='6' bgcolor='coral'><b> Officer Name is required...</b></td>");
                     doc.getElementById("officer_name").focus();
                     return false;
                }

//                if($("#offender_name").val().trim() == "" ){
//                     $("#message").html("<td colspan='6' bgcolor='coral'><b> Offender Name is required...</b></td>");
//                     doc.getElementById("offender_name").focus();
//                     return false;
//                }

                if($("#book_no").val().trim() == "" ){
                     $("#message").html("<td colspan='6' bgcolor='coral'><b> Book No. is required...</b></td>");
                     doc.getElementById("book_no").focus();
                     return false;
                }
                if($("#receipt_no").val().trim() == "" ){
                     $("#message").html("<td colspan='6' bgcolor='coral'><b> Challan No. is required...</b></td>");
                     doc.getElementById("receipt_no").focus();
                     return false;
                }
                result = IsNumeric("book_no");
                result = IsNumeric("deposit_amount");

                /*if($("#receipt_no").val() == "" ){
                     $("#message").html("<td colspan='6' bgcolor='coral'><b> Receipt No is required...</b></td>");
                     doc.getElementById("receipt_no").focus();
                     return false;
                }*/
                if ( navigator.geolocation ){//debugger;
                    navigator.geolocation.getCurrentPosition(handlePosition);
                    //alert("if");
                }else
                     alert("else");

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
                    $("#Offence_code"+i).autocomplete("trafficPoliceSearchCont.do", {
                        extraParams: {
                            action1: function() { return "getOffenceCodeList";}
                        }
                    });
                });

                 $("#Offence_code"+i).result(function(event, data, formatted){//debugger;
                     var id =this.id;
                    $.ajax({url: "trafficPoliceSearchCont.do?action1=getPenaltyAmount", data: "action2="+ data +"&q=", success: function(response_data) {                        
                        var p_amount = response_data.trim();
//                        if(p_amount == 100000.0){
//                            p_amount = 0;
//                            $("#spn_deposit_amount").html(" + Court");
//                        }else
//                            $("#spn_deposit_amount").html("");
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

    function getTotalPenaltyAmount(){debugger;
        var no_of_offence = $("#no_of_offence").val();
        var count = 0;
        $("#deposit_amount").val(0);
        for(var i = 0; i < no_of_offence; i++){
            var hdn_value = $("#hdn_Offence_code"+i).val();
            var tot_amount = $("#deposit_amount").val();
            var total;
            if(hdn_value != undefined){
                if(hdn_value == 100000.0){
                    hdn_value = 0;
                    count++;
                }else
                    total = parseFloat(hdn_value) + parseFloat(tot_amount);
            }else
                total = tot_amount;
            if(count > 0)
                $("#processing_type").val("Court");
                //$("#spn_deposit_amount").html(" + Court");
            else
                $("#processing_type").val("Non Court");
                //$("#spn_deposit_amount").html("");
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
                            }//else{
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
                <tr><td></td></tr>                
            </table>
                <a href="trafficPoliceSearchCont.do?menuid=${menuid}" >Search</a>&nbsp;&nbsp;&nbsp;
                <a href="?task=new&menuid=${menuid}" >New</a>&nbsp;&nbsp;&nbsp;
                <!--<a href="receiptBookCont.do?task=newReceipt&menuid=${menuid}" >Receipt</a>&nbsp;&nbsp;&nbsp;
                <a href="receiptBookCont.do?menuid=${menuid}" >Receipt View</a>-->
            <h1>Traffic Offence New Record</h1>
            <%-- <table align="center" border="1">--%>
                    <div class="form-group jumbotron table-responsive" >
                        <form id="" role="form" action="trafficPoliceSearchCont.do" method="post" onsubmit="return verify()" enctype="multipart/form-data" accept-charset="UTF-8">
                            <table  id="table2" class="table table-condensed " border="0"  align="center" width="600">
                               <tr id="message">
                                      <c:if test="${not empty message}">
                                          <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                      </c:if>
                               </tr>
                                <tr align="">                                 

                                    <td id="offence_td" align="">
                                        <div class="form-group" id="is_owner">
                                            <label for="">Is Owner</label>
                                            <input type="radio" id="is_owner_license" value="Yes" name="is_owner_license" checked disabled>Yes
                                            <input type="radio" id="is_not_owner_license" value="No" name="is_owner_license" disabled >No<br>
                                        </div>
                                        <div class="form-group" id="">
                                            <label for="offender_license_no">Offender License No. </label>
                                            <input type="text" class="ac_input" id="offender_license_no" name="offender_license_no" value="${offender_license_no}" disabled><br>
                                        
                                            <label for="vehicle_no">Vehicle No. </label>
                                            <input type="text" class="ac_input" id="vehicle_no" name="vehicle_no" value="${vehicle_no}" disabled><input type="hidden" class="ac_input" id="vehicle_no1" name="vehicle_no1" value="${vehicle_no}" ><br>
                                        
                                           <!-- <label for="officer_name">Jarayam No. </label>
                                            <input type="text" class="ac_input" id="jarayam_no" name="jarayam_no" value="" disabled><br>-->

                                        
                                            <label for="no_of_offence">Select No. of Offence</label>
                                            <select id="no_of_offence" class="dropdown" onfocus="" onchange="showOffenceCodeDiv()" style="width: 150px" name="no_of_offence" disabled>
                                                                   <option value="1">1</option>
                                                                   <option value="2">2</option>
                                                                   <option value="3">3</option>
                                                                   <option value="4">4</option>
                                                                   <option value="5">5</option>
                                                                   <option value="6">6</option>
                                                                   <option value="7">7</option>
                                                                   <option value="8">8</option>
                                                                   <option value="9">9</option>
                                                                   <option value="10">10</option>
                                            </select><br>

                                        <div class="form-group" id="offence_code_div">
                                            <!--<label for="Offence_code">Offence Code </label>
                                            <input class="ac_input" type="hidden" id="status_type_id" name="status_type_id" value="" >
                                            <input type="text" class="ac_input" id="Offence_code0" name="Offence_code" size="4" value="" disabled>-->
                                        </div>
                                        <%-->  </tr>
<tr>--%>                                
                                            <label for="officer_name">Officer Name </label>
                                            <input type="text" class="ac_input" id="officer_name" name="officer_name" value="" disabled><br>
                                            <label for="offender_name" style="display: none">Offender Name </label>
                                            <input type="hidden" class="ac_input" id="offender_name" name="offender_name" value="" disabled><br>
                                            <div class="form-group" style="display: none" id="">
                                            <label for="offence_Location">Offence Location </label><br>
                                            <label for="offence_city">City</label>
                                            <input type="text" class="ac_input" id="offence_city" name="offence_city" value="${offence_city}" disabled><br>
                                            <label for="offence_zone">Zone</label>
                                            <input type="text" class="ac_input" id="offence_zone" name="offence_zone" value="${offence_zone}" disabled><br>
                                            <label for="offence_location">Location</label>
                                            <input type="text" class="ac_input" id="offence_location" name="offence_location" value="${offence_location}" disabled>
                                            </div>
                                            
                                            <label for="book_no">Book No </label>
                                            <input type="text" class="ac_input" id="book_no" name="book_no" value="" disabled><br>
                                            <input type="hidden" class="ac_input" id="book_revision_no" name="book_revision_no" value=""><br>
                                            
                                            
                                            <label for="receipt_no">Challan No </label>
                                            <input type="text" class="ac_input" id="receipt_no" name="receipt_no" value="${receipt_no}" onkeyup="IsNumeric(id)" disabled require><br>
                                            
                                            <label for="deposit_amount">Deposited Amount </label>
                                            <input type="text" class="ac_input" id="deposit_amount" name="deposit_amount" value=""  onkeyup="IsNumeric(id)" disabled>
                                            <span id="spn_deposit_amount" ></span><br>
                                            <label for="processing_type">Processing Type</label>
                                            <Select id="processing_type" class="" name="processing_type">
                                                <option>Select...</option>
                                                <option>Court</option>
                                                <option>Non Court</option>
                                            </Select><br>
                                            <input type="hidden" id="latitude" name="latitude" value="">
                                            <input type="hidden" id="longitude" name="longitude" value="">
                                            <input type="hidden" id="timestamp" name="timestamp" value="">
                                            <input type="hidden" id="date_time" name="date_time" value="">

                                            <label for="select_image">Select Image </label>
                                            <input type="file" class="ac_input" id="select_image" name="select_image" value="" disabled><!--multiple-->

                                        </div>
                                    </td>
                                    <td>
                                        &nbsp;
                                    </td>                                    
                                </tr>
                                <tr>
                                    <td align='center' colspan="">
                                        <%-- <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>--%>
                                        <input class="btn" type="submit" name="task" id="save" value="Save" onclick="getVehicleLicenseValue();//setStatus(id)" disabled>
                                        <input class="btn" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
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
                                <div id="penalty_amoutn_div" style="display: none">
                                    <input type='hidden' id='hdn_id' name='hdn_id' size='4' value='0'>
                                </div>
                        </form>                            
                            <form id="" role="form" action="trafficPoliceSearchCont.do" method="post" onsubmit="" enctype="multipart/form-data" style="display: none">
                                <label for="select_image">Select Image </label>
                                <input type="file" class="ac_input" id="select_image" multiple name="select_image" value="" onclick="checkImage(id);" disabled>
                                <input class="btn" type="submit" name="task" id="upload" value="Upload" >
                            </form>
                    </div>
        </div>
    </body>
</html>

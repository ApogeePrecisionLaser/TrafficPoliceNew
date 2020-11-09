<%-- 
    Document   : trafficPoliceSearchVeiw
    Created on : May 9, 2015, 2:57:02 PM
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
        <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>-->
        <link rel="stylesheet" href="content/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="content/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="style/style1.css">
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <!--..............................-->

        <script src="js/jquery/jquery-1.7.1.min.js"></script>
<script src="js/jquery/jquery.ui.core.min.js"></script>
<script src="js/jquery/jquery.ui.widget.min.js"></script>
<script src="js/jquery/jquery.ui.position.min.js"></script>
<script src="js/jquery/jquery.ui.autocomplete.min.js"></script>

<link rel="stylesheet" href="js/jquery/smoothness/jquery-ui-1.8.16.custom.css"/>




        <title>Traffic Offence Record</title>
        <style type="text/css"  >
            .ac_results li {                    
                    font-family:Sans-Serif;                    
                }
        </style>
        <script type="text/javascript" language="javascript">
            var doc = document;
            
            var data = [
		{"label" : "Aragorn"},
		{"label" : "Arwen"},
		{"label" : "Bilbo Baggins"},
		{"label" : "Boromir"},
		{"label" : "Frodo Baggins"},
		{"label" : "Gandalf"},
		{"label" : "Gimli"},
		{"label" : "Gollum"},
		{"label" : "Legolas"},
		{"label" : "Meriadoc Merry Brandybuck"},
		{"label" : "Peregrin Pippin Took"},
		{"label" : "Samwise Gamgee"}
		];
           /*var vehicle_nos = $(function() {
                /*$.ajax({url: "unPaidBillRepCont.do", async: false, data: queryString, success: function(response_data) {
                              //  alert(response_data);
                              if(response_data != null || response_data != ''){
                                  gid=parseInt(response_data.trim());
                              }else{
                                  gid=0;
                              }
                          }
          //            });

                $.ajax({
                    url: "trafficPoliceSearchCont.do",
                    data: "action1=getVehicleNoJsonList",
                    dataType: 'json',
                    success: function(result){
                        vehicle_nos = result;                        
                    }
                });
            });*/
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
//                $("#searchVehicleNo").autocomplete({
//                   source:data
//                });


            });
            //Titanium.Geolocation.accuracy = Titanium.Geolocation.ACCURACY_BEST;
            $(doc).ready(function(){//debugger;
                if($("#menuid1").val() != "1"){
                    $("#menuid").hide();
                }
                if($("#noOfRows").val() > 0){
                    $("#resultTable").show();
                    $("#new_offence").show();
                }else{
                    $("#new_offence").hide();
                }

                if($("#searchOffenderLicenseNo").val() != "" || $("#searchVehicleNo").val() != "")
                    $("#new_offence").show();
                else
                    $("#new_offence").hide();
            
            });
            
            /*$("#searchOffenderLicenseNo").click(function(){
                alert(1);

            });*/

            var select_search_field = "";

            function verify(){debugger;
                var result = false;              

                if($("#searchOffenderLicenseNo").val() != "" || $("#searchVehicleNo").val() != ""){
                    result = true;
                }
                if(select_search_field == "Vehicle")
                    result = checkVehicleNo();
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
                doc.getElementById("jarayam_no").disabled = false;
                
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
     alert("FIrstly Search Offender License No. Or Vehicle No.")


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
            <input type='text' class='ac_input' id='Offence_code"+i+"' name='Offence_code"+i+"' size='4' value=''>");
            jQuery(function(){
                    $("#Offence_code"+i).autocomplete("trafficPoliceSearchCont.do", {
                        extraParams: {
                            action1: function() { return "getOffenceCodeList";}
                        }
                    });
                });
        }
//        <label for="Offence_code">Offence Code </label>
//        <input class="ac_input" type="hidden" id="status_type_id" name="status_type_id" value="" >
//        <input type="text" class="ac_input" id="Offence_code" name="Offence_code" size="4" value="" disabled>

    }

    function selectSearchField(id){
        var radio_val = $("#"+id).val();
        select_search_field = radio_val;
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

   function checkVehicleNo(){
       var result = true;
       var searchVehicleNo = $("#searchVehicleNo").val();
       var pattern1 = /^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$/;
       var pattern2 = /^[a-z]{2}[ -][0-9]{1,2}(?: [a-z])?(?: [a-z]*)? [0-9]{4}$/;
       var pattern3 = /^[a-z]{2}[0-9]{1,2}(?:[a-z])?(?:[a-z]*)?[0-9]{4}$/;
       var pattern4 = /^[A-Z]{2}[0-9]{1,2}(?:[A-Z])?(?:[A-Z]*)?[0-9]{4}$/;
       if(!searchVehicleNo.match(pattern1)){debugger;
           if(!searchVehicleNo.match(pattern2)){debugger;
               if(!searchVehicleNo.match(pattern3)){debugger;
                   if(!searchVehicleNo.match(pattern4)){debugger;
                       $("#message").html("<b style='background-color: red'> Please insert valid Vehicle No...</b>");//<td colspan='6' bgcolor='coral'> </td>
                       doc.getElementById("searchVehicleNo").focus();
                       result = false;
                   }else
                       $("#message").html("");

               }else
                   $("#message").html("");
           }else
               $("#message").html("");
           //$("#message").html("<b style='background-color: red'> Please insert valid Vehicle No...</b>");//<td colspan='6' bgcolor='coral'> </td>
           //doc.getElementById("searchVehicleNo").focus();
       }else{debugger;
           $("#message").html("");
       }
       return result;
   }

   function fillColumns(id){//debugger;
       var offender_license_no = $("#"+id).html();
       if(offender_license_no != ""){
           $("#offender_license_no_radio").attr("checked", true);
           $("#searchOffenderLicenseNo").val(offender_license_no);
           $("#license_no_div").show();
           $("#searchVehicleNo").val("");
           $("#vehicle_no_div").hide();
           $("#search").click();

       }
   }

   function getRequisitionimage(){
                      var gid;
                      var payment_req_no = $("#requition_no").val();
                      var bill_month=$("#month").val()+"-"+$("#year").val();
                      var queryString = "task=getGid&payment_req_no="+payment_req_no;
                      $.ajax({url: "unPaidBillRepCont.do", async: false, data: queryString, success: function(response_data) {
                              //  alert(response_data);
                              if(response_data != null || response_data != ''){
                                  gid=parseInt(response_data.trim());
                              }else{
                                  gid=0;
                              }
                          }
                      });
                      if(gid==0){
                          alert("Image of requisition is not uploaded Yet");
                      }else{
                          var   queryString ="uploaded_for=Requisition No&image_name=&bill_month="+bill_month+"&gid="+gid;
                          var url = "viewBillImageCont.do?" + queryString;
                          popupwin = openPopUp(url, "Bill Image", 600, 900);
                      }

                  }

                  function viewTrafficPoliceImage(id){
                //alert(id);
                //var emp_code= doc.getElementById("emp_code1"+id).value;
                //var queryString = "task1=viewImage&emp_code="+emp_code;
                var queryString = "task=View_Image&traffic_police_id="+id;
                // alert(queryString);
                var url = "trafficPoliceSearchCont.do?" + queryString;
                popupwin = openPopUp(url, "Show Image", 600, 900);
            }

                  var popupwin = null;
                    function openPopUp(url, window_name, popup_height, popup_width) {
                        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                        return window.open(url, window_name, window_features);
                    }

                    if (!doc.all) {
                        doc.captureEvents (Event.CLICK);
                    }
                    doc.onclick = function() {
                        if (popupwin != null && !popupwin.closed) {
                            popupwin.focus();
                        }
                    }

                   function openMapForCord() {
        var url="trafficPoliceSearchCont.do?task=GetCordinates1";//"getCordinate";
        popupwin = openPopUp(url, "",  600, 630);
    }
            
        </script>
    </head>
    <body>        
        <div class="container table-border" align="center">
            <table id="menushow" style="width: 100%" class="main" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td><%@include file="/layout/header.jsp" %></td>
                </tr>

                <tr>
                    <td><%@include file="/layout/menu.jsp" %></td>
                </tr>
                <tr></tr>
            </table>
                <a href="?menuid=${menuid}" >Search</a>&nbsp;&nbsp;&nbsp;
                <a href="trafficPoliceSearchCont.do?task=new&menuid=${menuid}">New</a>&nbsp;&nbsp;&nbsp;
                <!--<a href="receiptBookCont.do?task=newReceipt&menuid=${menuid}" >Receipt</a>&nbsp;&nbsp;&nbsp;
                <a href="receiptBookCont.do?menuid=${menuid}" >Receipt View</a>-->
            <h1>Traffic Offence Record</h1>
            <form role="form" action="trafficPoliceSearchCont.do" method="post" onsubmit="return verify()">
                <div class="form-group" id="">
                    <input type="radio" id="offender_license_no_radio" value="License" name="is_owner_license" onchange="selectSearchField(id)" ${searchOffenderLicenseNo == ""? '':'checked'}>Offender License No
                    <input type="radio" id="vehicle_no_radio" value="Vehicle" name="is_owner_license" onchange="selectSearchField(id)" ${searchVehicleNo == ""? '':'checked'}>Vehicle No
                </div>
                <div class="form-group" id="message" style="background-color: red"><b ></b>
                                      <c:if test="${not empty message}">
                                          <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                      </c:if>
                </div>
                <div class="form-group" id="license_no_div" style="display: ${searchOffenderLicenseNo == ""? 'none':''}">
                    <label for="searchOffenderLicenseNo">Offender License No </label>
                    <input type="text" class="ac_input " id="searchOffenderLicenseNo" name="searchOffenderLicenseNo" value="${searchOffenderLicenseNo}">
                </div>
                <div class="form-group" id="vehicle_no_div" style="display: ${searchVehicleNo == ""? 'none':''};">
                    <label for="searchVehicleNo"> Vehicle No </label>
                    <input type="text" class="ac_input ui-autocomplete-input" aria-autocomplete="list" aria-haspopup="true" id="searchVehicleNo" onkeyup="" name="searchVehicleNo" value="${searchVehicleNo}" require>
                </div>
                <input type="submit" id="search" value="Search">
                <div id="resultTable" class="form-group jumbotron table-responsive" style="display: none">
                    <table class="table table-condensed table-hover table-bordered">
                        <thead>                            
                            <tr>
                                <th>S.No.</th>
                                <th>Offence Type</th>
                                <th>Offender License No</th>
                                <!--<th>Vehicle No</th>
                                <th>Deposit Amount</th>-->
                                <th>Offence Date</th>
                                <!--<th>Image</th>-->
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="tp" items="${requestScope['list']}" varStatus="loopCounter">
                                <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" onclick="">
                                    <td id="t1c${IDGenerator.uniqueID}" onclick="" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                        <input type="hidden" id="traffic_police_id${loopCounter.count}" value="${tp.traffic_police_id}">
                                        <%--   <input type="hidden" id="balance_unit_id_gen${loopCounter.count}" value="${st.balance_unit_id}">--%>
                                    </td>
                                    <%--<td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.book_no}</td>
                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.book_revision_no}</td>
                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.key_person_name}</td>
                                    --%>
                                   <%-- <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" >${tp.offender_name}</td>
                                       <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${st.movement_type}</td>
                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.vehicle_type}</td>--%>
                                   <td id="t1c${IDGenerator.uniqueID}" style="white-space: normal"  onclick="">${tp.offence_type}</td>
                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${tp.offender_license_no}</td>
                                   <td id="t1c${IDGenerator.uniqueID}" onclick="">${tp.offence_date}</td>
                                   <td><input type="button" class="btn" value="View Image" id="${tp.traffic_police_id}" onclick="viewTrafficPoliceImage(id)"></td>
                                   <td>
                                      <input type="button" class="btn"  value ="View Map" id="get_cordinate" onclick="openMapForCord()"/>
                                   </td>
                                    <%-- <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.vehicle_no} </td>
                                      <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${st.quantity} ${st.balance_unit}</td>--%>


                                    <%--   <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${st.key_person_name}</td>
                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.offence_place}</td>
                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.deposited_amount}</td>--%>
                                    <%--<td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.offence_type}</td>
                                   <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.act}</td>
                                   <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.act_origin}</td>
                                   <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.penalty_amount}</td>
                                   <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.receipt_no}</td>
                                   <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.city}</td>
                                   <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.zone}</td>    --%>
                                </c:forEach>
                                <tr>
                                    <td align="center" colspan="6">
                                        
                                    </td>
                                </tr>
                        </tbody>
                    </table>
                </div>
                <input type="hidden" id="noOfRows" value="${noOfRowsTraversed}">
                <input type="hidden" id="menuid1" name="menuid" value="${menuid}">
                <input type="submit" style="display: none" class="btn" id="new_offence" name="task" value="ADD NEW OFFENCE">
            </form>
            <%-- <table align="center" border="1">--%>
            <!--<tr>
                <td align="center" >-->
            <%--<div class="form-group jumbotron table-responsive" >
                        <form id="form1" role="form" action="trafficPoliceSearchCont.do" method="get" >
                            <table  id="table2" class="table table-condensed " border="0"  align="center" width="600">
                               <tr id="message">
                                      <c:if test="${not empty message}">
                                          <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                      </c:if>
                               </tr>
                                <tr align="center"><td>                                        
                                        <div class="form-group" id="">
                                            <label for="">Is Owner</label>
                                            <input type="radio" id="is_owner_license" value="Yes" name="is_owner_license" checked disabled>Yes
                                            <input type="radio" id="is_not_owner_license" value="No" name="is_owner_license" disabled >No
                                        </div>
                                        <div class="form-group">
                                            <label for="officer_name">Jarayam No. </label>
                                            <input type="text" class="ac_input" id="jarayam_no" name="jarayam_no" value="" disabled>
                                        </div>
                                        <div class="form-group">
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
                                            </select>
                                        </div>
                                        <div class="form-group" id="offence_code_div">
                                            <label for="Offence_code">Offence Code </label>
                                            <!--<input class="ac_input" type="hidden" id="status_type_id" name="status_type_id" value="" >-->
                                            <input type="text" class="ac_input" id="Offence_code0" name="Offence_code" size="4" value="" disabled>
                                        </div>
                                        <%-->  </tr>


<tr>--%>                                <%--<div class="form-group">
                                            <label for="officer_name">Officer Name </label>
                                            <input type="text" class="ac_input" id="officer_name" name="officer_name" value="" disabled>
                                        </div>

                                        <div class="form-group">
                                            <label for="deposit_amount">Deposited Amount </label>
                                            <input type="text" class="ac_input" id="deposit_amount" name="deposit_amount" value="" disabled>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td align='center' colspan="2">
                                        <!-- <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>-->
                                        <input class="btn" type="submit" name="task" id="save" value="Save" onclick="getVehicleLicenseValue();setStatus(id)" disabled>
                                        <input class="btn" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                        <!-- <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                         <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>-->
                                    </td>
                                </tr>
                                <!-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. -->
                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                <input type="hidden" id="clickedButton" value="">
                                <input type="hidden"  name="searchStatusType" value="${searchStatusType}" >
                                <input type="hidden" id="license_vehicle"  name="license_vehicle" value="${searchStatusType}" >
                            </table>
                        </form>
                    </div>--%>
                <!--</td>
            </tr>-->
        </div>
    </body>
</html>

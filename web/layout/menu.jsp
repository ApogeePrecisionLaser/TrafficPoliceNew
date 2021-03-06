<%--
    Document   : index
    Created on : Jan 7, 2013, 3:26:07 PM
    Author     : Neha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html>

<html>
    <head>
        <title>Menu</title>
        <script type="text/javascript">
            $(function() {
                if ($.browser.msie && $.browser.version.substr(0,1)<7)
                {
                    $('li').has('ul').mouseover(function(){
                        $(this).children('ul').show();
                    }).mouseout(function(){
                        $(this).children('ul').hide();
                    })
                }
            });
        </script>

        <link type="text/css" href="style/menu.css" rel="stylesheet"/>



    </head>
    <body>

        <div id="menuid">
            <ul id="menu">
                <li><a id="mpeb1" href="#">Traffic polices</a>
                    <ul>
                        <li><a href="statusTypeCont">Status</a></li>
                        <li><a href="makeTypeCont">Make Type</a></li>
                        <li><a href="modelTypeCont">Model Type</a>
                        <li><a href="vehicleTypeCont">Vehicle Type</a></li>
                        <li><a href="vehicleModelMapCont.do">Vehicle Model Map</a></li>
                        <li><a href="#">License & Vehicle</a>
                            <ul>
                                <li><a href="vehicleDetailCont.do">Vehicle Detail</a></li>
                                <li><a href="licenseDetailCont.do">License Detail</a></li>
                                <li><a href="vehicleLicenseMapCont.do">License Vehicle Map</a></li>
                            </ul>
                        </li>
                        <li><a href="commercialTypeCont.do">Commercial Type</a></li>
                        <li><a href="processingTypeCont.do">Processing Type</a></li>
                        <li><a href="relationTypeCont.do">Relation Type</a></li>
                        <li><a href="actOriginCont">Act Origin</a></li>
                        <li><a href="offenceTypeCont">Offence Type</a></li>
                        <li><a href="officerBookCont">Officer Book</a></li>                        

<!--                        <li><a href="trafficPoliceCont">Traffic Police</a></li>-->

                        <li><a href="caseStatusCont.do">Case Status</a></li>
                        <li><a href="caseProcessingCont.do">Case Processing</a></li>
<!--                        <li><a href="userEntryByImageCont.do">User Entry By Image</a></li>-->
<!--                        <li><a href="CameraInfoCont.do">Camera Info</a></li>
                        <li><a href="ModeOfPaymentCon.do">Payment Mode</a></li>-->
                        <%-- <li><a href="reportsTypeCont">Reports</a></li>--%>
                    </ul>
                </li>
                
                
                <li><a id="mpeb3" href="#">Challan Info</a>
                    <ul>
                        <li><a href="trafficPoliceCont">Traffic Police</a></li>
                        <li><a href="userEntryByImageCont.do">User Entry By Image</a></li>
                        <li><a href="CameraInfoCont.do">Camera Info</a></li>
                        <li><a href="CameraTypeCont.do">Camera Type</a></li>
                        <li><a href="CameraFacingCont.do">Camera Facing</a></li>
                        <li><a href="ModeOfPaymentCon.do">Payment Mode</a></li>
                        
                        <li><a href="ChallanPaymentCont.do">Challan Payment</a></li>
                        
                        <li><a href="MISCont.do">MIS</a></li>
                    </ul>
                </li>
                
                
                
                
                
                <li><a id="mpeb3" href="#">Organization</a>
                    <ul>
                        <li><a href="orgDetailEntryCont.do">Org. All in One</a></li>
                        <li><a href="orgNameCont.do">Org. Name</a></li>
                        <li><a href="orgTypeCont.do">Org. Type</a></li>
                        <li><a href="organisationSubTypeCont.do">Org. Sub Type</a></li>
                        <li><a href="mapOrgCont.do">Org. Map Relation</a></li>
                        <li><a href="orgOfficeTypeCont.do">Org. Office Type </a></li>                                                                       
                        <li><a href="organisationCont.do">Org. Office </a></li>
                        <li><a href="designationCont.do">Designation</a></li>
                        <li><a href="personCount.do">Org Person's Name</a></li>
                    </ul>
                </li>
                <li><a id="mpeb2" href="#">Location</a>
                    <ul>
                        <li><a href="zonalCont">Zonal Council</a></li>
                        <li><a href="stateutTypeCont">State & UT</a></li>
                        <li><a href="divisionTypeCont">Division</a></li>
                        <li><a href="districtTypeCont">District</a></li>
<!--                        <li><a href="cityTypeCont">City(***)</a></li>
                        <li><a href="zoneTypeCont">Zone(***)</a></li>
                        <li><a href="cityLocationCont">Location(***)</a></li>-->
                    </ul>
                </li>
                <li><a id="mpeb2" href="#">City Location</a>
                    <ul>
                        <li><a href="cityTypeCont">City</a></li>
                        <li><a href="zoneTypeCont">Zone</a></li>
                        <li><a href="wardTypeCont">Ward</a></li>
                        <li><a href="areaTypeCont">Area </a></li>
                        <li><a href="cityLocationCont.do">Location</a></li>
                        
                        <li><a href="DutyLocationTypeCont.do">Duyt Location Type</a></li>
                    </ul>
                </li>
                <li><a id="mpeb1" href="#">Shift</a>
                    <ul>
                        <li><a href="ShiftController">Shift Type</a></li>
                        <!--  <li><a href="LocationTypeController">Location Type</a></li>
                           <li><a href="DesignationLocationTypeController">Designation Location Type</a></li>-->
                        <li><a href="ShiftDesinationLocationController">Shift Designation Map</a></li>
                        <li><a href="ShiftKeyPersonMapController">Shift Key Person Map</a></li>
                        <!--                        <li><a href="ShiftTimeController">Shift Time</a></li>-->
                        <li><a href="ShiftShowController">Current Location On Map</a></li>
                        <li><a href="AttendenceController">Attendance</a></li>
                        <li><a href="AttendenceViewController">Shift Attendance detail</a></li>
                        <!--                        <li><a href="shiftReasonCont.do">Shift Reason</a></li>
                                                <li><a href="shiftLoginCont.do?task=logout">Logout</a></li>-->
                    </ul>
                </li>

                <li><a id="mpeb2" href="trafficPoliceSearchCont.do?menuid=1">Offence History</a></li>
                  <li><a  href="EmailController">Email</a></li>
                   <li><a  href="Mobile">Payment</a></li>
                
                <li><a id="mpeb2" href="LoginCont.do?task=logout">LogOut</a></li>
<!--                <li><a id="mpeb2" href="correspondenceCont.do">Correspondence</a></li>-->
                <%--   <li><a id="mpeb" href="#">Estimate</a>
                       <ul>
                           <li><a href="showItemDataCont">Show Item</a></li>
                           <li><a href="createEstimateCont">Create Estimate</a></li>
                <%--<li><a href="esProcessBasicStepCont">Estimate Process</a></li>--%>
                <%-- <li><a href="existingEstimateCont">Existing Estimate</a></li>
                 <li><a href="statusTypeCont.do">Status Type</a></li>
                 <li><a href="rateTypeCont.do">Rate Type</a></li>
             </ul>
         </li>--%>
                <%-- <li><a id="mpeb2" href="#">Stock</a>
                     <ul>
                         <li><a href="showItemDataCont">Item Entry</a></li>
                         <li><a href="itemHierarchyCont">Item Hierarchy</a></li>
                         <li><a href="itemTransactionCont">Transaction</a></li>
                         <li><a href="stockDocumentTypeCont">Document Type Entry</a></li>
                         <li><a href="stockMovementTypeCont">Movement Type Entry</a></li>
                         <li><a href="stockUnitCont">Stock Unit Entry</a></li>
                         <li><a href="stockItemDetailCont">Stock Item Detail</a></li>
                     </ul>
                 </li>--%>
            </ul>
        </div>
    </body>
</html>
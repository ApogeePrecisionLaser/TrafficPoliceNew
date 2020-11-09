<%--
    document   : trafficpolice
    Created on : Jan 13, 2014, 3:35:23 PM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Traffic Police</title>
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
            <style type="text/css">
                .new_input
                {

                    font-size: 14px;
                    font-family:"kruti Dev 010", Sans-Serif;
                    font-weight: 500;
                    background-color: white;

                }

                .ontop {
				z-index: 999;
				width: 100%;
				height: 300%;
				top: 0;
				left: 0;
				display: none;
				position: absolute;
				background-color: #cccccc;
				color: #aaaaaa;
				opacity: .4;
				
			}
			#popup {
				width: 300px;
				height: 200px;
				position: absolute;
				color: #000000;
				background-color: #ffffff;
				/* To align popup window at the center of screen*/
				top: 70%;
				left: 50%;
				margin-top: -100px;
				margin-left: -150px;
			}
            </style>
            <script type="text/javascript" language="javascript">
                //filter: alpha(opacity = 50);
                var doc = document;
                var actOriginId ="";
                jQuery(function(){
                    //var cut_date = new Date('dd-mm-yy');
                    //$("#offence_date").val(getCurrentDate());
                    var actOriginId ="";
                    $(doc).ready(function() {
                        //getCurrentDate();
                        if($("#searchBy").val()=='Officer Name' || $("#searchBy").val()=='Work Order No' || $("#searchBy").val()=='Book No' ){
                            $("#supplier_item_details_row").attr("style", "display:''");
                        }

                        if( $("#select_by_officer_name").is(':checked') == true){$("#selectByOfficerName").attr("style","visibility:visible");}
                        if( $("#select_by_book_no").is(':checked') == true){$("#selectByBookNo").attr("style","visibility:visible");}
                        if( $("#select_challan_no").is(':checked') == true){$("#selectByChallan").attr("style","visibility:visible");}
                        //      getCurrentTime() ;

                        //$("#NEW").on("click", function(event){
                       $("#NEW").click(function(event){
                            //alert('before reset: ' + $("input[type='text']").val());
                            // executes before the form has been reset

                            //event.preventDefault();
                            // stops the form from resetting after this function
//                            $(this).closest('form').get(0).reset();
                           
                            // resets the form before continuing the function
                            getCurrentDate();
                            // alert('after reset: ' + $("input[type='text']").val());
                           // executes after the form has been reset
                        });

                    });
                    $("#case_date").datepicker({

                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });
                    $("#offence_date").datepicker({

                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });
                    $("#searchFromDate").datepicker({

                        //minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });
                    $("#searchToDate").datepicker({

                        //minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });
                   
                    $("#searchOfficerName").autocomplete("trafficPoliceCont", {
                        extraParams: {

                            action1: function() { return "getOfficerSearchList";}
                        }
                    });

                   var actOriginList =  $("#act_origin"+actOriginId).autocomplete("trafficPoliceCont", {
                        extraParams: {

                            action1: function() { return "getActOriginList";}
                        }
                    });

                    $("#searchJarayamNo").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getJarayamNo";}
                        }
                    });

                    $("#searchOffenceCode").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getOffenceCode";}
                        }
                    });

                    $("#searchBookNo").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() {  return "getBookSearchList";}
                            //officerName: function(){ return doc.getElementById("searchOfficerName").value ;}
                        }
                    });

                    
                    $("#searchOffenceType").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getOffenceSearchList";}
                            // BookNo: function(){ return doc.getElementById("searchBookNo").value ;}

                            
                        }
                    });
                    $("#searchActType").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getActSearchList"}
                            //OffenceType: function(){return doc.getElementById("searchOffenceType").value ;}
                        
                        }
                    });
                    $("#searchVehicleType").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getVehicleTypeSearchList"}
                            // ActType: function(){return doc.getElementById("searchActType").value ;}

                        }
                    });
                    
                    $("#vehicle_type").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getVehicleTypeSearchList"}
                            // ActType: function(){return doc.getElementById("searchActType").value ;}
                        }
                    });

                    $("#searchVehicleNo").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getVehicleNoSearchList"}
                            // VehicleType: function(){return doc.getElementById("searchVehicleType").value ;}

                        }
                    });
//                    $("#searchFromDate").autocomplete("trafficPoliceCont", {
//                        extraParams: {
//                            action1: function() { return "getFromDateList"}
//                        }
//                    });
//                    $("#searchToDate").autocomplete("trafficPoliceCont", {
//                        extraParams: {
//                            action1: function() { return "getToDateList"}
//                        }
//                    });
                    $("#searchOffenderLicenseNo").autocomplete("caseProcessingCont.do", {
                        extraParams: {
                            action1: function() { return "getOffenderLicenseNo"}
                        }
                    });
                    $("#city_name").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getCityName"}
                        }
                    });
                    $("#zone").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getZoneName"},
                            action2: function(){return doc.getElementById("city_name").value ;}

                        }
                    });$("#offence_place").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getLocationName"},
                            action2: function(){return doc.getElementById("city_name").value ;},
                            action3: function(){return doc.getElementById("zone").value ;}
                        }
                    });
                     $("#offender_city").autocomplete("trafficPoliceCont", {
                        extraParams: {
                            action1: function() { return "getOffenderCityName"}
                        }
                    });

                    $("#searchReceiptBookNo").autocomplete("receiptBookCont.do", {
                        extraParams: {
                            action1: function() {  return "getReceiptBookNo";},
                            action2: function(){ return doc.getElementById("searchOfficerName").value ;}
                        }
                    });

                    $("#reciept_book_no").autocomplete("receiptBookCont.do", {
                        extraParams: {
                            action1: function() {  return "getReceiptBookNo";},
                            action2: function(){ return doc.getElementById("officer_name_selected").value ;}
                        }
                    });

                    $("#receipt_book_no").result(function(event, data, formatted){
                    $.ajax({url: "receiptBookCont.do?action1=getBookRevision", data: "action2="+ data +"&q=", success: function(response_data) {
                        ///var id = response_data.split("_")[0];
                        //var name = response_data.split("_")[1];
                        //var i;
                        $("#reciept_book_rev_no").val(response_data.trim());
                    }
                });
                });

                    $("#reciept_page_no").autocomplete("receiptBookCont.do", {
                    extraParams: {
                        action1: function() { return "getReceiptNo"},
                        receipt_book_no: function(){return doc.getElementById("reciept_book_no").value ;}
                    }
                });

                    $("#select_by_circular").click(function(){
                        //alert("sgdjh");
                        $("#offence_type_selected").empty();
                        // $("#offence_type_selected").flushCache();
                    });
                    $("#select_by_centeral").click(function(){
                        $("#offence_type_selected").empty();
                        // $("#offence_type_selected").flushCache();
                    });
                    $("#select_by_mp").click(function(){
                        $("#offence_type_selected").empty();
                        //  $("#offence_type_selected").flushCache();
                    });
                });
                var btn_id = "";
                function makeEditable(id) {
                    btn_id = id;
                    doc.getElementById("jarayam_no").disabled=false;
                    doc.getElementById("offender_name").disabled=false;
                    doc.getElementById("father_name").disabled = false;
                    //doc.getElementById("vehicle_type").disabled = false;
                    doc.getElementById("vehicle_no").disabled=false;
                    doc.getElementById("offender_license_no").disabled = false;
                    doc.getElementById("city_name").disabled = false;
                    doc.getElementById("zone").disabled = false;
                    doc.getElementById("offence_place").disabled = false;
                    doc.getElementById("process_type").disabled = false;
                    
                    doc.getElementById("offender_age").disabled = false;
                    doc.getElementById("case_no").disabled = false;
                    doc.getElementById("case_date").disabled = false;
                    doc.getElementById("deposited_amount").disabled = false;
                    doc.getElementById("reciept_no").disabled = false;
                    doc.getElementById("reciept_book_no").disabled = false;
                    doc.getElementById("reciept_page_no").disabled = false;
                    doc.getElementById("offence_date").disabled = false;
                    doc.getElementById("no_of_offence").disabled = false;
                    doc.getElementById("relation_type").disabled = false;
                    doc.getElementById("salutation").disabled = false;
                    doc.getElementById("relative_name").disabled = false;
                    doc.getElementById("offender_city").disabled = false;
                    doc.getElementById("offender_address").disabled = false;
                    doc.getElementById("offender_age").disabled = false;
                    doc.getElementById("case_no").disabled = false;
                    doc.getElementById("case_date").disabled = false;
                    doc.getElementById("remark").disabled = false;
                    //doc.getElementById("is_owner_license").disabled = false;
                    //doc.getElementById("is_not_owner_license").disabled = false;
                    //doc.getElementById("offence_type_selected").disabled = false;
                    // doc.getElementById("act_type_selected").disabled = false;
                    // doc.getElementById("penalty_amount").disabled = false;
                    // doc.getElementById("act_origin").readOnly=true;
                    if(id == 'NEW') {
                        $("#message").html("");
                        //doc.getElementById("offence_date").value = getCurrentDate();
                        doc.getElementById("reciept_no").readOnly = false;
                        doc.getElementById("reciept_book_no").readOnly = false;
                        doc.getElementById("reciept_page_no").readOnly = false;
                        doc.getElementById("process_type").value = "";
                        doc.getElementById("no_of_offence").value = 1;
                        showStructureDiv();
                        changeValue();
                        showSelectedDiv("select_by_officer_name");
                        $("#traffic_police_id").val("0");
                        doc.getElementById("SAVE").disabled = false;
                        doc.getElementById("EDIT").disabled = true;
                        doc.getElementById("DELETE").disabled = true;
                        doc.getElementById("SAVE AS NEW").disabled = true;
                        setDefaultColor(doc.getElementById("noOfRowsTraversed").value, 28);
                        //doc.getElementById("select").style.display="";
                        $("#offence_date").val(getCurrentDate());

                    }
                    if(id == 'EDIT') {                        
                        doc.getElementById("reciept_no").readOnly = true;
                        doc.getElementById("reciept_book_no").readOnly = true;
                        doc.getElementById("reciept_page_no").readOnly = true;
                        doc.getElementById("SAVE").disabled = false;
                        doc.getElementById("DELETE").disabled=true;
                        doc.getElementById("SAVE AS NEW").disabled=false;
                    }
                    //doc.getElementById("SAVE").disabled = false;
                    //   doc.getElementById("get_item").disabled = false;

                }

                function setStatus(id) {

                    if(id == 'SAVE') {

                        doc.getElementById("clickedButton").value = "Save";

                    }
                    else if(id == 'DELETE'){
                        doc.getElementById("clickedButton").value = "Delete";
                        //alert(doc.getElementById("clickedButton").value);
                        
                    }
                    else{
                        doc.getElementById("clickedButton").value = "Save As New";;
                    }
                }
                function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                    for(var i = 0; i < noOfRowsTraversed; i++) {
                        for(var j = 1; j <= noOfColumns; j++) {
                            doc.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                        }
                    }
                }
                function fillColumns(id) {
                    var noOfRowsTraversed = doc.getElementById("noOfRowsTraversed").value;
                  
                    //alert(noOfRowsTraversed);
                    var noOfColumns = 28;
                    var columnId = id;
                <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                        var lowerLimit, higherLimit, rowNum = 0;
                        for(var i = 0; i < noOfRowsTraversed; i++) {
                            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                            rowNum++;
                            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                        }
                        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                        // alert(lowerLimit);
                        var t1id = "t1c";
                 
                        doc.getElementById("traffic_police_id").value= doc.getElementById("traffic_police_id"+rowNum).value;
                        //var x= doc.getElementById("traffic_police_id")
                        //alert(x);
                        doc.getElementById("book_no").value = doc.getElementById(t1id +(lowerLimit+1)).innerHTML;
                        doc.getElementById("book_revision_no").value = doc.getElementById(t1id +(lowerLimit+2)).innerHTML;
                        doc.getElementById("officer_name_selected").value = doc.getElementById(t1id +(lowerLimit+3)).innerHTML;
                        doc.getElementById("jarayam_no").value = doc.getElementById(t1id +(lowerLimit+4)).innerHTML;
                        var offence_date = doc.getElementById(t1id +(lowerLimit+5)).innerHTML;
                        //datePlus = doc.getElementById("complaint_date").value;
                        var pattern = /(\d{4})\-(\d{2})\-(\d{2})/;
                        var pattern1 = /(\d{1})\/(\d{1})\/(\d{2})/;
                        var pattern2 = /(\d{1})\/(\d{2})\/(\d{2})/;
                        var date = new Date(offence_date.replace(pattern1,'20$3-0$1-0$2'));
                        var bool_val = offence_date.match(pattern);
                        var bool_val1 = offence_date.match(pattern1);
                        if(offence_date.match(pattern))
                            offence_date = offence_date.replace(pattern,'$3-$2-$1');
                        else if(offence_date.match(pattern1))
                            offence_date = offence_date.replace(pattern1,'0$2-0$1-20$3');
                        else
                            offence_date = offence_date.replace(pattern2,'$2-0$1-20$3');
                        doc.getElementById("offence_date").value = offence_date;

                        doc.getElementById("vehicle_no").value = doc.getElementById(t1id +(lowerLimit+6)).innerHTML;
                        doc.getElementById("offender_license_no").value = doc.getElementById(t1id +(lowerLimit+7)).innerHTML;

                        doc.getElementById("offender_name").value = doc.getElementById(t1id +(lowerLimit+8)).innerHTML;
                        doc.getElementById("father_name").value = doc.getElementById(t1id +(lowerLimit+9)).innerHTML;
                        doc.getElementById("offender_age").value = doc.getElementById(t1id +(lowerLimit+10)).innerHTML;
                        doc.getElementById("offender_address").value = doc.getElementById(t1id +(lowerLimit+11)).innerHTML;

                        doc.getElementById("city_name").value = doc.getElementById(t1id +(lowerLimit+12)).innerHTML;
                        doc.getElementById("zone").value = doc.getElementById(t1id +(lowerLimit+13)).innerHTML;
                        doc.getElementById("offence_place").value = doc.getElementById(t1id +(lowerLimit+14)).innerHTML;
                        //  doc.getElementById("act_origin").value = doc.getElementById(t1id +(lowerLimit+9)).innerHTML;
                        //doc.getElementById("offence_type_selected").value = doc.getElementById(t1id +(lowerLimit+10)).innerHTML;
                        //  doc.getElementById("act_type_selected").value = doc.getElementById(t1id +(lowerLimit+11)).innerHTML;
                        // doc.getElementById("penalty_amount").value = doc.getElementById(t1id +(lowerLimit+12)).innerHTML;
                        // doc.getElementById("vehicle_type").value = doc.getElementById(t1id +(lowerLimit+13)).innerHTML;
                        
                        doc.getElementById("reciept_no").value = doc.getElementById(t1id +(lowerLimit+15)).innerHTML;
                        
                        doc.getElementById("reciept_book_no").value = doc.getElementById(t1id +(lowerLimit+16)).innerHTML;
                        doc.getElementById("reciept_book_rev_no").value = doc.getElementById(t1id +(lowerLimit+17)).innerHTML;
                        doc.getElementById("reciept_page_no").value = doc.getElementById(t1id +(lowerLimit+18)).innerHTML;
                        var deposit_amount = doc.getElementById(t1id +(lowerLimit+19)).innerHTML;
                        doc.getElementById("process_type").value = doc.getElementById(t1id +(lowerLimit+20)).innerHTML;
                        
                        doc.getElementById("relation_type").value = doc.getElementById(t1id +(lowerLimit+21)).innerHTML;
                        
                        doc.getElementById("case_no").value = doc.getElementById(t1id +(lowerLimit+22)).innerHTML;
                        var case_date = doc.getElementById(t1id +(lowerLimit+23)).innerHTML;
                        if(case_date.match(pattern))
                            case_date = case_date.replace(pattern,'$3-$2-$1');
                        else if(case_date.match(pattern1))
                            case_date = case_date.replace(pattern1,'0$2-0$1-20$3');
                        else
                            case_date = case_date.replace(pattern2,'$2-0$1-20$3');
                        doc.getElementById("case_date").value = case_date;
                        doc.getElementById("remark").value = doc.getElementById(t1id +(lowerLimit+24)).innerHTML;

                        var tp_id = doc.getElementById("traffic_police_id").value;
                        var no_of_offence = doc.getElementById(tp_id).value;
                        $("#no_of_offence").val(no_of_offence);
                        showStructureDiv();                     

                        for(var i = 0; i < no_of_offence; i++){
                            //$("#act_origin"+i).val(doc.getElementById(t1id +(lowerLimit+23)).innerHTML.trim());//offence_typ
                            var act_origin = $("#act_origin"+tp_id+"_"+(i+1)).html();
                            var of_code = $("#offence_cd"+tp_id+"_"+(i+1)).html();
                            var of_type = $("#offence_typ"+tp_id+"_"+(i+1)).html();
                            $("#act_origin"+i).val(act_origin);
                            $("#offence_code"+i).val(of_code);
                            $("#offence_type"+i).val(of_type);
                        }
                        doc.getElementById("deposited_amount").value = deposit_amount;
                        doc.getElementById("salutation").value = doc.getElementById(t1id +(lowerLimit+25)).innerHTML;
                        doc.getElementById("relative_name").value = doc.getElementById(t1id +(lowerLimit+26)).innerHTML;
                        doc.getElementById("offender_city").value = doc.getElementById(t1id +(lowerLimit+27)).innerHTML;

                        //doc.getElementById("act_origin").value = doc.getElementById(t1id +(lowerLimit+20)).innerHTML;
                        //var s = doc.getElementById("act_origin").value.trim();
                        
                        /*if(s!=""){
                            if(s == 'Circular Moter vehicle Rules'){
                                //alert(s);
                                $("#select_by_circular").attr("checked", true);
                                showSelectedDiv("select_by_circular");

                            }
                            if(s=='Centeral Moter vehicle Rules 1989'){
                                $("#select_by_centeral").attr("checked", true);
                                showSelectedDiv("select_by_centeral");
                            }
                            if(s=='Madhya pradesh Moter vehicle Rules 1994'){
                                $("#select_by_mp").attr("checked", true);
                                showSelectedDiv("select_by_mp");
                            }
                        }*/
                        for(var i = 0; i < noOfColumns; i++) {

                            doc.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                        }
                        doc.getElementById("EDIT").disabled = false;
                        if(!doc.getElementById("SAVE").disabled) {
                            doc.getElementById("SAVE AS NEW").disabled = false;
                            // if save button is already enabled, then make edit, and delete button enabled too.
                            //doc.getElementById("DELETE").disabled = false;
                        }
                        changeValue();
                        doc.getElementById("reciept_book_no").disabled = true;
                        doc.getElementById("reciept_page_no").disabled = true;
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

                    function trafficPoliceVerify(){//debugger;
                        try{
                            //alert("fgd");
                            var result ,offender_name="";

                            //$("#book_no").val($("#book_no_selected").val());
                            //$("#officer_name_selected").val($("#officer_name").val());

                            var checkSelectByval = "";
                            var selectBy = $('input[name=selectBy]:checked').val();
                            if(selectBy == "Officer Name")
                                checkSelectByval = $("#officer_name_selected").val();//Select officer Name
                            else if(selectBy == "Book No")
                                checkSelectByval = $("#book_no_selected").val();//Select Book No
                           var id;
                            
                            if(doc.getElementById("clickedButton").value == 'Save' || doc.getElementById("clickedButton").value == 'Save As New') {
                                //alert(doc.getElementById("clickedButton").value);

                                if(checkSelectByval == "Select officer Name" || checkSelectByval == "Select Book No") {
                                    var msgval = "";
                                    if(checkSelectByval == "Select officer Name"){
                                        msgval = "Officer Name";
                                        id = "officer_name_selected";
                                    }
                                    else{
                                        msgval = "Book No";
                                        id = "book_no_selected";
                                    }
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> " + msgval + " is required...</b></td>");
                                    doc.getElementById(id).focus();
                                    return false; // code to stop from submitting the form2.
                                }

                                var jarayam_no = doc.getElementById("jarayam_no").value;
                                if(myLeftTrim(jarayam_no).length == 0) {

                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> Jarayam No is required...</b></td>");
                                    doc.getElementById("jarayam_no").focus();
                                    return false; // code to stop from submitting the form2.
                                }

                                var vehicle_no= doc.getElementById("vehicle_no").value;
                                var offender_license_no= doc.getElementById("offender_license_no").value;
                                if(myLeftTrim(vehicle_no).length == 0 && myLeftTrim(offender_license_no).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Vehicle No. or License No. is required...</b></td>");
                                    doc.getElementById("vehicle_no").focus();
                                    return false; // code to stop from submitting the form2.
                                }

                                var offender_name = doc.getElementById("offender_name").value;
                                if(myLeftTrim(offender_name).length == 0) {

                                    $("#message").html("<td colspan='6' bgcolor='coral'><b> offender name is required...</b></td>");
                                    doc.getElementById("offender_name").focus();
                                    return false; // code to stop from submitting the form2.
                                }
                          
                                //                                var vehicle_type = doc.getElementById("vehicle_type").value;
                                //                                if(myLeftTrim(vehicle_type ).length == 0) {
                                //                                    $("#message").html("<td colspan='6' bgcolor='coral'><b>vehicle type  is required...</b></td>");
                                //                                    doc.getElementById("vehicle_type").focus();
                                //                                    return false; // code to stop from submitting the form2.
                                //                                }      
                                //alert(offender_license_no);
//                                if(myLeftTrim(offender_license_no).length == 0) {
//                                    $("#message").html("<td colspan='6' bgcolor='coral'><b>offender license no is required...</b></td>");
//                                    doc.getElementById("offender_license_no").focus();
//                                    return false;
//                                }
                                var offence_place = doc.getElementById("offence_place").value;
                                if(myLeftTrim(offence_place).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Offence place is required...</b></td>");
                                    doc.getElementById("offence_place").focus();
                                    return false; // code to stop from submitting the form2.
                                }
                                var city_name = doc.getElementById("city_name").value;
                                if(myLeftTrim(city_name).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b>City is required...</b></td>");
                                    doc.getElementById("city_name").focus();
                                    return false; // code to stop from submitting the form2.
                                }
                                var zone = doc.getElementById("zone").value;
                                if(myLeftTrim(zone).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Zone is required...</b></td>");
                                    doc.getElementById("zone").focus();
                                    return false; // code to stop from submitting the form2.
                                }
                                var deposited_amount = doc.getElementById("deposited_amount").value;
                                //alert(deposited_amount);
                                if(myLeftTrim(deposited_amount).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Deposited amount is required...</b></td>");
                                    doc.getElementById("deposited_amount").focus();
                                    return false; // code to stop from submitting the form2.
                                }
                            
                                var reciept_no = doc.getElementById("reciept_no").value;
                                //alert(reciept_no);
                                if(myLeftTrim(reciept_no).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Challan no  is required...</b></td>");
                                    doc.getElementById("reciept_no").focus();
                                    return false; // code to stop from submitting the form2.                                    
                                }
                                var offence_date = doc.getElementById("offence_date").value;
                                //alert(offence_date);
                                if(myLeftTrim(offence_date ).length == 0) {
                                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Offnce date  is required...</b></td>");
                                    doc.getElementById("offence_date").focus();
                                    return false; // code to stop from submitting the form2.                                    
                                }

                                var process_type = doc.getElementById("process_type").value;
                                if(process_type == "Court"){
                                    var case_no = doc.getElementById("case_no").value;
                                    if(myLeftTrim(case_no ).length == 0) {
                                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Case No. is required...</b></td>");
                                        doc.getElementById("case_no").focus();
                                        return false; // code to stop from submitting the form2.
                                    }
                                    
                                    var case_date = doc.getElementById("case_date").value;
                                    if(myLeftTrim(case_date ).length == 0) {
                                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Case Date is required...</b></td>");                                    
                                        doc.getElementById("case_date").focus();
                                        return false; // code to stop from submitting the form2.
                                    }
                                }
                                //                                var act_origin = doc.getElementById("act_origin").value;
                                //                                if(myLeftTrim(act_origin).length == 0) {
                                //                                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Act origin  is required...</b></td>");
                                //                                    return false; // code to stop from submitting the form2.
                                //                                    doc.getElementById("act_origin").focus();
                                //                                }

                                if(doc.getElementById("clickedButton").value == 'Save As New'){
                                    result = confirm("Are you sure you want to save it as New record?")
                                    //return result;
                                }
                            } else {
                                // doc.getElementById("DELETE").disabled = true;
                                result = confirm("Are you sure you want to delete this record?");
                            }
                            //$("#offenceCodeValues").val("");
                           var offenceCodeValues = $("#offenceCodeValues").val("");
                                var no_of_offence = $("#no_of_offence").val();
                                for(var i = 0;i < no_of_offence;i++){
                                    var offence_code = $("#offence_code"+i).val();
                                    if(offence_code === undefined){
                                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Select no of offence...</b></td>");
                                        doc.getElementById("no_of_offence").focus();
                                        return false;
                                    }
                                    if(myLeftTrim(offence_code).length == 0) {
                                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Offence Code is required...</b></td>");
                                        doc.getElementById("offence_code"+i).focus();
                                        return false; // code to stop from submitting the form2.
                                    }else{
                                        offenceCodeValues = offenceCodeValues + "_" + offence_code;
                                    }
                                }
                                $("#offenceCodeValues").val(offenceCodeValues);

                           //if(doc.getElementById("traffic_police_id").value == 0){

                                /*var table =doc.getElementById("insertTable1");
                                var rowCount = table.rows.length;
                                var arr=[];
                                var i=0;
                                var offence="";
                                for(i=1; i<=rowCount-1; i++) {
                                    if(doc.getElementById("check_print"+i).checked==true){
                                       alert( doc.getElementById("check_print"+i).value);
                                        
                                        arr.push( $("#offence_type_selected"+i).val());
                                        offence=arr.toString();
                                         alert(offence);
                                    }
                      
                                }*/
                                /*addRow($("#offender_name").val(),$("#vehicle_no").val() ,$("#offender_license_no").val(),
                                $("#offence_place").val() , $("#reciept_no").val(),$("#offence_date").val(),$("#deposited_amount").val(),
                                $("#book_no").val(),$("#officer_name_selected").val(),$("#book_revision_no").val()
                                ,$("#book_no_selected").val(),$("#officer_name").val(),$("#revision_no").val(),$("#city_name").val(),
                                $("#zone").val(),$("#act_origin").val(),$("#jarayam_no").val(),$("#process_type").val(),$("#relation_type").val(),
                                $("#offender_age").val(),$("#case_no").val(),$("#case_date").val(),$("#relative_name").val(),$("#offender_city").val()
                                ,$("#offender_address").val(),$("#salutation").val(),offenceCodeValues);

                              
                                return false;
                            }*/
                        }catch(e){
                            alert("error is"+e);
                            return false;
                        }
                        return result;
                    }


                    function addRow(offender_name,vehicle_no,offender_license_no,offence_place,
                    reciept_no,offence_date,deposited_amount,officer_name_selected,book_no,book_revision_no,
                    data1,data2,data3,city_name,zone,act_origin,jarayam_no,process_type,relation_type,offender_age,
                    case_no,case_date,relative_name,offender_city,offender_address,salutation,offence) {
                  
                        var arr = offence.split(",");
                      
                        var table = doc.getElementById('insertTable');
                        var rowCount = table.rows.length;                // alert(rowCount);
                        var row = table.insertRow(rowCount);

                        var cell1 = row.insertCell(0);
                        cell1.innerHTML = rowCount;
                        var element1 = doc.createElement("input");
                        element1.type = "hidden";
                        element1.name = "traffic_police_id";
                        element1.id = "traffic_police_id"+rowCount;
                        element1.size = 1;
                        element1.value = 1;
                        element1.readOnly = false;
                        cell1.appendChild(element1);
                        var element1 = doc.createElement("input");
                        element1.type = "hidden";
                        element1.name = "salutation";
                        element1.id = "salutation"+rowCount;
                        element1.size = 6;
                        element1.value = salutation;
                        element1.readOnly = false;
                        cell1.appendChild(element1);

                        var element1 = doc.createElement("input");
                        element1.type = "checkbox";
                        element1.name = "check_print";
                        element1.id = "check_print"+rowCount;                //element1.size = 1;
                        element1.value = "Yes";
                        element1.checked = true;
                        element1.setAttribute('onclick', 'singleCheckUncheck('+rowCount+')');
                        cell1.appendChild(element1);

                        var cell2 = row.insertCell(1);
                        //cell2.innerHTML = $.trim(offender_name);
                        var element2 = doc.createElement("input");
                        element2.type = "text";
                        element2.name = "jarayam_no";
                        element2.id = "jarayam_no"+rowCount;
                        element2.size = 20;
                        element2.value =jarayam_no;
                        // element2.attr('readonly','readonly');
                        element2.setAttribute('onfocus', '$(this).blur()');
                        cell2.appendChild(element2);


                        var cell3 = row.insertCell(2);
                        //cell2.innerHTML = $.trim(offender_name);
                        var element3 = doc.createElement("input");
                        element3.type = "text";
                        element3.name = "offender_name";
                        element3.id = "offender_name"+rowCount;
                        element3.size = 20;
                        element3.value =offender_name;
                        // element2.attr('readonly','readonly');
                        element3.setAttribute('onfocus', '$(this).blur()');
                        cell3.appendChild(element3);

                        var cell4 = row.insertCell(3);
                        //cell4.innerHTML = $.trim(vehicle_type);
                        var element4 = doc.createElement("input");
                        element4.type = "text";
                        element4.name = "vehicle_no";
                        element4.id = "vehicle_no"+rowCount;
                        element4.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element4.value =vehicle_no
                        element4.setAttribute('onfocus', '$(this).blur()');
                        cell4.appendChild(element4);
                        //alert("vt");
                        var cell5 = row.insertCell(4);
                        //cell5.innerHTML = $.trim(offender_license_no);
                        var element5 = doc.createElement("input");
                        element5.type = "text";
                        element5.name = "offender_license_no";
                        element5.id = "offender_license_no"+rowCount;
                        element5.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element5.value =offender_license_no;
                        element5.setAttribute('onfocus', '$(this).blur()');
                        cell5.appendChild(element5);

                        var cell6 = row.insertCell(5);
                        //cell6.innerHTML = $.trim(vehicle_no);
                        var element6 = doc.createElement("input");
                        element6.type = "text";
                        element6.name = "offence_place";
                        element6.id = "offence_place"+rowCount;
                        element6.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element6.value =offence_place;
                        element6.setAttribute('onfocus', '$(this).blur()');
                        cell6.appendChild(element6);

           
                        

                        var cell7 = row.insertCell(6);
                        //cell9.innerHTML = $.trim(act_type_selected);
                        var element7 = doc.createElement("input");
                        element7.type = "text";
                        element7.name = "process_type";
                        element7.id = "process_type"+rowCount;
                        element7.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element7.value =process_type;
                        element7.setAttribute('onfocus', '$(this).blur()');
                        cell7.appendChild(element7);
                       
                        var cell8= row.insertCell(7);
                        //cell7.innerHTML = $.trim(penalty_amount);
                        var element8 = doc.createElement("input");
                        element8.type = "text";
                        element8.name = "relation_type";
                        element8.id = "relation_type"+rowCount;
                        element8.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element8.value =relation_type;
                        element8.setAttribute('onfocus', '$(this).blur()');
                        cell8.appendChild(element8);

                        var cell9= row.insertCell(8);
                        //cell7.innerHTML = $.trim(penalty_amount);
                        var element9 = doc.createElement("input");
                        element9.type = "text";
                        element9.name = "relative_name";
                        element9.id = "relative_name"+rowCount;
                        element9.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element9.value =relative_name;
                        element9.setAttribute('onfocus', '$(this).blur()');
                        cell9.appendChild(element9);


                         var cell10= row.insertCell(9);
                        //cell7.innerHTML = $.trim(penalty_amount);
                        var element10 = doc.createElement("input");
                        element10.type = "text";
                        element10.name = "offender_city";
                        element10.id = "offender_city"+rowCount;
                        element10.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element10.value =offender_city;
                        element10.setAttribute('onfocus', '$(this).blur()');
                        cell10.appendChild(element10);

                         var cell11= row.insertCell(10);
                        //cell7.innerHTML = $.trim(penalty_amount);
                        var element11 = doc.createElement("input");
                        element11.type = "text";
                        element11.name = "offender_address";
                        element11.id = "offender_address"+rowCount;
                        element11.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element11.value =offender_address;
                        element11.setAttribute('onfocus', '$(this).blur()');
                        cell11.appendChild(element11);



                        var cell12= row.insertCell(11);
                        //cell7.innerHTML = $.trim(penalty_amount);
                        var element12 = doc.createElement("input");
                        element12.type = "text";
                        element12.name = "offender_age";
                        element12.id = "offender_age"+rowCount;
                        element12.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element12.value =offender_age;
                        element12.setAttribute('onfocus', '$(this).blur()');
                        cell12.appendChild(element12);


                        var cell13= row.insertCell(12);
                        //cell7.innerHTML = $.trim(penalty_amount);
                        var element13 = doc.createElement("input");
                        element13.type = "text";
                        element13.name = "case_no";
                        element13.id = "case_no"+rowCount;
                        element13.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element13.value =case_no;
                        element13.setAttribute('onfocus', '$(this).blur()');
                        cell13.appendChild(element13);


                        var cell14= row.insertCell(13);
                        //cell7.innerHTML = $.trim(penalty_amount);
                        var element14 = doc.createElement("input");
                        element14.type = "text";
                        element14.name = "case_date";
                        element14.id = "case_date"+rowCount;
                        element14.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element14.value =case_date;
                        element14.setAttribute('onfocus', '$(this).blur()');
                        cell14.appendChild(element14);

                        var cell15= row.insertCell(14);
                        //cell7.innerHTML = $.trim(penalty_amount);
                        var element15 = doc.createElement("input");
                        element15.type = "text";
                        element15.name = "reciept_no";
                        element15.id = "reciept_no"+rowCount;
                        element15.size = 6;
                        element15.value =reciept_no;
                        element15.setAttribute('onfocus', '$(this).blur()');
                        cell15.appendChild(element15);


                        var cell16 = row.insertCell(15);
                        //cell10.innerHTML = $.trim(reciept_no);
                        var element16 = doc.createElement("input");
                        element16.type = "text";
                        element16.name = "offence_date";
                        element16.id = "offence_date"+rowCount;
                        element16.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element16.value =offence_date;
                        element16.setAttribute('onfocus', '$(this).blur()');
                        cell16.appendChild(element16);

                        var cell17 = row.insertCell(16);
                        //cell11.innerHTML = $.trim(offence_date);
                        var element17 = doc.createElement("input");
                        element17.type = "text";
                        element17.name = "deposited_amount";
                        element17.id = "deposited_amount"+rowCount;
                        element17.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element17.value =deposited_amount;
                        element17.setAttribute('onfocus', '$(this).blur()');
                        cell17.appendChild(element17);

                       

                        var cell18= row.insertCell(17);
                        //cell12.innerHTML = $.trim(deposited_amount);
                        var element18= doc.createElement("input");
                        element18.type = "text";
                        element18.name = "officer_name_selected";
                        element18.id = "officer_name_selected"+rowCount;
                        element18.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());\
                        if(doc.getElementById('select_by_officer_name').checked == true){
                        
                   
                            element18.value =book_no;
                            element18.setAttribute('onfocus', '$(this).blur()');
                        }
                        else if(doc.getElementById('select_by_book_no').checked == true)
                        {
                                                  
                            element18.value =data2;
                            element18.setAttribute('onfocus', '$(this).blur()');
                   
                        }
                        cell18.appendChild(element18);

                        var cell19 = row.insertCell(18);
                        var element19= doc.createElement("input");
                        element19.type = "text";
                        element19.name = "book_no";
                        element19.id = "book_no"+rowCount;
                        element19.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        if(doc.getElementById('select_by_officer_name').checked == true)
                        {
                            element19.value =officer_name_selected;
                            element19.setAttribute('onfocus', '$(this).blur()');
                        }
                        else if(doc.getElementById('select_by_book_no').checked == true)
                        {
                            element19.value =data1;
                            element19.setAttribute('onfocus', '$(this).blur()');

                        }
                        cell19.appendChild(element19);

                        var cell20 = row.insertCell(19);
                        //cell12.innerHTML = $.trim(deposited_amount);
                        var element20= doc.createElement("input");
                        element20.type = "text";
                        element20.name = "book_revision_no";
                        element20.id = "book_revision_no"+rowCount;
                        element20.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        if(doc.getElementById('select_by_officer_name').checked == true)
                        {
                            element20.value =book_revision_no;
                            element20.setAttribute('onfocus', '$(this).blur()');
                        }
                        else if(doc.getElementById('select_by_book_no').checked == true)
                        {
                            element20.value =data3;
                            element20.setAttribute('onfocus', '$(this).blur()');
                  
                        }
                        cell20.appendChild(element20);

                        var cell21 = row.insertCell(20);
                        //cell12.innerHTML = $.trim(deposited_amount);
                        var element21= doc.createElement("input");
                        element21.type = "text";
                        element21.name = "city_name";
                        element21.id = "city_name"+rowCount;
                        element21.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element21.value =city_name;
                        element21.setAttribute('onfocus', '$(this).blur()');
                        cell21.appendChild(element21);

                        var cell22 = row.insertCell(21);
                        //cell12.innerHTML = $.trim(deposited_amount);
                        var element22= doc.createElement("input");
                        element22.type = "text";
                        element22.name = "zone";
                        element22.id = "zone"+rowCount;
                        element22.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element22.value =zone;
                        element22.setAttribute('onfocus', '$(this).blur()');
                        cell22.appendChild(element22);

                        var cell23 = row.insertCell(22);
                        var element23= doc.createElement("input");
                        element23.type = "text";
                        element23.name = "act_origin";
                        element23.id = "act_origin"+rowCount;
                        element23.size = 6;
                        act_origin = doc.getElementById('act_origin0').value;
                        /*if (doc.getElementById('select_by_circular').checked) {
                            act_origin = doc.getElementById('select_by_circular').value;
                            // alert(act_origin);
                        } if (doc.getElementById('select_by_centeral').checked) {
                            act_origin = doc.getElementById('select_by_centeral').value;
                            //  alert(act_origin);
                        } if (doc.getElementById('select_by_mp').checked) {
                            act_origin = doc.getElementById('select_by_mp').value;
                            //  alert(act_origin);
                        }*/
                        element23.value =act_origin;
                        element23.setAttribute('onfocus', '$(this).blur()');
                        cell23.appendChild(element23);
                        //  for(var i=0;i<arr.length;i++){
                        // var offence=arr[i];
                        var cell24 = row.insertCell(23);
                        //cell12.innerHTML = $.trim(deposited_amount);
                        var element24= doc.createElement("input");
                        element24.type = "text";
                        element24.name = "offence_type_selected";
                        element24.id = "offence_type_selected"+rowCount;
                        element24.size = 6;
                        //  alert( $('input[name=is_internal]:checked').val());
                        element24.value =offence;
                        element24.setAttribute('onfocus', '$(this).blur()');
                        cell24.appendChild(element24);
                        //  }
                        // arr.length=0;



                        var height = (rowCount * 40)+ 60;

                        doc.getElementById("autoCreateTableDiv").style.visibility = "visible";
                        doc.getElementById("autoCreateTableDiv").style.height = height+'px';
                        //                        for(var i=0;i<arr.length;i++){
                        //                            var offence=arr[i];
                        //                            addRow3(offence);
                        //                        }
                        //                        arr.length=0;
                        //                        alert(arr.length);
                        //checkValue();
                        if(rowCount == 1){
                            $('#checkUncheckAll').attr('hidden', true);
                        }else{
                            $('#checkUncheckAll').attr('hidden', false);
                        }

                    }
                    function deleteRow() {
                        try {
                       
                            var table =doc.getElementById("insertTable");
                            //alert(table);
                            var rowCount = table.rows.length;
                            for(var i=1; i<rowCount-1; i++) {
                                table.deleteRow(1);
                            }
                            doc.getElementById("autoCreateTableDiv").style.visibility = "visible";
                            doc.getElementById("autoCreateTableDiv").style.height = '0px';
                        }catch(e) {
                            alert(e);
                        }
                    }
                    function setCheck(rowCount){
                        if($("is_internal"+rowCount).val() == 'NO'){
                            $("#org_name"+rowCount).flushCache();
                            $("#org_name"+rowCount).val('');
                            $("#org_name"+rowCount).focus();
                        }else
                            $("#org_name"+rowCount).val('${homeOrgName[0]}');
                    }
                    function singleCheckUncheck(id){
                        // alert(id);
                        // alert($("#transaction_id"+id).val());
                        if ($('#check_print'+id).is(':checked')) {
                            $("#transaction_id"+id).val("1");
                        }else{
                            $("#transaction_id"+id).val("0");
                        }
                        if($('form input[type=checkbox]:checked').size() == 0){
                            $("#addAllRecords").attr("disabled", "disabled");
                        }else{
                            $("#addAllRecords").removeAttr("disabled");
                        }
                        doc.getElementById("offence_type_selected"+id).value;
                    }

                    function checkUncheck(id){
                        var table = doc.getElementById('insertTable');
                        var rowCount = table.rows.length;
                        if(id == 'Check All'){
                            $('input[name=check_print]').attr("checked", true);
                            for(var i=1;i<rowCount;i++){
                                $("#transaction_id"+i).val("1");
                            }
                            $('#checkUncheckAll').val('Uncheck All');
                            $("#addAllRecords").removeAttr("disabled");
                        }else{
                            $('input[name=check_print]').attr("checked", false);
                            $('#checkUncheckAll').val('Check All');
                            $("#addAllRecords").attr("disabled", "disabled");
                            for(var i=1;i<rowCount;i++){
                                $("#transaction_id"+i).val("2");
                            }
                        }
                    }

                    function IsNumeric(id) {
                        var strString=    doc.getElementById(id).value;
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
                                $("#message").html("<td colspan='8' bgcolor='coral'><b>Value should be Numeric </b></td>");
                                blnResult = false;
                            }else{
                                $("#message").html("");
                            }
                            /*else{
                                var  time = doc.getElementById(id).value;
                                if(id=="transaction_time_hour"){
                                    if(time>12){
                                        doc.getElementById(id).value="";
                                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Hour should Be less than 12</b></td> ");
                                        return;
                                    }
                                }else{
                                    if(time>59){
                                        doc.getElementById(id).value="";
                                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Min should Be less than 60</b></td>");
                                        return;
                                    }

                                    $("#message").html("");
                                }
                            }*/
                        }
                        return blnResult;
                    }
                    function getCurrentDate(id) {
                        var currentTime = new Date()
                        var month = currentTime.getMonth() + 1
                        var day = currentTime.getDate()
                        var year = currentTime.getFullYear()
                        var hours = currentTime.getHours()
                        var minutes = currentTime.getMinutes()
                        if (minutes < 10){
                            minutes = "0" + minutes
                        }
                        if(hours > 11){
                            $("#transaction_time option").each(function()
                            {
                                if($(this).text() == "PM"){
                                    $(this).attr('selected', true);
                                }
                            });

                        } else {
                            $("#transaction_time option").each(function()
                            {
                                if($(this).text() == "AM"){
                                    $(this).attr('selected', true);
                                }
                            });
                        }//debugger;
                        
                        if (day.toString().length == 1)
                            day = "0" + day;
                        if (month.toString().length == 1)
                            month = "0" + month;

                        var date= day + "-" + month + "-" + year;
                        if(hours > 12){
                            hours=hours-12;

                        }
                        $("#offence_date").val(date);
                        $("#case_date").val(date);
                       return date;
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
                    function getItemDetailList() {
                        var queryString = "task=getItemDetail";
                        var url = "getItemCont?" + queryString;
                        popupwin = openPopUp(url, "item_detail", 500, 1000);
                    }
                    function getItemDetailEnable() {
                        if($("#movement_type").val()!=''&&$("#key_person_name").val()!=''&&$("#home_person_name").val()!=''&&$("#supply_doc_name").val()!=''&&$("#doc_no").val()!=''
                            &&$("#transaction_person_name").val()!=''&&$("#org_name").val()!=''&&$("#transaction_date").val()!=''&&$("#transaction_time_hour").val()!=''&&$("#transaction_time_min").val()!=''){
                            doc.getElementById("get_item").disabled = false;
                        }else{
                            //                    $("#message").html("Please Fill All the entries ");
                        }
                    }

                    function showSelectedDiv(id){
                        if($("#"+id).val()=='Insert'){
                            $("#selectInsert").attr("style", "visibility:visible");
                            
                        }
                        
                        if($("#"+id).val()=='Officer Name'){
                            $("#officer_name_selected").attr("disabled", false);
                            $("book_no").attr("disabled", false);

                            $("#selectByOfficerName").show();
                            $("#selectByChallan").hide();
                            $("#selectByBookNo").hide();
//                            $("#selectByOfficerName").attr("style","visibility:visible");
//                            $("#selectByChallan").attr("style","visibility:collapse");
//                            $("#selectByBookNo").attr("style","visibility:collapse");
                       

                        }
                        if($("#"+id).val()=='Echallan'){
//                            $("#selectByOfficerName").attr("style","visibility:collapse");
//                            $("#selectByChallan").attr("style","visibility:visible");
//                            $("#selectByBookNo").attr("style","visibility:collapse");
                            //                        doc.getElementById("selectByEstimateNo").style.display="none";
                            //
                            //                        doc.getElementById("selectByWorkOrderNo").style.display="table-row";
                            //                        doc.getElementById("selectBySupplierName").style.display="none";


                        }
                        if($("#"+id).val()=='Book No'){
                            $("#officer_name_selected").attr("disabled", true);
                            $("book_no").attr("disabled", true);

                            $("#selectByOfficerName").hide();
                            $("#selectByChallan").hide();
                            $("#selectByBookNo").show();

//                            $("#selectByOfficerName").attr("style","visibility:collapse");
//                            $("#selectByChallan").attr("style","visibility:collapse");
//                            $("#selectByBookNo").attr("style","visibility:visible");
                        }
                        if($("#"+id).val()=='Circular Moter vehicle Rules'){
                            deleteRow1();
                            deleteRow2();
                            //$("#offence_type_selected").empty();
                            //   doc.getElementById("offence_type_selected").disabled = false;
                            // doc.getElementById("act_type_selected").disabled = false;
                            //  doc.getElementById("penalty_amount").disabled = false;
                            // doc.getElementById("vehicle_type").disabled = false;
                            var offence=doc.getElementById("select_by_circular").value;
                            $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getOffenceList&offence="
                                    + offence , success: function(response_data) {
                                    //  $("offence_type_selected").html("");
                                    var arr = response_data.split(",");
                                    var i;
                          
                                    for(i = 1; i < arr.length ; i++) {
                                        //                                        var opt = doc.createElement("option");
                                        //                                      opt.text = $.trim(arr[i]);
                                        //                                        opt.value = $.trim(arr[i]);
                                        //                                        doc.getElementById("offence_type_selected").options.add(opt);

                                        addRow1($.trim(arr[i]));
                                    }
                                }});

                        }
                        if($("#"+id).val()=='Centeral Moter vehicle Rules 1989'){
                            deleteRow1();
                            deleteRow2();
                            //                            $("#offence_type_selected").empty();
                            //                            doc.getElementById("offence_type_selected").disabled = false;
                            //                            doc.getElementById("act_type_selected").disabled = false;
                            //                            doc.getElementById("penalty_amount").disabled = false;
                            //                            doc.getElementById("vehicle_type").disabled = false;
                            var offence=doc.getElementById("select_by_centeral").value;
                            $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getOffenceList&offence="
                                    + offence , success: function(response_data) {
                                    //  $("offence_type_selected").html("");
                                    var arr = response_data.split(",");
                                    //alert(arr);
                                    var i;
                                    for(i =1; i < arr.length ; i++) {
                                        //   var opt = doc.createElement("option");
                                        //                                        opt.text = $.trim(arr[i]);
                                        //                                        opt.value = $.trim(arr[i]);
                                        //
                                        //                             doc.getElementById("offence_type_selected").options.add(opt);
                                        addRow1($.trim(arr[i]));
                                    }
                                }});

                        }
                        if($("#"+id).val()=='Madhya pradesh Moter vehicle Rules 1994'){
                            deleteRow1();
                            deleteRow2();
                            //                            $("#offence_type_selected").empty();
                            //                            doc.getElementById("offence_type_selected").disabled = false;
                            //                            doc.getElementById("act_type_selected").disabled = false;
                            //                            doc.getElementById("penalty_amount").disabled = false;
                            //                            doc.getElementById("vehicle_type").disabled = false;
                            var offence=doc.getElementById("select_by_mp").value;
                            $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getOffenceList&offence="
                                    + offence , success: function(response_data) {
                                    // $("offence_type_selected").html("");
                                    var arr = response_data.split(",");
                                    //alert(arr);
                                    var i;
                                    for(i =1; i < arr.length ; i++) {
                                        //                                        var opt = doc.createElement("option");
                                        //                                        opt.text = $.trim(arr[i]);
                                        //                                        opt.value = $.trim(arr[i]);
                                        //                                        doc.getElementById("offence_type_selected").options.add(opt);
                                        addRow1($.trim(arr[i]));
                                    }
                                }});

                        }

                    }
                    function fillOffeneceList(){
                        var offence=doc.getElementById("act_origin").value;
                        $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getOffenceList&offence="
                                + offence , success: function(response_data) {
                                $("offence_type_selected").html("");
                                var arr = response_data.split(",");
                                alert(arr);
                                var i;
                                for(i = 0; i < arr.length ; i++) {
                                    var opt = doc.createElement("option");
                                    opt.text = $.trim(arr[i]);
                                    opt.value = $.trim(arr[i]);
                                    doc.getElementById("offence_type_selected").options.add(opt);

                                }
                            }});
                    }
                    function FillVehicleTypeList() {

                        var vehicle = doc.getElementById("vehicle_type").value;

                        if(vehicle == 'Select') {
                            doc.getElementById("vehicle_type").focus();
                        } else {
                            $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getVehicleTypeList&vehicle="
                                    + vehicle , success: function(response_data) {
                                    alert(response_data);
                                    $("#vehicle_type").html("");
                                    
                                    var vehicle_type= response_data;
                                  
                                    doc.getElementById("vehicle_type").value =vehicle_type;
                                }});
                        }
                    }
                    function RecieptNo() {
             
                        var reciept_no=doc.getElementById("book_no").value;
                        var reciept_no1=doc.getElementById("book_no_selected").value;
                        var book_no;
                        if(reciept_no != "")
                            book_no = reciept_no;
                        else
                            book_no = reciept_no1;
                        if(reciept_no==doc.getElementById("book_no").value && btn_id != 'EDIT')
                        {

                            $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getRecieptNo&reciept_no="
                                    + book_no , success: function(response_data) {
                                    //alert(response_data);
                                    $("#msg").html("");
                                    //    var arr= response_data("&#;");
                                    // alert(arr);
                                    var responseArray = response_data.trim().split("_");
                                    var receipt_message= responseArray[0] ;
                                    $("#reciept_no").val(responseArray[1]);
                                    //$("#msg").html(receipt_message);
                                }});
                        }/*else if(reciept_no1==doc.getElementById("book_no_selected").value)  {
                            $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getRecieptNo&reciept_no1="
                                    + reciept_no1 , success: function(response_data) {
                                    //alert(response_data);
                                    $("#msg").html("");
                                    //    var arr= response_data("&#;");
                                    // alert(arr);
                                    var receipt_message= response_data;
                                    //   alert(complaint_no_detail);
                                    // doc.getElementById("message").value =vehicle_type;
                                    $("#msg").html(receipt_message);
                                    //alert(doc.getElementById("message").value);
                                }});
                        }*/
                    }
                    function FillOfficerBookRevisionNo()
                    {
                        var officer_name = doc.getElementById("officer_name").value;
               
                        if(officer_name  == 'Select') {
                            doc.getElementById("officer_name").focus();
                        } else {
                  
                            $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getofficerBookRevisionNolist&officer_name="
                                    + officer_name , success: function(response_data_amount) {
                                    // alert(response_data_amount);
                                    $("#revision_no").html("");
                                    //    var arr= response_data("&#;");
                                    // alert(arr);
                                    var book_revision_no = response_data_amount;
                                    //alert(book_revision_no);
                                    doc.getElementById("revision_no").value =book_revision_no;
                                }

                            });
                        }
                    }
                    function FillPenaltyAmountDetails() {
                        var act = doc.getElementById("act_type_selected").value;

                        if(act == 'Select') {
                            doc.getElementById("act_type_selected").focus();
                        } else {
                            $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getActPenaltyAmountList&act="
                                    + act , success: function(response_data) {
                                    // alert(response_data);
                                    $("#penalty_amount").html("");
                                    //    var arr= response_data("&#;");
                                    // alert(arr);
                                    var penalty_amount= response_data;
                                    //   alert(complaint_no_detail);
                                    doc.getElementById("penalty_amount").value =penalty_amount;
                                    fillActOrigin();
                                }});
                        }
                    }
                    function FillBookRevisionNo() {
                  
                        var book_no = doc.getElementById("book_no").value;
                        //  alert(book_no);
                        if(book_no == 'Select') {
                            doc.getElementById("book_no").focus();
                        } else {

                            $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getbookRevisionNolist&book_no="
                                    + book_no , success: function(response_data_amount) {
                                    //alert(response_data_amount);
                                    $("#book_revision_no").html("");
                                    //    var arr= response_data("&#;");
                                    // alert(arr);
                                    var book_revision_no= response_data_amount;
                                    //  alert( book_revision_no);
                                    doc.getElementById("book_revision_no").value =book_revision_no;
                                }
                    
                            });
                        }
                    }
                    function fill_OfficerName() {

                        var officer_name = doc.getElementById("officer_name_selected").value;

                        if(officer_name == 'Select') {
                            doc.getElementById("officer_name_selected").focus();
                        } else {

                            $.ajax({url: "trafficPoliceCont", data: "action1=getOfficerBookNameList&officer_name="+officer_name, success: function(response_data) {
                                    // alert(response_data);
                                    $("#book_no").html("");
                                    var arr = response_data.split(",");
                                    //alert(arr);
                                    var i;
                                    for(i = 0; i < arr.length ; i++) {
                                        var opt = doc.createElement("option");
                                        //alert(opt);
                                        opt.text = $.trim(arr[i]);
                                        opt.value = $.trim(arr[i]);

                                        doc.getElementById("book_no").options.add(opt);
                                        FillBookRevisionNo();
                                        $("#jarayam_no").focus();
                                    }
                                    RecieptNo();
                                }
                            });
                        }
                    }
                    function fill_BookNo() {

                        var book_no = doc.getElementById("book_no_selected").value;
               
                        if(book_no == 'Select') {
                            doc.getElementById("book_no_selected").focus();
                        } else {

                            $.ajax({url: "trafficPoliceCont", data: "action1=getBookOfficerNameList&book_no="
                                    +book_no, success: function(response_data) {
                                    $("#officer_name").html("");
                                                     
                                    var officer_name= response_data;
                                    //  alert( book_revision_no);
                                    doc.getElementById("officer_name").value =officer_name;
                                    FillOfficerBookRevisionNo();
                                    //$("#book_no").val($("#book_no_selected").val());
                                    //$("#officer_name_selected").val($("#officer_name").val());
                                }
                            });
                        }
                    }
                    function fill_OffenceType() {

                        var offence_type = doc.getElementById("offence_type_selected").value;
                        $.ajax({url: "trafficPoliceCont",data: "action1=getOffenceActTypeList&offence_type=" +offence_type, success: function(response_data){
                                $("#act_type_selected").html("");
                                response_data= $.trim(response_data);
                                var arr = response_data.split(",")
                                var i;
                                for(i =1; i < arr.length ; i++) {
                                    var opt = doc.createElement("option");
                                    opt.text = $.trim(arr[i]);
                                    opt.value = $.trim(arr[i]);

                                    doc.getElementById("act_type_selected").options.add(opt);
                                    // FillPenaltyAmountDetails();
                                    fillVehicleType();
                                    FillPenaltyAmountDetails();
                                }
                            }
                        });
                    }
                    function getOffenceDetail(id) {
                        //.prop('checked')
                        //var offence_type=doc.getElementById("offence_type_selected"+id).value;
                        //var offence_type=$("#offence_type_selected"+id+" :checked").val();
                        //var selectedVal = "";
//var selected = $("input[type='radio'][name='s_2_1_6_0']:checked");
//if (selected.length > 0) {
//    selectedVal = selected.val();//}
                        //if($("#offence_type_selected"+id).is(':checked')){
                        //if(doc.getElementById("offence_type_selected"+1).checked == true){}
                        if(doc.getElementById("act_origin_check_print"+id).checked == true){
                            var offence_type=doc.getElementById("offence_type_selected"+id).value;
                        
                        var queryString = "ajaxRequest=getOffenceActTypeList&offence_type="+offence_type ;
                        $.getJSON("trafficPoliceCont", queryString , function(response_data) {
                            var items = [];               // alert(response_data);
                            $.each(response_data, function(key, value) {
                                items.push(value);
                            });
                            var act= items[0];
                            var penalty_amount= items[1];
                            var vehicle_type=items[2];
                            addRow2(act,penalty_amount,vehicle_type, id);


                        });
                        } else{
                        //deleteActRow(id);.parent().parent()
                        $("#Act_type"+id).remove();
                        
                        }


                    }
                    function fillVehicleType() {
                        var offence_type = doc.getElementById("offence_type_selected").value;
                        $.ajax({url: "trafficPoliceCont",data: "action1=getVehicleType&offence_type=" +offence_type, success: function(response_data){
                                $("#vehicle_type").html("");
                                response_data= $.trim(response_data);
                                var arr = response_data.split(",")
                                var i;
                                for(i =0; i < arr.length ; i++) {
                                    var opt = doc.createElement("option");
                                    opt.text = $.trim(arr[i]);
                                    opt.value = $.trim(arr[i]);

                                    doc.getElementById("vehicle_type").options.add(opt);

                                }
                            }
                        });
                    }
                    function fillActOrigin() {
                        var act = doc.getElementById("act_type_selected").value;
                        if(act == 'Select') {
                            doc.getElementById("act_type_selected").focus();
                        } else {
                            $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getActOrigin&act="
                                    + act , success: function(response_data) {
                                    $("#act_origin").html("");
                                    var act_origin= response_data;
                                    doc.getElementById("act_origin").value =act_origin;
                                }});
                        }
                    }
                    function setValue(id){
                        if($("#"+id).val()=='Search'){
                            $("#clickedButton").val('Search');
                        }
                        else if($("#"+id).val()=='Save'){
                            $("#clickedButton").val('Save');
                        }
                        $("#lowerLimitBottom").val("0");
                        $("#noOfRowsTraversedBottom").val("0");

                    }
                    function getRecordlist(id){
                        var queryString;
                        var searchOfficerName=doc.getElementById("searchOfficerName").value;
                        var searchBookNo=doc.getElementById("searchBookNo").value;
                        var searchOffenceType=doc.getElementById("searchOffenceType").value;
                        var searchActType=doc.getElementById("searchActType").value;
                        var searchVehicleType=doc.getElementById("searchVehicleType").value;
                        var searchVehicleNo=doc.getElementById("searchVehicleNo").value;
                        var searchFromDate=doc.getElementById("searchFromDate").value;
                        var searchToDate=doc.getElementById("searchToDate").value;
                        var searchJarayamNo=doc.getElementById("searchJarayamNo").value;
                        var searchOffenceCode=doc.getElementById("searchOffenceCode").value;
                        if(id == "view_pdf")
                            queryString = "task=PRINTRecordList";
                        else
                            queryString = "task=PrintExcelList";
                        var url = "trafficPoliceCont?" + queryString + "&searchOfficerName="+searchOfficerName+"&searchBookNo="+searchBookNo+
                            "&searchOffenceType="+searchOffenceType+"&searchActType="+searchActType+"&searchActType="+searchActType+"&searchVehicleType="+searchVehicleType+
                            "&searchVehicleNo="+searchVehicleNo+"&searchFromDate="+searchFromDate+"&searchToDate="+searchToDate+"&searchJarayamNo="+searchJarayamNo+"&searchOffenceCode="+searchOffenceCode;
                        popupwin = openPopUp(url, "Division List", 600, 900);
                    }


                    function openPopUp(url, window_name, popup_height, popup_width) {
                        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
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

                    var currentselect = {};

                    function change () {
                        var count=0;
                        var fld = doc.getElementById('offence_type_selected');
                        var values = [];
                        for (var i = 0; i < fld.options.length; i++) {
                            if (fld.options[i].selected) {
                                values.push(fld.options[i].value);
                            }
                            doc.getElementById("offence_type").innerHTML=values;
                            //                             doc.getElementById("offence_table").style.visibility = "visible";
                            //                        var queryString = "task=List";
                            //                        var url = "trafficPoliceCont?" + queryString + "&values="+values;
                        }
                       
                    }
                    function addRow1(offence_type) {

                        var table = doc.getElementById('insertTable1');
                        var rowCount = table.rows.length;                // alert(rowCount);
                        var row = table.insertRow(rowCount);

                        var cell1 = row.insertCell(0);
                        cell1.innerHTML = rowCount;
                        var element1 = doc.createElement("input");
                        element1.type = "checkbox";
                        element1.name = "act_origin_check_print";
                        element1.id = "act_origin_check_print"+rowCount;                //element1.size = 1;
                        element1.value = "Yes";
                        element1.checked = false;
                        //  element1.setAttribute('checked', 'true');
                        element1.setAttribute('onclick', 'getOffenceDetail('+rowCount+')');
                        cell1.appendChild(element1);

                        var cell2 = row.insertCell(1);
                        var element2 = doc.createElement("input");
                        element2.type = "";
                        element2.name = "offence_type_selected";
                        element2.id = "offence_type_selected"+rowCount;
                        element2.size = 50;
                        element2.value =offence_type;
                        element2.setAttribute('class', 'offence_input');
                        element2.setAttribute('readonly', 'readonly');
                        element2.setAttribute('onfocus', '$(this).blur()');
                        cell2.appendChild(element2);
                        var height = (rowCount *5)+ 20;

                        doc.getElementById("autoCreateTableDiv1").style.visibility = "visible";
                        doc.getElementById("autoCreateTableDiv1").style.height = height+'px';
                        if(rowCount == 1){
                            $('#checkUncheckAll').attr('hidden', true);
                        }else{
                            $('#checkUncheckAll').attr('hidden', false);
                        }

                    }
                    function deleteRow1() {
                        try {
                            var table =doc.getElementById("insertTable1");
                            var rowCount = table.rows.length;
                            for(var i=1; i<rowCount; i++) {
                                table.deleteRow(1);
                            }

                            doc.getElementById("autoCreateTableDiv1").style.visibility = "visible";
                            doc.getElementById("autoCreateTableDiv1").style.height = '0px';
                        }catch(e) {
                            alert(e);
                        }
                    }

                    function deleteRow2() {
                        try {
                            var table =doc.getElementById("insertTable2");
                            var rowCount = table.rows.length;
                            for(var i=1; i<rowCount; i++) {
                                table.deleteRow(1);
                            }
                            //while(rowCount>0){
                            //    table.deleteRow(0);
                            //}
                            doc.getElementById("autoCreateTableDiv1").style.visibility = "visible";
                            doc.getElementById("autoCreateTableDiv1").style.height = '0px';
                        }catch(e) {
                            alert(e);
                        }
                    }

                    function deleteActRow(id){
                        try {
                            //var table =doc.getElementById("insertTable2");
                            //var rowCount = table.rows.length;
                            //for(var i=1; i<rowCount; i++) {
                            //var row = doc.getElementById(id);
                            //var i = row.parentNode.parentNode.rowIndex;
                            //doc.getElementById("insertTable2").deleteRow(i);
                            //$("#Act_type"+id).remove();
                                //table.deleteRow(id);
                                var table = doc.getElementById('insertTable2');
    table.removeChild(doc.getElementById("Act_type"+id));
                            //}
                            //while(rowCount>0){
                            //    table.deleteRow(0);
                            //}
                            //doc.getElementById("autoCreateTableDiv1").style.visibility = "visible";
                            //doc.getElementById("autoCreateTableDiv1").style.height = '0px';
                        }catch(e) {
                            alert(e);
                        }
                    }
                   
                    function addRow2(act_type,penalty_amount,vehicle_type, id) {
                  

                        var table = doc.getElementById('insertTable2');
                        var rowCount = table.rows.length;                // alert(rowCount);
                        var row = table.insertRow(rowCount);
                        row.id = "Act_type"+id;

                        var cell1 = row.insertCell(0);
                        cell1.innerHTML = rowCount;
                        var element1 = doc.createElement("input");
                        element1.type = "";
                        element1.name = "act_type";
                        element1.id = "act_type"+rowCount;
                        element1.size = 20;
                        element1.value =act_type;
                        element1.setAttribute('readonly', 'readonly');
                        element1.setAttribute('onfocus', '$(this).blur()');
                        cell1.appendChild(element1);

                        var cell2 = row.insertCell(1);
                        var element2 = doc.createElement("input");
                        element2.type = "";
                        element2.name = "penalty_amount";
                        element2.id = "penalty_amount"+rowCount;
                        element2.size = 20;
                        element2.value =penalty_amount;
                        element2.setAttribute('readonly', 'readonly');
                        element2.setAttribute('onfocus', '$(this).blur()');
                        cell2.appendChild(element2);

                        var cell3 = row.insertCell(2);
                        var element3 = doc.createElement("input");
                        element3.type = "";
                        element3.name = "vehicle_type";
                        element3.id = "vehicle_type"+rowCount;
                        element3.size = 20;
                        element3.value =vehicle_type;
                        element3.setAttribute('readonly', 'readonly');
                        element3.setAttribute('onfocus', '$(this).blur()');
                        cell3.appendChild(element3);
                        var height = (rowCount *20)+ 20;

                        doc.getElementById("autoCreateTableDiv2").style.visibility = "visible";
                        doc.getElementById("autoCreateTableDiv2").style.height = height+'px';
                    }
                    function addRow3(offence_type) {
                   
                        var table = doc.getElementById('insert');
                        var rowCount = table.rows.length;                // alert(rowCount);
                        var row = table.insertRow(rowCount);

                        var cell1 = row.insertCell(0);
                        cell1.innerHTML = rowCount;
                        var element1 = doc.createElement("input");
                        element1.type = "checkbox";
                        element1.name = "check_print";
                        element1.id = "check_print"+rowCount;                //element1.size = 1;
                        element1.value = "Yes";
                        element1.checked = false;
                        //  element1.setAttribute('checked', 'true');
                        element1.setAttribute('onclick', 'getOffenceDetail('+rowCount+')');
                        cell1.appendChild(element1);

                        var cell2 = row.insertCell(1);
                        var element2 = doc.createElement("input");
                        element2.type = "";
                        element2.name = "offence_type_selected";
                        element2.id = "offence_type_selected"+rowCount;
                        element2.size = 50;
                        element2.value =offence_type;
                        element2.setAttribute('class', 'offence_input');
                        element2.setAttribute('readonly', 'readonly');
                        element2.setAttribute('onfocus', '$(this).blur()');
                        cell2.appendChild(element2);
                        offence_type="";
                        var height = (rowCount *15)+ 20;

                        doc.getElementById("autoCreate").style.visibility = "visible";
                        doc.getElementById("autoCreate").style.height = height+'px';
                        if(rowCount == 1){
                            $('#checkUncheckAll').attr('hidden', true);
                        }else{
                            $('#checkUncheckAll').attr('hidden', false);
                        }
                        deleteRow1();
                        deleteRow2();
                    }
                    function checkValue(){
                        var table =doc.getElementById("insertTable1");
                        var rowCount = table.rows.length;
                        var i=0;
                        for(i=2; i<=rowCount-1; i++) {
                            if(doc.getElementById("check_print"+i).checked==true){
                                alert(i);
                                doc.getElementById("check_print"+i).checked==false;

                            }

                        }
                    }
                    function changeValue(){
                        if(doc.getElementById("process_type").value=='Court'){
                            doc.getElementById("relation_type").disabled = false;
                            doc.getElementById("salutation").disabled = false;
                            doc.getElementById("relative_name").disabled = false;
//                            doc.getElementById("offender_city").disabled = false;
//                            doc.getElementById("offender_address").disabled = false;
//                            doc.getElementById("offender_age").disabled = false;
                            doc.getElementById("case_no").disabled = false;
                            doc.getElementById("case_date").disabled = false;
                            doc.getElementById("reciept_book_no").disabled = true;
                            doc.getElementById("reciept_page_no").disabled = true;

                            $(".receipt_book").hide();
                            doc.getElementById("court_case").style.visibility='visible';
                            doc.getElementById("court_case1").style.visibility='visible';
                            doc.getElementById("court_case2").style.visibility='visible';
                            doc.getElementById("court_case3").style.visibility='visible';

                        
                        }
                        else{
                            doc.getElementById("relation_type").disabled = true;
                            doc.getElementById("salutation").disabled = true;
                            doc.getElementById("relative_name").disabled = true;
//                            doc.getElementById("offender_city").disabled = true;
//                            doc.getElementById("offender_address").disabled = true;
//                            doc.getElementById("offender_age").disabled = true;
                            doc.getElementById("case_no").disabled = true;
                            doc.getElementById("case_date").disabled = true;
                            doc.getElementById("reciept_book_no").disabled = false;
                            doc.getElementById("reciept_page_no").disabled = false;

                            $(".receipt_book").show();
                            doc.getElementById("court_case").style.visibility='collapse';
                            doc.getElementById("court_case1").style.visibility='collapse';
                            doc.getElementById("court_case2").style.visibility='collapse';
                            doc.getElementById("court_case3").style.visibility='collapse';
                           

                        }
                    }

                    function showStructureDiv() {
                        //deleteRow();
                        var table = doc.getElementById('structureTable');
                        table.innerHTML="";
                        var noOfStructure=parseInt(doc.getElementById("no_of_offence").value);
                        for(var i=0; i<noOfStructure; i++) {
                        makeStructureDiv();
                        }
                        doc.getElementById("structureDiv").style.visibility = "visible";
                        $("#deposited_amount").val(0);
                    }

                    function makeStructureDiv() {
                        var table = doc.getElementById('structureTable');
                        var rowCount = table.rows.length;                // alert(rowCount);
                        var row = table.insertRow(rowCount);
                        row.setAttribute("class", "heading");


                        var cell1 = row.insertCell(0);
                        //var element1 = doc.createElement("th");
                        cell1.innerHTML = "Act Origin";
                        //cell1.setAttribute('class','heading1');
                        //cell1.appendChild(element1);

                        //var cell2=row.insertCell(1);
                       /* var element2 = doc.createElement("input");
                        element2.type = "text";
                        element2.name = "act_origin";
                        element2.id = "act_origin"+rowCount;                //element1.size = 1;
                        //element2.setAttribute('onblur',"getStructureName("+rowCount+")");                //element1.size = 1;
                        element2.setAttribute('onkeypress',"setActOriginId("+rowCount+")");
                        element2.setAttribute('class','input');                //element1.size = 1;
                        cell1.appendChild(element2);*/
                        var element2 = doc.createElement("select");
                        //element2.type = "text";
                        element2.name = "act_origin";
                        element2.id = "act_origin"+rowCount;                //element1.size = 1;
                        var option = doc.createElement("option");
                        option.vlaue = "Circular Moter vehicle Rules";
                        option.innerHTML = "Circular Moter vehicle Rules";
                        element2.appendChild(option);

                        var option1 = doc.createElement("option");
                        option1.vlaue = "Centeral Moter vehicle Rules 1989";
                        option1.innerHTML = "Centeral Moter vehicle Rules 1989";
                        element2.appendChild(option1);

                        var option2 = doc.createElement("option");
                        option2.vlaue = "Madhya pradesh Moter vehicle Rules 1994";
                        option2.innerHTML = "Madhya pradesh Moter vehicle Rules 1994";
                        element2.appendChild(option2);
                        //element2.setAttribute('onblur',"getStructureName("+rowCount+")");                //element1.size = 1;
                        //element2.setAttribute('onkeypress',"setActOriginId("+rowCount+")");
                        element2.setAttribute('class','');                //element1.size = 1;
                        cell1.appendChild(element2);

                        var cell2=row.insertCell(1);
                        cell2.innerHTML = "Offence Code";
                        //var element5 = doc.createElement("th");
                        //cell3.setAttribute('class','heading1');
                        //cell3.appendChild(element5);

                        //var cell4=row.insertCell(3);
                        var element4 = doc.createElement("input");
                        element4.type = "text";
                        element4.name = "offence_code";
                        element4.id = "offence_code"+rowCount;                //element1.size = 1;
                        element4.value = "";
                        element4.size = 10;
                        //element4.setAttribute('onblur',"getStructureName("+rowCount+")");                //element1.size = 1;
                        element4.setAttribute('class','input');                //element1.size = 1;
                        element4.setAttribute('required', true);
                        cell2.appendChild(element4);

                        var element6 = doc.createElement("input");
                        element6.type = "hidden";
                        element6.id = "penalty_amount"+rowCount;                //element1.size = 1;
                        element6.value = "";
                        element6.size = 10;
                        cell2.appendChild(element6);

                        var cell3=row.insertCell(2);
                        cell3.innerHTML = "Offence Type";
                        //var element5 = doc.createElement("th");
                        //cell3.setAttribute('class','heading1');
                        //cell3.appendChild(element5);

                        //var cell4=row.insertCell(3);
                        var element5 = doc.createElement("input");
                        element5.type = "text";
                        element5.name = "offence_type";
                        element5.id = "offence_type"+rowCount;                //element1.size = 1;
                        element5.value = "";
                        element5.size = 40;
                        //element4.setAttribute('onblur',"getStructureName("+rowCount+")");                //element1.size = 1;
                        element5.setAttribute('class','input');                //element1.size = 1;
                        cell3.appendChild(element5);


                        var cell6=row.insertCell(3);

                var element13 = doc.createElement("input");
                element13.type = "button";
                element13.name = "getOffenceCode";
                element13.id = "quantityBtn"+rowCount;                //element1.size = 1;                                //element1.size = 1;
                element13.value = "Get Code";                //element1.size = 1;                                //element1.size = 1;
                element13.setAttribute('class','button');
                element13.setAttribute('onclick',"getOffenctCode(" + rowCount + ")");
                cell6.appendChild(element13);
                //$("#quantityBtn"+rowCount).hide();

                //                 var ht = (rowCount * 80)+ 250;
                //
                //                doc.getElementById("structureDiv").style.visibility = "visible";
                //                doc.getElementById("structureDiv").style.height = ht+'px';
                /*addQuantityDiv();
                if(rowCount == 1){
                    $('#checkUncheckAll').attr('hidden', true);
                }else{
                    $('#checkUncheckAll').attr('hidden', false);
                }
                showQuantityDiv(rowCount);*/
            }

            function setActOriginId(id){
                //getActOriginList
                $.ajax({url: "trafficPoliceCont", async: false ,data: "action1=getActOriginList" , success: function(response_data) {
                                    //$("#act_origin"+id).val(response_data);

                                    $("#act_origin"+id).autocomplete("", {
                        extraParams: {
                            action1: function() { return response_data;}
                        }
                    });
                                    //var act_origin= response_data;
                                    //doc.getElementById("act_origin").value =act_origin;
                                }});
            }

            function getOffenctCode(count){//debugger;
                var act_origin = $("#act_origin"+count).val();
                var vehicle_type = $("#vehicle_type").val();
                var commercial_type = $("#commercial_type").val();
                var queryString = "task=getOffenceCode";
                var url = "trafficPoliceCont?" + queryString + "&act_origin="+act_origin+"&count="+ count ;//+"&vehicle_type="+ vehicle_type +"&commercial_type="+ commercial_type;
                popupwin = openPopUp(url, "Division List", 600, 950);                
            }

            function pop(div) {
				doc.getElementById(div).style.display = 'block';
			}
			function hide(div) {
				doc.getElementById(div).style.display = 'none';
			}
			//To detect escape button
			doc.onkeydown = function(evt) {
				evt = evt || window.event;
				if (evt.keyCode == 27) {
					hide('popDiv');
				}
			};

                        function viewTrafficPoliceImage(id){
                            //alert(id);
                            //var emp_code= doc.getElementById("emp_code1"+id).value;
                            //var queryString = "task1=viewImage&emp_code="+emp_code;
                            var queryString = "task=View_Image&traffic_police_id="+id;
                            // alert(queryString);
                            var url = "trafficPoliceSearchCont.do?" + queryString;
                            popupwin = openPopUp(url, "Show Image", 600, 900);
                        }
                        function openMap(longitude, lattitude) {
                            var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
                            var y = longitude;//$.trim(doc.getElementById(logitude).value);
                            var url="trafficPoliceSearchCont.do?task=showMapWindow&logitude="+y+"&lattitude="+x;
                            popupwin = openPopUp(url, "",  580, 620);
                        }

//                        createEditableSelect(doc.forms[0].myText);
            </script>
        </head>
        <body>
            <table align="center"  class="main" cellpadding="0" cellspacing="0"  >
                <tr>
                    <td><%@include file="/layout/header.jsp" %></td>
                </tr>
                <tr>
                    <td><%@include file="/layout/menu.jsp" %></td>
                </tr>
                <tr>
                    <td>
                        <div class="maindiv" id="body" style="min-height: 300px;max-height: none">
                            <table align="center" width="450">
                                <tr>
                                    <td colspan="5">
                                        <table width="100%">
                                            <tr>
                                                <td class="header_table" colspan="2" align="center"><h1>Traffic Police Page</h1></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td  align="center">
                                        <form name="form0" method="post" action="trafficPoliceCont">
                                            <table  align="center"  class="heading1">
                                                <tr>
                                                    <td>
                                                        Officer Name
                                                        <input class="input" type="text" id="searchOfficerName" name="searchOfficerName" value="${searchOfficerName}" size="20" >
                                                        Challan Book No
                                                        <input class="input" type="text" id="searchBookNo" name="searchBookNo" value="${searchBookNo}" size="15" >
                                                        Receipt Book No
                                                        <input class="input" type="text" id="searchReceiptBookNo" name="searchReceiptBookNo" value="${searchReceiptBookNo}" size="15" >
                                                        Jarayam No.
                                                        <input class="input" type="text" id="searchJarayamNo" name="searchJarayamNo" value="${searchJarayamNo}" size="10" >
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        Offence Type
                                                        <input class="input" type="text" id="searchOffenceType" name="searchOffenceType" value="${searchOffenceType}" size="30" >
                                                        Offence Code
                                                        <input class="input" type="text" id="searchOffenceCode" name="searchOffenceCode" value="${searchOffenceCode}" size="12" >
                                                        Act Type
                                                        <input class="input" type="text" id="searchActType" name="searchActType" value="${searchActType}" size="20" >
                                                        Vehicle Type
                                                        <input class="input" type="text" id="searchVehicleType" name="searchVehicleType" value="${searchVehicleType}" size="20" >
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>                                                        
                                                        Vehicle No
                                                        <input class="input" type="text" id="searchVehicleNo" name="searchVehicleNo" value="${searchVehicleNo}" size="15" >
                                                        Offender License No.
                                                        <input class="input" type="text" id="searchOffenderLicenseNo" name="searchOffenderLicenseNo" value="${searchOffenderLicenseNo}" size="20" >
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
                                                        <input class="button" type="submit" name="task" id="search" value=" Search">
                                                        <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">

                                                        <input type="button" id="view_pdf" class="pdf_button" name="view_pdf" value="" onclick="getRecordlist(id)">
                                                        <input type="button" id="view_excel" class="button" name="view_excel" value="Excel" onclick="getRecordlist(id)">
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <div class="content_div" style="max-height: 375px">
                                            <form name="form1" action="trafficPoliceCont" method="post">
                                                <table id="table1" border="1" align="center" class="content" width="980">
                                                    <tr align="center">
                                                        <th class="heading">S.No</th>

                                                        <th class="heading" style="white-space: normal">Challan Book No.</th>
                                                        <th class="heading" style="white-space: normal">Challan Book Rev. No.</th>
                                                        <th class="heading" style="white-space: normal">Officer Name</th>
                                                        <th class="heading" style="white-space: normal">Jarayam No.</th>
                                                        <th class="heading" style="white-space: normal">Offence Date</th>
                                                        <th class="heading" style="white-space: normal">Vehicle No.</th>
                                                        <th class="heading"style="white-space: normal">License No.</th>
                                                        <th class="heading">Offender Name</th>
                                                        <th class="heading">Father's Name</th>
                                                        <th class="heading" style="white-space: normal">Offender Age</th>
                                                        <th class="heading"style="white-space: normal">Offender Address</th>

                                                        <th class="heading" style="">Offence City</th>
                                                        <th class="heading" style="white-space: normal">Offence Zone</th>
                                                        <th class="heading" style="white-space: normal">Place of Offence</th>
                                                        
                                                        <th class="heading" style="white-space: normal">Challan No.</th>
                                                        
                                                        <th class="heading" style="white-space: normal">Receipt Book No.</th>
                                                        <th class="heading" style="white-space: normal">receipt No.</th>
                                                        <th class="heading" style="white-space: normal">Deposited amount in court</th>
                                                        <th class="heading" style="white-space: normal">Process Type</th>
                                                        
                                                        <th class="heading" style="white-space: normal">Relation Type</th>
                                                        
                                                        <th class="heading" style="white-space: normal">Case No</th>
                                                        <th class="heading" style="white-space: normal">Case Date</th>
                                                        <th class="heading" style="white-space: normal">Vehicle description</th>
                                                        


                                                    </tr>
                                                    <c:forEach var="tp" items="${requestScope['list']}" varStatus="loopCounter">
                                                        <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                                <input type="hidden" id="traffic_police_id${loopCounter.count}" value="${tp.traffic_police_id}">                                                                
                                                                    <input type="hidden" id="${tp.traffic_police_id}" value="${fn:length(tp.offenceList)}">                                                                
                                                                <%--   <input type="hidden" id="balance_unit_id_gen${loopCounter.count}" value="${st.balance_unit_id}">--%>

                                                            </td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.book_no}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.book_revision_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="" width="90%">${tp.key_person_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.jarayam_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.offence_date}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.vehicle_no} </td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.offender_license_no}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.offender_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.father_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.offender_age}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.offender_address}</td><!--class="new_input"-->

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.city}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.zone}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.offence_place}</td>
                                                            
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.receipt_no}</td>
                                                            
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.receipt_book_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${tp.receipt_book_rev_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.receipt_page_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.deposited_amount}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.processing_type}</td>
                                                            
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.relation_type}</td>
                                                            
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.case_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.case_date}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.remark}</td>
                                                            
                                                            <td id="t1c${IDGenerator.uniqueID}" style="display: none"  onclick="fillColumns(id)">${tp.salutation}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" style="display: none"  onclick="fillColumns(id)">${tp.relative_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" style="display: none"  onclick="fillColumns(id)">${tp.offender_city}</td>

                                                        </tr>
                                                        <th class="heading" style="background-color: lightblue">S.No.</th>
                                                        <th class="heading" style="background-color: lightblue" colspan="3">Offence Type</th>
                                                        <th class="heading" style="background-color: lightblue">Offence Code</th>
                                                        <th class="heading" style="background-color: lightblue">Act</th>
                                                        <th class="heading" style="background-color: lightblue" colspan="2">Act Origin</th>
                                                        <th class="heading" style="background-color: lightblue" >Penalty Amount</th>
                                                        <th class="heading" style="background-color: lightblue" colspan="">Vehicle Type</th>
                                                        <th class="heading" style="background-color: lightblue" >
                                                            <input type="button" class="btn" value="View Image" id="${tp.traffic_police_id}" onclick="viewTrafficPoliceImage(id)">
                                                        </th>
                                                        <th style="background-color: lightblue" colspan="13">
                                                            <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${tp.longitude}' , '${tp.lattitude}');"/>
                                                        </th>
                                                        <c:forEach var="list" items="${tp.offenceList}"  varStatus="loopCounter">
                                                            <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                                <td style="background-color: lightgray">
                                                                    ${loopCounter.count}
                                                                    <%--<input type="hidden" id="${tp.traffic_police_id}" value="${loopCounter.count}">--%>
                                                                </td><!--lowerLimit - noOfRowsTraversed + -->
                                                                <td colspan="3" style="background-color: lightgray;" id="offence_typ${tp.traffic_police_id}_${loopCounter.count}">${list.offence_type}</td>
                                                                <td style="background-color: lightgray" id="offence_cd${tp.traffic_police_id}_${loopCounter.count}">${list.offence_code}</td>
                                                                <td style="background-color: lightgray">${list.act}</td>
                                                                <td colspan="2" id="act_origin${tp.traffic_police_id}_${loopCounter.count}" style="background-color: lightgray">${list.act_origin}</td>
                                                                <td style="background-color: lightgray">${list.penalty_amount == 100000 ? 'Court' : list.penalty_amount}</td>
                                                                <td style="background-color: lightgray" colspan="15">${list.vehicle_type}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:forEach>

                                                    <tr>
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
                                                    </tr>
                                                </table>
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden"  name="searchOfficerName" value="${searchOfficerName}" >
                                                <input type="hidden"  name="searchBookNo" value="${searchBookNo}" >
                                                <input type="hidden" id="searchOffenceType" name="searchOffenceType" value="${searchOffenceType}">
                                                <input type="hidden" id="searchActType" name="searchActType" value="${searchActType}">
                                                <input type="hidden" id="searchVehicleType" name="searchVehicleType" value="${searchVehicleType}">
                                                <input type="hidden" id="searchVehicleNo" name="searchVehicleNo" value="${searchVehicleNo}">
                                                <input type="hidden" id="searchFromDate" name="searchFromDate" value="${searchFromDate}">
                                                <input type="hidden" id="searchToDate" name="searchToDate" value="${searchToDate}">
                                                <input type="hidden" id="searchJarayamNo" name="searchJarayamNo" value="${searchJarayamNo}">
                                                <input type="hidden" id="searchOffenceCode" name="searchOffenceCode" value="${searchOffenceCode}">
                                                <input type="hidden" name="searchReceiptBookNo" value="${searchReceiptBookNo}">
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <DIV id="autoCreateTableDiv"  STYLE="visibility: hidden;height: 0px ;margin-bottom: 10px;overflow: auto;width: 990px">
                                            <form name="form2"  action="trafficPoliceCont" method="post" >
                                                <table id="parentTable" class="content"  align="center" width="100%">
                                                    <tr>
                                                        <td>
                                                            <table id="insertTable" class="content" align="center" width="100%">
                                                                <tr align="center">
                                                                    <th class="heading">S.No</th>
                                                                    <th class="heading">Jarayam No</th>
                                                                    <th class="heading">Offender Name</th>
                                                                    <th class="heading" style="white-space: normal">Vehicle Number</th>
                                                                    <th class="heading" style="white-space: normal">Offender License No</th>
                                                                    <th class="heading" style="white-space: normal">Offence Place</th>
                                                                    <th class="heading" style="white-space: normal">Process Type</th>
                                                                    <th class="heading" style="white-space: normal">Relation Type</th>
                                                                    <th class="heading" style="white-space: normal">Relative Name</th>
                                                                    <th class="heading" style="white-space: normal">Offender City</th>
                                                                    <th class="heading" style="white-space: normal">Offender Address</th>
                                                                    <th class="heading" style="white-space: normal">Offender Age</th>
                                                                    <th class="heading" style="white-space: normal">Case No</th>
                                                                    <th class="heading" style="white-space: normal">case Date</th>
                                                                    <th class="heading" style="white-space: normal">Receipt No.</th>
                                                                    <th class="heading" style="white-space: normal">Offence date</th>
                                                                    <th class="heading" style="white-space: normal">Deposited Amount In court</th>
                                                                    <th class="heading" style="white-space: normal">Officer Name</th>
                                                                    <th class="heading" style="white-space: normal">Book Number</th>
                                                                    <th class="heading" style="white-space: normal">Book revision No</th>
                                                                    <th class="heading" style="white-space: normal">City</th>
                                                                    <th class="heading" style="white-space: normal">Zone</th>
                                                                    <th class="heading" style="white-space: normal">Act Origin</th>
                                                                    <th class="heading" style="white-space: normal">Offence Type</th>

                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <%--    <tr>
                                                            <td>
                                                                <div  id="autoCreate"  STYLE="visibility: hidden;height: 0px ;margin-bottom: 10px;overflow: auto;width: 990px">
                                                                    <table id="insert" class="content" align="center" width="100%">
                                                                        <tr align="center">
                                                                            <th class="heading">S.No</th>
                                                                            <th class="heading">Offender TYPE</th>

                                                                    </tr>
                                                                </table>
                                                            </div>
                                                        </td>
                                                    </tr>--%>
                                                    <tr>
                                                        <td align="center">
                                                            <input type="button"  class="button"  name="checkUncheckAll" id="checkUncheckAll" value="Uncheck All" onclick="checkUncheck(value)"/>
                                                            <input type="submit" class="button" id="addAllRecords" name="task" value="Add All Records">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <input type="button"  class="button"  name="Cancel" value="Cancel" onclick="deleteRow();">
                                                        </td>
                                                    </tr>
                                                </table>
                                                <input  type="hidden" name="searchItem" value="${searchItem}"  >
                                                <%-- <input  type="hidden" name="searchMovementType" value="${searchMovementType}"  >--%>
                                                <input  type="hidden" name="searchOrg" value="${searchOrg}"  >
                                                <%--    <input  type="hidden" name="searchKeyPerson" value="${searchKeyPerson}"  >--%>
                                                <input  type="hidden" name="searchDocumentName" value="${searchDocumentName}"  >

                                            </form>
                                        </DIV>
                                    </td>
                                </tr>
                                <tr id="msg" style="width: 100%; background-color: red">
                                    <c:if test="${not empty msg}">
                                        <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${msg}</b></td>
                                    </c:if>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <form name="form3"  id="form3" action="trafficPoliceCont" method="post" onsubmit="return trafficPoliceVerify()">
                                            <table id="table" class="reference"  align="center" width="100%">
                                                <%-- <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
</tr>--%>
                                                <tr>
                                                    <th class="heading" width="10%">Data Entry On:</th>
                                                    <td id="selectByTd" width="80%">
                                                        <input type="radio" checked id="select_by_officer_name" name="selectBy"value="Officer Name" onclick="" onchange="showSelectedDiv(id);">Officer Name
                                                        <input type="radio" id="select_by_book_no" name="selectBy"value="Book No"  onchange="showSelectedDiv(id);">Book No
                                                        <input type="radio" id="select_challan_no" name="selectBy"value="Echallan"  onchange="showSelectedDiv(id);" disabled>E-Challan

                                                    </td>

                                                </tr>
                                                <tr id="selectByOfficerName" style="display: none">     <!--style="visibility: collapse"-->
                                                    <td colspan="2">
                                                        <table id="selectByOfficerName_table" width="90%">
                                                            <tr >

                                                                <th class="heading" >Officer Name:</th>
                                                                <td width="30%">
                                                                    <select class="dropdown" id="officer_name_selected" name="officer_name_selected" onchange="fill_OfficerName()" style="width: 70%" required >
                                                                        <option value="" style="color: red" Selected>Select officer Name</option>
                                                                        <c:forEach var="officer" items="${officerNameList}">
                                                                            <option>${officer}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <th class="heading">Challan Book No:</th>
                                                                <td width="20%">
                                                                    <select  class="dropdown" id="book_no" name="book_no"  >
                                                                        <option value="" style="color: red" selected>Select</option>
                                                                        <c:forEach var="bookNoList" items="${bookNoList}">
                                                                            <option>${bookNoList}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <td><input class="input" type="hidden" id="book_revision_no" name="book_revision_no" value="" size="40" >
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr id="selectByBookNo" style="display: none">
                                                    <td colspan="2">
                                                        <table id="selectByBookNo_table"width="90%">
                                                            <tr>
                                                                <th class="heading" width="20%">Challan Book No</th>
                                                                <td width="20%">
                                                                    <select class="dropdown" id="book_no_selected" name="book_no"  onchange="fill_BookNo()" style="width: 70%">
                                                                        <option value="" style="color: red" selected>Select Book No</option>
                                                                        <c:forEach var="book" items="${bookNoList}">
                                                                            <option>${book}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <th class="heading" align="left">Officer Name</th>
                                                                <td width="20%">
                                                                    <input class="input" type="text" id="officer_name" name="officer_name_selected" value="" size="40">

                                                                </td>
                                                                <td><input class="input" type="hidden" id="revision_no" name="book_revision_no" value="" size="40" >
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr id="selectByChallan" style="display: none">
                                                    <td colspan="2">
                                                        <table id="selectByBookNo_table"width="90%">
                                                            <tr>
                                                                <td width="60%">
                                                                    <input type="radio" id="select_by_yes" name="selectBy"value="yes"  onchange="showSelectedDiv(id);"${searchBy eq 'Book No'?'checked':''}>Yes
                                                                    <input type="radio" id="select_by_no" name="selectBy"value="No"  onchange="showSelectedDiv(id);"${searchBy eq 'Book No'?'checked':''}>No

                                                                </td>
                                                        </table>
                                                    </td>
                                                </tr>

                                            </table>
                                            <tr id="selectInsert"  align="cenetr">
                                                <td align="center">
                                                    <div>

                                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                                            <tr id="message">
                                                                <c:if test="${not empty message}">
                                                                    <td colspan="4" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                                </c:if>
                                                            </tr>
                                                            <td><input type="hidden" id="traffic_police_id" name="traffic_police_id" value="0"></td>
                                                            <tr class="heading">
                                                                <td colspan="4">
                                                                <b class="heading1" >Jarayam No.:</b>
                                                                    <input class="input" type="text" id="jarayam_no" name="jarayam_no" value="" size="20" disabled required>
                                                                <b class="heading1">Challan No</b><input class="input" type="text" id="reciept_no" name="reciept_no" value="" size="10" onclick="RecieptNo()"disabled>
                                                                <b class="heading1">Offence Date</b><input class="input" type="text" id="offence_date" name="offence_date" value="${cut_dt}" size="15" maxlength="10" disabled required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th class="heading1" >Vehicle No : </th>
                                                                <td>
                                                                    <input class="input" type="text" id="vehicle_no" name="vehicle_no" value="" size="20" maxlength="16" disabled required>
                                                                    <input class="input" type="text" id="remark" name="remark" value="" size="20" maxlength="" placeholder="Vehicle Description" disabled>
                                                                </td>
                                                                <th class="heading1" >License No : </th>
                                                                <td>                                                                    
                                                                    <input class="input" type="text" id="offender_license_no" name="offender_license_no" value="" size="30" disabled>
                                                                </td>
                                                            </tr>
                                                            <tr>                                                                
                                                                <th class="heading1">Offender Name</th>
                                                                <td>
                                                                    <input class="input" type="text" id="offender_name" name="offender_name" value="" size="25" disabled required>
                                                                </td>
                                                                <th class="heading1">Father's Name</th>
                                                                <td>
                                                                    <input class="input" type="text" id="father_name" name="father_name" value="" size="25" disabled>
                                                                </td>                                                                
                                                            </tr>
                                                            <tr>
                                                                <th class="heading1">Age : </th>
                                                                <td>
                                                                    <input class="input" type="text" id="offender_age" name="offender_age" value="" size="5" maxlength="3" disabled>
                                                                </td>
                                                                <th class="heading1">Address </th>
                                                                <td><input class="input" type="text" id="offender_address" name="offender_address" value="" size="40" disabled></td>                                                                                                                                  
                                                            </tr>
                                                            <tr>
                                                                <th class="heading1"> City : </th>
                                                                <td>
                                                                    <input class="input" type="text" id="offender_city" name="offender_city" value="" size="20" disabled>
                                                                </td>
                                                                <th class="heading1">Offence City </th>
                                                                <td><input class="input" type="text" id="city_name" name="city_name" value="${offence_city}" size="30" disabled required></td>                                                                
                                                            </tr>
                                                            <tr>
                                                                <th class="heading1">Zone : </th>
                                                                <td colspan=""><input class="input" type="text" id="zone" name="zone" value="${zone}" size="10" disabled required></td>
                                                                <th class="heading1">Offence Location</th>
                                                                <td>
                                                                    <input class="input" type="text" id="offence_place" name="offence_place" value="${offence_place}" size="25" disabled required>
                                                                </td>                                                                
                                                            </tr>
                                                            <tr>
                                                                <th class="heading1">Process Type</th>
                                                                <td>
                                                                    <select class="dropdown3" id="process_type" name="process_type" onchange="changeValue();" disabled>
                                                                        <option value="" style="color: red" selected>Select process type</option>
                                                                        <c:forEach var="processTypeList" items="${processingtypelist}">
                                                                            <option>${processTypeList}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <!--<th class="heading1">Is Owner License No.</th>
                                                                <td>
                                                                    <input type="radio" id="is_owner_license" value="Yes" name="is_owner_license" checked disabled>Yes
                                                                    <input type="radio" id="is_not_owner_license" value="No" name="is_owner_license" disabled >No
                                                                </td>-->

                                                            </tr>

                                                            <tr id="court_case" style="visibility: collapse; display: none">
                                                                <th class="heading1">Relation Type</th>
                                                                <td>

                                                                    <select class="dropdown3" id="relation_type" name="relation_type" disabled>
                                                                        <option value="" style="color: red" selected>Select Relation type</option>
                                                                        <c:forEach var="relationType" items="${relationTypeList}">
                                                                            <option>${relationType}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <th class="heading1">Relative Salutation</th>
                                                                <td>
                                                                    <select class="dropdown" id="salutation" name="salutation" disabled>
                                                                        <option>---Select--- </option>
                                                                        <option style="text-align: center">Mr.</option>
                                                                        <option style="text-align: center">Ms.</option>
                                                                        <option style="text-align: center">Mrs.</option>
                                                                    </select>
                                                                </td>
                                                            </tr>

                                                            <tr id="court_case1" style="visibility: collapse;display: none">

                                                                <th class="heading1">Relative Name</th>
                                                                <td>
                                                                    <input class="input" type="text" id="relative_name" name="relative_name" value="" size="40" disabled>
                                                                </td>
                                                                
                                                            </tr>
                                                            <tr id="court_case2" style="visibility: collapse">


                                                                
                                                            </tr>
                                                            <tr id="court_case3" style="visibility: collapse">
                                                                <th class="heading1">Case No.</th>
                                                                <td>
                                                                    <input class="input" type="text" id="case_no" name="case_no" value="" size="40" disabled>
                                                                </td>
                                                                <th class="heading1">Case Date</th>
                                                                <td>
                                                                    <input class="input" type="text" id="case_date" name="case_date" value="" size="15" maxlength="10" disabled>
                                                                </td>
                                                            </tr>
                                                            <%-- <tr>
                                                                         <th class="heading1">Act Origin</th>

                                                                <td id="selectByTd" colspan="8" style="background-color: #B9C9FE">
                                                                    <input type="radio"  id="select_by_circular" name="act_origin"value="Circular Moter vehicle Rules" onclick="abc(id);" onchange="showSelectedDiv(id);">Circular Moter vehicle Rules
                                                                    <input type="radio" id="select_by_centeral" name="act_origin"value="Centeral Moter vehicle Rules 1989"  onchange="showSelectedDiv(id);">Centeral Moter vehicle Rules 1989
                                                                    <input type="radio" id="select_by_mp" name="act_origin"value="Madhya pradesh Moter vehicle Rules 1994"  onchange="showSelectedDiv(id);">Madhya pradesh Moter vehicle Rules 1994
                                                                </td>

</tr>--%>

                                                            <%--          <tr>

                                                                <th class="heading1">Offence Type</th>
                                                                <td>
                                                                    <select class="dropdown" id="offence_type_selected" name="offence_type_selected" multiple size="4"  onchange="fill_OffenceType()" disabled >

                                                                        <option value="" style="color: red" selected>Select Offence Type</option>
                                                                        <option></option>
                                                                    </select>
                                                                </td>

                                                                <th class="heading1">Act </th>

                                                                <td>
                                                                    <select class="dropdown" id="act_type_selected" name="act_type_selected"    disabled >
                                                                        <option value="" style="color: red" selected>Select Act.</option>
                                                                        <c:forEach var="actTypeList" items="${actTypeList}">
                                                                            <option>${actTypeList}</option>
                                                                        </c:forEach>
                                                                    </select>

                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th class="heading1">Penalty Amount</th><td><input class="input" type="text" id="penalty_amount" name="penalty_amount" value="" size="40" disabled></td>
                                                                <th class="heading1">Vehicle Type</th>
                                                                <td>
                                                                    <input type="hidden" id="vehicle_type_id" name="vehicle_type_id" value="">
                                                                    <select class="dropdown" id="vehicle_type" name="vehicle_type" disabled>
                                                                        <option value="" style="color: red" selected>Select</option>
                                                                        <c:forEach var="vehicleTypeList" items="${vehicleTypeList}">
                                                                            <option> ${vehicleTypeList}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                            </tr>--%>

                                                            <tr class="receipt_book">
                                                                <th class="heading1 receipt_book">Receipt Book No. </th>
                                                                <td>
                                                                    <input class="input" type="text" id="reciept_book_no" name="reciept_book_no" value="${receipt_book_no}" size="10" onclick=""disabled>
                                                                    <input class="input" type="hidden" id="reciept_book_rev_no" name="reciept_book_rev_no" value="${reciept_book_rev_no}" size="10" onclick="">
                                                                </td>
                                                                <th class="heading1 receipt_book">Receipt No. </th>
                                                                <td>
                                                                    <input class="input" type="text" id="reciept_page_no" name="reciept_page_no" value="${receipt_page_no}" size="10" onclick=""disabled>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th class="heading1">Deposited Amount(in court) </th><td><input class="input" type="text" id="deposited_amount" name="deposited_amount" value="0" size="15" onkeyup="IsNumeric(id)" maxlength="" disabled required></td>
                                                            </tr>
                                                            <tr>
                                                               <!-- <th class="heading1">Act Origin</th>

                                                                <td id="selectByTd" colspan="8" style="background-color: #B9C9FE">
                                                                    <input type="text" id="act_origin" name="act_origin" value="">
                                                                    <input type="radio"  id="select_by_circular" name="act_origin"value="Circular Moter vehicle Rules" onclick="abc(id);" onchange="showSelectedDiv(id);">Circular Moter vehicle Rules
                                                                    <input type="radio" id="select_by_centeral" name="act_origin"value="Centeral Moter vehicle Rules 1989"  onchange="showSelectedDiv(id);">Centeral Moter vehicle Rules 1989
                                                                    <input type="radio" id="select_by_mp" name="act_origin"value="Madhya pradesh Moter vehicle Rules 1994"  onchange="showSelectedDiv(id);">Madhya pradesh Moter vehicle Rules 1994
                                                                </td>-->
                                                               <th class="heading1">Select No. of Offence</th>
                                                               <td>
                                                                   <select id="no_of_offence" class="dropdown" onfocus="" onchange="showStructureDiv()" style="width: 150px" name="no_of_offence" disabled>
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
                                                               </td>
                                                               <td style="display: none">
                                                                   <input id="vehicle_type" class="input" onfocus="" onchange="" style="width: 150px" placeholder="Vehicle Type" value="" name="vehicle_type">
                                                               </td>
                                                               <td style="display: none">
                                                                   <select id="commercial_type" class="dropdown" onfocus="" onchange="" style="width: 150px" name="commercial_type">
                                                                       <option>Commercial</option>
                                                                       <option>Non Commercial</option>
                                                                   </select>
                                                               </td>
                                                               
                                                            </tr>
                                                            <tr>
                                                                <!--<th class="heading1">Offence Type Selected</th>
                                                                <td colspan="4" >
                                                                    <DIV id="autoCreateTableDiv1"  STYLE="visibility: hidden;height: 0px ;margin-bottom: 10px;overflow: auto;width: 100%">

                                                                        <table id="parentTable1" class="content"  align="center" width="100%">
                                                                            <tr>
                                                                                <td>
                                                                                    <table id="insertTable1" class="content" align="center" width="100%">
                                                                                        <tr align="center">
                                                                                            <th class="heading" style="white-space: normal">Check Box</th>
                                                                                            <th class="heading" style="white-space: normal">Offence Type</th>

                                                                                        </tr>
                                                                                    </table>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </DIV>
                                                                </td>-->
                                                                <td colspan="4">
                                                                    <div  id="structureDiv"  STYLE="visibility: visible;margin-bottom: 10px;overflow: auto;height:auto;width:auto " align="center">
                                                                        <table class="content" id="structureTable" align="center" width="100%" >
                                                                        </table>
                                                                        <input type="hidden" name="isPopup" value="No">
                                                                        <input type="hidden" id="viewStatus" value="${viewStatus}">
                                                                        <input type="hidden" id="offenceCodeValues" name="offenceCodeValues"  value="${viewStatus}">
                                                                    </div>
                                                                </td>


                                                                <%--   <td>

                                                                  <select multiple="multiple" class="dropdown" id="offence_type_selected" name="offence_type_selected" onchange='javascript:change();'  disabled >

                                                                        <option value="" style="color: red" selected>Select Offence Type</option>
                                                                        <option></option>
</select>
                                                                    <input type="text" name="offence_type" id="offence_type" value="">
                                                                </td>--%>


                                                            </tr>
                                                            <tr>
                                                                <!--<th class="heading1">Act Type</th>
                                                                <td colspan="4" >
                                                                    <DIV id="autoCreateTableDiv2"  STYLE="visibility: hidden;height: 0px ;margin-bottom: 10px;overflow: auto;width: 100%">

                                                                        <table id="parentTable2" class="content"  align="center" width="100%">
                                                                            <tr>
                                                                                <td>
                                                                                    <table id="insertTable2" class="content" align="center" width="100%">
                                                                                        <tr align="center">
                                                                                            <th class="heading" style="white-space: normal">Act type</th>
                                                                                            <th class="heading" style="white-space: normal">Penalty Amount</th>
                                                                                            <th class="heading" style="white-space: normal">Vehicle Type</th>
                                                                                        </tr>
                                                                                    </table>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </DIV>
                                                                </td>-->
                                                            </tr>
                                                            <%--    <table id="offence_table" style="visibility: collapse">
                                                                    <tr>
                                                                        <th class="heading1">Act</th>
                                                                        <th class="heading1">Penalty Amount</th>
                                                                        <th class="heading1">Vehicle Type</th>
                                                                    </tr>
                                                                    <tr>   <c:forEach var="tp" items="${requestScope['offence_list']}" varStatus="loopCounter">
                                                                        <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">

                                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.act}</td>
                                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.penalty_amount}</td>
                                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.vehicle_type}</td>

                                                                    </c:forEach>
                                                                </tr>


</table>--%>

                                                            <tr>
                                                                <td align='center' colspan="4">
                                                                    <input class="button" type="button" name="edit" id="EDIT" value="Edit" onclick="makeEditable(id)" disabled>
                                                                    <input class="button" type="submit" name="task" id="SAVE" value="Save" onclick="setStatus(id)" disabled>
                                                                    <input class="button" type="submit" name="task" id="SAVE AS NEW" value="Save AS New" onclick="setStatus(id)" disabled>
                                                                    <input class="button" type="reset" name="new" id="NEW" value="New" onclick="makeEditable(id)">
                                                                    <input class="button" type="submit" name="task" id="DELETE" value="Delete" onclick="setStatus(id)" disabled>
                                                                </td>
                                                            </tr>
                                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                            <input type="hidden" id="clickedButton" value="">
                                                            <input type="hidden"  name="searchStatusType" value="${searchStatusType}">
                                                        </table>
                                                        
                                                    </div>
                                                </td>
                                            </tr>

                                        </form>
                                    </td>
                                </tr>




                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form of table. --%>

                                <input type="hidden" id="lowerLimitBottom" name="lowerLimit" value="${lowerLimit}">
                                <input type="hidden" id="noOfRowsTraversedTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                <input type="hidden" id="clickedButton" value="">
                                <input type="hidden"  name="searchOfficerName" value="${searchOfficerName}" >
                                <input type="hidden"  name="searchBookNo" value="${searchBookNo}" >
                                <input type="hidden"  name="searchOffenceType" value="${searchOffenceType}" >
                                <input type="hidden"  name="searchActType" value="${searchActType}" >

                            </table>
                        </div>
                    </td>
                </tr>
            </table>
                                <!--<div id="popDiv" class="ontop maindiv">
                                    <table class="content" border="1" id="popup">
				<tr>
					<td>
						This is can be used as a popup window
					</td>
				</tr>
				<tr>
					<td>
						Click Close OR escape button to close it
						<a href="#" onClick="hide('popDiv')">Close</a>
					</td>
				</tr>
			</table>
		</div>-->
        </body>
    </html>

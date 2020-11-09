<%--
    Document   : surveyView
    Created on : Jul 29, 2014, 11:36:10 PM
    Author     : JPSS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Traffic Police1</title>
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
             
             #vehicle_no{
                 font-size: 30px;
             }
                .new_input
                {
                    font-size: 16px;
                    font-family:"kruti Dev 010", Sans-Serif;
                    font-weight: 500;
                    background-color: white;
                }
                               
/*              .ac_results li {
               font-family: 'sans-serif';
               font-size: 32px;
               }*/
       
                
            </style>
<script type="text/javascript" language="javascript">
    var popupwin;
    
              $(document).ready(function(){
                
                 $("#searchOfficerName").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'kruti Dev 010'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                 $("#searchOffenceType").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'kruti Dev 010'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                 $("#searchActType").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                 $("#searchVehicleType").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                 $("#searchVehicleNo").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                   
                 $("#searchMobileNo").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                   
                 $("#searchVehicleNo").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                 
                 $("#searchLicense").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'kruti Dev 010'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                   
                 $("#searchOffenderName").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                   
                 $("#searchAge").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                   
                 $("#searchOffenderLicenseNo").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                   
                 $("#searchoffenceCity").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                   
                 $("#searchPlaceof").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                        
                 $("#searchAmount").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                   
                 $("#searchCaseDate").focus(function(){
                    //alert("vivek");
                    var sheet = document.createElement('style')
                    sheet.innerHTML = ".ac_results li {font-family: 'sans-serif'; font-size: 12px;}";
                    document.body.appendChild(sheet);
                    //alert(sheet);
//                $("span").css("display", "inline").fadeOut(2000);
                 });
                 
                  });
    
    
    
    jQuery(function(){
        
        $("#survey_date").datepicker({

                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                    });

      
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
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
                    $("#searchChallanNo").autocomplete("userEntryByImageCont.do", {
                        extraParams: {

                            action1: function() { return "getChallanNoSearchList";}
                        }
                    });
                    

//                   var actOriginList =  $("#act_origin"+actOriginId).autocomplete("trafficPoliceCont", {
//                        extraParams: {
//
//                            action1: function() { return "getActOriginList";}
//                        }
//                    });

//                    $("#searchJarayamNo").autocomplete("trafficPoliceCont", {
//                        extraParams: {
//                            action1: function() { return "getJarayamNo";}
//                        }
//                    });

                    $("#searchOffenceCode").autocomplete("userEntryByImageCont.do", {
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


                    $("#searchOffenceType").autocomplete("userEntryByImageCont.do", {
                        extraParams: {
                            action1: function() { return "getOffenceSearchList";}
                            // BookNo: function(){ return doc.getElementById("searchBookNo").value ;}


                        }
                    });
                    $("#searchActType").autocomplete("userEntryByImageCont.do", {
                        extraParams: {
                            action1: function() { return "getActSearchList"}
                            //OffenceType: function(){return doc.getElementById("searchOffenceType").value ;}

                        }
                    });
                    $("#searchVehicleType").autocomplete("userEntryByImageCont.do", {
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

                    $("#searchVehicleNo").autocomplete("userEntryByImageCont.do", {
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
                    $("#searchOffenderLicenseNo").autocomplete("userEntryByImageCont.do", {
                        extraParams: {
                            action1: function() { return "getOffenderLicenseNo"}
                        }
                    });
                    
                     $("#searchAge").autocomplete("userEntryByImageCont.do", {
                        extraParams: {
                            action1: function() { return "getOffenderAgeSearchList"}
                        }
                    });

                        $("#searchOffenderName").autocomplete("userEntryByImageCont.do", {
                        extraParams: {
                            action1: function() { return "getOffenderNameSearchList"}
                        }
                    });

                        $("#searchOffenceCity").autocomplete("userEntryByImageCont.do", {
                        extraParams: {
                            action1: function() { return "getSearchOffenceCity"}
                        }
                    });
                        
                         $("#searchPlaceof").autocomplete("userEntryByImageCont.do", {
                        extraParams: {
                            action1: function() { return "getplaceofSearchList"}
                        }
                    });
                      
                       $("#searchAmount").autocomplete("userEntryByImageCont.do", {
                        extraParams: {
                            action1: function() { return "getAmountSearchList"}
                        }
                    });
                        
                         $("#searchCaseDate").autocomplete("userEntryByImageCont.do", {
                        extraParams: {
                            action1: function() { return "getCaseDateSearchList"}
                        }
                    });
                  
                   $("#searchMobileNo").autocomplete("userEntryByImageCont.do", {
                        extraParams: {
                            action1: function() { return "getMobilenoSearchList"}
                        }
                    });

        
        $("#location").autocomplete("userEntryByImageCont.do", {
            extraParams: {
                action1: function() { return "getLocation"}
            }
        });                 
        $("#showAllRecords").click(function(){
            $("#searchPoleNo").val('');
            $("#searchPoleNo").flushCache();
            $("#searchIvrsNo").val('');
            $("#searchIvrsNo").flushCache();



        });

    });////////////////////////////////////////////////////////////////////////////////////////
//    $(document).ready(function(){
//        selectServeyType('onLoad');
//    });

    var value;
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

    function selectServeyType(survey_type){
        if(myLeftTrim(survey_type).length == 0){
            //   value =$("option:selected", $("#"+survey_type)).val();
            value ='newCase';
        }else{
            value =  survey_type;
        }

        //   document.getElementById("pole_no").value = "";
        $("#pole_no").flushCache();
        //   value =  $("#survey_type").val();

        //    alert('010'+value);
        //   var opt  = document.createElement("option");
        if(value=='pole_type_survey'){  //alert('dsfdsfs');
            $("#survey_type").html("<option value='pole_type_survey' >Pole Type Survey</option>");
            //   opt.text = 'Pole Type Survey';
            $("#table3").css("display", "none");
            $("#remark1").css("display", "inline-block");
            $("#remark_header").css("display", "inline-block");
        }else if(value == 'newCase'){  //alert('sdasda');
            var check = $("option:selected", $('#survey_type')).val();  //alert(check);
            if(check =='pole_type_survey'){
                $("#table5").css("display", "table");
                $("#table3").css("display", "none");
                $("#table4").css("display", "none");
                $("#remark1").css("display", "inline-block");
                $("#remark_header").css("display", "inline-block");
            }else if(check =='tubewell_type_survey'){
                $("#table3").css("display", "table");
                $("#table5").css("display", "table");
                $("#remark1").css("display", "none");
                $("#remark_header").css("display", "none");
                $("#table4").css("display", "none");
            }
            else if(check =='upload_file_data'){
                $("#table4").css("display", "table");
                $("#table3").css("display", "none");
                $("#table5").css("display", "none");
                $("#remark_header").css("display", "none");
            }
        }else if(survey_type == 'onLoad'){
            $("#table3").css("display", "none");
            $("#remark1").css("display", "inline-block");
            $("#remark_header").css("display", "inline-block");
            $("#table4").css("display", "none");
        }else if(survey_type=='tubewell_type_survey'){// alert('1');
            $("#survey_type").html("<option value='tubewell_type_survey'>Tube Well Type Survey</option>"); // alert('2');
            //  opt.text = 'Switching Point Type Survey';
            $("#table3").css("display", "table");
            $("#remark1").css("display", "none");
            $("#remark_header").css("display", "none");
            $("#table4").css("display", "none");
        }
        else if(survey_type=='upload_file_data'){// alert('1');
            $("#survey_type").html("<option value='upload_file_data'>Upload File Data</option>"); // alert('2');
            //  opt.text = 'Switching Point Type Survey';
            $("#table4").css("display", "table");
            $("#table3").css("display", "none");
            $("#table5").css("display", "none");
            //$("#remark_header").css("display", "none");
        }
        //   opt.value = value;
        //   document.getElementById("#survey_type").options.add(opt);
    }

    function makeEditable(id) {
        //alert("makeEditable");location
        //alert(id);
        $("#image_name").attr("disabled",false);
        $("#vehicle_no").attr("disabled",false);
        $("#survey_date").attr("disabled",false);
        $("#location").attr("disabled",false);
        $("#offence").attr("disabled",false);
        
        document.getElementById("no_of_offence").disabled = false;
        
        document.getElementById("owner_name").disabled = false;
        document.getElementById("father_name").disabled = false;
        document.getElementById("address").disabled = false;
        document.getElementById("mobile_no").disabled = false;
        
        document.getElementById("save").disabled = false; 
        document.getElementById("cancel").disabled = false;
        document.getElementById("deposited_amount").disabled = false;
        document.getElementById("time_h").disabled = false;
        document.getElementById("time_m").disabled = false;
        document.getElementById("lattitude").disabled = false;
        document.getElementById("longitude").disabled = false;
        
        document.getElementById("vehicle_no_state").disabled = false;
        document.getElementById("vehicle_no_city").disabled = false;
        document.getElementById("vehicle_no_series").disabled = false;
        document.getElementById("vehicle_no").disabled = false;mobileapp_btn
        document.getElementById("location_eng").disabled = false;
        
        document.getElementById("mobileapp_btn").disabled = false;
        document.getElementById("controllroom_btn").disabled = false;officer_name
        document.getElementById("vehicle_type").disabled = false;
        
        document.getElementById("officer_name").disabled = false;
        document.getElementById("officer_mobile_no").disabled = false;
        
        //jQuery('#image_id').removeAttr('src');
          jQuery('#blah').hide();
          jQuery('#blah1').hide();

        if(id == "new") {
            //alert("new");
            document.getElementById("location").focus();
             showStructureDiv();
             
            document.getElementById('form2').reset();
            
//            document.getElementById("traffice_police_id").value = '';         
            document.getElementById("image_name").focus();        
            document.getElementById("save").disabled = false;
            
            document.getElementById("deposited_amount").disabled = false;
            document.getElementById("no_of_offence").value = 1;
            document.getElementById("vehicle_no_state").value ="MP";
        document.getElementById("vehicle_no_city").value ="07";
        document.getElementById("vehicle_no_series").value ="R";
            
            //setDefaultColor(document.getElementById("noOfRowsTraversed").value, 25);
        document.getElementById("revise").disabled = true;
        }
        document.getElementById("revise").disabled = false;

        document.getElementById("message").innerHTML = "";
        $("#message").html("");
    }


    function verify() {
        var result;
        //alert("verify");
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Revise' || document.getElementById("clickedButton").value == 'Cancel') {
            var status = document.getElementById("status").value;

                     if(status == 'N') {
                         // document.getElementById("message").innerHTML = "<td colspan='8' bgcolor='coral'><b>Organisation Name is required...</b></td>";
                         $("#message").html( "<td colspan='8' bgcolor='coral'><b>Can't Do any Operation on De-Active Record...</b></td>");

                         return false;
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
                    var noOfColumns = 30;
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
                        
                        //setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                        // alert(lowerLimit);
                        var t1id = "t1c";
                 
                        document.getElementById("traffic_policeId").value= document.getElementById(t1id +(lowerLimit+1)).innerHTML;
                        document.getElementById("traffic_police_id").value= document.getElementById("traffic_police_id"+rowNum).value;
                        //alert(document.getElementById(t1id +(lowerLimit+1)).innerHTML);
                        //document.getElementById("receipt_book_id").value= document.getElementById("receipt_book_id"+rowNum).value;
                        var x= document.getElementById("traffic_police_id").value;
                        //alert("x="+x);
                        var full_vehicle_no=document.getElementById(t1id +(lowerLimit+7)).innerHTML;
                        var vehicle_no_state="";
                        var vehicle_no_city="";
                        var vehicle_no_series="";
                        var vehicle_no="";
                        var length = full_vehicle_no.length;
                       //alert(full_vehicle_no+" "+length);
                       if(length == 9){
                           vehicle_no_state=full_vehicle_no.substring(0,2);
                           vehicle_no_city=full_vehicle_no.substring(2,4);
                           vehicle_no_series=full_vehicle_no.substring(4,5);
                           vehicle_no=full_vehicle_no.substring(5,9);
                           //alert(vehicle_no_state+"-"+vehicle_no_city+"-"+vehicle_no_series+"-"+vehicle_no);
                           
                        }
                        else{
                            vehicle_no_state=full_vehicle_no.substring(0,2);
                           vehicle_no_city=full_vehicle_no.substring(2,4);
                           vehicle_no_series=full_vehicle_no.substring(4,6);
                           vehicle_no=full_vehicle_no.substring(6,10);
                           //alert(vehicle_no_state+"-"+vehicle_no_city+"-"+vehicle_no_series+"-"+vehicle_no);
                            }
                        
                        
                        document.getElementById("vehicle_no_state").value =vehicle_no_state ;
                        document.getElementById("vehicle_no_city").value = vehicle_no_city;
                        document.getElementById("vehicle_no_series").value = vehicle_no_series;
                        document.getElementById("vehicle_no").value = vehicle_no;
                        
                        document.getElementById("location").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
                        document.getElementById("location_eng").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
                        
                         var offence_date = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
                         //alert(offence_date);
                         var dateTime = offence_date.split(" ");
                         var date = dateTime[0];
                         var time = dateTime[1];
                         var time1 = time.split(":");
                         
                         
                        document.getElementById("survey_date").value = date;
                        document.getElementById("time_h").value = time1[0];
                        document.getElementById("time_m").value = time1[1];
                        ///////////////////////////////////////////////////////////////////
                        document.getElementById("officer_name").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
                        document.getElementById("officer_mobile_no").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
                        //alert(document.getElementById(t1id +(lowerLimit+4)).innerHTML);
                        //alert(document.getElementById(t1id +(lowerLimit+5)).innerHTML);
                        ///////////////////////////////////////////////////////////
                        document.getElementById("owner_name").value = document.getElementById(t1id +(lowerLimit+9)).innerHTML;
                        document.getElementById("father_name").value = document.getElementById(t1id +(lowerLimit+10)).innerHTML;
                        document.getElementById("address").value = document.getElementById(t1id +(lowerLimit+12)).innerHTML;
//                        document.getElementById("lattitude").value = document.getElementById(t1id +(lowerLimit+12)).innerHTML;
//                        document.getElementById("longitude").value = document.getElementById(t1id +(lowerLimit+13)).innerHTML;
                        document.getElementById("mobile_no").value = document.getElementById(t1id +(lowerLimit+13)).innerHTML;
                        document.getElementById("location").value = document.getElementById(t1id +(lowerLimit+16)).innerHTML;
                        document.getElementById("deposited_amount").value = document.getElementById(t1id +(lowerLimit+21)).innerHTML;
                       
                       
                       //alert("1");
                        var tp_id = document.getElementById("traffic_police_id").value;
                        //alert(tp_id +"2");
                        var no_of_offence = document.getElementById(tp_id).value;
                        //alert("no_of_offence");
                        $("#no_of_offence").val(no_of_offence);
                        //alert("showStructureDiv  is going to call");
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
//                        doc.getElementById("deposited_amount").value = deposit_amount;
//                        doc.getElementById("salutation").value = doc.getElementById(t1id +(lowerLimit+25)).innerHTML;
//                        doc.getElementById("relative_name").value = doc.getElementById(t1id +(lowerLimit+26)).innerHTML;
//                        doc.getElementById("offender_city").value = doc.getElementById(t1id +(lowerLimit+27)).innerHTML;

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

                            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                        }
//                        document.getElementById("EDIT").disabled = false;
//                        if(!document.getElementById("SAVE").disabled) {
//                            document.getElementById("SAVE AS NEW").disabled = false;
//                            // if save button is already enabled, then make edit, and delete button enabled too.
//                            //doc.getElementById("DELETE").disabled = false;
//                        }
                        //changeValue();
//                        doc.getElementById("reciept_book_no").disabled = true;
//                        doc.getElementById("reciept_page_no").disabled = true;
                        $("#message").html("");
                    }
                


                        function setStatus(id) {
                            if(id == 'save'){
                                document.getElementById("clickedButton").value = "Save";
                            }
                            else if(id == 'save_As'){
                                document.getElementById("clickedButton").value = "Save AS New";
                            }else if(id == 'revise'){
                                document.getElementById("clickedButton").value = "Revise";

                            }else if(id == 'update'){
                                document.getElementById("clickedButton").value = "Update";
                            }else document.getElementById("clickedButton").value = "Cancel";
                        }

                      
                        function openPopUp1(url, window_name, popup_height, popup_width) {
                            var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                            var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                            var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
                            return window.open(url, window_name, window_features);
                        }
                         function openMapForCord() {
        var url="trafficPoliceCont?task=GetCordinates1";//"getCordinate";
        popupwin = openPopUp(url, "",  600, 630);
    }


                        function openPopUp(url, window_name, popup_height, popup_width) {
                            var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                            var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                            var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                            return window.open(url, window_name, window_features);
                        }
                        function openMap(longitude, lattitude) {
                            var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
                            var y = longitude;//$.trim(doc.getElementById(logitude).value);

                            var url="tubeWellSurveyCont?task1=showMapWindow&logitude="+y+"&lattitude="+x;
                            popupwin = openPopUp(url, "",  580, 620);
                        }
                        function openCurrentMap(longitude, lattitude) {
                               var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
                               var y = longitude;//$.trim(doc.getElementById(logitude).value);
                                  var url="tubeWellSurveyCont?task1=showAllTubeWellSurvey&logitude="+y+"&lattitude="+x;
                              window.open(url);
                           //popupwin = openPopUp(url, "",  580, 620);
                                }

                        function isNumberKey(evt){
                            var charCode = (evt.which) ? evt.which : event.keyCode
                            if (charCode > 31 && (charCode < 48 || charCode > 57))
                                return false;
                            return true;
                        }
                        function displayImageList(id){

                            var image = document.getElementById('image'+id).value;
                            var survey_id=document.getElementById('survey_id'+id).value;
                            var image_id=document.getElementById('image_id'+id).value;
                            var queryString = "task1=viewImage&image_name="+image+"&general_image_details_id="+image_id+"&survey_id="+survey_id ;
                            var url = "tubeWellSurveyCont?"+queryString;
                            popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);

                        }

                        function showStructureDiv() {
                        //deleteRow();
                        //alert("showStructureDiv");
                        
                        var table = document.getElementById('structureTable');
                                        
                        table.innerHTML="";
                        var noOfStructure=parseInt(document.getElementById("no_of_offence").value);
                        for(var i=0; i<noOfStructure; i++) {
                        makeStructureDiv();
                        }
                        
                        document.getElementById("structureDiv").style.visibility = "visible";
                        $("#deposited_amount").val(0);
                        
                    }


//                   function getRecordlist(id){
//                        debugger;
//                        var queryString;
//                        var searchOfficerName=document.getElementById("searchOfficerName").value;
//                        var searchBookNo=document.getElementById("searchBookNo").value;
//                        var searchOffenceType=document.getElementById("searchOffenceType").value;
//                        var searchActType=document.getElementById("searchActType").value;
//                        var searchVehicleType=document.getElementById("searchVehicleType").value;
//                        var searchVehicleNo=document.getElementById("searchVehicleNo").value;
//                        var searchFromDate=document.getElementById("searchFromDate").value;
//                        var searchToDate=document.getElementById("searchToDate").value;
//                      
//                        var searchOffenceCode=document.getElementById("searchOffenceCode").value;
//                     
//                        if(id == "view_pdf")
//                            queryString = "task1=PRINTRecordList";
//                        else
//                            queryString = "task1=PrintExcelList";
//                      
//                        var url = "userEntryByImageCont.do?" + queryString + "&searchOfficerName="+searchOfficerName+"&searchBookNo="+searchBookNo+
//                            "&searchOffenceType="+searchOffenceType+"&searchActType="+searchActType+"&searchActType="+searchActType+"&searchVehicleType="+searchVehicleType+
//                            "&searchVehicleNo="+searchVehicleNo+"&searchFromDate="+searchFromDate+"&searchToDate="+searchToDate+"&searchOffenceCode="+searchOffenceCode;
//                        popupwin = openPopUp(url, "list", 600, 900);
//                    }




                     function getRecordlist(id){
                  
                   var queryString;
                   var searchOfficerName=document.getElementById("searchOfficerName").value;
                   alert(searchOfficerName);
                   
                   if(id === "view_pdf")
                   queryString = "task1=PRINTRecordList" ;
               
                      var url = "userEntryByImageCont.do?"+queryString+ "&searchOfficerName="+searchOfficerName;
                     popupwin = openPopUp(url, "list", 500, 1000);

                       }









                     function makeStructureDiv() {
                         //alert("makeStructureDiv");
                        var table = document.getElementById('structureTable');
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
                        var element2 = document.createElement("select");
                        //element2.type = "text";
                        element2.name = "act_origin";
                        element2.id = "act_origin"+rowCount;                //element1.size = 1;
                        var option = document.createElement("option");
                        option.vlaue = "Circular Moter vehicle Rules";
                        option.innerHTML = "Circular Moter vehicle Rules";
                        element2.appendChild(option);

                        var option1 = document.createElement("option");
                        option1.vlaue = "Centeral Moter vehicle Rules 1989";
                        option1.innerHTML = "Centeral Moter vehicle Rules 1989";
                        element2.appendChild(option1);

                        var option2 = document.createElement("option");
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
                        var element4 = document.createElement("input");
                        element4.type = "text";
                        element4.name = "offence_code";
                        element4.id = "offence_code"+rowCount;                //element1.size = 1;
                        element4.value = "";
                        element4.size = 10;
                        //element4.setAttribute('onblur',"getStructureName("+rowCount+")");                //element1.size = 1;
                        element4.setAttribute('class','input');                //element1.size = 1;
//                        element4.setAttribute('required', true);
                        cell2.appendChild(element4);

                        var element6 = document.createElement("input");
                        //var cell4=row.insertCell(1);
                        //cell4.innerHTML = "Offence Code";
//                        
//                        element6.type = "hidden";
//                        cell2.innerHTML = "Offence Code";
                        element6.type = "text";
                        element6.name = "penalty_amount";
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
                        var element5 = document.createElement("input");
                        element5.type = "text";
                        element5.name = "offence_type";
                        element5.id = "offence_type"+rowCount;                //element1.size = 1;
                        element5.value = "";
                        element5.size = 40;
                        //element4.setAttribute('onblur',"getStructureName("+rowCount+")");                //element1.size = 1;
                        element5.setAttribute('class','input');                //element1.size = 1;
                        cell3.appendChild(element5);


                        var cell6=row.insertCell(3);

                var element13 = document.createElement("input");
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
            function getOffenctCode(count){//debugger;
                var act_origin = $("#act_origin"+count).val();
                var vehicle_type = $("#vehicle_type").val();
                var commercial_type = $("#commercial_type").val();
                var queryString = "task=getOffenceCode";
                var url = "trafficPoliceCont?" + queryString + "&act_origin="+act_origin+"&count="+ count ;//+"&vehicle_type="+ vehicle_type +"&commercial_type="+ commercial_type;
                popupwin = openPopUp(url, "Division List", 600, 950);
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
                        function openMap(longitude, lattitude) {
                            var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
                            var y = longitude;//$.trim(doc.getElementById(logitude).value);
                            var url="trafficPoliceSearchCont.do?task=showMapWindow&logitude="+y+"&lattitude="+x;
                            popupwin = openPopUp(url, "",  580, 620);
                        }
                        function generatePDFReport(id){                                                    
                             //var queryString = "task1=viewImage&emp_code="+emp_code;
                             var searchOfficerName=document.getElementById("searchOfficerName").value;
                               var searchActType=document.getElementById("searchActType").value;
                               var searchAmount=document.getElementById("searchAmount").value;
                               var searchOffenceCode=document.getElementById("searchOffenceCode").value;
                               var searchMobileNo=document.getElementById("searchMobileNo").value;
                               var searchVehicleType=document.getElementById("searchVehicleType").value;
                               var searchVehicleNo=document.getElementById("searchVehicleNo").value;
                               var searchCaseDate=document.getElementById("searchCaseDate").value;
                               var searchPlaceOf=document.getElementById("searchPlaceof").value;
                               var searchOffenceType=document.getElementById("searchOffenceType").value;
                               var searchFromDate=document.getElementById("searchFromDate").value;
                             
                               var searchToDate=document.getElementById("searchToDate").value;
                           
                       
                            var queryString = "task1=PRINTRecordList&traffic_police_id="+id;
                            // alert(queryString);
                            var url = "userEntryByImageCont.do?" + queryString+ "&searchOfficerName="+searchOfficerName+ "&searchActType="+searchActType+ "&searchAmount="+searchAmount+ "&searchOffenceCode="+searchOffenceCode+"&searchMobileNo="+searchMobileNo+"&searchVehicleType="+searchVehicleType+"&searchVehicleNo="+searchVehicleNo+"&searchCaseDate="+searchCaseDate+"&searchPlaceof="+searchPlaceOf+"&searchOffenceType="+searchOffenceType+"&searchFromDate="+searchFromDate+"&searchToDate="+searchToDate;
                            popupwin = openPopUp(url, "Show Image", 600, 900);
                        }
                        
                         function generateReport(id){
                               var searchOfficerName=document.getElementById("searchOfficerName").value;
                               var searchActType=document.getElementById("searchActType").value;
                               var searchAmount=document.getElementById("searchAmount").value;
                               var searchOffenceCode=document.getElementById("searchOffenceCode").value;
                               var searchMobileNo=document.getElementById("searchMobileNo").value;
                               var searchVehicleType=document.getElementById("searchVehicleType").value;
                               var searchVehicleNo=document.getElementById("searchVehicleNo").value;
                               var searchCaseDate=document.getElementById("searchCaseDate").value;
                               var searchPlaceOf=document.getElementById("searchPlaceof").value;
                               var searchOffenceType=document.getElementById("searchOffenceType").value;
                              var searchFromDate=document.getElementById("searchFromDate").value;
                                var searchToDate=document.getElementById("searchToDate").value;
                             
                             var queryString = "task1=generateReport&traffic_police_id="+id;
                                 var url = "userEntryByImageCont.do?" + queryString+ "&searchOfficerName="+searchOfficerName+ "&searchActType="+searchActType+ "&searchAmount="+searchAmount+ "&searchOffenceCode="+searchOffenceCode+"&searchMobileNo="+searchMobileNo+"&searchVehicleType="+searchVehicleType+"&searchVehicleNo="+searchVehicleNo+"&searchCaseDate="+searchCaseDate+"&searchPlaceof"+searchPlaceOf+"&searchOffenceType"+searchOffenceType+"&searchFromDate="+searchFromDate+"&searchToDate="+searchToDate;
                              popupwin = openPopUp(url, "Show Image", 600, 900);
                         }
                        
                        
                        
                        
                        function getImageName(input){
                            
                            var selectedOption = $("input:radio[name=option]:checked").val()
                            //alert(selectedOption);
                            var filename = $('input[type=file]').val().replace(/.*(\/|\\)/, '');
                              //var filename = $('input[type=file]').val();
                              //alert(filename);
                              if(selectedOption == "MobileApp"){
                                  //alert(selectedOption);
                                  
                            var arr1 = filename.split("_");
                            var latLongDate = arr1[1];
                            var time = arr1[2];
                            var mobile_ext=arr1[3];
                            var mobile1=mobile_ext.split(".");
                            var mobile_no=mobile1[0];
                            
                            
                            var arr2 = latLongDate.split(",");
                            var lattitude = arr2[0];
                            var longitude = arr2[1];
                            var date = arr2[2];
                            var year = date.substring(0,4);
                            var month = date.substring(4,6);
                            var day = date.substring(6);
                            
                            var finalDate = day+"-"+month+"-"+year;
                            
                            var hours = time.substring(0,2);
                            var minutes = time.substring(2,4);
                            
                            var finalTime = hours+":"+minutes;
                            
                            document.getElementById('lattitude').value=lattitude;
                            document.getElementById('longitude').value=longitude;
                            document.getElementById('survey_date').value=finalDate;
                            document.getElementById('time_h').value=hours;
                            document.getElementById('time_m').value=minutes;
                            
                            document.getElementById('officer_mobile_no').value=mobile_no;
                            
                            
                            if(lattitude > 0.0 & longitude > 0.0)
                            {
                                //alert("aJax Call");
                                var myData=lattitude+" "+longitude;
                                $.ajax({url: "userEntryByImageCont.do?action1=getLocationUsingLatLong", data: "action2="+ myData +"&q=", success: function(response_data) {
                       $("#location").val(response_data.trim());
                            }               
                             });
                                
                                
                            }
                            
                            if(mobile_no  != " ")
                            {
                                //alert(mobile_no);
                                var myData=lattitude+" "+longitude;
                                $.ajax({url: "userEntryByImageCont.do?action1=getOfficerNameUsingMobileNo", data: "action2="+ mobile_no +"&q=", success: function(response_data) {
                       $("#officer_name").val(response_data.trim());
                            }               
                             });
                                
                                
                            }
                            
                            
                                  
                                  
                }
                  if(selectedOption == "ControllRoom"){
              //alert(selectedOption);
              
              var arr1 = filename.split("_");
              
              var location_name = arr1[2];
              var date = arr1[3];
              var time = arr1[4];
              
              var year = date.substring(0,4);
              var month = date.substring(4,6);
              var day = date.substring(6);
              var finalDate = day+"-"+month+"-"+year;
              //alert(finalDate);
              
              var hours = time.substring(0,2);
              var minutes = time.substring(2,4);
              var finalTime = hours+":"+minutes;
             // alert(finalDate);
              
              
              
//                            var latLongDate = arr1[1];
//                            var time = arr1[2];
//                            var arr2 = latLongDate.split(",");
//                            var lattitude = arr2[0];
//                            var longitude = arr2[1];
//                            var date = arr2[2];
//                            var year = date.substring(0,4);
//                            var month = date.substring(4,6);
//                            var day = date.substring(6);
//                            
//                            var finalDate = day+"-"+month+"-"+year;
//                            
//                            var hours = time.substring(0,2);
//                            var minutes = time.substring(2,4);
//                            
//                            var finalTime = hours+":"+minutes;
                            
//                            document.getElementById('lattitude').value=lattitude;
//                            document.getElementById('longitude').value=longitude;
                            document.getElementById('location_eng').value=location_name;
                            document.getElementById('survey_date').value=finalDate;
                            document.getElementById('time_h').value=hours;
                            document.getElementById('time_m').value=minutes;
            
                  }
                                               
    
          }
                            
                        
                        
                        
                        function readURL(input) {
                            getImageName(input);
    //alert(input.files[0]);
            if (input.files && input.files[0] ) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#blah')
                        .attr('src', e.target.result)
                        .width(350)
                        .height(400);
                };
                reader.readAsDataURL(input.files[0]);
                //reader.readAsDataURL(input.files[1]);
               // alert(reader.readAsDataURL(input.files[0]););
            }
            readURL1(input);
        }
        
        function readURL1(input) {
    //alert(input.files[1]);
            if (input.files && input.files[1] ) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#blah1')
                        .attr('src', e.target.result)
                        .width(350)
                        .height(400);
                };
                reader.readAsDataURL(input.files[1]);
                //reader.readAsDataURL(input.files[1]);
               // alert(reader.readAsDataURL(input.files[0]););
            }
        //    readURL1(input);
        //jQuery('#image_id').removeAttr('src')
        jQuery('#blah').show();
        jQuery('#blah1').show();
        }
        
        function removeSpace(id){
            var s = $("#"+id).val();
            var s2;
            var textValue =  $.trim($("#"+id).val());
           if(textValue ==''){
           $.trim($("#"+id).val('')); //to set it blank
           } else {
              s2 = s.trim();
              //alert(s2.length);
              document.getElementById(id).value=s2;
              getFocusNextField(id,s2);
                }
    }
        
        function getFocusNextField(id,s3){
           var len = s3.length;
            if(id == "vehicle_no_state"){
                if(len == 2)
                    $("#vehicle_no_city").focus();
            }else if(id == "vehicle_no_city"){
                if(len == 2)
                    $("#vehicle_no_series").focus();
            }else if(id == "vehicle_no_series"){
                if(len == 2)
                    $("#vehicle_no").focus();
                //$("#vehicle_no").focus();
            }else if(id == "vehicle_no"){
//                if(len == 4)
                   // $("#remark").focus();
            }
        }
        
        function checkForSecondOffence() {
//            alert("vivek");
var final_string;
var city_location;
var dateTime;
            var vehicle_no_state = $("#vehicle_no_state").val();
            var vehicle_no_city = $("#vehicle_no_city").val();
            var vehicle_no_series = $("#vehicle_no_series").val();
            var vehicle_no = $("#vehicle_no").val();
            //alert(vehicle_no);
            var length = vehicle_no.length;
            if(length == 4){
            //alert(length);   
            $.ajax({
                    dataType: "json",
                    async : false,
                    url: "userEntryByImageCont.do?task2=checkSecondOffence&vehicle_no_state="+vehicle_no_state+"&vehicle_no_city="+vehicle_no_city+"&vehicle_no_series="+vehicle_no_series+"&vehicle_no="+vehicle_no,
                    success: function(response_data) {
                        var arr = response_data.dateTime;
                        final_string=arr[0]["myString"];
                        var s = final_string.split(",");
                        
                        city_location = s[0];
                        dateTime = s[1];

//                        for(var i=0;i<datetime.length;i++){
//                            var d=datetime[i]["date_time"];
//                 
//                            var a=d.split(",");
//
//
//                            dateTime1[i]=new Date(a[0], a[1], a[2], a[3], a[4]);
//                 
//                        }
                        alert("This vehicle is already caught for traffic rule violence on "+city_location+" at "+dateTime);
       
                    }
                });
            
            
           }
        
            
//           var x = document.getElementById("fname");
//           x.value = x.value.toUpperCase();
             }
             function chkPanelChanged(){
                 
                 //alert("chkPanelChanged");
             }
             
             function showlocationTD(){
                      var radioValue = $("input[name='option']:checked").val();
                      if(radioValue){
                     //alert("Your are a - " + radioValue);
                     }
                     if(radioValue == "MobileApp"){
                         document.getElementById("location_eng_td").style.display = "none";
                         document.getElementById("location_td").style.display = "block";
                         
                         document.getElementById("lattitude").disabled =false;
                         document.getElementById("longitude").disabled =false;
                         
                      }
        
                    if(radioValue == "ControllRoom"){
                         
                         document.getElementById("location_td").style.display = "none";
                         document.getElementById("location_eng_td").style.display = "block";
                         
                         document.getElementById("lattitude").disabled =true;
                         document.getElementById("longitude").disabled =true;
                     }
                     
                     
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
                                        <td align="center" class="header_table" width="100%">User Entry</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td></tr>

            <tr>
                                    <td  align="center">
                                        <form name="form0" method="post" action="userEntryByImageCont.do">
                                            <table  align="center"  class="heading1">
                                                <tr>
                                                    <td>
                                                        Officer Name
                                                        <input class="input new_input" type="text" id="searchOfficerName" name="searchOfficerName" value="${searchOfficerName}" size="20" >
<!--                                                        Challan Book No
                                                        <input class="input" type="text" id="searchBookNo" name="searchBookNo" value="${searchBookNo}" size="15" >
                                                        Receipt Book No
                                                        <input class="input" type="text" id="searchReceiptBookNo" name="searchReceiptBookNo" value="${searchReceiptBookNo}" size="15" >
                                                        Jarayam No.
                                                        <input class="input" type="text" id="searchJarayamNo" name="searchJarayamNo" value="${searchJarayamNo}" size="10" >-->
                                                        Challan No.
                                                        <input class="input" type="text" id="searchChallanNo" name="searchChallanNo" value="${searchChallanNo}" size="10" >
                                                    </td>
<!--                                                    <td>
                                                        Challan No.
                                                        <input class="input" type="text" id="searchChallanNo" name="searchChallanNo" value="${searchChallanNo}" size="10" >
                                                    </td>-->
                                                </tr>
                                                <tr>
                                                    <td>
                                                        Offence Type
                                                        <input class="input new_input" type="text" id="searchOffenceType" name="searchOffenceType" value="${searchOffenceType}" size="30" >
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
                                                        officer Mobile no
                                                         <input class="input" type="text" id="searchMobileNo" name="searchMobileNo" value="${searchMobileNo}" size="15" >
                                                        License No.
                                                           <input class="input" type="text" id="searchLicense" name="searchLicense" value="${searchOffenderName}" size="15" >
                                                         Offender Name
                                                          <input class="input" type="text" id="searchOffenderName" name="searchOffenderName" value="${searchOffenderName}" size="15" >
                                                         Offender Age
                                                          <input class="input" type="text" id="searchAge" name="searchAge" value="${searchAge}" size="15" >
                                                         Offender License No.
                                                        <input class="input" type="text" id="searchOffenderLicenseNo" name="searchOffenderLicenseNo" value="${searchOffenderLicenseNo}" size="20" >
<!--                                                         Offence City
                                                         <input class="input" type="text" id="searchOffenceCity" name="searchOffenceCity" value="${searchOffenceCity}" size="15" >-->
                                                        Place Of Offence
                                                          <input class="input" type="text" id="searchPlaceof" name="searchPlaceof" value="${searchPlaceof}" size="15" >                                                        
                                                        Deposited amount in court
                                                         <input class="input" type="text" id="searchAmount" name="searchAmount" value="${searchAmount}" size="15" >
                                                       Offence Date
                                                          <input class="input" type="text" id="searchCaseDate" name="searchCaseDate" value="${searchCaseDate}" size="15" >
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

                                                        <input type="button" id="view_pdf" class="pdf_button" name="view_pdf" value="" onclick="generatePDFReport(id)">
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
                                            <form name="form1" action="userEntryByImageCont.do" method="post">
                                                <table id="table1" border="1" align="center" class="content" width="980">
                                                    <tr align="center">
                                                        <th class="heading">S.No</th>

                                                        <th class="heading" style="white-space: normal">Challan Book No.</th>
                                                        <th class="heading" style="white-space: normal">Challan Book Rev. No.</th>
                                                        <th class="heading" style="white-space: normal">Officer Name</th>
<!--                                                        <th class="heading" style="white-space: normal">Jarayam No.</th>-->
                                                        <th class="heading" style="white-space: normal">Officer Mobile No.</th>
                                                        <th class="heading" style="white-space: normal">Offence Date</th>
                                                        <th class="heading" style="white-space: normal">Vehicle No.</th>
                                                        <th class="heading"style="white-space: normal">License No.</th>
                                                        <th class="heading">Offender Name</th>
                                                        <th class="heading">Relative Name</th>
                                                        <th class="heading" style="white-space: normal">Offender Age</th>
                                                        <th class="heading"style="white-space: normal">Offender Address</th>
                                                        <th class="heading">Contact no.</th>
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
                                                        <th class="heading" style="white-space: normal">Report</th>



                                                    </tr>
                                                    <c:forEach var="tp" items="${requestScope['list']}" varStatus="loopCounter">
                                                        <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                                <input type="hidden" id="traffic_police_id${loopCounter.count}" value="${tp.traffic_police_id}">
                                                                <input type="hidden" id="${tp.traffic_police_id}" value="${fn:length(tp.offenceList)}">
                                                                
                                                                <%--   <input type="hidden" id="balance_unit_id_gen${loopCounter.count}" value="${st.balance_unit_id}">--%>

                                                            </td>
                                                            <td style="display:none;" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.traffic_police_id}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.book_no}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.book_revision_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="" width="90%">${tp.key_person_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.mobile_no1}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.offence_date}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.vehicle_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.offender_license_no}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}" class=""  onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.offender_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)"  style="white-space: normal" width="90%">${tp.father_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.offender_age}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)">${tp.offender_address}</td><!--class="new_input"-->
                                                            <td id="t1c${IDGenerator.uniqueID}" class="" onclick="fillColumns(id)">${tp.mobile_no}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${tp.city}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.zone}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${tp.offence_place}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.receipt_no}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.receipt_book_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">${tp.receipt_book_rev_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.receipt_page_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.deposited_amount}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.processing_type}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.relation_type}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.case_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${tp.case_date}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${tp.remark}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}" style="display: none"  onclick="fillColumns(id)">${tp.salutation}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" style="display: none"  onclick="fillColumns(id)">${tp.relative_name}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" class="new_input" style="display: none"  onclick="fillColumns(id)">${tp.offender_city}</td>

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
                                                            <input type="button" class="btn"  value ="View Map" id="get_cordinate" onclick="openMapForCord()">
                                                        </th>

                                                        <th style="background-color: lightblue" colspan="14">
                                                      
                                                            <c:if test = "${tp.amount_paid eq 'Yes'}">
                                                            <input type="button" class="btn" value="Generate Report" id="${tp.traffic_police_id}" onclick="generateReport(id)">
                                                          </c:if>
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
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}" >
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}" >
                                                <input type="hidden"  name="searchOfficerName" value="${searchOfficerName}" >
                                                <input type="hidden"  name="searchBookNo" value="${searchBookNo}" >
                                                <input type="hidden" id="searchOffenceType" name="searchOffenceType" value="${searchOffenceType}" >
                                                <input type="hidden" id="searchActType" name="searchActType" value="${searchActType}" >
                                                <input type="hidden" id="searchVehicleType" name="searchVehicleType" value="${searchVehicleType}" >
                                                <input type="hidden" id="searchVehicleNo" name="searchVehicleNo" value="${searchVehicleNo}" >
                                                <input type="hidden" id="searchFromDate" name="searchFromDate" value="${searchFromDate}" >
                                                <input type="hidden" id="searchToDate" name="searchToDate" value="${searchToDate}" >
                                                <input type="hidden" id="searchJarayamNo" name="searchJarayamNo" value="${searchJarayamNo}" >
                                                <input type="hidden" id="searchOffenceCode" name="searchOffenceCode" value="${searchOffenceCode}" >
                                                <input type="hidden" name="searchReceiptBookNo" value="${searchReceiptBookNo}" >
                                                <input type="hidden" id="searchMobileNo" name="searchMobileNo" value="${searchMobileNo}"  >
                                                <input type="hidden" name="searchLicense" value="${searchLicense}" >
                                                 <input type="hidden" id="searchOffenderName" name="searchOffenderName" value="${searchOffenderName}">
                                                <input type="hidden" name="searchAge" value="${searchAge}" >
                                                 <input type="hidden" id="searchOffenderLicenseNo" name="searchOffenderLicenseNo" value="${searchOffenderLicenseNo}" >
                                                <input type="hidden" name="searchOffenceCity" value="${searchOffenceCity}" >
                                                 <input type="hidden" id="searchPlaceOf" name="searchPlaceOf" value="${searchPlaceOf}" >
                                                <input type="hidden" name="searchAmount" value="${searchAmount}" >
                                                 <input type="hidden" id="searchCaseDate" name="searchCaseDate" value="${searchCaseDate}" >
                                                 
                                                
                                                
                                            </form>
                                        </div>
                                    </td>
                                </tr>










          <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
<!--                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%">User Entry</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td></tr>-->

                      
                        <tr>
                            <td align="center">
                              
                                    <form id="form2"  name="form2" method="POST" enctype="multipart/form-data"  action="userEntryByImageCont.do" onsubmit="return verify()" >
<!--                                        <table id="table2"  class="content" border="0"  align="center" width="953">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr> 
                                        </table>-->
                                            <table id="table5"  class="content" border="0"  align="center" width="953">
                                                
                                                <tr>
                                                    <td>
                                                        <input type="radio" name="option" id="mobileapp_btn" value="MobileApp" onclick="showlocationTD(this)" disabled >Mobile App</input>

                                                    </td>                                                    
                                                    <td>
                                                       <input type="radio" name="option" id="controllroom_btn" value="ControllRoom" onclick="showlocationTD(this)" disabled>Controll Room</input>
                                                    </td>
                                                    <th>MP Website Link</th>
                                                    <td>
                                                        <a href="http://mis.mptransport.org/MPLogin/eSewa/VehicleSearch.aspx" target="_blank">MP Transport</a> 
                                                    </td>
                                                 </tr>
                                            
                                                <tr>  
                                                    
                                                    <th class="heading1" >Vehicle No : </th>
                                                                <td>
                                                                    <input class="input" type="text" id="vehicle_no_state" name="vehicle_no_state" value="${vehicle_no_state}" size="2" maxlength="2" onkeyup="removeSpace(id);" value="MP" disabled required>
                                                                    <input class="input" type="text" id="vehicle_no_city" name="vehicle_no_city" value="${vehicle_no_city}" size="2" maxlength="2" onkeyup="removeSpace(id);"  value="07" disabled required>
                                                                    <input class="input" type="text" id="vehicle_no_series" name="vehicle_no_series" value="" size="4" maxlength="5" onkeyup="removeSpace(id);"  value="R" disabled required>
                                                                    <input class="input" type="text" id="vehicle_no" name="vehicle_no" value="" size="4" maxlength="4" onkeyup="removeSpace(id);" onfocusout="checkForSecondOffence()" disabled required>
<!--                                                                    <input class="input" type="text" id="vehicle_no" name="vehicle_no" value="" size="4" maxlength="4" onkeyup="getFocusNextField(id);"  disabled required>-->
<!--                                                                    <input class="input new_input" type="text" id="remark" name="remark" value="" size="20" maxlength="" placeholder="fofgdy dk fooj.k" disabled>-->
                                                                <input style="display:none;" type="text" id="traffic_policeId" name="traffic_policeId" value="" size="18"  >
                                                                </td>
                                                  
                                                    <th class="heading1" style=" width:16%">Location</th>
                                                    <td  id="location_td" ><input class="input new_input"  type="text" id="location" name="location" value="" size="26" placeholder="d" disabled></td>
                                                    
                                                    <td style="display: none;" id="location_eng_td"><input class="input" type="text" id="location_eng" name="location_eng" value="" size="26" disabled></td>
<!--                                                     <th class="heading1" style=" width:18%">Vehicle No</th>
                                                    <td>
                                                        <input class="input" type="text" id="vehicle_no" name="vehicle_no" value="" size="22" disabled>
                                                    </td>
                                                    -->  
                                                </tr>
                                                <tr>
                                                    <th class="heading1" style=" width:18%">Date</th> 
                                                    <td><input class="input" type="text" id="survey_date" name="survey_date" value="" size="18" disabled required>

                                                    <b>Time</b>
                                                        <input class="input " type="numeric" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" id="time_h" name="time_h" value="" maxlength="2" size="2" onkeyup="" disabled required>
                                                        <input class="input " type="numeric" pattern="[0-5]{1}[0-9]{1}" id="time_m" name="time_m" value="" maxlength="2" size="2" onkeyup="" disabled required>
                                                    
                                                    
                                                    </td>
                                                    
                                                     <th class="heading1">Upload Image</th>
                                                    <td>
                                                        <input type="file" id="image_name" name="image_name" onchange="readURL(this);" value="" size="35" multiple="muliple">
                                                    </td>

                                                 </tr>
                                                 
                                                 <tr>
                                                 <th class="heading1" style=" width:18%">Owner Name</th>
                                                    
                                                    <td><input class="input" type="text" id="owner_name" name="owner_name" value="" size="18" disabled >

                                                    </td>
                                                    <th class="heading1" style=" width:18%">Relative Name</th>
                                                    
                                                    <td><input class="input" type="text" id="father_name" name="father_name" value="" size="18" disabled >

                                                    </td>
                                                 </tr>
                                                 
                                                 <tr>
                                                 <th class="heading1" style=" width:18%">Address</th>   
                                                 <td><textarea class="input" type="text" id="address" name="address" value="" rows='6' cols='40' size="18" disabled ></textarea>
                                                 </td>
                                                 
                                                    
                                                 </tr>
                                                 <tr>
                                                     
                                                 <th class="heading1" style=" width:18%">Lattitude</th>   
                                                 <td><input class="input" type="text" id="lattitude" name="lattitude" value=""  size="18" disabled>
                                                 </td>
                                                 
                                                 <th class="heading1" style=" width:18%">Longitude</th>   
                                                 <td><input class="input" type="text" id="longitude" name="longitude" value=""  size="18" disabled>
                                                 </td>
                                                     
                                                     
                                                 </tr>
                                                 <tr>
                                                   <th class="heading1" style=" width:18%">Offender Mobile no.</th>
                                                    
                                                    <td><input class="input" type="text" id="mobile_no" name="mobile_no" value="" size="18" disabled>

                                                    </td>
                                                    <th class="heading1" style=" width:18%">Vehicle Type</th>
                                                    <td><input class="input" type="text" id="vehicle_type" name="vehicle_type" value="" size="18" disabled>
                                                    </td>
                                                     
                                                 </tr>
                                                 
                                                 
                                                 
                                                 
                                                 
                                                  <tr>
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

                                                               <th class="heading1">Deposit Amount </th><td><input class="input" type="text" id="deposited_amount" name="deposited_amount" value="0" size="15" onkeyup="IsNumeric(id)" maxlength="" disabled required></td>


                                                </tr>
                                                <tr>
                                                   <th class="heading1" style=" width:18%">Officer Name</th>
                                                    
                                                    <td><input class="input" type="text" id="officer_name" name="officer_name" value="" size="18" disabled>

                                                    </td>
                                                    <th class="heading1" style=" width:18%">Officer Mobile No.</th>
                                                    <td><input class="input" type="text" id="officer_mobile_no" name="officer_mobile_no" value="" size="18" disabled>
                                                    </td>
                                                     
                                                 </tr>
                                                <tr>

                                                     <td colspan="4">
                                                                    <div  id="structureDiv"  STYLE="visibility: visible;margin-bottom: 10px;overflow: auto;height:auto;width:auto " align="center">
                                                                        <table class="content" id="structureTable" align="center" width="100%" >
                                                                        </table>
                                                                        <input type="hidden" name="isPopup" value="No">
                                                                        <input type="hidden" id="viewStatus" value="${viewStatus}">
                                                                        <input type="hidden" id="offenceCodeValues" name="offenceCodeValues"  value="${viewStatus}">
                                                                    </div>
                                                                </td>

                                                </tr>
                                            </table>
                                        </table>
                                               
                                </div>

                                        <!-- hidden fields-->                                                                              
                                        <table align='center'>  <tr>
                                                <td  colspan="6">
                                                    <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
<!--                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled> &nbsp;&nbsp;-->
                                                    <input class="button" type="button" name="new" id="new" value="New" onclick="makeEditable(id)"> &nbsp;&nbsp;
                                                    <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)"> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="cancel" value="Delete" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                <td><img id="blah" src="#" alt="your image1"  height="500" width="600"/>
                   <img id="blah1" src="#" alt="your image2" height="500" width="600"/></td>
                </tr>
                                            
                                        </form>        
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                            <input type="hidden" id="clickedButton" value="">
                                                            <input type="hidden"  name="searchStatusType" value="${searchStatusType}">
                                                        
                                            <input type="hidden"  id="traffic_police_id" name="traffic_police_id" value="" >                                          

                                        </table>
                                 
                                
                            </td>
<!--                        </tr>-->

                        <input type="hidden" id="lowerLimitBottom" name="lowerLimit" value="${lowerLimit}">
                                <input type="hidden" id="noOfRowsTraversedTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                <input type="hidden" id="clickedButton" value="">
                                <input type="hidden"  name="searchOfficerName" value="${searchOfficerName}" >
                                <input type="hidden"  name="searchBookNo" value="${searchBookNo}" >
                                <input type="hidden"  name="searchOffenceType" value="${searchOffenceType}" >
                                <input type="hidden"  name="searchActType" value="${searchActType}" >
                                <input type="hidden" id="searchMobileNo" name="searchMobileNo" value="${searchMobileNo}">
                                <input type="hidden" name="searchLicense" value="${searchLicense}"
                                <input type="hidden" id="searchOffenderName" name="searchOffenderName" value="${searchOffenderName}">
                                <input type="hidden" name="searchAge" value="${searchAge}"
                                <input type="hidden" id="searchOffenderLicenseNo" name="searchOffenderLicenseNo" value="${searchOffenderLicenseNo}">
                                <input type="hidden" name="searchOffenceCity" value="${searchOffenceCity}"
                                <input type="hidden" id="searchPlaceOf" name="searchPlaceOf" value="${searchPlaceOf}">
                                <input type="hidden" name="searchAmount" value="${searchAmount}"
                                <input type="hidden" id="searchCaseDate" name="searchCaseDate" value="${searchCaseDate}">
                         <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>       
                                
                    </table>

                <!--</div>
            </td>-->
            
            
<!--        </table>-->
    </body>
</html>

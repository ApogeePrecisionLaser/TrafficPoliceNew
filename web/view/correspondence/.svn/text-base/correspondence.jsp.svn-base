<%-- 
    Document   : correspondence
    Created on : Sep 11, 2014, 12:33:42 PM
    Author     : Shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Correspondence</title>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <!--<script language="javascript" type="text/javascript" src="JS/calendar.js"></script>-->
        <script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
        <link rel="stylesheet" href="datePicker/jquery.ui.all.css">

        <script type="text/javascript" language="javascript">
            var popupwin = null
            var doc = document;
            jQuery(function(){
                $("#dealing_person").autocomplete("correspondenceCont.do", {
                    extraParams: {
                        action1: function() { return "getkey_personlist"}
                    }
                });
                $("#key_person").autocomplete("correspondenceCont.do", {
                    extraParams: {
                        action1: function() { return "getkey_personlist"}
                    }
                });search_org_from
                $("#source_organisation").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganization"}
                    }
                });

                $("#search_org_from").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganization"}
                    }
                });
                $("#search_org_from").result(function(event, data, formatted){
                    getSearchSourceofficelist();

                });

                $("#destination_organisation").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganization"}
                    }
                });

                $("#source_organisation").result(function(event, data, formatted){
                    getSourceofficelist();
                  
                });
                $("#destination_organisation").result(function(event, data, formatted){
                    var org= $(this).val();
                    getDesofficelist();

                });

                $("#priority").autocomplete("correspondencePriorityCont.do", {
                    extraParams: {
                        action1: function() { return "getPriorityList"}
                    }
                });

                $("#reason").autocomplete("correspondenceRemarkCont.do", {
                    extraParams: {
                        action1: function() { return "getRemarkList"}
                    }
                });

                $("#status").autocomplete("correspondenceStatusCont.do", {
                    extraParams: {
                        action1: function() { return "getStatusType"}
                    }
                });

                $("#search_subject").autocomplete("correspondenceCont.do", {
                    extraParams: {
                        action1: function() { return "getSubjectList"}
                    }
                });
                $("#other_person").autocomplete("correspondenceCont.do", {
                    extraParams: {
                        action1: function() { return "getOtherPersonList"},
                        org_name: function() { return $("#source_organisation").val();},
                        office_name: function() { return $("#src_office").val();}
                    }
                });



                var date_properties = {showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true};

                $("#corr_date").datepicker({
                    //minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });

                $("#ref_corr_date").datepicker({
                    //minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });

                $("#register_date").datepicker({
                    //minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
               
            });


            function getDesofficelist(){
                var organisation_name=  $("#destination_organisation").val();
                var queryString = "task=getOfficeList&organisation_name="+organisation_name;
                $("#des_office option").each(function()
                {
                    $(this).remove();
                });
                $.ajax({url: "correspondenceCont.do", async: true, data: queryString, success: function(response_data) {

                        if(response_data != ''){
                            var arr = response_data.split("&#");
                            var i;
                            for(i = 0; i < arr.length-1; i++) {
                                var opt = doc.createElement("option");
                                opt.text = $.trim(arr[i]);
                                opt.value = $.trim(arr[i]);
                                doc.getElementById("des_office").options.add(opt);
                            }
                        }else{
                            //  $("#correspondence_no").html('<option>Select</option>');
                        }
                    }
                });

            }


            function getSourceofficelist(src_office){
                var organisation_name=  $("#source_organisation").val();
                var queryString = "action1=getOfficeList&organisation_name="+organisation_name;
                $("#src_office option").each(function()
                {
                    $(this).remove();
                });
                $.ajax({url: "correspondenceCont.do", async: true, data: queryString, success: function(response_data) {
                        if(response_data != ''){
                            var arr = response_data.split("&#");
                            var i;
                            for(i = 0; i < arr.length-1; i++) {
                                var opt = doc.createElement("option");
                                opt.text = $.trim(arr[i]);
                                opt.value = $.trim(arr[i]);
                                doc.getElementById("src_office").options.add(opt);
                            }
                            doc.getElementById("src_office").value = src_office;
                        }else{
                            //  $("#correspondence_no").html('<option>Select</option>');
                        }
                    }
                });

            }

            function getSearchSourceofficelist(){//debugger;
                var organisation_name = $("#search_org_from").val();
                var search_office_hd = $("#search_office_hd").val();
                var queryString = "action1=getOfficeList&organisation_name="+organisation_name;
                $("#search_office option").each(function()
                {
                    $(this).remove();
                });
                var opt = doc.createElement("option");
                opt.text = "ALL";
                opt.value = "ALL";
                doc.getElementById("search_office").options.add(opt);
                if(organisation_name.trim() != ""){
                    $.ajax({url: "correspondenceCont.do", async: true, data: queryString, success: function(response_data) {
                            if(response_data != ''){
                                var arr = response_data.split("&#");
                                var i;
                                for(i = 0; i < arr.length-1; i++) {
                                    opt = doc.createElement("option");                                
                                    opt.text = $.trim(arr[i]);
                                    opt.value = $.trim(arr[i]);
                                    doc.getElementById("search_office").options.add(opt);
                                }
                                if(search_office_hd != "" || search_office_hd != "ALL")
                                    $("#search_office").val(search_office_hd);
                            }else{
                                //  $("#correspondence_no").html('<option>Select</option>');
                            }
                    }
                    });
                }

            }
//            function makeEditable(id) {
//                if(id == 'New') {
//                    $("#message").html('');
//                }
//                if(id == 'Edit') {
//                }
//                doc.getElementById("Save").disabled = false;
//            }
            function makeEditable(id) {
                doc.getElementById("correspondece_no").disabled = false;
                doc.getElementById("subject").disabled = false;
                doc.getElementById("type_of_correspondence").disabled = false;
                doc.getElementById("reply_forward").disabled = false;
                doc.getElementById("reply_yes").disabled = false;
                doc.getElementById("reply_no").disabled = false;
                doc.getElementById("type_of_document").disabled = false;
                //$("#type_of_document").attr("disabled", false);
                doc.getElementById("source_organisation").disabled = false;
                doc.getElementById("src_office").disabled = false;
                doc.getElementById("other_person").disabled = false;
                doc.getElementById("refrence_no").disabled = false;
                doc.getElementById("corr_date").disabled = false;
                doc.getElementById("dealing_person").disabled = false;
                doc.getElementById("key_person").disabled = false;
                doc.getElementById("own_ref_no").disabled = false;
                doc.getElementById("ref_corr_date").disabled = false;
                doc.getElementById("register_date").disabled = false;
                doc.getElementById("description").disabled = false;
                doc.getElementById("status").disabled = false;
                doc.getElementById("priority").disabled = false;
                doc.getElementById("reason").disabled = false;
                doc.getElementById("is_cc").disabled = false;
                //doc.getElementById("image").disabled = false;
                doc.getElementById("save").disabled = false;

                if(id == 'new') {
                    //doc.getElementById("message").innerHTML = "";      // Remove message
                    //$("#message").html("");
                    doc.getElementById("correspondence_id").value = 0;
                    var task_reply = $("#task_reply").val();
                    if(task_reply == "Y"){
                        $("#is_task_reply").val(task_reply);
                        $("#task_reply").val("N");
                    }else
                        $("#is_task_reply").val("N");
                    doc.getElementById("edit").disabled = true;
                    doc.getElementById("delete").disabled = true;
                    doc.getElementById("save_as").disabled = true;
                    setDefaultColor(doc.getElementById("noOfRowsTraversed").value, 21);
                    doc.getElementById("correspondece_no").focus();
                }
            if(id == 'edit'){
                doc.getElementById("save_as").disabled = false;
                doc.getElementById("delete").disabled = false;
            }
        }

        function makeDisable(){
            doc.getElementById("correspondece_no").disabled = true;
                doc.getElementById("subject").disabled = true;
                doc.getElementById("type_of_correspondence").disabled = true;
                doc.getElementById("reply_forward").disabled = true;
                doc.getElementById("reply_yes").disabled = true;
                doc.getElementById("reply_no").disabled = true;
                doc.getElementById("type_of_document").disabled = true;
                //$("#type_of_document").attr("disabled", false);
                doc.getElementById("source_organisation").disabled = true;
                doc.getElementById("src_office").disabled = true;
                doc.getElementById("other_person").disabled = true;
                doc.getElementById("refrence_no").disabled = true;
                doc.getElementById("corr_date").disabled = true;
                doc.getElementById("dealing_person").disabled = true;
                doc.getElementById("key_person").disabled = true;
                doc.getElementById("own_ref_no").disabled = true;
                doc.getElementById("ref_corr_date").disabled = true;
                doc.getElementById("register_date").disabled = true;
                doc.getElementById("description").disabled = true;
                doc.getElementById("status").disabled = true;
                doc.getElementById("priority").disabled = true;
                doc.getElementById("reason").disabled = true;
                doc.getElementById("is_cc").disabled = true;
        }

            function setStatus(id) {
              
                if(id == 'Save') {
                    doc.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'Delete'){
                    doc.getElementById("clickedButton").value = "Delete";
                }
                else {
                    doc.getElementById("clickedButton").value = "Save As New";;
                }
            }

            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        doc.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";
                        //    alert("t1c" + (i * noOfColumns + j));// set the default color.
                    }
                }
            }

            function fillColumns(id) {
                var noOfRowsTraversed = doc.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 21;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);   
                var lowerLimit, higherLimit, rowNum = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNum++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                var lower= lowerLimit;
                try{
                    setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                }catch(e){
                   
                }
                var t1id = "t1c";
                doc.getElementById("correspondence_id").value = doc.getElementById("correcpondence_id"+rowNum).value;
                doc.getElementById("revision").value = doc.getElementById("revision"+rowNum).value;
                var ref_corres_id = doc.getElementById("ref_corres_id" + rowNum).value;
                var is_forward = doc.getElementById("is_forward"+rowNum).value;
                var is_reply = doc.getElementById("is_reply"+rowNum).value;
                doc.getElementById("correspondece_no").value = doc.getElementById(t1id + (lowerLimit + 1)).innerHTML;
                doc.getElementById("subject").value = doc.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                var type_of_correspondence = doc.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                doc.getElementById("type_of_correspondence").value = type_of_correspondence;
                var type_of_doc = doc.getElementById(t1id + (lowerLimit + 4)).innerHTML;

                $("#type_of_document option" ).each(function()
                {
                    if($(this).text() == type_of_doc){
                        $(this).attr('selected', true);
                    }
                });
                doc.getElementById("source_organisation").value = doc.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                var src_office = doc.getElementById(t1id + (lowerLimit + 6)).innerHTML;
                getSourceofficelist(src_office);
                //doc.getElementById("destination_organisation").value = doc.getElementById(t1id + (lowerLimit + 7)).innerHTML;
                //getDesofficelist();
                
                //doc.getElementById("des_office").value = doc.getElementById(t1id + (lowerLimit + 8)).innerHTML;
                doc.getElementById("other_person").value = doc.getElementById(t1id + (lowerLimit + 7)).innerHTML;
                var refrence_no = doc.getElementById(t1id + (lowerLimit + 8)).innerHTML;
                doc.getElementById("refrence_no").value = refrence_no;
                doc.getElementById("corr_date").value = doc.getElementById(t1id + (lowerLimit + 9)).innerHTML;
                doc.getElementById("dealing_person").value = doc.getElementById(t1id + (lowerLimit + 10)).innerHTML;
                doc.getElementById("key_person").value = doc.getElementById(t1id + (lowerLimit + 11)).innerHTML;
                var own_ref_no = doc.getElementById(t1id + (lowerLimit + 12)).innerHTML;
                doc.getElementById("own_ref_no").value = own_ref_no;
                var ref_corr_date = doc.getElementById(t1id + (lowerLimit + 13)).innerHTML;
                doc.getElementById("ref_corr_date").value = ref_corr_date;
                doc.getElementById("register_date").value = doc.getElementById(t1id + (lowerLimit + 14)).innerHTML;
                doc.getElementById("description").value = doc.getElementById(t1id + (lowerLimit + 15)).innerHTML;
                doc.getElementById("status").value = doc.getElementById(t1id + (lowerLimit + 16)).innerHTML;
                doc.getElementById("priority").value = doc.getElementById(t1id + (lowerLimit + 17)).innerHTML;
                doc.getElementById("reason").value = doc.getElementById(t1id + (lowerLimit + 18)).innerHTML;
                doc.getElementById("is_cc").value = doc.getElementById(t1id + (lowerLimit + 19)).innerHTML;
                doc.getElementById("image").value = doc.getElementById("image"+rowNum).value;

//                if(type_of_correspondence == "Outgoing" && refrence_no == "" && ref_corr_date == "" && ref_corres_id != ""){
//                    $("#reply_forward").val("Forward");
//                    $("#reply_yes").attr("checked", true);
//                }

                if(type_of_correspondence == "Outgoing" && is_reply == "Y"){
                    $("#reply_forward").val("Reply");
                    $("#reply_yes").attr("checked", true);
                }else if(type_of_correspondence == "Outgoing" && is_forward == "Y"){
                    $("#reply_forward").val("Forward");
                    $("#reply_yes").attr("checked", true);
                }else if(type_of_correspondence == "Incoming" && is_reply == "Y"){
                    $("#reply_yes").attr("checked", true);
                }else
                    $("#reply_no").attr("checked", true);

                changeStatus();

                for(var i = 0; i < noOfColumns; i++) {
                    doc.getElementById(t1id + (lower + i)).bgColor = "#dodafd";        // set the background color of clicked row to yellow.
                }

                doc.getElementById("edit").disabled = false;
                //if(!doc.getElementById("save").disabled) {
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    doc.getElementById("save").disabled = true;//                    doc.getElementById("New").disabled = true;
                //}
                doc.getElementById("save_as").disabled = true;
                doc.getElementById("delete").disabled = true;
                makeDisable();
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

            function verify() {//debugger;
                try{
                    var result;
                    if(doc.getElementById("clickedButton").value == 'Save' || doc.getElementById("clickedButton").value == 'Save As New') {
                        var type_of_correspondence = doc.getElementById("type_of_correspondence").value;
                        var reply_forward = doc.getElementById("reply_forward").value;
                        var is_reply = $("input[type=radio][name=reply]:checked").val();
                        var correspondece_no = doc.getElementById("correspondece_no").value;
                        var subject = doc.getElementById("subject").value;
                        var source_organisation = doc.getElementById("source_organisation").value;
                        var src_office = doc.getElementById("src_office").value;
                        var other_person = doc.getElementById("other_person").value;
                        var refrence_no = doc.getElementById("refrence_no").value;
                        var corr_date = doc.getElementById("corr_date").value;
                        var dealing_person = doc.getElementById("dealing_person").value;
                        var key_person = doc.getElementById("key_person").value;
                        var own_ref_no = doc.getElementById("own_ref_no").value;
                        var ref_corr_date = doc.getElementById("ref_corr_date").value;
                        var register_date = doc.getElementById("register_date").value;
                        var status = doc.getElementById("status").value;
                        if(myLeftTrim(correspondece_no).length == 0) {
                            $("#message").html("<td colspan='4' bgcolor='coral'><b>Correspondece_no is required...</b></td>");
                            doc.getElementById("correspondece_no").focus();
                            return false; // code to stop from submitting the form2.
                        }                        
                        if(myLeftTrim(subject).length == 0) {
                            $("#message").html("<td colspan='4' bgcolor='coral'><b>Subject is required...</b></td>");
                            doc.getElementById("subject").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        if(myLeftTrim(source_organisation).length == 0) {
                            if(type_of_correspondence == "Incoming")
                                $("#message").html("<td colspan='4' bgcolor='coral'><b>Source Office is required...</b></td>");
                            else
                                $("#message").html("<td colspan='4' bgcolor='coral'><b>Destination Office is required...</b></td>");
                            doc.getElementById("source_organisation").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        if(myLeftTrim(corr_date).length == 0) {
                            $("#message").html("<td colspan='4' bgcolor='coral'><b>Corr. Date is required...</b></td>");
                            doc.getElementById("corr_date").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        if(myLeftTrim(dealing_person).length == 0) {
                            $("#message").html("<td colspan='4' bgcolor='coral'><b>Dealing Person is required...</b></td>");
                            doc.getElementById("dealing_person").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        if(type_of_correspondence == "Incoming"){
                            if(myLeftTrim(other_person).length == 0) {
                                $("#message").html("<td colspan='4' bgcolor='coral'><b>Sender Name is required...</b></td>");
                                doc.getElementById("other_person").focus();
                                return false; // code to stop from submitting the form2.
                            }
                            if(myLeftTrim(refrence_no).length == 0) {
                                $("#message").html("<td colspan='4' bgcolor='coral'><b>Sender Ref No. is required...</b></td>");
                                doc.getElementById("refrence_no").focus();
                                return false; // code to stop from submitting the form2.
                            }
                            if(myLeftTrim(key_person).length == 0) {
                                $("#message").html("<td colspan='4' bgcolor='coral'><b>Receiver Name is required...</b></td>");
                                doc.getElementById("key_person").focus();
                                return false; // code to stop from submitting the form2.
                            }
                            if(is_reply == "Y"){
                                if(myLeftTrim(own_ref_no).length == 0) {
                                    $("#message").html("<td colspan='4' bgcolor='coral'><b>Receiver Ref No. is required...</b></td>");
                                    doc.getElementById("own_ref_no").focus();
                                    return false; // code to stop from submitting the form2.
                                }
                                if(myLeftTrim(ref_corr_date).length == 0) {
                                    $("#message").html("<td colspan='4' bgcolor='coral'><b>Ref. Corr. Date is required...</b></td>");
                                    doc.getElementById("ref_corr_date").focus();
                                    return false; // code to stop from submitting the form2.
                                }
                            }
                        }else{
                            if(myLeftTrim(other_person).length == 0) {
                                $("#message").html("<td colspan='4' bgcolor='coral'><b>Receiver Name is required...</b></td>");
                                doc.getElementById("other_person").focus();
                                return false; // code to stop from submitting the form2.
                            }                            
                            if(myLeftTrim(key_person).length == 0) {
                                $("#message").html("<td colspan='4' bgcolor='coral'><b>Sender Name is required...</b></td>");
                                doc.getElementById("key_person").focus();
                                return false; // code to stop from submitting the form2.
                            }
                            if(myLeftTrim(own_ref_no).length == 0) {
                                $("#message").html("<td colspan='4' bgcolor='coral'><b>Sender Ref No. is required...</b></td>");
                                doc.getElementById("own_ref_no").focus();
                                return false; // code to stop from submitting the form2.
                            }
                            if(is_reply == "Y"){
                                if(reply_forward == "Reply"){
                                    if(myLeftTrim(refrence_no).length == 0){
                                        $("#message").html("<td colspan='4' bgcolor='coral'><b>Receiver Ref No. is required...</b></td>");
                                        doc.getElementById("refrence_no").focus();
                                        return false; // code to stop from submitting the form2.
                                    }
                                }else{
                                    if(myLeftTrim(refrence_no).length == 0){
                                        $("#message").html("<td colspan='4' bgcolor='coral'><b>Incoming Ref No. is required...</b></td>");
                                        doc.getElementById("refrence_no").focus();
                                        return false; // code to stop from submitting the form2.
                                    }      
                                }
                                if(myLeftTrim(ref_corr_date).length == 0) {
                                    $("#message").html("<td colspan='4' bgcolor='coral'><b>Ref. Corr. Date is required...</b></td>");
                                    doc.getElementById("ref_corr_date").focus();
                                    return false; // code to stop from submitting the form2.
                                }
                            }

                        }
                        if(myLeftTrim(register_date).length == 0) {
                            $("#message").html("<td colspan='4' bgcolor='coral'><b>Register Date is required...</b></td>");
                            doc.getElementById("register_date").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        if(doc.getElementById("correspondence_id").value == 0){
                           // addRow();
                        
                            //return false;
                        }
                    }
                }catch(e){
                    alert(e);
                }
                return result;
            }


            function addRow() {
                alert("&& this this add row fucntion");
                try{
                    var table = doc.getElementById('insertTable');
                    var rowCount = table.rows.length;               //  alert("rowCount --"+rowCount);
                    var row = table.insertRow(rowCount);
                    var cell1 = row.insertCell(0);
                    cell1.innerHTML = rowCount;
                    var element1 = doc.createElement("input");
                    element1.type = "hidden";
                    element1.name = "correspondence_id";
                    element1.id = "correspondence_id"+rowCount;
                    element1.size = 1;
                    element1.value = 1;
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
                    cell2.innerHTML = $.trim($("#correspondece_no").val());
                    var element2 = doc.createElement("input");
                    element2.type = "hidden";
                    element2.name = "correspondece_no";
                    element2.id = "correspondece_no"+rowCount;
                    element2.size = 8;
                    element2.value = $.trim($("#correspondece_no").val());
                    cell2.appendChild(element2);

                    var cell3 = row.insertCell(2);
                    cell3.innerHTML =  $.trim($("#subject").val());
                    var element3 = doc.createElement("input");
                    element3.type = "hidden";
                    element3.name = "subject";
                    element3.id = "subject"+rowCount;
                    element3.size = 8;
                    element3.value = $.trim($("#subject").val());
                    cell3.appendChild(element3);

                    var cell4 = row.insertCell(3);
                    cell4.innerHTML = $.trim($("#type_of_correspondence").val());
                    var element4 = doc.createElement("input");
                    element4.type = "hidden";
                    element4.name = "type_of_correspondence";
                    element4.id = "type_of_correspondence"+rowCount;
                    element4.size = 8;
                    element4.value = $.trim($("#type_of_correspondence").val());
                    cell4.appendChild(element4);

                    var cell5 = row.insertCell(4);
                    cell5.innerHTML =    $('#type_of_document :selected').text();
                    var element5 = doc.createElement("input");
                    element5.type = "hidden";
                    element5.name = "type_of_document";
                    element5.id = "type_of_document"+rowCount;
                    element5.size = 8;
                    element5.value = $('#type_of_document :selected').text();
                    cell5.appendChild(element5);

                    try{
                        var cell6 = row.insertCell(5);
                        var element5 = doc.createElement("input");
                        element5.type = "hidden";
                        element5.name = "src_office";
                        element5.id = "src_office"+rowCount;
                        element5.size = 8;
                        var element_5 = doc.createElement("input");
                        element_5.type = "hidden";
                        element_5.name = "source_organisation";
                        element_5.id = "source_organisation"+rowCount;
                        element_5.size = 8;
                        if($("#type_of_correspondence").val()=='Outgoing'){
                            cell6.innerHTML = "Home";
                            element_5.value = "Home";
                            element5.value = "Home";
                        }else{
                            cell6.innerHTML = $("#src_office").val();
                            element_5.value = $("#source_organisation").val();
                            element5.value = $("#src_office").val();
                            
                        }
                        cell6.appendChild(element5);
                        cell6.appendChild(element_5);
                    }catch(e){
                        alert(e);
                    }
                
                    //alert(rowCount);
                    var cell7 = row.insertCell(6);
                    var element6 = doc.createElement("input");
                    element6.type = "hidden";
                    element6.name = "des_office";
                    element6.id = "des_office"+rowCount;
                    element6.size = 10;
                    element6.value = $("#des_office").val();

                    var element_6 = doc.createElement("input");
                    element_6.type = "hidden";
                    element_6.name = "destination_organisation";
                    element_6.id = "destination_organisation"+rowCount;
                    element_6.size = 10;

                    if($("#type_of_correspondence").val()=='InComing'){
                        cell7.innerHTML ="Home";
                        element_6.value = "Home";
                        element6.value = "Home";
                    }else{
                        cell7.innerHTML =$("#des_office").val();
                        element_6.value = $("#destination_organisation").val();
                        element6.value = $("#des_office").val();
                        
                    }
                    cell7.appendChild(element6);
                    cell7.appendChild(element_6);
                    

                    var cell8 = row.insertCell(7);
                    cell8.innerHTML = $.trim($("#dealing_person").val());
                    var element7 = doc.createElement("input");
                    element7.type = "hidden";
                    element7.name = "dealing_person";
                    element7.id = "dealing_person"+rowCount;
                    element7.size = 15;
                    element7.value =  $.trim($("#dealing_person").val());
                    cell8.appendChild(element7);


                    var cell9 = row.insertCell(8);
                    cell9.innerHTML = $.trim($("#refrence_no").val());
                    var element7 = doc.createElement("input");
                    element7.type = "hidden";
                    element7.name = "refrence_no";
                    element7.id = "refrence_no"+rowCount;
                    element7.size = 7;
                    element7.value =  $.trim($("#refrence_no").val());
                    cell9.appendChild(element7);

                    var cell10 = row.insertCell(9);
                    cell10.innerHTML =  $.trim($("#description").val());
                    var element8 = doc.createElement("input");
                    element8.type = "hidden";
                    element8.name = "descrption";
                    element8.id = "descrption"+rowCount;
                    element8.size = 20;
                    element8.value =  $.trim($("#description").val());
                    cell10.appendChild(element8);

                    var cell11= row.insertCell(10);
                    cell11.innerHTML =   $.trim($("#corr_date").val());
                    var element9 = doc.createElement("input");
                    element9.type = "hidden";
                    element9.name = "corr_date";
                    element9.id = "corr_date"+rowCount;
                    element9.size = 8;
                    element9.value = $.trim($("#corr_date").val());
                    cell11.appendChild(element9);

                    var cell12= row.insertCell(11);
                    var element9 = doc.createElement("input");
                    element9.type = "file";
                    element9.name = "image_name";
                    element9.id = "image_name"+rowCount;
                    element9.size = 8;
                    cell12.appendChild(element9);

                    var height = (rowCount * 40)+ 100;
                    doc.getElementById("autoCreateTableDiv").style.visibility = "visible";
                    doc.getElementById("autoCreateTableDiv").style.height = height+'px';
                    if(rowCount == 1){
                        $('#checkUncheckAll').attr('hidden', true);
                    }else{
                        $('#checkUncheckAll').attr('hidden', false);
                    }
                }catch(e){
                    alert(e);
                    //  return false;
                }
            }
            function deleteRow() {
                try {
                    var table = doc.getElementById('insertTable');
                    var rowCount = table.rows.length;
                    for(var i=1; i<rowCount; i++) {
                        table.deleteRow(1);
                    }
                    doc.getElementById("autoCreateTableDiv").style.visibility = "visible";
                    doc.getElementById("autoCreateTableDiv").style.height = '0px';
                }catch(e) {
                    alert(e);
                }
            }
            function singleCheckUncheck(id){
                // alert(doc.getElementById('insertTable').rows.length);
                if ($('#check_print'+id).is(':checked')) {
                    $("#received_bill_id"+id).val("1");
                }else{
                    $("#received_bill_id"+id).val("0");
                }
                if($('form input[type=checkbox]:checked').size() == 0){
                    $("#addAllRecords").attr("disabled", "disabled");
                }else{
                    $("#addAllRecords").removeAttr("disabled");
                }
            }

            function checkUncheck(id){
                var table = doc.getElementById('insertTable');
                var rowCount = table.rows.length;
                if(id == 'Check All'){
                    $('input[name=check_print]').attr("checked", true);
                    for(var i=1;i<rowCount;i++){
                        $("#received_bill_id"+i).val("1");
                    }
                    $('#checkUncheckAll').val('Uncheck All');
                    $("#addAllRecords").removeAttr("disabled");
                }else{
                    $('input[name=check_print]').attr("checked", false);
                    $('#checkUncheckAll').val('Check All');
                    $("#addAllRecords").attr("disabled", "disabled");
                    for(var i=1;i<rowCount;i++){
                        $("#received_bill_id"+i).val("2");
                    }
                }
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

            function viewImage(id){
                id= id.replace("view_image", "image");
                var image_path= doc.getElementById(id).value;
                if(image_path ==''){
                    alert("This Document is not uploaded in database");
                }else{
                    popupwin = openPopUp("correspondenceCont.do?task=CorrespondenceImage&image_path="+image_path, "Bill Image ", 500, 800);
                }

            }

            function getRecord(id){
                        var queryString;
                        var search_corr_no=doc.getElementById("search_corr_no").value;
                        var search_subject=doc.getElementById("search_subject").value;
                        var search_type_corr=doc.getElementById("search_type_corr").value;
                        var searchreply_forward=doc.getElementById("searchreply_forward").value;
                        var type_of_doc=doc.getElementById("type_of_doc").value;
                        var search_org_from=doc.getElementById("search_org_from").value;
                        var search_office=doc.getElementById("search_office").value;
                        if(id == "view_pdf")
                            queryString = "task=PRINTRecordList";
                        else
                            queryString = "task=PrintExcelList";
                        var url = "correspondenceCont.do?" + queryString + "&search_corr_no="+search_corr_no+"&search_subject="+search_subject+
                            "&search_type_corr="+search_type_corr+"&searchreply_forward="+searchreply_forward+"&type_of_doc="+type_of_doc+"&search_org_from="+search_org_from+
                            "&search_office="+search_office;
                        popupwin = openPopUp(url, "Correspondence Report", 600, 900);
            }

            function changeStatus(){
                var type_of_correspondence =$("#type_of_correspondence").val();
//                if(type_of_correspondence=='Incoming'){
//                    $("#des_org").css("display", "none");
//                    $("#des_ofc").css("display", "none");
//                    $("#source_org").css("display", "table-cell");
//                    $("#source_ofc").css("display", "table-cell");
//                }else{
//                    $("#source_org").css("display", "none");
//                    $("#source_ofc").css("display", "none");
//                    $("#des_org").css("display", "table-cell");
//                    $("#des_ofc").css("display", "table-cell");
//                }
                  if(type_of_correspondence=='Incoming'){
                    //$("#des_org").css("display", "none");
                    //$("#des_ofc").css("display", "none");
                    //$("#source_org").css("display", "table-cell");
                    $("#source_org").text("Source Office");
                    $("#other_person_th").text("Sender Name");
                    $("#refrence_no").show();
                    $("#other_person_ref_th").text("Sender Ref. No.");
                    $("#other_person_ref_th").show();
                    $("#key_person_th").text("Receiver Name");
                    $("#key_person_ref_th").text("Receiver Ref. No.");
                    $("#key_person_ref_th").hide();
                    $("#own_ref_no").hide();
                    $("#reply_span").show();
                    $("#reply_forward").hide();
                    //$("#source_ofc").css("display", "table-cell");
                  }else{
                      $("#source_org").text("Destination Office");
                      $("#other_person_th").text("Receiver Name");
                      $("#refrence_no").hide();
                      $("#other_person_ref_th").text("Receiver Ref. No.");
                      $("#other_person_ref_th").hide();
                      $("#key_person_th").text("Sender Name");                      
                      $("#key_person_ref_th").text("Sender Ref. No.");
                      $("#key_person_ref_th").show();
                      $("#own_ref_no").show();
                      $("#reply_span").hide();
                      $("#reply_forward").show();
                    //$("#source_org").css("display", "none");
                    //$("#source_ofc").css("display", "none");
                    //$("#des_org").css("display", "table-cell");
                    //$("#des_ofc").css("display", "table-cell");
                  }
                  selectReply($("input[type='radio'][name='reply']:checked").val());
            }

            function selectReply(value){
                var type_of_correspondence =$("#type_of_correspondence").val();
                var reply_forward = $("#reply_forward").val();
                $(".ref_corr_date").hide();
                if(value == "Y"){
                    $("#key_person_ref_th").show();
                    $("#own_ref_no").show();                    
                    $("#refrence_no").show();
                    $("#other_person_ref_th").show();
                    if(type_of_correspondence=='Incoming')
                        $(".ref_corr_date").show();
                    else if(type_of_correspondence=='Outgoing' && reply_forward == "Reply" && value == "Y"){
                        $("#other_person_ref_th").text("Receiver Ref. No.");
                        $(".ref_corr_date").show();                        
                    }
                    else if(type_of_correspondence=='Outgoing' && reply_forward == "Forward" && value == "Y"){
                        $("#other_person_ref_th").text("Incoming Ref. No.");
                        $(".ref_corr_date").hide();
                    }
                }else if(type_of_correspondence=='Incoming'){
                    $("#key_person_ref_th").hide();
                    $("#own_ref_no").hide();
                }else if(type_of_correspondence=='Outgoing'){
                    $("#refrence_no").hide();
                    $("#other_person_ref_th").hide();
                    if(reply_forward == "Reply" && value == "Y")
                        $(".ref_corr_date").show();
                    else
                        $(".ref_corr_date").hide();
                }else{
                    $("#refrence_no").hide();
                    $("#other_person_ref_th").hide();                    
                }
            }

            function validateDate(){//debugger;
                var corr_date = $("#corr_date").val();
                var register_date = $("#register_date").val();
                if(corr_date > register_date){
                    $("#message").html("<td colspan='4' bgcolor='coral'><b>Correspondece Date must be greater than Or equal to Register Date required...</b></td>");
                    doc.getElementById("register_date").focus();
                }else
                    $("#message").html("");
            }

            $( document ).ready(function() {
                var task_reply = $("#task_reply").val();
                if(task_reply == "Y"){
                    var date = new Date();
                    var dateString = date.toLocaleFormat('%d-%m-%Y');
                    fillColumns("t1c1");
                    makeEditable("new");
                    $("#correspondece_no").val("");
                    $("#priority").val("");
                    $("#reason").val("");
                    $("#status").val("");
                    $("#ref_corr_date").val($("#corr_date").val());
                    $("#corr_date").val(dateString);
                    $("#register_date").val(dateString);
                    $("#type_of_correspondence").val("Outgoing");
                    $("#reply_forward").val("Reply");
                    //$("#correspondence_id").val(0);
                    $("#reply_yes").attr("checked", true);
                    $("#is_cc").val("N");
                }
                changeStatus();
                $("#reply_forward").change(function(){
                    selectReply($("input[type='radio'][name='reply']:checked").val());
                });
                $("#corr_date").change(function(){
                    validateDate()
                });
                $("#register_date").change(function(){
                    validateDate()
                });
                getSearchSourceofficelist();
                $("input[type='reset']").live('click', function(){
                    this.form.reset(); // forcing reset event
                    changeStatus();
                    //Sadly we need to use setTimeout to execute this after the reset has taken place
                    
                });
//                $("#search_type_corr").change(function(){
//                    var search_type_corr = $("#search_type_corr").val();
//                    if(search_type_corr == "Incoming"){
//                        $("#searchreply_forward").hide();
//                        $("#searchreply_span").show();
//                    }else if(search_type_corr == "Outgoing"){
//                        $("#searchreply_forward").show();
//                        $("#searchreply_span").hide();
//                    }
//                });
            });


        </script>
    </head>
    <body>

        <table align="center"  class="main" cellpadding="0" cellspacing="0" >            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr>
               <%--   <td><%@include file="/layout/metermenu.jsp" %></td>  --%>
            </tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv">
                        <table>
                            <tr><td>
                                    <table>
                                        <tr>
                                            <td align="center" class="header_table" width="90%">Correspondence</td>
                                            <td><%@include file="/layout/correspondence_menu.jsp" %> </td>
                                        </tr>
                                    </table>
                                </td></tr>
                            <tr>
                                <td>
                                    <form name="form0" method="POST" action="correspondenceCont.do">
                                        <table align="center" class="heading1"  width="100%">
                                            <tr>
                                                <td align="center">
                                                    Correspondence No.
                                                    <input class="input" type="text" id="search_corr_no" name="search_corr_no" value="${search_corr_no}" size="12" >
                                                    Subject
                                                    <input class="input" type="text" id="search_subject" name="search_subject" value="${search_subject}">
                                                    Type of Correspondence
                                                    <select id="search_type_corr" name="search_type_corr">
                                                        <option></option>
                                                        <option ${search_type_corr eq "Incoming"?'selected':''}>Incoming</option>
                                                        <option ${search_type_corr eq "Outgoing"?'selected':''}>Outgoing</option>
                                                    </select>
                                                    <span id="searchreply_span" style="display: none;" class="">Reply :</span>
                                                        <select id="searchreply_forward" name="searchreply_forward" >
                                                            <option ${searchreply_forward eq "All"?'selected':''}>All</option>
                                                            <option ${searchreply_forward eq "Reply"?'selected':''}>Reply</option>
                                                            <option ${searchreply_forward eq "Forward"?'selected':''}>Forward</option>
                                                        </select>                                                        
                                                    Type of document
                                                    <select class="" type="text" id="type_of_doc" name="type_of_doc" >
                                                    <c:forEach var="doclist" items="${doclist}" >
                                                                <option ${doclist eq type_of_doc?'selected':''}>${doclist}</option>
                                                    </c:forEach>
                                                    </select>
                                                </td>                                                   
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    Organization
                                                    <input class="input new_input" type="text" id="search_org_from" name="search_org_from" value="${search_org_from}" size="10">
                                                    Office
                                                     <select id="search_office" name="search_office" style="width:60px">
                                                            <option>ALL</option>                                                            
                                                     </select>
                                                   <%--organization to
                                                   <input class="input" type="text" id="search_org_to" name="search_org_to" value="${search_org_to}" size="10">--%>

                                                    <input class="button" type="submit" name="task" id="searchInReceivedBill" value="Search">
                                                    <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                    <input type="button" id="view_pdf" class="pdf_button" name="view_pdf" value="" onclick="getRecord(id)">
                                                    <input type="button" id="view_excel" class="button" name="view_excel" value="Excel" onclick="getRecord(id)">
                                                </td>
                                            </tr>
                                        </table>
                                    </form>
                                </td>
                            </tr>
                            <tr><td align="center">
                                    <div class="content_div">
                                        <form name="form1" method="POST" action="correspondenceCont.do" >
                                            <table border="1" id="table1"   class="content" width="900" >
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Corr. No</th>
                                                    <th class="heading">Subject</th>
                                                    <th class="heading">Correspondence Type</th>
                                                    <th class="heading">Type of Document</th>
                                                    <th class="heading">Organization</th>
                                                    <th class="heading">Office</th>
                                                    <th class="heading">Sender Name</th>
                                                    <th class="heading">Ref. No.</th>
                                                    <th class="heading">Corr. Date</th>
                                                    <th class="heading">Dealing Person</th>
                                                    <th class="heading">Receiver Name</th>
                                                    <th class="heading">Ref. No.</th>
                                                    <th class="heading">Ref. Corres. Date</th>
                                                    <th class="heading">Register Date</th>
                                                    <th class="heading">Description</th>
                                                    <th class="heading">Status</th>
                                                    <th class="heading">Priority</th>
                                                    <th class="heading">Reason</th>
                                                    <th class="heading">Is CC</th>
                                                    <th class="heading" style="display: none">View</th>

                                                </tr>
                                                <c:forEach var="corresBean" items="${requestScope['correcpondencelist']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="correcpondence_id${loopCounter.count}" value="${corresBean.correcpondence_id}">
                                                            <input type="hidden" id="revision${loopCounter.count}" value="${corresBean.revision}">
                                                            <input type="hidden" id="ref_corres_id${loopCounter.count}" value="${corresBean.ref_corres_id}">
                                                            <input type="hidden" id="is_forward${loopCounter.count}" value="${corresBean.is_forward}">
                                                            <input type="hidden" id="is_reply${loopCounter.count}" value="${corresBean.is_reply}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.correspondece_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${corresBean.subject}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.type_of_cop}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.type_of_document}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${corresBean.source_organisation}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.source_office}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${corresBean.other_person}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.refrence_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.corr_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${corresBean.dealing_person}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${corresBean.key_person}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.owner_ref_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.ref_corres_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.register_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${corresBean.description}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${corresBean.priority}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" class="new_input" onclick="fillColumns(id)">${corresBean.reason}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${corresBean.is_cc}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" style="display: none">
                                                            <input type="button" id="view_image${loopCounter.count}" name="view_image" value="View" onclick="viewImage(id)">
                                                            <input type="hidden" id="image${loopCounter.count}" name="" value="${corresBean.image_name}">
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="15">
                                                        <c:choose>
                                                            <c:when test="${showFirst eq 'false'}">
                                                                <input type='submit'class="button" name='buttonAction' value='First' disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type='submit' class="button" name='buttonAction' value='First'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showPrevious == 'false'}">
                                                                <input type='submit'class="button" name='buttonAction' value='Previous' disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type='submit' class="button" name='buttonAction' value='Previous'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showNext eq 'false'}">
                                                                <input type='submit' class="button" name='buttonAction' value='Next' disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type='submit'class="button" name='buttonAction' value='Next'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showLast == 'false'}">
                                                                <input type='submit'class="button" name='buttonAction' value='Last' disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type='submit'class="button" name='buttonAction' value='Last'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td> </tr>
                                                    <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" name="search_corr_no" value="${search_corr_no}">
                                                <input type="hidden" name="search_type_corr" value="${search_type_corr}">
                                                <input type="hidden" name="searchreply_forward" value="${searchreply_forward}">
                                                <input type="hidden" name="type_of_doc" value="${type_of_doc}">
                                                <input type="hidden" name="search_org_from" value="${search_org_from}">
                                                <input type="hidden" id="search_office_hd" name="search_office" value="${search_office}">
                                            </table>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <DIV id="autoCreateTableDiv"  STYLE="overflow: auto; visibility: hidden;height: 0px ;">
                                        <form  name="form2"  action="correspondenceCont.do" method="post" enctype= "multipart/form-data">
                                            <table id="parentTable" class="content"  align="center" width="900">
                                                <tr>
                                                    <td>
                                                        <table id="insertTable" class="content" align="center" width="100%">
                                                            <tr>
                                                                <th class="heading">S.No.</th>
                                                                <th class="heading">Co. No</th>
                                                                <th class="heading">subject</th>
                                                                <th class="heading">Type of Cop</th>
                                                                <th class="heading">Type of Doc.</th>
                                                                <th class="heading">Source Office</th>
                                                                <th class="heading">Destination</th>
                                                                <th class="heading">Deal. person</th>
                                                                <th class="heading">Ref. No</th>
                                                                <th class="heading">Description</th>
                                                                <th class="heading">Corr. Date</th>
                                                                <th class="heading">Image Name</th>
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
                                            <input type="hidden" name="searchAdSubType2" value="${searchAdSubType2}">
                                            <input type="hidden" name="searchOrgName" value="${searchOrgName}">
                                            <input type="hidden" name="searchBillNo" value="${searchBillNo}">
                                            <input type="hidden" name="searchBillDate" value="${searchBillDate}">
                                        </form>
                                    </DIV>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" >
                                    <div>
                                        <form id="form3" name="form3" method="POST" action="correspondenceCont.do" onsubmit="return verify()" enctype= "multipart/form-data" accept-charset="UTF-8">
                                            <table border="0" id="table2" align="center" class="content" width="940">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" colspan="3" style="text-align:right">Type Of Correspondence</th>
                                                    <td colspan="3">
                                                        <select id="type_of_correspondence" name="type_of_correspondence" onchange="changeStatus()" disabled>
                                                            <option>Incoming</option>
                                                            <option>Outgoing</option>
                                                        </select>
                                                        <span id="reply_span" class="heasing">Reply :</span>
                                                        <select id="reply_forward" style="display: none;" name="reply_forward" disabled>
                                                            <option>Reply</option>
                                                            <option>Forward</option>
                                                        </select>
                                                        <input type="radio" id="reply_yes" onchange="selectReply(value)" name="reply" value="Y" disabled>Yes
                                                        <input type="radio" id="reply_no" onchange="selectReply(value)" name="reply" value="N" checked disabled>No
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Correspondence No</th>
                                                    <td>
                                                        <input type="hidden" id="correspondence_id" name="correspondence_id" value="0" >
                                                        <input type="hidden" id="revision" name="revision" value="0" >
                                                        <input class="input" type="text" id="correspondece_no" name="correspondece_no"  value="" placeholder="" disabled>
                                                    </td>
                                                    <th class="heading1">Subject</th>
                                                    <td>
                                                        <input class="input new_input" type="text" id="subject" name="subject" disabled>
                                                    </td>                                                    
                                                </tr>
                                                <tr>
                                                    <th id="source_org" class="heading1" style="display:table-cell">Source Office</th>
                                                    <td id="source_ofc" style="display:table-cell">
                                                        <input type="text" class="input new_input" id="source_organisation" name="source_organisation" value="" size="12" disabled>
                                                        <select id="src_office" name="src_office" style="width:80px" disabled>
<!--                                                            <option>Behind Chhoti Maszid</option>
                                                            <option>sanskrit viddalay</option>
                                                            <option>Azad nagar</option>
                                                            <option>Motinala</option>-->
                                                        </select>
                                                    </td>
                                                    <th id="des_org" class="heading1" class="heading1" style="display:none">Destination Office</th>
                                                    <td id="des_ofc" class="heading1" style="display:none">
                                                        <input type="text" class="input" id="destination_organisation" name="destination_organisation" value="" size="12" >
                                                        <select id="des_office" name="des_office" style="width:60px">
<!--                                                            <option>sanskrit viddalay</option>
                                                            <option>Azad nagar</option>
                                                            <option>Behind Chhoti Maszid</option>
                                                            <option>Motinala</option>-->
                                                        </select>
                                                    </td>
                                                    <th class="heading1" id="other_person_th">Sender Name</th>
                                                    <td>
                                                        <input class="input new_input" type="text" id="other_person" name="other_person" disabled>
                                                    </td>
                                                    <th class="heading1" id="other_person_ref_th">Sender Ref. No</th>
                                                    <td>
                                                        <input class="input" class="input" type="text" id="refrence_no" name="refrence_no" disabled>
                                                    </td>
                                                </tr>
                                                <tr>                                                    
                                                    <th class="heading1">Corr. Date</th>
                                                    <td>
                                                        <input class="input" type="text" id="corr_date" name="corr_date" value="" disabled>
                                                        <!--<a href="#" onclick="setYears(2000,2020);showCalender(this,'corr_date')">
                                                            <img src="images/calender.png" alt="calender.png">
                                                        </a>-->
                                                    </td>
                                                    <th class="heading1">Type of Document</th>
                                                    <td>
                                                        <select id="type_of_document" name="type_of_document" style="height:20px" disabled>
                                                            <c:forEach var="doclist" items="${doclist}" >
                                                                <option>${doclist}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <th class="heading1">Description</th>
                                                    <td>
                                                        <input class="input new_input" type="text" id="description" name="description" disabled>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Dealing Person</th>
                                                    <td>
                                                        <input type="text" class="input new_input" id="dealing_person" name="dealing_person" value="" disabled>
                                                    </td>
                                                    <th class="heading1" id="key_person_th">Receiver Name</th>
                                                    <td>
                                                        <input type="text" class="input new_input" id="key_person" name="key_person" value="" disabled>
                                                    </td>
                                                    <th class="heading1" id="key_person_ref_th">Receiver Ref. No.</th>
                                                    <td>
                                                        <input type="text" class="input" id="own_ref_no" name="own_ref_no" value="" disabled>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1 ref_corr_date" id="ref_corr_date_th">Ref. Corr. Date</th>
                                                    <td class="ref_corr_date">
                                                        <input class="input " type="text" id="ref_corr_date" name="ref_corr_date" value="" disabled>
                                                        <!--<a href="#" onclick="setYears(2000,2020);showCalender(this,'corr_date')">
                                                            <img src="images/calender.png" alt="calender.png">
                                                        </a>-->
                                                    </td>
                                                    <th class="heading1">Priority</th>
                                                    <td>
                                                        <input type="text" class="input new_input" id="priority" name="priority" value="" disabled>
                                                    </td>
                                                    <th class="heading1">Reason</th>
                                                    <td>
                                                        <input type="text" class="input new_input" id="reason" name="reason" value="" disabled>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Is CC(Carbon Copy)</th>
                                                    <td>
                                                        <select id="is_cc" name="is_cc" onchange="" disabled>
                                                            <option value="N">No</option>
                                                            <option value="Y">Yes</option>
                                                        </select>
                                                    </td>
                                                    <th class="heading1">Register Date</th>
                                                    <td>
                                                        <input class="input" type="text" id="register_date" name="register_date" value="" disabled>
                                                        <!--<a href="#" onclick="setYears(2000,2020);showCalender(this,'corr_date')">
                                                            <img src="images/calender.png" alt="calender.png">
                                                        </a>-->
                                                    </td>
                                                    <th class="heading1">Status</th>
                                                    <td>
                                                        <input type="text" class="input" id="status" name="status" value="" disabled>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="6" >
                                                        <input class="button" type="button" name="Edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save_as" value="Save As New" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="button" name="view_image" id="view_image" value="View Image" style="display: none" onclick="viewImage(id)" disabled>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                                <input type="hidden" name="searchAdSubType2" value="${searchAdSubType2}">
                                                <input type="hidden" name="searchOrgName" value="${searchOrgName}">
                                                <input type="hidden" name="searchBillNo" value="${searchBillNo}">
                                                <input type="hidden" name="searchBillDate" value="${searchBillDate}">
                                                <input type="hidden" id="image" name="image" value="" >
                                                <input type="hidden" id="task_reply" name="task_reply" value="${task_reply}">
                                                <input type="hidden" id="is_task_reply" name="is_task_reply" value="">
                                                <input type="hidden" id="cottes_task_id" name="corres_task_id" value="${corres_task_id}">
                                            </table>
                                        </form>
                                        <jsp:include page="calendar_view"/>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </DIV>
                </td></tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>

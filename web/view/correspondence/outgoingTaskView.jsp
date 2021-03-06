<%-- 
    Document   : outgoingTaskView
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
        <title>Correspondence Task</title>
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
                $("#dealing_person").autocomplete("outgoingTaskCont.do", {
                    extraParams: {
                        action1: function() { return "getkey_personlist"}
                    }
                });
                $("#key_person").autocomplete("outgoingTaskCont.do", {
                    extraParams: {
                        action1: function() { return "getkey_personlist"}
                    }
                });
                $("#source_organisation").autocomplete("organisationCont.do", {
                    extraParams: {
                        action1: function() { return "getOrganization"}
                    }
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

                $("#task_status").autocomplete("correspondenceStatusCont.do", {
                    extraParams: {
                        action1: function() { return "getStatusType"}
                    }
                });
                $("#search_corr_no").autocomplete("outgoingTaskCont.do", {
                    extraParams: {
                        action1: function() { return "getCorrespondenceNo"}
                    }
                });

                $("#search_task").autocomplete("outgoingTaskCont.do", {
                    extraParams: {
                        action1: function() { return "getCorrespondenceTask"}
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

                 $("#start_date").datepicker({
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
                $.ajax({url: "outgoingTaskCont.do", async: true, data: queryString, success: function(response_data) {

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


            function getSourceofficelist(){
                var organisation_name=  $("#source_organisation").val();
                var queryString = "task=getOfficeList&organisation_name="+organisation_name;
                $("#src_office option").each(function()
                {
                    $(this).remove();
                });
                $.ajax({url: "outgoingTaskCont.do", async: true, data: queryString, success: function(response_data) {
                        if(response_data != ''){
                            var arr = response_data.split("&#");
                            var i;
                            for(i = 0; i < arr.length-1; i++) {
                                var opt = doc.createElement("option");
                                opt.text = $.trim(arr[i]);
                                opt.value = $.trim(arr[i]);
                                doc.getElementById("src_office").options.add(opt);
                            }
                        }else{
                            //  $("#correspondence_no").html('<option>Select</option>');
                        }
                    }
                });

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
              
                if(id == 'save') 
                    doc.getElementById("clickedButton").value = "Save";
                
                else if(id == 'cancel')
                    doc.getElementById("clickedButton").value = "Cancel";
                else if(id == 'reply')
                    doc.getElementById("clickedButton").value = "Reply";


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
                var noOfColumns = 6;
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
                var correspondence_id = doc.getElementById("correspondence_id"+rowNum).value;
                //doc.getElementById("correspondence_id").value = correspondence_id;
                doc.getElementById("correspondence_id_task").value = correspondence_id;
                doc.getElementById("task_correspondence_no").value = doc.getElementById("correspondence_no"+rowNum).value;
                doc.getElementById("correspondence_task_id").value = doc.getElementById("correcpondence_task_id"+rowNum).value;
                doc.getElementById("revision").value = doc.getElementById("revision"+rowNum).value;
                doc.getElementById("correspondence_task").value = doc.getElementById(t1id + (lowerLimit + 1)).innerHTML;
                doc.getElementById("start_date").value = doc.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                doc.getElementById("no_of_days").value = doc.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                doc.getElementById("remark").value = doc.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                doc.getElementById("task_status").value = doc.getElementById(t1id + (lowerLimit + 5)).innerHTML;                
//                var ref_corres_id = doc.getElementById("ref_corres_id" + rowNum).value;
//                var is_forward = doc.getElementById("is_forward"+rowNum).value;
//                var is_reply = doc.getElementById("is_reply"+rowNum).value;
                //var correspondence_no = doc.getElementById(t1id + (lowerLimit + 1)).innerHTML;
                //doc.getElementById("correspondece_no").value = correspondence_no;
                //doc.getElementById("task_correspondence_no").value = correspondence_no;
                //doc.getElementById("subject").value = doc.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                //var type_of_correspondence = doc.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                //doc.getElementById("type_of_correspondence").value = type_of_correspondence;
                //var type_of_doc = doc.getElementById(t1id + (lowerLimit + 4)).innerHTML;

//                $("#type_of_document option" ).each(function()
//                {
//                    if($(this).text() == type_of_doc){
//                        $(this).attr('selected', true);
//                    }
//                });
//                doc.getElementById("source_organisation").value = doc.getElementById(t1id + (lowerLimit + 5)).innerHTML;
//                getSourceofficelist();
//                doc.getElementById("src_office").value = doc.getElementById(t1id + (lowerLimit + 6)).innerHTML;
                //doc.getElementById("destination_organisation").value = doc.getElementById(t1id + (lowerLimit + 7)).innerHTML;
                //getDesofficelist();
                
                //doc.getElementById("des_office").value = doc.getElementById(t1id + (lowerLimit + 8)).innerHTML;
//                doc.getElementById("other_person").value = doc.getElementById(t1id + (lowerLimit + 7)).innerHTML;
//                var refrence_no = doc.getElementById(t1id + (lowerLimit + 8)).innerHTML;
//                doc.getElementById("refrence_no").value = refrence_no;
//                doc.getElementById("corr_date").value = doc.getElementById(t1id + (lowerLimit + 9)).innerHTML;
//                doc.getElementById("dealing_person").value = doc.getElementById(t1id + (lowerLimit + 10)).innerHTML;
//                doc.getElementById("key_person").value = doc.getElementById(t1id + (lowerLimit + 11)).innerHTML;
//                var own_ref_no = doc.getElementById(t1id + (lowerLimit + 12)).innerHTML;
//                doc.getElementById("own_ref_no").value = own_ref_no;
//                var ref_corr_date = doc.getElementById(t1id + (lowerLimit + 13)).innerHTML;
//                doc.getElementById("ref_corr_date").value = ref_corr_date;
//                doc.getElementById("register_date").value = doc.getElementById(t1id + (lowerLimit + 14)).innerHTML;
//                doc.getElementById("description").value = doc.getElementById(t1id + (lowerLimit + 15)).innerHTML;
//                doc.getElementById("status").value = doc.getElementById(t1id + (lowerLimit + 16)).innerHTML;
//                doc.getElementById("priority").value = doc.getElementById(t1id + (lowerLimit + 17)).innerHTML;
//                doc.getElementById("reason").value = doc.getElementById(t1id + (lowerLimit + 18)).innerHTML;
//                doc.getElementById("is_cc").value = doc.getElementById(t1id + (lowerLimit + 19)).innerHTML;
//                doc.getElementById("image").value = doc.getElementById("image"+rowNum).value;

//                if(type_of_correspondence == "Outgoing" && refrence_no == "" && ref_corr_date == "" && ref_corres_id != ""){
//                    $("#reply_forward").val("Forward");
//                    $("#reply_yes").attr("checked", true);
//                }

//                if(type_of_correspondence == "Outgoing" && is_reply == "Y"){
//                    $("#reply_forward").val("Reply");
//                    $("#reply_yes").attr("checked", true);
//                }else if(type_of_correspondence == "Outgoing" && is_forward == "Y"){
//                    $("#reply_forward").val("Forward");
//                    $("#reply_yes").attr("checked", true);
//                }else if(type_of_correspondence == "Incoming" && is_reply == "Y"){
//                    $("#reply_yes").attr("checked", true);
//                }else
//                    $("#reply_no").attr("checked", true);
//
//                changeStatus();

                for(var i = 0; i < noOfColumns; i++) {
                    doc.getElementById(t1id + (lower + i)).bgColor = "#dodafd";        // set the background color of clicked row to yellow.
                }

                //doc.getElementById("edit").disabled = false;
                //if(!doc.getElementById("save").disabled) {
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    doc.getElementById("save").disabled = false;//                    doc.getElementById("New").disabled = true;
                    doc.getElementById("cancel").disabled = false;
                    doc.getElementById("reply").disabled = false;
                //}
                //doc.getElementById("save_as").disabled = true;
                //doc.getElementById("delete").disabled = true;
                //makeDisable();
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

            function verify() {
                try{
                    var result;
                    var clickedButton = doc.getElementById("clickedButton").value;
                    if(clickedButton == 'Save' || clickedButton == 'Cancel' || clickedButton == 'Reply') {
                        var correspondece_no = doc.getElementById("task_correspondence_no").value;
                        var correspondence_task = doc.getElementById("correspondence_task").value;
                        if(myLeftTrim(correspondece_no).length == 0) {
                            $("#message").html("<td colspan='4' bgcolor='coral'><b>Correspondece_no is required...</b></td>");
                            doc.getElementById("task_correspondence_no").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        
                        if(myLeftTrim(correspondence_task).length == 0) {
                            $("#message").html("<td colspan='4' bgcolor='coral'><b>Task is required...</b></td>");
                            doc.getElementById("correspondence_task").focus();
                            return false; // code to stop from submitting the form2.
                        }                                      
                        //if(doc.getElementById("correspondence_id").value == 0){
                           // addRow();
                        
                            //return false;
                       // }
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
                    popupwin = openPopUp("outgoingTaskCont.do?task=CorrespondenceImage&image_path="+image_path, "Bill Image ", 500, 800);
                }

            }

            function getRecord(id){
                        var queryString;
                        var search_corr_no=doc.getElementById("search_corr_no").value;
                        var search_task=doc.getElementById("search_task").value;
                        
                        if(id == "view_pdf")
                            queryString = "task=PRINTRecordList";
                        else
                            queryString = "task=PrintExcelList";
                        var url = "outgoingTaskCont.do?" + queryString + "&search_corr_no="+search_corr_no+"&search_task="+search_task;
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
                                            <td align="center" class="header_table" width="90%">Outgoing Task</td>
                                            <td><%@include file="/layout/correspondence_menu.jsp" %> </td>
                                        </tr>
                                    </table>
                                </td></tr>
                            <tr>
                                <td>
                                    <form name="form0" method="POST" action="outgoingTaskCont.do">
                                        <table align="center" class="heading1"  width="100%">
                                            <tr>
                                                <td align="center">
                                                    Correspondence No.
                                                    <input class="input" type="text" id="search_corr_no" name="search_corr_no" value="${search_corr_no}" size="12" >
                                                    <%--Type of Correspondence
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
                                                    </select> --%>
                                                    Task
                                                    <input class="input" type="text" id="search_task" name="search_task" value="${search_task}">
                                                </td>                                                   
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <%--organization from
                                                    <input class="input" type="text" id="search_org_from" name="search_org_from" value="${search_org_from}" size="10">
                                                    organization to
                                                    <input class="input" type="text" id="search_org_to" name="search_org_to" value="${search_org_to}" size="10">
                                                    --%>
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
                                        <form name="form1" method="POST" action="outgoingTaskCont.do" >
                                            <table border="1" id="table1"   class="content" width="900" >
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Corr. No</th>
                                                    <th class="heading">Subject</th>                                                    
                                                    <th class="heading">Corr. Date</th>
                                                    <th class="heading">Task</th>
                                                    <th class="heading">Date Apply From</th>                                                    
                                                    <th class="heading">Status</th>
                                                </tr>                                                 
                                                <c:forEach var="taskBean" items="${requestScope['correcpondenceTasklist']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                        <td align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="correcpondence_id${loopCounter.count}" value="${taskBean.correspondence_id}">
                                                            <input type="hidden" id="revision${loopCounter.count}" value="${taskBean.revision}">
                                                            <input type="hidden" id="correcpondence_task_id${loopCounter.count}" value="${taskBean.correspondence_task_id}">
                                                            <input type="hidden" id="revision${loopCounter.count}" value="${taskBean.revision}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" >${taskBean.correspondence_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" >${taskBean.subject}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" >${taskBean.corr_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" >${taskBean.task}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" >${taskBean.start_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" >${taskBean.status}</td>
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
                                                <input type="hidden" name="search_task" value="${search_task}">
                                                <input type="hidden" name="search_type_corr" value="${search_type_corr}">
                                                <input type="hidden" name="type_of_doc" value="${type_of_doc}">
                                                <input type="hidden" name="search_org_from" value="${search_org_from}">
                                            </table>
                                        </form>
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

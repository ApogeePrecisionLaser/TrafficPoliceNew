<%--
    Document   : trafficPoliceQuickEntryView
    Created on : Mar 7, 2016, 9:53:15 AM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Traffic Police</title>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link rel="stylesheet" href="datePicker/jquery.ui.all.css">
        <script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
    </head>
      <style type="text/css">
                .new_input
                {
                    font-size: 16px;
                    font-family:"kruti Dev 010", Sans-Serif;
                    font-weight: 500;
                }                
      </style>

    <script type="text/javascript" language="javascript">

<%--      function change()
                     {
                        var table =document.getElementById("insertTable");
                        table.innerHTML="";
                        if(document.getElementById("no_of_offence"))
                        {
                           addHeader();
                        }
                        document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                     }

          function change1()
                     {
                        var table =document.getElementById("insertTable1");
                        table.innerHTML="";
                        if(document.getElementById("Total_No._of_Rows"))
                        {
                           addHeader1();
                        }
                        document.getElementById("autoCreateTableDiv1").style.visibility = "visible";
                     }

        function addHeader()
        {
                var table = document.getElementById('insertTable');
                var row1 = table.insertRow(0);
                row1.setAttribute("class", "heading");
                var cell1 = row1.insertCell(0);
                cell1.innerHTML ="Act Origin";
                var cell2 = row1.insertCell(1);
                cell2.innerHTML = "Act";
                var cell3 = row1.insertCell(2);
                cell3.innerHTML = "Offence Type";
                var cell4 = row1.insertCell(3);
                cell4.innerHTML = "Offence Code";
                var cell5 = row1.insertCell(4);
                cell5.innerHTML = "Penalty Amount";
        }

        function addHeader1()
        {
                var table = document.getElementById('insertTable1');
                var row1 = table.insertRow(0);
                row1.setAttribute("class", "heading1");
                var cell1 = row1.insertCell(0);
                cell1.innerHTML ="Jarayam No.";
                var cell2 = row1.insertCell(1);
                cell2.innerHTML = "Offender License No";
                var cell3 = row1.insertCell(2);
                cell3.innerHTML = "Offender Name";
                var cell4 = row1.insertCell(3);
                cell4.innerHTML = "Offender Age";
                var cell5 = row1.insertCell(4);
                cell5.innerHTML = "Offender Address";
                var cell6 = row1.insertCell(5);
                cell6.innerHTML = "Offender City";
                var cell7 = row1.insertCell(6);
                cell7.innerHTML = "Father's Name";
                var cell8 = row1.insertCell(7);
                cell8.innerHTML = "Vehicle No.";
                var cell9 = row1.insertCell(8);
                cell9.innerHTML = "Receipt Page No.";
                var cell10 = row1.insertCell(9);
                cell10.innerHTML = "Receipt Book No.";
                var cell11 = row1.insertCell(10);
                cell11.innerHTML = "Receipt Book Rev. No";
                var cell12 = row1.insertCell(11);
                cell12.innerHTML = "Process Type";
        } --%>


     var amount=0;
     jQuery(function(){
         checkValue();
         $("#Total_No_of_Rows").val(1);
         checkValue1();
            $("#offence_date").datepicker({

                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
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
            });


          jQuery(function(){
                $("#officer_name").autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getOfficerName" }               
            }
              });

                $("#offence_city").autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getOffenceCity" },
                action3: function() { return $("#Zone").val()},
                action4: function() { return $("#Offence_Location").val()}
            }
              });

                $("#Zone").autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getZone" },
                action2: function() { return $("#offence_city").val()},
                action4: function() { return $("#Offence_Location").val()}
            }
              });

                $("#Offence_Location").autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getOffenceLocation" },
                action2: function() { return $("#offence_city").val()},
                action3: function() { return $("#Zone").val()}
            }
              });

         });




       function addRow()
        {
           $("#penalty_amount"+rowCount).val(0);
           $("#total_penalty_amount").val(0);
           //var amount=0;
               var table = document.getElementById('tablebody');
               var rowCount = table.rows.length;                // alert(rowCount);
                var row = table.insertRow(rowCount);
               // row.setAttribute("class", "heading");
                var cell1 = row.insertCell(0);
                //var element1 = document.createElement("th");
                //cell1.innerHTML ="Act Origin";
                //cell1.setAttribute('class','heading');
                var element1 = document.createElement("input");
                element1.type = "text";
                element1.className = "input";
                element1.name = "act_origin";
                element1.id = "act_origin"+rowCount;
                element1.size = 20;
                element1.value = "";
                //element1.setAttribute('class','input');
                cell1.appendChild(element1);

                 jQuery(function(){
              $("#act_origin"+rowCount).autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getActOrigin" }
            }
              });

             /* $("#act_origin"+rowCount).result(function(event, data, formatted){
               var id = this.id;
               var commercial_type = $("#commercial_type").val();
                    $.ajax({url: "trafficPoliceQuickEntryCont.do?action1=getSelect&commercial_type="+commercial_type, data: "action5="+ data +"&q=", success: function(response_data) {
                        var array1 = response_data.trim().split("_");
                        document.getElementById("act"+rowCount).value = array1[0];
                        document.getElementById("offence_type"+rowCount).value = array1[1];
                        document.getElementById("offence_code"+rowCount).value = array1[2];
                        document.getElementById("penalty_amount"+rowCount).value = array1[3];
                        amount=parseInt(amount)+parseInt(document.getElementById("penalty_amount"+rowCount).value) ;
                       document.getElementById("total_penalty_amount").value=amount;
                    }
                });
            });*/
       });

                var cell2 = row.insertCell(1);
               // var element2 = document.createElement("th");
               // cell2.innerHTML = "Act";
               // cell2.setAttribute('class','heading');
                var element2 = document.createElement("input");
                element2.type = "text";
                element2.className = "input";
                element2.name = "act";
                element2.id = "act"+rowCount;
                element2.size = 20;
                element2.value = "";
               // element2.setAttribute('class','input');
                cell2.appendChild(element2);

                jQuery(function(){
                $("#act"+rowCount).autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getAct" },
                action5: function() { return $("#act_origin"+rowCount).val()},
                action3: function() { return $("#offence_type"+rowCount).val()},
                action4: function() { return $("#offence_code"+rowCount).val()}
            }
              });

              $("#act"+rowCount).result(function(event, data, formatted){
               var id = this.id;
               var commercial_type = $("#commercial_type").val();
                    $.ajax({url: "trafficPoliceQuickEntryCont.do?action1=getSelect&commercial_type="+commercial_type, data: "action2="+ data +"&q=", success: function(response_data) {
                        var array2 = response_data.trim().split("_");

                        document.getElementById("offence_type"+rowCount).value = array2[1];
                        document.getElementById("offence_code"+rowCount).value = array2[2];
                        document.getElementById("penalty_amount"+rowCount).value = array2[3];
                        document.getElementById("act_origin"+rowCount).value = array2[4];
                        amount=parseInt(amount)+parseInt(document.getElementById("penalty_amount"+rowCount).value) ;
                       document.getElementById("total_penalty_amount").value=amount;
                    }
                });
            });
        });
                var cell3 = row.insertCell(2);
                //var element3 = document.createElement("th");
                //cell3.innerHTML = "Offence Type";
               // cell3.setAttribute('class','heading');
                var element3 = document.createElement("input");
                element3.type = "text";
                element3.className = "new_input";
                element3.name = "offence_type";
                element3.id = "offence_type"+rowCount;
                element3.size = 20;
                element3.value = "";
                //element3.setAttribute('class','input');
                cell3.appendChild(element3);

                jQuery(function(){
                $("#offence_type"+rowCount).autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getOffenceType" },
                action5: function() { return $("#act_origin"+rowCount).val()},
                action2: function() { return $("#act"+rowCount).val()},
                action4: function() { return $("#offence_code"+rowCount).val()}
            }
              });
              $("#offence_type"+rowCount).result(function(event, data, formatted){
               var id = this.id;
               var commercial_type = $("#commercial_type").val();
                    $.ajax({url: "trafficPoliceQuickEntryCont.do?action1=getSelect&commercial_type="+commercial_type, data: "action3="+ data +"&q=", success: function(response_data) {
                        var array3 = response_data.trim().split("_");

                        document.getElementById("act"+rowCount).value = array3[0];
                        document.getElementById("offence_code"+rowCount).value = array3[2];
                        document.getElementById("penalty_amount"+rowCount).value = array3[3];
                        document.getElementById("act_origin"+rowCount).value = array3[4];
                        amount=parseInt(amount)+parseInt(document.getElementById("penalty_amount"+rowCount).value) ;
                       document.getElementById("total_penalty_amount").value=amount;
                    }
                });
            });
          });
                var cell4 = row.insertCell(3);
               // var element4 = document.createElement("th");
               // cell4.innerHTML = "Offence Code";
                //cell4.setAttribute('class','heading');
                var element4 = document.createElement("input");
                element4.type = "text";
                element4.className = "new_input";
                element4.name = "offence_code";
                element4.id = "offence_code"+rowCount;
                element4.size = 20;
                element4.value = "";
                //element4.setAttribute('class','input');
                cell4.appendChild(element4);
                jQuery(function(){
                $("#offence_code"+rowCount).autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getOffenceCode" },
                action5: function() { return $("#act_origin"+rowCount).val()},
                action2: function() { return $("#act"+rowCount).val()},
                action3: function() { return $("#offence_type"+rowCount).val()}
            }
              });

              $("#offence_code"+rowCount).result(function(event, data, formatted){
               var id = this.id;
               var commercial_type = $("#commercial_type").val();
                    $.ajax({url: "trafficPoliceQuickEntryCont.do?action1=getSelect&commercial_type="+commercial_type , data: "action4="+ data +"&q=", success: function(response_data) {
                        var array4 = response_data.trim().split("_");

                        document.getElementById("act"+rowCount).value = array4[0];
                        document.getElementById("offence_type"+rowCount).value = array4[1];
                        document.getElementById("penalty_amount"+rowCount).value = array4[3];
                        document.getElementById("act_origin"+rowCount).value = array4[4];                       
                        amount=parseInt(amount)+parseInt(document.getElementById("penalty_amount"+rowCount).value) ;
                        document.getElementById("total_penalty_amount").value=amount;

                    }
                });
            });
           });
                var cell5 = row.insertCell(4);
               // var element5 = document.createElement("th");
               // cell5.innerHTML = "Penalty Amount";
                //cell5.setAttribute('class','heading');
                var element5 = document.createElement("input");
                element5.type = "text";
                element5.name = "penalty_amount";
                element5.id = "penalty_amount"+rowCount;
                element5.size = 20;
                element5.value = "";
                element5.setAttribute("onkeyup","getTotalPenaltyAmount()");
                //element5.onblur='getTotalPenaltyAmount()';
                cell5.appendChild(element5);

                var height = (rowCount * 25)+ 60;
                document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                document.getElementById("autoCreateTableDiv").style.height = height+'px';
               $("#autoCreateTableDiv").show();
       }
     function Unique() {
         var table = document.getElementById('tablebody1');
         var rowCount = table.rows.length;
              var jarayam= $("#jarayam_no"+0).val();            
              for(var i=1;i<rowCount;i++){
                  jarayam=parseInt(jarayam)+1;              
                   $("#jarayam_no"+i).val(jarayam);                
              }
     }
      function Unique1() {
         var table = document.getElementById('tablebody1');
         var rowCount = table.rows.length;

              var state= $("#vehicle_no_state"+0).val();             
              for(var i=1;i<rowCount;i++){
                 
                   $("#vehicle_no_state"+i).val(state);
              }
             
            if($("#vehicle_no_state"+0).val().length == 2){
                $("#vehicle_no_city"+0).focus();
            }
           // alert(  $("#vehicle_no_state"+0).val().length );
           //  if( document.getElementById('vehicle_no_state').value.length == 2)
              
               
     }
      function Unique2() {
         var table = document.getElementById('tablebody1');
         var rowCount = table.rows.length;

              var city= $("#vehicle_no_city"+0).val();
              for(var i=1;i<rowCount;i++){
                 // city=parseFloat(city);
                   $("#vehicle_no_city"+i).val(city);
              }
              if($("#vehicle_no_city"+0).val().length == 2){
                $("#vehicle_no_series"+0).focus();
            }
     }

      function Unique3() {
         var table = document.getElementById('tablebody1');
         var rowCount = table.rows.length;
              var oCity= $("#offender_city"+0).val();
              for(var i=1;i<rowCount;i++){
                 
                   $("#offender_city"+i).val(oCity);
              }
     }

      function Unique4() {
         var table = document.getElementById('tablebody1');
         var rowCount = table.rows.length;

              var rbn= $("#receipt_book_no"+0).val();
              for(var i=1;i<rowCount;i++){
                 // rbn=parseInt(rbn);
                   $("#receipt_book_no"+i).val(rbn);
              }
     }

      function Unique5() {
          
         var table = document.getElementById('tablebody1');
         var rowCount = table.rows.length;

              var rpn= $("#receipt_page_no"+0).val();
              
              for(var i=1;i<rowCount;i++){
                  if(parseInt(rpn) <= 99)
                     rpn=parseInt(rpn)+1;
                  else
                      rpn = "";
                   $("#receipt_page_no"+i).val(rpn);
              }
     }

       function Unique6(rowCount)
       {
         //var table = document.getElementById('tablebody1');
         //var rowCount = table.rows.length;
              var Total_No_of_Rows = $("#Total_No_of_Rows").val();
              var rpn= $("#receipt_page_no"+rowCount).val();
             
              for(var i=rowCount;i<Total_No_of_Rows;i++)
              {
                    if(parseInt(rpn) <= 99)
                     rpn=parseInt(rpn)+1;
                  else
                      rpn = "";
                  $("#receipt_page_no"+(i+1)).val(rpn);
              }
     }
     function Unique7(rowCount)
       {
           
         //var table = document.getElementById('tablebody1');
         //var rowCount = table.rows.length;
              var Total_No_of_Rows = $("#Total_No_of_Rows").val();
              var rpn= $("#receipt_book_no"+rowCount).val();
              for(var i=rowCount;i<Total_No_of_Rows;i++)
                {
                     //rpn=parseInt(rpn);
                  
                  $("#receipt_book_no"+i).val(rpn);
              }
     }


         function addRow1()
        {            
                var table = document.getElementById('tablebody1');
                var rowCount = table.rows.length;                // alert(rowCount);
                var row = table.insertRow(rowCount);

                var cell1 = row.insertCell(0);
                //cell1.innerHTML = $.trim(data1);
                var element1 = document.createElement("input");
                //element1.type = "hidden";
                element1.name = "S.No";
                element1.id = "S.No"+rowCount;              
                element1.size = 1;
                element1.value = rowCount + 1;
                element1.readOnly = false;
                element1.disabled = true;
                cell1.appendChild(element1);

                var element1 = document.createElement("input");
                element1.type = "hidden";
                element1.name = "traffic_police_id";
                element1.id = "traffic_police_id"+rowCount;
                element1.size = 1;
                element1.value = 1;
                element1.readOnly = false;
                cell1.appendChild(element1);

                var element1 = document.createElement("input");
                element1.type = "checkbox";
                element1.name = "check_print";
                element1.id = "check_print"+rowCount;                //element1.size = 1;
                element1.value = "Yes";
                element1.checked = true;
                element1.setAttribute('onclick', 'singleCheckUncheck('+rowCount+')');
                cell1.appendChild(element1);

                var cell2 = row.insertCell(1);
                var element2 = document.createElement("input");
                element2.type = "text";
                element2.name = "jarayam_no";
                element2.id = "jarayam_no"+rowCount;
                element2.size = 20;
                element2.value = "";
                if(rowCount == 0)
                    element2.setAttribute("onkeyup","Unique()");
                cell2.appendChild(element2);

                var cell3 = row.insertCell(2);
                var element3 = document.createElement("input");
                element3.type = "text";
                element3.className = "input";
                element3.name = "vehicle_no_state";
                element3.id = "vehicle_no_state"+rowCount;
                element3.size = 5;
                element3.setAttribute("maxlength","2" );
                element3.value = "";
                if(rowCount == 0)
                    element3.setAttribute("onkeyup","Unique1()");
                cell3.appendChild(element3);
                var element3 = document.createElement("input");
                element3.type = "text";
                element3.className = "input";
                element3.name = "vehicle_no_city";
                element3.id = "vehicle_no_city"+rowCount;
                element3.size = 5;
                element3.setAttribute("maxlength","2" );
                element3.value = "";
                if(rowCount == 0)
                    element3.setAttribute("onkeyup","Unique2()");
                cell3.appendChild(element3);
                var element3 = document.createElement("input");
                element3.type = "text";
                element3.name = "vehicle_no_series";
                element3.id = "vehicle_no_series"+rowCount;
                element3.size = 5;
                element3.value = "";
                cell3.appendChild(element3);
                var element3 = document.createElement("input");
                element3.type = "text";
                element3.name = "vehicle_no_digits";
                element3.id = "vehicle_no_digits"+rowCount;
                element3.size = 5;
                element3.value = "";
                cell3.appendChild(element3);
                var cell4 = row.insertCell(3);
               // cell3.innerHTML = $.trim(data3);
                var element4 = document.createElement("input");
                element4.type = "text";
                element4.name = "offender_license_no";
                element4.id = "offender_license_no"+rowCount;
                element4.size = 20;
                element4.value = "";
                cell4.appendChild(element4);
                var cell5 = row.insertCell(4);
                //cell4.innerHTML = $.trim(data4);
                var element5 = document.createElement("input");
                element5.type = "text";
                element5.className = "new_input";
                element5.name = "offender_name";
                element5.id = "offender_name"+rowCount;
                element5.size = 20;
                element5.value = "";
                cell5.appendChild(element5);


                var cell6 = row.insertCell(5);
               // cell5.innerHTML = $.trim(data5);
                var element6 = document.createElement("input");
                element6.type = "text";
                element6.className = "new_input";
                element6.name = "father_name";
                element6.id = "father_name"+rowCount;
                element6.size = 20;
               element6.value = "";
                cell6.appendChild(element6);

                 var cell7 = row.insertCell(6);
               // cell6.innerHTML = $.trim(data6);
                var element7 = document.createElement("input");
                element7.type = "text";
                element7.name = "offender_age";
                element7.id = "offender_age"+rowCount;
                element7.size = 20;
                element7.value = "";
                cell7.appendChild(element7);

                 var cell8 = row.insertCell(7);
              //  cell7.innerHTML = $.trim(data7);
                var element8 = document.createElement("input");
                element8.type = "text";
                element8.className = "new_input";
                element8.name = "offender_address";
                element8.id = "offender_address"+rowCount;
                element8.size = 20;
                element8.value = "";
                cell8.appendChild(element8);

                var cell9 = row.insertCell(8);
                //cell9.innerHTML = $.trim(data2);
                var element9 = document.createElement("input");
                element9.type = "text";
                element9.className = "new_input";
                element9.name = "offender_city";
                element9.id = "offender_city"+rowCount;
                element9.size = 20;
                element9.value = "";
                if(rowCount == 0)
                   element9.setAttribute("onblur","Unique3()");
                cell9.appendChild(element9);
                jQuery(function(){
                $("#offender_city"+rowCount).autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getOffender_city" }
            }
              });
            });


                 var cell10 = row.insertCell(9);
              //  cell9.innerHTML = $.trim(data9);
                var element10 = document.createElement("input");
                element10.type = "text";
                element10.name = "receipt_book_no";
                element10.id = "receipt_book_no"+rowCount;
                element10.size = 20;
               element10.value = "";
               if(rowCount == 0)
                   element10.setAttribute("onblur","Unique4()");
               else
                    element10.setAttribute("onblur","Unique7("+rowCount+")");
                cell10.appendChild(element10);
                jQuery(function(){
                $("#receipt_book_no"+rowCount).autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getReceipt_book_no" },
                action2: function() { return $("#officer_name").val()}

            }
              });
            });

                 var cell11 = row.insertCell(10);
               // cell10.innerHTML = $.trim(data10);
                var element11 = document.createElement("input");
                element11.type = "text";
                element11.name = "receipt_page_no";
                element11.id = "receipt_page_no"+rowCount;
                element11.size = 20;
               element11.value = "";
               if(rowCount == 0)
                  element11.setAttribute("onblur","Unique5()");
                else
                element11.setAttribute("onblur","Unique6("+rowCount+")");
                cell11.appendChild(element11);

                jQuery(function(){
                $("#receipt_page_no"+rowCount).autocomplete("trafficPoliceQuickEntryCont.do", {
            extraParams: {
                action1: function() { return "getReceipt_page_no" },
                action2: function() { return $("#receipt_book_no"+rowCount).val()}
            }
              });
            });

               //  var cell11 = row.insertCell(10);
              //  cell11.innerHTML = $.trim(data11);
               // var element11 = document.createElement("input");
               // element11.type = "text";
               // element11.name = "receipt_book_rev_no";
               // element11.id = "receipt_book_rev_no"+rowCount;
               // element11.size = 20;
               // element11.value = "";
               // cell11.appendChild(element11);

               //  var cell12 = row.insertCell(11);
             //   cell12.innerHTML = $.trim(data12);
               // var element12 = document.createElement("select");
               // element12.type = "text";
               // element12.name = "process_type";
               // element12.id = "process_type"+rowCount;
               // element12.size = 20;
               //element12.value = "";
              // var option = document.createElement("option");
              // option.value = "Select process type";
              // option.innerHTML = "Select process type";
               //element12.appendChild(option);

              // var option1 = document.createElement("option");
              // option1.value = "Court";
              // option1.innerHTML = "Court";
              // element12.appendChild(option1);

              //  cell12.appendChild(element12);

                var height = (rowCount * 25)+ 60;
                document.getElementById("autoCreateTableDiv1").style.visibility = "visible";
                //document.getElementById("autoCreateTableDiv1").style.height = height+'px';
                $("#autoCreateTableDiv1").show();
       }

             function checkValue()
        {
            //alert("hi");
          var table =document.getElementById("tablebody");
          table.innerHTML="";
          var noOfStructure=parseInt(document.getElementById("no_of_offence").value);
                  for(var i=0; i<noOfStructure; i++) {
                        addRow();
                         amount=0;
                       // $("#penalty_amount"+i).val(0);
                        $("#total_penalty_amount").val(0);
                    }
                        document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                       
        }



         function checkValue1()
        {
          var table =document.getElementById("tablebody1");
          table.innerHTML="";
          var noOfStructure=parseInt(document.getElementById("Total_No_of_Rows").value);
                  for(var i=0; i<noOfStructure && i<50; i++) {
                        addRow1();

                     }
                        document.getElementById("autoCreateTableDiv1").style.visibility = "visible";
                        //Unique();
                        //changeValue();
         }

        function getTotalPenaltyAmount()
       { //debugger;
          // alert("hi");
         // var amount=0;
       // var no_of_offence = $("#no_of_offence").val();
        var table = document.getElementById('tablebody');
        var rowCount = table.rows.length;                     
       
         //var tot_amount = $("#total_penalty_amount").val();
         var pa_value=0;
        for(var i = 0; i < rowCount; i++){
            pa_value =parseInt(pa_value)+ parseInt( $("#penalty_amount"+i).val());
             //alert(pa_value) ;
        }
        $("#total_penalty_amount").val(pa_value);
   }
           /*    function changeValue(){
                   //alert("hi");
                        if(document.getElementById("process_type").value=='Court'){
                            // alert("hi");
                            document.getElementById("case_no").disabled = false;
                            document.getElementById("case_date").disabled = false;
                            document.getElementById("court_case3").style.visibility='visible';
                        }
                        else{
                             document.getElementById("court_case3").style.visibility='hidden';
                            document.getElementById("case_no").disabled = true;
                            document.getElementById("case_date").disabled = true;
                       }
                    } */

        function singleCheckUncheck(id)
          {
                if ($('#check_print'+id).is(':checked')) {
                    $("#traffic_police_id"+id).val("1");
                }else{
                    $("#traffic_police_id"+id).val("0");
                }
          }
    </script>

    <body>
        <form action="trafficPoliceQuickEntryCont.do" method="post">
        <table align="center"  class="main" cellpadding="0" cellspacing="0">
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
                 <td>
                 <table width="100%">
                     <tr>
                     <td class="header_table" align="center"><h1>Traffic Police Page</h1></td>
                     </tr>
                 </table>
                 </td>
                 </tr>

                 <tr>
                 <td align="center">
                 <table  align="center" class="heading1">
                      <tr>
                         <td>Select no. of offence
                             <select class="dropdown"  name="no_of_offence" id="no_of_offence" onclick="checkValue();"  >
                                  <%-- <option value="0" >0</option> --%>
                              <option value="1" >1</option>
                              <option value="2" >2</option>
                              <option value="3" >3</option>
                              <option value="4" >4</option>
                              <option value="5" >5</option>
                              <option value="6" >6</option>
                              <option value="7" >7</option>
                              <option value="8" >8</option>
                              <option value="9" >9</option>
                              <option value="10" >10</option>
                              </select>
                             Transport Type
                             <select class="dropdown"  name="commercial_type" id="commercial_type">
                              <option value="Commercial" >Commercial</option>
                              <option value="Non Commercial" >Non Commercial</option>
                             </select>
                         </td>
                     </tr>
                    <tr>
                    <td>
                        <DIV id="autoCreateTableDiv" class="content" style="visibility:visible;margin-bottom: 20px">  <%--;min-height: 300px;max-height: none --%>
                    <table id="insertTable" border="1" align="center" class="content" width="300">

                     <thead><th align="center">Act Origin</th>
                     <th align="center">Act</th>
                     <th align="center">Offence Type</th>
                     <th align="center">Offence Code</th>
                     <th align="center">Penalty Amount</th></thead>
                        <tbody id="tablebody"></tbody>
                        <tfoot ><th colspan="4" align="right">Total Penalty Amount</th>
                        <td colspan="4"><input type="text" id="total_penalty_amount" name="total_penalty_amount" value="${total_penalty_amount}" size="10"  ></td>
                        </tfoot>
               <%--  <tr>
                     <th class="heading">Act Origin</th>
                     <th class="heading">Act</th>
                     <th class="heading">Offence Type</th>
                     <th class="heading">Offence Code</th>
                     <th class="heading">Penalty Amount</th>
                     </tr>
                    <tr align="center">
                    <td><input type="text" class="new_input" id="act_origin" name="act_origin" value="${act_origin}" size="10" ></td>
                    <td><input type="text" class="new_input" id="act" name="act" value="${act}" size="10" ></td>
                    <td><input type="text" class="new_input" id="offence_type" name="offence_type" value="${offence_type}" size="10" ></td>
                    <td><input type="text" class="new_input" id="offence_code" name="offence_code" value="${offence_code}" size="10" ></td>
                    <td><input type="text" id="penalty_amount" name="penalty_amount" value="${penalty_amount}" size="10" ></td>
                    </tr>
                    <tr>
                    <th class="heading">Total Penalty Amount</t
                    <td><input type="text" id="total_penalty_amount" name="total_penalty_amount" value="${total_penalty_amount}" size="10"></td>
                    </tr>  --%>
                    </table>
                    </DIV>
                    </td>
                    </tr>
                    <tr>
                         <td> officer name <input class="new_input" type="text" id="officer_name" name="Officer_Name" value=""  size="25" />
                          offence date <input class="input" type="text" id="offence_date" name="offence_date" value=""/></td>
                     </tr>
                     <tr>
                         <td>offence City <input class="new_input" type="text" id="offence_city" name="offence_city" value="" size="25"/>
                         Zone<input class="new_input" type="text" id="Zone" name="Zone" value="" size="25" />
                         Offence location <input class="new_input" type="text" id="Offence_Location" name="Offence_Location" value="" size="25" /></td
                     </tr>
                     <tr>
                         <td>Total No. of Rows
                             <input class="input" type="number" id="Total_No_of_Rows" min="1" max="50" name="Total_No._of_Rows" value="" style="max-width: 50px" size="2" onclick="checkValue1();" onkeyup="checkValue1();" >
                         </td>
                     </tr>
              </table>
              </td>
              </tr>
              <tr>
              <td>
              <div id="autoCreateTableDiv1" class="content_div" style="visibility: visible;max-height: none">
              <table id="insertTable1" border="1" align="center" class="content" width="300">

                  <thead><th align="center" style="width: 50px">S.No</th>
                    <th align="center">Jarayam No.</th>
                    <th align="center">Vehicle No.</th>
                    <th align="center">Offender License No</th>
                    <th align="center">Offender Name</th>
                    <th align="center">Father's Name</th>
                    <th align="center">Offender Age</th>
                    <th align="center">Offender Address</th>
                    <th align="center">Offender City</th>
                    <th align="center">Receipt Book No.</th>
                    <th align="center">Receipt Page No.</th>
                    <%--<th align="center">Receipt Book Rev. No</th>
                    <th align="center">Process Type</th> </thead> --%>
                    <tbody id="tablebody1"></tbody>
                    <tfoot></tfoot>
                 <%--  <tr align="center">
                    <td>
                    <input class="input" type="text" id="jarayam_no" name="jarayam_no" value="" size="20">
                    </td>
                    <td>
                    <input class="input" type="text" id="offender_license_no" name="offender_license_no" value="" size="30">
                    </td>
                    <td>
                    <input class="new_input" type="text" id="Offender_Name" name="Offender_Name" value=""  size="25" >
                    </td>
                    <td>
                    <input class="input" type="text" id="offender_age" name="offender_age" value=""  size="25" >
                    </td>
                    <td>
                    <input class="new_input" type="text" id="offender_address" name="offender_address" value=""  size="25" >
                    </td>
                    <td>
                    <input class="new_input" type="text" id="offender_city" name="offender_city" value=""  size="25" >
                    </td>
                    <td>
                    <input class="new_input" type="text" id="father_name" name="father_name" value=""  size="25" >
                    </td>
                    <td>
                    <input class="new_input" type="text" id="vehicle_no_state" name="vehicle_no_state" value="${vehicle_no_state}" size="2" maxlength="2" onkeyup="getFocusNextField(id);" >
                    <input class="new_input" type="text" id="vehicle_no_city" name="vehicle_no_city" value="${vehicle_no_city}" size="2" maxlength="2" onkeyup="getFocusNextField(id);" >
                    <input class="input" type="text" id="vehicle_no_series" name="vehicle_no_series" value="" size="4" maxlength="5" onkeyup="getFocusNextField(id);" >
                    <input class="input" type="text" id="vehicle_no_digits" name="vehicle_no_digits" value="" size="4" maxlength="4" onkeyup="getFocusNextField(id);" >
                    </td>
                    <td>
                    <input class="input" type="text" id="reciept_page_no" name="reciept_page_no" value=""  size="25" >
                    </td>
                    <td>
                    <input class="input" type="text" id="reciept_book_no" name="reciept_book_no" value="${receipt_book_no}" size="10" onclick="">
                    </td>
                    <td>
                    <input class="input" type="text" id="reciept_book_rev_no" name="reciept_book_rev_no" value="${reciept_book_rev_no}" size="10" onclick="">
                    </td>
                    <td>
                    <select class="dropdown1" id="process_type" name="process_type" onchange="changeValue();">
                    <option value="" style="color: red" selected>Select process type</option>
                    <option>Court</option>
                    </select>
                    <table>
                    <tr id="court_case3" style="visibility:hidden">
                    <th class="heading1">Case No.</th>
                    <td>
                    <input class="input" type="text" id="case_no" name="case_no" value="" size="20" disabled>
                    </td>
                    <th class="heading1">Case Date</th>
                    <td>
                    <input class="input" type="text" id="case_date" name="case_date" value="" size="15" maxlength="10" >
                    </td>
                    </tr>
                    </table>
                    </td>
                    </tr> --%>
            </table>
            </div>
            </td>
            </tr>

                    <tr>
                    <td align="center" colspan="4">
                        <input class="button" type="submit" name="task" id="SAVE" value="Save"  >
                    </td>
                    </tr>
        </table>
        </div>
        </td>
        </tr>
        </table>
    </form>
    </body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
 <%@page import="java.util.*" %>  
 <%
 	Random randomGenerator = new Random();
	int randomInt = randomGenerator.nextInt(1000000);
 %>
 
 <%
 	Random randomGeneratorr = new Random();
	int randomcust = randomGeneratorr.nextInt(1000);
 %>





<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->

<head>
    <meta charset="UTF-8"/>
    <meta name="GENERATOR" content="Evrsoft First Page">
    <title>Title</title>
    <link rel="stylesheet" href="@{/style.css}"/>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css"/>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    
    <style>
        
        .new_input
                {
                    font-size: 16px;
                    font-family:"kruti Dev 010", Sans-Serif;
                    font-weight: 500;
                    background-color: white;
                }
    </style>
    
    
</head>
<body>
<form method="post" action="/view/trafficPolice/pgRedirect.jsp">
    <div class="container register">
        <div class="row">
            <div class="col-md-3 register-left">
                <img src="https://image.ibb.co/n7oTvU/logo_white.png" alt=""/>
                <h3>Welcome</h3>
                <p>Proceed the Payment</p>
            </div>
            <div class="col-md-9 register-right">
                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                        <h3 class="register-heading">Welcome to Paytm Payment</h3>
                        <div class="row register-form">
                            <div class="col-md-10">
                                  <c:forEach var="tp" items="${requestScope['tplist']}" >
                                <div class="form-group">
                                   
                                    <input id="ORDER_ID" tabindex="1" maxlength="20" size="20"
                                           name="ORDER_ID" autocomplete="off" class="form-control" value="${Challan_no}">
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Customer ID" 
                                           name="CUST_ID" value="CUST<%= randomcust %>">
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="INDUSTRY_TYPE_ID" 
                                           name="INDUSTRY_TYPE_ID" value="Retail" />
                                </div>
                                
                                
                                  <div class="form-group">
                                    <input type="text" class="form-control" placeholder="OffenderName" class="new_input"
                                           name="OffenderName" value="${tp.offender_name}" />
                                </div>
                                 
                                <div class="form-group">
                                  <input type="text" class="form-control" placeholder="offender_mobile_number" 
                                           name="offender_mobile_number" value="${offender_mobile_number}" />
                                </div>
                                
                                  <div class="form-group">
                                  <input type="text" class="form-control" placeholder="vehicle_no" 
                                           name="vehicle_no" value="${tp.vehicle_no}" />
                                </div>
                                
                                 
                                
                                  <div class="form-group">
                                  <input type="text" class="form-control" placeholder="key_person_name" class="new_input"
                                           name="key_person_name" value="${tp.key_person_name}" />
                                </div>
 
                                 <div class="form-group">
                                  <input type="text" class="form-control" placeholder="offence_date" 
                                           name="offence_date" value="${tp.offence_date}" />
                                    
                                 </div>
                                  <c:forEach var="list" items="${tp.offenceList}"  varStatus="loopCounter">
                                  
                                  <div class="form-group">
                                  <input type="text" class="form-control" placeholder="vehicle_type" 
                                           name="vehicle_type" value="${list.vehicle_type}" />
                                </div>
                                  
                                    
                                  <div class="form-group">
                                  <input type="text" class="form-control" placeholder="act" 
                                           name="act" value="${list.act}" />
                                </div>
                                  
                                  
                                  <div class="form-group">
                                  <input type="text" class="form-control" placeholder="act_origin" 
                                           name="act_origin" value="${list.act_origin}" />
                                </div>
                                  
                                  
                                  <div class="form-group">
                                  <input type="text" class="form-control" placeholder="offence_type" class="new_input"
                                           name="offence_type" value="${list.offence_type}" />
                                </div>
                                  
                                 <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Amount" value="${list.penalty_amount}"
                                           name="TXN_AMOUNT"/>
                                </div>
                                
                                
                             
                                </c:forEach>
                         
                                
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Channel" value="WEB"
                                           name="CHANNEL_ID"/>
                                     <input type="hidden" id="MID" tabindex="2" maxlength="30" size="12" name="MID" autocomplete="off" value="BtgxwM61526972362584">
                                </div>
                               
                                            </c:forEach>   
                                <!--  <input type="submit" class="btnRegister"  value="Pay with Paytm"/>   -->
                                <button type="submit" class="btnRegister" style="align : center">Pay with Paytm</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</form>
</body>
</html>

<%-- 
    Document   : header
    Created on : Nov 3, 2012, 1:20:58 PM
    Author     : Neha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" href="style/header.css" rel="stylesheet"/>
        <title>Header Page</title>
    </head>
    <body>
      <div id="header-wrapper">
            <div id="header">
                <div id="logo" align="center" style="height: 85px">
                    <div >
                        <div  align="center"> <h1 align="center" ><a href="#" > <img src="./images/police_logo.jpg" width="150" height="25"> </a></h1></div>
                        <span ><img src="./images/mp_police.png" width="50" height="50">
                           </span>
                        
                        <div style="display:inline-block;position:absolute;margin-left:260px;">
                            <p>Name: ${name}</p>
                            <p>Designation: ${designation}</p>
                        </div>
                    </div>
                </div>
            </div> </div>
    </body>
</html>

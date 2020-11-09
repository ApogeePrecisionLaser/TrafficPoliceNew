<%-- 
    Document   : index
    Created on : Jul 19, 2019, 4:03:27 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <style>
input[type=text], select {
  width: 30%;
  padding: 12px 10px;
  margin: 4px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 2px;
  box-sizing: border-box;
}

input[type=submit] {
  width: 20%;
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 4px 0;
  border: none;
  border-radius: 2px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}

div {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 10px;
}
</style>
    
    <body>
      
<h3>Mobile Number Validation</h3>

<div class="container-fluid">
  <form action="OTPGeneral" method="get">
    <label for="mno">Mobile number</label>
    <input type="text" id="mobile_no" name="mobile_no" placeholder="mobile number..">

    
    <input type="submit" value="Submit">
    
  </form>
    <strong style="color: red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${message}</strong>
</div>

    </body>
</html>

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
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

input[type=submit] {
  width: 30%;
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}

div {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
}
</style>
    
    <body>
      
<h3>Mobile Number Validation</h3>

<div>
  <form action="OTPverify">
    <label for="OTP">Enter the OTP</label>
    <input type="text" id="otp" name="otp" placeholder="Enter the OTP..">
    <input type="hidden" id="tplist" name= "tplist" value="${tplist}" >
  <input type="submit" value="Submit">
  </form>
     <Strong style="color: red">${message}</Strong>
</div>

    </body>
</html>

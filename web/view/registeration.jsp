<%-- 
    Document   : registeration
    Created on : Jul 19, 2019, 11:55:49 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
body {
  font-family: Arial, Helvetica, sans-serif;
  background-color: black;
}

* {
/*  box-sizing: border-box;*/
}
#username-error, #email-error {
  color: #d9534f;
  padding: 12px;
  display: none;
}

/* Add padding to containers */
.container {
  padding: 16px;
  background-color: white;
}

/* Full-width input fields */
input[type=text], input[type=password] {
  width: 50%;
  padding: 15px;
  margin: 5px 0 22px 0;
  display: inline-block;
  border: none;
  background: #f1f1f1;
}

input[type=text]:focus, input[type=password]:focus {
  background-color: #ddd;
  outline: none;
}

/* Overwrite default styles of hr */
hr {
  border: 1px solid #f1f1f1;
  margin-bottom: 25px;
}

/* Set a style for the submit button */
.registerbtn {
  background-color: #4CAF50;
  color: white;
  padding: 16px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 50%;
  opacity: 0.9;
}

.registerbtn:hover {
  opacity: 1;
}

/* Add a blue text color to links */
a {
  color: dodgerblue;
}

/* Set a grey background color and center the text of the "sign in" section */
.signin {
  background-color: #f1f1f1;
  text-align: center;
}
</style>
        <title>JSP Page</title>
    </head>
  
    <script>  
 
    
    function validate()
{
    emailvalidate();
    passwordvalidate(); 
    namevalidate();
    useridvalidate();
}
        
function emailvalidate()  
{  
var x=document.myform.email.value;  
var atposition=x.indexOf("@");  
var dotposition=x.lastIndexOf(".");  
if (atposition<1 || dotposition<atposition+2 || dotposition+2>=x.length){  
  alert("Please enter a valid e-mail address");  
  return false;

  }  
}  

function passwordvalidate()
{
    var p = document.getElementById('password').value,
        errors = [];
    if (p.length < 8) {
        errors.push("Your password must be at least 8 characters");
    }
    if (p.search(/[a-z]/i) < 0) {
        errors.push("Your password must contain at least one letter."); 
    }
    if (p.search(/[0-9]/) < 0) {
        errors.push("Your password must contain at least one digit.");
    }
    if (errors.length > 0) {
        alert(errors.join("\n"));
        return false;
    }
    return true;
}

function  namevalidate()
{
     var val = document.getElementById('name').value;
    
    if (!val.match(/^[a-zA-Z]+$/)) 
    {
        alert('Only alphabets are allowed in the client name');
        return false;
    }
    
    return true;
}
function useridvalidate()
{
      var u = document.getElementById('uid').value,
        errors = [];
    if (u.length < 5) {
        errors.push("Your Userid must be at least 5 characters"); 
    }
    if (u.search(/[a-z]/i) < 0) {
        errors.push("Your Userid must contain at least one letter.");
    }
    if (u.search(/[0-9]/) < 0) {
        errors.push("Your Userid must contain at least one digit."); 
    }
    if (u.search(/[!#$%&?@]/) < 0) {
        errors.push("Your Userid must contain at least one specialcharacter."); 
    }
    if (errors.length > 0) {
        alert(errors.join("\n"));
        return false;
    }
    return true;
    
    
}
</script>  
    
    
    
    
    
    
    
    
    
    <body>
             
        <form  name="myform" action="registeration.jsp" onsubmit="validate();">
  <div class="container">
    <h1>Register</h1>
    <p>Please fill this form to create an account.</p>
    <hr>

    <label for="name"><b>Client Name</b></label>
    <input type="text" placeholder="Enter name" id="name" name="name" required></br>
    
    <label for="mobile"><b>Mobile no</b></label>
    
    <input type="text" value=<%= request.getAttribute("mob") %> pattern="[789][0-9]{9}" id ="numb" name="mobile" readonly>
    </br>
    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email"  required></br>
    
   <label for="address"><b>Country </b></label>
   <input type="text" placeholder="Search.." name="search"  required>

   <label for="id"><b>User id(Your Userid must be of at least 5 characters,one letter,one digit & at least one specialcharacter)</b></label>
    <input type="text" placeholder="Enter id" name="id" id="uid" required>
    
    <label for="psw"><b>Password(Your password must be of at least 8 characters,one letter and one digit )</b></label>
    <input type="password" placeholder="Enter Password" id="password" name="psw" required>

  
    <hr>
    <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>

    <button type="submit" class="registerbtn" />Register</button>
    <p id="demo"></p>
  </div>
  
  <div class="container signin">
    <p>Already have an account? <a href="#">Sign in</a>.</p>
  </div>
</form>
     

    </body>
</html>

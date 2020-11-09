<%-- 
    Document   : reg
    Created on : Jul 22, 2019, 9:33:00 PM
    Author     : DELL
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/vendors/formvalidation/dist/css/formValidation.min.css">
        
        
        
  <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
    <style>
    
 
/*#wrapper
{
  width:995px;
  padding:0px;
  margin:0px auto;
  font-family:helvetica;
  text-align:center;
}*/
/*input[type="text"]
{
  width:200px;
  height:35px;
  margin-right:2px;
  border-radius:3px;
  border:1px solid green;
  padding:5px;
}*/
input[type="button"]
{
  background:none;
  color:white;
  border:none;
  width:110px;
  height:35px;
  border-radius:3px;
  background-color:green;
  font-size:16px;
}
        
        
        
        
        
        

        .emsg{
    color: red;
}

  
.hidden {
     visibility:hidden;
}

   .tool_tip{
    z-index:10;display:none; padding:15px 10px 10px 10px;
    margin-top:-12px; margin-left:28px;
    width:300px; line-height:20px;
							position:absolute; color:#111;
    border:1px solid #fa8605; background:#f1f1f1;
FONT-SIZE: 12px;
font-family: Verdana;
color: #ffffff;
}
#msg_email {
    color: #FF0000;;
}     
        
        
        
        
        /* The message box is shown when the user clicks on the password field */
#message {
  display:none;
  background: #f1f1f1;
  color: #000;
  position: relative;
  padding: 20px;
  margin-top: 10px;
}

#message p {
  padding: 10px 35px;
  font-size: 18px;
}
        
        
        
        /* Add a green text color and a checkmark when the requirements are right */
.valid {
  color: green;
}

.valid:before {
  position: relative;
  left: -35px;
  content: "✔";
}

/* Add a red text color and an "x" when the requirements are wrong */
.invalid {
  color: red;
}

.invalid:before {
  position: relative;
  left: -35px;
  content: "✖";
}
        
        
        
        
        
        </style>
    </head>
    
    <script type="text/javascript">
$(document).ready(function(){
    var $regexname=/^([a-zA-Z]{3,16})$/;
    $('.input-xlarge').on('keypress keydown keyup',function(){
             if (!$(this).val().match($regexname)) {
              // there is a mismatch, hence show the error message
                 $('.emsg').removeClass('hidden');
                 $('.emsg').show();
             }
           else{
                // else, do not display message
                $('.emsg').addClass('hidden');
               }
         });
});
</script>

<script>
$(document).ready(function() {
////////////////////
$('#uid').keyup(function(){
$('#d12,#d13,#d14,#d15,#d16').css("color", "white");

var str=$('#uid').val();

var upper_text= new RegExp('[A-Z]');
var lower_text= new RegExp('[a-z]');
var number_check=new RegExp('[0-9]');
var special_char= new RegExp('[!/\'^�$%&*()}{@#~?><>,|=_+�-\]');

var flag='T';

if(str.match(upper_text)){
$('#d12').css("background-color", "green");
}else{$('#d12').css("background-color", "red");
flag='F';}

if(str.match(lower_text)){
$('#d13').css("background-color", "green");
}else{$('#d13').css("background-color", "red");
flag='F';}

if(str.match(special_char)){
$('#d14').css("background-color", "green");
}else{$('#d14').css("background-color", "red");
flag='F';}

if(str.match(number_check)){
$('#d15').css("background-color", "green");
}else{$('#d15').css("background-color", "red");
flag='F';}


if(str.length>5){
$('#d16').css("background-color", "green");
}else{$('#d16').css("background-color", "red");
flag='F';}


if(flag=='T'){
$("#d1").fadeOut();
$('#display_box').css("color","green");
$('#display_box').html(str);
}else{
$("#d1").show();
$('#display_box').css("color","red");
$('#display_box').html(str);
}
});



///////////////
$('#uid').blur(function(){
$("#d1").fadeOut();
});
///////////
$('#uid').focus(function(){
$("#d1").show();
$('#d12,#d13,#d14,#d15,#d16').css("color", "black");
});

///////////////////

///////
})
</script>


<script>
$(document).ready(function() {
////////////////////

$('#email').blur(function(){
var str2=$('#email').val();
if(str2.length<1){
$('#msg_email').html('Please enter email ');
}
});






$('#email').blur(function(){
var email=$('#email').val();
var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
if(regex.test(email)){
	$('#email').closest('.form-group').removeClass('has-error');
	$('#email').closest('.form-group').addClass('has-success');
	$('#msg_email').removeClass('bg-danger').addClass('bg-success');
	$('#msg_email').html("<span class='glyphicon glyphicon-ok'></span> Valid Email address");
  }else{
	$('#msg_email').html('Failed');  
	$('#email').closest('.form-group').removeClass('has-success');
	$('#email').closest('.form-group').addClass('has-error');
	$('#msg_email').addClass('bg-danger');
	$('#msg_email').html("<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>Not a valid Email address");
}  
$('#msg_email').show();
});
///////////////////
})
</script>

<!-- <script type="text/javascript">
$(document).ready(function(){
    var $regexname=/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    $('.input-xlarge').on('keypress ',function(){
             if (!$(this).val().match($regexname)) {
              // there is a mismatch, hence show the error message
                 $('.emsg').removeClass('hiddenemail');
                 $('.emsg').show();
             }
           else{
                // else, do not display message
                $('.emsg').addClass('hiddenemail');
               }
         });
});





</script>-->
    <body>
       <div class="container">
 

    <form class="form-horizontal" action='' method="POST" id="myreg">
  <fieldset>
    <div id="legend">
      <legend class="">Admin Registeration</legend>
    </div>
    <div class="control-group">
      <!-- Username -->
      <label class="control-label"  for="username">Username</label>
      <div class="controls">
          <input type="text" id="username" name="username" placeholder="" class="input-xlarge"  required>
        <p><span class="emsg hidden">Please Enter a Valid Name</span></p>
      </div>
    </div>
 
    <div class="control-group">
      <!-- E-mail -->
      <label class="control-label" for="email">E-mail</label>
      <div class="controls">
        <input type="text" id="email" name="email" placeholder=""class="input-xlarge"  data-fv-email-address___message="The value is not a valid email address"   required>
<!--        <p><span class=" emsg hidden">Please Enter  Valid Email</span></p>-->
  <div id=msg_email colour="red"></div> 
      </div>
    </div>
      
      <div class="control-group">
      <!-- UserId -->
      <label class="control-label"  for="Userid">Name</label>
      <div class="controls">
          <input type="text" id="uid" name="uid" placeholder="" class="input-xlarge" required="">
<!--        <p class="help-block">Please confirm password</p>-->
 <span id="d1" class="tool_tip">
<span id=d12>One Upper case letter</span><br>
<span id=d13>One Lower case letter</span><br>
<span id=d14>One Special char</span><br>
<span id=d15>One number</span><br>
<span id=d16>Length 6 char</span>
 </span>
<!--</span><br><div id=display_box class=msg></div>-->

      </div>
    </div>
      
      
    
 
    <div class="control-group">
      <!-- Password-->
      <label class="control-label" for="psw">Password</label>
      <div class="controls">
        <input type="password" id="psw" name="psw" placeholder="" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required class="input-xlarge" required>
<!--        <p class="help-block">Password should be at least 4 characters</p>-->
      </div>
    </div>
 
    <div class="control-group">
      <!-- Password -->
      <label class="control-label"  for="mobile_no">Mobile no</label>
      <div class="controls">
          <h1>${requestScope.output}</h1>
          <input type="text" id="mobile_no" name="mobile_no" value="${mob}" placeholder="" class="input-xlarge" readonly>
<!--        <p class="help-block">Please confirm password</p>-->
      </div>
    </div>
      
      <div class="control-group">
      <!-- Rank -->
      <label class="control-label"  for="Rank">Rank</label>
      <div class="controls">
          <h1>${requestScope.output}</h1>
          <input type="text" id="rank" name="Rank" placeholder="" class="input-xlarge" value="${rank}" >
<!--        <p class="help-block">Please confirm password</p>-->
      </div>
    </div>
      
      
    
      
      
 
<!--      <div class="control-group" id="wrapper" >-->
    <div class="control-group"  >
      <label class="control-label"  for="State">Select no. of Entities</label>
      <div class="controls">

 <SELECT id="ppl">
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
</SELECT>
          
          
          
<div id="holder"></div>
        
</div>
       
    </div>
      
 



      
      
      <div class="control-group">
      <!-- City -->
      <label class="control-label"  for="Division">City </label>
      <div class="controls">
        <input type="text" id="division" name="city" placeholder="" class="input-xlarge">
<!--        <p class="help-block">Please confirm password</p>-->
      </div>
    </div>
      
      
      
    <div class="control-group">
      <!-- Button -->
      <div class="controls">
        <button type ="submit" class="btn btn-success">Register</button>
      </div>
    </div>
  </fieldset>
</form>
       <script src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.35.3/es6-shim.min.js"></script>
<script src="/vendors/formvalidation/dist/js/FormValidation.min.js"></script>
<script src="/vendors/formvalidation/dist/js/plugins/Tachyons.min.js"></script>



     <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>      
           
           
  </div>
      
<div id="message">
  <h3>Password must contain the following:</h3>
  <p id="letter" class="invalid">A <b>lowercase</b> letter</p>
  <p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
  <p id="number" class="invalid">A <b>number</b></p>
  <p id="length" class="invalid">Minimum <b>8 characters</b></p>
</div>
				
<script>
var myInput = document.getElementById("psw");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");

// When the user clicks on the password field, show the message box
myInput.onfocus = function() {
  document.getElementById("message").style.display = "block";
}

// When the user clicks outside of the password field, hide the message box
myInput.onblur = function() {
  document.getElementById("message").style.display = "none";
}

// When the user starts to type something inside the password field
myInput.onkeyup = function() {
  // Validate lowercase letters
  var lowerCaseLetters = /[a-z]/g;
  if(myInput.value.match(lowerCaseLetters)) {  
    letter.classList.remove("invalid");
    letter.classList.add("valid");
  } else {
    letter.classList.remove("valid");
    letter.classList.add("invalid");
  }
  
  // Validate capital letters
  var upperCaseLetters = /[A-Z]/g;
  if(myInput.value.match(upperCaseLetters)) {  
    capital.classList.remove("invalid");
    capital.classList.add("valid");
  } else {
    capital.classList.remove("valid");
    capital.classList.add("invalid");
  }

  // Validate numbers
  var numbers = /[0-9]/g;
  if(myInput.value.match(numbers)) {  
    number.classList.remove("invalid");
    number.classList.add("valid");
  } else {
    number.classList.remove("valid");
    number.classList.add("invalid");
  }
  
  // Validate length
  if(myInput.value.length >= 8) {
    length.classList.remove("invalid");
    length.classList.add("valid");
  } else {
    length.classList.remove("valid");
    length.classList.add("invalid");
  }
}




document.addEventListener('DOMContentLoaded', function(e) {
    FormValidation.formValidation(
        document.getElementById('myreg'),
        {
            plugins: {
                declarative: new FormValidation.plugins.Declarative({
                    html5Input: true,
                }),
                trigger: new FormValidation.plugins.Trigger(),
                tachyons: new FormValidation.plugins.Tachyons(),
                submitButton: new FormValidation.plugins.SubmitButton(),
                icon: new FormValidation.plugins.Icon({
                    valid: 'fa fa-check',
                    invalid: 'fa fa-times',
                    validating: 'fa fa-refresh',
                }),
            },
        }
    );
});










</script>
        

    </body>
</html>

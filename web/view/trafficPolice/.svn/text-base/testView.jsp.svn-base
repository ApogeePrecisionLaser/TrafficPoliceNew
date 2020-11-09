<%-- 
    Document   : testView
    Created on : Jul 21, 2015, 12:03:06 PM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<HTML>
<HEAD>
</HEAD>
<BODY>

<script type="text/javascript" src="http://localhost:12175/javascript/GpsGate.js"></script>

<script type="text/javascript">
  //<![CDATA[

  // That is the callback function that is specified in getGpsInfo() and
  // executed after the data is returned
  // See more info on the returned "gps" object below.

	if (typeof(GpsGate) == 'undefined' || typeof(GpsGate.Client) == 'undefined')
	{
		alert('GpsGate not installed or not started!');
	}

	function gpsGateCallback(gps)
	{
		var resultTag = document.getElementById('position');
		resultTag.innerHTML = 'longitude:' + gps.trackPoint.position.longitude +
		                      ' latitude:' + gps.trackPoint.position.latitude;

		var d = new Date(gps.trackPoint.utc);

		resultTag = document.getElementById('time');
		resultTag.innerHTML = d.toLocaleString();
	}

  //]]>
</script>

<div id="position"></div><br>
<br>

<div id="time"></div><br>
<br>

<form name="f1">
  <input value="GPS info" type="button"
                         onclick='JavaScript:GpsGate.Client.getGpsInfo(gpsGateCallback)'
                         id=button1 name=button1>
</form>

</BODY>
</HTML>

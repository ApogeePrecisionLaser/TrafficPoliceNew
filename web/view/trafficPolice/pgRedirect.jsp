<%@page import="com.tp.util.PaytmConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.paytm.pg.merchant.CheckSumServiceHelper"%>        

<%

Enumeration<String> paramNames = request.getParameterNames();
Map<String, String[]> mapData = request.getParameterMap();
TreeMap<String,String> parameters = new TreeMap<String,String>();
String paytmChecksum =  "";
String Challan_id = request.getParameter("ORDER_ID");
String cust_id = request.getParameter("CUST_ID");
String txn = request.getParameter("TXN_AMOUNT");
//while(paramNames.hasMoreElements()) {
//	String paramName = (String)paramNames.nextElement();
//	parameters.put(paramName,mapData.get(paramName)[0]);	
//}

parameters.put("ORDER_ID",Challan_id);
parameters.put("CUST_ID",cust_id);
parameters.put("TXN_AMOUNT",txn);
parameters.put("INDUSTRY_TYPE_ID","Retail");
parameters.put("CHANNEL_ID","WEB");
parameters.put("MID","BtgxwM61526972362584");

parameters.put("WEBSITE",PaytmConstants.WEBSITE);
parameters.put("MOBILE_NO","8840590504");
parameters.put("EMAIL","shubham.srivastava243@gmail.com");
parameters.put("CALLBACK_URL", "http://120.138.10.251:8080/TrafficPoliceNew/view/trafficPolice/pgResponse.jsp");


String checkSum =  CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(PaytmConstants.MERCHANT_KEY, parameters);


StringBuilder outputHtml = new StringBuilder();
outputHtml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
outputHtml.append("<html>");
outputHtml.append("<head>");
outputHtml.append("<title>Merchant Check Out Page</title>");
outputHtml.append("</head>");
outputHtml.append("<body>");
outputHtml.append("<center><h1>Please do not refresh this page...</h1></center>");
outputHtml.append("<form method='post' action='https://securegw-stage.paytm.in/order/process' name='f1'>");
outputHtml.append("<table border='1'>");
outputHtml.append("<tbody>");

for(Map.Entry<String,String> entry : parameters.entrySet()) {
	String key = entry.getKey();
	String value = entry.getValue();
	outputHtml.append("<input type='hidden' name='"+key+"' value='" +value+"'>");	
}	  
	  


outputHtml.append("<input type='hidden' name='CHECKSUMHASH' value='"+checkSum+"'>");
outputHtml.append("</tbody>");
outputHtml.append("</table>");
outputHtml.append("<script type='text/javascript'>");
outputHtml.append("document.f1.submit();");
outputHtml.append("</script>");
outputHtml.append("</form>");
outputHtml.append("</body>");
outputHtml.append("</html>");
out.println(outputHtml);
%>

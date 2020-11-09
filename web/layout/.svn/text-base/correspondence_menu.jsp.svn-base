<%-- 
    Document   : org_menu
    Created on : May 30, 2014, 9:45:52 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link rel="stylesheet" href="style/dropdown.css">
        <title>Organization Menu</title>
        <script>
            $(function() {
                if ($.browser.msie && $.browser.version.substr(0,1)<7)
                {
                    $('li').has('ul').mouseover(function(){
                        $(this).children('ul').css('visibility','visible');
                    }).mouseout(function(){
                        $(this).children('ul').css('visibility','hidden');
                    })
                }
            });
        </script>
    </head>
    <body>
        <div class="tab">
            <a href="#">Correspondence Menu</a>
            <div>
                <ul>                
                    <!--<li><a href="orgDetailEntryCont.do"></a></li>-->
                    <li><a href="typeOfDocumentCont.do">Type Of Document</a></li>
                    <li><a href="correspondenceStatusCont.do">Correspondence Status</a></li>
                    <li><a href="correspondencePriorityCont.do">Correspondence Priority</a></li>
                    <li><a href="correspondenceRemarkCont.do">Correspondence Reason</a></li>
                    <li><a href="correspondenceCont.do">Correspondence</a></li>
                    <li><a href="taskSchedulerCont.do?from=Schedule">Schedule Task</a></li>
                    <li><a href="taskSchedulerCont.do">Manage Task</a></li>
                    <li><a href="outgoingTaskCont.do">Outgoing Task</a></li>
                </ul>
            </div>
        </div>
    </body>
</html>

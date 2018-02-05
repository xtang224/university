<html>
<title>
DataList Error Page
</title>
<body>

<%@ include file="Header.html" %>

<!-- Need an error page to handle the exception message -->
<!-- What error page does an error page use? -->
<%@ page language="java" isErrorPage="true" import="java.util.*, java.sql.*" %>

<br>   
<h4>Exception details:</h4>
<p>
<!-- The fully-qualified class that is the exception -->
<%= exception.toString() %>
<br>
<!-- The exception's message to the world -->
<%= exception.getMessage() %>
<p>
</body>
</html>


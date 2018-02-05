<%@ include file="taglibs.jsp" %>
  
<HTML>
    <HEAD>
	<TITLE> Random Test Logout Page</TITLE> 			
    </HEAD>

<BODY>
    <CENTER><H1>Random Test Logout Page</H1><P>
    <CENTER><P>
    
    <HR WIDTH=70%>
    <BR><BR><BR>

    <logic:present name="personbean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.hello"/>
         <bean:write name="personbean" property="trueName" /><bean:message key="hello.jsp.page.student"/><p>        
       </h2>
    </logic:present>    

    <TABLE BGCOLOR="#D8D8D8" BORDER=2>
        <TR><TD ALIGN=center WIDTH=300><BR>
        <H3>Less than half of the students vote yes, therefore the entire class grades will not be revealed in public.</H3>
        <FONT SIZE=7 COLOR="#FF5555"> Have a good weekend!</FONT>
        <BR><BR>
        </TD></TR>
    </TABLE>
    </CENTER>
    <BR><BR><BR><BR>
    <HR WIDTH=60%><BR><BR>
        

    <html:img page="/struts-power.gif" alt="Powered by Struts"/>
</BODY>
</HTML>

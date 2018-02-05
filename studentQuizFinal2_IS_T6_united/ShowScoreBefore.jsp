<%@ include file="taglibs.jsp" %>

<html:html locale="true">
  <head>
    <title><bean:message key="hello.jsp.title.quit"/></title>
    <html:base/>
  </head>
  <body bgcolor="white"><p>

    <h2><bean:message key="hello.jsp.page.heading.quit"/></h2><p>

<!--   <html:errors/><p>  -->

    <logic:present name="personbean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.hello"/>
         <bean:write name="personbean" property="trueName" /><bean:message key="hello.jsp.page.student"/><p>        
       </h2>
    </logic:present>    

    <%! HttpSession session = null;      
        hello.PersonBean _pb2 = null;    
    %>
    <% session = request.getSession(true);          
       _pb2 = (hello.PersonBean)request.getAttribute(hello.Constants.PERSON_KEY);
    %>

    <center><h2>Not all students have voted yet, please continue to click the following submit button.</h2></center><P>
    <center>
    <html:form action="/Score.do?first=false" >           
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="playerId" value="<%= _pb2.getPlayerId()%>"/>
  
      <html:submit property="submit" value="Submit"/>
      <html:reset/>

    </html:form><br>
    </center>

    <html:img page="/struts-power.gif" alt="Powered by Struts"/>

  </body>
</html:html>

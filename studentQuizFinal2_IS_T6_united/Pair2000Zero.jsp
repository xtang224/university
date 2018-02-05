<%@ include file="taglibs.jsp" %>

<html:html locale="true">
  <head>   
    <html:base/>
  </head>
  <body bgcolor="white"><p>    

   <html:errors/><p> 

    <logic:present name="personbean" scope="request">
       <h2>
         <bean:message key="hello.jsp.page.hello"/>
         <bean:write name="personbean" property="trueName" /><bean:message key="hello.jsp.page.student"/><br>
         <bean:message key="hello.jsp.page.pair2000Zero" /><p>     
       </h2>
    </logic:present>        
   
    <%! HttpSession session = null;         
        hello.PersonBean _pb2 = null;
    %>
    <% session = request.getSession(true);     
       _pb2 = (hello.PersonBean)request.getAttribute(hello.Constants.PERSON_KEY);
      
       session.setAttribute(hello.Constants.PERSON_KEY, _pb2);
       request.setAttribute(hello.Constants.PERSON_KEY, _pb2);  
    %>
       
    <html:form action="/Random.do?first=true" >
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="playerId" value="<%= _pb2.getPlayerId()%>"/> 

      <html:submit property="submit" value="Submit"/>
      <html:reset/>
    </html:form><br>   
    
    <html:img page="/struts-power.gif" alt="Powered by Struts"/>
   
  </body>
</html:html>

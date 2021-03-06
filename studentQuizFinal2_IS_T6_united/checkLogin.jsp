<%@ include file="taglibs.jsp" %>
<%@page pageEncoding="utf-8"%>

<html:html locale="true">
  <head>
    <title><bean:message key="hello.jsp.title"/></title>
    <html:base/>
  </head>


  <body bgcolor="white"><p>

    <h2><bean:message key="hello.jsp.page.heading"/></h2><p>

   <html:errors/><p> 

    <logic:present name="personbean" scope="request">
       <h2>
         <bean:message key="hello.jsp.page.hello"/>
         <bean:write name="personbean" property="trueName" /><bean:message key="hello.jsp.page.student"/><br>
         <bean:write name="personbean" property="changePassword" /><p>        
       </h2>
    </logic:present>    

    <jsp:useBean id="pb2" scope="session" class="hello.PersonBean" />
    <%! HttpSession session = null;        
        hello.PersonBean _pb2 = null;       
    %>
    <% session = request.getSession(true);        
       _pb2 = (hello.PersonBean)session.getAttribute(hello.Constants.PERSON_KEY);      
       
       session.setAttribute(hello.Constants.PERSON_KEY, _pb2);       
       request.setAttribute(hello.Constants.PERSON_KEY, _pb2);     
    %>
<!--
    <html:form action="/Random.do?first=true" >
      <bean:message key="hello.jsp.prompt.quizTime"/><html:text property="times" size="16" maxlength="16" value="1st" /><br>
      <bean:message key="hello.jsp.prompt.person"/><br>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="questionUsedUp" value="<%= _pb2.getQuestionUsedUp()%>"/>

      <html:submit property="submit" value="Submit to normal random quiz"/>
      <html:reset/>

    </html:form><br>
   <HR>
-->
    <html:form action="/SmartRandom.do?first=true" >
      <bean:message key="hello.jsp.prompt.quizTime"/><html:text property="times" size="16" maxlength="16" value="FinalExam2" /><br>
      <bean:message key="hello.jsp.prompt.source"/><html:text property="source" size="16" maxlength="16" /><br>
      <bean:message key="hello.jsp.prompt.smart.person"/><br>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="lastCorrect" value="false"/>
      <html:hidden property="answeredProblems" value="0"/>
      <html:hidden property="correctAnswers" value="0"/>
      <html:hidden property="totalScore" value="0"/>
      <html:hidden property="correctAnswers_low" value="0"/>
      <html:hidden property="answeredProblems_low" value="0"/>
      <html:hidden property="correctAnswers_middle" value="0"/>
      <html:hidden property="answeredProblems_middle" value="0"/>
      <html:hidden property="correctAnswers_high" value="0"/>
      <html:hidden property="answeredProblems_high" value="0"/>
      <html:hidden property="lastType" value="L"/>
      <html:hidden property="thisType" value="L"/>
      <html:hidden property="continueRight" value="0"/>
      <html:hidden property="continueWrong" value="0"/>
      <html:hidden property="neverHigh" value="true"/>
      <html:hidden property="planStatus" value="not finished"/>

      <html:submit property="submit" value="Submit to final exam"/>
      <html:reset/>

    </html:form><br>

<HR>
    <html:form action="/UpdateScore.do?first=true" >
      <bean:message key="hello.jsp.prompt.quizTime"/><html:text property="times" size="16" maxlength="16" value="1st" /><br>
      <bean:message key="hello.jsp.prompt.person"/><br>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>      

      <html:submit property="submit" value="Submit to Update Those Students On Leave With Permission"/>
      <html:reset/>

    </html:form><br>
    
<HR>
   
    <h2><html:text property="changePassword" value="<%= _pb2.getChangePassword() %>" size="44" maxlength="100" /></h2><br>
    <h2><bean:message key="hello.jsp.page.changePassword"/></h2><p>           
       
    <html:form action="/Password.do" >
      
      <bean:message key="hello.jsp.prompt.oldPassword"/><html:password property="oldPassword" size="16" maxlength="16"/>
      <br>  
      <bean:message key="hello.jsp.prompt.newPassword"/><html:password property="newPassword" size="16" maxlength="16"/>
      <br>  
      <bean:message key="hello.jsp.prompt.confirmNewPassword"/>
      <html:password property="confirmNewPassword" size="16" maxlength="16"/>
      <br>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>

      <html:submit property="submit" value="Submit"/>
      <html:reset/>

    </html:form><br>

    <html:img page="/struts-power.gif" alt="Powered by Struts"/>

  </body>
</html:html>

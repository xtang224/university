<%@ include file="taglibs.jsp" %>

<HTML>
    <HEAD>
	<TITLE> Random Test End Page </TITLE> 			
    </HEAD>

<BODY>
    <CENTER><H1>Random Test End Page</H1><P>
    <CENTER><P>

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

    <CENTER><H3>Your score information is listed below:</H3></CENTER>

    <CENTER><TABLE BGCOLOR="#D8D8D8" BORDER=2>
    <tr ALIGN=center>
       <th>User Name</th>
       <th>PlayerId</th>
       <th>True Name</th>
       <th>Total Score</th>
       <th>Problems Answered</th>
       <th>Correct Answers</th>
       <th>Low Type Problems Answered</th>
       <th>Low Type Correct Answers</th>
       <th>Middle Type Problems Answered</th>
       <th>Middle Type Correct Answers</th>
       <th>High Type Problems Answered</th>
       <th>High Type Correct Answers</th>
    </tr>
    <TR ALIGN=center><FONT COLOR="#FF5555">
        <TD><%= _pb2.getUserName() %></TD>
        <TD><%= _pb2.getPlayerId() %></TD>
        <TD><%= _pb2.getTrueName() %></TD>
        <TD><%= _pb2.getTotalScore() %></TD>
        <TD><%= _pb2.getAnsweredProblems() %></TD>
        <TD><%= _pb2.getCorrectAnswers() %></TD>
        <TD><%= _pb2.getAnsweredProblems_low() %></TD>
        <TD><%= _pb2.getCorrectAnswers_low() %></TD>
        <TD><%= _pb2.getAnsweredProblems_middle() %></TD>
        <TD><%= _pb2.getCorrectAnswers_middle() %></TD>
        <TD><%= _pb2.getAnsweredProblems_high() %></TD>
        <TD><%= _pb2.getCorrectAnswers_high() %></TD>
    </FONT></TR>
    </TABLE>
    </CENTER>    

    <HR WIDTH=60%><BR>
    <!--
    <logic:present name="personbean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.hello"/>
         <bean:write name="personbean" property="trueName" /><bean:message key="hello.jsp.page.student"/><p>  
         <bean:message key="hello.jsp.page.lastCorrect"/>
         <bean:write name="personbean" property="lastCorrect" /><p>      
        <bean:message key="hello.jsp.page.answeredProblems"/>
         <bean:write name="personbean" property="answeredProblems" /><p>         
         <bean:message key="hello.jsp.page.correctAnswers"/>
         <bean:write name="personbean" property="correctAnswers" /><p>     
         <bean:message key="hello.jsp.page.answeredProblems_low"/>
         <bean:write name="personbean" property="answeredProblems_low" /><br> 
         <bean:message key="hello.jsp.page.correctAnswers_low"/>
         <bean:write name="personbean" property="correctAnswers_low" /><br>   
         <bean:message key="hello.jsp.page.answeredProblems_middle"/>
         <bean:write name="personbean" property="answeredProblems_middle" /><br> 
         <bean:message key="hello.jsp.page.correctAnswers_middle"/>
         <bean:write name="personbean" property="correctAnswers_middle" /><br>            
         <bean:message key="hello.jsp.page.answeredProblems_high"/>
         <bean:write name="personbean" property="answeredProblems_high" /><br> 
         <bean:message key="hello.jsp.page.correctAnswers_high"/>
         <bean:write name="personbean" property="correctAnswers_high" /><br>      
         <bean:message key="hello.jsp.page.totalScore"/>
         <bean:write name="personbean" property="totalScore" /><br>            
       </h2>
    </logic:present>
    -->
    <HR WIDTH=70%><BR>   
   
    <CENTER><H3>If you want to view the scores of the entire class, please type "yes" in the following box, otherwise type "no" in the following box, and then click the "submit" button</H3><P>
    <html:form action="/Score.do?first=true" >
      <bean:message key="hello.jsp.prompt.vote"/><html:text property="vote" size="16" maxlength="16"  /><br>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="playerId" value="<%= _pb2.getPlayerId()%>"/>
      <html:submit property="submit" value="Submit"/>
      <html:reset/>
    </html:form><br>   
  
</BODY>
</HTML>

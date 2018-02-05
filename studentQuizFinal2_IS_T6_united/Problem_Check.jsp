<%@ include file="taglibs.jsp" %>

<html:html locale="true">
  <head>
    <title><bean:message key="hello.jsp.page.solution"/></title>
    <html:base/>
  </head>
  <body bgcolor="white"><p>   

   <html:errors/><p> 
<!--
    <logic:present name="problembean" scope="request">
       <h2>
         <bean:message key="hello.jsp.page.solution"/>
         <bean:write name="problembean" property="correctChoice" /><p>
         <bean:message key="hello.jsp.page.explanation"/>
         <bean:write name="problembean" property="solution" /><p>         
       </h2>
    </logic:present>
-->
    <app:validateSession/>
    
    <logic:present name="personbean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.hello"/>
         <bean:write name="personbean" property="trueName" /><bean:message key="hello.jsp.page.student"/>ͬѧ<p>        
         <bean:message key="hello.jsp.page.answeredProblems"/>
         <bean:write name="personbean" property="answeredProblems" /><p>
         <bean:message key="hello.jsp.page.secondsRemaining"/>
         <bean:write name="personbean" property="remainSeconds" /><p>
<!--     <bean:message key="hello.jsp.page.correctAnswers"/>
         <bean:write name="personbean" property="correctAnswers" /><p>      -->
       </h2>
    </logic:present>

    <jsp:useBean id="pb" scope="session" class="hello.ProblemBean" />
    <jsp:useBean id="pb2" scope="session" class="hello.PersonBean" />
    <%! HttpSession session = null; 
        hello.ProblemBean _pb = null;
        hello.PersonBean _pb2 = null;
        String _hs = null;
        String _hs_tf = null;
        int answeredProblems = 0;
        int remainSeconds = 0;
        String questionUsedUp = null;
    %>
    <% session = request.getSession(true);     
       _pb = (hello.ProblemBean)request.getAttribute(hello.Constants.PROBLEM_KEY);
       _pb2 = (hello.PersonBean)request.getAttribute(hello.Constants.PERSON_KEY);
       _hs = (String)request.getAttribute(Constants.HASHSET_KEY);
       _hs_tf = (String)request.getAttribute(Constants.HASHSET_TF_KEY);

       questionUsedUp = _pb2.getQuestionUsedUp();

       session.setAttribute(hello.Constants.PROBLEM_KEY, _pb);
       session.setAttribute(hello.Constants.PERSON_KEY, _pb2);
       session.setAttribute(hello.Constants.HASHSET_KEY, _hs);
       session.setAttribute(hello.Constants.HASHSET_KEY, _hs_tf);
       
    %>
    <% remainSeconds = _pb2.getRemainSeconds();
       if (remainSeconds > 0 && questionUsedUp.equals("false")){ 
    %>
    <html:form action="/Random.do?first=false" >

      <bean:message key="hello.jsp.prompt.continue"/><br>
      <html:hidden property="answeredProblems" value="<%= (new Integer(_pb2.getAnsweredProblems()).toString())%>"/><br>
      <html:hidden property="correctAnswers" value="<%= (new Integer(_pb2.getCorrectAnswers()).toString())%>"/>
      <html:hidden property="answeredHashSet" value="<%= _hs%>"/>
      <html:hidden property="answeredHashSet_tf" value="<%= _hs_tf%>"/>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/><br>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/><br>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/><br>
      <html:hidden property="times" value="<%= _pb2.getTimes()%>"/>
      <html:hidden property="questionUsedUp" value="<%= questionUsedUp %>"/>      

      <html:submit property="submit" value="Submit"/>
      <html:reset/>

    </html:form><br>    
   
    <% } else {        
    %>
       <center><h2>Please click the following submit button to exit</h2></center>
        <center>
      <html:form action="/Quit.do" >   
  
      <html:hidden property="answeredHashSet" value="<%= _hs%>"/>
      <html:hidden property="answeredHashSet_tf" value="<%= _hs_tf%>"/>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="answeredProblems" value="<%= (new Integer(_pb2.getAnsweredProblems()).toString())%>"/>
      <html:hidden property="score" value="<%= new Integer(_pb2.getCorrectAnswers()).toString()%>"/>
      <html:hidden property="times" value="<%= _pb2.getTimes()%>"/>
  
      <html:submit property="submit" value="Submit"/>
      <html:reset/>

    </html:form><br>
    </center>

    <% } %>

    <html:img page="/struts-power.gif" alt="Powered by Struts"/>

  </body>
</html:html>

<%@ include file="taglibs.jsp" %>

<html:html locale="true">
  <head>   
    <html:base/>
  </head>
  <body bgcolor="white"><p>    

   <html:errors/><p> 

    <logic:present name="problembean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.problem"/>
         <bean:write name="problembean" property="id" /><p>
         <bean:write name="problembean" property="statement" /><p>
         <bean:write name="problembean" property="choiceA" /><p>
         <bean:write name="problembean" property="choiceB" /><p>
         <bean:write name="problembean" property="choiceC" /><p>
         <bean:write name="problembean" property="choiceD" /><p>
       </h2>
    </logic:present>  

    <jsp:useBean id="pb" scope="session" class="hello.ProblemBean" />
    <jsp:useBean id="pb2" scope="session" class="hello.PersonBean" />
    <%! HttpSession session = null; 
        hello.ProblemBean _pb = null;
        hello.PersonBean _pb2 = null;
        String _hs = null;
        String _hs_tf = null;        
    %>
    <% session = request.getSession(true);     
       _pb = (hello.ProblemBean)request.getAttribute(hello.Constants.PROBLEM_KEY);
       _pb2 = (hello.PersonBean)request.getAttribute(hello.Constants.PERSON_KEY);
       _hs = (String)request.getAttribute(hello.Constants.HASHSET_KEY);
       _hs_tf = (String)request.getAttribute(hello.Constants.HASHSET_TF_KEY);
    
       session.setAttribute(hello.Constants.PROBLEM_KEY, _pb);
       session.setAttribute(hello.Constants.PERSON_KEY, _pb2);
       request.setAttribute(hello.Constants.PROBLEM_KEY, _pb);
       request.setAttribute(hello.Constants.PERSON_KEY, _pb2);

       session.setAttribute(hello.Constants.HASHSET_KEY, _hs);
       session.setAttribute(hello.Constants.HASHSET_TF_KEY, _hs_tf);
       request.setAttribute(hello.Constants.HASHSET_KEY, _hs);
       request.setAttribute(hello.Constants.HASHSET_TF_KEY, _hs_tf);       
    %>

    <html:form action="/RandomCheck.do" >

      <bean:message key="hello.jsp.prompt.problem"/>
      <html:text property="inputAnswer" size="16" maxlength="16"/><br>
      <html:hidden property="correctAnswer" value="<%= _pb.getCorrectChoice()%>"/>
      <html:hidden property="solution" value="<%= _pb.getSolution()%>"/>
      <html:hidden property="answeredProblems" value="<%= (new Integer(_pb2.getAnsweredProblems()).toString())%>"/>
      <html:hidden property="correctAnswers" value="<%= (new Integer(_pb2.getCorrectAnswers()).toString())%>"/>
      <html:hidden property="answeredHashSet" value="<%= _hs%>"/>
      <html:hidden property="answeredHashSet_tf" value="<%= _hs_tf%>"/>        
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="times" value="<%= _pb2.getTimes()%>"/>
      <html:hidden property="randomNumber" value="<%= _pb2.getRandomNumber()%>"/>
      <html:hidden property="questionUsedUp" value="<%= _pb2.getQuestionUsedUp()%>"/>

      <html:submit property="submit" value="Submit"/>
      <html:reset/>

    </html:form><br>
    
    <html:img page="/struts-power.gif" alt="Powered by Struts"/>
   
  </body>
</html:html>

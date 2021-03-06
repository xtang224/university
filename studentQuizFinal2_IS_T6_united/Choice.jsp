<%@ include file="taglibs.jsp" %>

<html:html locale="true">
  <head>   
    <html:base/>
  </head>
  <body bgcolor="white"><p>    

   <html:errors/><p> 

    <logic:present name="choicebean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.problem"/>
         <bean:write name="choicebean" property="id" /><p>
         <bean:write name="choicebean" property="statement" /><p>         
       </h2>
    </logic:present>  

    <jsp:useBean id="cb" scope="session" class="hello.ChoiceBean" />
    <jsp:useBean id="pb2" scope="session" class="hello.PersonBean" />
    <%! HttpSession session = null; 
        hello.ChoiceBean _cb = null;
        hello.PersonBean _pb2 = null;
        String _hs = null;
        String _hs_tf = null; 
    %>
    <% session = request.getSession(true);     
       _cb = (hello.ChoiceBean)request.getAttribute(hello.Constants.CHOICE_KEY);
       _pb2 = (hello.PersonBean)request.getAttribute(hello.Constants.PERSON_KEY);
       _hs = (String)request.getAttribute(hello.Constants.HASHSET_KEY);
       _hs_tf = (String)request.getAttribute(hello.Constants.HASHSET_TF_KEY);
    
       /*
       session.setAttribute(hello.Constants.CHOICE_KEY, _cb);
       session.setAttribute(hello.Constants.PERSON_KEY, _pb2);
       request.setAttribute(hello.Constants.CHOICE_KEY, _cb);
       request.setAttribute(hello.Constants.PERSON_KEY, _pb2);

       session.setAttribute(hello.Constants.HASHSET_KEY, _hs);
       session.setAttribute(hello.Constants.HASHSET_TF_KEY, _hs_tf);
       session.setAttribute(hello.Constants.HASHSET_M_KEY, _mhs);
       session.setAttribute(hello.Constants.HASHSET_FB_KEY, _hs_fb);
       */
    %>

    <html:form action="/RandomCheck.do" >

      <bean:message key="hello.jsp.prompt.choice"/>
      <html:text property="inputAnswer" size="16" maxlength="16"/><br>
      <html:hidden property="correctAnswer" value="<%= _cb.getChoice()%>"/>
      <html:hidden property="solution" value="<%= _cb.getSolution()%>"/>
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

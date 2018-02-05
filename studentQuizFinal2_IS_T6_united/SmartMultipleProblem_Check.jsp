<%@ include file="taglibs.jsp" %>

<html:html locale="true">
  <head>
    <title><bean:message key="hello.jsp.page.solution"/></title>
    <html:base/>
  </head>
  <body bgcolor="white"><p>   

   <html:errors/><p> 

    <logic:present name="multipleproblembean" scope="request">
       <h2>
         <bean:message key="hello.jsp.page.solution"/>
         <bean:write name="multipleproblembean" property="correctChoice" /><p>
         <bean:message key="hello.jsp.page.explanation"/>
         <bean:write name="multipleproblembean" property="solution" /><p>         
       </h2>
    </logic:present>

    <app:validateSession/>
    
    <logic:present name="personbean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.hello"/>
         <bean:write name="personbean" property="trueName" /><bean:message key="hello.jsp.page.student"/><p>  
         <bean:message key="hello.jsp.page.inputAnswer"/>
         <bean:write name="personbean" property="inputAnswer" /><p>    
         <bean:message key="hello.jsp.page.answeredProblems"/>
         <bean:write name="personbean" property="answeredProblems" /><p>    
         <bean:message key="hello.jsp.page.secondsRemaining"/>
         <bean:write name="personbean" property="remainSeconds" /><p>     
  <!--   <bean:message key="hello.jsp.page.lastCorrect"/>
         <bean:write name="personbean" property="lastCorrect" /><p>          
         <bean:message key="hello.jsp.page.correctAnswers"/>
         <bean:write name="personbean" property="correctAnswers" /><p>     
         <bean:message key="hello.jsp.page.answeredProblems_low"/>
         <bean:write name="personbean" property="answeredProblems_low" /><p> 
         <bean:message key="hello.jsp.page.correctAnswers_low"/>
         <bean:write name="personbean" property="correctAnswers_low" /><p>   
         <bean:message key="hello.jsp.page.answeredProblems_middle"/>
         <bean:write name="personbean" property="answeredProblems_middle" /><p> 
         <bean:message key="hello.jsp.page.correctAnswers_middle"/>
         <bean:write name="personbean" property="correctAnswers_middle" /><p>            
         <bean:message key="hello.jsp.page.answeredProblems_high"/>
         <bean:write name="personbean" property="answeredProblems_high" /><p> 
         <bean:message key="hello.jsp.page.correctAnswers_high"/>
         <bean:write name="personbean" property="correctAnswers_high" /><p>      
         <bean:message key="hello.jsp.page.totalScore"/>
         <bean:write name="personbean" property="totalScore" /><p>        -->    
       </h2>
    </logic:present>

    
    <%! HttpSession session = null; 
        hello.SmartMultipleProblemBean _mpb = null;
        hello.PersonBean _pb2 = null;
        String _hs = null;
        String _hs_tf = null;
        String _mhs = null;
        String _hs_fb = null;
        String _hs_fdb = null;
        String _hs_tm = null;
        int answeredProblems = 0;
        int remainSeconds = 300;
        String lastCorrect = null;
    %>
    <% session = request.getSession(true);     
       _mpb = (hello.SmartMultipleProblemBean)request.getAttribute(hello.Constants.MULTIPLEPROBLEM_KEY);
       _pb2 = (hello.PersonBean)request.getAttribute(hello.Constants.PERSON_KEY);
       _hs = (String)request.getAttribute(Constants.HASHSET_KEY);
       _hs_tf = (String)request.getAttribute(Constants.HASHSET_TF_KEY);
       _mhs = (String)request.getAttribute(hello.Constants.HASHSET_M_KEY);
       _hs_fb = (String)request.getAttribute(Constants.HASHSET_FB_KEY);
       _hs_fdb = (String)request.getAttribute(Constants.HASHSET_FDB_KEY);
       _hs_tm = (String)request.getAttribute(Constants.HASHSET_TM_KEY);
    
       session.setAttribute(hello.Constants.MULTIPLEPROBLEM_KEY, _mpb);
       session.setAttribute(hello.Constants.PERSON_KEY, _pb2);
       session.setAttribute(hello.Constants.HASHSET_KEY, _hs);
       session.setAttribute(hello.Constants.HASHSET_TF_KEY, _hs_tf);
       session.setAttribute(hello.Constants.HASHSET_M_KEY, _mhs);
       session.setAttribute(hello.Constants.HASHSET_FB_KEY, _hs_fb);
       session.setAttribute(hello.Constants.HASHSET_FDB_KEY, _hs_fdb);
       session.setAttribute(hello.Constants.HASHSET_TM_KEY, _hs_tm);

       if (_pb2.getLastCorrect()){
          lastCorrect = "true";
       }else{
          lastCorrect = "false";
       }
       
    %>
    <% remainSeconds = _pb2.getRemainSeconds();
       if (remainSeconds > 0 && _pb2.getPlanStatus().equals("not finished")){ 
    %>
    <html:form action="/SmartRandom2.do?first=false" >

      <bean:message key="hello.jsp.prompt.updateScore"/><html:text property="updateScore" size="16" maxlength="16"/><br>
      <html:hidden property="answeredProblems" value="<%= (new Integer(_pb2.getAnsweredProblems()).toString())%>"/><br>
      <html:hidden property="correctAnswers" value="<%= (new Integer(_pb2.getCorrectAnswers()).toString())%>"/>
      <html:hidden property="totalScore" value="<%= (new Double(_pb2.getTotalScore()).toString())%>"/>
      <html:hidden property="correctAnswers_low" value="<%= (new Integer(_pb2.getCorrectAnswers_low()).toString())%>"/>
      <html:hidden property="answeredProblems_low" value="<%= (new Integer(_pb2.getAnsweredProblems_low()).toString())%>"/>
 <html:hidden property="correctAnswers_middle" value="<%= (new Integer(_pb2.getCorrectAnswers_middle()).toString())%>"/>
 <html:hidden property="answeredProblems_middle" value="<%= (new Integer(_pb2.getAnsweredProblems_middle()).toString())%>"/>
     <html:hidden property="correctAnswers_high" value="<%= (new Integer(_pb2.getCorrectAnswers_high()).toString())%>"/>
     <html:hidden property="answeredProblems_high" value="<%= (new Integer(_pb2.getAnsweredProblems_high()).toString())%>"/>
      <html:hidden property="answeredHashSet" value="<%= _hs%>"/>
      <html:hidden property="answeredHashSet_tf" value="<%= _hs_tf%>"/>
      <html:hidden property="answered_M_HashSet" value="<%= _mhs%>"/>
      <html:hidden property="answeredHashSet_fb" value="<%= _hs_fb%>"/>
      <html:hidden property="answeredHashSet_fdb" value="<%= _hs_fdb%>"/>
      <html:hidden property="answeredHashSet_tm" value="<%= _hs_tm%>"/>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="lastCorrect" value="<%= lastCorrect%>"/>
      <html:hidden property="lastType" value="<%= _pb2.getLastType()%>"/>
      <html:hidden property="thisType" value="<%= _pb2.getThisType()%>"/>
      <html:hidden property="continueRight" value="<%= (new Integer(_pb2.getContinueRight()).toString())%>"/>
      <html:hidden property="continueWrong" value="<%= (new Integer(_pb2.getContinueWrong()).toString())%>"/>
      <html:hidden property="neverHigh" value="<%= (new Boolean(_pb2.getNeverHigh()).toString())%>"/>
      <html:hidden property="times" value="<%= _pb2.getTimes()%>"/>
      <html:hidden property="planStatus" value="<%= _pb2.getPlanStatus()%>"/>

      <html:submit property="submit" value="Submit"/>
      <html:reset/>

    </html:form><br><br>    
 
    <% } else {        
    %>
       <center><h2>Either your time is used up or you have answered all the problems. Please click the following submit button to exit.</h2></center>
        <center>
   <html:form action="/SmartQuit.do" >     
      <html:hidden property="answeredHashSet" value="<%= _hs%>"/>
      <html:hidden property="answeredHashSet_tf" value="<%= _hs_tf%>"/>
      <html:hidden property="answered_M_HashSet" value="<%= _mhs%>"/>
      <html:hidden property="answeredHashSet_fb" value="<%= _hs_fb%>"/>
      <html:hidden property="answeredHashSet_fdb" value="<%= _hs_fdb%>"/>
      <html:hidden property="answeredHashSet_tm" value="<%= _hs_tm%>"/>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="answeredProblems" value="<%= (new Integer(_pb2.getAnsweredProblems()).toString())%>"/><br>
      <html:hidden property="correctAnswers" value="<%= (new Integer(_pb2.getCorrectAnswers()).toString())%>"/>
      <html:hidden property="totalScore" value="<%= (new Double(_pb2.getTotalScore()).toString())%>"/>
      <html:hidden property="correctAnswers_low" value="<%= (new Integer(_pb2.getCorrectAnswers_low()).toString())%>"/>
      <html:hidden property="answeredProblems_low" value="<%= (new Integer(_pb2.getAnsweredProblems_low()).toString())%>"/>
 <html:hidden property="correctAnswers_middle" value="<%= (new Integer(_pb2.getCorrectAnswers_middle()).toString())%>"/>
 <html:hidden property="answeredProblems_middle" value="<%= (new Integer(_pb2.getAnsweredProblems_middle()).toString())%>"/>
     <html:hidden property="correctAnswers_high" value="<%= (new Integer(_pb2.getCorrectAnswers_high()).toString())%>"/>
     <html:hidden property="answeredProblems_high" value="<%= (new Integer(_pb2.getAnsweredProblems_high()).toString())%>"/>
      <html:hidden property="times" value="<%= _pb2.getTimes()%>"/>
      <html:hidden property="planStatus" value="<%= _pb2.getPlanStatus()%>"/>
  
      <html:submit property="submit" value="Submit"/>
      <html:reset/>

    </html:form><br>
    </center>

    <% } %>

    <html:img page="/struts-power.gif" alt="Powered by Struts"/>

  </body>
</html:html>

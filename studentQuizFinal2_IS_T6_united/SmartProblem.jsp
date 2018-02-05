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
         <bean:write name="problembean" property="type" /><p> 
         <bean:write name="problembean" property="statement" /><p>
         <bean:write name="problembean" property="choiceA" /><p>
         <bean:write name="problembean" property="choiceB" /><p>
         <bean:write name="problembean" property="choiceC" /><p>
         <bean:write name="problembean" property="choiceD" /><p>
       </h2>
    </logic:present> 

    <logic:present name="personbean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.selectedStudent"/><bean:write name="personbean" property="userName" />
         <bean:message key="hello.jsp.page.doubleColon"/><bean:write name="personbean" property="trueName" /><p>          
       </h2>
    </logic:present>   
  
    <%! HttpSession session = null; 
        hello.SmartProblemBean _pb = null;
        hello.PersonBean _pb2 = null;
        String _hs = null;
        String _hs_tf = null;
        String _mhs = null;
        String _hs_fb = null;
        String _hs_fdb = null;   
        String _hs_tm = null; 
        String _hs_fqb = null;
        int remainSeconds = 300;
    %>
    <% session = request.getSession(true);     
       _pb = (hello.SmartProblemBean)request.getAttribute(hello.Constants.PROBLEM_KEY);
       _pb2 = (hello.PersonBean)request.getAttribute(hello.Constants.PERSON_KEY);
       _hs = (String)request.getAttribute(hello.Constants.HASHSET_KEY);
       _hs_tf = (String)request.getAttribute(hello.Constants.HASHSET_TF_KEY);
       _mhs = (String)request.getAttribute(hello.Constants.HASHSET_M_KEY);
       _hs_fb = (String)request.getAttribute(hello.Constants.HASHSET_FB_KEY);
       _hs_fdb = (String)request.getAttribute(hello.Constants.HASHSET_FDB_KEY);
       _hs_tm = (String)request.getAttribute(Constants.HASHSET_TM_KEY);
       _hs_fqb = (String)request.getAttribute(hello.Constants.HASHSET_FQB_KEY); 
    %>

    <% remainSeconds = _pb2.getRemainSeconds();
       if (remainSeconds > 0 && _pb2.getPlanStatus().equals("not finished")){ 
    %>

    <html:form action="/SmartRandomCheck.do" >

      <bean:message key="hello.jsp.prompt.problem"/>
      <html:text property="inputAnswer" size="16" maxlength="16"/><br>
      <html:hidden property="correctAnswer" value="<%= _pb.getCorrectChoice()%>"/>
      <html:hidden property="solution" value="<%= _pb.getSolution()%>"/>
      <html:hidden property="source" value="<%= _pb.getSource()%>"/>
      <html:hidden property="answeredProblems" value="<%= (new Integer(_pb2.getAnsweredProblems()).toString())%>"/>
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
      <html:hidden property="answeredHashSet_fqb" value="<%= _hs_fqb%>"/>
      <html:hidden property="answeredHashSet_tm" value="<%= _hs_tm%>"/>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="lastType" value="<%= _pb2.getLastType()%>"/>
      <html:hidden property="thisType" value="<%= _pb2.getThisType()%>"/>
      <html:hidden property="continueRight" value="<%= (new Integer(_pb2.getContinueRight()).toString())%>"/>
      <html:hidden property="continueWrong" value="<%= (new Integer(_pb2.getContinueWrong()).toString())%>"/>
      <html:hidden property="neverHigh" value="<%= (new Boolean(_pb2.getNeverHigh()).toString())%>"/>
      <html:hidden property="times" value="<%= _pb2.getTimes()%>"/>
      <html:hidden property="randomNumber" value="<%= _pb2.getRandomNumber()%>"/>
      <html:hidden property="id" value="<%= new Integer(_pb2.getCurrentProblemId()).toString() %>"/>
      <html:hidden property="planStatus" value="<%= _pb2.getPlanStatus()%>"/>

      <html:submit property="submit" value="Submit"/>
      <html:reset/>

    </html:form><br>

    <h2>If you want to skip this problem and do it later, then you can click the skip button, the skipped problem will come back later.<br></h2>
    <html:form action="/SmartRandomCheck2.do" >     
      
      <html:hidden property="correctAnswer" value="<%= _pb.getCorrectChoice()%>"/>
      <html:hidden property="solution" value="<%= _pb.getSolution()%>"/>
      <html:hidden property="source" value="<%= _pb.getSource()%>"/>
      <html:hidden property="answeredProblems" value="<%= (new Integer(_pb2.getAnsweredProblems()).toString())%>"/>
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
      <html:hidden property="answeredHashSet_fqb" value="<%= _hs_fqb%>"/>
      <html:hidden property="answeredHashSet_tm" value="<%= _hs_tm%>"/>
      <html:hidden property="userName" value="<%= _pb2.getUserName()%>"/>
      <html:hidden property="passWord" value="<%= _pb2.getPassWord()%>"/>
      <html:hidden property="trueName" value="<%= _pb2.getTrueName()%>"/>
      <html:hidden property="lastType" value="<%= _pb2.getLastType()%>"/>
      <html:hidden property="thisType" value="<%= _pb2.getThisType()%>"/>
      <html:hidden property="continueRight" value="<%= (new Integer(_pb2.getContinueRight()).toString())%>"/>
      <html:hidden property="continueWrong" value="<%= (new Integer(_pb2.getContinueWrong()).toString())%>"/>
      <html:hidden property="neverHigh" value="<%= (new Boolean(_pb2.getNeverHigh()).toString())%>"/>
      <html:hidden property="times" value="<%= _pb2.getTimes()%>"/>
      <html:hidden property="randomNumber" value="<%= _pb2.getRandomNumber()%>"/>
      <html:hidden property="id" value="<%= new Integer(_pb2.getCurrentProblemId()).toString() %>"/>
      <html:hidden property="planStatus" value="<%= _pb2.getPlanStatus()%>"/>

      <html:submit property="submit" value="Skip this problem"/>
      <html:reset/>

    </html:form><br>

    <% } else { %>

      <center><h2>Either your time is used up or you have answered all the problems. Please click the following submit button to exit</h2></center>
        <center>
      <html:form action="/SmartQuit.do" >     
      <html:hidden property="answeredHashSet" value="<%= _hs%>"/>
      <html:hidden property="answeredHashSet_tf" value="<%= _hs_tf%>"/>
      <html:hidden property="answered_M_HashSet" value="<%= _mhs%>"/>
      <html:hidden property="answeredHashSet_fb" value="<%= _hs_fb%>"/>
      <html:hidden property="answeredHashSet_fdb" value="<%= _hs_fdb%>"/>
      <html:hidden property="answeredHashSet_fqb" value="<%= _hs_fqb%>"/>
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

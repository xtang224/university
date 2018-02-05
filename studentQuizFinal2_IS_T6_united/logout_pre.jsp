<%@ include file="taglibs.jsp" %>

<html:html locale="true">
  <head>
    <title><bean:message key="hello.jsp.title.quit"/></title>
    <html:base/>
  </head>
  <body bgcolor="white"><p>

    <h2><bean:message key="hello.jsp.page.heading.quit"/></h2><p>

   <html:errors/><p> 

    <logic:present name="personbean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.hello"/>
         <bean:write name="personbean" property="trueName" /><bean:message key="hello.jsp.page.student"/>ͬѧ<p>        
       </h2>
    </logic:present>    

    <center><h2><a href=logout.jsp?>If you DO NOT want to register this test, please click here to exit</a></h2></center><P>
    <center><h2>If you DO want to register this test, please click the button below to exit</h2></center>

    <%! HttpSession session = null; 
        hello.ProblemBean _pb = null;
        hello.PersonBean _pb2 = null;
        String _hs = null;
        String _hs_tf = null;
    %>
    <% session = request.getSession(true);     
       _pb = (hello.ProblemBean)session.getAttribute(hello.Constants.PROBLEM_KEY);
       _pb2 = (hello.PersonBean)session.getAttribute(hello.Constants.PERSON_KEY);
       _hs = (String)session.getAttribute(hello.Constants.HASHSET_KEY);
       _hs_tf = (String)session.getAttribute(hello.Constants.HASHSET_TF_KEY);

       System.out.println("In logout_pre.jsp, _hs = " + _hs);
       System.out.println("In logout_pre.jsp, _hs_tf = " + _hs_tf);
    
       session.setAttribute(hello.Constants.PROBLEM_KEY, _pb);
       session.setAttribute(hello.Constants.PERSON_KEY, _pb2);      
       session.setAttribute(hello.Constants.HASHSET_KEY, _hs);
       session.setAttribute(hello.Constants.HASHSET_KEY, _hs_tf);
    %>

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

    <html:img page="/struts-power.gif" alt="Powered by Struts"/>

  </body>
</html:html>

<%@ include file="taglibs.jsp" %>

<html:html locale="true">
  <head>   
    <html:base/>
  </head>
  <body bgcolor="white"><p>    

   <html:errors/><p> 

   
    <%! HttpSession session = null; 
        hello.TermMatchBean _tmb = null;
        hello.PersonBean _pb2 = null;
        String _hs = null;
        String _hs_tf = null;
        String _mhs = null;
        String _hs_fb = null;
        String _hs_fdb = null;
        String _hs_tm = null;
        String answeredProblems = null;
        String correctAnswers = null;
        String totalScore = null;
        String correctAnswers_low = null;
        String answeredProblems_low = null;
        String correctAnswers_middle = null;
        String answeredProblems_middle = null;
        String correctAnswers_high = null;
        String answeredProblems_high = null;
        String userName  = null;
        String passWord = null;
        String trueName = null;
        String lastType = null;
        String thisType = null;
        String times = null;
        String randomNumber = null;
        String id = null;
        String planStatus = null;
        String lastCorrect = null;

        int intAnsweredProblems = 0;
        int intCorrectAnswers = 0;
        double dblTotalScore = 0;
        int intAnsweredProblems_middle = 0;
        int intCorrectAnswers_middle = 0;

        int remainSeconds = 300;       
        java.sql.Connection myConnection = null;
        PreparedStatement stmt = null;        
        ResultSet rs = null;

        String term1 = null;
        String term2 = null;
        int intId = 0;
    %>
    <% session = request.getSession(true);     
       
       //_fdbb = (hello.FillDoubleBlankBean)request.getAttribute(hello.Constants.FILLDOUBLEBLANK_KEY);
       //_pb2 = (hello.PersonBean)request.getAttribute(hello.Constants.PERSON_KEY);
       _hs = (String)request.getParameter("answeredHashSet");
       _hs_tf = (String)request.getParameter("answeredHashSet_tf");
       _mhs = (String)request.getParameter("answered_M_HashSet");
       _hs_fb = (String)request.getParameter("answeredHashSet_fb");
       _hs_fdb = (String)request.getParameter("answeredHashSet_fdb");
       _hs_tm = (String)request.getParameter("answeredHashSet_tm");
       answeredProblems = (String)request.getParameter("answeredProblems");
       correctAnswers = (String)request.getParameter("correctAnswers");
       totalScore = (String)request.getParameter("totalScore");
       correctAnswers_low = (String)request.getParameter("correctAnswers_low");
       answeredProblems_low = (String)request.getParameter("answeredProblems_low");
       correctAnswers_middle = (String)request.getParameter("correctAnswers_middle");
       answeredProblems_middle = (String)request.getParameter("answeredProblems_middle");
       correctAnswers_high = (String)request.getParameter("correctAnswers_high");
       answeredProblems_high = (String)request.getParameter("answeredProblems_high");
       userName = (String)request.getParameter("userName");
       passWord = (String)request.getParameter("passWord");
       trueName = (String)request.getParameter("trueName");
       lastType = (String)request.getParameter("lastType");
       thisType = (String)request.getParameter("thisType");
       times = (String)request.getParameter("times");
       randomNumber = (String)request.getParameter("randomNumber");
       id = (String)request.getParameter("id");
       planStatus = (String)request.getParameter("planStatus");

       System.out.println("inside TermMatch_Check.jsp, _hs = " + _hs);
       System.out.println("inside TermMatch_Check.jsp, _hs_tm = " + _hs_tm);
       System.out.println("inside TermMatch_Check.jsp, userName = " + userName);

       intAnsweredProblems = Integer.parseInt(answeredProblems);
       intCorrectAnswers = Integer.parseInt(correctAnswers);
       dblTotalScore = Double.parseDouble(totalScore);
       intAnsweredProblems_middle = Integer.parseInt(answeredProblems_middle);
       intCorrectAnswers_middle = Integer.parseInt(correctAnswers_middle);       

       lastType = thisType;
       thisType = "M";
       intId = Integer.parseInt(id);

       try{
          Class.forName("org.hsqldb.jdbcDriver");
          myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", ""); 

          stmt = myConnection.prepareStatement("select remainSeconds from student_time where studentNo=?");
          stmt.clearParameters();
          stmt.setString(1, userName);
          rs = stmt.executeQuery();              
          while(rs.next()){
             remainSeconds = rs.getInt(1);
          }

          stmt = myConnection.prepareStatement("select answerRightOrWrong from termMatchAnswer where studentNo=?");
          stmt.clearParameters();
          stmt.setString(1, userName);
          rs = stmt.executeQuery();              
          while(rs.next()){
             lastCorrect = rs.getString(1);
          }

          stmt = myConnection.prepareStatement("update usedTermMatch set answer=? where studentNo=? and id=?");
          stmt.clearParameters();
          stmt.setString(1, lastCorrect);
          stmt.setString(2, userName);
          stmt.setInt(3, intId);
          stmt.executeUpdate();

          stmt = myConnection.prepareStatement("select term1,term2 from termMatch where id=?");
          stmt.clearParameters();
          stmt.setInt(1, intId);
          rs = stmt.executeQuery();              
          while(rs.next()){
             term1 = rs.getString(1);
             term2 = rs.getString(2);
          }

          //below we want to get the correct form of trueName again
          stmt = myConnection.prepareStatement("select name from students where studentNo=?");
          stmt.clearParameters();
          stmt.setString(1, userName);
          rs = stmt.executeQuery();              
          while(rs.next()){
             trueName = rs.getString(1);
          }

       }catch(Exception e){
          e.printStackTrace();
       }finally{
          try {
             if (rs != null) rs.close();             
             if (stmt != null) stmt.close();
             if (myConnection != null) myConnection.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }

       intAnsweredProblems ++;
       intAnsweredProblems_middle ++;
       if (lastCorrect.equals("true")){
          dblTotalScore += 1.5;
          intCorrectAnswers += 1;
          intCorrectAnswers_middle += 1;
       }
       answeredProblems = "" + intAnsweredProblems;
       answeredProblems_middle = "" + intAnsweredProblems_middle;
       totalScore = "" + dblTotalScore;
       correctAnswers = "" + intCorrectAnswers;
       correctAnswers_middle = "" + intCorrectAnswers_middle;
    %>
<h2>Hello, <%= trueName%><P>
The Correct Answer of Problem:<br>
Id : <%= id%><p>
Type: <%= lastType%><p>
<%= term1 %><p>    
<%= term2 %><p>

<center> 
    <% if (lastCorrect.equals("true")) { %>
        You have made the right match choice.
    <% } else if (lastCorrect.equals("false")){ %>
        You have made the wrong match choice.
    <% } %>
</center>
</h2><p>
    <% 
       if (remainSeconds > 0 && planStatus.equals("not finished")){ 
    %>
     
         <html:form action="/SmartRandom2.do?first=false" >

      <bean:message key="hello.jsp.prompt.updateScore"/><html:text property="updateScore" size="16" maxlength="16"/><br>
      <html:hidden property="answeredProblems" value="<%= answeredProblems %>"/><br>
      <html:hidden property="correctAnswers" value="<%= correctAnswers %>"/>
      <html:hidden property="totalScore" value="<%= totalScore %>"/>
      <html:hidden property="correctAnswers_low" value="<%= correctAnswers_low %>"/>
      <html:hidden property="answeredProblems_low" value="<%= answeredProblems_low %>"/>
 <html:hidden property="correctAnswers_middle" value="<%= correctAnswers_middle %>"/>
 <html:hidden property="answeredProblems_middle" value="<%= answeredProblems_middle %>"/>
     <html:hidden property="correctAnswers_high" value="<%= correctAnswers_high %>"/>
     <html:hidden property="answeredProblems_high" value="<%= answeredProblems_high %>"/>
      <html:hidden property="answeredHashSet" value="<%= _hs%>"/>
      <html:hidden property="answeredHashSet_tf" value="<%= _hs_tf%>"/>
      <html:hidden property="answered_M_HashSet" value="<%= _mhs%>"/>
      <html:hidden property="answeredHashSet_fb" value="<%= _hs_fb%>"/>
      <html:hidden property="answeredHashSet_fdb" value="<%= _hs_fdb%>"/>
      <html:hidden property="answeredHashSet_tm" value="<%= _hs_tm%>"/>
      <html:hidden property="userName" value="<%= userName %>"/>
      <html:hidden property="passWord" value="<%= passWord %>"/>
      <html:hidden property="trueName" value="<%= trueName %>"/>
      <html:hidden property="lastCorrect" value="<%= lastCorrect%>"/>
      <html:hidden property="lastType" value="<%= lastType %>"/>
      <html:hidden property="thisType" value="<%= thisType %>"/>
      <html:hidden property="times" value="<%= times %>"/>
      <html:hidden property="planStatus" value="<%= planStatus %>"/>

      <html:submit property="submit" value="Submit"/>
      <html:reset/>

    </html:form><br><br>      

<% } else {   %>
    
       <center><h2>Either your time is used up or you have answered all the problems. Please click the following submit button to exit.</h2></center>
        <center>
      <html:form action="/SmartQuit.do" >     
      <html:hidden property="answeredHashSet" value="<%= _hs%>"/>
      <html:hidden property="answeredHashSet_tf" value="<%= _hs_tf%>"/>
      <html:hidden property="answered_M_HashSet" value="<%= _mhs%>"/>
      <html:hidden property="answeredHashSet_fb" value="<%= _hs_fb%>"/>
      <html:hidden property="answeredHashSet_fdb" value="<%= _hs_fdb%>"/>
      <html:hidden property="answeredHashSet_tm" value="<%= _hs_tm%>"/>
      <html:hidden property="userName" value="<%= userName %>"/>
      <html:hidden property="passWord" value="<%= passWord %>"/>
      <html:hidden property="trueName" value="<%= trueName %>"/>
      <html:hidden property="answeredProblems" value="<%= answeredProblems %>"/><br>
      <html:hidden property="correctAnswers" value="<%= correctAnswers %>"/>
      <html:hidden property="totalScore" value="<%= totalScore %>"/>
      <html:hidden property="correctAnswers_low" value="<%= correctAnswers_low %>"/>
      <html:hidden property="answeredProblems_low" value="<%= answeredProblems_low %>"/>
 <html:hidden property="correctAnswers_middle" value="<%= correctAnswers_middle %>"/>
 <html:hidden property="answeredProblems_middle" value="<%= answeredProblems_middle %>"/>
     <html:hidden property="correctAnswers_high" value="<%= correctAnswers_high %>"/>
     <html:hidden property="answeredProblems_high" value="<%= answeredProblems_high %>"/>
      <html:hidden property="times" value="<%= times %>"/>
      <html:hidden property="planStatus" value="<%= planStatus %>"/>
  
      <html:submit property="submit" value="Submit"/>
      <html:reset/>

    </html:form><br>
    </center>

    <% } %>

    <html:img page="/struts-power.gif" alt="Powered by Struts"/>
   
  </body>
</html:html>

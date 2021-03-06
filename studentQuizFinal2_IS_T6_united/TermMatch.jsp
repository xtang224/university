<%@ include file="taglibs.jsp" %>

<html:html locale="true">
  <head>       
  </head>
  <body bgcolor="white"><p>   

   <html:errors/><p> 

<!--
    <logic:present name="termmatchbean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.problem"/>
         <bean:write name="termmatchbean" property="id" /><p>
         <bean:write name="termmatchbean" property="type" /><p>
         <bean:write name="termmatchbean" property="term1" />   
         <bean:write name="termmatchbean" property="term2" />      
       </h2>
    </logic:present>  
-->        

    <logic:present name="personbean" scope="session">
       <h2>
         <bean:message key="hello.jsp.page.selectedStudent"/><bean:write name="personbean" property="userName" />
         <bean:message key="hello.jsp.page.doubleColon"/><bean:write name="personbean" property="trueName" /><p>          
       </h2>
    </logic:present>  
     
    <%! HttpSession session = null; 
        hello.TermMatchBean _tmb = null;
        hello.PersonBean _pb2 = null;
        String _hs = null;
        String _hs_tf = null;
        String _mhs = null;
        String _hs_fb = null;
        String _hs_fdb = null;
        String _hs_tm = null;
        int remainSeconds = 300;

        String userName  = null;
        String strCorrectContentSet = null; 
        java.sql.Connection myConnection = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        Statement s = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        HashSet usedSet = null;
        HashSet totalSet = null;
        int intTemp = 0;
        Iterator iter = null;
        int intRandom = 0;
        HashSet currentSet = null;
        int index = 0;
        int i = 0;
        int i2 = 0;
        int id = 0; String strTerm1 = null; String strTerm2 = null;
        String strType = null; String strSource = null;
        int[] intUsed = null;
        String answerRightOrWrong = null;

        HashSet hs_tm = new HashSet();
    %>
    <% session = request.getSession(true);     
       _tmb = (hello.TermMatchBean)request.getAttribute(hello.Constants.TERMMATCH_KEY);
       _pb2 = (hello.PersonBean)request.getAttribute(hello.Constants.PERSON_KEY);
       _hs = (String)request.getAttribute(hello.Constants.HASHSET_KEY);
       _hs_tf = (String)request.getAttribute(hello.Constants.HASHSET_TF_KEY);
       _mhs = (String)request.getAttribute(hello.Constants.HASHSET_M_KEY);
       _hs_fb = (String)request.getAttribute(hello.Constants.HASHSET_FB_KEY);
       _hs_fdb = (String)request.getAttribute(hello.Constants.HASHSET_FDB_KEY);
       _hs_tm = (String)request.getAttribute(hello.Constants.HASHSET_TM_KEY);
       
       System.out.println("inside TermMatch.jsp, _hs_tm = " + _hs_tm);
       hs_tm = StringSetTransfer.stringToSet(_hs_tm);

       userName = _pb2.getUserName();
       System.out.println("inside TermMatch.jsp, userName = " + userName);
       strCorrectContentSet = "false"; 
       usedSet = new HashSet();  
       totalSet = new HashSet(util.StringSetTransfer.hs_middle_tm);
       currentSet = new HashSet();

       try{
          Class.forName("org.hsqldb.jdbcDriver");
          myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", ""); 

          stmt = myConnection.prepareStatement("select id from usedTermMatch");
          stmt.clearParameters();
          rs = stmt.executeQuery();              
          while(rs.next()){
             intTemp = rs.getInt(1);
             usedSet.add(new Integer(intTemp));
          }
         
          totalSet.removeAll(usedSet); 
          s = myConnection.createStatement();
          s.execute("delete from currentTermMatch");      

          intUsed = new int[4];
          
          if (totalSet.size() > 4){
             i2 = 4;
          }else{
             i2 = totalSet.size();
          }
          for (i = 0; i < i2; i++){
             intRandom = (int)(Math.random() * totalSet.size());
             while (currentSet.contains(new Integer(intRandom))){
                intRandom = (int)(Math.random() * totalSet.size());
             }
             currentSet.add(new Integer(intRandom));
             index = 0;
             iter = totalSet.iterator();
             while (iter.hasNext()) {
                if (index == intRandom){
                   intTemp = ((Integer)iter.next()).intValue();
                   break;
                }
                iter.next();
                index++;                      
             }

             stmt = myConnection.prepareStatement("insert into currentTermMatch(id) values(?)");
             stmt.clearParameters();
             stmt.setInt(1, intTemp);
             stmt.executeUpdate();

             intUsed[i] = intTemp;
          }

          intRandom = (int)(Math.random() * i2);
          stmt = myConnection.prepareStatement("insert into currentTermMatch(id) values(?)");
          stmt.clearParameters();
          stmt.setInt(1, intRandom);
          stmt.executeUpdate();

          if (i2 > 1){
             hs_tm.add(new Integer(intUsed[intRandom]));
          }else{
             hs_tm = new HashSet(StringSetTransfer.hs_middle_tm);
          }
          _hs_tm = StringSetTransfer.setToString(hs_tm);

          //the value of intUsed[intRandom] will be saved in the usedTermMatch
          stmt = myConnection.prepareStatement("select id,term1,term2,type,source from termMatch where id=?");
          stmt.clearParameters();
          stmt.setInt(1, intUsed[intRandom]);
          rs = stmt.executeQuery();
          while(rs.next()){              
             id = rs.getInt(1);
             strTerm1 = rs.getString(2);
             strTerm2 = rs.getString(3);
             strType = rs.getString(4);
             strSource = rs.getString(5);

             stmt2 = myConnection.prepareStatement("insert into usedTermMatch(studentNo,id,type,source) values(?,?,?,?)");
             stmt2.clearParameters();
             stmt2.setString(1, userName);
             stmt2.setInt(2, id);       
             stmt2.setString(3, strType);         
             stmt2.setString(4, strSource);
             stmt2.executeUpdate();
          }

          stmt = myConnection.prepareStatement("select answerRightOrWrong from termMatchAnswer where studentNo=?");
          stmt.clearParameters();
          stmt.setString(1, userName);
          rs = stmt.executeQuery();
          while (rs.next()) {
             answerRightOrWrong = rs.getString(1);
          }
          if (answerRightOrWrong == null)
             answerRightOrWrong = "";

       }catch(Exception e){
          e.printStackTrace();
       }finally{
          try {
             if (rs != null) rs.close();
             if (rs2 != null) rs2.close();
             if (s != null) s.close();
             if (stmt != null) stmt.close();
             if (stmt2 != null) stmt2.close();
             if (myConnection != null) myConnection.close();
          } catch (SQLException e) {
             e.printStackTrace();
          }
       }
    %>

<h2>
The Problem:<br>
Id : <%= id%><p>
Type: <%= strType%><p>
<%= strTerm1 %><p>    
</h2>

     <% remainSeconds = _pb2.getRemainSeconds();
       if (remainSeconds > 0 && _pb2.getPlanStatus().equals("not finished")){ 
    %>

      <FORM ACTION="dragTermTable4.xhtml" METHOD="GET">
         <TABLE BORDER=0>    
        <TR>
           <TD><INPUT TYPE="Hidden" name="answeredHashSet" value="<%= _hs%>"></TD>
           <TD><INPUT TYPE="Hidden" name="answeredHashSet_tf" value="<%= _hs_tf%>"></TD>
           <TD><INPUT TYPE="Hidden" name="answered_M_HashSet" value="<%= _mhs%>"></TD>
           <TD><INPUT TYPE="Hidden" name="answeredHashSet_fb" value="<%= _hs_fb%>"></TD>
           <TD><INPUT TYPE="Hidden" name="answeredHashSet_fdb" value="<%= _hs_fdb%>"></TD>
           <TD><INPUT TYPE="Hidden" name="answeredHashSet_tm" value="<%= _hs_tm%>"></TD>
   <TD><INPUT TYPE="Hidden" name="answeredProblems" value="<%= (new Integer(_pb2.getAnsweredProblems()).toString())%>"></TD>
   <TD><INPUT TYPE="Hidden" name="correctAnswers" value="<%= (new Integer(_pb2.getCorrectAnswers()).toString())%>"></TD>
   <TD><INPUT TYPE="Hidden" name="totalScore" value="<%= (new Double(_pb2.getTotalScore()).toString())%>"></TD>
 <INPUT TYPE="Hidden" name="correctAnswers_low" value="<%= (new Integer(_pb2.getCorrectAnswers_low()).toString()) %>">
 <INPUT TYPE="Hidden" name="answeredProblems_low" value="<%= (new Integer(_pb2.getAnsweredProblems_low()).toString()) %>">
 <INPUT TYPE="Hidden" name="correctAnswers_middle" value="<%= (new Integer(_pb2.getCorrectAnswers_middle()).toString())%>">
<INPUT TYPE="Hidden" name="answeredProblems_middle" value="<%= (new Integer(_pb2.getAnsweredProblems_middle()).toString())%>">
 <INPUT TYPE="Hidden" name="correctAnswers_high" value="<%= (new Integer(_pb2.getCorrectAnswers_high()).toString())%>">
 <INPUT TYPE="Hidden" name="answeredProblems_high" value="<%= (new Integer(_pb2.getAnsweredProblems_high()).toString())%>">
           <TD><INPUT TYPE="Hidden" name="userName" value="<%= userName%>"></TD>
           <TD><INPUT TYPE="Hidden" name="passWord" value="<%= _pb2.getPassWord()%>"></TD>
           <TD><INPUT TYPE="Hidden" name="trueName" value="<%= _pb2.getTrueName()%>"></TD>
           <TD><INPUT TYPE="Hidden" name="lastType" value="<%= _pb2.getLastType()%>"></TD>
           <TD><INPUT TYPE="Hidden" name="thisType" value="<%= _pb2.getThisType()%>"></TD>
           <TD><INPUT TYPE="Hidden" name="times" value="<%= _pb2.getTimes()%>"></TD>
           <TD><INPUT TYPE="Hidden" name="randomNumber" value="<%= _pb2.getRandomNumber()%>"></TD>
           <TD><INPUT TYPE="Hidden" name="id" value="<%= new Integer(_pb2.getCurrentProblemId()).toString() %>"></TD>
           <TD><INPUT TYPE="Hidden" name="planStatus" value="<%= _pb2.getPlanStatus()%>"></TD>
        </TR>
        <TR>      
           <TD align=center><INPUT TYPE="Submit" VALUE="Submit to Visit dragTermTable4.xhtml">&nbsp;&nbsp;
                       <INPUT TYPE="Reset" VALUE="Reset"></TD>
       </TR>
         </TABLE>
      </FORM>
      <br>

       <h2>If you want to skip this problem and do it later, then you can click the skip button, the skipped problem will come back later.<br></h2>
    <html:form action="/SmartRandomCheck2.do" >     
      
      <html:hidden property="correctAnswer" value="<%= _tmb.getCorrectChoice()%>"/>
      <html:hidden property="solution" value="<%= _tmb.getSolution()%>"/>
      <html:hidden property="source" value="<%= _tmb.getSource()%>"/>
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

      <center><h2>Either your time is used up or you have answered all the problems. Please click the following submit button to exit.</h2></center>
        <center>
      <html:form action="/SmartQuit.do" >     
      <html:hidden property="answeredHashSet" value="<%= _hs%>"/>
      <html:hidden property="answeredHashSet_tf" value="<%= _hs_tf%>"/>
      <html:hidden property="answered_M_HashSet" value="<%= _mhs%>"/>
      <html:hidden property="answeredHashSet_fb" value="<%= _hs_fb%>"/>
      <html:hidden property="answeredHashSet_fdb" value="<%= _hs_fdb%>"/>
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

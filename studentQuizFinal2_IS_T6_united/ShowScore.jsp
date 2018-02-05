<%@ include file="taglibs.jsp" %>

<html:html locale="true">
  <head>   
    <html:base/>
  </head>
  <body bgcolor="white"><p>    


<!--
    <logic:present name="scorebean" scope="request">
       <h2>
         <bean:message key="hello.jsp.page.showScore"/>         
       </h2>
    </logic:present>  

    <jsp:useBean id="pb" scope="session" class="hello.ProblemBean" />
    <jsp:useBean id="pb2" scope="session" class="hello.PersonBean" />
-->

    <%! HttpSession session = null; 
        ArrayList infoList = null;
        //Object[] infoListArr = null;
        int i = 0;
        String info = null;
        int index = 0;
        String userName = null;
        String playerId = null;
        String totalScore = null;
        String answeredProblems = null;
        String correctAnswers = null;
        String result = null;
    %>
    <% session = request.getSession(true);     
       infoList = (ArrayList)request.getAttribute("infoList");       
       //infoListArr = request.getAttribute("infoListArr");         
       //result = (String)request.getAttribute("infoStr");
       //System.out.println("Inside ShowScore.jsp, result = " + result);
    %>
       <CENTER><H2>The entire class scores are displayed below:</H2><BR>  
       
    <CENTER><TABLE BGCOLOR="#D8D8D8" BORDER=2>
    <tr ALIGN=center>
       <th>User Name</th>
       <th>PlayerId</th>      
       <th>Total Score</th>
       <th>Problems Answered</th>
       <th>Correct Answers</th>
    </tr>
  
   <% for (i = 0; i < infoList.size(); i++){ 
         info = (String)infoList.get(i);
         System.out.println("inside ShowScore.jsp, info = " + info);

         index = info.indexOf(":");
         userName = info.substring(0, index);
         info = info.substring(index + 1);

         index = info.indexOf(":");
         playerId = info.substring(0, index);
         info = info.substring(index + 1);

         index = info.indexOf(":");
         totalScore = info.substring(0, index);
         info = info.substring(index + 1);

         index = info.indexOf(":");
         answeredProblems = info.substring(0, index);
         correctAnswers = info.substring(index + 1);
   %>
    <TR ALIGN=center><FONT COLOR="#FF5555">
        <TD><%= userName %></TD>
        <TD><%= playerId %></TD>
        <TD><%= totalScore %></TD>
        <TD><%= answeredProblems %></TD>
        <TD><%= correctAnswers %></TD>
    </FONT></TR>    

   <% } %>   
    </TABLE>   
    </CENTER>
    
    <html:img page="/struts-power.gif" alt="Powered by Struts"/>
   
  </body>
</html:html>

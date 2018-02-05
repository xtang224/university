package hello;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.config.*;
import org.apache.struts.action.*;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;

import util.*;

public final class ScoreAction extends Action {

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    throws Exception {

        // These "messages" come from the ApplicationResources.properties file
        MessageResources messages = getResources(request);

        /*
         * Validate the request parameters specified by the user
         * Note: Basic field validation done in HelloForm.java
         *       Business logic validation done in HelloAction.java
         */
        ActionMessages errors = new ActionMessages();
        HttpSession session = request.getSession(true); 
       
        String userName = (String)((ScoreForm) form).getUserName();     
        String playerId = (String)((ScoreForm) form).getPlayerId();
        String trueName = (String)((ScoreForm) form).getTrueName();
        PersonBean pb2 = new PersonBean(); 
        pb2.setUserName(userName);
        pb2.setPlayerId(playerId);
        pb2.setTrueName(trueName);

        request.removeAttribute(mapping.getAttribute());

        session.setAttribute( Constants.PERSON_KEY, pb2); 
        request.setAttribute( Constants.PERSON_KEY, pb2);    

        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null;
        PreparedStatement stmt = null;
        Statement s = null;
        ResultSet rs = null;
        String first = (String)request.getParameter("first");
        if (first.equals("true")){           
           String vote = (String)((ScoreForm) form).getVote();
           try {
              dataSource = getDataSource(request);
              myConnection = dataSource.getConnection();

              stmt = myConnection.prepareStatement("insert into student_vote(studentNo, playerId, vote) values(?,?,?)");  
              stmt.clearParameters();
              stmt.setString(1, userName);              
              stmt.setString(2, playerId); 
              stmt.setString(3, vote.toLowerCase()); 
              stmt.execute();
           }catch (SQLException sqle) {
              getServlet().log("In ScoreAction.java, Connection.process", sqle);
           } finally {          
              try {                 
                 if (stmt != null) stmt.close();
                 if (myConnection != null) myConnection.close();
              } catch (SQLException e) {
                 getServlet().log("In ScoreAction.java, Connection.close", e);
              }
           }        
        }
        
        int totalNumberPlayers = 0;
        int totalNumberVote = 0;
        int totalNumberVoteYes = 0;
        try {
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
           
           s = myConnection.createStatement();
           rs = s.executeQuery("select studentNo from student_score where status='active'");
           while (rs.next()){
              totalNumberPlayers ++;
           }
           getServlet().log("In ScoreAction.java, totalNumberPlayers = " + totalNumberPlayers);

           s = myConnection.createStatement();
           rs = s.executeQuery("select vote from student_vote");
           while (rs.next()){
              totalNumberVote ++;
           }
           getServlet().log("In ScoreAction.java, totalNumberVote = " + totalNumberVote);

           s = myConnection.createStatement();
           rs = s.executeQuery("select vote from student_vote where vote='yes' ");
           while (rs.next()){
              totalNumberVoteYes ++;
           }
           getServlet().log("In ScoreAction.java, totalNumberVote = " + totalNumberVote);           
        } catch (SQLException sqle) {
           getServlet().log("In ScoreAction.java, Connection.process", sqle);
        } finally {          
           try {
              if (rs != null) rs.close();
              if (s != null) s.close();
              if (myConnection != null) myConnection.close();
           } catch (SQLException e) {
              getServlet().log("In ScoreAction.java, Connection.close", e);
           }
        }
   
       
       if (totalNumberVote < totalNumberPlayers){
          return (mapping.findForward("ShowScoreBefore"));       
       }
       
       else if (totalNumberVoteYes < totalNumberVote/2){
          return (mapping.findForward("ShowLogout"));
       }
       
       ArrayList scoreList = new ArrayList();
       ArrayList infoList = new ArrayList();
       //HashMap map = new HashMap();
       try{
          dataSource = getDataSource(request);
          myConnection = dataSource.getConnection();
          //Class.forName("org.hsqldb.jdbcDriver");
          //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
           
          s = myConnection.createStatement();
     rs = s.executeQuery("select studentNo, playerId, totalScore, answeredProblems, correctAnswers from student_score where status='active'");
          while(rs.next()){
             userName = rs.getString(1);
             playerId = rs.getString(2);
             double totalScore = rs.getDouble(3);
             int answeredProblems = rs.getInt(4);
             int correctAnswers = rs.getInt(5);
             scoreList.add(new Double(totalScore));
             String info = userName + ":" + playerId + ":" + totalScore + ":" + answeredProblems + ":" + correctAnswers;
             infoList.add(info);
             //getServlet().log("info = " + info);
             //System.out.println("info = " + info);             
             //map.put(new Double(totalScore), info);
          }
       }catch (SQLException sqle) {
           getServlet().log("In ScoreAction.java, Connection.process", sqle);
       } finally {          
          try {
             if (rs != null) rs.close();
             if (s != null) s.close();
             if (myConnection != null) myConnection.close();
          } catch (SQLException e) {
             getServlet().log("In ScoreAction.java, Connection.close", e);
          }
       }

       Object[] scoreListArr = scoreList.toArray();
       double[] dbScoreListArr = new double[scoreList.size()];
       for (int i = 0; i < scoreList.size(); i++){
          dbScoreListArr[i] = ((Double)scoreListArr[i]).doubleValue();
       }
       Object[] infoListArr = infoList.toArray();

       double dbTemp = 0;
       Object obTemp = null;
       for (int i = 0; i < scoreList.size()-1; i++){          
          for (int j = i+1; j < scoreList.size(); j++){
             if (dbScoreListArr[i] < dbScoreListArr[j]){
                dbTemp = dbScoreListArr[i];
                dbScoreListArr[i] = dbScoreListArr[j];
                dbScoreListArr[j] = dbTemp;

                obTemp = infoListArr[i];
                infoListArr[i] = infoListArr[j];
                infoListArr[j] = obTemp; 
             }
          }
       }       

       ArrayList newInfoList = new ArrayList();
       for (int i = 0; i < infoListArr.length; i++){
          String strTemp = new String((String)infoListArr[i]);
          newInfoList.add(strTemp);
       }

       session.setAttribute( "infoList", newInfoList); 
       request.setAttribute( "infoList", newInfoList); 

       return (mapping.findForward("ShowScore"));  

       //Arrays.sort(scoreListArr);
       /*
       String result = "";
       for (int i = scoreList.size()-1; i>=0; i--){
          Double tempDouble = (Double)scoreListArr[i];
          String tempInfo = (String)map.get(tempDouble);
          infoList.add(tempInfo);
          System.out.println("info = " + info); 
          result += tempInfo + ";";
       }  
       System.out.println("Inside ScoreAction.java, result = " + result);       
       */       

       //session.setAttribute( "infoStr", result); 
       //request.setAttribute( "infoStr", result); 

       //ModuleConfig mConfig = getServlet().getModuleConfig(request);
       //RequestProcessor rProcessor = getServlet().getRequestProcessor(mConfig);
       //rProcessor.doForward("/ShowScore.jsp", request, response);
       /*
       ServletContext sContext = getServlet().getServletContext();
       RequestDispatcher rDispatcher = sContext.getRequestDispatcher("/ShowScore.jsp");
       rDispatcher.include(request, response);
       */   
       
        
        //pb.saveToPersistentStore();

        /*
         * If there was a choice of View components that depended on the model
         * (or some other) status, we'd make the decision here as to which
         * to display. In this case, there is only one View component.
         *
         * We pass data to the View components by setting them as attributes
         * in the page, request, session or servlet context. In this case, the
         * most appropriate scoping is the "request" context since the data
         * will not be neaded after the View is generated.
         *
         * Constants.PERSON_KEY provides a key accessible by both the
         * Controller component (i.e. this class) and the View component
         * (i.e. the jsp file we forward to).
         */     
        
    }
}

package hello;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;

import util.*;

public final class UpdateScoreAction extends Action {

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
        String userName = (String)((UpdateScoreForm) form).getUserName();
        String passWord = (String)((UpdateScoreForm) form).getPassWord();

        /*       
        String badUserName = "Monster";
        if (userName.equalsIgnoreCase(badUserName)) {
           errors.add("username", new ActionMessage("hello.dont.talk.to.monster", badUserName ));
           saveErrors(request, errors);
           return (new ActionForward(mapping.getInput()));
        }
        */
        /*
         * Having received and validated the data submitted
         * from the View, we now update the model
         */       
       
        /*
        if (pb == null) {
           return (mapping.findForward("toError"));           
        }        
        if (pb == null) {
           pb = new ProblemBean();
        }
        */

        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        Statement s = null;
        ResultSet rs = null;
        int exist = 0;
        //int numberTried = 0;
        PersonBean pb2 = new PersonBean();
        pb2.setUserName(userName);
        pb2.setPassWord(passWord); 
       
        double currentScore = -100;
        String scoreNumber = "score_1st";
        String tempStudentNo = "";  
        String sql = "";
        String sql2 = "";
        outer:
        while(true){
           try{
              dataSource = getDataSource(request);
              myConnection = dataSource.getConnection();
              //Class.forName("org.hsqldb.jdbcDriver");
              //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");

              sql = "select " + scoreNumber + ", studentNo from students where " + scoreNumber + " = ?";
              stmt = myConnection.prepareStatement(sql); 
              stmt.clearParameters();
              stmt.setInt(1, -2);
              rs = stmt.executeQuery();           
              while (rs.next()){
                 currentScore = rs.getDouble(1);
                 tempStudentNo = rs.getString(2);
                 if (currentScore == -2){                
                    sql2 = "update students set " + scoreNumber + " = ? where studentNo = ?";
                    stmt2 = myConnection.prepareStatement(sql2); 
                    stmt2.clearParameters();
                    stmt2.setInt(1, -1);
                    stmt2.setString(2, tempStudentNo);
                    stmt2.executeUpdate();         
                 }                      
              }
              
              if (scoreNumber.equals("score_1st"))
                 scoreNumber = "score_2nd";
              else if (scoreNumber.equals("score_2nd"))
                 scoreNumber = "score_3rd";
              else if (scoreNumber.equals("score_3rd"))
                 scoreNumber = "score_4th";
              else if (scoreNumber.equals("score_4th"))
                 scoreNumber = "score_5th";
              else if (scoreNumber.equals("score_5th"))
                 scoreNumber = "score_6th";
              else if (scoreNumber.equals("score_6th"))
                 scoreNumber = "score_7th";
              else if (scoreNumber.equals("score_7th"))
                 scoreNumber = "score_8th";
              else if (scoreNumber.equals("score_8th"))
                 scoreNumber = "score_9th";
              else
                 break outer;
          
           }catch (SQLException ex){
              getServlet().log("Inside SmartRandom2Action.java, process.", ex);
           }finally{
              try{
                 if (rs != null) rs.close();
                 if (s != null) s.close();
                 if (stmt != null) stmt.close(); 
                 if (stmt2 != null) stmt2.close(); 
                 if (myConnection != null) myConnection.close();
              }catch (SQLException e){
                 getServlet().log("Inside SmartRandom2Action.java, closing.", e);
              }
           } 
        }
        System.out.println("inside UpdateScoreAction, in the middle of update score");

       request.removeAttribute(mapping.getAttribute());
       session.setAttribute( Constants.PERSON_KEY, pb2); 
       request.setAttribute( Constants.PERSON_KEY, pb2);     
          
       return (mapping.findForward("UpdateScoreDone"));  
       //pb.saveToPersistentStore();        
    }
}

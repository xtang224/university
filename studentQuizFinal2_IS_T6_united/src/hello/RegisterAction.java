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

public final class RegisterAction extends Action {

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
        String userName = (String)((RegisterForm) form).getUserName();
        String trueName = (String)((RegisterForm) form).getTrueName();
        String passWord = (String)((RegisterForm) form).getPassWord();
        String checkPassWord = (String)((RegisterForm) form).getCheckPassWord();
        String phoneNumber = (String)((RegisterForm) form).getPhoneNumber();
        String emailAddress = (String)((RegisterForm) form).getEmailAddress();

        PersonBean pb2 = new PersonBean();       
        pb2.setUserName(userName);    
        pb2.setPassWord(passWord);
        pb2.setTrueName(trueName);        
        pb2.setQuestionUsedUp("false");        

        request.removeAttribute(mapping.getAttribute());
        session.setAttribute( Constants.PERSON_KEY, pb2); 
        request.setAttribute( Constants.PERSON_KEY, pb2);

        if (userName == null || userName.equals("") || trueName == null || trueName.equals("")){
           return (mapping.findForward("FailRegister1"));        
        }else if(!passWord.equals(checkPassWord)){
           return (mapping.findForward("FailRegister2"));        
        }
      
        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null;
        PreparedStatement stmt = null;
        Statement s = null;
        ResultSet rs = null;
        int exist = 0;
        //int numberTried = 0;
       
        try {
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");

           s = myConnection.createStatement();
           rs = s.executeQuery("select studentNo from students where studentNo='" + userName + "'");
           exist = 0;
           while (rs.next()){
              exist ++;                                       
           }                      
           if (exist > 0){
              return (mapping.findForward("FailRegister3"));        
           } 

           exist = 0;
           s = myConnection.createStatement();
           rs = s.executeQuery("select studentNo from student_score");
           while (rs.next()){
              exist ++;                                       
           }           
           String playerId = "player" + (exist + 1);

           stmt = myConnection.prepareStatement("insert into students(studentNo, password, name, phoneNumber, emailAddress, score) values(?,?,?,?,?,0)");
           stmt.clearParameters();
           stmt.setString(1, userName);
           stmt.setString(2, passWord);
           stmt.setString(3, trueName);
           stmt.setString(4, phoneNumber);
           stmt.setString(5, emailAddress); 
           stmt.execute();

           stmt = myConnection.prepareStatement("insert into student_time(studentNo, playerId, remainSeconds) values(?, ?, 300)");
           stmt.clearParameters();
           stmt.setString(1, userName);
           stmt.setString(2, playerId);
           stmt.execute();
 
           stmt = myConnection.prepareStatement("insert into student_score(studentNo, playerId, totalScore, answeredProblems, correctAnswers, answeredProblems_low, correctAnswers_low, answeredProblems_middle, correctAnswers_middle, answeredProblems_high, correctAnswers_high, status, planStatus, totalPublicPoints, totalPrivatePoints) values(?, ?, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'active', 'not finished', 0, 0)");
           stmt.clearParameters();
           stmt.setString(1, userName);
           stmt.setString(2, playerId);
           stmt.execute();
           
        } catch (SQLException sqle) {
           getServlet().log("In RegisterAction.java, Connection.process", sqle);
        } finally {
           //enclose this in a finally block to make
           //sure the connection is closed
           try {
              if (rs != null) rs.close();
              if (s != null) s.close();
              if (stmt != null) stmt.close();
              if (myConnection != null) myConnection.close();
           } catch (SQLException e) {
             getServlet().log("In RegisterAction.java, Connection.close", e);
         }
       }
   
       getServlet().log("Inside LoginAction, here is reached.");
    
       return (mapping.findForward("CheckLogin"));         
        
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

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

public final class QuitAction extends Action {

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

        PersonBean pb2 = null;
        pb2 = new PersonBean();          
        
        String userName = (String)((QuitForm) form).getUserName();
        String passWord = (String)((QuitForm) form).getPassWord();
        String trueName = (String)((QuitForm) form).getTrueName();
        pb2.setUserName(userName);
        pb2.setPassWord(passWord); 
        pb2.setTrueName(trueName);  

        String answeredProblems = (String)((QuitForm) form).getAnsweredProblems();
        String score = (String)((QuitForm) form).getScore();
        int intAnsweredProblems = Integer.parseInt(answeredProblems);
        int intScore = Integer.parseInt(score);
        pb2.setAnsweredProblems(intAnsweredProblems);
        pb2.setCorrectAnswers(intScore);
        pb2.setTotalScore(Double.parseDouble(score));

        String times = (String)((QuitForm) form).getTimes();        
        pb2.setTimes(times); 

        // Remove the Form Bean - don't need to carry values forward
        request.removeAttribute(mapping.getAttribute());

        session.setAttribute( Constants.PERSON_KEY, pb2); 
        request.setAttribute( Constants.PERSON_KEY, pb2);

        String answeredHashSetStr = (String)((QuitForm) form).getAnsweredHashSet();
        getServlet().log("before removing oldHS, hs can be represented by " + answeredHashSetStr);
        HashSet hs = StringSetTransfer.stringToSet(answeredHashSetStr);
        hs.remove(new Integer(0));
        String answeredHashSetStr_tf = (String)((QuitForm) form).getAnsweredHashSet_tf();
        getServlet().log("before removing oldHS_tf, hs_tf can be represented by " + answeredHashSetStr_tf);
        HashSet hs_tf = StringSetTransfer.stringToSet(answeredHashSetStr_tf);
        hs_tf.remove(new Integer(0));

        //HashSet hs = StringSetTransfer.stringToSet(answeredHashSetStr);
        //Object[] hsArray = hs.toArray();
        boolean isDone_1 = true;      
        boolean isDone_2 = false; 

        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null;  
        PreparedStatement stmt = null;  
        Statement s = null;
        ResultSet rs = null;           
        
        try {
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();           
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");   

           s = myConnection.createStatement();
           rs = s.executeQuery("select playerId from student_score where studentNo='" + userName + "'");                
           while (rs.next()){
              String playerId = rs.getString(1);
              pb2.setPlayerId(playerId);
           }

stmt = myConnection.prepareStatement("update student_score set totalscore=?, answeredProblems=?, correctAnswers=? where studentNo=?");  
           stmt.clearParameters();
           stmt.setDouble(1, Double.parseDouble(score));
           stmt.setInt(2, intAnsweredProblems);  
           stmt.setInt(3, intScore);
           stmt.setString(4, userName);
           stmt.executeUpdate();
           getServlet().log("score is " + score);            
           
           isDone_2 = true;
           System.out.println("isDone_2 is true");   
           getServlet().log("isDone_2 is true");     
        } catch (SQLException sqle) {
           getServlet().log("Connection.process", sqle);
        } finally {
           //enclose this in a finally block to make
           //sure the connection is closed
           try {
              if (rs != null) rs.close();
              if (s != null) s.close();
              if (stmt != null) stmt.close();
              if (myConnection != null) myConnection.close();
           } catch (SQLException e) {
             getServlet().log("Connection.close", e);
           }
        }        

        HashSet oldHS = new HashSet();
        HashSet oldHS_tf = new HashSet();

        try {
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();           
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
           
           s = myConnection.createStatement();
           rs = s.executeQuery("select id from usedQuestions where studentNo='" + userName + "'");                
           while (rs.next()){
              int usedId = rs.getInt("id");
              oldHS.add(new Integer(usedId));
           }
           rs = s.executeQuery("select id from usedChoices where studentNo='" + userName + "'");                
           while (rs.next()){
              int usedId = rs.getInt("id");
              oldHS_tf.add(new Integer(usedId));
           }

           isDone_1 = true; 
           System.out.println("isDone_1 is true");
           getServlet().log("isDone_1 is true from QuitAction");         

           
           hs.removeAll(oldHS);
           getServlet().log("inside QuitAction.java, after removing oldHS, hs.size() = " + hs.size());
           Object[] hsArray = null;
           if (hs.size() > 0){
              hsArray = hs.toArray();
              for (int i=0; i<hsArray.length; i++) {
                 int usedId = ((Integer)hsArray[i]).intValue();
                 stmt = myConnection.prepareStatement("insert into usedQuestions(id, times, studentNo) values(?,?,?)");  
                 stmt.clearParameters();
                 stmt.setInt(1, usedId);              
                 stmt.setString(2, times); 
                 stmt.setString(3, userName); 
                 stmt.execute();
              }
           }

           hs_tf.removeAll(oldHS_tf);
           getServlet().log("inside QuitAction.java, after removing oldHS_tf, hs_tf.size() = " + hs_tf.size());
           if (hs_tf.size() > 0){
              hsArray = hs_tf.toArray();
              for (int i=0; i<hsArray.length; i++) {
                 int usedId = ((Integer)hsArray[i]).intValue();
                 stmt = myConnection.prepareStatement("insert into usedChoices(id, times, studentNo) values(?,?,?)");  
                 stmt.clearParameters();
                 stmt.setInt(1, usedId);              
                 stmt.setString(2, times); 
                 stmt.setString(3, userName); 
                 stmt.execute();
              }
           }

        }catch (SQLException sqle) {
           getServlet().log("Connection.process", sqle);
        } finally {
           //enclose this in a finally block to make
           //sure the connection is closed
           try {
              if (rs != null) rs.close();
              if (s != null) s.close();
              if (stmt != null) stmt.close();
              if (myConnection != null) myConnection.close();
           } catch (SQLException e) {
             getServlet().log("Connection.close", e);
           }
        }       


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

        // Forward control to the specified success URI
        if (isDone_1 == false || isDone_2 == false)
            return (mapping.findForward("toError"));     
        else    
            return (mapping.findForward("QuitRegister"));        
    }
}

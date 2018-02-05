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

public final class SmartQuitAction extends Action {

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
        HttpSession session = request.getSession(true);

        /*
         * Validate the request parameters specified by the user
         * Note: Basic field validation done in HelloForm.java
         *       Business logic validation done in HelloAction.java
         */
        ActionMessages errors = new ActionMessages();        
                
        PersonBean pb2 = null;
        pb2 = new PersonBean();   
        
        String userName = (String)((SmartQuitForm) form).getUserName();
        String passWord = (String)((SmartQuitForm) form).getPassWord();
        String trueName = (String)((SmartQuitForm) form).getTrueName();
        pb2.setUserName(userName);
        pb2.setPassWord(passWord); 
        pb2.setTrueName(trueName);       

        String planStatus = (String)((SmartQuitForm) form).getPlanStatus();
        pb2.setPlanStatus(planStatus);

        String answeredProblems = (String)((SmartQuitForm) form).getAnsweredProblems();
        String correctAnswers = (String)((SmartQuitForm) form).getCorrectAnswers();
        int intAnsweredProblems = Integer.parseInt(answeredProblems);
        int intCorrectAnswers = Integer.parseInt(correctAnswers);                   
        pb2.setAnsweredProblems(intAnsweredProblems);
        pb2.setCorrectAnswers(intCorrectAnswers); 

        String totalScore = (String)((SmartQuitForm) form).getTotalScore();
        String correctAnswers_low = (String)((SmartQuitForm) form).getCorrectAnswers_low();
        String answeredProblems_low = (String)((SmartQuitForm) form).getAnsweredProblems_low();
        double dbTotalScore = Double.parseDouble(totalScore);
        int intCorrectAnswers_low = Integer.parseInt(correctAnswers_low);
        int intAnsweredProblems_low = Integer.parseInt(answeredProblems_low);                      
        pb2.setTotalScore(dbTotalScore);
        pb2.setCorrectAnswers_low(intCorrectAnswers_low);
        pb2.setAnsweredProblems_low(intAnsweredProblems_low);   

        String correctAnswers_middle = (String)((SmartQuitForm) form).getCorrectAnswers_middle();
        String answeredProblems_middle = (String)((SmartQuitForm) form).getAnsweredProblems_middle();        
        int intCorrectAnswers_middle = Integer.parseInt(correctAnswers_middle);
        int intAnsweredProblems_middle = Integer.parseInt(answeredProblems_middle);       
        pb2.setCorrectAnswers_middle(intCorrectAnswers_middle);
        pb2.setAnsweredProblems_middle(intAnsweredProblems_middle); 

        String correctAnswers_high = (String)((SmartQuitForm) form).getCorrectAnswers_high();
        String answeredProblems_high = (String)((SmartQuitForm) form).getAnsweredProblems_high();        
        int intCorrectAnswers_high = Integer.parseInt(correctAnswers_high);
        int intAnsweredProblems_high = Integer.parseInt(answeredProblems_high);       
        pb2.setCorrectAnswers_high(intCorrectAnswers_high);
        pb2.setAnsweredProblems_high(intAnsweredProblems_high);  
 
        String times = (String)((SmartQuitForm) form).getTimes();
        pb2.setTimes(times);     

        // Remove the Form Bean - don't need to carry values forward
        request.removeAttribute(mapping.getAttribute());        

        session.setAttribute( Constants.PERSON_KEY, pb2); 
        request.setAttribute( Constants.PERSON_KEY, pb2);
       
        boolean isDone_1 = true;      
        boolean isDone_2 = false;        
        
        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null;  
        PreparedStatement stmt = null; 
        Statement s = null;
        ResultSet rs = null; 
        String answeredHashSetStr = (String)((SmartQuitForm) form).getAnsweredHashSet();
        HashSet hs = StringSetTransfer.stringToSet(answeredHashSetStr);
        hs.remove(new Integer(0));

        String answeredHashSetStr_tf = (String)((SmartQuitForm) form).getAnsweredHashSet_tf();
        HashSet hs_tf = StringSetTransfer.stringToSet(answeredHashSetStr_tf);
        hs_tf.remove(new Integer(0));

        String answeredHashSetStr_m = (String)((SmartQuitForm) form).getAnswered_M_HashSet();
        HashSet mhs = StringSetTransfer.stringToSet(answeredHashSetStr_m);
        mhs.remove(new Integer(0));

        String answeredHashSetStr_fb = (String)((SmartQuitForm) form).getAnsweredHashSet_fb();       
        HashSet hs_fb = StringSetTransfer.stringToSet(answeredHashSetStr_fb);
        hs_fb.remove(new Integer(0));

        String answeredHashSetStr_fdb = (String)((SmartQuitForm) form).getAnsweredHashSet_fdb();       
        HashSet hs_fdb = StringSetTransfer.stringToSet(answeredHashSetStr_fdb);
        hs_fdb.remove(new Integer(0));

        getServlet().log("SmartQuitAction, hs string is " + answeredHashSetStr); 
        getServlet().log("SmartQuitAction, hs_tf string is " + answeredHashSetStr_tf); 
        getServlet().log("SmartQuitAction, mhs string is " + answeredHashSetStr_m); 
        getServlet().log("SmartQuitAction, hs_fb string is " + answeredHashSetStr_fb); 
        getServlet().log("SmartQuitAction, hs_fdb string is " + answeredHashSetStr_fdb); 
        
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

stmt = myConnection.prepareStatement("update student_score set totalscore=?, answeredProblems=?, correctAnswers=?, answeredProblems_low=?, correctAnswers_low=?, answeredProblems_middle=?, correctAnswers_middle=?, answeredProblems_high=?, correctAnswers_high=?, planStatus=? where studentNo=?");  
           stmt.clearParameters();
           stmt.setDouble(1, dbTotalScore);
           stmt.setInt(2, intAnsweredProblems);
           stmt.setInt(3, intCorrectAnswers);  
           stmt.setInt(4, intAnsweredProblems_low);
           stmt.setInt(5, intCorrectAnswers_low);    
           stmt.setInt(6, intAnsweredProblems_middle);
           stmt.setInt(7, intCorrectAnswers_middle);  
           stmt.setInt(8, intAnsweredProblems_high);
           stmt.setInt(9, intCorrectAnswers_high);
           stmt.setString(10, planStatus);  
           stmt.setString(11, userName);
           stmt.executeUpdate();
           //getServlet().log("score is " + dbTotalScore); 
           
           isDone_2 = true;
           //System.out.println("isDone_2 is true");   
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
        
        //since all the records are already inserted, the final insertion is not necessary any more
        /*
        HashSet oldHS = new HashSet();
        HashSet oldHS_tf = new HashSet();
        HashSet oldMHS = new HashSet();
        HashSet oldHS_fb = new HashSet();
        HashSet oldHS_fdb = new HashSet();
        try {
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();           
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
           
           s = myConnection.createStatement();
           rs = s.executeQuery("select id from usedQuestions2 where studentNo='" + userName + "'");                
           while (rs.next()){
              int usedId = rs.getInt("id");
              oldHS.add(new Integer(usedId));
           }
           rs = s.executeQuery("select id from usedChoices2 where studentNo='" + userName + "'");                
           while (rs.next()){
              int usedId = rs.getInt("id");
              oldHS_tf.add(new Integer(usedId));
           }
           rs = s.executeQuery("select id from usedMultipleQuestions2 where studentNo='" + userName + "'");                
           while (rs.next()){
              int usedId = rs.getInt("id");
              oldMHS.add(new Integer(usedId));
           }
           rs = s.executeQuery("select id from usedFillBlank2 where studentNo='" + userName + "'");                
           while (rs.next()){
              int usedId = rs.getInt("id");
              oldHS_fb.add(new Integer(usedId));
           }
           rs = s.executeQuery("select id from usedFillDoubleBlanks2 where studentNo='" + userName + "'");                
           while (rs.next()){
              int usedId = rs.getInt("id");
              oldHS_fdb.add(new Integer(usedId));
           }
           isDone_1 = true; 
           System.out.println("isDone_1 is true");
           getServlet().log("isDone_1 is true from SmartQuitAction"); 
           
           hs.removeAll(oldHS);
           Object[] hsArray = hs.toArray();
           for (int i=0; i<hsArray.length; i++) {
              int usedId = ((Integer)hsArray[i]).intValue();
              getServlet().log("usedId is " + usedId); 
              String type = null;
              //s = myConnection.createStatement();
              rs = s.executeQuery("select type from questions2 where id=" + usedId);                
              while (rs.next()){
                 type = rs.getString("type");                 
              }
              getServlet().log("type is " + type); 

           stmt = myConnection.prepareStatement("insert into usedQuestions2(id, type, times, studentNo) values(?,?,?,?)");  
              stmt.clearParameters();
              stmt.setInt(1, usedId); 
              stmt.setString(2, type); 
              stmt.setString(3, times); 
              stmt.setString(4, userName); 
              stmt.execute();
     getServlet().log("insert is success from SmartQuitActioin for usedQuestions2 " + usedId + " : " + type);               
           }
           hs_tf.removeAll(oldHS_tf);
           hsArray = hs_tf.toArray();
           for (int i=0; i<hsArray.length; i++) {
              int usedId = ((Integer)hsArray[i]).intValue();
              getServlet().log("usedId is " + usedId); 
              String type = null;
              //s = myConnection.createStatement();
              rs = s.executeQuery("select type from choices2 where id=" + usedId);               
              while (rs.next()){
                 type = rs.getString("type");                 
              }
              getServlet().log("type is " + type); 

             stmt = myConnection.prepareStatement("insert into usedChoices2(id, type, times, studentNo) values(?,?,?,?)");  
              stmt.clearParameters();
              stmt.setInt(1, usedId); 
              stmt.setString(2, type); 
              stmt.setString(3, times); 
              stmt.setString(4, userName);
              stmt.execute();
       getServlet().log("insert is success from SmartQuitActioin for usedChoices2 " + usedId + " : " + type);               
           }
           mhs.removeAll(oldMHS);
           hsArray = mhs.toArray();
           for (int i=0; i<hsArray.length; i++) {
              int usedId = ((Integer)hsArray[i]).intValue();
              getServlet().log("usedId is " + usedId); 
              String type = null;
              //s = myConnection.createStatement();
              rs = s.executeQuery("select type from multipleQuestions2 where id=" + usedId);               
              while (rs.next()){
                 type = rs.getString("type");                 
              }
              getServlet().log("type is " + type); 

   stmt = myConnection.prepareStatement("insert into usedMultipleQuestions2(id, type, times, studentNo) values(?,?,?,?)");  
              stmt.clearParameters();
              stmt.setInt(1, usedId); 
              stmt.setString(2, type); 
              stmt.setString(3, times); 
              stmt.setString(4, userName);
              stmt.execute();
              getServlet().log("in success SmartQuitAction usedMultipleQuestions2 " + usedId + " : " + type);               
           }  
           hs_fb.removeAll(oldHS_fb);
           hsArray = hs_fb.toArray();
           for (int i=0; i<hsArray.length; i++) {
              int usedId = ((Integer)hsArray[i]).intValue();
              getServlet().log("usedId is " + usedId); 
              String type = null;
              //s = myConnection.createStatement();
              rs = s.executeQuery("select type from fillBlank2 where id=" + usedId);               
              while (rs.next()){
                 type = rs.getString("type");                 
              }
              getServlet().log("type is " + type); 

           stmt = myConnection.prepareStatement("insert into usedFillBlank2(id, type, times, studentNo) values(?,?,?,?)");  
              stmt.clearParameters();
              stmt.setInt(1, usedId); 
              stmt.setString(2, type); 
              stmt.setString(3, times); 
              stmt.setString(4, userName);
              stmt.execute();
     getServlet().log("insert is success from SmartQuitActioin for usedFillBlank2 " + usedId + " : " + type);               
           } 
           hs_fdb.removeAll(oldHS_fdb);
           hsArray = hs_fdb.toArray();
           for (int i=0; i<hsArray.length; i++) {
              int usedId = ((Integer)hsArray[i]).intValue();
              getServlet().log("usedId is " + usedId); 
              String type = null;
              //s = myConnection.createStatement();
              rs = s.executeQuery("select type from fillDoubleBlanks2 where id=" + usedId);               
              while (rs.next()){
                 type = rs.getString("type");                 
              }
              getServlet().log("type is " + type); 

    stmt = myConnection.prepareStatement("insert into usedFillDoubleBlanks2(id, type, times, studentNo) values(?,?,?,?)");  
              stmt.clearParameters();
              stmt.setInt(1, usedId); 
              stmt.setString(2, type); 
              stmt.setString(3, times); 
              stmt.setString(4, userName);
              stmt.execute();
              getServlet().log("insert is success from SmartQuitActioin for usedFillDoubleBlanks2 " + usedId + " : " + type);               
           }                 
           isDone_2 = true;
           System.out.println("isDone_2 is true");   
           getServlet().log("isDone_2 is true from SmartQuitAction");     
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
        */

        // Forward control to the specified success URI
        if (isDone_1 == false || isDone_2 == false)
            return (mapping.findForward("toError"));     
        else    
            return (mapping.findForward("SmartQuitRegister"));        
    }
}

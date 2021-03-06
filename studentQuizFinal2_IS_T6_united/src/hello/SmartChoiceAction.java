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

public final class SmartChoiceAction extends Action {

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
        SmartChoiceBean cb = null;
        String first = null;
        first = (String)request.getParameter("first"); 

        PersonBean pb2 = null;
        pb2 = new PersonBean();
        String userName = (String)((SmartChoiceForm) form).getUserName();
        String passWord = (String)((SmartChoiceForm) form).getPassWord();
        String trueName = (String)((SmartChoiceForm) form).getTrueName();
        pb2.setUserName(userName);
        pb2.setPassWord(passWord); 
        pb2.setTrueName(trueName); 

        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null;
        Statement s = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        cb = new SmartChoiceBean();  
        HashSet hs = null;
        HashSet hs_tf = null;
        
        if (first.equals("true")){
            hs = new HashSet(); 
            hs.add(new Integer(0));
            hs_tf = new HashSet();
            hs_tf.add(new Integer(0));
            HashSet hs_low = new HashSet();
            HashSet hs_middle = new HashSet(); 
            HashSet hs_high = new HashSet();
            HashSet hs_low_tf = new HashSet();
            HashSet hs_middle_tf = new HashSet(); 
            HashSet hs_high_tf = new HashSet();
            try{
                //we will try to initiate the usedQuestions first, then generate the random question id 
               dataSource = getDataSource(request);
               myConnection = dataSource.getConnection();
               //Class.forName("org.hsqldb.jdbcDriver");
               //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
               s = myConnection.createStatement();
               rs = s.executeQuery("select id from usedQuestions2 where studentNo='" + userName + "'");                
               while (rs.next()){
                  int usedId = rs.getInt("id");
                  hs.add(new Integer(usedId));
               }
               rs = s.executeQuery("select id from questions2 where type='L'");                
               while (rs.next()){
                  int usedId = rs.getInt("id");
                  hs_low.add(new Integer(usedId));
               }
               StringSetTransfer.hs_low = hs_low;
               rs = s.executeQuery("select id from questions2 where type='M'");
               while (rs.next()){
                  int usedId = rs.getInt("id");
                  hs_middle.add(new Integer(usedId));
               }
               StringSetTransfer.hs_middle = hs_middle;
               rs = s.executeQuery("select id from questions2 where type='H'");
               while (rs.next()){
                  int usedId = rs.getInt("id");
                  hs_high.add(new Integer(usedId));
               }
               StringSetTransfer.hs_high = hs_high;
               rs = s.executeQuery("select id from questions2");         
               int numberId = 0;
               while (rs.next()){
                 int usedId = rs.getInt("id");
                 numberId ++;                 
                 //getServlet().log("score part is reached");
               }  
               StringSetTransfer.total = numberId;

               rs = s.executeQuery("select id from usedChoices2 where studentNo='" + userName + "'");                
               while (rs.next()){
                  int usedId = rs.getInt("id");
                  hs_tf.add(new Integer(usedId));
               }
               rs = s.executeQuery("select id from choices2 where type='L'");                
               while (rs.next()){
                  int usedId = rs.getInt("id");
                  hs_low_tf.add(new Integer(usedId));
               }
               StringSetTransfer.hs_low_tf = hs_low_tf;
               rs = s.executeQuery("select id from choices2 where type='M'");
               while (rs.next()){
                  int usedId = rs.getInt("id");
                  hs_middle_tf.add(new Integer(usedId));
               }
               StringSetTransfer.hs_middle_tf = hs_middle_tf;
               rs = s.executeQuery("select id from choices2 where type='H'");
               while (rs.next()){
                  int usedId = rs.getInt("id");
                  hs_high_tf.add(new Integer(usedId));
               }
               StringSetTransfer.hs_high_tf = hs_high_tf;
               rs = s.executeQuery("select id from choices2");         
               numberId = 0;
               while (rs.next()){
                 int usedId = rs.getInt("id");
                 numberId ++;                 
                 //getServlet().log("score part is reached");
               }  
               StringSetTransfer.total_tf = numberId;
               
            }catch (SQLException sqle) {
               getServlet().log("Connection.process", sqle);
            } finally {           
                try {
                    rs.close();
                    s.close();
                    myConnection.close();
                } catch (SQLException e) {
                    getServlet().log("Connection.close", e);
                }
            }
        }else{
            String answeredHashSetStr = (String)((SmartChoiceForm) form).getAnsweredHashSet();
            hs = StringSetTransfer.stringToSet(answeredHashSetStr);
            String answeredHashSetStr_tf = (String)((SmartChoiceForm) form).getAnsweredHashSet_tf();
            hs_tf = StringSetTransfer.stringToSet(answeredHashSetStr_tf);
        } 
        
        String lastType = (String)((SmartChoiceForm) form).getLastType();
        String thisType = (String)((SmartChoiceForm) form).getThisType();
        String continueRight = (String)((SmartChoiceForm) form).getContinueRight();
        String continueWrong = (String)((SmartChoiceForm) form).getContinueWrong();
        int intContinueRight = Integer.parseInt(continueRight);
        int intContinueWrong = Integer.parseInt(continueWrong);
        String neverHigh = (String)((SmartChoiceForm) form).getNeverHigh();
        boolean boolNeverHigh = Boolean.parseBoolean(neverHigh);
        try {
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
           // do what you wish with myConnection         
                    
           int id = 0;
           String lastCorrect = (String)((SmartChoiceForm) form).getLastCorrect();
           if (first.equals("true")){
              id = StringSetTransfer.getRandomNumber('L', hs);               
              //StringSetTransfer.lastType = 'L';             
           }else{
              boolean boolLastCorrect = Boolean.parseBoolean(lastCorrect);
              if (boolLastCorrect){
                 switch (lastType.charAt(0)){
                    case 'L':
                       thisType = 'M' + "";
                       break;
                    case 'M':
                       thisType = 'H' + "";              
                       break;
                    case 'H':
                       boolNeverHigh = false;
                       intContinueRight ++;
                       if (intContinueRight == 3){
                          thisType = 'M' + "";
                          intContinueRight = 0;
                       }else{
                          thisType = 'H' + ""; 
                       }             
                       break;
                    default:
                       thisType = 'L' + "";
                       break;
                 }
             }else{
                 switch (lastType.charAt(0)){
                    case 'L':
                        intContinueWrong ++;
                        if (intContinueWrong == 3){
                           thisType = 'M' + "";
                           intContinueWrong = 0;
                        }else{
                           thisType = 'L' + ""; 
                        }       
                        break;
                    case 'M':
                        thisType = 'L' + "";
                        break;
                    case 'H':
                        boolNeverHigh = false;
                        thisType = 'M' + "";
                        break;
                    default:
                        thisType = 'L' + "";
                        break;
                  }
              }
      
              id = StringSetTransfer.getRandomNumber_tf(thisType.charAt(0), hs_tf); 
              lastType = thisType;
           }
           pb2.setLastType(lastType);
           pb2.setThisType(thisType);
           pb2.setContinueRight(intContinueRight);
           pb2.setContinueWrong(intContinueWrong);
           pb2.setNeverHigh(boolNeverHigh);

           String answeredProblems = (String)((SmartChoiceForm) form).getAnsweredProblems();
           int intAnsweredProblems = Integer.parseInt(answeredProblems);           
           if ((intAnsweredProblems == 9) && (boolNeverHigh == true)){
              id = StringSetTransfer.getRandomNumber_tf('H', hs_tf);  
              thisType = "H";
              lastType = "H";  
              pb2.setLastType(lastType);
              pb2.setThisType(thisType);
           }           
           hs_tf.add(new Integer(id));

           stmt = myConnection.prepareStatement("select * from choices2 where id=?");
           stmt.clearParameters();
           stmt.setInt(1, id);
           rs = stmt.executeQuery();
           while (rs.next()){
              cb.setId(rs.getInt("id"));
              cb.setStatement(new String(rs.getBytes("statement"), "utf8"));
              cb.setChoice(rs.getString("choice"));              
              cb.setSolution(new String(rs.getBytes("solution"), "utf8"));
              cb.setType(rs.getString("type"));
           }
        } catch (SQLException sqle) {
           getServlet().log("Connection.process", sqle);
        } finally {
           //enclose this in a finally block to make
           //sure the connection is closed
           try {
              rs.close();             
              stmt.close();
              myConnection.close();
           } catch (SQLException e) {
             getServlet().log("Connection.close", e);
         }
       }
        
        cb.saveToPersistentStore();

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

        // Remove the Form Bean - don't need to carry values forward
        request.removeAttribute(mapping.getAttribute());

        request.setAttribute( Constants.CHOICE_KEY, cb);        
        session.setAttribute( Constants.CHOICE_KEY, cb);
        session.setAttribute( Constants.HASHSET_KEY, StringSetTransfer.setToString(hs));   
        session.setAttribute( Constants.HASHSET_TF_KEY, StringSetTransfer.setToString(hs_tf));         
        
        String answeredProblems = (String)((SmartChoiceForm) form).getAnsweredProblems();
        String correctAnswers = (String)((SmartChoiceForm) form).getCorrectAnswers();
        int intAnsweredProblems = Integer.parseInt(answeredProblems);
        int intCorrectAnswers = Integer.parseInt(correctAnswers);           
        pb2.setAnsweredProblems(intAnsweredProblems);
        pb2.setCorrectAnswers(intCorrectAnswers); 

        String totalScore = (String)((SmartChoiceForm) form).getTotalScore();
        String correctAnswers_low = (String)((SmartChoiceForm) form).getCorrectAnswers_low();
        String answeredProblems_low = (String)((SmartChoiceForm) form).getAnsweredProblems_low();
        int intTotalScore = Integer.parseInt(totalScore);
        int intCorrectAnswers_low = Integer.parseInt(correctAnswers_low);
        int intAnsweredProblems_low = Integer.parseInt(answeredProblems_low);                      
        pb2.setTotalScore(intTotalScore);
        pb2.setCorrectAnswers_low(intCorrectAnswers_low);
        pb2.setAnsweredProblems_low(intAnsweredProblems_low);   

        String correctAnswers_middle = (String)((SmartChoiceForm) form).getCorrectAnswers_middle();
        String answeredProblems_middle = (String)((SmartChoiceForm) form).getAnsweredProblems_middle();        
        int intCorrectAnswers_middle = Integer.parseInt(correctAnswers_middle);
        int intAnsweredProblems_middle = Integer.parseInt(answeredProblems_middle);       
        pb2.setCorrectAnswers_middle(intCorrectAnswers_middle);
        pb2.setAnsweredProblems_middle(intAnsweredProblems_middle); 

        String correctAnswers_high = (String)((SmartChoiceForm) form).getCorrectAnswers_high();
        String answeredProblems_high = (String)((SmartChoiceForm) form).getAnsweredProblems_high();        
        int intCorrectAnswers_high = Integer.parseInt(correctAnswers_high);
        int intAnsweredProblems_high = Integer.parseInt(answeredProblems_high);       
        pb2.setCorrectAnswers_high(intCorrectAnswers_high);
        pb2.setAnsweredProblems_high(intAnsweredProblems_high);       

        String times = (String)((SmartChoiceForm) form).getTimes();
        pb2.setTimes(times);

        session.setAttribute( Constants.PERSON_KEY, pb2); 
        request.setAttribute( Constants.PERSON_KEY, pb2);              

        // Forward control to the specified success URI
        return (mapping.findForward("ShowSmartChoice"));        
    }   
}

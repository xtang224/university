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

public final class RandomCheckAction extends Action {

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
        String strRandomNumber = (String)((RandomCheckForm) form).getRandomNumber();
        int randomNumber = Integer.parseInt(strRandomNumber);
        String inputAnswer = (String)((RandomCheckForm) form).getInputAnswer();
        inputAnswer = inputAnswer.trim();
        HttpSession session = request.getSession(true);
        String correctChoice = null;
        correctChoice = (String)((RandomCheckForm) form).getCorrectAnswer();
        String solution = null;
        solution = (String)((RandomCheckForm) form).getSolution();
        ProblemBean pb = new ProblemBean();
        ChoiceBean cb = new ChoiceBean();
        switch (randomNumber){
           case 0:
              pb.setCorrectChoice(correctChoice);
              pb.setSolution(solution);
              request.setAttribute( Constants.PROBLEM_KEY, pb); 
              session.setAttribute( Constants.PROBLEM_KEY, pb); 
              break;
           case 1:
              cb.setChoice(correctChoice);
              cb.setSolution(solution);
              request.setAttribute( Constants.CHOICE_KEY, cb); 
              session.setAttribute( Constants.CHOICE_KEY, cb);  
              break;
           default:
              break;
        }
        
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
        
        String answeredProblems = (String)((RandomCheckForm) form).getAnsweredProblems();
        String correctAnswers = (String)((RandomCheckForm) form).getCorrectAnswers();
        int intAnsweredProblems = Integer.parseInt(answeredProblems);
        int intCorrectAnswers = Integer.parseInt(correctAnswers);
        PersonBean pb2 = new PersonBean();
        intAnsweredProblems ++;
        pb2.setAnsweredProblems(intAnsweredProblems);
        if (inputAnswer.toLowerCase().equals(correctChoice.toLowerCase())){
           intCorrectAnswers ++;
        }
        pb2.setCorrectAnswers(intCorrectAnswers);

        String userName = (String)((RandomCheckForm) form).getUserName();
        String passWord = (String)((RandomCheckForm) form).getPassWord();
        String trueName = (String)((RandomCheckForm) form).getTrueName();
        pb2.setUserName(userName);
        pb2.setPassWord(passWord);  
        pb2.setTrueName(trueName);

        String questionUsedUp = (String)((RandomCheckForm) form).getQuestionUsedUp();
        pb2.setQuestionUsedUp(questionUsedUp);

        String times = (String)((RandomCheckForm) form).getTimes();
        pb2.setTimes(times); 

        //now get the remainSeconds
        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null; 
        Statement s = null;
        ResultSet rs = null;         
        try{
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
           s = myConnection.createStatement();
           rs = s.executeQuery("select remainSeconds from student_time where studentNo='" + userName + "'");
           int remainSeconds = 0;
           while (rs.next()){
              remainSeconds = rs.getInt("remainSeconds");              
           }
           pb2.setRemainSeconds(remainSeconds);
        }catch (SQLException ex){
           getServlet().log("Inside RandomCheckAction.java, process.", ex);
        }finally{
           try{
              if (rs != null) rs.close();
              if (s != null) s.close();
              if (myConnection != null) myConnection.close();
           }catch (SQLException e){
              getServlet().log("Inside RandomCheckAction.java, closing.", e);
           }
        }
   

        // Remove the Form Bean - don't need to carry values forward
        request.removeAttribute(mapping.getAttribute());

        session.setAttribute( Constants.PERSON_KEY, pb2);      
        request.setAttribute( Constants.PERSON_KEY, pb2);  

        String answeredHashSetStr = (String)((RandomCheckForm) form).getAnsweredHashSet();
        session.setAttribute( Constants.HASHSET_KEY, answeredHashSetStr);   
        request.setAttribute( Constants.HASHSET_KEY, answeredHashSetStr);  

        String answeredHashSetStr_tf = (String)((RandomCheckForm) form).getAnsweredHashSet_tf();
        session.setAttribute( Constants.HASHSET_TF_KEY, answeredHashSetStr_tf);   
        request.setAttribute( Constants.HASHSET_TF_KEY, answeredHashSetStr_tf);  
        /*
        String totalHashSetStr = (String)((RandomCheckForm) form).getTotalHashSet();
        session.setAttribute( Constants.HASHSET_TOTAL_KEY, totalHashSetStr);   
        request.setAttribute( Constants.HASHSET_TOTAL_KEY, totalHashSetStr);  

        String totalHashSetStr_tf = (String)((RandomCheckForm) form).getTotalHashSet_tf();
        session.setAttribute( Constants.HASHSET_TOTAL_TF_KEY, totalHashSetStr_tf);   
        request.setAttribute( Constants.HASHSET_TOTAL_TF_KEY, totalHashSetStr_tf); 
        */
        // Forward control to the specified success URI
        switch (randomNumber){
           case 0:
              return (mapping.findForward("ShowProblemSolution"));   
           case 1:
              return (mapping.findForward("ShowChoiceSolution"));  
           default:
              break;
        }
        return (mapping.findForward("ShowProblemSolution"));        
    }
}

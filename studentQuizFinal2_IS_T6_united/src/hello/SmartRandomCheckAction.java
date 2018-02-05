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

public final class SmartRandomCheckAction extends Action {

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
        String strRandomNumber = (String)((SmartRandomCheckForm) form).getRandomNumber();
        int randomNumber = Integer.parseInt(strRandomNumber);
        String inputAnswer = (String)((SmartRandomCheckForm) form).getInputAnswer();
        inputAnswer = inputAnswer.trim();
        HttpSession session = request.getSession(true);
        String correctChoice = null;
        correctChoice = (String)((SmartRandomCheckForm) form).getCorrectAnswer();
        String solution = null;
        solution = (String)((SmartRandomCheckForm) form).getSolution();
        String source = (String)((SmartRandomCheckForm) form).getSource();
        SmartProblemBean pb = new SmartProblemBean();
        SmartChoiceBean cb = new SmartChoiceBean();
        SmartMultipleProblemBean mpb = new SmartMultipleProblemBean();    
        FillBlankBean fbb = new FillBlankBean();
        FillDoubleBlankBean fdbb = new FillDoubleBlankBean();
        FillQuadBlankBean fqbb = new FillQuadBlankBean();
        TermMatchBean tmb = new TermMatchBean();

        String statement_1st = null;
        String statement_2nd = null;
        String statement_3rd = null;
        String statement_4th = null;
        String statement_5th = null;
        switch (randomNumber){
           case 0:
              pb.setCorrectChoice(correctChoice);
              pb.setSolution(solution);
              pb.setSource(source);
              request.setAttribute( Constants.PROBLEM_KEY, pb); 
              session.setAttribute( Constants.PROBLEM_KEY, pb); 
              break;
           case 1:
              cb.setChoice(correctChoice);
              cb.setSolution(solution);
              cb.setSource(source);
              request.setAttribute( Constants.CHOICE_KEY, cb); 
              session.setAttribute( Constants.CHOICE_KEY, cb);  
              break;
           case 2:
              mpb.setCorrectChoice(correctChoice);
              mpb.setSolution(solution);
              mpb.setSource(source);
              request.setAttribute( Constants.MULTIPLEPROBLEM_KEY, mpb); 
              session.setAttribute( Constants.MULTIPLEPROBLEM_KEY, mpb); 
              break;
           case 3:
              statement_1st = (String)((SmartRandomCheckForm) form).getStatement_1st();
              statement_2nd = (String)((SmartRandomCheckForm) form).getStatement_2nd();
              fbb.setStatement_1st(statement_1st);
              fbb.setStatement_2nd(statement_2nd);
              fbb.setSolution(solution);
              fbb.setSource(source);
              request.setAttribute( Constants.FILLBLANK_KEY, fbb); 
              session.setAttribute( Constants.FILLBLANK_KEY, fbb);  
              break;
           case 4:
              statement_1st = (String)((SmartRandomCheckForm) form).getStatement_1st();
              statement_2nd = (String)((SmartRandomCheckForm) form).getStatement_2nd();
              statement_3rd = (String)((SmartRandomCheckForm) form).getStatement_3rd();
              fdbb.setStatement_1st(statement_1st);
              fdbb.setStatement_2nd(statement_2nd);              
              fdbb.setStatement_3rd(statement_3rd);
              fdbb.setSource(source);
              request.setAttribute( Constants.FILLDOUBLEBLANK_KEY, fdbb); 
              session.setAttribute( Constants.FILLDOUBLEBLANK_KEY, fdbb);  
              break;
           case 5:
              tmb.setCorrectChoice(correctChoice);
              tmb.setSolution(solution);
              tmb.setSource(source);
              request.setAttribute( Constants.TERMMATCH_KEY, tmb); 
              session.setAttribute( Constants.TERMMATCH_KEY, tmb); 
              break;
           case 6:
              statement_1st = (String)((SmartRandomCheckForm) form).getStatement_1st();
              statement_2nd = (String)((SmartRandomCheckForm) form).getStatement_2nd();
              statement_3rd = (String)((SmartRandomCheckForm) form).getStatement_3rd();
              statement_4th = (String)((SmartRandomCheckForm) form).getStatement_4th();
              statement_5th = (String)((SmartRandomCheckForm) form).getStatement_5th();
              fqbb.setStatement_1st(statement_1st);
              fqbb.setStatement_2nd(statement_2nd);              
              fqbb.setStatement_3rd(statement_3rd);
              fqbb.setStatement_4th(statement_4th);
              fqbb.setStatement_5th(statement_5th);
              fqbb.setSource(source);
              request.setAttribute( Constants.FILLQUADBLANK_KEY, fqbb); 
              session.setAttribute( Constants.FILLQUADBLANK_KEY, fqbb);  
              break;
           default:
              break;
        }       
        
        /*
         * Having received and validated the data submitted
         * from the View, we now update the model
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
        PersonBean pb2 = new PersonBean();
        String userName = (String)((SmartRandomCheckForm) form).getUserName();
        String passWord = (String)((SmartRandomCheckForm) form).getPassWord();
        String trueName = (String)((SmartRandomCheckForm) form).getTrueName();
        pb2.setUserName(userName);
        pb2.setPassWord(passWord);  
        pb2.setTrueName((String)StringSetTransfer.nameMap.get(userName));
        pb2.setInputAnswer(inputAnswer);      

        String times = (String)((SmartRandomCheckForm) form).getTimes();
        pb2.setTimes(times);

        String answeredProblems = (String)((SmartRandomCheckForm) form).getAnsweredProblems();
        String correctAnswers = (String)((SmartRandomCheckForm) form).getCorrectAnswers();
        int intAnsweredProblems = Integer.parseInt(answeredProblems);
        int intCorrectAnswers = Integer.parseInt(correctAnswers);        

        intAnsweredProblems ++;
        pb2.setAnsweredProblems(intAnsweredProblems);        
       
        String totalScore = (String)((SmartRandomCheckForm) form).getTotalScore();
        String correctAnswers_low = (String)((SmartRandomCheckForm) form).getCorrectAnswers_low();
        String answeredProblems_low = (String)((SmartRandomCheckForm) form).getAnsweredProblems_low();
        String correctAnswers_middle = (String)((SmartRandomCheckForm) form).getCorrectAnswers_middle();
        String answeredProblems_middle = (String)((SmartRandomCheckForm) form).getAnsweredProblems_middle();
        String correctAnswers_high = (String)((SmartRandomCheckForm) form).getCorrectAnswers_high();
        String answeredProblems_high = (String)((SmartRandomCheckForm) form).getAnsweredProblems_high();
        double dbTotalScore = Double.parseDouble(totalScore);
        int intCorrectAnswers_low = Integer.parseInt(correctAnswers_low);
        int intAnsweredProblems_low = Integer.parseInt(answeredProblems_low);    
        int intCorrectAnswers_middle = Integer.parseInt(correctAnswers_middle);
        int intAnsweredProblems_middle = Integer.parseInt(answeredProblems_middle);
        int intCorrectAnswers_high = Integer.parseInt(correctAnswers_high);
        int intAnsweredProblems_high = Integer.parseInt(answeredProblems_high);
        boolean lastCorrect = false;
        String lastType = (String)((SmartRandomCheckForm) form).getLastType();

        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null;
        Statement s = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int intCurrentProblemId = 0;
        String strCurrentProblemId = null;
        String inputAnswer_lowerCase = inputAnswer.toLowerCase();
        String correctChoice_lowerCase = correctChoice.toLowerCase();        
        switch (randomNumber){
           case 0: //questions2
              if (correctChoice_lowerCase.equals(inputAnswer_lowerCase)){
                 intCorrectAnswers ++;
                 lastCorrect = true;  
              }

              //we want to record studentAnswer of questions2
              strCurrentProblemId = (String)((SmartRandomCheckForm) form).getId();
              intCurrentProblemId = Integer.parseInt(strCurrentProblemId);
              try{
                 dataSource = getDataSource(request);
                 myConnection = dataSource.getConnection();

  stmt = myConnection.prepareStatement("insert into usedQuestions2(Id,Type,Times,StudentNo,StudentAnswer,source) values(?,'L',?,?,?,?)");
               stmt.clearParameters();
               stmt.setInt(1, intCurrentProblemId);
               stmt.setString(2, times);
               stmt.setString(3, userName);
               stmt.setString(4, inputAnswer_lowerCase);
               stmt.setString(5, source);
               stmt.execute(); 
              }catch (SQLException sqle) {
                 getServlet().log("Connection.process", sqle);
              } finally {           
                 try {
                    if (rs != null) rs.close();
                    if (s != null) s.close();
                    if (stmt != null) stmt.close();
                    if (myConnection != null) myConnection.close();
                 } catch (SQLException e) {
                    getServlet().log("Connection.close", e);
                 }
              }
              break;
           case 1: //choices2
              if (correctChoice_lowerCase.equals(inputAnswer_lowerCase)){
                 intCorrectAnswers ++;
                 lastCorrect = true;  
              }

              //we want to record studentAnswer of choices2
              strCurrentProblemId = (String)((SmartRandomCheckForm) form).getId();
              intCurrentProblemId = Integer.parseInt(strCurrentProblemId);
              try{
                 dataSource = getDataSource(request);
                 myConnection = dataSource.getConnection();

  stmt = myConnection.prepareStatement("insert into usedChoices2(Id,Type,Times,StudentNo,StudentAnswer,source) values(?,'L',?,?,?,?)");
               stmt.clearParameters();
               stmt.setInt(1, intCurrentProblemId);
               stmt.setString(2, times);
               stmt.setString(3, userName);
               stmt.setString(4, inputAnswer_lowerCase);
               stmt.setString(5, source);
               stmt.execute(); 
              }catch (SQLException sqle) {
                 getServlet().log("Connection.process", sqle);
              } finally {           
                 try {
                    if (rs != null) rs.close();
                    if (s != null) s.close();
                    if (stmt != null) stmt.close();
                    if (myConnection != null) myConnection.close();
                 } catch (SQLException e) {
                    getServlet().log("Connection.close", e);
                 }
              }
              break;
           case 2: //multiplequestions2 
              char[] inputAnswer_lowerCase_Arr = inputAnswer_lowerCase.toCharArray();
              Arrays.sort(inputAnswer_lowerCase_Arr);
              String temp = new String(inputAnswer_lowerCase_Arr);
              //assuming in multiple choices, correctChoice takes the correct order
              if (temp.equals(correctChoice_lowerCase)){
                 intCorrectAnswers ++;
                 lastCorrect = true;          
              }

              //we want to record studentAnswer of multipleQuestions2
              strCurrentProblemId = (String)((SmartRandomCheckForm) form).getId();
              intCurrentProblemId = Integer.parseInt(strCurrentProblemId);
              try{
                 dataSource = getDataSource(request);
                 myConnection = dataSource.getConnection();

  stmt = myConnection.prepareStatement("insert into usedMultipleQuestions2(Id,Type,Times,StudentNo,StudentAnswer,source) values(?,'H',?,?,?,?)");
               stmt.clearParameters();
               stmt.setInt(1, intCurrentProblemId);
               stmt.setString(2, times);
               stmt.setString(3, userName);
               stmt.setString(4, inputAnswer_lowerCase);
               stmt.setString(5, source);
               stmt.execute(); 
              }catch (SQLException sqle) {
                 getServlet().log("Connection.process", sqle);
              } finally {           
                 try {
                    if (rs != null) rs.close();
                    if (s != null) s.close();
                    if (stmt != null) stmt.close();
                    if (myConnection != null) myConnection.close();
                 } catch (SQLException e) {
                    getServlet().log("Connection.close", e);
                 }
              }
              break;              
           case 3: //fillblank2
              /*
              int indexOfAnswer = correctChoice_lowerCase.indexOf(inputAnswer_lowerCase);
              getServlet().log("correctChoice_lowerCase = " + correctChoice_lowerCase);
              getServlet().log("inputAnswer_lowerCase = " + inputAnswer_lowerCase);
              getServlet().log("indexOfAnswer = " + indexOfAnswer);
         if ((!inputAnswer_lowerCase.equals("")) && indexOfAnswer >=0 && indexOfAnswer < correctChoice_lowerCase.length()){
                 intCorrectAnswers ++;
                 lastCorrect = true;  
              }
              */

              String[] correctChoices = correctChoice_lowerCase.split("/");
              for (int i = 0; i < correctChoices.length; i++){
                 System.out.println("correctChoices[" + i + "]=" + correctChoices[i]);
                 if ((!inputAnswer_lowerCase.equals("")) && inputAnswer_lowerCase.equals(correctChoices[i])){
                    intCorrectAnswers ++;
                    lastCorrect = true;
                    break;
                 }
              }
 
              //to be able to recheck the solutions of fillblank2, we want to record the student answer
              strCurrentProblemId = (String)((SmartRandomCheckForm) form).getId();
              intCurrentProblemId = Integer.parseInt(strCurrentProblemId);
              getServlet().log("In fillBlank2, intCurrentProblemId = " + intCurrentProblemId);
              try{
                 dataSource = getDataSource(request);
                 myConnection = dataSource.getConnection();

  stmt = myConnection.prepareStatement("insert into usedFillBlank2(Id,Type,Times,StudentNo,StudentAnswer,source) values(?,'M',?,?,?,?)");
               stmt.clearParameters();
               stmt.setInt(1, intCurrentProblemId);
               stmt.setString(2, times);
               stmt.setString(3, userName);
               stmt.setString(4, inputAnswer_lowerCase);
               stmt.setString(5, source);
               stmt.execute(); 
              }catch (SQLException sqle) {
                 getServlet().log("Connection.process", sqle);
              } finally {           
                 try {
                    if (rs != null) rs.close();
                    if (s != null) s.close();
                    if (stmt != null) stmt.close();
                    if (myConnection != null) myConnection.close();
                 } catch (SQLException e) {
                    getServlet().log("Connection.close", e);
                 }
              }
              break;
           case 4: //filldoubleblanks2
              int indexOfColon = inputAnswer_lowerCase.indexOf(";");
              int indexOfColon2 = inputAnswer_lowerCase.indexOf("��"); 
              String inputAnswer_1st = null;
              String inputAnswer_2nd = null;
              if (indexOfColon >=0 && indexOfColon < inputAnswer_lowerCase.length()){
                 inputAnswer_1st = inputAnswer_lowerCase.substring(0, indexOfColon);
                 inputAnswer_2nd = inputAnswer_lowerCase.substring(indexOfColon + 1);
              }else if(indexOfColon2 >=0 && indexOfColon2 < inputAnswer_lowerCase.length()){
                 inputAnswer_1st = inputAnswer_lowerCase.substring(0, indexOfColon2);
                 inputAnswer_2nd = inputAnswer_lowerCase.substring(indexOfColon2 + 1);
              }else{
                 inputAnswer_1st = "wrong";
                 inputAnswer_2nd = "wrong";
              }
              inputAnswer_1st = inputAnswer_1st.trim();
              inputAnswer_2nd = inputAnswer_2nd.trim();
              indexOfColon = correctChoice_lowerCase.indexOf(";");
              String correctChoice_1st = correctChoice_lowerCase.substring(0, indexOfColon);
              String correctChoice_2nd = correctChoice_lowerCase.substring(indexOfColon + 1);

              fdbb.setSolution_1st(correctChoice_1st);
              fdbb.setSolution_2nd(correctChoice_2nd);
              request.setAttribute( Constants.FILLDOUBLEBLANK_KEY, fdbb); 
              session.setAttribute( Constants.FILLDOUBLEBLANK_KEY, fdbb); 
              /*
              int index1 = correctChoice_1st.indexOf(inputAnswer_1st);
              int index2 = correctChoice_2nd.indexOf(inputAnswer_2nd);
              if ((!inputAnswer_1st.equals("")) && index1 >= 0 && index1 < correctChoice_1st.length()){
                 if ((!inputAnswer_2nd.equals("")) && index2 >= 0 && index2 < correctChoice_2nd.length()){
                    intCorrectAnswers ++;
                    lastCorrect = true; 
                 }         
              }
              */
              String[] solution_lower_1sts = correctChoice_1st.split("/");
              String[] solution_lower_2nds = correctChoice_2nd.split("/");
              outer:
              for (int i = 0; i < solution_lower_1sts.length; i++){
                 for (int j = 0; j < solution_lower_2nds.length; j++){
                    if (!inputAnswer_1st.equals("") && inputAnswer_1st.equals(solution_lower_1sts[i])){
                       if (!inputAnswer_2nd.equals("") && inputAnswer_2nd.equals(solution_lower_2nds[j])){
                          intCorrectAnswers ++;
                          lastCorrect = true; 
                          break outer;
                       }   
                    }
                 }
              }        

              //to be able to recheck the solutions of fillDoubleblanks2, we want to record the student answer
              strCurrentProblemId = (String)((SmartRandomCheckForm) form).getId();
              intCurrentProblemId = Integer.parseInt(strCurrentProblemId);
              getServlet().log("In fillDoubleBlanks2, intCurrentProblemId = " + intCurrentProblemId);
              try{
                 dataSource = getDataSource(request);
                 myConnection = dataSource.getConnection();

stmt = myConnection.prepareStatement("insert into usedFillDoubleBlanks2(Id,Type,Times,StudentNo,StudentAnswer,source) values(?,'H',?,?,?,?)");
               stmt.clearParameters();
               stmt.setInt(1, intCurrentProblemId);
               stmt.setString(2, times);
               stmt.setString(3, userName);
               stmt.setString(4, inputAnswer_lowerCase);
               stmt.setString(5, source);
               stmt.execute(); 
              }catch (SQLException sqle) {
                 getServlet().log("Connection.process", sqle);
              } finally {           
                 try {
                    if (rs != null) rs.close();
                    if (s != null) s.close();
                    if (stmt != null) stmt.close();
                    if (myConnection != null) myConnection.close();
                 } catch (SQLException e) {
                    getServlet().log("Connection.close", e);
                 }
              }
              break;
           case 5: //termMatch2
              System.out.println("inside SmartRandomCheckAction.java, termMatch2 is reached, and inputAnswer_lowerCase = " + inputAnswer_lowerCase);
              if (correctChoice_lowerCase.equals(inputAnswer_lowerCase)){
                 intCorrectAnswers ++;
                 lastCorrect = true;  
                 //hs_tm_correct.add(new Integer(intCurrentProblemId)); 
              }
              
              //we want to record studentAnswer of termMatch2    
              strCurrentProblemId = (String)((SmartRandomCheckForm) form).getId();
              intCurrentProblemId = Integer.parseInt(strCurrentProblemId);          
              try{
                 dataSource = getDataSource(request);
                 myConnection = dataSource.getConnection();
             
                 stmt = myConnection.prepareStatement("insert into usedTermMatch2(Id,Type,Times,StudentNo,StudentAnswer,source) values(?,'M',?,?,?,?)");
                 stmt.clearParameters();
                 stmt.setInt(1, intCurrentProblemId);
                 stmt.setString(2, times);
                 stmt.setString(3, userName);
                 stmt.setString(4, inputAnswer_lowerCase);
                 stmt.setString(5, source);
                 stmt.execute(); 

                 /*
                 stmt = myConnection.prepareStatement("update usedTermMatch2 set StudentAnswer=? where studentNo=? and id=?");
                 stmt.clearParameters();
                 stmt.setString(1, inputAnswer_lowerCase);
                 stmt.setString(2, userName);   
                 stmt.setInt(3, intCurrentProblemId);                
                 stmt.executeUpdate(); 
                 */
              }catch (SQLException sqle) {
                 getServlet().log("Connection.process", sqle);
              } finally {           
                 try {
                    if (rs != null) rs.close();
                    if (s != null) s.close();
                    if (stmt != null) stmt.close();
                    if (myConnection != null) myConnection.close();
                 } catch (SQLException e) {
                    getServlet().log("Connection.close", e);
                 }
              }
              break; 
           case 6: //fillquadblanks2
              indexOfColon = inputAnswer_lowerCase.indexOf(";");
              indexOfColon2 = inputAnswer_lowerCase.indexOf("；"); 
              String[] inputAnswers = null;              
              if (indexOfColon >=0 && indexOfColon < inputAnswer_lowerCase.length()){
                 inputAnswers = inputAnswer_lowerCase.split(";");                 
              }else if(indexOfColon2 >=0 && indexOfColon2 < inputAnswer_lowerCase.length()){
                 inputAnswers = inputAnswer_lowerCase.split("；"); 
              }else{
                 inputAnswers = new String[4];
                 for (int i=0; i<4; i++)
                    inputAnswers[i] = "Wrong";
              }
              
              indexOfColon = correctChoice_lowerCase.indexOf(";");
              correctChoices = correctChoice_lowerCase.split(";");

              fqbb.setSolution_1st(correctChoices[0]);
              fqbb.setSolution_2nd(correctChoices[1]);
              fqbb.setSolution_3rd(correctChoices[2]);
              fqbb.setSolution_4th(correctChoices[3]);
              request.setAttribute( Constants.FILLQUADBLANK_KEY, fqbb); 
              session.setAttribute( Constants.FILLQUADBLANK_KEY, fqbb);               
             
              lastCorrect = true;
              outer:
              for (int i = 0; i < correctChoices.length; i++){
                 String tmpCorrectAnswer = (String)correctChoices[i];                 
                 if (!StringSetTransfer.match(tmpCorrectAnswer, inputAnswers[i])){                     
                     lastCorrect = false; 
                     break outer;
                 }                  
              } 
              if (lastCorrect) 
                  intCorrectAnswers ++;      

              //to be able to recheck the solutions of fillDoubleblanks2, we want to record the student answer
              strCurrentProblemId = (String)((SmartRandomCheckForm) form).getId();
              intCurrentProblemId = Integer.parseInt(strCurrentProblemId);
              System.out.println("In fillQuadBlanks2, intCurrentProblemId = " + intCurrentProblemId);
              int existCount = 0;
              try{
                 dataSource = getDataSource(request);
                 myConnection = dataSource.getConnection();

                 s = myConnection.createStatement();
                 rs = s.executeQuery("select id from usedFillQuadBlanks2");
                 while(rs.next()){
                    existCount++; 
                 }
                 existCount++;
                
               stmt = myConnection.prepareStatement("insert into usedFillQuadBlanks2(Id,problemId,Type,Times,StudentNo,StudentAnswer,source) values(?,?,'H',?,?,?,?)");
               stmt.clearParameters();
               stmt.setInt(1, existCount);
               stmt.setInt(2, intCurrentProblemId);
               stmt.setString(3, times);
               stmt.setString(4, userName);
               stmt.setString(5, inputAnswer_lowerCase);
               stmt.setString(6, source);
               stmt.execute(); 
              }catch (SQLException sqle) {
                 getServlet().log("Connection.process", sqle);
              } finally {           
                 try {
                    if (rs != null) rs.close();
                    if (s != null) s.close();
                    if (stmt != null) stmt.close();
                    if (myConnection != null) myConnection.close();
                 } catch (SQLException e) {
                    getServlet().log("Connection.close", e);
                 }
              }
              break;  
           default:
              break;
        }

        
        //StringSetTransfer stringSetTransfer = new StringSetTransfer(userName);
        double[] result =  StringSetTransfer.rate(lastCorrect, lastType.charAt(0));         
        dbTotalScore += result[0];        
        intAnsweredProblems_low += (int)result[1];
        intCorrectAnswers_low += (int)result[2];
        intAnsweredProblems_middle += (int)result[3];
        intCorrectAnswers_middle += (int)result[4]; 
        intAnsweredProblems_high += (int)result[5];       
        intCorrectAnswers_high += (int)result[6];        

        pb2.setCorrectAnswers(intCorrectAnswers);
        pb2.setLastCorrect(lastCorrect);
        pb2.setTotalScore(dbTotalScore);
        pb2.setCorrectAnswers_low(intCorrectAnswers_low);
        pb2.setAnsweredProblems_low(intAnsweredProblems_low);
        pb2.setCorrectAnswers_middle(intCorrectAnswers_middle);
        pb2.setAnsweredProblems_middle(intAnsweredProblems_middle);
        pb2.setCorrectAnswers_high(intCorrectAnswers_high);
        pb2.setAnsweredProblems_high(intAnsweredProblems_high);        
        
        String thisType = (String)((SmartRandomCheckForm) form).getThisType();
        String continueRight = (String)((SmartRandomCheckForm) form).getContinueRight();
        String continueWrong = (String)((SmartRandomCheckForm) form).getContinueWrong();
        int intContinueRight = Integer.parseInt(continueRight);
        int intContinueWrong = Integer.parseInt(continueWrong);       
        pb2.setLastType(lastType);
        pb2.setThisType(thisType);
        pb2.setContinueRight(intContinueRight);
        pb2.setContinueWrong(intContinueWrong);

        String neverHigh = (String)((SmartRandomCheckForm) form).getNeverHigh();
        boolean boolNeverHigh = Boolean.parseBoolean(neverHigh);
        pb2.setNeverHigh(boolNeverHigh);

        String planStatus = (String)((SmartRandomCheckForm) form).getPlanStatus();
        pb2.setPlanStatus(planStatus);
               
        //now get the remainSeconds
        //javax.sql.DataSource dataSource = null;
        //java.sql.Connection myConnection = null; 
        //Statement s = null;
        //ResultSet rs = null;         
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
           pb2.setRemainSeconds(1800); //now we want this demonstration program not to end
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

        String answeredHashSetStr = (String)((SmartRandomCheckForm) form).getAnsweredHashSet();
        session.setAttribute( Constants.HASHSET_KEY, answeredHashSetStr);   
        request.setAttribute( Constants.HASHSET_KEY, answeredHashSetStr);  

        String answeredHashSetStr_tf = (String)((SmartRandomCheckForm) form).getAnsweredHashSet_tf();
        session.setAttribute( Constants.HASHSET_TF_KEY, answeredHashSetStr_tf);   
        request.setAttribute( Constants.HASHSET_TF_KEY, answeredHashSetStr_tf);  

        String answeredHashSetStr_m = (String)((SmartRandomCheckForm) form).getAnswered_M_HashSet();
        session.setAttribute( Constants.HASHSET_M_KEY, answeredHashSetStr_m);   
        request.setAttribute( Constants.HASHSET_M_KEY, answeredHashSetStr_m); 

        String answeredHashSetStr_fb = (String)((SmartRandomCheckForm) form).getAnsweredHashSet_fb();
        session.setAttribute( Constants.HASHSET_FB_KEY, answeredHashSetStr_fb);   
        request.setAttribute( Constants.HASHSET_FB_KEY, answeredHashSetStr_fb); 

        String answeredHashSetStr_fdb = (String)((SmartRandomCheckForm) form).getAnsweredHashSet_fdb();
        session.setAttribute( Constants.HASHSET_FDB_KEY, answeredHashSetStr_fdb);   
        request.setAttribute( Constants.HASHSET_FDB_KEY, answeredHashSetStr_fdb); 

        String answeredHashSetStr_fqb = (String)((SmartRandomCheckForm) form).getAnsweredHashSet_fqb();
        session.setAttribute( Constants.HASHSET_FQB_KEY, answeredHashSetStr_fqb);   
        request.setAttribute( Constants.HASHSET_FQB_KEY, answeredHashSetStr_fqb); 

        String answeredHashSetStr_tm = (String)((SmartRandomCheckForm) form).getAnsweredHashSet_tm();
        session.setAttribute( Constants.HASHSET_TM_KEY, answeredHashSetStr_tm);   
        request.setAttribute( Constants.HASHSET_TM_KEY, answeredHashSetStr_tm); 

        getServlet().log("SmartRandomCheckAction, hs string is " + answeredHashSetStr);
        getServlet().log("SmartRandomCheckAction, hs_tf str is " + answeredHashSetStr_tf);
        getServlet().log("SmartRandomCheckAction, mhs str is " + answeredHashSetStr_m);   
        getServlet().log("SmartRandomCheckAction, hs_fb str is " + answeredHashSetStr_fb); 
        getServlet().log("SmartRandomCheckAction, hs_fdb str is " + answeredHashSetStr_fdb);        
        getServlet().log("SmartRandomCheckAction, hs_fqb str is " + answeredHashSetStr_fqb);        
        getServlet().log("SmartRandomCheckAction, hs_tm str is " + answeredHashSetStr_tm);       
        
        // Forward control to the specified success URI
        switch (randomNumber){
           case 0:
              return (mapping.findForward("ShowSmartProblemSolution"));   
           case 1:
              return (mapping.findForward("ShowSmartChoiceSolution"));   
           case 2:
              return (mapping.findForward("ShowSmartMultipleProblemSolution")); 
           case 3:
              return (mapping.findForward("ShowFillBlankSolution")); 
           case 4:
              return (mapping.findForward("ShowFillDoubleBlankSolution")); 
           case 5:
              return (mapping.findForward("ShowTermMatchSolution")); 
           case 6:
              return (mapping.findForward("ShowFillQuadBlankSolution"));
           default:
              break;
        } 
        return (mapping.findForward("ShowSmartProblemSolution"));        
    }
}

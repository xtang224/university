package hello;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;

import util.*;

public class TermMatchServlet extends HttpServlet {  

  public void init() throws ServletException {}

  public void doPost(HttpServletRequest _req, HttpServletResponse _res)
    throws ServletException, IOException {
    HttpSession session = _req.getSession(true);
    PersonBean _pb2 = (PersonBean)session.getAttribute(Constants.PERSON_KEY);        
    session.setAttribute(Constants.PERSON_KEY, _pb2);       
    _req.setAttribute(Constants.PERSON_KEY, _pb2);  
    int remainSeconds = _pb2.getRemainSeconds();

    TermMatchBean _tmb = (TermMatchBean)session.getAttribute(Constants.TERMMATCH_KEY);
    
    String _hs = (String)_req.getAttribute(Constants.HASHSET_KEY);
    String _hs_tf = (String)_req.getAttribute(Constants.HASHSET_TF_KEY);
    String _mhs = (String)_req.getAttribute(Constants.HASHSET_M_KEY);
    String _hs_fb = (String)_req.getAttribute(Constants.HASHSET_FB_KEY);
    String _hs_fdb = (String)_req.getAttribute(Constants.HASHSET_FDB_KEY);
    String _hs_tm = (String)_req.getAttribute(Constants.HASHSET_TM_KEY);
    
    System.out.println("inside SmartProblemServlet, _pb2.getMemberExist() = " + _pb2.getMemberExist());   

    _res.setContentType("text/html;charset=utf-8");
    PrintWriter out = _res.getWriter(); 
   
    out.println("<html>");    
    out.println("<head>");
    out.println("<title>Final Exam</title>");   
    out.println("</head>");
    out.println("<body bgcolor=\"white\"><p>");
    //out.println("<h2>" + _pb2.getCautionNote() + "</h2><p>");

    out.println("<h2>Question " + _tmb.getId() + "</h2><p>");
    out.println("<h2>" + _tmb.getType() + "</h2><p>");
    out.println("<h2>" + _tmb.getStatement() + "</h2><p>");
    out.println("<h2>" + _tmb.getChoiceA() + "</h2><p>");
    out.println("<h2>" + _tmb.getChoiceB() + "</h2><p>");
    out.println("<h2>" + _tmb.getChoiceC() + "</h2><p>");
    out.println("<h2>" + _tmb.getChoiceD() + "</h2><p>");
    

    if (remainSeconds > 0){
       out.println("<h2>Selected Student:" + _pb2.getUserName() + "::" + _pb2.getTrueName() + "</h2><br>");
       out.println("Please enter only one letter of the above four choices(A, B, C or D), which best matches the term, then click the button to submit.<br>");
       out.println("<form action=\"SmartRandomCheck.do\" >");

       out.println("<input type=\"text\" name=\"inputAnswer\" size=\"16\" maxlength=\"16\" /><br>");
       out.println("<input type=\"hidden\" name=\"correctAnswer\" value=\"" + _tmb.getCorrectChoice() + "\" />");
       out.println("<input type=\"hidden\" name=\"solution\" value=\"" + _tmb.getSolution() + "\" />");
       out.println("<input type=\"hidden\" name=\"source\" value=\"" + _tmb.getSource() + "\" />");       
      
       out.println("<input type=\"hidden\" name=\"answeredProblems\" value=\"" + _pb2.getAnsweredProblems() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"correctAnswers\" value=\"" + _pb2.getCorrectAnswers() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"totalScore\" value=\"" + _pb2.getTotalScore() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"correctAnswers_low\" value=\"" + _pb2.getCorrectAnswers_low() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"answeredProblems_low\" value=\"" + _pb2.getAnsweredProblems_low() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"correctAnswers_middle\" value=\"" + _pb2.getCorrectAnswers_middle() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"answeredProblems_middle\" value=\"" + _pb2.getAnsweredProblems_middle() + "\" />");
       out.println("<input type=\"hidden\" name=\"correctAnswers_high\" value=\"" + _pb2.getCorrectAnswers_high() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"answeredProblems_high\" value=\"" + _pb2.getAnsweredProblems_high() + "\" />");
       
       out.println("<input type=\"hidden\" name=\"answeredHashSet\" value=\"" + _hs + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_tf\" value=\"" + _hs_tf + "\" />");
       out.println("<input type=\"hidden\" name=\"answered_M_HashSet\" value=\"" + _mhs + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_fb\" value=\"" + _hs_fb + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_fdb\" value=\"" + _hs_fdb + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_tm\" value=\"" + _hs_tm + "\" />");
       
       out.println("<input type=\"hidden\" name=\"userName\" value=\"" + _pb2.getUserName() + "\" />");
       out.println("<input type=\"hidden\" name=\"passWord\" value=\"" + _pb2.getPassWord() + "\" />");
       out.println("<input type=\"hidden\" name=\"trueName\" value=\"" + _pb2.getTrueName() + "\" />");
       out.println("<input type=\"hidden\" name=\"lastType\" value=\"" + _pb2.getLastType() + "\" />");
       out.println("<input type=\"hidden\" name=\"thisType\" value=\"" + _pb2.getThisType() + "\" />");
       out.println("<input type=\"hidden\" name=\"times\" value=\"" + _pb2.getTimes() + "\" />");
       out.println("<input type=\"hidden\" name=\"randomNumber\" value=\"" + _pb2.getRandomNumber() + "\" />");
       out.println("<input type=\"hidden\" name=\"id\" value=\"" + _pb2.getCurrentProblemId() + "\" />");  
       out.println("<input type=\"hidden\" name=\"planStatus\" value=\"" + _pb2.getPlanStatus() + "\" />");   
       out.println("<input type=\"hidden\" name=\"memberExist\" value=\"" + _pb2.getMemberExist() + "\" />");

       out.println("<input type=\"Submit\" value=\"Submit\" />");
       out.println("<input type=\"Reset\" value=\"Reset\" />");    
       out.println("</form><br>");

       //For Skip
       out.println("If you want to skip this problem and do it later, then you can click the skip button, the skipped problem will come back later.<br>");
       out.println("<form action=\"SmartRandomCheck2.do\" >");
       
       //out.println("<input type=\"text\" name=\"inputAnswer\" size=\"16\" maxlength=\"16\" /><br>");
       out.println("<input type=\"hidden\" name=\"correctAnswer\" value=\"" + _tmb.getCorrectChoice() + "\" />");
       out.println("<input type=\"hidden\" name=\"solution\" value=\"" + _tmb.getSolution() + "\" />");
       out.println("<input type=\"hidden\" name=\"source\" value=\"" + _tmb.getSource() + "\" />");       
      
       out.println("<input type=\"hidden\" name=\"answeredProblems\" value=\"" + _pb2.getAnsweredProblems() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"correctAnswers\" value=\"" + _pb2.getCorrectAnswers() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"totalScore\" value=\"" + _pb2.getTotalScore() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"correctAnswers_low\" value=\"" + _pb2.getCorrectAnswers_low() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"answeredProblems_low\" value=\"" + _pb2.getAnsweredProblems_low() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"correctAnswers_middle\" value=\"" + _pb2.getCorrectAnswers_middle() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"answeredProblems_middle\" value=\"" + _pb2.getAnsweredProblems_middle() + "\" />");
       out.println("<input type=\"hidden\" name=\"correctAnswers_high\" value=\"" + _pb2.getCorrectAnswers_high() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"answeredProblems_high\" value=\"" + _pb2.getAnsweredProblems_high() + "\" />");
       
       out.println("<input type=\"hidden\" name=\"answeredHashSet\" value=\"" + _hs + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_tf\" value=\"" + _hs_tf + "\" />");
       out.println("<input type=\"hidden\" name=\"answered_M_HashSet\" value=\"" + _mhs + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_fb\" value=\"" + _hs_fb + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_fdb\" value=\"" + _hs_fdb + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_tm\" value=\"" + _hs_tm + "\" />");
       
       out.println("<input type=\"hidden\" name=\"userName\" value=\"" + _pb2.getUserName() + "\" />");
       out.println("<input type=\"hidden\" name=\"passWord\" value=\"" + _pb2.getPassWord() + "\" />");
       out.println("<input type=\"hidden\" name=\"trueName\" value=\"" + _pb2.getTrueName() + "\" />");
       out.println("<input type=\"hidden\" name=\"lastType\" value=\"" + _pb2.getLastType() + "\" />");
       out.println("<input type=\"hidden\" name=\"thisType\" value=\"" + _pb2.getThisType() + "\" />");
       out.println("<input type=\"hidden\" name=\"times\" value=\"" + _pb2.getTimes() + "\" />");
       out.println("<input type=\"hidden\" name=\"randomNumber\" value=\"" + _pb2.getRandomNumber() + "\" />");
       out.println("<input type=\"hidden\" name=\"id\" value=\"" + _pb2.getCurrentProblemId() + "\" />");  
       out.println("<input type=\"hidden\" name=\"planStatus\" value=\"" + _pb2.getPlanStatus() + "\" />");   
       out.println("<input type=\"hidden\" name=\"memberExist\" value=\"" + _pb2.getMemberExist() + "\" />");

       out.println("<input type=\"Submit\" value=\"Skip this problem\" />");
       out.println("<input type=\"Reset\" value=\"Reset\" />");    
       out.println("</form><br>");


    }else{
       out.println("<center><h2>Your time is used up. Please click the following submit button to exit</h2></center><br>");
       out.println("<center>");
       out.println("<form action=\"SmartQuit.do\" >");
       
       out.println("<input type=\"hidden\" name=\"answeredHashSet\" value=\"" + _hs + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_tf\" value=\"" + _hs_tf + "\" />");
       out.println("<input type=\"hidden\" name=\"answered_M_HashSet\" value=\"" + _mhs + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_fb\" value=\"" + _hs_fb + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_fdb\" value=\"" + _hs_fdb + "\" />");
       out.println("<input type=\"hidden\" name=\"answeredHashSet_tm\" value=\"" + _hs_tm + "\" />");
       
       out.println("<input type=\"hidden\" name=\"userName\" value=\"" + _pb2.getUserName() + "\" />");
       out.println("<input type=\"hidden\" name=\"passWord\" value=\"" + _pb2.getPassWord() + "\" />");
       out.println("<input type=\"hidden\" name=\"trueName\" value=\"" + _pb2.getTrueName() + "\" />");

       out.println("<input type=\"hidden\" name=\"answeredProblems\" value=\"" + _pb2.getAnsweredProblems() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"correctAnswers\" value=\"" + _pb2.getCorrectAnswers() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"totalScore\" value=\"" + _pb2.getTotalScore() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"correctAnswers_low\" value=\"" + _pb2.getCorrectAnswers_low() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"answeredProblems_low\" value=\"" + _pb2.getAnsweredProblems_low() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"correctAnswers_middle\" value=\"" + _pb2.getCorrectAnswers_middle() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"answeredProblems_middle\" value=\"" + _pb2.getAnsweredProblems_middle() + "\" />");
       out.println("<input type=\"hidden\" name=\"correctAnswers_high\" value=\"" + _pb2.getCorrectAnswers_high() + "\" />"); 
       out.println("<input type=\"hidden\" name=\"answeredProblems_high\" value=\"" + _pb2.getAnsweredProblems_high() + "\" />");

       out.println("<input type=\"hidden\" name=\"times\" value=\"" + _pb2.getTimes() + "\" />");
       out.println("<input type=\"hidden\" name=\"planStatus\" value=\"" + _pb2.getPlanStatus() + "\" />");   
       out.println("<input type=\"hidden\" name=\"memberExist\" value=\"" + _pb2.getMemberExist() + "\" />");

       out.println("<input type=\"Submit\" value=\"Submit\" />");    
       out.println("<input type=\"Reset\" value=\"Reset\" />");    
       out.println("</form></center><br>");
    }
 
    out.println("<html:img page=\"/struts-power.gif\" alt=\"Powered by Struts\"/>");
    out.println("</body>");
    out.println("</html>");
    out.close();    
  }

  public void doGet(HttpServletRequest _req, HttpServletResponse _res)
    throws ServletException, IOException {
    doPost(_req, _res); 
  }

  public void destroy() {}

}

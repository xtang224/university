package hello;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;

import util.*;

public class TermMatchCheckServlet extends HttpServlet {  

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
    
    System.out.println("inside TermMatchCheckServlet, _pb2.getMemberExist() = " + _pb2.getMemberExist());  

    String lastCorrect = null;
    if (_pb2.getLastCorrect()){
       lastCorrect = "true";
    }else{
       lastCorrect = "false";
    }  

    _res.setContentType("text/html;charset=utf-8");
    PrintWriter out = _res.getWriter(); 
   
    out.println("<html>");    
    out.println("<head>");
    out.println("<title>Final Exam</title>");   
    out.println("</head>");
    out.println("<body bgcolor=\"white\"><p>");
    out.println("<h2>Hello " + StringSetTransfer.nameMap.get(_pb2.getUserName()) + "</h2><p>");

    out.println("<h2>" + _pb2.getCautionNote() + "</h2><p>");

    out.println("<h2>The Correct Solution Is:" + _tmb.getCorrectChoice() + "</h2><p>");
    out.println("<h2>The Reason Is:" + _tmb.getSolution() + "</h2><p>");
    out.println("<h2>You Answer Is:" + _pb2.getInputAnswer() + "</h2><p>");
    out.println("<h2>The Number of Problems You Have Answered Is:" + _pb2.getAnsweredProblems() + "</h2><p>");
    out.println("<h2>Your remaining seconds are:" + _pb2.getRemainSeconds() + "</h2><p>");    

    if (remainSeconds > 0){
       out.println("Please enter the score of the selected student, and then click the submit button to continue");
       out.println("<form action=\"SmartRandom2.do\" >");
       out.println("<input type=\"text\" name=\"updateScore\" size=\"16\" maxlength=\"16\" /><br>");
       
       out.println("<input type=\"hidden\" name=\"first\" value=\"false\" />");       
      
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
       out.println("<input type=\"hidden\" name=\"trueName\" value=\"" + StringSetTransfer.nameMap.get(_pb2.getUserName()) + "\" />");
       out.println("<input type=\"hidden\" name=\"lastCorrect\" value=\"" + lastCorrect + "\" />");
       out.println("<input type=\"hidden\" name=\"lastType\" value=\"" + _pb2.getLastType() + "\" />");
       out.println("<input type=\"hidden\" name=\"thisType\" value=\"" + _pb2.getThisType() + "\" />");
       out.println("<input type=\"hidden\" name=\"times\" value=\"" + _pb2.getTimes() + "\" />");       
       out.println("<input type=\"hidden\" name=\"planStatus\" value=\"" + _pb2.getPlanStatus() + "\" />");   
       out.println("<input type=\"hidden\" name=\"memberExist\" value=\"" + _pb2.getMemberExist() + "\" />");

       out.println("<input type=\"Submit\" value=\"Submit\" />");
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
       out.println("<input type=\"hidden\" name=\"trueName\" value=\"" + StringSetTransfer.nameMap.get(_pb2.getUserName()) + "\" />");

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

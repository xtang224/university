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

public final class SmartChoiceCheckAction extends Action {

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
        String inputAnswer = (String)((SmartChoiceCheckForm) form).getInputAnswer();
        HttpSession session = request.getSession(true);
        String correctChoice = null;
        correctChoice = (String)((SmartChoiceCheckForm) form).getCorrectAnswer();
        String solution = null;
        solution = (String)((SmartChoiceCheckForm) form).getSolution();
        SmartChoiceBean cb = new SmartChoiceBean();
        cb.setChoice(correctChoice);
        cb.setSolution(solution);
        request.setAttribute( Constants.CHOICE_KEY, cb); 
        session.setAttribute( Constants.CHOICE_KEY, cb);  
       
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
        
        String answeredProblems = (String)((SmartChoiceCheckForm) form).getAnsweredProblems();
        String correctAnswers = (String)((SmartChoiceCheckForm) form).getCorrectAnswers();
        int intAnsweredProblems = Integer.parseInt(answeredProblems);
        int intCorrectAnswers = Integer.parseInt(correctAnswers);
        PersonBean pb2 = new PersonBean();
        intAnsweredProblems ++;
        pb2.setAnsweredProblems(intAnsweredProblems);        
       
        String totalScore = (String)((SmartChoiceCheckForm) form).getTotalScore();
        String correctAnswers_low = (String)((SmartChoiceCheckForm) form).getCorrectAnswers_low();
        String answeredProblems_low = (String)((SmartChoiceCheckForm) form).getAnsweredProblems_low();
        String correctAnswers_middle = (String)((SmartChoiceCheckForm) form).getCorrectAnswers_middle();
        String answeredProblems_middle = (String)((SmartChoiceCheckForm) form).getAnsweredProblems_middle();
        String correctAnswers_high = (String)((SmartChoiceCheckForm) form).getCorrectAnswers_high();
        String answeredProblems_high = (String)((SmartChoiceCheckForm) form).getAnsweredProblems_high();
        int intTotalScore = Integer.parseInt(totalScore);
        int intCorrectAnswers_low = Integer.parseInt(correctAnswers_low);
        int intAnsweredProblems_low = Integer.parseInt(answeredProblems_low);    
        int intCorrectAnswers_middle = Integer.parseInt(correctAnswers_middle);
        int intAnsweredProblems_middle = Integer.parseInt(answeredProblems_middle);
        int intCorrectAnswers_high = Integer.parseInt(correctAnswers_high);
        int intAnsweredProblems_high = Integer.parseInt(answeredProblems_high);
        boolean lastCorrect = false;
        String lastType = (String)((SmartChoiceCheckForm) form).getLastType();

        if (inputAnswer.toLowerCase().equals(correctChoice.toLowerCase())){
           intCorrectAnswers ++;
           lastCorrect = true;          
        }
        double[] result =  StringSetTransfer.rate(lastCorrect, lastType.charAt(0));         
        intTotalScore += result[0];        
        intAnsweredProblems_low += result[1];
        intCorrectAnswers_low += result[2];
        intAnsweredProblems_middle += result[3];
        intCorrectAnswers_middle += result[4]; 
        intAnsweredProblems_high += result[5];       
        intCorrectAnswers_high += result[6];        

        pb2.setCorrectAnswers(intCorrectAnswers);
        pb2.setLastCorrect(lastCorrect);
        pb2.setTotalScore(intTotalScore);
        pb2.setCorrectAnswers_low(intCorrectAnswers_low);
        pb2.setAnsweredProblems_low(intAnsweredProblems_low);
        pb2.setCorrectAnswers_middle(intCorrectAnswers_middle);
        pb2.setAnsweredProblems_middle(intAnsweredProblems_middle);
        pb2.setCorrectAnswers_high(intCorrectAnswers_high);
        pb2.setAnsweredProblems_high(intAnsweredProblems_high);

        String userName = (String)((SmartChoiceCheckForm) form).getUserName();
        String passWord = (String)((SmartChoiceCheckForm) form).getPassWord();
        String trueName = (String)((SmartChoiceCheckForm) form).getTrueName();
        pb2.setUserName(userName);
        pb2.setPassWord(passWord);  
        pb2.setTrueName(trueName);
        
        String thisType = (String)((SmartChoiceCheckForm) form).getThisType();
        String continueRight = (String)((SmartChoiceCheckForm) form).getContinueRight();
        String continueWrong = (String)((SmartChoiceCheckForm) form).getContinueWrong();
        int intContinueRight = Integer.parseInt(continueRight);
        int intContinueWrong = Integer.parseInt(continueWrong);       
        pb2.setLastType(lastType);
        pb2.setThisType(thisType);
        pb2.setContinueRight(intContinueRight);
        pb2.setContinueWrong(intContinueWrong);

        String neverHigh = (String)((SmartChoiceCheckForm) form).getNeverHigh();
        boolean boolNeverHigh = Boolean.parseBoolean(neverHigh);
        pb2.setNeverHigh(boolNeverHigh);
       
        String times = (String)((SmartChoiceCheckForm) form).getTimes();
        pb2.setTimes(times);

        // Remove the Form Bean - don't need to carry values forward
        request.removeAttribute(mapping.getAttribute());

        session.setAttribute( Constants.PERSON_KEY, pb2);      
        request.setAttribute( Constants.PERSON_KEY, pb2);  

        String answeredHashSetStr = (String)((SmartChoiceCheckForm) form).getAnsweredHashSet();
        session.setAttribute( Constants.HASHSET_KEY, answeredHashSetStr);   
        request.setAttribute( Constants.HASHSET_KEY, answeredHashSetStr);  

        String answeredHashSetStr_tf = (String)((SmartChoiceCheckForm) form).getAnsweredHashSet_tf();
        session.setAttribute( Constants.HASHSET_TF_KEY, answeredHashSetStr_tf);   
        request.setAttribute( Constants.HASHSET_TF_KEY, answeredHashSetStr_tf);  
        /*
        HashSet hs = null;     
        hs = (HashSet)((ProblemCheckForm) form).getAnsweredHashSet();
        if (hs == null){
           return (mapping.findForward("toError"));     
        }
        */
        // Forward control to the specified success URI
        return (mapping.findForward("ShowSmartChoiceSolution"));        
    }
}

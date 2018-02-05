package hello;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.util.*;

public final class SmartRandomCheck2Form extends ActionForm {

    private String userName = null; 
    private String passWord = null;
    private String trueName = null;
    private String playerId = null;
    private String id = null;       
    private String inputAnswer = null;
    private String correctAnswer = null;   
    private String solution = null;   
    private String answeredProblems = null; 
    private String correctAnswers = null;
    private String answeredHashSet = null;
    private String answeredHashSet_tf = null;
    private String answered_M_HashSet = null;
    private String answeredHashSet_fb = null;
    private String answeredHashSet_fdb = null;
    private String answeredHashSet_fqb = null;
    private String answeredHashSet_tm = null; 
    private String type = null;

    private String totalScore = null;
    private String correctAnswers_low = null;
    private String answeredProblems_low = null;
    private String correctAnswers_middle = null;
    private String answeredProblems_middle = null;
    private String correctAnswers_high = null;
    private String answeredProblems_high = null;

    private String lastType = null;
    private String thisType = null;
    private String continueRight = null;
    private String continueWrong = null;
    private String neverHigh = null;

    private String times = null;
    private String randomNumber = null;

    private String statement_1st = null;
    private String statement_2nd = null;

    private String planStatus = null;

    private String source = null;

    private String statement_3rd = null;
    private String memberExist = null;

    private String lastCorrect = null;

    public String getUserName() {
        return (this.userName);
    }

    public String getPassWord() {
        return (this.passWord);
    } 

    public String getTrueName() {
        return (this.trueName);
    } 

    public String getPlayerId() {
        return (this.playerId);
    }  

    public String getId() {
        return (this.id);
    }

    public String getInputAnswer() {
        return (this.inputAnswer);
    }

    public String getCorrectAnswer() {
        return (this.correctAnswer);
    }

    public String getSolution() {
        return (this.solution);
    }

    public String getAnsweredProblems() {
        return (this.answeredProblems);
    }

    public String getCorrectAnswers() {
        return (this.correctAnswers);
    }

    public String getAnsweredHashSet() {
        return (this.answeredHashSet);
    }

    public String getAnsweredHashSet_tf() {
        return (this.answeredHashSet_tf);
    }

    public String getAnswered_M_HashSet() {
        return (this.answered_M_HashSet);
    }

    public String getAnsweredHashSet_fb() {
        return (this.answeredHashSet_fb);
    }

    public String getAnsweredHashSet_fdb() {
        return (this.answeredHashSet_fdb);
    }

    public String getAnsweredHashSet_fqb() {
        return (this.answeredHashSet_fqb);
    }

    public String getAnsweredHashSet_tm() {
        return (this.answeredHashSet_tm);
    }

    public String getType() {
        return (this.type);
    }

    public String getTotalScore() {
        return (this.totalScore);
    }

    public String getCorrectAnswers_low() {
        return this.correctAnswers_low;
    }

    public String getAnsweredProblems_low() {
        return this.answeredProblems_low;
    }

    public String getCorrectAnswers_middle() {
        return this.correctAnswers_middle;
    }

    public String getAnsweredProblems_middle() {
        return this.answeredProblems_middle;
    }

    public String getCorrectAnswers_high() {
        return this.correctAnswers_high;
    }

    public String getAnsweredProblems_high() {
        return this.answeredProblems_high;
    }

    public String getLastType() {
        return this.lastType;
    }

    public String getThisType() {
        return this.thisType;
    }

    public String getContinueRight() {
        return this.continueRight;
    }

    public String getContinueWrong() {
        return this.continueWrong;
    }

    public String getNeverHigh() {
        return this.neverHigh;
    }

    public String getTimes() {
        return this.times;
    }

    public String getRandomNumber() {
        return this.randomNumber;
    }

    public String getStatement_1st() {
        return this.statement_1st;
    }

    public String getStatement_2nd() {
        return this.statement_2nd;
    }

    public String getPlanStatus() {
        return this.planStatus;
    }

    public String getSource() {
        return this.source;
    }

    public String getStatement_3rd() {
        return this.statement_3rd;
    }

    public String getMemberExist() {
        return this.memberExist;
    }

    public String getLastCorrect() {
        return this.lastCorrect;
    }

/***********************************/
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInputAnswer(String inputAnswer) {
        this.inputAnswer = inputAnswer;
    }
   
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setAnsweredProblems(String answeredProblems) {
        this.answeredProblems = answeredProblems;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setAnsweredHashSet(String answeredHashSet) {
        this.answeredHashSet = answeredHashSet;
    }

    public void setAnsweredHashSet_tf(String answeredHashSet_tf) {
        this.answeredHashSet_tf = answeredHashSet_tf;
    }

    public void setAnswered_M_HashSet(String answered_M_HashSet) {
        this.answered_M_HashSet = answered_M_HashSet;
    }

    public void setAnsweredHashSet_fb(String answeredHashSet_fb) {
        this.answeredHashSet_fb = answeredHashSet_fb;
    }

    public void setAnsweredHashSet_fdb(String answeredHashSet_fdb) {
        this.answeredHashSet_fdb = answeredHashSet_fdb;
    }

    public void setAnsweredHashSet_fqb(String answeredHashSet_fqb) {
        this.answeredHashSet_fqb = answeredHashSet_fqb;
    }

    public void setAnsweredHashSet_tm(String answeredHashSet_tm) {
        this.answeredHashSet_tm = answeredHashSet_tm;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public void setCorrectAnswers_low(String correctAnswers_low) {
        this.correctAnswers_low = correctAnswers_low;
    }

    public void setAnsweredProblems_low(String answeredProblems_low) {
        this.answeredProblems_low = answeredProblems_low;
    }

    public void setCorrectAnswers_middle(String correctAnswers_middle) {
        this.correctAnswers_middle = correctAnswers_middle;
    }

    public void setAnsweredProblems_middle(String answeredProblems_middle) {
        this.answeredProblems_middle = answeredProblems_middle;
    }
    
    public void setCorrectAnswers_high(String correctAnswers_high) {
        this.correctAnswers_high = correctAnswers_high;
    }

    public void setAnsweredProblems_high(String answeredProblems_high) {
        this.answeredProblems_high = answeredProblems_high;
    }

    public void setLastType(String lastType) {
        this.lastType = lastType;
    }

    public void setThisType(String thisType) {
        this.thisType = thisType;
    }

    public void setContinueRight(String continueRight) {
        this.continueRight = continueRight;
    }

    public void setContinueWrong(String continueWrong) {
        this.continueWrong = continueWrong;
    }

    public void setNeverHigh(String neverHigh) {
        this.neverHigh = neverHigh;
    }  

    public void setTimes(String times) {
        this.times = times;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    } 
   
    public void setStatement_1st(String statement_1st) {
        this.statement_1st = statement_1st;
    }

    public void setStatement_2nd(String statement_2nd) {
        this.statement_2nd = statement_2nd;
    } 

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setStatement_3rd(String statement_3rd) {
        this.statement_3rd = statement_3rd;
    }

    public void setMemberExist(String memberExist) {
        this.memberExist = memberExist;
    }

    public void setLastCorrect(String lastCorrect) {
        this.lastCorrect = lastCorrect;
    }

    /**
     * Reset all properties to their default values.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.userName = null;
    }

    /**
     * Validate the properties posted in this request. If validation errors are
     * found, return an <code>ActionErrors</code> object containing the errors.
     * If no validation errors occur, return <code>null</code> or an empty
     * <code>ActionErrors</code> object.
     */
    /*
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();

        if ((userName == null) || (userName.length() < 1))
            errors.add("username", new ActionMessage("hello.no.username.error"));

        return errors;
    }
    */
}

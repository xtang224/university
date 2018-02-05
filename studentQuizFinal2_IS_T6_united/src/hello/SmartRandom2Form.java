package hello;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public final class SmartRandom2Form extends ActionForm {

    private String userName = null;
    private String passWord = null;
    private String trueName = null;
    private String playerId = null;
    private int id = 0; 
    private String answeredProblems = null; 
    private String correctAnswers = null; 
    private String answeredHashSet = null;   
    private String answeredHashSet_tf = null;   
    private String answered_M_HashSet = null;
    private String answeredHashSet_fb = null; 
    private String answeredHashSet_fdb = null; 
    private String answeredHashSet_fqb = null; 
    private String answeredHashSet_tm = null; 
    private String lastCorrect = null;

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

    private String hashSet_low = null;
    private String hashSet_middle = null;
    private String hashSet_high = null;

    private String hashSet_low_tf = null;
    private String hashSet_middle_tf = null;
    private String hashSet_high_tf = null;
    
    private String hashSet_low_fb = null;
    private String hashSet_middle_fb = null;
    private String hashSet_high_fb = null;

    private String hashSet_low_fdb = null;
    private String hashSet_middle_fdb = null;
    private String hashSet_high_fdb = null;

    private String hashSet_low_m = null;
    private String hashSet_middle_m = null;
    private String hashSet_high_m = null;

    private String planStatus = null;

    private String source = null;

    private String updateScore = null;

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

    public int getId() {
        return (this.id);
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

    public String getLastCorrect() {
        return (this.lastCorrect);
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

    public String getHashSet_low() {
        return this.hashSet_low;
    }

    public String getHashSet_middle() {
        return this.hashSet_middle;
    }

    public String getHashSet_high() {
        return this.hashSet_high;
    }

    public String getHashSet_low_tf() {
        return this.hashSet_low_tf;
    }

    public String getHashSet_middle_tf() {
        return this.hashSet_middle_tf;
    }

    public String getHashSet_high_tf() {
        return this.hashSet_high_tf;
    }

    public String getHashSet_low_fb() {
        return this.hashSet_low_fb;
    }

    public String getHashSet_middle_fb() {
        return this.hashSet_middle_fb;
    }

    public String getHashSet_high_fb() {
        return this.hashSet_high_fb;
    }

    public String getHashSet_low_fdb() {
        return this.hashSet_low_fdb;
    }

    public String getHashSet_middle_fdb() {
        return this.hashSet_middle_fdb;
    }

    public String getHashSet_high_fdb() {
        return this.hashSet_high_fdb;
    }

    public String getHashSet_low_m() {
        return this.hashSet_low_m;
    }

    public String getHashSet_middle_m() {
        return this.hashSet_middle_m;
    }

    public String getHashSet_high_m() {
        return this.hashSet_high_m;
    }

    public String getPlanStatus() {
        return this.planStatus;
    }

    public String getSource() {
        return this.source;
    }

    public String getUpdateScore() {
        return this.updateScore;
    } 
/*********************************/
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

    public void setId(int id) {
        this.id = id;
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

    public void setLastCorrect(String lastCorrect) {
        this.lastCorrect = lastCorrect;
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

    public void setHashSet_low(String hashSet_low) {
        this.hashSet_low = hashSet_low;
    }

    public void setHashSet_middle(String hashSet_middle) {
        this.hashSet_middle = hashSet_middle;
    }

    public void setHashSet_high(String hashSet_high) {
        this.hashSet_high = hashSet_high;
    }

    public void setHashSet_low_tf(String hashSet_low_tf) {
        this.hashSet_low_tf = hashSet_low_tf;
    }

    public void setHashSet_middle_tf(String hashSet_middle_tf) {
        this.hashSet_middle_tf = hashSet_middle_tf;
    }

    public void setHashSet_high_tf(String hashSet_high_tf) {
        this.hashSet_high_tf = hashSet_high_tf;
    }

    public void setHashSet_low_fb(String hashSet_low_fb) {
        this.hashSet_low_fb = hashSet_low_fb;
    }

    public void setHashSet_middle_fb(String hashSet_middle_fb) {
        this.hashSet_middle_fb = hashSet_middle_fb;
    }

    public void setHashSet_high_fb(String hashSet_high_fb) {
        this.hashSet_high_fb = hashSet_high_fb;
    }

    public void setHashSet_low_fdb(String hashSet_low_fdb) {
        this.hashSet_low_fdb = hashSet_low_fdb;
    }

    public void setHashSet_middle_fdb(String hashSet_middle_fdb) {
        this.hashSet_middle_fdb = hashSet_middle_fdb;
    }

    public void setHashSet_high_fdb(String hashSet_high_fdb) {
        this.hashSet_high_fdb = hashSet_high_fdb;
    }

    public void setHashSet_low_m(String hashSet_low_m) {
        this.hashSet_low_m = hashSet_low_m;
    }

    public void setHashSet_middle_m(String hashSet_middle_m) {
        this.hashSet_middle_m = hashSet_middle_m;
    }

    public void setHashSet_high_m(String hashSet_high_m) {
        this.hashSet_high_m = hashSet_high_m;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUpdateScore(String updateScore) {
        this.updateScore = updateScore;
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

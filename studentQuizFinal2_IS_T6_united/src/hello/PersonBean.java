package hello;
public class PersonBean {

    private String playerId = null;
    private String userName = null;
    private String passWord = null;
    private String trueName = null;
    private String inputAnswer = null;

    private int correctAnswers = 0;
    private int answeredProblems = 0;
    private int numberTried = 0;
    private String changePassword = ""; 
    private double totalScore = 0;  

    private boolean lastCorrect = false; 

    private int correctAnswers_low = 0;
    private int answeredProblems_low = 0;
    private int correctAnswers_middle = 0;
    private int answeredProblems_middle = 0;
    private int correctAnswers_high = 0;
    private int answeredProblems_high = 0;

    private String lastType = null;
    private String thisType = null;
    private int continueRight = 0;
    private int continueWrong = 0;
    private boolean neverHigh = false;

    private String times = null;
    private String randomNumber = null;

    private int remainSeconds = 0;
    private String questionUsedUp = "false";

    private int currentProblemId = 0;

    private String planStatus = null;
    private String memberExist = null;

    private String cautionNote = "";

    public String getPlayerId() {
        return this.playerId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public String getTrueName() {
        return this.trueName;
    }

    public String getInputAnswer() {
        return this.inputAnswer;
    }

    public int getCorrectAnswers() {
        return this.correctAnswers;
    }

    public int getAnsweredProblems() {
        return this.answeredProblems;
    }

    public int getNumberTried() {
        return this.numberTried;
    }

    public String getChangePassword() {
        return this.changePassword;
    }

    public double getTotalScore() {
        return this.totalScore;
    }

    public boolean getLastCorrect() {
        return this.lastCorrect;
    }

    public int getCorrectAnswers_low() {
        return this.correctAnswers_low;
    }

    public int getAnsweredProblems_low() {
        return this.answeredProblems_low;
    }

    public int getCorrectAnswers_middle() {
        return this.correctAnswers_middle;
    }

    public int getAnsweredProblems_middle() {
        return this.answeredProblems_middle;
    }

    public int getCorrectAnswers_high() {
        return this.correctAnswers_high;
    }

    public int getAnsweredProblems_high() {
        return this.answeredProblems_high;
    }

    public String getLastType() {
        return this.lastType;
    }

    public String getThisType() {
        return this.thisType;
    }

    public int getContinueRight() {
        return this.continueRight;
    }

    public int getContinueWrong() {
        return this.continueWrong;
    }

    public boolean getNeverHigh() {
        return this.neverHigh;
    }

    public String getTimes() {
        return this.times;
    }

    public String getRandomNumber() {
        return this.randomNumber;
    }

    public int getRemainSeconds() {
        return this.remainSeconds;
    }

    public String getQuestionUsedUp() {
        return this.questionUsedUp;
    } 

    public int getCurrentProblemId() {
        return this.currentProblemId;
    }

    public String getPlanStatus() {
        return this.planStatus;
    }

    public String getMemberExist() {
        return this.memberExist;
    }

    public String getCautionNote() {
        return this.cautionNote;
    }

/************************************************/
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setAnsweredProblems(int answeredProblems) {
        this.answeredProblems = answeredProblems;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public void setInputAnswer(String inputAnswer) {
        this.inputAnswer = inputAnswer;
    }

    public void setNumberTried(int numberTried) {
        this.numberTried = numberTried;
    }

    public void setChangePassword(String changePassword) {
        this.changePassword = changePassword;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public void setLastCorrect(boolean lastCorrect) {
        this.lastCorrect = lastCorrect;
    }

    public void setCorrectAnswers_low(int correctAnswers_low) {
        this.correctAnswers_low = correctAnswers_low;
    }

    public void setAnsweredProblems_low(int answeredProblems_low) {
        this.answeredProblems_low = answeredProblems_low;
    }

    public void setCorrectAnswers_middle(int correctAnswers_middle) {
        this.correctAnswers_middle = correctAnswers_middle;
    }

    public void setAnsweredProblems_middle(int answeredProblems_middle) {
        this.answeredProblems_middle = answeredProblems_middle;
    }

    public void setCorrectAnswers_high(int correctAnswers_high) {
        this.correctAnswers_high = correctAnswers_high;
    }

    public void setAnsweredProblems_high(int answeredProblems_high) {
        this.answeredProblems_high = answeredProblems_high;
    }

    public void setLastType(String lastType) {
        this.lastType = lastType;
    }

    public void setThisType(String thisType) {
        this.thisType = thisType;
    }

    public void setContinueRight(int continueRight) {
        this.continueRight = continueRight;
    }

    public void setContinueWrong(int continueWrong) {
        this.continueWrong = continueWrong;
    }

    public void setNeverHigh(boolean neverHigh) {
        this.neverHigh = neverHigh;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public void setQuestionUsedUp(String questionUsedUp) {
        this.questionUsedUp = questionUsedUp;
    }

    public void setCurrentProblemId(int currentProblemId) {
        this.currentProblemId = currentProblemId;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public void setMemberExist(String memberExist) {
        this.memberExist = memberExist;
    }

    public void setCautionNote(String cautionNote) {
        this.cautionNote = cautionNote;
    }
 
    /**
     * This is a stub method that would be used for the Model to save
     * the information submitted to a persistent store. In this sample
     * application it is not used.
     */
    public void saveToPersistentStore() {

        /*
         * This is a stub method that might be used to save the person's
         * name to a persistent store(i.e. database) if this were a real application.
         *
         * The actual business operations that would exist within a Model
         * component would depend upon the requirements of the application.
         */
    }
}

package hello;
public class SmartProblemBean {

    private String userName = null;
    private int id = 0;
    private String statement = null;
    private String choiceA = null;
    private String choiceB = null;
    private String choiceC = null;
    private String choiceD = null;
    private String correctChoice = null;
    private String solution = null;
    private String type = null;
    private String source = null;

    public String getUserName() {
        return this.userName;
    }

    public int getId() {
        return this.id;
    }

    public String getStatement() {
        return this.statement;
    }

    public String getChoiceA() {
        return this.choiceA;
    }

    public String getChoiceB() {
        return this.choiceB;
    }

    public String getChoiceC() {
        return this.choiceC;
    }

    public String getChoiceD() {
        return this.choiceD;
    }

    public String getCorrectChoice() {
        return this.correctChoice;
    }

    public String getSolution() {
        return this.solution;
    }

    public String getType() {
        return this.type;
    }

    public String getSource() {
        return this.source;
    }
/*************************************************************/
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }
 
    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSource(String source) {
        this.source = source;
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

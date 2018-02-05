import javax.faces.bean.ManagedBean;

@ManagedBean(name = "term", eager = true)

public class Term {
    String content = "";
    boolean rightAnswer = false;

    public Term(){}

    public Term(String content){
       this.content = content;
    }

    public Term(Term aTerm) {
       this(aTerm.getContent());
    }

    public String getContent() {
        return content;
    }

    public boolean getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer){
        this.rightAnswer = rightAnswer;
    }
}


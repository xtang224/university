package hello;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public final class RegisterForm extends ActionForm {

    private String userName = null;
    private String trueName = null;
    private String passWord = null;
    private String checkPassWord = null;      
    private String phoneNumber = null;
    private String emailAddress = null;
    
    public String getUserName() {
        return (this.userName);
    }

    public String getTrueName() {
        return (this.trueName);
    }

    public String getPassWord() {
        return (this.passWord);
    }    

    public String getCheckPassWord() {
        return (this.checkPassWord);
    }    

    public String getPhoneNumber() {
        return (this.phoneNumber);
    }    

    public String getEmailAddress() {
        return (this.emailAddress);
    }    
/******************************************/
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    
    public void setCheckPassWord(String checkPassWord) {
        this.checkPassWord = checkPassWord;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }    

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

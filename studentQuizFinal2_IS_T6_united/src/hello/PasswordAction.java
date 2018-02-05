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

public final class PasswordAction extends Action {

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
        HttpSession session = request.getSession(true);
        String userName = (String)((PasswordForm) form).getUserName(); 
        String passWord = (String)((PasswordForm) form).getPassWord();
        String oldPassword = (String)((PasswordForm) form).getOldPassword();       
        String newPassword = (String)((PasswordForm) form).getNewPassword();
        String confirmNewPassword = (String)((PasswordForm) form).getConfirmNewPassword();
        String trueName = (String)((PasswordForm) form).getTrueName();            
        
        PersonBean pb2 = new PersonBean();        
        pb2.setUserName(userName);
        pb2.setTrueName(trueName);
        pb2.setPassWord(passWord);
        if (!passWord.equalsIgnoreCase(oldPassword)){
           pb2.setChangePassword("Your old password is not correct."); 
           session.setAttribute( Constants.PERSON_KEY, pb2); 
           request.setAttribute( Constants.PERSON_KEY, pb2);       
           return (mapping.findForward("CheckLogin"));       
        }else if (!newPassword.equalsIgnoreCase(confirmNewPassword)){
           pb2.setChangePassword("The two new passwords do not match."); 
           session.setAttribute( Constants.PERSON_KEY, pb2); 
           request.setAttribute( Constants.PERSON_KEY, pb2);       
           return (mapping.findForward("CheckLogin"));       
        }

       
        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null;
        PreparedStatement stmt = null;
        
        try {
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
           // do what you wish with myConnection           
           
           stmt = myConnection.prepareStatement("update students set password=? where studentNo=?");
           stmt.clearParameters();
           stmt.setString(1, newPassword);
           stmt.setString(2, userName);
           stmt.executeUpdate();           
        } catch (SQLException sqle) {
           getServlet().log("Connection.process", sqle);
        } finally {
           //enclose this in a finally block to make
           //sure the connection is closed
           try {
              stmt.close();
              myConnection.close();
           } catch (SQLException e) {
             getServlet().log("Connection.close", e);
         }
       }
        
        

         /*      
         * We pass data to the View components by setting them as attributes
         * in the page, request, session or servlet context. In this case, the
         * most appropriate scoping is the "request" context since the data
         * will not be neaded after the View is generated.
         *
         * Constants.PERSON_KEY provides a key accessible by both the
         * Controller component (i.e. this class) and the View component
         * (i.e. the jsp file we forward to).
         */         
        

        // Remove the Form Bean - don't need to carry values forward
        request.removeAttribute(mapping.getAttribute());   

        pb2.setPassWord(newPassword);
        pb2.setChangePassword("Your password has been changed."); 
        session.setAttribute( Constants.PERSON_KEY, pb2); 
        request.setAttribute( Constants.PERSON_KEY, pb2);           

        // Forward control to the specified success URI
        return (mapping.findForward("CheckLogin"));        
    }
}

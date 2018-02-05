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

public final class LoginAction extends Action {

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
        String userName = (String)((LoginForm) form).getUserName();
        String passWord = (String)((LoginForm) form).getPassWord();

        /*       
        String badUserName = "Monster";
        if (userName.equalsIgnoreCase(badUserName)) {
           errors.add("username", new ActionMessage("hello.dont.talk.to.monster", badUserName ));
           saveErrors(request, errors);
           return (new ActionForward(mapping.getInput()));
        }
        */
        /*
         * Having received and validated the data submitted
         * from the View, we now update the model
         */       
       
        /*
        if (pb == null) {
           return (mapping.findForward("toError"));           
        }        
        if (pb == null) {
           pb = new ProblemBean();
        }
        */

        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int exist = 0;
        //int numberTried = 0;
        PersonBean pb2 = new PersonBean();
       
        try {
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
           // do what you wish with myConnection          
           
           stmt = myConnection.prepareStatement("select * from students where studentNo=?");
           stmt.clearParameters();
           stmt.setString(1, userName);
           rs = stmt.executeQuery();
           while (rs.next()){
              exist ++;              
              pb2.setUserName(rs.getString("studentNo"));    
              pb2.setPassWord(rs.getString("password"));
              //pb2.setTrueName(new String(rs.getBytes("name"), "utf8"));             
              pb2.setTrueName(rs.getString("name"));
              StringSetTransfer.nameMap.put(pb2.getUserName(), pb2.getTrueName());
           }
        } catch (SQLException sqle) {
           getServlet().log("Connection.process", sqle);
        } finally {
           //enclose this in a finally block to make
           //sure the connection is closed
           try {
              rs.close();
              stmt.close();
              myConnection.close();
           } catch (SQLException e) {
             getServlet().log("Connection.close", e);
         }
       }
   
       request.removeAttribute(mapping.getAttribute());
       session.setAttribute( Constants.PERSON_KEY, pb2); 
       request.setAttribute( Constants.PERSON_KEY, pb2);    
       if (exist == 0){
          return (mapping.findForward("FailLogin1"));        
       }else if (!pb2.getPassWord().equalsIgnoreCase(passWord)){
          return (mapping.findForward("FailLogin2"));
       }else {            
          return (mapping.findForward("CheckLogin"));  
       }
        
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
        
    }
}

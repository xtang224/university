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

public final class SmartRandomAction extends Action {

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
        SmartProblemBean pb = null;
        SmartChoiceBean cb = null;
        SmartMultipleProblemBean mpb = null;
        FillBlankBean fbb = null;
        FillDoubleBlankBean fdbb = null;
        FillQuadBlankBean fqbb = null;
        TermMatchBean tmb = null;
        String first = null;
        first = (String)request.getParameter("first"); 

        PersonBean pb2 = null;
        pb2 = new PersonBean();
        String userName = (String)((SmartRandomForm) form).getUserName();
        String passWord = (String)((SmartRandomForm) form).getPassWord();
        String trueName = (String)((SmartRandomForm) form).getTrueName();
        String playerId = (String)((SmartRandomForm) form).getPlayerId();
        pb2.setUserName(userName);
        pb2.setPassWord(passWord); 
        pb2.setTrueName(trueName);
        pb2.setPlayerId(playerId);  

        String times = (String)((SmartRandomForm) form).getTimes();
        pb2.setTimes(times);

        String planStatus = (String)((SmartRandomForm) form).getPlanStatus();
        pb2.setPlanStatus(planStatus);

        String source = (String)((SmartRandomForm) form).getSource();

        javax.sql.DataSource dataSource = null;
        java.sql.Connection myConnection = null;
        Statement s = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;
        pb = new SmartProblemBean();
        cb = new SmartChoiceBean();
        mpb = new SmartMultipleProblemBean(); 
        fbb = new FillBlankBean();
        fdbb = new FillDoubleBlankBean();     
        fqbb = new FillQuadBlankBean();     
        tmb = new TermMatchBean();
        pb.setSource(source);
        cb.setSource(source);
        mpb.setSource(source);
        fbb.setSource(source);
        fdbb.setSource(source);
        fqbb.setSource(source);
        tmb.setSource(source);

        HashSet hs = null;
        HashSet hs_tf = null;
        HashSet mhs = null;
        HashSet hs_fb = null;
        HashSet hs_fdb = null;
        HashSet hs_fqb = null;
        HashSet hs_tm = null;        

        String tempPlayerId = null;
        String tempUserName = null;
        String tempTrueName = null;
        String tempPassword = null;

        if (first.equals("true")){
            hs = new HashSet(); 
            hs.add(new Integer(0));
            hs_tf = new HashSet();
            hs_tf.add(new Integer(0));
            mhs = new HashSet();
            mhs.add(new Integer(0));
            hs_fb = new HashSet(); 
            hs_fb.add(new Integer(0));
            hs_fdb = new HashSet(); 
            hs_fdb.add(new Integer(0));
            hs_fqb = new HashSet(); 
            hs_fqb.add(new Integer(0));
            hs_tm = new HashSet(); 
            hs_tm.add(new Integer(0));

            HashSet hs_low = new HashSet();            
            HashSet hs_middle = new HashSet(); 
            HashSet hs_high = new HashSet();
            HashSet hs_low_tf = new HashSet();
            HashSet hs_middle_tf = new HashSet(); 
            HashSet hs_high_tf = new HashSet();
            HashSet mhs_low = new HashSet();
            HashSet mhs_middle = new HashSet(); 
            HashSet mhs_high = new HashSet();
            HashSet hs_low_fb = new HashSet();
            HashSet hs_middle_fb = new HashSet(); 
            HashSet hs_high_fb = new HashSet();
            HashSet hs_low_fdb = new HashSet();
            HashSet hs_middle_fdb = new HashSet(); 
            HashSet hs_high_fdb = new HashSet();
            HashSet hs_high_fqb = new HashSet();
            HashSet hs_middle_tm = new HashSet();
            HashSet hs_middle_tm2 = new HashSet();

            HashSet hs_low_used = new HashSet();
            HashSet hs_low_tf_used = new HashSet();
            HashSet hs_middle_fb_used = new HashSet(); 
            HashSet mhs_high_used = new HashSet();
            HashSet hs_high_fdb_used = new HashSet();
            HashSet hs_high_fqb_used = new HashSet();
            HashSet hs_middle_tm_used = new HashSet();
            HashSet hs_middle_tm2_used = new HashSet();

            int count = 0;             
            try{
                //we will try to initiate the usedQuestions first, then generate the random question id 
               dataSource = getDataSource(request);
               myConnection = dataSource.getConnection();
               //Class.forName("org.hsqldb.jdbcDriver");
               //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
               if (!userName.equals("2000")){
                  stmt = myConnection.prepareStatement("update student_score set status='active',planStatus='not finished' where studentNo = ?");
                  stmt.clearParameters();
                  stmt.setString(1, userName);
                  stmt.executeUpdate();   
        
                  s = myConnection.createStatement();
                  rs = s.executeQuery("select status from student_score where studentNo = '2000' ");
                  String status = null;
                  while (rs.next()){
                     status = rs.getString("status");
                  }
                  if (status == null || status.equals("") || status.equals("not active")){
                     request.removeAttribute(mapping.getAttribute());     
                     session.setAttribute( Constants.PERSON_KEY, pb2); 
                     request.setAttribute( Constants.PERSON_KEY, pb2); 
                     return (mapping.findForward("ShowSmartPairWait"));        
                  }
               }else{//userName.equals("2000")
                     s = myConnection.createStatement();
                     s.executeUpdate("delete from student_vote");
 
                     /*
                     s = myConnection.createStatement();
               rs = s.executeQuery("select studentNo from student_score where status = 'active' and studentNo <> '2000' ");
                     count = 0;
                     while (rs.next()){
                        count++;
                        tempUserName = rs.getString("studentNo");
                        tempPlayerId = "player" + count;
                        
stmt = myConnection.prepareStatement("update student_score set playerId = '" + tempPlayerId + "', planStatus='not finished' where studentNo = ?");
                        stmt.clearParameters();
                        stmt.setString(1, tempUserName);
                        stmt.executeUpdate();

stmt = myConnection.prepareStatement("update student_time set playerId = '" + tempPlayerId + "' where studentNo = ?");
                        stmt.clearParameters();
                        stmt.setString(1, tempUserName);
                        stmt.executeUpdate();
                     }
                    
                     HashSet set2 = new HashSet();
                     s = myConnection.createStatement();
                     rs = s.executeQuery("select studentNo from student_score");
                     while(rs.next()){
                        tempUserName = rs.getString("studentNo");
                        set2.add(tempUserName);
                     }
   
                     s = myConnection.createStatement();
            rs = s.executeQuery("select studentNo from student_score where status='active'");       
                     while(rs.next()){
                        tempUserName = rs.getString("studentNo");
                        set2.remove(tempUserName);
                     }

                     Iterator iter = set2.iterator();
                     while(iter.hasNext()){
                        tempUserName = (String)iter.next();
                         s = myConnection.createStatement();
                  s.executeUpdate("update student_score set playerId = 'player0' where studentNo ='" + tempUserName +"'");
                        s = myConnection.createStatement();
                  s.executeUpdate("update student_time set playerId = 'player0' where studentNo ='" + tempUserName + "'");
                     }
                     */

                     //we want to initiate StringSetTransfer.hs_low_used,hs_low,hs_middle,hs_high,total
                     rs = s.executeQuery("select id from usedQuestions");                
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_low_used.add(new Integer(usedId));
                     }
                     rs = s.executeQuery("select id from usedQuestions2 where type='L' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_low_used.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_low_used = hs_low_used;
                     rs = s.executeQuery("select id from questions2 where type='L' and source='" + source + "'");        
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_low.add(new Integer(usedId));
                     }
                     hs_low.removeAll(hs_low_used);
                     StringSetTransfer.hs_low = hs_low;               
                     rs = s.executeQuery("select id from questions2 where type='M' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_middle.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_middle = hs_middle;
                     rs = s.executeQuery("select id from questions2 where type='H' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_high.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_high = hs_high;
                     rs = s.executeQuery("select id from questions2 where source='" + source + "'");         
                     int numberId = 0;
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        numberId ++;                 
                        //getServlet().log("score part is reached");
                     }  
                     StringSetTransfer.total = numberId;               

                     //now we want to initiate StringSetTransfer.hs_low_tf_used,hs_low_tf,hs_middle_tf,hs_high_tf,total_tf
                     rs = s.executeQuery("select id from usedChoices");                
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_low_tf_used.add(new Integer(usedId));
                     }
                     rs = s.executeQuery("select id from usedChoices2 where type='L' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_low_tf_used.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_low_tf_used = hs_low_tf_used;
                     rs = s.executeQuery("select id from choices2 where type='L' and source='" + source + "'");     
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_low_tf.add(new Integer(usedId));
                     }
                     hs_low_tf.removeAll(hs_low_tf_used);
                     StringSetTransfer.hs_low_tf = hs_low_tf;
                     rs = s.executeQuery("select id from choices2 where type='M' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_middle_tf.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_middle_tf = hs_middle_tf;
                     rs = s.executeQuery("select id from choices2 where type='H' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_high_tf.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_high_tf = hs_high_tf;
                     rs = s.executeQuery("select id from choices2 where source='" + source + "'");         
                     numberId = 0;
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        numberId ++;                 
                        //getServlet().log("score part is reached");
                     }  
                     StringSetTransfer.total_tf = numberId;

                     //now we want to initiate StringSetTransfer.mhs_low,mhs_middle,mhs_high_used,mhs_high,mtotal
                     rs = s.executeQuery("select id from multipleQuestions2 where type='L' and source='" + source + "'");   
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        mhs_low.add(new Integer(usedId));
                     }
                     StringSetTransfer.mhs_low = mhs_low;               
                     rs = s.executeQuery("select id from multipleQuestions2 where type='M' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        mhs_middle.add(new Integer(usedId));
                     }
                     StringSetTransfer.mhs_middle = mhs_middle;
                     //
                   rs = s.executeQuery("select id from usedMultipleQuestions2 where type='H' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        mhs_high_used.add(new Integer(usedId));
                     }
                     StringSetTransfer.mhs_high_used = mhs_high_used;
                     rs = s.executeQuery("select id from multipleQuestions2 where type='H' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        mhs_high.add(new Integer(usedId));
                     }
                     mhs_high.removeAll(mhs_high_used);
                     StringSetTransfer.mhs_high = mhs_high;
                     rs = s.executeQuery("select id from multipleQuestions2 where source='" + source + "'");         
                     numberId = 0;
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        numberId ++;                 
                        //getServlet().log("score part is reached");
                     }  
                     StringSetTransfer.mtotal = numberId;

                    //now we want to initiate StringSetTransfer.hs_low_fb,hs_middle_fb_used,hs_middle_fb,hs_high_fb,total_fb
                     rs = s.executeQuery("select id from fillBlank2 where type='L' and source='" + source + "'");          
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_low_fb.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_low_fb = hs_low_fb;
                     //
                     rs = s.executeQuery("select id from usedFillBlank2 where type='M' and source='" + source + "'");       
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_middle_fb_used.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_middle_fb_used = hs_middle_fb_used;
                     rs = s.executeQuery("select id from fillBlank2 where type='M' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_middle_fb.add(new Integer(usedId));
                     }
                     hs_middle_fb.removeAll(hs_middle_fb_used);
                     StringSetTransfer.hs_middle_fb = hs_middle_fb;
                     rs = s.executeQuery("select id from fillBlank2 where type='H' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_high_fb.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_high_fb = hs_high_fb;
                     rs = s.executeQuery("select id from fillBlank2 where source='" + source + "'");         
                     numberId = 0;
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        numberId ++;                 
                        //getServlet().log("score part is reached");
                     }  
                     StringSetTransfer.total_fb = numberId;

                 //now we want to initiate StringSetTransfer.hs_low_fdb,hs_middle_fdb,hs_high_fdb_used,hs_high_fdb,total_fdb
                     rs = s.executeQuery("select id from fillDoubleBlanks2 where type='L' and source='" + source + "'");    
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_low_fdb.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_low_fdb = hs_low_fdb;
                     rs = s.executeQuery("select id from fillDoubleBlanks2 where type='M' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_middle_fdb.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_middle_fdb = hs_middle_fdb;
                     //
                     rs = s.executeQuery("select id from usedFillDoubleBlanks2 where type='H' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_high_fdb_used.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_high_fdb_used = hs_high_fdb_used;
                     rs = s.executeQuery("select id from fillDoubleBlanks2 where type='H' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_high_fdb.add(new Integer(usedId));
                     }
                     hs_high_fdb.removeAll(hs_high_fdb_used);
                     StringSetTransfer.hs_high_fdb = hs_high_fdb;
                     rs = s.executeQuery("select id from fillDoubleBlanks2 where source='"+ source + "'");         
                     numberId = 0;
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        numberId ++;                 
                        //getServlet().log("score part is reached");
                     }  
                     StringSetTransfer.total_fdb = numberId;

                     //now we want to initiate StringSetTransfer.hs_high_fqb_used,hs_high_fqb,total_fqb                     
                     rs = s.executeQuery("select id from usedFillQuadBlanks2 where type='H' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_high_fqb_used.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_high_fqb_used = hs_high_fqb_used;
                     rs = s.executeQuery("select id from fillQuadBlanks2 where type='H' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_high_fqb.add(new Integer(usedId));
                     }
                     hs_high_fqb.removeAll(hs_high_fqb_used);
                     StringSetTransfer.hs_high_fqb = hs_high_fqb;
                     rs = s.executeQuery("select id from fillQuadBlanks2 where source='"+ source + "'");         
                     numberId = 0;
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        numberId ++;                 
                        //getServlet().log("score part is reached");
                     }  
                     StringSetTransfer.total_fqb = numberId;

                    //now we want to initiate StringSetTransfer.hs_middle_tm, hs_middle_tm_used
                     rs = s.executeQuery("select id from usedTermMatch where type='M' and source='" + source + "'");    
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_middle_tm_used.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_middle_tm_used = hs_middle_tm_used;
                     rs = s.executeQuery("select id from termMatch where type='M' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_middle_tm.add(new Integer(usedId));
                     }
                     hs_middle_tm.removeAll(hs_middle_tm_used);
                     StringSetTransfer.hs_middle_tm = hs_middle_tm;

                     //now we want to initiate StringSetTransfer.hs_middle_tm2, hs_middle_tm2_used
                     rs = s.executeQuery("select id from usedTermMatch2 where type='M' and source='" + source + "'");    
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_middle_tm2_used.add(new Integer(usedId));
                     }
                     StringSetTransfer.hs_middle_tm2_used = hs_middle_tm2_used;
                     rs = s.executeQuery("select id from termMatch2 where type='M' and source='" + source + "'");
                     while (rs.next()){
                        int usedId = rs.getInt("id");
                        hs_middle_tm2.add(new Integer(usedId));
                     }
                     hs_middle_tm2.removeAll(hs_middle_tm2_used);
                     StringSetTransfer.hs_middle_tm2 = hs_middle_tm2;

 System.out.println("the total number of questions: " + (StringSetTransfer.total_fdb+StringSetTransfer.total_fb+StringSetTransfer.mtotal+StringSetTransfer.total_tf+StringSetTransfer.total) + "");
                System.out.println("among which, the total number of type low questions: " + (StringSetTransfer.hs_low_fdb.size()+StringSetTransfer.hs_low_fb.size()+StringSetTransfer.mhs_low.size()+StringSetTransfer.hs_low_tf.size()+StringSetTransfer.hs_low_tf_used.size()+StringSetTransfer.hs_low.size()+StringSetTransfer.hs_low_used.size()) + "");
                System.out.println("among which, the total number of type middle questions: " + (StringSetTransfer.hs_middle_fdb.size()+StringSetTransfer.hs_middle_fb.size()+StringSetTransfer.hs_middle_fb_used.size()+StringSetTransfer.mhs_middle.size()+StringSetTransfer.hs_middle_tf.size()+StringSetTransfer.hs_middle.size()) + "");     
                System.out.println("among which, the total number of type high questions: " + (StringSetTransfer.hs_high_fdb.size()+StringSetTransfer.hs_high_fqb.size()+StringSetTransfer.hs_high_fdb_used.size()+StringSetTransfer.hs_high_fb.size()+StringSetTransfer.mhs_high.size()+StringSetTransfer.mhs_high_used.size()+StringSetTransfer.hs_high_tf.size()+StringSetTransfer.hs_high.size()) + "");          

                     //now we also want 2000 to insert into StringSetTransfer.nameMap all the student names
                     s = myConnection.createStatement();
                     rs = s.executeQuery("select studentNo, name from students");
                     while (rs.next()){
                        tempUserName = rs.getString(1);
                        tempTrueName = rs.getString(2);
                        StringSetTransfer.nameMap.put(tempUserName, tempTrueName);
                     }  

                     s = myConnection.createStatement();
                     s.executeUpdate("update student_score set status = 'initiated' where studentNo ='2000' ");

               }//THE END OF ELSE userName.equals("2000")

               s = myConnection.createStatement();
               rs = s.executeQuery("select playerId, planStatus from student_score where studentNo = '" + userName + "'");
               while (rs.next()){
                  playerId = rs.getString("playerId");
                  planStatus = rs.getString("planStatus");
               }
               pb2.setPlayerId(playerId);              
               pb2.setPlanStatus(planStatus);

            }catch (SQLException sqle) {
               getServlet().log("Connection.process", sqle);
            } finally {           
                try {
                    if (rs != null) rs.close();
                    if (s != null) s.close();
                    if (myConnection != null) myConnection.close();
                } catch (SQLException e) {
                    getServlet().log("Connection.close", e);
                }
            }
        }else{//first.equals("false")
            String answeredHashSetStr = (String)((SmartRandomForm) form).getAnsweredHashSet();
            hs = StringSetTransfer.stringToSet(answeredHashSetStr);
            String answeredHashSetStr_tf = (String)((SmartRandomForm) form).getAnsweredHashSet_tf();
            hs_tf = StringSetTransfer.stringToSet(answeredHashSetStr_tf);
            String answered_M_HashSetStr = (String)((SmartRandomForm) form).getAnswered_M_HashSet();
            mhs = StringSetTransfer.stringToSet(answered_M_HashSetStr);
            String answeredHashSetStr_fb = (String)((SmartRandomForm) form).getAnsweredHashSet_fb();
            hs_fb = StringSetTransfer.stringToSet(answeredHashSetStr_fb);            
            String answeredHashSetStr_fdb = (String)((SmartRandomForm) form).getAnsweredHashSet_fdb();
            hs_fdb = StringSetTransfer.stringToSet(answeredHashSetStr_fdb);
            String answeredHashSetStr_fqb = (String)((SmartRandomForm) form).getAnsweredHashSet_fqb();
            hs_fqb = StringSetTransfer.stringToSet(answeredHashSetStr_fqb);
            String answeredHashSetStr_tm = (String)((SmartRandomForm) form).getAnsweredHashSet_tm();  
            hs_tm = StringSetTransfer.stringToSet(answeredHashSetStr_tm);
           /*
            String hashSet_lowStr = (String)((SmartRandomForm) form).getHashSet_low();
            stringSetTransfer.hs_low = StringSetTransfer.stringToSet(hashSet_lowStr);
            String hashSet_middleStr = (String)((SmartRandomForm) form).getHashSet_middle();
            stringSetTransfer.hs_middle = StringSetTransfer.stringToSet(hashSet_middleStr);
            String hashSet_highStr = (String)((SmartRandomForm) form).getHashSet_high();
            stringSetTransfer.hs_high = StringSetTransfer.stringToSet(hashSet_highStr);

            String hashSet_low_tfStr = (String)((SmartRandomForm) form).getHashSet_low_tf();
            stringSetTransfer.hs_low_tf = StringSetTransfer.stringToSet(hashSet_low_tfStr);
            String hashSet_middle_tfStr = (String)((SmartRandomForm) form).getHashSet_middle_tf();
            stringSetTransfer.hs_middle_tf = StringSetTransfer.stringToSet(hashSet_middle_tfStr);
            String hashSet_high_tfStr = (String)((SmartRandomForm) form).getHashSet_high_tf();
            stringSetTransfer.hs_high_tf = StringSetTransfer.stringToSet(hashSet_high_tfStr);

            String hashSet_low_fbStr = (String)((SmartRandomForm) form).getHashSet_low_fb();
            stringSetTransfer.hs_low_fb = StringSetTransfer.stringToSet(hashSet_low_fbStr);
            String hashSet_middle_fbStr = (String)((SmartRandomForm) form).getHashSet_middle_fb();
            stringSetTransfer.hs_middle_fb = StringSetTransfer.stringToSet(hashSet_middle_fbStr);
            String hashSet_high_fbStr = (String)((SmartRandomForm) form).getHashSet_high_fb();
            stringSetTransfer.hs_high_fb = StringSetTransfer.stringToSet(hashSet_high_fbStr);

            String hashSet_low_fdbStr = (String)((SmartRandomForm) form).getHashSet_low_fdb();
            stringSetTransfer.hs_low_fdb = StringSetTransfer.stringToSet(hashSet_low_fdbStr);
            String hashSet_middle_fdbStr = (String)((SmartRandomForm) form).getHashSet_middle_fdb();
            stringSetTransfer.hs_middle_fdb = StringSetTransfer.stringToSet(hashSet_middle_fdbStr);
            String hashSet_high_fdbStr = (String)((SmartRandomForm) form).getHashSet_high_fdb();
            stringSetTransfer.hs_high_fdb = StringSetTransfer.stringToSet(hashSet_high_fdbStr);

            String hashSet_low_mStr = (String)((SmartRandomForm) form).getHashSet_low_m();
            stringSetTransfer.mhs_low = StringSetTransfer.stringToSet(hashSet_low_mStr);
            String hashSet_middle_mStr = (String)((SmartRandomForm) form).getHashSet_middle_m();
            stringSetTransfer.mhs_middle = StringSetTransfer.stringToSet(hashSet_middle_mStr);
            String hashSet_high_mStr = (String)((SmartRandomForm) form).getHashSet_high_m();
            stringSetTransfer.mhs_high = StringSetTransfer.stringToSet(hashSet_high_mStr);
            */
        }

        // Remove the Form Bean - don't need to carry values forward
        request.removeAttribute(mapping.getAttribute());

        session.setAttribute( Constants.PERSON_KEY, pb2); 
        request.setAttribute( Constants.PERSON_KEY, pb2); 

        if (userName.equals("2000")){
           return (mapping.findForward("Show2000Zero"));
        }
         
        String lastType = (String)((SmartRandomForm) form).getLastType();
        String thisType = (String)((SmartRandomForm) form).getThisType();
        String continueRight = (String)((SmartRandomForm) form).getContinueRight();
        String continueWrong = (String)((SmartRandomForm) form).getContinueWrong();
        int intContinueRight = Integer.parseInt(continueRight);
        int intContinueWrong = Integer.parseInt(continueWrong);
        String neverHigh = (String)((SmartRandomForm) form).getNeverHigh();
        boolean boolNeverHigh = Boolean.parseBoolean(neverHigh);
        System.out.println("Inside SmartRandomAction.java, lastType = " + lastType);

        int randomNumber = 0;
        HashSet control = new HashSet();
        control.add(new Integer(0)); control.add(new Integer(1)); control.add(new Integer(2)); control.add(new Integer(3)); control.add(new Integer(4));
        HashSet forCom = new HashSet();
        int id = 0;
        HashSet forType = new HashSet();
        String lastCorrect = (String)((SmartRandomForm) form).getLastCorrect();
        System.out.println("Inside SmartRandomAction.java, lastCorrect = " + lastCorrect);

        boolean boolLowNotUsedUp = true;
        boolean boolMiddleNotUsedUp = true;
        boolean boolHighNotUsedUp = true;
        
        // if first == true, then thisType = L for sure, thus we can directly find id
        if (first.equals("true")){              
           lastType = "L";
           thisType = "L";             
        }

        //in below we will implement the same logic no matter first==true or not
        outer:
        while (true){           
           id = 0;           
          
                 //however, first, we want to check if there are exceptions
                 //now to simplify the process, we want to get rid of this exception
                 //if there is no exception, then we want to decide thisType 
                 
                 //we want to enforce the lastCorrect mechanism only when none of low,middle,high are used up
                 if (boolLowNotUsedUp && boolMiddleNotUsedUp && boolHighNotUsedUp){
                    boolean boolLastCorrect = Boolean.parseBoolean(lastCorrect);
                    if (boolLastCorrect){
                       switch (lastType.charAt(0)){
                          case 'L':
                             thisType = 'M' + "";
                             break;
                          case 'M':
                             thisType = 'H' + "";              
                             break;
                          case 'H':
                             thisType = 'H' + "";           
                             break;
                          default:
                             thisType = 'L' + "";
                             break;
                       }
                    }else{ //booLastCorrect == false
                       switch (lastType.charAt(0)){
                          case 'L':
                             thisType = 'L' + "";      
                             break;
                          case 'M':
                             thisType = 'L' + "";
                             break;
                          case 'H':
                             thisType = 'M' + "";
                             break;
                          default:
                             thisType = 'L' + "";
                             break;
                        }
                     }
                 }
             
             //up to this point, we have decided the value of thisChar for both first==true and first!=true

                 System.out.println("Inside SmartRandomAction, thisType = " + thisType);
                 //below we will not care about any used set when we decide whether low,middle,high are used up
                 switch (thisType.charAt(0)){
                    case 'L':
                       System.out.println("Inside SmartRandomAction, case 'L', thisType = " + thisType);
                       HashSet temp_hs_low = new HashSet(StringSetTransfer.hs_low);
                       temp_hs_low.removeAll(StringSetTransfer.hs_low_used);
                       HashSet temp_hs_low_tf = new HashSet(StringSetTransfer.hs_low_tf);
                       temp_hs_low_tf.removeAll(StringSetTransfer.hs_low_tf_used);
                       if (hs.containsAll(temp_hs_low)){
                          if (hs_tf.containsAll(temp_hs_low_tf)){
                             boolLowNotUsedUp = false;
                             if (boolMiddleNotUsedUp){
                                thisType = "M";
                                continue outer;
                             }else if (boolHighNotUsedUp){
                                thisType = "H";
                                continue outer;
                             }else{
                                planStatus = "finished";
                                pb2.setPlanStatus(planStatus);
                                id = 0;
                                break outer;
                             }
                          }
                       }
                       randomNumber = (int)(Math.random() * 2); //generate randomNumber= 0,1
                       switch (randomNumber){
                          case 0:
                             id = StringSetTransfer.getRandomNumber(thisType.charAt(0), hs);
                             if (id == 0){
                                continue outer;
                             } 
                             break;
                          case 1:
                             id = StringSetTransfer.getRandomNumber_tf(thisType.charAt(0), hs_tf); 
                             if (id == 0){
                                continue outer;
                             }
                             break;
                          default:
                             break;    
                       }//up to this point, we have also decided the value of id for type 'L'
                       break;
                    case 'M':
                       System.out.println("Inside SmartRandomAction, case 'M', thisType = " + thisType);
                       
                       if (hs_fb.containsAll(StringSetTransfer.hs_middle_fb)){
                          if (hs_tm.containsAll(StringSetTransfer.hs_middle_tm)){
                             System.out.println("Inside SmartRandomAction, case 'M', this means all middle problems are ex = " + thisType);
                             boolMiddleNotUsedUp = false;
                             if (boolLowNotUsedUp){
                                thisType = "L";
                                continue outer;
                             }else if (boolHighNotUsedUp){
                                thisType = "H";
                                continue outer;
                             }else{
                                planStatus = "finished";
                                pb2.setPlanStatus(planStatus);
                                id = 0;
                                break outer;
                             }  
                          }
                       }
                       if (Math.random() > 0.5){
                          randomNumber = 3;
                       }else{
                          randomNumber = 5;
                       }
                       switch(randomNumber){
                          case 3:
                             id = StringSetTransfer.getRandomNumber_fb(thisType.charAt(0), hs_fb); 
                             if (id == 0){
                                continue outer;
                             }
                             break;
                          case 5:
                             id = StringSetTransfer.getRandomNumber_tm(thisType.charAt(0), hs_tm); 
                             if (id == 0){
                                continue outer;
                             }
                             break;
                          default:
                             break;
                       }
                       //up to this point, we have also decided the value of id for type 'M'
                       break;
                    case 'H':
                       System.out.println("Inside SmartRandomAction, case 'H', thisType = " + thisType);
                       if (mhs.containsAll(StringSetTransfer.mhs_high)){
                          if (hs_fdb.containsAll(StringSetTransfer.hs_high_fdb)){
                             if (hs_fqb.containsAll(StringSetTransfer.hs_high_fqb)){
                                boolHighNotUsedUp = false;
                                if (boolLowNotUsedUp){
                                   thisType = "L";
                                   continue outer;
                                }else if (boolMiddleNotUsedUp){
                                   thisType = "M";
                                   continue outer;
                                }else{
                                   planStatus = "finished";
                                   pb2.setPlanStatus(planStatus);
                                   id = 0;
                                   break outer;
                                }  
                             }
                          }
                       }
                       if (Math.random() < 0.3){
                          randomNumber = 2;
                       }else if (Math.random() < 0.7){
                          randomNumber = 4;
                       }else{
                          randomNumber = 6;
                       }
                       switch(randomNumber){
                          case 2:
                             id = StringSetTransfer.get_M_RandomNumber(thisType.charAt(0), mhs); 
                             if (id == 0){
                                continue outer;
                             }
                             break;
                          case 4:
                             id = StringSetTransfer.getRandomNumber_fdb(thisType.charAt(0), hs_fdb); 
                             if (id == 0){
                                continue outer;
                             }
                             break;
                          case 6:
                             id = StringSetTransfer.getRandomNumber_fqb(thisType.charAt(0), hs_fqb); 
                             if (id == 0){
                                continue outer;
                             }
                             break;
                          default:
                             break;
                       }//up to this point, we have also decided the value of id for type 'H'
                       break;
                    default:
                       break;
                 }
                 //up to this point, we have also decided the value of id
                  
                 lastType = thisType;


          if (id != 0){
             break outer;
          }
     }//end of while(true)

     System.out.println("Inside SmartRandomAction, after id and randomNumber is obtained, id = " + id + " and randomNumber = " + randomNumber);

     if (id != 0){
        pb2.setCurrentProblemId(id);

        try {
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");    
                
           pb2.setLastType(lastType);
           pb2.setThisType(thisType);
           pb2.setContinueRight(intContinueRight);
           pb2.setContinueWrong(intContinueWrong);
           pb2.setNeverHigh(boolNeverHigh);

           switch (randomNumber){
              case 0:
                  hs.add(new Integer(id));  
                  stmt = myConnection.prepareStatement("select * from questions2 where id=?");
                  stmt.clearParameters();
                  stmt.setInt(1, id);
                  rs = stmt.executeQuery();
                  while (rs.next()){
                     pb.setId(rs.getInt("id"));
                     pb.setStatement(rs.getString("statement"));
                     pb.setChoiceA(rs.getString("choiceA"));
                     pb.setChoiceB(rs.getString("choiceB"));
                     pb.setChoiceC(rs.getString("choiceC"));
                     pb.setChoiceD(rs.getString("choiceD"));
                     pb.setCorrectChoice(rs.getString("correctChoice")); 
                     pb.setSolution(rs.getString("solution"));                             
                     pb.setType(rs.getString("type"));
                  }
                  break;
               case 1:
                  hs_tf.add(new Integer(id)); 
                  stmt = myConnection.prepareStatement("select * from choices2 where id=?");
                  //getServlet().log("inside SmartRandomAction.java, when select * from choices2 where id=?,  id = " + id);
                  stmt.clearParameters();
                  stmt.setInt(1, id);
                  rs = stmt.executeQuery();
                  while (rs.next()){
                     cb.setId(rs.getInt("id"));
                     cb.setStatement(rs.getString("statement"));
                     cb.setChoice(rs.getString("choice"));     
                     cb.setSolution(rs.getString("solution"));         
                     cb.setType(rs.getString("type"));
                  }
                  break;
               case 2:
                  mhs.add(new Integer(id));  
                  stmt = myConnection.prepareStatement("select * from multipleQuestions2 where id=?");
                  stmt.clearParameters();
                  stmt.setInt(1, id);
                  rs = stmt.executeQuery();
                  while (rs.next()){
                     mpb.setId(rs.getInt("id"));
                     mpb.setStatement(rs.getString("statement"));
                     mpb.setChoiceA(rs.getString("choiceA"));
                     mpb.setChoiceB(rs.getString("choiceB"));
                     mpb.setChoiceC(rs.getString("choiceC"));
                     mpb.setChoiceD(rs.getString("choiceD"));
                     mpb.setChoiceE(rs.getString("choiceE"));
                     mpb.setCorrectChoice(rs.getString("correctChoice")); 
                     mpb.setSolution(rs.getString("solution"));                              
                     mpb.setType(rs.getString("type"));
                  }
                  break;
               case 3:
                  hs_fb.add(new Integer(id)); 
                  stmt = myConnection.prepareStatement("select * from fillBlank2 where id=?");
                  stmt.clearParameters();
                  stmt.setInt(1, id);
                  rs = stmt.executeQuery();
                  while (rs.next()){
                     fbb.setId(rs.getInt("id"));
                     fbb.setStatement_1st(rs.getString("statement_1st"));
                     fbb.setStatement_2nd(rs.getString("statement_2nd"));
                     fbb.setSolution(rs.getString("solution"));
                     fbb.setType(rs.getString("type"));
                  }
                  break;
               case 4:
                  hs_fdb.add(new Integer(id)); 
                  stmt = myConnection.prepareStatement("select * from fillDoubleBlanks2 where id=?");
                  stmt.clearParameters();
                  stmt.setInt(1, id);
                  rs = stmt.executeQuery();
                  while (rs.next()){
                     fdbb.setId(rs.getInt("id"));
                     fdbb.setStatement_1st(rs.getString("statement_1st"));
                     fdbb.setStatement_2nd(rs.getString("statement_2nd"));
                     fdbb.setStatement_3rd(rs.getString("statement_3rd"));
                     fdbb.setSolution_1st(rs.getString("solution_1st"));
                     fdbb.setSolution_2nd(rs.getString("solution_2nd"));
                     //String source = rs.getString("source");
                     fdbb.setType(rs.getString("type"));
                  }
                  //getServlet().log("at initiation in SmartRandomActioin: fdbb is reached");
                  break;
               case 5:
                  /*
                  hs_tm.add(new Integer(id));                    
                  stmt = myConnection.prepareStatement("select * from termMatch2 where id=?");
                  stmt.clearParameters();
                  stmt.setInt(1, id);
                  rs = stmt.executeQuery();
                  while (rs.next()){
                     tmb.setId(rs.getInt("id"));
                     tmb.setStatement(rs.getString("statement"));
                     tmb.setChoiceA(rs.getString("choiceA"));
                     tmb.setChoiceB(rs.getString("choiceB"));
                     tmb.setChoiceC(rs.getString("choiceC"));
                     tmb.setChoiceD(rs.getString("choiceD"));
                     tmb.setCorrectChoice(rs.getString("correctChoice")); 
                     tmb.setSolution(rs.getString("solution"));                             
                     tmb.setType(rs.getString("type"));
                     tmb.setSource(rs.getString("source"));
                    
                     stmt2 = myConnection.prepareStatement("insert into usedTermMatch2(Id,Type,Times,StudentNo,StudentAnswer,source) values(?,'M',?,?,?,?)");
                     stmt2.clearParameters();
                     stmt2.setInt(1, rs.getInt("id"));
                     stmt2.setString(2, times);
                     stmt2.setString(3, userName);
                     stmt2.setString(4, "");
                     stmt2.setString(5, rs.getString("source"));
                     stmt2.execute();                                  
                  }                
                  */
                  /*
                  stmt = myConnection.prepareStatement("select * from termMatch where id=?");
                  stmt.clearParameters();
                  stmt.setInt(1, id);
                  rs = stmt.executeQuery();
                  while (rs.next()){
                     tmb.setId(rs.getInt("id"));
                     tmb.setTerm1(rs.getString("term1"));
                     tmb.setTerm2(rs.getString("term2"));
                     tmb.setType(rs.getString("type"));                     
                     tmb.setSource(rs.getString("source"));                     
                  }
                  */
                  break; 
               case 6:
                  hs_fqb.add(new Integer(id)); 
                  stmt = myConnection.prepareStatement("select * from fillQuadBlanks2 where id=?");
                  stmt.clearParameters();
                  stmt.setInt(1, id);
                  rs = stmt.executeQuery();
                  while (rs.next()){
                     fqbb.setId(rs.getInt("id"));
                     fqbb.setStatement_1st(rs.getString("statement_1st"));
                     fqbb.setStatement_2nd(rs.getString("statement_2nd"));
                     fqbb.setStatement_3rd(rs.getString("statement_3rd"));
                     fqbb.setStatement_4th(rs.getString("statement_4th"));
                     fqbb.setStatement_5th(rs.getString("statement_5th"));
                     fqbb.setSolution_1st(rs.getString("solution_1st"));
                     fqbb.setSolution_2nd(rs.getString("solution_2nd"));
                     fqbb.setSolution_3rd(rs.getString("solution_3rd"));
                     fqbb.setSolution_4th(rs.getString("solution_4th"));
                     //String source = rs.getString("source");
                     fqbb.setType(rs.getString("type"));
                  }
                  System.out.println("at initiation in SmartRandomActioin: fqbb is reached");
                  break;             
               default:
                  break;
           }//end of switch(randomNumber)                       
        } catch (SQLException sqle) {
            getServlet().log("Connection.process", sqle);
        } finally {
            //enclose this in a finally block to make
            //sure the connection is closed
            try {
               if (rs != null) rs.close();             
               if (stmt != null) stmt.close();
               if (stmt2 != null) stmt2.close();
               if (myConnection != null) myConnection.close();
            } catch (SQLException e) {
               getServlet().log("Connection.close", e);
            }
        }//end of try-catch-finally block 
     }else {//id==0 and planStatus.equals("finished")
        //do nothing
     }  
       
        switch (randomNumber){
            case 0:
               request.setAttribute( Constants.PROBLEM_KEY, pb);        
               session.setAttribute( Constants.PROBLEM_KEY, pb); 
               break;
            case 1:
               request.setAttribute( Constants.CHOICE_KEY, cb);        
               session.setAttribute( Constants.CHOICE_KEY, cb);   
               break;
            case 2:
               request.setAttribute( Constants.MULTIPLEPROBLEM_KEY, mpb);        
               session.setAttribute( Constants.MULTIPLEPROBLEM_KEY, mpb); 
               break;
            case 3:
               request.setAttribute( Constants.FILLBLANK_KEY, fbb);        
               session.setAttribute( Constants.FILLBLANK_KEY, fbb); 
               break;
            case 4:
               request.setAttribute( Constants.FILLDOUBLEBLANK_KEY, fdbb);        
               session.setAttribute( Constants.FILLDOUBLEBLANK_KEY, fdbb); 
               break;
            case 5:
               request.setAttribute( Constants.TERMMATCH_KEY, tmb);        
               session.setAttribute( Constants.TERMMATCH_KEY, tmb); 
               break;
            case 6:
               request.setAttribute( Constants.FILLQUADBLANK_KEY, fqbb);        
               session.setAttribute( Constants.FILLQUADBLANK_KEY, fqbb); 
               break;
            default:
               break;
        }          
        
        session.setAttribute( Constants.HASHSET_KEY, StringSetTransfer.setToString(hs));   
        session.setAttribute( Constants.HASHSET_TF_KEY, StringSetTransfer.setToString(hs_tf));
        session.setAttribute( Constants.HASHSET_M_KEY, StringSetTransfer.setToString(mhs)); 
        session.setAttribute( Constants.HASHSET_FB_KEY, StringSetTransfer.setToString(hs_fb));
        session.setAttribute( Constants.HASHSET_FDB_KEY, StringSetTransfer.setToString(hs_fdb));
        session.setAttribute( Constants.HASHSET_FQB_KEY, StringSetTransfer.setToString(hs_fqb));
        session.setAttribute( Constants.HASHSET_TM_KEY, StringSetTransfer.setToString(hs_tm));
        request.setAttribute( Constants.HASHSET_KEY, StringSetTransfer.setToString(hs));   
        request.setAttribute( Constants.HASHSET_TF_KEY, StringSetTransfer.setToString(hs_tf));
        request.setAttribute( Constants.HASHSET_M_KEY, StringSetTransfer.setToString(mhs)); 
        request.setAttribute( Constants.HASHSET_FB_KEY, StringSetTransfer.setToString(hs_fb)); 
        request.setAttribute( Constants.HASHSET_FDB_KEY, StringSetTransfer.setToString(hs_fdb)); 
        request.setAttribute( Constants.HASHSET_FQB_KEY, StringSetTransfer.setToString(hs_fqb)); 
        request.setAttribute( Constants.HASHSET_TM_KEY, StringSetTransfer.setToString(hs_tm));
        
        System.out.println("at SmartRandomAction, hs string is " + StringSetTransfer.setToString(hs));
        System.out.println("SmartRandomAction, hs_tf str is " + StringSetTransfer.setToString(hs_tf));
        System.out.println("SmartRandomAction, mhs str is " + StringSetTransfer.setToString(mhs));   
        System.out.println("SmartRandomAction, hs_fb str is " + StringSetTransfer.setToString(hs_fb));   
        System.out.println("SmartRandomAction, hs_fdb str is " + StringSetTransfer.setToString(hs_fdb));
        System.out.println("SmartRandomAction, hs_fqb str is " + StringSetTransfer.setToString(hs_fqb));
        System.out.println("SmartRandomAction, hs_tm str is " + StringSetTransfer.setToString(hs_tm));                      
        
        String answeredProblems = (String)((SmartRandomForm) form).getAnsweredProblems();
        String correctAnswers = (String)((SmartRandomForm) form).getCorrectAnswers();
        int intAnsweredProblems = Integer.parseInt(answeredProblems);
        int intCorrectAnswers = Integer.parseInt(correctAnswers);           
        pb2.setAnsweredProblems(intAnsweredProblems);
        pb2.setCorrectAnswers(intCorrectAnswers); 

        String totalScore = (String)((SmartRandomForm) form).getTotalScore();
        String correctAnswers_low = (String)((SmartRandomForm) form).getCorrectAnswers_low();
        String answeredProblems_low = (String)((SmartRandomForm) form).getAnsweredProblems_low();
        double dbTotalScore = Double.parseDouble(totalScore);
        int intCorrectAnswers_low = Integer.parseInt(correctAnswers_low);
        int intAnsweredProblems_low = Integer.parseInt(answeredProblems_low);                      
        pb2.setTotalScore(dbTotalScore);
        pb2.setCorrectAnswers_low(intCorrectAnswers_low);
        pb2.setAnsweredProblems_low(intAnsweredProblems_low);   

        String correctAnswers_middle = (String)((SmartRandomForm) form).getCorrectAnswers_middle();
        String answeredProblems_middle = (String)((SmartRandomForm) form).getAnsweredProblems_middle();        
        int intCorrectAnswers_middle = Integer.parseInt(correctAnswers_middle);
        int intAnsweredProblems_middle = Integer.parseInt(answeredProblems_middle);       
        pb2.setCorrectAnswers_middle(intCorrectAnswers_middle);
        pb2.setAnsweredProblems_middle(intAnsweredProblems_middle); 

        String correctAnswers_high = (String)((SmartRandomForm) form).getCorrectAnswers_high();
        String answeredProblems_high = (String)((SmartRandomForm) form).getAnsweredProblems_high();        
        int intCorrectAnswers_high = Integer.parseInt(correctAnswers_high);
        int intAnsweredProblems_high = Integer.parseInt(answeredProblems_high);       
        pb2.setCorrectAnswers_high(intCorrectAnswers_high);
        pb2.setAnsweredProblems_high(intAnsweredProblems_high);         

        //String times = (String)((SmartRandomForm) form).getTimes();
        //pb2.setTimes(times);
        pb2.setRandomNumber(new Integer(randomNumber).toString());          

        //now we are going to set up the timer
        if (first.equals("true")){
           Timer aTimer = new Timer(userName);
           MyTask myTask = new MyTask(userName);           
           aTimer.scheduleAtFixedRate(myTask, 0, 30000);
        }          

        //getServlet().log("before forward to Pair2000Zero");
        /*
        session.setAttribute( Constants.PERSON_KEY, pb2); 
        request.setAttribute( Constants.PERSON_KEY, pb2); 

        if (userName.equals("2000")){
           return (mapping.findForward("Show2000Zero"));
        }    
        */

        //now get the remainSeconds and update planStatus
        //javax.sql.DataSource dataSource = null;
        //java.sql.Connection myConnection = null; 
        //Statement s = null;
        //ResultSet rs = null;         
        try{
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
           s = myConnection.createStatement();
           rs = s.executeQuery("select remainSeconds from student_time where studentNo='" + userName + "'");
           int remainSeconds = 0;
           while (rs.next()){
              remainSeconds = rs.getInt("remainSeconds");              
           }
           pb2.setRemainSeconds(remainSeconds);
           pb2.setRemainSeconds(1800); //now we want this demonstration program not to end

           if (pb2.getPlanStatus().equals("finished")){
              s = myConnection.createStatement();
              s.executeUpdate("update student_score set planStatus='finished' where studentNo = '" + userName + "'");
           }
        }catch (SQLException ex){
           getServlet().log("Inside RandomCheckAction.java, process.", ex);
        }finally{
           try{
              if (rs != null) rs.close();
              if (s != null) s.close();
              if (myConnection != null) myConnection.close();
           }catch (SQLException e){
              getServlet().log("Inside RandomCheckAction.java, closing.", e);
           }
        }
     
        session.setAttribute( Constants.PERSON_KEY, pb2); 
        request.setAttribute( Constants.PERSON_KEY, pb2);  

        //below we want to find the student who needs an opportunity to answer this question
        HashSet candidateStudents = new HashSet();
        String scoreNumber = "score_1st";
        String sql = "";
        outer2:
        while(true){
           try{
              dataSource = getDataSource(request);
              myConnection = dataSource.getConnection();
              //Class.forName("org.hsqldb.jdbcDriver");
              //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");

              sql = "select studentNo from students where " + scoreNumber + "=?";
              stmt = myConnection.prepareStatement(sql);
              stmt.clearParameters();
              stmt.setInt(1, -1);
              rs = stmt.executeQuery();           
              while (rs.next()){
                 tempUserName = rs.getString("studentNo");             
                 candidateStudents.add(tempUserName);             
              }
              candidateStudents.remove("2000");

              if (candidateStudents.size()==0){                    
                 if (scoreNumber.equals("score_1st"))
                    scoreNumber = "score_2nd";
                 else if (scoreNumber.equals("score_2nd"))
                    scoreNumber = "score_3rd";
                 else if (scoreNumber.equals("score_3rd"))
                    scoreNumber = "score_4th";
                 else if (scoreNumber.equals("score_4th"))
                    scoreNumber = "score_5th";
                 else if (scoreNumber.equals("score_5th"))
                    scoreNumber = "score_6th";
                 else if (scoreNumber.equals("score_6th"))
                    scoreNumber = "score_7th";
                 else if (scoreNumber.equals("score_7th"))
                    scoreNumber = "score_8th";
                 else if (scoreNumber.equals("score_8th"))
                    scoreNumber = "score_9th";
              }else{
                 break outer2;
              }
           
           }catch (SQLException ex){
              getServlet().log("Inside SmartRandomAction.java, process.", ex);
           }finally{
              try{
                 if (rs != null) rs.close();
                 if (s != null) s.close();
                 if (stmt != null) stmt.close(); 
                 if (myConnection != null) myConnection.close();
              }catch (SQLException e){
                 getServlet().log("Inside SmartRandomAction.java, closing.", e);
              }
           } 
        }

        //decide the student selected to answer the question
        int selectNumber = (int)(Math.random() * candidateStudents.size());                  
        Iterator iter = candidateStudents.iterator();
        int index = 0;
        while (iter.hasNext()) {
           if (index == selectNumber){
              tempUserName = (String)iter.next();
              System.out.println("inside SmartRandomAction, the selected student = " + tempUserName);
              break;
           }
           iter.next();
           index++;                      
        }
         
        //get all the information about this student
        try{
           dataSource = getDataSource(request);
           myConnection = dataSource.getConnection();
           //Class.forName("org.hsqldb.jdbcDriver");
           //myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
           s = myConnection.createStatement();
           sql = "select password from students where studentNo='" + tempUserName + "'";
           rs = s.executeQuery(sql);          
           while (rs.next()){
              tempPassword = rs.getString("password");              
           }

           s = myConnection.createStatement();
           rs = s.executeQuery("select playerId from student_score where studentNo='" + tempUserName + "'");          
           while (rs.next()){
              tempPlayerId = rs.getString("playerId");              
           }           
        }catch (SQLException ex){
           getServlet().log("Inside SmartRandomCheckAction.java, process.", ex);
        }finally{
           try{
              if (rs != null) rs.close();
              if (s != null) s.close();
              if (myConnection != null) myConnection.close();
           }catch (SQLException e){
              getServlet().log("Inside RandomCheckAction.java, closing.", e);
           }
        }
        pb2.setUserName(tempUserName);
        pb2.setPassWord(tempPassword); 
        pb2.setTrueName((String)StringSetTransfer.nameMap.get(tempUserName));
        pb2.setPlayerId(tempPlayerId);  

        session.setAttribute( Constants.PERSON_KEY, pb2); 
        request.setAttribute( Constants.PERSON_KEY, pb2);  

        // Forward control to the specified success URI
        switch (randomNumber){
            case 0:
               return (mapping.findForward("ShowSmartProblem"));               
            case 1:
               return (mapping.findForward("ShowSmartChoice")); 
            case 2:
               return (mapping.findForward("ShowSmartMultipleProblem"));   
            case 3:
               return (mapping.findForward("ShowFillBlank"));   
            case 4:
               return (mapping.findForward("ShowFillDoubleBlank"));
            case 5:
               return (mapping.findForward("ShowTermMatch"));  
            case 6:
               return (mapping.findForward("ShowFillQuadBlank"));
            default:
               break;
        }          
        return (mapping.findForward("ShowSmartProblem"));        
    }
}


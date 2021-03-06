package util;

import java.util.*;
import java.sql.*;

public class MyTask extends TimerTask{
    int count = 0;
    int totalSeconds = 0;    
    public String userName;

    public MyTask(){
       super();
    }

    public MyTask(String userName){
       super();
       this.userName = userName;
    }
      
    public void run(){
       javax.sql.DataSource dataSource = null;
       java.sql.Connection myConnection = null;          
       PreparedStatement stmt = null;        
       count ++;
       totalSeconds = 1830 - count * 30;
       if (totalSeconds >= 0){
          try{
             Class.forName("org.hsqldb.jdbcDriver");
             myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
            
   stmt = myConnection.prepareStatement("update student_time set remainSeconds = ? where studentNo = '" + userName + "'");
             stmt.clearParameters();
             stmt.setInt(1, totalSeconds);
             stmt.executeUpdate();   
   
             System.out.println("inside MyTask, totalSeconds = " + totalSeconds);
          }catch (Exception ex){
             System.out.println("inside MyTask" + ex.getMessage());
          }finally{
             try{
                if (stmt != null) stmt.close();
                if (myConnection != null) myConnection.close(); 
             }catch (Exception ex){
                System.out.println(ex.getMessage());
             }
          }
       }
    }
}

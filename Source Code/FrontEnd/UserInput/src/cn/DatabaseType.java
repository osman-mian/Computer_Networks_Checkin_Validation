package cn;

import java.sql.*;
import javax.swing.JOptionPane;

public class DatabaseType {
    
    static String ip1="localhost";
    static String ip2="localhost";
    static String userId="root";
    static String password="";
    
    private Connection connection;
    
    public DatabaseType()
    {
    }
    
    public static void setLogInfo(String[] details)
    {
        details[0]="localhost";
        details[1]="localhost";
        details[2]="root";
        details[3]="";
    }
    
    public int insert(String query)
    {
        int x=1;
        try {
            x=connection.createStatement().executeUpdate(query);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,query);
            JOptionPane.showMessageDialog(null, "Error in Insert: "+ex.getMessage(),"Oops",JOptionPane.ERROR_MESSAGE);
        }
        
        return x;
    }
    
    public int delete(String query)
    {
         return insert(query);
    }
    
    public int update(String query)
    {
        return insert(query);
    }
    
    public ResultSet read(String query)
    {
        ResultSet rs=null;
        
         try
         {
             rs=connection.createStatement().executeQuery(query);
         }
         catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage(),"Oops",JOptionPane.ERROR_MESSAGE);
             //Prints: ERROR NO OPERATION ALLOWED AFTER CONNECTION CLOSED MESSAGE STRANGELY
        }

        return rs;
    }
    
    public void commit()
    {
        try {
            connection.createStatement().execute("commit");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error in Commit: "+ex.getMessage(),"Oops",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void rollback()
    {
        try {
           // connection.createStatement().execute("roll back");
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage(),"Oops",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public int openConnection()
    {
        int x=1;
       		try 
                {
 
                        //System.out.println("jdbc:mysql://10.11.0.2/project?user=ooadUser&password=ooad");
			
                     String url = "jdbc:mysql://"+ip1+"/lbs?connectTimeout="+500;
                     connection = DriverManager.getConnection(url,userId,password);
        
       
                    //connection=DriverManager.getConnection("jdbc:mysql://"+ip1+"/project?user="+userId+"&password="+password);//MySQL
                       
                        
                        //connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "mercury","fast");//Oracle
		} 
                catch (Exception ex) 
                {
                            //a = 2 hard-coded because backup not implemented yet
                         int a = 2;//JOptionPane.showConfirmDialog(null, "Server Down Click ok to switch server!","Confirmation Message",JOptionPane.OK_CANCEL_OPTION);
                         if(a==2) {
                            JOptionPane.showMessageDialog(null, "Error: Server Down, Program Will Exit"+ex.getMessage(),"Oops",JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                            return 0;
                         }
                         else{
                         String tempIp = ip1;
                         ip1=ip2;
                         ip2=tempIp;
                         openConnection();
                         }   
 			//System.out.println("Connection Failed! Check output console");
                       
			//ex.printStackTrace();
		}
 
		if (connection != null) 
                {
			//System.out.println("Connected");
		} 
                else 
                {
			JOptionPane.showMessageDialog(null, "Error: Server is Down, Program will Exit","Oops",JOptionPane.ERROR_MESSAGE);
                        x=0;
                        System.exit(0);
		}

                return x;
    }
    
    public int closeConnection()
    {
        int x=0;
    
        try
        {
            connection.close();
            x=1;
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error in close Connection: "+ex.getMessage(),"Oops",JOptionPane.ERROR_MESSAGE);
        }
        
        return x;
    }
    
}

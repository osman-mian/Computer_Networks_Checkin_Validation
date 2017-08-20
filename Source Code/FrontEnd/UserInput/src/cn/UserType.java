/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn;

import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author osman
 */
public class UserType {
    
    public static void UserStatus(String id,String status)
    {
        DatabaseType db=new DatabaseType();
        String query="Update user set isActive="+status+" where userId='"+id+"'";
        ResultSet rs;
 
        try
        {
            db.openConnection();
            db.update(query);
            db.closeConnection();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Problem in status user: "+ e.getMessage());
        }
    }
    
    
    
    
    public static boolean verifyUser(String id,String pass)
    {
        DatabaseType db=new DatabaseType();
        String query="Select password from user where userId='"+id+"'";
        ResultSet rs;
        boolean flag=false;
        
        try
        {
            db.openConnection();
            rs=db.read(query);
            rs.next();
            if(rs.getString("password").contentEquals(pass))
            {
                flag=true;
                UserStatus(id, "1");
            }
            db.closeConnection();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Problem in veirfy user: "+ e.getMessage());
        }
        
        
        return flag;
    }
    
}

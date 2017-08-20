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
public class Location {

    public static String getName(String id)
    {
        DatabaseType db=new DatabaseType();
        String query="Select pname from place where pid='"+id+"'";
        ResultSet rs;
        String details="0";
        
        try
        {
            db.openConnection();
            rs=db.read(query);
            rs.next();
            details=rs.getString(1);
            db.closeConnection();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Problem in veirfy user: "+ e.getMessage());
        }
        
        return details;
        
    }
    public static String[] getLatLong(String id)
    {
        DatabaseType db=new DatabaseType();
        String query="Select plat,plong from place where pId='"+id+"'";
        ResultSet rs;
        String[] details=new String[2];
        
        try
        {
            db.openConnection();
            rs=db.read(query);
            rs.next();
            
            for(int i=0; i<2;i++)
                details[i]=rs.getString(i+1);
            
            db.closeConnection();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Problem in veirfy user: "+ e.getMessage());
        }
        
        
        return details;
    }

    public static String[] getLocation(String id)
    {
        DatabaseType db=new DatabaseType();
        String query="Select * from place where pId='"+id+"'";
        ResultSet rs;
        String[] details=new String[6];
        
        try
        {
            db.openConnection();
            rs=db.read(query);
            rs.next();
            
            for(int i=0; i<6;i++)
                details[i]=rs.getString(i+1);
            
            db.closeConnection();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Problem in veirfy location: "+ e.getMessage());
        }
        
        
        return details;
    }
    public static void addLocation(String id,String name, String latitude, String longitude,String ip, String port)
    {
        DatabaseType db=new DatabaseType();
        String query="Insert into place values ('"
               +id+"','"
               +name+"','"
               +latitude+"','"
               +longitude+"','"
               +ip+"','"
               +port+"')";
        
        try
        {
            db.openConnection();
            db.insert(query);
            db.closeConnection();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Problem in adding location: "+ e.getMessage());
        }
        
        

    }
}

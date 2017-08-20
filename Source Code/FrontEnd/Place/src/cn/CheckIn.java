/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author arsalan
 */
public class CheckIn implements Serializable{

    public static void makeCheckin(String details[])
    {
        String query= "INSERT INTO checkIn (userId,placeId,ulat,ulong,ctime)" +
                "VALUES ('"+
                details[0]+"', '"+
                details[1]+"', '"+
                details[2]+"', '"+
                details[3]+"', "
                + "CURRENT_TIMESTAMP)";
        DatabaseType db=new DatabaseType();

        try
        {
            db.openConnection();
            db.insert(query);
            db.closeConnection();
            System.out.println("CheckIn Complete");
        }
        catch(Exception e)
        {
            System.out.println("CheckIn Error");
            JOptionPane.showMessageDialog(null,"Problem in making checkIn: "+ e.getMessage());
        }
        
               
        
    }
    public static String[] getLastCheckIn(String uid)
    {
        String[] m=new String[3];
        String query= "Select c.ulat, c.ulong, c.ctime, time_to_sec(timediff(CURRENT_TIMESTAMP, c.ctime))/60 as elapsed from checkin c, place p where c.userid='"+uid+"' AND p.pid=c.placeid order by c.ctime desc";
        DatabaseType db=new DatabaseType();
        ResultSet rs=null;
        try
        {
            db.openConnection();
            rs=db.read(query);
            rs.next();
            m[0]=rs.getString("ulat");
            m[1]=rs.getString("ulong");
            m[2]=rs.getString("elapsed");
            
            //JOptionPane.showMessageDialog(null,m[0] + " "+m[1]+" "+m[2]);
            db.closeConnection();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error in getting last location for userId #"+uid+": "+e.getMessage());
            return null;
        }
        
        return m;
        
    }

    public static int getCount(String uid,int i) 
    {
        int count=Integer.MAX_VALUE;
        String query= "Select count(*) as countV from checkIn where "
                + "time_to_sec(timediff(CURRENT_TIMESTAMP, ctime)) <="+i*60
                +" AND  userid='"+uid+"'";
        
        DatabaseType db=new DatabaseType();
        ResultSet rs=null;
        try
        {
            db.openConnection();
            rs=db.read(query);
            if(rs.next())
                count=rs.getInt("countV");
            
                
            db.closeConnection();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error in getting location count for userId #"+uid+": "+e.getMessage());
            return Integer.MAX_VALUE;
        }
        return count;
    }

    static int getAllCount(String uid) 
    {
        int count=Integer.MAX_VALUE;
        String query= "Select count(*) as countV from checkIn where userid='"+uid+"'";
        
        DatabaseType db=new DatabaseType();
        ResultSet rs=null;
        try
        {
            db.openConnection();
            rs=db.read(query);
            if(rs.next())
                count=rs.getInt("countV");
            else
                count=0;
            
                
            db.closeConnection();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error in getting location count for userId #"+uid+": "+e.getMessage());
            return Integer.MAX_VALUE;
        }
        return count;

       
    }

}

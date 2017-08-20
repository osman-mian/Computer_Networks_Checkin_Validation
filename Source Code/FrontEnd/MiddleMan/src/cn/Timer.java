/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author osman
 */
public class Timer extends Thread {
    
    JLabel label;
    Frm_Venue venuePage;
    int limit=72500;
    
    public  Timer (JLabel timer, Frm_Venue v)
    {
        label=timer;
        venuePage=v;
    }
    
    @Override
    public void run()
    {
       
        String q="Select userid,count(*) as checkIns from checkIn "
                + "where placeid='"+venuePage.getName()+"' "
                + "group by userid  "
                + "order by checkIns desc "
                + "LIMIT 2 ";
        DatabaseType db =new DatabaseType();
        db.openConnection();
        ResultSet rs;
        
        try
        {
            while(true)
            {
                rs=db.read(q);
                venuePage.updateMayor(rs);
                sleep(2000);
            }
            
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author osman
 */
public class Middle_Server extends Thread {

    protected Socket socket;//clients socket
    String user;//store username
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public Middle_Server(Socket clientSocket)
    {
        this.socket = clientSocket;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main Thread">
    @Override
    public void run() {
        
        InputStream inp;
        DataInputStream in;
        DataOutputStream out;
        ObjectInputStream obj;
        
        //<editor-fold defaultstate="collapsed" desc="User Verification">
        try
        {
            inp = socket.getInputStream();
            in = new DataInputStream(inp);
            out = new DataOutputStream(socket.getOutputStream());
            int z=-1;//on success password will replace user name by user id
            
            while(z<=0)
            {
                user=in.readUTF();
                z=password(user.split(","));
                
                if(z==1)
                {
                    out.writeUTF("ACK");
                    user=user.split(",")[0];
                    System.out.println(user+" connected");
                }
                else
                {
                    out.writeUTF("NACK");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("In Socket Thread: "+e.getMessage());
            return;
        }
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Main Program Loop">
        String[] details;
        String[] checkInData=new String[4];
        while (true)
        {
            
            try
            {
                details=in.readUTF().split(",");
                
                //<editor-fold defaultstate="collapsed" desc="Decode Data">
                if(details[0].contentEquals("Logout"))
                {
                    out.writeUTF("ACK");
                    Users.UserStatus(user.split(",")[0], "0");
                    return;
                }
                else if(details[0].contentEquals("send details"))
                {
                    out.writeUTF(VenueDetails(details[1]));
                }
                else if(verified(details))
                {
                    System.out.println("Successful Checkin For "+user+" at "+details[0]);
                    out.writeUTF("ACK");
                    
                    checkInData[0]=user;
                    checkInData[1]=details[0];
                    checkInData[2]=details[1];
                    checkInData[3]=details[2];
                    
                    CheckIn.makeCheckin(checkInData);
                }
                else
                {
                    System.out.println("Resource Request failed for "+user);
                    out.writeUTF("NACK");
                }
                //</editor-fold>
                
                Middle_Server.sleep(500);
            }
            catch (IOException | InterruptedException e)
            {
                System.out.println(user.split(",")[0]+ " Disconnected");
                Users.UserStatus(user.split(",")[0],"0");
                return;
            }
        }
        //</editor-fold>
        
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Retrieve Venue Details">
    
    private String VenueDetails(String Name)
    {
        String q="Select userid,count(*) as checkIns from checkIn "
                + "where placeid='"+Name+"' "
                + "group by userid  "
                + "order by checkIns desc "
                + "LIMIT 3";
        DatabaseType db =new DatabaseType();
        //db.openConnection();
        ResultSet rs;
        String Details="";
        
        try
        {
            db.openConnection();
            rs=db.read(q);
            
            while(rs.next())
            {
                //System.out.println(rs.getString("userid"));
                //System.out.println(rs.getString("checkIns"));
                Details= Details + rs.getString(1)+ ","+ rs.getString(2)+";";
            }
            System.out.println(Details);
            db.closeConnection();
        }
        catch (Exception ex)
        {
            Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Details;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GPS Distance Code">
    
    private boolean GPS_ok(String id, String ulat, String ulong)
    {
        double dist;

        final double threshold=1;
        String[] latLong=Location.getLatLong(id);
        System.out.println(id+": "+ "user(lat,long)= ("+ ulat+" "+ ulong+") : place(lat,long)= ("+latLong[0]+" "+latLong[1]+")");
        dist= calcDist(latLong,ulat,ulong);
        System.out.printf(user+ " distance from location: %.2f",dist);
        
        return (dist<threshold);
    }
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Speed Calculation Code">
    public static boolean Speed_ok(String uid,String ulat, String ulong, String u)
    {
        boolean flag=false;
        if(CheckIn.getAllCount(uid)==0)
            return true;
        
        String[] latLong=CheckIn.getLastCheckIn(uid);
        double distance = calcDist(latLong, ulat, ulong);
        double time=Double.parseDouble(latLong[2]);
        double thresholdSmall=4; //4km/min
        double thresholdBig=25; // 25km /min
        
        System.out.println("Last CheckIn: "+latLong[0]+", "+latLong[1]);
        if(distance < 100)
        {
            flag = ((distance/time)  < thresholdSmall);
            System.out.println("Small Distance ");
            

        }
        else if(distance >= 100)
        {
            System.out.println("Large Distance ");
            flag = ((distance/time)  < thresholdBig);
        }
        System.out.println(u+"'s Speed Since Last CheckIn: "+distance/time);
        
        return flag;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Max CheckIn Count Calculation Code">
    private boolean Count_ok()
    {
        boolean flag=true;
        int thresholdMin=5;
        int thresholdMed=8;
        int thresholdMax=49;
        
        int CountMin=CheckIn.getCount(user,1);
        int CountMed=CheckIn.getCount(user,15);
        int CountMax=CheckIn.getCount(user,24*60);
        
        if(CountMin>thresholdMin)
        {
            System.out.println("Minute Checkin Count Exceeded");
            flag=false;
        }
        if (CountMed > thresholdMed)
        {
            System.out.println("Quarterly Checkin Count Exceeded");
            flag=false;
        }
        if (CountMax > thresholdMax)
        {
            System.out.println("Daily Checkin Count Exceeded");
            flag=false;
        }
        
        
        return flag;
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Verification Function Controller">
    private boolean verified(String[] UserInput)
    {
        System.out.println("Attempting Checkin For "+user);
        boolean GPS=GPS_ok(UserInput[0],UserInput[1],UserInput[2]);
        boolean Speed=Speed_ok(user,UserInput[1],UserInput[2],user);
        boolean Count=Count_ok();
        
        if(!GPS)
        {
            System.out.println("GPS Validation Error");
        }
        if(!Speed)
            System.out.println("Speed Validation Error");
        if (!Count)
            System.out.println("CheckIn Count Error");
        
        return   (GPS && Speed && Count);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Password Check">
    
    private int password(String[] details)
    {
        int x;
        
        if(details[1].length()<=2)//if it was meant to be a password
        {
            x=0;
        }
        else if(Users.verifyUser(details[0], details[1]))//if it was a password and was verified
        {
            x=1;
        }
        else //if password was not verified
        {
            x=2;
        }
        return x;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Coordiante distance, Horne's Formula">
    
    private static double calcDist(String[] latLong, String ulat, String ulong) {
        final double R = 6371; // Radius of the earth
        double distance;
        
        Double lat1 = Double.parseDouble(ulat);
        Double lon1 = Double.parseDouble(ulong);
        Double lat2 = Double.parseDouble(latLong[0]);
        Double lon2 = Double.parseDouble(latLong[1]);
        
        Double latDelta = toRad(lat2-lat1);
        Double lonDelta = toRad(lon2-lon1);
        
        Double a= Math.sin(latDelta / 2) * Math.sin(latDelta / 2);
        Double b=Math.sin(lonDelta / 2) * Math.sin(lonDelta / 2);
        b= b * Math.cos(toRad(lat1)) * Math.cos(toRad(lat2));
        
        a+=b;
        
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        distance = R * c;
        return distance;
    }
}
//</editor-fold>

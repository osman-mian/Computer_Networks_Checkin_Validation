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
import java.util.Calendar;
import java.util.Date;
import javax.swing.text.DateFormatter;

/**
 *
 * @author osman
 */
public class SocketListener extends Thread {
    protected Socket socket;

    String user;
    
    public SocketListener(Socket clientSocket) {
        this.socket = clientSocket;

        //System.out.println("A client connected");
    }

    @Override
    public void run() {
        
        InputStream inp;
        DataInputStream in;
        DataOutputStream out;
        ObjectInputStream obj;

        try 
        {
            inp = socket.getInputStream();
            in = new DataInputStream(inp);
//            obj=new ObjectInputStream(inp);
            out = new DataOutputStream(socket.getOutputStream());
            user=in.readUTF();
            int z=password(user.split(","));//on success password will replace user name by user id
                if(z>0)
                {
                    if(z==1)
                    {
                        out.writeUTF("ACK");
                        user=user.split(",")[0];
                        System.out.println(user+" connected");
                        
                    }
                    else
                    {
                        out.writeUTF("NACK");
                        return;
                    }
                }
                
            
            
        } 
        catch (Exception e) 
        {
            System.out.println("In Socket Thread: "+e.getMessage());
            return;
            //e.printStackTrace();
            
        }
        
        String[] details;
        String[] checkInData=new String[4];
        while (true) 
        {
            
            try 
            {
                details=in.readUTF().split(",");
                
                if(details[0].contentEquals("Logout"))
                {
                    out.writeUTF("ACK");
                    Users.UserStatus(user.split(",")[0], "0");
                    return;
                }

                if(verified(details))
                {
                    System.out.println(details[0]);
                    out.writeUTF("ACK");
                    
                    checkInData[0]=user;
                    checkInData[1]=details[0];
                    checkInData[2]=details[1];
                    checkInData[3]=details[2];
                    
                    CheckIn.makeCheckin(checkInData);
                    
                }
                else
                {
                    out.writeUTF("NACK");
                }
                SocketListener.sleep(500);
            } 
            catch (IOException | InterruptedException e) 
            {
                System.out.println(user.split(",")[0]+ " Disconnected");
                Users.UserStatus(user.split(",")[0],"0");
                return;
            }
        }
        
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="GPS Distance Code">
    
    private boolean GPS_ok(String id, String ulat, String ulong)
    {
        double dist;

        final double threshold=25.0;
        String[] latLong=Location.getLatLong(id);
        System.out.println(id+" "+ " "+ ulat+" "+ ulong+" : place: "+latLong[0]+" "+latLong[1]);
        dist= calcDist(latLong,ulat,ulong);
        
        return (dist<threshold);
    }
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Speed Calculation Code">
    public static boolean Speed_ok(String uid,String ulat, String ulong)
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
              System.out.println("SMALL: "+distance+". Time: "+time+". Speed: "+distance/time);

        }
        else if(distance >= 100)
        {
            System.out.println("BIG: "+distance+". Time: "+time+". Speed: "+distance/time);

            flag = ((distance/time)  < thresholdBig);
        }
        
        
        return flag;
    }
    //</editor-fold>
    
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
            System.out.println("Min Exceeded");
            flag=false;
        }
        if (CountMed > thresholdMed)
        {
            System.out.println("Med Exceeded");
            flag=false;
        }
        if (CountMax > thresholdMax)
        {
            System.out.println("Max Exceeded");
            flag=false;
        }
            
        
        return flag;
        
    }
    
    private boolean verified(String[] UserInput) 
    {
        boolean GPS=GPS_ok(UserInput[0],UserInput[1],UserInput[2]);
        boolean Speed=Speed_ok(user,UserInput[1],UserInput[2]);
        boolean Count=Count_ok();
        
        if(!GPS)
            System.out.println("GPS Fail");
        if(!Speed)
            System.out.println("Speed Fail");
        if (!Count)
            System.out.println("Count Exceeded");
        
        return   (GPS && Speed && Count);
    }

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

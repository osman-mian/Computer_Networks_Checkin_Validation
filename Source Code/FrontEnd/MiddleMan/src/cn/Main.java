/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author osman
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
   static final int PORT = 10001;
   
  public Main()
    {
        
        
        //String x[]=Location.getLocation("1");
        //for(int i=0; i<6;i++) System.out.println(x[i]);
        
        
    }
    public void myFunct()
    {
        
        

        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
                new Middle_Server(socket).start();
            } catch (Exception e) {
                System.out.println("Error in Thread Pool: " + e);
            }
            // new thread for a client
            
        }
    }
    
    public static void main(String[] args) {
        Main m=new Main();
        m.myFunct();
       // Middle_Server.Speed_ok("1","67.46","54.79");
        
        //Code to test GPS
       /* Scanner x=new Scanner(System.in);
        String j=x.next();
        String k=x.next();
        
        while(!j.contentEquals("LOL"))
        {
            if(Middle_Server.GPS_ok("1", j,k))
                System.out.println("ACK");
            else
                System.out.println("NACK");
            
            j=x.next();
            k=x.next();
        
        }
        */
        
    }
}

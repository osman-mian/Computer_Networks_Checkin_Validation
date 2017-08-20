/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn;

import java.io.File;
import java.io.FileNotFoundException;
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
    
   public void addToDb() throws FileNotFoundException
   {
       String query="";
        Scanner cin=new Scanner(new File("areas.txt"));
        String [] details;
        DatabaseType db= new DatabaseType();
        db.openConnection();
        do
        {
            details=cin.nextLine().split(" ");
            query="INSERT into place values('"
                    +details[0]+"', '"
                    +details[1].replace("_"," ")+"', '"
                    +details[2]+"', '"
                    +details[3]+"')";
            db.insert(query);

        }while(cin.hasNext());
        
        db.closeConnection();
        
   }
    public static void main(String[] args) 
    {
        
       try {
           for(int i=1; i<=3; i++)
                new Frm_Venue(""+i).setVisible(true);
           //String x[]=Location.getLocation("1");
           //for(int i=0; i<6;i++) System.out.println(x[i]);
       } catch (Exception ex) {
           Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
       } 
    }
}

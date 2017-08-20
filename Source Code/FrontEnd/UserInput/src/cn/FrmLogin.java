/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author osman
 */

public class FrmLogin extends javax.swing.JFrame {

    static int count=1;
      String serverName = "localhost";
      int port = Integer.parseInt("10001");
      Socket client ;
    
    public FrmLogin() 
    {
        
        initComponents();
        try
        {
            client = new Socket(serverName, port);
            setBounds(0,0,370,180);//, port, port, port);
                    
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmdCancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cmdOK = new javax.swing.JButton();
        txtPass = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel2.setText("User");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 40, 70, 20);
        getContentPane().add(txtUserName);
        txtUserName.setBounds(90, 40, 240, 20);

        jLabel1.setText("Password");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 70, 70, 20);

        cmdCancel.setText("Cancel");
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });
        getContentPane().add(cmdCancel);
        cmdCancel.setBounds(170, 100, 160, 23);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("LOGIN");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 10, 320, 20);

        cmdOK.setText("OK");
        cmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOKActionPerformed(evt);
            }
        });
        getContentPane().add(cmdOK);
        cmdOK.setBounds(10, 100, 160, 23);
        getContentPane().add(txtPass);
        txtPass.setBounds(90, 70, 240, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        System.exit(0);
    }//GEN-LAST:event_cmdCancelActionPerformed

    private void cmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOKActionPerformed
        
        try
        {
         
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out =new DataOutputStream(outToServer);
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         
         out.writeUTF(txtUserName.getText()+","+txtPass.getText());
         
         
         String resp=in.readUTF();
         
         if(resp.contentEquals("ACK"))
         {
             this.setVisible(false);
             new FrmUser(txtUserName.getText(), client).setVisible(true);
             this.dispose();
         }
         else
                System.out.println(resp);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"ERROR: "+e.getMessage());
        }
    }//GEN-LAST:event_cmdOKActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
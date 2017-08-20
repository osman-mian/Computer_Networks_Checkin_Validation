/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CN;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osman
 */
public class myTableModel extends DefaultTableModel{
    
    DefaultTableModel a=new DefaultTableModel();
    
    @Override
    public boolean isCellEditable(int row,int col)
    {
        return false;
    }
    
}

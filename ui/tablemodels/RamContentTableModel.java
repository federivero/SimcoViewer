/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.ui.tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import simcoviewer.datatypes.MemoryLine;

/**
 *
 * @author fede
 */
public class RamContentTableModel extends AbstractTableModel{
    
    List<MemoryLine> lines;
    
    public RamContentTableModel(){
        
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                // Address
                return "Address";
            case 1:
                // Line content
                return "Line Content";
            default:
                return null;
        }
    }
    
    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MemoryLine line = lines.get(rowIndex);
        switch(columnIndex){
            case 0:
                return line.getAddress();
            case 1:
                return line.getLineContent();
            default:
                return null;
        }
    }
    
    
    
}

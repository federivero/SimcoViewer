/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.ui.tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import simcoviewer.datatypes.processor.Register;
import simcoviewer.datatypes.processor.RegisterType;

/**
 *
 * @author fede
 */
public class RegisterFileViewerTableModel extends AbstractTableModel{
    
    private List<Register> registerList;
    private long cycle;
    
    public RegisterFileViewerTableModel(List<Register> list, long cycle){
        registerList = list;
        this.cycle = cycle;
    }
    
    public void setCycle(long cycle){
        this.cycle = cycle;
    }
    
    @Override
    public int getRowCount() {
        return registerList.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Register register = registerList.get(rowIndex);
        switch(columnIndex){
            case 0:
                return register.getRegisterNumber();
            case 1:
                return register.getRegType();
            case 2:
                return register.getRegisterValue(cycle);
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0:
                return Long.class;
            case 1:
                return RegisterType.class;
            case 2:
                return String.class;
            default:
                return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public String getColumnName(int i){
        switch(i){
            case 0:
                return "Reg. Number";
            case 1:
                return "Reg. Type";
            case 2:
                return "Value";
            case 3:
                return "Data";
            case 4:
                
            default:
                return null;
        }
    }

}

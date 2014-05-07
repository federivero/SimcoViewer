/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.ui.tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import simcoviewer.datatypes.MemoryDevice;

/**
 *
 * @author fede
 */
public class MemoryHierarchyTableModel extends AbstractTableModel{

    List<MemoryDevice> memoryDeviceList;
    
    public MemoryHierarchyTableModel(List<MemoryDevice> list){
        memoryDeviceList = list;
    }
    
    public MemoryDevice getMemoryDevice(int rowIndex){
        return memoryDeviceList.get(rowIndex);
    }
    
    @Override
    public int getRowCount() {
        return memoryDeviceList.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MemoryDevice device = memoryDeviceList.get(rowIndex);
        switch(columnIndex){
            case 0:
                return device.getName();
            case 1:
                return device.getType();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int i){
        switch(i){
            case 0:
                return "Name";
            case 1:
                return "Type";
            default:
                return null;
        }
    }
    
}

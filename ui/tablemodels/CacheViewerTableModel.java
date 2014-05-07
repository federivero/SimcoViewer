/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.ui.tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import simcoviewer.datatypes.CacheLineEntry;
import simcoviewer.datatypes.CacheLineState;

/**
 *
 * @author fede
 */
public class CacheViewerTableModel extends AbstractTableModel{
    
    private List<CacheLineEntry> cacheLineList;
    
    public CacheViewerTableModel(List<CacheLineEntry> list){
        cacheLineList = list;
    }
    
    @Override
    public int getRowCount() {
        return cacheLineList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CacheLineEntry line = cacheLineList.get(rowIndex);
        switch(columnIndex){
            case 0:
                return line.getCacheLineNumber();
            case 1:
                return line.getTag();
            case 2:
                return line.getState();
            case 3:
                return line.getData();
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
                return Long.class;
            case 2:
                return CacheLineState.class;
            case 3:
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
                return "Line";
            case 1:
                return "Tag";
            case 2:
                return "State";
            case 3:
                return "Data";
            case 4:
                
            default:
                return null;
        }
    }

    /**
     * @param cacheLineList the cacheLineList to set
     */
    public void setCacheLineList(List<CacheLineEntry> cacheLineList) {
        this.cacheLineList = cacheLineList;
    }
}

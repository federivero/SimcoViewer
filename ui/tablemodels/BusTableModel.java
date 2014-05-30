/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.ui.tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import simcoviewer.datatypes.Bus;

/**
 *
 * @author fede
 */
public class BusTableModel extends AbstractTableModel {

    private List<Bus> buses;
    
    public BusTableModel(List<Bus> buses){
        this.buses = buses;
    }
  
    public Bus getBus(int row){
        return buses.get(row);
    }
    
    public void setBuses(List<Bus> buses){
        this.buses = buses;
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                return "Name";
            case 1:
                return "Width";
            default:
                return null;
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch(columnIndex){
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            default:
                return null;
        }
    }
            
    @Override
    public int getRowCount() {
        return buses.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bus bus = buses.get(rowIndex);
        switch(columnIndex){
            case 0:
                return bus.getName();
            case 1:
                return bus.getDataWidth();
            default:
                return null;
        }
    }
    
}

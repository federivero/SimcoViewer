/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.ui.tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import simcoviewer.datatypes.processor.Processor;

/**
 *
 * @author fede
 */
public class ProcessorTableModel extends AbstractTableModel {
    
    private List<Processor> processors;
    
    public ProcessorTableModel(List<Processor> processors){
        this.processors = processors;
    }

    public Processor getProcessor(int row){
        return processors.get(row);
    } 
    
    @Override
    public int getRowCount() {
        return processors.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                return "Processor Name";
            case 1:
                return "Processor Type";
            default:
                return "error";
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Processor processor = processors.get(rowIndex);
        Object returnValue = null;
        switch(columnIndex){
            case 0:
                returnValue = processor.getProcessorName();
                break;
            case 1:
                returnValue = processor.getProcessorType();
                break;
        }
        return returnValue;
    }
    
    
    
    
}

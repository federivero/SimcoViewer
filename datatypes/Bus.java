/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes;

import java.util.Map;

/**
 *
 * @author fede
 */
public class Bus {
    
    private int dataWidth;
    
    private Map<Long, BusStatus> busDataPerCycle;
    
    public Bus(){
        
    }

    public void addBusStatus(long cycle, BusStatus status){
        
    }
    /**
     * @return the dataWidth
     */
    public int getDataWidth() {
        return dataWidth;
    }

    /**
     * @param dataWidth the dataWidth to set
     */
    public void setDataWidth(int dataWidth) {
        this.dataWidth = dataWidth;
    }

    /**
     * @return the busDataPerCycle
     */
    public Map<Long, BusStatus> getBusDataPerCycle() {
        return busDataPerCycle;
    }

    /**
     * @param busDataPerCycle the busDataPerCycle to set
     */
    public void setBusDataPerCycle(Map<Long, BusStatus> busDataPerCycle) {
        this.busDataPerCycle = busDataPerCycle;
    }
    
    
    
    
}

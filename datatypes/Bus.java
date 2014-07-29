/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes;

import simcoviewer.datatypes.enumerates.MessageType;
import java.util.HashMap;
import java.util.Map;
import simcoviewer.datatypes.processor.Register;

/**
 *
 * @author fede
 */
public class Bus {
    
    private Long id;
    private int dataWidth;
    private String name;
    
    private Map<Long, BusStatus> busDataPerCycle;
    
    public Bus(){
        busDataPerCycle = new HashMap();
    }

    public BusStatus getBusStatus(long cycle){
        return busDataPerCycle.get(cycle);
    }
    
    public void updateBusStatus(long cycle, int messageType, long address, String data, String submitterName){
        BusStatus currentUpdate;
        /* if there's a current entry for this cycle, get it and update it. Otherwise create new */
        if (busDataPerCycle.containsKey(cycle)){
            currentUpdate = busDataPerCycle.get(cycle);
        }else{
            currentUpdate = new BusStatus();
            busDataPerCycle.put(cycle, currentUpdate);
        }
        if (messageType != -1){
            MessageType mType = Message.parseMessageType(messageType);
            currentUpdate.setMessageType(mType);
        }
        if (address != -1){
            currentUpdate.setAddress(address);
        }
        if (data != null){
            currentUpdate.setData(data);
        }
        if (submitterName != null){
            currentUpdate.setOwner(submitterName);
        }
    }

    public void addBusStatusIfEmpty(long cycle){
        if (!busDataPerCycle.containsKey(cycle)){
            BusStatus lastCycleState = new BusStatus();
            busDataPerCycle.put(cycle, lastCycleState);
        }
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    
}

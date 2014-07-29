/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fede
 */
public class Processor {
   
    private Map<Long,ProcessorState> processorStatePerCycle;
    private List<Register> architectedRegisterFile;
    private Map<Integer,Register> architectedRegisterFileMap;
    
    private String processorType;
    private String processorName;
    private long id;
    
    public Processor(){
        processorStatePerCycle = new HashMap();
        architectedRegisterFile = new ArrayList();
        architectedRegisterFileMap = new HashMap();
    }
    
    public List<Register> getArchitectedRegisterFile(){
        return architectedRegisterFile;
    }

    public void addRegisterValueEntry(long cycle, int registerNumber, int registerType, String registerValue){
        Register r = architectedRegisterFileMap.get(registerNumber * 16 + registerType);
        r.addCycleValue(cycle,registerValue);
    }
    
    public void addRegister(Register reg){
        architectedRegisterFile.add(reg);
        // Assuming 16 types of registers
        architectedRegisterFileMap.put(reg.getRegisterNumber() * 16 + reg.getRegTypeAsInt(), reg);
        // TODO: collections.sort?
    }
    
    public ProcessorState getCycleProcessorState(long cycle){
        return processorStatePerCycle.get(cycle);
    }
    
    public void copyProcessorStatusFromLastCycle(long cycle){
        // First check if there's something to merge
        if (processorStatePerCycle.containsKey(cycle - 1)){
            ProcessorState lastCycleState = processorStatePerCycle.get(cycle - 1);
            if (!processorStatePerCycle.containsKey(cycle)){
                // No entry for current cycle but there is one for last one, copy state!
                processorStatePerCycle.put(cycle, lastCycleState);
            }else{
                ProcessorState currentState = processorStatePerCycle.get(cycle);
                currentState.mergeState(lastCycleState);
            }
            
        }
        Iterator<Register> registerIterator = architectedRegisterFile.iterator();
        while(registerIterator.hasNext()){
            registerIterator.next().copyRegisterStateFromLastCycle(cycle);
        }
        
    }
    
    public void addCyclePCValue(long cycle, long pcValue){
        ProcessorState state;
        if (processorStatePerCycle.containsKey(cycle)){
            state = processorStatePerCycle.get(cycle);
            state.setPcValue(pcValue);
        }else{
            state = new ProcessorState();
            state.setPcValue(pcValue);
            processorStatePerCycle.put(cycle, state);
        }
    }
    
    public void addCycleIRValue(long cycle, String instructionRegister){
        ProcessorState state;
        if (processorStatePerCycle.containsKey(cycle)){
            state = processorStatePerCycle.get(cycle);
            state.setIrValue(instructionRegister);
        }else{
            state = new ProcessorState();
            state.setIrValue(instructionRegister);
            processorStatePerCycle.put(cycle, state);
        }
    }
    
    public void addCycleFlagsValue(long cycle, int zflag, int nflag, int cflag, int vflag){
        ProcessorState state;
        if (processorStatePerCycle.containsKey(cycle)){
            state = processorStatePerCycle.get(cycle);
        }else{
            state = new ProcessorState();
            processorStatePerCycle.put(cycle, state);
        }    
        state.setzFlag(zflag);
        state.setnFlag(nflag);
        state.setcFlag(cflag);
        state.setvFlag(vflag);
    }
    
    /**
     * @return the processorType
     */
    public String getProcessorType() {
        return processorType;
    }

    /**
     * @param processorType the processorType to set
     */
    public void setProcessorType(String processorType) {
        this.processorType = processorType;
    }

    /**
     * @return the processorName
     */
    public String getProcessorName() {
        return processorName;
    }

    /**
     * @param processorName the processorName to set
     */
    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    
}

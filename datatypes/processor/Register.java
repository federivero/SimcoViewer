/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes.processor;

import java.util.HashMap;
import java.util.Map;
import static simcoviewer.datatypes.processor.RegisterType.REGISTER_TYPE_FP;
import static simcoviewer.datatypes.processor.RegisterType.REGISTER_TYPE_INT;

/**
 *
 * @author fede
 */
public class Register {
    
    private RegisterType regType;
    private int registerNumber;
    private Map<Long, String> registerValuePerCycle;
    
    public Register(){
        this.registerValuePerCycle = new HashMap();
    }
    
    public String getRegisterValue(Long cycle){
        return registerValuePerCycle.get(cycle);
    }
    
    public void addCycleValue(Long cycle, String registerValue){
        if (registerValuePerCycle.containsKey(cycle)){
            registerValuePerCycle.remove(cycle);
        }
        registerValuePerCycle.put(cycle, registerValue);
    }
    
    public void copyRegisterStateFromLastCycle(long cycle){
        // First check if there's something to copy
        if (registerValuePerCycle.containsKey(cycle - 1)){
            String lastCycleState = registerValuePerCycle.get(cycle - 1);
            if (!registerValuePerCycle.containsKey(cycle)){
                // No entry for current cycle but there is one for last one, copy state!
                registerValuePerCycle.put(cycle, lastCycleState);
            }
        }
    }
    
    /**
     * @return the regType
     */
    public RegisterType getRegType() {
        return regType;
    }

    public int getRegTypeAsInt(){
        switch(regType){
            case REGISTER_TYPE_INT:
                return 0;
            case REGISTER_TYPE_FP:
                return 1;
        }
        return -1;
    }
    
    public void setRegTypeAsInt(int regType){
        switch(regType){
            case 0:
                this.regType = REGISTER_TYPE_INT;
                break;
            case 1:
                this.regType = REGISTER_TYPE_FP;
        }
    }
    
    /**
     * @param regType the regType to set
     */
    public void setRegType(RegisterType regType) {
        this.regType = regType;
    }

    /**
     * @return the registerNumber
     */
    public int getRegisterNumber() {
        return registerNumber;
    }

    /**
     * @param registerNumber the registerNumber to set
     */
    public void setRegisterNumber(int registerNumber) {
        this.registerNumber = registerNumber;
    }

    /**
     * @return the registerValuePerCycle
     */
    public Map<Long, String> getRegisterValuePerCycle() {
        return registerValuePerCycle;
    }

    /**
     * @param registerValuePerCycle the registerValuePerCycle to set
     */
    public void setRegisterValuePerCycle(Map<Long, String> registerValuePerCycle) {
        this.registerValuePerCycle = registerValuePerCycle;
    }
        
}

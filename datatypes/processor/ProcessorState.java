/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes.processor;

import simcoviewer.datatypes.enumerates.ProcessorStage;

/**
 *
 * @author fede
 */
public class ProcessorState {
    
    // Value of PC
    private long pcValue;
    
    // Value of instruction register
    private String irValue;
    
    private int zFlag;
    private int nFlag;
    private int cFlag;
    private int vFlag;
  
    // Current Stage
    private ProcessorStage currentStage;
    
    public ProcessorState(){
        irValue = null;
        pcValue = -1;
        zFlag = -1;
        nFlag = -1;
        cFlag = -1;
        vFlag = -1;
        currentStage = ProcessorStage.UNINITIALIZED;
    }

    public void mergeState(ProcessorState otherState){
        if  (getIrValue() == null){
            setIrValue(otherState.getIrValue());
        }
        if (getPcValue() == -1){
            setPcValue(otherState.getPcValue());
        }
        if (getzFlag() == -1){
            setzFlag(otherState.getzFlag());
        }
        if (getnFlag() == -1){
            setnFlag(otherState.getnFlag());
        }
        if (getcFlag() == -1){
            setcFlag(otherState.getcFlag());
        }
        if (getvFlag() == -1){
            setvFlag(otherState.getvFlag());
        }
        if (getCurrentStage() == ProcessorStage.UNINITIALIZED){
            setCurrentStage(otherState.getCurrentStage());
        }
    }
    
    /**
     * @return the pcValue
     */
    public long getPcValue() {
        return pcValue;
    }

    /**
     * @param pcValue the pcValue to set
     */
    public void setPcValue(long pcValue) {
        this.pcValue = pcValue;
    }

    /**
     * @return the irValue
     */
    public String getIrValue() {
        return irValue;
    }

    /**
     * @param irValue the irValue to set
     */
    public void setIrValue(String irValue) {
        this.irValue = irValue;
    }

    /**
     * @return the zFlag
     */
    public int getzFlag() {
        return zFlag;
    }

    /**
     * @param zFlag the zFlag to set
     */
    public void setzFlag(int zFlag) {
        this.zFlag = zFlag;
    }

    /**
     * @return the nFlag
     */
    public int getnFlag() {
        return nFlag;
    }

    /**
     * @param nFlag the nFlag to set
     */
    public void setnFlag(int nFlag) {
        this.nFlag = nFlag;
    }

    /**
     * @return the cFlag
     */
    public int getcFlag() {
        return cFlag;
    }

    /**
     * @param cFlag the cFlag to set
     */
    public void setcFlag(int cFlag) {
        this.cFlag = cFlag;
    }

    /**
     * @return the vFlag
     */
    public int getvFlag() {
        return vFlag;
    }

    /**
     * @param vFlag the vFlag to set
     */
    public void setvFlag(int vFlag) {
        this.vFlag = vFlag;
    }

    /**
     * @return the currentStage
     */
    public ProcessorStage getCurrentStage() {
        return currentStage;
    }

    /**
     * @param currentStage the currentStage to set
     */
    public void setCurrentStage(ProcessorStage currentStage) {
        this.currentStage = currentStage;
    }
    
    public void setCurrentStage(int step){
        switch(step){
            case 0:
                currentStage = ProcessorStage.FETCH;
                break;
            case 1:
                currentStage = ProcessorStage.DECODE;
                break;
            case 2:
                currentStage = ProcessorStage.EXECUTE;
                break;
            case 3:
                currentStage = ProcessorStage.WRITEBACK;
                break;
            case 4:
                currentStage = ProcessorStage.IDLE;
                break;
            default:
                break;
        }
    }
    
}

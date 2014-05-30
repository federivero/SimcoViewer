/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes.processor;

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
    
    public ProcessorState(){
        irValue = null;
        pcValue = -1;
        zFlag = -1;
        nFlag = -1;
        cFlag = -1;
        vFlag = -1;
    }

    public void mergeState(ProcessorState otherState){
        if  (irValue == null){
            irValue = otherState.getIrValue();
        }
        if (pcValue == -1){
            pcValue = otherState.getPcValue();
        }
        if (zFlag == -1){
            zFlag = otherState.getzFlag();
        }
        if (nFlag == -1){
            nFlag = otherState.getnFlag();
        }
        if (cFlag == -1){
            cFlag = otherState.getcFlag();
        }
        if (vFlag == -1){
            vFlag = otherState.getvFlag();
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
    
}

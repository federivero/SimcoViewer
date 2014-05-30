/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes;

/**
 *
 * @author fede
 */
public class MemoryLine {
    
    private long address;
    
    private String lineContent;
    
    public MemoryLine(){
        
    }

    /**
     * @return the address
     */
    public long getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(long address) {
        this.address = address;
    }

    /**
     * @return the lineContent
     */
    public String getLineContent() {
        return lineContent;
    }

    /**
     * @param lineContent the lineContent to set
     */
    public void setLineContent(String lineContent) {
        this.lineContent = lineContent;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes;

/**
 *
 * @author fede
 */
public class BusStatus {
    
    private MessageDispatcher owner;
    private String data;
    private long address;
    private MessageType messageType;
    
    public BusStatus(){
        
    }

    /**
     * @return the owner
     */
    public MessageDispatcher getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(MessageDispatcher owner) {
        this.owner = owner;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
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
     * @return the messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
    
    
}

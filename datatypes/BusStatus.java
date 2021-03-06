/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes;

import simcoviewer.datatypes.enumerates.MessageType;

/**
 *
 * @author fede
 */
public class BusStatus {
    
    private String owner;
    private String data;
    private long address;
    private MessageType messageType;
    
    public BusStatus(){
        data = null;
        address = -1;
        messageType = MessageType.NO_MESSAGE;
        owner = null;
    }
    
    void mergeState(BusStatus statusToMerge){
        if (data == null){
            this.data = statusToMerge.getData();
        }
        if (address == -1){
            this.address = statusToMerge.getAddress();
        }
        if (owner == null){
            this.owner = statusToMerge.getOwner();
        }
        if (messageType == MessageType.NO_MESSAGE){
            messageType = statusToMerge.getMessageType();
        }
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
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

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
public class Message {
    
    public static MessageType parseMessageType(int messageType){
        return MessageType.values()[messageType];
    }
    
}

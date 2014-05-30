/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes;

/**
 *
 * @author fede
 */
public enum MessageType {
    MEMORY_REQUEST_MEMORY_READ, 
    MEMORY_REQUEST_MEMORY_WRITE,
    MEMORY_RESPONSE,
    INVALIDATING_MEMORY_RESPONSE,
    CACHE_COHERENCE_INVALIDATE
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes;

/**
 *
 * @author fede
 */
public class MemoryDevice extends MessageDispatcher{
    
    private String name;
    private long id;
    private MemoryDeviceType type;
    
    public MemoryDevice(MemoryDeviceType type){
        this.type = type;
    }

    /**
     * @return the type
     */
    public MemoryDeviceType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MemoryDeviceType type) {
        this.type = type;
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

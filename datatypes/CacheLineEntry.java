/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes;

import simcoviewer.datatypes.enumerates.CacheLineState;

/**
 *
 * @author fede
 */
public class CacheLineEntry implements Comparable<CacheLineEntry> {
     // Content of line
    private long cacheLineNumber;
    private String data;
    private long tag;
    private CacheLineState state;
    
    public CacheLineEntry(){
        
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
     * @return the tag
     */
    public long getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(long tag) {
        this.tag = tag;
    }

    /**
     * @return the state
     */
    public CacheLineState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(CacheLineState state) {
        this.state = state;
    }
    
    /* Sets the state from an int matching c++ convention for enumerates */
    public void setStateFromInt(int i){
        switch(i){
            case 0: // invalid
                this.state = CacheLineState.INVALID;
                break;
            case 1: // modified
                this.state = CacheLineState.MODIFIED;
                break;
            case 2: // exclusive
                this.state = CacheLineState.EXCLUSIVE;
                break;
            case 3: // shared
                this.state = CacheLineState.SHARED;
                break;
        }
    }

    /**
     * @return the cacheLineNumber
     */
    public long getCacheLineNumber() {
        return cacheLineNumber;
    }

    /**
     * @param cacheLineNumber the cacheLineNumber to set
     */
    public void setCacheLineNumber(long cacheLineNumber) {
        this.cacheLineNumber = cacheLineNumber;
    }

    @Override
    public int compareTo(CacheLineEntry o) {
        if (this.cacheLineNumber > o.getCacheLineNumber()){
            return 1;
        }else if (this.cacheLineNumber < o.getCacheLineNumber()){
            return -1;
        }else{
            return 0;
        }
    }
    
}

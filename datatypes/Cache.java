/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.datatypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fede
 */
public class Cache extends MemoryDevice{
    
    // Line size in bytes
    private int lineSize;    
    // Number of sets
    private int setCount;
    // Associativity of each set
    private int associativity;
    
    Map<Long, List<CacheLineEntry>> cacheStatusPerCycle;
    
    public Cache(){
        super(MemoryDeviceType.CACHE);
        cacheStatusPerCycle = new HashMap();
    }

    public void addCacheLineStatus(CacheLineEntry entry, long cycle){
        if (cacheStatusPerCycle.containsKey(cycle)){
            List<CacheLineEntry> entries = cacheStatusPerCycle.get(cycle);
            Iterator<CacheLineEntry> iter = entries.iterator();
            while(iter.hasNext()){
                if (iter.next().getCacheLineNumber() == entry.getCacheLineNumber()){
                    iter.remove();
                    entries.add(entry);
                    break;
                }
            }
        }else{
            List<CacheLineEntry> entries = new ArrayList();
            entries.add(entry);
            cacheStatusPerCycle.put(cycle, entries);
        }
    }
    
    public List<CacheLineEntry> getCacheLineList(long cycle){
        if (cacheStatusPerCycle.containsKey(cycle)){
            return cacheStatusPerCycle.get(cycle);
        }else{
            List<CacheLineEntry> newList = new ArrayList();
            cacheStatusPerCycle.put(cycle, newList);
            return newList;
        }
    }
    
    public void copyLineStatusFromLastCycle(long cycle){
        if (cacheStatusPerCycle.containsKey(cycle - 1)){
            List<CacheLineEntry> lastCycleStatus = cacheStatusPerCycle.get(cycle - 1);
            if (!cacheStatusPerCycle.containsKey(cycle)){
                // No state for this cycle, copy from last cycle!
                cacheStatusPerCycle.put(cycle, lastCycleStatus);
            }else{
                // Merge last cycle with current one
                List<CacheLineEntry> currentCycleStatus = cacheStatusPerCycle.get(cycle);
                Iterator<CacheLineEntry> lastCycleEntriesIterator = lastCycleStatus.iterator();
                while(lastCycleEntriesIterator.hasNext()){
                    // if line from last cycle was not modified, copy its state to current cycle
                    CacheLineEntry testEntry = lastCycleEntriesIterator.next();
                    Iterator<CacheLineEntry> currentCycleEntriesIterator = currentCycleStatus.iterator();
                    boolean found = false;
                    while(currentCycleEntriesIterator.hasNext()){
                        CacheLineEntry currentEntry = currentCycleEntriesIterator.next();
                        if (currentEntry.getCacheLineNumber() == testEntry.getCacheLineNumber()){
                            // Found new entry on line, not merge!
                            found = true;
                            break;
                        }
                    }
                    if (!found){
                        currentCycleStatus.add(testEntry);
                    }
                }
                    
            }
        }
        
    }
    
    public void sortCacheLineEntries(){
        // CacheLineEntries might not be in the correct order, so 
        Iterator<List<CacheLineEntry>> iter = cacheStatusPerCycle.values().iterator();
        while(iter.hasNext()){
            Collections.sort(iter.next());
        }
    }
   
    /**
     * @return the lineSize
     */
    public int getLineSize() {
        return lineSize;
    }

    /**
     * @param lineSize the lineSize to set
     */
    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

    /**
     * @return the setCount
     */
    public int getSetCount() {
        return setCount;
    }

    /**
     * @param setCount the setCount to set
     */
    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }

    /**
     * @return the associativity
     */
    public int getAssociativity() {
        return associativity;
    }

    /**
     * @param associativity the associativity to set
     */
    public void setAssociativity(int associativity) {
        this.associativity = associativity;
    }
    
    
}

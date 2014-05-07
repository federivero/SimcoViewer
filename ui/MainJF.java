/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.ui;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import simcoviewer.datatypes.Cache;
import simcoviewer.datatypes.CacheLineEntry;
import simcoviewer.datatypes.MemoryDevice;
import simcoviewer.ui.tablemodels.MemoryHierarchyTableModel;

/**
 *
 * @author fede
 */
public class MainJF extends javax.swing.JFrame {

    private static final String LINE_START_CACHE = "Cache-";
    private static final String LINE_START_NEW_CYCLE = "Cycle ";
    private static final String LINE_START_CACHE_LINE_STATUS = "CacheLine-";
    private static final String LINE_START_RAM = "ram";
    
    // Simulable Objects properties
    private static final String SIMULABLE_PROPERTY_NAME = "name";
    private static final String SIMULABLE_PROPERTY_ID = "id";
    
    // Cache Properties
    private static final String CACHE_PROPERTY_SET_COUNT = "setCount";
    private static final String CACHE_PROPERTY_LINE_SIZE = "lineSize";
    private static final String CACHE_PROPERTY_ASSOCIATIVITY = "associativity";
    
    // Cache Line Status Change Property
    private static final String CACHE_LINE_CHANGE_CACHE_ID = "cacheId";
    private static final String CACHE_LINE_CHANGE_CACHE_LINE_NUMBER = "lineNumber";
    private static final String CACHE_LINE_CHANGE_PROPERTY_TAG = "tag";
    private static final String CACHE_LINE_CHANGE_PROPERTY_STATE = "state";
    private static final String CACHE_LINE_CHANGE_PROPERTY_DATA = "data";
    
    // Label Constants
    private static final String LABEL_CURRENT_CYCLE = "Current Cycle: ";
    
    private List<CycleViewerJF> viewers;
    
    // MemoryDevices Containers
    private List<MemoryDevice> memoryDevices;
    private Map<Long,MemoryDevice> memoryDeviceMap; // Maps Id's to memory devices
    
    // Cycle view variables
    private long maxCycle;
    private long minCycle;
    private long currentCycle;
    
    /**
     * Creates new form JFPrincipal
     */
    public MainJF(String traceFileName) throws FileNotFoundException, IOException {
        
        this.setName("Simco Viewer");
        viewers = new ArrayList();
        memoryDevices = new ArrayList();
        memoryDeviceMap = new HashMap();
        
        initComponents();
        currentCycle = -1;
        maxCycle = -1;
        minCycle = -1;
        
        BufferedReader br = new BufferedReader(new FileReader(traceFileName));
        String line;
        while ((line = br.readLine()) != null) {
            String propertyName, propertyValue;
            if (line.startsWith(LINE_START_CACHE)){ // Cache defining Line
                Cache cache = new Cache();
                String[] props = line.split("-");
                for (int i = 1; i < props.length; i++){
                    propertyName = props[i].split(":")[0];
                    propertyValue = props[i].split(":")[1];
                    switch (propertyName) {
                        case SIMULABLE_PROPERTY_ID:
                            cache.setId(Long.parseLong(propertyValue));
                            break;
                        case SIMULABLE_PROPERTY_NAME:
                            cache.setName(propertyValue);
                            break;
                        case CACHE_PROPERTY_SET_COUNT:
                            cache.setSetCount(Integer.parseInt(propertyValue));
                            break;
                        case CACHE_PROPERTY_LINE_SIZE:
                            cache.setLineSize(Integer.parseInt(propertyValue));
                            break;
                        case CACHE_PROPERTY_ASSOCIATIVITY:
                            cache.setAssociativity(Integer.parseInt(propertyValue));
                            break;
                    }
                    
                }
                memoryDevices.add(cache);
                memoryDeviceMap.put(cache.getId(), cache);
                
                
            }else if (line.startsWith(LINE_START_CACHE_LINE_STATUS)){ // CacheLine status change
                
                String[] props = line.split("-");
                CacheLineEntry lineEntry = new CacheLineEntry();
                long cacheId = -1;
                for (int i = 1; i < props.length; i++){
                    propertyName = props[i].split(":")[0];
                    propertyValue = props[i].split(":")[1];
                    switch (propertyName) {
                        case CACHE_LINE_CHANGE_CACHE_ID:
                            cacheId = Long.parseLong(propertyValue);
                            break;
                        case CACHE_LINE_CHANGE_CACHE_LINE_NUMBER:
                            lineEntry.setCacheLineNumber(Long.parseLong(propertyValue));
                            break;
                        case CACHE_LINE_CHANGE_PROPERTY_TAG:
                            lineEntry.setTag(Long.parseLong(propertyValue));
                            break;
                        case CACHE_LINE_CHANGE_PROPERTY_STATE:
                            lineEntry.setStateFromInt(Integer.parseInt(propertyValue));
                            break;
                        case CACHE_LINE_CHANGE_PROPERTY_DATA:
                            lineEntry.setData(propertyValue);
                            break;
                    }
                }
                Cache c = (Cache) memoryDeviceMap.get(cacheId);
                c.addCacheLineStatus(lineEntry, currentCycle);
            
            }else if (line.startsWith(LINE_START_NEW_CYCLE)){ // CacheLine status change
                if (currentCycle == -1){            
                    maxCycle = Long.parseLong(line.split(" ")[1]);
                    minCycle = maxCycle;
                }else{
                    Iterator<MemoryDevice> it = memoryDevices.iterator();
                    while(it.hasNext()){
                        MemoryDevice mdev = it.next();
                        if (mdev instanceof Cache){
                            ((Cache) mdev).copyLineStatusFromLastCycle(currentCycle);
                        }
                    }
                }
                currentCycle = Long.parseLong(line.split(" ")[1]);
                if (currentCycle > maxCycle){
                    maxCycle = currentCycle;
                }
                if (currentCycle < minCycle){
                    minCycle = currentCycle;
                }
            }
        }
        
        Iterator<MemoryDevice> iter = memoryDevices.iterator();
        while (iter.hasNext()){
            MemoryDevice current = iter.next();
            if (current instanceof Cache){
                ((Cache) current).sortCacheLineEntries();
            }
        }
        currentCycle = minCycle;
        setCurrentCycle();
        
        Rectangle maxWindow = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        Double maxHeigth = maxWindow.getHeight();
        this.setBounds(0, 0, this.getWidth(), maxHeigth.intValue());
        this.setLocation(345,0);
        
        MemoryHierarchyTableModel mhtm = new MemoryHierarchyTableModel(memoryDevices);
        tblMemoryDevices.setModel(mhtm) ;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabPaneCompSystem = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMemoryDevices = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInterconnectionNetworks = new javax.swing.JTable();
        btnViewElement = new javax.swing.JButton();
        lblCurrentCycle = new javax.swing.JLabel();
        btnNextCycle = new javax.swing.JButton();
        btnPreviousCycle = new javax.swing.JButton();
        btnGoToCycle = new javax.swing.JButton();
        txtTargetCycle = new javax.swing.JTextField();
        lblTargetCycle = new javax.swing.JLabel();
        lblAdvanceCycle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblMemoryDevices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblMemoryDevices);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPaneCompSystem.addTab("MemoryDevices", jPanel1);

        tblInterconnectionNetworks.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jScrollPane1.setViewportView(tblInterconnectionNetworks);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPaneCompSystem.addTab("Interconnection Networks", jPanel2);

        btnViewElement.setText("View");
        btnViewElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewElementActionPerformed(evt);
            }
        });

        lblCurrentCycle.setText("Current Cycle: 0");

        btnNextCycle.setText(">");
        btnNextCycle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextCycleActionPerformed(evt);
            }
        });

        btnPreviousCycle.setText("<");
        btnPreviousCycle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousCycleActionPerformed(evt);
            }
        });

        btnGoToCycle.setText("Go");
        btnGoToCycle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoToCycleActionPerformed(evt);
            }
        });

        txtTargetCycle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTargetCycleActionPerformed(evt);
            }
        });

        lblTargetCycle.setText("Target Cycle:");

        lblAdvanceCycle.setText("Advance Cycle:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabPaneCompSystem)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnViewElement)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAdvanceCycle))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblTargetCycle)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTargetCycle)
                            .addComponent(btnPreviousCycle, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGoToCycle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNextCycle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addComponent(lblCurrentCycle, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnViewElement)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPreviousCycle)
                            .addComponent(btnNextCycle)
                            .addComponent(lblCurrentCycle)
                            .addComponent(lblAdvanceCycle))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGoToCycle)
                            .addComponent(txtTargetCycle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTargetCycle))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGap(11, 11, 11)
                .addComponent(tabPaneCompSystem, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewElementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewElementActionPerformed
        int row = tblMemoryDevices.getSelectedRow();
        if (row != -1){
            MemoryHierarchyTableModel mhtm = (MemoryHierarchyTableModel) tblMemoryDevices.getModel();
            MemoryDevice device = mhtm.getMemoryDevice(row);
            switch(device.getType()){
                case CACHE:
                    Cache cache = (Cache) device;
                    CacheViewerJF cacheViewer = new CacheViewerJF(cache,currentCycle);
                    viewers.add(cacheViewer);
                    cacheViewer.setVisible(true);
                    break;
                case RAM:
                    break;
                default:
                    break;
            }
        }
        
        
    }//GEN-LAST:event_btnViewElementActionPerformed

    private void btnNextCycleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextCycleActionPerformed
        // Changes the current cycle being shown
        if (currentCycle < maxCycle){
            currentCycle++;
            setCurrentCycle();
        }
    }//GEN-LAST:event_btnNextCycleActionPerformed

    private void txtTargetCycleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTargetCycleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTargetCycleActionPerformed

    private void btnPreviousCycleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousCycleActionPerformed
        // Changes the current cycle being shown
        if (currentCycle > minCycle){
            currentCycle--;
            setCurrentCycle();
        }
    }//GEN-LAST:event_btnPreviousCycleActionPerformed
   
    private void setCurrentCycle(){
        Iterator<CycleViewerJF> it = viewers.iterator();
        while(it.hasNext()){
            it.next().setCurrentCycle(currentCycle);
        }
        lblCurrentCycle.setText(LABEL_CURRENT_CYCLE + currentCycle);
    }
    
    private void btnGoToCycleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoToCycleActionPerformed
        // Changes the current cycle being shown
        try{
            long targetCycle = Long.parseLong(txtTargetCycle.getText());
            if (targetCycle >= minCycle && targetCycle <= maxCycle){
                currentCycle = targetCycle;
                setCurrentCycle();
            }
        }catch(NumberFormatException e){
            
        }
    }//GEN-LAST:event_btnGoToCycleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGoToCycle;
    private javax.swing.JButton btnNextCycle;
    private javax.swing.JButton btnPreviousCycle;
    private javax.swing.JButton btnViewElement;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAdvanceCycle;
    private javax.swing.JLabel lblCurrentCycle;
    private javax.swing.JLabel lblTargetCycle;
    private javax.swing.JTabbedPane tabPaneCompSystem;
    private javax.swing.JTable tblInterconnectionNetworks;
    private javax.swing.JTable tblMemoryDevices;
    private javax.swing.JTextField txtTargetCycle;
    // End of variables declaration//GEN-END:variables
}

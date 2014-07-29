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
import simcoviewer.datatypes.Bus;
import simcoviewer.datatypes.Cache;
import simcoviewer.datatypes.CacheLineEntry;
import simcoviewer.datatypes.MemoryDevice;
import simcoviewer.datatypes.processor.Processor;
import simcoviewer.datatypes.processor.Register;
import simcoviewer.datatypes.processor.RegisterType;
import simcoviewer.ui.tablemodels.BusTableModel;
import simcoviewer.ui.tablemodels.MemoryHierarchyTableModel;
import simcoviewer.ui.tablemodels.ProcessorTableModel;
import simcoviewer.utls.Conversions;

/**
 *
 * @author fede
 */
public class MainJF extends javax.swing.JFrame {

    private static final String LINE_START_CACHE = "Cache-";
    private static final String LINE_START_NEW_CYCLE = "Cycle ";
    private static final String LINE_START_CACHE_LINE_STATUS = "CacheLine-";
    private static final String LINE_START_RAM = "ram";
    private static final String LINE_START_BUS = "Bus-";
    private static final String LINE_START_BUS_CHANGE = "BusMessage-";
    private static final String LINE_START_PROCESSOR = "Processor-";
    private static final String LINE_START_REGISTER_DEFINITION = "Register-";
    private static final String LINE_START_REGISTER_VALUE_UPDATE = "RegisterValue-";
    private static final String LINE_START_PC_VALUE_UPDATE = "PCValue-";
    private static final String LINE_START_IR_VALUE_UPDATE = "InstructionRegisterValue-";
    private static final String LINE_START_FLAGS_VALUE_UPDATE = "FlagsValue-";
    
    // Simulable Objects properties
    private static final String SIMULABLE_PROPERTY_NAME = "name";
    private static final String SIMULABLE_PROPERTY_ID = "id";
    
    // Cache Properties
    private static final String CACHE_PROPERTY_SET_COUNT = "setCount";
    private static final String CACHE_PROPERTY_LINE_SIZE = "lineSize";
    private static final String CACHE_PROPERTY_ASSOCIATIVITY = "associativity";
    
    // Bus Properties
    private static final String BUS_PROPERTY_WIDTH = "width";
    
    // Cache Line Status Change Prperties
    private static final String CACHE_LINE_CHANGE_CACHE_ID = "cacheId";
    private static final String CACHE_LINE_CHANGE_CACHE_LINE_NUMBER = "lineNumber";
    private static final String CACHE_LINE_CHANGE_PROPERTY_TAG = "tag";
    private static final String CACHE_LINE_CHANGE_PROPERTY_STATE = "state";
    private static final String CACHE_LINE_CHANGE_PROPERTY_DATA = "data";
    
    // Bus Status Properties
    private static final String BUS_STATUS_CHANGE_BUS_ID = "busId";
    private static final String BUS_STATUS_CHANGE_MESSAGE_ID = "messageId"; // Relevant?
    private static final String BUS_STATUS_CHANGE_MESSAGE_TYPE = "messageType";
    private static final String BUS_STATUS_CHANGE_ADDRESS = "address";
    private static final String BUS_STATUS_CHANGE_DATA = "data";
    private static final String BUS_STATUS_CHANGE_SUBMITTER = "submitter";
    
    // Processor properties
    private static final String PROCESSOR_PROPERTY_PROCESSOR_TYPE = "type";
    
    // Register properties
    private static final String REGISTER_PROPERTY_PROCESSOR_ID = "processorId";
    private static final String REGISTER_PROPERTY_REGISTER_TYPE = "registerType";
    private static final String REGISTER_PROPERTY_REGISTER_NUMBER = "registerNumber";
    private static final String REGISTER_PROPERTY_REGISTER_VALUE = "value";
    
    // Flags properties
    private static final String FLAGS_PROPERTY_Z_FLAG = "zFlag";
    private static final String FLAGS_PROPERTY_N_FLAG = "nFlag";
    private static final String FLAGS_PROPERTY_C_FLAG = "cFlag";
    private static final String FLAGS_PROPERTY_V_FLAG = "vFlag";
    
    // PC properties
    private static final String PC_PROPERTY_PC_VALUE = "pcValue";
    
    // IR properties
    private static final String IR_PROPERTY_IR_VALUE = "irValue";   
            
    // Label Constants
    private static final String LABEL_CURRENT_CYCLE = "Current Cycle: ";
    
    private List<CycleViewerJF> viewers;
    
    // MemoryDevices Containers
    private List<MemoryDevice> memoryDevices;
    private Map<Long,MemoryDevice> memoryDeviceMap; // Maps Id's to memory devices
    // Bus Containers
    private List<Bus> buses;
    private Map<Long,Bus> busesMap; // maps ID's to buses
    // Processor Containers
    private List<Processor> processors;
    private Map<Long,Processor> processorMap; // maps ID's to processors
    // MessageDispatcherNames map
    private Map<Long,String> dispatcherNames;
    
    // Cycle view variables
    private long maxCycle;
    private long minCycle;
    private long currentCycle;
    
    /**
     * Creates new form JFPrincipal
     */
    public MainJF(String traceFileName) throws FileNotFoundException, IOException {
        
        this.setName("Simco Viewer");
        // Initialize containers 
        viewers = new ArrayList();
        memoryDevices = new ArrayList();
        memoryDeviceMap = new HashMap();
        buses = new ArrayList();
        busesMap = new HashMap();
        processors = new ArrayList();
        processorMap = new HashMap();
        dispatcherNames = new HashMap();
        
        initComponents();
        currentCycle = -1;
        maxCycle = -1;
        minCycle = -1;
        
        /* Open trace file and parse it */
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
                dispatcherNames.put(cache.getId(), cache.getName());
                
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
                            // Data comes in hexadecimal format
                            lineEntry.setData(Conversions.hexToASCII(propertyValue));
                            break;
                    }
                }
                Cache c = (Cache) memoryDeviceMap.get(cacheId);
                c.addCacheLineStatus(lineEntry, currentCycle);
            }else if (line.startsWith(LINE_START_BUS)){
                
                String[] props = line.split("-");
                Bus bus = new Bus();
                for (int i = 1; i < props.length; i++){
                    propertyName = props[i].split(":")[0];
                    propertyValue = props[i].split(":")[1];
                    switch (propertyName) {
                        case SIMULABLE_PROPERTY_ID:
                            bus.setId(Long.parseLong(propertyValue));
                            break;
                        case SIMULABLE_PROPERTY_NAME:
                            bus.setName(propertyValue);
                            break;
                        case BUS_PROPERTY_WIDTH:
                            bus.setDataWidth(Integer.parseInt(propertyValue));
                            break;
                        default:
                            break;
                    }
                }
                buses.add(bus);
                busesMap.put(bus.getId(), bus);
            }else if (line.startsWith(LINE_START_NEW_CYCLE)){ // CacheLine status change
                if (currentCycle == -1){            
                    maxCycle = Long.parseLong(line.split(" ")[1]);
                    minCycle = maxCycle;
                }else{
                    Iterator<MemoryDevice> iterMemoryDevices = memoryDevices.iterator();
                    while(iterMemoryDevices.hasNext()){
                        MemoryDevice mdev = iterMemoryDevices.next();
                        if (mdev instanceof Cache){
                            ((Cache) mdev).copyLineStatusFromLastCycle(currentCycle);
                        }
                    }
                    Iterator<Processor> iterProcessors = processors.iterator();
                    while(iterProcessors.hasNext()){
                        Processor proc = iterProcessors.next();
                        proc.copyProcessorStatusFromLastCycle(currentCycle);
                    }
                    Iterator<Bus> iterBuses = buses.iterator();
                    while(iterBuses.hasNext()){
                        Bus bus = iterBuses.next();
                        bus.addBusStatusIfEmpty(currentCycle);
                    }
                    
                    
                }
                currentCycle = Long.parseLong(line.split(" ")[1]);
                if (currentCycle > maxCycle){
                    maxCycle = currentCycle;
                }
                if (currentCycle < minCycle){
                    minCycle = currentCycle;
                }
            }else if (line.startsWith(LINE_START_BUS_CHANGE)){ // Bus Change Line
                
                String[] props = line.split("-");
                long busId = -1, address = -1;
                int messageType = -1;
                String data = null;
                String submitterName = null;
                for (int i = 1; i < props.length; i++){
                    propertyName = props[i].split(":")[0];
                    propertyValue = props[i].split(":")[1];
                    switch (propertyName) {
                        case BUS_STATUS_CHANGE_BUS_ID:
                            busId = Long.parseLong(propertyValue);
                            break;
                        case BUS_STATUS_CHANGE_MESSAGE_TYPE:
                            messageType = Integer.parseInt(propertyValue);
                            break;
                        case BUS_STATUS_CHANGE_ADDRESS:
                            address = Long.parseLong(propertyValue);
                            break;
                        case BUS_STATUS_CHANGE_DATA:
                            data = Conversions.hexToASCII(propertyValue);
                            break;
                        case BUS_STATUS_CHANGE_SUBMITTER:
                            submitterName = dispatcherNames.get(Long.parseLong(propertyValue));
                            break;
                        default:
                            // Message Id ??
                            break;
                    }
                }
                Bus bus = (Bus) busesMap.get(busId);
                bus.updateBusStatus(currentCycle, messageType,address,data,submitterName);
                
            }else if (line.startsWith(LINE_START_PROCESSOR)){

                String[] props = line.split("-");
                String processorType = null;
                String processorName = null;
                long id = -1;
                for (int i = 1; i < props.length; i++){
                    propertyName = props[i].split(":")[0];
                    propertyValue = props[i].split(":")[1];
                    switch (propertyName) {
                        case PROCESSOR_PROPERTY_PROCESSOR_TYPE:
                            processorType = propertyValue;
                            break;
                        case SIMULABLE_PROPERTY_ID:
                            id = Long.parseLong(propertyValue);
                            break;
                        case SIMULABLE_PROPERTY_NAME:
                            processorName = propertyValue;
                            break;
                        default:
                            // Message Id ??
                            break;
                    }
                }
                // TODO: Instance specific processor subclass when more than one available
                Processor processor = new Processor();
                processor.setId(id);
                processor.setProcessorName(processorName);
                processor.setProcessorType(processorType);
                processors.add(processor);
                processorMap.put(id, processor);
                dispatcherNames.put(id, processorName);
            } else if (line.startsWith(LINE_START_REGISTER_DEFINITION)){
                // Trace new register file 
                String[] props = line.split("-");
                long processorId = -1;
                int registerType = -1;
                int registerNumber = -1;
                long id = -1;
                for (int i = 1; i < props.length; i++){
                    propertyName = props[i].split(":")[0];
                    propertyValue = props[i].split(":")[1];
                    switch (propertyName) {
                        case REGISTER_PROPERTY_PROCESSOR_ID:
                            processorId = Long.parseLong(propertyValue);
                            break;
                        case REGISTER_PROPERTY_REGISTER_NUMBER:
                            registerNumber = Integer.parseInt(propertyValue);
                            break;
                        case REGISTER_PROPERTY_REGISTER_TYPE:
                            registerType = Integer.parseInt(propertyValue);
                            break;
                        default:
                            // Message Id ??
                            break;
                    }
                }
                // TODO: Instance specific processor subclass when more than one available
                Processor processor = processorMap.get(processorId);
                Register reg = new Register();
                if (registerType == 0){
                    reg.setRegType(RegisterType.REGISTER_TYPE_INT);
                }else if (registerType == 1){
                    reg.setRegType(RegisterType.REGISTER_TYPE_FP);
                }else{
                    throw new RuntimeException("Unsupported register type");
                }
                reg.setRegisterNumber(registerNumber);
                reg.setRegTypeAsInt(registerType);
                processor.addRegister(reg);
            }else if (line.startsWith(LINE_START_REGISTER_VALUE_UPDATE)){
                // Programmable register update line
                // TODO
                String[] props = line.split("-");
                long processorId = -1;
                int registerNumber = -1;
                int registerType = -1;
                String registerValue = null;
                for (int i = 1; i < props.length; i++){
                    propertyName = props[i].split(":")[0];
                    propertyValue = props[i].split(":")[1];
                    switch (propertyName) {
                        case REGISTER_PROPERTY_PROCESSOR_ID:
                            processorId = Long.parseLong(propertyValue);
                            break;
                        case REGISTER_PROPERTY_REGISTER_NUMBER:
                            registerNumber = Integer.parseInt(propertyValue);
                            break;
                        case REGISTER_PROPERTY_REGISTER_VALUE:
                            registerValue = propertyValue;
                            break;
                        case REGISTER_PROPERTY_REGISTER_TYPE:
                            registerType = Integer.parseInt(propertyValue);
                            break;
                        default:
                            // Message Id ??
                            break;
                    }
                }
                Processor processor = processorMap.get(processorId);
                processor.addRegisterValueEntry(currentCycle, registerNumber, registerType, registerValue);
                
            } else if (line.startsWith(LINE_START_PC_VALUE_UPDATE)){
                // Program counter update line
                String[] props = line.split("-");
                long processorId = -1;
                long pcValue = -1;
                for (int i = 1; i < props.length; i++){
                    propertyName = props[i].split(":")[0];
                    propertyValue = props[i].split(":")[1];
                    switch (propertyName) {
                        case REGISTER_PROPERTY_PROCESSOR_ID:
                            processorId = Long.parseLong(propertyValue);
                            break;
                        case PC_PROPERTY_PC_VALUE:
                            pcValue = Long.parseLong(propertyValue);
                            break;
                        default:
                            break;
                    }
                }
                Processor processor = processorMap.get(processorId);
                processor.addCyclePCValue(currentCycle,pcValue);
                
            }else if (line.startsWith(LINE_START_IR_VALUE_UPDATE)){
                // Instruction register update line
                String[] props = line.split("-");
                long processorId = -1;
                String irValue = null;
                for (int i = 1; i < props.length; i++){
                    propertyName = props[i].split(":")[0];
                    propertyValue = props[i].split(":")[1];
                    switch (propertyName) {
                        case REGISTER_PROPERTY_PROCESSOR_ID:
                            processorId = Long.parseLong(propertyValue);
                            break;
                        case IR_PROPERTY_IR_VALUE:
                            irValue = Conversions.hexToASCII(propertyValue);
                            break;
                        default:
                            break;
                    }
                }
                Processor processor = processorMap.get(processorId);
                processor.addCycleIRValue(currentCycle,irValue);
            }else if (line.startsWith(LINE_START_FLAGS_VALUE_UPDATE)){
                // Flags register update line
                String[] props = line.split("-");
                long processorId = -1;
                int nflag = -1;
                int zflag = -1;
                int vflag = -1;
                int cflag = -1;
                for (int i = 1; i < props.length; i++){
                    propertyName = props[i].split(":")[0];
                    propertyValue = props[i].split(":")[1];
                    switch (propertyName) {
                        case REGISTER_PROPERTY_PROCESSOR_ID:
                            processorId = Long.parseLong(propertyValue);
                            break;
                        case FLAGS_PROPERTY_Z_FLAG:
                            zflag = Integer.parseInt(propertyValue);
                            break;
                        case FLAGS_PROPERTY_N_FLAG:
                            nflag = Integer.parseInt(propertyValue);
                            break;        
                        case FLAGS_PROPERTY_C_FLAG:
                            cflag = Integer.parseInt(propertyValue);
                            break;
                        case FLAGS_PROPERTY_V_FLAG:
                            vflag = Integer.parseInt(propertyValue);
                            break;
                        default:
                            break;
                    }
                }
                Processor processor = processorMap.get(processorId);
                processor.addCycleFlagsValue(currentCycle, zflag, nflag, cflag, vflag);
            }   // else if (line.starsWith(
            
            
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
        tblMemoryDevices.setModel(mhtm);
        BusTableModel btm = new BusTableModel(buses);
        tblBuses.setModel(btm);
        ProcessorTableModel ptm = new ProcessorTableModel(processors);
        tblProcessors.setModel(ptm);
        
        if (buses.isEmpty()){
            tabPaneCompSystem.remove(pnlBuses);
        }
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
        pnlMemoryDevices = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMemoryDevices = new javax.swing.JTable();
        pnlBuses = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBuses = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProcessors = new javax.swing.JTable();
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

        javax.swing.GroupLayout pnlMemoryDevicesLayout = new javax.swing.GroupLayout(pnlMemoryDevices);
        pnlMemoryDevices.setLayout(pnlMemoryDevicesLayout);
        pnlMemoryDevicesLayout.setHorizontalGroup(
            pnlMemoryDevicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMemoryDevicesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlMemoryDevicesLayout.setVerticalGroup(
            pnlMemoryDevicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMemoryDevicesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
        );

        tabPaneCompSystem.addTab("MemoryDevices", pnlMemoryDevices);

        tblBuses.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jScrollPane1.setViewportView(tblBuses);

        javax.swing.GroupLayout pnlBusesLayout = new javax.swing.GroupLayout(pnlBuses);
        pnlBuses.setLayout(pnlBusesLayout);
        pnlBusesLayout.setHorizontalGroup(
            pnlBusesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBusesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBusesLayout.setVerticalGroup(
            pnlBusesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBusesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
        );

        tabPaneCompSystem.addTab("Buses", pnlBuses);

        tblProcessors.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblProcessors);

        tabPaneCompSystem.addTab("Processors", jScrollPane3);

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
                            .addComponent(lblTargetCycle))))
                .addGap(11, 11, 11)
                .addComponent(tabPaneCompSystem, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewElementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewElementActionPerformed
        int selectedPane = tabPaneCompSystem.getSelectedIndex();
        int row;
        CycleViewerJF viewer = null;
        switch(selectedPane){
            case 0:
                // MemoryHierarchy
                row = tblMemoryDevices.getSelectedRow();
                if (row != -1){
                    MemoryHierarchyTableModel mhtm = (MemoryHierarchyTableModel) tblMemoryDevices.getModel();
                    MemoryDevice device = mhtm.getMemoryDevice(row);
                    switch(device.getType()){
                        case CACHE:
                            Cache cache = (Cache) device;
                            viewer = new CacheViewerJF(cache,currentCycle);
                            break;
                        case RAM:
                            break;
                        default:
                            break;
                    }
                }
                break;
            case 1:
                // Pane Buses
                row = tblBuses.getSelectedRow();
                if (row != -1){
                    BusTableModel btm = (BusTableModel) tblBuses.getModel();
                    Bus bus = btm.getBus(row);
                    viewer = new BusViewerJF(bus);
                }
                break;
            case 2:
                // Pane Processors
                row = tblProcessors.getSelectedRow();
                if (row != -1){
                    ProcessorTableModel ptm = (ProcessorTableModel) tblProcessors.getModel();
                    Processor processor = ptm.getProcessor(row);
                    switch(processor.getProcessorType()){
                        case "SimpleUnpipedProcessor":
                            viewer = new SimpleUnpipedProcessorViewerJF(processor);
                            break;
                        default:
                            System.out.println("Error: not supported processor type");
                            break;
                    }
                }
            default:
                break;
        }
        if (viewer != null){                
            viewers.add(viewer);
            viewer.setVisible(true);
            viewer.setCurrentCycle(currentCycle);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAdvanceCycle;
    private javax.swing.JLabel lblCurrentCycle;
    private javax.swing.JLabel lblTargetCycle;
    private javax.swing.JPanel pnlBuses;
    private javax.swing.JPanel pnlMemoryDevices;
    private javax.swing.JTabbedPane tabPaneCompSystem;
    private javax.swing.JTable tblBuses;
    private javax.swing.JTable tblMemoryDevices;
    private javax.swing.JTable tblProcessors;
    private javax.swing.JTextField txtTargetCycle;
    // End of variables declaration//GEN-END:variables
}

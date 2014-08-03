/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.ui;

import javax.swing.JTable;
import simcoviewer.datatypes.processor.Processor;
import simcoviewer.datatypes.processor.ProcessorState;
import simcoviewer.ui.tablemodels.RegisterFileViewerTableModel;
import simcoviewer.utls.Conversions;

/**
 *
 * @author fede
 */
public class SimpleUnpipedProcessorViewerJF extends CycleViewerJF {

    private Processor processor;
    private long currentCycle;
    
    /**
     * Creates new form SiimpleUnpipedProcessorViewerJF
     */
    public SimpleUnpipedProcessorViewerJF(Processor proc) {
        initComponents();
        this.processor = proc;
        this.setTitle(processor.getProcessorName());
        currentCycle = 0;
        RegisterFileViewerTableModel regTableModel = 
                new RegisterFileViewerTableModel(processor.getArchitectedRegisterFile(), 0);
        tblProgramableRegisters.setModel(regTableModel);
    }

    @Override
    public void setCurrentCycle(long currentCycle) {
        ProcessorState cycleState = processor.getCycleProcessorState(currentCycle);
        this.currentCycle = currentCycle;
        if (cycleState != null){
            long pcValue = cycleState.getPcValue();
            setPCValue(pcValue);
            setIRValue(cycleState.getIrValue());
            txtCurrentStage.setText(cycleState.getCurrentStage().toString());
            txtFlagsRegister.setText("Z = " + cycleState.getzFlag() + "    N =  " + cycleState.getnFlag()
                    + "    C = " + cycleState.getcFlag() + "    V = " + cycleState.getvFlag());
        }
        ((RegisterFileViewerTableModel) tblProgramableRegisters.getModel()).setCycle(currentCycle);
        ((RegisterFileViewerTableModel) tblProgramableRegisters.getModel()).fireTableDataChanged();
        
        tblProgramableRegisters.getColumnModel().getColumn(0).setWidth(95);
        tblProgramableRegisters.getColumnModel().getColumn(0).setMinWidth(95);
        tblProgramableRegisters.getColumnModel().getColumn(0).setMaxWidth(95);
        tblProgramableRegisters.getColumnModel().getColumn(0).setResizable(false);
        tblProgramableRegisters.getColumnModel().getColumn(1).setWidth(140);
        tblProgramableRegisters.getColumnModel().getColumn(1).setMinWidth(140);
        tblProgramableRegisters.getColumnModel().getColumn(1).setMaxWidth(140);
        tblProgramableRegisters.getColumnModel().getColumn(1).setResizable(false);
        tblProgramableRegisters.getColumnModel().getColumn(2).setWidth(130);
        tblProgramableRegisters.getColumnModel().getColumn(2).setMinWidth(130);
        tblProgramableRegisters.getColumnModel().getColumn(2).setMaxWidth(130);
        tblProgramableRegisters.getColumnModel().getColumn(2).setResizable(false);
        tblProgramableRegisters.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
    
    void setPCValue(long pcValue){
        switch(dataFormat){
            case ASCII:
                txtProgramCounter.setText(Long.toString(pcValue));
                break;
            case BINARY:
                txtProgramCounter.setText(Long.toBinaryString(pcValue));
                break;
            case HEXADECIMAL:
                txtProgramCounter.setText(String.format("0x%08X", pcValue));
                break;
        }
    }
    
    void setIRValue(String IRValue){
        if (IRValue == null){
            txtInstructionRegister.setText(IRValue);
        }else{
            switch(dataFormat){
                case ASCII:
                    txtInstructionRegister.setText(IRValue);
                    break;
                case BINARY:
                    txtInstructionRegister.setText(Conversions.asciiToBinary(IRValue));
                    break;
                case HEXADECIMAL:
                    txtInstructionRegister.setText(Conversions.asciiToHex(IRValue));
                    break;
            }
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

        lblProgramCounter = new javax.swing.JLabel();
        lblInstructionRegister = new javax.swing.JLabel();
        lblFlagsRegister = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProgramableRegisters = new javax.swing.JTable();
        txtInstructionRegister = new javax.swing.JTextField();
        txtProgramCounter = new javax.swing.JTextField();
        lblProgramableRegisters = new javax.swing.JLabel();
        txtFlagsRegister = new javax.swing.JTextField();
        lblCurrentStage = new javax.swing.JLabel();
        txtCurrentStage = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        lblProgramCounter.setText("Program Counter: ");

        lblInstructionRegister.setText("Instruction Register: ");

        lblFlagsRegister.setText("FLAGS Register: ");

        tblProgramableRegisters.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblProgramableRegisters);

        txtInstructionRegister.setEditable(false);
        txtInstructionRegister.setText("jTextField5");
        txtInstructionRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInstructionRegisterActionPerformed(evt);
            }
        });

        txtProgramCounter.setEditable(false);
        txtProgramCounter.setText("jTextField6");
        txtProgramCounter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProgramCounterActionPerformed(evt);
            }
        });

        lblProgramableRegisters.setText("Programable Registers:");

        txtFlagsRegister.setEditable(false);
        txtFlagsRegister.setText("jTextField1");

        lblCurrentStage.setText("Current Stage:");

        txtCurrentStage.setEditable(false);
        txtCurrentStage.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFlagsRegister)
                        .addGap(25, 25, 25)
                        .addComponent(txtFlagsRegister))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblInstructionRegister)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInstructionRegister))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProgramableRegisters)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProgramCounter)
                            .addComponent(lblCurrentStage))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProgramCounter)
                            .addComponent(txtCurrentStage))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCurrentStage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCurrentStage, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProgramCounter)
                    .addComponent(txtProgramCounter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInstructionRegister)
                    .addComponent(txtInstructionRegister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFlagsRegister)
                    .addComponent(txtFlagsRegister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProgramableRegisters)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtInstructionRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInstructionRegisterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInstructionRegisterActionPerformed

    private void txtProgramCounterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProgramCounterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProgramCounterActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCurrentStage;
    private javax.swing.JLabel lblFlagsRegister;
    private javax.swing.JLabel lblInstructionRegister;
    private javax.swing.JLabel lblProgramCounter;
    private javax.swing.JLabel lblProgramableRegisters;
    private javax.swing.JTable tblProgramableRegisters;
    private javax.swing.JTextField txtCurrentStage;
    private javax.swing.JTextField txtFlagsRegister;
    private javax.swing.JTextField txtInstructionRegister;
    private javax.swing.JTextField txtProgramCounter;
    // End of variables declaration//GEN-END:variables

    @Override
    public void dataFormatValueChanged() {
        setCurrentCycle(currentCycle);
    }
    

    
}

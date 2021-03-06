/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.ui;

import simcoviewer.datatypes.Bus;
import simcoviewer.datatypes.BusStatus;
import simcoviewer.datatypes.enumerates.DataFormat;
import static simcoviewer.datatypes.enumerates.DataFormat.ASCII;
import static simcoviewer.datatypes.enumerates.DataFormat.BINARY;
import static simcoviewer.datatypes.enumerates.DataFormat.HEXADECIMAL;
import simcoviewer.datatypes.enumerates.MessageType;
import simcoviewer.utls.Conversions;

/**
 *
 * @author fede
 */
public class BusViewerJF extends CycleViewerJF {

    Bus bus;
    long currentCycle;
    
    /**
     * Creates new form BusViewerJF
     */
    public BusViewerJF(Bus bus) {
        initComponents();
        this.setTitle(bus.getName());
        this.bus = bus;
        currentCycle = -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtOwnerName = new javax.swing.JTextField();
        lblOwner = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        lblData = new javax.swing.JLabel();
        lblMessageType = new javax.swing.JLabel();
        txtMessageType = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        txtOwnerName.setEditable(false);
        txtOwnerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOwnerNameActionPerformed(evt);
            }
        });

        lblOwner.setText("Owner:");

        txtAddress.setEditable(false);

        lblData.setText("Data:");

        lblMessageType.setText("Message Type:");

        txtMessageType.setEditable(false);
        txtMessageType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMessageTypeActionPerformed(evt);
            }
        });

        lblAddress.setText("Address:");

        txtData.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddress)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblMessageType)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(40, 40, 40)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblOwner)
                                .addGap(47, 47, 47)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtOwnerName)
                            .addComponent(txtAddress)
                            .addComponent(txtMessageType, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                            .addComponent(txtData))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOwner)
                    .addComponent(txtOwnerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMessageType)
                    .addComponent(txtMessageType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddress)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblData)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtOwnerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOwnerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOwnerNameActionPerformed

    private void txtMessageTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMessageTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMessageTypeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblMessageType;
    private javax.swing.JLabel lblOwner;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtMessageType;
    private javax.swing.JTextField txtOwnerName;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setCurrentCycle(long currentCycle) {
        this.currentCycle = currentCycle;
        BusStatus status = bus.getBusStatus(currentCycle);
        if (status.getAddress() != -1){
            txtAddress.setText("0x"+Long.toHexString(status.getAddress()));
            txtAddress.setEnabled(true);
        }else{
            txtAddress.setText("");
            txtAddress.setEnabled(false);
        }
        if (status.getData() != null){
            switch(dataFormat){
                    case ASCII:
                        txtData.setText(status.getData());
                        break;
                    case BINARY:
                        txtData.setText(Conversions.asciiToBinary(status.getData()));
                        break;
                    case HEXADECIMAL:
                        txtData.setText(Conversions.asciiToHex(status.getData()));
                        break;
                
            }
            txtData.setEnabled(true);
        }else{
            txtData.setText("");
            txtData.setEnabled(false);
        }
        if (status.getOwner()!= null){
            txtOwnerName.setText(status.getOwner());
            txtOwnerName.setEnabled(true);
        }else{
            txtOwnerName.setText("");
            txtOwnerName.setEnabled(false);
        }
        if (status.getMessageType() != MessageType.NO_MESSAGE){
            txtMessageType.setText(status.getMessageType().toString());
            txtMessageType.setEnabled(true);
        }else{
            txtMessageType.setText("");
            txtMessageType.setEnabled(false);
        }
    }

    @Override
    public void dataFormatValueChanged() {
        setCurrentCycle(currentCycle);
    }
}

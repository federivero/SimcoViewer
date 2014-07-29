/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer.ui;

import simcoviewer.datatypes.enumerates.DataFormat;

/**
 *
 * @author fede
 */
public abstract class CycleViewerJF extends javax.swing.JFrame {

    protected DataFormat dataFormat; 
            
    /**
     * Creates new form CycleViewerJF
     */
    public CycleViewerJF() {
        initComponents();
        dataFormat = DataFormat.HEXADECIMAL;
    }
    
    public abstract void setCurrentCycle(long currentCycle);

    public abstract void dataFormatValueChanged();
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        MenuItemBinaryData = new javax.swing.JMenuItem();
        MenuItemHexadecimalData = new javax.swing.JMenuItem();
        MenuItemASCIIData = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jMenu1.setText("Data");

        jMenu2.setText("Format");

        MenuItemBinaryData.setText("Binary");
        MenuItemBinaryData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemBinaryDataActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemBinaryData);

        MenuItemHexadecimalData.setText("Hexadecimal");
        MenuItemHexadecimalData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemHexadecimalDataActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemHexadecimalData);

        MenuItemASCIIData.setText("ASCII");
        MenuItemASCIIData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemASCIIDataActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemASCIIData);

        jMenu1.add(jMenu2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuItemBinaryDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemBinaryDataActionPerformed
        dataFormat = DataFormat.BINARY;
        dataFormatValueChanged();
    }//GEN-LAST:event_MenuItemBinaryDataActionPerformed

    private void MenuItemASCIIDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemASCIIDataActionPerformed
        dataFormat = DataFormat.ASCII;
        dataFormatValueChanged();
    }//GEN-LAST:event_MenuItemASCIIDataActionPerformed

    private void MenuItemHexadecimalDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemHexadecimalDataActionPerformed
        dataFormat = DataFormat.HEXADECIMAL;
        dataFormatValueChanged();
    }//GEN-LAST:event_MenuItemHexadecimalDataActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuItemASCIIData;
    private javax.swing.JMenuItem MenuItemBinaryData;
    private javax.swing.JMenuItem MenuItemHexadecimalData;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simcoviewer;

import java.io.FileNotFoundException;
import java.io.IOException;
import simcoviewer.ui.MainJF;

/**
 *
 * @author fede
 */
public class SimcoViewer {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String traceFileName;
        if (args.length >= 1){
            traceFileName = args[1];
        }else{
            traceFileName = "trace.trc"; 
        }
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>    
        try{        
            new MainJF(traceFileName).setVisible(true);
        }catch(FileNotFoundException e){
            System.out.println("No trace file found of name " + traceFileName);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

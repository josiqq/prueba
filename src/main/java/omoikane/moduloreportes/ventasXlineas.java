/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ventasXlineas.java
 *
 * Created on 12/07/2010, 04:53:23 PM
 */

package omoikane.moduloreportes;


import javax.swing.*;
import java.text.*;
/**
 *
 * @author Phesus-Lab
 */
public class ventasXlineas extends JPanel {

        
    /** Creates new form ventasXlineas */
    public ventasXlineas(){
        initComponents();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        dateDesde = new org.jdesktop.swingx.JXDatePicker();
        dateHasta = new org.jdesktop.swingx.JXDatePicker();
        panel = new JPanel();
        barra = new JProgressBar();
        jScrollPane1 = new JScrollPane();
        lista = new JList();
        jButton1 = new JButton();
        jLabel4 = new JLabel();

        setMinimumSize(new java.awt.Dimension(50, 50));
        setPreferredSize(new java.awt.Dimension(600, 400));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Ventas por líneas");
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 3, 12));
        jLabel2.setText("Desde :");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 3, 12));
        jLabel3.setText("Hasta :");

        dateDesde.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N

        dateHasta.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panel.setMinimumSize(new java.awt.Dimension(580, 218));
        panel.setLayout(new java.awt.BorderLayout());

        barra.setBackground(new java.awt.Color(0, 0, 0));
        barra.setForeground(new java.awt.Color(255, 255, 255));

        lista.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        lista.setVisibleRowCount(4);
        jScrollPane1.setViewportView(lista);

        jButton1.setFont(new java.awt.Font("Times New Roman", 3, 12));
        jButton1.setText("Generar Reporte");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 3, 12));
        jLabel4.setText("Líneas");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(dateDesde, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateHasta, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)))
                            .addComponent(barra, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                            .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(166, 166, 166)
                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addGap(206, 206, 206))
                    .addComponent(panel, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(dateDesde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(dateHasta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(barra, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Funciones.lanzarReporteVXL(this);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JProgressBar barra;
    private org.jdesktop.swingx.JXDatePicker dateDesde;
    private org.jdesktop.swingx.JXDatePicker dateHasta;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JList lista;
    private JPanel panel;
    // End of variables declaration//GEN-END:variables

    public Object[] getLineas(){
        return this.lista.getSelectedValues();
    }

    public String getFechaDesde(){
        String fechaDesde     = "";
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        if(this.dateDesde.getDate() != null)
        {
            try {
                fechaDesde = sdf.format(this.dateDesde.getDate());
            } catch(Exception e) { JOptionPane.showMessageDialog(null, "Error en el registro: Fecha invÃ¡lida"); }
        }
        fechaDesde=fechaDesde+" 00:00:00";
        return fechaDesde ;
    }

    public String getFechaHasta(){
        String fechaHasta     = "";
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        if(this.dateDesde.getDate() != null)
        {
            try {
                fechaHasta = sdf.format(this.dateHasta.getDate());
            } catch(Exception e) { JOptionPane.showMessageDialog(null, "Error en el registro: Fecha invÃ¡lida"); }
        }
        fechaHasta=fechaHasta+" 23:59:59";
        return fechaHasta ;
    }

}


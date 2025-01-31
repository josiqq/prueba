/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CatalogoVentas.java
 *
 * Created on 10/12/2008, 11:56:06 PM
 */

package omoikane.formularios;

import java.sql.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import omoikane.sistema.*;
import javax.swing.event.*;
import java.text.*;
import omoikane.principal.*;




/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class VentasDetalles extends JInternalFrame {

    //TimerBusqueda          timerBusqueda;
    BufferedImage          fondo;
    public int             IDSeleccionado;
    public String          codigoSeleccionado;
    public String          txtQuery;
    public String          letra;
    public Boolean         hacer = true;
    NadesicoTableModel modelo;

    


    /** Creates new form CatalogoVentas */
    public VentasDetalles() {
        initComponents();
               
        //Instrucciones para el funcionamiento del fondo semistransparente
        this.setOpaque(false);
        ((JPanel)this.getContentPane()).setOpaque(false);
        this.getLayeredPane().setOpaque(false);
        this.getRootPane().setOpaque(false);
        this.generarFondo(this);
        this.txtFecha.setDate(new java.util.Date());
        Herramientas.centrarVentana(this);
    }

    public void setQueryTable(String query) {
        txtQuery = query;
        modelo.setQuery(query);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new JLabel();
        btnCerrar = new JButton();
        btnImprimir = new JButton();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        jLabel1 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        jLabel9 = new JLabel();
        txtCliente = new JTextField();
        txtSubtotal = new JTextField();
        txtAlmacen = new JTextField();
        txtDescuento = new JTextField();
        txtImpuesto = new JTextField();
        txtTotal = new JTextField();
        txtFecha = new org.jdesktop.swingx.JXDatePicker();
        chkFacturado = new JCheckBox();
        btnFacturado = new JButton();
        jLabel10 = new JLabel();
        aFacturar = new JTextField();
        txtIDVenta = new JLabel();

        jLabel2.setFont(new Font("Arial", 1, 24)); // NOI18N
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("<html><head><style type='text/css'>body { font-family: 'Roboto Thin'; font-size: 28px; }</style></head>\n<body>\nVentas Detalles\n</body></html>");

        btnCerrar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnCerrar.setIcon(new ImageIcon(getClass().getResource("/32x32/back.png"))); // NOI18N
        btnCerrar.setText("<HTML>Regresar a menú [Esc]</HTML>");
        btnCerrar.setRequestFocusEnabled(false);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnImprimir.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnImprimir.setIcon(new ImageIcon(getClass().getResource("/32x32/printer.png"))); // NOI18N
        btnImprimir.setText("<html><center>Imprimir [F8]</center></html>");
        btnImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jTable1.setFont(new Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código artículo", "Descripción", "Costo", "Cantidad", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setRowHeight(20);
        jTable1.setShowHorizontalLines(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setText("<html>Cliente a <br>facturar [F1]:<html>");

        jLabel3.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new Color(255, 255, 255));
        jLabel3.setText("Almacen :");

        jLabel4.setFont(new Font("Arial", 1, 24)); // NOI18N
        jLabel4.setForeground(new Color(255, 255, 255));
        jLabel4.setText("Total :");

        jLabel6.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new Color(255, 255, 255));
        jLabel6.setText("Subtotal :");

        jLabel7.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel7.setForeground(new Color(255, 255, 255));
        jLabel7.setText("Impuesto :");

        jLabel8.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new Color(255, 255, 255));
        jLabel8.setText("Descuento :");

        jLabel9.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel9.setForeground(new Color(255, 255, 255));
        jLabel9.setText("Fecha :");

        txtCliente.setEditable(false);
        txtCliente.setFont(new Font("Arial", 0, 12)); // NOI18N
        txtCliente.setHorizontalAlignment(JTextField.CENTER);

        txtSubtotal.setEditable(false);
        txtSubtotal.setFont(new Font("Arial", 0, 12)); // NOI18N
        txtSubtotal.setHorizontalAlignment(JTextField.CENTER);

        txtAlmacen.setEditable(false);
        txtAlmacen.setFont(new Font("Arial", 0, 12)); // NOI18N
        txtAlmacen.setHorizontalAlignment(JTextField.CENTER);

        txtDescuento.setEditable(false);
        txtDescuento.setFont(new Font("Arial", 0, 12)); // NOI18N
        txtDescuento.setHorizontalAlignment(JTextField.CENTER);

        txtImpuesto.setEditable(false);
        txtImpuesto.setFont(new Font("Arial", 0, 12)); // NOI18N
        txtImpuesto.setHorizontalAlignment(JTextField.CENTER);
        txtImpuesto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                txtImpuestoActionPerformed(evt);
            }
        });

        txtTotal.setEditable(false);
        txtTotal.setFont(new Font("Arial", 0, 24)); // NOI18N
        txtTotal.setHorizontalAlignment(JTextField.CENTER);

        txtFecha.setEditable(false);
        txtFecha.setFocusable(false);
        txtFecha.setFont(new Font("Arial", 0, 12)); // NOI18N
        txtFecha.setFormats(DateFormat.getDateInstance(DateFormat.MEDIUM));

        chkFacturado.setFont(new Font("Arial", 1, 14)); // NOI18N
        chkFacturado.setForeground(new Color(255, 255, 255));
        chkFacturado.setText("Facturado");
        chkFacturado.setEnabled(false);
        chkFacturado.setFocusable(false);

        btnFacturado.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnFacturado.setIcon(new ImageIcon(getClass().getResource("/32x32/notes_edit.png"))); // NOI18N
        btnFacturado.setText("Facturar [F6]");
        btnFacturado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnFacturadoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new Color(255, 255, 255));
        jLabel10.setText("Cliente :");

        aFacturar.setEditable(false);
        aFacturar.setFont(new Font("Arial", 0, 12)); // NOI18N
        aFacturar.setHorizontalAlignment(JTextField.CENTER);
        aFacturar.setFocusable(false);

        txtIDVenta.setBackground(new Color(153, 51, 0));
        txtIDVenta.setFont(new Font("Arial", 1, 24)); // NOI18N
        txtIDVenta.setForeground(new Color(255, 255, 255));
        txtIDVenta.setHorizontalAlignment(SwingConstants.LEFT);
        txtIDVenta.setVerticalAlignment(SwingConstants.BOTTOM);
        txtIDVenta.setFocusable(false);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(aFacturar))
                                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSubtotal))
                                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel7)
                                            .addComponent(chkFacturado))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnFacturado, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                            .addComponent(txtDescuento, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                            .addComponent(txtImpuesto)
                                            .addComponent(txtTotal))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCliente, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtAlmacen, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtFecha, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtIDVenta, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(btnImprimir, GroupLayout.PREFERRED_SIZE, 433, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIDVenta, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtCliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtAlmacen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtFecha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(aFacturar, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFacturado, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkFacturado))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSubtotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescuento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(txtImpuesto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotal)
                            .addComponent(jLabel4)))
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimir, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        Ventas.reimprimirTicket(IDSeleccionado);
}//GEN-LAST:event_btnImprimirActionPerformed

    private void btnFacturadoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnFacturadoActionPerformed
        // TODO add your handling code here:
        Ventas.lanzarImprimirFactura(this,letra);
        if(hacer){
        this.chkFacturado.setSelected(true);
        Ventas.actualizar(this.txtIDVenta.getText(),this.aFacturar.getText());}

}//GEN-LAST:event_btnFacturadoActionPerformed

    private void btnCerrarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
}//GEN-LAST:event_btnCerrarActionPerformed

    private void txtImpuestoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_txtImpuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImpuestoActionPerformed

   public JTextField getFactura() { return this.aFacturar;    }
   public SimpleDateFormat sdf          = new SimpleDateFormat("yyyy-MM-dd");
   public void setFacturar    (String  val) { aFacturar.setText(val);this.chkFacturado.setSelected(true);this.aFacturar.setVisible(false);this.jLabel1.setVisible(false);this.hacer=false;}
   public void setIDVenta     (String  val) { txtIDVenta.setText(val);  }
   public void setCliente     (String  val) { txtCliente.setText(val);  }
   public void setDescuento   (String  val) { txtDescuento.setText(val);}
   public void setImpuesto    (String  val) { txtImpuesto.setText(val); }
   public void setSubtotal    (String  val) { txtSubtotal.setText(val); }
   public void setTotal       (String  val) { txtTotal.setText(val);    }
   public void setAlmacen     (String  val) { txtAlmacen.setText(val);  }
   public void setFecha       (String  val) { try {txtFecha.setDate(sdf.parse(val)); } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el registro: Fecha inválida", Herramientas.getStackTraceString(e)); } }
   public String getCliente() { return aFacturar.getText();}

   
    public void setTablaPrincipal(java.util.List val) {
        DefaultTableModel modelo = ((DefaultTableModel)this.jTable1.getModel());
        for(int i = 0; i < val.size(); i++)
        {
            modelo.addRow(((ArrayList) val.get(i)).toArray());
        }
     }

    public void paintComponent(Graphics g)
    {
      Graphics2D g2d = (Graphics2D) g;
      g2d.drawImage(fondo, 0, 0, null);

    }

    public void generarFondo(Component componente)
    {
      Rectangle areaDibujo = this.getBounds();
      BufferedImage tmp;
      GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

      tmp = gc.createCompatibleImage(areaDibujo.width, areaDibujo.height,BufferedImage.TRANSLUCENT);
      Graphics2D g2d = (Graphics2D) tmp.getGraphics();
      g2d.setColor(new Color(0,0,0,165));
      g2d.fillRect(0,0,areaDibujo.width,areaDibujo.height);
      fondo = tmp;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JTextField aFacturar;
    private JButton btnCerrar;
    private JButton btnFacturado;
    private JButton btnImprimir;
    private JCheckBox chkFacturado;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JTextField txtAlmacen;
    private JTextField txtCliente;
    private JTextField txtDescuento;
    private org.jdesktop.swingx.JXDatePicker txtFecha;
    public JLabel txtIDVenta;
    private JTextField txtImpuesto;
    private JTextField txtSubtotal;
    private JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

}

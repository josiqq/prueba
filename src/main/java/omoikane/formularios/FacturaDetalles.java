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

import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import omoikane.sistema.*;
import javax.swing.table.*;

public class FacturaDetalles extends JInternalFrame {

    //TimerBusqueda          timerBusqueda;
    BufferedImage          fondo;
    // omoikane.sistema.NadesicoTableModel modelo;
    
    /** Creates new form CatalogoVentas */
    public FacturaDetalles() {
        initComponents();
               
        //Instrucciones para el funcionamiento del fondo semistransparente
        this.setOpaque(false);
        ((JPanel)this.getContentPane()).setOpaque(false);
        this.getLayeredPane().setOpaque(false);
        this.getRootPane().setOpaque(false);
        this.generarFondo(this);
        this.jxdFecha.setDate(new java.util.Date());
        Herramientas.centrarVentana(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new JLabel();
        btnCerrar = new JButton();
        lblIdFactura = new JLabel();
        lblAlmacen = new JLabel();
        lblCliente = new JLabel();
        txtAlmacen = new JTextField();
        txtCliente = new JTextField();
        jxdFecha = new org.jdesktop.swingx.JXDatePicker();
        lblFecha = new JLabel();
        jScrollPane1 = new JScrollPane();
        tblDetalles = new JTable();
        lblSubtotal = new JLabel();
        lblImpuestos = new JLabel();
        lblTotal = new JLabel();
        txtSubtotal = new JTextField();
        txtImpuestos = new JTextField();
        txtTotal = new JTextField();
        btnAgregar = new JButton();
        btnBorrar = new JButton();
        btnCancelar = new JButton();
        lblExpidio = new JLabel();
        lblCancelo = new JLabel();
        txtExpidio = new JTextField();
        txtCancelo = new JTextField();
        btnImprimir = new JButton();
        txtTicket = new JTextField();
        lblTotal1 = new JLabel();
        txtCliente1 = new JTextField();

        setFocusable(false);

        lblTitulo.setFont(new Font("Arial", 1, 36));
        lblTitulo.setForeground(new Color(255, 255, 255));
        lblTitulo.setText("Factura Detalles");

        btnCerrar.setFont(new Font("Arial", 0, 14));
        btnCerrar.setIcon(new ImageIcon(getClass().getResource("/32x32/back.png"))); // NOI18N
        btnCerrar.setText("<HTML>Regresar a menú [Esc]</HTML>");
        btnCerrar.setNextFocusableComponent(txtCliente);
        btnCerrar.setRequestFocusEnabled(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        lblIdFactura.setBackground(new Color(153, 51, 0));
        lblIdFactura.setFont(new Font("Arial", 1, 24));
        lblIdFactura.setForeground(new Color(255, 255, 255));
        lblIdFactura.setHorizontalAlignment(SwingConstants.LEFT);
        lblIdFactura.setVerticalAlignment(SwingConstants.BOTTOM);
        lblIdFactura.setFocusable(false);

        lblAlmacen.setFont(new Font("Arial", 1, 14));
        lblAlmacen.setForeground(new Color(255, 255, 255));
        lblAlmacen.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAlmacen.setText("Almacen:");
        lblAlmacen.setHorizontalTextPosition(SwingConstants.RIGHT);

        lblCliente.setFont(new Font("Arial", 1, 14));
        lblCliente.setForeground(new Color(255, 255, 255));
        lblCliente.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCliente.setText("Cliente[F1]:");
        lblCliente.setHorizontalTextPosition(SwingConstants.RIGHT);

        txtAlmacen.setBackground(new Color(55, 55, 255));
        txtAlmacen.setEditable(false);
        txtAlmacen.setFont(new Font("Arial", 0, 12));
        txtAlmacen.setForeground(new Color(255, 255, 255));
        txtAlmacen.setBorder(null);
        txtAlmacen.setFocusable(false);

        txtCliente.setEditable(false);
        txtCliente.setFont(new Font("Arial", 0, 12));
        txtCliente.setNextFocusableComponent(txtTicket);

        jxdFecha.setEditable(false);
        jxdFecha.setFocusable(false);

        lblFecha.setFont(new Font("Arial", 1, 14));
        lblFecha.setForeground(new Color(255, 255, 255));
        lblFecha.setText("Fecha:");
        lblFecha.setHorizontalTextPosition(SwingConstants.RIGHT);

        tblDetalles.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Venta", "Articulo", "Descripcion", "Precio", "Cantidad", "Total"
            }
        ));
        tblDetalles.setFocusable(false);
        tblDetalles.setRequestFocusEnabled(false);
        tblDetalles.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(tblDetalles);

        lblSubtotal.setFont(new Font("Arial", 1, 14));
        lblSubtotal.setForeground(new Color(255, 255, 255));
        lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubtotal.setText("Subtotal:");
        lblSubtotal.setHorizontalTextPosition(SwingConstants.RIGHT);

        lblImpuestos.setFont(new Font("Arial", 1, 14));
        lblImpuestos.setForeground(new Color(255, 255, 255));
        lblImpuestos.setHorizontalAlignment(SwingConstants.RIGHT);
        lblImpuestos.setText("Impuestos:");
        lblImpuestos.setHorizontalTextPosition(SwingConstants.RIGHT);

        lblTotal.setFont(new Font("Arial", 1, 14));
        lblTotal.setForeground(new Color(255, 255, 255));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setText("Total:");
        lblTotal.setHorizontalTextPosition(SwingConstants.RIGHT);

        txtSubtotal.setBackground(new Color(55, 55, 255));
        txtSubtotal.setEditable(false);
        txtSubtotal.setFont(new Font("Arial", 0, 12));
        txtSubtotal.setForeground(new Color(255, 255, 255));
        txtSubtotal.setHorizontalAlignment(JTextField.RIGHT);
        txtSubtotal.setText("0");
        txtSubtotal.setBorder(null);
        txtSubtotal.setFocusable(false);

        txtImpuestos.setBackground(new Color(55, 55, 255));
        txtImpuestos.setEditable(false);
        txtImpuestos.setFont(new Font("Arial", 0, 12));
        txtImpuestos.setForeground(new Color(255, 255, 255));
        txtImpuestos.setHorizontalAlignment(JTextField.RIGHT);
        txtImpuestos.setText("0");
        txtImpuestos.setBorder(null);
        txtImpuestos.setFocusable(false);

        txtTotal.setBackground(new Color(55, 55, 255));
        txtTotal.setEditable(false);
        txtTotal.setFont(new Font("Arial", 0, 12));
        txtTotal.setForeground(new Color(255, 255, 255));
        txtTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtTotal.setText("0");
        txtTotal.setBorder(null);
        txtTotal.setFocusable(false);

        btnAgregar.setFont(new Font("Arial", 0, 14));
        btnAgregar.setIcon(new ImageIcon(getClass().getResource("/32x32/page_add.png"))); // NOI18N
        btnAgregar.setText("Agregar [F5]");
        btnAgregar.setEnabled(false);
        btnAgregar.setFocusable(false);
        btnAgregar.setNextFocusableComponent(btnBorrar);

        btnBorrar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnBorrar.setIcon(new ImageIcon(getClass().getResource("/32x32/page_remove.png"))); // NOI18N
        btnBorrar.setText("Borrar Venta");
        btnBorrar.setEnabled(false);
        btnBorrar.setNextFocusableComponent(btnCancelar);

        btnCancelar.setFont(new Font("Arial", 0, 14));
        btnCancelar.setIcon(new ImageIcon(getClass().getResource("/32x32/remove.png"))); // NOI18N
        btnCancelar.setText("Cancelar factura [F4]");
        btnCancelar.setNextFocusableComponent(btnImprimir);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblExpidio.setFont(new Font("Arial", 1, 14));
        lblExpidio.setForeground(new Color(255, 255, 255));
        lblExpidio.setHorizontalAlignment(SwingConstants.RIGHT);
        lblExpidio.setText("U. Expidió:");
        lblExpidio.setHorizontalTextPosition(SwingConstants.RIGHT);

        lblCancelo.setFont(new Font("Arial", 1, 14));
        lblCancelo.setForeground(new Color(255, 255, 255));
        lblCancelo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCancelo.setText("U. Canceló:");
        lblCancelo.setHorizontalTextPosition(SwingConstants.RIGHT);

        txtExpidio.setBackground(new Color(55, 55, 255));
        txtExpidio.setEditable(false);
        txtExpidio.setFont(new Font("Arial", 0, 12));
        txtExpidio.setForeground(new Color(255, 255, 255));
        txtExpidio.setBorder(null);
        txtExpidio.setFocusable(false);

        txtCancelo.setBackground(new Color(55, 55, 255));
        txtCancelo.setEditable(false);
        txtCancelo.setFont(new Font("Arial", 0, 12));
        txtCancelo.setForeground(new Color(255, 255, 255));
        txtCancelo.setBorder(null);
        txtCancelo.setFocusable(false);

        btnImprimir.setFont(new Font("Arial", 0, 14));
        btnImprimir.setIcon(new ImageIcon(getClass().getResource("/32x32/printer.png"))); // NOI18N
        btnImprimir.setText("Guardar e Imprimir [F8]");
        btnImprimir.setNextFocusableComponent(btnCerrar);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        txtTicket.setEditable(false);
        txtTicket.setFont(new Font("Arial", 0, 12));
        txtTicket.setNextFocusableComponent(btnAgregar);

        lblTotal1.setFont(new Font("Arial", 1, 14));
        lblTotal1.setForeground(new Color(255, 255, 255));
        lblTotal1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal1.setText("ID Venta:");
        lblTotal1.setHorizontalTextPosition(SwingConstants.RIGHT);

        txtCliente1.setBackground(new Color(55, 55, 255));
        txtCliente1.setEditable(false);
        txtCliente1.setFont(new Font("Arial", 0, 12));
        txtCliente1.setForeground(new Color(255, 255, 255));
        txtCliente1.setBorder(null);
        txtCliente1.setFocusable(false);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(lblSubtotal, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                                            .addComponent(lblImpuestos, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                                            .addComponent(lblTotal, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                                            .addComponent(lblCliente, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblAlmacen, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)))
                                    .addComponent(lblTotal1, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtTicket, GroupLayout.Alignment.LEADING)
                                                .addComponent(txtTotal, GroupLayout.Alignment.LEADING)
                                                .addComponent(txtImpuestos, GroupLayout.Alignment.LEADING)
                                                .addComponent(txtSubtotal, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                            .addComponent(btnAgregar, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtAlmacen, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblExpidio, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtCliente, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                                .addGap(14, 14, 14)
                                                .addComponent(txtCliente1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblCancelo, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCancelo, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                            .addComponent(txtExpidio, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jxdFecha, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(57, 57, 57)
                                                .addComponent(lblFecha))))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblIdFactura, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCerrar, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))))
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(btnBorrar, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImprimir, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(lblTitulo)
                        .addComponent(lblIdFactura, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAlmacen)
                            .addComponent(txtAlmacen, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(lblExpidio)
                            .addComponent(txtExpidio, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblFecha))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCliente1, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addComponent(txtCliente, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCancelo, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCliente)
                        .addComponent(jxdFecha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCancelo)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSubtotal)
                            .addComponent(txtSubtotal, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(lblImpuestos)
                            .addComponent(txtImpuestos, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotal)
                            .addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTicket, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotal1))
                        .addGap(44, 44, 44)
                        .addComponent(btnAgregar))
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimir, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorrar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCerrarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if(JOptionPane.showConfirmDialog(null, "¿Realmente desea cancelar esta factura : ?", "seguro...", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
        omoikane.principal.Facturas.cancelarFacturaDesdeDetalles(this);}
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImprimirActionPerformed

    @Override
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
      g2d.setColor(new Color(55,55,255,165));
      g2d.fillRect(0,0,areaDibujo.width,areaDibujo.height);
      fondo = tmp;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAgregar;
    private JButton btnBorrar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    private JButton btnImprimir;
    private JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXDatePicker jxdFecha;
    private JLabel lblAlmacen;
    private JLabel lblCancelo;
    private JLabel lblCliente;
    private JLabel lblExpidio;
    private JLabel lblFecha;
    public JLabel lblIdFactura;
    private JLabel lblImpuestos;
    private JLabel lblSubtotal;
    private JLabel lblTitulo;
    private JLabel lblTotal;
    private JLabel lblTotal1;
    private JTable tblDetalles;
    private JTextField txtAlmacen;
    private JTextField txtCancelo;
    private JTextField txtCliente;
    private JTextField txtCliente1;
    private JTextField txtExpidio;
    private JTextField txtImpuestos;
    private JTextField txtSubtotal;
    private JTextField txtTicket;
    private JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    public void setEditable(boolean editable)
    {
        this.txtCliente.setEditable(editable);
        this.txtTicket.setEditable(editable);
        this.btnAgregar.setEnabled(editable);
        this.btnBorrar.setEnabled(editable);
        this.btnCancelar.setEnabled(!editable);

    }

    public JTextField   getCampoID()                {return txtCliente;}
    public void setTxtClienteDes(String ID){txtCliente1.setText(ID);}
    public void setCliente(String ID){txtCliente.setText(ID);}
}

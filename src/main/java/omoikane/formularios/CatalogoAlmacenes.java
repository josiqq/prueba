/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CatalogoAlmacenes.java
 *
 * Created on 11/10/2008, 07:56:04 PM
 */

package omoikane.formularios;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import omoikane.sistema.*;


/**
 * @author Adan
 */

public class CatalogoAlmacenes extends JInternalFrame {

    BufferedImage     fondo;
    TimerBusqueda     timerBusqueda;
    public    int ID;
    public boolean modal = false;

    class TimerBusqueda extends Thread
    {
        CatalogoAlmacenes ca;
        boolean busquedaActiva = true;

        TimerBusqueda(CatalogoAlmacenes ca) { this.ca = ca; }
        public void run()
        {
            synchronized(this)
            {
                busquedaActiva = true;
                try { this.wait(600); } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el timer de búsqueda automática", Herramientas.getStackTraceString(e)); }
                if(busquedaActiva) { ca.buscar(); }
            }
        }
        void cancelar()
        {
            busquedaActiva = false;
            try { this.notify(); } catch(Exception e) {}
        }
    }

        public void actionPerformed(ActionEvent ae)
    {System.out.println("acción");}

    /** Creates new form CatalogoAlmacenes */
    public CatalogoAlmacenes() {
        initComponents();
        this.setOpaque(false);
        ((JPanel)this.getContentPane()).setOpaque(false);
        this.getLayeredPane().setOpaque(false);
        this.getRootPane().setOpaque(false);
        this.generarFondo(this);
        this.btnAceptar.setVisible(false);
        Herramientas.centrarVentana(this);
    }

    public void setModoDialogo()
    {
        modal=true;
        this.btnAceptar.setVisible(true);
        Action aceptar = new AbstractAction() { public void actionPerformed(ActionEvent e) {
            ((CatalogoAlmacenes)e.getSource()).btnAceptar.doClick();
        } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "aceptar");
        getActionMap().put("aceptar"                 , aceptar  );
    }
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBusqueda = new JTextField();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jScrollPane1 = new JScrollPane();
        tablaAlmacenes = new JTable();
        btnAceptar = new JButton();
        btnDetalles = new JButton();
        btnNuevo = new JButton();
        btnModificar = new JButton();
        btnEliminar = new JButton();
        btnImprimir = new JButton();
        btnCerrar = new JButton();

        setTitle("Catálogo de Almacenes");
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        txtBusqueda.setFont(new Font("Arial", 0, 14)); // NOI18N
        txtBusqueda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                txtBusquedaKeyPressed(evt);
            }
            public void keyTyped(KeyEvent evt) {
                txtBusquedaKeyTyped(evt);
            }
        });

        jLabel1.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setText("Buscar :");

        jLabel2.setFont(new Font("Arial", 1, 52)); // NOI18N
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("<html><head><style type='text/css'>body { font-family: 'Roboto Thin'; font-size: 36px; }</style></head>\n<body>\nCatálogo de Almacenes\n</body></html>");

        tablaAlmacenes.setFont(new Font("Arial", 0, 14)); // NOI18N
        tablaAlmacenes.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Almacen", "Descripcion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAlmacenes.setFocusable(false);
        tablaAlmacenes.setShowHorizontalLines(false);
        jScrollPane1.setViewportView(tablaAlmacenes);

        btnAceptar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnAceptar.setIcon(new ImageIcon(getClass().getResource("/32x32/accept.png"))); // NOI18N
        btnAceptar.setText("Aceptar [Enter]");

        btnDetalles.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnDetalles.setIcon(new ImageIcon(getClass().getResource("/32x32/page_search.png"))); // NOI18N
        btnDetalles.setText("Detalles [F4]");
        btnDetalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });

        btnNuevo.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnNuevo.setIcon(new ImageIcon(getClass().getResource("/32x32/page_add.png"))); // NOI18N
        btnNuevo.setText("Nuevo [F5]");
        btnNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnModificar.setIcon(new ImageIcon(getClass().getResource("/32x32/blog_post_edit.png"))); // NOI18N
        btnModificar.setText("Modificar [F6]");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/32x32/page_remove.png"))); // NOI18N
        btnEliminar.setText("Eliminar [Supr]");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnImprimir.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnImprimir.setIcon(new ImageIcon(getClass().getResource("/32x32/printer.png"))); // NOI18N
        btnImprimir.setText("Imprimir [F7]");
        btnImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnCerrar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnCerrar.setIcon(new ImageIcon(getClass().getResource("/48x48/back.png"))); // NOI18N
        btnCerrar.setText("Cerrar [Esc]");
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusqueda, GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDetalles)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImprimir, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
                        .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBusqueda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnDetalles, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyTyped
        // TODO add your handling code here:
        preBuscar();
    }//GEN-LAST:event_txtBusquedaKeyTyped

    private void txtBusquedaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyPressed
        // TODO add your handling code here:
           if(evt.getKeyCode() == evt.VK_DOWN)
        {
            int sigFila = tablaAlmacenes.getSelectedRow()+1;
            if(sigFila < tablaAlmacenes.getRowCount())
            {
                this.tablaAlmacenes.setRowSelectionInterval(sigFila, sigFila);
                this.tablaAlmacenes.scrollRectToVisible(tablaAlmacenes.getCellRect(sigFila, 1, true));
            }
        }
        if(evt.getKeyCode() == evt.VK_UP)
        {
            int antFila = tablaAlmacenes.getSelectedRow()-1;
            if(antFila >= 0) {
                this.tablaAlmacenes.setRowSelectionInterval(antFila, antFila);
                this.tablaAlmacenes.scrollRectToVisible(tablaAlmacenes.getCellRect(antFila, 1, true));
            }
        }
        if(evt.getKeyCode() == evt.VK_PAGE_DOWN)
        {
            int nFilas  = (int) this.jScrollPane1.getViewportBorderBounds().getHeight() / tablaAlmacenes.getRowHeight();
            int sigFila = tablaAlmacenes.getSelectedRow()+nFilas;
            if(sigFila > tablaAlmacenes.getRowCount()) {
                sigFila = tablaAlmacenes.getRowCount()-1;
            }
            if(sigFila < tablaAlmacenes.getRowCount()) {
                this.tablaAlmacenes.setRowSelectionInterval(sigFila, sigFila);
                this.tablaAlmacenes.scrollRectToVisible(tablaAlmacenes.getCellRect(sigFila, 1, true));
            }
        }
        if(evt.getKeyCode() == evt.VK_PAGE_UP)
        {
            int nFilas  = (int) this.jScrollPane1.getViewportBorderBounds().getHeight() / tablaAlmacenes.getRowHeight();
            int antFila = tablaAlmacenes.getSelectedRow()-nFilas;
            if(antFila < 0) {
                antFila = 0;
            }
            this.tablaAlmacenes.setRowSelectionInterval(antFila, antFila);
            this.tablaAlmacenes.scrollRectToVisible(tablaAlmacenes.getCellRect(antFila, 1, true));
        }

        if(evt.getKeyCode() == evt.VK_DELETE)
        {this.btnEliminar.doClick();}

    }//GEN-LAST:event_txtBusquedaKeyPressed

    private void btnDetallesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
        // TODO add your handling code here:
        int sel = this.tablaAlmacenes.getSelectedRow();
        int id;
        if(sel == -1) {
            Dialogos.lanzarAlerta("Ninguna fila ha sido seleccionada.");
        } else {
            id = (Integer)this.tablaAlmacenes.getValueAt(sel, 0);
            //Lanzar ventana y agregarle un listener
            JInternalFrame wnd = (JInternalFrame) omoikane.principal.Almacenes.lanzarDetallesAlmacen(id);
            wnd.addInternalFrameListener(iframeAdapter);
        }
}//GEN-LAST:event_btnDetallesActionPerformed

    private void btnNuevoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        JInternalFrame wnd = (JInternalFrame) omoikane.principal.Almacenes.lanzarFormNuevoAlmacen();
        wnd.addInternalFrameListener(iframeAdapter);
}//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        int sel = this.tablaAlmacenes.getSelectedRow();
        int id;

        if(sel == -1) {
            Dialogos.lanzarAlerta("Ninguna fila ha sido seleccionada.");
        } else {
            id = (Integer)this.tablaAlmacenes.getValueAt(sel, 0);
            //Lanzar ventana y agregarle un listener
            JInternalFrame wnd = (JInternalFrame) omoikane.principal.Almacenes.lanzarModificarAlmacen(id);
            wnd.addInternalFrameListener(iframeAdapter);
        }
}//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int sel = this.tablaAlmacenes.getSelectedRow();
        int id;

        if(sel == -1) {
            Dialogos.lanzarAlerta("Ninguna fila ha sido seleccionada.");
        } else {
            id = (Integer)this.tablaAlmacenes.getValueAt(sel, 0);
            String descripcion = String.valueOf(((DefaultTableModel)tablaAlmacenes.getModel()).getValueAt(tablaAlmacenes.getSelectedRow(),1));
            if(JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar éste Almacen : \""+descripcion+"\"?", "seguro...", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                omoikane.principal.Almacenes.eliminarAlmacen(id);
                resetTable();
            }
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void btnImprimirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Almacenes.lanzarImprimir();
}//GEN-LAST:event_btnImprimirActionPerformed

    private void btnCerrarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        if(!modal){
        ((JInternalFrame)((omoikane.principal.MenuPrincipal)omoikane.principal.Principal.getMenuPrincipal()).getMenuPrincipal()).requestFocusInWindow();
        }
}//GEN-LAST:event_btnCerrarActionPerformed

    private void formFocusGained(FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
        this.txtBusqueda.requestFocusInWindow();
    }//GEN-LAST:event_formFocusGained



    public void preBuscar()
    {
        if(timerBusqueda != null && timerBusqueda.isAlive()) { timerBusqueda.cancelar(); }
        this.timerBusqueda = new TimerBusqueda(this);
        timerBusqueda.start();
    }

    public void buscar()
    {resetTable();}

    public JTable getTablaAlmacenes()
    {return tablaAlmacenes;}

    public void resetTable()
    {
        String[] columnas = {"ID Almacen", "Descripcion"};
        this.tablaAlmacenes.setModel(new DefaultTableModel());
        ((DefaultTableModel)this.tablaAlmacenes.getModel()).setColumnIdentifiers(columnas);
        omoikane.principal.Almacenes.poblarAlmacenes(this.tablaAlmacenes,txtBusqueda.getText());
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
    public JButton btnAceptar;
    private JButton btnCerrar;
    private JButton btnDetalles;
    private JButton btnEliminar;
    private JButton btnImprimir;
    private JButton btnModificar;
    private JButton btnNuevo;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    public JTable tablaAlmacenes;
    public JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables

    public InternalFrameAdapter iframeAdapter = new InternalFrameAdapter()
    {public void internalFrameClosed(InternalFrameEvent e) { resetTable();requestFocusInWindow(); }};
}

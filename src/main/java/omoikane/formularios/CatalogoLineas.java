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
import java.awt.event.*;
import java.awt.image.*;
import java.awt.*;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;
import omoikane.sistema.*;

/**
 *
 * @author Adan
 */

public class CatalogoLineas extends JInternalFrame {

        BufferedImage     fondo;
        TimerBusqueda     timerBusqueda;
        public boolean modal = false;

    class TimerBusqueda extends Thread
    {
        CatalogoLineas ca;
        boolean busquedaActiva = true;

        TimerBusqueda(CatalogoLineas ca) { this.ca = ca; }
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
    {
        System.out.println("acción");
    }

    /** Creates new form CatalogoAlmacenes */
    public CatalogoLineas() {
        initComponents();
        programarShortcuts();

        this.setOpaque(false);
        ((JPanel)this.getContentPane()).setOpaque(false);
        this.getLayeredPane().setOpaque(false);
        this.getRootPane().setOpaque(false);
        this.generarFondo(this);

        this.btnAceptar.setVisible(false);

        Herramientas.centrarVentana(this);

        //Instrucciones para el funcionamiento de las teclas de navegación
        Set newKeys = new HashSet(getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, newKeys);

        newKeys = new HashSet(getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeys);
   }

    public void setModoDialogo()
    {
        modal=true;
        this.btnAceptar.setVisible(true);
        Action aceptar = new AbstractAction() { public void actionPerformed(ActionEvent e) {
            ((CatalogoLineas)e.getSource()).btnAceptar.doClick();
        } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "aceptar");
        getActionMap().put("aceptar"                 , aceptar  );
    }

    public void programarShortcuts() {
        /*
            //xHerramientas.In2ActionX(cat, KeyEvent.VK_ESCAPE, "cerrar"   ) { cat.btnCerrar.doClick()   }
            //xHerramientas.In2ActionX(cat, KeyEvent.VK_DELETE, "eliminar" ) { cat.btnEliminas.doClick() }
            //xHerramientas.In2ActionX(cat, KeyEvent.VK_F4    , "detalles" ) { cat.btnDetalles.doClick() }
            //xHerramientas.In2ActionX(cat, KeyEvent.VK_F5    , "nuevo"    ) { cat.btnNuevo.doClick() }
            //xHerramientas.In2ActionX(cat, KeyEvent.VK_F6    , "modificar") { cat.btnModificar.doClick() }
            //xHerramientas.In2ActionX(cat, KeyEvent.VK_F7    , "imprimir" ) { cat.btnImprimir.doClick() }
         */

        Action cerrar = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnCerrar.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrar");
        getActionMap().put("cerrar"                 , cerrar  );

        Action buscar = new AbstractAction() { public void actionPerformed(ActionEvent e) { txtBusqueda.requestFocusInWindow(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "buscar");
        getActionMap().put("buscar"                 , buscar  );

        Action detalles = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnDetalles.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "detalles");
        getActionMap().put("detalles"                 , detalles  );

        Action nuevo = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnNuevo.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "nuevo");
        getActionMap().put("nuevo"                 , nuevo  );

        Action modificar = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnModificar.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "modificar");
        getActionMap().put("modificar"                 , modificar  );

        Action eliminar = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnEliminar.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "eliminar");
        getActionMap().put("eliminar"                 , eliminar  );

        Action imprimir = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnImprimir.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "imprimir");
        getActionMap().put("imprimir"                 , imprimir  );
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
        tablaLineas = new JTable();
        jButton1 = new JButton();
        btnAceptar = new JButton();
        btnDetalles = new JButton();
        btnNuevo = new JButton();
        btnModificar = new JButton();
        btnEliminar = new JButton();
        btnImprimir = new JButton();
        btnCerrar = new JButton();
        jProgressBar1 = new JProgressBar();

        setTitle("Catálogo de Lineas");

        txtBusqueda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                txtBusquedaActionPerformed(evt);
            }
        });
        txtBusqueda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                txtBusquedaKeyPressed(evt);
            }
            public void keyTyped(KeyEvent evt) {
                txtBusquedaKeyTyped(evt);
            }
        });

        jLabel1.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setText("Buscar [F3]:");

        jLabel2.setFont(new Font("Arial", 1, 36)); // NOI18N
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("<html><head><style type='text/css'>body { font-family: 'Roboto Thin'; font-size: 36px; }</style></head>\n<body>\nCatálogo de Lineas\n</body></html>");

        tablaLineas.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Linea", "Descripcion", "Descuento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaLineas.setShowHorizontalLines(false);
        jScrollPane1.setViewportView(tablaLineas);

        jButton1.setText("Buscar");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnAceptar.setIcon(new ImageIcon(getClass().getResource("/32x32/accept.png"))); // NOI18N
        btnAceptar.setText("Aceptar [Enter]");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnDetalles.setIcon(new ImageIcon(getClass().getResource("/32x32/page_search.png"))); // NOI18N
        btnDetalles.setText("Detalles [F4]");
        btnDetalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new ImageIcon(getClass().getResource("/32x32/page_add.png"))); // NOI18N
        btnNuevo.setText("Nuevo [F5]");
        btnNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new ImageIcon(getClass().getResource("/32x32/blog_post_edit.png"))); // NOI18N
        btnModificar.setText("Modificar [F6]");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/32x32/page_remove.png"))); // NOI18N
        btnEliminar.setText("Eliminar [Supr]");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new ImageIcon(getClass().getResource("/32x32/printer.png"))); // NOI18N
        btnImprimir.setText("Imprimir [F7]");
        btnImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new ImageIcon(getClass().getResource("/48x48/back.png"))); // NOI18N
        btnCerrar.setText("Cerrar[ESC]");
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusqueda, GroupLayout.PREFERRED_SIZE, 658, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
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
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCerrar, 0, 0, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(txtBusqueda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDetalles, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed
        // TODO add your handling code here:       
    }//GEN-LAST:event_txtBusquedaActionPerformed

    private void jButton1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        resetTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtBusquedaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyTyped
        // TODO add your handling code here:
        preBuscar();
    }//GEN-LAST:event_txtBusquedaKeyTyped

    private void btnAceptarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_btnAceptarActionPerformed

    private void txtBusquedaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == evt.VK_DOWN)
        {
            int sigFila = tablaLineas.getSelectedRow()+1;
            if(sigFila < tablaLineas.getRowCount())
            {
                this.tablaLineas.setRowSelectionInterval(sigFila, sigFila);
                this.tablaLineas.scrollRectToVisible(tablaLineas.getCellRect(sigFila, 1, true));
            }
        }
        if(evt.getKeyCode() == evt.VK_UP)
        {
            int antFila = tablaLineas.getSelectedRow()-1;
            if(antFila >= 0) {
                this.tablaLineas.setRowSelectionInterval(antFila, antFila);
                this.tablaLineas.scrollRectToVisible(tablaLineas.getCellRect(antFila, 1, true));
            }
        }
        if(evt.getKeyCode() == evt.VK_PAGE_DOWN)
        {
            int nFilas  = (int) this.jScrollPane1.getViewportBorderBounds().getHeight() / tablaLineas.getRowHeight();
            int sigFila = tablaLineas.getSelectedRow()+nFilas;
            if(sigFila > tablaLineas.getRowCount()) {
                sigFila = tablaLineas.getRowCount()-1;
            }
            if(sigFila < tablaLineas.getRowCount()) {
                this.tablaLineas.setRowSelectionInterval(sigFila, sigFila);
                this.tablaLineas.scrollRectToVisible(tablaLineas.getCellRect(sigFila, 1, true));
            }
        }
        if(evt.getKeyCode() == evt.VK_PAGE_UP)
        {
            int nFilas  = (int) this.jScrollPane1.getViewportBorderBounds().getHeight() / tablaLineas.getRowHeight();
            int antFila = tablaLineas.getSelectedRow()-nFilas;
            if(antFila < 0) {
                antFila = 0;
            }
            this.tablaLineas.setRowSelectionInterval(antFila, antFila);
            this.tablaLineas.scrollRectToVisible(tablaLineas.getCellRect(antFila, 1, true));
        }
        if(evt.getKeyCode() == evt.VK_DELETE)
        {
            this.btnEliminar.doClick();
        }

    }//GEN-LAST:event_txtBusquedaKeyPressed

    private void btnDetallesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
        // TODO add your handling code here:
        int sel = this.tablaLineas.getSelectedRow();
        int id;
        if(sel == -1)
        {
            Dialogos.lanzarAlerta("Ninguna fila ha sido seleccionada.");
        } else {
            id = (Integer)this.tablaLineas.getValueAt(sel, 0);
            //Lanzar ventana y agregarle un listener
            omoikane.principal.Lineas.lanzarDetallesLinea(id);
               }
}//GEN-LAST:event_btnDetallesActionPerformed

    private void btnNuevoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
         JInternalFrame wnd = (JInternalFrame) omoikane.principal.Lineas.lanzarFormNuevoLinea();
        wnd.addInternalFrameListener(iframeAdapter);
}//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
                 int sel = this.tablaLineas.getSelectedRow();
        int id;

        if(sel == -1)
        {
            Dialogos.lanzarAlerta("Ninguna fila ha sido seleccionada.");
        } else {
            id = (Integer)this.tablaLineas.getValueAt(sel, 0);
            //Lanzar ventana y agregarle un listener
            JInternalFrame wnd = (JInternalFrame) omoikane.principal.Lineas.lanzarModificarLinea(id);
            wnd.addInternalFrameListener(iframeAdapter);
        }
}//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int sel = this.tablaLineas.getSelectedRow();
        int id;

        if(sel == -1)
        {
            Dialogos.lanzarAlerta("Ninguna fila ha sido seleccionada.");
        }
        else {
            id = (Integer)this.tablaLineas.getValueAt(sel, 0);
            String descripcion = String.valueOf(((DefaultTableModel)tablaLineas.getModel()).getValueAt(tablaLineas.getSelectedRow(),1));
            if(JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar éste linea: \""+descripcion+"\"?", "lalajiji", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            omoikane.principal.Lineas.eliminarLinea(id);
            resetTable();
            }
             }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void btnImprimirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Lineas.lanzarImprimir();
}//GEN-LAST:event_btnImprimirActionPerformed

    private void btnCerrarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        if(!modal){
        ((JInternalFrame)((omoikane.principal.MenuPrincipal)omoikane.principal.Principal.getMenuPrincipal()).getMenuPrincipal()).requestFocusInWindow();
        }
}//GEN-LAST:event_btnCerrarActionPerformed

        public void preBuscar()
    {
        if(timerBusqueda != null && timerBusqueda.isAlive()) { timerBusqueda.cancelar(); }
        this.timerBusqueda = new TimerBusqueda(this);
        timerBusqueda.start();

    }

    public void buscar()
    {
       resetTable();
    }

    public JTable getTablaLineas()
    {
        return tablaLineas;
    }

    public void resetTable()
    {
        String[] columnas = {"ID Linea", "Descripcion","Descuento"};
        this.tablaLineas.setModel(new DefaultTableModel());
        ((DefaultTableModel)this.tablaLineas.getModel()).setColumnIdentifiers(columnas);
        omoikane.principal.Lineas.poblarLineas(this.tablaLineas,txtBusqueda.getText());
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
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JProgressBar jProgressBar1;
    private JScrollPane jScrollPane1;
    private JTable tablaLineas;
    private JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables

    public InternalFrameAdapter iframeAdapter = new InternalFrameAdapter()
    {
        public void internalFrameClosed(InternalFrameEvent e) { resetTable(); }

    };
}

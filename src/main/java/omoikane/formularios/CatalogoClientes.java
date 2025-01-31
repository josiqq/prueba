 /* Author Phesus        //////////////////////////////
 *  ORC,ACR             /////////////
 *                     /////////////
 *                    /////////////
 *                   /////////////
 * //////////////////////////////                   */

package omoikane.formularios;

import java.util.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import omoikane.sistema.*;
import javax.swing.event.*;

public class CatalogoClientes extends JInternalFrame {

    TimerBusqueda                       timerBusqueda;
    BufferedImage                       fondo;
    NadesicoTableModel modelo;
    public String                       txtQuery;
    public String                       codigoSeleccionado;
    public int                          IDSeleccionado;
    public int                          IDAlmacen           = omoikane.principal.Principal.IDAlmacen;
    public boolean                      modal               = false;

    //funcion de busqueda automatica

    class TimerBusqueda extends Thread
    {
        CatalogoClientes    ca;
        boolean             busquedaActiva = true;
        TimerBusqueda(CatalogoClientes ca) { this.ca = ca; }
        public void run(){
            synchronized(this)
            {
                busquedaActiva = true;
                try { this.wait(1000); } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el timer de búsqueda automática", Herramientas.getStackTraceString(e)); }
                if(busquedaActiva) { ca.buscar(); }
            }
        }
        void cancelar(){busquedaActiva = false;try { this.notify(); } catch(Exception e) {}}
    }

    /** Creates new form CatalogoArticulos */
    public CatalogoClientes() {
        //Conectar a MySQL

        initComponents();
        jProgressBar1.setIndeterminate(true);
        programarShortcuts();

        String[]  columnas = {"RFC", "Razon Social", "Direccion", "Telefono", "Descuento", "Saldo"};
        ArrayList cols     = new ArrayList<String>(Arrays.asList(columnas));
        Class[]   clases   = {String.class, String.class, String.class, String.class, Double.class, Double.class};
        ArrayList cls      = new ArrayList<Class>(Arrays.asList(clases));

        NadesicoTableModel modeloTabla = new NadesicoTableModel(cols, cls);
        //jTable1.enableInputMethods(false);
        modelo = modeloTabla;
        jTable1.setModel(modeloTabla);

        setQueryTable("SELECT id_cliente,RFC,razonSocial,direccion,telefono,descuento,saldo FROM clientes");
        jProgressBar1.setIndeterminate(false);

        //Instrucciones para el funcionamiento del fondo semistransparente
        this.setOpaque(false);
        ((JPanel)this.getContentPane()).setOpaque(false);
        this.getLayeredPane().setOpaque(false);
        this.getRootPane().setOpaque(false);
        this.generarFondo(this);

        Herramientas.centrarVentana(this);
        this.btnAceptar.setVisible(false);

        //Instrucciones para el funcionamiento de las teclas de navegación
        Set newKeys = new HashSet(getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, newKeys);

        newKeys = new HashSet(getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeys);

    }
    public void programarShortcuts() {
        /*
            Herramientas.In2ActionX(cat, KeyEvent.VK_F3     , "buscar"   ) { cat.txtBusqueda.requestFocusInWindow() }
            Herramientas.In2ActionX(cat, KeyEvent.VK_F4     , "detalles" ) { cat.btnDetalles.doClick() }
            Herramientas.In2ActionX(cat, KeyEvent.VK_F5     , "nuevo"    ) { cat.btnNuevo.doClick()    }
            Herramientas.In2ActionX(cat, KeyEvent.VK_F6     , "modificar") { cat.btnModificar.doClick()}
            Herramientas.In2ActionX(cat, KeyEvent.VK_F8     , "imprimir" ) { cat.btnImprimir.doClick()}
            Herramientas.In2ActionX(cat, KeyEvent.VK_DELETE , "eliminar" ) { cat.btnEliminar.doClick() }
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
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "imprimir");
        getActionMap().put("imprimir"                 , imprimir  );
    }


    public void setModoDialogo()
    {
        modal=true;
        this.btnAceptar.setVisible(true);
        Action aceptar = new AbstractAction() { public void actionPerformed(ActionEvent e) {
            ((CatalogoClientes)e.getSource()).btnAceptar.doClick();
        } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "aceptar");
        getActionMap().put("aceptar"                 , aceptar  );
    }

    public void setQueryTable(String query) {
        txtQuery = query;
        if(modelo != null) {
            modelo.setQuery(query);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        txtBusqueda = new JTextField();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        btnCerrar = new JButton();
        btnEliminar = new JButton();
        btnModificar = new JButton();
        btnNuevo = new JButton();
        btnDetalles = new JButton();
        btnImprimir = new JButton();
        btnAceptar = new JButton();
        jProgressBar1 = new JProgressBar();

        setIconifiable(true);
        setTitle("Catálogo de clientes");
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        jScrollPane1.setAutoscrolls(true);

        jTable1.setFont(new Font("Tahoma", 0, 17)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RFC", "Razón Social", "Dirección", "Teléfono", "Descuento", "Saldo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setRowHeight(20);
        jTable1.setShowHorizontalLines(false);
        jScrollPane1.setViewportView(jTable1);

        txtBusqueda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                txtBusquedaKeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                salir(evt);
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
        jLabel2.setText("<html><head><style type='text/css'>body { font-family: 'Roboto Thin'; font-size: 28px; }</style></head>\n<body>\nCatálogo de Clientes\n</body></html>");

        btnCerrar.setFont(new Font("Arial", 0, 12)); // NOI18N
        btnCerrar.setIcon(new ImageIcon(getClass().getResource("/64x64/back.png"))); // NOI18N
        btnCerrar.setText("Cerrar [Esc]");
        btnCerrar.setRequestFocusEnabled(false);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        btnCerrar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                salir(evt);
            }
        });

        btnEliminar.setFont(new Font("Arial", 0, 12)); // NOI18N
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/32x32/page_remove.png"))); // NOI18N
        btnEliminar.setText("Eliminar [Supr]");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        btnEliminar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                salir(evt);
            }
        });

        btnModificar.setFont(new Font("Arial", 0, 12)); // NOI18N
        btnModificar.setIcon(new ImageIcon(getClass().getResource("/32x32/page_edit.png"))); // NOI18N
        btnModificar.setText("Modificar [F6]");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        btnModificar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                salir(evt);
            }
        });

        btnNuevo.setFont(new Font("Arial", 0, 12)); // NOI18N
        btnNuevo.setIcon(new ImageIcon(getClass().getResource("/32x32/page_add.png"))); // NOI18N
        btnNuevo.setText("Nuevo [F5]");
        btnNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        btnNuevo.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                salir(evt);
            }
        });

        btnDetalles.setFont(new Font("Arial", 0, 12)); // NOI18N
        btnDetalles.setIcon(new ImageIcon(getClass().getResource("/32x32/page_search.png"))); // NOI18N
        btnDetalles.setText("Detalles [F4]");
        btnDetalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });
        btnDetalles.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                salir(evt);
            }
        });

        btnImprimir.setFont(new Font("Arial", 0, 12)); // NOI18N
        btnImprimir.setIcon(new ImageIcon(getClass().getResource("/32x32/printer.png"))); // NOI18N
        btnImprimir.setText("<html><center>Imprimir [F8]</center></html>");
        btnImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        btnImprimir.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                salir(evt);
            }
        });

        btnAceptar.setFont(new Font("Arial", 0, 12)); // NOI18N
        btnAceptar.setIcon(new ImageIcon(getClass().getResource("/32x32/accept.png"))); // NOI18N
        btnAceptar.setText("Aceptar [Enter]");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        btnAceptar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                salir(evt);
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
                    .addComponent(jProgressBar1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDetalles)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevo)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImprimir))
                    .addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(txtBusqueda, GroupLayout.PREFERRED_SIZE, 841, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCerrar, 0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBusqueda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDetalles)
                    .addComponent(btnNuevo)
                    .addComponent(btnEliminar)
                    .addComponent(btnAceptar)
                    .addComponent(btnImprimir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyTyped
        // TODO add your handling code here:
        preBuscar();
    }//GEN-LAST:event_txtBusquedaKeyTyped

    private void btnCerrarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new Thread() {
            public void run() {
                NadesicoTableModel tabModelo = modelo;
                modelo = null;
                tabModelo.destroy();
                dispose();
            }
        }.start();
        if(!modal){
        ((JInternalFrame)((omoikane.principal.MenuPrincipal)omoikane.principal.Principal.getMenuPrincipal()).getMenuPrincipal()).requestFocusInWindow();
        }
}//GEN-LAST:event_btnCerrarActionPerformed

    private void btnEliminarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int IDCliente = ((NadesicoTableModel)jTable1.getModel()).getIDArticuloFila(this.jTable1.getSelectedRow());
        if(IDCliente != -1) {
            String descripcion = ((NadesicoTableModel)jTable1.getModel()).getDescripcion(jTable1.getSelectedRow());
            if(JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar éste Cliente: \""+descripcion+"\"?", "lala", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                omoikane.principal.Clientes.eliminarCliente(IDCliente);
            }
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        int IDCliente = ((NadesicoTableModel)jTable1.getModel()).getIDArticuloFila(this.jTable1.getSelectedRow());
        //Lanzar la ventana de detalles:
        if(IDCliente != -1) { 
            JInternalFrame wnd = (JInternalFrame) omoikane.principal.Clientes.lanzarModificarCliente(IDCliente);
            wnd.addInternalFrameListener(iframeAdapter);
        }
}//GEN-LAST:event_btnModificarActionPerformed

    private void btnNuevoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        JInternalFrame wnd = (JInternalFrame) omoikane.principal.Clientes.lanzarFormNuevoCliente();
        wnd.addInternalFrameListener(iframeAdapter);
}//GEN-LAST:event_btnNuevoActionPerformed

    private void btnDetallesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
        // TODO add your handling code here:
        int IDCliente = ((NadesicoTableModel)jTable1.getModel()).getIDArticuloFila(this.jTable1.getSelectedRow());
        
        //Lanzar la ventana de detalles:
        if(IDCliente != -1) { 
        JInternalFrame wnd = (JInternalFrame) omoikane.principal.Clientes.lanzarDetallesClientes(IDCliente);
        wnd.addInternalFrameListener(iframeAdapter);}
}//GEN-LAST:event_btnDetallesActionPerformed

    private void txtBusquedaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == evt.VK_ENTER) {
            this.txtBusqueda.selectAll();
        }
        if(evt.getKeyCode() == evt.VK_DOWN)
        {
            int sigFila = jTable1.getSelectedRow()+1;
            if(sigFila < jTable1.getRowCount())
            {
                this.jTable1.setRowSelectionInterval(sigFila, sigFila);
                this.jTable1.scrollRectToVisible(jTable1.getCellRect(sigFila, 1, true));
            }
        }
        if(evt.getKeyCode() == evt.VK_UP)
        {
            int antFila = jTable1.getSelectedRow()-1;
            if(antFila >= 0) {
                this.jTable1.setRowSelectionInterval(antFila, antFila);
                this.jTable1.scrollRectToVisible(jTable1.getCellRect(antFila, 1, true));
            }
        }
        if(evt.getKeyCode() == evt.VK_PAGE_DOWN)
        {
            int nFilas  = (int) this.jScrollPane1.getViewportBorderBounds().getHeight() / jTable1.getRowHeight();
            int sigFila = jTable1.getSelectedRow()+nFilas;
            if(sigFila > jTable1.getRowCount()) {
                sigFila = jTable1.getRowCount()-1;
            }
            if(sigFila < jTable1.getRowCount()) {
                this.jTable1.setRowSelectionInterval(sigFila, sigFila);
                this.jTable1.scrollRectToVisible(jTable1.getCellRect(sigFila, 1, true));
            }
        }
        if(evt.getKeyCode() == evt.VK_PAGE_UP)
        {
            int nFilas  = (int) this.jScrollPane1.getViewportBorderBounds().getHeight() / jTable1.getRowHeight();
            int antFila = jTable1.getSelectedRow()-nFilas;
            if(antFila < 0) {
                antFila = 0;
            }
            this.jTable1.setRowSelectionInterval(antFila, antFila);
            this.jTable1.scrollRectToVisible(jTable1.getCellRect(antFila, 1, true));
        }
        if(evt.getKeyCode() == evt.VK_DELETE)
        {
            this.btnEliminar.doClick();
        }
    }//GEN-LAST:event_txtBusquedaKeyPressed

    private void btnImprimirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Clientes.lanzarImprimir(txtQuery);
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnAceptarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        NadesicoTableModel stm = ((NadesicoTableModel)jTable1.getModel());
        int IDCliente = stm.getIDArticuloFila(this.jTable1.getSelectedRow());

        if(IDCliente != -1) {
            IDSeleccionado = IDCliente; codigoSeleccionado = (String)stm.getValueAt(this.jTable1.getSelectedRow(), 0);
            this.btnCerrar.doClick();
        }
}//GEN-LAST:event_btnAceptarActionPerformed

    private void formFocusGained(FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
        this.txtBusqueda.requestFocusInWindow();
    }//GEN-LAST:event_formFocusGained

    private void salir(KeyEvent evt) {//GEN-FIRST:event_salir
        // TODO add your handling code here:
        if(evt.getKeyCode() == evt.VK_ESCAPE) { this.btnCerrar.doClick(); }
    }//GEN-LAST:event_salir
    
    public void preBuscar()
    {
        if(timerBusqueda != null && timerBusqueda.isAlive()) { timerBusqueda.cancelar(); }
        this.timerBusqueda = new TimerBusqueda(this);
        timerBusqueda.start();
    }

    public void buscar()
    {
       String busqueda = this.txtBusqueda.getText();
       String query    = "select id_cliente,RFC,razonSocial,direccion,telefono,descuento,saldo FROM clientes WHERE razonSocial like '%"+busqueda+"%'";
       setQueryTable(query);
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
    private JButton btnAceptar;
    private JButton btnCerrar;
    private JButton btnDetalles;
    private JButton btnEliminar;
    private JButton btnImprimir;
    private JButton btnModificar;
    private JButton btnNuevo;
    private JLabel jLabel1;
    private JLabel jLabel2;
    public JProgressBar jProgressBar1;
    private JScrollPane jScrollPane1;
    public JTable jTable1;
    public JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables

    public InternalFrameAdapter iframeAdapter = new InternalFrameAdapter()
    {
        public void internalFrameClosed(InternalFrameEvent e) { buscar();requestFocusInWindow(); }
    };
}

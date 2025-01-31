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
import java.text.*;
import java.util.Calendar;

public class CatalogoVentas extends JInternalFrame {

    //TimerBusqueda          timerBusqueda;
    TimerBusqueda          timerBusqueda;
    public boolean modal = false;
    public int IDAlmacen = omoikane.principal.Principal.IDAlmacen;
    BufferedImage          fondo;
    public int             IDSeleccionado;
    public String          codigoSeleccionado;
    public String          txtQuery;
    VentasTableModel modelo;

    class TimerBusqueda extends Thread
    {
        CatalogoVentas ca;
        boolean busquedaActiva = true;

        TimerBusqueda(CatalogoVentas ca) { this.ca = ca; }
        public void run()
        {
            synchronized(this)
            {
                busquedaActiva = true;
                try { this.wait(500); } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el timer de búsqueda automática", Herramientas.getStackTraceString(e)); }
                if(busquedaActiva && ca.modelo != null) { ca.buscar(); }
            }
        }
        void cancelar()
        {
            busquedaActiva = false;
            try { this.notify(); } catch(Exception e) {}
        }
    }

    /** Creates new form CatalogoVentas */
    public CatalogoVentas() {
        initComponents();
        jProgressBar1.setIndeterminate(true);
        programarShortcuts();

        configJTable();

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
            xHerramientas.In2ActionX(cat, KeyEvent.VK_ESCAPE, "cerrar"   ) { cat.btnCerrar.doClick()   }
            xHerramientas.In2ActionX(cat, KeyEvent.VK_F1    , "filtrar"  ) { cat.btnFiltrar.doClick() }
            xHerramientas.In2ActionX(cat, KeyEvent.VK_F3    , "buscar"   ) { cat.txtBusqueda.requestFocusInWindow()}
            xHerramientas.In2ActionX(cat, KeyEvent.VK_F4    , "detalles" ) { cat.btnDetalles.doClick() }
            XHerramientas.In2ActionX(cat, KeyEvent.VK_F5    , "cambiar"  ) { if(cat.getBuscarCajero()){cat.activarCajero(false)}else{cat.activarCajero(true)}}
            Herramientas.In2ActionX(cat, KeyEvent.VK_F6    , "cambiar1" ) { if(cat.getBuscarCliente()){cat.activarCliente(false)}else{cat.activarCliente(true)}}
            Herramientas.In2ActionX(cat, KeyEvent.VK_F7    , "cambiar2" ) { if(cat.getBuscarCaja()){cat.activarCaja(false)}else{cat.activarCaja(true)}}
            Herramientas.In2ActionX(cat, KeyEvent.VK_F8    , "imprimir" ) { cat.btnImprimir.doClick() }
         */

        Action cerrar = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnCerrar.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrar");
        getActionMap().put("cerrar"                 , cerrar  );

        Action filtrar = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnFiltrar.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "filtrar");
        getActionMap().put("filtrar"                 , filtrar  );

        Action buscar = new AbstractAction() { public void actionPerformed(ActionEvent e) { txtBusqueda.requestFocusInWindow(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "buscar");
        getActionMap().put("buscar"                 , buscar  );

        Action detalles = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnDetalles.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "detalles");
        getActionMap().put("detalles"                 , detalles  );

        Action cambiar = new AbstractAction() { public void actionPerformed(ActionEvent e) { if(getBuscarCajero()){activarCajero(false);}else{activarCajero(true);} } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "cambiar");
        getActionMap().put("cambiar"                 , cambiar  );

        Action cambiar1 = new AbstractAction() { public void actionPerformed(ActionEvent e) { if(getBuscarCliente()){activarCliente(false);}else{activarCliente(true);} } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "cambiar1");
        getActionMap().put("cambiar1"                 , cambiar1  );

        Action cambiar2 = new AbstractAction() { public void actionPerformed(ActionEvent e) { if(getBuscarCaja()){activarCaja(false);}else{activarCaja(true);} } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "cambiar2");
        getActionMap().put("cambiar2"                 , cambiar2  );

        Action imprimir = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnImprimir.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "imprimir");
        getActionMap().put("imprimir"                 , imprimir  );
    }


    public void configJTable() {
        new Thread() {
            public void run() {
                String[]  columnas = { "Fecha","Venta", "Caja", "Almacen", "Cliente","Total"};
                ArrayList cols     = new ArrayList<String>(Arrays.asList(columnas));
                Class[]   clases   = {String.class, Integer.class, Integer.class, String.class, String.class, Double.class};
                ArrayList cls      = new ArrayList<Class>(Arrays.asList(clases));
                double[] anchoCols = {0.2,0.1,0.08,0.25,0.25,0.1};

                VentasTableModel modeloTabla = new VentasTableModel(cols, cls);
                //jTable1.enableInputMethods(false);
                modelo = modeloTabla;

                Calendar calendario = Calendar.getInstance();
                txtFechaHasta.setDate(calendario.getTime());
                calendario.add(Calendar.DAY_OF_MONTH, -2);
                txtFechaDesde.setDate(calendario.getTime());
                String whereFecha = "" ;
                String fechaDesde     = "";
                String fechaHasta     = "";
                SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
                calendario.setTime(txtFechaHasta.getDate());
                calendario.add(Calendar.DAY_OF_MONTH, 1);

                if(txtFechaDesde.getDate() != null || txtFechaHasta.getDate() != null)
                {
                    try {
                        fechaDesde = sdf.format(txtFechaDesde.getDate());
                        fechaHasta = sdf.format(calendario.getTime());
                        whereFecha = " AND ventas.fecha_hora >= '"+fechaDesde+"' AND ventas.fecha_hora <= '"+fechaHasta+"'  ";

                    } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el registro: Fecha inválida", Herramientas.getStackTraceString(e)); }
                }

                setQueryTable("SELECT ventas.id_venta,ventas.fecha_hora,ventas.id_venta,ventas.id_caja,almacenes.descripcion,clientes.razonSocial,ventas.total FROM ventas,clientes,almacenes WHERE ventas.id_almacen="+IDAlmacen+" AND ventas.id_cliente=clientes.id_cliente"+whereFecha);
                jTable1.setModel(modeloTabla);

                Herramientas.setColumnsWidth(jTable1, anchoCols);
                jProgressBar1.setIndeterminate(false);
            }
        }.start();

    }
    public void setModoDialogo()
    {
        modal=true;
        this.btnAceptar.setVisible(true);
        Action aceptar = new AbstractAction() { public void actionPerformed(ActionEvent e) {
            ((CatalogoVentas)e.getSource()).btnAceptar.doClick();
        } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "aceptar");
        getActionMap().put("aceptar"                 , aceptar  );
    }

    public void setQueryTable(String query) {
        txtQuery = query;

        modelo.setQuery(query);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new JLabel();
        txtBusqueda = new JTextField();
        jLabel1 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        btnFiltrar = new JButton();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        btnCerrar = new JButton();
        txtFechaDesde = new org.jdesktop.swingx.JXDatePicker();
        txtFechaHasta = new org.jdesktop.swingx.JXDatePicker();
        chkCajero = new JCheckBox();
        chkCliente = new JCheckBox();
        chkCaja = new JCheckBox();
        btnAceptar = new JButton();
        btnDetalles = new JButton();
        btnImprimir = new JButton();
        btnVentasXLinea = new JButton();
        jProgressBar1 = new JProgressBar();

        setTitle("Catálogo de Ventas");

        jLabel2.setFont(new Font("Arial", 1, 36)); // NOI18N
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("<html><head><style type='text/css'>body { font-family: 'Roboto Thin'; font-size: 28px; }</style></head>\n<body>\nCatálogo de Ventas\n</body></html>");

        txtBusqueda.setFont(new Font("Arial", 0, 14)); // NOI18N
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

        jLabel3.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new Color(255, 255, 255));
        jLabel3.setText("Desde:");

        jLabel4.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new Color(255, 255, 255));
        jLabel4.setText("Hasta:");

        btnFiltrar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnFiltrar.setIcon(new ImageIcon(getClass().getResource("/16x16/search.png"))); // NOI18N
        btnFiltrar.setText("Filtrar [F1]");
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });
        btnFiltrar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                salir(evt);
            }
        });

        jTable1.setFont(new Font("Tahoma", 0, 17)); // NOI18N
        jTable1.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Venta", "Caja", "Almacén", "Cliente", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setRowHeight(20);
        jScrollPane1.setViewportView(jTable1);

        btnCerrar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnCerrar.setIcon(new ImageIcon(getClass().getResource("/32x32/back.png"))); // NOI18N
        btnCerrar.setText("<HTML>Regresar a menú [Esc]</HTML>");
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

        txtFechaDesde.setFont(new Font("Arial", 0, 14)); // NOI18N
        txtFechaDesde.setFormats(DateFormat.getDateInstance(DateFormat.MEDIUM));
        txtFechaDesde.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                salir(evt);
            }
        });

        txtFechaHasta.setFont(new Font("Arial", 0, 14)); // NOI18N
        txtFechaHasta.setFormats(DateFormat.getDateInstance(DateFormat.MEDIUM));
        txtFechaHasta.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                salir(evt);
            }
        });

        chkCajero.setFont(new Font("Arial", 0, 14)); // NOI18N
        chkCajero.setText("Cajero [F5]");
        chkCajero.setFocusable(false);

        chkCliente.setFont(new Font("Arial", 0, 14)); // NOI18N
        chkCliente.setSelected(true);
        chkCliente.setText("Id Venta [F6]");
        chkCliente.setFocusable(false);

        chkCaja.setFont(new Font("Arial", 0, 14)); // NOI18N
        chkCaja.setText("Caja [F7]");
        chkCaja.setFocusable(false);

        btnAceptar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnAceptar.setIcon(new ImageIcon(getClass().getResource("/32x32/accept.png"))); // NOI18N
        btnAceptar.setText("Aceptar [Enter]");
        btnAceptar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                salir(evt);
            }
        });

        btnDetalles.setFont(new Font("Arial", 0, 14)); // NOI18N
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

        btnImprimir.setFont(new Font("Arial", 0, 14)); // NOI18N
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

        btnVentasXLinea.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnVentasXLinea.setIcon(new ImageIcon(getClass().getResource("/32x32/page_search.png"))); // NOI18N
        btnVentasXLinea.setText("Ventas X Linea");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(chkCajero)
                                .addGap(34, 34, 34))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtBusqueda, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(txtFechaDesde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFechaHasta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFiltrar, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chkCliente)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(chkCaja)
                                .addGap(301, 301, 301))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptar, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDetalles, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVentasXLinea, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                        .addComponent(btnImprimir, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtFechaDesde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addComponent(txtBusqueda)
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(txtFechaHasta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addComponent(btnFiltrar)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(chkCajero)
                    .addComponent(chkCaja)
                    .addComponent(chkCliente))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimir, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDetalles, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVentasXLinea, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFiltrarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        // TODO add your handling code here:
        buscar();
        
}//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnCerrarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        VentasTableModel tabModelo = this.modelo;
        this.modelo = null;
        if(tabModelo != null) {
            tabModelo.destroy();
        }
        this.dispose();
        if(!modal){
        ((JInternalFrame)((omoikane.principal.MenuPrincipal)omoikane.principal.Principal.getMenuPrincipal()).getMenuPrincipal()).requestFocusInWindow();
        }
}//GEN-LAST:event_btnCerrarActionPerformed

    private void btnDetallesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
        // TODO add your handling code here:
        int sel = this.jTable1.getSelectedRow();
        int id;

        if(sel == -1)
        {
            Dialogos.lanzarAlerta("Ningúna fila ha sido seleccionada.");
        } else {
            //++++++++++++++++++++++++++++
            id = (Integer)this.jTable1.getValueAt(sel, -1);
            //Lanzar ventana y agregarle un listener
            omoikane.principal.Ventas.lanzarDetalles(id);
        }


}//GEN-LAST:event_btnDetallesActionPerformed

    private void btnImprimirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Ventas.lanzarImprimir(txtQuery);
       
}//GEN-LAST:event_btnImprimirActionPerformed

    private void txtBusquedaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyTyped
        // TODO add your handling code here:
        preBuscar();
    }//GEN-LAST:event_txtBusquedaKeyTyped

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
    }//GEN-LAST:event_txtBusquedaKeyPressed

    private void salir(KeyEvent evt) {//GEN-FIRST:event_salir
        // TODO add your handling code here:
        if(evt.getKeyCode() == evt.VK_ESCAPE) { this.btnCerrar.doClick(); }
    }//GEN-LAST:event_salir

    public boolean getBuscarCliente()
    {
        return this.chkCliente.getModel().isSelected();
    }
    public boolean getBuscarCaja() {
        return this.chkCaja.getModel().isSelected();
    }
    public boolean getBuscarCajero() {
        return this.chkCajero.getModel().isSelected();
    }

    public void activarCliente(boolean a)
    {
        this.chkCliente.setSelected(a);
    }
    public void activarCaja(boolean a)
    {
        this.chkCaja.setSelected(a);
    }
    public void activarCajero(boolean a)
    {
        this.chkCajero.setSelected(a);
    }

    public void preBuscar()
    {
        if(timerBusqueda != null && timerBusqueda.isAlive()) { timerBusqueda.cancelar(); }
        this.timerBusqueda = new TimerBusqueda(this);
        timerBusqueda.start();

    }

    public void buscar()
    {
     
        boolean xCliente = getBuscarCliente();
        boolean xCaja = getBuscarCaja();
        boolean xCajero= getBuscarCajero();
        String whereFecha = "" ;
        String fechaDesde     = "";
        String fechaHasta     = "";
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(this.txtFechaHasta.getDate());
        calendario.add(Calendar.DAY_OF_MONTH, 1);
        if(this.txtFechaDesde.getDate() != null || this.txtFechaHasta.getDate() != null)
        {
            try {
                fechaDesde = sdf.format(this.txtFechaDesde.getDate());
                fechaHasta = sdf.format(calendario.getTime());
                whereFecha = " AND ventas.fecha_hora >= '"+fechaDesde+"' AND ventas.fecha_hora <= '"+fechaHasta+"'  ";

            } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el registro: Fecha inválida", Herramientas.getStackTraceString(e)); }
        }
 
        String busqueda = this.txtBusqueda.getText();
        String query    = "SELECT ventas.id_venta,ventas.fecha_hora,ventas.id_venta,ventas.id_caja,almacenes.descripcion,clientes.razonSocial,ventas.total FROM ventas,clientes,almacenes WHERE ventas.id_almacen="+IDAlmacen+" AND ventas.id_cliente=clientes.id_cliente "+whereFecha;
        if(xCliente || xCaja || xCajero) { query += "AND ("; }
        if(xCliente) {
                //query += "(clientes.razonSocial like '%"+busqueda+"%') ";
                query += "(ventas.id_venta like '%"+busqueda+"%') ";
        }
        if(xCliente && (xCaja || xCajero)) { query += "OR "; }
        if(xCaja) {
                query += "(id_caja like '%"+busqueda+"%') ";
        }
        if((xCaja||xCliente) && xCajero) { query += "OR "; }
        if(xCajero) {
                query += "(id_caja like '%"+busqueda+"%') ";
        }
        if(xCliente || xCaja || xCajero) { query += ")"; }
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
    private JButton btnFiltrar;
    private JButton btnImprimir;
    private JButton btnVentasXLinea;
    private JCheckBox chkCaja;
    private JCheckBox chkCajero;
    private JCheckBox chkCliente;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JProgressBar jProgressBar1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    public JTextField txtBusqueda;
    private org.jdesktop.swingx.JXDatePicker txtFechaDesde;
    private org.jdesktop.swingx.JXDatePicker txtFechaHasta;
    // End of variables declaration//GEN-END:variables

}

class VentasTableModel extends ScrollableTableModel {
    VentasTableModel(java.util.List ColNames,ArrayList ColClasses){
        super(ColNames,ColClasses);
    }
    public Object getValueAt(int row,int col){
        if(col==0) {
            SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy '@' hh:mm a");
            return sdf.format((java.util.Date) super.getValueAt(row, col));
        } else {
            return super.getValueAt(row,col);
        }
    }
}
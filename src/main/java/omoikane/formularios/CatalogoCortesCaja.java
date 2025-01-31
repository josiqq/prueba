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


public class CatalogoCortesCaja extends JInternalFrame {

    //TimerBusqueda          timerBusqueda;
    public boolean modal = false;
    TimerBusqueda          timerBusqueda;
    public int IDAlmacen = omoikane.principal.Principal.IDAlmacen;
    BufferedImage          fondo;
    public int             IDSeleccionado;
    public String          codigoSeleccionado;
    public String          txtQuery;
    CortesTableModel modelo;

    class TimerBusqueda extends Thread
    {
        CatalogoCortesCaja ca;
        boolean busquedaActiva = true;

        TimerBusqueda(CatalogoCortesCaja ca) { this.ca = ca; }
        public void run()
        {
            synchronized(this)
            {
                busquedaActiva = true;
                try { this.wait(1000); } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el timer de búsqueda automática", Herramientas.getStackTraceString(e)); }
                if(busquedaActiva) { ca.buscar(); }
            }
        }
        void cancelar()
        {
            busquedaActiva = false;
            try { this.notify(); } catch(Exception e) {}
        }
    }
    /** Creates new form Catalogocortes */
    public CatalogoCortesCaja() {
        initComponents();
        Calendar calendario = Calendar.getInstance();
        txtFechaHasta.setDate(calendario.getTime());
        calendario.add(Calendar.DAY_OF_MONTH, -30);
        txtFechaDesde.setDate(calendario.getTime());

        String[]  columnas = { "Fecha","Caja", "Desde","Hasta","Impuesto","Total"};
        ArrayList cols     = new ArrayList<String>(Arrays.asList(columnas));
        Class[]   clases   = {String.class, String.class, String.class, String.class, Double.class, Double.class};
        ArrayList cls      = new ArrayList<Class>(Arrays.asList(clases));

        CortesTableModel modeloTabla = new CortesTableModel(cols, cls);
        //jTable1.enableInputMethods(false);
        this.modelo = modeloTabla;
        this.jTable1.setModel(modeloTabla);

        setQueryTable("SELECT cortes.id_corte,cortes.fecha_hora,cajas.descripcion,cortes.desde,cortes.hasta,cortes.impuestos,cortes.total FROM cortes,cajas WHERE cortes.id_almacen=1 AND cortes.id_caja=cajas.id_caja ORDER BY cortes.fecha_hora desc");

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

    public void setModoDialogo()
    {
        modal=true;
        this.btnAceptar.setVisible(true);
        Action aceptar = new AbstractAction() { public void actionPerformed(ActionEvent e) {
            ((CatalogoCortesCaja)e.getSource()).btnAceptar.doClick();
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
        btnAceptar = new JButton();
        btnDetalles = new JButton();
        btnImprimir = new JButton();
        btnCorteDia = new JButton();

        setTitle("Cortes de Caja");

        jLabel2.setFont(new Font("Arial", 1, 36));
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("Cortes de Caja");

        txtBusqueda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                txtBusquedaKeyPressed(evt);
            }
            public void keyTyped(KeyEvent evt) {
                txtBusquedaKeyTyped(evt);
            }
        });

        jLabel1.setFont(new Font("Tahoma", 1, 12));
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setText("Buscar [F3]:");

        jLabel3.setFont(new Font("Tahoma", 1, 12));
        jLabel3.setForeground(new Color(255, 255, 255));
        jLabel3.setText("Desde:");

        jLabel4.setFont(new Font("Tahoma", 1, 12));
        jLabel4.setForeground(new Color(255, 255, 255));
        jLabel4.setText("Hasta:");

        btnFiltrar.setIcon(new ImageIcon(getClass().getResource("/16x16/search.png"))); // NOI18N
        btnFiltrar.setText("Filtrar [F2]");
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        jTable1.setModel(new DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jTable1.setFocusable(false);
        jScrollPane1.setViewportView(jTable1);

        btnCerrar.setIcon(new ImageIcon(getClass().getResource("/64x64/back.png"))); // NOI18N
        btnCerrar.setText("<HTML>Regresar a menú [Esc]</HTML>");
        btnCerrar.setRequestFocusEnabled(false);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        txtFechaDesde.setFormats(DateFormat.getDateInstance(DateFormat.MEDIUM));

        txtFechaHasta.setFormats(DateFormat.getDateInstance(DateFormat.MEDIUM));

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

        btnImprimir.setIcon(new ImageIcon(getClass().getResource("/32x32/printer.png"))); // NOI18N
        btnImprimir.setText("<html><center>Imprimir [F8]</center></html>");
        btnImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnCorteDia.setIcon(new ImageIcon(getClass().getResource("/32x32/application_edit.png"))); // NOI18N
        btnCorteDia.setText("<html><center>Corte de caja del día [F7]</center></html>");
        btnCorteDia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCorteDiaActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 403, Short.MAX_VALUE)
                                                .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(8, 8, 8)
                                                .addComponent(txtBusqueda, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtFechaDesde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(16, 16, 16)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtFechaHasta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnFiltrar, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnAceptar)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDetalles)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 282, Short.MAX_VALUE)
                                                .addComponent(btnCorteDia)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnImprimir)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                                .addComponent(txtBusqueda, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(btnFiltrar, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(txtFechaHasta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtFechaDesde, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(19, 19, 19)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnDetalles)
                                        .addComponent(btnAceptar)
                                        .addComponent(btnImprimir)
                                        .addComponent(btnCorteDia))
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
        CortesTableModel tabModelo = this.modelo;
        this.modelo = null;
        tabModelo.destroy();
        this.dispose();
        if(!modal){
            ((JInternalFrame)((omoikane.principal.MenuPrincipal)omoikane.principal.Principal.getMenuPrincipal()).getMenuPrincipal()).requestFocusInWindow();
        }
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnAceptarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnAceptarActionPerformed

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
            omoikane.principal.Cortes.lanzarDetalles(id);
        }


    }//GEN-LAST:event_btnDetallesActionPerformed

    private void btnImprimirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Cortes.lanzarImprimir(txtQuery);

    }//GEN-LAST:event_btnImprimirActionPerformed

    private void txtBusquedaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyTyped
        // TODO add your handling code here:
        buscar();
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

    private void btnCorteDiaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCorteDiaActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Cortes.lanzarCorteSucursal(omoikane.principal.Principal.IDAlmacen, true);
    }//GEN-LAST:event_btnCorteDiaActionPerformed



    public void buscar()
    {

        String whereFecha = "" ;
        String fechaDesde     = "";
        String fechaHasta     = "";
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        if(this.txtFechaDesde.getDate() != null || this.txtFechaHasta.getDate() != null)
        {
            try {
                fechaDesde = sdf.format(this.txtFechaDesde.getDate());
                fechaHasta = sdf.format(this.txtFechaHasta.getDate());
                whereFecha = " AND cortes.fecha_hora >= '"+fechaDesde+"' AND cortes.fecha_hora <= '"+fechaHasta+"'  ";

            } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el registro: Fecha inválida", Herramientas.getStackTraceString(e)); }
        }

        String busqueda = this.txtBusqueda.getText();
        String query    = "SELECT cortes.id_corte,cortes.fecha_hora,cajas.descripcion,cortes.desde,cortes.hasta,cortes.impuestos,cortes.total FROM cortes,cajas WHERE cortes.id_almacen=1 AND cortes.id_caja=cajas.id_caja "+whereFecha;
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
        g2d.setColor(new Color(55,55,255,165));
        g2d.fillRect(0,0,areaDibujo.width,areaDibujo.height);
        fondo = tmp;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAceptar;
    private JButton btnCerrar;
    private JButton btnCorteDia;
    private JButton btnDetalles;
    private JButton btnFiltrar;
    private JButton btnImprimir;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    public JTextField txtBusqueda;
    private org.jdesktop.swingx.JXDatePicker txtFechaDesde;
    private org.jdesktop.swingx.JXDatePicker txtFechaHasta;
    // End of variables declaration//GEN-END:variables

}
class CortesTableModel extends NadesicoTableModel{
    CortesTableModel(java.util.List ColNames,ArrayList ColClasses){super((ArrayList)ColNames,ColClasses);}
    public Object getValueAt(int row,int col){    if(col==0 || col==2 || col==3)
    {
        SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy '@' hh:mm a");
        return sdf.format((java.util.Date) super.getValueAt(row, col));}
    else
    {return super.getValueAt(row,col);}
    }}
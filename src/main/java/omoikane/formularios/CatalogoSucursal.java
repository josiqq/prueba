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

import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import omoikane.sistema.*;
import java.util.Calendar;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.*;


public class CatalogoSucursal extends JInternalFrame {

    public boolean   modal = false;
    public int       IDAlmacen = omoikane.principal.Principal.IDAlmacen;
    public int       IDSeleccionado;
    public String    codigoSeleccionado;
    public String    txtQuery;
    TimerBusqueda    timerBusqueda;
    SucursalTableModel modelo;
    BufferedImage    fondo;

    class TimerBusqueda extends Thread
    {
        CatalogoSucursal ca;
        boolean busquedaActiva = true;

        TimerBusqueda(CatalogoSucursal ca) { this.ca = ca; }
        public void run()
        {
            synchronized(this)
            {
                busquedaActiva = true;
                try { this.wait(1000); } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el timer de búsqueda automática", Herramientas.getStackTraceString(e)); }
                if(busquedaActiva) { ca.resetTable(); }
            }
        }

        void cancelar()
        {
            busquedaActiva = false;
            try { this.notify(); } catch(Exception e) {}
        }
    }
    /** Creates new form Catalogocortes */
    public CatalogoSucursal() {
        initComponents();
        programarShortcuts();

        String[]  columnas = { "ID Corte","Fecha"};
        ArrayList cols     = new ArrayList<String>(Arrays.asList(columnas));
        Class[]   clases   = {Integer.class, String.class};
        ArrayList cls      = new ArrayList<Class>(Arrays.asList(clases));
        SucursalTableModel modeloTabla = new SucursalTableModel(cols, cls);

        //jTable1.enableInputMethods(false);
        this.modelo = modeloTabla;
        this.jTable1.setModel(modeloTabla);
        setQueryTable("SELECT cs.id_corte AS id, cs.creacion AS fecha " +
                "FROM cortes_sucursal cs " +
                "ORDER BY fecha DESC");

        //Instrucciones para el funcionamiento del fondo semistransparente
        this.setOpaque(false);
        ((JPanel)this.getContentPane()).setOpaque(false);
        this.getLayeredPane().setOpaque(false);
        this.getRootPane().setOpaque(false);
        this.generarFondo(this);
        Herramientas.centrarVentana(this);
        //this.btnAceptar.setVisible(false);

        //Instrucciones para el funcionamiento de las teclas de navegación
        Set newKeys = new HashSet(getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, newKeys);
        newKeys = new HashSet(getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeys);
        //Instrucciones para el funcionamiento del campos de fecha
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_MONTH,+1);
        fechaHasta.setDate(calendario.getTime());
        calendario.add(Calendar.DAY_OF_MONTH,-31);
        fechaDesde.setDate(calendario.getTime());
        

    }

    public void programarShortcuts() {
        /*
            xHerramientas.In2ActionX(cat, KeyEvent.VK_ESCAPE, "cerrar"   ) { cat.btnCerrar.doClick()   }
            xHerramientas.In2ActionX(cat, KeyEvent.VK_F4    , "detalles" ) { cat.btnDetalles.doClick() }
            xHerramientas.In2ActionX(cat, KeyEvent.VK_F7    , "corte" ) { cat.btnCorteDia.doClick() }
            xHerramientas.In2ActionX(cat, KeyEvent.VK_F1    , "caja" ) { cat.btnCortesCaja.doClick() }
            xHerramientas.In2ActionX(cat, KeyEvent.VK_F3    , "filtrar" ) { cat.btnFiltrar.doClick() }
         */

        Action cerrar = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnCerrar.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrar");
        getActionMap().put("cerrar"                 , cerrar  );

        Action detalles = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnDetalles.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "detalles");
        getActionMap().put("detalles"                 , detalles  );

        Action corteCaja = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnCortesCaja.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "corteCaja");
        getActionMap().put("corteCaja"                 , corteCaja  );

        Action axCorte = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnCorteDia.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "axCorte");
        getActionMap().put("axCorte"                 , axCorte  );

        Action filtrar = new AbstractAction() { public void actionPerformed(ActionEvent e) { btnFiltrar.doClick(); } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "filtrar");
        getActionMap().put("filtrar"                 , filtrar  );
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
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        btnCerrar = new JButton();
        fechaDesde = new org.jdesktop.swingx.JXDatePicker();
        fechaHasta = new org.jdesktop.swingx.JXDatePicker();
        btnCortesCaja = new JButton();
        btnDetalles = new JButton();
        jLabel5 = new JLabel();
        btnFiltrar = new JButton();
        btnCorteDia = new JButton();

        setTitle("Cortes de Sucursal");

        jLabel2.setFont(new Font("Arial", 1, 36));
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("Cortes de Sucursal");

        txtBusqueda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                txtBusquedaKeyPressed(evt);
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

        fechaHasta.setLinkDay(new Date(1247680799000L));

        btnCortesCaja.setIcon(new ImageIcon(getClass().getResource("/64x64/applications.png"))); // NOI18N
        btnCortesCaja.setText("Cortes Catalogo Caja [F2]");
        btnCortesCaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCortesCajaActionPerformed(evt);
            }
        });

        btnDetalles.setIcon(new ImageIcon(getClass().getResource("/32x32/page_search.png"))); // NOI18N
        btnDetalles.setText("Detalles [F4]");
        btnDetalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });

        btnFiltrar.setIcon(new ImageIcon(getClass().getResource("/16x16/search.png"))); // NOI18N
        btnFiltrar.setText("Filtrar [F3]");
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        btnCorteDia.setIcon(new ImageIcon(getClass().getResource("/32x32/application_edit.png"))); // NOI18N
        btnCorteDia.setText("<html><center>Corte de Sucursal del día [F7]</center></html>");
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
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(8, 8, 8)
                                        .addComponent(txtBusqueda, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3))
                                    .addComponent(jLabel2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fechaDesde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jLabel4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fechaHasta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnFiltrar, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                                    .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCortesCaja, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDetalles)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 246, Short.MAX_VALUE)
                                .addComponent(btnCorteDia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addComponent(jLabel5, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 9, GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(txtBusqueda, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(fechaDesde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(fechaHasta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFiltrar)))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnCortesCaja, 0, 0, Short.MAX_VALUE)
                    .addComponent(btnDetalles, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCorteDia))
                .addGap(11, 11, 11))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(433, Short.MAX_VALUE)
                .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 9, GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        SucursalTableModel tabModelo = this.modelo;
        this.modelo = null;
        tabModelo.destroy();
        this.dispose();
        if(!modal){
        ((JInternalFrame)((omoikane.principal.MenuPrincipal)omoikane.principal.Principal.getMenuPrincipal()).getMenuPrincipal()).requestFocusInWindow();
        }
}//GEN-LAST:event_btnCerrarActionPerformed

    private void btnCortesCajaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCortesCajaActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Cortes.lanzarCatalogo();
    }//GEN-LAST:event_btnCortesCajaActionPerformed

    private void btnDetallesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
        // TODO add your handling code here:
        int sel = this.jTable1.getSelectedRow();
        int id;

        if(sel == -1) {
            Dialogos.lanzarAlerta("Ningúna fila ha sido seleccionada.");
        } else {
            //++++++++++++++++++++++++++++
            id = (Integer)this.jTable1.getValueAt(sel, -1);
            //Lanzar ventana y agregarle un listener
            omoikane.principal.Cortes.lanzarDetallesCorteSucursal(IDAlmacen, id);
        }

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
    }//GEN-LAST:event_txtBusquedaKeyPressed

    private void btnFiltrarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        // TODO add your handling code here:
        resetTable();
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnCorteDiaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCorteDiaActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Cortes.lanzarCorteSucursal(omoikane.principal.Principal.IDAlmacen, true);
}//GEN-LAST:event_btnCorteDiaActionPerformed

    public void resetTable()
    {
        String Desde     = "";
        String Hasta     = "";


        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        if(this.fechaDesde.getDate() != null || this.fechaHasta.getDate() != null)
        {
            try {
                Desde = sdf.format(this.fechaDesde.getDate());
                Hasta = sdf.format(this.fechaHasta.getDate());
            } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el registro: Fecha inválida", Herramientas.getStackTraceString(e)); }
        }
        String whereFecha = " AND cs.creacion >= '"+Desde+"' AND cs.creacion < '"+Hasta+"' ";
        System.out.println(whereFecha);
        String query    = "SELECT cs.id_corte AS id, cs.creacion AS fecha FROM cortes_sucursal cs WHERE cs.id_corte like '%"+txtBusqueda.getText()+"%'"+whereFecha;
        System.out.println(query);
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
    private JButton btnCerrar;
    private JButton btnCorteDia;
    private JButton btnCortesCaja;
    private JButton btnDetalles;
    private JButton btnFiltrar;
    private org.jdesktop.swingx.JXDatePicker fechaDesde;
    private org.jdesktop.swingx.JXDatePicker fechaHasta;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    public JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables

}

class SucursalTableModel extends ScrollableTableModel {
SucursalTableModel(java.util.List ColNames,ArrayList ColClasses){super((ArrayList)ColNames,ColClasses);}
public Object getValueAt(int row,int col){    if(col==0)
    {
    SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy '@' hh:mm a");
    return sdf.format((Date) super.getValueAt(row, col));}
    else
    {
        return super.getValueAt(row,col-1);
    }
}}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CatalogoArticulos.java
 *
 * Created on 17/08/2008, 05:43:22 PM
 */

package omoikane.formularios;

import java.math.BigDecimal;
import java.sql.*;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;

import com.jhlabs.image.*;
import omoikane.principal.Principal;
import omoikane.producto.BaseParaPrecio;
import omoikane.producto.PrecioOmoikaneLogic;
import omoikane.sistema.*;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.image.GaussianBlurFilter;

/** ////////////////////////////////////////////////////////////////////////////////////////////////
 * /////////////////////////////////////////////////////////////////////////////////////////////////
 * @author Octavio
 */
public class CatalogoArticulos extends OmJInternalFrame {

    TimerBusqueda          timerBusqueda;
    BufferedImage          fondo;
    public int             IDSeleccionado;
    public String          codigoSeleccionado;
    public int IDAlmacen = Principal.IDAlmacen;
    public String          txtQuery;
    public ArticulosTableModel modelo;
    public boolean modal = false;

    public void mostrar() { this.setVisible(true);  }
    public void ocultar() { this.setVisible(false); }

    class TimerBusqueda extends Thread
    {
        CatalogoArticulos ca;
        boolean busquedaActiva = true;

        TimerBusqueda(CatalogoArticulos ca) { this.ca = ca; }
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

    private String mainQuery;
    public String getMainQuery() { return mainQuery; }
    public void setMainQuery(String mainQuery) { this.mainQuery = mainQuery; }
    /** Creates new form CatalogoArticulos */
    public CatalogoArticulos() {
        //Conectar a MySQL
        try {
            initComponents();
            programarShortcuts();
            cargaProgressBar.setIndeterminate(true);

            new Thread() {
                public void run() {
                    String[]  columnas = {"Código", "Línea", "Grupo", "Concepto", "Unidad", "Precio", "Existencias"};
                    ArrayList cols     = new ArrayList<String>(Arrays.asList(columnas));
                    Class[]   clases   = {String.class, String.class, String.class, String.class, String.class, String.class, String.class};
                    ArrayList cls      = new ArrayList<Class>(Arrays.asList(clases));
                    double[]  widths   = {0.16,0.15,0.07,0.40,0.05,0.08,0.08};

                    ArticulosTableModel modeloTabla = new ArticulosTableModel(cols, cls);
                    //jTable1.enableInputMethods(false);
                    
                    modelo = modeloTabla;
                    //Notas:
                    //(1) Favor de no alterar el orden  de los campos, añadir nuevos campos al final. Por defecto ArticulosTableModel depende de éste orden
                    //(2) Por defecto select, distinct y from deben estár escritos con minúsculas. Ver omoikane.sistema.ScrollableTableModel.getRowCount(ScrollableTableModel.groovy).
                    setMainQuery("select a.id_articulo as xID, a.codigo as xCodigo, l.descripcion as xLinea, g.descripcion as xGrupo, a.descripcion as xDescripcion, a.unidad as xUnidad, 0 as xPrecio, 0 as xExistencias, " +
                            "bp.costo as xCosto, bp.porcentajeImpuestos, bp.porcentajeDescuentoLinea, bp.porcentajeDescuentoGrupo, bp.porcentajeDescuentoProducto, bp.porcentajeUtilidad, " +
                            "s.enTienda + s.enBodega " +
                            "from articulos a " +
                            "JOIN base_para_precios bp ON a.id_articulo = bp.id_articulo " +
                            "JOIN Stock s ON s.idArticulo = a.id_articulo " +
                            "JOIN lineas l ON l.id_linea = a.id_linea " +
                            "JOIN grupos g ON g.id_grupo = a.id_grupo ");
                    setQueryTable(getMainQuery());
                    
                    jTable1.setModel(modeloTabla);
                    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                    rightRenderer.setHorizontalAlignment( DefaultTableCellRenderer.RIGHT );
                    jTable1.getColumnModel().getColumn(5).setCellRenderer( rightRenderer );
                    jTable1.getColumnModel().getColumn(6).setCellRenderer( rightRenderer );

                    cargaProgressBar.setIndeterminate(false);

                    Herramientas.setColumnsWidth(jTable1, 960, widths);

                }
            }.start();

            //Instrucciones para el funcionamiento del fondo semistransparente
            this.setOpaque(false);

            this.generarFondo();
            Herramientas.centrarVentana(this);
            this.btnAceptar.setVisible(false);
       } catch(Exception e) {
           Dialogos.error("Error al iniciar catálogo", e);
           this.dispose();
       }

    }
    public void programarShortcuts() {

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
            System.out.println("Print desde catálogo articulos setModoDialogo()");
            ((CatalogoArticulos)e.getSource()).btnAceptar.doClick();
        } };
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "aceptar");
        getActionMap().put("aceptar"                 , aceptar  );

    }

    public void setQueryTable(String query) {
        txtQuery = query;

        modelo.setQuery(query);
        //Selecciona la primera fila luego de una búsqueda
        //jTable1.getSelectionModel().setSelectionInterval(0,0);

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
        jLabel6 = new JLabel();
        chkCodigo = new JCheckBox();
        chkLineas = new JCheckBox();
        chkGrupos = new JCheckBox();
        cargaProgressBar = new JProgressBar();

        setIconifiable(true);
        setTitle("Catálogo de artículos");
        setPreferredSize(new Dimension(1000, 590));



        jScrollPane1.setAutoscrolls(true);

        jTable1.setFont(new Font("Tahoma", 0, 17)); // NOI18N
        jTable1.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Línea", "Grupo", "Concepto", "Unidad", "Precio", "Existencias"
            }
        ));
        jTable1.setFocusable(false);
        jTable1.setRowHeight(20);
        jTable1.setShowHorizontalLines(false);
        jScrollPane1.setViewportView(jTable1);

        txtBusqueda.setNextFocusableComponent(btnDetalles);
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
        jLabel2.setText("<html><head><style type='text/css'>body { font-family: 'Roboto Thin'; font-size: 36px; }</style></head>\n<body>\nCatálogo de Artículos\n</body></html>");

        btnCerrar.setIcon(new ImageIcon(getClass().getResource("/32x32/back.png"))); // NOI18N
        btnCerrar.setText("Cerrar [Esc]");
        btnCerrar.setRequestFocusEnabled(false);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/32x32/page_remove.png"))); // NOI18N
        btnEliminar.setText("Eliminar [Supr]");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new ImageIcon(getClass().getResource("/32x32/page_edit.png"))); // NOI18N
        btnModificar.setText("Modificar [F6]");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new ImageIcon(getClass().getResource("/32x32/page_add.png"))); // NOI18N
        btnNuevo.setText("Nuevo [F5]");
        btnNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnNuevoActionPerformed(evt);
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

        btnAceptar.setIcon(new ImageIcon(getClass().getResource("/32x32/accept.png"))); // NOI18N
        btnAceptar.setText("Aceptar [Enter]");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new Color(255, 255, 255));
        jLabel6.setText("Critério:");

        chkCodigo.setForeground(new Color(255, 255, 255));
        chkCodigo.setSelected(true);
        chkCodigo.setText("Cód, Desc");
        chkCodigo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                chkCodigoActionPerformed(evt);
            }
        });

        chkLineas.setForeground(new Color(255, 255, 255));
        chkLineas.setText("Líneas");
        chkLineas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                chkLineasActionPerformed(evt);
            }
        });

        chkGrupos.setForeground(new Color(255, 255, 255));
        chkGrupos.setText("Grupos");
        chkGrupos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                chkGruposActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
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
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusqueda, GroupLayout.PREFERRED_SIZE, 557, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkCodigo, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkLineas, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkGrupos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cargaProgressBar, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(txtBusqueda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkCodigo)
                    .addComponent(chkLineas)
                    .addComponent(chkGrupos))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cargaProgressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDetalles)
                    .addComponent(btnNuevo)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnAceptar)
                    .addComponent(btnImprimir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    private void txtBusquedaKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyTyped
        // TODO add your handling code here:
        //System.out.println("······KeyEvent>"+evt.getKeyChar());
        //System.out.println("······KeyEvent>"+evt.getKeyCode());
        preBuscar(); 
    }//GEN-LAST:event_txtBusquedaKeyTyped

    private void btnCerrarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:

        
        new Thread() {
            public void run() {
                dispose();
                ArticulosTableModel tabModelo = modelo;

                if(tabModelo != null) { tabModelo.destroy(); }
                modelo = null;

                if(!modal){
                ((JInternalFrame)((omoikane.principal.MenuPrincipal) Principal.getMenuPrincipal()).getMenuPrincipal()).requestFocusInWindow();
                }
            }
        }.start();

}//GEN-LAST:event_btnCerrarActionPerformed

    private void btnEliminarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int IDArticulo = ((ScrollableTableModel)jTable1.getModel()).getIDArticuloFila(this.jTable1.getSelectedRow());
        if(IDArticulo != -1) {
            String descripcion = ((ScrollableTableModel)jTable1.getModel()).getDescripcion(jTable1.getSelectedRow());
            if(JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar éste artículo: \""+descripcion+"\"?", "lala", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                omoikane.principal.Articulos.eliminarArticulo(IDArticulo);
            }
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        int IDArticulo = ((ScrollableTableModel)jTable1.getModel()).getIDArticuloFila(this.jTable1.getSelectedRow());

        //Lanzar la ventana de detalles:
        if(IDArticulo != -1) {
            JInternalFrame wnd = (JInternalFrame)omoikane.principal.Articulos.lanzarModificarArticulo(IDArticulo);
            wnd.addInternalFrameListener(iframeAdapter);
        }
}//GEN-LAST:event_btnModificarActionPerformed

    public InternalFrameAdapter iframeAdapter = new InternalFrameAdapter() {
        public void internalFrameClosed(InternalFrameEvent e) { ((ScrollableTableModel)jTable1.getModel()).refrescar(); }
    };

    private void btnNuevoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Articulos.lanzarFormNuevoArticulo();
}//GEN-LAST:event_btnNuevoActionPerformed

    private void btnDetallesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
        // TODO add your handling code here:
        int IDArticulo = ((ScrollableTableModel)jTable1.getModel()).getIDArticuloFila(this.jTable1.getSelectedRow());
        
        //Lanzar la ventana de detalles:
        if(IDArticulo != -1) { omoikane.principal.Articulos.lanzarDetallesArticulo(IDArticulo); }
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
        omoikane.principal.Articulos.lanzarImprimir(this);
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnAceptarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        Boolean isSelected = jTable1.getSelectionModel().getMinSelectionIndex() >= 0;
        if(isSelected) {
            ScrollableTableModel stm = ((ScrollableTableModel)jTable1.getModel());
            int IDArticulo = stm.getIDArticuloFila(this.jTable1.getSelectedRow());
            System.out.println ("btn aceptar");

            if(IDArticulo != -1) {
                IDSeleccionado = IDArticulo; codigoSeleccionado = (String)stm.getValueAt(this.jTable1.getSelectedRow(), 0);
                this.btnCerrar.doClick();
            }
        }
}//GEN-LAST:event_btnAceptarActionPerformed

    private void chkCodigoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_chkCodigoActionPerformed
        // TODO add your handling code here:
        buscar();
}//GEN-LAST:event_chkCodigoActionPerformed

    private void chkGruposActionPerformed(ActionEvent evt) {//GEN-FIRST:event_chkGruposActionPerformed
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_chkGruposActionPerformed

    private void chkLineasActionPerformed(ActionEvent evt) {//GEN-FIRST:event_chkLineasActionPerformed
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_chkLineasActionPerformed

    public boolean getBuscarCodigoDescripcion()
    {
        return this.chkCodigo.getModel().isSelected();
    }
    public boolean getBuscarLineas() {
        return this.chkLineas.getModel().isSelected();
    }
    public boolean getBuscarGrupos() {
        return this.chkGrupos.getModel().isSelected();
    }
    public void preBuscar()
    {
        if(timerBusqueda != null && timerBusqueda.isAlive()) { timerBusqueda.cancelar(); }
        this.timerBusqueda = new TimerBusqueda(this);
        timerBusqueda.start();
       
    }

    public void buscar()
    {
        boolean xCodDes = getBuscarCodigoDescripcion();
        boolean xLineas = getBuscarLineas();
        boolean xGrupos = getBuscarGrupos();
        String busqueda = this.txtBusqueda.getText();

        if(busqueda==null) { xCodDes = xLineas = xGrupos = false; }
        //String query = "select DISTINCT a.id_articulo as xID,a.codigo as xCodigo, id_linea as xLinea, 'NA' as xGrupo, descripcion as xDescripcion, unidad as xUnidad, 1 as xPrecio, 1 as xExistencias" +
        //        " from articulos as a ";
        String query = getMainQuery();

        if(xCodDes) {
            query += "LEFT JOIN codigo_producto as b ON a.id_articulo = b.producto_id_articulo ";
        }
        
        if(xCodDes || xLineas || xGrupos) { query += "WHERE "; }
        if(xCodDes) {
                query += " (a.descripcion like '%"+busqueda+"%' or " +
                        "a.codigo like '%"+busqueda+"%' or " +
                        "b.codigo like '%"+busqueda+"%') ";
        }
        if(xCodDes && (xLineas || xGrupos)) { query += "OR "; }
        if(xLineas) {
                query += "(linea like '%"+busqueda+"%' ) ";
        }
        if((xLineas||xCodDes) && xGrupos) { query += "OR "; }
        if(xGrupos) {
                query += "(grupo like '%"+busqueda+"%' ) ";
        }
        query += "GROUP BY a.id_articulo";
        
        setQueryTable(query);
    }
    Rectangle lastBounds;
    public Boolean areSameBounds(Rectangle r) {
        if(lastBounds == null) { lastBounds = r; return false; }
        else if(lastBounds.equals(r)) { return true; }
        else { lastBounds = r; return false; }
    }

    /*public void paintComponent(Graphics g)
    {
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

      if(!areSameBounds(getBounds())) {
          BoxBlurFilter filter = new BoxBlurFilter();
          BufferedImage fondo = Principal.getEscritorio().getPanelEscritorio().getBufferImage(getHeight(), getWidth());
          fondo = fondo.getSubimage(0+5+5, 0+5+5, getWidth()-10, getHeight()-10);
          System.out.println("Fondo: "+getX()+","+getY()+","+getWidth()+","+getHeight());

          //fondo = new GaussianBlurFilter(4).filter(fondo, null);
          filter.setIterations(1);
          filter.setRadius(1);
          //filter.filter(fondo, fondo);
          PointillizeFilter pointillizeFilter = new PointillizeFilter();
          pointillizeFilter.setEdgeColor(1);
          pointillizeFilter.setEdgeThickness(1);
          pointillizeFilter.setFadeEdges(true);
          pointillizeFilter.setFuzziness(1);
          //pointillizeFilter.filter(fondo, fondo);

          g2d.setColor(new Color(0,0,0,165));
          g2d.fillRect(0,0,getWidth(),getHeight());
          fondo.getGraphics().setColor(Color.RED);
          fondo.getGraphics().drawRect(0,0,50,50);
      }

    } */



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAceptar;
    private JButton btnCerrar;
    private JButton btnDetalles;
    private JButton btnEliminar;
    private JButton btnImprimir;
    private JButton btnModificar;
    private JButton btnNuevo;
    public JProgressBar cargaProgressBar;
    private JCheckBox chkCodigo;
    private JCheckBox chkGrupos;
    private JCheckBox chkLineas;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel6;
    private JScrollPane jScrollPane1;
    public JTable jTable1;
    public JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables

}

class ArticulosTableModel extends ScrollableTableModel {

    public int IDAlmacen = Principal.IDAlmacen;
    NumberFormat numberFormat;

    public ArticulosTableModel(java.util.List colNames, ArrayList colClases) {
        super(colNames, colClases);
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
    }


    public Object getValueAt(int row,int col){
        if(col==5) {
            BaseParaPrecio bp = new BaseParaPrecio();
            bp.setCosto((Double) super.getValueAt(row, 7));
            bp.setPorcentajeImpuestos((Double) super.getValueAt(row, 8));
            bp.setPorcentajeDescuentoLinea((Double) super.getValueAt(row, 9));
            bp.setPorcentajeDescuentoGrupo((Double) super.getValueAt(row, 10));
            bp.setPorcentajeDescuentoProducto((Double) super.getValueAt(row, 11));
            bp.setPorcentajeUtilidad((Double) super.getValueAt(row, 12));

            PrecioOmoikaneLogic precioOmoikaneLogic = new PrecioOmoikaneLogic(bp);

            return numberFormat.format(precioOmoikaneLogic.getPrecio());
        } else if(col == 6) {
            BigDecimal existencias = (BigDecimal) super.getValueAt(row, 13);
            String display = existencias.compareTo(BigDecimal.ZERO) <= 0 ? "N/D" : numberFormat.format(existencias);
            return display;
        } else {
            return super.getValueAt(row,col);
        }
    }

}
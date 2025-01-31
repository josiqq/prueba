/* Articulo.java
 * Created on 22/08/2008, 07:40:21 PM
 */

package omoikane.formularios;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import omoikane.sistema.*;
import java.util.*;
import java.awt.event.*;


/*
 * @author Octavio
 */

public class Almacen extends JInternalFrame {

    BufferedImage fondo;

    public Almacen() {
        initComponents();
        //Instrucciones para el funcionamiento del fondo semistransparente
        this.setOpaque(false);
        ((JPanel)this.getContentPane()).setOpaque(false);
        this.getLayeredPane().setOpaque(false);
        this.getRootPane().setOpaque(false);
        this.generarFondo(this);
        Herramientas.centrarVentana(this);

        Set newKeys = new HashSet(getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, newKeys);
        newKeys = new HashSet(getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeys);
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
        txtIdAlmacen = new JTextField();
        txtDescripcion = new JTextField();
        txtUModificacion = new JTextField();
        jLabel4 = new JLabel();
        btnModificar = new JButton();
        btnGuardar = new JButton();
        cerrar = new JButton();
        jLabel5 = new JLabel();

        setTitle("Almacen");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new Font("Arial", 1, 52)); // NOI18N
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setText("<html><head><style type='text/css'>body { font-family: 'Roboto Thin'; font-size: 28px; }</style></head>\n<body>\nAlmacen\n</body>\n</html>");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 50));

        jLabel2.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("Id Almacen:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 84, -1, -1));

        jLabel3.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new Color(255, 255, 255));
        jLabel3.setText("Descripcion:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 124, -1, -1));

        txtIdAlmacen.setEditable(false);
        txtIdAlmacen.setFont(new Font("Arial", 0, 14)); // NOI18N
        txtIdAlmacen.setFocusable(false);
        getContentPane().add(txtIdAlmacen, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 280, 26));

        txtDescripcion.setEditable(false);
        txtDescripcion.setFont(new Font("Arial", 0, 14)); // NOI18N
        txtDescripcion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });
        getContentPane().add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 280, 26));

        txtUModificacion.setEditable(false);
        txtUModificacion.setFont(new Font("Arial", 0, 14)); // NOI18N
        txtUModificacion.setFocusable(false);
        getContentPane().add(txtUModificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 380, 26));

        jLabel4.setFont(new Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new Color(255, 255, 255));
        jLabel4.setText("Última Modificación: ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        btnModificar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnModificar.setText("Modificar [F6]");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 25));

        btnGuardar.setFont(new Font("Arial", 0, 14)); // NOI18N
        btnGuardar.setText("Guardar [F6]");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, -1, 25));

        cerrar.setFont(new Font("Arial", 0, 14)); // NOI18N
        cerrar.setText("Cerrar [ESC]");
        cerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cerrarActionPerformed(evt);
            }
        });
        getContentPane().add(cerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, -1, 25));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 10, 10));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Almacenes.guardar(this);
}//GEN-LAST:event_btnGuardarActionPerformed

    private void cerrarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
}//GEN-LAST:event_cerrarActionPerformed

    private void btnModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        omoikane.principal.Almacenes.modificar(this);
}//GEN-LAST:event_btnModificarActionPerformed

    private void txtDescripcionActionPerformed(ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDescripcionActionPerformed

   public void setEditable(boolean editable)
    {this.txtDescripcion.setEditable(editable);}

  public void setModoNuevo()
    {setEditable(true);
     this.btnModificar.setVisible(false);}

  public void setModoDetalles()
    {setEditable(false);
     this.btnGuardar.setVisible(false);
     this.btnModificar.setVisible(false);}

  public void setModoModificar()
    {setEditable(true);
     this.btnGuardar.setVisible(false);
     this.btnModificar.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton cerrar;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField txtDescripcion;
    private JTextField txtIdAlmacen;
    private JTextField txtUModificacion;
    // End of variables declaration//GEN-END:variables

    public String getTxtIDAlmacen()             {return txtIdAlmacen.getText();}
    public String getTxtDescripcion()           {return txtDescripcion.getText();}
    public void setTxtIDAlmacen(String txt)     {txtIdAlmacen.setText(txt);}
    public void setTxtDescripcion(String txt)   {txtDescripcion.setText(txt);}
    public void setTxtUModificacion(String txt) {txtUModificacion.setText(txt);}

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
}
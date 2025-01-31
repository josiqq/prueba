/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Escritorio.java
 *
 * Created on 14/08/2008, 05:31:36 PM
 */

package omoikane.formularios;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

import omoikane.sistema.Apagado;
import omoikane.principal.Calendario;
import omoikane.principal.Calculadora;
import java.awt.datatransfer.Clipboard;

/**
 *
 * @author Octavio
 */
public class Escritorio extends JFrame {

    private Clipboard clipboard = getToolkit().getSystemClipboard();
    private boolean calculadoraAbierta = false;
    private boolean calendarioAbierto = false;

    /** Creates new form Escritorio */
    public Escritorio() {
        initComponents();
        //this.PanelEscritorio.setVisible(true);
        //lblImagenFondo.setVisible(true);

        this.transferFocus();
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        this.PanelEscritorio.getActionMap().put("apagado", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Apagado.apagar();
            }
        });
        this.PanelEscritorio.getInputMap(this.PanelEscritorio.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F10"), "apagado");

        this.PanelEscritorio.getActionMap().put("calculadora", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!calculadoraAbierta) {
                    Component invocador = getFocusOwner();
                    Calculadora.lanzarCalculadora(clipboard, invocador);
                }
            }
        });
        this.PanelEscritorio.getInputMap(this.PanelEscritorio.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F2"), "calculadora");

        this.PanelEscritorio.getActionMap().put("calendario", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!calendarioAbierto) {
                    Component invocador = getFocusOwner();
                    Calendario.lanzarCalendario(invocador);
                }
            }
        });
        this.PanelEscritorio.getInputMap(this.PanelEscritorio.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F9"), "calendario");

    }

    public void setCalculadoraAbierta(boolean b) {
        calculadoraAbierta = b;
    }

    public void setCalendarioAbierto(boolean b) {
        calendarioAbierto = b;
    }

    public void fondoToBack() {
        PanelEscritorio.moveToBack(this.lblImagenFondo);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelEscritorio = new FrostedGlassDesktopPane();
        reloj = new JLabel();
        usuario = new JLabel();
        calculadoraCanvas = new Canvas();
        calendarioCanvas = new Canvas();
        lblImagenFondo = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Omoikane");
        setFocusable(false);
        setMaximizedBounds(new Rectangle(0, 0, 0, 0));
        setMinimumSize(new Dimension(1280, 800));
        setUndecorated(true);
        getContentPane().setLayout(new GridLayout(1, 0));

        PanelEscritorio.setFocusable(false);
        PanelEscritorio.setMinimumSize(new Dimension(1280, 800));
        PanelEscritorio.setOpaque(false);

        reloj.setForeground(new Color(255, 255, 255));
        reloj.setHorizontalAlignment(SwingConstants.CENTER);
        reloj.setText("HORA");
        reloj.setBounds(1140, 0, 130, 70);
        PanelEscritorio.add(reloj, JLayeredPane.DEFAULT_LAYER);

        usuario.setFont(new Font("Tahoma", 0, 12)); // NOI18N
        usuario.setForeground(new Color(255, 255, 255));
        usuario.setHorizontalAlignment(SwingConstants.CENTER);
        usuario.setText("Sin Sesión");
        usuario.setBounds(10, 10, 90, 50);
        PanelEscritorio.add(usuario, JLayeredPane.DEFAULT_LAYER);

        calculadoraCanvas.setBackground(new Color(Color.OPAQUE));
        calculadoraCanvas.setVisible(false);
        calculadoraCanvas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calculadoraCanvas(evt);
            }
        });
        calculadoraCanvas.setBounds(1160, 90, 90, 100);
        PanelEscritorio.add(calculadoraCanvas, JLayeredPane.DEFAULT_LAYER);

        calendarioCanvas.setVisible(false);
        calendarioCanvas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calendarioCanvasClicked(evt);
            }
        });
        calendarioCanvas.setBounds(1160, 200, 90, 110);
        PanelEscritorio.add(calendarioCanvas, JLayeredPane.DEFAULT_LAYER);

        lblImagenFondo.setBackground(new Color(0, 0, 0));
        lblImagenFondo.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenFondo.setIcon(new ImageIcon(getClass().getResource("/omoikane/Media2/skin/SkinMadera1280x720.png"))); // NOI18N
        lblImagenFondo.setAlignmentY(0.0F);
        lblImagenFondo.setFocusable(false);
        lblImagenFondo.setHorizontalTextPosition(SwingConstants.CENTER);
        lblImagenFondo.setIconTextGap(0);
        lblImagenFondo.setInheritsPopupMenu(false);
        lblImagenFondo.setOpaque(true);
        lblImagenFondo.setBounds(0, 0, 1280, 720);
        PanelEscritorio.add(lblImagenFondo, JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(PanelEscritorio);

        //pack();
    }// </editor-fold>//GEN-END:initComponents

    private void calendarioCanvasClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calendarioCanvasClicked
        Component invocador = getFocusOwner();
        Calendario.lanzarCalendario(invocador);
    }//GEN-LAST:event_calendarioCanvasClicked

    private void calculadoraCanvas(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calculadoraCanvas
        Component invocador = getFocusOwner();
        Calculadora.lanzarCalculadora(clipboard, invocador);
    }//GEN-LAST:event_calculadoraCanvas

    /**
    * @param args the command line arguments
    */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public JDesktopPane PanelEscritorio;
    private Canvas calculadoraCanvas;
    private Canvas calendarioCanvas;
    public JLabel lblImagenFondo;
    public JLabel reloj;
    public JLabel usuario;
    // End of variables declaration//GEN-END:variables

}

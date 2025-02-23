package omoikane.caja.presentation;

/**
 * Sample Skeleton for "Caja.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import jfxtras.labs.scene.control.BigDecimalField;
import omoikane.caja.business.ICajaLogic;
import omoikane.caja.business.LineaDeCapturaFilter;
import omoikane.caja.handlers.*;
import omoikane.principal.Principal;
import omoikane.sistema.Dialogos;
import omoikane.sistema.Herramientas;
import org.apache.log4j.Logger;
import org.synyx.hades.domain.PageRequest;

import javax.xml.ws.spi.http.HttpHandler;


public class CajaController
        implements Initializable {

    private CajaModel modelo;
    private ICajaLogic cajaLogic;

    private TimerBusqueda timerBusqueda;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Button buscarProductoButton;

    @FXML
    private Label cajaLabel;

    @FXML
    private TextField cambioTextField;

    @FXML
    private Button cancelarArticuloButton;

    @FXML
    private Button cancelarVentaButton;

    @FXML
    private TextField capturaTextField;

    @FXML
    private Label descuentoLabel;

    @FXML
    private BigDecimalField efectivoTextField;

    @FXML
    private Label fechaLabel;

    @FXML
    private Label impuestosLabel;

    @FXML
    private Button movimientosButton;

    @FXML
    private Label nArticulosLabel;

    @FXML
    private Button pausarButton;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Button terminarVentaButton;

    @FXML
    private Label totalLabel;

    @FXML
    private Button ventaEspecialButton;

    @FXML
    private TableView<ProductoModel> ventaTableView;

    @FXML private TableColumn<ProductoModel, String> conceptoVentaColumn;
    @FXML private TableColumn cantidadVentaColumn;
    @FXML private TableColumn precioVentaColumn;
    @FXML private TableColumn importeVentaColumn;

    @FXML
    private TableView<ProductoModel> productosTableView;

    @FXML private TableColumn descripcionProductoColumn;
    @FXML private TableColumn precioProductoColumn;
    @FXML private ToggleButton btnEfectivo;
    @FXML private ToggleButton btnVale;
    @FXML private ToggleButton btnCheque;
    @FXML private ToggleButton btnTarjeta;
    @FXML private Button abrirCajonButton;
    @FXML private Button btnCobrar;
    @FXML private Button btnCancelarProducto;
    @FXML private Button cerrarButton;
    @FXML private Button abrirCatalogoButton;
    @FXML private AnchorPane capturaPane;
    @FXML private ToolBar mainToolBar;

    @FXML private Rectangle hudRectangle;
    @FXML private Text hudText;

    private BasculaHandler basculaHandler;
    VentaEspecialHandler ventaEspecialHandler;
    public static Logger logger = Logger.getLogger(CajaController.class);
    private CerrarCajaSwingHandler cerrarCajaSwingHandler;
    private MostrarCatalogoHandler mostrarCatalogoHandler;

    public Button getCerrarButton() { return cerrarButton; }
    public AnchorPane getMainAnchorPane() { return mainAnchorPane; }

    public void shutdownBasculaHandler() { basculaHandler.close(); }

    @FXML
    private void onCapturaKeyReleased(KeyEvent event) {
        if ( (modelo.getCaptura().get() != null && !modelo.getCaptura().get().isEmpty()) && event.getCode() == KeyCode.ENTER ) {

            ifAnySelectedProductoThenSelect();
            getCajaLogic().onCaptura(modelo);

        } else if ( event.getCode() == KeyCode.ESCAPE ) {
            modelo.getCaptura().set("");
        } else if( event.getCode() == KeyCode.ADD ) {
            event.consume();
            basculaHandler.pesar();
        }

    }

    public TableColumn getPrecioVentaColumn() {
        return precioVentaColumn;
    }

    public void setCerrarCajaSwingHandler(CerrarCajaSwingHandler cerrarCajaSwingHandler) {
        this.cerrarCajaSwingHandler = cerrarCajaSwingHandler;
    }

    public CerrarCajaSwingHandler getCerrarCajaSwingHandler() {
        return cerrarCajaSwingHandler;
    }

    private void ifAnySelectedProductoThenSelect() {
        ProductoModel pm = productosTableView.getSelectionModel().getSelectedItem();
        LineaDeCapturaFilter capturaFilter = new LineaDeCapturaFilter(modelo.getCaptura().get());
        String cantidad = capturaFilter.getCantidad().toPlainString();

        if(pm != null) {
            modelo.getCaptura().set( cantidad + "*" + pm.codigoProperty().get() );
        }
    }

    public void setCapturaPaneDisable(Boolean b) {
        capturaPane.setDisable(b);
    }

    public void setMainToolBarDisable(Boolean b) {
        mainToolBar.setDisable(b);
    }

    public TableView<ProductoModel> getVentaTableView() {
        return ventaTableView;
    }

    @FXML void onBusquedaIntegradaClicked(MouseEvent event) {
        if(!ifBuscarMasSelected()) {
            ifAnySelectedProductoThenSelect();
        }
    }

    @FXML
    private void onCapturaKeyPressed(KeyEvent event) {
        SelectionModel selectionModel = productosTableView.getSelectionModel();
        if( event.getCode() == KeyCode.DOWN ) {
            selectionModel.selectNext();
            ifBuscarMasSelected();
            scroll();

        } else if( event.getCode() == KeyCode.UP ) {
            selectionModel.selectPrevious();
            scroll();
        }
    }

    @FXML
    private void onTerminarVentaClicked(ActionEvent event) {
        cajaLogic.terminarVenta(getModel());
    }

    public void showHud(String msg) {
        hudText.setDisable(false);
        hudRectangle.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), hudRectangle);
        ft.setFromValue(0.0);
        ft.setToValue(0.8);
        ft.setCycleCount(1);
        ft.play();

        hudText.setText(msg);
        hudText.setVisible(true);
    }

    public void hideHud() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), hudRectangle);
        ft.setFromValue(0.8);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.play();
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                hudRectangle.setVisible(false);
                hudText.setVisible(false);
            }
        });
    }

    private void scroll() {
        SelectionModel selectionModel = productosTableView.getSelectionModel();
        productosTableView.scrollTo(selectionModel.getSelectedIndex());
    }

    private Boolean ifBuscarMasSelected() {
        if( BuscarMasDummyProducto.class.isInstance( productosTableView.getSelectionModel().getSelectedItem() )) {
            getCajaLogic().buscar(getModel());

            SelectionModel selectionModel = productosTableView.getSelectionModel();
            return true;
        }
        return false;
    }

    @FXML
    private void onCapturaKeyTyped(KeyEvent event) {
        if ( modelo.getCaptura().get() != null && !modelo.getCaptura().get().isEmpty() ) {
            if(timerBusqueda != null && timerBusqueda.isAlive()) { timerBusqueda.cancelar(); }
            this.timerBusqueda = new TimerBusqueda(this);
            timerBusqueda.start();
        }
    }

    public CajaModel getModel() {
        return modelo;
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert buscarProductoButton != null : "fx:id=\"buscarProductoButton\" was not injected: check your FXML file 'Caja.fxml'.";
        assert cajaLabel != null : "fx:id=\"cajaLabel\" was not injected: check your FXML file 'Caja.fxml'.";
        assert getCambioTextField() != null : "fx:id=\"cambioTextField\" was not injected: check your FXML file 'Caja.fxml'.";
        //assert cancelarArticuloButton != null : "fx:id=\"cancelarArticuloButton\" was not injected: check your FXML file 'Caja.fxml'.";
        assert cancelarVentaButton != null : "fx:id=\"cancelarVentaButton\" was not injected: check your FXML file 'Caja.fxml'.";
        assert capturaTextField != null : "fx:id=\"capturaTextField\" was not injected: check your FXML file 'Caja.fxml'.";
        assert descuentoLabel != null : "fx:id=\"descuentoLabel\" was not injected: check your FXML file 'Caja.fxml'.";
        assert getEfectivoTextField() != null : "fx:id=\"efectivoTextField\" was not injected: check your FXML file 'Caja.fxml'.";
        assert fechaLabel != null : "fx:id=\"fechaLabel\" was not injected: check your FXML file 'Caja.fxml'.";
        assert impuestosLabel != null : "fx:id=\"impuestosLabel\" was not injected: check your FXML file 'Caja.fxml'.";
        assert movimientosButton != null : "fx:id=\"movimientosButton\" was not injected: check your FXML file 'Caja.fxml'.";
        assert nArticulosLabel != null : "fx:id=\"nArticulosLabel\" was not injected: check your FXML file 'Caja.fxml'.";
        assert pausarButton != null : "fx:id=\"pausarButton\" was not injected: check your FXML file 'Caja.fxml'.";
        assert productosTableView != null : "fx:id=\"productosTableView\" was not injected: check your FXML file 'Caja.fxml'.";
        assert subtotalLabel != null : "fx:id=\"subtotalLabel\" was not injected: check your FXML file 'Caja.fxml'.";
        //assert terminarVentaButton != null : "fx:id=\"terminarVentaButton\" was not injected: check your FXML file 'Caja.fxml'.";
        assert totalLabel != null : "fx:id=\"totalLabel\" was not injected: check your FXML file 'Caja.fxml'.";
        assert ventaEspecialButton != null : "fx:id=\"ventaEspecialButton\" was not injected: check your FXML file 'Caja.fxml'.";
        assert ventaTableView != null : "fx:id=\"ventaTableView\" was not injected: check your FXML file 'Caja.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected
        conceptoVentaColumn     .prefWidthProperty().bind( ventaTableView.widthProperty().multiply(0.545d) );
        cantidadVentaColumn     .prefWidthProperty().bind( ventaTableView.widthProperty().multiply(0.15d) );
        getPrecioVentaColumn().prefWidthProperty().bind( ventaTableView.widthProperty().multiply(0.15d) );
        importeVentaColumn      .prefWidthProperty().bind( ventaTableView.widthProperty().multiply(0.15d) );

        descripcionProductoColumn.prefWidthProperty().bind( productosTableView.widthProperty().multiply(0.743d) );
        precioProductoColumn     .prefWidthProperty().bind( productosTableView.widthProperty().multiply(0.25d ) );

        //Preparar GUI
        hideHud();

        //Escribir la fecha
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy");
        String fecha = sdf.format(new Date());
        fechaLabel.textProperty().set( capitalizarFecha(fecha) );

        //Escribir leyenda de caja
        Integer idCaja = Principal.IDCaja;
        cajaLabel.textProperty().set( "Caja "+idCaja );

        //Agregar el controlador de nevegación por teclado
        KBNavigationHandler kbnc = new KBNavigationHandler(this);
        mainAnchorPane.addEventFilter(KeyEvent.KEY_RELEASED, kbnc);

        //Agregar el controlador de clicks
        ClickHandler clickHandler = new ClickHandler(this);
        mainToolBar.addEventFilter(ActionEvent.ACTION, clickHandler);

        //Agregar el manejador de ventas especiales
        ventaEspecialHandler = new VentaEspecialHandler(this);

        //Agrego bascula handler
        basculaHandler = new BasculaHandler(this, modelo);

        //Coloco el cursor en el campo de captura (quiza seria mejor moverlo a un evento)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                capturaTextField.requestFocus();
            }
        });
    }

    private class ClickHandler implements EventHandler<ActionEvent> {
        CajaController controller;

        public ClickHandler(CajaController cajaController) {
            this.controller = cajaController;
        }

        @Override
        public void handle(ActionEvent event) {
            if (event.getTarget() == abrirCajonButton)
                new AbrirCajon(controller).handle(event);
            if (event.getTarget() == cancelarVentaButton)
                new CancelarVenta(controller).handle(event);
            if (event.getTarget() == btnCancelarProducto)
                new CancelarProducto(controller).handle(event);
            if (event.getTarget() == movimientosButton)
                new MovimientosDeCaja(controller).handle(event);
            if (event.getTarget() == ventaEspecialButton)
                ventaEspecialHandler.handle(event);
            if (event.getTarget() == cerrarButton)
                getCerrarCajaSwingHandler().handle(event);
            if (event.getTarget() == abrirCatalogoButton)
                new MostrarCatalogoHandler(controller).handle(event);
        }
    }

    public String capitalizarFecha(String s) {

        final StringBuilder result = new StringBuilder(s.length());
        String[] words = s.split("\\s");
        for(int i=0,l=words.length;i<l;++i) {
            if(i>0) { result.append(" ");  }
            if(words[i].equalsIgnoreCase("de")) {
                result.append(words[i]);
            } else {
                result.append(Character.toUpperCase(words[i].charAt(0)))
                        .append(words[i].substring(1));
            }

        }
        return result.toString();
    }

    public void setModel(final CajaModel modelo) {
        this.modelo = modelo;

        //Bindings
        capturaTextField.textProperty().bindBidirectional(modelo.getCaptura());
        subtotalLabel.textProperty().bind(
                new Number2StringBinding(modelo.getSubtotal(), NumberFormat.getCurrencyInstance())
        );
        descuentoLabel.textProperty().bind(
                new Number2StringBinding(modelo.getDescuento(), NumberFormat.getCurrencyInstance())
        );
        impuestosLabel.textProperty().bind(
                new Number2StringBinding(modelo.getImpuestos(), NumberFormat.getCurrencyInstance())
        );
        totalLabel.textProperty().bind(
                new Number2StringBinding(modelo.getTotal(), NumberFormat.getCurrencyInstance())
        );
        getCambioTextField().textProperty().bind(
                new Number2StringBinding(modelo.getCambio(), NumberFormat.getCurrencyInstance())
        );
        getEfectivoTextField().numberProperty().bindBidirectional(modelo.getEfectivo());

        //Binding efectivo and cambio to logic
        getModel().getEfectivo().addListener(new ChangeListener<BigDecimal>() {
            @Override
            public void changed(ObservableValue<? extends BigDecimal> observableValue, BigDecimal bigDecimal, BigDecimal bigDecimal1) {
                getCajaLogic().calcularCambio(getModel());
            }
        });
        getModel().getTotal().addListener( new ChangeListener<BigDecimal>() {
            @Override
            public void changed(ObservableValue<? extends BigDecimal> observableValue, BigDecimal bigDecimal, BigDecimal bigDecimal1) {
                getCajaLogic().calcularCambio(getModel());
            }
        });

        //Setup table view and table model (ventas)
        ventaTableView.setItems(modelo.getVenta());
        conceptoVentaColumn.setCellValueFactory(
                new PropertyValueFactory<ProductoModel, String>("conceptoString")
        );
        cantidadVentaColumn.setCellValueFactory(
                new PropertyValueFactory<ProductoModel, String>("cantidadString")
        );
        getPrecioVentaColumn().setCellValueFactory(
                new PropertyValueFactory<ProductoModel, String>("precioString")
        );
        importeVentaColumn.setCellValueFactory(
                new PropertyValueFactory<ProductoModel, String>("importeString")
        );

        //Setup table productos
        productosTableView.setItems(modelo.getProductos());
        descripcionProductoColumn.setCellValueFactory(
                new PropertyValueFactory<ProductoModel, String>("conceptoString")
        );
        precioProductoColumn.setCellValueFactory(
                new PropertyValueFactory<ProductoModel, String>("precioString")
        );

        nArticulosLabel.textProperty().set(modelo.getVenta().size() + " artículo(s)");
        ventaTableView.getItems().addListener(new ListChangeListener<ProductoModel>() {

            public void onChanged(Change<? extends ProductoModel> change) {
                getCajaLogic().onVentaListChanged(modelo);
                nArticulosLabel.textProperty().set( modelo.getVenta().size() + " artículo(s)" );
            }
        });
    }

    public ICajaLogic getCajaLogic() {
        return cajaLogic;
    }

    public void setCajaLogic(ICajaLogic cajaLogic) {
        this.cajaLogic = cajaLogic;
        cajaLogic.setController(this);
    }

    public TextField getCapturaTextField() {
        return capturaTextField;
    }

    public TextField getCambioTextField() {
        return cambioTextField;
    }

    public void setCambioTextField(TextField cambioTextField) {
        this.cambioTextField = cambioTextField;
    }

    public BigDecimalField getEfectivoTextField() {
        return efectivoTextField;
    }

    public void setEfectivoTextField(BigDecimalField efectivoTextField) {
        this.efectivoTextField = efectivoTextField;
    }

    public Button getBtnCobrar() {
        return btnCobrar;
    }

    public void setBtnCobrar(Button btnCobrar) {
        this.btnCobrar = btnCobrar;
    }

    public ToggleButton getBtnEfectivo() {
        return btnEfectivo;
    }

    class TimerBusqueda extends Thread
    {
        CajaController cc;
        boolean busquedaActiva = true;

        TimerBusqueda(CajaController cc) { this.cc = cc; }
        public void run()
        {
            synchronized(this)
            {
                busquedaActiva = true;
                try { this.wait(500); } catch(Exception e) { Dialogos.lanzarDialogoError(null, "Error en el timer de búsqueda automática", Herramientas.getStackTraceString(e)); }
                if(busquedaActiva && cc.modelo != null) {
                    getModel().setPaginacionBusqueda(new PageRequest(0,10));
                    cc.getCajaLogic().buscar(cc.getModel());
                }
            }
        }
        void cancelar()
        {
            busquedaActiva = false;
            try { this.notify(); } catch(Exception e) {}
        }
    }

}

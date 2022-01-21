package controladores;

import clases.ContratoEntity;
import clases.GranjaEntity;
import clases.TrabajadorEntity;
import factoria.ContratoManagerImplementation;
import interfaces.ContratoInterface;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LongStringConverter;
import javax.ws.rs.core.GenericType;
import restful.TrabajadorClient;

/**
 * El controlador de la ventana de SignIn la cual controla las excepciones y las
 * acciones de los botones y los textos.
 *
 * @author Idoia Ormaetxea y Alain Cosgaya
 */
public class ContratosController {

    private static final Logger LOGGER = Logger.getLogger(ContratosController.class.getName());

    private Stage stage;

    private ContratoInterface contratoManager;

    @FXML
    private TableColumn<ContratoEntity, String> colTrabajador;
    @FXML
    private TableColumn<ContratoEntity, String> colGranja;
    @FXML
    private TableColumn colFechaCon;
    @FXML
    private TableColumn colSalario;
    @FXML
    private ComboBox<String> cBoxFiltro;
    @FXML
    private ComboBox cBoxOpcion;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView tablaContratos;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnDespedir;
    @FXML
    private Button btnContratar;

    private ObservableList<ContratoEntity> contratos;

    private ObservableList opciones;

    /**
     * El metodo que indica el stage.
     *
     * @param stage1
     */
    public void setStage(Stage stage1) {
        stage = stage1;
    }

    /**
     * El metodo que instancia la ventana. Se añaden tanto el icono como el
     * titulo y los listener de los metodos.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setResizable(false);

        stage.setTitle("Contratos");
        stage.setScene(scene);
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        // Listener de los campos
        contratoManager = new ContratoManagerImplementation();
        tablaContratos.setEditable(true);
        colTrabajador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrabajador().getUsername()));
        colGranja.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGranja().getNombreGranja()));
        colFechaCon.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colSalario.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        cBoxFiltro.setItems(FXCollections.observableArrayList(
                "Trabajador",
                "Granja",
                "Todos"
        ));

        cBoxFiltro.getSelectionModel().selectedItemProperty().addListener(this::handleCambioFiltro);
        cBoxOpcion.getSelectionModel().selectedItemProperty().addListener(this::handleOpcionSeleccionada);
        btnBuscar.setOnAction(this::buscarContratos);
        btnContratar.setOnAction(this::contratarTrabajador);
        btnInsert.setOnAction(this::insertarFila);

        btnDespedir.setOnAction(this::despedirTrabajador);
        // Metodo de vinculacion de campos
        stage.setOnCloseRequest(this::confirmClose);
        colSalario.setOnEditCommit(this::modificarSalario);

        cambiarFormatoFecha();
        // Control de seleccion de fila
        tablaContratos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnDespedir.setDisable(false);
            } else {
                btnDespedir.setDisable(true);
            }
        });
        stage.show();

    }

    /**
     * Control de Cambio de filtro para la comboBox
     *
     * @param ov
     * @param event
     */
    @FXML
    public void handleCambioFiltro(ObservableValue ov, String oldValue, String newValue) {
        if (newValue != null) {

            String filtro = cBoxFiltro.getValue();
            TrabajadorClient webClientTrabajador = new TrabajadorClient();
            btnBuscar.setDisable(true);
            cBoxOpcion.setDisable(false);
            cBoxOpcion.setItems(null);
            Collection lista = null;
            switch (filtro) {
                case ("Trabajador"):

                    // En proceso
                    lista = webClientTrabajador.findAll(new GenericType<List<TrabajadorEntity>>() {
                    });
                    cargarDatosFiltrado(lista);
                    break;
                case ("Granja"):
                    break;
                case ("Todos"):
                    btnBuscar.setDisable(false);
                    cBoxOpcion.setDisable(true);
                    break;
            }
        }
    }

    /**
     * Carga las opciones en la comboBox
     *
     * @param lista
     */
    public void cargarDatosFiltrado(Collection lista) {
        opciones = FXCollections.observableArrayList(lista);
        conversorComboOpcion(cBoxFiltro.getValue());
        cBoxOpcion.setItems(opciones);
        cBoxOpcion.getSelectionModel().selectFirst();

    }

    /**
     * Conversor de los datos que se muestran en la comboBox, haciendo que se
     * muestre un dato especifico
     *
     * @param filtro
     */
    public void conversorComboOpcion(String filtro) {
        switch (filtro) {
            case ("Trabajador"):
                cBoxOpcion.setConverter(new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return ((TrabajadorEntity) object).getUsername();
                    }

                    @Override
                    public Object fromString(String string) {
                        return null;
                    }
                });
                break;
            case ("Granja"):
                cBoxOpcion.setConverter(new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return ((GranjaEntity) object).getNombreGranja();
                    }

                    @Override
                    public Object fromString(String string) {
                        return null;
                    }
                });

        }

    }

    /**
     * En proceso.
     *
     * @param ov
     * @param oldValue
     * @param newValue
     */
    @FXML
    public void handleOpcionSeleccionada(ObservableValue ov, Object oldValue, Object newValue) {
        if (newValue != null) {
            btnBuscar.setDisable(false);
            System.out.println(ov.toString());
            cBoxOpcion.setValue(cBoxOpcion.getSelectionModel().getSelectedItem());
            System.out.println(cBoxOpcion.getValue());

        }
    }

    /**
     * Ejecuta una busqueda en la base de datos en base al filtro y al campo
     *
     * @param event
     */
    @FXML
    public void buscarContratos(ActionEvent event) {
        Collection<ContratoEntity> contratos = null;
        String id;
        switch (cBoxFiltro.getValue()) {
            case ("Trabajador"):
                id = String.valueOf(((TrabajadorEntity) cBoxOpcion.getSelectionModel().getSelectedItem()).getId());
                contratos = contratoManager.getContratosTrabajador(id);
                break;
            case ("Granja"):
                break;
            case ("Todos"):
                contratos = contratoManager.getAllContratos();
                // contratos = contratoManager.getContratosGranjero("2");
                break;

        }
        cargarDatos(contratos);
    }

    /**
     * Carga los datos buscados en la tabla
     *
     * @param contratosList
     */
    public void cargarDatos(Collection<ContratoEntity> contratosList) {

        contratos = FXCollections.observableArrayList(contratosList);

        tablaContratos.setItems(contratos);
        cBoxOpcion.getSelectionModel().clearSelection();
    }

    @FXML
    public void contratarTrabajador(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/ContratarTrabajador.fxml"));
            // Se carga el FXML de Session
            Parent root;

            root = (Parent) loader.load();

            LOGGER.info("Llamada al controlador del FXML");
            // Se recoge el controlador del FXML
            ContratarTrabajadorController controller = ((ContratarTrabajadorController) loader.getController());
            controller.setStage(stage);
            LOGGER.info("Inicio del stage de Session");
            controller.initStage(root);

            //  paneVentana.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Alert de confirmacion de cerrado de programa. Tendras la opcion de elegir
     * si deseas cerrarlo o no.
     *
     * @param event Pulsacion del evento de cerrado.
     */
    public void confirmClose(Event event) {
        LOGGER.info("Creacion de alert de confirmacion");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estas seguro de que quieres salir del programa?");
        Button btnClose = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        btnClose.setText("Salir");
        // Muestra el alert a la espera de la pulsacion de un boton del alert.
        Optional<ButtonType> close = alert.showAndWait();
        if (!ButtonType.OK.equals(close.get())) {
            event.consume();
        }

    }

    @FXML
    public void insertarFila(ActionEvent event) {

        tablaContratos.getItems().add(new ContratoEntity());
        tablaContratos.getSelectionModel().focus(tablaContratos.getItems().size());
    }

    @FXML
    public void modificarSalario(Event event) {
        ContratoEntity contrato = (ContratoEntity) ((TableColumn.CellEditEvent) event).getRowValue();
        String salario = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
        contratoManager.cambiarSueldo(String.valueOf(contrato.getIdContrato().getTrabajadorId()),
                String.valueOf(contrato.getIdContrato().getGranjaId()), salario);

    }

    @FXML
    public void despedirTrabajador(ActionEvent event) {
        ContratoEntity contrato;
        contrato = (ContratoEntity) tablaContratos.getSelectionModel().getSelectedItem();
        contratoManager.despedirTrabajador(String.valueOf(contrato.getIdContrato().getTrabajadorId()),
                String.valueOf(contrato.getIdContrato().getGranjaId()));
        buscarContratos(event);
    }

    public void cambiarFormatoFecha() {
        colFechaCon.setCellFactory(column -> {
            TableCell<Object, Date> cell;
            cell = new TableCell<Object, Date>() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        setText(dateFormat.format(item));
                    }
                }
            };
            return cell;
        });
    }

}

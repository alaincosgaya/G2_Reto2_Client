package controladores;

import clases.ContratoEntity;
import clases.ContratoId;
import clases.DateEditingCell;
import clases.GranjaEntity;
import clases.TrabajadorEntity;
import clases.UserEntity;
import clases.UserPrivilegeType;
import clases.ZonaEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import excepciones.CrearContratoException;
import excepciones.ZonasDesasignarException;
import static factoria.ContratoManagerFactory.getContratoManagerImplementation;
import factoria.GranjaManagerImplementation;
import static factoria.TrabajadorManagerFactory.getTrabajadorManagerImplementation;
import static factoria.ZonaManagerFactory.getZonaManagerImplementation;
import interfaces.ContratoInterface;
import interfaces.GranjaInterface;
import interfaces.TrabajadorInterface;
import interfaces.ZonaInterface;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.LongStringConverter;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import restful.UserClient;

/**
 * El controlador de la ventana de Contratos la cual controla las excepciones y
 * las acciones de los botones y los textos.
 *
 * @author Alain Cosgaya
 */
public class ContratosController {

    private static final Logger LOGGER = Logger.getLogger(ContratosController.class.getName());

    private Stage stage;
    /**
     * Implementaciones que va a utilizar.
     */
    private ContratoInterface contratoManager;

    private TrabajadorInterface trabajadorManager;

    private GranjaInterface granjaManager;

    private ZonaInterface zonaManager;

    /**
     * Atributos FXML.
     */
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
    @FXML
    private Button btnInforme;
    /**
     * Atributos a utilizar.
     */

    private ObservableList<ContratoEntity> contratos;

    private ObservableList granjas;

    private ObservableList trabajadores;

    private ContratoEntity contratoInsert;

    private ContratoId idContrato;

    private boolean contratar;

    private ObservableList opciones;

    private String idBusqueda;
    private UserEntity user;

    private boolean boolTrabajador, boolGranja, boolSalario, boolFecha;

    /**
     * El metodo que indica el stage.
     *
     * @param stage1
     */
    public void setStage(Stage stage1) {
        stage = stage1;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * El metodo que instancia la ventana. Se a??aden tanto el icono como el
     * titulo y los listener de los metodos.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setResizable(false);

        stage.setTitle("Contratos");
        stage.setScene(scene);
        LOGGER.log(Level.INFO, "Llamada a los metodos y restricciones del controlador");
        contratar = false;
        // Uso de factorias para recuperar implementaciones.
        contratoManager = getContratoManagerImplementation();
        trabajadorManager = getTrabajadorManagerImplementation();
        granjaManager = new GranjaManagerImplementation();
        zonaManager = getZonaManagerImplementation();
        tablaContratos.setEditable(true);

        // Vincular columnas con el valor correspondiente.
        colTrabajador.setCellValueFactory(new PropertyValueFactory<>("trabajador"));
        colGranja.setCellValueFactory(new PropertyValueFactory<>("granja"));

        colFechaCon.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colSalario.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        // Definir opciones de filtrado.
        cBoxFiltro.setItems(FXCollections.observableArrayList(
                "Trabajador",
                "Granja",
                "Todos"
        ));

        // Listener de las ComboBox.
        cBoxFiltro.getSelectionModel().selectedItemProperty().addListener(this::handleCambioFiltro);
        cBoxOpcion.getSelectionModel().selectedItemProperty().addListener(this::handleOpcionSeleccionada);

        // Definir la factoria de la celda utilizando una clase creada, para convertir las celdas en DatePicker
        colFechaCon.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                DateEditingCell dateEdit = new DateEditingCell();

                return dateEdit;
            }
        });
        // Listener de los botones.
        btnBuscar.setOnAction(this::btnBuscarContratos);
        btnContratar.setOnAction(this::contratarTrabajador);
        btnInsert.setOnAction(this::insertarFila);
        btnDespedir.setOnAction(this::despedirTrabajador);
        btnInforme.setOnAction(this::imprimirInforme);
        // Confirmacion de cerrado
        stage.setOnCloseRequest(this::confirmClose);
        // Listener de cambios en las celdas de la tabla.
        colSalario.setOnEditCommit(this::modificarSalario);
        colTrabajador.setOnEditCommit(this::guardarTrabajador);
        colGranja.setOnEditCommit(this::guardarGranja);
        colFechaCon.setOnEditCommit(this::guardarFecha);
        // Listener de cancelacion de cambios
        colSalario.setOnEditCancel(this::cancelarEdicion);
        colTrabajador.setOnEditCancel(this::cancelarEdicion);
        colGranja.setOnEditCancel(this::cancelarEdicion);
        colFechaCon.setOnEditCancel(this::cancelarEdicion);

        colFechaCon.setEditable(false);

        // Control de seleccion de fila
        tablaContratos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                LOGGER.log(Level.INFO, "Fila seleccionada.");
                btnDespedir.setDisable(false);
            } else {
                LOGGER.log(Level.INFO, "Fila deseleccionada.");
                btnDespedir.setDisable(true);
            }
        });
        tipoUsuario(user);

        stage.show();

    }

    /**
     * Control de Cambio de filtro para la comboBox. En base a la opcion
     * seleccionada, carga los datos en la comboBox
     *
     * @param ov Valor observable.
     */
    @FXML
    public void handleCambioFiltro(ObservableValue ov, String oldValue, String newValue) {
        LOGGER.log(Level.INFO, "Comprobando opcion de filtrado seleccionada");
        // Comprueba que el campo seleccionado no es nulo.
        if (newValue != null) {
            try {
                String filtro = cBoxFiltro.getValue();

                btnBuscar.setDisable(true);
                cBoxOpcion.setDisable(false);
                cBoxOpcion.setItems(null);
                Collection lista = null;
                switch (filtro) {
                    case ("Trabajador"):

                        // Carga todos los trabajadores
                        lista = trabajadorManager.getAllTrabajadores();
                        cargarDatosFiltrado(lista);
                        break;
                    case ("Granja"):
                        // Carga todas los granjas.
                        lista = granjaManager.getAllGranjas();
                        cargarDatosFiltrado(lista);
                        break;
                    case ("Todos"):
                        btnBuscar.setDisable(false);
                        cBoxOpcion.setDisable(true);
                        break;
                }
            } catch (ClienteServidorConexionException ex) {
                alertErrores("Debido a un problema al conectarse al servidor,"
                        + " no se han podido cargar las opciones de filtrado. En el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);

            } catch (BDServidorException ex) {
                alertErrores("Debido a un problema del servidor,"
                        + " no se han podido cargar las opciones de filtrado. En el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Carga las opciones en la comboBox.
     *
     * @param lista
     */
    public void cargarDatosFiltrado(Collection lista) {
        LOGGER.log(Level.INFO, "Cargando datos en la combobox de opciones");
        opciones = FXCollections.observableArrayList(lista);

        cBoxOpcion.setItems(opciones);
        // Deja seleccionado la primera opcion.
        cBoxOpcion.getSelectionModel().selectFirst();

    }

    /**
     * Comprueba si ha sido seleccionado algun elemento. En base a ello,
     * habilita o deshabilita el boton de busqueda.
     *
     * @param ov
     * @param oldValue
     * @param newValue
     */
    @FXML
    public void handleOpcionSeleccionada(Observable ov) {
        LOGGER.info("Opcion seleccionada");

        if (((ObservableValue) ov).getValue() != null) {
            btnBuscar.setDisable(false);
            cBoxOpcion.setValue(cBoxOpcion.getSelectionModel().getSelectedItem());
            String filtro = cBoxFiltro.getValue();
            switch (filtro) {
                case ("Trabajador"):
                    idBusqueda = String.valueOf(((TrabajadorEntity) cBoxOpcion.getSelectionModel().getSelectedItem()).getId());
                    break;
                case ("Granja"):
                    idBusqueda = String.valueOf(((GranjaEntity) cBoxOpcion.getSelectionModel().getSelectedItem()).getIdGranja());
                    break;
            }
            LOGGER.log(Level.INFO, "Id del objeto seleccionado: {0}", idBusqueda);

        }
    }

    /**
     * Ejecuta el metodo de buscarContratos.
     *
     * @param event
     */
    @FXML
    public void btnBuscarContratos(ActionEvent event) {
        LOGGER.log(Level.INFO, "Boton de busqueda de contratos pulsado.");
        buscarContratos();

    }

    /**
     * Carga los datos buscados en la tabla
     *
     * @param contratosList
     */
    public void cargarDatos(Collection<ContratoEntity> contratosList) {
        LOGGER.log(Level.INFO, "Cargando datos en la tabla");
        contratos = FXCollections.observableArrayList(contratosList);

        tablaContratos.setItems(contratos);
        cBoxOpcion.getSelectionModel().clearSelection();
        btnBuscar.setDisable(true);
    }

    /**
     * Abre la ventana de contratar Trabajador.
     *
     * @param event
     */
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
            LOGGER.info("Inicio del stage de Contratar trabajador");
            controller.setUser(user);
            controller.initStage(root);

            //  paneVentana.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(ContratosController.class
                    .getName()).log(Level.SEVERE, null, ex);
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "??Estas seguro de que quieres salir del programa?");
        Button btnClose = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        btnClose.setText("Salir");
        // Muestra el alert a la espera de la pulsacion de un boton del alert.
        Optional<ButtonType> close = alert.showAndWait();
        if (!ButtonType.OK.equals(close.get())) {
            event.consume();
        }

    }

    /**
     * Metodo para la insercion de filas para la creacion de contratos. En el
     * caso de que se haya pulsado por primera vez, se insertara una fila a la
     * tabla, habilitando la edicion de estos. En el caso de que se pulse el
     * bot??n por segunda vez, se insertara el contrato con los parametros
     * establecidos.
     *
     * @param event
     */
    @FXML
    public void insertarFila(ActionEvent event) {
        // Comprueba si se ha insertado una fila previamente.
        if (!contratar) {
            // Habilitaria la edicion de campos e iniciaria la insercion de datos.
            LOGGER.log(Level.INFO, "Inicio de insercion de fila para contrato");
            boolFecha = false;
            boolGranja = false;
            boolSalario = false;
            boolTrabajador = false;
            contratar = true;
            colFechaCon.setEditable(true);
            colTrabajador.setEditable(true);
            colGranja.setEditable(true);
            contratoInsert = new ContratoEntity();
            idContrato = new ContratoId();
            // Cargaria los datos en las columnas de trabajadores y granjas.
            cargarTrabajadoresColumn();
            cargarGranjasColumn();

            tablaContratos.getItems().add(new ContratoEntity());
            tablaContratos.getSelectionModel().selectLast();
            tablaContratos.layout();
            tablaContratos.edit(tablaContratos.getSelectionModel().getSelectedIndex(), colTrabajador);
        } else {
            try {
                // Deshabilitaria la edicion de campos y ejecutaria el metodo de contratar.
                LOGGER.log(Level.INFO, "Creacion de contrato en base a los datos");
                contratar = false;
                if (!boolFecha || !boolTrabajador || !boolGranja || !boolSalario) {
                    throw new CrearContratoException("No se han definido todas las opciones");
                }
                contratoInsert.setIdContrato(idContrato);
                contratoManager.contratarTrabajador(contratoInsert);
                colFechaCon.setEditable(false);
                colTrabajador.setEditable(false);
                colGranja.setEditable(false);
                buscarContratos();
                tablaContratos.refresh();
            } catch (ClienteServidorConexionException ex) {
                alertErrores("Debido a un problema al conectarse al servidor,"
                        + " no se ha podido crear el contrato. En el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);

            } catch (BDServidorException ex) {
                alertErrores("Debido a un problema del servidor,"
                        + " no se ha podido crear el contrato. En el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);

            } catch (CrearContratoException ex) {
                alertErrores("No se han definido todos los campos a la hora de contratar al trabajador");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
                buscarContratos();
            }
        }
    }

    /**
     * Metodo para la modificacion del salario definido para un contrato. Se
     * comprobaria si se ha realizado una pulsacion previa del boton de
     * insercion de fila, para la modificacion o guardado del salario definido.
     *
     * @param event
     */
    @FXML
    public void modificarSalario(Event event) {
        // Comprobar si se ha iniciado una insercion de contratos.
        LOGGER.log(Level.INFO, "Comprobando creacion o modificacion");
        if (!contratar) {
            try {
                LOGGER.log(Level.INFO, "Modificando salario");
                // Recupera el valor de la fila seleccionada.
                ContratoEntity contrato = (ContratoEntity) ((TableColumn.CellEditEvent) event).getRowValue();
                // Recupera el valor del salario introducido.
                String salario = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
                contratoManager.cambiarSueldo(String.valueOf(contrato.getIdContrato().getTrabajadorId()),
                        String.valueOf(contrato.getIdContrato().getGranjaId()), salario);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Salario modificado correctamente");
                alert.showAndWait();
            } catch (ClienteServidorConexionException ex) {
                alertErrores("Debido a un problema al conectarse al servidor,"
                        + " no se ha podido modificar el salario. En el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BDServidorException ex) {
                alertErrores("Debido a un problema del servidor,"
                        + " no se ha podido modificar el salario. En el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Guardaria el nuevo salario.
            LOGGER.log(Level.INFO, "Guardando salario del contrato");
            contratoInsert.setSalario((Long) ((TableColumn.CellEditEvent) event).getNewValue());
            boolSalario = true;
        }

    }

    /**
     * Metodo para la realizacion del despido de un trabajador. Se avisaria con
     * un Alert al usuario antes de hacer el despido. En el caso de que este de
     * acuerdo, se realizaria una comprobacion de las zonas, comprobando si esta
     * en alguna zona asignado. En el caso de que este asignado, se le avisaria
     * al usuario y se cancelaria el despido.
     *
     * @param event
     */
    @FXML
    public void despedirTrabajador(ActionEvent event) {
        try {
            LOGGER.log(Level.INFO, "Iniciando despido del trabajador.");
            ContratoEntity contrato;
            // Recupera el contrato seleccionado
            contrato = (ContratoEntity) tablaContratos.getSelectionModel().getSelectedItem();
            Alert alertDespido = new Alert(Alert.AlertType.CONFIRMATION, "??Estas seguro de que quiere despedir al trabajador?");
            Button btnClose = (Button) alertDespido.getDialogPane().lookupButton(ButtonType.OK);
            btnClose.setText("Despedir");
            // Muestra el alert a la espera de la pulsacion de un boton del alert.
            Optional<ButtonType> close = alertDespido.showAndWait();
            if (!ButtonType.OK.equals(close.get())) {
                event.consume();
                LOGGER.log(Level.INFO, "Despido cancelado");
            } else {
                // Comprobaria las zonas
                Collection<ZonaEntity> zonas = null;
                LOGGER.log(Level.INFO, "Iniciando comprobaci??n de zonas");
                zonas = zonaManager.getZonasPorTrabajador(contrato.getTrabajador().getUsername());
                if (zonas.isEmpty()) {
                    contratoManager.despedirTrabajador(String.valueOf(contrato.getIdContrato().getTrabajadorId()),
                            String.valueOf(contrato.getIdContrato().getGranjaId()));
                    buscarContratos();
                    LOGGER.log(Level.INFO, "Despido completado");
                } else {
                    LOGGER.log(Level.WARNING, "Despido no completado, zonas por desasignar");
                    throw new ZonasDesasignarException("Hay zonas sin desasignar.");

                }
            }

        } catch (ClienteServidorConexionException ex) {
            alertErrores("Debido a un problema al conectarse al servidor,"
                    + " no se ha podido realizar el despido. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            alertErrores("Debido a un problema del servidor,"
                    + " no se ha podido realizar el despido. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ZonasDesasignarException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No se ha podido despedir al trabajador.");
            alert.setContentText("Debido a que el trabajador esta asignado a alguna zona, no se ha podido realizar el despido."
                    + " Asegurese de que lo ha desasignado de las zonas antes de volver a intentar despedirlo.");
            alert.showAndWait();

        }
    }

    /**
     * Metodo para el cargado de trabajadores en una combobox de una columna de
     * la tabla.
     */
    public void cargarTrabajadoresColumn() {

        try {
            LOGGER.log(Level.INFO, "Cargando datos en columna de trabajador.");
            trabajadores = FXCollections.observableArrayList(getTrabajadorManagerImplementation().getAllTrabajadores());
            colTrabajador.setCellFactory(ComboBoxTableCell.forTableColumn(trabajadores));
        } catch (ClienteServidorConexionException ex) {
            alertErrores("Debido a un problema al conectarse al servidor,"
                    + " no se han podido cargar los trabajadores. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            alertErrores("Debido a un problema del servidor,"
                    + " no se han podido cargar los trabajadores. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo para el cargado de granjas en una combobox de una columna de la
     * tabla.
     */
    public void cargarGranjasColumn() {
        try {
            LOGGER.log(Level.INFO, "Cargando datos en columna de granjas");
            granjas = FXCollections.observableArrayList(granjaManager.getGranjasPorGranjero(user.getUsername()));
            colGranja.setCellFactory(ComboBoxTableCell.forTableColumn(granjas));

        } catch (ClienteServidorConexionException ex) {
            alertErrores("Debido a un problema al conectarse al servidor,"
                    + " no se han podido cargar las granjas. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            alertErrores("Debido a un problema del servidor,"
                    + " no se han podido cargar las granjas. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Guardado del trabajador para la creaci??n del contrato.
     *
     * @param event
     */
    public void guardarTrabajador(Event event) {
        LOGGER.log(Level.INFO, "Guardando trabajador para el contrato.");
        contratoInsert.setTrabajador((TrabajadorEntity) ((TableColumn.CellEditEvent) event).getNewValue());
        idContrato.setTrabajadorId(((TrabajadorEntity) ((TableColumn.CellEditEvent) event).getNewValue()).getId());
        boolTrabajador = true;

    }

    /**
     * Guaraddo de la granja para la creacion del contrato.
     *
     * @param event
     */
    public void guardarGranja(Event event) {
        LOGGER.log(Level.INFO, "Guardando granja para el contrato");
        contratoInsert.setGranja((GranjaEntity) ((TableColumn.CellEditEvent) event).getNewValue());
        idContrato.setTrabajadorId(((GranjaEntity) ((TableColumn.CellEditEvent) event).getNewValue()).getIdGranja());
        boolGranja = true;

    }

    /**
     * Guardado de la fecha para la creacion del contrato.
     *
     * @param event
     */
    public void guardarFecha(Event event) {
        LOGGER.log(Level.INFO, "Guardando nueva fecha de contratacion.");
        contratoInsert.setFechaContratacion((Date) ((TableColumn.CellEditEvent) event).getNewValue());
        boolFecha = true;

    }

    /**
     * Cancelar edicion deshabilitando los habilitados previamente.
     *
     * @param event
     */
    public void cancelarEdicion(Event event) {
        LOGGER.log(Level.INFO, "Creacion del contrato cancelada.");
        contratar = false;
        colFechaCon.setEditable(false);
        colTrabajador.setEditable(false);
        colGranja.setEditable(false);

        tablaContratos.refresh();

    }

    /**
     * Ejecuta una busqueda en la base de datos en base al filtro y al campo.
     * Establece una conexion con el servidor a traves del uso del REST.
     */
    private void buscarContratos() {
        Collection<ContratoEntity> contratos = null;
        LOGGER.log(Level.INFO, "Comprobando si se ha seleccionado algun tipo de filtro.");

        if (!cBoxFiltro.getSelectionModel().isEmpty()) {
            try {
                LOGGER.log(Level.INFO, "Realizando busqueda de contratos.");

                switch (cBoxFiltro.getValue()) {
                    case ("Trabajador"):
                        // Recupera los contratos del trabajador.

                        contratos = contratoManager.getContratosTrabajador(idBusqueda);
                        break;
                    case ("Granja"):
                        // Recupera los contratos de la granja.

                        contratos = contratoManager.getContratosGranja(idBusqueda);

                        break;
                    case ("Todos"):
                        // Recupera todos los contratos.
                        contratos = contratoManager.getAllContratos();
                        // contratos = contratoManager.getContratosGranjero("2");
                        break;

                }
                cargarDatos(contratos);
            } catch (ClienteServidorConexionException ex) {
                alertErrores("Debido a un problema al conectarse al servidor,"
                        + " no se ha podido realizar la busqueda de contratos, en el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BDServidorException ex) {
                alertErrores("Debido a un problema del servidor,"
                        + " no se ha podido realizar la busqueda de contratos, en el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Compara si el usuario seleccionado es un granjero o un trabajador. En
     * base al tipo habilita y deshabilita funciones de la ventana.
     *
     * @param user
     */
    public void tipoUsuario(UserEntity user) {
        try {
            LOGGER.log(Level.INFO, "Ajustando permisos de acceso en base al usuario.");
            if (user.getUserPrivilege().equals(UserPrivilegeType.TRABAJADOR)) {
                LOGGER.log(Level.INFO, "Usuario de tipo Trabajador");
                btnContratar.setVisible(false);
                btnDespedir.setVisible(false);
                btnInsert.setVisible(false);
                tablaContratos.setEditable(false);
                tablaContratos.setItems(FXCollections.observableArrayList(
                        contratoManager.getContratosTrabajador(
                                String.valueOf(user.getId()))));
            } else {
                LOGGER.log(Level.INFO, "Usuario de tipo Granjero");
                tablaContratos.setItems(FXCollections.observableArrayList(
                        contratoManager.getContratosGranjero(
                                String.valueOf(user.getId()))));
            }
        } catch (ClienteServidorConexionException ex) {
            alertErrores("Debido a un problema al conectarse al servidor,"
                    + " no se han podido cargar los contratos en la tabla, en el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            alertErrores("Debido a un problema del servidor,"
                    + " no se han podido cargar los contratos en la tabla, en el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para la impresion del informe de los datos de la tabla.
     *
     * @param event
     */
    public void imprimirInforme(ActionEvent event) {
        try {
            LOGGER.log(Level.INFO, "Imprimiendo informe de los datos de la tabla.");
            // Compila el informe, en base a la ruta establecida.
            JasperReport report = JasperCompileManager.compileReport("src/informes/informeContratos.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<ContratoEntity>) this.tablaContratos.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            alertErrores("Debido a un problema de la aplicacion no se ha podido imprimir el informe.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Alert que se le mostrara al usuario
     *
     * @param message
     */
    public void alertErrores(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

}

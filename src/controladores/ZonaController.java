/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clases.AnimalEntity;
import clases.DateEditingCell;
import clases.GranjaEntity;
import clases.TipoAnimal;
import clases.TrabajadorEntity;
import clases.UserEntity;
import clases.UserPrivilegeType;
import clases.ZonaEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import factoria.GranjaManagerImplementation;
import static factoria.TrabajadorManagerFactory.getTrabajadorManagerImplementation;
import factoria.TrabajadorManagerImplementation;
import static factoria.ZonaManagerFactory.getZonaManagerImplementation;
import factoria.ZonaManagerImplementation;
import interfaces.GranjaInterface;
import interfaces.TrabajadorInterface;
import interfaces.ZonaInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import restful.GranjaClient;
import restful.ZonaClient;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class ZonaController {

    private static final Logger LOGGER = Logger.getLogger(ZonaController.class.getName());

    private Stage stage;
    private ZonaInterface zonaManager;
    private GranjaInterface granjaManager;
    private TrabajadorInterface trabajadorManager;
    @FXML
    private BorderPane paneZona;
    @FXML
    private TableColumn colNombreZona;
    @FXML
    private TableColumn colFechaCreacion;
    @FXML
    private TableColumn<GranjaEntity, String> colGranja;
    @FXML
    private ComboBox<String> cBoxFiltro;
    @FXML
    private ComboBox cBoxOpcion;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView tablaZona;
    @FXML
    private Button btnPlus;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnDesasignar;
    @FXML
    private Button btnAsignar;
    @FXML
    private Button btnInforme;

    private TrabajadorEntity trabajadorDesasignar;
    private ObservableList granjas;
    private ObservableList opciones;
    private ObservableList<ZonaEntity> zonas;
    private boolean crearZona;
    private ZonaEntity zonaCrear;

    private UserEntity usr;

    public void setUser(UserEntity user) {
        this.usr = user;
    }

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
        Collection<ZonaEntity> zonas = null;
        Scene scene = new Scene(root);
        stage.setResizable(false);

        stage.setTitle("Zona");
        stage.setScene(scene);
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        // Listener de los campos
        zonaManager = getZonaManagerImplementation();
        trabajadorManager = getTrabajadorManagerImplementation();
        granjaManager = new GranjaManagerImplementation();
        tablaZona.setEditable(true);
        zonas = zonaManager.getAllZonas();
        cargarDatos(zonas);
        crearZona = false;
        colGranja.setCellValueFactory(new PropertyValueFactory<>("granja"));

        colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fechaCreacionZona"));
        colNombreZona.setCellValueFactory(new PropertyValueFactory<>("nombreZona"));
        cBoxFiltro.setItems(FXCollections.observableArrayList(
                "Nombre",
                "Tipo Animal",
                "Granja",
                "Trabajador",
                "Todos"
        ));

        cBoxFiltro.getSelectionModel().selectedItemProperty().addListener(this::handleCambioFiltro);
        cBoxOpcion.getSelectionModel().selectedItemProperty().addListener(this::handleOpcionSeleccionada);
        btnDesasignar.setVisible(false);
        btnAsignar.setDisable(true);
        btnEliminar.setDisable(true);
        btnBuscar.setOnAction(this::buscarZonas);

        btnAnadir.setOnAction(this::anadirZona);
        btnAsignar.setOnAction(this::asignarTrabajador);
        btnDesasignar.setOnAction(this::desasignarTrabajador);
        btnEliminar.setOnAction(this::eliminarZona);
        btnInforme.setOnAction(this::generarInforme);
        btnPlus.setOnAction(this::insertarFila);

        // Metodo de vinculacion de campos
        stage.setOnCloseRequest(this::confirmClose);
        colNombreZona.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombreZona.setOnEditCommit(this::modificarNombre);
        colFechaCreacion.setOnEditCommit(this::modificarFecha);
        colFechaCreacion.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                DateEditingCell dateEdit = new DateEditingCell();

                return dateEdit;
            }
        });

        colGranja.setOnEditCommit(this::guardarGranja);

        //cambiarFormatoFecha();
        // Control de seleccion de fila
        tablaZona.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnDesasignar.setDisable(false);
                btnAsignar.setDisable(false);
                btnEliminar.setDisable(false);
            } else {
                btnDesasignar.setDisable(true);
                btnAsignar.setDisable(true);
                btnEliminar.setDisable(true);
            }
        });
        tipoUsuario(usr);
        stage.show();

    }

    /**
     * Metodo el cual, mediante la pulsacion del boton informe, se encargara de
     * generar un informe con todos los datos de la tabla animal
     *
     * @param event
     */
    @FXML
    private void generarInforme(ActionEvent event) {
        JasperReport report;
        try {
            LOGGER.info("Generando el informe con los datos actualmente introducidos");
            report = JasperCompileManager.compileReport("src/vistas/InformeZona.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<ZonaEntity>) this.tablaZona.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ZonaController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

            try {
                String filtro = cBoxFiltro.getValue();

                btnBuscar.setDisable(true);
                cBoxOpcion.setDisable(false);
                cBoxOpcion.setItems(null);
                Collection lista = null;

                switch (filtro) {
                    case ("Nombre"):
                        lista = zonaManager.getAllZonas();
                        cargarDatosFiltrado(lista);
                        break;
                    case ("Tipo Animal"):
                        cBoxOpcion.setItems(FXCollections.observableArrayList(TipoAnimal.values()));
                        break;
                    case ("Granja"):
                        lista = granjaManager.getAllGranjas();
                        cargarDatosFiltrado(lista);
                        break;
                    case ("Trabajador"):

                        lista = trabajadorManager.getAllTrabajadores();

                        cargarDatosFiltrado(lista);
                        break;
                    case ("Todos"):
                        btnBuscar.setDisable(false);
                        cBoxOpcion.setDisable(true);
                        break;
                }
            } catch (ClienteServidorConexionException ex) {
                Logger.getLogger(ZonaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BDServidorException ex) {
                Logger.getLogger(ZonaController.class.getName()).log(Level.SEVERE, null, ex);
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
        //conversorComboOpcion(cBoxFiltro.getValue());
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
            case ("Nombre"):
                cBoxOpcion.setConverter(new StringConverter() {
                    @Override
                    public String toString(Object object) {
                        return ((ZonaEntity) object).getNombreZona();
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
                break;
            default:

                break;

        }

    }

    /**
     * 
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
    public void buscarZonas(ActionEvent event) {
        Collection<ZonaEntity> zonas = null;
        String id;
        switch (cBoxFiltro.getValue()) {
            case ("Nombre"):
                zonas = new ArrayList();
                id = String.valueOf(((ZonaEntity) cBoxOpcion.getSelectionModel().getSelectedItem()).getIdZona());
                zonas.add(zonaManager.getZona(id));
                break;
            case ("Tipo Animal"):
                zonas = zonaManager.getZonasPorAnimal(String.valueOf(cBoxOpcion.getSelectionModel().getSelectedItem()));
                break;
            case ("Granja"):
                zonas = zonaManager.getZonasPorGranja(String.valueOf(((GranjaEntity) cBoxOpcion.getSelectionModel().getSelectedItem()).getIdGranja()));
                break;
            case ("Trabajador"):
                btnDesasignar.setVisible(true);
                trabajadorDesasignar = ((TrabajadorEntity) cBoxOpcion.getSelectionModel().getSelectedItem());
                zonas = zonaManager.getZonasPorTrabajador(String.valueOf(((TrabajadorEntity) cBoxOpcion.getSelectionModel().getSelectedItem())));
                break;
            case ("Todos"):
                zonas = zonaManager.getAllZonas();
                break;

        }
        cargarDatos(zonas);
    }

    /**
     * Carga los datos buscados en la tabla
     *
     * @param contratosList
     */
    public void cargarDatos(Collection<ZonaEntity> zonasList) {

        zonas = FXCollections.observableArrayList(zonasList);

        tablaZona.setItems(zonas);
        cBoxOpcion.getSelectionModel().clearSelection();
    }

    @FXML
    public void insertarFila(ActionEvent event) {
        if (!crearZona) {
            crearZona = true;
            zonaCrear = new ZonaEntity();
            colNombreZona.setCellFactory(TextFieldTableCell.forTableColumn());
            cargarGranjasColumn();
            tablaZona.getItems().add(new ZonaEntity());
            tablaZona.getSelectionModel().select(tablaZona.getItems().size());
        } else {
            crearZona = false;
            zonaManager.crearZona(zonaCrear);
        }

    }

    @FXML
    public void modificarNombre(Event event) {
        String nombre = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
        if (crearZona) {
            zonaCrear.setNombreZona(nombre);
        } else {
            ZonaEntity zona = (ZonaEntity) ((TableColumn.CellEditEvent) event).getRowValue();

            zonaManager.cambiarNombre(String.valueOf(zona.getNombreZona()),
                    nombre);
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

    /**
     * Alert de confirmacion de eliminado de zona
     *
     * @param event
     */
    public void confirmDelete(Event event) {
        LOGGER.info("Creacion de alert de confirmacion");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estas seguro de que quieres eliminar esta zona?");
        Button btnClose = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        btnClose.setText("Eliminar");
        // Muestra el alert a la espera de la pulsacion de un boton del alert.
        Optional<ButtonType> close = alert.showAndWait();
        if (!ButtonType.OK.equals(close.get())) {
            event.consume();
        }

    }

    public void cargarGranjasColumn() {

        try {
            granjas = FXCollections.observableArrayList(granjaManager.getGranjasPorGranjero(usr.getUsername()));
            colGranja.setCellFactory(ComboBoxTableCell.forTableColumn(granjas));
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(ZonaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            Logger.getLogger(ZonaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void asignarTrabajador(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Trabajadores.fxml"));
            // Se carga el FXML de Session
            Parent root;

            root = (Parent) loader.load();

            LOGGER.info("Llamada al controlador del FXML");
            // Se recoge el controlador del FXML
            TrabajadoresController controller = ((TrabajadoresController) loader.getController());
            controller.setStage(stage);
            LOGGER.info("Inicio del stage de Session");
            controller.initStage(root);

            controller.habilitarAsignarTrabajador(true, ((ZonaEntity) tablaZona.getSelectionModel().getSelectedItem()));
            //  paneVentana.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*@FXML
    public void desasignarTrabajador(ActionEvent event) {*/
    //try {
    //controller.habilitarAsignarTrabajador(true, ((ZonaEntity) tablaZona.getSelectionModel().getSelectedItem()));
    /*
            trabajadorManager = new TrabajadorManagerImplementation();
            trabajadorManager.getTrabajadoresZona(((ZonaEntity) tablaZona.getSelectionModel().getSelectedItem()));*/
 /*} catch (IOException ex) {
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    //}
    @FXML
    public void eliminarZona(ActionEvent event) {
        confirmDelete(event);
        ZonaEntity zona = (ZonaEntity) tablaZona.getSelectionModel().getSelectedItem();
        zonaManager.eliminarZona(zona.getIdZona());
        //cargarDatos((Collection<ZonaEntity>) event);
    }

    @FXML
    public void modificarFecha(Event event) {
        Date fechaCreacion = (Date) ((TableColumn.CellEditEvent) event).getNewValue();
        if (crearZona) {
            zonaCrear.setFechaCreacionZona(fechaCreacion);
        } else {
            ZonaEntity zona = (ZonaEntity) ((TableColumn.CellEditEvent) event).getRowValue();
            zona.setFechaCreacionZona(fechaCreacion);
            zonaManager.editarZona(zona, String.valueOf(zona.getIdZona()));

            // zonaManager.
        }
    }

    public void guardarGranja(Event event) {
        GranjaEntity granja = (GranjaEntity) ((TableColumn.CellEditEvent) event).getNewValue();
        if (crearZona) {
            zonaCrear.setGranja(granja);
        }
    }

    /*
    @FXML
    public void guardarGranja(ActionEvent event) {

    }*/
    public void asignarTrabajadores(Collection<TrabajadorEntity> trabajadoresAsignar, ZonaEntity zona) {

        trabajadoresAsignar.stream().forEach((trabajador) -> {

            zonaManager.asignarTrabajador(trabajador.getUsername(), zona.getIdZona());
        }); //  
    }

    public void desasignarTrabajador(ActionEvent event) {
        zonaManager.desasignarTrabajador(trabajadorDesasignar.getUsername(), ((ZonaEntity) tablaZona.getSelectionModel().getSelectedItem()).getIdZona());

    }

    public void anadirZona(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/CrearZona.fxml"));
            // Se carga el FXML de Session
            Parent root;

            root = (Parent) loader.load();

            LOGGER.info("Llamada al controlador del FXML");
            // Se recoge el controlador del FXML
            CrearZonaController controller = ((CrearZonaController) loader.getController());
            controller.setStage(stage);
            LOGGER.info("Inicio del stage de Session");
            controller.initStage(root);

            //  paneVentana.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /**
     * Compara si el usuario seleccionado es un granjero o un trabajador. En
     * base al tipo habilita y deshabilita funciones de la ventana.
     *
     * @param user
     */
    public void tipoUsuario(UserEntity user) {
     
            LOGGER.log(Level.INFO, "Ajustando permisos de acceso en base al usuario.");
            if (user.getUserPrivilege().equals(UserPrivilegeType.TRABAJADOR)) {
                LOGGER.log(Level.INFO, "Usuario de tipo Trabajador");
                btnAnadir.setVisible(false);
                btnAsignar.setVisible(false);
                btnBuscar.setVisible(false);
                btnDesasignar.setVisible(false);
                btnEliminar.setVisible(false);
                btnPlus.setVisible(false);
                tablaZona.setEditable(false);
                tablaZona.setItems(FXCollections.observableArrayList(
                        zonaManager.getZonasPorTrabajador(String.valueOf(user.getId()))));
            } else {
                LOGGER.log(Level.INFO, "Usuario de tipo Granjero");
                tablaZona.setItems(FXCollections.observableArrayList(
                        zonaManager.getAllZonas()));
            }
       
    }

}

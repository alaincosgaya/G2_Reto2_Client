package controladores;

import clases.GranjaEntity;
import clases.TrabajadorEntity;
import clases.ZonaEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import factoria.GranjaManagerImplementation;
import static factoria.TrabajadorManagerFactory.getTrabajadorManagerImplementation;
import static factoria.ZonaManagerFactory.getZonaManagerImplementation;
import factoria.ZonaManagerImplementation;
import interfaces.GranjaInterface;
import interfaces.TrabajadorInterface;
import interfaces.ZonaInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import restful.GranjaClient;

/**
 * El controlador de la ventana de Trabajadores la cual controla las excepciones
 * y las acciones de los botones y los textos.
 *
 * @author Alain Cosgaya
 */
public class TrabajadoresController {

    private static final Logger LOGGER = Logger.getLogger(TrabajadoresController.class.getName());

    private Stage stage;

    private TrabajadorInterface trabajadorManager;
    private ZonaInterface zonaManager;
    private GranjaInterface granjaManager;

    @FXML
    private TableColumn colLogin;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colEmail;

    @FXML
    private ComboBox<String> cBoxFiltro;
    @FXML
    private ComboBox cBoxOpcion;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView tablaTrabajador;

    @FXML
    private Button btnContratar;
    @FXML
    private Button btnAsignar;
    @FXML
    private Button btnInforme;

    private ObservableList<TrabajadorEntity> ObservableTrabajadores;

    private Collection<TrabajadorEntity> trabajadoresAsignar;

    private boolean zonaCreada;

    private ObservableList opciones;

    private ZonaEntity zonaSeleccionada;

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
        trabajadorManager = getTrabajadorManagerImplementation();
        zonaManager = getZonaManagerImplementation();
        granjaManager = new GranjaManagerImplementation();

        tablaTrabajador.setEditable(true);

        // Vincular columnas con el valor correspondiente.
        colLogin.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        colNombre.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        // Definir opciones de filtrado.
        cBoxFiltro.setItems(FXCollections.observableArrayList(
                "Contratados",
                "Contratables",
                "Zona",
                "Todos"
        ));

        // Listener de las ComboBox.
        cBoxFiltro.getSelectionModel().selectedItemProperty().addListener(this::handleCambioFiltro);
        cBoxOpcion.getSelectionModel().selectedItemProperty().addListener(this::handleOpcionSeleccionada);
        // Listener de los botones.
        btnBuscar.setOnAction(this::buscarTrabajadores);
        btnContratar.setOnAction(this::contratarTrabajador);
        btnAsignar.setOnAction(this::asignarZonaTrabajador);
        // Confirmacion de cerrado
        stage.setOnCloseRequest(this::confirmClose);

        // Listener de cambios en las celdas de la tabla.
        // Control de seleccion de fila
        tablaTrabajador.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnAsignar.setDisable(false);
                btnContratar.setDisable(false);
            } else {
                btnAsignar.setDisable(true);
                btnContratar.setDisable(true);
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
    public void handleCambioFiltro(ObservableValue ov, Object oldValue, Object newValue) {
        if (newValue != null) {

            try {
                String filtro = cBoxFiltro.getValue();
                
                GranjaClient webClientGranja = new GranjaClient();
                btnBuscar.setDisable(true);
                cBoxOpcion.setDisable(false);
                cBoxOpcion.setItems(null);
                Collection lista;
                switch (filtro) {
                    case ("Contratados"):
                        
                        // En proceso
                        lista = granjaManager.getAllGranjas();
                        cargarDatosFiltrado(lista);
                        break;
                    case ("Contratables"):
                        
                        lista = granjaManager.getAllGranjas();
                        cargarDatosFiltrado(lista);
                        break;
                        
                    case ("Zona"):
                        lista = zonaManager.getAllZonas();
                        cargarDatosFiltrado(lista);
                        break;
                    case ("Todos"):
                        btnBuscar.setDisable(false);
                        cBoxOpcion.setDisable(true);
                        break;
                        
                }
            } catch (ClienteServidorConexionException ex) {
                Logger.getLogger(TrabajadoresController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BDServidorException ex) {
                Logger.getLogger(TrabajadoresController.class.getName()).log(Level.SEVERE, null, ex);
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
        //  conversorComboOpcion(cBoxFiltro.getValue());
        cBoxOpcion.setItems(opciones);
        cBoxOpcion.getSelectionModel().selectFirst();

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
    public void buscarTrabajadores(ActionEvent event) {
        try {
            Collection<TrabajadorEntity> trabajadores = null;
            String id;
            switch (cBoxFiltro.getValue()) {
                case ("Contratados"):
                    id = String.valueOf(((GranjaEntity) cBoxOpcion.getSelectionModel().getSelectedItem()).getIdGranja());
                    trabajadores = trabajadorManager.getTrabajadoresGranja(id);
                    break;
                case ("Contratables"):
                    id = String.valueOf(((GranjaEntity) cBoxOpcion.getSelectionModel().getSelectedItem()).getIdGranja());
                    trabajadores = trabajadorManager.getTrabajadoresPorContratar(id);
                    break;

                case ("Zona"):
                    id = String.valueOf(((ZonaEntity) cBoxOpcion.getSelectionModel().getSelectedItem()).getIdZona());
                    trabajadores = trabajadorManager.getTrabajadoresZona(id);
                    break;
                case ("Todos"):
                    trabajadores = trabajadorManager.getAllTrabajadores();
                    break;

            }
            cargarDatos(trabajadores);
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(TrabajadoresController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            Logger.getLogger(TrabajadoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Carga los datos buscados en la tabla
     *
     * @param trabajadoresList
     */
    public void cargarDatos(Collection<TrabajadorEntity> trabajadoresList) {

        ObservableTrabajadores = FXCollections.observableArrayList(trabajadoresList);

        tablaTrabajador.setItems(ObservableTrabajadores);
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
            controller.trabajadorSeleccionado((TrabajadorEntity) tablaTrabajador.getSelectionModel().getSelectedItem(), true);
            controller.initStage(root);

            //  paneVentana.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(TrabajadoresController.class.getName()).log(Level.SEVERE, null, ex);
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

    public void asignarZonaTrabajador(ActionEvent event) {
        trabajadoresAsignar = new ArrayList();

        trabajadoresAsignar.add((TrabajadorEntity) tablaTrabajador.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Â¿Quiere seguir asignando trabajadores a la zona?");
        Button btnClose = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        btnClose.setText("Volver");
        // Muestra el alert a la espera de la pulsacion de un boton del alert.
        Optional<ButtonType> close = alert.showAndWait();
        if (!ButtonType.OK.equals(close.get())) {
            event.consume();
        } else {
            volverZonas();
        }
    }

    public void habilitarAsignarTrabajador(boolean zonaExiste, ZonaEntity zona) {
        zonaSeleccionada = zona;
        zonaCreada = zonaExiste;
        btnAsignar.setVisible(true);
        Collection trabajadores;
        try {
            if (zonaExiste) {
                trabajadores = getTrabajadorManagerImplementation()
                        .getTrabajadoresPorAsignarZona(zona.getIdZona(), zona.getGranja().getIdGranja());
            } else {
                trabajadores = getTrabajadorManagerImplementation().getAllTrabajadores();
            }
        
        tablaTrabajador.setItems(FXCollections.observableArrayList(trabajadores));
        cBoxFiltro.setDisable(true);
        cBoxOpcion.setDisable(true);
        btnBuscar.setDisable(true);
        btnContratar.setVisible(false);
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(TrabajadoresController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            Logger.getLogger(TrabajadoresController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void volverZonas() {
        try {
            if (zonaCreada) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/VentanaZona.fxml"));
                // Se carga el FXML de Session
                Parent root;

                root = (Parent) loader.load();

                LOGGER.info("Llamada al controlador del FXML");
                // Se recoge el controlador del FXML
                ZonaController controller = ((ZonaController) loader.getController());
                controller.setStage(stage);

                LOGGER.info("Inicio del stage de Session");

                controller.initStage(root);
                controller.asignarTrabajadores(trabajadoresAsignar, zonaSeleccionada);
            } else {
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
                controller.asignarTrabajadores(trabajadoresAsignar, zonaSeleccionada);
            }

            //  paneVentana.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(TrabajadoresController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /* public void asignarTrabajadorZonas(ZonaEntity zona) {
        btnBuscar.setDisable(true);
        btnContratar.setDisable(true);
        btnInforme.setDisable(true);
        cBoxFiltro.setDisable(true);
        cBoxOpcion.setDisable(true);
        Collection trabajadores = trabajadorManager.getTrabajadoresPorAsignarZona(zona.getIdZona(), zona.getGranja().getIdGranja());
        tablaTrabajador.setItems(FXCollections.observableArrayList(trabajadores));
        

    }*/
}

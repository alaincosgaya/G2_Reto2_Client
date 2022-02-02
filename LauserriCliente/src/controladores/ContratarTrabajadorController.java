/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clases.ContratoEntity;
import clases.ContratoId;
import clases.GranjaEntity;
import clases.TrabajadorEntity;
import clases.UserEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import static factoria.ContratoManagerFactory.getContratoManagerImplementation;
import factoria.ContratoManagerImplementation;
import factoria.GranjaManagerImplementation;
import static factoria.TrabajadorManagerFactory.getTrabajadorManagerImplementation;
import interfaces.ContratoInterface;
import interfaces.GranjaInterface;
import interfaces.TrabajadorInterface;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import restful.GranjaClient;

/**
 *
 * @author Alain Cosgaya
 */
public class ContratarTrabajadorController {

    private static final Logger LOGGER = Logger.getLogger(ContratarTrabajadorController.class.getName());

    private Stage stage;

    private ContratoInterface contratoManager;

    private GranjaInterface granjaManager;

    private TrabajadorInterface trabajadorManager;

    @FXML
    private ComboBox<TrabajadorEntity> cBoxTrabajador;

    @FXML
    private ComboBox<GranjaEntity> cBoxGranja;

    @FXML
    private DatePicker datePickerContrato;

    @FXML
    private TextField txtSalario;

    @FXML
    private Button btnContratar;

    @FXML
    private Button btnAtras;

    private boolean boolTrabajador;

    private TrabajadorEntity trabajadorSeleccionado;
    private UserEntity user;

    /**
     * El metodo que indica el stage.
     *
     * @param stage1
     */
    public void setStage(Stage stage1) {
        stage = stage1;
    }
    public void setUser(UserEntity user){
        this.user = user;
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
        LOGGER.log(Level.INFO,"Llamada a los metodos y restricciones del controlador");
        // Interfaces de implementaciones de clases.
        contratoManager = getContratoManagerImplementation();
        granjaManager = new GranjaManagerImplementation();
        trabajadorManager = getTrabajadorManagerImplementation();
        // Valor del DatePicker por defecto
        datePickerContrato.setValue(Optional.ofNullable(datePickerContrato.getValue()).orElse(LocalDate.now()));
        trabajadorSeleccionado = null;
        // Comprueba si se ha seleccionado un trabajador previamente
        if (!boolTrabajador) {
            try {
                LOGGER.log(Level.INFO, "Cargando trabajadores en la combobox.");
                cBoxTrabajador.setItems(FXCollections.observableArrayList(trabajadorManager.getAllTrabajadores()));
            } catch (ClienteServidorConexionException ex) {
                alertErrores("Debido a un problema del servidor,"
                        + " no se han podido cargar los trabajadores. En el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);

            } catch (BDServidorException ex) {
                alertErrores("Debido a un problema al conectarse al servidor,"
                        + " no se han podido cargar los trabajadores. En el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            LOGGER.log(Level.INFO, "Cargando granjas en la combobox.");
            cBoxGranja.setItems(FXCollections.observableArrayList(granjaManager.getGranjasPorGranjero(user.getUsername())));
        } catch (ClienteServidorConexionException ex) {
            alertErrores("Debido a un problema del servidor,"
                    + " no se han podido cargar las granjas. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (BDServidorException ex) {
            alertErrores("Debido a un problema al conectarse al servidor,"
                    + " no se han podido cargar las granjas. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cBoxTrabajador.getSelectionModel().selectedItemProperty().addListener(this::handleSeleccionTrabajador);
        cBoxGranja.getSelectionModel().selectedItemProperty().addListener(this::filtradoTrabajadores);
        camposReportados();
        txtSalario.textProperty().addListener(this::restriccionNumerico);
        btnAtras.setOnAction(this::volverContratos);
        btnContratar.setOnAction(this::contratarTrabajador);
        //stage.setOnCloseRequest(this::confirmClose);

    }

    /**
     * Comprueba el trabajador seleccionado, y en base a ello filtra las
     * granjas.
     *
     * @param ov
     */
    public void handleSeleccionTrabajador(Observable ov) {
        LOGGER.log(Level.INFO, "Comprobando el trabajador seleccionado");

        ObservableValue obsV = (ObservableValue) ov;
        // Comprueba si es nula la seleccion.
        if (obsV.getValue() != null) {
            // Comprueba si el trabajador seleccionado ya habia sido seleccionado
            // previamente.
            if (!obsV.getValue().equals(trabajadorSeleccionado)) {

                LOGGER.log(Level.INFO, "Nuevo trabajador seleccionado: {0}", obsV.getValue());
                cBoxTrabajador.setValue(cBoxTrabajador.getSelectionModel().getSelectedItem());
                trabajadorSeleccionado = cBoxTrabajador.getSelectionModel().getSelectedItem();
                // Ejecuta el filtrado de granjas
                filtradoGranjas(cBoxTrabajador.getSelectionModel().getSelectedItem());

            }
        }
    }

    /**
     * Comprueba la granja seleccionada, y en base a ello filtra los
     * trabajadores. Si se ha seleccionado previamente un trabajador, en el caso
     * de que dicho trabajador se pueda contratar en esa granja, se mantendra
     * seleccionado.
     *
     * @param ov
     * @param oldValue
     * @param newValue
     */
    public void filtradoTrabajadores(ObservableValue ov, Object oldValue, Object newValue) {
        if (newValue != null && oldValue != newValue) {
            try {
                LOGGER.log(Level.INFO, "Granja seleccionada");
                TrabajadorEntity trabajador;
                Collection trabajadores;
                trabajador = cBoxTrabajador.getSelectionModel().getSelectedItem();

                String idGranja = String.valueOf(((GranjaEntity) newValue).getIdGranja());
                trabajadores = trabajadorManager.getTrabajadoresPorContratar(idGranja);
                // Carga todos los datos en la combobox.
                cBoxTrabajador.setItems(FXCollections.observableArrayList(trabajadores));
                if (trabajadores.contains(trabajador)) {
                    LOGGER.log(Level.INFO, "Trabajador seleccionado disponible para contratar");
                    cBoxTrabajador.getSelectionModel().select(trabajador);
                }
            } catch (ClienteServidorConexionException ex) {
                alertErrores("Debido a un problema del servidor,"
                        + " no se han podido cargar los trabajadores. En el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);

            } catch (BDServidorException ex) {
                alertErrores("Debido a un problema al conectarse al servidor,"
                        + " no se han podido cargar los trabajadores. En el caso de "
                        + "que este error persista, contacte con el soporte tecnico.");
                Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Comprueba que todos los campos tienen contenido.
     */
    public void camposReportados() {
        btnContratar.disableProperty().bind(
                cBoxTrabajador.valueProperty().isNull().or(
                        cBoxGranja.valueProperty().isNull().or(
                                datePickerContrato.valueProperty().isNull().or(
                                        txtSalario.textProperty().isEmpty())))
        );
    }

    /**
     * Metodo para la contratacion de un trabajador. Recoge el valor de todos
     * los campos y crea un contrato.
     *
     * @param event
     */
    public void contratarTrabajador(ActionEvent event) {
        try {
            // Guarda todos los datos en un objeto de Contrato
            LOGGER.log(Level.INFO, "Iniciando creacion de contrato");
            ContratoId idContrato = new ContratoId();
            idContrato.setGranjaId(cBoxGranja.getSelectionModel().getSelectedItem().getIdGranja());
            idContrato.setTrabajadorId(cBoxTrabajador.getSelectionModel().getSelectedItem().getId());
            ContratoEntity contrato;
            contrato = new ContratoEntity();
            contrato.setIdContrato(idContrato);
            contrato.setGranja(cBoxGranja.getSelectionModel().getSelectedItem());
            contrato.setTrabajador(cBoxTrabajador.getSelectionModel().getSelectedItem());
            contrato.setFechaContratacion(Date.valueOf(datePickerContrato.getValue()));
            contrato.setSalario(Long.parseLong(txtSalario.getText()));
            // Ejecuta el metodo de contratacion.
            contratoManager.contratarTrabajador(contrato);
            // Limpia todos los campos.
            LOGGER.log(Level.INFO, "Limpiando campos de la tabla");
            cBoxTrabajador.getSelectionModel().clearSelection();
            cBoxGranja.getSelectionModel().clearSelection();
            datePickerContrato.setValue(LocalDate.now());
            txtSalario.clear();
        } catch (ClienteServidorConexionException ex) {
            alertErrores("Debido a un problema del servidor,"
                    + " no se ha podido contratar al trabajador. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (BDServidorException ex) {
            alertErrores("Debido a un problema al conectarse al servidor,"
                    + " no se ha podido contratar al trabajador. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cierra la ventana actual y abre la ventana de contratos.
     *
     * @param event
     */
    public void volverContratos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Contratos.fxml"));
            // Se carga el FXML de Contratos

            Parent root;

            root = (Parent) loader.load();

            LOGGER.log(Level.INFO, "Llamada al controlador del FXML");
            // Se recoge el controlador del FXML
            ContratosController controller = ((ContratosController) loader.getController());
            controller.setStage(stage);
            LOGGER.log(Level.INFO, "Inicio del stage de Contratos");
            controller.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(ContratarTrabajadorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo que se ejecuta al haber seleccionado previamente un trabajador.
     * Cargara el trabajador seleccionado por defecto.
     *
     * @param selectedItem
     * @param vTrabajador
     */
    public void trabajadorSeleccionado(TrabajadorEntity selectedItem, boolean vTrabajador) {
        LOGGER.log(Level.INFO, "Cargando trabajador");
        boolTrabajador = vTrabajador;
        cBoxTrabajador.setItems(FXCollections.observableArrayList(selectedItem));
        cBoxTrabajador.getSelectionModel().select(selectedItem);
        cBoxTrabajador.setValue(selectedItem);

        filtradoGranjas(selectedItem);

    }

    /**
     * Metodo de filtrado de granjas en base al trabajador seleccionado.
     *
     * @param select
     */
    public void filtradoGranjas(TrabajadorEntity select) {

        try {
            LOGGER.log(Level.INFO, "Iniciando filtrado de granjas");
            GranjaEntity granja;
            Collection granjas;
            granja = cBoxGranja.getSelectionModel().getSelectedItem();
            String usernameTrabajador = select.getUsername();

            granjas = granjaManager.getGranjasNoTrabajador(usernameTrabajador);
            // Carga todos los datos en la combobox.
            cBoxGranja.setItems(FXCollections.observableArrayList(granjas));
            if (granjas.contains(granja)) {
                LOGGER.log(Level.INFO, "Granja seleccionada disponible para crear contrato.");
                cBoxGranja.getSelectionModel().select(granja);

            }
        } catch (ClienteServidorConexionException ex) {
            alertErrores("Debido a un problema del servidor,"
                    + " no se han podido cargar las granjas. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (BDServidorException ex) {
            alertErrores("Debido a un problema al conectarse al servidor,"
                    + " no se han podido cargar las granjas. En el caso de "
                    + "que este error persista, contacte con el soporte tecnico.");
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para la restriccion de cualquier tipo de valor no numerico.
     *
     * @param ov
     * @param oldValue
     * @param newValue
     */
    public void restriccionNumerico(ObservableValue ov, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) {
            txtSalario.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }

    /**
     * Alert de errores que se le mostrara al usuario.
     * @param message
     */
    public void alertErrores(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}

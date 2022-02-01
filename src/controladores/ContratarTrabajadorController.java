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

    private static final Logger LOGGER = Logger.getLogger(ContratosController.class.getName());

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
        GranjaClient webClientGranja = new GranjaClient();
        contratoManager = getContratoManagerImplementation();
        granjaManager = new GranjaManagerImplementation();
        trabajadorManager = getTrabajadorManagerImplementation();
        datePickerContrato.setValue(Optional.ofNullable(datePickerContrato.getValue()).orElse(LocalDate.now()));
        trabajadorSeleccionado = null;
        if (!boolTrabajador) {
            try {
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
        cBoxGranja.setItems(FXCollections.observableArrayList(webClientGranja.granjasPorLoginDelGranjero(new GenericType<List<GranjaEntity>>() {

        }, "g1")));
        cBoxTrabajador.getSelectionModel().selectedItemProperty().addListener(this::handleSeleccionTrabajador);
        cBoxGranja.getSelectionModel().selectedItemProperty().addListener(this::filtradoTrabajadores);
        camposReportados();
        txtSalario.textProperty().addListener(this::restriccionNumerico);
        btnAtras.setOnAction(this::volverContratos);
        btnContratar.setOnAction(this::contratarTrabajador);
        //stage.setOnCloseRequest(this::confirmClose);

    }

    public void handleSeleccionTrabajador(Observable ov) {
        LOGGER.info("Accede: " + trabajadorSeleccionado);

        ObservableValue obsV = (ObservableValue) ov;
        LOGGER.info("Seleccion: " + obsV.getValue());
        if (obsV.getValue() != null) {
            
            if (!obsV.getValue().equals(trabajadorSeleccionado)) {

                LOGGER.info("Selecciona un nuevo trabajador: " + obsV.getValue());
                cBoxTrabajador.setValue(cBoxTrabajador.getSelectionModel().getSelectedItem());
                trabajadorSeleccionado = cBoxTrabajador.getSelectionModel().getSelectedItem();

                filtradoGranjas(cBoxTrabajador.getSelectionModel().getSelectedItem());

            }
        }
    }

    public void filtradoTrabajadores(ObservableValue ov, Object oldValue, Object newValue) {
        if (newValue != null && oldValue != newValue) {
            try {
                LOGGER.info("Selecciona una nueva granja");
                TrabajadorEntity trabajador;
                Collection trabajadores;
                trabajador = cBoxTrabajador.getSelectionModel().getSelectedItem();

                String idGranja = String.valueOf(((GranjaEntity) newValue).getIdGranja());
                trabajadores = trabajadorManager.getTrabajadoresPorContratar(idGranja);
                System.out.println(trabajadores);
                cBoxTrabajador.setItems(FXCollections.observableArrayList(trabajadores));
                if (trabajadores.contains(trabajador)) {
                    LOGGER.info("Trabajador seleccionado ya esta en la tabla");
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

    public void camposReportados() {
        btnContratar.disableProperty().bind(
                cBoxTrabajador.valueProperty().isNull().or(
                        cBoxGranja.valueProperty().isNull().or(
                                datePickerContrato.valueProperty().isNull().or(
                                        txtSalario.textProperty().isEmpty())))
        );
    }

    public void contratarTrabajador(ActionEvent event) {
        try {
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

            contratoManager.contratarTrabajador(contrato);

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

    public void volverContratos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Contratos.fxml"));
            // Se carga el FXML de Session

            Parent root;

            root = (Parent) loader.load();

            LOGGER.info("Llamada al controlador del FXML");
            // Se recoge el controlador del FXML
            ContratosController controller = ((ContratosController) loader.getController());
            controller.setStage(stage);
            LOGGER.info("Inicio del stage de Session");
            controller.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(ContratarTrabajadorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void trabajadorSeleccionado(TrabajadorEntity selectedItem, boolean vTrabajador) {
        boolTrabajador = vTrabajador;
        System.out.println(selectedItem);
        cBoxTrabajador.setItems(FXCollections.observableArrayList(selectedItem));
        cBoxTrabajador.getSelectionModel().select(selectedItem);
        cBoxTrabajador.setValue(selectedItem);

        filtradoGranjas(selectedItem);

    }

    public void filtradoGranjas(TrabajadorEntity select) {

        try {
            GranjaEntity granja;
            Collection granjas;
            granja = cBoxGranja.getSelectionModel().getSelectedItem();
            String usernameTrabajador = select.getUsername();

            granjas = granjaManager.getGranjasNoTrabajador(usernameTrabajador);

            cBoxGranja.setItems(FXCollections.observableArrayList(granjas));
            if (granjas.contains(granja)) {

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

    public void restriccionNumerico(ObservableValue ov, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) {
            txtSalario.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }

    public void alertErrores(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}

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
import factoria.ContratoManagerImplementation;
import static factoria.TrabajadorManagerFactory.getTrabajadorManagerImplementation;
import interfaces.ContratoInterface;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import restful.GranjaClient;
import restful.TrabajadorClient;

/**
 *
 * @author YO
 */
public class ContratarTrabajadorController {

    private static final Logger LOGGER = Logger.getLogger(ContratosController.class.getName());

    private Stage stage;

    private ContratoInterface contratoManager;

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
        contratoManager = new ContratoManagerImplementation();

        // datePickerContrato.setValue(Optional.ofNullable(datePickerContrato.getValue()).orElse(LocalDate.now()));
        cBoxTrabajador.getSelectionModel().selectedItemProperty().addListener(this::handleSeleccionTrabajador);
        if (!boolTrabajador) {
            cBoxTrabajador.setItems(FXCollections.observableArrayList(getTrabajadorManagerImplementation().getAllTrabajadores()));
        }
        cBoxGranja.setItems(FXCollections.observableArrayList(webClientGranja.granjasPorLoginDelGranjero(new GenericType<List<GranjaEntity>>() {

        }, "g1")));
        cBoxGranja.getSelectionModel().selectedItemProperty().addListener(this::filtradoTrabajadores);
        camposReportados();

        btnAtras.setOnAction(this::volverContratos);
        btnContratar.setOnAction(this::contratarTrabajador);
        //stage.setOnCloseRequest(this::confirmClose);

    }

    public void handleSeleccionTrabajador(ObservableValue ov, Object oldValue, Object newValue) {
        if (newValue != null && newValue != oldValue) {
            filtradoGranjas((TrabajadorEntity) newValue);

        }
    }

    public void filtradoTrabajadores(ObservableValue ov, Object oldValue, Object newValue) {
        if (newValue != null && newValue != oldValue) {
            TrabajadorEntity trabajador;
            Collection trabajadores;
            trabajador = cBoxTrabajador.getSelectionModel().getSelectedItem();
            System.out.println(trabajador);
            String idGranja = String.valueOf(((GranjaEntity) newValue).getIdGranja());
            trabajadores = getTrabajadorManagerImplementation().getTrabajadoresPorContratar(idGranja);
            if (!trabajadores.contains(trabajador)) {
                cBoxTrabajador.getSelectionModel().clearSelection();

                cBoxTrabajador.getItems().clear();
                cBoxTrabajador.setItems(FXCollections.observableArrayList(trabajadores));
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
        ContratoId idContrato = new ContratoId();
        idContrato.setGranjaId(cBoxGranja.getSelectionModel().getSelectedItem().getIdGranja());
        idContrato.setTrabajadorId(cBoxTrabajador.getSelectionModel().getSelectedItem().getId());
        ContratoEntity contrato;

        /* contrato = new ContratoEntity(idContrato, cBoxTrabajador.getValue(),
                cBoxGranja.getValue(), Date.valueOf(datePickerContrato.getValue()),
                Long.parseLong(txtSalario.getText()));*/
        System.out.println(cBoxTrabajador.getSelectionModel().getSelectedItem());
        contrato = new ContratoEntity();
        contrato.setIdContrato(idContrato);
        contrato.setGranja(cBoxGranja.getSelectionModel().getSelectedItem());
        contrato.setTrabajador(cBoxTrabajador.getSelectionModel().getSelectedItem());
        contrato.setFechaContratacion(String.valueOf(datePickerContrato.getValue()));
        contrato.setSalario(Long.parseLong(txtSalario.getText()));

        contratoManager.contratarTrabajador(contrato);

        cBoxTrabajador.getSelectionModel().clearSelection();
        cBoxGranja.getSelectionModel().clearSelection();
        datePickerContrato.setValue(LocalDate.now());
        txtSalario.clear();
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

            //  paneVentana.getScene().getWindow().hide();
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
        GranjaClient webClientGranja = new GranjaClient();
        GranjaEntity granja;
        Collection granjas;
        granja = cBoxGranja.getSelectionModel().getSelectedItem();
        String idTrabajador = String.valueOf(select.getId());
        granjas = webClientGranja.granjasPorLoginDelGranjero(new GenericType<List<GranjaEntity>>() {

        }, "g1");
        if (!granjas.contains(granja)) {

            cBoxGranja.getSelectionModel().clearSelection();

            cBoxGranja.getItems().clear();
            cBoxGranja.setItems(FXCollections.observableArrayList(granjas));
        }
    }
}

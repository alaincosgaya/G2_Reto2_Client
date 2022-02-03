/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clases.GranjaEntity;
import clases.TrabajadorEntity;
import clases.UserEntity;
import clases.ZonaEntity;
import controladores.*;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import factoria.GranjaManagerImplementation;
import static factoria.ZonaManagerFactory.getZonaManagerImplementation;
import factoria.ZonaManagerImplementation;
import interfaces.GranjaInterface;
import interfaces.ZonaInterface;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import restful.GranjaClient;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class CrearZonaController {

    private static final Logger LOGGER = Logger.getLogger(CrearZonaController.class.getName());
    @FXML
    private BorderPane paneSignUp;
    @FXML
    private Button btnAtras;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnAnadir;
    @FXML
    private DatePicker dateFechaNacimiento;
    @FXML
    private ComboBox<GranjaEntity> comboSeleccionarGranja;
    @FXML
    private Hyperlink anadirTrabajador;

    private Collection<TrabajadorEntity> trabajadoresAsignar;

    ZonaInterface zonaManager = getZonaManagerImplementation();
    GranjaInterface granjaManager = new GranjaManagerImplementation();

    private Stage stage;
    private UserEntity usr;
    
    public void setUser(UserEntity user){
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

    public void initStage(Parent root) {
        try {
            stage.setResizable(false);
            stage.setTitle("Crear Zona");
            stage.setScene(new Scene(root));
            
            //inicializamos las combos con los datos pertinentes 
            ObservableList<GranjaEntity> listaGranja = FXCollections.observableArrayList(granjaManager.getGranjasPorGranjero(usr.getUsername()));
            comboSeleccionarGranja.setItems(listaGranja);
            dateFechaNacimiento.setValue(LocalDate.now());
            validarDatos();
            anadirTrabajador.setOnAction(this::TrabajadoresParaAsignar);
            btnAnadir.setOnAction(this::crearZona);
            trabajadoresAsignar = new ArrayList();
            stage.show();
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(CrearZonaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            Logger.getLogger(CrearZonaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void buttonEventBack(ActionEvent event) {
        try {
            LOGGER.info("Carga del FXML de Crear Animal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/VentanaZona.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            ZonaController controller = ((ZonaController) loader.getController());
            controller.setStage(stage);
            controller.setUser(usr);
            controller.initStage(root);

            //paneSignUp.getScene().getWindow().hide();

        } catch (IOException e) {

        }
    }

    @FXML
    private void eventKey(KeyEvent event) {
    }

    @FXML
    private void buttonEvent(ActionEvent event) {
    }

    @FXML
    private void crearZona(ActionEvent event) {
        ZonaEntity zona = new ZonaEntity();
        zona.setFechaCreacionZona(Date.valueOf(dateFechaNacimiento.getValue()));
        zona.setGranja(comboSeleccionarGranja.getSelectionModel().getSelectedItem());
        zona.setNombreZona(txtName.getText());

        zonaManager.crearZona(zona);
 
        if (!trabajadoresAsignar.isEmpty()) {
            trabajadoresAsignar.stream().forEach((trabajador) -> {

                zonaManager.asignarTrabajador(trabajador.getUsername(),7L);
            });
        }
        buttonEventBack(event);
    }

    @FXML
    private void validarDatos() {
        btnAnadir.disableProperty().bind(
                comboSeleccionarGranja.selectionModelProperty().isNull().or(
                        dateFechaNacimiento.editorProperty().isNull().or(
                                txtName.textProperty().isEmpty()))
        );
    }

    public void TrabajadoresParaAsignar(ActionEvent event) {
        try {
            ZonaEntity zona = new ZonaEntity();
            zona.setNombreZona(txtName.getText());
            zona.setGranja(comboSeleccionarGranja.getSelectionModel().getSelectedItem());
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
            controller.habilitarAsignarTrabajador(false, zona);

            //  paneVentana.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(ContratosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void asignarTrabajadores(Collection trabajadores, ZonaEntity zona) {
        txtName.setText(zona.getNombreZona());
        comboSeleccionarGranja.getSelectionModel().select(zona.getGranja());
        trabajadoresAsignar.addAll(trabajadores);
    }

}

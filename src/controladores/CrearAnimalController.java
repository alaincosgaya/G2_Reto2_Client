/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clases.AnimalEntity;
import clases.EstadoAnimal;
import clases.SexoAnimal;
import clases.TipoAnimal;
import clases.ZonaEntity;
import controladores.*;
import factoria.AnimalImplementacion;
import factoria.ZonaManagerImplementation;
import interfaces.InterfazAnimal;
import interfaces.ZonaInterface;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import restful.AnimalClient;
import restful.ZonaClient;

/**
 * FXML Controller class
 *
 * @author Jonathan Camacho
 */
public class CrearAnimalController {

    private static final Logger LOGGER = Logger.getLogger(PrincipalAnimalController.class.getName());

    @FXML
    private BorderPane paneCrearAnimal;
    @FXML
    private Button btnAtras;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnCrearAnimal;
    @FXML
    private ComboBox<TipoAnimal> comboSeleccionarTipo;
    @FXML
    private ComboBox<SexoAnimal> comboSeleccionarSexo;
    @FXML
    private ComboBox<ZonaEntity> comboSeleccionarZona;
    @FXML
    private DatePicker dateFechaNacimiento;

    private Stage stage;
    //private InterfazAnimal animalManager;
    InterfazAnimal animalManager = new AnimalImplementacion();
    ZonaInterface zonaManager = new ZonaManagerImplementation();
    //ZonaClient webClienteZona = new ZonaClient();

    /**
     * El metodo que indica el stage.
     *
     * @param stage1
     */
    public void setStage(Stage stage1) {
        stage = stage1;
    }

    /**
     * Metodo con el cual se inicializa la ventana. La ventana iniciara con las
     * combos y el text del nombre vacio. Se tendra que seleccionar
     *
     * @param root
     */
    public void initStage(Parent root) {
        Stage anotherStage = new Stage();
        anotherStage.setResizable(false);
        anotherStage.setTitle("CrearAnimal");
        anotherStage.setScene(new Scene(root));

        //inicializamos las combos con los datos pertinentes
        seleccionarTipo();
        seleccionarSexo();
        seleccionarZona();
        //indicamos la fecha de hoy por defecto
        dateFechaNacimiento.setValue(LocalDate.now());
        //validamos que el boton de añadir animal se habilite cuando todos los campos
        //necesarios esten rellenados
        validarDatos();
        //mostramos la ventana
        anotherStage.show();
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    private void volver(ActionEvent event) {
        try {
            LOGGER.info("Carga del FXML de Crear Animal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/PrincipalAnimal.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            PrincipalAnimalController controller = ((PrincipalAnimalController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);

            paneCrearAnimal.getScene().getWindow().hide();

        } catch (IOException e) {

        }
    }

    @FXML
    private void eventKey(KeyEvent event) {
    }

    @FXML
    private void crearAnimal(ActionEvent event) {
        AnimalEntity animal = new AnimalEntity();

        animal.setNombreAnimal(txtName.getText());
        animal.setEstado(EstadoAnimal.SANO);
        animal.setTipo(comboSeleccionarTipo.getSelectionModel().getSelectedItem());
        animal.setFechaNacimiento(Date.valueOf(dateFechaNacimiento.getValue()));
        animal.setSexo(comboSeleccionarSexo.getSelectionModel().getSelectedItem());
        animal.setZona(comboSeleccionarZona.getSelectionModel().getSelectedItem());

        animalManager.crearAnimal(animal);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se han introducido los datos del animal");
        alert.show();

        txtName.clear();
        comboSeleccionarTipo.getSelectionModel().clearSelection();
        dateFechaNacimiento.setValue(null);
        comboSeleccionarSexo.getSelectionModel().clearSelection();
        comboSeleccionarZona.getSelectionModel().clearSelection();
    }

    @FXML
    private void seleccionarTipo() {
        ObservableList<TipoAnimal> listaTipo = FXCollections.observableArrayList(TipoAnimal.values());
        comboSeleccionarTipo.setItems(listaTipo);
    }

    @FXML
    private void seleccionarSexo() {
        ObservableList<SexoAnimal> listaSexo = FXCollections.observableArrayList(SexoAnimal.values());
        comboSeleccionarSexo.setItems(listaSexo);
    }

    @FXML
    private void seleccionarZona() {
        ObservableList<ZonaEntity> listaZona = FXCollections.observableArrayList(zonaManager.getAllZonas());
        comboSeleccionarZona.setItems(listaZona);
    }

    @FXML
    private void validarDatos() {
        btnCrearAnimal.disableProperty().bind(
                comboSeleccionarTipo.selectionModelProperty().isNull().or(
                        dateFechaNacimiento.editorProperty().isNull().or(
                                comboSeleccionarSexo.selectionModelProperty().isNull().or(
                                        txtName.textProperty().isEmpty())))
        );
    }
}

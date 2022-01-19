/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clases.TipoAnimal;
import controladores.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    private ComboBox<?> comboSeleccionarTipo;
    @FXML
    private ComboBox<?> comboSeleccionarSexo;
    @FXML
    private ComboBox<?> comboSeleccionarZona;
    @FXML
    private DatePicker dateFechaNacimiento;

    private Stage stage;

    /**
     * El metodo que indica el stage.
     *
     * @param stage1
     */
    public void setStage(Stage stage1) {
        stage = stage1;
    }

    public void initStage(Parent root) {
        Stage anotherStage = new Stage();
        anotherStage.setResizable(false);
        anotherStage.setTitle("CrearAnimal");
        anotherStage.setScene(new Scene(root));
        anotherStage.show();
    }

    /**
     * Initializes the controller class.
     */
    /*

     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
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
    }

    @FXML
    private void seleccionarTipo(ActionEvent event) {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("item-1", "item-2", "item-3", "item-4", "item-5");

        ComboBox<String> cbx = new ComboBox<>(items);
        comboSeleccionarTipo = cbx;
 //       StackPane pane = new StackPane(cbx);

    }

    @FXML
    private void seleccionarSexo(ActionEvent event) {
    }

    @FXML
    private void seleccionarZona(ActionEvent event) {
    }

    @FXML
    private void validarDatos(ActionEvent event) {
    }

}

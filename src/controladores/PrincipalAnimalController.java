/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clases.EstadoAnimal;
import clases.TipoAnimal;
import java.io.IOException;
import java.util.Observable;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class PrincipalAnimalController {

    private static final Logger LOGGER = Logger.getLogger(PrincipalAnimalController.class.getName());

    private Stage stage;

    @FXML
    private BorderPane panePrincipalAnimal;
    @FXML
    private Button btnInforme;
    @FXML
    private Button btnCrearAnimal;
    @FXML
    private TableView<?> tabla;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colTipo;
    @FXML
    private TableColumn<?, ?> colEstado;
    @FXML
    private TableColumn<?, ?> colSexo;
    @FXML
    private TableColumn<?, ?> colFechaNacimiento;
    @FXML
    private TableColumn<?, ?> colZona;
    @FXML
    private ComboBox seleccionFiltro;
    @FXML
    private ComboBox seleccionDato;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnInsertar;

    /**
     * El metodo que indica el stage.
     *
     * @param stage1
     */
    public void setStage(Stage stage1) {
        stage = stage1;
    }

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
        // TODO
        Scene scene = new Scene(root,640,360);
        stage.setResizable(false);
        stage.setTitle("PrincipalAnimal");
        stage.setScene(scene);
        //inicializamos lo que necesitemos
        ObservableList<String> filtro = FXCollections.observableArrayList("Tipo", "Estado", "Sexo","Todos");
        seleccionFiltro.setItems(filtro);
        //ObservableList<TipoAnimal> dato = FXCollections.observableSet(elements);
        //seleccionDato.setItems(dato);
        //
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        btnCrearAnimal.setOnAction(this::aniadirAnimal);
        //seleccionFiltro.setOnAction(this::seleccionFiltro);
        //

        //
        stage.show();
    }

    @FXML
    private void generarInforme(ActionEvent event) {
    }

    @FXML
    private void aniadirAnimal(ActionEvent event) {
        try {
            LOGGER.info("Carga del FXML de Crear Animal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/CrearAnimal.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            CrearAnimalController controller = ((CrearAnimalController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);

            panePrincipalAnimal.getScene().getWindow().hide();

        } catch (IOException e) {

        }
    }

    @FXML
    private void seleccionFiltro(ActionEvent event) {

        //ObservableList<String>tipoFiltro = FXCollections.observableArrayList("Tipo","Estado","Sexo");
        //seleccionFiltro.setItems(tipoFiltro);
        //ObservableList<String> filtro = FXCollections.observableArrayList("Tipo", "Estado", "Sexo");
        //seleccionFiltro.setItems(filtro);
        
        //String filtro = seleccionFiltro.getSelectionModel().getSelectedItem().toString();
        //seleccionFiltro.
    }

    @FXML
    private void seleccionDato(ActionEvent event) {

    }

    @FXML
    private void buscarAnimal(ActionEvent event) {
        
    }

    @FXML
    private void eliminarAnimal(ActionEvent event) {
    }

    @FXML
    private void insertarAnimal(ActionEvent event) {
    }

}

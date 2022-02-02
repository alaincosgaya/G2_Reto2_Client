/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import excepciones.NameLetterException;
import factoria.GranjeroManagerImplementation;
import clases.GranjaEntity;
import clases.GranjeroEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import restful.GranjeroClient;
import factoria.GranjaManagerImplementation;
import interfaces.GranjaInterface;
import interfaces.GranjeroInterface;
import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 * Clase la cual consiste en un formulario para que se puedan crear granjas.
 *
 * @author Alejandro Gomez
 *
 */
public class CreateFarmController {

    private static final Logger LOGGER = Logger.getLogger(CreateFarmController.class.getName());

    private Stage stage;

    private GranjaInterface granjaInterface;

    @FXML
    private TextField txtName;

    @FXML
    private Label lblLetters;

    @FXML
    private DatePicker datePickerCreate;

    @FXML
    private ComboBox comboBoxFarmer;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnCreateFarm;

    @FXML
    private Pane paneCreateFarm;

    GranjeroInterface granjeroImplementation = new GranjeroManagerImplementation();

    private final int max = 50;

    /**
     * El metodo que indica el stage.
     *
     * @param stage1
     */
    public void setStage(Stage stage1) {
        stage = stage1;
    }

    /**
     * El metodo que instancia la ventana.
     *
     * @param root
     */
    public void initStage(Parent root) {
        Stage anotherStage = new Stage();
        anotherStage.setResizable(false);
        //stage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
        anotherStage.setTitle("CreateFarm");
        anotherStage.setScene(new Scene(root));
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        GranjeroClient webClientGranjero = new GranjeroClient();
        granjaInterface = new GranjaManagerImplementation();
        comboBoxFarmer.setItems(FXCollections.observableArrayList(webClientGranjero.findAll_XML(new GenericType<List<GranjeroEntity>>() {
        })));
        btnCreateFarm.setDisable(true);
        validarCampos();
        txtName.textProperty().addListener(this::NameTextLetterValidation);
        anotherStage.show();
    }

    /**
     * El metodo que controla si los campos están informados.
     */
    private void validarCampos() {
        btnCreateFarm.disableProperty().bind(
                txtName.textProperty().isEmpty().or(
                        lblLetters.visibleProperty().or(
                                datePickerCreate.editorProperty().isNull().or(
                                        comboBoxFarmer.getSelectionModel().selectedItemProperty().isNull()
                                )
                        )
                )
        );
    }

    /**
     * El metodo que selecciona el granjero de la ComboBox.
     *
     */
    @FXML
    private void selectFarmer() {
        ObservableList<GranjeroEntity> listaGranjero = FXCollections.observableArrayList(granjeroImplementation.getAllGranjeros());
        comboBoxFarmer.setItems(listaGranjero);
    }

    /**
     * El metodo que indica las acciones del botón y crea la ventana a la que se
     * dirige.
     *
     * @param event
     */
    @FXML
    private void buttonEventCreateFarm(ActionEvent event) {
        try {
            LOGGER.info("Inicializacion de la variable granja");
            GranjaEntity granja;
            granja = granjaInterface.getGranjaPorNombre(txtName.getText());
            if (granja != null) {
                LOGGER.severe("Este nombre ya pertenece a una granja");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Este nombre ya pertenece a una granja");
                alert.show();
            } else {
                granja = new GranjaEntity();
                granja.setNombreGranja(txtName.getText());
                granja.setGranjero((GranjeroEntity) comboBoxFarmer.getSelectionModel().getSelectedItem());
                if (datePickerCreate.getValue() == null) {
                    datePickerCreate.setValue(LocalDate.now());
                    granja.setFechaCreacion(Date.valueOf(datePickerCreate.getValue()));
                    granjaInterface.crearGranja(granja);
                    txtName.clear();
                    datePickerCreate.setValue(null);
                    comboBoxFarmer.getSelectionModel().clearSelection();
                    LOGGER.info("Registro de la granja exitoso");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Granja registrada correctamente");
                    alert.show();
                } else {
                    if (datePickerCreate.getValue().isAfter(LocalDate.now())) {
                        LOGGER.severe("No puede introducir una granja la cual su fecha de creacion sea posterior al dia de hoy");
                        Alert alert = new Alert(Alert.AlertType.ERROR, "No puede introducir una granja la cual su fecha de creacion sea posterior al dia de hoy");
                        alert.show();
                    } else {
                        
                        granja.setFechaCreacion(Date.valueOf(datePickerCreate.getValue()));
                        granjaInterface.crearGranja(granja);
                        txtName.clear();
                        datePickerCreate.setValue(null);
                        comboBoxFarmer.getSelectionModel().clearSelection();
                        LOGGER.info("Registro de la granja exitoso");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Granja registrada correctamente");
                        alert.show();
                    }
                    
                }
            }
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(CreateFarmController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            Logger.getLogger(CreateFarmController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * El metodo que indica las acciones del botón y crea la ventana a la que se
     * dirige.
     *
     * @param event
     */
    @FXML
    private void buttonEventBack(ActionEvent event) {
        try {
            LOGGER.info("Carga del FXML de Granja");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/Granja.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            GranjaController controller = ((GranjaController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);

            paneCreateFarm.getScene().getWindow().hide();

        } catch (IOException e) {
            Logger.getLogger(CreateFarmController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Metodo que muestra la etiqueta de caracteres especiales si no se cumple
     * el patrón definido en el metodo validateLettersAndSpaces().
     *
     * @param ov
     * @param oldV
     * @param newV
     */
    private void NameTextLetterValidation(ObservableValue ov, String oldV,
            String newV) {

        if (!txtName.getText().equals("")) {
            try {
                String name = txtName.getText();
                validateLettersAndSpaces(name);
                lblLetters.setVisible(false);
            } catch (NameLetterException e) {
                lblLetters.setVisible(true);
            }
        } else {
            lblLetters.setVisible(false);
        }

    }

    /**
     * El metodo que controla que no se sobrepase el maximo numero de
     * caracteres.
     *
     * @param event
     */
    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtName)) {
            if (txtName.getText().length() > max) {
                event.consume();
            }
        }
    }

    /**
     * Metodo que comprueba que en el campo de nombre solo se puedan meter
     * letras o espacios.
     *
     * @param name
     * @throws NameLetterException
     */
    private void validateLettersAndSpaces(String name) throws NameLetterException {
        String regex = "[a-zA-Z ]+";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(name);

        if (!matcher.matches()) {
            throw new NameLetterException(lblLetters.getText());
        }
    }

}

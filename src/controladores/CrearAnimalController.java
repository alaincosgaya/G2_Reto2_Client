package controladores;

import clases.AnimalEntity;
import clases.EstadoAnimal;
import clases.SexoAnimal;
import clases.TipoAnimal;
import clases.ZonaEntity;
import controladores.*;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import excepciones.InvalidNameException;
import factoria.AnimalImplementacion;
import factoria.FactoriaAnimal;
import factoria.ZonaManagerImplementacion;
import interfaces.InterfazAnimal;
import interfaces.InterfazZona;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Clase FXML del controlador de la ventana PrincipalAnimal, la cual se
 * encargara de realizar todas las acciones pertinentes redactadas en la
 * documentacion
 *
 * @author Jonathan Camacho
 */
public class CrearAnimalController {

    private static final Logger LOGGER = Logger.getLogger(PrincipalAnimalController.class.getName());
    /**
     * Panel de la ventana Crear Animal
     */
    @FXML
    private BorderPane paneCrearAnimal;
    /**
     * Boton que permite volver a la pantalla PrincipalAnimal
     */
    @FXML
    private Button btnAtras;
    /**
     * TextField en el cual el usuario podrá introducir el nombre del animal
     */
    @FXML
    private TextField txtName;
    /**
     * Boton que permite la creacion del animal una vez introducidos los datos
     * necesarios
     */
    @FXML
    private Button btnCrearAnimal;
    /**
     * ComboBox en la cual se cargarán los tipos de animales
     */
    @FXML
    private ComboBox<TipoAnimal> comboSeleccionarTipo;
    /**
     * ComboBox en la cual se cargará el Sexo de los animales
     */
    @FXML
    private ComboBox<SexoAnimal> comboSeleccionarSexo;
    /**
     * ComboBox en la cual se cargarán las zonas disponibles para introducir a
     * los animales
     */
    @FXML
    private ComboBox<ZonaEntity> comboSeleccionarZona;
    /**
     * DatePicker en la cual se podrá introducir la fecha de nacimiento del
     * animal
     */
    @FXML
    private DatePicker dateFechaNacimiento;

    private final int max = 30;

    private Stage stage;
    private InterfazAnimal animalManager;
    InterfazZona zonaManager = new ZonaManagerImplementacion();

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
     * combos y el text del nombre vacio. Se tendran que rellenar
     * obligatoriamente los campos de nombre, tipo, sexo y fecha de nacimiento.
     * Esta ultima empezara iniciada con el dia actual en el que nos encontremos
     * a la hora de hacer la creacion del animal.
     *
     * No sera necesario agregar una zona al animal para su creacion,
     *
     * @param root
     */
    public void initStage(Parent root) {
        Stage anotherStage = new Stage();
        anotherStage.setResizable(false);
        anotherStage.setTitle("CrearAnimal");
        anotherStage.setScene(new Scene(root));
        //implementacion de los metodos de la interfaz animal
        animalManager = FactoriaAnimal.getInterfazAnimalImplementacion();
        //inicializamos las combos con los datos pertinentes
        LOGGER.info("Se inicializan las combos con los datos pertinentes");
        seleccionarTipo();
        seleccionarSexo();
        seleccionarZona();
        //indicamos la fecha de hoy por defecto
        LOGGER.info("Se carga por defecto la fecha actual en el campo dateFechaNacimiento");
        dateFechaNacimiento.setValue(LocalDate.now());
        //validamos que el boton de añadir animal se habilite cuando todos los campos necesarios esten rellenados
        LOGGER.info("Se deshabilita el boton btnCrearAnimal hasta que se rellenen los campos");
        validarDatos();
        //mostramos la ventana
        LOGGER.info("Se muestra la ventana");
        anotherStage.show();
    }

    /**
     * Metodo que permite volver a la pantalla PrincipalAnimal cuando se pulsa
     * el boton de atras
     */
    @FXML
    private void volver(ActionEvent event) {
        try {
            LOGGER.info("Carga del FXML de PrincipalAnimal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/PrincipalAnimal.fxml")
            );
            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML y se esconde la ventana actual");
            PrincipalAnimalController controller = ((PrincipalAnimalController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
            //cerramos la ventana actual
            paneCrearAnimal.getScene().getWindow().hide();

        } catch (IOException e1) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, e1);
        }
    }

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtName)) {
            if (txtName.getText().length() >= max) {
                event.consume();
            }
        }
    }

    /**
     * Metodo con el cual, al rellenar los campos necesarios y pulsar el boton
     * añadir animal, se ejecutara creando asi el animal. En caso de que se
     * realice con exito se mostrara al usuario un alert de informacion
     * indicando que se ha creado bien.
     *
     * Ademas se limpiaran los campos tras su creacion. El campo fecha aparecerá
     * con la del día actual por defecto.
     *
     * @param event
     */
    @FXML
    private void crearAnimal(ActionEvent event) throws InvalidNameException {
        AnimalEntity animal = new AnimalEntity();
        try {
            //se introducen los datos
            LOGGER.info("Se procede a la creacion del animal");
            animal.setNombreAnimal(txtName.getText());

            //se comprueba la longitud del nombre
            String nombre = txtName.getText();
            validarMinimoCaracteresPattern(nombre);
            //se introducen el resto de datos
            animal.setEstado(EstadoAnimal.SANO);
            animal.setTipo(comboSeleccionarTipo.getSelectionModel().getSelectedItem());
            animal.setFechaNacimiento(Date.valueOf(dateFechaNacimiento.getValue()));
            animal.setSexo(comboSeleccionarSexo.getSelectionModel().getSelectedItem());
            animal.setZona(comboSeleccionarZona.getSelectionModel().getSelectedItem());
            //se envian los datos introducidos al servidor
            LOGGER.info("Se envian los datos introducidos por el usuario al servidor");
            animalManager.crearAnimal(animal);
            //se informa al usuario
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se han introducido los datos del animal");
            alert.show();
            //se limpian los campos y se pone el foco en el nombre
            LOGGER.info("Se vacian los campos, se pone el foco en el campo de texto txtName y se pone la fecha actual por defecto");
            txtName.clear();
            comboSeleccionarTipo.getSelectionModel().clearSelection();
            dateFechaNacimiento.setValue(LocalDate.now());
            comboSeleccionarSexo.getSelectionModel().clearSelection();
            comboSeleccionarZona.getSelectionModel().clearSelection();
            txtName.requestFocus();
            //se informa al usuario de que ha habido un error
        } catch (InvalidNameException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El nombre contiene muy pocos caracteres, mínimo se requieren 2");
            alert.show();
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(CrearAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ha surgido un problema con el servidor, por favor intentelo más tarde.");
            alert.show();
        } catch (BDServidorException ex) {
            Logger.getLogger(CrearAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ha surgido un problema con el servidor, por favor intentelo más tarde.");
            alert.show();
        } catch (Exception e) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al introducir los datos", ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * Metodo el cual permite el cargado de datos de la enumeracion TipoAnimal
     * en la comboBox comboSeleccionarTipo
     */
    @FXML
    private void seleccionarTipo() {
        LOGGER.info("Cargando datos de TipoAnimal en la combo");
        ObservableList<TipoAnimal> listaTipo = FXCollections.observableArrayList(TipoAnimal.values());
        comboSeleccionarTipo.setItems(listaTipo);
    }

    /**
     * Metodo el cual permite el cargado de los datos de la enumeracion
     * SexoAnimal en la comboBox comboSeleccionarSexo.
     */
    @FXML
    private void seleccionarSexo() {
        LOGGER.info("Cargando datos de Sexo en la combo");
        ObservableList<SexoAnimal> listaSexo = FXCollections.observableArrayList(SexoAnimal.values());
        comboSeleccionarSexo.setItems(listaSexo);
    }

    /**
     * Metodo el cual permite el cargado de las zonas existentes en la comboBox
     * comboSeleccionarZona.
     */
    @FXML
    private void seleccionarZona() {
        LOGGER.info("Cargando datos de Zona en la combo, si los hay");
        ObservableList<ZonaEntity> listaZona = FXCollections.observableArrayList(zonaManager.getAllZonas());
        comboSeleccionarZona.setItems(listaZona);
    }

    /**
     * Metodo el cual valida que los campos de texto, el datepicker y las
     * comboBox, a excepcion de comboSeleccionarZona, esten rellenos.
     *
     * En caso de que se cumpla dicha condicion, el boton btnCrearAnimal se
     * habilitara
     */
    @FXML
    private void validarDatos() {
        btnCrearAnimal.disableProperty().bind(
                comboSeleccionarTipo.getSelectionModel().selectedItemProperty().isNull().or(
                        dateFechaNacimiento.editorProperty().isNull().or(
                                comboSeleccionarSexo.getSelectionModel().selectedItemProperty().isNull().or(
                                        txtName.textProperty().isEmpty())))
        );
    }

    /**
     * El metodo que controla los caracteres mínimos del nombre.
     *
     * @param nombre recoge el valor del nombre.
     * @throws InvalidNameException
     */
    public void validarMinimoCaracteresPattern(String nombre)
            throws InvalidNameException {
        String regex = "^(.+){2,30}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(nombre);

        if (!matcher.matches()) {
            throw new InvalidNameException("Numero de caracteres excesivos");
        }
    }

}

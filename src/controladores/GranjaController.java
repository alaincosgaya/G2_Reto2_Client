/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clases.DateEditingCell;
import clases.GranjaEntity;
import clases.GranjeroEntity;
import clases.UserEntity;
import clases.UserPrivilegeType;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import excepciones.NameLetterException;
import factoria.GranjaManagerImplementation;
import factoria.GranjeroManagerImplementation;
import interfaces.GranjaInterface;
import interfaces.GranjeroInterface;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Clase la cual consta de una tabla y unos ComboBox con sus respectivos valores
 * y de unos botones con sus respectivas funciones.
 *
 * @author Alejandro Gomez
 */
public class GranjaController {

    private static final Logger LOGGER = Logger.getLogger(GranjaController.class.getName());

    private Stage stage;

    private GranjaInterface granjaInterface;

    private GranjeroInterface granjeroInterface;

    private GranjaEntity createGranja;

    @FXML
    private ComboBox<String> comboBoxTipo;

    @FXML
    private ComboBox comboBoxTipoElegido;

    @FXML
    private TableView tableViewGranja;

    @FXML
    private TableColumn columnNombreGranja;

    @FXML
    private TableColumn columnFechaCreacionGranja;

    @FXML
    private TableColumn<GranjaEntity, String> columnGranjeroGranja;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInforme;

    @FXML
    private Pane paneGranja;

    private ObservableList<GranjaEntity> granjas;

    private ObservableList listado;

    private final int max = 50;

    private boolean nueva;

    private String user;

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
        Scene scene = new Scene(root);
        stage.setResizable(false);
        //stage.getIcons().add(new Image("/photos/descargas-removebg-preview.png"));
        stage.setTitle("Granja");
        stage.setScene(scene);
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        tableViewGranja.setEditable(true);
        columnNombreGranja.setCellValueFactory(new PropertyValueFactory<>("nombreGranja"));
        columnNombreGranja.setCellFactory(TextFieldTableCell.forTableColumn());
        columnFechaCreacionGranja.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        columnGranjeroGranja.setCellValueFactory(new PropertyValueFactory<>("granjero"));
        columnFechaCreacionGranja.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                DateEditingCell dateEdit = new DateEditingCell();
                return dateEdit;
            }
        });
        cargarGranjeros();
        columnNombreGranja.setOnEditCommit(this::modificarNombreGranja);
        columnGranjeroGranja.setOnEditCommit(this::modificarGranjero);
        columnNombreGranja.getText();
        comboBoxTipo.setItems(FXCollections.observableArrayList(
                "Nombre",
                "Granjero",
                "Todas"
        ));
        comboBoxTipo.getSelectionModel().selectedItemProperty().addListener(this::checkTipo);
        comboBoxTipoElegido.setDisable(true);
        comboBoxTipoElegido.getSelectionModel().selectedItemProperty().addListener(this::checkTipoElegido);
        tableViewGranja.getSelectionModel().selectedItemProperty().addListener((os, oldSelection, newSelection) -> {
            if (newSelection == null) {
                btnDelete.setDisable(true);
            } else {
                btnDelete.setDisable(false);
            }
        });
        granjaInterface = new GranjaManagerImplementation();
        try {
            tableViewGranja.setItems(FXCollections.observableArrayList(granjaInterface.getAllGranjas()));
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nueva = false;
        stage.show();
    }

    /**
     * Metodo que comprueba según el tipo elegido, los datos que se le pasarán a
     * la segunda ComboBox.
     *
     * @param ov
     * @param oldValue
     * @param newValue
     */
    @FXML
    public void checkTipo(ObservableValue ov, String oldValue, String newValue) {
        if (newValue != null) {
            String filtrado = comboBoxTipo.getValue();
            btnBuscar.setDisable(true);
            comboBoxTipoElegido.setDisable(false);
            comboBoxTipoElegido.setItems(null);
            Collection listadoGranjas = null;
            switch (filtrado) {
                case ("Nombre"):
            {
                try {
                    listadoGranjas = granjaInterface.getAllGranjas();
                } catch (ClienteServidorConexionException ex) {
                    Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BDServidorException ex) {
                    Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    cargarDatosTipo(listadoGranjas);
                    break;
                case ("Granjero"):
                    listadoGranjas = granjeroInterface.getAllGranjeros();
                    cargarDatosTipo(listadoGranjas);
                    break;
                case ("Todas"):
                    btnBuscar.setDisable(false);
                    comboBoxTipoElegido.setDisable(true);
                    break;
            }
        }
    }

    /**
     * Metodo que en función del tipo elegido, escribe los datos deseados en la
     * segunda ComboBox.
     *
     * @param ov
     */
    @FXML
    public void checkTipoElegido(ObservableValue ov, Object oldValue, Object newValue) {
        if (newValue != null) {
            btnBuscar.setDisable(false);
            comboBoxTipoElegido.setValue(comboBoxTipoElegido.getSelectionModel().getSelectedItem());
        }

    }

    /**
     * Metodo que carga los datos en la segunda ComboBox.
     *
     * @param seleccion
     */
    public void cargarDatosTipo(Collection seleccion) {
        listado = FXCollections.observableArrayList(seleccion);
        // muestraDatosComboBoxTipoElegido();
        comboBoxTipoElegido.setItems(listado);
    }

    /**
     * Metodo que carga el nombre de la granja en la tabla en función del nombre
     * elegido.
     *
     * @param granja
     */
    public void cargarDatosEnTablaPorNombre(GranjaEntity granja) {
        granjas = FXCollections.observableArrayList(granja);
        tableViewGranja.setItems(granjas);
        comboBoxTipo.getSelectionModel().clearSelection();
    }

    /**
     * Metodo que carga todas las granjas de ese granjero en la tabla.
     *
     * @param listadoGranjas
     */
    public void cargarDatosEnTablaPorGranjero(Collection<GranjaEntity> listadoGranjas) {
        granjas = FXCollections.observableArrayList(listadoGranjas);
        tableViewGranja.setItems(granjas);
        comboBoxTipo.getSelectionModel().clearSelection();
    }

    /**
     * Metodo que carga todas las granjas en la tabla.
     *
     * @param listadoGranjas
     */
    public void cargarDatosEnTablaPorTodas(Collection<GranjaEntity> listadoGranjas) {
        granjas = FXCollections.observableArrayList(listadoGranjas);
        tableViewGranja.setItems(granjas);
        comboBoxTipo.getSelectionModel().clearSelection();
    }

    /**
     * El metodo que indica las acciones del botón y crea la ventana a la que se
     * dirige.
     *
     * @param event
     */
    @FXML
    private void buttonEventCreate(ActionEvent event) {
        try {
            LOGGER.info("Carga del FXML de CreateFarm");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/CreateFarm.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            CreateFarmController controller = ((CreateFarmController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);

            paneGranja.getScene().getWindow().hide();

        } catch (IOException e) {
            Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Metodo que habilita la segunda ComboBox con el nombre de las granjas o la
     * deshabilita en función de lo elegido en la primera ComboBox.
     *
     * @param event
     */
    @FXML
    private void findFarm(ActionEvent event) {
        try {
            Collection<GranjaEntity> granjas = null;
            GranjaEntity granja = null;
            String eleccion;
            switch (comboBoxTipo.getValue()) {
                case ("Nombre"):
                    String valorNombre = String.valueOf(((GranjaEntity) comboBoxTipoElegido.getSelectionModel().getSelectedItem()).getNombreGranja());
                    granja = granjaInterface.getGranjaPorNombre(valorNombre);
                    cargarDatosEnTablaPorNombre(granja);
                    comboBoxTipoElegido.getSelectionModel().clearSelection();
                    comboBoxTipoElegido.setDisable(true);
                    btnBuscar.setDisable(true);
                    break;
                case ("Granjero"):
                    String valorGranjero = String.valueOf(((GranjeroEntity) comboBoxTipoElegido.getSelectionModel().getSelectedItem()).getUsername());
                    granjas = granjaInterface.getGranjasPorGranjero(valorGranjero);
                    cargarDatosEnTablaPorGranjero(granjas);
                    comboBoxTipoElegido.getSelectionModel().clearSelection();
                    comboBoxTipoElegido.setDisable(true);
                    btnBuscar.setDisable(true);
                    break;
                case ("Todas"):
                    granjas = granjaInterface.getAllGranjas();
                    cargarDatosEnTablaPorTodas(granjas);
                    btnBuscar.setDisable(true);
                    break;
            }
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo valida que al pulsar el botón Informe, se cree un informa 
     * con todos los datos de als granjas 
     *
     * @param event
     */
    @FXML
    private void generarInforme(ActionEvent event) {
        JasperReport report;
        try {
            //ruta donde se genera el report
            LOGGER.info("Generando el informe con las granjas introducidas");
            report = JasperCompileManager.compileReport("src/informes/GranjaReport.jrxml");
            //JasperReport report = JasperCompileManager.compileReport("src/vistas/InformeContratos.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<GranjaEntity>) this.tableViewGranja.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.severe("Error al generar el informe");

        }
    }

    /**
     * Metodo que actualiza el nombre de la granja. Primero se comprueba si la
     * granja ya existe, si existe se muestra un mensaje y se reinicia la tabla
     * a los valores anteriores. Si esa granja no existe, se comprueba si la
     * longitud maxima no se supera, en caso de que se supere, se muestra un
     * mensaje al usuario y se reinicia la tabla a sus antiguos valores. Si la
     * granja no existe y la longitud esta en el limite de caracteres, se
     * comprueba si el usuario no ha metido caracteres distintos de letras o
     * espacios, si hay algun caracter distinto de esos, se muestra un mensaje y
     * se reinicia la tabla a sus viejos valores, si no los hay, se modifica el
     * nombre de la granja.
     *
     * @param event
     */
    @FXML
    private void modificarNombreGranja(Event event) {
        try {
            GranjaEntity granja = (GranjaEntity) ((TableColumn.CellEditEvent) event).getRowValue();
            String nombreGranja = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
            GranjaEntity granjaEncontrada = granjaInterface.getGranjaPorNombre(nombreGranja);
            if (granjaEncontrada != null) {
                LOGGER.severe("Este nombre ya pertenece a una granja");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Este nombre ya pertenece a una granja");
                alert.show();
                Collection<GranjaEntity> granjas = null;
                granjas = granjaInterface.getAllGranjas();
                cargarDatosEnTablaPorTodas(granjas);
            } else {
                if (nombreGranja.length() > max) {
                    LOGGER.severe("Ha superado el límite de caracteres permitido, que son 50");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Ha superado el límite de caracteres permitido, que son 50");
                    alert.show();
                    Collection<GranjaEntity> granjas = null;
                    granjas = granjaInterface.getAllGranjas();
                    cargarDatosEnTablaPorTodas(granjas);
                } else {
                    boolean correcto = false;
                    correcto = validateLettersAndSpaces(nombreGranja);
                    if (correcto) {
                        granjaInterface.cambiarNombreDeLaGranja(granja.getIdGranja(), nombreGranja);
                        granja.setNombreGranja(nombreGranja);
                        Collection<GranjaEntity> granjas = null;
                        granjas = granjaInterface.getAllGranjas();
                        cargarDatosEnTablaPorTodas(granjas);
                    } else {
                        LOGGER.severe("Solo puede introducir letras o espacios");
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Solo puede introducir letras o espacios");
                        alert.show();
                        Collection<GranjaEntity> granjas = null;
                        granjas = granjaInterface.getAllGranjas();
                        cargarDatosEnTablaPorTodas(granjas);
                    }
                }
            }
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que comprueba que en el campo de nombre solo se puedan meter
     * letras o espacios.
     *
     * @param name
     * @throws NameLetterException
     */
    private boolean validateLettersAndSpaces(String nombreGranja) {
        boolean correcto = true;
        String regex = "[a-zA-Z ]+";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(nombreGranja);

        if (!matcher.matches()) {
            correcto = false;
        }
        return correcto;
    }

    /**
     * Metodo que actualiza la fecha de creación de la granja. Se comprueba si
     * la fecha a introducir es posterior al día de hoy, si es posterior, se
     * muestra una alerta y se reinicia la tabla con el valor anterior, si no es
     * posterior, se modifica la fecha.
     *
     * @param event
     */
    @FXML
    private void modificarFechaCreacionGranja(Event event) {
        GranjaEntity granja = (GranjaEntity) ((TableColumn.CellEditEvent) event).getRowValue();
        Date fecha = (Date) ((TableColumn.CellEditEvent) event).getNewValue();
        // Creating the LocalDatetime object
        LocalDate currentLocalDate = LocalDate.now();

        // Getting system timezone
        ZoneId systemTimeZone = ZoneId.systemDefault();

        // converting LocalDateTime to ZonedDateTime with the system timezone
        ZonedDateTime zonedDateTime = currentLocalDate.atStartOfDay(systemTimeZone);

        // converting ZonedDateTime to Date using Date.from() and ZonedDateTime.toInstant()
        Date utilDate = Date.from(zonedDateTime.toInstant());
        if (fecha.after(utilDate)) {
            try {
                LOGGER.severe(user);
                Alert alert = new Alert(Alert.AlertType.ERROR, "No puede cambiar una fecha la cual su fecha de creacion sea posterior al dia de hoy");
                alert.show();
                Collection<GranjaEntity> granjas = null;
                granjas = granjaInterface.getAllGranjas();
                cargarDatosEnTablaPorTodas(granjas);
            } catch (ClienteServidorConexionException ex) {
                Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BDServidorException ex) {
                Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                granja.setFechaCreacion(fecha);
                granjaInterface.editarGranja(granja);
                Collection<GranjaEntity> granjas = null;
                granjas = granjaInterface.getAllGranjas();
                cargarDatosEnTablaPorTodas(granjas);
            } catch (ClienteServidorConexionException ex) {
                Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BDServidorException ex) {
                Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo que actualiza el granjero al que pertenece la granja.
     *
     * @param event
     */
    @FXML
    private void modificarGranjero(Event event) {
        try {
            GranjaEntity granja = (GranjaEntity) ((TableColumn.CellEditEvent) event).getRowValue();
            GranjeroEntity granjero = (GranjeroEntity) ((TableColumn.CellEditEvent) event).getNewValue();
            // granjaInterface.cambiarNombreDeLaGranja(String.valueOf(granja.getIdGranja()), nombreGranja);
            granja.setGranjero(granjero);
            granjaInterface.editarGranja(granja);
            Collection<GranjaEntity> granjas = null;
            granjas = granjaInterface.getAllGranjas();
            cargarDatosEnTablaPorTodas(granjas);
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDServidorException ex) {
            Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo que borra una granja. Si el usuario selecciona una granja, se
     * habilita el botón de Eliminar, antes de eliminarla, se mostrara un
     * mensaje el cual indicara si esta seguro de eliminar la granja. Si el
     * usuario elige Aceptar, se borrara la granja y se actualizara la tabla sin
     * esa granja, si el usuario elige Cancelar, se moestrara un mensaje y no se
     * borrara la granja.
     *
     * @param event
     */
    @FXML
    public void eliminarGranja(ActionEvent event) {
        GranjaEntity granja;
        granja = (GranjaEntity) tableViewGranja.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Esta seguro de querer borrar esa granja?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            try {
                LOGGER.info("Borrado de la granja");
                granjaInterface.borrarGranja(String.valueOf(granja.getIdGranja()));
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Se ha eliminado correctamente la granja");
                alert2.show();
                Collection<GranjaEntity> granjas = null;
                granjas = granjaInterface.getAllGranjas();
                cargarDatosEnTablaPorTodas(granjas);
            } catch (ClienteServidorConexionException ex) {
                Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BDServidorException ex) {
                Logger.getLogger(GranjaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION, "No se ha eliminado la granja");
            alert3.show();
        }

    }

    /**
     * Metodo que carga los granjeros en las ComboBox de la tabla y Tipo
     * elegido.
     *
     */
    private void cargarGranjeros() {
        granjeroInterface = new GranjeroManagerImplementation();
        ObservableList granjeros = FXCollections.observableArrayList(granjeroInterface.getAllGranjeros());
        columnGranjeroGranja.setCellFactory(ComboBoxTableCell.forTableColumn(granjeros));
    }

    /**
     * Metodo que comprueba si el usuario registrado es un granjero o un
     * trabajador
     *
     * @param user
     */
    public void granjeroOTrabajador(UserEntity user) {
        if (user.getUserPrivilege().equals(UserPrivilegeType.TRABAJADOR)) {
            btnCreate.setVisible(false);
            btnDelete.setVisible(false);
            tableViewGranja.setEditable(false);
        }
    }

}

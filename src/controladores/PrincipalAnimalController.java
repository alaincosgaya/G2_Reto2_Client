package controladores;

import clases.AnimalEntity;
import clases.DateEditingCell;
import clases.EstadoAnimal;
import clases.SexoAnimal;
import clases.TipoAnimal;
import clases.ZonaEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import excepciones.ConnectException;
import excepciones.InvalidNameException;
import factoria.AnimalImplementacion;
import factoria.FactoriaAnimal;
import factoria.ZonaManagerImplementation;
import interfaces.InterfazAnimal;
import interfaces.ZonaInterface;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.FormatStringConverter;
import javafx.util.converter.LongStringConverter;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Clase FXML del controlador de la ventana PrincipalAnimal, la cual se
 * encargara de realizar todas las acciones pertinentes redactadas en la
 * documentacion
 *
 * @author Jonathan Camacho
 */
public class PrincipalAnimalController {

    private static final Logger LOGGER = Logger.getLogger(PrincipalAnimalController.class.getName());

    private Stage stage;
    //interfaces que se utilizaran en el controlador
    private InterfazAnimal animalManager;
    private ZonaInterface zonaManager;
    //Listas que se utilizaran en el controlador
    private ObservableList<AnimalEntity> animales;
    private ObservableList estados;
    private ObservableList sexo;
    private ObservableList tipo;

    //Clase animalEntity
    private AnimalEntity animalEntity;
    //Elementos de la ventana PrincipalAnimal FXML
    /**
     * Panel de Principal Animal.
     */
    @FXML
    private BorderPane panePrincipalAnimal;
    /**
     * Tabla en la que se cargarán los animales.
     */
    @FXML
    private TableView tablaAnimal;
    /**
     * Columna en la cual aparecerá el nombre del animal.
     */
    @FXML
    private TableColumn colNombre;
    /**
     * Columna en la cual aparecerá el tipo de animal.
     */
    @FXML
    private TableColumn<TipoAnimal, TipoAnimal> colTipo;
    /**
     * Columna en la cual aparecerá el estado de dicho animal.
     */
    @FXML
    private TableColumn<EstadoAnimal, EstadoAnimal> colEstado;
    /**
     * Columna en la cual aparecerá el sexo del animal.
     */
    @FXML
    private TableColumn<SexoAnimal, SexoAnimal> colSexo;
    /**
     * Columna en la cual aparecerá la fecha en la que nació tal animal.
     */
    @FXML
    private TableColumn colFechaNacimiento;
    /**
     * Columna en la cual aparecerá el nombre de la zona a la que pertenece.
     */
    @FXML
    private TableColumn<AnimalEntity, String> colZona;
    /**
     * ComboBox en la cual se podrá seleccionar el tipo de filtro deseado.
     */
    @FXML
    private ComboBox<String> seleccionFiltro;
    /**
     * ComboBox en la cual, en función al filtro seleccionado, permitirá al
     * usuario seleccionar el tipo de dato por el que se desea filtrar.
     */
    @FXML
    private ComboBox seleccionDato;
    /**
     * Boton con el cual se generara el informe.
     */
    @FXML
    private Button btnInforme;
    /**
     * Boton con el cual se permitirá al usuario ir al formulario de creacion de
     * animales.
     */
    @FXML
    private Button btnCrearAnimal;
    /**
     * Boton con el cual se permitirá la búsqueda de los animales en funcion al
     * filtrado seleccionado.
     */
    @FXML
    private Button btnBuscar;
    /**
     * Boton con el cual se permitirá la eliminación del animal seleccionado en
     * la tabla.
     */
    @FXML
    private Button btnEliminar;

    /**
     * Metodo que indica el stage.
     *
     * @param stage1
     */
    public void setStage(Stage stage1) {
        stage = stage1;
    }

    /**
     * Inicializacion de la ventana PrincipalAnimal en la cual se tendra en
     * cuenta lo siguiente:
     *
     * Se inicia la ventana con la tabla llena de datos de todos los animales,
     * si existen. Ambas comboBox empezarán vacías. El botón Buscar estará
     * deshabilitado. El botón Añadir animal estará habilitado. El botón
     * Eliminar estará deshabilitado. El botón Informe estará habilitado. El
     * tamaño no es ajustable (360×640)
     *
     * @param root
     */
    public void initStage(Parent root) {
        try {
            // Inicializacion
            Scene scene = new Scene(root, 640, 360);
            stage.setResizable(false);
            stage.setTitle("PrincipalAnimal");
            stage.setScene(scene);
            //implementacion de AnimalImplementation
            LOGGER.info("Inicializacion de los diferentes elementos que componen la ventana PrincipalAnimal");
            animalManager = FactoriaAnimal.getInterfazAnimalImplementacion();
            //inicializamos la Tabla Animal con sus columnas
            tablaAnimal.setEditable(true);
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreAnimal"));
            colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
            colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
            colZona.setCellValueFactory(new PropertyValueFactory<>("zona"));
            //columnas editables de la tabla
            estados = FXCollections.observableArrayList(EstadoAnimal.values());
            colEstado.setCellFactory(ComboBoxTableCell.forTableColumn(estados));
            sexo = FXCollections.observableArrayList(SexoAnimal.values());
            colSexo.setCellFactory(ComboBoxTableCell.forTableColumn(sexo));
            tipo = FXCollections.observableArrayList(TipoAnimal.values());
            colTipo.setCellFactory(ComboBoxTableCell.forTableColumn(tipo));
            colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
            colFechaNacimiento.setCellFactory(new Callback<TableColumn, TableCell>() {
                @Override
                public TableCell call(TableColumn p) {
                    DateEditingCell dateEdit = new DateEditingCell();
                    return dateEdit;
                }
            });
            //metodo encargado del editado de la columna zona
            cargarZonasColumn();
            //lista de filtros inicializada
            seleccionFiltro();
            //llamada al metodo que selecciona el tipo de datos a cargar en la comboBox en funcion al filtro seleccionado
            seleccionFiltro.getSelectionModel().selectedItemProperty().addListener(this::seleccionDato);
            //commit que guardara los cambios reslizados en las columnas
            colEstado.setOnEditCommit(this::modificarEstado);
            colSexo.setOnEditCommit(this::modificarSexo);
            colTipo.setOnEditCommit(this::modificarTipo);
            colNombre.setOnEditCommit(this::modificarNombre);
            colFechaNacimiento.setOnEditCommit(this::modificarFecha);
            colZona.setOnEditCommit(this::modificarZona);
            //botones con la accion que ejecutaran
            btnBuscar.setOnAction(this::buscarAnimal);
            btnCrearAnimal.setOnAction(this::aniadirAnimal);
            btnEliminar.setOnAction(this::eliminarAnimal);
            btnInforme.setOnAction(this::generarInforme);
            //Control de seleccion de fila, en caso de seleccionar una se controla que el boton este habilitado
            tablaAnimal.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    btnEliminar.setDisable(false);
                } else {
                    btnEliminar.setDisable(true);
                }
            });
            //Se inicia la ventana con los datos cargados en la tabla
            animales = FXCollections.observableArrayList(animalManager.getAllAnimales());
            tablaAnimal.setItems(animales);
            //se muestra el stage
            stage.show();
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.");
        } catch (BDServidorException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema con el servidor, por favor intentelo más tarde.");
        }
    }

    /**
     * Metodo el cual, mediante la pulsacion del boton informe, se encargara de
     * generar un informe con todos los datos de la tabla animal
     *
     * @param event
     */
    @FXML
    private void generarInforme(ActionEvent event) {
        JasperReport report;
        try {
            //ruta donde se compila el report
            LOGGER.info("Generando el informe con los datos de todos los animales actualmente introducidos");
            report = JasperCompileManager.compileReport("src/informes/ReportAnimales.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<AnimalEntity>) this.tablaAnimal.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Error al generar el informe, intentelo más tarde");

        }
    }

    /**
     * Metodo el cual, mediante la pulsacion del boton Añadir animal, permite
     * cerrar la ventana PrincipalAnimal y abrir la ventana CrearAnimal
     *
     * @param event
     */
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

        } catch (IOException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema al abrir el formulario de creación, disculpe las molestias");
        }
    }

    /**
     * Metodo el cual se encarga de cargar la comboBox seleccionFiltro con los
     * diferentes tipos de filtro disponibles.
     */
    @FXML
    public void seleccionFiltro() {
        LOGGER.info("Cargado de datos en la combo Filtro");
        ObservableList<String> filtro = FXCollections.observableArrayList("Tipo", "Estado", "Sexo", "Todos");
        seleccionFiltro.setItems(filtro);
    }

    /**
     * Metodo el cual se encarga de cargar los datos de la comboBox
     * seleccionDato en funcion del tipo de filtro que se ha escogido
     * previamente.
     *
     * @param ov
     * @param oldValue
     * @param newValue
     */
    @FXML
    private void seleccionDato(ObservableValue ov, String oldValue, String newValue) {
        LOGGER.info("Cargado de datos en la combo Dato");
        if (newValue != null) {

            String filtro = seleccionFiltro.getValue();
            // TrabajadorClient webClientTrabajador = new TrabajadorClient();
            seleccionDato.setItems(null);
            //Collection lista = null;
            if (filtro.equalsIgnoreCase("Tipo")) {
                btnBuscar.setDisable(false);
                ObservableList<TipoAnimal> listaTipo = FXCollections.observableArrayList(TipoAnimal.values());
                seleccionDato.setItems(listaTipo);
            }
            if (filtro.equalsIgnoreCase("Estado")) {
                btnBuscar.setDisable(false);
                ObservableList<EstadoAnimal> listaEstado = FXCollections.observableArrayList(EstadoAnimal.values());
                seleccionDato.setItems(listaEstado);
            }
            if (filtro.equalsIgnoreCase("Sexo")) {
                btnBuscar.setDisable(false);
                ObservableList<SexoAnimal> listaSexo = FXCollections.observableArrayList(SexoAnimal.values());
                seleccionDato.setItems(listaSexo);
            }
            if (filtro.equalsIgnoreCase("Todos")) {
                btnBuscar.setDisable(false);
            }
        }
    }

    /**
     * Metodo el cual se encarga de buscar a los animales en funcion de los
     * filtros seleccionados en las comboBox seleccionarFiltro y
     * seleccionarDato.
     *
     * @param event
     */
    @FXML
    private void buscarAnimal(ActionEvent event) {
        LOGGER.info("Busqueda de los animales en funcion del filtro deseado");
        try {
            String filtro = (String) seleccionFiltro.getValue();
            Collection<AnimalEntity> animales = null;
            String dato;
            switch (filtro) {
                case ("Tipo"):
                    dato = seleccionDato.getValue().toString();
                    animales = animalManager.getAnimalesPorTipo(dato);
                    break;
                case ("Estado"):
                    dato = seleccionDato.getValue().toString();
                    animales = animalManager.getAnimalesPorEstado(dato);
                    break;
                case ("Sexo"):
                    dato = seleccionDato.getValue().toString();
                    animales = animalManager.getAnimalesPorSexo(dato);
                    break;
                case ("Todos"):
                    animales = animalManager.getAllAnimales();
                    break;
            }
            cargarDatos(animales);
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.");
        } catch (BDServidorException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema con el servidor, por favor intentelo más tarde.");
        } catch (Exception ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema debido a que no se ha seleccionado el filtro correctamente, por favor agrege uno.");
        }

    }

    /**
     * Metodo el cual se encarga de cargar en la tabla los datos de los animales
     * previamente buscados.
     *
     * @param animalesList
     */
    public void cargarDatos(Collection<AnimalEntity> animalesList) {
        LOGGER.info("Cargado de datos en la tabla");
        animales = FXCollections.observableArrayList(animalesList);
        tablaAnimal.setItems(animales);
    }

    //metodos disponibles para el modificado de los atributos de la tabla animal
    /**
     * Metodo el cual se encarga de modificar el estado de un animal. Al hacer
     * click en la celda se abrira una comboBox con los valores disponibles
     *
     * @param event
     */
    @FXML
    public void modificarEstado(Event event) {
        try {
            LOGGER.info("Se procede a realizar el cambio de estado");
            AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
            String estado = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
            animalManager.cambiarEstadoAnimal(animal.getIdAnimal(), estado);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de estado");
            alert.show();
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.");
        } catch (BDServidorException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema con el servidor, por favor intentelo más tarde.");
        }

    }

    /**
     * Metodo el cual se encarga de modificar el sexo de un animal. Al hacer
     * click en la celda se abrira una comboBox con los valores disponibles
     *
     * @param event
     */
    @FXML
    private void modificarSexo(Event event) {
        try {
            LOGGER.info("Se procede a realizar el cambio de Sexo");
            AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
            String sexo = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
            animalManager.cambiarSexoAnimal(animal.getIdAnimal(), sexo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de sexo");
            alert.show();
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.");
        } catch (BDServidorException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema con el servidor, por favor intentelo más tarde.");
        }
    }

    /**
     * Metodo el cual se encarga de modificar el tipo de un animal. Al hacer
     * click en la celda se abrira una comboBox con los valores disponibles
     *
     * @param event
     */
    @FXML
    private void modificarTipo(Event event) {
        try {
            LOGGER.info("Se procede a realizar el cambio de Tipo");
            AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
            String tipo = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
            animalManager.cambiarTipoAnimal(animal.getIdAnimal(), tipo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de tipo");
            alert.show();
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.");
        } catch (BDServidorException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema con el servidor, por favor intentelo más tarde.");
        }
    }

    /**
     * Metodo el cual se encarga de modificar el nombre de un animal. Al hacer
     * click en la celda habilitara la edicion en la celda. Para confirmar la
     * edicion, sera necesario pulsar la tecla enter.
     *
     * @param event
     */
    @FXML
    private void modificarNombre(Event event) {
        LOGGER.info("Se procede a realizar el cambio de Nombre");
        AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
        try {

            String nombre = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
            validarMinimoCaracteresPattern(nombre);
            animalManager.cambiarNombreAnimal(animal.getIdAnimal(), nombre);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de nombre");
            alert.show();
        } catch (InvalidNameException ine) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El nombre contiene muy pocos caracteres, mínimo se requieren 2");
            alert.show();
            colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("El campo no puede quedar en blanco, por favor, introduzca un nombre adecuado");
            colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        } catch (NotFoundException nfe) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, nfe);
            mostrarError("El campo no puede quedar en blanco, por favor, introduzca un nombre");
            colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.");
        } catch (BDServidorException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema con el servidor, por favor intentelo más tarde.");
        }

    }

    /**
     * Metodo el cual se encarga de modificar la fecha de nacimiento de un
     * animal. Al hacer click en la celda, se permitira la seleccion de la fecha
     * deseada.
     *
     * @param event
     */
    @FXML
    private void modificarFecha(Event event) {
        try {
            LOGGER.info("Se procede a realizar el cambio de fecha de nacimiento del animal");

            AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
            Date fecha = (Date) ((TableColumn.CellEditEvent) event).getNewValue();
            animal.setFechaNacimiento(fecha);
            animalManager.editarAnimal(animal);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de fecha");
            alert.show();
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.");
        } catch (BDServidorException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema con el servidor, por favor intentelo más tarde.");
        }

    }

    /**
     * Metodo el cual se encarga de modificar la zona de un animal. Al hacer
     * click en la celda habilitara una comboBox con las zonas disponibles.
     *
     * @param event
     */
    @FXML
    private void modificarZona(Event event) {
        try {
            LOGGER.info("Se procede a realizar el cambio de zona a la que pertenece el animal");
            AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
            ZonaEntity zona = (ZonaEntity) ((TableColumn.CellEditEvent) event).getNewValue();
            //  animalManager.cambiarZonaAnimal(animal.getIdAnimal(), zona);
            animal.setZona(zona);
            animalManager.editarAnimal(animal);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de zona del animal");
            alert.show();
        } catch (ClienteServidorConexionException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.");
        } catch (BDServidorException ex) {
            Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            mostrarError("Ha surgido un problema con el servidor, por favor intentelo más tarde.");
        }
    }

    /**
     * Metodo el cual se encarga de eliminar una fila de la tabla animal. Al
     * pulsar el boton para eliminar la fila, se pedira confirmacion. En caso de
     * que el usuario confirmar dicha eliminacion, la fila desaparecera de la
     * base de datos, refrescara la tabla y el boton eliminar se deshabilitara
     * hasta que se seleccione otra fila.
     *
     * @param event
     */
    @FXML
    private void eliminarAnimal(ActionEvent event) {
        LOGGER.info("Se procede a eliminar al animal");
        AnimalEntity animal = (AnimalEntity) tablaAnimal.getSelectionModel().getSelectedItem();
        //informamos al usuario con un alert de confirmacion.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estas seguro de que quieres eliminar al animal?");
        Button btnClose = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        btnClose.setText("Eliminar");
        // Muestra el alert a la espera de la pulsacion de un boton del alert.
        Optional<ButtonType> close = alert.showAndWait();
        if (ButtonType.OK.equals(close.get())) {
            try {
                animalManager.eliminarAnimal(animal.getIdAnimal());
                alert = new Alert(Alert.AlertType.INFORMATION, "Animal eliminado exitosamente");
                alert.show();
                //carga de nuevo los datos de la tabla
                Collection<AnimalEntity> animales = null;
                animales = animalManager.getAllAnimales();
                cargarDatos(animales);

            } catch (ClienteServidorConexionException ex) {
                Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
                mostrarError("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.");
            } catch (BDServidorException ex) {
                Logger.getLogger(PrincipalAnimalController.class.getName()).log(Level.SEVERE, null, ex);
                mostrarError("Ha surgido un problema con el servidor, por favor intentelo más tarde.");
            }
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION, "Borrado Cancelado");
            alert.show();
        }
    }

    /**
     * Metodo el cual se encarga de cargar las zonas en la celda seleccionada de
     * la columna Zonas de la tabla animales.
     */
    private void cargarZonasColumn() {
        zonaManager = new ZonaManagerImplementation();
        ObservableList zonas = FXCollections.observableArrayList(zonaManager.getAllZonas());
        colZona.setCellFactory(ComboBoxTableCell.forTableColumn(zonas));
    }

    /**
     * Metodo con el cual se mostrarán los errores al usuario
     *
     * @param errorMsg
     */
    protected void mostrarError(String errorMsg) {
        LOGGER.info("Se procede a mostrar por pantalla el error");
        //Muestra el alert de error con el mensaje correspondiente
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();

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

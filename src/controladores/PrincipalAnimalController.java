package controladores;

import clases.AnimalEntity;
import clases.DateEditingCell;
import clases.EstadoAnimal;
import clases.SexoAnimal;
import clases.TipoAnimal;
import clases.Zona;
import factoria.AnimalImplementacion;
import factoria.ZonaImplementacion;
import interfaces.InterfazAnimal;
import interfaces.InterfazZona;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Observable;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.FormatStringConverter;
import javafx.util.converter.LongStringConverter;

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
    private InterfazZona zonaManager;

    private ObservableList<AnimalEntity> animales;
    private ObservableList estados;
    private ObservableList sexo;
    private ObservableList tipo;

    private boolean agregarAnimal;
    //Clase animalEntity
    private AnimalEntity animalEntity;
    //Elementos de la ventana PrincipalAnimal FXML
    @FXML
    private BorderPane panePrincipalAnimal;
    @FXML
    private Button btnInforme;
    @FXML
    private Button btnCrearAnimal;
    @FXML
    private TableView tablaAnimal;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn<TipoAnimal, TipoAnimal> colTipo;
    @FXML
    private TableColumn<EstadoAnimal, EstadoAnimal> colEstado;
    @FXML
    private TableColumn<SexoAnimal, SexoAnimal> colSexo;
    @FXML
    private TableColumn colFechaNacimiento;
    @FXML
    private TableColumn<AnimalEntity, String> colZona;
    @FXML
    private ComboBox<String> seleccionFiltro;
    @FXML
    private ComboBox seleccionDato;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnInsertar;

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
        // TODO
        Scene scene = new Scene(root, 640, 360);
        stage.setResizable(false);
        stage.setTitle("PrincipalAnimal");
        stage.setScene(scene);
        agregarAnimal = false;
        //implemenmtacion de AnimalImplementation
        animalManager = new AnimalImplementacion();
        //btnBuscar.setDisable(true);
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
        //llamada al metodo que selecciona el tipo de datos a cargar en la comboBox
        //en funcion al filtro seleccionado
        seleccionFiltro.getSelectionModel().selectedItemProperty().addListener(this::seleccionDato);
        //commit que guardara los cambios reslizados en las columnas
        colEstado.setOnEditCommit(this::modificarEstado);
        colSexo.setOnEditCommit(this::modificarSexo);
        colTipo.setOnEditCommit(this::modificarTipo);
        colNombre.setOnEditCommit(this::modificarNombre);
        colFechaNacimiento.setOnEditCommit(this::modificarFecha);
        colZona.setOnEditCommit(this::modificarZona);
        //botones con la accion que ejecutaran
        btnInsertar.setOnAction(this::insertarAnimal);
        btnBuscar.setOnAction(this::buscarAnimal);
        btnCrearAnimal.setOnAction(this::aniadirAnimal);
        btnEliminar.setOnAction(this::eliminarAnimal);
        //Control de seleccion de fila, en caso de seleccionar una se controla que
        //el boton este habilitado
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
    }

    /**
     * Metodo el cual, mediante la pulsacion del boton insertar "+", se
     * encargara de generar un informe con todos los datos de la tabla animal
     *
     * @param event
     */
    @FXML
    private void generarInforme(ActionEvent event) {

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

        } catch (IOException e) {

        }
    }

    /**
     * Metodo el cual se encarga de cargar la comboBox seleccionFiltro con los
     * diferentes tipos de filtro disponibles.
     */
    @FXML
    public void seleccionFiltro() {
        ObservableList<String> filtro = FXCollections.observableArrayList("Tipo", "Estado", "Sexo", "Todos");
        seleccionFiltro.setItems(filtro);
    }

    /**
     * metodo el cual se encarga de cargar los datos de la comboBox
     * seleccionDato en funcion del tipo de filtro que se ha escogido
     * previamente.
     *
     * @param ov
     * @param oldValue
     * @param newValue
     */
    @FXML
    private void seleccionDato(ObservableValue ov, String oldValue, String newValue) {
        if (newValue != null) {

            String filtro = seleccionFiltro.getValue();
            // TrabajadorClient webClientTrabajador = new TrabajadorClient();
            seleccionDato.setItems(null);
            //Collection lista = null;
            if (filtro.equalsIgnoreCase("Tipo")) {
                ObservableList<TipoAnimal> listaTipo = FXCollections.observableArrayList(TipoAnimal.values());
                seleccionDato.setItems(listaTipo);
            }
            if (filtro.equalsIgnoreCase("Estado")) {
                ObservableList<EstadoAnimal> listaEstado = FXCollections.observableArrayList(EstadoAnimal.values());
                seleccionDato.setItems(listaEstado);
            }
            if (filtro.equalsIgnoreCase("Sexo")) {
                ObservableList<SexoAnimal> listaSexo = FXCollections.observableArrayList(SexoAnimal.values());
                seleccionDato.setItems(listaSexo);
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

    }

    /**
     * Metodo el cual se encarga de cargar en la tabla los datos de los animales
     * previamente buscados.
     *
     * @param animalesList
     */
    public void cargarDatos(Collection<AnimalEntity> animalesList) {

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

        AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
        String estado = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
        animalManager.cambiarEstadoAnimal(animal.getIdAnimal(), estado);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de estado");
        alert.show();

    }

    /**
     * Metodo el cual se encarga de modificar el sexo de un animal. Al hacer
     * click en la celda se abrira una comboBox con los valores disponibles
     *
     * @param event
     */
    @FXML
    private void modificarSexo(Event event) {
        AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
        String sexo = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
        animalManager.cambiarSexoAnimal(animal.getIdAnimal(), sexo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de sexo");
        alert.show();
    }

    /**
     * Metodo el cual se encarga de modificar el tipo de un animal. Al hacer
     * click en la celda se abrira una comboBox con los valores disponibles
     *
     * @param event
     */
    @FXML
    private void modificarTipo(Event event) {
        AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
        String tipo = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
        animalManager.cambiarTipoAnimal(animal.getIdAnimal(), tipo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de tipo");
        alert.show();
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
        AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
        String nombre = String.valueOf(((TableColumn.CellEditEvent) event).getNewValue());
        animalManager.cambiarNombreAnimal(animal.getIdAnimal(), nombre);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de nombre");
        alert.show();
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
        //animalEntity = new AnimalEntity();
        AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
        //animalEntity.setFechaNacimiento((Date) ((TableColumn.CellEditEvent) event).getNewValue());

        Date fecha = (Date) ((TableColumn.CellEditEvent) event).getNewValue();

        animal.setFechaNacimiento(fecha);
        animalManager.editarAnimal(animal);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de fecha");
        alert.show();
    }

    /**
     * Metodo el cual se encarga de modificar la zona de un animal. Al hacer
     * click en la celda habilitara una comboBox con las zonas disponibles.
     *
     * @param event
     */
    @FXML
    private void modificarZona(Event event) {
        AnimalEntity animal = (AnimalEntity) ((TableColumn.CellEditEvent) event).getRowValue();
        Zona zona = (Zona) ((TableColumn.CellEditEvent) event).getNewValue();
        //  animalManager.cambiarZonaAnimal(animal.getIdAnimal(), zona);
        animal.setZona(zona);
        animalManager.editarAnimal(animal);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de zona del animal");
        alert.show();
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
        AnimalEntity animal = (AnimalEntity) tablaAnimal.getSelectionModel().getSelectedItem();
        //informamos al usuario con un alert de confirmacion.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estas seguro de que quieres eliminar al animal?");
        Button btnClose = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        btnClose.setText("Eliminar");
        // Muestra el alert a la espera de la pulsacion de un boton del alert.
        Optional<ButtonType> close = alert.showAndWait();
        if (ButtonType.OK.equals(close.get())) {
            animalManager.eliminarAnimal(animal.getIdAnimal());
            buscarAnimal(event);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////Agregar valores a la tabla, de momento no funcional
    @FXML
    private void insertarAnimal(ActionEvent event) {
        if (!agregarAnimal) {
            agregarAnimal = true;
            colNombre.setEditable(true);
            colTipo.setEditable(true);
            colEstado.setEditable(true);
            colSexo.setEditable(true);
            colFechaNacimiento.setEditable(true);
            colZona.setEditable(true);
            animalEntity = new AnimalEntity();
        } else {
            agregarAnimal = false;
        }
    }

    /**
     * Metodo el cual se encarga de cargar las zonas en la celda seleccionada de
     * la columna Zonas de la tabla animales.
     */
    private void cargarZonasColumn() {
        zonaManager = new ZonaImplementacion();
        ObservableList zonas = FXCollections.observableArrayList(zonaManager.getAllZonas());
        colZona.setCellFactory(ComboBoxTableCell.forTableColumn(zonas));
    }
}

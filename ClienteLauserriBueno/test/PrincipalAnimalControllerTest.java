
import aplicacion.App;
import clases.AnimalEntity;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javax.swing.text.TableView;
import static junit.framework.Assert.assertEquals;
import org.junit.Assert;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;

/**
 * Test en el que se comprueban todos los métodos del controlador Principal
 * Animal.
 *
 * @author Jonathan Camacho
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrincipalAnimalControllerTest extends ApplicationTest {

    private TableView tablaAnimal;

    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
    }

    /**
     * Metodo que permite la navegacion entre ventanas.
     */
    public void navegacion() {
        clickOn("#textUser");
        write("pepa");
        clickOn("#textPasswd");
        write("abcd*1234");
        clickOn("#btnLogin");
        sleep(1000);
        clickOn("#inicioMenu");
        clickOn("#animalMenuItem");
        sleep(1000);
    }

    /**
     * Metodo Test que prueba que se puede acceder al formulario de creacion de
     * animales.
     */
    @Test
    @Ignore
    public void testA_abrirCrearAnimal() {
        navegacion();
        clickOn("#btnCrearAnimal");
        verifyThat("#txtName", isVisible());
    }

    /**
     * Test que comprueba que se puede eliminar a un animal seleccionado en la
     * tabla. En caso de que de error en el test, descomentar el clicOn que pone
     * Eliminar.
     */
    @Test
    @Ignore
    public void testB_EliminarAnimal() {
        navegacion();
        //int rowCount=tabla.getItems().size();
        Node fila = lookup(".table-row-cell").nth(0).query();
        clickOn(fila);
        verifyThat("#btnEliminar", isEnabled());
        clickOn("#btnEliminar");
        verifyThat("¿Estas seguro de que quieres eliminar al animal?", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
        //clickOn("Eliminar");
        verifyThat("Animal eliminado exitosamente", isVisible());
    }

    /**
     * Test que comprueba que se puede cancelar la eliminación de un animal.
     */
    @Test
    @Ignore
    public void testC_CancelarEliminarAnimal() {
        navegacion();
        Node fila = lookup(".table-row-cell").nth(0).query();
        clickOn(fila);
        verifyThat("#btnEliminar", isEnabled());
        clickOn("#btnEliminar");
        verifyThat("¿Estas seguro de que quieres eliminar al animal?", isVisible());
        clickOn("Cancelar");
        verifyThat("Borrado Cancelado", isVisible());
    }

    /**
     * Test que comprueba que si ocurre algun tipo de error con la conexion del
     * cliente con el servidor a la hora de realizar una eliminacion, salta una
     * excepción tipo ClienteServidorConexionException.
     *
     * Cabe mencionar que para la realización de este test, es necesario apagar
     * el servidor, de tal forma que se simula el error.
     *
     * Se ha añadido un sleep para que de tiempo a realizar el testeo.
     */
    @Test
    @Ignore
    public void testD_ErrorEliminarAnimal() {
        navegacion();
        //sleep para cerrar el servidor
        int milisegundos = 15000;
        Node fila = lookup(".table-row-cell").nth(0).query();
        clickOn(fila);
        verifyThat("#btnEliminar", isEnabled());
        clickOn("#btnEliminar");
        verifyThat("¿Estas seguro de que quieres eliminar al animal?", isVisible());
        sleep(milisegundos);
        press(KeyCode.ENTER);
        verifyThat("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.", isVisible());
    }

    /**
     * Test que comprueba que se informa al usuario cuando no se han introducido
     * los filtros debidamente.
     */
    @Test
    @Ignore
    public void testE_FiltrosVacios() {
        navegacion();
        clickOn("#btnBuscar");
        verifyThat("Ha surgido un problema debido a que no se ha seleccionado el filtro correctamente, por favor agrege uno.", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
        clickOn("#seleccionFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnBuscar");
        verifyThat("Ha surgido un problema debido a que no se ha seleccionado el filtro correctamente, por favor agrege uno.", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
        clickOn("#seleccionFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnBuscar");
        verifyThat("Ha surgido un problema debido a que no se ha seleccionado el filtro correctamente, por favor agrege uno.", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
        clickOn("#seleccionFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnBuscar");
        verifyThat("Ha surgido un problema debido a que no se ha seleccionado el filtro correctamente, por favor agrege uno.", isVisible());
        sleep(1000);
        //clickOn("#seleccionDato");
        //type(KeyCode.DOWN);
        //type(KeyCode.ENTER);

    }

    /**
     * Metodo con el que se prueba que el filtro funciona e influye en la tabla.
     * **AVISO** El test puede resultar fallido si da la casualidad de que los
     * nombres de los animales que esta comparando son iguales o no hay datos en
     * la tabla.
     */
    @Test
    @Ignore
    public void testF_ProbarFiltro() {
        navegacion();
        clickOn("#seleccionFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#seleccionDato");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnBuscar");
        Node fila1 = lookup("#colNombre").nth(1).query();
        clickOn("#seleccionFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnBuscar");
        Node fila2 = lookup("#colNombre").nth(1).query();
        assertNotEquals(fila1.toString(), fila2.toString());
    }

    /**
     * Test que demuestra la posibilidad de cambiar de nombre en la propia
     * tabla.
     */
    @Test
    @Ignore
    public void testG_ModificarNombre() {
        navegacion();
        Node celdaNombre = lookup("#colNombre").nth(1).query();
        doubleClickOn(celdaNombre);
        write("Perico");
        press(KeyCode.ENTER);
        sleep(1000);
        verifyThat("Se ha realizado el cambio de nombre", isVisible());
    }

    /**
     * Metodo que demuestra la poibilidad de cambiar de tipo al animal en la
     * propia tabla.
     */
    @Test
    @Ignore
    public void testH_ModificarTipo() {
        navegacion();
        Node celdaTipo = lookup("#colTipo").nth(1).query();
        doubleClickOn(celdaTipo);
        clickOn(celdaTipo);
        type(KeyCode.DOWN);
        verifyThat("Se ha realizado el cambio de tipo", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
    }

    /**
     * Metodo que comprueba la posibilidad de cambiar de estado de animal. Cabe
     * mencionar que si el estado que se elije es "Muerto", será eliminado de la
     * tabla en la proxima búsqueda que se realice.
     */
    @Test
    @Ignore
    public void testI_ModificarEstado() {
        navegacion();
        Node celdaEstado = lookup("#colEstado").nth(1).query();
        doubleClickOn(celdaEstado);
        clickOn(celdaEstado);
        type(KeyCode.DOWN);
        verifyThat("Se ha realizado el cambio de estado", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
    }

    /**
     * Metodo que comprueba la posibilidad de cambiar el sexo de un animal. En
     * caso de que el valor de la combo sea el último, descomentar la linea de
     * type(KeyCode.UP); y comentar la que pone DOWN.
     */
    @Test
    @Ignore
    public void testJ_ModificarSexo() {
        navegacion();
        Node celdaSexo = lookup("#colSexo").nth(1).query();
        doubleClickOn(celdaSexo);
        clickOn(celdaSexo);
        type(KeyCode.DOWN);
        //type(KeyCode.UP);
        verifyThat("Se ha realizado el cambio de sexo", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
    }

    /**
     * Metodo que comprueba la posibilidad de cambiar de Zona al animal.
     * Importante tener antes una o varias zonas creadas.
     */
    @Test
      @Ignore
    public void testK_ModificarZona() {
        navegacion();
        Node celdaZona = lookup("#colZona").nth(1).query();
        doubleClickOn(celdaZona);
        clickOn(celdaZona);
        type(KeyCode.DOWN);
        verifyThat("Se ha realizado el cambio de zona del animal", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
    }

    /**
     * Metodo que demuestra la posibilidad de cambiar de fecha de nacimiento a
     * un animal.
     */
    @Test
    @Ignore
    public void testL_ModificarFecha() {
        navegacion();
        Node celdaFecha = lookup("#colFechaNacimiento").nth(1).query();
        doubleClickOn(celdaFecha);
        clickOn(celdaFecha);
        eraseText(10);
        write("01/02/20");
        press(KeyCode.ENTER);
        sleep(1000);
        verifyThat("Se ha realizado el cambio de fecha", isVisible());
    }

    ////////////////////////////////////////////////ERRORES////////////////////////////////////////////////////////////
    /**
     * Test que informa al usuario de un error ClienteServidorConexionException
     * a la hora de cambiar de nombre. Antes de que el test pulse el presione
     * enter, hay un sleep de 10 segundos el cual permite cerrar el servidor. De
     * esta manera podremos comprobar que salta dicha excepcion
     */
    @Test
    @Ignore
    public void testM_ErrorModificarNombre() {
        navegacion();
        Node celdaNombre = lookup("#colNombre").nth(1).query();
        doubleClickOn(celdaNombre);
        write("Perico");
        //sleep para cerrar el servidor
        sleep(10000);
        press(KeyCode.ENTER);
        sleep(1000);
        verifyThat("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.", isVisible());
    }

    /**
     * Test que informa al usuario de un error ClienteServidorConexionException
     * a la hora de cambiar de tipo. Antes de que el test seleccione la opcion
     * de la combo, hay un sleep de 10 segundos el cual permite cerrar el
     * servidor. Tras cerrarlo, clicaremos de nuevo en la combo de la tabla para
     * que se vuelva a abrir. De esta manera podremos comprobar que salta dicha
     * excepcion
     *
     * **IMPORTANTE** Si en la celda ya esta seleccionado el último dato de la
     * combo, es necesario cambiarlo previamente a uno de los anteriores.
     */
    @Test
    @Ignore
    public void testN_ErrorModificarTipo() {
        navegacion();
        Node celdaTipo = lookup("#colTipo").nth(1).query();
        doubleClickOn(celdaTipo);
        clickOn(celdaTipo);
        //sleep para cerrar el servidor
        sleep(10000);
        type(KeyCode.DOWN);
        verifyThat("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
    }

    /**
     * Test que informa al usuario de un error ClienteServidorConexionException
     * a la hora de cambiar de estado. Antes de que el test seleccione la opcion
     * de la combo, hay un sleep de 10 segundos el cual permite cerrar el
     * servidor. Tras cerrarlo, clicaremos de nuevo en la combo de la tabla para
     * que se vuelva a abrir. De esta manera podremos comprobar que salta dicha
     * excepcion
     *
     * **IMPORTANTE** Si en la celda ya esta seleccionado el último dato de la
     * combo, es necesario cambiarlo previamente a uno de los anteriores.
     */
    @Test
    @Ignore
    public void testO_ErrorModificarEstado() {
        navegacion();
        Node celdaEstado = lookup("#colEstado").nth(1).query();
        doubleClickOn(celdaEstado);
        clickOn(celdaEstado);
        //sleep para cerrar el servidor
        sleep(10000);
        type(KeyCode.DOWN);
        verifyThat("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
    }

    /**
     * Test que informa al usuario de un error ClienteServidorConexionException
     * a la hora de cambiar de sexo. Antes de que el test seleccione la opcion
     * de la combo, hay un sleep de 10 segundos el cual permite cerrar el
     * servidor. Tras cerrarlo, clicaremos de nuevo en la combo de la tabla para
     * que se vuelva a abrir. De esta manera podremos comprobar que salta dicha
     * excepcion
     *
     * **IMPORTANTE** Si en la celda ya esta seleccionado el último dato de la
     * combo, es necesario cambiarlo previamente a uno de los anteriores.
     */
    @Test
    @Ignore
    public void testP_ErrorModificarSexo() {
        navegacion();
        Node celdaSexo = lookup("#colSexo").nth(1).query();
        doubleClickOn(celdaSexo);
        clickOn(celdaSexo);
        //sleep para cerrar el servidor
        sleep(10000);
        type(KeyCode.DOWN);
        verifyThat("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
    }

    /**
     * Test que informa al usuario de un error ClienteServidorConexionException
     * a la hora de cambiar de zona. Antes de que el test seleccione la opcion
     * de la combo, hay un sleep de 10 segundos el cual permite cerrar el
     * servidor. Tras cerrarlo, clicaremos de nuevo en la combo de la tabla para
     * que se vuelva a abrir. De esta manera podremos comprobar que salta dicha
     * excepcion
     *
     **IMPORTANTE** Si en la celda ya esta seleccionado el último dato de la
     * combo, es necesario cambiarlo previamente a uno de los anteriores.
     */
    @Test
    @Ignore
    public void testQ_ErrorModificarZona() {
        navegacion();
        Node celdaZona = lookup("#colZona").nth(1).query();
        doubleClickOn(celdaZona);
        clickOn(celdaZona);
        //sleep para cerrar el servidor
        sleep(10000);
        type(KeyCode.DOWN);
        verifyThat("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.", isVisible());
        sleep(1000);
        press(KeyCode.ENTER);
    }

    /**
     * Test que informa al usuario de un error ClienteServidorConexionException
     * a la hora de cambiar de fecha. Antes de que el test pulse el presione
     * enter, hay un sleep de 10 segundos el cual permite cerrar el servidor. De
     * esta manera podremos comprobar que salta dicha excepcion
     */
    @Test
    @Ignore
    public void testR_ErrorModificarFecha() {
        navegacion();
        Node celdaFecha = lookup("#colFechaNacimiento").nth(1).query();
        //sleep para cerrar el servidor
        sleep(10000);
        doubleClickOn(celdaFecha);
        clickOn(celdaFecha);
        write("01/02/2021");
        press(KeyCode.ENTER);
        sleep(1000);
        verifyThat("Ha surgido un problema a la hora de conectarse con el servidor, por favor intentelo más tarde.", isVisible());
    }

    /*
    @Test
    @Ignore
    public void testS_ModificarFecha() {
    navegacion();
        clickOn("#btnInforme");
        sleep(2000);
        verifyThat("JasperViwer",isVisible());
    }
     */
}

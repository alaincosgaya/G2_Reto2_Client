
import aplicacion.App;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * Clase Test que prueba los metodos y validaciones del controlador
 * CrearAnimalController
 *
 * @author Jonathan Camacho
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrearAnimalControllerTest extends ApplicationTest {

    private TextField txtName;

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
     * Test que abre y cierra la ventana de CrearAnimal correctamente.
     */
    @Test
    @Ignore
    public void testA_AbrirCerrarCrearAnimal() {
        navegacion();
        clickOn("#btnCrearAnimal");
        verifyThat("#txtName", isVisible());
        verifyThat("#btnCrearAnimal", isDisabled());
        verifyThat("#btnAtras", isEnabled());
        clickOn("#btnAtras");
        verifyThat("#tablaAnimal", isVisible());
    }

    /**
     * Test que demuestra que se puede crear a un animal correctamente e informa
     * de ello al usuario.
     */
    @Test
  //  @Ignore
    public void testB_CrearAnimal() {
        navegacion();
        clickOn("#btnCrearAnimal");
        clickOn("#txtName");
        write("Rodolfin");
        clickOn("#comboSeleccionarTipo");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#comboSeleccionarSexo");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnCrearAnimal");
        verifyThat("Se han introducido los datos del animal", NodeMatchers.isVisible());
    }

    /**
     * Test que demuestra que cuando uno de los campos obligatorios esta vacío
     * (txtName en este caso), el botón Crear Animal esta vacío.
     */
    @Test
    @Ignore
    public void testC_CampoNombreVacio() {
        navegacion();
        clickOn("#btnCrearAnimal");
        clickOn("#txtName");
        write("");
        clickOn("#comboSeleccionarTipo");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#comboSeleccionarSexo");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnCrearAnimal");
        verifyThat("#btnCrearAnimal", isDisabled());
    }

    /**
     * Test que demuestra que cuando se mete un solo caracter en el nombre salta
     * una excepcion tipo InvalidNameException.
     */
    @Test
    @Ignore
    public void testD_NombreInvalido() {
        navegacion();
        clickOn("#btnCrearAnimal");
        clickOn("#txtName");
        write("a");
        clickOn("#comboSeleccionarTipo");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#comboSeleccionarSexo");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnCrearAnimal");
        verifyThat("El nombre contiene muy pocos caracteres, mínimo se requieren 2", NodeMatchers.isVisible());
    }

    /**
     * Test que comprueba que cuando hay un error en la comunicacion del cliente
     * con el servidor a la hora de crear un animal, se avisa al usuario. Antes
     * de que el test pulse el botón para crear, hay un sleep de 10 segundos el
     * cual permite cerrar el servidor. De esta manera podremos comprobar que
     * salta dicha excepcion
     */
    @Test
    @Ignore
    public void testE_ClienteServidorConexionServidor() {
        navegacion();
        clickOn("#btnCrearAnimal");
        clickOn("#txtName");
        write("Pepa");
        clickOn("#comboSeleccionarTipo");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#comboSeleccionarSexo");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        sleep(10000);
        clickOn("#btnCrearAnimal");
        verifyThat("Ha surgido un problema con el servidor, por favor intentelo más tarde.", NodeMatchers.isVisible());
    }

}

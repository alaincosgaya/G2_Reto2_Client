/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aplicacion.App;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Clase Test que comprueba que los metodos de la clase ZonaController
 * funcionan.
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrearZonaControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
    }

    public void navegacion() {
        clickOn("#textUser");
        write("pepa");
        clickOn("#textPasswd");
        write("abcd*1234");
        clickOn("#btnLogin");
        sleep(1000);
        clickOn("#inicioMenu");
        clickOn("#zonaMenuItem");
        sleep(1000);
    }

    /**
     * 
     */
    @Test
    //   @Ignore
    public void testA_abrirCrearZona() {
        navegacion();
        clickOn("#btnAnadir");
        verifyThat("#txtName", isVisible());
    }

    /**
     * Metodo con el que se prueba que el filtro funciona e influye en la tabla.
     * **AVISO** El test puede resultar fallido si da la casualidad de que los
     * nombres de los animales que esta comparando son iguales.
     */
    @Test
    @Ignore
    public void testB_ProbarFiltro() {
        navegacion();
        clickOn("#cBoxFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#cBoxOpcion");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnBuscar");
        Node fila1 = lookup("#colNombreZona").nth(1).query();
        clickOn("#seleccionFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnBuscar");
        Node fila2 = lookup("#colNombreZona").nth(1).query();
        assertNotEquals(fila1.toString(), fila2.toString());
    }

    /**
     * Test que demuestra la posibilidad de cambiar de nombre en la propia
     * tabla.
     */
    @Test
    @Ignore
    public void testC_ModificarNombre() {
        navegacion();
        Node celdaNombre = lookup("#colNombreZona").nth(1).query();
        doubleClickOn(celdaNombre);
        write("Vacas");
        press(KeyCode.ENTER);
        sleep(1000);
        //verifyThat("Se ha realizado el cambio de nombre", isVisible());
    }

    /**
     * Metodo que demuestra la posibilidad de cambiar de fecha de nacimiento a
     * una zona.
     */
    @Test
    @Ignore
    public void testD_ModificarFecha() {
        navegacion();
        Node celdaFecha = lookup("#colFechaCreacion").nth(1).query();
        doubleClickOn(celdaFecha);
        clickOn(celdaFecha);
        write("01/02/2021");
        press(KeyCode.ENTER);
        sleep(1000);
        //verifyThat("Se ha realizado el cambio de fecha", isVisible());
    }

    /**
     * Test que comprueba que se puede eliminar a una zona seleccionado en la
     * tabla.
     */
    @Test
    @Ignore
    public void testE_EliminarZona() {
        navegacion();
        //int rowCount=tabla.getItems().size();
        Node fila = lookup(".table-row-cell").nth(0).query();
        clickOn(fila);
        verifyThat("#btnEliminar", isEnabled());
        clickOn("#btnEliminar");
        //verifyThat("¿Estas seguro de que quieres eliminar al animal?", isVisible());
        //press(KeyCode.ENTER);
        //clickOn("Eliminar");
        //verifyThat("Animal eliminado exitosamente", isVisible());
    }

    /**
     * Test que comprueba que se abre la ventana de asignarTrabajadores.
     */
    @Test
    @Ignore
    public void testF_AsignarTrabajadorVentana() {
        navegacion();
        clickOn("#btnAsignar");
        verifyThat("#tablaTrabajador", isVisible());
    }
}

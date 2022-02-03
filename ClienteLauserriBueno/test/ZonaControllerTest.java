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
public class ZonaControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
    }

    /**
     * Metodo que permite la navegacion entre ventanas. llamaremos a este metodo
     * en todos los teses
     */
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
     * Test que demuestra que que se puede abrir la ventana de animal.
     */
    @Test
    @Ignore
    public void testA_abrirCrearZona() {
        navegacion();
        clickOn("#btnAnadir");
        verifyThat("#txtName", isVisible());
    }

    /**
     * Metodo con el que se prueba que el filtro funciona e influye en la tabla.
     * **AVISO** El test puede resultar fallido si da la casualidad de que los
     * nombres de las zonas que esta comparando son iguales o no hay nada en la
     * tabla.
     */
    @Test
    @Ignore
    public void testB_ProbarFiltro() {
        navegacion();
        clickOn("#cBoxFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#cBoxOpcion");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnBuscar");
        Node fila1 = lookup("#colNombreZona").nth(1).query();
        clickOn("#cBoxFiltro");
        type(KeyCode.DOWN);
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
     * tabla. El test dará erroneo en caso de que las zonas se llamen igual
     */
    @Test
    @Ignore
    public void testC_ModificarNombre() {
        String nombre1;
        String nombre2;
        navegacion();
        Node celdaNombre = lookup("#colNombreZona").nth(1).query();
        doubleClickOn(celdaNombre);
        nombre1 = celdaNombre.toString();
        write("Vacas");
        press(KeyCode.ENTER);
        nombre2 = celdaNombre.toString();
        sleep(1000);
        assertNotEquals(nombre1, nombre2);
    }

    /**
     * Metodo que demuestra la posibilidad de cambiar de fecha de nacimiento a
     * una zona. El test dara erroneo en caso de que las fechas sean las mismas
     */
    @Test
    @Ignore
    public void testD_ModificarFecha() {
        String fecha1;
        String fecha2;
        navegacion();
        Node celdaFecha = lookup("#colFechaCreacion").nth(1).query();
        doubleClickOn(celdaFecha);
        fecha1 = celdaFecha.toString();
        clickOn(celdaFecha);
        eraseText(10);
        write("01/02/2021");
        press(KeyCode.ENTER);
        fecha2 = celdaFecha.toString();
        sleep(1000);
        assertNotEquals(fecha1, fecha2);
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
        Node fila = lookup(".table-row-cell").nth(0).query();
        clickOn(fila);
        clickOn("#btnAsignar");
        verifyThat("#tablaTrabajador", isVisible());
    }
}

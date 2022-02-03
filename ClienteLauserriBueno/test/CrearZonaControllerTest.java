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
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Clase Test que comprueba que los metodos de la clase CrearZona funcionan.
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrearZonaControllerTest extends ApplicationTest {

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
     * Metodo que comprueba que se pueden crear zonas.
     */
    @Test
    @Ignore
    public void testA_CrearZona() {
        navegacion();
        clickOn("#btnAnadir");
        clickOn("#txtName");
        write("zona1");
        clickOn("#comboSeleccionarGranja");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnAnadir");
        verifyThat("#tablaZona", isVisible());
    }

    /**
     * Test que comprueba que se pueden abrir y cerrar la ventana CrearZona
     */
    @Test
    @Ignore
    public void testB_AbrirCerrarCrearZona() {
        navegacion();
        clickOn("#btnAnadir");
        verifyThat("#txtName", isVisible());
        clickOn("#btnAtras");
        verifyThat("#tablaZona", isVisible());
    }

    /**
     * test el cual comprueba que se puede ir a la ventana de asignar
     * trabajadores.
     */
    @Test
    @Ignore
    public void testC_AsignarTrabajadores() {
        navegacion();
        clickOn("#btnAnadir");
        clickOn("#anadirTrabajador");
        verifyThat("#tablaTrabajador", isVisible());
        Node fila = lookup("#.table-row-cell").nth(0).query();
        clickOn(fila);
        clickOn("#btnAsignar");
        verifyThat("¿Quiere seguir asignando trabajadores a la zona?", isVisible());
    }

    /**
     * Test que comprueba que el boton btnAnadir esta deshabilitado hasta que se
     * introduzcan los campos pertinenetes.
     */
    @Test
    @Ignore
    public void testD_ValidarDatos() {
        navegacion();
        clickOn("#btnAnadir");
        verifyThat("#btnAnadir", isDisabled());
    }

    /**
     * Test que comprueba que se puede volver a la VentanaZona
     */
    @Test
    @Ignore
    public void testE_Volver() {
        navegacion();
        clickOn("#btnAnadir");
        verifyThat("#txtName", isVisible());
        clickOn("#btnAtras");
        verifyThat("#tablaZona", isVisible());
    }
}

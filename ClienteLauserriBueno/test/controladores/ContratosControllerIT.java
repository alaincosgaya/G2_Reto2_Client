/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.controladores;

import aplicacion.App;
import clases.ContratoEntity;
import javafx.scene.Node;
import javafx.scene.control.TableView;
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
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 * Tests del controlador de la ventana ContratosControllerIT.
 *
 * @author Alain Cosgaya
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContratosControllerIT extends ApplicationTest {

    private TableView tablaContratos;

    public void navegacionGranjero() {
        clickOn("#textUser");
        write("admin");
        clickOn("#textPasswd");
        write("abcd*1234");
        clickOn("#btnLogin");
        sleep(1000);
        clickOn("#trabajadorMenu");
        clickOn("#contratosMenuItem");
        sleep(1000);
    }

    public void navegacionTrabajador() {
        clickOn("#textUser");
        write("idoia");
        clickOn("#textPasswd");
        write("abcd*1234");
        clickOn("#btnLogin");
        sleep(1000);
        clickOn("#trabajadorMenu");
        clickOn("#contratosMenuItem");
        sleep(1000);

    }

    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
    }

    /**
     * Test que verifica que elementos están habilitados y deshabilitados cuando
     * el usuario que ha entrado ha sido un granjero.
     *
     */
    @Test
    @Ignore
    public void testA_VerificarGranjero() {
        navegacionGranjero();
        verifyThat("#cBoxFiltro", isEnabled());
        verifyThat("#cBoxOpcion", isEnabled());
        verifyThat("#btnBuscar", isDisabled());
        verifyThat("#btnContratar", isEnabled());
        verifyThat("#btnDespedir", isDisabled());
        verifyThat("#btnInforme", isEnabled());
        verifyThat("#btnInsert", isEnabled());
    }

    /**
     * Test que verifica que elementos están habilitados y deshabilitados cuando
     * el usuario que ha entrado ha sido un trabajador.
     *
     */
    @Test
    @Ignore
    public void testB_VerificarTrabajador() {
        navegacionTrabajador();
        verifyThat("#cBoxFiltro", isEnabled());
        verifyThat("#cBoxOpcion", isEnabled());
        verifyThat("#btnBuscar", isDisabled());
        verifyThat("#btnContratar", isInvisible());
        verifyThat("#btnDespedir", isInvisible());
        verifyThat("#btnInforme", isEnabled());
        verifyThat("#btnInsert", isInvisible());
    }

    /**
     * Test que valida que el boton de Contratar te lleve a la ventana de
     * ContratarTrabajador.
     *
     */
    @Test
    @Ignore
    public void testC_BtnContratar() {
        navegacionGranjero();
        verifyThat("#btnContratar", isEnabled());
        clickOn("#btnContratar");
        verifyThat("#paneContratar", isEnabled());
    }

    @Test
    @Ignore
    public void testD_ContratarTrabajadorTabla() {
        navegacionGranjero();
        tablaContratos = lookup("#tablaContratos").queryTableView();
        int numContratos = tablaContratos.getItems().size();
        clickOn("#btnInsert");
        Node nodoTrabajador = lookup("#colTrabajador").nth(2).query();
        doubleClickOn(nodoTrabajador);
        press(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        Node nodoGranja = lookup("#colGranja").nth(2).query();
        doubleClickOn(nodoGranja);
        press(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        Node nodoFecha = lookup("#colFechaCon").nth(2).query();
        doubleClickOn(nodoFecha);
        clickOn(nodoFecha);
        eraseText(10);
        write("01/01/22");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        Node nodoSalario = lookup("#colSalario").nth(2).query();
        doubleClickOn(nodoSalario);
        clickOn(nodoSalario);
        eraseText(1);
        write("1000");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#btnInsert");
        assertNotEquals(numContratos, tablaContratos.getItems().size());

    }

    @Test
    @Ignore
    public void testD_cancelarContratoTabla() {
        navegacionGranjero();
        tablaContratos = lookup("#tablaContratos").queryTableView();
        int numContratos = tablaContratos.getItems().size();
        clickOn("#btnInsert");
        Node nodoTrabajador = lookup("#colTrabajador").nth(2).query();
        clickOn(nodoTrabajador);
        press(KeyCode.ESCAPE);

        assertEquals(numContratos, tablaContratos.getItems().size());
    }

    @Test
    @Ignore
    public void testE_errorContratoTabla() {
        navegacionGranjero();
        tablaContratos = lookup("#tablaContratos").queryTableView();
        int numContratos = tablaContratos.getItems().size();
        clickOn("#btnInsert");
        Node nodoTrabajador = lookup("#colTrabajador").nth(2).query();
        doubleClickOn(nodoTrabajador);
        press(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#btnInsert");
        verifyThat("No se han definido todos los campos a la hora de contratar al trabajador.", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals(numContratos, tablaContratos.getItems().size());
    }

    /**
     * Test que valida que el cambio del salario de un trabajador haya sido
     * exitoso, habiendo seleccionado previamente la columna de Salario y el
     * trabajador deseado.
     *
     */
    @Test
    @Ignore
    public void testF_modificarSalarioTrabajador() {
        navegacionGranjero();
        tablaContratos = lookup("#tablaContratos").queryTableView();
        Node filaSalario = lookup("#colSalario").nth(1).query();
        doubleClickOn(filaSalario);
        Long salario = ((ContratoEntity) tablaContratos.getSelectionModel().getSelectedItem()).getSalario();
        write("3000");
        press(KeyCode.ENTER);
        verifyThat("Salario modificado correctamente", NodeMatchers.isVisible());
        Node fila = lookup(".table-row-cell").nth(0).query();
        clickOn(fila);

        assertNotEquals(salario, ((ContratoEntity) tablaContratos.getSelectionModel().getSelectedItem()).getSalario());
    }

    @Test
    @Ignore
    public void testG_cancelarModificarSalario() {
        navegacionGranjero();
        tablaContratos = lookup("#tablaContratos").queryTableView();
        Node filaSalario = lookup("#colSalario").nth(1).query();
        doubleClickOn(filaSalario);
        Long salario = ((ContratoEntity) tablaContratos.getSelectionModel().getSelectedItem()).getSalario();
        write("3000");
        press(KeyCode.ESCAPE);
        Node fila = lookup(".table-row-cell").nth(0).query();
        clickOn(fila);
        assertEquals(salario, ((ContratoEntity) tablaContratos.getSelectionModel().getSelectedItem()).getSalario());
    }

    /**
     * Test que valida que a la hora de haber seleccionado un contrato de la
     * tabla, te de la posibilidad de Cancelar la eliminacion.
     *
     */
    @Test
    @Ignore
    public void testH_borrarContratoBtnActivadoCancelar() {
        navegacionGranjero();
        Node fila = lookup(".table-row-cell").nth(0).query();
        clickOn(fila);
        verifyThat("#btnDespedir", isEnabled());
        clickOn("#btnDespedir");
        verifyThat("¿Estas seguro de que quiere despedir al trabajador?", NodeMatchers.isVisible());
        clickOn("Cancelar");
    }

    /**
     * Test que valida que no puedas despedir a un trabajador que pertenezca a
     * una zona.
     *
     */
    @Test
    @Ignore
    public void testI_zonasDesasignarException() {
        navegacionGranjero();
        Node fila = lookup(".table-row-cell").nth(1).query();
        clickOn(fila);
        verifyThat("#btnDespedir", isEnabled());
        clickOn("#btnDespedir");
        verifyThat("¿Estas seguro de que quiere despedir al trabajador?", NodeMatchers.isVisible());
        clickOn("Despedir");
        verifyThat("Debido a que el trabajador esta asignado a alguna zona, no se ha podido realizar el despido. Asegurese de que lo ha desasignado de las zonas antes de volver a intentar despedirlo.", NodeMatchers.isVisible());
    }

    /**
     * Test que valida que el borrado de un contrato sea exitoso, habiendo
     * seleccionado previamente una fila de la tabla.
     */
    @Test
    public void testJ_borrarContratoBtnActivadoDespedir() {
        navegacionGranjero();
        tablaContratos = lookup("#tablaContratos").queryTableView();
        int numContratos = tablaContratos.getItems().size();
        Node fila = lookup(".table-row-cell").nth(0).query();
        clickOn(fila);
        verifyThat("#btnDespedir", isEnabled());
        clickOn("#btnDespedir");
        verifyThat("¿Estas seguro de que quiere despedir al trabajador?", NodeMatchers.isVisible());
        clickOn("Despedir");
        assertNotEquals(numContratos, tablaContratos.getItems().size());
    }

    /**
     *
     */
    /*@Test
    public void testP_ContratarTrabajador() {
        navegacionGranjero();
    }*/
    /**
     *
     */
    /*@Test
    @Ignore
    public void testQ_MostrarInforme() {
        verifyThat("#cBoxFiltro", isEnabled());
        clickOn("#cBoxFiltro");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);        
        verifyThat("#cBoxOpcion", isEnabled());
        clickOn("#cBoxOpcion");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("#btnBuscar", isEnabled());
        clickOn("#btnBuscar");
        verifyThat("#btnInforme", isEnabled());
        clickOn("#btnInforme");
        
    }*/
}

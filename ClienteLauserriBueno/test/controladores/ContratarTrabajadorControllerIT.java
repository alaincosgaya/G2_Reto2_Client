/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.controladores;

import aplicacion.App;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 * Tests del controlador de la ventana ContratarTrabajadorControllerIT.
 *
 * @author Alain Cosgaya
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ContratarTrabajadorControllerIT extends ApplicationTest {

    private TableView tablaContratos;
    private ComboBox cBoxTrabajador;

    private ComboBox cBoxGranja;

    private DatePicker datePickerContrato;

    private TextField txtSalario;

    private Alert alert;

    public void navegacion() {
        clickOn("#textUser");
        write("admin");
        clickOn("#textPasswd");
        write("abcd*1234");
        clickOn("#btnLogin");
        sleep(1000);
        clickOn("#trabajadorMenu");
        clickOn("#contratosMenuItem");

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
     * Test qeu verifica que elementos estan habilitados y deshabilitados.
     *
     */
    @Test
    @Ignore
    public void testA_Verificar() {
        navegacion();
        clickOn("#btnContratar");
        verifyThat("#btnAtras", isEnabled());
        verifyThat("#btnContratar", isDisabled());

    }

    /**
     * Test que valida que no haya nada escrito en los campos de la ventana
     * Contratos.
     *
     */
    @Test
    @Ignore
    public void testB_CamposVacios() {
        navegacion();
        clickOn("#btnContratar");
        sleep(1000);
        clickOn("#cBoxTrabajador");
        cBoxTrabajador = lookup("#cBoxTrabajador").query();
        clickOn("#cBoxGranja");
        cBoxGranja = lookup("#cBoxGranja").query();
        clickOn("#datePickerContrato");
        datePickerContrato = lookup("#datePickerContrato").query();
        clickOn("#txtSalario");
        txtSalario = lookup("txtSalario").query();
        eraseText(txtSalario.getText().length());
        verifyThat("#btnContratar", isDisabled());
    }

    /**
     * Test que comprueba que la ComboBox de Trabajador este vacia.
     *
     */
    @Test
    @Ignore
    public void testC_CamposComboBoxTrabajadorVacio() {
        navegacion();
        clickOn("#btnContratar");
        sleep(1000);
        clickOn("#cBoxGranja");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#datePickerContrato");
        clickOn("#txtSalario");
        write("1000");
        verifyThat("#btnContratar", isDisabled());
    }

    /**
     * Test que comprueba que la ComboBox de Granja este vacia.
     *
     */
    @Test
    @Ignore
    public void testD_CampoComboBoxGranjaVacio() {
        navegacion();
        sleep(1000);
        clickOn("#cBoxTrabajador");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#datePickerContrato");
        clickOn("#txtSalario");
        write("500");
        verifyThat("#btnContratar", isDisabled());
    }

    /**
     * Test que comprueba que el TextField de Salario este vacio.
     *
     */
    @Test
    @Ignore
    public void testE_CampoSalarioVacio() {
        navegacion();
        clickOn("#btnContratar");
        clickOn("#cBoxTrabajador");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#cBoxGranja");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#txtSalario");
        txtSalario = lookup("txtSalario").query();
        eraseText(txtSalario.getText().length());
        verifyThat("#btnContratar", isDisabled());
    }

    /**
     * Test que comprueba que el boton de Atras te lleve a la ventana de
     * Contratos.
     *
     */
    @Test
    @Ignore
    public void testF_BtnAtras() {
        navegacion();
        clickOn("#btnContratar");
        verifyThat("#btnAtras", isEnabled());
        clickOn("#btnAtras");
        verifyThat("#paneContratos", isEnabled());
    }

    /**
     *
     */
    //@Test
    public void testG_RestriccionNumerico() {
        String text = "Alain Cosgaya";
        navegacion();
        clickOn("#btnContratar");
        write(text);
        verifyThat(txtSalario.getText(), hasText(text));
    }

    /**
     * Test que valida que un trabajdor se pueda contratar correctamente.
     *
     */
    @Test

    public void testH_ContratarTrabajador() {
        navegacion();
        tablaContratos = lookup("#tablaContratos").queryTableView();
        int numContratos = tablaContratos.getItems().size();
        clickOn("#btnContratar");
        clickOn("#cBoxTrabajador");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        sleep(100);
        clickOn("#cBoxGranja");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        sleep(100);
        clickOn("#txtSalario");
        write("2500");
        clickOn("#btnContratar");
        clickOn("#btnAtras");
        verifyThat("#paneContratos",isEnabled());
        tablaContratos = lookup("#tablaContratos").queryTableView();
        Assert.assertNotEquals(tablaContratos.getItems().size(), numContratos);
    }
}

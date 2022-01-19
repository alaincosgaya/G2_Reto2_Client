/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class MenuController {

    private static final Logger LOGGER = Logger.getLogger(MenuController.class.getName());

    private Stage stage;
    @FXML
    private HBox hBoxMenu;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu inicioMenu;
    @FXML
    private MenuItem granjaMenuItem;
    @FXML
    private MenuItem zonaMenuItem;
    @FXML
    private MenuItem animalMenuItem;
    @FXML
    private Menu trabajadorMenu;
    @FXML
    private MenuItem contratosMenuItem;
    @FXML
    private MenuItem trabajadoresMenuItem;
    @FXML
    private Menu relleno;
    @FXML
    private Menu perfilMenu;
    @FXML
    private MenuItem perfilMenuItem;
    @FXML
    private Menu salirMenu;
    @FXML
    private MenuItem salirMenuItem;
    @FXML
    private MenuItem sesionMenuItem;

    public void setStage(Stage stage1) {
        stage = stage1;
    }

    /**
     * Initializes the controller class.
     */
    /*
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
     */
    
    @FXML
    private void menuGranja(ActionEvent event) {
        /*
        try {
            LOGGER.info("Carga del FXML de Principal Animal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/Granja.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            GranjaController controller = ((GranjaController) loader.getController());

            controller.setStage(stage);
            controller.initStage(root);

            //stage.getScene().getWindow().hide();
        } catch (IOException e) {

        }
        */
    }

    @FXML
    private void menuZona(ActionEvent event) {
        /*
        try {
            LOGGER.info("Carga del FXML de Principal Animal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/Zona.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            ZonaController controller = ((ZonaController) loader.getController());

            controller.setStage(stage);
            controller.initStage(root);

            //stage.getScene().getWindow().hide();
        } catch (IOException e) {

        }
        */
    }

    @FXML
    private void menuAnimal(ActionEvent event) {
        
        try {
            LOGGER.info("Carga del FXML de Principal Animal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/PrincipalAnimal.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            PrincipalAnimalController controller = ((PrincipalAnimalController) loader.getController());
            //closeAll(event);
            controller.setStage(stage);
            controller.initStage(root);

            //stage.getScene().getWindow().hide();
            //stage.getScene().getWindow().onHidingProperty();
            //stage.getScene().getWindow().onShowingProperty();
            
            
            
        } catch (IOException e) {

        }
    }

    @FXML
    private void menuContratos(ActionEvent event) {
        /*
        try {
            LOGGER.info("Carga del FXML de Principal Animal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/Contratos.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            ContratosController controller = ((ContratosController) loader.getController());

            controller.setStage(stage);
            controller.initStage(root);

            //stage.getScene().getWindow().hide();
        } catch (IOException e) {

        }
        */
    }

    @FXML
    private void menuTrabajadores(ActionEvent event) {
        /*
        try {
            LOGGER.info("Carga del FXML de Principal Animal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/Trabajador.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            TrabajadorController controller = ((TrabajadorController) loader.getController());

            controller.setStage(stage);
            controller.initStage(root);

            //stage.getScene().getWindow().hide();
        } catch (IOException e) {

        }
        */
    }

    @FXML
    private void menuPerfil(ActionEvent event) {
        /*
        try {
            LOGGER.info("Carga del FXML de Principal Animal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/Session.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SessionController controller = ((SessionController) loader.getController());

            controller.setStage(stage);
            controller.initStage(root);

            //stage.getScene().getWindow().hide();
        } catch (IOException e) {

        }
        */
    }

    @FXML
    private void menuSalir(ActionEvent event) {
        LOGGER.info("Cierre del programa");
        Platform.exit();
    }

    @FXML
    private void sesionMenu(ActionEvent event) {
        /*
        try {
            LOGGER.info("Carga del FXML de Principal Animal");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/vistas/SignIn.fxml")
            );

            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SignInController controller = ((SignInController) loader.getController());

            controller.setStage(stage);
            controller.initStage(root);

            //stage.getScene().getWindow().hide();
        } catch (IOException e) {

        }
        */
    }
    /*
    private void closeAll(ActionEvent event){
        Platform.setImplicitExit(false);
        ((Node)(event.getSource())).getScene.getWindow().hide();
    }
*/
}

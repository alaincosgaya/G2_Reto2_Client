/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import aplicacion.App;
import clases.UserEntity;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author 2dam
 */
public class SessionController implements Initializable {

    @FXML
    private BorderPane bPane;
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private static UserEntity usr;

    
    public static UserEntity getUser(){
        return usr;
    }
    public void setUser(UserEntity user){
        usr = user;
    }
    
    public void setStage(Stage stage1) {
        stage = stage1;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Session");
        stage.setScene(scene);
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        stage.show();
       
    }

    @FXML
    private void buttonEventCambiar(ActionEvent event) throws IOException {

        try {
            LOGGER.info("Carga del FXML de SignIn");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/cambiarcon.fxml"));
            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            cambiarconController controller = ((cambiarconController) loader.getController());
            Stage anotherStage = new Stage();
            controller.setStage(anotherStage);
            controller.initStage(root, usr);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void buttonEventExit(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}

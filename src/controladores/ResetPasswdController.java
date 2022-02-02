/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import aplicacion.App;
import clases.UserEntity;
import excepciones.EmailPatternException;
import factoria.UserManagerImplementation;
import interfaces.UserInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class ResetPasswdController implements Initializable{

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblEmail;
    @FXML
    private Button btnEnviar;

    public void setStage(Stage stage1) {
        stage = stage1;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("ResetPasswd");
        stage.setScene(scene);
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        stage.show();
    }

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtEmail)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }

    }

    @FXML
    private void buttonEvent(ActionEvent event) throws IOException {
        try {
            
            UserEntity user = new UserEntity();
            user.setEmail(txtEmail.getText());
            UserInterface u = new UserManagerImplementation();
            
            u.resetPasswd(user);
            
            
            LOGGER.info("Carga del FXML de SignUp");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignIn.fxml"));
            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SignInController controller = ((SignInController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
            

        } catch (IOException e) {
            e.printStackTrace();
            //LOGGER
        }
    }

    @FXML
    private void buttonEventBack(ActionEvent event) throws IOException {
        try {
            LOGGER.info("Carga del FXML de SignIn");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignIn.fxml"));
            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SignInController controller = ((SignInController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
            //LOGGER
        }
    }

    private void emailTextValidation() {
        txtEmail.textProperty().addListener((ov, oldV, newV) -> {
            if (!txtEmail.getText().equals("")) {

                try {
                    String email = txtEmail.getText();
                    validarEmailPattern(email);
                    lblEmail.setVisible(false);
                } catch (EmailPatternException e) {
                    lblEmail.setVisible(true);
                }

            }
        });

    }

    private void reportedFields() {
        btnEnviar.disableProperty().bind(
                txtEmail.textProperty().isEmpty().or(
                        lblEmail.visibleProperty()
                )
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emailTextValidation();
        reportedFields();

    }

    public void validarEmailPattern(String email) throws EmailPatternException {

        String regex = "^(.+)@(.+)[.](.+)$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new EmailPatternException(txtEmail.getText());
        }

    }
}

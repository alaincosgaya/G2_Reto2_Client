/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import aplicacion.App;
import cifrado.CifradoClient;
import clases.UserEntity;
import excepciones.*;
import factoria.UserManagerImplementation;
import interfaces.UserInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import restful.UserClient;
//import logic.UiLogicImplementation;

/**
 *
 * @author idoia
 */
public class SignInController implements Initializable {

    private boolean exception = false;
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    @FXML
    private TextField textUser;
    
    @FXML
    private Pane paneVentana;
    
    @FXML
    private PasswordField textPasswd;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblCaract;

    @FXML
    private Label lblNum;

    @FXML
    private ImageView fondo;

    private final int max = 50;

    public void setStage(Stage stage1) {
        stage = stage1;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("SignIn");
        stage.setScene(scene);
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        stage.show();
    }

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(textUser)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (evt.equals(textPasswd)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (textUser.getText().length() >= max) {
            event.consume();
        }
        if (textPasswd.getText().length() >= max) {
            event.consume();
        }
    }

    @FXML
    private void buttonEventSignIn(ActionEvent event) throws IOException, PasswordLengthException, PasswordNumException, ConnectException, UpdateException {

        try {
            /*User user = new User();
            user.setUsername(textUser.getText());
            user.setPassword(textPasswd.getText());
            Signable imple = new UiLogicImplementation();
            imple.signIn(user);*/
            //UserEntity user = new UserEntity();
            /*
            UserClient user = new UserClient();
            String con = CifradoClient.encrypt(textPasswd.getText());
            System.out.println(con);
            user.validatePassword(new GenericType<UserEntity>(){}, textUser.getText(),con );
            */
            UserEntity user = new UserEntity();
            user.setUsername(textUser.getText());
            //String con = CifradoClient.encrypt(textPasswd.getText());
            user.setPassword(textPasswd.getText());
            UserInterface u = new UserManagerImplementation();
            u.findClientValidatePasswd(user);
            
            LOGGER.info("Carga del FXML de Session");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Session.fxml"));
            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SessionController controller = ((SessionController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root, user);
            event.consume();
            //paneVentana.getScene().getWindow().hide();
            //((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException e1) {
            e1.printStackTrace();
            //LOGGER
        }
        /*catch (SignInException e2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e2.getMessage());
            alert.show();
        }*/
    }

    @FXML
    private void buttonEvent(ActionEvent event) throws IOException {
        try {
            LOGGER.info("Carga del FXML de SignUp");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignUp.fxml"));
            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SignUpController controller = ((SignUpController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
            //LOGGER
        }
    }

    @FXML
    private void buttonEventReset(ActionEvent event) throws IOException {
        try {
            LOGGER.info("Carga del FXML de ResetPasswd");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/ResetPasswd.fxml"));
            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            ResetPasswdController controller = ((ResetPasswdController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
            

        } catch (IOException e) {
            e.printStackTrace();
            //LOGGER
        }
    }

    private void passwdTextCaractValidation() {
        textPasswd.textProperty().addListener((ov, oldV, newV) -> {
            if (!textPasswd.getText().equals("")) {
                try {
                    String passwd = textPasswd.getText();
                    validarMinCaractPasswdPattern(passwd);
                    lblCaract.setVisible(false);
                } catch (PasswordLengthException e) {
                    lblCaract.setVisible(true);
                }
            } else {
                lblCaract.setVisible(false);
            }
        });
    }

    private void passwdTextNumValidation() {
        textPasswd.textProperty().addListener((ov, oldV, newV) -> {
            if (!textPasswd.getText().equals("")) {
                try {
                    String passwd = textPasswd.getText();
                    validarNumPasswdPattern(passwd);
                    lblNum.setVisible(false);
                } catch (PasswordNumException e) {
                    lblNum.setVisible(true);
                }
            } else {
                lblNum.setVisible(false);
            }
        });
    }

    private void reportedFields() {
        btnLogin.disableProperty().bind(
                textUser.textProperty().isEmpty().or(
                        textPasswd.textProperty().isEmpty()).or(
                        lblCaract.visibleProperty().or(
                                lblNum.visibleProperty()
                        )
                )
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        passwdTextCaractValidation();
        passwdTextNumValidation();
        reportedFields();
    }

    public void validarMinCaractPasswdPattern(String passwd) throws PasswordLengthException {
        String regex = "^(.+){8,50}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(passwd);

        if (!matcher.matches()) {
            throw new PasswordLengthException(lblCaract.getText());
        }

    }

    public void validarNumPasswdPattern(String passwd) throws PasswordNumException {

        String regex = ".*\\d.*";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(passwd);
        if (!matcher.matches()) {
            throw new PasswordNumException(lblNum.getText());
        }
    }

}

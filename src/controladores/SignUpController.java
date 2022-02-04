/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import aplicacion.App;
import cifrado.CifradoClient;
import clases.UserEntity;
import clases.UserPrivilegeType;
import clases.UserStatusType;
import excepciones.*;
import factoria.UserManagerImplementation;
import interfaces.UserInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author idoia
 */
public class SignUpController implements Initializable {

    @FXML
    private Label lblCaract;

    @FXML
    private PasswordField txtPasswd;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblNum;

    @FXML
    private PasswordField txtPassw2;

    @FXML
    private Label lblPasswd2;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtName;

    @FXML
    private Button btnSignUp;

    @FXML
    private ChoiceBox cBoxTipo;

    private UserPrivilegeType privilege;

    private final int max = 50;
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public void setStage(Stage stage1) {
        stage = stage1;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("SignUp");
        stage.setScene(scene);

        cBoxTipo.setItems(FXCollections.observableArrayList("Granjero", "Trabajador"));
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        cBoxTipo.selectionModelProperty().addListener(this::handlePrivilegios);
        stage.show();
    }

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtUser) || evt.equals(txtPasswd) || evt.equals(txtPassw2) || evt.equals(txtEmail)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (txtUser.getText().length() >= max) {
            event.consume();
        }
        if (txtPasswd.getText().length() >= max) {
            event.consume();
        }
        if (txtPassw2.getText().length() >= max) {
            event.consume();
        }
        if (txtEmail.getText().length() >= max) {
            event.consume();
        }
        if (txtName.getText().length() >= max) {
            event.consume();
        }

    }

    @FXML
    private void buttonEvent(ActionEvent event) throws IOException {

        try {
            UserEntity user = new UserEntity();
            user.setUsername(txtUser.getText());
            user.setFullName(txtName.getText());
            user.setEmail(txtEmail.getText());
            
            user.setUserPrivilege(privilege);
            user.setUserStatus(UserStatusType.ENABLED);

            String con = CifradoClient.encrypt(txtPasswd.getText());
            user.setPassword(con);

            UserInterface u = new UserManagerImplementation();
            UserEntity find = null;
            find = u.getUsuarioPorLogin(user.getUsername(), user.getPassword());

            if (find != null) {
                u.crearUsuario(user);
            } else {
                throw new SignInException("Usuario ya existe");

            }

            LOGGER.info("Carga del FXML de Session");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Session.fxml"));
            Parent root = (Parent) loader.load();
            LOGGER.info("Llamada al controlador del FXML");
            SessionController controller = ((SessionController) loader.getController());
            controller.setStage(stage);
            controller.setUser(user);
            controller.initStage(root);

            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SignInException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, "El nombre de usuario introducido no esta disponible");
            alert.showAndWait();

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

    private void passwdTextCaractValidation() {

        txtPasswd.textProperty().addListener((ov, oldV, newV) -> {
            if (!txtPasswd.getText().equals("")) {

                try {
                    String passwd = txtPasswd.getText();
                    validarMinCaractPasswdPattern(passwd);
                    lblCaract.setVisible(false);
                } catch (PasswordLengthException e) {
                    lblCaract.setVisible(true);
                }

            }
        });
    }

    private void passwdTextNumValidation() {

        txtPasswd.textProperty().addListener((ov, oldV, newV) -> {
            if (!txtPasswd.getText().equals("")) {

                try {
                    String passwd = txtPasswd.getText();
                    validarNumPasswdPattern(passwd);
                    lblNum.setVisible(false);
                } catch (PasswordNumException e) {
                    lblNum.setVisible(true);
                }

            }
        });

    }

    private void repeatpasswd() {

        txtPassw2.textProperty().addListener((ov, oldV, newV) -> {
            if (!txtPassw2.getText().equals("")) {

                try {
                    String passwd = txtPassw2.getText();
                    validarEqualPasswd();
                    lblPasswd2.setVisible(false);
                } catch (SamePasswordException e) {
                    lblPasswd2.setVisible(true);
                }

            }
        });
    }

    private void reportedFields() {
        btnSignUp.disableProperty().bind(
                txtPasswd.textProperty().isEmpty().or(
                        txtEmail.textProperty().isEmpty().or(
                                txtPassw2.textProperty().isEmpty().or(
                                        txtUser.textProperty().isEmpty().or(
                                                txtName.textProperty().isEmpty().or(
                                                        lblCaract.visibleProperty().or(
                                                                lblEmail.visibleProperty().or(
                                                                        cBoxTipo.valueProperty().isNull().or(
                                                                                lblNum.visibleProperty().or(
                                                                                        lblPasswd2.visibleProperty()
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emailTextValidation();
        passwdTextCaractValidation();
        passwdTextNumValidation();
        repeatpasswd();
        reportedFields();

    }

    public void validarEmailPattern(String email) throws EmailPatternException {

        String regex = "^(.+)@(.+)[.](.+)$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new EmailPatternException(lblEmail.getText());
        }

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

    public void validarEqualPasswd() throws SamePasswordException {
        if (!txtPasswd.getText().equals(txtPassw2.getText())) {
            throw new SamePasswordException(lblPasswd2.getText());
        }
    }

    public void handlePrivilegios(ObservableValue ov, Object oldValue, Object newValue) {
        if (newValue != null) {
            switch (newValue.toString()) {
                case ("Granjero"):
                    privilege = UserPrivilegeType.GRANJERO;
                    break;
                case ("Trabajador"):
                    privilege = UserPrivilegeType.TRABAJADOR;
                    break;
            }
        }
    }
}

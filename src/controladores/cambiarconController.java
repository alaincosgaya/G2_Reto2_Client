/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import aplicacion.App;
import cifrado.CifradoClient;
import clases.UserEntity;
import excepciones.EmailPatternException;
import excepciones.PasswordLengthException;
import excepciones.PasswordNumException;
import excepciones.SamePasswordException;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class cambiarconController implements Initializable{

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private UserEntity user;

    @FXML
    private PasswordField PasswdNueva;

    @FXML
    private PasswordField PasswdActual;

    @FXML
    private PasswordField PasswdRepetir;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnAtras;
    
    @FXML
    private Label lblCaract;
    
    @FXML
    private Label lblNum;
    
    @FXML
    private Label lblPasswd2;
    
    

    public void setStage(Stage stage1) {
        stage = stage1;
    }

    public void initStage(Parent root, UserEntity user) {
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("CambiarCon");
        stage.setScene(scene);
        LOGGER.info("Llamada a los metodos y restricciones del controlador");
        stage.show();
        this.user = user;
    }

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(PasswdNueva)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (evt.equals(PasswdActual)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
        if (evt.equals(PasswdRepetir)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }

    }

    @FXML
    private void buttonEventEnviar(ActionEvent event) throws IOException {
        //UserEntity user1 = new UserEntity();
        user.setUsername(user.getUsername());
        //String con = CifradoClient.encrypt(PasswdActual.getText());
        user.setPassword(PasswdActual.getText());
        UserInterface u = new UserManagerImplementation();
        String passwd = PasswdNueva.getText();
        
        UserEntity find = u.findClientValidatePasswd(user);
        System.out.println(find);
            if(!find.equals(null)){
                u.editPasswd(user, passwd);
            }else{
                System.out.println(PasswdActual.getText());
        System.out.println(user.getPassword());
            System.out.println("Mal : ");
    }
        
    }

    @FXML
    private void buttonEventBack(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private void passwdTextCaractValidation() {
        PasswdNueva.textProperty().addListener((ov, oldV, newV) -> {
            if (!PasswdNueva.getText().equals("")) {
                try {
                    String passwd = PasswdNueva.getText();
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
        PasswdNueva.textProperty().addListener((ov, oldV, newV) -> {
            if (!PasswdNueva.getText().equals("")) {
                try {
                    String passwd = PasswdNueva.getText();
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
        btnEnviar.disableProperty().bind(
                PasswdNueva.textProperty().isEmpty().or(
                        PasswdActual.textProperty().isEmpty()).or(
                        PasswdRepetir.textProperty().isEmpty().or(
                        lblCaract.visibleProperty().or(
                                lblNum.visibleProperty().or(lblPasswd2.visibleProperty())
                        )
                )
        )
    

    );
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        passwdTextCaractValidation();
        passwdTextNumValidation();
        repeatpasswd();
        reportedFields();
    }

    public void validarMinCaractPasswdPattern(String passwd) throws PasswordLengthException {
        String regex = "^(.+){8,50}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(passwd);

        if (!matcher.matches()) {
            //throw new PasswordLengthException(lblCaract.getText());
        }

    }

    public void validarNumPasswdPattern(String passwd) throws PasswordNumException {

        String regex = ".*\\d.*";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(passwd);
        if (!matcher.matches()) {
            //throw new PasswordNumException(lblNum.getText());
        }
    }
    
    private void repeatpasswd() {

        PasswdRepetir.textProperty().addListener((ov, oldV, newV) -> {
            if (!PasswdRepetir.getText().equals("")) {

                try {
                    String passwd = PasswdRepetir.getText();
                    validarEqualPasswd();
                    lblPasswd2.setVisible(false);
                } catch (SamePasswordException e) {
                    lblPasswd2.setVisible(true);
                }

            }
        });
    }
    
    public void validarEqualPasswd() throws SamePasswordException {
        if (!PasswdNueva.getText().equals(PasswdRepetir.getText())) {
            throw new SamePasswordException(lblPasswd2.getText());
        }
    }
}

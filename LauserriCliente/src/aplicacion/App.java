package aplicacion;

import controladores.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * Is the main class
 *
 * @author
 */
public class App extends Application {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) throws Exception {
        LOGGER.info("Carga del FXML de SignIn");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignIn.fxml"));
        Parent root = (Parent) loader.load();
        LOGGER.info("Llamada al controlador del FXML");
        SignInController controller = ((SignInController) loader.getController());
        controller.setStage(stage);
        controller.initStage(root);

    }
    public static void main(String[] args) {
        launch(args);
    }

     }

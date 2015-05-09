package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootController implements Initializable {

    @FXML
    private MenuItem menu_Exit;
    @FXML
    private MenuItem menu_Path;
    @FXML
    private MenuItem menu_Filter;
    @FXML
    private MenuItem menu_About;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        menu_Exit.setOnAction((e) -> exit());
        menu_Path.setOnAction((e) -> setBasePath());
        menu_Filter.setOnAction((e) -> setFilter());
        menu_About.setOnAction((e) -> about());
    }

    private void exit() {
        System.out.println("Close mail client...");
        System.exit(0);
    }

    private void setBasePath() {
        System.out.println("Set base path...");
    }

    private void setFilter() {
        System.out.println("Set filter...");
    }

    private void about() {
        try {
            System.out.println("About...");
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setTitle("About");
            stage.setResizable(false);
//            ImageView view = new ImageView(new Image("images/icon.png"));
            Parent parent = FXMLLoader.load(getClass().getResource("../view/about.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
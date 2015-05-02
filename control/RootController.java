package control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
    public void initialize(URL location, ResourceBundle resources) {
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
        System.out.println("About...");
    }

}
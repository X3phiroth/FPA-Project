package control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author X3phiroth
 */
public class TreeController implements Initializable{

    @FXML
    private TreeView<String> treeView;
    
    private File basePath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        basePath = new File("");
        ImageView view = new ImageView(new Image("images/folder.png"));
        view.setFitHeight(25.0);
        view.setFitWidth(25.0);
        TreeItem<String> root = new TreeItem<>("Folder", view);
        
        for(int i = 1; i < 6; ++i) {
            view = new ImageView(new Image("images/folder.png"));
            view.setFitHeight(25.0);
            view.setFitWidth(25.0);
            root.getChildren().add(new TreeItem<>("Subfolder " + i, view));
        }
        root.setExpanded(true);
        treeView.setRoot(root);
    }
}
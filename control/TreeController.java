package control;

import directory.DirectoryItem;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

/**
 *
 * @author X3phiroth
 */
public class TreeController implements Initializable{

    @FXML
    private TreeView<DirectoryItem> treeView;
    
    private File basePath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        basePath = new File("");
        DirectoryItem root = new DirectoryItem(new File("src/messages/examples"));
//        }
        treeView.setRoot(root);
        root.setExpanded(true);
    }
}
package directory;

import java.io.File;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author X3phiroth
 */
public class DirectoryItem extends AbstractItem {

    public DirectoryItem(File file) {
        super(file, new ImageView(new Image("images/folder2.png")));
        getChildren();
    }

    @Override
    public ObservableList getChildren() {
        ObservableList list = super.getChildren();
        list.clear();
        for (File temp : file.listFiles()) {
            if (temp.isDirectory()) {
                list.add(new DirectoryItem(temp));
            }
        }
        return list;
    }
}

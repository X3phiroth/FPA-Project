package directory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author X3phiroth
 */
public class DirectoryItem extends AbstractItem{

    public DirectoryItem(File file) {
        super(file);
    }
    
    @Override
    public ObservableList getChildren() {
        List<File> list = new Arra<File>();
        for (File temp : file.listFiles()) {
            if (temp.isDirectory()) {
                list.add(temp)
            }
        }
    }
}

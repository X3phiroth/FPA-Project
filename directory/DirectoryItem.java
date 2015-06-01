package directory;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        ObservableList<File> list = FXCollections.observableList(null);
        for (File temp : file.listFiles()) {
            if (temp.isDirectory()) {
                list.add(temp);
            }
        }
        return list;
    }
}

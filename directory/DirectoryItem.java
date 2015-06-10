package directory;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author X3phiroth
 */
public class DirectoryItem extends AbstractItem {

    public DirectoryItem(File file) {
        super(file, new ImageView(new Image("images/folder2.png")));
        init();
    }

    public final void init() {
        for (File fileItem : file.listFiles()) {
            if (fileItem.isDirectory()) {
                DirectoryItem item = new DirectoryItem(fileItem);
                getChildren().add(item);
            }
        }
    }
}

package util;

import directory.DirectoryItem;
import java.util.Observable;
import javafx.scene.control.TreeView;

/**
 *
 * @author X3phiroth
 */
public class FolderSelectionObservable extends Observable {
    private static TreeView<DirectoryItem> treeView;
    private static FolderSelectionObservable instance;
    
    private FolderSelectionObservable(TreeView<DirectoryItem> treeView) {
        FolderSelectionObservable.treeView = treeView;
        treeView.getSelectionModel().selectedItemProperty().addListener((e) -> workWithSelected());
        
    }
    
    public static FolderSelectionObservable getInstance(TreeView<DirectoryItem> treeView) {
        if (instance == null) {
            instance = new FolderSelectionObservable(treeView);
        }
        return instance;
    }

    private void workWithSelected() {
        setChanged();
        notifyObservers(treeView);
    }
}

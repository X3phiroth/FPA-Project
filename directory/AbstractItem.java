/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package directory;

import java.io.File;
import javafx.scene.control.TreeItem;

/**
 *
 * @author s812529
 */
public class AbstractItem extends TreeItem {
    
    protected File file;
    
    public AbstractItem(File file) {
        super(file.getName());
        this.file = file;
    }
}

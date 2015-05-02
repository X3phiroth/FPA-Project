package control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableController implements Initializable{

    @FXML
    private Label labelContent;

    @FXML
    private TableColumn<?, ?> table_Rec;

    @FXML
    private Button allenAntworten;

    @FXML
    private TableColumn<?, ?> table_Read;

    @FXML
    private Button forward;

    @FXML
    private TableColumn<?, ?> table_Sender;

    @FXML
    private TableColumn<?, ?> table_Subject;

    @FXML
    private Label labelTo;

    @FXML
    private TableColumn<?, ?> table_Prio;

    @FXML
    private Button reply;

    @FXML
    private Label labelDate;

    @FXML
    private TableView<?> table;

    @FXML
    private Label labelFrom;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}

package control;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import messages.*;

public class TableController implements Initializable{

    private ObservableList<Message> table_Content;
    
    @FXML
    private Label labelContent;

    @FXML
    private TableColumn<Message, LocalDateTime> table_Rec;

    @FXML
    private Button allenAntworten;

    @FXML
    private TableColumn<Message, Boolean> table_Read;

    @FXML
    private Button forward;

    @FXML
    private TableColumn<Message, MessageStakeholder> table_Sender;

    @FXML
    private TableColumn<Message, String> table_Subject;

    @FXML
    private Label labelTo;

    @FXML
    private TableColumn<Message, MessageImportance> table_Prio;

    @FXML
    private Button reply;

    @FXML
    private Label labelDate;

    @FXML
    private TableView<Message> table;

    @FXML
    private Label labelFrom;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table_Content = FXCollections.observableArrayList();
        initTable();
    }
    
    private void initTable() {

        table_Read.setCellValueFactory(new PropertyValueFactory<>("readStatus"));
        table_Read.setCellFactory(column -> new TableCell<Message, Boolean>() {
            @Override
            protected void updateItem(Boolean readStatus, boolean empty) {
                super.updateItem(readStatus, empty);
                if (readStatus == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    ImageView imageView = new ImageView();
                    Image priorityImage;
                    imageView.setFitWidth(15.0);
                    imageView.setFitHeight(15.0);
                    if (readStatus) {
                        priorityImage = new Image("images/erfolgreich_icon.jpg.png");
                    } else {
                        priorityImage = new Image("images/x_button.jpg.png");
                    }
                    imageView.setImage(priorityImage);
                    setGraphic(imageView);
                }
            }
        });
        table_Sender.setCellValueFactory(new PropertyValueFactory<>("sender"));
        table_Sender.setCellFactory(cellData -> new TableCell<Message, MessageStakeholder>() {
            @Override
            protected void updateItem(MessageStakeholder sender, boolean empty) {
                super.updateItem(sender, empty);

                if (sender == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(sender.getMailAddress());
                }
            }
        });
        table_Subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        table_Rec.setCellValueFactory(new PropertyValueFactory<>("receivedAt"));
        table_Rec.setCellFactory(cellData -> new TableCell<Message, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime receivedDate, boolean empty) {
                super.updateItem(receivedDate, empty);
                if (receivedDate == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    DateTimeFormatter f1 = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm");
                    setText(f1.format(receivedDate));
                }
            }
        });
        table_Prio.setCellValueFactory(new PropertyValueFactory<>("importanceOfMessage"));
        table_Prio.setCellFactory(column -> new TableCell<Message, MessageImportance>() {
            @Override
            protected void updateItem(MessageImportance importanceOfMessage, boolean empty) {
                super.updateItem(importanceOfMessage, empty);
                if (importanceOfMessage == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    ImageView imageView = new ImageView();
                    Image priorityImage = null;
                    imageView.setFitWidth(15.0);
                    imageView.setFitHeight(15.0);
                    if (MessageImportance.LOW.equals(importanceOfMessage)) {
                        priorityImage = new Image("images/pfeil_gelb.jpg");
                    }
                    if (MessageImportance.NORMAL.equals(importanceOfMessage)) {
                        priorityImage = new Image("images/pfeil_gruen_rechts.jpg");
                    }
                    if (MessageImportance.HIGH.equals(importanceOfMessage)) {
                        priorityImage = new Image("images/pfeilrot.jpg.png");
                    }
                    imageView.setImage(priorityImage);
                    setGraphic(imageView);
                }
            }
        });
        
        fillList();
    }
    
    private void fillList() {
        Message m1 = new Message(MessageImportance.LOW, LocalDateTime.now(), true, new MessageStakeholder("Atha", "atha@gmail.com"), "Bestellung");
        table_Content.add(m1);
        table.setItems(table_Content);

        Message m2 = new Message(MessageImportance.HIGH, LocalDateTime.now(), false, new MessageStakeholder("johann", "Johann@gmail.com"), "Steam");
        table_Content.add(m2);
        table.setItems(table_Content);
    }
}

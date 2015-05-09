package control;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import messages.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TableController implements Initializable {

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

    @FXML
    private TextArea area;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table_Content = FXCollections.observableArrayList();
        table.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> refresh(newValue));
        area.setWrapText(true);
        area.setEditable(false);
        initTable();
        fillList();
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
        table_Sender.setCellFactory(column -> new TableCell<Message, MessageStakeholder>() {
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
        table_Rec.setCellFactory(column -> new TableCell<Message, LocalDateTime>() {
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
    }

    private void fillList() {
        File file = new File("src/messages/examples");
        for (File each : file.listFiles()) {
            table_Content.add(readMessage(each));
        }
        table.setItems(table_Content);
    }

    private Message readMessage(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            Element element = (Element) doc.getFirstChild();

            String id = element.getElementsByTagName("id").item(0).getTextContent();
            MessageImportance importance = MessageImportance.valueOf(element.getElementsByTagName("importanceOfMessage").item(0).getTextContent());
            boolean read = Boolean.parseBoolean(element.getElementsByTagName("readStatus").item(0).getTextContent());
            LocalDateTime date = LocalDateTime.parse(element.getElementsByTagName("receivedAt").item(0).getTextContent());
            ArrayList<MessageStakeholder> recipients = new ArrayList<>();
            NodeList list = element.getElementsByTagName("recipients");
            for (int i = 0; i < list.getLength(); ++i) {
                Node recipient = list.item(i);
                Element temp = (Element) recipient;
                recipients.add(new MessageStakeholder(
                        temp.getElementsByTagName("name").item(0).getTextContent(),
                        temp.getElementsByTagName("mailAddress").item(0).getTextContent()));
            }
            list = element.getElementsByTagName("sender");
            Element eSender = (Element) list.item(0);
            MessageStakeholder sender = new MessageStakeholder(
                    eSender.getElementsByTagName("name").item(0).getTextContent(),
                    eSender.getElementsByTagName("mailAddress").item(0).getTextContent());
            String subject = element.getElementsByTagName("subject").item(0).getTextContent();
            String text = element.getElementsByTagName("text").item(0).getTextContent();

            Message message = new Message(importance, date, read, sender, subject);
            message.setRecipients(recipients);
            message.setText(text);
            message.setId(id);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void refresh(Message message) {
        labelContent.setText(message.getSubject());
        labelDate.setText(message.getReceivedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        labelFrom.setText(message.getSender().getName() + " (" + message.getSender().getMailAddress() + ")");
        StringBuilder to = new StringBuilder(message.getRecipients().get(0).getMailAddress());
        for (int i = 0; i < message.getRecipients().size(); ++i) {
            to.append(", ").append(message.getRecipients().get(i).getMailAddress());
        }
        labelTo.setText(to.toString());
        area.setText(message.getText());
        message.setReadStatus(true);
        saveRead(message);
    }

    private void saveRead(Message message) {
        try {
            Path path = Paths.get("src/messages/examples/" + message.getId() + ".xml");
            Charset charset = StandardCharsets.UTF_8;
            
            String content = new String(Files.readAllBytes(path), charset);
            content = content.replaceAll("<readStatus>false</readStatus>", "<readStatus>true</readStatus>");
            Files.write(path, content.getBytes(charset));
        } catch (IOException ex) {
            Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
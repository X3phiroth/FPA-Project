package control;

import directory.ContainsSenderMailFilter;
import directory.DirectoryItem;
import directory.FpaMessageLoader;
import directory.IsSubjectFilter;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import messages.*;
import util.FolderSelectionObservable;

/**
 *
 * @author X3phiroth
 */
public class TableController implements Initializable, Observer {

    private ObservableList<Message> table_Content;
    @FXML
    private TableView<Message> table;
    @FXML
    private TableColumn<Message, MessageImportance> table_Prio;
    @FXML
    private TableColumn<Message, LocalDateTime> table_Rec;
    @FXML
    private TableColumn<Message, MessageStakeholder> table_Sender;
    @FXML
    private TableColumn<Message, Boolean> table_Read;
    @FXML
    private TableColumn<Message, String> table_Subject;

    @FXML
    private Button reply;
    @FXML
    private Button replyAll;
    @FXML
    private Button forward;

    @FXML
    private Label labelTo;
    @FXML
    private Label labelFrom;
    @FXML
    private Label labelDate;
    @FXML
    private Label labelContent;
    @FXML
    private TextArea area;

    private String selectedFolder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table_Content = FXCollections.observableArrayList();
        // Changes the read status
        table.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setReadStatus(true);
                load(newValue);
            }
        });
        initTable();
        table.setItems(table_Content);
        //Hard set default path
        selectedFolder = "src/messages/examples";
        fillTable(selectedFolder);
        setContextMenu();
        initButtons();
        modifyTextArea();
        FolderSelectionObservable.getInstance(null).addObserver(this);
    }

    @Override
    public void update(Observable o, Object object) {
        TreeView<DirectoryItem> treeView = (TreeView<DirectoryItem>) object;
        DirectoryItem item = (DirectoryItem) treeView.getSelectionModel().getSelectedItem();

        clearDetails();
        selectedFolder = item.getFile().getPath();
        fillTable(selectedFolder);
    }

    private void clearDetails() {
        labelTo.setText("");
        labelFrom.setText("");
        labelDate.setText("");
        labelContent.setText("");
        area.clear();
    }

    /**
     * Initializes the TableView
     */
    private void initTable() {
        table_Prio.setStyle("-fx-alignment: center;");
        table_Prio.setCellValueFactory(new PropertyValueFactory<>("importanceOfMessage"));
        table_Prio.setCellFactory(column -> new TableCell<Message, MessageImportance>() {
            @Override
            protected void updateItem(MessageImportance importanceOfMessage, boolean empty) {
                super.updateItem(importanceOfMessage, empty);
                ImageView view = new ImageView();
                if (importanceOfMessage == null || empty) {
                    setText(null);
                } else {
                    view.setFitWidth(18.0);
                    view.setFitHeight(18.0);
                    if (MessageImportance.LOW.equals(importanceOfMessage)) {
                        view.setImage(new Image("images/arrow_yellow.png"));
                    }
                    if (MessageImportance.NORMAL.equals(importanceOfMessage)) {
                        view.setImage(new Image("images/arrow_green.png"));
                    }
                    if (MessageImportance.HIGH.equals(importanceOfMessage)) {
                        view.setImage(new Image("images/arrow_red.png"));
                    }
                }
                setGraphic(view);
            }
        });

        table_Rec.setCellValueFactory(new PropertyValueFactory<>("receivedAt"));
        table_Rec.setCellFactory(column -> new TableCell<Message, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime receivedDate, boolean empty) {
                super.updateItem(receivedDate, empty);
                if (receivedDate == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    DateTimeFormatter f1 = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    setText(f1.format(receivedDate));
                }
            }
        });

        table_Read.setStyle("-fx-alignment: center;");
        table_Read.setCellValueFactory(new PropertyValueFactory<>("readStatus"));
        table_Read.setCellFactory(column -> new TableCell<Message, Boolean>() {
            @Override
            protected void updateItem(Boolean readStatus, boolean empty) {
                super.updateItem(readStatus, empty);
                ImageView view = new ImageView();
                if (readStatus == null || empty) {
                    setText(null);
                } else {
                    view.setFitWidth(18.0);
                    view.setFitHeight(18.0);
                    if (readStatus) {
                        view.setImage(new Image("images/tick_green.png"));
                    } else {
                        view.setImage(new Image("images/cross_red.png"));
                    }
                }
                setGraphic(view);
            }
        });

        table_Sender.setCellValueFactory(new PropertyValueFactory<>("sender"));
        table_Sender.setCellFactory(column -> new TableCell<Message, MessageStakeholder>() {
            @Override
            protected void updateItem(MessageStakeholder sender, boolean empty) {
                if (sender == null || empty) {
                    setText(null);
                } else {
                    setText(sender.getMailAddress());
                }
            }
        });

        table_Subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
    }

    /**
     * Fills the table by the xml files within the passed path.
     *
     * @param path the path containing the xml files.
     */
    private void fillTable(String path) {
        table_Content.clear();
//        table_Content.addAll(new IsSubjectFilter(new FpaMessageLoader(), "XING-Profil").filterMessages(path));
//        table_Content.addAll(new ContainsSenderMailFilter(new FpaMessageLoader(), "netflix").filterMessages(path));
        File file = new File(path);
        for (File each : file.listFiles()) {
            if (each != null && each.isFile() && each.getName().endsWith(".xml")) {
                table_Content.add(readMessageFile(each));
            }
        }
    }

    /**
     * Creates the ContextMenu for the table.
     */
    private void setContextMenu() {
        MenuItem item = new MenuItem("mark as unread");
        item.setOnAction((e) -> {
            Message temp = table.getSelectionModel().getSelectedItem();
            temp.setReadStatus(false);
            saveMessage(temp);
        });
        ContextMenu menu = new ContextMenu(item);
        item = new MenuItem("Filter messages");
        item.setOnAction((e) -> {
            ArrayList<Message> messages = new ContainsSenderMailFilter(new FpaMessageLoader(), "info").filterMessages(selectedFolder);
            messages = new IsSubjectFilter(new FpaMessageLoader(), "Ihre Bestellung").filterMessages(messages);
            table_Content.clear();
            table_Content.addAll(messages);
        });
        menu.getItems().add(item);
        table.setContextMenu(menu);
    }

    /**
     * Sets some options to the TextArea.
     */
    private void modifyTextArea() {
        area.setWrapText(true);
        area.setEditable(false);
    }

    /**
     * Loads the selected message and displays all the information in the lower
     * section. also updates the "read" status.
     *
     * @param message The selected message
     */
    private void load(Message message) {
        labelContent.setText(message.getSubject());
        labelDate.setText(message.getReceivedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        labelFrom.setText(message.getSender().getName() + " (" + message.getSender().getMailAddress() + ")");
        StringBuilder to = new StringBuilder(message.getRecipients().get(0).getMailAddress());
        for (int i = 1; i < message.getRecipients().size(); ++i) {
            to.append(", ").append(message.getRecipients().get(i).getMailAddress());
        }
        labelTo.setText(to.toString());
        area.setText(message.getText());
        saveMessage(message);
    }

    /**
     * Opens the xml file, reads all the information and returns a new message
     * object.
     *
     * @param file The passed xml file
     * @return The resulting Message object
     */
    private Message readMessageFile(File file) {
        if (file.isFile()) {
            try {
                JAXBContext jc = JAXBContext.newInstance(Message.class);
                Unmarshaller um = jc.createUnmarshaller();
                return (Message) um.unmarshal(file);
            } catch (JAXBException ex) {
                Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Saves the "read" status in the message file.
     *
     * @param message The edited message
     */
    private void saveMessage(Message message) {
        try {
//            ID of message = name of the xml file
            File file = new File(selectedFolder + "/" + message.getId() + ".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            For a better format in file
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(message, file);
//            Just for output in console
//            jaxbMarshaller.marshal(message, System.out); 
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

// Old Version of my reading message method
//    /**
//     * Opens the xml file, reads all the information and returns a new message
//     * object.
//     *
//     * @param file The file to read
//     * @return The created message
//     */
//    private Message readMessage(File file) {
//        try {
//            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
//
//            Element element = (Element) doc.getFirstChild();
//
//            String id = element.getElementsByTagName("id").item(0).getTextContent();
//            MessageImportance importance = MessageImportance.valueOf(element.getElementsByTagName("importanceOfMessage").item(0).getTextContent());
//            boolean read = Boolean.parseBoolean(element.getElementsByTagName("readStatus").item(0).getTextContent());
//            LocalDateTime date = LocalDateTime.parse(element.getElementsByTagName("receivedAt").item(0).getTextContent());
//            ArrayList<MessageStakeholder> recipients = new ArrayList<>();
//            NodeList list = element.getElementsByTagName("recipients");
//            for (int i = 0; i < list.getLength(); ++i) {
//                Node recipient = list.item(i);
//                Element temp = (Element) recipient;
//                recipients.add(new MessageStakeholder(
//                        temp.getElementsByTagName("name").item(0).getTextContent(),
//                        temp.getElementsByTagName("mailAddress").item(0).getTextContent()));
//            }
//            list = element.getElementsByTagName("sender");
//            Element eSender = (Element) list.item(0);
//            MessageStakeholder sender = new MessageStakeholder(
//                    eSender.getElementsByTagName("name").item(0).getTextContent(),
//                    eSender.getElementsByTagName("mailAddress").item(0).getTextContent());
//            String subject = element.getElementsByTagName("subject").item(0).getTextContent();
//            String text = element.getElementsByTagName("text").item(0).getTextContent();
//
//            Message message = new Message(importance, date, read, sender, subject);
//            message.setRecipients(recipients);
//            message.setText(text);
//            message.setId(id);
//            return message;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
// Old Version of my saving message method
//     /**
//     * Saves the "read" status in the message file.
//     *
//     * @param message The edited message
//     */
//    private void saveRead(Message message, boolean bol) {
//        try {
//            System.out.println("Testing...");
//            message.setReadStatus(bol);
//            Path path = Paths.get("src/messages/examples/" + message.getId() + ".xml");
//            Charset charset = StandardCharsets.UTF_8;
//            String content = new String(Files.readAllBytes(path), charset);
//            content = content.replaceAll("<readStatus>.*</readStatus>", "<readStatus>" + bol + "</readStatus>");
//            Files.write(path, content.getBytes(charset));
//        } catch (IOException ex) {
//            Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    private void initButtons() {
        reply.setOnAction((e) -> reply());
        replyAll.setOnAction((e) -> replyAll());
        forward.setOnAction((e) -> forward());
    }

    private void reply() {
        System.out.println("Reply to sender...");
    }

    private void replyAll() {
        System.out.println("Reply to all...");
    }

    private void forward() {
        System.out.println("Forward this to...");
    }
}

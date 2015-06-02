package control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = FXMLLoader.load(getClass().getResource("../view/root.fxml"));
        root.setLeft(FXMLLoader.load(getClass().getResource("../view/tree.fxml")));
        root.setRight(FXMLLoader.load(getClass().getResource("../view/table.fxml")));
        primaryStage.setTitle("Mail-Client (FPA - Christoph Kozielski - 812529)");
        primaryStage.getIcons().add(new Image("/images/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

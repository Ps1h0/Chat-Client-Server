import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import requests.QuitRequest;

import java.io.IOException;
import java.util.Objects;

public class ChatApp extends Application {

    @Override
    public void start(Stage primaryStage)  {
        Network network = Network.getInstance();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("chat.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Chat");
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(request -> {
            try {
                network.writeMessage(new QuitRequest());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

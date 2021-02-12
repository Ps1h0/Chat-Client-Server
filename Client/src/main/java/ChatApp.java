import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Network network = Network.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("chat.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Chat");
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(request ->{
            try {
                network.writeMessage(new QuitRequest());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}

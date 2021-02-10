import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController {
    public ListView<String> listView;
    public TextField input;

    public void sendMessage(ActionEvent actionEvent) {
        String message = input.getText();
        input.clear();
        listView.getItems().add(message);
    }
}

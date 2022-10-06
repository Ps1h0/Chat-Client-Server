import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import messages.AbstractMessage;
import messages.TextMessage;
import messages.UserListMessage;
import requests.NickRequest;
import requests.QuitRequest;
import responses.NickResponse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    public ListView<String> userList;
    public TextField input;
    public ListView<String> listView;

    private Network network;
    private String nick;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        network = Network.getInstance();

        new Thread(() -> {
            try {
                network.writeMessage(new NickRequest());
                while (true) {
                    AbstractMessage message = network.readMessage();
                    if (message instanceof NickResponse) {
                        nick = ((NickResponse) message).getNick();
                    }
                    if (message instanceof UserListMessage) {
                        Platform.runLater(() -> {
                            userList.getItems().clear();
                            userList.getItems().addAll(((UserListMessage) message).getNames());
                        });
                    }
                    if (message instanceof QuitRequest) {
                        network.close();
                        break;
                    }
                    if (message instanceof TextMessage) {
                        TextMessage msg = (TextMessage) message;
                        String out = "[" + msg.getSendAt() + "]\n" + msg.getFrom() + ": " + msg.getMessage();
                        Platform.runLater(() -> listView.getItems().add(out));
                    }
                }
            } catch (IOException e) {
                System.err.println("Server was broken");
                Platform.runLater(() -> listView.getItems().add("Server was broken"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage() {
        String to = userList.getSelectionModel().getSelectedItem();
        try {
            if ((to != null))
                network.writeMessage(TextMessage.of(nick, to, input.getText()));
            else
                network.writeMessage(TextMessage.of(nick, input.getText()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userList.getSelectionModel().clearSelection();
        input.clear();
    }
}

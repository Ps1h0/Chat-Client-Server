import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    public ListView<String> userList;
    private Network network;
    public TextField input;
    public ListView<String> listView;
    private String nick;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        network = Network.getInstance();

        new Thread(() -> {
            try {
                network.writeMessage(new NickRequest());
                while (true) {
                    AbstractMessage message = network.readMessage();
                    if (message instanceof NickResponse){
                        nick = ((NickResponse) message).getNick();
                    }
                    if (message instanceof UserListMessage){
                        Platform.runLater(() ->{
                            userList.getItems().clear();
                            userList.getItems().addAll(((UserListMessage) message).getNames());
                        });
                    }
                    if (message instanceof QuitRequest){
                        network.close();
                        break;
                    }
                    if (message instanceof TextMessage){
                        TextMessage msg = (TextMessage) message;
                        String out = msg.getSendAt() + " " + msg.getFrom() + ":   " + msg.getMessage();
                        Platform.runLater(() -> listView.getItems().add(out));
                    }
                }
            } catch (IOException ioException) {
                System.err.println("Server was broken");
                Platform.runLater(() -> listView.getItems().add("Server was broken"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        String to = userList.getSelectionModel().getSelectedItem();
        if (to != null){
            network.writeMessage(TextMessage.of(nick, to, input.getText()));
        }else {
            network.writeMessage(TextMessage.of(nick, input.getText()));
        }
        userList.getSelectionModel().clearSelection();
        input.clear();
    }

    public void quit(ActionEvent actionEvent) throws IOException {
        network.writeMessage(new QuitRequest());
    }
}

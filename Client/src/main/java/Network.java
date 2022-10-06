import messages.AbstractMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Network {

    private static final int PORT = Integer.parseInt(ConfigHandler.handleConfig(ConfigHandler.Name.Client).getProperty("PORT"));
    private static final String HOST = ConfigHandler.handleConfig(ConfigHandler.Name.Client).getProperty("HOST");
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private static Network instance;
    private Socket socket;

    public static Network getInstance() {
        if (instance == null) {
            instance = new Network();
        }
        return instance;
    }

    private Network() {
        try {
            socket = new Socket(HOST, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.err.println("Problem with server on port: " + PORT);
        }
    }

    public AbstractMessage readMessage() throws IOException, ClassNotFoundException {
        return (AbstractMessage) in.readObject();
    }

    public void writeMessage(AbstractMessage message) throws IOException {
            out.writeObject(message);
            out.flush();
    }

    public void close() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

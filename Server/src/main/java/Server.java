import messages.AbstractMessage;
import messages.TextMessage;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class Server {

    private static final int PORT = Integer.parseInt(ConfigHandler.handleConfig(ConfigHandler.Name.Server).getProperty("PORT"));

    private final ConcurrentLinkedDeque<ClientHandler> clients;


    public Server(int port) {
        clients = new ConcurrentLinkedDeque<>();
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("[DEBUG] server starter on port: " + port);
            while (true) {
                Socket socket = server.accept();
                System.out.println("[DEBUG] client accepted");
                ClientHandler handler = new ClientHandler(socket, this);
                addClient(handler);
                new Thread(handler).start();
            }
        } catch (Exception e) {
            System.err.println("Server was broken");
        }
    }

    public List<String> getUserNickNames() {
        return clients.stream().map(ClientHandler::getNickName).collect(Collectors.toList());
    }

    public void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        System.out.println("[DEBUG] client added to broadcast queue");
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("[DEBUG] client removed from broadcast queue");
    }

    public void broadcastMessage(AbstractMessage message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void sendPrivateMessage(TextMessage message) {
        for (ClientHandler client : clients) {
            if (client.getNickName().equals(message.getTo()) || client.getNickName().equals(message.getFrom())) {
                client.sendMessage(message);
            }
        }
    }

    public static void main(String[] args) {
        new Server(PORT);
    }
}

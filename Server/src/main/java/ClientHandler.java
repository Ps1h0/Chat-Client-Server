import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private final boolean running;
    private final String nickName;
    private static int cnt = 0;

    public String getNickName() {
        return nickName;
    }

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        running = true;
        cnt++;
        nickName = "user" + cnt;
    }

    @Override
    public void run() {
        try{
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            System.out.println("[DEBUG] client start processing");
            while (running){
                String message = in.readUTF();
                if (message.startsWith("/")){
                    if (message.equals("/quit")){
                        out.writeUTF(message);
                    }
                    if (message.startsWith("/w ")){
                        String[] tokens = message.split("\\s");
                        String nick = tokens[1];
                        String msg = message.substring(4 + nick.length());
                        server.sendPrivateMessage(this, nick, msg);
                    }
                    continue;
                }
                server.broadcastMessage(nickName + ": " + message);
                System.out.println("[DEBUG] message from client: " + message);
            }
        }catch (Exception e){
            System.err.println("Handled connection was broken");
            server.removeClient(this);
        }

    }

    public void sendMessage(String message) throws IOException {
            out.writeUTF(message);
            out.flush();

    }
}

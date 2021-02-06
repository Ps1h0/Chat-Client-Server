import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class EchoServer {

    private boolean running;

    public EchoServer(){
        running = true;
        try (ServerSocket server = new ServerSocket(8189)){
            while (running){
                Socket socket = server.accept();
                    new Thread(() -> {
                        try {
                            handle(socket);
                        } catch (IOException ioException) {
                            System.out.println("Client connection was broken");
                        }
                    }).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in);
        DataInputStream is = new DataInputStream(socket.getInputStream());
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        Thread listen = new Thread(() -> {
           try {
               while (true){
                   String msg = is.readUTF();
                   System.out.println("from client: " + msg);
                   if (msg.equals("exit")){
                       os.writeUTF("Good bye!");
                       os.flush();
                       break;
                   }
               }
           }catch (IOException e){
               e.printStackTrace();
           }
        });

        Thread write = new Thread(() -> {
            try {
                while (true){
                    String msg = scanner.next();
                    os.writeUTF(msg);
                    os.flush();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        listen.start();
        write.start();
    }

    public static void main(String[] args) {
        new EchoServer();
    }
}

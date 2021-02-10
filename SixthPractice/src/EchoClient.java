import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 8189);
        DataInputStream is = new DataInputStream(socket.getInputStream());
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        Thread listen = new Thread(() -> {
            try {
                while (true){
                    String message = is.readUTF();
                    System.out.println("From server: " + message);
                }
            }catch (IOException e){
                System.out.println("Server was broken");
            }
        });

        Thread write = new Thread(() -> {
            try{
                while (true){
                    String message = scanner.nextLine();
                    if (message.equals("exit")){
                        os.writeUTF("Good bye!");
                        os.flush();
                        break;
                    }
                    os.writeUTF(message);
                    os.flush();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        listen.start();
        write.start();
    }
}

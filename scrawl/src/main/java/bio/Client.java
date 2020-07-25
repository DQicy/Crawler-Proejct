package bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.SocketHandler;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
//            socket.connect(new InetSocketAddress(8080));

            //下面的代码也会阻塞
            Scanner scanner = new Scanner(System.in);
            String txt = scanner.next();

            socket.getOutputStream().write(txt.getBytes());

            //socket.getOutputStream().write("111".getBytes());

            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

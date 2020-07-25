package bio;

import com.mysql.cj.fabric.Server;
import sun.tools.jstat.Jstat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

//服务端的编写
public class QQServer {
    //new出来一个字节数组，进行存数据
    static byte[] bytes = new byte[1024];

    public static void main(String[] args) {


        try {

            //用于监听
            ServerSocket serverSocket = new ServerSocket();
            //绑定服务器的ip和端口，ip为本机，所以省略
            serverSocket.bind(new InetSocketAddress(8080));

            while(true) {
            System.out.println("wait conn");
            //监听，会阻塞->当前线程回放弃CPU线程，就意味着不会再向下执行了
            //这个socket是专门用于与客户端通信的socket
            Socket socket = serverSocket.accept();

            System.out.println("conn success");
            System.out.println("wait data");

            //专门用于接受客户端发来的byte数组,返回一个int类型的值，用于表示返回多少字节
            int read = socket.getInputStream().read(bytes);

            System.out.println("data success");
            String content = new String(bytes);
            System.out.println(content);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

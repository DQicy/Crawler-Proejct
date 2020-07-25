package bio;

import com.sun.tools.internal.xjc.model.CArrayInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class QQServerNIO {

    static byte[] bytes = new byte[1024];
    static List<SocketChannel> list = new ArrayList<SocketChannel>();
    //申请对外内存
    static ByteBuffer byteBuffer = ByteBuffer.allocate(512);

    public static void main(String[] args) throws  Exception{
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.bind(new InetSocketAddress(8080));

            //设置为serversocketchannel为非阻塞
            serverSocketChannel.configureBlocking(false);

            while(true){

                //非阻塞
                SocketChannel socketChannel = serverSocketChannel.accept();

                if(socketChannel == null) {

                    Thread.sleep(500);
                    System.out.println("no conn");

                    for (SocketChannel client : list) {
                        int k = client.read(byteBuffer);
                        if (k > 0) {
                            byteBuffer.flip();
                            while (byteBuffer.hasRemaining()) {
                                System.out.print((char) byteBuffer.get());
                            }
                            System.out.println("\n");
                        }
                    }
                }else{
                    System.out.println("conn----");
                    socketChannel.configureBlocking(false);
                    list.add(socketChannel);

                    for(SocketChannel client : list){
                        int i = client.read(byteBuffer);
                        if(i > 0){
                            byteBuffer.flip();
                            System.out.println(byteBuffer.toString());
                        }

                    }
                }

            }

    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
    }
}

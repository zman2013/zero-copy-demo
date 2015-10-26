package zero.copy.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ZeroCopyServer {

    private ServerSocketChannel listener;

    public void init() throws IOException {
        InetSocketAddress listenAddress = new InetSocketAddress(9026);

        listener = ServerSocketChannel.open();

        ServerSocket serverSocket = listener.socket();

        serverSocket.setReuseAddress(true);

        serverSocket.bind(listenAddress);

        System.out.println("监听端口：" + listenAddress.toString());
    }

    public void readData() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel channel = listener.accept();

            System.out.println("创建连接：" + channel);

            channel.configureBlocking(true);

            while (channel.read(buffer) != -1) {
                buffer.rewind();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ZeroCopyServer server = new ZeroCopyServer();
        server.init();
        server.readData();
    }
}

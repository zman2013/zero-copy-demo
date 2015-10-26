package zero.copy.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class ZeroCopyClient {

    public void sendFile() throws IOException {
        SocketAddress socketAddress = new InetSocketAddress("localhost", 9026);
        SocketChannel channel = SocketChannel.open();
        channel.connect(socketAddress);
        channel.configureBlocking(true);

        String file = "src/main/resources/file.store";
        try (FileInputStream input = new FileInputStream(file)) {
            FileChannel fileChannel = input.getChannel();
            fileChannel.transferTo(0, fileChannel.size(), channel);
        }
    }

    public static void main(String[] args) throws IOException {
        ZeroCopyClient client = new ZeroCopyClient();
        client.sendFile();
    }
}

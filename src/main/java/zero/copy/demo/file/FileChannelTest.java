package zero.copy.demo.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Random;

public class FileChannelTest {

    public static void transferTo(String srcFilePath, String dstFilePath) throws IOException {
        try (FileInputStream input = new FileInputStream(srcFilePath);
                FileOutputStream output = new FileOutputStream(dstFilePath)) {
            FileChannel srcChannel = input.getChannel();
            FileChannel dstChannel = output.getChannel();

            long startTime = System.nanoTime();
            long count = srcChannel.size();
            srcChannel.transferTo(0, count, dstChannel);
            System.out.println("transfer, sent byte counts: " + count + ", used time: "
                    + (System.nanoTime() - startTime));
        }
    }

    public static void copy(String srcFilePath, String dstFilePath) throws IOException {

        try (FileInputStream input = new FileInputStream(srcFilePath);
                FileOutputStream output = new FileOutputStream(dstFilePath)) {
            int len = 0;
            byte[] buf = new byte[1024];
            long count = 0;
            long startTime = System.nanoTime();
            while ((len = input.read(buf, 0, 1024)) != -1) {
                count += len;
                output.write(buf, 0, len);
            }
            System.out.println("copy    , sent byte counts: " + count + ", used time: "
                    + (System.nanoTime() - startTime));
        }
    }

    public static void main(String[] args) throws IOException {
        String srcFilePath = "src/main/resources/file.src";
        String dstFilePath = "src/main/resources/file.dst";
        FileChannelTest.transferTo(srcFilePath, dstFilePath);
        FileChannelTest.transferTo(srcFilePath, dstFilePath);
        FileChannelTest.copy(srcFilePath, dstFilePath);
        FileChannelTest.copy(srcFilePath, dstFilePath);

        //        FileChannelTest.initSrcFile();
    }

    public static void initSrcFile() throws FileNotFoundException, IOException {
        String srcFilePath = "src/main/resources/file.src";
        try (FileOutputStream output = new FileOutputStream(srcFilePath)) {
            Random random = new Random();
            for (int i = 0; i < 1024 * 1024 * 100; i++) {
                output.write(random.nextInt());
            }
        }
    }
}

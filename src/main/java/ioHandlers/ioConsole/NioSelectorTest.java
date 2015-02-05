package ioHandlers.ioConsole;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lexor on 03.02.2015.
 */
public class NioSelectorTest {
    public static void main(String[] args) {
        new Thread(new Receiver()).start();
        new Thread(new Sender()).start();
    }
}

class Receiver implements Runnable {
    private static byte[] data = new byte[255];

    public void run() {
        try {
            for (int i = 0; i < data.length; i++)
                data[i] = (byte) i;

            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);

            server.socket().bind(new InetSocketAddress(9000));
            Selector selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                Set readyKeys = selector.selectedKeys();
                Iterator iterator = readyKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = (SelectionKey) iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        ByteBuffer source = ByteBuffer.wrap(data);
                        SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
                        key2.attach(source);
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        if (!output.hasRemaining()) {
                            output.rewind();
                        }
                        client.write(output);
                    }

                    key.channel().close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Sender implements Runnable {

    public void run() {
        try {
            SocketChannel sChannel = SocketChannel.open();
            sChannel.configureBlocking(false);
            sChannel.connect(new InetSocketAddress("localhost", 9000));
            while (!sChannel.finishConnect()) {
                ByteBuffer buf = ByteBuffer.allocateDirect(1024);
                buf.put((byte) 0xFF);

                buf.flip();
                int numBytesWritten = sChannel.write(buf);
                System.out.println("sent " + numBytesWritten + " bytes");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

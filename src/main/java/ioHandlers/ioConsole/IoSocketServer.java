package ioHandlers.ioConsole;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by lexor on 03.02.2015.
 */
public class IoSocketServer {


    public void prepareConnection() throws IOException, ExecutionException, InterruptedException {


        AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 3883);
        serverChannel.bind(hostAddress);

        Future acceptResult = serverChannel.accept();
        AsynchronousSocketChannel clientChannel = (AsynchronousSocketChannel) acceptResult.get();

        ByteBuffer buffer = ByteBuffer.allocate(32);
        Future result = clientChannel.read(buffer);

        String message = new String(buffer.array()).trim();
        System.out.println(message);

        clientChannel.close();
        serverChannel.close();

      /*  ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);

        while (true) {
            SocketChannel socketChannel =
                    serverSocketChannel.accept();

            if (socketChannel != null) {

                ByteBuffer buf = ByteBuffer.allocateDirect(1024);
                buf.put((byte) 0xFF);
                buf.flip();
                int numBytesWritten = socketChannel.write(buf);



            }
        }*/
    }
}

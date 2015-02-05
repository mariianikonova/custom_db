package ioHandlers.ioConsole;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by lexor on 03.02.2015.
 */
public class IoSocketServer {


    public static void main(String[] args)
            throws Exception {

        new IoSocketServer().prepareServerConnection();
    }

    public void prepareServerConnection() throws IOException, ExecutionException, InterruptedException {

        AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 9999);
        serverChannel.bind(inetSocketAddress);

        System.out.println("Server channel bound to port: " + inetSocketAddress.getPort());
        System.out.println("Waiting for client to connect... ");

        Future acceptResult = serverChannel.accept();
        AsynchronousSocketChannel clientChannel = (AsynchronousSocketChannel) acceptResult.get();
        System.out.println("Messages from client: ");

        if (clientChannel != null && clientChannel.isOpen()) {

            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(32);
                Future result = clientChannel.read(byteBuffer);

                while (!result.isDone()) {
                    //do nothing
                }
                byteBuffer.flip();

                String message = new String(byteBuffer.array()).trim();
                System.out.println(message);

                if (message.equals("Bye. ")) {
                    break; //while loop
                }
                byteBuffer.clear();
            }//end-while
            clientChannel.close();
        }//end-if
        serverChannel.close();
    }
}

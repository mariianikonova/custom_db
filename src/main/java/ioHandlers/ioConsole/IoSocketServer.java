package ioHandlers.ioConsole;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SocketChannel;
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

/*        Future acceptResult = serverChannel.accept();
        AsynchronousSocketChannel clientChannel = (AsynchronousSocketChannel) acceptResult.get();
        System.out.println("Messages from client: ");*/


        while(true) {
            SocketChannel socketChannel =
                    serverChannel.accept();
        }
            while (clientChannel != null && clientChannel.isOpen()) {
            int clientHash = clientChannel.hashCode();
            System.out.println("Client was Caught: " + clientHash);

            ByteBuffer byteBuffer = ByteBuffer.allocate(124);
            Future result = clientChannel.read(byteBuffer);

            while (!result.isDone()) {
                System.out.println("Waiting for task from Client: " + clientChannel.hashCode());/*wait while task will be finished*/
            }
            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                String message = new String(byteBuffer.array()).trim();
                System.out.println(message);
                if (message.equals("Bye. ")) {
                    break; //while loop
                }
            }
            byteBuffer.clear();
        }//end-while
            clientChannel.close();
            System.out.println("Client is cloused: " + clientChannel.hashCode());

        //   serverChannel.close();
    }//end-if
}




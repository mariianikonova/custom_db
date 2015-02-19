package ioHandlers.ioClientServer.NioClientServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.*;

import static ioHandlers.ioClientServer.ClientServerConstr.*;


/**
 * Created by lexor on 03.02.2015.
 */
public class NioSocketClientAsynch {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        prepareClientConnection();
    }

    public static void prepareClientConnection() throws IOException, ExecutionException, InterruptedException {
        System.out.println("START prepareClientConnection time: " + System.nanoTime() + TimeUnit.MILLISECONDS);

        try (AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open()) {
            InetSocketAddress socketAddress = new InetSocketAddress(RECEIVER_IP, RECEIVER_PORT);

            System.out.println(CLIENT_LOCAL_ADDRESS_STRING + socketChannel.getLocalAddress());
            System.out.println(CLIENT_REMOTE_ADDRESS_STRING + socketChannel.getLocalAddress());

            Future future = socketChannel.connect(socketAddress);
            System.out.println("Client is started: " + socketChannel.isOpen());
            System.out.println("Sending messages to server: ");
            String[] messages = new String[]{MESSAGE1, MESSAGE2, MESSAGE3};

            while (!future.isDone()) {
                System.out.println("Connection establishment process...: " + System.nanoTime() + TimeUnit.MILLISECONDS);
            }
            for (int i = 0; i < messages.length; i++) {
                byte[] message = new String(messages[i]).getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(message);
                future = socketChannel.write(buffer);
                System.out.println("MESSAGE was sent time: " + System.nanoTime() + TimeUnit.MILLISECONDS + message);
                while (!future.isDone()) {
                    System.out.println("... ");
                }
                System.out.println(messages[i]);
                buffer.clear();
                Thread.sleep(3000);

            }
        }
        System.out.println("END prepareClientConnection time: " + System.nanoTime() + TimeUnit.MILLISECONDS);
    }

}
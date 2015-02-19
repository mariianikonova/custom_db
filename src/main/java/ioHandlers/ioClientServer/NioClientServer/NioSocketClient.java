package ioHandlers.ioClientServer.NioClientServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import static ioHandlers.ioClientServer.ClientServerConstr.*;

/**
 * Created by user on 16.02.15.
 */
public class NioSocketClient {


    public static void main(String[] args) {

        SocketAddress address = new InetSocketAddress(RECEIVER_IP, RECEIVER_PORT);
        try (SocketChannel clientChannel = SocketChannel.open(address)) {

            System.out.println(CLIENT_LOCAL_ADDRESS_STRING + clientChannel.getLocalAddress());
            System.out.println(CLIENT_REMOTE_ADDRESS_STRING + clientChannel.getLocalAddress());

            String messages[] = new String[]{MESSAGE1, MESSAGE2, MESSAGE3};

            for (String message : messages) {
                byte[] bytes = (message).getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                int written = clientChannel.write(buffer);
                System.out.println("MESSAGE: "+ message +" was sent FROM : " + clientChannel.getLocalAddress() + " AT: " + System.nanoTime() + File.separator + TimeUnit.MILLISECONDS);
                System.out.println(message);
                while (written == 0) {
                    System.out.println("... ");
                }
                buffer.clear();
                Thread.sleep(1000);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}




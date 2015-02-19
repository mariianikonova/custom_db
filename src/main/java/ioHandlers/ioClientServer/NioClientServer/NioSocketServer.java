package ioHandlers.ioClientServer.NioClientServer;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static ioHandlers.ioClientServer.ClientServerConstr.*;

/**
 * Created by lexor on 03.02.2015.
 */
public class NioSocketServer {

    public static void main(String[] args)
            throws Exception {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(RECEIVER_IP, RECEIVER_PORT);
            serverChannel.bind(inetSocketAddress);

            System.out.println("Waiting for client to connect... ");
            System.out.println(SERVER_LOCAL_ADDRESS_STRING + serverChannel.getLocalAddress());
            System.out.println(SERVER_REMOTE_ADDRESS_STRING + serverChannel.getLocalAddress());

            ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (true) {
                SocketChannel socketChannel =
                        serverChannel.accept();

                if (socketChannel != null) {
                    while (socketChannel.read(byteBuffer) != -1) {
                        byteBuffer.flip();
                        byte[] buff = new byte[byteBuffer.remaining()];
                        byteBuffer.get(buff);
                        System.out.println("MESSAGE: " + new String(buff) + "FROM: " + socketChannel.getLocalAddress());
                        byteBuffer.clear();
                    }
                }
            }
        }
    }
}




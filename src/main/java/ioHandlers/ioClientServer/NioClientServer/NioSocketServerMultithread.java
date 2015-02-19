package ioHandlers.ioClientServer.NioClientServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

import static ioHandlers.ioClientServer.ClientServerConstr.*;


/**
 * Created by lexor on 03.02.2015.
 */
public class NioSocketServerMultiThread {

    private static Selector selector;

    public static void main(String[] args)
            throws Exception {

        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {

            InetSocketAddress inetSocketAddress = new InetSocketAddress(RECEIVER_IP, RECEIVER_PORT);
            serverChannel.bind(inetSocketAddress);

            System.out.println("Waiting for client to connect... ");
            System.out.println(SERVER_LOCAL_ADDRESS_STRING + serverChannel.getLocalAddress());
            System.out.println(SERVER_REMOTE_ADDRESS_STRING + serverChannel.getLocalAddress());

            serverChannel.configureBlocking(false);
            selector = Selector.open();
            ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT, byteBuffer);

            while (true) {
                if (selector.select() != 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        key.attach(byteBuffer);
                        try {
                            if (key.isAcceptable()) {
                                System.out.println("Client was Caught 1: " + key.channel().hashCode());

                                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                                SocketChannel socketChannel = serverSocketChannel.accept();

                                System.out.println(CLIENT_REMOTE_ADDRESS_STRING + socketChannel.getRemoteAddress());
                                System.out.println(CLIENT_LOCAL_ADDRESS_STRING + socketChannel.getLocalAddress());

                                registerChannel(selector, socketChannel, SelectionKey.OP_READ);
                            }

                            if (key.isReadable()) {
                                System.out.println("Client was Caught 2: " + key.channel().hashCode());
                                readDataFromSocket(key);
                            }
                            if (key.isWritable()) {
                                System.out.println("Client was Caught 3: " + key.channel().hashCode());
                            }
                            if (key.isConnectable()) {
                                System.out.println("Client was Caught 4: " + key.channel().hashCode());
                            }
                            keyIterator.remove();
                        } catch (CancelledKeyException e) {
                            System.out.println("Key cancelled... Closing channel.");
                            key.channel().close();
                        }
                    }
                } else {
                    continue;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readDataFromSocket(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();

        while ((count = socketChannel.read(byteBuffer)) > 0) {
            byteBuffer.flip();

            byte[] buff = new byte[byteBuffer.remaining()];
            byteBuffer.get(buff);
            System.out.println("MESSAGE: " + new String(buff) + " FROM: " + socketChannel.getLocalAddress());
            byteBuffer.clear();
        }
        if (count < 0) {
            socketChannel.close();
        }
    }

    private static void registerChannel(Selector selector, SelectableChannel socketChannel, int opRead) throws IOException {
        if (socketChannel == null) {
            return;
        }
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, opRead);
    }
}




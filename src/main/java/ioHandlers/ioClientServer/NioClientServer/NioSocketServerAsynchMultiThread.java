package ioHandlers.ioClientServer.NioClientServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.*;

import static ioHandlers.ioClientServer.ClientServerConstr.*;

/**
 * Created by lexor on 03.02.2015.
 */
public class NioSocketServerAsynchMultiThread {

    public static void main(String[] args) {
        try {
            AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 2);

            InetSocketAddress inetSocketAddress = new InetSocketAddress(RECEIVER_IP, RECEIVER_PORT);
            AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open(channelGroup);

            if (serverChannel.isOpen()) {
                serverChannel.bind(inetSocketAddress);
                serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                System.out.println("Waiting for connections ...");
                System.out.println(SERVER_LOCAL_ADDRESS_STRING + serverChannel.getLocalAddress());
                System.out.println(SERVER_REMOTE_ADDRESS_STRING + serverChannel.getLocalAddress());

                serverChannel.accept(null, new
                        CompletionHandler<AsynchronousSocketChannel, Void>() {


                            @Override
                            public void completed(AsynchronousSocketChannel result, Void attachment) {
                                serverChannel.accept(null, this);
                                ByteBuffer buffer = ByteBuffer.allocateDirect(4 * BUFFER_SIZE);
                                try {
                                    System.out.println(CLIENT_REMOTE_ADDRESS_STRING + result.getRemoteAddress());
                                    System.out.println(CLIENT_LOCAL_ADDRESS_STRING + result.getLocalAddress());

                                    while (result.read(buffer).get() != -1) {
                                        buffer.flip();
                                        byte buff[] = new byte[buffer.remaining()];
                                        buffer.get(buff);
                                        System.out.println("MESSAGE: " + new String(buff) + " FROM: " + result.getRemoteAddress());
                                        if (buffer.hasRemaining()) {
                                            buffer.compact();
                                        } else {
                                            buffer.clear();
                                        }
                                    }
                                } catch (IOException | InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        result.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void failed(Throwable exc, Void attachment) {
                                serverChannel.accept(null, this);
                                throw new UnsupportedOperationException("Cannot accept connections!");
                            }
                        });
                channelGroup.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}





package ioHandlers.ioConsole;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by lexor on 03.02.2015.
 */
public class IoSocketServer {

    private ConcurrentHashMap<Integer, AsynchronousSocketChannel> clientConnections = new ConcurrentHashMap<>();

    private volatile static AsynchronousServerSocketChannel serverChannel;

    static {
        try (final AsynchronousServerSocketChannel serverChannelInintial = AsynchronousServerSocketChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 9999);
            serverChannelInintial.bind(inetSocketAddress);
            System.out.println("Server channel bound to port: " + inetSocketAddress.getPort());
            System.out.println("Waiting for client to connect... ");
            serverChannel = serverChannelInintial;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
            throws Exception {
        IoSocketServer.handleIncome(serverChannel);
    }

/*    public void prepareServerConnection() throws IOException, ExecutionException, InterruptedException {

        final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 9999);
        serverChannel.bind(inetSocketAddress);
        System.out.println("Server channel bound to port: " + inetSocketAddress.getPort());
        System.out.println("Waiting for client to connect... ");
    }*/

    public static void handleIncome(AsynchronousServerSocketChannel serverChannel) {
        Future<AsynchronousSocketChannel> accepted = serverChannel.accept();
        try (AsynchronousSocketChannel clientChannel = accepted.get()) {



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


/*        ByteBuffer bb = ByteBuffer.allocateDirect(4096);
        Future<String> readFuture = clientChannel.read(bb);*/

        while (clientChannel != null && clientChannel.isOpen()) {
            int clientHash = clientChannel.hashCode();
            System.out.println("Client was Caught: " + clientHash);

            ByteBuffer byteBuffer = ByteBuffer.allocate(124);
            Future result = clientChannel.read(byteBuffer);

            while (!result.isDone()) {
                System.out.println("Waiting for task from Client: " + clientChannel.hashCode());/*wait while task will be finished*/
            }
            byteBuffer.flip();

    /*        while (byteBuffer.hasRemaining()) {*/
            String message = new String(byteBuffer.array()).trim();
            System.out.println("MESSAGE: " + message + "FROM: " + clientHash);
            if (message.equals("Bye. ")/* || message.isEmpty()*/) {
                clientChannel.close();
                break; //while loop
            }
/*            }*/
            byteBuffer.clear();
        }//end-while

        System.out.println("Client is cloused: " + clientChannel.hashCode());

        //   serverChannel.close();
    }//end-if
}




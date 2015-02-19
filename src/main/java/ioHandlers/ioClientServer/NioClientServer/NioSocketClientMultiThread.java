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
public class NioSocketClientMultiThread implements Runnable {

    private static final int CLIENT_THREADS = 12;
    private CountDownLatch latch;
    private String threadName;


    public NioSocketClientMultiThread(CountDownLatch latch, String threadName) {
        this.latch = latch;
        this.threadName = threadName;
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(4);

        ExecutorService service = Executors.newFixedThreadPool(4);

        for (int i = 0; i < CLIENT_THREADS; i++) {
            Runnable t = new NioSocketClientMultiThread(countDownLatch, i + "THREAD: ");
            service.execute(t);

        }
        service.shutdown();
        while (service.isTerminated()) {
            System.out.println("Finished all threads");
        }
        System.out.println("Waiting for all workers");
        System.out.println("All workers finished. Now we can shake.");
    }

    public void prepareClientConnection() throws IOException, ExecutionException, InterruptedException {
        System.out.println("START prepareClientConnection: " + threadName + " time: " + System.nanoTime() + TimeUnit.MILLISECONDS);

        try (AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open()) {
            InetSocketAddress socketAddress = new InetSocketAddress(RECEIVER_IP, RECEIVER_PORT);

            System.out.println(CLIENT_LOCAL_ADDRESS_STRING + socketChannel.getLocalAddress());
            System.out.println(CLIENT_REMOTE_ADDRESS_STRING + socketChannel.getLocalAddress());

            Future future = socketChannel.connect(socketAddress);
            while (!future.isDone()) {
                System.out.println("Connection establishment process...: " + System.nanoTime() + TimeUnit.MILLISECONDS);
            }

            System.out.println("Client is started: " + socketChannel.isOpen());
            System.out.println("Sending messages to server: ");
            String[] messages = new String[]{MESSAGE1, MESSAGE2, MESSAGE3};

            for (String message : messages) {
                byte[] bytes = (message).getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                future = socketChannel.write(buffer);
                System.out.println("MESSAGE was sent: " + threadName + " time: " + System.nanoTime() + TimeUnit.MILLISECONDS + message);
                while (!future.isDone()) {
                    System.out.println("... ");
                }
                System.out.println(message);
                buffer.clear();
                Thread.sleep(3000);

            }
        }
        System.out.println("END prepareClientConnection: " + threadName + " time: " + System.nanoTime() + TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        System.out.println("BEFORE LATCH: " + threadName + " time: " + System.nanoTime() + TimeUnit.MILLISECONDS);
        latch.countDown();
        System.out.println("BEFORE LATCH: COUNT" + latch.getCount());
        try {
            latch.await();
            System.out.println("AFTER LATCH: " + threadName + " time: " + System.nanoTime() + TimeUnit.MILLISECONDS);
            prepareClientConnection();
            Thread.sleep(RandomGenerator.getRandom(500, 1000));

        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public static class RandomGenerator {
        public static long getRandom(long min, long max) {
            return min + (long) (Math.random() * (max - min + 1));
        }
    }
}

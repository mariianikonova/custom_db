package ioHandlers.ioConsole;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.*;

/**
 * Created by lexor on 03.02.2015.
 */
public class IoSocketClient implements Runnable {

    private static CountDownLatch latch;
    private String threadName;


    public IoSocketClient(CountDownLatch latch, String threadName) {
        this.latch = latch;
        this.threadName = threadName;
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(4);

        ExecutorService service = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 12; i++) {
            Runnable t = new IoSocketClient(countDownLatch, i + "THREAD: ");
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
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 9999);
        Future future = socketChannel.connect(socketAddress);
        future.get(); //returns null

        System.out.println("Client is started: " + socketChannel.isOpen());
        System.out.println("Sending messages to server: ");
        String[] messages = new String[]{"Time goes fast.", "What now?", "Bye."};

        for (int i = 0; i < messages.length; i++) {
            byte[] message = new String(messages[i]).getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            future = socketChannel.write(buffer);
            while (!future.isDone()) {
                System.out.println("... ");
            }
            System.out.println(messages[i]);
            buffer.clear();
            Thread.sleep(3000);

        }//for
        socketChannel.close();
        System.out.println("END prepareClientConnection: " + threadName + " time: " + System.nanoTime() + TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        System.out.println("BEFORE LATCH: " + threadName + " time: " + System.nanoTime() + TimeUnit.MILLISECONDS);
        latch.countDown();
        System.out.println("BEFORE LATCH: COUNT" + latch.getCount());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("AFTER LATCH: " + threadName + " time: " + System.nanoTime() + TimeUnit.MILLISECONDS);
        try {
            prepareClientConnection();
            Thread.sleep(RandomGenerator.getRandom(500, 1000));
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class RandomGenerator {

        public static long getRandom(long min, long max) {
            return min + (long) (Math.random() * (max - min + 1));
        }
    }
}

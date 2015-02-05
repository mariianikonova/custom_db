package ioHandlers.ioConsole;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by lexor on 03.02.2015.
 */
public class IoSocketClient implements Runnable {

    private static CountDownLatch latch;
    private String threadName;

    private IoSocketClient(CountDownLatch latch, String threadName) {
        this.latch = latch;
        this.threadName = threadName;
    }

    public static void main(String[] args)
            throws Exception {


        CountDownLatch countDownLatch = new CountDownLatch(4);

        new IoSocketClient(countDownLatch, "1FIRST: ").run();
        new IoSocketClient(countDownLatch, "2SECOND: ").prepareClientConnection();
        new IoSocketClient(countDownLatch, "3THIRD: ").prepareClientConnection();
        new IoSocketClient(countDownLatch, "4FORTH: ").prepareClientConnection();

        System.out.println("Waiting for all workers");
        latch.await();
        System.out.println("All workers finished. Now we can shake.");
    }

    public void prepareClientConnection() throws IOException, ExecutionException, InterruptedException {
        System.out.println("START prepareClientConnection: " + threadName + " time: " + TimeUnit.MILLISECONDS + " ms");
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
            Future result = socketChannel.write(buffer);
            while (!result.isDone()) {
                System.out.println("... ");
            }
            System.out.println(messages[i]);
            buffer.clear();
            Thread.sleep(3000);

        }//for
        socketChannel.close();
        System.out.println("END prepareClientConnection: " + threadName + " time: " + TimeUnit.MILLISECONDS + " ms");
    }

    @Override
    public void run() {
        System.out.println("BEFORE LATCH: " + threadName + " time: " + TimeUnit.MILLISECONDS + " ms");
        latch.countDown();
        System.out.println("AFTER LATCH: " + threadName + " time: " + TimeUnit.MILLISECONDS + " ms");
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

package concurent_tests;

import ioHandlers.ioConsole.IoSocketClient;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lexor on 11.01.2015.
 */

public class MultiThreadingClientSocketTest {
    @Test

    public void testAdd() {
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
}

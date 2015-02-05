package ioHandlers.ioConsole;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

/**
 * Created by lexor on 03.02.2015.
 */
public class IoSocketClient {

    public void prepareConnection() throws IOException, ExecutionException, InterruptedException {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
    }
}

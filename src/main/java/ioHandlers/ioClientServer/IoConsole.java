package ioHandlers.ioClientServer;

import operations.operationDeterminators.CrudDeterminator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created on 30.01.15.
 */
public class IoConsole {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int inputDef = 0;
        while (inputDef == 0) {
            System.out.print("Select Operations (create/retrieve/update/delete):  ");
            String str = br.readLine();
            inputDef = CrudDeterminator.handleInput(str);
        }
    }


}


/*public void readConsole() throws IOException {
        Selector selector = Selector.open();
        SystemInPipe stdinPipe = new SystemInPipe();
        SelectableChannel stdin = stdinPipe.getStdinChannel();
        ByteBuffer buffer = ByteBuffer.allocate(32);

        stdin.register(selector, SelectionKey.OP_READ);
        stdinPipe.start();

        System.out.println("Entering select(), type something:");

        while (true) {
            selector.select(2000);

            Iterator it = selector.selectedKeys().iterator();

            if (!it.hasNext()) {
                System.out.println("I'm waiting");
                continue;
            }

            SelectionKey key = (SelectionKey) it.next();

            it.remove();
            buffer.clear();

            ReadableByteChannel channel =
                    (ReadableByteChannel) key.channel();
            int count = channel.read(buffer);

            if (count < 0) {
                System.out.println("EOF, bye");

                channel.close();
                break;
            }

            buffer.flip();

            System.out.println("Hey, read " + count + " chars:");

            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }

            System.out.println();
        }
    }

    class SystemInPipe {
        Pipe pipe;
        CopyThread copyThread;

        public SystemInPipe(InputStream in)
                throws IOException {
            pipe = Pipe.open();

            copyThread = new CopyThread(in, pipe.sink());
        }

        public SystemInPipe()
                throws IOException {
            this(System.in);
        }

        public void start() {
            copyThread.start();
        }

        public SelectableChannel getStdinChannel()
                throws IOException {
            SelectableChannel channel = pipe.source();

            channel.configureBlocking(false);

            return (channel);
        }

        protected void finalize() {
            copyThread.shutdown();
        }

        // ---------------------------------------------------

        public class CopyThread extends Thread {
            boolean keepRunning = true;
            byte[] bytes = new byte[128];
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            InputStream in;
            WritableByteChannel out;

            CopyThread(InputStream in, WritableByteChannel out) {
                this.in = in;
                this.out = out;
                this.setDaemon(true);
            }

            public void shutdown() {
                keepRunning = false;
                this.interrupt();
            }

            public void run() {
                // this could be improved

                try {
                    while (keepRunning) {
                        int count = in.read(bytes);

                        if (count < 0) {
                            break;
                        }

                        buffer.clear().limit(count);

                        out.write(buffer);
                    }

                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
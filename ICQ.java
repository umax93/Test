package ICQ;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ICQ implements Runnable {
   // public void run() {
        private ServerSocket ss;
        private Thread serverThread;
        private int port;
        BlockingQueue<SocketProcessor> q = new LinkedBlockingQueue<SocketProcessor>();

        public ICQ( int port)throws IOException {
            ss = new ServerSocket(port);
            this.port = port;
             }
              public void run() {
            serverThread = Thread.currentThread();
            while (true) {
                Socket s = getNewConn();
                if (serverThread.isInterrupted()) {
                    break;
                } else if (s != null) { //
                    try {
                        final SocketProcessor processor = new SocketProcessor(s);
                        final Thread thread = new Thread(processor);
                        thread.setDaemon(true);
                        thread.start();
                        q.offer(processor);
                    } catch (IOException ignored) {
                    }
                }
            }
        }

    private Socket getNewConn() {
        Socket s = null;
        try {
            s = ss.accept();
        } catch (IOException e) {
            shutdownServer();
        }
        return s;
    }

    private synchronized void shutdownServer() {

        for (SocketProcessor s : q) {
            s.close();
        }
        if (!ss.isClosed()) {
            try {
                ss.close();
            } catch (IOException ignored) {
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //45000
        new ICQ(45000).run();
    }


    private class SocketProcessor implements Runnable {
        Socket s;
        BufferedReader br;
        BufferedWriter bw;

        SocketProcessor(Socket socketParam) throws IOException {
            s = socketParam;
            br = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
        }

        public void run() {
            while (!s.isClosed()) {
                String line = null;
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    close();
                }

                if (line == null) {
                    close();
                } else if ("shutdown".equals(line)) {
                    serverThread.interrupt();
                    try {
                        new Socket("localhost", port);
                    } catch (IOException ignored) {
                    } finally {
                        shutdownServer();
                    }
                } else {
                    for (SocketProcessor sp : q) {
                        sp.send(line);
                    }
                }
            }
        }

        public synchronized void send(String line) {
            try {
                bw.write(line);
                bw.write("\n");
                bw.flush();
            } catch (IOException e) {
                close();
            }
        }

        public synchronized void close() {
            q.remove(this);
            if (!s.isClosed()) {
                try {
                    s.close();
                } catch (IOException ignored) {
                }
            }
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            close();
        }
    }
}

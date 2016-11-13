package me.yummykang.ch2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/11/13 15:20
 */
public class TimerServer {
    public static void main(String[] args) {
        int port = 8089;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("the server is started in port:" + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                // 普通开线程处理
//                new Thread(new TimerServerHandler(socket)).start();
                // 利用线程池及消息队列去处理
                new TimerServerHandlerExecutePool(50, 1000).execute(new Thread(new TimerServerHandler(socket)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                System.out.println("The time server closed.");
                try {
                    serverSocket.close();
//                    serverSocket = null; // 为什么要写这个呢，我是真不懂的
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * 普通的线程处理类
 */
class TimerServerHandler implements Runnable {

    private Socket socket;

    public TimerServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;

            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }

                System.out.println("The timer server receive order:" + body);
                currentTime = "Time".equalsIgnoreCase(body) ? new Date().toString() : "Bad Order";
                out.println(currentTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * 伪异步I/O处理类
 */
class TimerServerHandlerExecutePool {
    private ExecutorService executorService;

    public TimerServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        executorService.execute(task);
    }
}
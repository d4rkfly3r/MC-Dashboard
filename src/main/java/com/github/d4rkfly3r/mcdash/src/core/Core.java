package com.github.d4rkfly3r.mcdash.src.core;

import com.github.d4rkfly3r.mcdash.src.MCDashThreadGroup;
import com.github.d4rkfly3r.mcdash.src.MainClass;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class Core extends Thread {

    private boolean running;
    private ThreadPoolExecutor threadPoolExecutor;
    private HashMap<SocketAddress, ClientThread> clientThreads;

    public Core() {
        super(MCDashThreadGroup.getInstance(), "Core Thread");
        setRunning(true);
        clientThreads = new HashMap<>(MainClass.CONSTANTS.DEF_SITE_THREADS);
        ThreadFactory threadFactory = r -> new Thread(MCDashThreadGroup.getInstance(), r);
        threadPoolExecutor = new ThreadPoolExecutor(MainClass.CONSTANTS.DEF_SITE_THREADS, MainClass.CONSTANTS.MAX_SITE_THREADS, MainClass.CONSTANTS.SITE_THREAD_KEEP_ALIVE, MainClass.CONSTANTS.SITE_THREAD_KEEP_ALIVE_TIME_UNIT, new ArrayBlockingQueue<>(5), threadFactory);
        new Thread(() -> {
            while (MainClass.isRunning()) {
                while (this.isRunning()) {
                    System.out.println(String.format("[Core Monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated:%s",
                            this.threadPoolExecutor.getPoolSize(),
                            this.threadPoolExecutor.getCorePoolSize(),
                            this.threadPoolExecutor.getActiveCount(),
                            this.threadPoolExecutor.getCompletedTaskCount(),
                            this.threadPoolExecutor.getTaskCount(),
                            this.threadPoolExecutor.isShutdown(),
                            this.threadPoolExecutor.isTerminated()
                    ));
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void run() {
        System.out.println("Starting Core Server!");
        while (MainClass.isRunning()) {
            while (this.isRunning()) {
                try (ServerSocket serverSocket = new ServerSocket(MainClass.CONSTANTS.SITE_PORT, MainClass.CONSTANTS.SITE_BACKLOG, InetAddress.getByAddress(MainClass.CONSTANTS.SITE_BIND_IP))) {
                    while (!serverSocket.isClosed()) {
                        try {
                            Socket client = serverSocket.accept();
                            ClientThread clientThread = new ClientThread(client);
                            clientThreads.put(client.getRemoteSocketAddress(), clientThread);
                            threadPoolExecutor.execute(clientThread);
                        } catch (Exception general) {
                            general.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public synchronized void disconnectClient(SocketAddress socketAddress) {

        if (clientThreads.containsKey(socketAddress)) {
            clientThreads.get(socketAddress).stop();
        }

    }
}

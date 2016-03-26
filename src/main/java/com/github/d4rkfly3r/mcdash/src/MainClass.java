package com.github.d4rkfly3r.mcdash.src;

import com.github.d4rkfly3r.mcdash.src.ajax.AJAXHandler;
import com.github.d4rkfly3r.mcdash.src.core.Core;
import com.github.d4rkfly3r.mcdash.src.plugins.Plugins;

import java.util.concurrent.TimeUnit;

public class MainClass {

    private static boolean running;
    private final Core core;
    private final Plugins plugins;
    private final AJAXHandler ajaxHandler;

    public MainClass() {

        setRunning(true);

        this.core = new Core();
        this.plugins = new Plugins();
        this.ajaxHandler = new AJAXHandler();

        this.core.start();
        this.plugins.start();
        this.ajaxHandler.start();
    }

    public static void main(String[] args) {
        new MainClass();
    }

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        MainClass.running = running;
    }

    public Core getCore() {
        return core;
    }

    public Plugins getPlugins() {
        return plugins;
    }

    public AJAXHandler getAjaxHandler() {
        return ajaxHandler;
    }

    public static class CONSTANTS {
        public static final byte[] SITE_BIND_IP = new byte[]{0, 0, 0, 0};
        public static final int SITE_BACKLOG = 50;
        public static final int SITE_PORT = 80;
        public static final int DEF_SITE_THREADS = 10;
        public static final int MAX_SITE_THREADS = 20;
        public static final long SITE_THREAD_KEEP_ALIVE = 5;
        public static final TimeUnit SITE_THREAD_KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        public static final String SERVER_NAME = "MC Dashboard";
    }
}

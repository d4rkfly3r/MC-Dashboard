package com.github.d4rkfly3r.mcdash.src;

import com.github.d4rkfly3r.mcdash.src.ajax.AJAXHandler;
import com.github.d4rkfly3r.mcdash.src.core.Core;
import com.github.d4rkfly3r.mcdash.src.plugins.Plugins;
import com.github.d4rkfly3r.mcdash.src.util.FileResponse;
import com.github.d4rkfly3r.mcdash.src.util.HTTPHeaderParser;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

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
        public static final String SITE_WORKING_DIR = "site";
        public static final DateTimeFormatter DEF_DATETIME_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
        public static BiConsumer<HTTPHeaderParser, Socket> indexPage = (httpHeaderParser, client) -> {
            try {
                client.getOutputStream().write(new FileResponse("index.html", new HashMap<String, Object>() {{
                    put("date", DEF_DATETIME_FORMAT.format(Instant.now()));
                }}).getData());
                client.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        static {
            try {
                Files.createDirectories(Paths.get(SITE_WORKING_DIR));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

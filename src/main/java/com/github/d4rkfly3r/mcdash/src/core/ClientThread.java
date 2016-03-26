package com.github.d4rkfly3r.mcdash.src.core;

import com.github.d4rkfly3r.mcdash.src.MainClass;
import com.github.d4rkfly3r.mcdash.src.util.HTTPHeaderParser;
import com.github.d4rkfly3r.mcdash.src.util.Routes;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class ClientThread implements Runnable {
    private final Socket client;

    public ClientThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            HTTPHeaderParser headers = new HTTPHeaderParser(client.getInputStream());
            headers.parseRequest();

            if (!Routes.routeAvailable(headers)) {
                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                printWriter.println("HTTP/1.1 404 Not Found");
                printWriter.println("Date: " + new Date().toString());
                printWriter.println("Server: " + MainClass.CONSTANTS.SERVER_NAME);
                printWriter.println("Accept-Ranges: bytes");
                printWriter.println("Content-Type: text/html");
                printWriter.println();
                printWriter.println("<html>");
                printWriter.println("<Title>404 File Not Found</Title>");
                printWriter.println("<body style='background-color: #2A3132;'>");
                printWriter.println("<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>");
                printWriter.println("<div align='center'><center>");
                printWriter.println("<div style='width: 60%;padding: 7px;background-color: #763626;'>");
                printWriter.println("<p align='center'><font color='#FFFFFF' size='6'><strong>404 File Not Found</strong></font></p>");
                printWriter.println("<p><font color='#FFFFFF' size='4'>The Web Server cannot find the requested file or script.  Please check the URL to be sure that it is correct.</font></p>");
                printWriter.println("</div>");
                printWriter.println("</center></div>");
                printWriter.println("</html>");
                printWriter.flush();
            } else if (Routes.isRouteSpecial(headers)) {

            } else {
                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                printWriter.println("HTTP/1.1 202 OK");
                printWriter.println("Date: " + new Date().toString());
                printWriter.println("Server: " + MainClass.CONSTANTS.SERVER_NAME);
                printWriter.println("Accept-Ranges: bytes");
                printWriter.println("Content-Type: text/html");
                printWriter.println();
                printWriter.flush();
                Routes.getRoute(headers).call(headers, client);
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}

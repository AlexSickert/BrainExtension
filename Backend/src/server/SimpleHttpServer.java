package server;

import Log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {
    private int port;
    private HttpServer server;

    public void Start(int port) {
        try {
            this.port = port;
            server = HttpServer.create(new InetSocketAddress(port), 0);
            Log.log("info", "server started at " + port);
            server.createContext("/", new Handlers.RootHandler());
            server.createContext("/echoHeader", new Handlers.EchoHeaderHandler());
            server.createContext("/jsOrCssFile", new Handlers.EchoGetHandler());
            server.createContext("/echoGet", new Handlers.EchoGetHandler());
            server.createContext("/post", new Handlers.EchoPostHandler());
            // Handler for handling file uploads
            server.createContext("/file", new Handlers.FilePostHandler());
            server.createContext("/fileDownload", new Handlers.GetFileDownloadHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Stop() {
        server.stop(0);
        Log.log("info", "server stopped");
    }
}

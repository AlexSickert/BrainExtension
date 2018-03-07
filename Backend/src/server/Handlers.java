package server;

import Log.Log;
import Util.CheckMemory;
import Util.CheckUrl;
import Util.FileActions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import database.BusinessLayerData;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import static server.Main.tmpSession;

public class Handlers {

    static Log Logger = new Log();
    static long memoryTimer = System.nanoTime();


    public static class RootHandler implements HttpHandler {

        //        @Override
//        public void handle(HttpExchange he) throws IOException {
//            Logger.log("info", "in RootHandler");
//            String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + Main.port + "</h1><br><a href='./echoHeader'> link</a>";
//            he.sendResponseHeaders(200, response.length());
//            OutputStream os = he.getResponseBody();
//            os.write(response.getBytes());
//            os.close();
//        }
        @Override
        public void handle(HttpExchange he) throws IOException {
            Logger.log("info", "in RootHandler now streaming main file");

            if (!CheckUrl.checkAddress(he)) {
                Logger.log("info", "not a valid remote address");
                return;
            }

//            String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + Main.port + "</h1><br><a href='./echoHeader'> link</a>";
            FileActions u = new FileActions();
            String response = u.getFileAsString(Main.pathToClientFiles + "template.html");

            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

    }

    public static class EchoHeaderHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            Logger.log("info", "in EchoHeaderHandler");

            if (!CheckUrl.checkAddress(he)) {
                return;
            }

            Headers headers = he.getRequestHeaders();
            Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
            String response = "";
            for (Map.Entry<String, List<String>> entry : entries) {
                response += entry.toString() + "\n";
            }
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }

    /**
     * this is being used for ending the JS files
     */
    public static class EchoGetHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {

            if (!CheckUrl.checkAddress(he)) {
                return;
            }

            // parse request
            Logger.log("info", "in EchoGetHandler");
            Map<String, Object> parameters = new HashMap<String, Object>();
            URI requestedUri = he.getRequestURI();

            Logger.log("info", "he.getLocalAddress().toString() = " + he.getLocalAddress().toString());
            Logger.log("info", "he.getLocalAddress().getRemoteAddress() = " + he.getRemoteAddress().toString());

            Logger.log("info", "requestedUri is " + requestedUri);

            String query = requestedUri.getRawQuery();
            parseQuery(query, parameters);
            // send response
//            String response = "";
//            for (String key : parameters.keySet()) {
//                response += key + " = " + parameters.get(key) + "\n";
//            }
//            
            Logger.log("info", "load file");
            String fileToLoad = (String) parameters.get("file");
            FileActions u = new FileActions();
            String response = u.getFileAsString(Main.pathToClientFiles + fileToLoad);

//            Logger.log("info", response);

            Logger.log("info", "prepare response");

            he.setAttribute("Content-Type", "application/javascript");

            he.sendResponseHeaders(200, response.length());
            // he.setAttribute("Content-Type", "application/javascript");           

            OutputStream os = he.getResponseBody();
            Logger.log("info", "write response");
            os.write(response.toString().getBytes());
            os.close();
            Logger.log("info", "response sent");
            Logger.log("info", "------------------------------------------");
        }

    }

    /**
     * this one is used when downloading a file from the tree etc.
     */
    public static class GetFileDownloadHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {

            // parse request
            Logger.log("info", "in GetFileDownloadHandler");

            if (!CheckUrl.checkAddress(he)) {
                return;
            }

            Map<String, Object> parameters = new HashMap<String, Object>();
            URI requestedUri = he.getRequestURI();
            String query = requestedUri.getRawQuery();
            parseQuery(query, parameters);
            // send response
//            String response = "";
//            for (String key : parameters.keySet()) {
//                response += key + " = " + parameters.get(key) + "\n";
//            }
//            
//check sessiion
            String session = (String) parameters.get("s");

            if (session.equals(tmpSession)) {
                if (parameters.get("c").equals("tree")) {

                    //get path from node
                    String node = (String) parameters.get("id");
                    String name = (String) parameters.get("name");
                    BusinessLayerData bl = new BusinessLayerData();
                    String fileToLoad = bl.getTreeFileDownloadPath(node, name);

                    FileActions u = new FileActions();
//                    String response = u.getFileAsString(fileToLoad);
                    byte[] fileContent = u.getFileAsBinaryArray(fileToLoad);

                    //image/png
//                    he.setAttribute("Content-Type", "application/javascript");
//                    he.setAttribute("Content-Type", "application/octet-stream; name=\"" + name + "\"");
//                    he.sendResponseHeaders(200, response.length());
                    // he.setAttribute("Content-Type", "application/javascript"); 
                    he.setAttribute("Content-Type", "application/octet-stream");

                    he.getResponseHeaders().add("Content-Disposition", "attachment; filename=\"" + name + "\"");
//                    he.setAttribute("Content-Disposition", "attachment; filename=\"" + name + "\"");

                    he.sendResponseHeaders(200, fileContent.length);

                    OutputStream os = he.getResponseBody();
                    os.write(fileContent);
                    os.flush();
                    os.close();

                } else {
                    Log.info("category is not tree: " + parameters.get("c"));
                }

            } else {
                Log.info("wrong session: " + session);
            }

        }

    }

    public static class FilePostHandler implements HttpHandler {

        @Override

        public void handle(HttpExchange he) throws IOException {
            String fileContent = "";
            String queryLine = "";
            Logger.log("info", "in FilePostHandler");

            if (!CheckUrl.checkAddress(he)) {
                return;
            }

            try {

                InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");

//                InputStream ir = he.getRequestBody();
                BufferedReader br = new BufferedReader(isr);
//                boolean startFile = false;
//                boolean recordingFile = false;

                boolean isDelimiteLine = false;
                int lineCount = 0;
                String delimiter = "";
                boolean inHeader = false;
                String header = "";

                while ((queryLine = br.readLine()) != null) {
                    lineCount = lineCount + 1;

                    // first line is delimiter
                    // first time after delimiter when zero sting, then start of file
                    if (lineCount == 1) {
                        delimiter = queryLine;
                        Logger.log("info", "delimiter is " + delimiter);
                        Logger.log("info", "-----------------------------------");
//                        isDelimiteLine = true;
                        inHeader = true;
                    } else if (queryLine.contains(delimiter)) {
                        // we are either at the beginning of a block or 
                        // at the end of a block
                        if (!inHeader) {
                            // this means if we are currently recording body
                            // and at same time we have delimiter
                            // then we are at end of recoding file 

                            // save the file
                            Logger.log("info", "file is " + fileContent);

                            String tmpName = "file-" + lineCount + ".png";

                            byte[] array = Charset.forName("utf-8").encode(CharBuffer.wrap(fileContent)).array();

                            FileActions fa = new FileActions();
                            fa.saveFile(tmpName, "C:\\Users\\Alex Sickert\\Pictures\\", array);

                            // reset file content to nothing
                            fileContent = "";

                            // and we are in header again
                            inHeader = true;
                            Logger.log("info", "-----------------------------------");
                        }
                    } else if (inHeader) {
                        if (queryLine.length() == 0) {
                            // we reached the end of the header and siwtch to body
                            inHeader = false;
                            Logger.log("info", "header is " + header);
                            header = "";
                        } else {
                            // now we need to record the header
                            header += queryLine;
                        }

                    } else {
                        // if we are in body
                        fileContent += queryLine;
                    } // if not in delimiter line // linke count = 1
                } // end of while loop

                // dump the last contnetn
//                if (!inHeader) {
//                    if (fileContent.length() > 501) {
//                        Logger.log("info", "file is " + fileContent.substring(1, 500));
//                    } else {
//                        Logger.log("info", "file is " + fileContent);
//                    }
//                }
                //Logger.log("info", query);
            } catch (Exception x) {

                Logger.log("error", x.getMessage());
                Logger.log("error", x.toString());

            } // end of try catch
        } // end of method

    } //end of class

    public static class EchoPostHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {

            long startTime = System.nanoTime();

            Logger.log("info", "in EchoPostHandler");

            if (!CheckUrl.checkAddress(he)) {
                return;
            }

            //System.out.println("Served by /echoPost handler...");
            // parse request
//            Map<String, Object> parameters = new HashMap<String, Object>();
            InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
//            String queryLine = br.readLine();
            String query = "";
            String queryLine = "";

            while ((queryLine = br.readLine()) != null) {
//                System.out.println(queryLine);
                query += queryLine;
            }

            Logger.log("info", "================= decoding ===============");
            query = URLDecoder.decode(query, System.getProperty("file.encoding"));

//            Logger.log("info", query);

            Logger.log("info", "================= make object ===============");

            Gson g = new Gson();
            JsonRequest jasonReq = new JsonRequest();
            try {
                jasonReq = g.fromJson(query, JsonRequest.class);
            } catch (Exception x) {
                Logger.log("error", x.getMessage());
                Logger.log("error", x.toString());
            }

            Logger.log("info", "================= access parameters  ===============");

            try {
                Logger.log("info  p = ", jasonReq.p);
                Logger.log("info  s = ", jasonReq.s);
                Logger.log("info  m = ", jasonReq.m);
                Logger.log("info  v = ", Boolean.toString(jasonReq.v));
                Logger.log("info  e = ", jasonReq.e);
                Logger.log("info  c = ", jasonReq.c);
                Logger.log("info  t = ", jasonReq.t);
                Logger.log("info  idnode = ", jasonReq.idNode);
                Logger.log("info  idhtml = ", jasonReq.idHtml);
//                Logger.log("info  r = ", login.r.toString());

            } catch (Exception x) {
                Logger.log("error", x.getMessage());
                Logger.log("error", x.toString());
                x.printStackTrace();
            }

            Logger.log("info", "================= convert to json   ===============");
//            System.out.println(g.toJson(login));
            JsonRequestHandler h = new JsonRequestHandler();

            Logger.log("info", "session: " + jasonReq.s);
            Logger.log("info", "error status: " + jasonReq.e);  // should be status OK otherwise we have problem 

//             JsonRequestHandler h = new JsonRequestHandler();
            Logger.log("info", "================= create response  ===============");
            String response = new String();
            try {

                jasonReq = h.handle(jasonReq);

                Logger.log("info  p = ", jasonReq.p);
                Logger.log("info  s = ", jasonReq.s);
                Logger.log("info  m = ", jasonReq.m);
                Logger.log("info  v = ", Boolean.toString(jasonReq.v));
                Logger.log("info  e = ", jasonReq.e);
                Logger.log("info  c = ", jasonReq.c);
                Logger.log("info  t = ", jasonReq.t);
                Logger.log("info  idnode = ", jasonReq.idNode);
                Logger.log("info  idhtml = ", jasonReq.idHtml);
                //Logger.log("info  r = ", login.r.toString());

                response = g.toJson(jasonReq);

//                Logger.log("info  JSON object for client is ", response.toString());

            } catch (Exception x) {
                x.printStackTrace();
                Logger.log("error", x.getMessage());
                Logger.log("error", x.toString());
            } finally {

                Logger.log("info", "================= done    ===============");

//            Person person = g.fromJson("{\"name\": \"John\"}", Person.class);
//            System.out.println(person.name); //John
//
//            System.out.println(g.toJson(person)); // {"name":"John"}
                // convert JSON  to java object 
//            parseQuery(query, parameters);
//            // send response
//            for (String key : parameters.keySet()) {
//                response += key + " = " + parameters.get(key) + "\n";
//            }
//                he.sendResponseHeaders(200, response.length());
                //s.getBytes("utf8").length
                he.sendResponseHeaders(200, response.getBytes("utf8").length);

                he.setAttribute(query, br);

                OutputStream os = he.getResponseBody();
                os.write(response.toString().getBytes());
                os.close();

                long elapsedTime = System.nanoTime() - startTime;
                double seconds = (double) elapsedTime / 1000000000.0;
                Logger.log("info", "handling the request in handle(HttpExchange he)  took in seconds: " + seconds);

                // check status of memory - needed as we sometimes have problems with crashes... 


                if (System.nanoTime() - memoryTimer > 20000) {
                    CheckMemory.dumpStatus();
                    memoryTimer = System.nanoTime();
                }


            }

        }
    }

    @SuppressWarnings("unchecked")
    public static void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {

        Logger.log("info", "in parseQuery");
        if (query != null) {
            String pairs[] = query.split("[&]");

            for (String pair : pairs) {
                String param[] = pair.split("[=]");

                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
                }

                if (param.length > 1) {
                    value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
                }

                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        List<String> values = (List<String>) obj;
                        values.add(value);
                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<String>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }
}

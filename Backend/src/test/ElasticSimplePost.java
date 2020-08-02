package test;


import java.nio.*;
import java.net.URLEncoder;
import java.net.*;
import java.net.URL.*;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;


public class ElasticSimplePost {

    public static void main(String[] args) {

        try {
            // Construct data
//            String data = URLEncoder.encode("v_u", "UTF-8") + "=" + URLEncoder.encode("haselmax", "UTF-8");
//            data += "&" + URLEncoder.encode("v_p", "UTF-8") + "=" + URLEncoder.encode("didhvn74", "UTF-8");
//            data += "&" + URLEncoder.encode("v_x", "UTF-8") + "=" + URLEncoder.encode("asdfasdf", "UTF-8");

            String data = URLEncoder.encode("{\"query\": {\"match_all\":{}}}", "UTF-8");

            data = "{\"query\": {\"match_all\":{}}}";

            System.out.println("preparing url: " + data);


            // Send data

//            URL url = new URL("http://www.alex-admin.net/alex-admin/live/perl/admin/index.pl");
//            URL url = new URL("http://localhost:9200");

            URL url = new URL("http://localhost:9200/_count?pretty");


//            URL url = convertToURLEscapingIllegalCharacters("http://localhost:9200");
//            System.out.println("preparing url: " + url.getQuery());


            //URL url = new URL("localhost:9200");
            // PROXY

           /* Properties systemSettings = System.getProperties();
            systemSettings.put("http.proxyHost", "proxyz.hypovereinsbank.de");
            systemSettings.put("http.proxyPort", "80");*/


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");


//            URLConnection conn = url.openConnection();
//            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoOutput(true);


//        conn.getRequestProperty(data);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            System.out.println("------------- start reading -----------------");
            while ((line = rd.readLine()) != null) {
                // Process line...
                System.out.println("Reading line: " + line);
            }
            System.out.println("------------- end reading -----------------");
            wr.close();
            rd.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }

    }




    private static URL convertToURLEscapingIllegalCharacters(String string) {
        try {
            String decodedURL = URLDecoder.decode(string, "UTF-8");
            URL url = new URL(decodedURL);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            return uri.toURL();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}




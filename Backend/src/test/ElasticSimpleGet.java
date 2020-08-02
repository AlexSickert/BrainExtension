package test;

import java.io.IOException;
import java.net.*;
import java.security.cert.Certificate;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class ElasticSimpleGet {


    static String tmpError = "";



    public static void main(String[] args) {


        getString("http://localhost:9200");

    }




    private static String getString(String urlStr) {
        String r = "";
//        Main.tryAgain = false;
        boolean hasError = false;
        try {

//            Properties systemSettings = System.getProperties();

//            systemSettings.put("http.proxyHost", "172.17.1.5");
//            systemSettings.put("http.proxyPort", "3128");

//            URI uri = new URI(urlStr);
//
//            URL u = new URL(uri.toASCIIString());
            System.out.println("preparing url: " + urlStr);
            URL u = convertToURLEscapingIllegalCharacters(urlStr);
            System.out.println("preparing url: " + u.getQuery());

            //URL u = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();

            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//            con.setRequestProperty("Expect", "100-continue");

//            HttpsURLConnection con = (HttpsURLConnection) u.openConnection();

            con.setReadTimeout(1000);
            con.setConnectTimeout(1000);

//            printHttpsCert(con);

            //
            // it's not the greatest idea to use a sun.misc.* class
            // Sun strongly advises not to use them since they can
            // change or go away in a future release so beware.
            //
//      sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//      String encodedUserPwd = encoder.encode("700019287012:020374".getBytes());
//      con.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
            // PROXY ----------

//      byte [] b = new byte[1];

//      DataInputStream di = new DataInputStream(con.getInputStream());

            System.out.println("reading from  url...");

//      int counter = 0;
//
//      while(-1 != di.read(b,0,1)) {
//         //System.out.print(new String(b));
//         r += new String(b);
//
//         if(counter == 1000){
//             counter = 0;
//             System.out.println("length: " + r.length());
//         }else{
//             counter += 1;
//         }
//      }
//
//      System.out.println("finished reading from  url");


//        ###########################

            r = new Scanner(con.getInputStream()).useDelimiter("\\Z").next();


            System.out.println("reading from  url finished");
//        String r = new Scanner( u.openStream() ).useDelimiter( "\\Z" ).next();


            // r = new Scanner( u.openStream() ).useDelimiter( "\\Z" ).next();
            //put all in one string
        System.out.println( r );

        } catch (Exception x) {
            System.out.println(x.toString());
            hasError = true;

            if (x.toString().contains("java.util.NoSuchElementException")) {
                hasError = false;
                tmpError = x.toString();
            }

            if (x.toString().contains("java.io.IOException: Server returned HTTP response code: 500 for URL")) {
                hasError = false;
                tmpError = x.toString();
            }


            if (x.toString().contains("java.net.UnknownHostException")) {

                // java.net.UnknownHostException
                System.out.println("error");

            }

            if (x.toString().contains("java.net.SocketTimeoutException")) {

                // java.net.UnknownHostException
                System.out.println("error");

            }


            if (x.toString().contains("java.net.ConnectException")) {

                // java.net.UnknownHostException
                System.out.println("error");
            }
            // java.net.NoRouteToHostException

            if (x.toString().contains("java.net.NoRouteToHostException")) {

                // java.net.UnknownHostException
                System.out.println("error");

            }

            if (hasError) {
                // nothing
                System.out.println("error");
            }
            hasError = false;
        }
        return r;
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

package server;

import Log.Log;
import Util.CheckUrl;
import database.DataAccess;
import database.SessionHandler;

import java.util.HashMap;

public class Main {

    public static int port = 80;
    //    public static Log log = new Log();
    public static String pathToClientFiles;
    public static String pathToUploadFiles;
    public static String dbUser;
    public static String dbPass;
    public static String dbName;
    public static String dbConnString;
    public static String acceptedAddress;
    public static String pathFragmentLegacy;
    public static HashMap userMap;

    //public static String tmpSession = "";  // this is just for temporary usage !!!!

    public static SessionHandler sessHan = new SessionHandler();




    public static void main(String[] args) {

//        CheckUrl.checkUrl();

        String path;

        if (args.length < 1) {
            System.out.println("error: no path provided");
            System.out.println("no path provided - I will use './'");
            path = "./";
        } else {

            path = args[0];

            if (path.equals(null) || path.equals("")) {
                System.out.println("no path provided - I will use '.'");
                path = "./";

            } else {
                System.out.println("path: " + path);
            }
        }

        XmlConfigManager config = new XmlConfigManager(path + "BrainExtensionServerConfig.xml");

        sessHan.loadFromFile();

        pathToClientFiles = config.getValue("pathToClientFiles");
        pathToUploadFiles = config.getValue("pathToUploadFiles");
        dbUser = config.getValue("dbUser");
        dbPass = config.getValue("dbPass");
        dbName = config.getValue("dbName");
        pathFragmentLegacy = config.getValue("pathFragmentLegacy");

        acceptedAddress = config.getValue("acceptedAddress");

        userMap = config.getValueMap("user", "user_name", "user_pass");


        Log.set(true, true, true, true);

        port = Integer.parseInt(config.getValue("port"));

        //     jdbc:mysql://localhost:3306/
        dbConnString = config.getValue("dbConnString");

        //test db
        DataAccess con = new DataAccess();

//        con.test();

        // start http server
//        SimpleHttpServer httpServer = new SimpleHttpServer();
//        httpServer.Start(port);

        // start https server
		SimpleHttpsServer httpsServer = new SimpleHttpsServer();
		httpsServer.Start(port);



//		System.out.println(System.getProperty("user.dir"));
//		System.out.println(Main.class.getClassLoader().getResource("").getPath());
    }
}

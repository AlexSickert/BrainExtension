package database;

import Util.RandomString;

import java.io.*;
import Log.Log;

public class SessionHandler {

    private  Session s = new Session();
    private  String fileName = "BrainExtensionServerSessions.ser";

    // load existing sessions from file system
    public  void loadFromFile(){

        // load from serialized
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            s = (Session) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            s = new Session();
            Log.error("Session IOException exception");
        } catch (ClassNotFoundException c) {
            Log.error("Session class not found");
            c.printStackTrace();
            s = new Session();
        }

        serialize();


    }

    private  void serialize(){

        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(s);
            out.close();
            fileOut.close();
            Log.info("Serialized data is saved in " + fileName);
        } catch (IOException i) {
            i.printStackTrace();
            Log.error("error in SessionHandler.serialize()");
        }
    }

    // get a session and store the status quo in the file
    public  String get_session(){

        String rs = RandomString.getString(25);
        s.map.put(rs,rs);
        serialize();
        return rs;
    }

    public  boolean checkSession(String sess){

        if(s.map.containsKey(sess)){
            return true;
        }else{
            return false;
        }
    }


    // delete all sessions and start from zero
    public void clear_all(){

        s.map.clear();
        serialize();
    }

}

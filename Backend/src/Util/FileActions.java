/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Log.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import server.Main;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author xandi
 */
public class FileActions {

    /**
     * Read the given binary file, and return its contents as a byte array.
     *
     */
    public byte[] getFileAsBinaryArray(String aInputFileName) {
        System.out.println("Reading in binary file named : " + aInputFileName);
        File file = new File(aInputFileName);
        System.out.println("File size: " + file.length());
        byte[] result = new byte[(int) file.length()];
        try {
            InputStream input = null;
            try {
                int totalBytesRead = 0;
                input = new BufferedInputStream(new FileInputStream(file));
                while (totalBytesRead < result.length) {
                    int bytesRemaining = result.length - totalBytesRead;
                    //input.read() returns -1, 0, or more :
                    int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
                    if (bytesRead > 0) {
                        totalBytesRead = totalBytesRead + bytesRead;
                    }
                }
                /*
                 the above style is a bit tricky: it places bytes into the 'result' array; 
                 'result' is an output parameter;
                 the while loop usually has a single iteration only.
                 */
                System.out.println("Num bytes read: " + totalBytesRead);
            } finally {
                System.out.println("Closing input stream.");
                input.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return result;
    }

    public String getFileAsString(String p) {

        try (BufferedReader br = new BufferedReader(new FileReader(p))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            return everything;
        } catch (Exception e) {
            System.out.println("Erro reading file: " + e);
        }

        return null;

    }

    public void saveFile(String name, String path, byte[] content) {
        try {
            FileOutputStream fos = new FileOutputStream(path + name);
            fos.write(content);
            fos.close();
            System.out.println("saveFile done for fie " + path + name);
        } catch (Exception x) {
            System.out.println("ERROR in saveFile(String name, String path, byte[] content) : " + x.getMessage());

        }

    }

    /*
     * uses the base Path from main and the prepares a path for a cpecific id
     this implementaiton is very bad and not safe
     */
    public String makeValidPath(String forId) {

        // put new stuff in a version2 folder to separate it from old stuff
        String pathFragment = java.nio.file.FileSystems.getDefault().getSeparator() + "version2";
        FileActions.makeDir(Main.pathToUploadFiles + pathFragment);

        // first dimension
        int tmpInt;
        tmpInt = ThreadLocalRandom.current().nextInt(0, 10 + 1);
        pathFragment += java.nio.file.FileSystems.getDefault().getSeparator() + Integer.toString(tmpInt);
        FileActions.makeDir(Main.pathToUploadFiles + pathFragment);

        // second dimension
        tmpInt = ThreadLocalRandom.current().nextInt(0, 10 + 1);
        pathFragment += java.nio.file.FileSystems.getDefault().getSeparator() + Integer.toString(tmpInt);
        FileActions.makeDir(Main.pathToUploadFiles + pathFragment);

        //no make the final foler
        pathFragment += java.nio.file.FileSystems.getDefault().getSeparator() + forId;
        FileActions.makeDir(Main.pathToUploadFiles + pathFragment);

        Log.log("info", "made path: " + Main.pathToUploadFiles + pathFragment);

        return pathFragment;

    }

    ;
    
    /*
    this implementaiton is very bad and not safe
    */
    public static void makeDir(String s) {

        File d = new File(s);

        if (!d.exists()) {
            d.mkdir();
        }

    }

    public static ArrayList getFiles(String path) {
        Log.log("info", "getting files for: " + path);
        ArrayList arl = new ArrayList();

        File dir = new File(path);

        String[] children = dir.list();

        if (children == null) {
            return arl;
        } else {
            for (int i = 0; i < children.length; i++) {
                String filename = children[i];

                File child = new File(path + java.nio.file.FileSystems.getDefault().getSeparator() + filename);
                if (child.exists()) {
                    if (!child.isDirectory()) {
                        arl.add(filename);
                    }
                }
            }
            return arl;
        }
    }

}

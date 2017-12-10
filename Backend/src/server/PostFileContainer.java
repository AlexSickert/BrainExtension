/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.HashMap;

/**
 * @author Alex Sickert
 */
public class PostFileContainer {

    HashMap mHead = new HashMap();
    HashMap mFile = new HashMap();


    /*
     * Add a string for the Header
     * we will get the whole header in one line of string
     */
    public void addHeaderRow(String id, String s) {
        String tmp;
        if (mHead.containsKey(id)) {
            tmp = (String) mHead.get(id);
        } else {
            tmp = "";
        }
        tmp += " " + s;
        mHead.put(id, tmp);
    }

    /*
    add a line of string for a file
     */
    public void addFileRow(String id, String s) {
        String tmp;
        if (mFile.containsKey(id)) {
            tmp = (String) mFile.get(id);
        } else {
            tmp = "";
        }
        tmp += s;
        mFile.put(id, tmp);
    }

    public int getFileCount() {
        return 1;
    }

    public void saveFile(int i, String Name, String path) {

    }

    public String getFileName(int i) {
        return "";
    }

    private String getMiddlePart(String startString, String endString) {
        return "";
    }

}

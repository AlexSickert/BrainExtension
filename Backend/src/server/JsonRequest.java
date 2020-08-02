/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author xandi
 */
public class JsonRequest {

    public String u = "";  // user
    public String p = "";  // password 

    public String s = "";  // session
    public String m = "";  // module
    public Boolean v = false;  // editable or not
    public String value = "";  // value of a field, used for update
    public String e = "";  // error status
    public String c = "";  // category - it is a sub-element of module m
    public String d = "";  // detail

    public String t = "";  // title of tree node
    public String f = "";  // field of a table or a recordset
    public String i = "";  // info string to pass through messages

    public String idNode = "";
    public String idParent = ""; // used when moving nodes or linking nodes
    
    public String idHtml = "";
    
    public String search = ""; // string we search for 

    public ArrayList r = new ArrayList();  // this is where the reponse gets stored

    public ArrayList fileNames = new ArrayList();
    public HashMap filesHashMap = new HashMap();

}

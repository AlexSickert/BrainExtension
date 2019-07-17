/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Log.Log;
import Util.RandomString;
import database.BusinessLayerData;

import static server.Handlers.Logger;
//import static server.Main.tmpSession;

/**
 * @author xandi
 */
public class JsonRequestHandler {

    BusinessLayerData bld = new BusinessLayerData();

    public JsonRequest handle(JsonRequest processObj) {
        Log.log("info", "in JsonRequest handle(JsonRequest processObj)  ");
        try {
            if (processObj.m.matches("l")) {
                Log.log("info", "processObj.m is matching l ... " + processObj.m);
                // check login
                Log.log("info", "checking login");

                boolean logInOk = false;

                if(Main.userMap.containsKey(processObj.u)){
                    String pass = (String) Main.userMap.get(processObj.u);
                    if(processObj.p.matches(pass)){
                        logInOk = true;
                    }else{
                        Log.log("info", "password wrong: " + processObj.p);
                    }
                }else{
                    Log.log("info", "user does not exist: " + processObj.u);
                }

                if (logInOk) {
                    processObj.e = "OK";

                    processObj.s = Main.sessHan.get_session();

                } else {
                    processObj.e = "Error: login failed";
                }
            } else {
                // check session
                Log.log("info", "checking session");
                Log.log("info", "session is: " + processObj.s);

                if (Main.sessHan.checkSession(processObj.s)) {

                    // her we process everything else
                    Log.log("info", "processObj.m: " + processObj.m);

                    if (processObj.m.matches("t")) {  // t stands for module TREE

                        Log.log("info", "we are in if (processObj.m.matches(\"t\")) { ");

                        // get one individual node
                        if (processObj.c.trim().matches("node")) {
                            Log.log("info", "get one node");
                            Log.log("info", "processObj.c = " + processObj);
                            processObj = bld.getNode(processObj);
                        }

                        if (processObj.c.matches("update")) {
                            Log.log("info", "update node");
                            Log.log("info", "processObj.c = " + processObj);
                            processObj = bld.updateNode(processObj);
                        }

                        // get root Nodes
                        if (processObj.c.matches("rootNodes")) {
                            Log.log("info", "getting rootNodes");
                            processObj = bld.getChildNodes("0", processObj);
                        }

                        // get nodes by a serach criteria
                        if (processObj.c.matches("searchNodes")) {
                            Log.log("info", "getting searchNodes");
                            processObj = bld.getSearchNodes(processObj.search, processObj);
                        }

                        // get child nodes  childNodes
                        if (processObj.c.matches("childNodes")) {
                            Log.log("info", "getting childNodes");
                            processObj = bld.getChildNodes(processObj);
                        }

                        // get partent nodes for bread crumb
                        if (processObj.c.matches("parentNodes")) {
                            Log.log("info", "getting parentNodes");
                            processObj = bld.getAnchestors(processObj);
                        }

                        // create new child and return full child list
                        if (processObj.c.matches("makeChildNodes")) {
                            Log.log("info", "getting childNodes");
                            processObj = bld.makeChildNodes(processObj);
                        }

                        // upload files
                        if (processObj.c.matches("file-upload")) {
                            Log.log("info", "uploading file");
                            processObj = bld.uploadFile(null, processObj);
                        }

                        if (processObj.c.matches("file-list")) {
                            Log.log("info", "get file list");
                            processObj = bld.getFileList(null, processObj);
                        }

                        // move-or-link
                        if (processObj.c.matches("move-or-link")) {
                            Log.log("info", "get file list");
                            processObj = bld.moveOrLinkId(processObj);
                        }

                        // next things are: make new node and update node
                        // problem is that when switching from collapsed 
                        // to expanded, the div tag javascript stays active
                        // therefore we need to access element.parent... 
                    } else {
                        Log.log("error", "so far unhandled output of parameter m");
                    }

                    processObj.e = "OK";
                } else {
                    processObj.e = "Error: Invalid session. " + processObj.s;
                }
            }
            return processObj;
        } catch (Exception x) {
            Logger.log("error", "JsonRequest handle(JsonRequest processObj)  " + x.getMessage());
            x.printStackTrace();

            processObj.e = "ERROR";
            return processObj;
        }
    }
}

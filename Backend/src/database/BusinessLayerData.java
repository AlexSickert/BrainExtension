/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Log.Log;
import Util.FileActions;
import Util.StringCorrection;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import server.JsonRequest;
import server.Main;
//import org.apache.commons.codec.binary.Base64;
//
//import java.util.Base64;

/**
 *
 * @author xandi
 */
public class BusinessLayerData {

    public JsonRequest getNode(JsonRequest j) {

        Log.log("info", " in JsonRequest getNode(JsonRequest j) { ");

        try {
            DataAccess da = new DataAccess();
            ResultSet rs = da.getResultSet("SELECT * FROM " + Main.dbName + ".nodes where id=" + j.idNode);
            Log.log("info", "SQL = " + "SELECT * FROM " + Main.dbName + ".nodes where id=" + j.idNode);

            j.r = new ArrayList();

            while (rs.next()) {

                // only test data
                HashMap m = new HashMap();
                m.put("id", rs.getInt("id"));
                m.put("title", StringCorrection.cleanStringForDisplay(rs.getString("title")));
                m.put("content", StringCorrection.cleanStringForDisplay(rs.getString("content")));
                m.put("files", "files");
                m.put("tags", "tags tags tags ");
                m.put("children", "children children ");

                j.r.add(m);

            }

            rs.close();
            da.closeConn();

        } catch (Exception x) {
            Log.log("error", x.getMessage());
            x.printStackTrace(System.out);
            j.e = "Error";
            j.r = null;
            return j;
        } finally {
            return j;
        }

    }

    /*
     *  executes two SQL statement - one for the history of the node and one 
     *to save the latest status 
     */
    public JsonRequest updateNode(JsonRequest j) {

        j.i = "updated node " + j.idNode + " field = " + j.f + " with value = " + j.v;
        j.r = new ArrayList();

        String sql = "";

        sql += " INSERT INTO `" + Main.dbName + "`.`changes` (`modul`, `section`, `field`, `content`) ";
        sql += "VALUES (";
        sql += "'" + j.idNode + "', ";
        sql += "'" + j.idNode + "', ";
        sql += "'" + j.f + "', ";
        sql += "'" + StringCorrection.cleanStringForSql(j.value) + "'";
        sql += ");";

        Log.log("info", "SQL statement is: " + sql);

        String sql2 = "";

        sql2 += "UPDATE `nodes` SET " + j.f + " = '" + StringCorrection.cleanStringForSql(j.value) + "' WHERE id=" + j.idNode;
        Log.log("info", "SQL statement is: " + sql2);

        try {
            DataAccess da = new DataAccess();
            da.executeStatement(sql);
            da.executeStatement(sql2);

        } catch (Exception x) {
            Log.log("error", x.getMessage());
            x.printStackTrace(System.out);
            j.e = "Error";
            j.r = null;
            return j;
        } finally {
            return j;
        }
    }

    public JsonRequest getChildNodes(String parent, JsonRequest j) {

        try {

            long startTime = System.nanoTime();

            DataAccess da = new DataAccess();

            Log.info("getting nodes for parent id: " + parent);

            ResultSet rs = da.getResultSet(" SELECT id, title, content FROM " + Main.dbName + ".nodes where id in (select child_node from " + Main.dbName + ".tree where parent_node = " + parent + ");");
            j.r = new ArrayList();

            while (rs.next()) {
                // only test data
                HashMap m = new HashMap();
                m.put("id", rs.getInt("id"));
                m.put("title", StringCorrection.cleanStringForDisplay(rs.getString("title")));
                m.put("content", StringCorrection.cleanStringForDisplay(rs.getString("content")));
                m.put("files", "files");
                m.put("tags", "tags tags tags ");
                m.put("children", "children children ");
                j.r.add(m);
            }

            rs.close();
            da.closeConn();

            long elapsedTime = System.nanoTime() - startTime;
            double seconds = (double) elapsedTime / 1000000000.0;
            Log.log("info", "getRootNodes(String parent, JsonRequest j) took in seconds: " + seconds);

        } catch (Exception x) {
            Log.log("error", x.getMessage());
            x.printStackTrace(System.out);
            j.e = "Error";
            j.r = null;
            return j;
        } finally {
            return j;
        }

    }

    private String makeSqlSearchClaus(String s) {

        String sql = "";
        String[] searchArr = s.split(" ");

        if (searchArr.length > 1) {
            for (int i = 0; i < searchArr.length; i++) {
                sql += " ( title like '%" + searchArr[i].trim() + "%' or content like '%" + searchArr[i].trim() + "%' ) ";
                if (i < searchArr.length - 1) {
                    sql += " and ";
                }
            }
            sql += "  limit 0, 250; ";

        } else {
            sql = " title like '%" + s.trim() + "%' or content like '%" + s.trim() + "%' limit 0, 250; ";
        }

        Log.info("sql statement for SEARCH is: " + sql);

        return sql;
    }

    public JsonRequest getAnchestors(JsonRequest j) {

        String tst = j.idNode;

        Boolean cont = true;
        j.r = new ArrayList(); // replace or add for the first time an ArrayList to the response object
        int depth = 0;

        while (cont) {
            depth += 1;

            HashMap m = new HashMap();

            tst = getParent(tst, m);

            if (tst != null) {
                if (tst.equals("")) {
                    cont = false;
                } else {
                    j.r.add(m);
                }
            }

            if (depth > 20) {
                cont = false;
            }

        }
        return j;

    }

    private String getParent(String x, HashMap m) {

        String ret = "";
        Boolean tst;

        try {
            long startTime = System.nanoTime();
            DataAccess da = new DataAccess();

            Log.info("getting parent node for id: " + x);

//            ResultSet rs = da.getResultSet(" SELECT id, title, content FROM " + Main.dbName + ".nodes where LENGTH(trim(title)) > 0 and id in (select child_node from " + Main.dbName + ".tree where  parent_node = " + j.idNode + ") ORDER BY title;");
            ResultSet rs = da.getResultSet(" SELECT id, title FROM " + Main.dbName + ".nodes where LENGTH(trim(title)) > 0 and id in (select parent_node from " + Main.dbName + ".tree where  child_node = " + x + ") ORDER BY title;");

            tst = rs.next(); // we only take the first value
            // check if recordset is emplty
            if (tst) {
                ret = Integer.toString(rs.getInt("id"));
                m.put("id", rs.getInt("id"));
                m.put("title", StringCorrection.cleanStringForDisplay(rs.getString("title")));
            }

            rs.close();
            da.closeConn();

            long elapsedTime = System.nanoTime() - startTime;
            double seconds = (double) elapsedTime / 1000000.0;
            Log.log("info", "getParent(String x, HashMap m) took in milli seconds: " + seconds);

        } catch (Exception e) {
            Log.log("error", e.getMessage());
            e.printStackTrace(System.out);

            return ret;
        } finally {
            return ret;
        }

    }

    public JsonRequest getSearchNodes(String search, JsonRequest j) {

        String sql = "";

        try {
            DataAccess da = new DataAccess();

            sql += " SELECT id, title, content FROM " + Main.dbName + ".nodes where  " + this.makeSqlSearchClaus(search);

            Log.info("getting nodes for search string: " + search);
            Log.info("sql statement is: " + sql);

            ResultSet rs = da.getResultSet(sql);
            j.r = new ArrayList(); // replace or add for the first time an ArrayList to the response object

            while (rs.next()) {
                // only test data
                HashMap m = new HashMap();
                m.put("id", rs.getInt("id"));
                m.put("title", StringCorrection.cleanStringForDisplay(rs.getString("title")));
//                m.put("content", rs.getString("content"));
                m.put("content", "");
                m.put("files", "files");
                m.put("tags", "tags tags tags ");
                m.put("children", "children children ");
                j.r.add(m);
            }

            rs.close();
            da.closeConn();

        } catch (Exception x) {
            Log.log("error", x.getMessage());
            x.printStackTrace(System.out);
            j.e = "Error";
            j.r = null;
            return j;
        } finally {
            return j;
        }

    }

    public JsonRequest makeChildNodes(JsonRequest j) {

        //first make a child and then call getChildNodes
        String newNodeid = this.makeNode();
        // register new node in tree
        Log.info("adding new child node for parent " + j.idNode);

        this.setIdsInTree(j.idNode, newNodeid);

        // now we can reload all children
        return this.getChildNodes(j);
    }

    private void setIdsInTree(String parentId, String childId) {

        String sql = "INSERT INTO `" + Main.dbName + "`.`tree` (`parent_node`, `child_node`) VALUES ('" + parentId + "', '" + childId + "');";

        this.executeSql(sql);
    }

    private void executeSql(String sql) {

        try {
            DataAccess da = new DataAccess();
            da.executeStatement(sql);

        } catch (Exception x) {
            Log.log("error", x.getMessage());
            x.printStackTrace(System.out);

        }
    }

    // makes a node and returns its ID
    private String makeNode() {

        String sql = "INSERT INTO `" + Main.dbName + "`.`nodes` (`title`, `content`) VALUES ('new', 'new');";

        String sql2 = "SELECT max(id) as id from  `" + Main.dbName + "`.`nodes` where title = 'new' and content = 'new';";
        String ret = "";

        try {
            DataAccess da = new DataAccess();
            da.executeStatement(sql);
            ResultSet rs = da.getResultSet(sql2);

            while (rs.next()) {
                ret = Integer.toString(rs.getInt("id"));
            }
            rs.close();

        } catch (Exception x) {
            Log.log("error", x.getMessage());
            x.printStackTrace(System.out);

            return ret;
        } finally {
            return ret;
        }
    }

    public JsonRequest getChildNodes(JsonRequest j) {

        try {
            long startTime = System.nanoTime();
            DataAccess da = new DataAccess();

            Log.info("getting nodes for parent id: " + j.idNode);

//            ResultSet rs = da.getResultSet(" SELECT id, title, content FROM " + Main.dbName + ".nodes where LENGTH(trim(title)) > 0 and id in (select child_node from " + Main.dbName + ".tree where  parent_node = " + j.idNode + ") ORDER BY title;");
            ResultSet rs = da.getResultSet(" SELECT id, title FROM " + Main.dbName + ".nodes where LENGTH(trim(title)) > 0 and id in (select child_node from " + Main.dbName + ".tree where  parent_node = " + j.idNode + ") ORDER BY title;");

            j.r = new ArrayList();

            while (rs.next()) {
                // only test data
                HashMap m = new HashMap();
                m.put("id", rs.getInt("id"));
                m.put("title", StringCorrection.cleanStringForDisplay(rs.getString("title")));
//                m.put("content", StringCorrection.cleanStringForDisplay(rs.getString("content")));
                m.put("content", "");
                m.put("files", "files");
                m.put("tags", "tags tags tags ");
                m.put("children", "children children ");
                j.r.add(m);
            }

            rs.close();
            da.closeConn();

            long elapsedTime = System.nanoTime() - startTime;
            double seconds = (double) elapsedTime / 1000000000.0;
            Log.log("info", "getChildNodes(JsonRequest j) took in seconds: " + seconds);

        } catch (Exception x) {
            Log.log("error", x.getMessage());
            x.printStackTrace(System.out);
            j.e = "Error";
            j.r = null;
            return j;
        } finally {
            return j;
        }
    }

    public JsonRequest moveOrLinkId(JsonRequest j) {

        DataAccess da = new DataAccess();
        String sql = "";

        try {

            long p = Long.parseLong(j.idParent);
            long c = Long.parseLong(j.idNode);

            if (p > 0 && c > 0) {

                if (j.d.equals("link")) {
                    sql = "INSERT INTO `" + Main.dbName + "`.`relations` (`node1`, `node2`) VALUES ('" + j.idParent + "', '" + j.idNode + "');";
                    da.executeStatement(sql);
                }

                if (j.d.equals("move")) {
                    sql = "DELETE FROM `" + Main.dbName + "`.`tree` WHERE child_node = " + j.idNode + ";";
                    da.executeStatement(sql);
                    sql = "INSERT INTO `" + Main.dbName + "`.`tree` (`parent_node`, `child_node`) VALUES ('" + j.idParent + "', '" + j.idNode + "');";
                    da.executeStatement(sql);
                }

                j.e = "OK";
            } else {
                j.e = "ERROR: one of the parameters is null";
            }
        } catch (Exception x) {
            j.e = "ERROR: " + x.toString();
            return j;
        } finally {
            return j;
        }
    }

    /**
     * get the file list for a given node id and put that into the provided JSON
     * object with which we construct the response
     *
     * @param node
     * @param j
     * @return
     */
    public JsonRequest getFileList(String node, JsonRequest j) {

        DataAccess da = new DataAccess();
        String pathFragment;
        pathFragment = da.getFirstStringValue("SELECT files FROM " + Main.dbName + ".nodes WHERE id = " + j.idNode + " limit 0, 2");
        pathFragment = pathFragment.replace("|", java.nio.file.FileSystems.getDefault().getSeparator());

        j.r = new ArrayList();
        j.fileNames = new ArrayList();
        j.filesHashMap = new HashMap();

        // this block is for legacy purpose after migration from old system
        if (pathFragment == null || pathFragment.length() < 1) {

            String c = da.getFirstStringValue("SELECT content FROM " + Main.dbName + ".nodes WHERE id = " + j.idNode + " limit 0, 2");

            if (c != null) {
                //  dataIdStart10884232019dataIdEnd 
                if (c.contains("dataIdStart")) {
                    String idForFile = c.substring(c.indexOf("dataIdStart") + 11, c.indexOf("dataIdEnd"));

                    Log.log("info", "idForFile for legacy update and uploade is " + idForFile);
                    idForFile = Main.pathFragmentLegacy + idForFile;

                    String completePath = Main.pathToUploadFiles + idForFile;

                    idForFile = idForFile.replace("//", "|");
                    idForFile = idForFile.replace("\\", "|");

                    Log.log("info", "full string idForFile for legacy update and uploade is " + idForFile);

                    //check if this path exists
                    File f = new File(completePath);

                    if (f.exists()) {

                        // now write this to the 
                        String sql = "UPDATE  " + Main.dbName + ".nodes set files = '" + idForFile + "' WHERE id = " + j.idNode;
                        da.executeStatement(sql);

                        pathFragment = da.getFirstStringValue("SELECT files FROM " + Main.dbName + ".nodes WHERE id = " + j.idNode + " limit 0, 2");
                        pathFragment = pathFragment.replace("|", java.nio.file.FileSystems.getDefault().getSeparator());
                    } else {
                        Log.log("info", "the path for this id does not exist and therefore we do not enter it in DB " + completePath);
                    }

                }
            }
//            System.exit(0);

        }

        if (pathFragment == null) {
            // then nothing was uploaded

        } else {

            FileActions fa = new FileActions();
            ArrayList fal = fa.getFiles(Main.pathToUploadFiles + pathFragment);

            int i = fal.size();
            String tmp;
            String tmpLink;

            for (int x = 0; x < i; x++) {
                tmp = (String) fal.get(x);
                j.fileNames.add(tmp);
                tmpLink = "fileDownload?s=" + j.s + "&c=tree&id=" + j.idNode + "&name=" + tmp;
                j.filesHashMap.put(tmp, tmpLink);
            }

        }

        return j;
    }

    /**
     * get the file upload path for a given node id
     *
     * @param id
     * @return
     */
    private String getFilePath(String id) {

        DataAccess da = new DataAccess();
        String sql = "SELECT files FROM " + Main.dbName + ".nodes where id = " + id + " limit 0, 2";
        String path = da.getFirstStringValue(sql);

        if (path.equals("")) {
            FileActions fa = new FileActions();
            path = fa.makeValidPath(id);
            path = path.replace(java.nio.file.FileSystems.getDefault().getSeparator(), "|");

            da.setValue("nodes", "files", path, "id", id);
        }
        path = path.replace("|", java.nio.file.FileSystems.getDefault().getSeparator());
        return path;
    }

    public JsonRequest uploadFile(String node, JsonRequest j) {

        // get the upload path
        String currentFileName;
        String currentFile;
        int pos;
        String filePart;
        j.r = new ArrayList();
        try {

            for (int i = 0; i < j.fileNames.size(); i++) {
                currentFileName = (String) j.fileNames.get(i);
                currentFile = (String) j.filesHashMap.get(currentFileName);
//                Log.log("info", "----------------------- file ------------------------------");
                Log.log("info", currentFile);

                pos = currentFile.indexOf(",");
                filePart = currentFile.substring(pos + 1);

//                Log.log("info", "---------------------- reduced ----------------------------");
                Log.log("info", filePart);

                byte[] decoded = Base64.getDecoder().decode(filePart);
//                byte[] decoded = Base64.decodeBase64(filePart.getBytes());

                String filePath = Main.pathToUploadFiles + this.getFilePath(j.idNode);
                Log.info("upload path in business layer: " + filePath);

//                FileOutputStream fos = new FileOutputStream("C:\\Users\\Alex Sickert\\Pictures\\out\\" + currentFileName);
                FileOutputStream fos = new FileOutputStream(filePath + java.nio.file.FileSystems.getDefault().getSeparator() + StringCorrection.cleanStringForFileName(currentFileName));

                fos.write(decoded);
                fos.close();

                Log.log("info", "-----------------------------------------------------------");
                j.e = "ok";
                j.r.add(" uploaded file " + currentFileName);
            }

        } catch (Exception x) {
            j.e = "error";
            x.printStackTrace();
            Log.log("error", x.getMessage());
        }

        // remove the files from object to make response small
        j.fileNames = null;
        j.filesHashMap = null;

        return j;
    }

    public String getTreeFileDownloadPath(String id, String fileName) {
        String s = Main.pathToUploadFiles + getFilePath(id) + java.nio.file.FileSystems.getDefault().getSeparator() + fileName;
        Log.info("file download path in business layer: " + s);
        return s;

    }

}

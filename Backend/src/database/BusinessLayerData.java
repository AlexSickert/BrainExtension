/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Log.Log;
import Util.FileActions;
import Util.RandomString;
import Util.StringCorrection;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import server.JsonCrmRequest;
import server.JsonRequest;
import server.Main;
//import org.apache.commons.codec.binary.Base64;
//
//import java.util.Base64;

/**
 * @author xandi
 */
public class BusinessLayerData {

    public void checkExistTables() {

        String sql_DELETE_TABLE = "DROP TABLE  crm_contacts ;";


        String sql_1 = "CREATE TABLE IF NOT EXISTS crm_contacts (\n" +
                "\n" +
                "\tID INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "\ttitle VARCHAR(250),\n" +  // name of person
                "\ttitle_phonetic VARCHAR(250),\n" +  // name of person that that we can find duplicates
                "\tshort_info VARCHAR(250) default '',\n" +  // name of person
                "\tcategory_tag VARCHAR(250) default '',\n" +  // source, network, recruiter, company, wmnzd
                //"\tpriority VARCHAR(250),\n" +  // priority, but we ahve already watch list...
                "\tlocation VARCHAR(250) default '',\n" + // tags of location
                "\ttopic_tags VARCHAR(250) default '',\n" + // tags of topics like recruiter, platform,
                "\tcloseness  TINYINT  default 0,  \n" +  // close friends = 1, work colleaugues = 2, in life met = 3, other = 4

                "\taction VARCHAR(250) default '', -- what next action will be\n" +
                "\taction_time BIGINT default 0,  -- when next action should take place\n" +

                "\tcompany_tags VARCHAR(250) default '',\n" + // tags of companies where contact worked ciklum, evolvice, ...

                "\twatch_list  TINYINT  default 0,  -- yes no\n" +  // to be used like a prio list
                "\tprocessing_info  TINYINT  default 0,  \n" +  // 0 = clean, 1=was merged into new, 2 = merged from others, 3 = deleted/hide
                "\tcomment TEXT, \n" +   // long text

                "\tjson_data TEXT, \n" +   // can store history of all other fields

                "\temail_1 VARCHAR(100) default '',\n" +
                "\temail_2 VARCHAR(100) default '',\n" +

                "\tinstagram VARCHAR(250) default '',\n" +
                "\tfacebook VARCHAR(250) default '',\n" +
                "\tfacebook_messenger VARCHAR(250) default '',\n" +
                "\tlinkedin VARCHAR(250),\n" +
                "\txing VARCHAR(250) default '',\n" +
                "\tweb_1 VARCHAR(250) default '',\n" +
                "\tweb_2 VARCHAR(250) default '',\n" +
                "\tviber VARCHAR(250) default '',\n" +

                "\twhatsapp VARCHAR(250) default '',\n" +
                "\ttelegram VARCHAR(250) default '',\n" +
                "\tphone_1 VARCHAR(250) default '',\n" +
                "\tphone_2 VARCHAR(250) default '',\n" +

                "\tdu_sie VARCHAR(5) default '',\n" +  //
                "\tgender VARCHAR(2) default '',\n" +  //  m, f, d,
                "\tsalutation VARCHAR(100) default '',\n" +  // for paper letter Sehr geehrter Herr xxx
                "\tpostal_address VARCHAR(250) default '',\n" +   // for paper letters

                "\tsource VARCHAR(250) default '',\n" +
                "\tlast_visit_date BIGINT default 0,\n" +  // last date we looked at the contact
                "\tadded_when BIGINT default 0, -- read only for sorting\n" +
                "\tlast_edit BIGINT default 0,  -- read only for sorting\n" +
                "\tlist_ids VARCHAR(250) default '',  -- to make refernces to the list\n" +
                "\tgoogle_spreadsheet_id VARCHAR(50) default ''  -- read only\n" +
                "\n" +
                ");";


        String sql_3 = "CREATE TABLE IF NOT EXISTS crm_actions (\n" +
                "\n" +
                "\tID INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "\tcontacts_id BIGINT, \n" +  // reference to contacts
                "\tstatus  TINYINT,  \n" +  // 1 = todo, 2 = doing, 3 = done
                "\tnext_action_date BIGINT, \n" +  // where we can choose +1 +3 +7 +30 + 180 +rnd
                "\tcomment TEXT \n" +  // free text to add

                "\n" +
                ");";


        String sql_2 = "CREATE TABLE IF NOT EXISTS crm_changes (\n" +
                "\n" +
                "\tID INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "\tcontacts_id BIGINT,\n" +
                "\ttime_stamp BIGINT,\n" +
                "\tfield_name VARCHAR(250),\n" +
                "\tnew_value TEXT\n" +
                "\n" +
                ");";

        try {
            DataAccess da = new DataAccess();
            //da.executeStatement(sql_DELETE_TABLE);
            da.executeStatement(sql_1);
            da.executeStatement(sql_2);
            da.executeStatement(sql_3);
            Log.log("info", " table existing check all OK ");
        } catch (Exception x) {
            Log.log("error", x.getMessage());

        }

    }


    public ArrayList uploadCrmBulkContacts(String s){

        /*
        *
        *   whitespace handing    String after = before.trim().replaceAll(" +", " ");
        *
        *   Todo: use the extra field for phonetic sound of names and convert from cyrillic
        *
        *   when upload new then need to check if name already exists, and if yes we update. but when update we check if
        *   this content already exists in the field. for this we separate by ";" semicolon. otherwise we append after a
        *   semicolon if theres is already content. by that we prevent pusshing the same content more than once
        *
        *
        *
        *
        * */



        String lines[] = s.split("\n");

        // line one must have the fields
        String field_names[] = lines[0].split("\t");

        ArrayList ret = new ArrayList();

        for(int i = 1; i < lines.length; i++){

            String id_str = createCrmContact();
            int id = Integer.parseInt(id_str);

            String line[] = lines[i].split("\t");

            for(int x = 0; x < field_names.length; x++){
                String field_name = field_names[x];
                String field_value = (String) line[x];
                if(! field_name.toLowerCase().trim().equals("id")){
                    this.updateCrmContact(id_str, field_name, field_value);
                }

            }

            ret.add("added contact with id " + id_str);

        }

        return ret;


    }

    public ArrayList loadCrmContacts(JsonCrmRequest processObj){

        //this.createCrmContact();
        //this.createCrmContact();
        //this.createCrmContact();

        String sql = "SELECT * FROM crm_contacts WHERE 1=1 ";

        if (processObj.filter_4_value.length() > 0){

            sql += " AND watch_list = " + processObj.filter_4_value + "";  // is tiny int numeric value
        }

        if (processObj.filter_3_value.length() > 0){

            sql += " AND closeness = " + processObj.filter_3_value + "";  // is tiny int numeric value
        }

        if (processObj.filter_2_value.length() > 0){

            sql += " AND category_tag like '%" + processObj.filter_2_value.trim() + "%' ";  // is string
        }

        if (processObj.filter_1_value.length() > 0){

            sql += " AND ( ";  // is string
            sql += " title like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR short_info like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR location like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR category_tag like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR action like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR topic_tags like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR company_tags like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR instagram like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR email_1 like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR email_2 like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR facebook like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR linkedin like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR xing like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR web_1 like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR web_2 like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR viber like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR whatsapp like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR telegram like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR phone_1 like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR phone_2 like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR source like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " OR comment like '%" + processObj.filter_1_value.trim() + "%' ";  // is string
            sql += " ) ";  // is string
        }


        if (processObj.filter_5_value.length() > 0){

            sql += " ORDER BY " + processObj.filter_5_value.trim() + " ";  // is string
        }

        sql += " LIMIT 300;";

        Log.log("info", sql);


        ArrayList arl = new ArrayList();

        try {
            DataAccess da = new DataAccess();

            ResultSet rs = da.getResultSet(sql);

            while (rs.next()) {

                HashMap m = new HashMap();
                m.put("ID", rs.getString("ID"));
                m.put("title", rs.getString("title"));
                m.put("short_info", rs.getString("short_info") + "");
                m.put("location", rs.getString("location") + "");
                m.put("topic_tags", rs.getString("topic_tags") + "");
                m.put("closeness", rs.getString("closeness") + "");
                arl.add(m);
            }

            da.closeConn();

            Log.log("info", " loadCrmContacts all OK ");
        } catch (Exception x) {
            Log.log("error", x.getMessage());

        }

        return arl;


    }


    public ArrayList loadCrmOneContact(String id){

        //this.createCrmContact();
        //this.createCrmContact();
        //this.createCrmContact();

        String sql = "SELECT * FROM crm_contacts where id = " + id;

        ArrayList arl = new ArrayList();

        try {
            DataAccess da = new DataAccess();

            ResultSet rs = da.getResultSet(sql);

            while (rs.next()) {

                HashMap m = new HashMap();
                m.put("ID", rs.getString("ID"));
                m.put("title", rs.getString("title"));
                m.put("short_info", rs.getString("short_info") + "");
                m.put("location", rs.getString("location") + "");
                m.put("category_tag", rs.getString("category_tag") + "");
                m.put("topic_tags", rs.getString("topic_tags") + "");
                m.put("closeness", rs.getString("closeness") + "");
                m.put("company_tags", rs.getString("company_tags") + "");
                m.put("watch_list", rs.getString("watch_list") + "");
                m.put("processing_info", rs.getString("processing_info") + "");
                m.put("comment", rs.getString("comment") + "");
                m.put("email_1", rs.getString("email_1") + "");
                m.put("email_2", rs.getString("email_2") + "");
                m.put("instagram", rs.getString("instagram") + "");
                m.put("facebook", rs.getString("facebook") + "");
                m.put("facebook_messenger", rs.getString("facebook_messenger") + "");
                m.put("linkedin", rs.getString("linkedin") + "");
                m.put("xing", rs.getString("xing") + "");
                m.put("web_1", rs.getString("web_1") + "");
                m.put("web_2", rs.getString("web_2") + "");
                m.put("viber", rs.getString("viber") + "");
                m.put("whatsapp", rs.getString("whatsapp") + "");
                m.put("telegram", rs.getString("telegram") + "");
                m.put("phone_1", rs.getString("phone_1") + "");
                m.put("phone_2", rs.getString("phone_2") + "");
                m.put("du_sie", rs.getString("du_sie") + "");
                m.put("gender", rs.getString("gender") + "");
                m.put("salutation", rs.getString("salutation") + "");
                m.put("postal_address", rs.getString("postal_address") + "");
                m.put("source", rs.getString("source") + "");
                m.put("action", rs.getString("action") + "");
                m.put("action_time", rs.getString("action_time") + "");

                arl.add(m);
            }

            da.closeConn();

            Log.log("info", " loadCrmContacts all OK ");
        } catch (Exception x) {
            Log.log("error", x.getMessage());

        }

        return arl;


    }



    public String createCrmContact() {

        String rnd = RandomString.getString(50);
        String ret = "";

        String sql = "INSERT INTO crm_contacts (" +
                "`title`" +
                ") values (" +
                "'" + rnd + "'" +
                ")";

        String sql_2 = "SELECT ID FROM crm_contacts WHERE `title` = '" + rnd + "';";

        try {
            DataAccess da = new DataAccess();
            da.executeStatement(sql);
            ret = da.getFirstStringValue(sql_2);
            Log.log("info", " createCrmContact all OK ");
        } catch (Exception x) {
            Log.log("error", x.getMessage());

        }

        return ret;
    }

//
//    public void XXXXXinsertCrmContact(JsonCrmRequest obj) {
//
//        String sql = "INSERT INTO crm_contacts (" +
//                "`category`," +
//                "`title`," +
//                "`location`," +
//                "`status`," +
//                "`last_action`," +
//                "`next_action_date`," +
//                "`prio`," +
//                "`watch_list`," +
//                "`comment`," +
//                "`action_history`," +
//                "`email_1`," +
//                "`email_2`," +
//                "`instagram`," +
//                "`facebook`," +
//                "`xing`," +
//                "`linkedin`," +
//                "`web_1`," +
//                "`web_2`," +
//                "`phone_1`," +
//                "`phone_2`," +
//                "`source`," +
//                "`added_when`," +
//                "`last_edit`," +
//                "`list_ids`," +
//                "`google_spreadsheet_id`" +
//                ") values (" +
//                "`" + obj.category + "`," +
//                "`" + obj.title + "`," +
//                "`" + obj.location + "`," +
//                "`" + obj.status + "`," +
//                "`" + obj.last_action + "`," +
//                "`" + obj.next_action_date + "`," +
//                "`" + obj.prio + "`," +
//                "`" + obj.watch_list + "`," +
//                "`" + obj.comment + "`," +
//                "`" + obj.action_history + "`," +
//                "`" + obj.email_1 + "`," +
//                "`" + obj.email_2 + "`," +
//                "`" + obj.instagram + "`," +
//                "`" + obj.facebook + "`," +
//                "`" + obj.xing + "`," +
//                "`" + obj.linkedin + "`," +
//                "`" + obj.web_1 + "`," +
//                "`" + obj.web_2 + "`," +
//                "`" + obj.phone_1 + "`," +
//                "`" + obj.phone_2 + "`," +
//                "`" + obj.source + "`," +
//                "`" + obj.added_when + "`," +
//                "`" + obj.last_edit + "`," +
//                "`" + obj.list_ids + "`," +
//                "`" + obj.google_spreadsheet_id + "`" +
//                ")";
//
//
//        try {
//            DataAccess da = new DataAccess();
//            da.executeStatement(sql);
//            Log.log("info", " insertCrmContact all OK ");
//        } catch (Exception x) {
//            Log.log("error", x.getMessage());
//
//        }
//
//    }

    public String updateCrmContact(String id, String field, String value) {

        String valueType = "";
        String errorStr = "";

        try {
            DataAccess da = new DataAccess();

            switch (field) {

                case "title":
                    valueType = "string";
                    break;
                default:
                    valueType = "string";

            }

            da.updateTableViaPreparedStatement("crm_contacts", field, Integer.parseInt(id), valueType, value);

            Log.log("info", " insertCrmContact all OK ");
        } catch (Exception x) {
            Log.log("error", x.getMessage());
            errorStr = x.getMessage();

        }


        return errorStr;


    }


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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
import Log.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import server.Main;
import Log.Log;

/**
 *
 * @author xandi
 *
 *
 * prepared statements https://stackoverflow.com/questions/28686908/jdbc-pass-parameters-to-sql-query
 *
 */
public class DataAccess {

    Connection conn;

    public void closeConn() {
        try {
            if (conn.isValid(0)) {
                Log.log("info", "closing the database connection");
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() {

        try {

//            Log.log("info", x.getMessage());
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", "root", "password");
            conn = DriverManager.getConnection(Main.dbConnString + Main.dbName, Main.dbUser, Main.dbPass);

            Statement stmt = conn.createStatement();
            stmt.execute("SELECT * FROM " + Main.dbName + ".nodes;");
            stmt.close();

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM nodes");

            Log.log("info", "--------------- 3 ----------------------");
            while (rs.next()) {
                Log.log("info", "--------------- loop ----------------------");
                int x = rs.getInt("id");
                Log.log("info", Integer.toString(x));
                String s = rs.getString("title");
                Log.log("info", s);
                //float f = rs.getFloat("c");
            }

            this.closeConn();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void executeStatement(String sql) {

        Log.log("info", "in executeStatement(String sql)");

        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", "root", "password");
            conn = DriverManager.getConnection(Main.dbConnString + Main.dbName + "?useUnicode=true&characterEncoding=UTF-8", Main.dbUser, Main.dbPass);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();

            this.closeConn();

        } catch (Exception e) {
            Log.log("info", e.toString());
            e.printStackTrace();
        }

    }

    public int getFirstPositiveIntValue(String sql) {
        try {
            long startTime = System.nanoTime();
            ResultSet rs = this.getResultSet(sql);
            rs.next();
            long elapsedTime = System.nanoTime() - startTime;
            double seconds = (double) elapsedTime / 1000000000.0;
            Log.log("info", "getFirstPositiveIntValue(String sql) took in seconds: " + seconds);

            int res = rs.getInt(1);
            rs.close();
            return res;
            //rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setValue(String table, String field, String value, String id, String idValue) {

        String sql = "";

        // UPDATE `test_db`.`nodes` SET `content`='newx' WHERE `id`='561';
        sql += "Update " + Main.dbName + "." + table + " ";
        sql += "set " + field + " = '" + value + "' ";
        sql += "where  " + id + " = '" + idValue + "' ";

        Log.info("executing SQL: " + sql);

        this.executeStatement(sql);

    }

    public String getFirstStringValue(String sql) {
        String tst;
        try {
            long startTime = System.nanoTime();
            Log.info("sql: " + sql);
            ResultSet rs = this.getResultSet(sql);
            rs.next();
            tst = rs.getString(1);
            rs.close();
            long elapsedTime = System.nanoTime() - startTime;
            double seconds = (double) elapsedTime / 1000000000.0;
            Log.log("info", "getFirstStringValue(String sql) took in seconds: " + seconds);

            if (tst == null) {
                tst = "";
            }
            
            
            this.closeConn();
            
            return tst;

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
            return "";
        }
    }

    public ResultSet getResultSet(String sql) {

        ResultSet rs = null;
        try {
            long startTime = System.nanoTime();
//            System.out.println("--------------- 1 ----------------------");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", "root", "password");
            conn = DriverManager.getConnection(Main.dbConnString + Main.dbName, Main.dbUser, Main.dbPass);

            Statement stmt;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            long elapsedTime = System.nanoTime() - startTime;
            double seconds = (double) elapsedTime / 1000000000.0;
            Log.log("info", " getResultSet(String sql) took in seconds: " + seconds);

//            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return rs;
        }

    }

}

package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {

    public static String table = "key_db.keys";

    public static Connection Connect(){
        Connection conn = null;

        //try to connect to db at localhost:3306 and if it failed presume it's
        // running in docker and try to connect in the container.
        try {


            conn = DriverManager.getConnection("jdbc:mysql://0.0.0.0:3306/key_db?" +
                            "user=bob&password=bob123!");

            // Do something with the Connection

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

            try {
                conn = DriverManager.getConnection("jdbc:mysql://db:3306/key_db?" +
                        "user=bob&password=bob123!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    return conn;
    }

    /**Creates a table in the database with the columns
     *
     * @apiNote Name      String,
     *          Priv_key  String,
     *          Pub_key   String,
     *          ip        String,
     *          port      Int
     *
     */

    public static void createTable() {

        Connection c = DB.Connect();

        String sql = "CREATE TABLE IF NOT EXISTS " + table + "(\n" +
                "  Name VARCHAR(20) NOT NULL,\n" +
                "  Priv_key VARCHAR(1024) NOT NULL,\n" +
                "  Pub_key VARCHAR(1024) NOT NULL,\n" +
                "  ip VARCHAR(32) NOT NULL,\n" +
                "  port INT(11) UNSIGNED NOT NULL,\n" +
                "  PRIMARY KEY(`Name`(20)))\n";

        System.out.println(sql);
        try {
            Statement statement = c.createStatement();
            statement.execute(sql);
            statement.close();
            c.close();
            System.out.println("Created table in given database...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jetsetgo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author megh
 */
public class MySql {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void readDataBase() throws Exception {
      try {
        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.jdbc.Driver");
        // Setup the connection with the DB
        connect = DriverManager
            .getConnection("jdbc:mysql://<host>/<database>?"
                + "user=<user>&password=<password>");

        // Statements allow to issue SQL queries to the database
        statement = connect.createStatement();
        // Result set get the result of the SQL query
        resultSet = statement
            .executeQuery("select * from fb_refman.gift");
        writeResultSet(resultSet);

        // PreparedStatements can use variables and are more efficient
        preparedStatement = connect
            .prepareStatement("insert into  fb_refman.gift values (?, ?, ?, ?, ?)");
        // "code, name, description, image_url, coin_value, GIFT from FB_REFMAN.GIFT");
        // Parameters start with 1
        preparedStatement.setString(1, "LIQ002");
        preparedStatement.setString(2, "Kingfisher Beer");
        preparedStatement.setString(3, "Kingfisher beer is not an alcohol and blah blah blah");
//        preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
        preparedStatement.setString(4, "http://www.kingfisher.com/neer1.jpg");
        preparedStatement.setString(5, "10");
        preparedStatement.executeUpdate();

        preparedStatement = connect
            .prepareStatement("SELECT code, name, description, image_url, coin_value from fb_refman.gift");
        resultSet = preparedStatement.executeQuery();
        writeResultSet(resultSet);

        // Remove again the insert comment
        preparedStatement = connect
        .prepareStatement("delete from fb_refman.gift where code= ? ; ");
        preparedStatement.setString(1, "LIQ002");
        preparedStatement.executeUpdate();

        resultSet = statement
        .executeQuery("select * from fb_refman.gift");
        writeMetaData(resultSet);

      } catch (Exception e) {
        throw e;
      } finally {
        close();
      }

    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
      //   Now get some metadata from the database
      // Result set get the result of the SQL query

      System.out.println("The columns in the table are: ");

      System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
      for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
        System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
      }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
      // ResultSet is initially before the first data set
      while (resultSet.next()) {
        // It is possible to get the columns via name
        // also possible to get the columns via the column number
        // which starts at 1
        // e.g. resultSet.getSTring(2);
        String code = resultSet.getString("code");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
//        Date date = resultSet.getDate("datum");
        String image_url = resultSet.getString("image_url");
        String coin_value = resultSet.getString("coin_value");
        System.out.println("Code: " + code);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Imag Link: " + image_url);
        System.out.println("Coin Value: " + coin_value);
      }
    }

    // You need to close the resultSet
    private void close() {
      try {
        if (resultSet != null) {
          resultSet.close();
        }

        if (statement != null) {
          statement.close();
        }

        if (connect != null) {
          connect.close();
        }
      } catch (Exception e) {

      }
    }
  
  
  
  
  
}

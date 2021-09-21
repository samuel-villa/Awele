package view;

import java.sql.*;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 *
 * Create a connexion to the database
 */

public class Connexion {

    private final String DBPath;
    private Connection connection = null;
    private Statement statement = null;

    /**
     * Constructor
     * @param dBPath path to the SQLite db
     */
    public Connexion(String dBPath) {
        DBPath = dBPath;
    }

    /**
     * Provide connection to the db
     */
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            //connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
                // the previous line has been replaced by this one in order to include the db to the jar file
            connection = DriverManager.getConnection("jdbc:sqlite::resource:" + DBPath);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException notFoundException) {
            notFoundException.printStackTrace();
            System.out.println("Connexion error");
        }
    }

    /**
     * Close the connection to the db
     */
    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows to query data from db
     * @param request SQL command
     * @return SQL result
     */
    public ResultSet query(String request) {
        ResultSet resultat = null;
        try {
            resultat = statement.executeQuery(request);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Query error into: " + request);
        }
        return resultat;
    }

    /**
     * Add score to the db
     * @param scoreDB scoreDB object
     */
    public void addScore(ScoreDB scoreDB) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO scores VALUES(?,?,?,?,?)");
            preparedStatement.setString(1, scoreDB.getWinner());
            preparedStatement.setInt(2, scoreDB.getWinnerSeeds());
            preparedStatement.setInt(3, scoreDB.getLooserSeeds());
            preparedStatement.setString(4, scoreDB.getGameDebutTime());
            preparedStatement.setString(5, scoreDB.getGameDuration());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


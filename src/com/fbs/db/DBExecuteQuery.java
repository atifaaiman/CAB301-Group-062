package com.fbs.db;

import com.fbs.general.UserPermissions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.fbs.db.DBConnection.*;

public class DBExecuteQuery {

    // Method to execute queries in the database
    /**
     * @author Fernando Barbosa Silva
     * Execute a query in the database
     * @param query is the SQL query in a string format.This method can be used for all types
     * of SQL statements
     * @throws SQLException exception if there is some problem in the query or database connection.
     * @return a boolean value. TRUE indicates that statement has returned a ResultSet object and FALSE
     * indicates that statement has returned an int value or returned nothing.
     */
    public static boolean execute(String query)  {
        boolean result = false;
        try {
            Connection connection = DBConnection.getInstance();
            Statement statement = connection.createStatement();
            result = statement.execute(query);
            statement.close();
            connection.close();
            setInstanceToNull();
            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Execute a query in the database
     * @param query is the SQL query in a string format. The method accepts only non-select statements.
     * @throws SQLException exception if there is some problem in the query or database connection.
     * @return int value representing number of records affected; Returns 0 if the query returns nothing.
     */
    public static int executeUpdate(String query)  {
        int rowsAffected = 0;
        try {
            Connection connection = DBConnection.getInstance();
            Statement statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(query);
            statement.close();
            connection.close();
            setInstanceToNull();
            return rowsAffected;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * @author Fernando Barbosa Silva
     * Execute a query in the database
     * @param query is the SQL query in a string format. The method accepts only non-select statements.
     * @throws SQLException exception if there is some problem in the query or database connection.
     * @return a resultSet object which contains the result returned by the query.
     */
    public static void executeQuery(String query)  {
        try {
            Connection connection = DBConnection.getInstance();
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            statement.close();
            connection.close();
            setInstanceToNull();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * @author Fernando Barbosa Silva
     * Get user's permissions from the database.
     * @return returns an object with the permission of  the users, returns an empty object if
     * no users were found in the database.
     * @throws SQLException if there is an error in the query or database connection.
     */
    public static List executeGetUserPermissions(String query){
        List<UserPermissions> userList = new ArrayList<>();

        try{
            // get connection
            Connection connection = DBConnection.getInstance();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Check each row of the table
            while (resultSet.next()) {
                String userName = resultSet.getString(2);
                String administrator= resultSet.getString(4);
                String createBillboards =  resultSet.getString(5);
                String editAllBillboards = resultSet.getString(6);
                String scheduleBillboards= resultSet.getString(7);
                String editUsers = resultSet.getString(8);
                UserPermissions userPermissions = new UserPermissions(userName,administrator,createBillboards,
                        editAllBillboards, scheduleBillboards, editUsers);
                userList.add(userPermissions);
            }

            statement.close();
            connection.close();
            setInstanceToNull();
            return userList;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return userList;
    }




}

package com.fbs.db;



import java.sql.*;

import static com.fbs.db.DBConnection.setInstanceToNull;
import static com.fbs.db.DBExecuteQuery.*;

public class DBUsersTableQueries {

    /**
     * @author Fernando Barbosa Silva
     * Add new user in the database
     * @param array of strings : arrayExample => ["user_name", "password", "administrator", "create_billboards","edit_all_billboards", "schedule_billboards", "edit_users" ]
     * @return returns 1 if user was added successfully, returns -1 if user already exists,
     * returns 0 if user was not added because of some error.
     */
    public static int createUser(String [] array){
        // Query to add user
        String ADD_USER_QUERY = "INSERT INTO " +
                "users  (       user_name              , password     ,   administrator  , create_billboards  , edit_all_billboards, schedule_billboards, edit_users)" +
                "VALUES ('"+array[0].toLowerCase()+  "','"+array[1]+"','" + array[2] + "','" +  array[3]  + "','" +   array[4] + "','" +  array[5]  + "','"+array[6]+"')";

        int result = 0;
        if (userExists(array[0])) return -1;
        result =  executeUpdate(ADD_USER_QUERY);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Add new user in the database
     * @param user_name  strings, provide the user_name that need to be updated
     * @param columnName  strings, provide the name of the columns which need to be updated
     * valid columns's names (password,administrator,create_billboards,edit_all_billboards,schedule_billboards,edit_users).
     * @param newValue  strings, provide the new value for the column that need to be updated.
     * values for user permission,administrator,create_billboards,edit_all_billboards,schedule_billboards,edit_users,
     * must be 'no' or 'yes', no other value is valid in the user table
     * @return returns 1 if user was deleted successfully, returns -1 if user does not exist,
     * returns 0 if user was not updated because of some error.
     */
    public static int updateUser(String user_name, String columnName, String newValue){

        // Query to update user's information
        String update = "UPDATE users SET " + columnName.toLowerCase() + "='" + newValue + "'WHERE user_name = '"+
                user_name.toLowerCase() +"'";

        int result = 0;
        if (!userExists(user_name)) return -1;
        result = executeUpdate(update);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Delete user from the database
     * @param user_name  strings, provide the user_name that need to be deleted
     * @return returns 1 if user was deleted successfully, returns -1 if user does not exist in the database,
     * returns 0 if user was not deleted because of some error.
     */
    public static int deleteUser(String user_name){

        // Query used to delete user from the database
        String deleteQuery = "DELETE FROM users WHERE user_name ='"+ user_name.toLowerCase() + "'";

        int result = 0;
        if (!userExists(user_name)) return -1;
        result = executeUpdate(deleteQuery);
        return  result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Check if a user is in the database.
     * @param user_name  strings, provide the user_name that need to be checked.
     * @return returns 1 if user exist in the database, returns 0 if user does not exist.
     * @throws SQLException if there is an error in the query or database connection.
     */
    public static boolean userExists(String user_name){

        // Query used for retrieving all users from the database
        String query = "SELECT user_name FROM users";

        try{
            // get connection
            Connection connection = DBConnection.getInstance();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            // Check each row
            while (rs.next()) {
                String user = rs.getString(1);
                //System.out.println( user);
                if (user.equals(user_name.toLowerCase())) {
                    st.close();
                    connection.close();
                    setInstanceToNull();
                    return true;
                }
            }
            st.close();
            connection.close();
            setInstanceToNull();
            return false;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }


    // Main class to validate the code
    public static void main(String[] Args){
        String [] demoUser = {"FernAndo", "24353", "no", "no", "yes", "no", "yes"};

        System.out.println(createUser(demoUser));
        System.out.println(userExists("Fernando"));
        System.out.println(deleteUser("FaBio"));
        System.out.println(updateUser("fernando", "password", "silvaSILVA"));
        System.out.println(updateUser("fernando", "administrator", "No"));
        System.out.println(updateUser("fernando", "create_billboards", "nO"));
        System.out.println(updateUser("fernando", "edit_all_billboards", "no"));
        System.out.println(updateUser("fernando", "schedule_billboards", "no"));
        System.out.println(updateUser("fernando", "edit_users", "NO"));


    }
}

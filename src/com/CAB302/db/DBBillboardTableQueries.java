package com.CAB302.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.CAB302.db.DBConnection.setInstanceToNull;
import static com.CAB302.db.DBExecuteQuery.executeUpdate;

public class DBBillboardTableQueries {

    /**
     * @author Fernando Barbosa Silva
     * Add new user in the database
     * @param array of strings : arrayExample => ["billboard_name", "xml", "create_by"]
     * @return returns 1 if billboard was added successfully, return -1 if billboard_name
     * already exist in the database, returns 0 if billboard was not added because of some error.
     */
    public static int addBillboard(String [] array){
        // Query to add billboard
        String ADD_BILLBOARD_QUERY = "INSERT INTO " +
                "billboards  (       billboard_name    ,      xml       ,         create_by      )" +
                "VALUES ('"+array[0].toLowerCase()+  "','"+ array[1] +"','" + array[2].toLowerCase() + "')";

        int result = 0;
        if( billboardExists(array[0])) return -1;
        result =  executeUpdate(ADD_BILLBOARD_QUERY);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Add new user in the database
     * @param billboard_name  strings, provide the name of the billboard that need to be updated
     * @param columnName  strings, provide the name of the columns which need to be updated
     * valid columns's names (billboard_name, xml).
     * @param newValue  strings, provide the new value for the column that need to be updated.
     * @return returns 1 if user was deleted successfully, returns  0 if user was not updated
     * because of some error, returns -1 if billboard does not exist in the database.
     */
    public static int updateBillboard(String billboard_name, String columnName, String newValue){

        // Query to update billboards's information
        String update = "UPDATE billboards SET " + columnName.toLowerCase() + "='" + newValue + "'WHERE billboard_name = '"+
                billboard_name.toLowerCase() +"'";

        int result = 0;
        if (!billboardExists(billboard_name)) return -1;
        result = executeUpdate(update);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Delete billboard from the database
     * @param billboard_name  strings, provide the billboard_name that need to be deleted
     * @return returns 1 if user was deleted successfully, returns  0 if user was not deleted
     * or found because of some error, or return -1 if billboard does no exist in the database.
     */
    public static int deleteBillboard(String billboard_name){

        // Query used to delete billboard from the database
        String deleteQuery = "DELETE FROM billboards WHERE billboard_name ='"+ billboard_name.toLowerCase() + "'";

        int result = 0;
        if (!billboardExists(billboard_name)) return -1;
        result = executeUpdate(deleteQuery);
        return  result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Check if a billboard is in the database using the unique billboard_name.
     * @param billboard_name  strings, provide the user_name that need to be checked.
     * @return returns 1 if billboard_name exist in the database, returns 0 if it does not exist.
     * @throws SQLException if there is an error in the query or database connection.
     */
    public static boolean billboardExists(String billboard_name){

        // Query used for retrieving all billboards from the database
        String query = "SELECT billboard_name FROM billboards";

        try{
            // get connection
            Connection connection = DBConnection.getInstance();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            // Check each row
            while (rs.next()) {
                String billboard = rs.getString(1);
                if (billboard.equals(billboard_name.toLowerCase())) {
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

    /**
     * @author Fernando Barbosa Silva
     * Check if a billboard is in the database using the unique billboard_name.
     * @param xmlValues strings array, provide the information to be inserted in the xml.
     * xmlArrayTemplate => {"backgroundColour","messageColour", "message", "pictureType",
     *                  "pictureData", "informationColour", "information" };
     * @return returns xml string with all information used by the billboard applications.
     */
    public static String createXmlString(String [] xmlValues) {

        String backgroundColour = xmlValues[0];
        String messageColour = xmlValues[1];
        String message = xmlValues[2].replace("'", "''");
        String pictureType = xmlValues[3];
        String pictureData = xmlValues[4];
        String informationColour = xmlValues[5];
        String information = xmlValues[6];

        String xml = "<?xml version=\"1.0\" encoding =\"UTF-8\"?>\n" +
                "<billboard background=\""+ backgroundColour + "\">\n" +
                "<message colour=\""+ messageColour + "\">" + message + "</message>\n" +
                "<picture \""+pictureType+"\"=\""+ pictureData +"\" />\n" +
                "<information colour=\""+ informationColour +"\">" + information + "</information>\n" +
                "</billboard>";

        return xml;
    }


    // Main class to validate the code
    public static void main(String[] Args){

        String [] xmlFormat = {"#0000FF","#FFFF00","Welcome to the _____ Corporation's Annual Fundraiser!", "url",
                "https://example.com/fundraiser_image.jpg", "#00FFF",
                "Be sure to check out https://example.com/ for more information."};

        String [] xmlFormat_2 = {"#0000FF","#FFFF00","Test", "url",
                "test", "#00FFF", "Be sure to check out https://example.com/ for more information."};

        String xml = createXmlString(xmlFormat);
        System.out.println(xml);

        boolean test = billboardExists("billboard_01");
        System.out.println(test);

        String [] billboardData = {"billboard_01", xml, "Fernando"};
        System.out.println("Add billboard status: " + addBillboard(billboardData));

        String newXML = createXmlString(xmlFormat_2);

        System.out.println( "Update billboard status: "+ updateBillboard("billboard_05","xml",newXML));

        System.out.println("Delete billboard status: " + deleteBillboard("billboard_01"));

        String [] billboard = {"billboard_02", newXML, "fernando"};
        System.out.println("Add billboard Status: " + addBillboard(billboard));

    }


}

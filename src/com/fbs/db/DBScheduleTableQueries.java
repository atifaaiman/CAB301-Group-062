package com.fbs.db;


import com.fbs.general.Schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fbs.db.DBExecuteQuery.*;

public class DBScheduleTableQueries {


    /**
     * @author Fernando Barbosa Silva
     * Add new user in the database
     * @param schedule object with the attributes (billboard_name, xml, create_by)
     * @return returns 1 if billboard was added successfully,
     * returns 0 if billboard was not added because of some error.
     */
    public static int addSchedule(Schedule schedule){
        // Query to add billboard
        String ADD_BILLBOARD_QUERY = "INSERT INTO " +
                "schedules  (billboard_name, date_time_start, date_time_finish, schedule_create_by)"+
                "VALUES ('"+schedule.getBillboard_name()+ "','"+ schedule.getDate_time_start()+"','"+schedule.getDate_time_finish()+ "'," +
                "'"+schedule.getSchedule_create_by()+"')";

        int result = 0;
        result =  executeUpdate(ADD_BILLBOARD_QUERY);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Add update billboard schedule in the database
     * @param schedule_id  int, provide the id number of the schedule that need to be updated
     * @param columnName  strings, provide the name of the columns which need to be updated
     * valid columns's names (date_time_start,date_time_finish).
     * @param newValue  strings, provide the new value for the column that need to be updated.
     * @return returns 1 if user was updated successfully, returns  0 if user was not updated
     * because of some error.
     */
    public static int updateSchedule(int schedule_id, String columnName, String newValue){

        // Query to update billboards's information
        String update = "UPDATE schedules SET " + columnName.toLowerCase() + "='" + newValue + "'WHERE billboard_name = '"+
                schedule_id +"'";
        int result = 0;
        result = executeUpdate(update);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Delete schedules from the database
     * @param schedule_id int, provide the schedule id that need to be deleted from the database.
     * @return returns 1 if user was deleted successfully, returns  0 if user was not deleted
     * or found because of some error.
     */
    public static int deleteSchedule(int schedule_id){

        // Query used to delete billboard from the database
        String deleteQuery = "DELETE FROM schedules WHERE schedule_id ='"+ schedule_id + "'";

        int result = 0;
        result = executeUpdate(deleteQuery);
        return  result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Get list of  all schedules from the database.
     * @return returns a List object with all schedules, if schedule does
     * not exist it return an empty object.
     */
    public static List getsScheduleList(){

        // Query used for retrieving all users from the database
        String query = "SELECT * FROM schedules";

        List<Schedule> result = new ArrayList<>();
        result = executeGetSchedule(query);
        return result;
    }


    /**
     * @author Fernando Barbosa Silva
     * Get a schedule from the database.
     * @param schedule_id string.
     * @return returns an object of the schedule selected, if schedule does
     * not exist it return an empty object.
     */
    public static List getScheduleBillboard(int schedule_id){

        // Query used for retrieving all users from the database
        String query = "SELECT * FROM schedules WHERE schedule_id='"+schedule_id+"'";

        List<Schedule> result = new ArrayList<>();
        result = executeGetSchedule(query);
        return result;
    }

    /**
     * @author Fernando Barbosa Silva
     * Get a schedule from the database.
     * @return returns an object of the schedule selected, if schedule does
     * not exist it return an empty object.
     */
    public static List<Schedule> selectScheduledBillboard(){
        // Get the current date and time in the format "2020-05-21 19:00:00":
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        String currentDate =  dtf.format(localDate).toString();
        System.out.println("Current date: " + currentDate);

        // Query
        //String query = "SELECT * FROM schedules WHERE date_time_start <='"+currentDate+"' AND '"+currentDate+"'<= date_time_finish";
        String query = "SELECT * FROM schedules WHERE schedule_create_date=(" +
                "SELECT MAX(schedule_create_date) FROM schedules WHERE date_time_start <='"+currentDate+"' AND '"+currentDate+"'<= date_time_finish)";
        List<Schedule> list = new ArrayList<>();
        list = executeGetSchedule(query);
        return list;
    }

    // Main class to validate the code
    public static void main(String[] Args){

        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss ");
        String currentDate =  dtf.format(localDate).toString();
        System.out.println("Current date: " + currentDate);

        //java.sql.Date date = new java.sql.Date(2020, 5, 21);

        String dateStart   = "2020-05-21 19:00:00";
        String dateFinish  = "2020-05-21 21:05:00";


        long time = System.currentTimeMillis();
        java.sql.Date d = new java.sql.Date(time);
        System.out.println(d);

        //Date date = new Date();
        //Schedule schedule = new Schedule(0, "New billboard",dateStart,dateFinish,"fernando", "");
        //System.out.println(addSchedule(schedule));

        List<Schedule> list = selectScheduledBillboard();
        System.out.println(list.size());
        System.out.println(list.get(0).getBillboard_name());


        //String newXML = Xml.createXmlString(xmlFormat_2);

        //System.out.println( "Update billboard status: "+ updateBillboard("billboard_05","xml",newXML));

        // System.out.println("Delete billboard status: " + deleteBillboard("billboard_01"));

        //Billboard billboard = new Billboard("billboard_02", newXML, "fernando");
        //System.out.println("Add billboard Status: " + addBillboard(billboard));

        //List<Billboard> list = getBillboardList();
        //System.out.println(list.get(0).getCreated_by());


    }



}

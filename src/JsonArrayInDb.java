import org.json.simple.JSONArray;

import java.sql.*;

public class JsonArrayInDb{
    public JSONArray getArrayData(){
        JSONArray list = new JSONArray();
        try{
            list.add("'[{ 'Course_ID': 'INSY 124','Course_Name': 'Java','Grade':'A',}]'");
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String args[]){

        JsonArrayInDb storeArrayDB=new JsonArrayInDb();
        JSONArray list=storeArrayDB.getArrayData();
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/students",
                            "postgres", "baraza");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
              //String sql ="SELECT name FROM students";
            String sql = "INSERT INTO grades_list (grade_score) VALUES(?)";
            PreparedStatement pstmt = c.prepareStatement(sql);




           pstmt.setString(1, list.toString());

            int affectedRows= pstmt.executeUpdate();

             if(affectedRows> 0){
                 System.out.println("INSERTED");
             }else {
                 System.out.println("NOT INSERTED");
             }

            System.out.println();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }	 }
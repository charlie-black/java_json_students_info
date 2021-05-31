import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentsJsonGradesInsert
{
    public static Connection ConnectToDB() throws Exception {
        //Registering the Driver
        DriverManager.registerDriver(new org.postgresql.Driver());
        //Getting the connection
        String url = "jdbc:postgresql://localhost:5432/grades";
        Connection con = DriverManager.getConnection(url, "postgres", "baraza");
        System.out.println("Connection established......");
        return con;
    }
    public static void main(String args[]) {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\charles\\IdeaProjects\\student_io\\grades.json"));
            //Retrieving the array
            JSONArray jsonArray = (JSONArray) jsonObject.get("students_grades");
            Connection con = ConnectToDB();
            //Insert a row into the comp_graphics table
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO comp_graphics values (?, ?, ?, ?, ?, ? )");
            for(Object object : jsonArray) {
                JSONObject record = (JSONObject) object;
                int id = Integer.parseInt((String) record.get("ID"));
                String first_name = (String) record.get("First_Name");
                String last_name = (String) record.get("Last_Name");
                String course_id = (String) record.get("Course_ID");
                String course_name = (String) record.get("Course_Name");
                String grade = (String) record.get("Grade");
                pstmt.setInt(1, id);
                pstmt.setString(2, first_name);
                pstmt.setString(3, last_name);
                pstmt.setString(4, course_id);
                pstmt.setString(5, course_name);
                pstmt.setString(6, grade);
                pstmt.executeUpdate();
            }
            System.out.println("Records inserted.....");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

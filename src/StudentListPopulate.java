import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

class StudentListPopulate
{

    private static Connection con = null;
    private static String URL = "jdbc:postgresql://localhost:5432/grades";
    private static String driver = "org.postgresql.Driver";
    private static String user = "postgres";
    private static String pass = "baraza";

    /**
     * Main aplication entry point
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException
    {

        // a Postgresql statement
        Statement stmt;
        // a Postgresql query
        String query;
        // the results from Postgresql query
        ResultSet rs;

        // 2 dimension array to hold table contents
        // it holds temp values for now
        Object rowData[][] = {{"Row1-Column1", "Row1-Column2", "Row1-Column3", "Row1-Column4", "Row1-Column5", "Row1-Column6"}};
        // array to hold column names
        Object columnNames[] = {"id","first_name", "last_name", "course_id","course_name","grade"};

        // create a table model and table based on it
        DefaultTableModel mTableModel = new DefaultTableModel(rowData, columnNames);
        JTable table = new JTable(mTableModel);

        // try and connect to the database
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(URL, user, pass);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        // run the desired query
        query = "SELECT id, first_name, last_name, course_id, course_name, grade FROM comp_graphics";
        // make a statement with the server
        stmt = con.createStatement();
        // execute the query and return the result
        rs = stmt.executeQuery(query);

        // create the gui
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);

        // remove the temp row
        mTableModel.removeRow(0);

        // create a temporary object array to hold the result for each row
        Object[] rows;
        // for each row returned
        while (rs.next()) {
            // add the values to the temporary row
            rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6)};
            // add the temp row to the table
            mTableModel.addRow(rows);
        }
    }
}
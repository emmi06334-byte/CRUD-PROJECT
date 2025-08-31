import java.sql.*;
import java.util.Scanner;

public class StudentCRUD {
    // Database credentials
    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "punam";     // change if your MySQL username is different
    static final String PASS = "12345";  // replace with your MySQL password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Load and register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Connected to Database Successfully!");

            while (true) {
                System.out.println("\n--- Student CRUD Menu ---");
                System.out.println("1. Insert Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: // Insert
                        System.out.print("Enter Name: ");
                        sc.nextLine(); // consume newline
                        String name = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        System.out.print("Enter Course: ");
                        sc.nextLine();
                        String course = sc.nextLine();

                        String insertSQL = "INSERT INTO students (name, age, course) VALUES (?, ?, ?)";
                        PreparedStatement psInsert = con.prepareStatement(insertSQL);
                        psInsert.setString(1, name);
                        psInsert.setInt(2, age);
                        psInsert.setString(3, course);
                        psInsert.executeUpdate();
                        System.out.println("✅ Student Inserted Successfully!");
                        break;

                    case 2: // Read
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM students");
                        System.out.println("\nID | Name | Age | Course");
                        while (rs.next()) {
                            System.out.println(rs.getInt("id") + " | " +
                                    rs.getString("name") + " | " +
                                    rs.getInt("age") + " | " +
                                    rs.getString("course"));
                        }
                        break;

                    case 3: // Update
                        System.out.print("Enter Student ID to Update: ");
                        int upId = sc.nextInt();
                        System.out.print("Enter New Course: ");
                        sc.nextLine();
                        String newCourse = sc.nextLine();

                        String updateSQL = "UPDATE students SET course=? WHERE id=?";
                        PreparedStatement psUpdate = con.prepareStatement(updateSQL);
                        psUpdate.setString(1, newCourse);
                        psUpdate.setInt(2, upId);
                        int rowsUpdated = psUpdate.executeUpdate();
                        if (rowsUpdated > 0)
                            System.out.println("✅ Student Updated Successfully!");
                        else
                            System.out.println("❌ Student ID not found!");
                        break;

                    case 4: // Delete
                        System.out.print("Enter Student ID to Delete: ");
                        int delId = sc.nextInt();

                        String deleteSQL = "DELETE FROM students WHERE id=?";
                        PreparedStatement psDelete = con.prepareStatement(deleteSQL);
                        psDelete.setInt(1, delId);
                        int rowsDeleted = psDelete.executeUpdate();
                        if (rowsDeleted > 0)
                            System.out.println("✅ Student Deleted Successfully!");
                        else
                            System.out.println("❌ Student ID not found!");
                        break;

                    case 5:
                        con.close();
                        System.out.println("🔒 Connection Closed. Exiting...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("❌ Invalid Choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
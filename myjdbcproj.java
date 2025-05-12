import java.sql.*;
import java.io.*;

public class myjdbcproj {
    public static void main(String args[]) throws IOException {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            // Load the driver class
            Class.forName("oracle.jdbc.OracleDriver");


            // Create the connection object
            String conurl = "jdbc:oracle:thin:@172.17.144.110:1521:ora11g";
            con = DriverManager.getConnection(conurl, "system", "admin123");
            stmt = con.createStatement();

            int choice;
            do {
                System.out.println("\n***** Banking Management System *****");
                System.out.println("1. Show Customer Records");
                System.out.println("2. Add Customer Record");
                System.out.println("3. Delete Customer Record");
                System.out.println("4. Update Customer Information");
                System.out.println("5. Show Account Details of a Customer");
                System.out.println("6. Show Loan Details of a Customer");
                System.out.println("7. Deposit Money to an Account");
                System.out.println("8. Withdraw Money from an Account");
                System.out.println("9. Exit");
                System.out.print("Enter your choice (1-9): ");
                choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1: // Show Customer Records
                        rs = stmt.executeQuery("SELECT * FROM customer");
                        System.out.println("Cust_No\tName\t\tPhone\t\tCity");
                        while (rs.next()) {
                            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" +
                                    rs.getString(3) + "\t" + rs.getString(4));
                        }
                        break;

                    case 2: // Add Customer Record
                        System.out.print("Enter Customer Number: ");
                        String cust_no = br.readLine();
                        System.out.print("Enter Name: ");
                        String name = br.readLine();
                        System.out.print("Enter Phone Number: ");
                        String phone = br.readLine();
                        System.out.print("Enter City: ");
                        String city = br.readLine();
                        stmt.executeUpdate("INSERT INTO customer VALUES('" + cust_no + "','" + name + "','" + phone + "','" + city + "')");
                        System.out.println("Customer added successfully!");
                        break;

                    case 3: // Delete Customer Record
                        System.out.print("Enter Customer Number to delete: ");
                        cust_no = br.readLine();
                        int deleted = stmt.executeUpdate("DELETE FROM customer WHERE cust_no = '" + cust_no + "'");
                        if (deleted > 0)
                            System.out.println("Customer deleted successfully!");
                        else
                            System.out.println("Customer not found.");
                        break;

                    case 4: // Update Customer Information
                        System.out.print("Enter Customer Number to update: ");
                        cust_no = br.readLine();
                        System.out.println("Enter 1: Update Name 2: Update Phone 3: Update City");
                        int updateChoice = Integer.parseInt(br.readLine());
                        switch (updateChoice) {
                            case 1:
                                System.out.print("Enter New Name: ");
                                name = br.readLine();
                                stmt.executeUpdate("UPDATE customer SET name='" + name + "' WHERE cust_no='" + cust_no + "'");
                                break;
                            case 2:
                                System.out.print("Enter New Phone Number: ");
                                phone = br.readLine();
                                stmt.executeUpdate("UPDATE customer SET phoneno='" + phone + "' WHERE cust_no='" + cust_no + "'");
                                break;
                            case 3:
                                System.out.print("Enter New City: ");
                                city = br.readLine();
                                stmt.executeUpdate("UPDATE customer SET city='" + city + "' WHERE cust_no='" + cust_no + "'");
                                break;
                        }
                        System.out.println("Customer updated successfully!");
                        break;

                    case 5: // Show Account Details
                        System.out.print("Enter Customer Number: ");
                        cust_no = br.readLine();
                        rs = stmt.executeQuery("SELECT c.cust_no, c.name, a.account_no, a.type, a.balance, b.branch_code, b.branch_name, b.branch_city " +
                                "FROM customer c JOIN account a ON c.cust_no=a.cust_no JOIN branch b ON a.branch_code=b.branch_code WHERE c.cust_no='" + cust_no + "'");
                        while (rs.next()) {
                            System.out.println("Cust_No: " + rs.getString(1) + ", Name: " + rs.getString(2) +
                                    ", Acc_No: " + rs.getString(3) + ", Type: " + rs.getString(4) + ", Balance: " + rs.getDouble(5) +
                                    ", Branch_Code: " + rs.getString(6) + ", Branch_Name: " + rs.getString(7) + ", City: " + rs.getString(8));
                        }
                        break;

                    case 6: // Show Loan Details
                        System.out.print("Enter Customer Number: ");
                        cust_no = br.readLine();
                        rs = stmt.executeQuery("SELECT c.cust_no, c.name, l.loan_no, l.amount, b.branch_code, b.branch_name, b.branch_city " +
                                "FROM customer c JOIN loan l ON c.cust_no=l.cust_no JOIN branch b ON l.branch_code=b.branch_code WHERE c.cust_no='" + cust_no + "'");
                        boolean hasLoan = false;
                        while (rs.next()) {
                            hasLoan = true;
                            System.out.println("Cust_No: " + rs.getString(1) + ", Name: " + rs.getString(2) +
                                    ", Loan_No: " + rs.getString(3) + ", Amount: " + rs.getDouble(4) +
                                    ", Branch_Code: " + rs.getString(5) + ", Branch_Name: " + rs.getString(6) + ", City: " + rs.getString(7));
                        }
                        if (!hasLoan) System.out.println("Congratulations! No loan found.");
                        break;

                    case 7: // Deposit Money
                        System.out.print("Enter Account Number: ");
                        String accNo = br.readLine();
                        System.out.print("Enter amount to deposit: ");
                        double deposit = Double.parseDouble(br.readLine());
                        stmt.executeUpdate("UPDATE account SET balance = balance + " + deposit + " WHERE account_no = '" + accNo + "'");
                        System.out.println("Deposit successful!");
                        break;

                    case 8: // Withdraw Money
                        System.out.print("Enter Account Number: ");
                        accNo = br.readLine();
                        System.out.print("Enter amount to withdraw: ");
                        double withdraw = Double.parseDouble(br.readLine());
                        rs = stmt.executeQuery("SELECT balance FROM account WHERE account_no = '" + accNo + "'");
                        if (rs.next()) {
                            double currentBalance = rs.getDouble(1);
                            if (currentBalance >= withdraw) {
                                stmt.executeUpdate("UPDATE account SET balance = balance - " + withdraw + " WHERE account_no = '" + accNo + "'");
                                System.out.println("Withdrawal successful!");
                            } else {
                                System.out.println("Insufficient balance.");
                            }
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    case 9:
                        System.out.println("Exiting the program.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 9.");
                }

            } while (choice != 9);

        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error in closing connection: " + e);
            }
        }
    }
}

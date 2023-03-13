import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemoCRUDPeUsers {
    final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
    final String USERNAMEDB = "postgres";
    final String PWDDB = "cliff2005";

    public static void main(String[] args) throws SQLException {
        //cum fac CRUD pe un obiect de tip user

        DemoCRUDPeUsers obiect = new DemoCRUDPeUsers();

        int i = 0;
        String n = "";
        while (i!=9) {
            System.out.println("Choose the operation: ");
            System.out.println("1 = insertion ");
            System.out.println("2 = read all");
            System.out.println("3 = update");
            System.out.println("4 = delete");
            System.out.println("9 = EXIT");
            Scanner sc = new Scanner(System.in);

            try {
                i = sc.nextInt();
            } catch (Exception e) {
                i = 0;
            }

            switch (i) {
                case 1:
                    User u = new User();
                    u = obiect.ReadFromKeyboard(u, i, n);
                    boolean isAdded = obiect.insert(u);
                    System.out.println(isAdded ? "S-a inserat inregistrarea!" : "Nu s-a putut efectua inserarea!");
                    break;
                case 2:
                    List result = obiect.readAllUsers();
                    System.out.println(result);
                    break;
                case 3:
                    u = new User();
                    n = "New";
                    u = obiect.ReadFromKeyboard(u, i, n);
                    boolean ex = obiect.update(u);
                    System.out.println(ex);
                    break;
                case 4:
                    u = new User();
                    u = obiect.ReadFromKeyboard(u, i, n);
                    boolean rasp = obiect.delete(u);
                    System.out.println(rasp);
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Introduceti doar 1,2,3,4 sau 9");
            }
        }
    }

        private boolean insert (User u) throws SQLException {
            //cod care scrie in DB

            //conectare la DB cu incarcare driver
            //daca are rezultate, citirea lor

            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            //rulare SQL:
            PreparedStatement pSt = conn.prepareStatement("insert into users (username, password) values (?,?)");
            pSt.setString(1, u.getUsername());
            pSt.setString(2, u.getPassword());
            int val = pSt.executeUpdate(); // returneaza 1 daca a reusit sa insereze un rand
            // sau returneaza 0, daca  nu a reusit sa insereze
            boolean ok = false;
            if (val != 0) {
                ok = true;
            }
            return ok;
        }

        private List<User> readAllUsers () throws SQLException {
            List<User> lu = new ArrayList<>();
            //citeste din DB toti userii si returneaza lista lor

            //conectare la DB cu incarcare driver:

            Connection conn = DriverManager.getConnection(URLDB,USERNAMEDB,PWDDB);;

            //rulare SQL:
            Statement pSt = conn.createStatement();

            ResultSet rs = pSt.executeQuery("select * from users order by username");

            while (rs.next()) {
                String user = rs.getString("username").trim();
                String p = rs.getString("password").trim();
                int i = rs.getInt("id");

                User u = new User(user, p, i);
                lu.add(u);
            }
            return lu;
        }

        private boolean update (User u) throws SQLException {
            //cod care modifica in DB

            //conectare la DB cu incarcare driver:

            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            //rulare SQL:
            PreparedStatement pSt = conn.prepareStatement("update users set password = ? where username = ?");
            pSt.setString(1, u.getPassword());
            pSt.setString(2, u.getUsername());
            int val = pSt.executeUpdate(); // returneaza 1 daca a reusit sa faca update
            // sau returneaza 0, daca  nu a reusit sa faca update
            boolean ok = false;
            if (val != 0) {
                ok = true;
            }
            return ok;
        }

        private boolean delete (User u) throws SQLException {
            //cod care sterge o inregistrare din DB

            //conectare la DB cu incarcare driver

            //rulare SQL:
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);
            PreparedStatement pSt = conn.prepareStatement("delete from users where username = ?");
            pSt.setString(1, u.getUsername());

            int val = 0; // returneaza 1 daca a reusit sa stearga randul

            try {
                val = pSt.executeUpdate();
                // sau returneaza 0, daca  nu a reusit sa stearga
            } catch (SQLException e) {
                System.out.println("Acest username mai exista si in alt tabel!");
            }

            boolean ok = false;
            if (val != 0) {
                ok = true;
            }
            return ok;
        }

        private User ReadFromKeyboard (User u, int i, String n) {
            System.out.print("Username: ");
            String us = new Scanner(System.in).nextLine();
            u.setUsername(us);
            if (i != 4) {
                System.out.print(n + " Password: ");
                String pa = new Scanner(System.in).nextLine();
                u.setPassword(pa);
            }
            return u;
        }

}

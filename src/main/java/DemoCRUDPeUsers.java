import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemoCRUDPeUsers {
    final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
    final String USERNAMEDB = "postgres";
    final String PWDDB = "cliff2005";
//    final String PWDDB = "PAROLAGRESITA";
    int i = 0;
    boolean isAdded = false;

        boolean insert (User u) {
            //cod care scrie in DB

            //conectare la DB cu incarcare driver
            //daca are rezultate, citirea lor
            int val = 0;
            try {
                Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

                //rulare SQL:
                PreparedStatement pSt = conn.prepareStatement("insert into users (username, password) values (?,?)");
                pSt.setString(1, u.getUsername());
                pSt.setString(2, u.getPassword());
                val = pSt.executeUpdate(); // returneaza 1 daca a reusit sa insereze un rand
                // sau returneaza 0, daca  nu a reusit sa insereze

            }
            catch (SQLException e){
                e.printStackTrace();
            }

            boolean ok = false;
            if (val != 0) {
                ok = true;
            }
            return ok;
        }

        List<User> readAllUsers () {
            List<User> lu = new ArrayList<>();
            //citeste din DB toti userii si returneaza lista lor

            //conectare la DB cu incarcare driver:

            try {
                Connection conn = DriverManager.getConnection(URLDB,USERNAMEDB,PWDDB);

                //rulare SQL:
                Statement pSt = conn.createStatement();

                ResultSet rs = pSt.executeQuery("select * from users order by username");
                while (rs.next()) {
                    String user = rs.getString("username").trim();
                    String p = rs.getString("password").trim();
                    long i = rs.getLong("id");

                    User u = new User(user, p);
                    u.setId(i);
                    lu.add(u);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Nu se poate conecta la baza de date");
            }
            return lu;
        }

        boolean update (User u) {
            //cod care modifica in DB

            //conectare la DB cu incarcare driver:
            int val = 0;
            try {
                Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

                //rulare SQL:
                PreparedStatement pSt = conn.prepareStatement("update users set password = ? where username = ?");
                pSt.setString(1, u.getPassword());
                pSt.setString(2, u.getUsername());
                val = pSt.executeUpdate(); // returneaza 1 daca a reusit sa faca update
                                                // sau returneaza 0, daca  nu a reusit sa faca updat
            }
            catch(SQLException e) {
                e.printStackTrace();
            }

            boolean ok = false;
            if (val != 0) {
                ok = true;
            }
            return ok;
        }

        boolean delete (long idU) {
            //cod care sterge o inregistrare din DB

            //conectare la DB cu incarcare driver

            //rulare SQL:
            int val = 0; // returneaza 1 daca a reusit sa stearga randul
            // sau returneaza 0, daca  nu a reusit sa stearga
            try {
                Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);
                PreparedStatement pSt = conn.prepareStatement("delete from users where id = ?");
                pSt.setLong (1, idU);
                val = pSt.executeUpdate();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }

            boolean ok = false;
            if (val != 0) {
                ok = true;
            }
            return ok;
        }

        String readFromKeyboard(String s) {
            String pa = "";
            System.out.print(s);
            String us = new Scanner(System.in).nextLine();
            return us;
        }

    List<Food> readFoodOfAUser (long userId) {
        List<Food> lf = new ArrayList<>();
        //citeste din DB toti userii si returneaza lista lor

        //conectare la DB cu incarcare driver:

        try {
            Connection conn = DriverManager.getConnection(URLDB,USERNAMEDB,PWDDB);

            PreparedStatement pSt = conn.prepareStatement("SELECT * FROM loggedfood,users WHERE users.id = ? and users.id = loggedfood.iduser");
            pSt.setLong(1, userId);

            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {
                String foodname = rs.getString("foodname").trim();
                Food f = new Food(foodname);
                lf.add(f);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nu se poate conecta la baza de date");
        }
        return lf;
    }

    boolean insertFoodForUserId (Food f, long id) {
        int val = 0;
        try {
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            //rulare SQL:
            PreparedStatement pSt = conn.prepareStatement("insert into loggedfood (foodname,iduser) values (?,?)");
            pSt.setString(1, f.getFoodname());
            pSt.setLong(2, id);
            val = pSt.executeUpdate(); // returneaza 1 daca a reusit sa insereze un rand
            // sau returneaza 0, daca  nu a reusit sa insereze
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        boolean ok = false;
        if (val != 0) {
            ok = true;
        }
        return ok;
    }

    long login (User u) {
        // returneaza -1 daca nu exista si id-ul userului daca exista

        //conectare la DB cu incarcare driver:

        long id = -1;

        try {
            Connection conn = DriverManager.getConnection(URLDB,USERNAMEDB,PWDDB);

            PreparedStatement pSt = conn.prepareStatement("SELECT id FROM users WHERE username = ? and password = ?");
            pSt.setString(1, u.getUsername());
            pSt.setString(2, u.getPassword());

            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {
                id = rs.getLong("id");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nu se poate conecta la baza de date");
        }
        return id;
    }

    boolean isAdmin (User u) {
        // returneaza -1 daca nu exista si id-ul userului daca exista

        //conectare la DB cu incarcare driver:

        boolean isAdmin = false;

        try {
            Connection conn = DriverManager.getConnection(URLDB,USERNAMEDB,PWDDB);

            PreparedStatement pSt = conn.prepareStatement("SELECT isadmin FROM users WHERE username = ? and password = ?");
            pSt.setString(1, u.getUsername());
            pSt.setString(2, u.getPassword());

            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {
                isAdmin = rs.getBoolean("isAdmin");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nu se poate conecta la baza de date");
        }
        return isAdmin;
    }

    List<Food> readAllFood () {
        List<Food> lf = new ArrayList<>();
        //citeste din DB toti userii si returneaza lista lor

        //conectare la DB cu incarcare driver:

        try {
            Connection conn = DriverManager.getConnection(URLDB,USERNAMEDB,PWDDB);

            PreparedStatement pSt = conn.prepareStatement("SELECT * FROM loggedfood");

            ResultSet rs = pSt.executeQuery();

            while (rs.next()) {
                String foodname = rs.getString("foodname").trim();
                Food f = new Food(foodname);
                lf.add(f);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nu se poate conecta la baza de date");
        }
        return lf;
    }

    boolean menuAdmin(User u) {
        boolean stop = false;
        DemoCRUDPeUsers object = new DemoCRUDPeUsers();
        System.out.println("Menu Administrator - choose the operation: ");
        System.out.println("1 = user insertion ");
        System.out.println("2 = read all users");
        System.out.println("3 = update user");
        System.out.println("4 = delete user");
        System.out.println("9 = EXIT");

        Scanner sc = new Scanner(System.in);

        try {
            i = sc.nextInt();
        } catch (Exception e) {
            i = 0;
        }

        switch (i) {
            case 1:
                String stringUsername = object.readFromKeyboard("Username: ");
                String stringPassword = object.readFromKeyboard("Password: ");
                isAdded = object.insert(new User(stringUsername, stringPassword));
                System.out.println(isAdded ? "S-a inserat inregistrarea!" : "Nu s-a putut efectua inserarea!");
                break;
            case 2:
                List result = object.readAllUsers();
                System.out.println(result);
                break;
            case 3:
                stringUsername = object.readFromKeyboard("Username: ");
                stringPassword = object.readFromKeyboard("New password: ");
                u = new User(stringUsername, stringPassword);
                boolean ex = object.update(u);
                System.out.println(ex);
                break;
            case 4:
                long idUser = Long.parseLong(object.readFromKeyboard("Id: "));
                boolean rasp = object.delete(idUser);
                System.out.println(rasp);
                break;
            case 9:
                break;
            default:
                System.out.println("Please enter just 1,2,3,4 or 9");
        }
        if (i==9) {
            stop = true;
        }
        return stop;
    }

    boolean menuUser(User u) {
        boolean stop = false;
        DemoCRUDPeUsers object = new DemoCRUDPeUsers();
        System.out.println("Menu User - choose the operation: ");
        System.out.println("1 = read all the food");
        System.out.println("2 = read my food");
        System.out.println("3 = insert my food");
        System.out.println("4 = update my food");
        System.out.println("9 = EXIT");

        Scanner sc = new Scanner(System.in);

        try {
            i = sc.nextInt();
        } catch (Exception e) {
            i = 0;
        }

        switch (i) {
            case 1:
                List result = object.readAllFood();
                System.out.println(result);
                break;
            case 2:
                List resultFood = object.readFoodOfAUser(u.getId());
                System.out.println(resultFood);
                break;
            case 3:
                String sf = object.readFromKeyboard("Add food:");
                Food f = new Food(sf);
                isAdded = object.insertFoodForUserId(f, u.getId());
                System.out.println(isAdded ? "The food was added!" : "The food could'nt be added!");
                break;
            case 9:
                break;
            default:
                System.out.println("Please enter just 1,2,3,or 9");

        }
        if (i==9) {
            stop = true;
        }
        return stop;
    }
}



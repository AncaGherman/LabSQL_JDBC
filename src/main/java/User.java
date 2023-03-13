import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private int id;


    public User(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                '}' + '\n';
    }

//    public User ReadFromKeyboard () {
//        User
//        System.out.print("Username: ");
//        String us = new Scanner(System.in).nextLine();
//        System.out.print("Password: ");
//        String pa = new Scanner(System.in).nextLine();
//        u.setUsername(us);
//        u.setPassword(pa);
//        u.setId(0);
//        return u;
//    }
}

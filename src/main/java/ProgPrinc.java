import java.util.List;
import java.util.Scanner;

public class ProgPrinc {
    static int i = 0;
    static String s = "";
    static String n = "";
    static boolean isAdded;
    static long userId = -1;
    static  boolean stop = false;

    public static void main(String[] args) {
        DemoCRUDPeUsers dbacces = new DemoCRUDPeUsers();
        User u = null;
        while (true) {
            String username = dbacces.readFromKeyboard("Username: ");
            String pwd = dbacces.readFromKeyboard("Password: ");
            u = new User(username, pwd);
            userId = dbacces.login(u);
            u.setId(userId);
            if (userId != -1) {
                break;
            }
        }

        boolean isAdmin = dbacces.isAdmin(u);
        while (!stop) {
            if (!isAdmin) {
                stop = dbacces.menuUser(u);
            } else {
                stop = dbacces.menuAdmin(u);
            }
        }
    }
}

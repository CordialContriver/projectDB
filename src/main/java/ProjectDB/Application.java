package ProjectDB;

import ProjectDB.Services.UserService;
import ProjectDB.Tables.User;
import ProjectDB.Utilities.UserType;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;

import static ProjectDB.Utilities.Methods.sc;
import static ProjectDB.Utilities.Methods.stringNotEmpty;

public class Application {
    private final UserService us = new UserService();

    public void mainMenu() {
        boolean repeat = true;
        do {
            System.out.println("""
                    [1] Prisijungti
                    [2] Studentų registracija
                    [0] Atsijungti
                    """);
            switch (sc.nextLine()) {
                case "1" -> login(us);
                case "2" -> register(us);
                case "0" -> {
                    System.out.println("Viso gero");
                    repeat = false;
                }
                default -> System.out.println("Tokio nėra");
            }
        } while (repeat);
    }

    private void register(UserService us) {
        String name = stringNotEmpty("Vardas?");
        String surname = stringNotEmpty("Pavardė?");
        String username = stringNotEmpty("Username?");
        String salt = LocalDateTime.now().toString().substring(20);
        String password = inputPassTwice(salt);
        UserType userType = UserType.STUDENT;
        User newUser = new User(name, surname, username, salt, password, userType);
        us.createNewUser(newUser);
    }

    private void login(UserService us) {

        String nameLogin = stringNotEmpty("Vartotojas");
        String passLogin = stringNotEmpty("Slaptažodis");
        User user = us.getUserByUsername(nameLogin);
        if (user != null && user.getPasswordHash().equals(DigestUtils.sha1Hex(passLogin+user.getSalt()))) {
            if (user.getUserType() == UserType.STUDENT) {
                new studentMenu(user, us);
            } else if (user.getUserType() == UserType.TEACHER) {
                new teacherMenu(user, us);
            } else if(user.getUserType() == UserType.ADMIN){
                new adminMenu(user, us);
            }
        } else {
            System.out.println("Duomenys neteisingi");
        }
    }

    private String inputPassTwice(String salt) {
        for (; ; ) {
            String pass1 = stringNotEmpty("Password");
            String pass2 = stringNotEmpty("Pakartokite");
            if (pass1.equals(pass2)) {
                return DigestUtils.sha1Hex(pass1+salt);
            } else {
                System.out.println("Slaptažodžiai nesutampa. Pakartokite.");
            }
        }
    }

}

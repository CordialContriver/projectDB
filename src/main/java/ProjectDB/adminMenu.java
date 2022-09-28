package ProjectDB;

import ProjectDB.Services.UserService;
import ProjectDB.Tables.User;
import ProjectDB.Utilities.UserType;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static ProjectDB.Utilities.Methods.*;

public class adminMenu {

    public adminMenu(User adminUser, UserService us) {
        boolean repeat = true;
        do {
            System.out.println("""
                    [1] Pridėti naują vartotoją
                    [2] Ištrinti vartotoją
                    [3] Atkurti vartotojo prisijungimo duomenis
                    [4] Administratoriaus nustatymai
                    [0] Atsijungti
                            """);
            switch (sc.nextLine()) {
                case "1" -> registerNewUser(us);
                case "2" -> deleteUser(us);
                case "3" -> resetUserAccount(us);
                case "4" -> new accountMenu(adminUser, us);
                case "0" -> {
                    System.out.println("Viso gero");
                    repeat = false;
                }
                default -> System.out.println("Tokio nėra");
            }
        } while (repeat);

    }

    private void resetUserAccount(UserService us) {
        User user = pickUserAdmin(us);
        if (user != null) {
            user.setUsername((user.getName().substring(0, Math.min(1, user.getName().length())) +
                    user.getSurname().substring(0, Math.min(1, user.getSurname().length()))).toLowerCase());
            System.out.println("Vartotojas: " + user.getUsername());
            user.setSalt(LocalDateTime.now().toString().substring(20));
            user.setPasswordHash(createRandomPassword(user.getSalt()));
        }
    }

    private void deleteUser(UserService us) {
        User user = pickUserAdmin(us);
        if (user == null || !tnSwitch("Tikrai ištrinti? \n" + user.getName().charAt(0) + "." + user.getSurname() + " " + user.getUserType())) {
        } else if (user.getUserType() == UserType.ADMIN && us.getAdminCount() == 1) {
            System.out.println("Negalima ištrinti vienintelio administratoriaus");
        } else {
            us.deleteUser(user);
        }
    }

    private User pickUserAdmin(UserService us) {
        List<User> userList = us.getUserList();
        int i = 0;
        for (User user : userList) {
            System.out.printf("[%d] %s %s, %s id:%d\n", ++i, user.getName(), user.getSurname(), user.getUserType(), user.getId());
        }
        int nr = Integer.parseInt(stringEmpty("Pasirinkte vartotoją")) - 1;
        try {
            return userList.get(nr);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Tokio vartotojo nėra.");
            return null;
        }
    }

    private void registerNewUser(UserService us) {
        String name = stringNotEmpty("Vardas?");
        String surname = stringNotEmpty("Pavardė?");
        UserType userType = null;
        while (userType == null) {
            System.out.println("""
                    [1] studentas
                    [2] dėstytojas
                    [3] administratorius
                    """);
            switch (sc.nextLine()) {
                case "1" -> userType = UserType.STUDENT;
                case "2" -> userType = UserType.TEACHER;
                case "3" -> userType = UserType.ADMIN;
                default -> System.out.println("Tokio nėra");
            }
            String username = (name.substring(0, Math.min(3, name.length())) +
                    surname.substring(0, Math.min(3, surname.length()))).toLowerCase();
            System.out.println("Vartotojas: " + username);
            String salt = LocalDateTime.now().toString().substring(20);
            String password = createRandomPassword(salt);

            User newUser = new User(name, surname, username, salt, password, userType);
            us.createNewUser(newUser);
        }
    }

    private String createRandomPassword(String salt) {
        String randPass = "" + new Random().nextInt(1000, 9999);
        System.out.println("Slaptažodis: " + randPass);
        return DigestUtils.sha1Hex(randPass + salt);
    }
}
package ProjectDB;

import ProjectDB.Services.UserService;
import ProjectDB.Tables.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;

import static ProjectDB.Utilities.Methods.*;

public class accountMenu {
    private final UserService us;
    public accountMenu(User user, UserService us) {
        this.us=us;
        boolean repeat = true;
        do {
            System.out.println("""
                    Vartotojo meniu.
                    [1] Pakeisti vardą
                    [2] Pakeisti pavardę
                    [3] Pakeisti vartotoją
                    [4] Pakeisti slaptažodį
                    [0] Grįžti
                    """);
            switch (sc.nextLine()) {
                case "1" -> changeName(user);
                case "2" -> changeSurname(user);
                case "3" -> changeUsername(user);
                case "4" -> changePassword(user);
                case "0" -> repeat = false;
                default -> System.out.println("Tokio nėra");
            }
        } while (repeat);
    }

    private void changePassword(User user) {
        String pass1 = stringNotEmpty("Įveskite naują slaptažodį");
        String pass2 = stringNotEmpty("Pakartokite");
        if (pass1.equals(pass2)) {
            user.setSalt(LocalDateTime.now().toString().substring(20));
            user.setPasswordHash(DigestUtils.sha1Hex(pass1+user.getSalt()));
            System.out.println("Slaptažodis pakeistas");
            us.updateUser(user);
        } else {
            System.out.println("Slaptažodžiai nesutampa. Nepakeista");
        }

    }



    private void changeUsername(User user) {
        String newInput = stringEmpty("Esamas vartotojas: \""+user.getUsername()+"\" Įrašykite naują. [tuščias - grįžti]");
        if (newInput.equals("")) {
            System.out.println("Nepakeista");
        } else {
            user.setSurname(newInput);
            System.out.println("Vartotojas pakeistas");
            us.updateUser(user);

        }
    }

    private void changeSurname(User user) {
        String newInput = stringEmpty("Esama pavardė: \""+user.getSurname()+"\" Įrašykite naują. [tuščias - grįžti]");
        if (newInput.equals("")) {
            System.out.println("Nepakeista");
        } else {
            user.setSurname(newInput);
            System.out.println("Pavardė pakeistas");
            us.updateUser(user);

        }
    }

    private void changeName(User user) {
        String newInput = stringEmpty("Esamas vardas: \""+user.getName()+"\" Įrašykite naują. [tuščias - grįžti]");
        if (newInput.equals("")) {
            System.out.println("Nepakeista");
        } else {
            user.setName(newInput);
            System.out.println("Vardas pakeistas");
            us.updateUser(user);

        }

    }
}

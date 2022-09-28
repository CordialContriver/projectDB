package ProjectDB.Utilities;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Methods {
    public static Scanner sc = new Scanner(System.in);

    public static String stringEmpty(String s){
        System.out.println(s);
        return sc.nextLine().trim();
    }

    public static String stringNotEmpty(String s){
        System.out.println(s);
        while (true){
            String įvestis = sc.nextLine().trim();
            if(!įvestis.equals("")){
                return įvestis;
            } else{
                System.out.println("Tuščias negalimas");
            }
        }
    }

    public static String stringNull(String s){
        System.out.println(s);
        String įvestis = sc.nextLine().trim();
        if(įvestis!=""){
            return įvestis;
        } else{
            return null;
        }
    }

    public static boolean tnSwitch(String s) {
        System.out.println(s+" T/N");
        while (true) {
            switch (sc.nextLine().toUpperCase()) {
                case "T" -> {
                    return true;
                }
                case "N" -> {
                    return false;
                }
                default -> System.out.println("T/t arba N/n");
            }
        }
    }

    public static double notEmptyDouble(String tekstas) {
        double įvestis = 0;
        for(;;) {
            try {
                System.out.println(tekstas);
                return įvestis = Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Bloga įvestis, ne numeris");
            }
        }
    }

}

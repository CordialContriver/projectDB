package ProjectDB;

import ProjectDB.Services.QuestionService;
import ProjectDB.Services.TestAttemptService;
import ProjectDB.Services.TestService;
import ProjectDB.Services.UserService;
import ProjectDB.Tables.Question;
import ProjectDB.Tables.Test;
import ProjectDB.Tables.User;

import java.util.List;

import static ProjectDB.Utilities.Methods.*;

public class teacherMenu {
    private final TestService ts = new TestService();

    public teacherMenu(User user, UserService us) {

        boolean repeat = true;
        do {
            System.out.println("""
                    [1] Peržiūrėti testus
                    [2] Sukurti testą
                    [3] Vartotojo nustatymai
                    [0] Atsijungti
                    """);
            switch (sc.nextLine()) {
                case "1" -> seeAllTests(user, ts);
                case "2" -> createTest(user, ts);
                case "3" -> new accountMenu(user, us);
                case "0" -> {
                    System.out.println("Viso gero");
                    repeat = false;
                }
                default -> System.out.println("Tokio nėra");
            }
        } while (repeat);
    }


    private void seeAllTests(User user, TestService ts) {
        Test test = pickTestTeacher(user);
        if (test != null) {
            System.out.println("""
                    [1] peržiūrėti rezultatus
                    [2] redaguoti testą
                    [3] trinti testą
                    """);
            switch (sc.nextLine()) {
                case "1" -> ts.seeTestResults(test);
                case "2" -> editTest(test, ts);
                case "3" -> {
                    if (tnSwitch("Ištrinti?")) {
                        ts.deleteTest(test);
                    }
                }
                default -> System.out.println("Tokio pasirinkimo nėra");
            }
        }
    }

    private void editTest(Test test, TestService ts) {
//        System.out.println("""
//                    [1] redaguoti rezultatus
//                    [2] trinti testą
//                    """);
//        switch (sc.nextLine()) {
////            case "1" -> ;
//            case "2" -> ts.deleteTest(test);
//            default -> System.out.println("Tokio pasirinkimo nėra");
//        }
//        QuestionService qs = new QuestionService();
        System.out.println("Edit test menu now OPEN");
    }

    private Test pickTestTeacher(User user) {
        TestAttemptService tas = new TestAttemptService();
        List<Test> tests = ts.getTestListByTeacher(user);
        if (tests.size() == 0) {
            return null;
        }
        int i = 0;
        for (Test test : tests) {
            System.out.printf("[%d] %s, id:%d\n", ++i, test.getTestName(), test.getId());
            System.out.println("Kartų laikytas: " + tas.getListByTest(test).size());
        }
        int testNr = Integer.parseInt(stringNotEmpty("Pasirinkte testo nr.")) - 1;
        try {
            return tests.get(testNr);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private void createTest(User user, TestService ts) {
        String testName = stringNotEmpty("Testo pavadinimas");
        Test test = new Test(user, testName);
        ts.createNewTest(test);
        QuestionService qs = new QuestionService();
        while (true) {
            Question newQuestion = createQuestion(test);
            if (newQuestion == null) {
                break;
            }
            qs.addNewQuestion(newQuestion);
        }
//        ts.updateTest(test);
    }

    private Question createQuestion(Test test) {
        System.out.println("""
                    Naujas klausimas: Rašykite klausimo tekstą. [tuščias baigia]
                """);
        String questionText = sc.nextLine();
        if (questionText.equals("")) {
            return null;
        }
        String aA = stringNotEmpty("Atsakymas A");
        String aB = stringNotEmpty("Atsakymas B");
        String aC = stringNull("Atsakymas C (gali būti tuščia)");
        String correctA = stringCorrectAnswer(aC);
        return new Question(test, questionText, aA, aB, aC, correctA);
    }

    private String stringCorrectAnswer(String aC) {
        do {
            String įvestis = stringNotEmpty("Kuris atsakymas teisingas?");
            boolean cNotNull = !(aC == null);
            if (cNotNull && įvestis.equals("C") || įvestis.equals("c") || įvestis.equals("3")) {
                return "C";
            } else if (įvestis.equals("A") || įvestis.equals("a") || įvestis.equals("1")) {
                return "A";
            } else if (įvestis.equals("B") || įvestis.equals("b") || įvestis.equals("2")) {
                return "B";
            } else {
                System.out.println("Pasirinkite [A a 1] / [B b 2]" + (cNotNull ? " / [C c 3]" : ""));
            }
        } while (true);
    }


}
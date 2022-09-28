package ProjectDB.Services;

import ProjectDB.Repositories.TestRepository;
import ProjectDB.Tables.Answer;
import ProjectDB.Tables.Test;
import ProjectDB.Tables.TestAttempt;
import ProjectDB.Tables.User;

import java.util.List;

public class TestService {
    private final TestRepository testRepository;

    public TestService() {
        testRepository = new TestRepository();
    }

    public void createNewTest(Test test) {
        testRepository.createTest(test);
    }

  /*  public void updateTest(Test test) {
        testRepository.updateTest(test);
    }*/

    public List<Test> getTestList() {
        return testRepository.getTestList();
    }

    public List<Test> getTestListByTeacher(User user) {
//        return null;
        return testRepository.getTestList().stream().filter(test -> test.getUser().getId().equals(user.getId())).toList();
//        return testRepository.getTestListByTeacher(user);
    }


    public void seeTestResults(Test test) {
        TestAttemptService tas = new TestAttemptService();
        List<TestAttempt> allAttempts = tas.getListByTest(test);
        if (allAttempts.size() == 0) {
            System.out.println("Testų nėra");
        } else {

            for (TestAttempt ta : tas.getListByTest(test)) {
                seeAttemptResult(ta);
            }
        }
    }

    public void seeAttemptResult(TestAttempt ta) {
        System.out.printf("%s, %s\n%s.%s, %d%s",
                ta.getTest().getTestName(),
                ta.getDateOfTest(),
                ta.getStudent().getName().charAt(0),
                ta.getStudent().getSurname(),
                sumCorrectAnswers(ta), "%\n"
        );

    }

    private int sumCorrectAnswers(TestAttempt ta) {
        int g = 0;
        AnswerService as = new AnswerService();
        List<Answer> answers = as.getAnswersByAttempt(ta);
        int all = answers.size();
        if (all == 0) {
            System.out.println("List empty");
            return 0;
        } else {
            for (Answer a : answers) {
                if (a.getQuestion().getCorrectChoice().equals(a.getAnswerChoice())) {
                    g++;
                }
            }
            return (g * 100) / all;
        }

    }

    public void deleteTest(Test test) {
//        testRepository.archiveDeleteTest(test);
        testRepository.deleteTest(test);
    }
}

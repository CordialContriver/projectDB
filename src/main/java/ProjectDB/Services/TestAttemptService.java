package ProjectDB.Services;

import ProjectDB.Repositories.TestAttemptRepository;
import ProjectDB.Tables.Test;
import ProjectDB.Tables.TestAttempt;
import ProjectDB.Tables.User;

import java.util.List;

public class TestAttemptService {
    private final TestAttemptRepository testAttemptRepository;
    public TestAttemptService(){ testAttemptRepository  = new TestAttemptRepository(); }

    public void newTestAttempt(TestAttempt testAttempt) {
        testAttemptRepository.createTestAttempt(testAttempt);
    }

    public List<TestAttempt> getListByTest(Test test) {
        return testAttemptRepository.getAttemptList().stream().filter(testA -> testA.getTest().getId().equals(test.getId())).toList();

//        return testAttemptRepository.getAttemptListByTest(test);
    }

    public TestAttempt getTAByTest(Test test) {
        return testAttemptRepository.getTAByTest(test);
    }


    public List<TestAttempt> getListByUser(User user) {
        return testAttemptRepository.getAttemptList().stream().filter(ta-> ta.getStudent().getId().equals(user.getId())).toList();
    }
}

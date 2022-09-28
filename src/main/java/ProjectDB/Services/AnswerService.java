package ProjectDB.Services;

import ProjectDB.Repositories.AnswerRepository;
import ProjectDB.Tables.Answer;
import ProjectDB.Tables.TestAttempt;

import java.util.List;

public class AnswerService {
    private final AnswerRepository answerRepository;
    public AnswerService() {
        answerRepository = new AnswerRepository();
    }

    public void createNewAnswer(Answer answer) {
        answerRepository.createNewAnswer(answer);

    }

    public List<Answer> getAnswerList() {
        return answerRepository.getAnswerList();
    }

    public List<Answer> getAnswersByAttempt(TestAttempt ta) {
        return answerRepository.getAnswerList().stream().filter(a -> a.getTestAttempt().getId().equals(ta.getId())).toList();


    }


}

package ProjectDB.Services;

import ProjectDB.Repositories.QuestionRepository;
import ProjectDB.Tables.Question;
import ProjectDB.Tables.Test;

import java.util.ArrayList;
import java.util.List;

public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService() {
        questionRepository = new QuestionRepository();
    }

    public void addNewQuestion(Question newQuestion) {
        questionRepository.addNewQuestion(newQuestion);
    }

    public List<Question> getQuestionListByTest(Test test) {
        List<Question> questions = new ArrayList<>();
        for (Question q : questionRepository.getQuestionList()){
            if (q.getTest().getId().equals(test.getId())) {
                questions.add(q);
            }
        }
        return questions;
//        return questionRepository.getQuestionList().stream().filter(question -> question.getTest()==(test)).toList();
//        return questionRepository.getQuestionListByTest(test);
    }
}

package ProjectDB.Tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answers")

public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "testattempt_id")
    private TestAttempt testAttempt;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    private String answerChoice;

    public Answer(TestAttempt testAttempt, Question question, String answerChoice) {
        this.testAttempt = testAttempt;
        this.question = question;
        this.answerChoice = answerChoice;
    }

    @Override
    public String toString() {
        return "Answer{"+
                "id="+id+
                ", testAttempt="+testAttempt.getId()+
                ", question="+question.getId()+", "+question.getCorrectChoice()+
                ", answer='"+answerChoice+'\''+
                '}';
    }
}
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
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    private String questionText;
    private String varA;
    private String varB;
    private String varC;
    private String correctChoice;

    public Question(Test test, String questionText, String varA, String varB, String varC, String correctChoice) {
        this.test = test;
        this.questionText = questionText;
        this.varA = varA;
        this.varB = varB;
        this.varC = varC;
        this.correctChoice = correctChoice;
    }
}


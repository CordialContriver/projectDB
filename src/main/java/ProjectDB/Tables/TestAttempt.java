package ProjectDB.Tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "testattempts")

public class TestAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student;
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    private LocalDate dateOfTest;

    public TestAttempt(User student, Test test, LocalDate dateOfTest) {
        this.student = student;
        this.test = test;
        this.dateOfTest = dateOfTest;
    }
}

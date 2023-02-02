package nl.briansporkslede.workshopper.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings" /*, uniqueConstraints = { @UniqueConstraint(columnNames = {"student_id", "workshop_id"})}*/ )
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean attended;
    private String feedback;
    private LocalDateTime dtBooked;

    @ManyToOne
    @JoinColumn(name = "student_id")            // optioneel
    private Student student;

    @ManyToOne
    @JoinColumn(name = "workshop_id")           // optioneel
    private Workshop workshop;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDateTime getDtBooked() {
        return dtBooked;
    }

    public void setDtBooked(LocalDateTime dtBooked) {
        this.dtBooked = dtBooked;
    }
}
package nl.briansporkslede.workshopper.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations", uniqueConstraints = { @UniqueConstraint(columnNames = {"student_id", "workshop_id"})  })
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dtReserved;
    private LocalDateTime dtCancelled;
    private LocalDateTime dtProcessed;
    private Integer priority;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "student_id")            // optioneel
    private Student student;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "workshop_id")           // optioneel
    private Workshop workshop;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDateTime getDtReserved() {
        return dtReserved;
    }

    public void setDtReserved(LocalDateTime dtReserved) {
        this.dtReserved = dtReserved;
    }

    public LocalDateTime getDtCancelled() {
        return dtCancelled;
    }

    public void setDtCancelled(LocalDateTime dtCancelled) {
        this.dtCancelled = dtCancelled;
    }

    public LocalDateTime getDtProcessed() {
        return dtProcessed;
    }

    public void setDtProcessed(LocalDateTime dtProcessed) {
        this.dtProcessed = dtProcessed;
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
}
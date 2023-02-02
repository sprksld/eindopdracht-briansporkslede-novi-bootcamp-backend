package nl.briansporkslede.workshopper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private char gender;

    @Column( nullable = false,unique = true)
    private String name;
    private Integer gradeYear;
    private String className;

    @OneToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name = "mentor_id")
    private Teacher mentor;

    @OneToMany(mappedBy = "student")
    @JsonIgnore                             // prevent recursion
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "student", cascade=CascadeType.DETACH)        // sequence fix?
    @JsonIgnore                             // prevent recursion
    private List<Booking> bookings;

    private Long user_id;      // TODO change back to Class, when error is gone

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public Integer getGradeYear() {
        return gradeYear;
    }

    public void setGradeYear(Integer gradeYear) {
        this.gradeYear = gradeYear;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Teacher getMentor() {
        return mentor;
    }

    public void setMentor(Teacher mentor) {
        this.mentor = mentor;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}

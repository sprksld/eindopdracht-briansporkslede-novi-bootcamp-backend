package nl.briansporkslede.workshopper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "workshops")
public class Workshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dtStart;
    private Integer duration;
    private String room;
    private String category;
    private String title;
    private String description;
    private Integer minGradeYear;
    private Integer maxGradeYear;
    private Integer minParticipants;
    private Integer maxParticipants;
    private LocalDateTime dtReservationsStart;
    private LocalDateTime dtReservationsEnd;

    @ManyToOne (/*fetch = FetchType.LAZY,*/ cascade=CascadeType.DETACH)
    @JoinColumn(name = "teacher_id")            // optioneel
    @JsonIgnore
    private Teacher teacher;

    @ManyToOne (/*fetch = FetchType.LAZY,*/ cascade=CascadeType.DETACH)
    @JoinColumn(name = "creator_id")            // optioneel
    @JsonIgnore
    private Teacher creator;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDtStart() {
        return dtStart;
    }

    public void setDtStart(LocalDateTime dtStart) {
        this.dtStart = dtStart;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String classroom) {
        this.room = classroom;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinGradeYear() {
        return minGradeYear;
    }

    public void setMinGradeYear(Integer minGradeYear) {
        this.minGradeYear = minGradeYear;
    }

    public Integer getMaxGradeYear() {
        return maxGradeYear;
    }

    public void setMaxGradeYear(Integer maxGradeYear) {
        this.maxGradeYear = maxGradeYear;
    }

    public Integer getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(Integer minParticipants) {
        this.minParticipants = minParticipants;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public LocalDateTime getDtReservationsStart() {
        return dtReservationsStart;
    }

    public void setDtReservationsStart(LocalDateTime dtReservationsStart) {
        this.dtReservationsStart = dtReservationsStart;
    }

    public LocalDateTime getDtReservationsEnd() {
        return dtReservationsEnd;
    }

    public void setDtReservationsEnd(LocalDateTime dtReservationsEnd) {
        this.dtReservationsEnd = dtReservationsEnd;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Teacher getCreator() {
        return creator;
    }

    public void setCreator(Teacher creator) {
        this.creator = creator;
    }

}
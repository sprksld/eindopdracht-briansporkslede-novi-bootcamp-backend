package nl.briansporkslede.workshopper.dto;

import net.bytebuddy.utility.nullability.MaybeNull;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.model.Workshop;
import org.springframework.stereotype.Service;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Service
public class WorkshopInputDto {
    //    public Long id;
    @FutureOrPresent
    public LocalDateTime dtStart;
    @Min(10)
    public int duration;
    @NotEmpty
    public String room;
    @NotEmpty
    @Size(min = 2, max = 20)
    public String category;
    @NotEmpty
    @Size(min = 2, max = 50)
    public String title;
    @NotEmpty
    @Size(min = 10)
    public String description;
    @Min(1)
    @Max(8)
    public int minGradeYear;
    @Min(1)
    @Max(8)
    public int maxGradeYear;
    public int minParticipants;
    @Min(2)
    public int maxParticipants;
    @FutureOrPresent(message = "vul een datum in de toekomst in, of vandaag, of laat het veld leeg")
    @MaybeNull
    public LocalDateTime dtReservationsStart;
    @FutureOrPresent(message = "creation date must be future or present")
    @MaybeNull
    public LocalDateTime dtReservationsEnd;

    public Teacher teacher;
    public Teacher creator;
    public Long teacher_id;
    public Long creator_id;

    public Workshop toClass() {
        Workshop workshop = new Workshop();

        workshop.setDtStart(this.dtStart);
        workshop.setDuration(this.duration);
        workshop.setRoom(this.room);
        workshop.setCategory(this.category);
        workshop.setTitle(this.title);
        workshop.setDescription(this.description);
        workshop.setMinGradeYear(this.minGradeYear);
        workshop.setMaxGradeYear(this.maxGradeYear);
        workshop.setMinParticipants(this.minParticipants);
        workshop.setMaxParticipants(this.maxParticipants);
        workshop.setDtReservationsStart(this.dtReservationsStart);
        workshop.setDtReservationsEnd(this.dtReservationsEnd);

        Teacher teacher = new Teacher();
        teacher.setId(this.teacher_id);
        workshop.setTeacher(teacher);
        if (this.teacher_id == 0L)
            workshop.setTeacher(null);

        Teacher creator = new Teacher();
        creator.setId(this.creator_id);
        workshop.setCreator(creator);
        if (this.creator_id == 0L)
            workshop.setCreator(null);

        return workshop;
    }

}

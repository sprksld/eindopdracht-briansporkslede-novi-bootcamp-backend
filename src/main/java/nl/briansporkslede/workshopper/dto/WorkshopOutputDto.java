package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.model.Workshop;

import java.time.LocalDateTime;

public class  WorkshopOutputDto {
    public Long id;
    public LocalDateTime dtStart;
    public Integer duration;
    public String room;
    public String category;
    public String title;
    public String description;
    public Integer minGradeYear;
    public Integer maxGradeYear;
    public Integer minParticipants;
    public Integer maxParticipants;
    public LocalDateTime dtReservationsStart;
    public LocalDateTime dtReservationsEnd;
    public Teacher teacher;
    public Teacher creator;


    public WorkshopOutputDto toDto(Workshop workshop) {
        WorkshopOutputDto dto = new WorkshopOutputDto();

        dto.id = workshop.getId();
        dto.dtStart = workshop.getDtStart();
        dto.duration = workshop.getDuration();
        dto.room = workshop.getRoom();
        dto.category = workshop.getCategory();
        dto.title = workshop.getTitle();
        dto.description = workshop.getDescription();
        dto.minGradeYear = workshop.getMinGradeYear();
        dto.maxGradeYear = workshop.getMaxGradeYear();
        dto.minParticipants = workshop.getMinParticipants();
        dto.maxParticipants = workshop.getMaxParticipants();
        dto.dtReservationsStart = workshop.getDtReservationsStart();
        dto.dtReservationsEnd = workshop.getDtReservationsEnd();
        dto.teacher = workshop.getTeacher();
        dto.creator = workshop.getCreator();

        return dto;
    }

}
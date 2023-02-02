package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Teacher;

public class TeacherOutputDto {
    public Long id;
    public String name;


    public TeacherOutputDto toDto(Teacher teacher) {
        TeacherOutputDto dto = new TeacherOutputDto();

        dto.id = teacher.getId();
        dto.name = teacher.getName();

        return dto;
    }
}

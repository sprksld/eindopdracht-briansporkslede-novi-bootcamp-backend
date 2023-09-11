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

    public Teacher toClass() {
        Teacher teacher = new Teacher();

        teacher.setId(this.id);
        teacher.setName(this.name);

        return teacher;
    }
}

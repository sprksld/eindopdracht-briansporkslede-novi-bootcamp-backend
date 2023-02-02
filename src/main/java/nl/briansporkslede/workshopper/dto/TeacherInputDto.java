package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Teacher;

import javax.validation.constraints.*;

public class TeacherInputDto {
    public Long id;
    @NotBlank
    @Size(min=2,max=60)
    public String name;

    public Teacher toClass() {
        Teacher teacher = new Teacher();

        teacher.setName(this.name);

        return teacher;
    }
}

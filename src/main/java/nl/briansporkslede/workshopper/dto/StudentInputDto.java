package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Teacher;
import org.springframework.stereotype.Service;

import javax.validation.constraints.*;

@Service
public class StudentInputDto {
    public Long id;

    public char gender;
    @NotBlank
    @Size(min=2,max=60)
    public String name;
    @Min(1)
    @Max(8)
    public Integer gradeYear;
    @NotEmpty
    @Size(min=2,max=32)
    public String className;

    public Long mentor_id;

    public Student toClass() {
        Student student = new Student();

        student.setGender(this.gender);
        student.setName(this.name);
        student.setGradeYear(this.gradeYear);
        student.setClassName(this.className);

        if (this.mentor_id == 0L)
            student.setMentor(null);
        else {
            Teacher teacher = new Teacher();
            teacher.setId(this.mentor_id);
            student.setMentor(teacher);
        }

        return student;
    }
}

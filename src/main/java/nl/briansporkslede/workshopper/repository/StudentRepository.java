package nl.briansporkslede.workshopper.repository;

import nl.briansporkslede.workshopper.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Iterable<Student> findByGradeYearEquals(String year);
    public Iterable<Student> findByClassName(String classname);
    public Iterable<Student> findStudentsByMentorId(Long mentor);

}

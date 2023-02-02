package nl.briansporkslede.workshopper.repository;

import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    public Iterable<Teacher> findTeacherById( Long id);
}

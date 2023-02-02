package nl.briansporkslede.workshopper.repository;

import nl.briansporkslede.workshopper.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    public Iterable<Workshop> findAllByOrderByDtStart();

    @Query(value = "SELECT * FROM workshops w"
            + " WHERE ( w.min_grade_year IS NULL OR w.min_grade_year <= ?1)"
            + " AND ( w.max_grade_year IS NULL OR w.max_grade_year >= ?1)"
            + " AND ( NOT( w.min_grade_year IS NULL AND w.max_grade_year IS NULL ) )"
            , nativeQuery = true)
    public Iterable<Workshop> findWorkshopsForMyGradeYear(Integer gradeYear);

    @Query(value = "SELECT * FROM workshops w"
            + " FULL JOIN bookings b ON w.id = b.workshop_id AND b.student_id = ?1"
            + " WHERE b.student_id IS NULL AND w.id IS NOT NULL"
            + " AND ( w.min_grade_year IS NULL OR w.min_grade_year <= (SELECT grade_year FROM students WHERE id = ?1) )"
            + " AND ( w.max_grade_year IS NULL OR w.max_grade_year >= (SELECT grade_year FROM students WHERE id = ?1))"
            + " AND ( NOT( w.min_grade_year IS NULL AND w.max_grade_year IS NULL ) )"
            + " AND w.dt_start >= CURRENT_DATE"
            + " AND w.dt_reservations_start <= CURRENT_DATE AND ( w.dt_reservations_end is NULL OR w.dt_reservations_end > CURRENT_DATE)" // en jouw eventuele reservation nog niet behandeld is
            + " ORDER BY dt_start"
            , nativeQuery = true)
    public Iterable<Workshop> findAllOpenWorkshopsByStudent(Long studentId);




    @Query( value = "SELECT * FROM workshops w"
            + ", ( SELECT MIN(findMin) as minGrad, MAX(findMax) as maxGrad"
                    + " FROM (SELECT DISTINCT s.grade_year as findMax FROM students s WHERE s.mentor_id > ?1) as x"
                    + "    , (SELECT DISTINCT s.grade_year as findMin FROM students s WHERE s.mentor_id > ?1) as y"
                    + " ) as z"
            + " WHERE ( min_grade_year <= z.minGrad AND max_grade_year >= z.minGrad )"
            + " OR ( min_grade_year <= z.maxGrad AND max_grade_year >= z.maxGrad )"
            + " ORDER BY w.dt_start"
            , nativeQuery = true )
    public Iterable<Workshop> findAllWorkshopsByMentor(Long teacherId);

    @Query(value = "SELECT * FROM workshops w"
            + " WHERE teacher_id = ?1"
            + " AND w.dt_start >= CURRENT_DATE"
            + " ORDER BY dt_start"
            , nativeQuery = true)
    public Iterable<Workshop> findAllWorkshopsByTeacher(Long teacherId);

    @Query( value = "SELECT DISTINCT category FROM workshops ORDER BY category", nativeQuery = true)
    public Iterable<String> findDistinctCategories();

    @Query( value = "SELECT DISTINCT room FROM workshops ORDER BY room", nativeQuery = true)
    public Iterable<String> findDistinctRooms();

}

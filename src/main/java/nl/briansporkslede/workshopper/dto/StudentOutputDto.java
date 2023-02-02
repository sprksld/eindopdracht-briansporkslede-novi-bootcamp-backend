package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.*;

import java.util.List;

public class StudentOutputDto {
    public Long id;
    public char gender;
    public String name;
    public Integer gradeYear;
    public String className;
    public Teacher mentor;
    private List<Reservation> reservations;
    private List<Booking> bookings;

    public StudentOutputDto toDto(Student student) {
        StudentOutputDto dto = new StudentOutputDto();

        dto.id = student.getId();
        dto.gender = student.getGender();
        dto.name = student.getName();
        dto.gradeYear = student.getGradeYear();
        dto.className = student.getClassName();
        dto.mentor = student.getMentor();
        dto.reservations = student.getReservations();
        dto.bookings = student.getBookings();

        return dto;
    }

}

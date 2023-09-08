package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.ReservationOutputDto;
import nl.briansporkslede.workshopper.dto.WorkshopOutputDto;
import nl.briansporkslede.workshopper.model.Teacher;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import nl.briansporkslede.workshopper.exception.RecordNotFoundException;
import nl.briansporkslede.workshopper.repository.WorkshopRepository;
import nl.briansporkslede.workshopper.dto.WorkshopInputDto;
import nl.briansporkslede.workshopper.model.Workshop;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class WorkshopService {
    private final WorkshopRepository repos; // constructor injection instead of @Autowired

    public WorkshopService(WorkshopRepository r) {
        this.repos = r;
    }

    public WorkshopOutputDto getWorkshop(Long id) {
        Optional<Workshop> optionalWorkshop = repos.findById(id);
        if (optionalWorkshop.isEmpty())
            throw new RecordNotFoundException("workshop id not found");

        Workshop workshop = optionalWorkshop.get();
        WorkshopOutputDto dto = new WorkshopOutputDto();
        return dto.toDto(workshop);
    }

    public Iterable<WorkshopOutputDto> getWorkshops() {
        Iterable<Workshop> allWorkshops = repos.findAllByOrderByDtStart();
        ArrayList<WorkshopOutputDto> foundWorkshops = new ArrayList<>();

        for (Workshop workshop : allWorkshops) {
            WorkshopOutputDto dto = new WorkshopOutputDto();
            foundWorkshops.add(dto.toDto(workshop));
        }
        return (foundWorkshops);

    }

    public Long createWorkshop(WorkshopInputDto workshopInputDto) {
        Workshop workshop = workshopInputDto.toClass();
        repos.save(workshop);
        return workshop.getId();
    }

    public Long updateWorkshop(Long id, WorkshopInputDto dto) {
        Optional<Workshop> optionalWorkshop = repos.findById(id);
        if (optionalWorkshop.isEmpty()) throw new RecordNotFoundException("invalid workshop id");

        Workshop updatedWorkshop = dto.toClass();
        updatedWorkshop.setId(id);
        repos.save(updatedWorkshop);

        return updatedWorkshop.getId();
    }

    public Long deleteWorkshop(Long id) {
        repos.deleteById(id);
        if (repos.existsById(id))
            throw new RecordNotFoundException("workshop could not be deleted");

        return null;
    }

    public Iterable<WorkshopOutputDto> getMyWorkshopOptions(Integer myGradeYear) {
        Iterable<Workshop> allWorkshops = repos.findWorkshopsForMyGradeYear(myGradeYear);
        ArrayList<WorkshopOutputDto> foundWorkshops = new ArrayList<>();

        for (Workshop workshop : allWorkshops) {
            WorkshopOutputDto dto = new WorkshopOutputDto();
            foundWorkshops.add(dto.toDto(workshop));
        }
        return (foundWorkshops);
    }

    public Iterable<WorkshopOutputDto> getWorkshopsByStudent(Long studentId) {
        Iterable<Workshop> allWorkshops = repos.findAllOpenWorkshopsByStudent(studentId);
        ArrayList<WorkshopOutputDto> foundWorkshops = new ArrayList<>();

        for (Workshop workshop : allWorkshops) {
            WorkshopOutputDto dto = new WorkshopOutputDto();
            foundWorkshops.add(dto.toDto(workshop));
        }
        return (foundWorkshops);
    }

    public Iterable<WorkshopOutputDto> getWorkshopsByTeacher(Long teacherId) {
        Iterable<Workshop> allWorkshops = repos.findAllWorkshopsByTeacher(teacherId);
        ArrayList<WorkshopOutputDto> foundWorkshops = new ArrayList<>();

        for (Workshop workshop : allWorkshops) {
            WorkshopOutputDto dto = new WorkshopOutputDto();
            foundWorkshops.add(dto.toDto(workshop));
        }
        return (foundWorkshops);
    }

    public Iterable<WorkshopOutputDto> getWorkshopsByMentor(Long teacherId) {
        Iterable<Workshop> allWorkshops = repos.findAllWorkshopsByMentor(teacherId);
        ArrayList<WorkshopOutputDto> foundWorkshops = new ArrayList<>();

        for (Workshop workshop : allWorkshops) {
            WorkshopOutputDto dto = new WorkshopOutputDto();
            foundWorkshops.add(dto.toDto(workshop));
        }
        return (foundWorkshops);
    }

    public Iterable<String> getCategories() {
        return repos.findDistinctCategories();
    }

    public Iterable<String> getRooms() {
        return repos.findDistinctRooms();
    }

}

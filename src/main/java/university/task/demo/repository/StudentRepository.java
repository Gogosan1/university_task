package university.task.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import university.task.demo.model.dto.StudentResponseDto;
import university.task.demo.model.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT new university.task.demo.model.dto.StudentResponseDto(stud.id, stud.fullName, stud.acceptanceDate)" +
        " FROM Student stud" +
        " WHERE stud.group.id = :groupId" +
        " ORDER BY stud.fullName ASC"
    )
    List<StudentResponseDto> findAllByGroupIdOrderByFullNameAsc(Long groupId);
    
}

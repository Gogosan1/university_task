package university.task.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import university.task.demo.model.dto.GroupWithStudentsCountResponseDto;
import university.task.demo.model.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT new university.task.demo.model.dto.GroupWithStudentsCountResponseDto(group.id, group.number, COUNT(stud))" +
        " FROM Group gr" +
        " LEFT JOIN Student stud ON gr = stud.group" +
        " GROUP BY gr.id, gr.number" +
        " ORDER BY gr.createdAt DESC"
    )
    Page<GroupWithStudentsCountResponseDto> findAllWithStudentCount(Pageable pageable);
    
}

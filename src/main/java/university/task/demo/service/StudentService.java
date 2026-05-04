package university.task.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import university.task.demo.model.dto.StudentCreateRequestDto;
import university.task.demo.model.dto.StudentResponseDto;
import university.task.demo.model.entity.Student;
import university.task.demo.util.mapper.StudentMapper;
import university.task.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final GroupService groupService;

    @Transactional
    public StudentResponseDto addStudent(StudentCreateRequestDto studentDto) {

        if (!groupService.groupExists(studentDto.groupId())) {
            log.warn("Attempt to add student to non-existent group id: {}", studentDto.groupId());
            throw new EntityNotFoundException("Group with id " + studentDto.groupId() + " not found");
        }

        log.debug("Adding student: {} {}", studentDto.fullName(), " to group id: {}", studentDto.groupId());
        Student student = studentMapper.toEntity(studentDto);
        studentRepository.save(student);
        log.info("Student saved with id: {}", student.getId());
        return studentMapper.toDto(student);
    }

    @Transactional
    public void deleteStudent(Long id){

        if (!studentRepository.existsById(id)) {
            log.warn("Attempt to delete non-existent student with id: {}", id);
            throw new EntityNotFoundException("Student with id " + id + " not found");
        }
        log.debug("Deleting student with id: {}", id);
        studentRepository.deleteById(id);
        log.info("Student with id: {} deleted", id);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDto> getStudentsByGroupId(Long groupId) {
        log.debug("Fetching students for group id: {}", groupId);
        List<StudentResponseDto> result = studentRepository.findAllByGroupIdOrderByFullNameAsc(groupId);
        log.debug("Fetched {} students for group id: {}", result.size(), groupId);
        return result;
    }
}

package university.task.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import university.task.demo.model.dto.StudentCreateRequestDto;
import university.task.demo.model.dto.StudentResponseDto;
import university.task.demo.model.entity.Student;
import university.task.demo.util.mapper.StudentMapper;
import university.task.demo.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private StudentService studentService;

    private StudentCreateRequestDto requestDto;
    private Student student;
    private StudentResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = new StudentCreateRequestDto("Ivan Ivanov", 1L, LocalDate.of(2024, 9, 1));
        student = new Student();
        student.setId(1L);
        student.setFullName("Ivan Ivanov");
        student.setAcceptanceDate(LocalDate.of(2024, 9, 1));
        responseDto = new StudentResponseDto(1L, "Ivan Ivanov", LocalDate.of(2024, 9, 1));
    }

    @Test
    void addStudent_whenGroupDoesNotExist_throwsEntityNotFoundException() {
        when(groupService.groupExists(requestDto.groupId())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> studentService.addStudent(requestDto));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void addStudent_whenGroupExists_savesStudentAndReturnsDto() {
        when(groupService.groupExists(requestDto.groupId())).thenReturn(true);
        when(studentMapper.toEntity(requestDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.toDto(student)).thenReturn(responseDto);

        StudentResponseDto actual = studentService.addStudent(requestDto);

        assertEquals(responseDto, actual);
        verify(studentRepository).save(student);
    }

    @Test
    void deleteStudent_whenStudentDoesNotExist_throwsEntityNotFoundException() {
        when(studentRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> studentService.deleteStudent(1L));
        verify(studentRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void deleteStudent_whenStudentExists_deletesStudent() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        studentService.deleteStudent(1L);

        verify(studentRepository).deleteById(1L);
    }

    @Test
    void getStudentsByGroupId_returnsStudentResponseDtoList() {
        when(studentRepository.findAllByGroupIdOrderByFullNameAsc(1L)).thenReturn(List.of(responseDto));

        List<StudentResponseDto> actual = studentService.getStudentsByGroupId(1L);

        assertEquals(1, actual.size());
        assertEquals(responseDto, actual.get(0));
    }
}

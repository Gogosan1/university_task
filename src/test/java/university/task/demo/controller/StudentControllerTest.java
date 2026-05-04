package university.task.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import university.task.demo.model.dto.StudentCreateRequestDto;
import university.task.demo.model.dto.StudentResponseDto;
import university.task.demo.service.StudentService;
import university.task.demo.util.exception.GlobalExceptionHandler;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void addStudent_returnsStudentResponseDto() throws Exception {
        String requestJson = "{\"fullName\":\"Ivan Ivanov\",\"groupId\":1,\"acceptanceDate\":\"2024-09-01\"}";
        StudentResponseDto response = new StudentResponseDto(1L, "Ivan Ivanov", java.time.LocalDate.of(2024, 9, 1));

        when(studentService.addStudent(any(StudentCreateRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/students/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"id\":1,\"fullName\":\"Ivan Ivanov\",\"acceptanceDate\":\"2024-09-01\"}"));
    }
}

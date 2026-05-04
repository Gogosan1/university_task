package university.task.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import university.task.demo.model.dto.GroupCreateRequestDto;
import university.task.demo.model.dto.GroupWithStudentsCountResponseDto;
import university.task.demo.model.dto.StudentResponseDto;
import university.task.demo.service.GroupService;
import university.task.demo.service.StudentService;
import university.task.demo.util.exception.GlobalExceptionHandler;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void addGroup_returnsSavedGroup() throws Exception {
        String requestJson = "{\"number\":\"101\"}";

    GroupWithStudentsCountResponseDto responseDto = 
    new GroupWithStudentsCountResponseDto(1L,"101",0);

        when(groupService.addGroup(any(GroupCreateRequestDto.class))).thenReturn(responseDto);

   mockMvc.perform(post("/api/v1/groups/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.number").value("101"));
    }

    @Test
    void getGroups_returnsPagedResponse() throws Exception {
        GroupWithStudentsCountResponseDto dto = new GroupWithStudentsCountResponseDto(1L, "101", 0L);
        when(groupService.getGroups(any())).thenReturn(new org.springframework.data.domain.PageImpl<>(List.of(dto)));

        mockMvc.perform(get("/api/v1/groups?page=0&size=10"))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"content\":[{\"id\":1,\"number\":\"101\",\"studentsCount\":0}]}"));
    }

    @Test
    void getStudentsByGroup_returnsStudentResponseDtoList() throws Exception {
        StudentResponseDto response = new StudentResponseDto(1L, "Ivan Ivanov", LocalDate.of(2024, 9, 1));
        when(studentService.getStudentsByGroupId(1L)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/groups/1/students"))
            .andExpect(status().isOk())
            .andExpect(content().json("[{\"id\":1,\"fullName\":\"Ivan Ivanov\",\"acceptanceDate\":\"2024-09-01\"}]"));
    }
}

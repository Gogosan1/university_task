package university.task.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import university.task.demo.model.dto.StudentCreateRequestDto;
import university.task.demo.model.dto.StudentResponseDto;
import university.task.demo.service.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
    
    private final StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<StudentResponseDto> addStudent(@RequestBody StudentCreateRequestDto studentDto){
        return ResponseEntity.ok().body(studentService.addStudent(studentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}

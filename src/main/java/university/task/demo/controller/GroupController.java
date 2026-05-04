package university.task.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import university.task.demo.model.dto.GroupCreateRequestDto;
import university.task.demo.model.dto.GroupWithStudentsCountResponseDto;
import university.task.demo.model.dto.StudentResponseDto;
import university.task.demo.service.GroupService;
import university.task.demo.service.StudentService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {
    
    private final GroupService groupService;
    private final StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<GroupWithStudentsCountResponseDto> addGroup(@RequestBody GroupCreateRequestDto groupDto) {
        return ResponseEntity.ok().body(groupService.addGroup(groupDto));
    }

    @GetMapping
    public ResponseEntity<Page<GroupWithStudentsCountResponseDto>> getGroups(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(groupService.getGroups(PageRequest.of(page, size)));
    }

    @GetMapping("/{groupId}/students")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByGroup(@PathVariable Long groupId){
        return ResponseEntity.ok().body(studentService.getStudentsByGroupId(groupId));
    }
    
}

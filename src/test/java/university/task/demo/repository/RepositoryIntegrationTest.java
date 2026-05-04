// package university.task.demo.repository;

// import static org.assertj.core.api.Assertions.assertThat;

// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.test.context.TestPropertySource;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

// import university.task.demo.model.dto.GroupWithStudentsCountResponseDto;
// import university.task.demo.model.dto.StudentResponseDto;
// import university.task.demo.model.entity.Group;
// import university.task.demo.model.entity.Student;

// @DataJpaTest
// @TestPropertySource(properties = "spring.liquibase.enabled=false")
// class RepositoryIntegrationTest {

//     @Autowired
//     private GroupRepository groupRepository;

//     @Autowired
//     private StudentRepository studentRepository;

//     @Test
//     void findAllWithStudentCount_returnsCorrectPage() {
//         Group firstGroup = new Group();
//         firstGroup.setNumber("101");
//         firstGroup.setCreatedAt(LocalDateTime.now().minusDays(1));
//         groupRepository.save(firstGroup);

//         Group secondGroup = new Group();
//         secondGroup.setNumber("102");
//         secondGroup.setCreatedAt(LocalDateTime.now());
//         groupRepository.save(secondGroup);

//         Student student = new Student();
//         student.setFullName("Ivan Ivanov");
//         student.setAcceptanceDate(LocalDate.of(2024, 9, 1));
//         student.setGroup(firstGroup);
//         studentRepository.save(student);

//         var page = groupRepository.findAllWithStudentCount(PageRequest.of(0, 10));

//         assertThat(page.getTotalElements()).isEqualTo(2);
//         List<GroupWithStudentsCountResponseDto> content = page.getContent();
//         assertThat(content).hasSize(2);
//         assertThat(content.get(0).number()).isEqualTo("102");
//         assertThat(content.get(0).studentsCount()).isEqualTo(0);
//         assertThat(content.get(1).studentsCount()).isEqualTo(1);
//     }

//     @Test
//     void findAllByGroupIdOrderByFullNameAsc_returnsOrderedStudentResponseDtos() {
//         Group group = new Group();
//         group.setNumber("101");
//         groupRepository.save(group);

//         Student second = new Student();
//         second.setFullName("Zinaida Petrova");
//         second.setAcceptanceDate(LocalDate.of(2024, 9, 2));
//         second.setGroup(group);
//         studentRepository.save(second);

//         Student first = new Student();
//         first.setFullName("Anton Sidorov");
//         first.setAcceptanceDate(LocalDate.of(2024, 9, 1));
//         first.setGroup(group);
//         studentRepository.save(first);

//         List<StudentResponseDto> students = studentRepository.findAllByGroupIdOrderByFullNameAsc(group.getId());

//         assertThat(students).hasSize(2);
//         assertThat(students.get(0).fullName()).isEqualTo("Anton Sidorov");
//         assertThat(students.get(1).fullName()).isEqualTo("Zinaida Petrova");
//     }
// }

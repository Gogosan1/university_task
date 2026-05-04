package university.task.demo.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import university.task.demo.model.dto.StudentCreateRequestDto;
import university.task.demo.model.dto.StudentResponseDto;
import university.task.demo.model.entity.Student;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "group.id", source = "groupId")
    Student toEntity(StudentCreateRequestDto studentDto);

    StudentResponseDto toDto(Student student);
}

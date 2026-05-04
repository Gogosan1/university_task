package university.task.demo.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import university.task.demo.model.dto.GroupCreateRequestDto;
import university.task.demo.model.entity.Group;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GroupMapper {
    Group toEntity(GroupCreateRequestDto groupDto);

}
